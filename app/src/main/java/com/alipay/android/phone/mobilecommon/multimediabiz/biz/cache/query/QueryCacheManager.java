package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query;

import android.support.v4.util.LruCache;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageOriginalQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc.ImageCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.QueryCacheConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.APImageWorker;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;

public class QueryCacheManager {
    private static QueryCacheManager c;
    private LruCache<String, ImgQueryCache> a;
    private LruCache<String, APFileQueryResult> b;
    private QueryCacheConf d;
    private ImageCacheManager e;

    private QueryCacheManager() {
        this.a = null;
        this.b = null;
        this.a = new LruCache<String, ImgQueryCache>() {
            /* access modifiers changed from: protected */
            public int sizeOf(String key, ImgQueryCache result) {
                return 1;
            }
        };
        this.b = new LruCache<String, APFileQueryResult>() {
            /* access modifiers changed from: protected */
            public int sizeOf(String key, APFileQueryResult result) {
                return 1;
            }
        };
    }

    public static QueryCacheManager getInstance() {
        if (c == null) {
            synchronized (QueryCacheManager.class) {
                try {
                    if (c == null) {
                        c = new QueryCacheManager();
                    }
                }
            }
        }
        return c;
    }

    public void put(String key, APImageQueryResult result) {
        if (!TextUtils.isEmpty(key) && result != null && getConf().getQueryImageSwitch()) {
            Logger.P("QueryCacheManager", "put image query key=" + key + ";result=" + result.path, new Object[0]);
            ImgQueryCache cache = new ImgQueryCache();
            cache.success = result.success;
            cache.path = result.path;
            cache.width = result.width;
            cache.height = result.height;
            this.a.put(key, cache);
        }
    }

    public void queryOriginalAndPut(String path) {
        if (!TextUtils.isEmpty(path) && getConf().getImgOriginalCacheSwitch()) {
            APImageOriginalQuery query = new APImageOriginalQuery(path);
            APImageQueryResult result = a().queryImageFor(query);
            if (result != null) {
                Logger.P("QueryCacheManager", "queryOriginalAndPut key=" + path + ";result=" + result.path, new Object[0]);
                ImgQueryCache cache = new ImgQueryCache();
                cache.success = result.success;
                cache.path = result.path;
                cache.width = result.width;
                cache.height = result.height;
                this.a.put(query.getQueryKey(), cache);
            }
        }
    }

    public ImgQueryCache getImageQuery(String key) {
        if (TextUtils.isEmpty(key) || !getConf().getQueryImageSwitch() || !AppUtils.inMainLooper()) {
            return null;
        }
        return (ImgQueryCache) this.a.get(key);
    }

    public void put(String key, APFileQueryResult result) {
        if (!TextUtils.isEmpty(key) && getConf().getQueryFileSwitch()) {
            Logger.P("QueryCacheManager", "put file query key=" + key + ";result=" + result.path, new Object[0]);
            this.b.put(key, result);
        }
    }

    public APFileQueryResult getFileQuery(String key) {
        if (TextUtils.isEmpty(key) || !getConf().getQueryFileSwitch() || !AppUtils.inMainLooper()) {
            return null;
        }
        return (APFileQueryResult) this.b.get(key);
    }

    public void trimToSize(int maxSize) {
        this.a.trimToSize(maxSize);
        this.b.trimToSize(maxSize);
    }

    public QueryCacheConf getConf() {
        this.d = ConfigManager.getInstance().getQueryCacheConf();
        return this.d;
    }

    private ImageCacheManager a() {
        if (this.e == null) {
            this.e = APImageWorker.getInstance(AppUtils.getApplicationContext()).getImageCacheManager();
        }
        return this.e;
    }
}
