package swa.meet.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import swa.meet.entities.Meeting;
import swa.meet.util.ValidUUID;

import javax.validation.Valid;
import java.util.List;

@Validated
@Tag(name = "meetings", description = "the meetings API")
public interface MeetingApi {
    /**
     * GET /meetings : Retrieve all meeting schedules
     *
     * @return Fetching all meetings (status code 200)
     */
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
            produces = { "application/json" }
    )
    ResponseEntity<CollectionModel<EntityModel<Meeting>>> meetingsGet();

    /**
     * POST /meetings : Create a new meeting
     *
     * @param meeting  (required)
     * @return Meeting created (status code 201)
     *         or Error during creating Meeting (status code 400)
     */
    @Operation(
            operationId = "meetingsPost",
            summary = "Create a new meeting",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Meeting created", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Meeting.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Error during creating Meeting")
            }
    ) @RequestMapping(
            method = RequestMethod.POST,
            value = "/meetings",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    ResponseEntity<EntityModel<Meeting>>  meetingsPost(@Parameter(name = "Meeting", description = "", required = true) @Valid @RequestBody Meeting meeting);

    /**
     * DELETE /meetings/{meetingId} : Delete a specific meeting
     *
     * @param meetingId  (required)
     * @return Meeting deleted successfully (status code 204)
     *         or Meeting not found (status code 404)
     */
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
    ResponseEntity<Void> meetingsMeetingIdDelete(@Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH) @ValidUUID @PathVariable("meetingId") String meetingId);

    /**
     * GET /meetings/{meetingId} : Get a specific meeting
     *
     * @param meetingId  (required)
     * @return Details of a specific meeting (status code 200)
     *         or Meeting not found (status code 404)
     */
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
            produces = { "application/json" }
    )
    ResponseEntity<EntityModel<Meeting>> meetingsMeetingIdGet(@Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH) @ValidUUID @PathVariable("meetingId") String meetingId);

    /**
     * PUT /meetings/{meetingId} : Update a specific meeting
     *
     * @param meetingId  (required)
     * @param meeting  (required)
     * @return Meeting updated successfully (status code 200)
     *         or Meeting not found (status code 404)
     */
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
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    ResponseEntity<EntityModel<Meeting>>  meetingsMeetingIdPut(
            @Parameter(name = "meetingId", description = "", required = true, in = ParameterIn.PATH) @ValidUUID @PathVariable("meetingId") String meetingId,
            @Parameter(name = "Meeting", description = "", required = true) @Valid @RequestBody Meeting meeting
    );





}
