package org.example.entities.banks;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import org.example.entities.accounts.Account;
import org.example.entities.clients.Client;
import org.example.exceptions.BankException;
import org.example.models.AccountTerm;
import org.example.models.Address;
import org.example.models.PercentageOnBalance;
import org.example.models.transactions.ReplenishmentTransaction;
import org.example.models.transactions.Transaction;
import org.example.models.transactions.TransferTransaction;
import org.example.models.transactions.WithdrawalTransaction;

import java.math.BigDecimal;
import java.util.*;

/**
 * Класс, описывающий обычный банк
 * @author Andey Gusev
 * @version 1.0
 * */
@Getter
@EqualsAndHashCode
public class Bank implements BankFeatures {

    /** Список клиентов в банке */
    private final List<Client> clients = new ArrayList<>();

    /** Список счетов */
    private final List<Account> accounts = new ArrayList<>();

    /** Список транзакций */
    private final List<Transaction> transactions = new ArrayList<>();

    /** Комиссионный период */
    public static final Integer COMMISSION_OPERATION_PERIOD = 30;

    /** Дебетовые проценты */
    private BigDecimal debitPercentage;

    /** Депозитные проценты */
    private PercentageOnBalance depositPercentage;

    /** Кредитный лимит */
    private BigDecimal creditLimit;

    /** Лимит денег для "сомнительного клиента" */
    private BigDecimal doubtLimit;

    /** Комиссия */
    private final BigDecimal commission;
    /** Поле идентификатора */
    private UUID id;

    public Bank(BigDecimal debitPercentage, PercentageOnBalance depositPercentage,
                BigDecimal creditLimit, BigDecimal commission, BigDecimal doubtLimit) throws BankException {
        if (debitPercentage.compareTo(BigDecimal.ZERO) <= 0 || creditLimit.compareTo(BigDecimal.ZERO) <= 0
            || commission.compareTo(BigDecimal.ZERO) <= 0 || doubtLimit.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BankException("The debit percentage or credit limit or commission value is incorrect");
        }

        this.debitPercentage = debitPercentage;
        this.depositPercentage = depositPercentage;
        this.creditLimit = creditLimit;
        this.doubtLimit = doubtLimit;
        this.commission = commission;
        this.id = UUID.randomUUID();
    }

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Метод для снятия денег в банке с определенного счета
     * @param account текущий счет
     * @param money количество денег
     * */
    @SneakyThrows
    @Override
    public WithdrawalTransaction withdrawalCash(Account account, BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BankException("The quantity of money is incorrect");
        }
        isDoubtfulClient(account, money);


        accounts.stream()
                .filter(a -> a.equals(account))
                .findFirst()
                .ifPresent(a -> a.decrease(money));

