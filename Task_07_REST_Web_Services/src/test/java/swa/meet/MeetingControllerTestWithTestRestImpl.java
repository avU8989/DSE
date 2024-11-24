package swa.meet;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static swa.meet.MockMvcOperationsUtils.assertJsonPaths;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = OpenApiGeneratorApplication.class)
public class MeetingControllerTestWithTestRestImpl extends BaseFullContextTest {
    @Autowired
    private TestRESTOperations testRESTOperations;

    @Test
    @Order(0)
    void createNewMeeting(){
        String requestBody = "{\n" +
                "    \"id\": \"123e4567-e89b-12d3-a456-426614174000\",\n" +
                "    \"scheduleName\": \"Team Meeting\",\n" +
                "    \"description\": \"Discussion on project milestones\",\n" +
                "    \"creator\": \"John Doe\",\n" +
                "    \"created\": \"2024-06-07T10:15:30\",\n" +
                "    \"validUntil\": \"2024-06-10T10:15:30\",\n" +
                "    \"timeslots\": [\n" +
                "        {\n" +
                "            \"id\": \"123e4567-e89b-12d3-a456-426614174001\",\n" +
                "            \"start\": \"2024-06-08T10:00:00\",\n" +
                "            \"end\": \"2024-06-08T11:00:00\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"123e4567-e89b-12d3-a456-426614174002\",\n" +
                "            \"start\": \"2024-06-09T14:00:00\",\n" +
                "            \"end\": \"2024-06-09T15:00:00\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"responses\": []\n" +
                "}";
        ResultActions postActions = testRESTOperations.postActions("/api/meetings", requestBody);

        Map<String, Object> expectedValues = new HashMap<>();
        expectedValues.put("id", "123e4567-e89b-12d3-a456-426614174000");
        expectedValues.put("scheduleName", "Team Meeting");
        expectedValues.put("description", "Discussion on project milestones");
        expectedValues.put("creator", "John Doe");
        expectedValues.put("created", "2024-06-07T10:15:30");
        expectedValues.put("validUntil", "2024-06-10T10:15:30");
        expectedValues.put("timeslots[0].id", "123e4567-e89b-12d3-a456-426614174001");
        expectedValues.put("timeslots[0].start", "2024-06-08T10:00:00");
        expectedValues.put("timeslots[0].end", "2024-06-08T11:00:00");
        expectedValues.put("timeslots[1].id", "123e4567-e89b-12d3-a456-426614174002");
        expectedValues.put("timeslots[1].start", "2024-06-09T14:00:00");
        expectedValues.put("timeslots[1].end", "2024-06-09T15:00:00");
        expectedValues.put("responses", new Object[]{});

        assertJsonPaths(postActions, expectedValues);
    }

    @Test
    @Order(1)
    void shouldFetchMeeting_WithValidMeetingID(){
        String meetingId = "123e4567-e89b-12d3-a456-426614174000";
        Map byMeetingID = testRESTOperations.doGet("/api/meetings/" + meetingId);
        assert byMeetingID.get("id").equals("123e4567-e89b-12d3-a456-426614174000");
        assert byMeetingID.get("scheduleName").equals("Team Meeting");
        assert byMeetingID.get("description").equals("Discussion on project milestones");
        assert byMeetingID.get("creator").equals( "John Doe");
        assert byMeetingID.get("created").equals( "2024-06-07T10:15:30");
        assert byMeetingID.get("validUntil").equals( "2024-06-10T10:15:30");
        List<Map<String, Object>> timeslots = (List<Map<String, Object>>) byMeetingID.get("timeslots");
        assert timeslots != null;
        assert timeslots.size() == 2;
        assert timeslots.get(0).get("id").equals("123e4567-e89b-12d3-a456-426614174001");
        assert timeslots.get(0).get("start").equals("2024-06-08T10:00:00");
        assert timeslots.get(0).get("end").equals("2024-06-08T11:00:00");
        assert timeslots.get(1).get("id").equals("123e4567-e89b-12d3-a456-426614174002");
        assert timeslots.get(1).get("start").equals("2024-06-09T14:00:00");
        assert timeslots.get(1).get("end").equals("2024-06-09T15:00:00");

        List<Object> responses = (List<Object>) byMeetingID.get("responses");
        assert responses != null;
        assert responses.isEmpty();
    }

