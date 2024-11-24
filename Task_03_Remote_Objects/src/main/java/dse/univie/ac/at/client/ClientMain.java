package dse.univie.ac.at.client;

import dse.univie.ac.at.client.rmi.RMIClient;
import dse.univie.ac.at.client.soap.SOAPClient;

public class ClientMain {

    public static void main(String[] args) {
        RMIClient rmi = new RMIClient();
        SOAPClient soap = new SOAPClient();

        Thread[] threads = new Thread[10];

        //here im creating Threads manually
        //we could actually also implement Runnable in the BankingClient and submit it to a ExecutorService
        for (int i = 0; i < 10; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                BankingClient client = new BankingClient(soap, rmi);
                client.startClient();
                System.out.println("Starting RMI requests for thread " + index + "................");
                client.transferFundsRMI(300);
                System.out.println("The audit from the RMI for thread " + index + " is: " + client.auditRMI());
                System.out.println("Starting SOAP requests for thread " + index + "..................");
                client.transferFundsSOAP(300);
                System.out.println("The audit from the SOAP for thread " + index + " is: " + client.auditSOAP());
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
