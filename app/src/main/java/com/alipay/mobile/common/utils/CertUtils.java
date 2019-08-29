package com.alipay.mobile.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;

public class CertUtils {
    private static final X500Principal a = new X500Principal("CN=dev,OU=Alipay,O=Alipay,L=HangZhou,ST=ZheJiang,C=CN");
    private static Boolean b = null;

    public CertUtils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static final boolean isDevSignPackage(Context context) {
        if (b != null) {
            return b.booleanValue();
        }
        try {
            PackageManager pm = context.getPackageManager();
            String packageName = context.getPackageName();
            ApplicationInfo appInfo = pm.getApplicationInfo(packageName, 128);
            if (appInfo == null || appInfo.metaData == null || appInfo.metaData.getBoolean("switch.certverifier", true)) {
                Signature[] signatures = pm.getPackageInfo(packageName, 64).signatures;
                if (signatures != null && signatures.length > 0) {
                    CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                    for (Signature byteArray : signatures) {
                        if (((X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(byteArray.toByteArray()))).getSubjectX500Principal().equals(a)) {
                            LogCatUtil.info("CertUtils", " SubjectX500Principal is DEBUG_DN.");
                            b = Boolean.valueOf(true);
                            return true;
                        }
                    }
                }
                b = Boolean.valueOf(false);
                return false;
            }
            LogCatUtil.info("CertUtils", " KEY_SWITCH_CERTVERIFIER is true.");
            b = Boolean.valueOf(true);
            return true;
        } catch (Throwable e) {
            LogCatUtil.error("CertUtils", " get signature error ", e);
        }
    }
}
