package com.sina.weibo.sdk.statistic;

import java.util.Map;

class AdEventLog extends EventLog {
    private String mAid = "";
    private String mAppkey = "";
    private Map<String, String> mExtend;
    private String mImei;

    public String getmAid() {
        return this.mAid;
    }

    public void setmAid(String str) {
        this.mAid = str;
    }

    public Map<String, String> getmExtend() {
        return this.mExtend;
    }

    public void setmExtend(Map<String, String> map) {
        this.mExtend = map;
    }

    public String getmAppkey() {
        return this.mAppkey;
    }

    public void setmAppkey(String str) {
        this.mAppkey = str;
    }

    public String getmImei() {
        return this.mImei;
    }

    public void setmImei(String str) {
        this.mImei = str;
    }
}
