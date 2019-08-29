package com.autonavi.miniapp.plugin.map.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider;
import java.util.HashMap;
import java.util.Map;

public class EmbedMapOfflineImageLoader {
    private static final int BMP_LIMIT_500KB = 512000;
    private static EmbedMapOfflineImageLoader sInstance = new EmbedMapOfflineImageLoader();
    private Map<String, AppImgCache> mCacheMap = new HashMap();

    static class AppImgCache {
        Map<String, Bitmap> imgCache;
        int refCount;

        private AppImgCache() {
            this.refCount = 0;
            this.imgCache = new HashMap();
        }
    }

    private EmbedMapOfflineImageLoader() {
    }

    public static EmbedMapOfflineImageLoader getInstance() {
        return sInstance;
    }

    public void onMapCreate(String str) {
        AppImgCache appImgCache = this.mCacheMap.get(str);
        if (appImgCache == null) {
            appImgCache = new AppImgCache();
            this.mCacheMap.put(str, appImgCache);
        }
        appImgCache.refCount++;
    }

    public void onMapDestroy(String str) {
        AppImgCache appImgCache = this.mCacheMap.get(str);
        if (appImgCache != null) {
            appImgCache.refCount--;
            if (appImgCache.refCount == 0) {
                this.mCacheMap.remove(str);
            }
        }
    }

    public Bitmap loadOfflineImage(H5Page h5Page, String str) {
        if (h5Page != null) {
            H5Session session = h5Page.getSession();
            if (session != null) {
                String id = session.getId();
                if (TextUtils.isEmpty(id)) {
                    return null;
                }
                AppImgCache appImgCache = this.mCacheMap.get(id);
                if (appImgCache == null) {
                    return null;
                }
                Bitmap bitmap = appImgCache.imgCache.get(str);
                if (bitmap != null) {
                    return bitmap;
                }
                H5ContentProvider webProvider = session.getWebProvider();
                if (webProvider != null) {
                    byte[] localResource = webProvider.getLocalResource(str);
                    if (localResource != null) {
                        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(localResource, 0, localResource.length);
                        if (decodeByteArray.getAllocationByteCount() < BMP_LIMIT_500KB) {
                            appImgCache.imgCache.put(str, decodeByteArray);
                        }
                        return decodeByteArray;
                    }
                }
            }
        }
        return null;
    }
}
