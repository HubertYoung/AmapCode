package com.alipay.mobile.security.bio.service;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.exception.BioIllegalArgumentException;
import com.alipay.mobile.security.bio.exception.InvalidCallException;
import com.alipay.mobile.security.bio.module.MicroModule;
import com.alipay.mobile.security.bio.runtime.Runtime;
import com.alipay.mobile.security.bio.service.impl.BioServiceManagerImpl;
import com.alipay.mobile.security.bio.service.local.LocalService;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.workspace.Env;

public abstract class BioServiceManager {
    public static final String TAG = "BioServiceManager";
    protected static BioServiceManager a;
    private static Env c = Env.ONLINE;
    private final String b;
    public final Context mContext;

    public abstract void destroy();

    public abstract <T> T getBioService(Class<T> cls);

    public abstract <T> T getBioService(String str);

    public abstract int preLoad();

    public abstract <T extends BioService> T putBioService(String str, Class<T> cls);

    public abstract String startBioActivity(BioAppDescription bioAppDescription, MicroModule microModule);

    public static Env getEnv() {
        return c;
    }

    public static void setEnv(Env env) {
        if (c != env) {
            c = env;
            BioLog.i("setEnv: " + env.name);
        }
    }

    public BioServiceManager(Context context, String str) {
        if (context == null) {
            throw new BioIllegalArgumentException();
        }
        this.mContext = context.getApplicationContext();
        this.b = str;
    }

    @Deprecated
    public static synchronized void createInstance(Context context) {
        synchronized (BioServiceManager.class) {
            if (a == null) {
                BioLog.w((String) TAG, (String) "BioServiceManager.createInstance()");
                a = new BioServiceManagerImpl(context, null);
            } else {
                BioLog.w((String) TAG, (Throwable) new InvalidCallException("BioServiceManager.createInstance(Context) : null != sInstance"));
            }
        }
    }

    public static synchronized void createInstance(Context context, String str) {
        synchronized (BioServiceManager.class) {
            if (a == null) {
                BioLog.w((String) TAG, "BioServiceManager.createInstance() zimId=" + str);
                a = new BioServiceManagerImpl(context, str);
            } else if (TextUtils.isEmpty(a.b) || !TextUtils.equals(a.b, str)) {
                a.destroy();
                a = new BioServiceManagerImpl(context, str);
            } else {
                BioLog.w((String) TAG, "Reuse the BioServiceManager.sInstance for zimId=" + str);
            }
        }
    }

    public static BioServiceManager getCurrentInstance() {
        return a;
    }

    public static void destroyInstance() {
        if (a != null) {
            a.destroy();
            BioLog.w((String) TAG, "BioServiceManager.destroyInstance() zimId=" + a.b);
            a = null;
        }
    }

    public Context getBioApplicationContext() {
        return this.mContext;
    }

    public static <T extends LocalService> T getLocalService(Context context, Class<T> cls) {
        return getLocalService(context, Runtime.getBioServiceDescriptionByInterface(context, cls.getName()));
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0011  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T extends com.alipay.mobile.security.bio.service.local.LocalService> T getLocalService(android.content.Context r3, com.alipay.mobile.security.bio.service.BioServiceDescription r4) {
        /*
            r1 = 0
            if (r4 == 0) goto L_0x0020
            java.lang.Class r0 = r4.getClazz()
            if (r0 == 0) goto L_0x0020
            java.lang.Object r0 = r0.newInstance()     // Catch:{ Throwable -> 0x001c }
            com.alipay.mobile.security.bio.service.local.LocalService r0 = (com.alipay.mobile.security.bio.service.local.LocalService) r0     // Catch:{ Throwable -> 0x001c }
        L_0x000f:
            if (r0 == 0) goto L_0x001b
            android.content.Context r2 = r3.getApplicationContext()
            r0.setContext(r2)
            r0.create(r1)
        L_0x001b:
            return r0
        L_0x001c:
            r0 = move-exception
            com.alipay.mobile.security.bio.utils.BioLog.e(r0)
        L_0x0020:
            r0 = r1
            goto L_0x000f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.service.BioServiceManager.getLocalService(android.content.Context, com.alipay.mobile.security.bio.service.BioServiceDescription):com.alipay.mobile.security.bio.service.local.LocalService");
    }
}
