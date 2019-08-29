package com.alipay.mobile.framework.service.common;

import com.alipay.android.hackbyte.ClassVerifier;

public interface ImageLoaderListener extends com.alipay.mobile.common.image.ImageLoaderListener {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);
}
