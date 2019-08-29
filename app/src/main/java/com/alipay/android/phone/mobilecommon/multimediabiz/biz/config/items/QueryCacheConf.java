package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;

public class QueryCacheConf extends BaseConfig {
    @JSONField(name = "fcs")
    public int fileCacheSwitch = 1;
    @JSONField(name = "fs")
    public int fileSwitch = 0;
    @JSONField(name = "fts")
    public int fileTimeoutSwitch = 1;
    @JSONField(name = "ops")
    public int getOrgPathSwitch = 0;
    @JSONField(name = "is")
    public int imageSwitch = 0;
    @JSONField(name = "ics")
    public int imgCacheSwitch = 1;
    @JSONField(name = "ilts")
    public int imgLoadTimeoutSwitch = 1;
    @JSONField(name = "iocs")
    public int imgOriginalCacheSwitch = 1;
    @JSONField(name = "iots")
    public int imgOriginalTimeoutSwitch = 1;
    @JSONField(name = "its")
    public int imgTimeoutSwitch = 1;
    @JSONField(name = "ioto")
    public int queryTimeout = 500;

    public boolean getQueryFileSwitch() {
        return this.fileCacheSwitch == 1;
    }

    public boolean getQueryImageSwitch() {
        return this.imgCacheSwitch == 1;
    }

    public boolean getImgOriginalCacheSwitch() {
        return this.imgOriginalCacheSwitch == 1;
    }

    public boolean getFileSwitch() {
        return this.fileSwitch == 1 && AppUtils.inMainLooper();
    }

    public boolean getImageSwitch() {
        return this.imageSwitch == 1 && AppUtils.inMainLooper();
    }

    public boolean getOriginalImgPathSwitch() {
        return this.getOrgPathSwitch == 1 && AppUtils.inMainLooper();
    }

    public boolean getImageOriginalPathTimeoutSwith() {
        return this.imgOriginalTimeoutSwitch == 1 && AppUtils.inMainLooper();
    }

    public boolean getImagePathTimeoutSwith() {
        return this.imgTimeoutSwitch == 1 && AppUtils.inMainLooper();
    }

    public boolean getFileTimeoutSwitch() {
        return this.fileTimeoutSwitch == 1 && AppUtils.inMainLooper();
    }

    public boolean loadThumbImgTimeoutSwitch() {
        return this.imgLoadTimeoutSwitch == 1 && AppUtils.inMainLooper();
    }

    public void updateTime() {
        super.updateTime();
    }

    public void cloneValue(QueryCacheConf val) {
        if (val != null) {
            this.fileCacheSwitch = val.fileCacheSwitch;
            this.imgCacheSwitch = val.imgCacheSwitch;
            this.fileSwitch = val.fileSwitch;
            this.imageSwitch = val.imageSwitch;
        }
    }

    public String toString() {
        return "QueryCacheConf{fileCacheSwitch=" + this.fileCacheSwitch + ", imgCacheSwitch=" + this.imgCacheSwitch + ", imgOriginalCacheSwitch=" + this.imgOriginalCacheSwitch + ", imgOriginalTimeoutSwitch=" + this.imgOriginalTimeoutSwitch + ", imgTimeoutSwitch=" + this.imgTimeoutSwitch + ", imgLoadTimeoutSwitch=" + this.imgLoadTimeoutSwitch + ", fileTimeoutSwitch=" + this.fileTimeoutSwitch + ", fileSwitch=" + this.fileSwitch + ", imageSwitch=" + this.imageSwitch + ", getOrgPathSwitch=" + this.getOrgPathSwitch + ", queryTimeout=" + this.queryTimeout + '}';
    }
}
