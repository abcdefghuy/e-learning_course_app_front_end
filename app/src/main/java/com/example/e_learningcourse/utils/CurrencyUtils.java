package com.example.e_learningcourse.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {
    public static String formatCurrencyVND(double amount) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        currencyFormat.setMaximumFractionDigits(0);
        return currencyFormat.format(amount);
    }
}
