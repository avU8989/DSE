The Project is runnable through two Main classes, one for the client and one for the server. 
I used Gradle 8.7 to build my Project using JAVA JDK 17. I used a Classloader to load the 
wsdl located in the resources folder, because it didn't work with :classpath. I tried it 
to do it like the project example for SOAP but couldn't read from resources folder, even though
the dse.wsdl file was located there. 

The project should work fine and the audit is never above 100.000,-. I created multiple threads
for the client to send requests to the server. The server would store a ConcurrentHashMap of the BankAccounts
with the assigned UUID. Because we initialize the BankAccounts on the server i had to add an additional 
method where we would assign each client a BankAccount by adding an AtomicInteger and increment it everytime another
BankingClient would start. Unfortunaley that only works when we start the RMI server, i know that this doesn't
make sense in the real world, but i just had to add that function because of the random generated UUID on the server. 
I think normally you would create the BankingAccounts on the Client then send the request to server to store those bankaccounts. 
The BankAccount would implement the Runnable to do the withdrawing/depositing tasks. The server would use a ExecutorService
to execute the multiple Threads in a Threadpool.

Problems during the Project: 
Initially i had big problems with SOAP, especially with the WSDL file. I was able to generate the classes responsible
for the SOAP. At first i couldn't invoke any requests to the SOAP server, because it always showed me:
at jdk.proxy1/jdk.proxy1.$Proxy31.transferFunds(Unknown Source)
at jdk.proxy1/jdk.proxy1.$Proxy31.audit(Unknown Source)

But going through the Tips and Recommendations section for the SOAP again, i fixed it. 

