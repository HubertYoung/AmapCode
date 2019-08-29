package com.alipay.mobile.securitycommon.aliauth;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.account.adapter.LogAdapter;
import com.alipay.mobile.securitycommon.aliauth.util.LogUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AliAuthCache {
    private static AliAuthCache a;
    private UrlParser b;
    private Map<String, CacheWrap> c = new HashMap();

    class CacheWrap {
        public Map<String, AliAuthResult> caches = new HashMap();

        CacheWrap() {
        }
    }

    private AliAuthCache() {
    }

    public static AliAuthCache getInstance() {
        if (a == null) {
            synchronized (AliAuthCache.class) {
                if (a == null) {
                    a = new AliAuthCache();
                }
            }
        }
        return a;
    }

    public void setUrlParser(UrlParser urlParser) {
        this.b = urlParser;
    }

    public void addCache(Bundle bundle, List<String> list, AliAuthResult aliAuthResult) {
        if (list != null && !list.isEmpty()) {
            String string = bundle.getString("userId");
            if (!TextUtils.isEmpty(string)) {
                CacheWrap cacheWrap = this.c.get(string);
                if (cacheWrap == null) {
                    cacheWrap = new CacheWrap();
                }
                for (String put : list) {
                    cacheWrap.caches.put(put, aliAuthResult);
                }
                this.c.put(string, cacheWrap);
            }
        }
    }

    public AliAuthResult getCache(Bundle bundle) {
        String string = bundle.getString("targetUrl");
        String string2 = bundle.getString("userId");
        if (!TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string)) {
            CacheWrap cacheWrap = this.c.get(string2);
            String a2 = a(cacheWrap, this.b.getHost(string));
            if (!TextUtils.isEmpty(a2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(string2);
                sb.append("命中缓存:");
                sb.append(a2);
                LogAdapter.a((String) "AliAuthCache", sb.toString());
                return cacheWrap.caches.get(a2);
            }
        }
        return null;
    }

    public void resetCache(Bundle bundle) {
        LogUtil.log("AliAuthCache", "强制删除所有免登");
        String string = bundle.getString("userId");
        if (!TextUtils.isEmpty(string)) {
            this.c.remove(string);
        }
    }

    public void removeCache(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.c.remove(str);
        }
        LogUtil.log("AliAuthCache", "删除缓存成功:".concat(String.valueOf(str)));
    }

    private static String a(CacheWrap cacheWrap, String str) {
        if (!(cacheWrap == null || cacheWrap.caches == null || cacheWrap.caches.isEmpty())) {
            for (String next : cacheWrap.caches.keySet()) {
                if (str.endsWith(next)) {
                    return next;
                }
            }
        }
        return "";
    }

    public boolean validCache(AliAuthResult aliAuthResult) {
        if (aliAuthResult == null) {
            return false;
        }
        boolean z = System.currentTimeMillis() - aliAuthResult.timeStamp < 2700000;
        LogUtil.log("AliAuthCache", String.format("免登缓存是否超过期：%s", new Object[]{Boolean.valueOf(z)}));
        return z;
    }

    public AliAuthResult getValidCacheResult(Bundle bundle) {
        String string = bundle.getString("targetUrl");
        String string2 = bundle.getString("userId");
        if (!TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string)) {
            CacheWrap cacheWrap = this.c.get(string2);
            String a2 = a(cacheWrap, this.b.getHost(string));
            if (!TextUtils.isEmpty(a2)) {
                LogUtil.log("AliAuthCache", String.format("命中%s的缓存:%s", new Object[]{string2, a2}));
                AliAuthResult aliAuthResult = cacheWrap.caches.get(a2);
                if (validCache(aliAuthResult)) {
                    return aliAuthResult;
                }
                cacheWrap.caches.remove(a2);
            }
        }
        return null;
    }
}
