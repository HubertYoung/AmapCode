package com.alipay.mobile.tinyappcustom.h5plugin;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.j;
import java.util.Map;

public class AuthResult {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;

    public AuthResult(Map<String, String> rawResult, boolean removeBrackets) {
        String[] split;
        if (rawResult != null) {
            for (String key : rawResult.keySet()) {
                if (TextUtils.equals(key, j.a)) {
                    this.a = rawResult.get(key);
                } else if (TextUtils.equals(key, "result")) {
                    this.b = rawResult.get(key);
                } else if (TextUtils.equals(key, "memo")) {
                    this.c = rawResult.get(key);
                }
            }
            for (String value : this.b.split("&")) {
                if (value.startsWith("alipay_open_id")) {
                    this.f = a(a((String) "alipay_open_id=", value), removeBrackets);
                } else if (value.startsWith("auth_code")) {
                    this.e = a(a((String) "auth_code=", value), removeBrackets);
                } else if (value.startsWith("result_code")) {
                    this.d = a(a((String) "result_code=", value), removeBrackets);
                }
            }
        }
    }

    private static String a(String str, boolean remove) {
        if (!remove || TextUtils.isEmpty(str)) {
            return str;
        }
        if (str.startsWith("\"")) {
            str = str.replaceFirst("\"", "");
        }
        if (str.endsWith("\"")) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    public String toString() {
        return "resultStatus={" + this.a + "};memo={" + this.c + "};result={" + this.b + h.d;
    }

    private static String a(String header, String data) {
        return data.substring(header.length(), data.length());
    }

    public String getResultStatus() {
        return this.a;
    }

    public String getMemo() {
        return this.c;
    }

    public String getResult() {
        return this.b;
    }

    public String getResultCode() {
        return this.d;
    }

    public String getAuthCode() {
        return this.e;
    }

    public String getAlipayOpenId() {
        return this.f;
    }
}
