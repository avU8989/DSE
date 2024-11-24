# DefaultApi

All URIs are relative to *http://localhost:8080/api*

Method | HTTP request | Description
------------- | ------------- | -------------
[**meetingsGet**](DefaultApi.md#meetingsGet) | **GET** /meetings | Retrieve all meeting schedules
[**meetingsMeetingIdAttendancePost**](DefaultApi.md#meetingsMeetingIdAttendancePost) | **POST** /meetings/{meetingId}/attendance | Submit a response for a meeting
[**meetingsMeetingIdDelete**](DefaultApi.md#meetingsMeetingIdDelete) | **DELETE** /meetings/{meetingId} | Delete a specific meeting
[**meetingsMeetingIdGet**](DefaultApi.md#meetingsMeetingIdGet) | **GET** /meetings/{meetingId} | Get a specific meeting
[**meetingsMeetingIdPut**](DefaultApi.md#meetingsMeetingIdPut) | **PUT** /meetings/{meetingId} | Update a specific meeting
[**meetingsMeetingIdResponsesGet**](DefaultApi.md#meetingsMeetingIdResponsesGet) | **GET** /meetings/{meetingId}/responses | Get all attendances for a meeting
[**meetingsMeetingIdSlotsPost**](DefaultApi.md#meetingsMeetingIdSlotsPost) | **POST** /meetings/{meetingId}/slots | Add a new time slot to a specific meeting
[**meetingsMeetingIdSlotsSlotIdDelete**](DefaultApi.md#meetingsMeetingIdSlotsSlotIdDelete) | **DELETE** /meetings/{meetingId}/slots/{slotId} | Delete a specific time slot from a meeting
[**meetingsPost**](DefaultApi.md#meetingsPost) | **POST** /meetings | Create a new meeting



## meetingsGet

> List&lt;Meeting&gt; meetingsGet()

Retrieve all meeting schedules

### Example

```java
// Import classes:
import com.example.invoker.ApiClient;
import com.example.invoker.ApiException;
import com.example.invoker.Configuration;
import com.example.invoker.models.*;
import com.example.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        try {
            List<Meeting> result = apiInstance.meetingsGet();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#meetingsGet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**List&lt;Meeting&gt;**](Meeting.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Fetching all meetings |  -  |


## meetingsMeetingIdAttendancePost

> Attendee meetingsMeetingIdAttendancePost(meetingId, attendee)

Submit a response for a meeting

### Example

```java
// Import classes:
import com.example.invoker.ApiClient;
import com.example.invoker.ApiException;
import com.example.invoker.Configuration;
import com.example.invoker.models.*;
import com.example.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String meetingId = "meetingId_example"; // String | 
        Attendee attendee = new Attendee(); // Attendee | 
        try {
            Attendee result = apiInstance.meetingsMeetingIdAttendancePost(meetingId, attendee);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#meetingsMeetingIdAttendancePost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **meetingId** | **String**|  |
 **attendee** | [**Attendee**](Attendee.md)|  |

### Return type

[**Attendee**](Attendee.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Attendance submitted successfully |  -  |
| **404** | Meeting not found |  -  |


## meetingsMeetingIdDelete

> meetingsMeetingIdDelete(meetingId)

Delete a specific meeting

### Example

```java
// Import classes:
import com.example.invoker.ApiClient;
import com.example.invoker.ApiException;
import com.example.invoker.Configuration;
import com.example.invoker.models.*;
import com.example.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String meetingId = "meetingId_example"; // String | 
        try {
            apiInstance.meetingsMeetingIdDelete(meetingId);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#meetingsMeetingIdDelete");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **meetingId** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Meeting deleted successfully |  -  |
| **404** | Meeting not found |  -  |


## meetingsMeetingIdGet

> Meeting meetingsMeetingIdGet(meetingId)

Get a specific meeting

### Example

```java
// Import classes:
import com.example.invoker.ApiClient;
import com.example.invoker.ApiException;
import com.example.invoker.Configuration;
import com.example.invoker.models.*;
import com.example.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String meetingId = "meetingId_example"; // String | 
        try {
            Meeting result = apiInstance.meetingsMeetingIdGet(meetingId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#meetingsMeetingIdGet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **meetingId** | **String**|  |

### Return type

[**Meeting**](Meeting.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Details of a specific meeting |  -  |
| **404** | Meeting not found |  -  |


## meetingsMeetingIdPut

> Meeting meetingsMeetingIdPut(meetingId, meeting)

Update a specific meeting

### Example

```java
// Import classes:
import com.example.invoker.ApiClient;
import com.example.invoker.ApiException;
import com.example.invoker.Configuration;
import com.example.invoker.models.*;
import com.example.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String meetingId = "meetingId_example"; // String | 
        Meeting meeting = new Meeting(); // Meeting | 
        try {
            Meeting result = apiInstance.meetingsMeetingIdPut(meetingId, meeting);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#meetingsMeetingIdPut");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **meetingId** | **String**|  |
 **meeting** | [**Meeting**](Meeting.md)|  |

### Return type

[**Meeting**](Meeting.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Meeting updated successfully |  -  |
| **404** | Meeting not found |  -  |


## meetingsMeetingIdResponsesGet

> List&lt;Attendee&gt; meetingsMeetingIdResponsesGet(meetingId)

Get all attendances for a meeting

### Example

```java
// Import classes:
import com.example.invoker.ApiClient;
import com.example.invoker.ApiException;
import com.example.invoker.Configuration;
import com.example.invoker.models.*;
import com.example.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String meetingId = "meetingId_example"; // String | 
        try {
            List<Attendee> result = apiInstance.meetingsMeetingIdResponsesGet(meetingId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#meetingsMeetingIdResponsesGet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **meetingId** | **String**|  |

### Return type

[**List&lt;Attendee&gt;**](Attendee.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of all attendances |  -  |
| **404** | Meeting not found |  -  |


## meetingsMeetingIdSlotsPost

> Timeslot meetingsMeetingIdSlotsPost(meetingId, timeslot)

Add a new time slot to a specific meeting

### Example

```java
// Import classes:
import com.example.invoker.ApiClient;
import com.example.invoker.ApiException;
import com.example.invoker.Configuration;
import com.example.invoker.models.*;
import com.example.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String meetingId = "meetingId_example"; // String | 
        Timeslot timeslot = new Timeslot(); // Timeslot | 
        try {
            Timeslot result = apiInstance.meetingsMeetingIdSlotsPost(meetingId, timeslot);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#meetingsMeetingIdSlotsPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **meetingId** | **String**|  |
 **timeslot** | [**Timeslot**](Timeslot.md)|  |

### Return type

[**Timeslot**](Timeslot.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Time slot added successfully |  -  |
| **404** | Meeting not found |  -  |


## meetingsMeetingIdSlotsSlotIdDelete

> meetingsMeetingIdSlotsSlotIdDelete(meetingId, slotId)

Delete a specific time slot from a meeting

### Example

```java
// Import classes:
import com.example.invoker.ApiClient;
import com.example.invoker.ApiException;
import com.example.invoker.Configuration;
import com.example.invoker.models.*;
import com.example.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        String meetingId = "meetingId_example"; // String | 
        String slotId = "slotId_example"; // String | 
        try {
            apiInstance.meetingsMeetingIdSlotsSlotIdDelete(meetingId, slotId);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#meetingsMeetingIdSlotsSlotIdDelete");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **meetingId** | **String**|  |
 **slotId** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Time slot deleted successfully |  -  |
| **404** | Meeting or time slot not found |  -  |


## meetingsPost

> Meeting meetingsPost(meeting)

Create a new meeting

### Example

```java
// Import classes:
import com.example.invoker.ApiClient;
import com.example.invoker.ApiException;
import com.example.invoker.Configuration;
import com.example.invoker.models.*;
import com.example.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("http://localhost:8080/api");

        DefaultApi apiInstance = new DefaultApi(defaultClient);
        Meeting meeting = new Meeting(); // Meeting | 
        try {
            Meeting result = apiInstance.meetingsPost(meeting);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#meetingsPost");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **meeting** | [**Meeting**](Meeting.md)|  |

### Return type

[**Meeting**](Meeting.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Meeting created |  -  |
| **400** | Error during creating Meeting |  -  |

