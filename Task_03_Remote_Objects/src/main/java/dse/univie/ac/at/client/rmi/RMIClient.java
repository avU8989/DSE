package dse.univie.ac.at.client.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import dse.univie.ac.at.client.BankAccount;
import dse.univie.ac.at.exceptions.NotExistingAccountException;
import dse.univie.ac.at.server.rmi.BankOperations;

public class RMIClient{
	private static final Logger logger = Logger.getLogger(RMIClient.class.getName());
	private Registry registry;
	private BankOperations bankService;

	public RMIClient() {

	}
	public void startRMI() {
		try {
			this.registry = LocateRegistry.getRegistry("localhost", 1099);
			this.bankService = (BankOperations) Naming.lookup("rmi://localhost:1099/BankServiceRMI");
		} catch (Exception e) {
			logger.severe("Error during starting Client for RMI: " + e.toString());
			System.err.println("Error during starting Client for RMI: " + e.toString());
			e.printStackTrace();
		}
	}

	public void transferFundsRequest(UUID fromAccount, UUID toAccount, double amount) {
		try {
		bankService.transferFunds(fromAccount, toAccount, amount);
		} catch (RemoteException e) {
			System.err.println("Error during transfering Funds: " + e.getMessage());
		}

	}

	public double auditRequest() {
		try {
			return bankService.audit();
		} catch (RemoteException e) {
			System.err.println("Error during audit call: " + e.getMessage());
		}

		return 0;
	}

	public BankAccount assignBankAccount() {
		try {
			BankAccount assignedBankAccount = this.bankService.assignNextBankAccount();
			return assignedBankAccount;
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new NotExistingAccountException("BankAccount cannot be assigned to Client");
		}
	}

	public List<UUID> getBankAccounts() {
		try {
			List<UUID> accountUUIDs = this.bankService.getAccountIds();
			return accountUUIDs;
		} catch (RemoteException e) {
			e.printStackTrace();
			logger.severe("Unable to get the accounts");
		}

		return new ArrayList<>();
	}
}
