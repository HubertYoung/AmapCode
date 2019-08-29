package com.alipay.mobile.framework.service.common;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.service.CommonService;

public abstract class DiskCacheService extends CommonService {
    public abstract void close();

    public abstract byte[] get(String str, String str2);

    public abstract String getDirectory();

    public abstract long getMaxsize();

    public abstract long getSize();

    public abstract void open();

    public abstract void put(String str, String str2, String str3, byte[] bArr, long j, long j2, String str4);

    public abstract void remove(String str);

    public abstract void removeByGroup(String str);

    public abstract void removeByOwner(String str);

    public DiskCacheService() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }
}
