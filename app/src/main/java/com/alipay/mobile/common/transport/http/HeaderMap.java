package com.alipay.mobile.common.transport.http;

import android.text.TextUtils;
import java.lang.String;
import java.util.LinkedHashMap;

public class HeaderMap<K extends String, V> extends LinkedHashMap<K, V> {
    public HeaderMap() {
    }

    public HeaderMap(int capacity) {
        super(capacity);
    }

    public V get(Object key) {
        return super.get(getKeyString((String) key));
    }

    public boolean containsKey(Object key) {
        return super.containsKey(getKeyString((String) key));
    }

    public V put(K key, V value) {
        return super.put(getKeyString(key), value);
    }

    /* access modifiers changed from: protected */
    public String getKeyString(String key) {
        String keyStr = key;
        if (TextUtils.isEmpty(key)) {
            keyStr = "";
        }
        return keyStr.toLowerCase();
    }
}
