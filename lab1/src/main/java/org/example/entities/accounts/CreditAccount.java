package org.example.entities.accounts;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.example.entities.banks.Bank;
import org.example.entities.clients.Client;

import javax.security.auth.login.AccountException;
import java.math.BigDecimal;

/**
 * Класс, описывающий кредитный счет
 * @author Andey Gusev
 * @version 1.0
 * @see Account
 * */
@Getter
@EqualsAndHashCode(callSuper = true)
public class CreditAccount extends Account {

    /** Текущий баланс */
    private BigDecimal balance;

    /** Клиент */
    private final Client client;

    /** Банк, где был открыт счет */
    private final Bank bank;

    /** Лимит */
    private final BigDecimal creditLimit;

    /** Комиссия */
    private final BigDecimal commission;

    /** Комиссионная сумма */
    private BigDecimal sumCommission;

    public CreditAccount(BigDecimal balance, Client client, Bank bank) {
        this.balance = balance;
        this.client = client;
        this.bank = bank;
        this.creditLimit = bank.getCreditLimit();
        this.commission = bank.getCommission();
        this.sumCommission = BigDecimal.ZERO;
    }

    @SneakyThrows
    @Override
    public void increase(BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountException("The money quantity is negative");
        }
        balance = balance.add(money);
    }

    @SneakyThrows
    @Override
    public void decrease(BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountException("The money quantity is negative");
        }

        switch ((balance = balance.subtract(money).add(creditLimit)).compareTo(BigDecimal.ZERO)) {
            case -1:
                throw new AccountException("The credit account limit is incorrect");
            case 1:
                if ((balance = balance.subtract(money)).compareTo(BigDecimal.ZERO) < 0) {
                    addCommission();
                }
                break;
        }

        balance = balance.subtract(money);
    }

    @SneakyThrows
    @Override
    public void commissionOperation(int day) {
        if (day < 1) {
            throw new AccountException("The quantity of days is incorrect");
        }
        balance = balance.subtract(sumCommission);
    }

    /**
     * Метод для подсчета комиссионной суммы
     * */
    public void addCommission() {
        sumCommission = sumCommission.add(commission);
    }
}
