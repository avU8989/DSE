package com.example.api;

import com.example.invoker.ApiClient;

import com.example.model.Attendee;
import com.example.model.Meeting;
import com.example.model.Timeslot;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-06-04T01:04:18.517807200+02:00[Europe/Vienna]")
@Component("com.example.api.DefaultApi")
public class DefaultApi {
    private ApiClient apiClient;

    public DefaultApi() {
        this(new ApiClient());
    }

    @Autowired
    public DefaultApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Retrieve all meeting schedules
     * 
     * <p><b>200</b> - Fetching all meetings
     * @return List&lt;Meeting&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Meeting> meetingsGet() throws RestClientException {
        return meetingsGetWithHttpInfo().getBody();
    }

    /**
     * Retrieve all meeting schedules
     * 
     * <p><b>200</b> - Fetching all meetings
     * @return ResponseEntity&lt;List&lt;Meeting&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Meeting>> meetingsGetWithHttpInfo() throws RestClientException {
        Object postBody = null;
        

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] contentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<Meeting>> returnType = new ParameterizedTypeReference<List<Meeting>>() {};
        return apiClient.invokeAPI("/meetings", HttpMethod.GET, Collections.<String, Object>emptyMap(), queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, authNames, returnType);
    }
    /**
     * Submit a response for a meeting
     * 
     * <p><b>201</b> - Attendance submitted successfully
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @param attendee  (required)
     * @return Attendee
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Attendee meetingsMeetingIdAttendancePost(String meetingId, Attendee attendee) throws RestClientException {
        return meetingsMeetingIdAttendancePostWithHttpInfo(meetingId, attendee).getBody();
    }

    /**
     * Submit a response for a meeting
     * 
     * <p><b>201</b> - Attendance submitted successfully
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @param attendee  (required)
     * @return ResponseEntity&lt;Attendee&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Attendee> meetingsMeetingIdAttendancePostWithHttpInfo(String meetingId, Attendee attendee) throws RestClientException {
        Object postBody = attendee;
        
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'meetingId' when calling meetingsMeetingIdAttendancePost");
        }
        
        // verify the required parameter 'attendee' is set
        if (attendee == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'attendee' when calling meetingsMeetingIdAttendancePost");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("meetingId", meetingId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Attendee> returnType = new ParameterizedTypeReference<Attendee>() {};
        return apiClient.invokeAPI("/meetings/{meetingId}/attendance", HttpMethod.POST, uriVariables, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, authNames, returnType);
    }
    /**
     * Delete a specific meeting
     * 
     * <p><b>204</b> - Meeting deleted successfully
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void meetingsMeetingIdDelete(String meetingId) throws RestClientException {
        meetingsMeetingIdDeleteWithHttpInfo(meetingId);
    }

    /**
     * Delete a specific meeting
     * 
     * <p><b>204</b> - Meeting deleted successfully
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> meetingsMeetingIdDeleteWithHttpInfo(String meetingId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'meetingId' when calling meetingsMeetingIdDelete");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("meetingId", meetingId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = {  };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] contentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/meetings/{meetingId}", HttpMethod.DELETE, uriVariables, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, authNames, returnType);
    }
    /**
     * Get a specific meeting
     * 
     * <p><b>200</b> - Details of a specific meeting
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @return Meeting
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Meeting meetingsMeetingIdGet(String meetingId) throws RestClientException {
        return meetingsMeetingIdGetWithHttpInfo(meetingId).getBody();
    }

    /**
     * Get a specific meeting
     * 
     * <p><b>200</b> - Details of a specific meeting
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @return ResponseEntity&lt;Meeting&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Meeting> meetingsMeetingIdGetWithHttpInfo(String meetingId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'meetingId' when calling meetingsMeetingIdGet");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("meetingId", meetingId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] contentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Meeting> returnType = new ParameterizedTypeReference<Meeting>() {};
        return apiClient.invokeAPI("/meetings/{meetingId}", HttpMethod.GET, uriVariables, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, authNames, returnType);
    }
    /**
     * Update a specific meeting
     * 
     * <p><b>200</b> - Meeting updated successfully
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @param meeting  (required)
     * @return Meeting
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Meeting meetingsMeetingIdPut(String meetingId, Meeting meeting) throws RestClientException {
        return meetingsMeetingIdPutWithHttpInfo(meetingId, meeting).getBody();
    }

    /**
     * Update a specific meeting
     * 
     * <p><b>200</b> - Meeting updated successfully
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @param meeting  (required)
     * @return ResponseEntity&lt;Meeting&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Meeting> meetingsMeetingIdPutWithHttpInfo(String meetingId, Meeting meeting) throws RestClientException {
        Object postBody = meeting;
        
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'meetingId' when calling meetingsMeetingIdPut");
        }
        
        // verify the required parameter 'meeting' is set
        if (meeting == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'meeting' when calling meetingsMeetingIdPut");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("meetingId", meetingId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Meeting> returnType = new ParameterizedTypeReference<Meeting>() {};
        return apiClient.invokeAPI("/meetings/{meetingId}", HttpMethod.PUT, uriVariables, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, authNames, returnType);
    }
    /**
     * Get all attendances for a meeting
     * 
     * <p><b>200</b> - List of all attendances
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @return List&lt;Attendee&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public List<Attendee> meetingsMeetingIdResponsesGet(String meetingId) throws RestClientException {
        return meetingsMeetingIdResponsesGetWithHttpInfo(meetingId).getBody();
    }

    /**
     * Get all attendances for a meeting
     * 
     * <p><b>200</b> - List of all attendances
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @return ResponseEntity&lt;List&lt;Attendee&gt;&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<List<Attendee>> meetingsMeetingIdResponsesGetWithHttpInfo(String meetingId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'meetingId' when calling meetingsMeetingIdResponsesGet");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("meetingId", meetingId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] contentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<List<Attendee>> returnType = new ParameterizedTypeReference<List<Attendee>>() {};
        return apiClient.invokeAPI("/meetings/{meetingId}/responses", HttpMethod.GET, uriVariables, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, authNames, returnType);
    }
    /**
     * Add a new time slot to a specific meeting
     * 
     * <p><b>201</b> - Time slot added successfully
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @param timeslot  (required)
     * @return Timeslot
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Timeslot meetingsMeetingIdSlotsPost(String meetingId, Timeslot timeslot) throws RestClientException {
        return meetingsMeetingIdSlotsPostWithHttpInfo(meetingId, timeslot).getBody();
    }

    /**
     * Add a new time slot to a specific meeting
     * 
     * <p><b>201</b> - Time slot added successfully
     * <p><b>404</b> - Meeting not found
     * @param meetingId  (required)
     * @param timeslot  (required)
     * @return ResponseEntity&lt;Timeslot&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Timeslot> meetingsMeetingIdSlotsPostWithHttpInfo(String meetingId, Timeslot timeslot) throws RestClientException {
        Object postBody = timeslot;
        
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'meetingId' when calling meetingsMeetingIdSlotsPost");
        }
        
        // verify the required parameter 'timeslot' is set
        if (timeslot == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'timeslot' when calling meetingsMeetingIdSlotsPost");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("meetingId", meetingId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Timeslot> returnType = new ParameterizedTypeReference<Timeslot>() {};
        return apiClient.invokeAPI("/meetings/{meetingId}/slots", HttpMethod.POST, uriVariables, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, authNames, returnType);
    }
    /**
     * Delete a specific time slot from a meeting
     * 
     * <p><b>200</b> - Time slot deleted successfully
     * <p><b>404</b> - Meeting or time slot not found
     * @param meetingId  (required)
     * @param slotId  (required)
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public void meetingsMeetingIdSlotsSlotIdDelete(String meetingId, String slotId) throws RestClientException {
        meetingsMeetingIdSlotsSlotIdDeleteWithHttpInfo(meetingId, slotId);
    }

    /**
     * Delete a specific time slot from a meeting
     * 
     * <p><b>200</b> - Time slot deleted successfully
     * <p><b>404</b> - Meeting or time slot not found
     * @param meetingId  (required)
     * @param slotId  (required)
     * @return ResponseEntity&lt;Void&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Void> meetingsMeetingIdSlotsSlotIdDeleteWithHttpInfo(String meetingId, String slotId) throws RestClientException {
        Object postBody = null;
        
        // verify the required parameter 'meetingId' is set
        if (meetingId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'meetingId' when calling meetingsMeetingIdSlotsSlotIdDelete");
        }
        
        // verify the required parameter 'slotId' is set
        if (slotId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'slotId' when calling meetingsMeetingIdSlotsSlotIdDelete");
        }
        
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("meetingId", meetingId);
        uriVariables.put("slotId", slotId);

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = {  };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] contentTypes = {  };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Void> returnType = new ParameterizedTypeReference<Void>() {};
        return apiClient.invokeAPI("/meetings/{meetingId}/slots/{slotId}", HttpMethod.DELETE, uriVariables, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, authNames, returnType);
    }
    /**
     * Create a new meeting
     * 
     * <p><b>201</b> - Meeting created
     * <p><b>400</b> - Error during creating Meeting
     * @param meeting  (required)
     * @return Meeting
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public Meeting meetingsPost(Meeting meeting) throws RestClientException {
        return meetingsPostWithHttpInfo(meeting).getBody();
    }

    /**
     * Create a new meeting
     * 
     * <p><b>201</b> - Meeting created
     * <p><b>400</b> - Error during creating Meeting
     * @param meeting  (required)
     * @return ResponseEntity&lt;Meeting&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<Meeting> meetingsPostWithHttpInfo(Meeting meeting) throws RestClientException {
        Object postBody = meeting;
        
        // verify the required parameter 'meeting' is set
        if (meeting == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'meeting' when calling meetingsPost");
        }
        

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] localVarAccepts = { 
            "application/json"
         };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] contentTypes = { 
            "application/json"
         };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<Meeting> returnType = new ParameterizedTypeReference<Meeting>() {};
        return apiClient.invokeAPI("/meetings", HttpMethod.POST, Collections.<String, Object>emptyMap(), queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, authNames, returnType);
    }
}
