package swa.meet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.hibernate.bytecode.enhance.internal.bytebuddy.EnhancerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swa.meet.api.MeetingApi;
import swa.meet.entities.Attendee;
import swa.meet.entities.Meeting;
import swa.meet.entities.Timeslot;
import swa.meet.requests.WishlistRequest;
import swa.meet.services.MeetingNotificationService;
import swa.meet.services.MeetingService;
import swa.meet.services.TimeslotService;
import swa.meet.util.ValidUUID;

import javax.management.Notification;
import javax.validation.Valid;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api")
public class MeetingController implements MeetingApi {
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private TimeslotService timeslotService;
    @Autowired
    private MeetingNotificationService notificationService;
    private static final Logger logger = LoggerFactory.getLogger(MeetingController.class);


    @Override
    @Operation(
            operationId = "meetingsGet",
            summary = "Retrieve all meeting schedules",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fetching all meetings", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Meeting.class)))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/meetings",
            produces = {"application/json"}
    )
    public ResponseEntity<CollectionModel<EntityModel<Meeting>>> meetingsGet() {
        List<Meeting> meetings = meetingService.getAllMeetings();
        logger.info("Fetching meetings");

        List<EntityModel<Meeting>> meetingResources = new ArrayList<>();

        for (Meeting meeting : meetings) {
            EntityModel<Meeting> resource = EntityModel.of(meeting);

            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(MeetingController.class).meetingsMeetingIdGet(meeting.getId().toString())).withSelfRel();
            resource.add(selfLink);

            for (Timeslot timeslot : meeting.getTimeslots()) {
                Link timeslotLink = WebMvcLinkBuilder.linkTo(methodOn(TimeslotController.class).getTimeslot(timeslot.getId().toString())).withSelfRel();
                timeslot.add(timeslotLink);
            }

            for (Attendee attendee : meeting.getResponses()) {
                Link attendeeLink = WebMvcLinkBuilder.linkTo(methodOn(AttendeeController.class).getAttendee(attendee.getId().toString())).withSelfRel();
                attendee.add(attendeeLink);
            }

            meetingResources.add(resource);
        }

        Link link = WebMvcLinkBuilder.linkTo(methodOn(MeetingController.class).meetingsGet()).withSelfRel();
        CollectionModel<EntityModel<Meeting>> collectionModel = CollectionModel.of(meetingResources, link);

