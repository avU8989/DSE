package swa.meet;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static swa.meet.MockMvcOperationsUtils.assertJsonPaths;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = OpenApiGeneratorApplication.class)
public class MeetingControllerTestWithTestRestImpl extends BaseFullContextTest {
    @Autowired
    private TestRESTOperations testRESTOperations;

    @Test
    @Order(0)
    void createNewMeeting() {
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

        assertJsonPaths(postActions, Map.of(
                "id", "123e4567-e89b-12d3-a456-426614174000",
                "scheduleName", "Team Meeting",
                "description", "Discussion on project milestones",
                "creator", "John Doe",
                "created", "2024-06-07T10:15:30",
                "validUntil", "2024-06-10T10:15:30",
                "timeslots[0].id", "123e4567-e89b-12d3-a456-426614174001",
                "timeslots[1].id", "123e4567-e89b-12d3-a456-426614174002",
                "timeslots[1].end", "2024-06-09T15:00:00"
        ));
    }
}
