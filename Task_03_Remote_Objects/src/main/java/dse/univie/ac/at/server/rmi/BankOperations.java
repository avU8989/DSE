package dse.univie.ac.at.server.rmi;

import dse.univie.ac.at.client.BankAccount;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface BankOperations extends Remote {
	public void transferFunds(UUID fromAccount, UUID toAccount, double amount) throws RemoteException;

	public double audit() throws RemoteException;

	public List<UUID> getAccountIds() throws RemoteException;

	BankAccount assignNextBankAccount() throws RemoteException;
}
