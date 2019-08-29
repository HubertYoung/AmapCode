package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;

public class OriginalBitmapCacheKey extends BitmapCacheKey {
    public OriginalBitmapCacheKey(String key) {
        this(key, true);
    }

    public OriginalBitmapCacheKey(String key, boolean thumb) {
        super(key, Integer.MAX_VALUE, Integer.MAX_VALUE, CutScaleType.NONE.getValue(), (String) null, -1, (String) null);
        if (thumb) {
            this.tag = 256;
        }
    }
}
