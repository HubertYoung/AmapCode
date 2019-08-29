package com.alipay.android.phone.inside.commonbiz.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.ex.ExceptionEnum;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.android.phone.inside.wallet.cons.Constants;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class ApkVerifyTool {
    private static final char[] a = HexUtils.HEX_CHARS.toCharArray();

    public static class PkgInfo {
        public boolean a = false;
        public List<String> b;
        public int c;
    }

    public static boolean a(Context context) {
        boolean z;
        PkgInfo a2 = a(context, "com.eg.android.AlipayGphone");
        if (a2.a) {
            int i = 0;
            while (true) {
                if (i >= a2.b.size()) {
                    z = true;
                    break;
                } else if (!a2.b.get(i).equals(Constants.ALIPAY_SHA256_FINGERPRINT)) {
                    z = false;
                    break;
                } else {
                    i++;
                }
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    private static PkgInfo a(Context context, String str) {
        LoggerFactory.f().b(ApkVerifyTool.class.getName(), (String) "getInsideSignInfo init");
        long currentTimeMillis = System.currentTimeMillis();
        PkgInfo pkgInfo = new PkgInfo();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 64);
            if (packageInfo != null) {
                pkgInfo.a = true;
            }
            pkgInfo.b = b(context, str);
            pkgInfo.c = packageInfo.versionCode;
        } catch (Throwable th) {
            LoggerFactory.f().e(ApkVerifyTool.class.getName(), th.getMessage());
            LoggerFactory.e().a(ExceptionEnum.EXCEPTION, (String) GlobalConstants.EXCEPTIONTYPE, (String) "ApkSignInfo", th);
        }
        TraceLogger f = LoggerFactory.f();
        String name = ApkVerifyTool.class.getName();
        StringBuilder sb = new StringBuilder("getInsideSignInfo cost:");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        f.e(name, sb.toString());
        return pkgInfo;
    }

    private static List<String> b(Context context, String str) {
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(str, 64).signatures;
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            ArrayList arrayList = new ArrayList();
            for (Signature byteArray : signatureArr) {
                byte[] digest = instance.digest(byteArray.toByteArray());
                StringBuffer stringBuffer = new StringBuffer((digest.length * 3) - 1);
                int length = digest.length - 1;
                for (int i = 0; i <= length; i++) {
                    byte b = digest[i];
                    stringBuffer.append(a[(b & 240) >> 4]);
                    stringBuffer.append(a[b & 15]);
                    if (i < length) {
                        stringBuffer.append(':');
                    }
                }
                arrayList.add(stringBuffer.toString());
            }
            return arrayList;
        } catch (Exception e) {
            LoggerFactory.f().c(ApkVerifyTool.class.getName(), (Throwable) e);
            return null;
        }
    }
}
