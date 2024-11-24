package swa.meet;

import lombok.SneakyThrows;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Contains various helper methods that help reduce amount of copy-paste
 */
public class MockMvcOperationsUtils {

    @SneakyThrows
    public static void assertJsonPaths(ResultActions resultActions, Map<String, Object> expectedValues) {
        for (Map.Entry<String, Object> entry : expectedValues.entrySet()) {
            Object expectedValue = entry.getValue();
            if (expectedValue instanceof String) {
                resultActions.andExpect(jsonPath(entry.getKey()).value((String) expectedValue));
            } else if (expectedValue instanceof Number) {
                resultActions.andExpect(jsonPath(entry.getKey()).value((Number) expectedValue));
            } else if (expectedValue instanceof Boolean) {
                resultActions.andExpect(jsonPath(entry.getKey()).value((Boolean) expectedValue));
            } else if (expectedValue instanceof Object[]) {
                resultActions.andExpect(jsonPath(entry.getKey()).isArray());
                resultActions.andExpect(jsonPath(entry.getKey()).isEmpty());
            }
        }
    }


}
