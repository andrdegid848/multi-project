package org.example.entities.banks;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.entities.accounts.Account;
import org.example.exceptions.BankException;
import org.example.models.PercentageOnBalance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс, описывающий центральный банк
 * @author Andey Gusev
 * @version 1.0
 * */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CentralBank {

    /** Поле для возращения единственного экземпляра данного класса (используется паттерн Singleton) */
    private static final CentralBank INSTANCE = new CentralBank();

    /** Список обычных банков */
    private List<Bank> banks = new ArrayList<>();

    public List<Bank> getBanks() {
        return Collections.unmodifiableList(banks);
    }

    public static CentralBank getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public Bank addBank(BigDecimal debitPercentage,
                        PercentageOnBalance depositPercentage,
                        BigDecimal creditLimit,
                        BigDecimal commission,
                        BigDecimal doubtLimit) {
        Bank newBank = new Bank(debitPercentage, depositPercentage, creditLimit, commission, doubtLimit);
        banks.add(newBank);
        return newBank;
    }

    /**
     * Метод для удаления определенного банка из центрального
     * @param bank банк
     * */
    @SneakyThrows
    public boolean removeBank(Bank bank) {
        if (!banks.contains(bank))
            throw new BankException("There isn't such bank in the central bank");
        return banks.remove(bank);
    }

    /**
     * Метод для уведомлений о комиссионных операциях
     * */
    public void sendMessageAboutCommissionOperation() {
        banks.forEach(Bank::getMessage);
    }

    /**
     * Метод для перевода денег с счета на счет. Счета принадлежат рахным банкам
     * @param bank1 первый банк, в котором есть счет отправителя
     * @param bank2 второй банк, в котром есть счет получателя
     * @param account1 счет отправителя
     * @param account2 счет получателя
     * @param money количество денег
     * */
    @SneakyThrows
    public void transferMoneyBetweenDifferentBanks(Bank bank1, Bank bank2, Account account1, Account account2, BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) <= 0)
            throw new BankException("The quantity of money is incorrect");
        if (!banks.contains(bank1) || !banks.contains(bank2))
            throw new BankException("The bank doesn't exists");
        Account currentAccount1 = banks.stream()
                .filter(bank -> bank.getId().equals(bank1.getId()))
                .flatMap(bank -> bank.getAccounts().stream())
                .filter(account -> account.equals(account1))
                .findFirst()
                .orElse(null);

        Account currentAccount2 = banks.stream()
                .filter(bank -> bank.getId().equals(bank2.getId()))
                .flatMap(bank -> bank.getAccounts().stream())
                .filter(account -> account.equals(account2))
                .findFirst()
                .orElse(null);

        if (currentAccount1 != null) {
            currentAccount1.decrease(money);
        }
        if (currentAccount2 != null) {
            currentAccount2.increase(money);
        }
    }
}
