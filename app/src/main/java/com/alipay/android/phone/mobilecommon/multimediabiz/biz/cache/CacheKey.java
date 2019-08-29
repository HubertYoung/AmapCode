package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.HashMap;
import java.util.Map;

public class CacheKey {
    protected static final String TAG = "CacheKey";
    private Map<String, Object> a;
    @JSONField(deserialize = false, serialize = false)
    public String key;

    public CacheKey() {
    }

    public CacheKey(String key2) {
        this.key = key2;
    }

    public String complexCacheKey() {
        return this.key;
    }

    public <T> T getExtra(String key2) {
        if (this.a != null) {
            return this.a.get(key2);
        }
        return null;
    }

    public void putExtra(String key2, Object extra) {
        if (this.a == null) {
            synchronized (this) {
                if (this.a == null) {
                    this.a = new HashMap();
                }
            }
        }
        this.a.put(key2, extra);
    }
}