    @Test
    @Order(3)
    void shouldUpdateMeeting_WithPutRequest() {
        String requestBody = "{\n" +
                "  \"scheduleName\": \"Updated Team Meeting\",\n" +
                "  \"description\": \"Updated discussion on project milestones\",\n" +
                "  \"creator\": \"Jane Doe\",\n" +
                "  \"created\": \"2024-06-07T10:15:30\",\n" +
                "  \"validUntil\": \"2024-06-10T10:15:30\",\n" +
                "  \"timeslots\": [\n" +
                "    {\n" +
                "      \"id\": \"123e4567-e89b-12d3-a456-426614174001\",\n" +
                "      \"start\": \"2024-06-08T12:00:00\",\n" +
                "      \"end\": \"2024-06-08T13:00:00\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"123e4567-e89b-12d3-a456-426614174002\",\n" +
                "      \"start\": \"2024-06-09T16:00:00\",\n" +
                "      \"end\": \"2024-06-09T17:00:00\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"responses\": []\n" +
                "}\n";
        String meetingId = "123e4567-e89b-12d3-a456-426614174000";
        ResultActions putActions = testRESTOperations.putActions("/api/meetings/" + meetingId, requestBody);
        Map<String, Object> expectedValues = new HashMap<>();
        expectedValues.put("id", "123e4567-e89b-12d3-a456-426614174000");
        expectedValues.put("scheduleName", "Updated Team Meeting");
        expectedValues.put("description", "Updated discussion on project milestones");
        expectedValues.put("creator", "Jane Doe");
        expectedValues.put("created", "2024-06-07T10:15:30");
        expectedValues.put("validUntil", "2024-06-10T10:15:30");
        expectedValues.put("timeslots[0].id", "123e4567-e89b-12d3-a456-426614174001");
        expectedValues.put("timeslots[0].start", "2024-06-08T12:00:00");
        expectedValues.put("timeslots[0].end", "2024-06-08T13:00:00");
        expectedValues.put("timeslots[1].id", "123e4567-e89b-12d3-a456-426614174002");
        expectedValues.put("timeslots[1].start", "2024-06-09T16:00:00");
        expectedValues.put("timeslots[1].end", "2024-06-09T17:00:00");
        expectedValues.put("responses", new Object[]{});
        assertJsonPaths(putActions, expectedValues);
    }

    @Test
    @Order(3)
    void ShouldSubmitAttendance_WithPostRequest() throws Exception {
        String requestBody = "{\n" +
                "  \"id\": \"123e4567-e89b-12d3-a456-436614174001\",\n" +
                "  \"person\": {\n" +
                "    \"id\": \"456e4567-e89b-12d3-a456-426614174002\",\n" +
                "    \"name\": \"Anna Smith\",\n" +
                "    \"email\": \"anna.smith@example.com\"\n" +
                "  },\n" +
                "  \"availabilities\": [\n" +
                "    {\n" +
                "  \"slotId\": \"c56a4180-65aa-42ec-a945-5fd21dec0539\",\n" +
                "  \"availability\": \"yes\",\n" +
                "  \"timeslotId\": \"123e4567-e89b-12d3-a456-426614174001\"\n" +
                "}\n" +
                "\n" +
                "  ],\n" +
                "  \"meetings\": []\n" +
                "}\n";
        String meetingId = "123e4567-e89b-12d3-a456-426614174000";
        ResultActions postActions = testRESTOperations.postActions("/meetings/" + meetingId + "/attendance", requestBody);
        Map<String, Object> expectedValues = new HashMap<>();
        expectedValues.put("id", "123e4567-e89b-12d3-a456-436614174001");
        expectedValues.put("person.id", "456e4567-e89b-12d3-a456-426614174002");
        expectedValues.put("person.name", "Anna Smith");
        expectedValues.put("person.email", "anna.smith@example.com");
        expectedValues.put("availabilities[0].slotId", "c56a4180-65aa-42ec-a945-5fd21dec0539");
        expectedValues.put("availabilities[0].availability", "yes");
        expectedValues.put("availabilities[0].timeslotId", "123e4567-e89b-12d3-a456-426614174001");

        for (Map.Entry<String, Object> entry : expectedValues.entrySet()) {
            postActions.andExpect(jsonPath(entry.getKey()).value(entry.getValue()));
        }
    }

