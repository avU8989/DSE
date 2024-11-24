I have 2 Main files for the TCP and 2 Main Files for the UDP Task. One Main is for the Client and one for the Server. 
Just import the folder Task_02_Sockets and you can start the server and the client by running these main files.
The project is build with gradle with the IDE Intellij.

Reflection:
The TCP was fairly easy to implement in comparison to the UDP. I noticed that the UDP is way faster than the TCP. It was more noticeable 
in the Message with the datablob of 128KB. Even though with my implementation i will have package loss and lose rougly 1-5 messages per 
1000 iteration. Its more noticeable when we run the programs with 4 threads, where the performance is way better.  

The difficult part for me was the fragmenting algorithm. Sometimes the client would already sent the fragmented messages to the server, but 
in the receiving process by the client, these messages would get lost. I just added a socket.setSoTimeout(100) to skip these lost package. 
I know that we can add an Acknowledgement function, but i guess this will work also, because not a lot of messages will be lost at the end. I had
a very difficult time, because i initially sent 1000 times the same Message with the same UUID, thus completely making my use of my HashMap by storing UUIDs
and fragmented Messages useless. Which i changed then after and now it works. 



