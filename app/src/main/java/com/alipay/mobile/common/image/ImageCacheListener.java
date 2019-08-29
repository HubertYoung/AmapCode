package com.alipay.mobile.common.image;

import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.transport.http.HttpUrlRequest;
import com.alipay.mobile.common.transport.http.HttpUrlResponse;

public interface ImageCacheListener {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    long getCachePeriod(HttpUrlRequest httpUrlRequest, HttpUrlResponse httpUrlResponse);
}
