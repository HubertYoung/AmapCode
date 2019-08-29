package com.alipay.mobile.common.nbnet.api;

import android.content.Context;
import java.util.Map.Entry;
import java.util.Set;

public interface NBNetContext {
    public static final String RESERVED_PREFIX = "nbnet.";

    void addNBNetInterceptor(NBNetInterceptor nBNetInterceptor);

    void clear();

    void copyOverTo(NBNetContext nBNetContext);

    Set<Entry<String, Object>> entrySet();

    Context getApplicationContext();

    Object getAttribute(String str);

    Set<String> keySet();

    Object removeAttribute(String str);

    void setAttribute(String str, Object obj);

    int size();
}
