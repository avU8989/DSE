package swa.meet.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import swa.meet.entities.Timeslot;

import javax.validation.Valid;

@Validated
public interface TimeslotApi {

    /**
     * POST /meetings/{meetingId}/slots : Add a new time slot to a specific meeting
     *
     * @param meetingId  (required)
     * @param timeslot  (required)
     * @return Time slot added successfully (status code 201)
     *         or Meeting not found (status code 404)
     */
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
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/meetings/{meetingId}/slots",
            produces = { "application/json" },
            consumes = { "application/json" }
    )

    ResponseEntity<Timeslot> meetingsMeetingIdSlotsPost(
            @Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("meetingId") String meetingId,
            @Parameter(name = "Timeslot", description = "", required = true) @Valid @RequestBody Timeslot timeslot
    );


    /**
     * DELETE /meetings/{meetingId}/slots/{slotId} : Delete a specific time slot from a meeting
     *
     * @param meetingId  (required)
     * @param slotId  (required)
     * @return Time slot deleted successfully (status code 200)
     *         or Meeting or time slot not found (status code 404)
     */
    @Operation(
            operationId = "meetingsMeetingIdSlotsSlotIdDelete",
            summary = "Delete a specific time slot from a meeting",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Time slot deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Meeting or time slot not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/meetings/{meetingId}/slots/{slotId}"
    )

    ResponseEntity<Void> meetingsMeetingIdSlotsSlotIdDelete(
            @Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("meetingId") String meetingId,
            @Parameter(name = "slotId", description = "", required = true, in = ParameterIn.PATH) @PathVariable("slotId") String slotId
    );
}
