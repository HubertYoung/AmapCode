package com.alipay.mobile.framework.service.common;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.service.CommonService;

public abstract class GenericMemCacheService extends CommonService {
    public abstract Object get(String str, String str2);

    public abstract void put(String str, String str2, String str3, Object obj);

    public abstract Object remove(String str);

    public abstract void removeByGroup(String str);

    public abstract void removeByOwner(String str);

    public GenericMemCacheService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
