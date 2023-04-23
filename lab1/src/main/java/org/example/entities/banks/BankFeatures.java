package org.example.entities.banks;

import org.example.entities.accounts.Account;
import org.example.entities.clients.Client;
import org.example.exceptions.BankException;
import org.example.models.Address;
import org.example.models.PercentageOnBalance;
import org.example.models.transactions.ReplenishmentTransaction;
import org.example.models.transactions.Transaction;
import org.example.models.transactions.TransferTransaction;
import org.example.models.transactions.WithdrawalTransaction;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Interface для банка
 * @see Bank
 * */
public interface BankFeatures {
    WithdrawalTransaction withdrawalCash(Account account, BigDecimal money) throws BankException;

    ReplenishmentTransaction replenishmentMoney(Account account, BigDecimal money);

    TransferTransaction transferMoney(Account account1, Account account2, BigDecimal money);

    void cancelTransaction(Transaction transaction);

    Client addClient(String name, String surname, Optional<String> passport, Optional<Address> address);

    void addAccount(Client client, Account account);

    boolean removeClient(Client client);

    void commissionOperation();

    void changeDebitPercentage(BigDecimal debitPercentage);

    void changeDepositPercentage(PercentageOnBalance depositPercentage);

    void changeCreditLimit(BigDecimal creditLimit);

    void changeDoubtLimit(BigDecimal doubtLimit);

    void getMessage();
}
