package com.alipay.mobile.antui.utils;

import android.support.annotation.NonNull;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils extends android.text.format.DateUtils {

    public enum DifferenceMode {
        Second,
        Minute,
        Hour,
        Day
    }

    public static long calculateDifferentSecond(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Second);
    }

    public static long calculateDifferentMinute(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Minute);
    }

    public static long calculateDifferentHour(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Hour);
    }

    public static long calculateDifferentDay(Date startDate, Date endDate) {
        return calculateDifference(startDate, endDate, DifferenceMode.Day);
    }

    public static long calculateDifferentSecond(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Second);
    }

    public static long calculateDifferentMinute(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Minute);
    }

    public static long calculateDifferentHour(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Hour);
    }

    public static long calculateDifferentDay(long startTimeMillis, long endTimeMillis) {
        return calculateDifference(startTimeMillis, endTimeMillis, DifferenceMode.Day);
    }

    public static long calculateDifference(long startTimeMillis, long endTimeMillis, DifferenceMode mode) {
        return calculateDifference(new Date(startTimeMillis), new Date(endTimeMillis), mode);
    }

    public static long calculateDifference(Date startDate, Date endDate, DifferenceMode mode) {
        long[] different = calculateDifference(startDate, endDate);
        if (mode.equals(DifferenceMode.Minute)) {
            return different[2];
        }
        if (mode.equals(DifferenceMode.Hour)) {
            return different[1];
        }
        if (mode.equals(DifferenceMode.Day)) {
            return different[0];
        }
        return different[3];
    }

    public static long[] calculateDifference(Date startDate, Date endDate) {
        return calculateDifference(endDate.getTime() - startDate.getTime());
    }

    public static long[] calculateDifference(long differentMilliSeconds) {
        long elapsedDays = differentMilliSeconds / 86400000;
        long differentMilliSeconds2 = differentMilliSeconds % 86400000;
        long elapsedHours = differentMilliSeconds2 / 3600000;
        long differentMilliSeconds3 = differentMilliSeconds2 % 3600000;
        return new long[]{elapsedDays, elapsedHours, differentMilliSeconds3 / 60000, (differentMilliSeconds3 % 60000) / 1000};
    }

    public static int calculateDaysInMonth(int month) {
        return calculateDaysInMonth(0, month);
    }

    public static int calculateDaysInMonth(int year, int month) {
        List bigList = Arrays.asList(new String[]{"1", "3", "5", "7", "8", "10", "12"});
        List littleList = Arrays.asList(new String[]{"4", "6", "9", "11"});
        if (bigList.contains(String.valueOf(month))) {
            return 31;
        }
        if (littleList.contains(String.valueOf(month))) {
            return 30;
        }
        if (year <= 0) {
            return 29;
        }
        if ((year % 4 != 0 || year % 100 == 0) && year % 400 != 0) {
            return 28;
        }
        return 29;
    }

    public static ArrayList<String> caculateMonths(int dstDate, int type, List<String> months) {
        ArrayList list1 = new ArrayList();
        List list = new ArrayList();
        if (type == 0) {
            try {
                list = months.subList(0, dstDate);
            } catch (Exception e) {
                return getMonths();
            }
        } else if (type == 1) {
            list = months.subList(dstDate, 12);
        }
        for (int i = 0; i < list.size(); i++) {
            list1.add(list.get(i));
        }
        return list1;
    }

    public static ArrayList<String> getMonths() {
        ArrayList months = new ArrayList();
        for (int i = 1; i <= 12; i++) {
            months.add(fillZero(i));
        }
        return months;
    }

    @NonNull
    public static String fillZero(int number) {
        return number < 10 ? "0" + number : String.valueOf(number);
    }

    public static boolean isSameDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date is null");
        }
        Calendar nowCalendar = Calendar.getInstance();
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTime(date);
        if (nowCalendar.get(0) == newCalendar.get(0) && nowCalendar.get(1) == newCalendar.get(1) && nowCalendar.get(6) == newCalendar.get(6)) {
            return true;
        }
        return false;
    }

    public static Date parseDate(String dateStr, String dataFormat) {
        try {
            return new Date(new SimpleDateFormat(dataFormat).parse(dateStr).getTime());
        } catch (Exception e) {
            Log.w("compositeui", e);
            return null;
        }
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }
}
