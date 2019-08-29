package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class DiskConf {
    private static final String TAG = "DiskConf";
    @JSONField(name = "acs")
    public AutoCleanStrategy autoCleanStrategy = new AutoCleanStrategy();
    @JSONField(name = "acv2s")
    public int autoCleanV2Switch = 1;
    @JSONField(name = "dt")
    public long backgroundDelayTime = 60;
    @JSONField(name = "bcd")
    public List<BusinessCleanStrategy> businessCleanStrategies = new ArrayList();
    @JSONField(name = "bcs")
    public int businessCleanSwitch = 0;
    @JSONField(name = "ci")
    public long cleanInterval = 30;
    @JSONField(name = "data")
    public List<CleanStrategy> cleanStrategyList = new ArrayList();
    @JSONField(name = "dcs")
    public int diskCacheSize = 524288000;
    @JSONField(name = "epwl")
    public String expiredPrefixWhiteList = null;
    @JSONField(name = "ewl")
    public String expiredWhiteList = "";
    @JSONField(name = "lies")
    public String largeImageExcludeSuffix = "mp4";
    @JSONField(name = "lis")
    public long largeImageSize = 52428800;
    private Pattern mLargeImageExludeSuffixPattern;
    @JSONField(name = "maxVideoCacheSize")
    public long maxVideoCacheSize = 20971520;
    @JSONField(name = "sltd")
    public int saveLocalToDiskCache = 0;
    @JSONField(name = "clids")
    public SingleCleanItem[] singleCleanItems;
    @JSONField(name = "scs")
    public int singleCleanSwitch = 0;
    @JSONField(name = "urlVideoSpace")
    public long urlVideoNeedSpace = 52428800;
    @JSONField(name = "parcel")
    public int useParcelCache = 1;
    @JSONField(name = "upmf")
    public float useParcelMemFactor = 0.2f;
    @JSONField(name = "videoSpace")
    public long videoNeedSpace = 52428800;

    public Pattern getLargeImageExcludeSuffixPattern() {
        if (this.mLargeImageExludeSuffixPattern == null && !TextUtils.isEmpty(this.largeImageExcludeSuffix)) {
            try {
                this.mLargeImageExludeSuffixPattern = Pattern.compile(this.largeImageExcludeSuffix, 2);
            } catch (Throwable t) {
                Logger.E((String) TAG, t, (String) "getLargeImageExcludeSuffixPattern", new Object[0]);
            }
        }
        return this.mLargeImageExludeSuffixPattern;
    }

    public boolean saveLocalToDiskCache() {
        return 1 == this.saveLocalToDiskCache;
    }

    public String toString() {
        return "DiskConf{videoNeedSpace=" + this.videoNeedSpace + ", cleanStrategyList=" + this.cleanStrategyList + ", cleanInterval=" + this.cleanInterval + ", backgroundDelayTime=" + this.backgroundDelayTime + ", autoCleanV2Switch=" + this.autoCleanV2Switch + ", businessCleanSwitch=" + this.businessCleanSwitch + ", businessCleanStrategies=" + this.businessCleanStrategies + ", autoCleanStrategy=" + this.autoCleanStrategy + ", useParcelCache=" + this.useParcelCache + ", largeImageSize=" + this.largeImageSize + ", largeImageExcludeSuffix=" + this.largeImageExcludeSuffix + ", expiredWhiteList=" + this.expiredWhiteList + ", expiredPrefixWhiteList=" + this.expiredPrefixWhiteList + ", diskCacheSize=" + this.diskCacheSize + ", useParcelMemFactor=" + this.useParcelMemFactor + ", singleCleanSwitch=" + this.singleCleanSwitch + ", singleCleanItems=" + Arrays.toString(this.singleCleanItems) + '}';
    }
}
