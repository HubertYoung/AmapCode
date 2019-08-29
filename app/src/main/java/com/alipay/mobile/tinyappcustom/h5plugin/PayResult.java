package com.alipay.mobile.tinyappcustom.h5plugin;

import android.text.TextUtils;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.j;
import java.util.Map;

public class PayResult {
    private String a;
    private String b;
    private String c;

    public PayResult(Map<String, String> rawResult) {
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
        }
    }

    public String toString() {
        return "resultStatus={" + this.a + "};memo={" + this.c + "};result={" + this.b + h.d;
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
}
