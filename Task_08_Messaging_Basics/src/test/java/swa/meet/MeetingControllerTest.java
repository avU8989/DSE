package swa.meet;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import swa.meet.OpenApiGeneratorApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = OpenApiGeneratorApplication.class)
@AutoConfigureMockMvc
public class MeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(0)
    void createNewMeeting() throws Exception{
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
        mockMvc.perform(post("/api/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.scheduleName").value("Team Meeting"))
                .andExpect(jsonPath("$.description").value("Discussion on project milestones"))
                .andExpect(jsonPath("$.creator").value("John Doe"))
                .andExpect(jsonPath("$.created").value("2024-06-07T10:15:30"))
                .andExpect(jsonPath("$.validUntil").value("2024-06-10T10:15:30"))
                .andExpect(jsonPath("$.timeslots[0].id").value("123e4567-e89b-12d3-a456-426614174001"))
                .andExpect(jsonPath("$.timeslots[0].start").value("2024-06-08T10:00:00"))
                .andExpect(jsonPath("$.timeslots[0].end").value("2024-06-08T11:00:00"))
                .andExpect(jsonPath("$.timeslots[1].id").value("123e4567-e89b-12d3-a456-426614174002"))
                .andExpect(jsonPath("$.timeslots[1].start").value("2024-06-09T14:00:00"))
                .andExpect(jsonPath("$.timeslots[1].end").value("2024-06-09T15:00:00"))
                .andExpect(jsonPath("$.responses").isEmpty());
    }




}
