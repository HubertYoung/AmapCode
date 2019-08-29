package com.alipay.mobile.common.nbnet.api;

import android.util.Log;
import com.alipay.mobile.common.nbnet.api.download.NBNetDownloadClient;
import com.alipay.mobile.common.nbnet.api.upload.NBNetUploadClient;

public abstract class NBNetFactory {
    private static volatile NBNetFactory a = null;
    private static String b = "com.alipay.mobile.common.nbnet.biz.DefaultNBNetFactory";

    public abstract NBNetDownloadClient getDownloadClient();

    public abstract NBNetConfigHelper getNBNetConfigHelper();

    public abstract NBNetContext getNBNetContext();

    public abstract NBNetLog getNBNetLog();

    public abstract NBNetUploadClient getUploadClient();

    public abstract void init();

    public static NBNetFactory getDefault() {
        if (a != null) {
            return a;
        }
        synchronized (NBNetFactory.class) {
            try {
                NBNetFactory defaultNBNetFactoryObj = (NBNetFactory) Class.forName(b).newInstance();
                if (a == null) {
                    a = defaultNBNetFactoryObj;
                }
            } catch (Throwable e) {
                Log.e(NBNetFactory.class.getSimpleName(), "", e);
            }
        }
        return a;
    }
}