        return ResponseEntity.ok(collectionModel);
    }

    @Override
    @Operation(
            operationId = "meetingsPost",
            summary = "Create a new meeting",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Meeting created", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Meeting.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Error during creating Meeting")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/meetings",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<EntityModel<Meeting>> meetingsPost(@Parameter(name = "Meeting", description = "", required = true) @Valid @RequestBody Meeting meeting) {
        meetingService.checkForExistingMeetingId(meeting.getId());
        Meeting createdMeeting = meetingService.saveMeeting(meeting);
        notificationService.notifyMeetingUpdate(createdMeeting);
        logger.info("Meeting created");
        timeslotService.saveAllTimeslots(createdMeeting.getTimeslots());

        EntityModel<Meeting> resource = EntityModel.of(createdMeeting);

        Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(MeetingController.class).meetingsMeetingIdGet(createdMeeting.getId().toString())).withSelfRel();
        Link creationLink = WebMvcLinkBuilder.linkTo(methodOn(MeetingController.class).meetingsPost(createdMeeting)).withRel("create meeting");
        for (Timeslot timeslot : createdMeeting.getTimeslots()) {
            Link timeslotLink = WebMvcLinkBuilder.linkTo(methodOn(TimeslotController.class).getTimeslot(timeslot.getId().toString())).withSelfRel();
            timeslot.add(timeslotLink);
        }
        for (Attendee attendee : createdMeeting.getResponses()) {
            Link attendeeLink = WebMvcLinkBuilder.linkTo(methodOn(AttendeeController.class).getAttendee(attendee.getId().toString())).withSelfRel();
            attendee.add(attendeeLink);
        }

        resource.add(selfLink, creationLink);
        return ResponseEntity.created(selfLink.toUri()).body(resource);
    }

    @Override
    @Operation(
            operationId = "meetingsMeetingIdDelete",
            summary = "Delete a specific meeting",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Meeting deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Meeting not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/meetings/{meetingId}"
    )
    public ResponseEntity<Void> meetingsMeetingIdDelete(
            @Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH)
            @ValidUUID @PathVariable("meetingId") String meetingId) {

        UUID meetingUUID;
        try {
            meetingUUID = UUID.fromString(meetingId);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid UUID format: {}", meetingId);
            return ResponseEntity.badRequest().build();
        }

        logger.info("Attempting to delete meeting with ID: {}", meetingUUID);

        Optional<Meeting> optionalMeeting = meetingService.getMeetingById(meetingUUID);

        if (optionalMeeting.isPresent()) {
            meetingService.deleteMeeting(meetingUUID);
            logger.info("Meeting deleted successfully with ID: {}", meetingUUID);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Meeting not found with ID: {}", meetingUUID);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @Operation(
            operationId = "meetingsMeetingIdGet",
            summary = "Get a specific meeting",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Details of a specific meeting", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Meeting.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Meeting not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/meetings/{meetingId}",
            produces = {"application/json"}
    )
    public ResponseEntity<EntityModel<Meeting>> meetingsMeetingIdGet(@Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH) @ValidUUID @PathVariable("meetingId") String meetingId) {
        Optional<Meeting> optionalMeeting = meetingService.getMeetingById(UUID.fromString(meetingId));
        logger.info("Fetching specific meeting");
        if (optionalMeeting.isPresent()) {
            Meeting meeting = optionalMeeting.get();
            try {
                EntityModel<Meeting> resource = EntityModel.of(meeting);
                Method method = MeetingController.class.getMethod("meetingsMeetingIdGet", String.class);
                Link selfLink = WebMvcLinkBuilder.linkTo(method, meetingId).withSelfRel();
                Link attendeesLink = linkTo(methodOn(AttendeeController.class).meetingsMeetingIdResponsesGet(meetingId, new WishlistRequest())).withRel("attendees");
                Link timeslotsLink = linkTo(methodOn(MeetingController.class).getTimeslots(meetingId)).withRel("timeslots");
                resource.add(selfLink, attendeesLink, timeslotsLink);

                //embedded objects
                for (Timeslot timeslot : meeting.getTimeslots()) {
                    timeslot.removeLinks();
                    timeslot.add(linkTo(methodOn(TimeslotController.class).getTimeslot(timeslot.getId().toString())).withSelfRel());
                }
                for (Attendee attendee : meeting.getResponses()) {
                    attendee.removeLinks();
                    attendee.add(linkTo(methodOn(AttendeeController.class).getAttendee(attendee.getId().toString())).withSelfRel());
                }

                return ResponseEntity.ok(resource);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Operation(
            operationId = "meetingsMeetingIdPut",
            summary = "Update a specific meeting",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Meeting updated successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Meeting.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Meeting not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/meetings/{meetingId}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    public ResponseEntity<EntityModel<Meeting>> meetingsMeetingIdPut(
            @Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH) @ValidUUID @PathVariable("meetingId") String meetingId,
            @Parameter(name = "Meeting", description = "", required = true) @Valid @RequestBody Meeting meeting
    ) {
        logger.info("UPDATING");
        Optional<Meeting> optionalMeeting = meetingService.getMeetingById(UUID.fromString(meetingId));
        if (optionalMeeting.isPresent()) {
            Meeting updatedMeeting = meetingService.updateMeeting(UUID.fromString(meetingId), meeting);
            notificationService.notifyMeetingUpdate(updatedMeeting);

            EntityModel<Meeting> resource = EntityModel.of(updatedMeeting);

            Link selfLink = WebMvcLinkBuilder.linkTo(methodOn(MeetingController.class).meetingsMeetingIdGet(meetingId)).withSelfRel();
            Link updateLink = WebMvcLinkBuilder.linkTo(methodOn(MeetingController.class).meetingsMeetingIdPut(meetingId, meeting)).withRel("update meeting");

            resource.add(selfLink, updateLink);

            for (Timeslot timeslot : meeting.getTimeslots()) {
                timeslot.removeLinks();
                timeslot.add(linkTo(methodOn(TimeslotController.class).getTimeslot(timeslot.getId().toString())).withSelfRel());
            }
            for (Attendee attendee : meeting.getResponses()) {
                attendee.removeLinks();
                attendee.add(linkTo(methodOn(AttendeeController.class).getAttendee(attendee.getId().toString())).withSelfRel());
            }

            return ResponseEntity.ok(resource);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Operation(
            operationId = "getTimeslots",
            summary = "Get all timeslots for the specific meeting ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Details of specific timeslots", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Timeslot.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Timeslots not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/meetings/{meetingId}/timeslots",
            produces = {"application/json"}
    )
    public ResponseEntity<CollectionModel<Timeslot>> getTimeslots(
            @Parameter(name = "meetingId", description = "ID of the meeting", required = true, in = ParameterIn.PATH) @ValidUUID @PathVariable("meetingId") String meetingId) {

        List<Timeslot> timeslots = timeslotService.getTimeslotsByMeetingId(UUID.fromString(meetingId));

        if (timeslots.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        for (Timeslot timeslot : timeslots) {
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TimeslotController.class).getTimeslot(timeslot.getId().toString())).withSelfRel();
            timeslot.add(selfLink);
        }

        CollectionModel<Timeslot> collectionModel = CollectionModel.of(timeslots);
        collectionModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MeetingController.class).getTimeslots(meetingId)).withSelfRel());

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);

    }

}
