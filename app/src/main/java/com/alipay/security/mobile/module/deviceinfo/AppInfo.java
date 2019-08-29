package com.alipay.security.mobile.module.deviceinfo;

import android.content.Context;
import android.content.pm.PackageManager;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class AppInfo {
    private static AppInfo appInfo = new AppInfo();

    private AppInfo() {
    }

    public static AppInfo getInstance() {
        return appInfo;
    }

    private byte[] getAppSignature(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 64).signatures[0].toByteArray();
        } catch (Exception unused) {
            return null;
        }
    }

    public byte[] getPublicKey(Context context, String str) {
        try {
            return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(getAppSignature(context, str)))).getPublicKey().getEncoded();
        } catch (Exception unused) {
            return null;
        }
    }

    public String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16).versionName;
        } catch (Exception unused) {
            return "0.0.0";
        }
    }

    public String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), 0)).toString();
        } catch (Exception unused) {
            r4 = "";
            return "";
        }
    }
}
