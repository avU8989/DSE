package dse.univie.ac.at.client;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;
import util.TransactionRequest;

public class BankAccount implements Serializable, BankTransactions, Runnable {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(BankAccount.class.getName());
	private BlockingQueue<TransactionRequest> bankOperations;
	private UUID accountId;
	private String name;
	private double balance;

	public BankAccount(String name, double balance) {
		this.accountId = UUID.randomUUID();
		this.name = name;
		this.balance = balance;
		this.bankOperations = new LinkedBlockingQueue<>();
	}

	public UUID getAccountId() {
		return this.accountId;
	}

	public String getName() {
		return this.name;
	}

	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public void run(){
		do{
			try{
				TransactionRequest transaction = bankOperations.take();

				if (transaction != null) {
					processTransaction(transaction);
				} else {
					Thread.sleep(2000);
				}
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}while(true);
	}

	private void processTransaction(TransactionRequest transactionRequest){
		switch(transactionRequest.getType()){
			case WITHDRAW :
				withdraw(transactionRequest.getAmount());
				break;
			case DEPOSIT :
				deposit(transactionRequest.getAmount());
				break;

		}
	}

	@Override
	public void withdraw(double amount) {
		this.balance -= amount;
	}

	@Override
	public void deposit(double amount) {
		this.balance += amount;
	}

	public void addTransaction(TransactionRequest transaction) {
		try {
			bankOperations.put(transaction);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public String toString() {
		return "BankAccount{" +
				"bankOperations=" + bankOperations +
				", accountId=" + accountId +
				", name='" + name + '\'' +
				", balance=" + balance +
				'}';
	}
}