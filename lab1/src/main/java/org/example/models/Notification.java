package org.example.models;

import lombok.Getter;

/**
 * Класс, описывающий уведомления о событии
 * @author Andey Gusev
 * @version 1.0
 * */
@Getter
public class Notification {

    private final String text;

    public Notification(AccountTerm term)
    {
        text = "Dear customer, please note that the " + term + " value changed";
    }
}
