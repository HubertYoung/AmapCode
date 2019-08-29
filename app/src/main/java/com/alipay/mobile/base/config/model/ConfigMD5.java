package com.alipay.mobile.base.config.model;

import android.text.TextUtils;
import java.io.Serializable;

public class ConfigMD5 implements Serializable, Comparable {
    public String name;
    public long updateTime;

    public int compareTo(Object o) {
        if (!(o instanceof ConfigMD5)) {
            return 0;
        }
        long l = ((ConfigMD5) o).updateTime - this.updateTime;
        if (l > 0) {
            return 1;
        }
        if (l != 0) {
            return -1;
        }
        return 0;
    }

    public int hashCode() {
        if (!TextUtils.isEmpty(this.name)) {
            return this.name.hashCode();
        }
        return super.hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof ConfigMD5) || o == null) {
            return super.equals(o);
        }
        if (!TextUtils.isEmpty(this.name)) {
            return this.name.equals(((ConfigMD5) o).name);
        }
        return TextUtils.isEmpty(((ConfigMD5) o).name);
    }
}
