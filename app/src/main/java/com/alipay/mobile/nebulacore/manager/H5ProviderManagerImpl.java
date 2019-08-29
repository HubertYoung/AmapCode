package com.alipay.mobile.nebulacore.manager;

import android.text.TextUtils;
import com.alipay.mobile.h5container.service.H5ConfigService;
import com.alipay.mobile.nebula.provider.H5ProviderManager;
import com.alipay.mobile.nebula.providermanager.H5BaseProviderInfo;
import com.alipay.mobile.nebula.providermanager.H5ProviderConfig;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class H5ProviderManagerImpl extends H5ProviderManager {
    public static final String TAG = "H5ProviderManagerImpl";
    private static H5ProviderManagerImpl a;
    private Map<String, H5ProviderConfig> b;
    private Map<String, Object> c = new ConcurrentHashMap();
    private boolean d;
    private H5ConfigService e;

    private H5ProviderManagerImpl() {
        b();
    }

    public static synchronized H5ProviderManagerImpl getInstance() {
        H5ProviderManagerImpl h5ProviderManagerImpl;
        synchronized (H5ProviderManagerImpl.class) {
            try {
                if (a == null) {
                    a = new H5ProviderManagerImpl();
                }
                h5ProviderManagerImpl = a;
            }
        }
        return h5ProviderManagerImpl;
    }

    private H5ConfigService a() {
        if (this.e == null) {
            this.e = (H5ConfigService) H5Utils.findServiceByInterface(H5ConfigService.class.getName());
        }
        return this.e;
    }

    private void b() {
        try {
            H5Log.d(TAG, "initProviderConfig");
            this.d = true;
            long time = System.currentTimeMillis();
            this.b = new ConcurrentHashMap();
            if (a() == null || a().getProviderInfoMap() == null) {
                this.b = H5BaseProviderInfo.providerInfoMap;
            } else {
                this.b = a().getProviderInfoMap();
                H5Log.d(TAG, "use getH5ConfigService().getProviderInfoMap()");
            }
            H5Log.d(TAG, "Nebula cost time initProviderConfig delta " + (System.currentTimeMillis() - time));
        } catch (Throwable t) {
            this.d = false;
            H5Log.e(TAG, "parse h5 external provider configuration exception.", t);
        }
    }

    private void a(String name, boolean useCache) {
        if (this.b != null) {
            long time = System.currentTimeMillis();
            if ((!useCache || !this.c.containsKey(name)) && this.b.containsKey(name)) {
                H5ProviderConfig info = this.b.get(name);
                String bundleName = info.bundleName;
                String clazzName = info.className;
                Class clazz = H5Environment.getClass(bundleName, clazzName);
                if (clazz != null) {
                    Object object = null;
                    try {
                        object = clazz.newInstance();
                        if (object != null) {
                            H5Log.d(TAG, "initialize ext provider " + clazzName);
                            this.c.put(name, object);
                            if (Nebula.DEBUG) {
                                long delta = System.currentTimeMillis() - time;
                                if (delta > 10) {
                                    H5Log.d(TAG, "Nebula cost time initProviderConfig delta " + delta + Token.SEPARATOR + name);
                                }
                            }
                        }
                    } catch (Throwable t) {
                        H5Log.e(TAG, "failed to initialize provider " + clazzName, t);
                    }
                }
            }
        }
    }

    public final synchronized void setProvider(String name, Object object) {
        if (object != null) {
            if (!TextUtils.isEmpty(name)) {
                H5Log.d(TAG, "setProvider:" + name + " object" + object);
                this.c.put(name, object);
            }
        }
    }

    public final synchronized <T> T getProvider(String name) {
        try {
        }
        return getProviderUseCache(name, true);
    }

    public final synchronized <T> T getProviderUseCache(String name, boolean useCache) {
        T t;
        if (!this.d) {
            b();
        }
        if (this.b != null && this.b.containsKey(name)) {
            a(name, useCache);
        }
        if (this.c.containsKey(name)) {
            t = this.c.get(name);
        } else {
            t = null;
        }
        return t;
    }

    public final synchronized boolean removeProvider(String name) {
        H5Log.d(TAG, "removeProvider:" + name);
        return this.c.remove(name) != null;
    }
}
