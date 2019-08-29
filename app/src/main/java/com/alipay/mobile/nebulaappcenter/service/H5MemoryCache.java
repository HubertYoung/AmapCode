package com.alipay.mobile.nebulaappcenter.service;

import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider.OnConfigChangeListener;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class H5MemoryCache {
    private static H5MemoryCache a;
    private final Map<String, Map<String, AppInfo>> b = new H5LruCache();
    /* access modifiers changed from: private */
    public boolean c = false;

    private static class H5LruCache<K, V> extends LinkedHashMap<K, V> {
        private final int MAX_CACHE_SIZE = 10;

        H5LruCache() {
            super(((int) Math.ceil(13.333333333333334d)) + 1, 0.75f, true);
        }

        /* access modifiers changed from: protected */
        public boolean removeEldestEntry(Entry eldest) {
            return size() > this.MAX_CACHE_SIZE;
        }
    }

    public static synchronized H5MemoryCache a() {
        H5MemoryCache h5MemoryCache;
        synchronized (H5MemoryCache.class) {
            try {
                if (a == null) {
                    a = new H5MemoryCache();
                }
                h5MemoryCache = a;
            }
        }
        return h5MemoryCache;
    }

    private H5MemoryCache() {
        boolean z = false;
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            this.c = !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfigWithNotifyChange("h5_use_appCache", new OnConfigChangeListener() {
                public final void onChange(String s) {
                    H5MemoryCache.this.c = !BQCCameraParam.VALUE_NO.equalsIgnoreCase(s);
                }
            })) ? true : z;
        }
    }

    public final void a(AppInfo appInfo) {
        if (appInfo != null) {
            try {
                synchronized (this.b) {
                    if (this.b.get(appInfo.app_id) != null) {
                        this.b.get(appInfo.app_id).put(appInfo.version, appInfo);
                    } else {
                        Map map = new ConcurrentHashMap();
                        map.put(appInfo.version, appInfo);
                        this.b.put(appInfo.app_id, map);
                    }
                }
            } catch (Exception e) {
                H5Log.e((String) "H5MemoryCache", (Throwable) e);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        return null;
     */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.alipay.mobile.nebula.appcenter.model.AppInfo a(java.lang.String r8, java.lang.String r9) {
        /*
            r7 = this;
            r3 = 0
            boolean r2 = r7.c     // Catch:{ Throwable -> 0x0069 }
            if (r2 != 0) goto L_0x000e
            java.lang.String r2 = "H5MemoryCache"
            java.lang.String r4 = " can not use cache config is close"
            com.alipay.mobile.nebula.util.H5Log.e(r2, r4)     // Catch:{ Throwable -> 0x0069 }
            r2 = r3
        L_0x000d:
            return r2
        L_0x000e:
            boolean r2 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0069 }
            if (r2 == 0) goto L_0x0016
            r2 = r3
            goto L_0x000d
        L_0x0016:
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.alipay.mobile.nebula.appcenter.model.AppInfo>> r4 = r7.b     // Catch:{ Throwable -> 0x0069 }
            monitor-enter(r4)     // Catch:{ Throwable -> 0x0069 }
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.alipay.mobile.nebula.appcenter.model.AppInfo>> r2 = r7.b     // Catch:{ all -> 0x0066 }
            java.lang.Object r2 = r2.get(r8)     // Catch:{ all -> 0x0066 }
            if (r2 == 0) goto L_0x0071
            java.util.Map<java.lang.String, java.util.Map<java.lang.String, com.alipay.mobile.nebula.appcenter.model.AppInfo>> r2 = r7.b     // Catch:{ all -> 0x0066 }
            java.lang.Object r1 = r2.get(r8)     // Catch:{ all -> 0x0066 }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ all -> 0x0066 }
            if (r1 == 0) goto L_0x0031
            boolean r2 = r1.isEmpty()     // Catch:{ all -> 0x0066 }
            if (r2 == 0) goto L_0x0034
        L_0x0031:
            monitor-exit(r4)     // Catch:{ all -> 0x0066 }
            r2 = r3
            goto L_0x000d
        L_0x0034:
            boolean r2 = android.text.TextUtils.isEmpty(r9)     // Catch:{ all -> 0x0066 }
            if (r2 != 0) goto L_0x0071
            java.lang.String r2 = "*"
            boolean r2 = r9.contains(r2)     // Catch:{ all -> 0x0066 }
            if (r2 != 0) goto L_0x0071
            java.lang.String r2 = "H5MemoryCache"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0066 }
            r5.<init>()     // Catch:{ all -> 0x0066 }
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ all -> 0x0066 }
            java.lang.String r6 = " getNebulaAppInfo from H5MemoryCache "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0066 }
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch:{ all -> 0x0066 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0066 }
            com.alipay.mobile.nebula.util.H5Log.debug(r2, r5)     // Catch:{ all -> 0x0066 }
            java.lang.Object r2 = r1.get(r9)     // Catch:{ all -> 0x0066 }
            com.alipay.mobile.nebula.appcenter.model.AppInfo r2 = (com.alipay.mobile.nebula.appcenter.model.AppInfo) r2     // Catch:{ all -> 0x0066 }
            monitor-exit(r4)     // Catch:{ all -> 0x0066 }
            goto L_0x000d
        L_0x0066:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0066 }
            throw r2     // Catch:{ Throwable -> 0x0069 }
        L_0x0069:
            r0 = move-exception
            java.lang.String r2 = "H5MemoryCache"
            com.alipay.mobile.nebula.util.H5Log.e(r2, r0)
        L_0x006f:
            r2 = r3
            goto L_0x000d
        L_0x0071:
            monitor-exit(r4)     // Catch:{ all -> 0x0066 }
            goto L_0x006f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulaappcenter.service.H5MemoryCache.a(java.lang.String, java.lang.String):com.alipay.mobile.nebula.appcenter.model.AppInfo");
    }

    public final void b(String appId, String version) {
        try {
            synchronized (this.b) {
                Map map = this.b.get(appId);
                if (map != null && !map.isEmpty()) {
                    map.remove(version);
                }
            }
        } catch (Exception e) {
            H5Log.e((String) "H5MemoryCache", (Throwable) e);
        }
    }

    public final void b() {
        try {
            this.b.clear();
        } catch (Exception e) {
            H5Log.e((String) "H5MemoryCache", (Throwable) e);
        }
    }
}
