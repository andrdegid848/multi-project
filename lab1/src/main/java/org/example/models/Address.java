package org.example.models;

import lombok.Getter;
import lombok.SneakyThrows;
import org.example.exceptions.ClientException;

/**
 * Класс, описывающий адрес клиента
 * @author Andey Gusev
 * @version 1.0
 * */
@Getter
public class Address {

    /** Улица */
    private final String street;

    /** Номер дома */
    private final Integer houseNumber;

    @SneakyThrows
    public Address(String street, Integer houseNumber) {
        if (street == null || !street.chars().allMatch(Character::isLetterOrDigit) || houseNumber < 1) {
            throw new ClientException("The address is incorrect");
        }
        this.street = street;
        this.houseNumber = houseNumber;
    }
}
