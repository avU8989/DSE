
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import swa.meet.OpenApiGeneratorApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = OpenApiGeneratorApplication.class)
@AutoConfigureMockMvc
public class IntegrationTestMeetingsAPI {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetMeetings_thenReturnNotImplemented_andMatchExampleResponse() throws Exception {
        String expectedResponse = "[ { \"scheduleName\" : \"scheduleName\", \"creator\" : \"creator\", \"created\" : \"2000-01-23T04:56:07.000+00:00\", \"description\" : \"description\", \"validUntil\" : \"2000-01-23T04:56:07.000+00:00\", \"responses\" : [ { \"person\" : { \"name\" : \"name\", \"email\" : \"email\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"person\" : { \"name\" : \"name\", \"email\" : \"email\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"timeslots\" : [ { \"start\" : \"2000-01-23T04:56:07.000+00:00\", \"end\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"start\" : \"2000-01-23T04:56:07.000+00:00\", \"end\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ] }, { \"scheduleName\" : \"scheduleName\", \"creator\" : \"creator\", \"created\" : \"2000-01-23T04:56:07.000+00:00\", \"description\" : \"description\", \"validUntil\" : \"2000-01-23T04:56:07.000+00:00\", \"responses\" : [ { \"person\" : { \"name\" : \"name\", \"email\" : \"email\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"person\" : { \"name\" : \"name\", \"email\" : \"email\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"timeslots\" : [ { \"start\" : \"2000-01-23T04:56:07.000+00:00\", \"end\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"start\" : \"2000-01-23T04:56:07.000+00:00\", \"end\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ] } ]";

