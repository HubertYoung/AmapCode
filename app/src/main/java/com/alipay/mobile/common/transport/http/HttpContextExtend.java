package com.alipay.mobile.common.transport.http;

import android.content.Context;
import com.alipay.mobile.common.netsdkextdependapi.appinfo.AppInfoUtil;
import com.alipay.mobile.common.netsdkextdependapi.deviceinfo.DeviceInfoUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.io.Serializable;
import java.lang.reflect.Method;

public class HttpContextExtend {
    public static int MAX_HTTP_REQUEST_COUNT_PER_BATCH = 4;
    public static String TAG = HttpContextExtend.class.getSimpleName();
    private static HttpContextExtend e = null;
    private final Object a;
    private final Method b;
    private final Method c;
    private final Method d;

    private HttpContextExtend() {
        Class diskCacheClazz = getClass().getClassLoader().loadClass("com.alipay.mobile.common.cache.disk.lru.DefaultLruDiskCache");
        this.a = diskCacheClazz.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        diskCacheClazz.getMethod("open", new Class[0]).invoke(this.a, new Object[0]);
        this.b = diskCacheClazz.getMethod("putSerializable", new Class[]{String.class, String.class, String.class, Serializable.class, Long.TYPE, Long.TYPE, String.class});
        this.c = diskCacheClazz.getMethod("getSerializable", new Class[]{String.class, String.class});
        this.d = diskCacheClazz.getMethod("remove", new Class[]{String.class});
    }

    public static synchronized HttpContextExtend createInstance(Context context) {
        HttpContextExtend httpContextExtend;
        synchronized (HttpContextExtend.class) {
            if (e != null) {
                httpContextExtend = e;
            } else {
                synchronized (HttpContextExtend.class) {
                    if (e == null) {
                        try {
                            e = new HttpContextExtend();
                        } catch (Throwable e2) {
                            LogCatUtil.error(TAG, e2);
                            httpContextExtend = null;
                        }
                    }
                }
                httpContextExtend = e;
            }
        }
        return httpContextExtend;
    }

    public static synchronized HttpContextExtend getInstance() {
        HttpContextExtend httpContextExtend;
        synchronized (HttpContextExtend.class) {
            try {
                if (e == null) {
                    throw new IllegalStateException("HttpContextExtend.createInstance() need called before use");
                }
                httpContextExtend = e;
            }
        }
        return httpContextExtend;
    }

    public void putSerializable(String owner, String group, String url, Serializable serializable, long createTime, long period, String contentType) {
        try {
            this.b.invoke(this.a, new Object[]{owner, group, url, serializable, Long.valueOf(createTime), Long.valueOf(period), contentType});
        } catch (Exception e2) {
            LogCatUtil.error(TAG, (Throwable) e2);
        }
    }

    public Serializable getSerializable(String owner, String url) {
        try {
            return (Serializable) this.c.invoke(this.a, new Object[]{owner, url});
        } catch (Exception e2) {
            LogCatUtil.error(TAG, (Throwable) e2);
            return null;
        }
    }

    public void remove(String url) {
        try {
            this.d.invoke(this.a, new Object[]{url});
        } catch (Exception e2) {
            LogCatUtil.error(TAG, (Throwable) e2);
        }
    }

    public String getDid() {
        try {
            return DeviceInfoUtil.getDeviceId();
        } catch (Throwable e2) {
            LogCatUtil.error(TAG, e2);
            return "";
        }
    }

    public String getClientId() {
        return DeviceInfoUtil.getClientId();
    }

    public String getLatitude() {
        try {
            return DeviceInfoUtil.getLatitude();
        } catch (Exception e2) {
            LogCatUtil.error(TAG, (Throwable) e2);
            return null;
        }
    }

    public String getLongitude() {
        try {
            return DeviceInfoUtil.getLongitude();
        } catch (Exception e2) {
            LogCatUtil.error(TAG, (Throwable) e2);
            return null;
        }
    }

    public String getProductId() {
        try {
            return AppInfoUtil.getProductId();
        } catch (Throwable e2) {
            LogCatUtil.error(TAG, e2);
            return "";
        }
    }
}
