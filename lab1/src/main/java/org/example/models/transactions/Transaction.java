package org.example.models.transactions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.entities.accounts.Account;
import org.example.exceptions.BankException;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Абстрактный класс, содержащий общую информацию о транзакциях
 * @author Andey Gusev
 * @version 1.0
 * */
@Getter
@EqualsAndHashCode
public abstract class Transaction {

    private final Optional<Account> firstAccount;
    private final Optional<Account> secondAccount;
    private final BigDecimal sum;

    public Transaction(Optional<Account> firstAccount, Optional<Account> secondAccount, BigDecimal sum) throws BankException {
        if (sum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BankException("The quantity of money is incorrect");
        }
        this.firstAccount = firstAccount;
        this.secondAccount = secondAccount;
        this.sum = sum;
    }
}
