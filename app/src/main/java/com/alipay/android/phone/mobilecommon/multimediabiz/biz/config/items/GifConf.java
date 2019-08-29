package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class GifConf {
    @JSONField(name = "cks")
    public int cacheKeySwitch = 1;
    @JSONField(name = "cn")
    public int cacheNum = 6;
    @JSONField(name = "ct")
    public int cacheTime = 5;
    @JSONField(name = "cfs")
    public int checkFrameSwitch = 1;
    @JSONField(name = "dcth")
    public int decodeTimeThreshold = 500;
    @JSONField(name = "enlog")
    public int enableDebugLog = 0;
    @JSONField(name = "fu")
    public int forceUpload = 0;
    @JSONField(name = "ldcth")
    public int lowDeviceDecodeTimeThreshold = 600;
    @JSONField(name = "rubmp")
    public int reuseBitmap = 1;

    public boolean checkCacheKeySwitch() {
        return this.cacheKeySwitch == 1;
    }

    public boolean checkFrameSwitch() {
        return this.checkFrameSwitch == 1;
    }

    public boolean checkForceUpload() {
        return this.forceUpload == 1;
    }

    public String toString() {
        return "GifConf{decodeTimeThreshold=" + this.decodeTimeThreshold + "lowDeviceDecodeTimeThreshold=" + this.lowDeviceDecodeTimeThreshold + ", reuseBitmap=" + this.reuseBitmap + ", enableDebugLog=" + this.enableDebugLog + ", cacheNum=" + this.cacheNum + ", cacheTime=" + this.cacheTime + ", cacheKeySwitch=" + this.cacheKeySwitch + ", forceUpload=" + this.forceUpload + '}';
    }
}
