package dse.univie.ac.at.client;

import dse.univie.ac.at.exceptions.NotEnoughFundsForTransactionException;
import dse.univie.ac.at.client.rmi.RMIClient;
import dse.univie.ac.at.client.soap.SOAPClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class BankingClient implements Runnable{
    private SOAPClient soap;
    private RMIClient rmi;
    private BankAccount bankAccount;
    private List<UUID> otherAccountUUIDs;
    private static final Logger logger = Logger.getLogger(BankingClient.class.getName());
    public BankingClient(SOAPClient soap, RMIClient rmi){
        this.soap = soap;
        this.rmi = rmi;
        this.otherAccountUUIDs = new ArrayList<>();
        this.bankAccount = null;
    }

    public void startClient() {
        rmi.startRMI();
        assignBankAccount();
        logger.info(this.bankAccount.toString());
        assignOtherAccounts();
        soap.startSOAP();

    }

    public double auditRMI(){
        return rmi.auditRequest();
    }

    public double auditSOAP(){return soap.audit();}


    public void transferFundsRMI(double amount){
        for(var otherAccount : otherAccountUUIDs){
            if(this.bankAccount.getBalance() >= amount){
                this.rmi.transferFundsRequest(this.bankAccount.getAccountId(), otherAccount, amount);
            }else{
                throw new NotEnoughFundsForTransactionException("Not enough funds to transfer");
            }
        }
    }

    //we are transfering from one account to to other 9 accounts
    //
    public void transferFundsSOAP(double amount){
        for(var otherAccount : otherAccountUUIDs){
            if(this.bankAccount.getBalance() >= amount){
                this.soap.requestTransferFunds(this.bankAccount.getAccountId(), otherAccount, amount);
            }else{
                throw new NotEnoughFundsForTransactionException("Not enough funds to transfer");
            }
        }
    }

    private void assignBankAccount(){
        this.bankAccount = rmi.assignBankAccount();
    }

    private void assignOtherAccounts(){
        this.otherAccountUUIDs = rmi.getBankAccounts().stream().filter(uuid -> !uuid.equals(this.bankAccount.getAccountId())).toList();
    }

    @Override
    public void run() {

    }
}
