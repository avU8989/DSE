package swa.meet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import swa.meet.api.TimeslotApi;
import swa.meet.entities.Meeting;
import swa.meet.entities.Timeslot;
import swa.meet.services.MeetingService;
import swa.meet.services.TimeslotService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class TimeslotController implements TimeslotApi {

    @Autowired
    MeetingService meetingService;

    @Autowired
    TimeslotService timeslotService;

    @Override
    @Operation(
            operationId = "meetingsMeetingIdSlotsPost",
            summary = "Add a new time slot to a specific meeting",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Time slot added successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Timeslot.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Meeting not found")
            }
    )
    public ResponseEntity<Timeslot> meetingsMeetingIdSlotsPost(String meetingId, Timeslot timeslot) {
        Optional<Meeting> optionalMeeting = meetingService.getMeetingById(UUID.fromString(meetingId));
        if (optionalMeeting.isPresent()){
            Meeting meeting = optionalMeeting.get();
            timeslotService.saveTimeslot(timeslot);
            meeting.getTimeslots().add(timeslot);
            meetingService.saveMeeting(meeting);
            return new ResponseEntity<>(timeslot, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Operation(
            operationId = "meetingsMeetingIdSlotsSlotIdDelete",
            summary = "Delete a specific time slot from a meeting",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Time slot deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Meeting or time slot not found")
            }
    )
    public ResponseEntity<Void> meetingsMeetingIdSlotsSlotIdDelete(String meetingId, String slotId) {
        Optional<Meeting> optionalMeeting = meetingService.getMeetingById(UUID.fromString(meetingId));
        if(optionalMeeting.isPresent()){
            Meeting meeting = optionalMeeting.get();
            meeting.getTimeslots().removeIf(slot -> slot.getId().equals(UUID.fromString(slotId)));
            meetingService.saveMeeting(meeting);
            timeslotService.deleteTimeslot(UUID.fromString(slotId));
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "getTimeslot",
            summary = "Get a specific timeslot",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Details of a specific timeslot", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Timeslot.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Timeslot not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/timeslots/{timeslotId}",
            produces = { "application/json" }
    )
    public ResponseEntity<EntityModel<Timeslot>> getTimeslot(
            @Parameter(name = "timeslotId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("timeslotId") String timeslotId) {
        Timeslot timeslot = timeslotService.getTimeslotById(UUID.fromString(timeslotId));
        if (timeslot != null) {
            EntityModel<Timeslot> resource = EntityModel.of(timeslot);
            Link selfLink = linkTo(methodOn(TimeslotController.class).getTimeslot(timeslotId)).withSelfRel();
            resource.add(selfLink);
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
