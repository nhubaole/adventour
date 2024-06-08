package com.adventour.web.utils;

public class FormatNumber {
    public static String formatNumberWithCommas(long number) {
        return String.format("%,d",number);
    }
}
