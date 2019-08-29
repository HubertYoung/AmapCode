package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    @SuppressLint({"SimpleDateFormat"})
    public static String getCurrentDateFormat() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }
}
