package dse.univie.ac.at.server.rmi;

import dse.univie.ac.at.server.BankingHandler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public class RMIServer {

    private static final Logger logger = Logger.getLogger(RMIServer.class.getName());
	private static final int PORT = 1099;
	private RMIBankingServiceImpl rmiService;

	public RMIServer(BankingHandler handler) {
        try {
            this.rmiService = new RMIBankingServiceImpl(handler);
        } catch (RemoteException e){
            logger.severe("Error setting up RMI Service");
        }
	}


    public void startRMIServer() {
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            Naming.rebind("rmi://localhost:" + PORT + "/BankServiceRMI", rmiService);

            logger.info("RMI Server started successfully");
        } catch (RemoteException e) {
            logger.severe("Failed to start RMI server: " + e.getMessage());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
