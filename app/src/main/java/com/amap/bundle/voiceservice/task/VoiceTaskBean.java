package com.amap.bundle.voiceservice.task;

import com.autonavi.data.service.IResultCallBack;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class VoiceTaskBean {
    private IResultCallBack mCallback;
    private boolean mIsBlock;
    private String mMethodId;
    private String mPkgName;
    private String mRequestJson;
    private int mToken;

    public VoiceTaskBean() {
    }

    public VoiceTaskBean(String str, int i, String str2, String str3, IResultCallBack iResultCallBack, boolean z) {
        this.mPkgName = str;
        this.mToken = i;
        this.mMethodId = str2;
        this.mRequestJson = str3;
        this.mCallback = iResultCallBack;
        this.mIsBlock = z;
    }

    public int getToken() {
        return this.mToken;
    }

    public void setToken(int i) {
        this.mToken = i;
    }

    public String getMethodId() {
        return this.mMethodId;
    }

    public void setMethodId(String str) {
        this.mMethodId = str;
    }

    public IResultCallBack getCallback() {
        return this.mCallback;
    }

    public boolean isBlock() {
        return this.mIsBlock;
    }

    public void setIsBlock(boolean z) {
        this.mIsBlock = z;
    }

    public void setCallback(IResultCallBack iResultCallBack) {
        this.mCallback = iResultCallBack;
    }

    public void setRequestJson(String str) {
        this.mRequestJson = str;
    }

    public String getRequestJson() {
        return this.mRequestJson;
    }

    public void setPackageName(String str) {
        this.mPkgName = str;
    }

    public String getPkgName() {
        return this.mPkgName;
    }
}
