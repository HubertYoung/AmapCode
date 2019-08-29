package com.uc.webview.export.cyclone;

import java.util.HashMap;

@Constant
/* compiled from: ProGuard */
public class UCHashMap<K, V> extends HashMap<K, V> {
    public UCHashMap<K, V> set(K k, V v) {
        put(k, v);
        return this;
    }
}
