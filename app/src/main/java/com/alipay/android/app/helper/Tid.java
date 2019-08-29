package com.alipay.android.app.helper;

import android.text.TextUtils;

public class Tid {
    private String key;
    private String tid;
    private long time;

    public String getTid() {
        return this.tid;
    }

    public void setTid(String str) {
        this.tid = str;
    }

    public String getTidSeed() {
        return this.key;
    }

    public long getTimestamp() {
        return this.time;
    }

    public void setTidSeed(String str) {
        this.key = str;
    }

    public void setTimestamp(long j) {
        this.time = j;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(this.tid);
    }
}
