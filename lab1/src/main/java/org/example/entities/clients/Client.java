package org.example.entities.clients;

import lombok.*;
import org.example.entities.accounts.Account;
import org.example.exceptions.ClientException;
import org.example.models.AccountTerm;
import org.example.models.Address;
import org.example.models.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, описывающий клиента
 * @author Andey Gusev
 * @version 1.0
 * */
@Getter
@EqualsAndHashCode
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Client {

    /** Список счетов клиента */
    @Builder.Default
    private List<Account> accounts = new ArrayList<>();
    /** Список уведомлений */
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();

    private final String name;

    /** Фамилия клиента */
    private final String surname;

    /** Номер паспорта */
    private String passportNumber;

    /** Адрес */
    private Address address;

    /** Поле для определения статуса "сомнительного клиента" */
    private boolean doubtful;

    /** Подписка на уведомления */
    private boolean subscription;

    public Client(String name, String surname, String passportNumber, Address address) {
        if (!name.chars().allMatch(Character::isLetterOrDigit))
            throw new IllegalArgumentException("The name is incorrect");
        if (!surname.chars().allMatch(Character::isLetterOrDigit))
            throw new IllegalArgumentException("The surname is incorrect");

        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
        this.address = address;
        this.doubtful = passportNumber == null || address == null;
        this.subscription = false;
    }

    /**
     * Метод для добавления счета
     * @param account счет
     * */
    @SneakyThrows
    public void addAccount(Account account) {
        if (accounts.contains(account)) {
            throw new ClientException("This account is already added");
        }
        accounts.add(account);
    }

    /**
     * Метод для удаления счета
     * @param account счет
     * */
    @SneakyThrows
    public boolean removeAccount(Account account) {
        if (!accounts.contains(account)) {
            throw new ClientException("There isn't such account");
        }
        return accounts.remove(account);
    }

    @SneakyThrows
    public void setPassportNumber(String passportNumber) {
        if (passportNumber == null || !passportNumber.matches("\\d{6}")) {
            throw new ClientException("The passport number is incorrect");
        }
        if (!this.passportNumber.isBlank()) {
            throw new ClientException("The passport number is already added");
        }

        if (doubtful && this.passportNumber != null && address != null)
            doubtful = false;
        this.passportNumber = passportNumber;
    }

    @SneakyThrows
    public void setAddress(Address address) {
        if (this.address != null) {
            throw new ClientException("The address is already added");
        }

        if (doubtful && this.passportNumber != null && address != null)
            doubtful = false;
        this.address = address;
    }

    /**
     * Метод для изменения подписки клиента на уведомления
     * */
    public void changeSubscription() {
        this.subscription = !this.subscription;
    }

    public void addNotification(AccountTerm term) {
        notifications.add(new Notification(term));
    }
}

