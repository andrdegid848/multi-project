package org.example.models;

import lombok.Getter;
import lombok.SneakyThrows;

import javax.security.auth.login.AccountException;
import java.math.BigDecimal;

/**
 * Класс, описывающий процентную ставку
 * @author Andey Gusev
 * @version 1.0
 * */
@Getter
public class PercentageOnBalance {

    /** Минимальная процентная ставка */
    private final BigDecimal lowPercentage;

    /** Средняя процентная ставка */
    private final BigDecimal middlePercentage;

    /** Максимальная процентная ставка */
    private final BigDecimal highPercentage;

    @SneakyThrows
    public PercentageOnBalance(BigDecimal lowPercentage, BigDecimal middlePercentage, BigDecimal highPercentage) {
        if (lowPercentage.compareTo(BigDecimal.ZERO) <= 0 || middlePercentage.compareTo(BigDecimal.ZERO) <= 0
            || highPercentage.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AccountException("The percentage value is incorrect");
        }
        this.lowPercentage = lowPercentage;
        this.middlePercentage = middlePercentage;
        this.highPercentage = highPercentage;
    }
}
