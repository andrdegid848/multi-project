package org.example.entities.accounts;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 *Абстрактный класс, содержащий общую информацию о счетах
 * @author Andey Gusev
 * @version 1.0
 * */
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public abstract class Account {

    /** Поле индентификатор */
    private final UUID id = UUID.randomUUID();

    /** Метод для увеличения суммы денег на счете
     * @param money сумма денег
     * */
    public abstract void increase(BigDecimal money);

    /** Метод для уменьшения суммы денег на счете
     * @param money сумма денег
     * */
    public abstract void decrease(BigDecimal money);

    /** Метод для операций с комиссей
     * @param day количество дней
     * */
    public abstract void commissionOperation(int day);
}
