package com.alipay.mobile.securitycommon.taobaobind.util;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.ali.auth.third.login.LoginConstants;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk;
import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.TokenResult;
import com.alipay.mobile.account.adapter.AppInfoAdapter;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;

public class TaobaoBindUtil {
    public static String getUmidToken(Context context) {
        TokenResult tokenResult = APSecuritySdk.getInstance(context).getTokenResult();
        return tokenResult != null ? tokenResult.umidToken : "tokenResult=null";
    }

    public static Bundle serialBundle(String str) {
        String[] split;
        Bundle bundle = new Bundle();
        if (str != null && str.length() > 0) {
            for (String str2 : str.split("&")) {
                int indexOf = str2.indexOf("=");
                if (indexOf > 0 && indexOf < str2.length() - 1) {
                    bundle.putString(str2.substring(0, indexOf), str2.substring(indexOf + 1));
                }
            }
        }
        return bundle;
    }

    public static String hideAccount(String str, String str2) {
        if (!LoginConstants.TAOBAO_LOGIN.equals(str2)) {
            return hideAccount(str);
        }
        if (str.contains(AUScreenAdaptTool.PREFIX_ID) || str.matches("\\d{1,}")) {
            return hideAccount(str);
        }
        return str;
    }

    public static String hideAccount(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.contains(AUScreenAdaptTool.PREFIX_ID)) {
            str = hideMail(str);
        } else if (str.matches("^(86){0,1}1\\d{10}$")) {
            str = hidePhone(str);
        } else if (str.matches("^(00){0,1}(852-){1}0{0,1}(?:\\d{8}|\\d{9}|\\d{13})$")) {
            str = hideHongkong(str);
        } else if (str.matches("^(00){0,1}(853-){1}6\\d{7}$")) {
            str = hideAomen(str);
        } else if (str.matches("^(00){0,1}(886-){1}0{0,1}[6,7,9](?:\\d{7}|\\d{8}|\\d{10})$")) {
            str = hideTaiwan(str);
        } else {
            boolean z = false;
            if (str.indexOf(45) > 0) {
                String[] split = str.split("-");
                if (split[0].matches("\\d{1,}") && split[1].matches("\\d{1,}")) {
                    z = true;
                }
            }
            if (z) {
                str = hideOversea(str);
            }
        }
        return str;
    }

    public static String hideMail(String str) {
        int indexOf = str.indexOf(64);
        String substring = str.substring(0, indexOf);
        String substring2 = str.substring(indexOf, str.length());
        if (substring.length() >= 3) {
            StringBuilder sb = new StringBuilder();
            sb.append(substring.substring(0, 3));
            sb.append("***");
            sb.append(substring2);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(substring);
        sb2.append("***");
        sb2.append(substring2);
        return sb2.toString();
    }

    public static String hidePhone(String str) {
        String substring = str.substring(0, 3);
        String substring2 = str.substring(str.length() - 4, str.length());
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append("****");
        sb.append(substring2);
        return sb.toString();
    }

    public static String hideHongkong(String str) {
        String substring = str.substring(0, 2);
        String substring2 = str.substring(str.length() - 2, str.length());
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append("****");
        sb.append(substring2);
        return sb.toString();
    }

    public static String hideAomen(String str) {
        String substring = str.substring(0, 2);
        String substring2 = str.substring(str.length() - 2, str.length());
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append("****");
        sb.append(substring2);
        return sb.toString();
    }

    public static String hideTaiwan(String str) {
        String substring = str.substring(0, 2);
        String substring2 = str.substring(str.length() - 3, str.length());
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append("****");
        sb.append(substring2);
        return sb.toString();
    }

    public static String hideOversea(String str) {
        int length = str.length() / 3;
        int i = str.length() % 3 != 0 ? length + 1 : length;
        int length2 = (str.length() - i) - length;
        String substring = str.substring(0, i);
        String str2 = "";
        for (int i2 = 0; i2 < length2; i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("*");
            str2 = sb.toString();
        }
        String substring2 = str.substring(i + length2);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(substring);
        sb2.append(str2);
        sb2.append(substring2);
        return sb2.toString();
    }

    public static String addCallbackToUrl(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(str.indexOf(63) >= 0 ? "&callback=" : "?callback=");
        sb.append("https://www.alipay.com/webviewbridge");
        sb.append("&walletVersion=");
        AppInfoAdapter.a();
        sb.append(AppInfoAdapter.b());
        return sb.toString();
    }

    public static String addSecurityCallbackToUrl(String str, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(str.indexOf(63) >= 0 ? "&callbackUrl=" : "?callbackUrl=");
        sb.append("https://www.alipay.com/webviewbridge");
        sb.append(str2);
        sb.append("&walletVersion=");
        AppInfoAdapter.a();
        sb.append(AppInfoAdapter.b());
        return sb.toString();
    }

    public static String addCallbackToUrl(String str, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(str.indexOf(63) >= 0 ? "&callback=" : "?callback=");
        sb.append("https://www.alipay.com/webviewbridge");
        sb.append(str2);
        sb.append("&walletVersion=");
        AppInfoAdapter.a();
        sb.append(AppInfoAdapter.b());
        return sb.toString();
    }

    public static String addAccountToUrl(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(str.indexOf(63) >= 0 ? "&loginIdHiddened=" : "?loginIdHiddened=");
        sb.append(hideAccount(str2, str3));
        sb.append("&loginId=");
        sb.append(str2);
        sb.append("&walletVersion=");
        AppInfoAdapter.a();
        sb.append(AppInfoAdapter.b());
        return sb.toString();
    }
}
