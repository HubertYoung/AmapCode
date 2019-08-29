package com.alibaba.baichuan.android.trade.utils.cache;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.adapter.security.AlibcSecurityGuard;
import java.util.ArrayList;
import java.util.List;

public class CacheUtils {
    public static final String SHARE_PREFERENCE_EXPIRE = "SHARE_PREFERENCE_EXPIRE";
    public static final String SHARE_PREFERENCE_NAME = "ali_bc_auth_cache";

    public static class CacheInfo {
        public String key;
        public String obj;

        public CacheInfo() {
        }

        public CacheInfo(String str, String str2) {
            this.key = str;
            this.obj = str2;
        }
    }

    public static void asyncPutCache(String str, String str2) {
        if (!TextUtils.isEmpty(str) && str2 != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new CacheInfo(str, str2));
            asyncPutCacheList(arrayList);
        }
    }

    public static void asyncPutCacheList(List list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (!(list.get(i) == null || TextUtils.isEmpty(((CacheInfo) list.get(i)).key) || ((CacheInfo) list.get(i)).obj == null)) {
                    putCache(((CacheInfo) list.get(i)).key, ((CacheInfo) list.get(i)).obj);
                }
            }
        }
    }

    public static void asyncPutEncryptedCache(String str, String str2) {
        asyncPutCache(str, AlibcSecurityGuard.getInstance().dynamicEncrypt(str2));
    }

    public static synchronized void cleanCache(String str) {
        synchronized (CacheUtils.class) {
            if (!TextUtils.isEmpty(str)) {
                Editor edit = AlibcContext.context.getSharedPreferences(SHARE_PREFERENCE_NAME, 0).edit();
                edit.remove(str);
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(SHARE_PREFERENCE_EXPIRE);
                edit.remove(sb.toString());
                edit.apply();
            }
        }
    }

    public static synchronized String getCache(String str) {
        synchronized (CacheUtils.class) {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            SharedPreferences sharedPreferences = AlibcContext.context.getSharedPreferences(SHARE_PREFERENCE_NAME, 0);
            String string = sharedPreferences.getString(str, "");
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(SHARE_PREFERENCE_EXPIRE);
            Long.valueOf(sharedPreferences.getLong(sb.toString(), 0));
            return string;
        }
    }

    public static String getDecryptedCache(String str) {
        String cache = getCache(str);
        if (!TextUtils.isEmpty(cache)) {
            return AlibcSecurityGuard.getInstance().dynamicDecrypt(cache);
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0028, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void putCache(java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.Class<com.alibaba.baichuan.android.trade.utils.cache.CacheUtils> r0 = com.alibaba.baichuan.android.trade.utils.cache.CacheUtils.class
            monitor-enter(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r4)     // Catch:{ all -> 0x0029 }
            if (r1 != 0) goto L_0x0027
            if (r5 != 0) goto L_0x000c
            goto L_0x0027
        L_0x000c:
            android.content.Context r1 = com.alibaba.baichuan.android.trade.AlibcContext.context     // Catch:{ Exception -> 0x0021 }
            java.lang.String r2 = "ali_bc_auth_cache"
            r3 = 0
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)     // Catch:{ Exception -> 0x0021 }
            android.content.SharedPreferences$Editor r1 = r1.edit()     // Catch:{ Exception -> 0x0021 }
            r1.putString(r4, r5)     // Catch:{ Exception -> 0x0021 }
            r1.apply()     // Catch:{ Exception -> 0x0021 }
            monitor-exit(r0)
            return
        L_0x0021:
            r4 = move-exception
            r4.printStackTrace()     // Catch:{ all -> 0x0029 }
            monitor-exit(r0)
            return
        L_0x0027:
            monitor-exit(r0)
            return
        L_0x0029:
            r4 = move-exception
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.trade.utils.cache.CacheUtils.putCache(java.lang.String, java.lang.String):void");
    }
}
