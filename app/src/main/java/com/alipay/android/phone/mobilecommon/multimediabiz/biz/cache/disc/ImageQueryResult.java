package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;

public class ImageQueryResult<T extends APImageQuery> extends APImageQueryResult<T> {
    public BitmapCacheKey cacheKey;

    public String toString() {
        return "ImageQueryResult{success=" + this.success + ", query=" + this.query + ", path='" + this.path + '\'' + ", width=" + this.width + ", height=" + this.height + ", cacheKey=" + this.cacheKey + '}';
    }
}
