package dse.univie.ac.at.server;

import dse.univie.ac.at.client.BankAccount;
import dse.univie.ac.at.exceptions.InvalidNumbersOfAccountsException;
import dse.univie.ac.at.exceptions.NotEnoughFundsForTransactionException;
import dse.univie.ac.at.exceptions.NotExistingAccountException;
import dse.univie.ac.at.exceptions.ServerException;
import util.TransactionRequest;
import util.TransactionType;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
public class BankingHandler{
	private Map<UUID, BankAccount> accounts;
	private static final Logger logger = Logger.getLogger(BankingHandler.class.getName());
	private ExecutorService executorService;

	public BankingHandler(ConcurrentHashMap<UUID, BankAccount> accounts) {

		this.accounts = accounts;
		executorService = Executors.newFixedThreadPool(10);
		startExecutions();
	}

	public void transferFunds(UUID fromAccount, UUID toAccount, double amount) {
		accounts.get(fromAccount).addTransaction(new TransactionRequest(TransactionType.WITHDRAW, fromAccount, toAccount, amount));
		accounts.get(toAccount).addTransaction(new TransactionRequest(TransactionType.DEPOSIT, fromAccount, toAccount, amount));

		if (!accounts.containsKey(fromAccount) || !accounts.containsKey(toAccount)) {
			throw new NotExistingAccountException("During transfering funds one of the account is not existin");
		}

		if (accounts.isEmpty() || accounts.size() < 1) {
			throw new InvalidNumbersOfAccountsException(
					"During transfering funds there are not enough accounts registered");
		}

		if (amount <= 0) {
			throw new ServerException("Invalid amount to transfer");
		}

	}

	public double audit() {
		synchronized (accounts) {
			double totalBalance = 0.0;

			for (BankAccount account : accounts.values()) {
				totalBalance += account.getBalance();
			}
			return totalBalance;
		}

	}

	public List<UUID> getAccountIds() {
		return new ArrayList<>(accounts.keySet());
	}

	public double getBalance(UUID uuid) {
		return accounts.get(uuid).getBalance();
	}

	public Map<UUID, BankAccount> getAccounts() {
		return this.accounts;
	}

	private void startExecutions(){
		for (BankAccount account : accounts.values()) {
			executorService.submit(() -> {
				logger.info("Thread started for account: " + account.getName() + ", Thread ID: " + Thread.currentThread().getId());
			});
		}
	}

	public BankAccount getBankAccount(UUID uuid){
		return accounts.get(uuid);
	}

}
