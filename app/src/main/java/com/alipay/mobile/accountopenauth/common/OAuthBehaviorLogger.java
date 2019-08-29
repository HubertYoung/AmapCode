package com.alipay.mobile.accountopenauth.common;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;

public class OAuthBehaviorLogger {
    public static void a(String str, String str2, String str3, String str4, String str5) {
        a(str, str2, str3, str4, str5, BehaviorType.EVENT);
    }

    public static void a(String str, String str2, Throwable th) {
        LoggerFactory.e().a(str, str2, th);
    }

    public static void a(String str, String str2, String str3, String str4, String str5, BehaviorType behaviorType) {
        Behavior behavior = new Behavior();
        behavior.a = str;
        behavior.c = str2;
        if (!TextUtils.isEmpty(str3)) {
            behavior.g = str3;
        }
        if (!TextUtils.isEmpty(str4)) {
            behavior.h = str4;
        }
        if (!TextUtils.isEmpty(str5)) {
            behavior.i = str5;
        }
        behavior.b = behaviorType;
        LoggerFactory.d().a(behavior);
    }
}
