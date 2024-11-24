I didn't need to validate the yaml because i used the generated Server Project, but still added some things like Relationships between Entities to 
store them in the Database. I used the org.hibernate.dialect.H2Dialect Database to store my entities locally. I additionally added two endpoints like 
getTimeslot and getAttendee, but other than that my Implementation should match the yaml file. After generating the enums in the Availabilities where also 
wrong, i dont know why but it was set to "true", "false", "maybe".. and i initally had it to "yes", "no", "maybe". 
Therfore i also had to change the generated Code.

Invoking HTTP requests with POSTMAN:
 - To invoke the Http Requests using Postman i provided a json File under the Project Task_07_REST_Web_Services. 
   It is named (PT7.postman_collection.json)

Integration Tests are under Task_07_REST_Web_Services\src\test\java\swa\meet.
There you can run MeetingControllerTestWithTestRestImpl.java. There i followed the Tutorial provided in the PT7 specification. 

HateOASLink 
Each Entity should have Links to the related Entity and a Link to itself.  

I also added a custom ValidUUID annotation to check if its a valid UUID, but i dont know if the generated Server already checks that. 

I have a GlobalExceptionHandler which in the case of an error sends the receiver a HTTP response status codes based on my custom Exception. 

I also added a Wishlist Pattern for the endpoint "http://localhost:8080/api/meetings/{meetingId}/responses. There you can specify the fields you want to 
fetch from the entity. 

You can view the api documentation in swagger-ui by pointing to
http://localhost:8080/swagger-ui.html

