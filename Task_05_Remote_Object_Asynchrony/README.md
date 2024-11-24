You can run the PT5 by running two Main classes. One is the ClientMain and the other is the Server Main. 
My approach for choosing the patterns and communication protocol for the different use cases are

 - First Use-Case: Send single log message to the server --> used TCP, with Fire and Forget Pattern
 - Second Use-Case: Order removal of old logs --> used TCP, with Sync with Server Pattern
 - Third Use-Case: Add logs in Bulk --> used UDP, with Polling Pattern
 - Fourth Use-Case: Search for specific lgos --> used UDP, with Result Callback Pattern

Reflexion: 
Initially i struggled a lot with the concurrency, because i refactored the UDP and TCP to a NetworkRquestHandler, which is an interface and cover the 
operations to send and receive Messages. Therefore i also used a ConnectionHandler which should handle incoming connections from the client. My
problem was that i forgot that with each new Connection, my RemoteObject was not consistent. So i thought to myself maybe my implementation on the client
with the different patterns are wrong. 
But the root cause which took me almost 2 weeks to figure out was that i didnt pass the remoteobject to the UDPNetworkHandler
so thats why the server with each request handling within the UDPNetworkHandler was creating a new instance of RemoteObject instead of using a single shared instance.
This led to a situation where changes made to the RemoteObject in one operation were not reflected in subsequent operations because they were operating on different instances of RemoteObject.
I thought i had problems with the implementation of the different patterns but the root cause was something completely different (Cause of the Problem ConnectionUDPHandler and UDPNetworkHandler)

But in the end everything seemed to work fine, by fixing this problem. 