        WithdrawalTransaction withdrawalTransaction = new WithdrawalTransaction(Optional.of(account), null, money);
        transactions.add(withdrawalTransaction);
        return withdrawalTransaction;
    }

    /**
     * Метод для пополнения счета
     * @param account текущий счет
     * @param money количество денег
     * */
    @SneakyThrows
    @Override
    public ReplenishmentTransaction replenishmentMoney(Account account, BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) <= 0)
            throw new BankException("The quantity of money is incorrect");
        isDoubtfulClient(account, money);

        accounts.stream()
                .filter(item -> item.equals(account))
                .findFirst()
                .ifPresent(item -> item.increase(money));

        var replenishmentTransaction = new ReplenishmentTransaction(null, Optional.of(account), money);
        transactions.add(replenishmentTransaction);
        return replenishmentTransaction;
    }

    /**
     * Метод для перевода денег с одного счета на другой
     * @param account1 счет, с которого переводят деньги
     * @param account2 счет получателя
     * @param money количество денег
     * */
    @SneakyThrows
    @Override
    public TransferTransaction transferMoney(Account account1, Account account2, BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) <= 0)
            throw new BankException("The quantity of money is incorrect");
        accounts.stream()
                .filter(item -> item.equals(account1))
                .findFirst()
                .ifPresent(item -> item.decrease(money));

        accounts.stream()
                .filter(item -> item.equals(account2))
                .findFirst()
                .ifPresent(item -> item.increase(money));

        TransferTransaction transferTransaction = new TransferTransaction(Optional.of(account1), Optional.of(account2), money);
        transactions.add(transferTransaction);
        return transferTransaction;
    }

    /**
     * Метод для отмены транзакции
     * @param transaction необходимая транзакция
     * */
    @Override
    public void cancelTransaction(Transaction transaction) {
        Transaction currentTransaction = transactions.stream()
                .filter(t -> t.equals(transaction))
                .findFirst().orElse(null);


        if (currentTransaction.getFirstAccount().isEmpty() && currentTransaction.getSecondAccount().isPresent()) {
            Account currentAccount = findAccount(currentTransaction.getSecondAccount().orElse(null));
            currentAccount.increase(currentTransaction.getSum());
        } else if (currentTransaction.getSecondAccount().isEmpty() && currentTransaction.getFirstAccount().isPresent()) {
            Account currentAccount = findAccount(currentTransaction.getFirstAccount().orElse(null));
            currentAccount.decrease(currentTransaction.getSum());
        } else if (currentTransaction.getSecondAccount().isPresent() && currentTransaction.getFirstAccount().isPresent()) {
            Account currentAccount1 = findAccount(currentTransaction.getFirstAccount().orElse(null));
            Account currentAccount2 = findAccount(currentTransaction.getSecondAccount().orElse(null));
            currentAccount1.increase(currentTransaction.getSum());
            currentAccount2.decrease(currentTransaction.getSum());
        }

        transactions.remove(currentTransaction);
    }

    /**
     * Метод для добавления уже существующего клиента
     * @param client клиент
     * */
    @SneakyThrows
    public void addClient(Client client) {
        if (clients.contains(client))
            throw new BankException("This client is already added");

        clients.add(client);
    }

    /**
     * Метод для создания и добавления клиента с соотвествующими полями объекта
     * */
    @Override
    public Client addClient(String name, String surname, Optional<String> passport, Optional<Address> address) {
        Client client = Client.builder()
                .name(name)
                .surname(surname)
                .passportNumber(passport.orElse(null))
                .address(address.orElse(null))
                .build();

        addClient(client);
        return client;
    }

    /**
     * Метод для добавления счета
     * @param client клиент
     * @param account счет
     * */
    @SneakyThrows
    @Override
    public void addAccount(Client client, Account account) {
        if (!clients.contains(client))
            throw new BankException("There isn't such client in this bank");
        if (accounts.contains(account))
            throw new BankException("This account is already added");

        clients.stream()
                .filter(item -> item.equals(client))
                .findFirst()
                .ifPresent(item -> item.addAccount(account));

        accounts.add(account);
    }

    /**
     * Метод для удаления клиента
     * @param client клиент
     * */
    @SneakyThrows
    @Override
    public boolean removeClient(Client client) {
        if (!clients.contains(client))
            throw new BankException("There isn't such client in this bank");
        accounts.removeIf(item -> client.getAccounts().contains(item));
        return clients.remove(client);
    }

    /**
     * Метод для операции с комиссией
     * */
    @Override
    public void commissionOperation() {
        accounts.forEach(account -> account.commissionOperation(COMMISSION_OPERATION_PERIOD));
    }

    /**
     * Метод для изменения процентов на дебетовом счете
     * @param debitPercentage процент
     * */
    @SneakyThrows
    @Override
    public void changeDebitPercentage(BigDecimal debitPercentage) {
        if (debitPercentage.compareTo(BigDecimal.ZERO) <= 0)
            throw new BankException("The debit percentage is incorrect");
        this.debitPercentage = debitPercentage;
        sendNotification(AccountTerm.DebitPercentage);
    }

    /**
     * Метод для изменения процентов на депозитном счете
     * @param depositPercentage процент
     * */
    @Override
    public void changeDepositPercentage(PercentageOnBalance depositPercentage) {
        this.depositPercentage = depositPercentage;
        sendNotification(AccountTerm.DepositPercentage);
    }

    /**
     * Метод для изменения лимита на кредитном счете
     * @param creditLimit лимит
     * */
    @SneakyThrows
    @Override
    public void changeCreditLimit(BigDecimal creditLimit) {
        if (creditLimit.compareTo(BigDecimal.ZERO) <= 0)
            throw new BankException("The credit limit is incorrect");
        this.creditLimit = creditLimit;
        sendNotification(AccountTerm.CreditLimit);
    }

    /**
     * Метод для изменения лимита денег на счете для определения "сомнительного клиента"
     * @param doubtLimit лимит
     * */
    @SneakyThrows
    @Override
    public void changeDoubtLimit(BigDecimal doubtLimit) {
        if (doubtLimit.compareTo(BigDecimal.ZERO) <= 0)
            throw new BankException("The doubt limit is incorrect");
        this.doubtLimit = doubtLimit;
        sendNotification(AccountTerm.DoubtLimit);
    }

    @Override
    public void getMessage() {
        commissionOperation();
    }

    /**
     * Метод для отправки уведомлений
     * */
    private void sendNotification(AccountTerm accountTerm) {
        clients.stream()
                .filter(Client::isSubscription)
                .forEach(item -> item.addNotification(accountTerm));
    }

    /**
     * Приватный метод для поиска определенного счета
     * @param account счет
     * */
    private Account findAccount(Account account) {
        return accounts.stream()
                .filter(item -> item.equals(account))
                .findFirst()
                .orElse(null);
    }

    /**
     * Приватный метод для определения "сомнительного клиента"
     * @param account счет
     * @param money количество денег
     * */
    @SneakyThrows
    private void isDoubtfulClient(Account account, BigDecimal money) {
        Optional<Client> maybeClient = clients.stream()
                .filter(item -> item.getAccounts().contains(account))
                .findFirst();
        if (maybeClient.get().isDoubtful() && money.compareTo(doubtLimit) > 0)
            throw new BankException("The limit of doubtful client is exceeded");
    }
}
