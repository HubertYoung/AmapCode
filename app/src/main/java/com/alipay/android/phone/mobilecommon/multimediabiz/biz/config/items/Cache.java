package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;
import com.autonavi.ae.bl.map.IMapPageConstant;

public class Cache {
    @JSONField(name = "crincc")
    public float commRationInCommCache = 0.167f;
    @JSONField(name = "ctq")
    public int commUseTQCache = 0;
    @JSONField(name = "fhm")
    public String forceHeapMem = ",asus_z00a,,23";
    @JSONField(name = "interval")
    public int interval = 1440;
    @JSONField(name = "largeMemSize")
    public int largeMemSize = 6291456;
    @JSONField(name = "maxccs")
    public long maxCommCacheSize = IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_NORMAL;
    @JSONField(name = "mricc")
    public float maxRatioInCommCache = 0.25f;
    @JSONField(name = "minccs")
    public long minCommCacheSize = 8388608;
    @JSONField(name = "switch")
    public int switcher = 1;
    @JSONField(name = "tq")
    public int useTQCache = 0;

    public boolean isUseTQCache() {
        return this.useTQCache == 1;
    }

    public boolean isUseCommonTQCache() {
        return this.commUseTQCache == 1;
    }

    public String toString() {
        return "Cache{switcher=" + this.switcher + ", interval=" + this.interval + ", largeMemSize=" + this.largeMemSize + ", useTQCache=" + this.useTQCache + ", commUseTQCache=" + this.commUseTQCache + ", maxCommCacheSize=" + this.maxCommCacheSize + ", minCommCacheSize=" + this.minCommCacheSize + ", maxRatioInCommCache=" + this.maxRatioInCommCache + ", commRationInCommCache=" + this.commRationInCommCache + ", forceHeapMem=" + this.forceHeapMem + '}';
    }
}
