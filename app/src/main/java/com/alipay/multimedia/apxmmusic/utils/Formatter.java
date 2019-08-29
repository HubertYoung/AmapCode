package com.alipay.multimedia.apxmmusic.utils;

public class Formatter {
    public static String formatLengthToDuration(int length) {
        return formatInt(length / 60) + ":" + formatInt(length % 60);
    }

    private static String formatInt(int val) {
        String text = String.valueOf(val);
        return text.length() < 2 ? "0" + text : text;
    }
}
