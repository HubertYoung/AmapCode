package com.alipay.mobile.framework.locale;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;

public class LocaleHelper {
    public static final String CURRENT_LANGUAGE = "language";
    public static final int FLAG_CHINA = 1;
    public static final int FLAG_HONGKONG = 3;
    public static final int FLAG_JAPANESE = 8;
    public static final int FLAG_KOREAN = 6;
    public static final int FLAG_RUSSIAN = 10;
    public static final int FLAG_TAIWAN = 2;
    public static final int FLAG_US = 4;
    public static final String LANGUAGE_CHANGE = "com.alipay.mobile.language.CHANGE";
    public static boolean LANGUAGE_UPLOAD = false;
    public static final String SPKEY_CHANGE_FLAG = "change";
    public static final String SPKEY_CURRENT_LANGUAGE = "currentlanguage";
    public static final String SPKEY_UPLOAD_FLAG = "uploadflag";
    /* access modifiers changed from: private */
    public static final String a = LocaleHelper.class.getSimpleName();
    public static int[] availableLanguage = {1, 2, 3, 4};
    private static LocaleHelper b;
    private static int e = -1;
    public static Locale systemLocale = null;
    private boolean c;
    private Application d;
    private SharedPreferences f;
    private final BroadcastReceiver g = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.LOCALE_CHANGED")) {
                TraceLogger.w(LocaleHelper.a, (String) "ACTION_LOCALE_CHANGED");
                LocaleHelper.this.a(false);
            }
        }
    };

    public static LocaleHelper getInstance() {
        if (b == null) {
            synchronized (LocaleHelper.class) {
                if (b == null) {
                    b = new LocaleHelper();
                }
            }
        }
        return b;
    }

    public void initSavedLocale(Application application) {
        if (!this.c) {
            TraceLogger.i(a, (String) "initSavedLocale start");
            this.d = application;
            f();
            a(true);
            TraceLogger.i(a, (String) "initSavedLocale end");
            this.c = true;
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean needRefresh) {
        int flag = c();
        if (a(getLocaleDesByFlag(flag))) {
            TraceLogger.i(a, (String) "isCurrentInBlacklist！");
            flag = 4;
            a(4);
        }
        e = getCurrentLanguage();
        TraceLogger.i(a, "flag = " + flag);
        TraceLogger.i(a, "currentLanguage = " + e);
        if (e != flag) {
            setUploadLocaleFlag(true);
            b(flag);
        }
        setNewLocale(flag, null, null, null, needRefresh);
    }

    public String getAlipayLocaleDes() {
        return getLocaleDesByFlag(getAlipayLocaleFlag());
    }

    public ArrayList<Integer> getAvailableLocaleFlagList() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < availableLanguage.length; i++) {
            if (!a(getLocaleDesByFlag(availableLanguage[i]))) {
                list.add(Integer.valueOf(availableLanguage[i]));
            }
        }
        return list;
    }

    public int getLocaleFlagByDes(String des) {
        return 4;
    }

    public String getLocaleDesByFlag(int flag) {
        switch (flag) {
            case -1:
                return "en";
            case 1:
                return "zh-Hans";
            case 2:
                return "zh-Hant";
            case 3:
                return "zh-HK";
            case 4:
                return "en";
            default:
                return null;
        }
    }

    public int getAlipayLocaleFlag() {
        Locale locale = null;
        try {
            locale = this.d.getResources().getConfiguration().locale;
        } catch (Exception e2) {
            TraceLogger.w(a, "getAlipayLocaleFlag", e2);
        }
        return a(locale);
    }

    public int getSystemLocaleFlag() {
        int flag = a(a());
        TraceLogger.i(a, "getSystemLocaleFlag: " + flag);
        return flag;
    }

    public int getAvailableLocaleFlag() {
        Locale locale = a();
        TraceLogger.i(a, "getSystemLocale(): " + locale.getLanguage());
        int flag = c(locale);
        TraceLogger.i(a, "getAvailableFlagByLocale: " + flag);
        return flag;
    }

    public boolean equalsLocale(Locale l1, Locale l2) {
        if (VERSION.SDK_INT <= 23) {
            return l1.equals(l2);
        }
        if (l1 == null || l2 == null || !l1.getLanguage().equals(l2.getLanguage()) || !l1.getCountry().equals(l2.getCountry())) {
            return false;
        }
        return true;
    }

    private static Locale a() {
        if (VERSION.SDK_INT <= 23 || systemLocale == null) {
            return Locale.getDefault();
        }
        return systemLocale;
    }

    public boolean setNewLocale(int localeFlag, Activity localeSetting, String appId, Bundle bundle) {
        return setNewLocale(localeFlag, localeSetting, appId, bundle, true);
    }

    public boolean setNewLocale(int localeFlag, Activity localeSetting, String appId, Bundle bundle, boolean needRefresh) {
        boolean isRefresh = false;
        if (localeFlag < 0 && getSystemLocaleFlag() < 0) {
            return false;
        }
        TraceLogger.i(a, "setNewLocale " + localeFlag);
        int alipayFlag = getAlipayLocaleFlag();
        if (!(localeSetting == null && appId == null && bundle == null)) {
            a(localeFlag);
        }
        if (localeFlag == alipayFlag) {
            return false;
        }
        Locale locale = c(localeFlag);
        if (locale == null) {
            return false;
        }
        b(localeFlag);
        b();
        if (needRefresh) {
            isRefresh = a(localeSetting, appId, bundle);
        }
        d(locale);
        Intent intent = new Intent();
        intent.setAction(LANGUAGE_CHANGE);
        intent.putExtra("language", getAlipayLocaleDes());
        if (this.d != null) {
            intent.setPackage(this.d.getPackageName());
        }
        this.d.sendBroadcast(intent);
        return isRefresh;
    }

    private LocaleHelper() {
    }

    private void a(int flag) {
        if (d().getInt("flag", -1) != flag) {
            TraceLogger.i(a, "saveLocale " + flag);
            try {
                d().edit().putInt("flag", flag).apply();
            } catch (Exception e2) {
                TraceLogger.w(a, "saveLocale", e2);
            }
        }
    }

    private void b() {
        try {
            d().edit().putInt(SPKEY_CHANGE_FLAG, 1).commit();
            TraceLogger.i(a, (String) "setChangeLocaleFlag 1");
        } catch (Exception e2) {
            TraceLogger.w(a, "setChangeLocaleFlag", e2);
        }
    }

    public void setUploadLocaleFlag(boolean flag) {
        TraceLogger.i(a, "setUploadLocaleFlag " + flag);
        try {
            d().edit().putBoolean(SPKEY_UPLOAD_FLAG, flag).commit();
        } catch (Exception e2) {
            TraceLogger.w(a, "setUploadLocaleFlag", e2);
        }
    }

    private void b(int flag) {
        TraceLogger.i(a, "setCurrentLanguage " + flag);
        try {
            d().edit().putInt(SPKEY_CURRENT_LANGUAGE, flag).commit();
        } catch (Exception e2) {
            TraceLogger.w(a, "setCurrentLanguage", e2);
        }
    }

    public boolean getUploadLocaleFlag() {
        boolean uploadFlag = d().getBoolean(SPKEY_UPLOAD_FLAG, false);
        TraceLogger.i(a, "setCurrentLanguage " + uploadFlag);
        return uploadFlag;
    }

    public int getCurrentLanguage() {
        return d().getInt(SPKEY_CURRENT_LANGUAGE, -1);
    }

    private int c() {
        int flag = -1;
        try {
            flag = d().getInt("flag", -1);
        } catch (Exception e2) {
            TraceLogger.w(a, "getSavedLocaleFlag", e2);
        }
        if (flag == -1) {
            flag = getAvailableLocaleFlag();
        }
        TraceLogger.i(a, "getSavedLocaleFlag " + flag);
        return flag;
    }

    private SharedPreferences d() {
        try {
            if (this.f == null) {
                this.f = this.d.getSharedPreferences("locale", 0);
            }
            return this.f;
        } catch (Throwable e2) {
            TraceLogger.w(a, "getPreference", e2);
            return null;
        }
    }

    private int a(Locale locale) {
        int result = b(locale);
        if (result != -100) {
            return result;
        }
        TraceLogger.w(a, "getFlagByLocale", new Exception("incorrect locale: " + locale));
        return -1;
    }

    private int b(Locale locale) {
        if (locale == null) {
            return -1;
        }
        if (equalsLocale(Locale.CHINA, locale)) {
            return 1;
        }
        if (equalsLocale(Locale.TAIWAN, locale)) {
            return 2;
        }
        if (equalsLocale(new Locale("zh", "HK"), locale)) {
            return 3;
        }
        if ("zh".equals(locale.getLanguage()) && "MO".equals(locale.getCountry())) {
            return 2;
        }
        String language = locale.getLanguage();
        if (Locale.US.getLanguage().equals(language)) {
            return 4;
        }
        if (new Locale("ru").getLanguage().equals(language)) {
            return 10;
        }
        return -100;
    }

    private int c(Locale locale) {
        int result = b(locale);
        if (result != -100) {
            return result;
        }
        TraceLogger.w(a, "getAvailableFlagByLocale", new Exception("incorrect locale: " + locale));
        return 4;
    }

    private static Locale c(int flag) {
        if (flag < 0) {
            return null;
        }
        switch (flag) {
            case 1:
                return Locale.CHINA;
            case 2:
                return Locale.TAIWAN;
            case 3:
                return new Locale("zh", "HK");
            case 4:
                return Locale.US;
            default:
                TraceLogger.w(a, "getLocaleByFlag", new Exception("incorrect flag: " + flag));
                return null;
        }
    }

    private void d(Locale locale) {
        try {
            Method method = this.d.getClass().getDeclaredMethod("setLocaleToApplicationResources", new Class[]{Locale.class});
            method.setAccessible(true);
            method.invoke(this.d, new Object[]{locale});
            Method method2 = this.d.getClass().getDeclaredMethod("clearCachedBundleResources", new Class[0]);
            method2.setAccessible(true);
            method2.invoke(this.d, new Object[0]);
            TraceLogger.i(a, (String) "setLocaleToResource");
        } catch (Exception e2) {
            TraceLogger.w(a, "setLocaleToResource", e2);
        }
    }

    private boolean a(Activity localeSetting, String appId, Bundle bundle) {
        if (!this.c) {
            return false;
        }
        return LocaleUtils.refreshHomeActivity(localeSetting, appId, bundle);
    }

    private boolean a(String currentDes) {
        if (currentDes == null) {
            return false;
        }
        String[] list = e();
        if (list == null) {
            return false;
        }
        if (list == null || len == 0) {
            return false;
        }
        for (String equals : list) {
            if (currentDes.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    private String[] e() {
        try {
            String val = PreferenceManager.getDefaultSharedPreferences(this.d).getString("localeBlacklist", "");
            TraceLogger.i(a, "localeBlacklist = " + val);
            if (TextUtils.isEmpty(val)) {
                return null;
            }
            return val.split(",");
        } catch (Throwable e2) {
            TraceLogger.w(a, e2);
            return null;
        }
    }

    private void f() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.LOCALE_CHANGED");
        this.d.registerReceiver(this.g, filter);
    }
}
