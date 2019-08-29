package com.alipay.mobile.framework.service.common;

import android.graphics.Bitmap;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.service.CommonService;

public abstract class ImageMemCacheService extends CommonService {
    public abstract Bitmap get(String str, String str2);

    public abstract long getMaxsize();

    public abstract long getSize();

    public abstract void put(String str, String str2, String str3, Bitmap bitmap);

    public abstract Bitmap remove(String str);

    public abstract void removeByGroup(String str);

    public ImageMemCacheService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
