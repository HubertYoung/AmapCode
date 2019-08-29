package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import android.os.Environment;
import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import java.io.File;

public class CleanStrategy {
    @JSONField(name = "cs")
    public long cleanCacheSize = ((long) (((double) this.maxCacheSize) * 0.3d));
    @JSONField(name = "ed")
    public long expiredDays = 90;
    @JSONField(name = "max")
    public long maxCacheSize = 104857600;
    @JSONField(name = "min")
    public long minProtectedSize = 20971520;
    @JSONField(name = "name")
    public String name = "";
    @JSONField(name = "path")
    public String path = "";

    public CleanStrategy() {
    }

    public CleanStrategy(String name2, String path2, long expiredDays2, long maxCacheSize2, long minProtectedSize2, long cleanCacheSize2) {
        this.name = name2;
        this.path = path2;
        this.expiredDays = expiredDays2;
        this.maxCacheSize = maxCacheSize2;
        this.minProtectedSize = minProtectedSize2;
        this.cleanCacheSize = cleanCacheSize2;
    }

    public String getRealPath() {
        if (!TextUtils.isEmpty(this.path) && !this.path.startsWith("/")) {
            this.path = new File(Environment.getExternalStorageDirectory(), this.path).getAbsolutePath();
        }
        return this.path;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.name.equals(((CleanStrategy) o).name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return "CleanStrategy{name='" + this.name + '\'' + ", path='" + this.path + '\'' + ", expiredDays=" + this.expiredDays + ", maxCacheSize=" + this.maxCacheSize + ", minProtectedSize=" + this.minProtectedSize + ", cleanCacheSize=" + this.cleanCacheSize + '}';
    }
}
