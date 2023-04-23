package org.example.entities.banks;

import org.example.entities.accounts.CreditAccount;
import org.example.entities.accounts.DebitAccount;
import org.example.entities.accounts.DepositAccount;
import org.example.entities.clients.Client;
import org.example.models.Address;
import org.example.models.PercentageOnBalance;
import org.example.models.transactions.TransferTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс для тестирования всех методов центрального банка
 * @author Andey Gusev
 * @version 1.0
 * @see CentralBank
 * */
class CentralBankTest {

    /** Центральный банк */
    private final CentralBank centralBank = CentralBank.getInstance();

    /** Первый обычный банк */
    private Bank bank1;

    /** Второй обычный банк */
    private Bank bank2;

    /** Первый клиент */
    private Client client1;

    /** Второй клиент */
    private Client client2;

    /** Дебетовый счет */
    private DebitAccount debitAccount;

    /** Депозитный счет */
    private DepositAccount depositAccount;

    /** Кредитный счет */
    private CreditAccount creditAccount;


    /** Создание объектов */
    @BeforeEach
    void setUp() {
        PercentageOnBalance percentageOnBalance1 = new PercentageOnBalance(BigDecimal.valueOf(3), BigDecimal.valueOf(5), BigDecimal.valueOf(5.75));
        PercentageOnBalance percentageOnBalance2 = new PercentageOnBalance(BigDecimal.valueOf(3.5), BigDecimal.valueOf(4), BigDecimal.valueOf(5.55));
        bank1 = centralBank.addBank(BigDecimal.valueOf(2), percentageOnBalance1, BigDecimal.valueOf(50_000), BigDecimal.valueOf(2000), BigDecimal.valueOf(30_000));
        bank2 = centralBank.addBank(BigDecimal.valueOf(1.5), percentageOnBalance2, BigDecimal.valueOf(35_000), BigDecimal.valueOf(2500), BigDecimal.valueOf(40_000));
        client1 = bank1.addClient("Name1", "Surname1", Optional.of("123456"), Optional.of(new Address("Street1", 1)));
        client2 = bank2.addClient("Name2", "Surname2", Optional.of("123654"), Optional.of(new Address("Street2", 2)));
        debitAccount = new DebitAccount(BigDecimal.valueOf(100_000), client1, bank1);
        depositAccount = new DepositAccount(BigDecimal.valueOf(50_000), client2, bank2);
        creditAccount = new CreditAccount(BigDecimal.valueOf(10_000), client2, bank2);
    }

    /** Тестирование метода для перевода денег между двумя разными счетами двух банков
     * Тестирование метода для подсчета комиссионных
     * */
    @Test
    void createTwoBanksAndCheckTransfersFromFirstBankToSecondBank_CommissionPerMonth() {
        bank1.addAccount(client1, debitAccount);
        bank2.addAccount(client2, depositAccount);
        bank2.addAccount(client2, creditAccount);

        centralBank.transferMoneyBetweenDifferentBanks(bank1, bank2, debitAccount, depositAccount, BigDecimal.valueOf(10_000));
        assertEquals(BigDecimal.valueOf(90_000), debitAccount.getBalance());
        assertEquals(BigDecimal.valueOf(60_000), depositAccount.getBalance());

        centralBank.sendMessageAboutCommissionOperation();
        assertEquals(BigDecimal.valueOf(90_147.95), debitAccount.getBalance().setScale(2, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.valueOf(60_197.26), depositAccount.getBalance().setScale(2, RoundingMode.HALF_UP));
    }

    /** Тестирование метода для перевода денег между двумя разными счетами одного банка
     * Тестирование метода для отмены транзакции
     * */
    @Test
    void transferMoneyInOneBankOnly_CancelTransaction() {
        bank1.addClient(client2);
        bank1.addAccount(client1, debitAccount);
        bank1.addAccount(client2, depositAccount);
        bank1.addAccount(client2, creditAccount);

        TransferTransaction transferTransaction = bank1.transferMoney(debitAccount, depositAccount, BigDecimal.valueOf(5000));
        assertEquals(BigDecimal.valueOf(95_000), debitAccount.getBalance());
        assertEquals(BigDecimal.valueOf(55_000), depositAccount.getBalance());

        bank1.cancelTransaction(transferTransaction);
        assertEquals(BigDecimal.valueOf(100_000), debitAccount.getBalance());
        assertEquals(BigDecimal.valueOf(50_000), depositAccount.getBalance());
    }

    /** Тестирование метода для отправки уведомления клиенту об изменении условий тарифа */
    @Test
    void changeAccountTerms_SendMessageToClientsAboutIt() {
        bank1.addClient(client2);
        bank1.addAccount(client1, debitAccount);
        bank1.addAccount(client2, depositAccount);
        bank1.addAccount(client2, creditAccount);
        client1.changeSubscription();

        bank1.changeDebitPercentage(BigDecimal.valueOf(1.75));
        bank1.changeCreditLimit(BigDecimal.valueOf(40_000));

        assertEquals(BigDecimal.valueOf(1.75), bank1.getDebitPercentage());
        assertEquals(BigDecimal.valueOf(40_000), bank1.getCreditLimit());
        assertEquals(2, client1.getNotifications().size());
        assertEquals(0, client2.getNotifications().size());
    }

}