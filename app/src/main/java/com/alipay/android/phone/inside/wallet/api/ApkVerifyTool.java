package com.alipay.android.phone.inside.wallet.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.ex.ExceptionEnum;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.offlinecode.utils.HexUtils;
import com.alipay.android.phone.inside.wallet.cons.Constants;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class ApkVerifyTool {
    private static final String EXCEPTIONTYPE = "sdk";
    private static final char[] HEX_MAP = HexUtils.HEX_CHARS.toCharArray();

    public static class PkgInfo {
        public boolean isInstall = false;
        public List<String> sha256FingerPrint;
        public int version;
        public String versionName;
    }

    public static WalletStatusEnum checkAlipayStatus(Context context, int i) {
        if (i <= 0) {
            i = Constants.ALIPAY_SUPPORT_MIN_VRESION;
        }
        WalletStatusEnum walletStatusEnum = WalletStatusEnum.SUCCESS;
        PkgInfo alipayPkgInfo = getAlipayPkgInfo(context);
        if (alipayPkgInfo == null || !alipayPkgInfo.isInstall) {
            walletStatusEnum = WalletStatusEnum.NOT_INSTALL;
        } else if (!verifyAlipaySign(alipayPkgInfo)) {
            walletStatusEnum = WalletStatusEnum.SIGN_ERROR;
        } else if (alipayPkgInfo.version < i) {
            walletStatusEnum = WalletStatusEnum.VERSION_UNMATCH;
        }
        LoggerFactory.d().b("wallet", BehaviorType.EVENT, "CheckAlipayStatus|".concat(String.valueOf(walletStatusEnum)));
        LoggerFactory.f().b((String) "inside", "ApkVerifyTool::checkAlipayStatus > WalletStatusEnum:".concat(String.valueOf(walletStatusEnum)));
        return walletStatusEnum;
    }

    public static boolean isAlipayAppInstalled(Context context) {
        PkgInfo apkSignInfo = getApkSignInfo(context, "com.eg.android.AlipayGphone");
        return apkSignInfo.isInstall && verifyAlipaySign(apkSignInfo);
    }

    public static PkgInfo getAlipayPkgInfo(Context context) {
        return getApkSignInfo(context, "com.eg.android.AlipayGphone");
    }

    public static boolean verifyAlipaySign(PkgInfo pkgInfo) {
        for (int i = 0; i < pkgInfo.sha256FingerPrint.size(); i++) {
            if (!pkgInfo.sha256FingerPrint.get(i).equals(Constants.ALIPAY_SHA256_FINGERPRINT)) {
                return false;
            }
        }
        return true;
    }

    public static PkgInfo getApkSignInfo(Context context, String str) {
        LoggerFactory.f().b((String) "inside", (String) "getInsideSignInfo init");
        long currentTimeMillis = System.currentTimeMillis();
        PkgInfo pkgInfo = new PkgInfo();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(str, 64);
            if (packageInfo != null) {
                pkgInfo.isInstall = true;
                pkgInfo.sha256FingerPrint = getPkgSHA256FingerPrint(context, str);
                pkgInfo.version = packageInfo.versionCode;
                pkgInfo.versionName = packageInfo.versionName;
            }
        } catch (Throwable th) {
            LoggerFactory.f().e("inside", th.getMessage());
            LoggerFactory.e().a(ExceptionEnum.EXCEPTION, (String) "sdk", (String) "ApkSignInfo", th);
        }
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("getInsideSignInfo cost:");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        f.e("inside", sb.toString());
        return pkgInfo;
    }

    public static List<String> getPkgSHA256FingerPrint(Context context, String str) {
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
                    stringBuffer.append(HEX_MAP[(b & 240) >> 4]);
                    stringBuffer.append(HEX_MAP[b & 15]);
                    if (i < length) {
                        stringBuffer.append(':');
                    }
                }
                arrayList.add(stringBuffer.toString());
            }
            return arrayList;
        } catch (Exception e) {
            LoggerFactory.f().c((String) "inside", (Throwable) e);
            return null;
        }
    }
}
