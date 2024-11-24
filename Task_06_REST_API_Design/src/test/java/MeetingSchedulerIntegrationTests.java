import dse.univie.ac.at.Task06RestApiDesignApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Task06RestApiDesignApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MeetingSchedulerIntegrationTests {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void creatingMeetings_whenPostMeetings_thenStatusNotImplemented() throws Exception {
        String meetingJson = "{ \"scheduleName\": \"Project Meeting\", \"creator\": \"John Doe\", \"created\": \"2023-05-27T10:00:00+02:00\", \"validUntil\": \"2023-06-27T10:00:00+02:00\" }";

        mockMvc.perform(post("/api/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(meetingJson))
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void getAllMeetings_whenGetMeetings_thenStatusNotImplemented() throws Exception {
        mockMvc.perform(get("/api/meetings")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotImplemented());
    }

    @Test
    public void submitAttendance_whenPostValidAttendance_thenStatusNotImplemented() throws Exception {
        // Valid payload
        String validAttendanceJson = "{ \"person\": { \"name\": \"string\", \"email\": \"user@example.com\" }, \"availabilities\": [ { \"slotId\": \"175eb645-92b7-478f-b868-207aea32920e\", \"availability\": \"true\" } ] }";

        mockMvc.perform(post("/api/meetings/{meetingId}/attendance", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validAttendanceJson))
                .andDo(print()) // Print the request and response
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void deleteMeeting_whenDeleteMeeting_thenStatusNotImplemented() throws  Exception{
        mockMvc.perform(delete("/api/meetings/{meetingId}", "1"))
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void getMeeting_whenGetMeeting_thenStatusNotImplemented() throws Exception{
        mockMvc.perform(delete("/api/meetings/{meetingId}", "1"))
                .andExpect(status().isNotImplemented());
    }

    @Test
    public void whenGetMeetings_thenStatus200() throws Exception {
        mockMvc.perform(get("/api/meetings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }






}
