# openapi-java-client

Meeting Scheduler API

- API version: 1.0.0

- Build date: 2024-06-04T01:04:18.517807200+02:00[Europe/Vienna]

API for scheduling and managing meetings with multiple proposed time slots.


*Automatically generated by the [OpenAPI Generator](https://openapi-generator.tech)*

## Requirements

Building the API client library requires:

1. Java 1.8+
2. Maven/Gradle

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

Refer to the [OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-java-client</artifactId>
  <version>1.0.0</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
  repositories {
    mavenCentral()     // Needed if the 'openapi-java-client' jar has been published to maven central.
    mavenLocal()       // Needed if the 'openapi-java-client' jar has been published to the local maven repo.
  }

  dependencies {
     implementation "org.openapitools:openapi-java-client:1.0.0"
  }
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

- `target/openapi-java-client-1.0.0.jar`
- `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import com.example.invoker.*;
import com.example.invoker.auth.*;
import com.example.model.*;
import com.example.api.DefaultApi;

public class DefaultApiExample {

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

## Documentation for API Endpoints

All URIs are relative to *http://localhost:8080/api*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*DefaultApi* | [**meetingsGet**](docs/DefaultApi.md#meetingsGet) | **GET** /meetings | Retrieve all meeting schedules
*DefaultApi* | [**meetingsMeetingIdAttendancePost**](docs/DefaultApi.md#meetingsMeetingIdAttendancePost) | **POST** /meetings/{meetingId}/attendance | Submit a response for a meeting
*DefaultApi* | [**meetingsMeetingIdDelete**](docs/DefaultApi.md#meetingsMeetingIdDelete) | **DELETE** /meetings/{meetingId} | Delete a specific meeting
*DefaultApi* | [**meetingsMeetingIdGet**](docs/DefaultApi.md#meetingsMeetingIdGet) | **GET** /meetings/{meetingId} | Get a specific meeting
*DefaultApi* | [**meetingsMeetingIdPut**](docs/DefaultApi.md#meetingsMeetingIdPut) | **PUT** /meetings/{meetingId} | Update a specific meeting
*DefaultApi* | [**meetingsMeetingIdResponsesGet**](docs/DefaultApi.md#meetingsMeetingIdResponsesGet) | **GET** /meetings/{meetingId}/responses | Get all attendances for a meeting
*DefaultApi* | [**meetingsMeetingIdSlotsPost**](docs/DefaultApi.md#meetingsMeetingIdSlotsPost) | **POST** /meetings/{meetingId}/slots | Add a new time slot to a specific meeting
*DefaultApi* | [**meetingsMeetingIdSlotsSlotIdDelete**](docs/DefaultApi.md#meetingsMeetingIdSlotsSlotIdDelete) | **DELETE** /meetings/{meetingId}/slots/{slotId} | Delete a specific time slot from a meeting
*DefaultApi* | [**meetingsPost**](docs/DefaultApi.md#meetingsPost) | **POST** /meetings | Create a new meeting


## Documentation for Models

 - [Attendee](docs/Attendee.md)
 - [AttendeeAvailabilities](docs/AttendeeAvailabilities.md)
 - [AttendeePerson](docs/AttendeePerson.md)
 - [Meeting](docs/Meeting.md)
 - [Timeslot](docs/Timeslot.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author



