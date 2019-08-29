package com.alipay.mobile.common.feedback;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.HashMap;
import java.util.Map;

public class FeedbackBizData {
    private String a;
    private String b;
    private Map<String, String> c;

    public FeedbackBizData() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public String getBizCode() {
        return this.a;
    }

    public void setBizCode(String bizCode) {
        this.a = bizCode;
    }

    public String getBizMsg() {
        return this.b;
    }

    public void setBizMsg(String bizMsg) {
        this.b = bizMsg;
    }

    public Map<String, String> getExtParams() {
        if (this.c == null) {
            this.c = new HashMap();
        }
        return this.c;
    }

    public void addExtParam(String key, String value) {
        if (this.c == null) {
            this.c = new HashMap();
        }
        this.c.put(key, value);
    }

    public void removeExtParam(String key) {
        if (this.c != null) {
            this.c.remove(key);
        }
    }
}
