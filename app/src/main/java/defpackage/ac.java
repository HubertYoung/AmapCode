package defpackage;

import anetwork.channel.cache.Cache;
import anetwork.channel.cache.Cache.Entry;
import com.taobao.alivfssdk.cache.AVFSCache;
import com.taobao.alivfssdk.cache.AVFSCacheConfig;
import com.taobao.alivfssdk.cache.AVFSCacheManager;
import com.taobao.alivfssdk.cache.IAVFSCache;
import com.taobao.alivfssdk.cache.IAVFSCache.OnAllObjectRemoveCallback;
import com.taobao.alivfssdk.cache.IAVFSCache.OnObjectRemoveCallback;
import com.taobao.alivfssdk.cache.IAVFSCache.OnObjectSetCallback;

/* renamed from: ac reason: default package */
/* compiled from: AVFSCacheImpl */
public final class ac implements Cache {
    private static boolean a = false;
    private static Object b;
    private static Object c;
    private static Object d;

    static {
        try {
            Class.forName("com.taobao.alivfssdk.cache.AVFSCacheManager");
            b = new OnObjectSetCallback() {
            };
            c = new OnObjectRemoveCallback() {
            };
            d = new OnAllObjectRemoveCallback() {
            };
        } catch (ClassNotFoundException unused) {
            cl.c("anet.AVFSCacheImpl", "no alivfs sdk!", null, new Object[0]);
        }
    }

    public static void a() {
        if (a) {
            AVFSCache cacheForModule = AVFSCacheManager.getInstance().cacheForModule("networksdk.httpcache");
            if (cacheForModule != null) {
                AVFSCacheConfig aVFSCacheConfig = new AVFSCacheConfig();
                aVFSCacheConfig.limitSize = Long.valueOf(5242880);
                aVFSCacheConfig.fileMemMaxSize = 1048576;
                cacheForModule.moduleConfig(aVFSCacheConfig);
            }
        }
    }

    public final Entry a(String str) {
        if (!a) {
            return null;
        }
        try {
            IAVFSCache b2 = b();
            if (b2 != null) {
                return (Entry) b2.objectForKey(cz.b(str));
            }
        } catch (Exception unused) {
            cl.e("anet.AVFSCacheImpl", "get cache failed", null, new Object[0]);
        }
        return null;
    }

    public final void a(String str, Entry entry) {
        if (a) {
            try {
                IAVFSCache b2 = b();
                if (b2 != null) {
                    b2.setObjectForKey(cz.b(str), entry, (OnObjectSetCallback) b);
                }
            } catch (Exception unused) {
                cl.e("anet.AVFSCacheImpl", "put cache failed", null, new Object[0]);
            }
        }
    }

    public final void b(String str) {
        if (a) {
            try {
                IAVFSCache b2 = b();
                if (b2 != null) {
                    b2.removeObjectForKey(cz.b(str), (OnObjectRemoveCallback) c);
                }
            } catch (Exception unused) {
                cl.e("anet.AVFSCacheImpl", "remove cache failed", null, new Object[0]);
            }
        }
    }

    private static IAVFSCache b() {
        AVFSCache cacheForModule = AVFSCacheManager.getInstance().cacheForModule("networksdk.httpcache");
        if (cacheForModule != null) {
            return cacheForModule.getFileCache();
        }
        return null;
    }
}
