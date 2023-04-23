package org.example.entities.accounts;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.example.entities.banks.Bank;
import org.example.entities.clients.Client;

import javax.security.auth.login.AccountException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Класс, описывающий дебетовый счет
 * @author Andey Gusev
 * @version 1.0
 * @see Account
 * */
@Getter
@EqualsAndHashCode(callSuper = true)
public class DebitAccount extends Account {

    /** Текущий баланс */
    private BigDecimal balance;

    /** Клиент */
    private final Client client;

    /** Банк, где был открыт счет */
    private final Bank bank;

    /** Проценты */
    private final BigDecimal percentageOnBalance;

    /** Процентная сумма */
    private BigDecimal sumPercentage;

    public DebitAccount(BigDecimal money, Client client, Bank bank) {
        this.balance = money;
        this.client = client;
        this.bank = bank;
        this.percentageOnBalance = bank.getDebitPercentage();
        this.sumPercentage = BigDecimal.ZERO;
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
        if (money.compareTo(BigDecimal.ZERO) <= 0 || balance.compareTo(money) < 0) {
            throw new AccountException("The money quantity or balance is negative");
        }
        balance = balance.subtract(money);
    }

    @SneakyThrows
    @Override
    public void commissionOperation(int day) {
        if (day < 1) {
            throw new AccountException("The quantity of days is incorrect");
        }
        balance = balance.add(changeSumPercentage(day));
        sumPercentage = BigDecimal.ZERO;
    }

    /**
     * Метод для подсчета процентов на счете
     * @param day количсетво дней
     * */
    private BigDecimal changeSumPercentage(int day) throws AccountException {
        if (day < 1) {
            throw new AccountException("The quantity of days is incorrect");
        }
        BigDecimal dailyInterest = balance
                .multiply(percentageOnBalance)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(day));
        return sumPercentage = sumPercentage.add(dailyInterest);
    }
}
