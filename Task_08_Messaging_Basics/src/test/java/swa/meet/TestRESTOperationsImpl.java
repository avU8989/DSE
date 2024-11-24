package swa.meet;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import swa.meet.TestRESTOperations;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static swa.meet.BaseFullContextTest.OBJECT_MAPPER;

@Component
@RequiredArgsConstructor
public class TestRESTOperationsImpl implements TestRESTOperations {

    private final MockMvc mockMvc;

    @Override
    @SneakyThrows
    public ResultActions postActions(String path, String content) {
        MockHttpServletRequestBuilder requestBuilder = post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        return mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Override
    @SneakyThrows
    public <T> T doGet(String path) {
        String response = mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return readResponse(response);

    }

    @Override
    @SneakyThrows
    public <T> T doGet(HttpStatus expectedStatus, String path) {
       String response = mockMvc.perform(get(path))
               .andExpect(status().is(expectedStatus.value()))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andReturn().getResponse().getContentAsString();
       return readResponse(response);
    }

    @Override
    @SneakyThrows
    public <T> T doPost(String path, Object request) {
        MockHttpServletRequestBuilder requestBuilder = post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(request));

        String response = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return readResponse(response);
    }

    @Override
    @SneakyThrows
    public <T> T doPut(String path, Object request) {
        MockHttpServletRequestBuilder requestBuilder = put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(request));

        String response = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return readResponse(response);
    }

    @Override
    @SneakyThrows
    public ResultActions putActions(String path, String content) {
        MockHttpServletRequestBuilder requestBuilder = put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        return mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Override
    @SneakyThrows
    public void doDelete(String path) {
        mockMvc.perform(delete(path))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    private static <T> T readResponse(String response) {
        return (T) OBJECT_MAPPER.readValue(response, Object.class);
    }
}