        mockMvc.perform(get("/api/meetings")
                        .accept(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isNotImplemented())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void whenPostAttendance_thenReturnNotImplemented_andMatchExampleResponse() throws Exception {
        String meetingId = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";
        String requestBody = "{ \"person\" : { \"name\" : \"John Doe\", \"email\" : \"john.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ] }";
        String expectedResponse = "{ \"person\" : { \"name\" : \"John Doe\", \"email\" : \"john.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }";

        mockMvc.perform(post("/api/meetings/{meetingId}/attendance", meetingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotImplemented())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    public void whenDeleteMeeting_thenReturnNotImplemented() throws Exception {
        String meetingId = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";

        mockMvc.perform(delete("/api/meetings/{meetingId}", meetingId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void whenGetMeetingById_thenReturnNotImplemented_andMatchExampleResponse() throws Exception {
        String meetingId = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";
        String exampleString = "{ \"scheduleName\" : \"scheduleName\", \"creator\" : \"creator\", \"created\" : \"2000-01-23T04:56:07.000+00:00\", \"description\" : \"description\", \"validUntil\" : \"2000-01-23T04:56:07.000+00:00\", \"responses\" : [ { \"person\" : { \"name\" : \"name\", \"email\" : \"email\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"person\" : { \"name\" : \"name\", \"email\" : \"email\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"timeslots\" : [ { \"start\" : \"2000-01-23T04:56:07.000+00:00\", \"end\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"start\" : \"2000-01-23T04:56:07.000+00:00\", \"end\" : \"2000-01-23T04:56:07.000+00:00\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ] }";
        mockMvc.perform(get("/api/meetings/{meetingId}", meetingId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotImplemented())
                .andExpect(content().json(exampleString));
    }

    @Test
    public void givenValidRequest_whenPutMeeting_thenReturnNotImplemented_andMatchExampleResponse() throws Exception {
        String meetingId = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";
        String requestBody = "{ \"scheduleName\" : \"scheduleName\", \"creator\" : \"creator\", \"created\" : \"2000-01-23T04:56:07\", \"description\" : \"description\", \"validUntil\" : \"2000-01-23T04:56:07\", \"responses\" : [ { \"person\" : { \"name\" : \"John Doe\", \"email\" : \"john.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"person\" : { \"name\" : \"Jane Doe\", \"email\" : \"jane.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"timeslots\" : [ { \"start\" : \"2000-01-23T04:56:07\", \"end\" : \"2000-01-23T04:56:07\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"start\" : \"2000-01-23T04:56:07\", \"end\" : \"2000-01-23T04:56:07\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ] }";
        String exampleString = "{ \"scheduleName\" : \"scheduleName\", \"creator\" : \"creator\", \"created\" : \"2000-01-23T04:56:07\", \"description\" : \"description\", \"validUntil\" : \"2000-01-23T04:56:07\", \"responses\" : [ { \"person\" : { \"name\" : \"John Doe\", \"email\" : \"john.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"person\" : { \"name\" : \"Jane Doe\", \"email\" : \"jane.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"timeslots\" : [ { \"start\" : \"2000-01-23T04:56:07\", \"end\" : \"2000-01-23T04:56:07\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"start\" : \"2000-01-23T04:56:07\", \"end\" : \"2000-01-23T04:56:07\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ] }";

        mockMvc.perform(put("/api/meetings/{meetingId}", meetingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotImplemented())
                .andExpect(content().json(exampleString));
    }

    //invalid Email
    @Test
    public void givenInvalidEmail_whenPutMeeting_thenReturnBadRequest() throws Exception {
        String meetingId = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";
        String requestBody = "{ \"scheduleName\" : \"scheduleName\", \"creator\" : \"creator\", \"created\" : \"2000-01-23T04:56:07\", \"description\" : \"description\", \"validUntil\" : \"2000-01-23T04:56:07\", \"responses\" : [ { \"person\" : { \"name\" : \"John Doe\", \"email\" : \"john.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"person\" : { \"name\" : \"name\", \"email\" : \"email\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"timeslots\" : [ { \"start\" : \"2000-01-23T04:56:07\", \"end\" : \"2000-01-23T04:56:07\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" }, { \"start\" : \"2000-01-23T04:56:07\", \"end\" : \"2000-01-23T04:56:07\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ] }";

        mockMvc.perform(put("/api/meetings/{meetingId}", meetingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenInvalidDateTime_whenPutMeeting_thenReturnBadRequest() throws Exception {
        String meetingId = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";
        String requestBody = "{ \"scheduleName\" : \"scheduleName\", \"creator\" : \"creator\", \"created\" : \"invalid-date-time\", \"description\" : \"description\", \"validUntil\" : \"invalid-date-time\", \"responses\" : [ { \"person\" : { \"name\" : \"John Doe\", \"email\" : \"john.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" }, { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ] }";

        mockMvc.perform(put("/api/meetings/{meetingId}", meetingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpMessageNotReadableException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Cannot deserialize value of type `java.time.LocalDateTime`")));
    }

    @Test
    public void givenMissingFields_whenPutMeeting_thenReturnBadRequest() throws Exception {
        String meetingId = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";
        // Missing required fields like scheduleName and creator
        String requestBody = "{ \"created\" : \"2000-01-23T04:56:07\", \"description\" : \"description\", \"validUntil\" : \"2000-01-23T04:56:07\" }";

        mockMvc.perform(put("/api/meetings/{meetingId}", meetingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("scheduleName")))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("creator")));
    }

    @Test
    public void givenInvalidUUID_whenPutMeeting_thenReturnBadRequest() throws Exception {
        String meetingId = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";
        String requestBody = "{ \"scheduleName\" : \"Valid Schedule\", \"creator\" : \"John Doe\", \"created\" : \"2000-01-23T04:56:07\", \"description\" : \"A valid meeting\", \"validUntil\" : \"2000-01-23T04:56:07\", \"responses\" : [ { \"person\" : { \"name\" : \"John Doe\", \"email\" : \"john.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"invalid-uuid\" } ], \"timeslots\" : [ { \"start\" : \"2000-01-23T04:56:07\", \"end\" : \"2000-01-23T04:56:07\", \"id\" : \"invalid-uuid\" } ] }";

        mockMvc.perform(put("/api/meetings/{meetingId}", meetingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenEmptyMeetingID_whenPutMeeting_thenReturnMethodNotAllowed() throws Exception {
        String meetingId = "";
        String requestBody = "{ \"scheduleName\" : \"Valid Schedule\", \"creator\" : \"John Doe\", \"created\" : \"2000-01-23T04:56:07\", \"description\" : \"A valid meeting\", \"validUntil\" : \"2000-01-23T04:56:07\", \"responses\" : [ { \"person\" : { \"name\" : \"John Doe\", \"email\" : \"john.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"invalid-uuid\" } ], \"timeslots\" : [ { \"start\" : \"2000-01-23T04:56:07\", \"end\" : \"2000-01-23T04:56:07\", \"id\" : \"invalid-uuid\" } ] }";

        mockMvc.perform(put("/api/meetings/{meetingId}", meetingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void givenUnsupportedMediaType_whenPutMeeting_thenReturnUnsupportedMediaType() throws Exception {
        String meetingId = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";
        String requestBody = "{ \"scheduleName\" : \"Valid Schedule\", \"creator\" : \"John Doe\", \"created\" : \"2000-01-23T04:56:07\", \"description\" : \"A valid meeting\", \"validUntil\" : \"2000-01-23T04:56:07\", \"responses\" : [ { \"person\" : { \"name\" : \"John Doe\", \"email\" : \"john.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ], \"timeslots\" : [ { \"start\" : \"2000-01-23T04:56:07\", \"end\" : \"2000-01-23T04:56:07\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ] }";

        mockMvc.perform(put("/api/meetings/{meetingId}", meetingId)
                        .contentType(MediaType.APPLICATION_XML)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void givenEmptyRequestBody_whenPutMeeting_thenReturnBadRequest() throws Exception {
        String meetingId = "046b6c7f-0b8a-43b9-b35d-6489e6daee91";
        String requestBody = "{}";

        mockMvc.perform(put("/api/meetings/{meetingId}", meetingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidRequestBody_whenPostMeeting_thenReturnNotImplemented() throws Exception {
        String requestBody = "{ \"scheduleName\" : \"Valid Schedule\", \"creator\" : \"John Doe\", \"created\" : \"2000-01-23T04:56:07\", \"description\" : \"A valid meeting\", \"validUntil\" : \"2000-01-23T04:56:07\", \"responses\" : [ { \"person\" : { \"name\" : \"Jane Doe\", \"email\" : \"jane.doe@example.com\" }, \"availabilities\" : [ { \"slotId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\", \"availability\" : \"true\" } ], \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ], \"timeslots\" : [ { \"start\" : \"2000-01-23T04:56:07\", \"end\" : \"2000-01-23T05:56:07\", \"id\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" } ] }";
        mockMvc.perform(post("/api/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotImplemented());
    }
}
