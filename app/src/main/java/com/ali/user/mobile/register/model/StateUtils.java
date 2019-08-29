package com.ali.user.mobile.register.model;

import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.ali.user.mobile.AliUserInit;
import com.ali.user.mobile.accountbiz.sp.AUSharedPreferences;
import com.ali.user.mobile.accountbiz.sp.SharedPreferencesManager;
import com.ali.user.mobile.encryption.DataEncryptor;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.register.store.ActionCenter;
import com.amap.bundle.drivecommon.inter.NetConstant;

public class StateUtils {
    private static Handler a = new Handler(Looper.getMainLooper()) {
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            if (-1 == message.what) {
                ActionCenter actionCenter = RegContext.a().c;
                if (actionCenter != null) {
                    StateUtils.a(actionCenter.a());
                }
            }
        }
    };

    public static void a(State state) {
        if (state != null && state.a() != null) {
            AUSharedPreferences a2 = SharedPreferencesManager.a(AliUserInit.b(), "secuitySharedDataStore", 0);
            if (a2 != null) {
                a2.b(NetConstant.KEY_MONEY_ACCOUNT);
                a2.b("countryId");
                a2.b("countryName");
                ContextWrapper contextWrapper = new ContextWrapper(AliUserInit.b());
                a(a2, contextWrapper, NetConstant.KEY_MONEY_ACCOUNT, state.a().getPhoneNumber());
                a(a2, contextWrapper, "countryId", state.a().getFullAreaCode());
                a(a2, contextWrapper, "countryName", state.a().getAreaName());
                a2.c();
            }
        }
    }

    private static void a(AUSharedPreferences aUSharedPreferences, ContextWrapper contextWrapper, String str, String str2) {
        try {
            aUSharedPreferences.b(str, DataEncryptor.a(contextWrapper, str2));
        } catch (Throwable unused) {
        }
    }

    public static State a() {
        AUSharedPreferences a2 = SharedPreferencesManager.a(AliUserInit.b(), "secuitySharedDataStore", 0);
        if (a2 == null) {
            return null;
        }
        ContextWrapper contextWrapper = new ContextWrapper(AliUserInit.b());
        String a3 = a(a2, contextWrapper, NetConstant.KEY_MONEY_ACCOUNT);
        String a4 = a(a2, contextWrapper, "countryId");
        String a5 = a(a2, contextWrapper, "countryName");
        State state = new State();
        state.a(a4, a3, a5);
        return state;
    }

    private static String a(AUSharedPreferences aUSharedPreferences, ContextWrapper contextWrapper, String str) {
        try {
            return DataEncryptor.b(contextWrapper, aUSharedPreferences.a(str, ""));
        } catch (Throwable unused) {
            r2 = "";
            return "";
        }
    }

    public static void b() {
        if (!a.hasMessages(-1)) {
            a.sendEmptyMessageDelayed(-1, 200);
        }
    }

    public static void c() {
        a.removeMessages(-1);
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter != null) {
            AUSharedPreferences a2 = SharedPreferencesManager.a(AliUserInit.b(), "secuitySharedDataStore", 0);
            if (a2 != null) {
                a2.b(NetConstant.KEY_MONEY_ACCOUNT);
                a2.b("countryId");
                a2.b("countryName");
                a2.c();
            }
            actionCenter.a(new State());
        }
    }
}
