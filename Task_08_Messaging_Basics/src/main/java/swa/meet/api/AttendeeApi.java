package swa.meet.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;
import swa.meet.entities.Attendee;
import swa.meet.requests.WishlistRequest;
import swa.meet.util.ValidUUID;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
public interface AttendeeApi {
    /**
     * POST /meetings/{meetingId}/attendance : Submit a response for a meeting
     *
     * @param meetingId  (required)
     * @param attendee  (required)
     * @return Attendance submitted successfully (status code 201)
     *         or Meeting not found (status code 404)
     */
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
    ResponseEntity<EntityModel<Attendee>> meetingsMeetingIdAttendancePost(
            @Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH) @ValidUUID @PathVariable("meetingId") String meetingId,
            @Parameter(name = "Attendee", description = "", required = true) @Valid @RequestBody Attendee attendee
    ) throws NoSuchMethodException;

    /**
     * GET /meetings/{meetingId}/responses : Get all attendances for a meeting
     *
     * @param meetingId  (required)
     * @return List of all attendances (status code 200)
     *         or Meeting not found (status code 404)
     */
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
    ResponseEntity<Object> meetingsMeetingIdResponsesGet(@Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH)  @ValidUUID @PathVariable("meetingId") String meetingId,  @RequestBody WishlistRequest wishlistRequest);

}
