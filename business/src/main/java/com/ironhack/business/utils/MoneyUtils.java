package com.ironhack.business.utils;


import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Locale;

public class MoneyUtils {

    public static CurrencyUnit getCurrencyUnit() {
        return Monetary.getCurrency(new Locale("es", "ES"));
    }

}
