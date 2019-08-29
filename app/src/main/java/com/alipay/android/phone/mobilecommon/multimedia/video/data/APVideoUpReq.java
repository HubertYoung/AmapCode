package com.alipay.android.phone.mobilecommon.multimedia.video.data;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APConstants;
import com.alipay.android.phone.mobilecommon.multimedia.video.APVideoUploadCallback;

public class APVideoUpReq {
    public static final int TYPE_ALBUM_VIDEO = 1;
    public static final int TYPE_SHORT_VIDEO = 0;
    private String bizType;
    private String businessId = APConstants.DEFAULT_BUSINESS;
    private APVideoUploadCallback callback;
    private String localId;
    private boolean sync;
    private int timeout = -1;
    private int videoType = 1;

    public APVideoUpReq() {
    }

    public APVideoUpReq(String localId2) {
        this.localId = localId2;
    }

    public String getLocalId() {
        return this.localId;
    }

    public APVideoUpReq setLocalId(String localId2) {
        this.localId = localId2;
        return this;
    }

    public APVideoUploadCallback getCallback() {
        return this.callback;
    }

    public APVideoUpReq setCallback(APVideoUploadCallback callback2) {
        this.callback = callback2;
        return this;
    }

    public boolean isSync() {
        return this.sync;
    }

    public APVideoUpReq setSync(boolean sync2) {
        this.sync = sync2;
        return this;
    }

    public String getBusinessId() {
        return this.businessId;
    }

    public APVideoUpReq setBusinessId(String businessId2) {
        if (!TextUtils.isEmpty(businessId2)) {
            this.businessId = businessId2;
        }
        return this;
    }

    public String getBizType() {
        if (TextUtils.isEmpty(this.bizType)) {
            this.bizType = this.businessId;
        }
        return this.bizType;
    }

    public APVideoUpReq setBizType(String bizType2) {
        this.bizType = bizType2;
        return this;
    }

    public int getVideoType() {
        return this.videoType;
    }

    public APVideoUpReq setVideoType(int videoType2) {
        this.videoType = videoType2;
        return this;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setTimeout(int timeout2) {
        this.timeout = timeout2;
    }

    public String toString() {
        return "APVideoUpReq{localId='" + this.localId + '\'' + ", callback=" + this.callback + ", sync=" + this.sync + ", businessId='" + this.businessId + '\'' + ", bizType='" + this.bizType + '\'' + ", videoType=" + this.videoType + '}';
    }
}
