package org.example.models.transactions;

import org.example.entities.accounts.Account;
import org.example.exceptions.BankException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Класс, описывающий транзакцию, связанную с уменьшением денежных средств на счете
 * @author Andey Gusev
 * @version 1.0
 * @see Transaction
 * */
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(Optional<Account> firstAccount, Optional<Account> secondAccount, BigDecimal sum) throws BankException {
        super(firstAccount, secondAccount, sum);
    }
}
