package util;

import java.io.Serializable;
import java.util.UUID;

public class TransactionRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private final UUID requestId;
	private final TransactionType transactionType;
	private final UUID fromAccount;
	private final UUID toAccount;
	private double amount;

	public TransactionRequest(TransactionType type, UUID fromAccount, UUID toAccount, double amount) {
		this.requestId = UUID.randomUUID();
		this.transactionType = type;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
	}

	public TransactionType getType() {
		return this.transactionType;
	}

	public UUID getFromAccount() {
		return this.fromAccount;
	}

	public UUID getToAccount() {
		return this.toAccount;
	}

	public double getAmount() {
		return this.amount;
	}

}
