package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.Arrays;

public class AutoCleanStrategy {
    @JSONField(name = "atacs")
    public int accessTimeAutoCleanSwitch = 1;
    @JSONField(name = "acs")
    public int autoCleanSwitch = 0;
    @JSONField(name = "aczcs")
    public int autoCleanZombieCacheSwitch = 0;
    @JSONField(name = "cbcs")
    public int cleanBizCacheSwitch = 1;
    @JSONField(name = "cbct")
    public int cleanBizCacheTime = 30;
    @JSONField(name = "cb")
    public String cleanBizs = "";
    @JSONField(name = "cop")
    public int cleanOldPeriod = 12;
    @JSONField(name = "cots")
    public int cleanOldTimeCacheSwitch = 1;
    @JSONField(name = "covs")
    public int cleanOldVersionSwitch = 1;
    @JSONField(name = "cs")
    public long cleanSize = 50;
    @JSONField(name = "cto")
    public int cleanTimeOut = 10000;
    @JSONField(name = "ctcs")
    public int cleanTypeCacheSwitch = 1;
    @JSONField(name = "ctct")
    public int cleanTypeCacheTime = 10;
    @JSONField(name = "ct")
    public int[] cleanTypes = null;
    @JSONField(name = "czcs")
    public int cleanZombieCacheSwitch = 1;
    @JSONField(name = "ecacs")
    public int expiredCacheAutoCleanSwitch = 1;
    @JSONField(name = "ect")
    public long expiredCacheTime = 30;
    @JSONField(name = "ecl")
    public int expiredCleanLimit = 100;
    @JSONField(name = "igs")
    public String ignoreSuffix = ".info";
    @JSONField(name = "mcs")
    public long maxCacheSize = 500;
    @JSONField(name = "zet")
    public int zombieExpiredTime = 1;

    public String[] getCleanBizs() {
        if (!TextUtils.isEmpty(this.cleanBizs)) {
            return this.cleanBizs.split("&");
        }
        return null;
    }

    public int[] getCleanTypes() {
        return this.cleanTypes;
    }

    public String toString() {
        return "AutoCleanStrategy{autoCleanSwitch=" + this.autoCleanSwitch + ", maxCacheSize=" + this.maxCacheSize + ", cleanSize=" + this.cleanSize + ", expiredCacheTime=" + this.expiredCacheTime + ", expiredCacheAutoCleanSwitch=" + this.expiredCacheAutoCleanSwitch + ", expiredCleanLimit=" + this.expiredCleanLimit + ", accessTimeAutoCleanSwitch=" + this.accessTimeAutoCleanSwitch + ", cleanOldVersionSwitch=" + this.cleanOldVersionSwitch + ", cleanOldTimeCacheSwitch=" + this.cleanOldTimeCacheSwitch + ", cleanZombieCacheSwitch=" + this.cleanZombieCacheSwitch + ", cleanOldPeriod=" + this.cleanOldPeriod + ", cleanTimeOut=" + this.cleanTimeOut + ", ignoreSuffix='" + this.ignoreSuffix + '\'' + ", cleanBizCacheSwitch=" + this.cleanBizCacheSwitch + ", cleanBizCacheTime=" + this.cleanBizCacheTime + ", cleanBizs='" + this.cleanBizs + '\'' + ", cleanTypeCacheSwitch=" + this.cleanTypeCacheSwitch + ", cleanTypeCacheTime=" + this.cleanTypeCacheTime + ", cleanTypes=" + Arrays.toString(this.cleanTypes) + ", autoCleanZombieCacheSwitch=" + this.autoCleanZombieCacheSwitch + ", zombieExpiredTime=" + this.zombieExpiredTime + '}';
    }
}