    @Test
    @Order(4)
    void FetchMeetingAfterAttendanceSubmission_ReturnUpdatedMeetingDetails(){
        String meetingId = "123e4567-e89b-12d3-a456-426614174000";
        Map<String, Object> byMeetingID = testRESTOperations.doGet("/api/meetings/" + meetingId);

        Map<String, Object> expectedValues = new HashMap<>();
        expectedValues.put("id", "123e4567-e89b-12d3-a456-426614174000");
        expectedValues.put("scheduleName", "Team Meeting");
        expectedValues.put("description", "Discussion on project milestones");
        expectedValues.put("creator", "John Doe");
        expectedValues.put("created", "2024-06-07T10:15:30");
        expectedValues.put("validUntil", "2024-06-10T10:15:30");
        expectedValues.put("timeslots[0].id", "123e4567-e89b-12d3-a456-426614174001");
        expectedValues.put("timeslots[0].start", "2024-06-08T10:00:00");
        expectedValues.put("timeslots[0].end", "2024-06-08T11:00:00");
        expectedValues.put("timeslots[1].id", "123e4567-e89b-12d3-a456-426614174002");
        expectedValues.put("timeslots[1].start", "2024-06-09T14:00:00");
        expectedValues.put("timeslots[1].end", "2024-06-09T15:00:00");
        expectedValues.put("responses[0].id", "123e4567-e89b-12d3-a456-436614174001");
        expectedValues.put("responses[0].person.id", "456e4567-e89b-12d3-a456-426614174002");
        expectedValues.put("responses[0].person.name", "Anna Smith");
        expectedValues.put("responses[0].person.email", "john.smith@example.com");
        expectedValues.put("responses[0].availabilities[0].slotId", "c56a4180-65aa-42ec-a945-5fd21dec0539");
        expectedValues.put("responses[0].availabilities[0].availability", "yes");
        expectedValues.put("responses[0].availabilities[0].timeslotId", "123e4567-e89b-12d3-a456-426614174001");
    }

    @Test
    @Order(5)
    void submitResponseWithInvalidTimeSlotID_shouldReturn400() {
        String requestBody = "{\n" +
                "  \"id\": \"123e4567-e89b-12d3-a456-436614174001\",\n" +
                "  \"person\": {\n" +
                "    \"id\": \"456e4567-e89b-12d3-a456-426614174002\",\n" +
                "    \"name\": \"Anna Smith\",\n" +
                "    \"email\": \"anna.smith@example.com\"\n" +
                "  },\n" +
                "  \"availabilities\": [\n" +
                "    {\n" +
                "  \"slotId\": \"c56a4180-65aa-42ec-a945-5fd21dec0539\",\n" +
                "  \"availability\": \"yes\",\n" +
                "  \"timeslotId\": \"123e4567-e89b-12d3-a456-426614174012\"\n" +
                "}\n" +
                "\n" +
                "  ],\n" +
                "  \"meetings\": []\n" +
                "}\n";
        String meetingId = "123e4567-e89b-12d3-a456-426614174000";

        Map responseAsMap = testRESTOperations.doPostWithStatus(HttpStatus.BAD_REQUEST, "/meetings/" + meetingId + "/attendance", requestBody);
        assert responseAsMap.get("message").equals("A timeslot with this given id does not exist: 123e4567-e89b-12d3-a456-426614174012");
    }

    @Test
    @Order(6)
    void submitResponseWithEmptyAvailabilities_shouldReturn400() {
        // Arrange
        String requestBody = "{\n" +
                "  \"id\": \"123e4567-e89b-12d3-a456-436614174001\",\n" +
                "  \"person\": {\n" +
                "    \"id\": \"456e4567-e89b-12d3-a456-426614174002\",\n" +
                "    \"name\": \"Anna Smith\",\n" +
                "    \"email\": \"anna.smith@example.com\"\n" +
                "  },\n" +
                "  \"availabilities\": [],\n" +
                "  \"meetings\": []\n" +
                "}\n";
        String meetingId = "123e4567-e89b-12d3-a456-426614174000";

        Map responseAsMap = testRESTOperations.doPostWithStatus(HttpStatus.BAD_REQUEST, "/meetings/" + meetingId + "/attendance", requestBody);
        System.out.println(responseAsMap);
        assert responseAsMap.get("message").equals("Availabilities should not be empty!");
    }

    @Test
    @Order(7)
    void deleteById_shouldRemoveFrom_getPage() {
        String meetingId = "123e4567-e89b-12d3-a456-426614174000";
        testRESTOperations.doDelete("/api/meetings/" + meetingId);

        Map pageResponse = testRESTOperations.doGet("/api/meetings");
        List<Map<String, Object>> content = (List) pageResponse.get("content");
        assert content == null;
    }

    @Test
    @Order(8)
    void getMissingMeetingById_shouldReturn404() {
        String meetingId = "123e4567-e89b-12d3-a456-426614174000";

        Map responseAsMap = testRESTOperations.doGet(HttpStatus.NOT_FOUND, "/api/meetings/" + meetingId);
        assert responseAsMap.get("message").equals("Meeting not found with id: " + meetingId);
    }




}
