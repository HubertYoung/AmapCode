package com.alipay.diskcache;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.file.HttpFileUpMMTask;

public class StrategyConfig {
    public static final long KB = 1024;
    public static final long MB = 1048576;
    public static final long MINUTE = 60000;
    public static final int OFF = 0;
    public static final int ON = 1;
    @JSONField(name = "cfc")
    public int mClearFileCount = 10;
    @JSONField(name = "cs")
    public long mClearSize = HttpFileUpMMTask.BIG_FILE_SIZE_THRESHOLD;
    @JSONField(name = "ct")
    public long mClearThreshold = 20971520;
    @JSONField(name = "lst")
    public long mLowSpaceThreshold = 20971520;
    @JSONField(name = "lrus")
    public int mLruSwitch = 0;
    @JSONField(name = "mcs")
    public long mMaxCacheSize = 524288000;
    @JSONField(name = "sci")
    public long mSpaceCheckInterval = TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL;

    public String toString() {
        return "DiskCacheConf{mLruSwitch=" + this.mLruSwitch + ", mSpaceCheckInterval=" + this.mSpaceCheckInterval + ", mClearThreshold=" + this.mClearThreshold + ", mClearSize=" + this.mClearSize + ", mClearFileCount=" + this.mClearFileCount + ", mMaxCacheSize=" + this.mMaxCacheSize + ", mLowSpaceThreshold=" + this.mLowSpaceThreshold + '}';
    }
}
