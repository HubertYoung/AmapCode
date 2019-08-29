package com.alipay.android.phone.inside.log.ex;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.LogCollect;
import com.alipay.android.phone.inside.log.api.ex.ExceptionEnum;
import com.alipay.android.phone.inside.log.api.ex.ExceptionLogger;
import com.alipay.android.phone.inside.log.field.ExceptionField;

public class ExceptionLoggerImpl implements ExceptionLogger {
    private static boolean b(String str, String str2) {
        return !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2);
    }

    private static boolean a(ExceptionEnum exceptionEnum) {
        return exceptionEnum == ExceptionEnum.CRASH;
    }

    private static void a(ExceptionField exceptionField) {
        if (exceptionField != null) {
            LogCollect logCollect = new LogCollect();
            logCollect.a(exceptionField);
            logCollect.c();
        }
    }

    public final void a(String str, String str2) {
        if (b(str, str2)) {
            LogCollect.a().a(new ExceptionField(str, str2));
        }
    }

    public final void a(String str, String str2, String... strArr) {
        if (b(str, str2)) {
            LogCollect.a().a(new ExceptionField(str, str2, strArr));
        }
    }

    public final void a(String str, String str2, Throwable th) {
        if (b(str, str2)) {
            LogCollect.a().a(new ExceptionField(str, str2, th));
        }
    }

    public final void a(ExceptionEnum exceptionEnum, String str, String str2, Throwable th) {
        if (b(str, str2)) {
            ExceptionField exceptionField = new ExceptionField(str, str2, th);
            if (a(exceptionEnum)) {
                a(exceptionField);
            } else {
                LogCollect.a().a(exceptionField);
            }
        }
    }

    public final void a(String str, String str2, Throwable th, String... strArr) {
        if (b(str, str2)) {
            LogCollect.a().a(new ExceptionField(str, str2, th, strArr));
        }
    }

    public final void a(ExceptionEnum exceptionEnum, String str, String str2, Throwable th, String... strArr) {
        if (b(str, str2)) {
            ExceptionField exceptionField = new ExceptionField(str, str2, th, strArr);
            if (a(exceptionEnum)) {
                a(exceptionField);
            } else {
                LogCollect.a().a(exceptionField);
            }
        }
    }
}
