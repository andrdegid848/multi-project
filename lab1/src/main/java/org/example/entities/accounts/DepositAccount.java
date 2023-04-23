package org.example.entities.accounts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.entities.banks.Bank;
import org.example.entities.clients.Client;
import org.example.models.PercentageOnBalance;

import javax.security.auth.login.AccountException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Класс, описывающий депозитный счет
 * @author Andey Gusev
 * @version 1.0
 * @see Account
 * */
@Getter
@RequiredArgsConstructor
public class DepositAccount extends Account {

    /** Сумма, при которой начисляются средние по величине проценты */
    private static final BigDecimal MIDDLE_MONEY = new BigDecimal("50000");

    /** Сумма, при которой начисляются высокие по величине проценты */
    private static final BigDecimal HIGH_MONEY = new BigDecimal("100000");

    /** Текущий баланс */
    private BigDecimal balance;

    /** Клиент */
    private final Client client;

    /** Банк, где был открыт счет */
    private final Bank bank;

    /** Проценты */
    private final BigDecimal percentageOnBalance;

    /** Процентная сумма */
    private BigDecimal sumPercentage = BigDecimal.ZERO;

    public DepositAccount(BigDecimal money, Client client, Bank bank) {
        this.balance = money;
        this.client = client;
        this.bank = bank;
        this.percentageOnBalance = findRequiredPercentage(money, bank.getDepositPercentage());
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
     * Метод для определения величины процента
     * @param money сумма денег
     * @param percentageOnBalance проценты
     * */
    @SneakyThrows
    private BigDecimal findRequiredPercentage(BigDecimal money, PercentageOnBalance percentageOnBalance) {
        switch (money.compareTo(BigDecimal.ZERO)) {
            case -1, 0:
                throw new AccountException("The money quantity is negative");
            case 1:
                if (money.compareTo(MIDDLE_MONEY) < 0) {
                    return percentageOnBalance.getLowPercentage();
                } else if (money.compareTo(HIGH_MONEY) < 0) {
                    return percentageOnBalance.getMiddlePercentage();
                } else {
                    return percentageOnBalance.getHighPercentage();
                }
            default:
                throw new AccountException("The money quantity is invalid");
        }
    }


    /**
     * Метода для изменения процентной суммы на счете
     * @param day количество дней
     * */
    @SneakyThrows
    private BigDecimal changeSumPercentage(int day) {
        if (day < 1) {
            throw new AccountException("The quantity of days is incorrect");
        }
        return sumPercentage = sumPercentage.add(balance = balance
                .multiply(percentageOnBalance)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(day)));
    }
}
