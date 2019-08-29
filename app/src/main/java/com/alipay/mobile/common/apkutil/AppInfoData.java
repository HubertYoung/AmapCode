package com.alipay.mobile.common.apkutil;

import android.graphics.drawable.Drawable;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class AppInfoData {
    private Drawable a;
    private String b;
    private String c;
    private String d;
    private String e;

    public AppInfoData() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public Drawable getAppicon() {
        return this.a;
    }

    public void setAppicon(Drawable appicon) {
        this.a = appicon;
    }

    public String getAppname() {
        return this.b;
    }

    public void setAppname(String appname) {
        this.b = appname;
    }

    public String getAppversion() {
        return this.c;
    }

    public void setAppversion(String appversion) {
        this.c = appversion;
    }

    public String getAppversionCode() {
        return this.d == null ? "" : this.d;
    }

    public void setAppversionCode(String appversionCode) {
        this.d = appversionCode;
    }

    public String getApppackage() {
        return this.e;
    }

    public void setApppackage(String apppackage) {
        this.e = apppackage;
    }
}
