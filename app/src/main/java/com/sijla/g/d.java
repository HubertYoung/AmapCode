package com.sijla.g;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class d {
    public static String a() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String b() {
        try {
            return new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis()));
        } catch (Exception unused) {
            return "";
        }
    }

    public static String a(long j) {
        return new SimpleDateFormat("yyyyMMdd").format(Long.valueOf(j));
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String b(long j) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(j));
    }

    @SuppressLint({"SimpleDateFormat"})
    public static String c() {
        try {
            return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        } catch (Exception unused) {
            return "";
        }
    }

    public static long d() {
        return System.currentTimeMillis() / 1000;
    }
}
