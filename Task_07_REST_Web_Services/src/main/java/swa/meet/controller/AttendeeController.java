package swa.meet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swa.meet.api.AttendeeApi;
import swa.meet.entities.Attendee;
import swa.meet.entities.Meeting;
import swa.meet.requests.WishlistRequest;
import swa.meet.services.AttendeeService;
import swa.meet.services.MeetingService;
import swa.meet.services.TimeslotService;
import swa.meet.util.FieldFilter;
import swa.meet.util.ValidUUID;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
public class AttendeeController implements AttendeeApi {
    @Autowired
    AttendeeService attendeeService;

    @Autowired
    MeetingService meetingService;

    private static final Logger logger = LoggerFactory.getLogger(AttendeeController.class);

    @Override
    @Operation(
            operationId = "meetingsMeetingIdAttendancePost",
            summary = "Submit a response for a meeting",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Attendance submitted successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Attendee.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Meeting not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/meetings/{meetingId}/attendance",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<EntityModel<Attendee>> meetingsMeetingIdAttendancePost(
            @Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH) @ValidUUID @PathVariable("meetingId") String meetingId,
            @Parameter(name = "Attendee", description = "", required = true) @Valid @RequestBody Attendee attendee) {
        Optional<Meeting> optionalMeeting = meetingService.getMeetingById(UUID.fromString(meetingId));
        if(optionalMeeting.isPresent()){
            Meeting meeting = optionalMeeting.get();

            attendeeService.saveAttendee(attendee, meeting);
            meetingService.saveMeeting(meeting);

            EntityModel<Attendee> resource = EntityModel.of(attendee);
            try {
                Method method = AttendeeController.class.getMethod("meetingsMeetingIdAttendancePost", String.class, Attendee.class);
                Link selfLink = WebMvcLinkBuilder.linkTo(method, meetingId, attendee).withSelfRel();
                Link meetingLink = WebMvcLinkBuilder.linkTo(methodOn(MeetingController.class).meetingsMeetingIdGet(meetingId)).withRel("meeting");

                resource.add(selfLink, meetingLink);
                return ResponseEntity.created(selfLink.toUri()).body(resource);
            }catch(NoSuchMethodException e){
                return ResponseEntity.internalServerError().build();
            }

        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    @Operation(
            operationId = "meetingsMeetingIdResponsesGet",
            summary = "Get all attendances for a meeting",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all attendances", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Attendee.class)))
                    }),
                    @ApiResponse(responseCode = "404", description = "Meeting not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/meetings/{meetingId}/responses",
            produces = { "application/json" }
    )
    public ResponseEntity<Object> meetingsMeetingIdResponsesGet(@Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH) @ValidUUID @PathVariable("meetingId") String meetingId,  @RequestBody WishlistRequest wishlistRequest) {
        Optional<Meeting> optionalMeeting = meetingService.getMeetingById(UUID.fromString(meetingId));
        logger.info("Filtering with Wishlist pattern");

        if(optionalMeeting.isPresent()){
            Meeting meeting = optionalMeeting.get();
            List<Attendee> responses = meeting.getResponses();
            Object filteredResponses = FieldFilter.filterFields(meeting, wishlistRequest.getFields());
            return new ResponseEntity<>(filteredResponses, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "getAttendee",
            summary = "Get a specific attendee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Details of a specific attendee", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Attendee.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Attendee not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/attendees/{attendeeId}",
            produces = { "application/json" }
    )
    public ResponseEntity<EntityModel<Attendee>> getAttendee(
            @Parameter(name = "attendeeId", description = "", required = true, in = ParameterIn.PATH) @ValidUUID @PathVariable("attendeeId") String attendeeId) {
        Optional<Attendee> optionalAttendee = attendeeService.getAttendeeById(UUID.fromString(attendeeId));
        if (optionalAttendee.isPresent()) {
            Attendee attendee = optionalAttendee.get();
            EntityModel<Attendee> resource = EntityModel.of(attendee);
            Link selfLink = linkTo(methodOn(AttendeeController.class).getAttendee(attendeeId)).withSelfRel();
            resource.add(selfLink);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
