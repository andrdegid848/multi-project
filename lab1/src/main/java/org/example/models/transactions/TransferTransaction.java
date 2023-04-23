package org.example.models.transactions;

import org.example.entities.accounts.Account;
import org.example.exceptions.BankException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Класс, описывающий транзакцию, связанную с переводом средств между счетами
 * @author Andey Gusev
 * @version 1.0
 * @see Transaction
 * */
public class TransferTransaction extends Transaction {

    public TransferTransaction(Optional<Account> firstAccount, Optional<Account> secondAccount, BigDecimal sum) throws BankException {
        super(firstAccount, secondAccount, sum);
    }
}
