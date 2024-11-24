package swa.meet;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

public interface TestRESTOperations {

    ResultActions postActions(String path, String content);

    <T> T doGet(String path);

    <T> T doGet(HttpStatus expectedStatus, String path);

    <T> T doPost(String path, Object request);

    <T> T doPut(String path, Object request);

    ResultActions putActions(String path, String content);



    void doDelete(String path);

    <T> T doPostWithStatus(HttpStatus request, String path, String content);
}
