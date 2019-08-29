package com.alibaba.analytics.core;

import android.content.Context;

public class ClientVariables {
    public static final ClientVariables s_instance = new ClientVariables();
    private volatile String appKey;
    private volatile Context mContext = null;
    private volatile boolean mIs1010AutoTrackClosed = false;
    private volatile boolean mIsAliyunOSPlatform = false;
    private volatile String mOutsideTTID = null;
    private String mTimestamp;

    private ClientVariables() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        this.mTimestamp = sb.toString();
    }

    public void setToAliyunOSPlatform() {
        this.mIsAliyunOSPlatform = true;
    }

    public boolean isAliyunOSPlatform() {
        return this.mIsAliyunOSPlatform;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String str) {
        this.appKey = str;
    }

    public static ClientVariables getInstance() {
        return s_instance;
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void set1010AutoTrackClose() {
        this.mIs1010AutoTrackClosed = true;
    }

    public boolean is1010AutoTrackClosed() {
        return this.mIs1010AutoTrackClosed;
    }

    public void setOutsideTTID(String str) {
        this.mOutsideTTID = str;
    }

    public String getOutsideTTID() {
        return this.mOutsideTTID;
    }

    public String getTimestamp() {
        return this.mTimestamp;
    }
}
