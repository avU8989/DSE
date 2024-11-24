package dse.univie.ac.at.server.rmi;

import dse.univie.ac.at.client.BankAccount;
import dse.univie.ac.at.exceptions.NotExistingAccountException;
import dse.univie.ac.at.server.BankingHandler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;


public class RMIBankingServiceImpl extends UnicastRemoteObject implements BankOperations {
	private BankingHandler handler;
	private static final Logger logger = Logger.getLogger(RMIBankingServiceImpl.class.getName());
	private AtomicInteger nextAccountIdx = new AtomicInteger(0);

	public RMIBankingServiceImpl(BankingHandler handler) throws RemoteException {
		this.handler = handler;
	}

	@Override
	public void transferFunds(UUID fromAccount, UUID toAccount, double amount) throws RemoteException {
		try {
			logger.info("Transfering from " + this.handler.getBankAccount(fromAccount).getName() + " to " + this.handler.getBankAccount(toAccount).getName() + " the amount " + String.valueOf(amount));
			handler.transferFunds(fromAccount, toAccount, amount);
		} catch (Exception e) {
			throw new RemoteException("Failed to transfer funds: " + e.getMessage());
		}

	}

	@Override
	public double audit() throws RemoteException {
		try {
			double totalBalance = handler.audit();
			logger.info("Calling audit function from RMI...total balance is: " + String.valueOf(totalBalance));
			return totalBalance;
		} catch (Exception e) {
			throw new RemoteException("Failed to audit bank accounts: " + e.getMessage());
		}
	}

	@Override
	public List<UUID> getAccountIds() throws RemoteException {
		return handler.getAccountIds();
	}

	@Override
	public BankAccount assignNextBankAccount() throws RemoteException {
		int idx = nextAccountIdx.getAndIncrement();
		UUID accountUUID = handler.getAccountIds().get(idx);
		logger.info(accountUUID.toString());
		if(idx < handler.getAccountIds().size()){
			return handler.getAccounts().get(accountUUID);
		}else{
			throw new NotExistingAccountException("Not enough Accounts to assign");
		}
	}
}
