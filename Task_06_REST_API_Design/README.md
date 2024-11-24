For this Programming Task i followed first the tutorial from Baeldung. And it worked but then the professor came with another suggestion in the 
Forum and i tried that approach too, because there was a lot more info about the steps to the PT6. So in the end i chose the approach from the Professor.

Project Structure:
Under Task_06_REST_API_Design\meet\* 
 - is my generated Server

Under Task_06_REST_API_Design\src\integrationTest
 - is my generated Client for Testing Purposes

Where is the yaml File and html representation? 
It is under Task_06_REST_API_Design\src\main\resources. There you can find the yaml file, the html representation of the endpoints (redoc-static.html)

How to test it: 
For the Integration Tests using MockMvc, you can run the Test class in the server project wihtout having to start the SpringBootApplication.
It is under Task_06_REST_API_Design\meet\src\test\IntegrationTestMeetingsAPI.java

For the Integration Tests using a generated API Client, you need to start the server by starting the SpringBootApplication in the meet directory before running the tests. 
Then you can run the Test class under Task_06_REST_API_Design\src\integrationTest\resources\open-api-client\src\test\com\example\api\DefaultApiTest.java

So i created a yaml File, validated it, generated the code for the server and additionally for the Client, but only for testing purposes. After creating 
both of the projects i wrote the integration tests. 

- The first approach of the Integration Test was for the Server, which is under the directory
  meet/src/main/test. There i have IntegrationTestMeetingsAPI where you dont have the precondition to run the server before, because it gets handled by the MockMvc.


- The second approach of the Integration Test was for the Client, which is under the directory
  \src\integrationTest\resources\open-api-client\src\test\com\example\api. There i have a DefaultApiTeset to test the integration between the client and the server. 
