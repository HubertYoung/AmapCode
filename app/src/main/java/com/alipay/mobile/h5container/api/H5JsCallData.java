package com.alipay.mobile.h5container.api;

import android.text.TextUtils;

public class H5JsCallData {
    private String action;
    private long elapse;
    private String joMsg;

    public static class Builder {
        /* access modifiers changed from: private */
        public String action = "";
        /* access modifiers changed from: private */
        public long elapse = 0;
        /* access modifiers changed from: private */
        public String joMsg = "";

        public Builder setAction(String action2) {
            this.action = action2;
            return this;
        }

        public Builder setElapse(long elapse2) {
            this.elapse = elapse2;
            return this;
        }

        public Builder setJoMsg(String joMsg2) {
            this.joMsg = H5JsCallData.getAvailableJoMsg(joMsg2);
            return this;
        }

        public H5JsCallData build() {
            return new H5JsCallData(this);
        }
    }

    private H5JsCallData(Builder builder) {
        this.action = builder.action;
        this.elapse = builder.elapse;
        this.joMsg = builder.joMsg;
    }

    public String getAction() {
        return this.action;
    }

    public long getElapse() {
        return this.elapse;
    }

    public void setElapse(long elapse2) {
        this.elapse = elapse2;
    }

    public String getJoMsg() {
        return this.joMsg;
    }

    /* access modifiers changed from: private */
    public static String getAvailableJoMsg(String str) {
        if (TextUtils.isEmpty(str) || str.length() <= 500) {
            return str;
        }
        return str.substring(0, 500);
    }
}
