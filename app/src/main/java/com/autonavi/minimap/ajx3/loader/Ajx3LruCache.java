package com.autonavi.minimap.ajx3.loader;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.image.ImageCache.Image;
import com.autonavi.minimap.ajx3.loader.picasso.Cache;
import com.autonavi.minimap.ajx3.util.LogHelper;

public class Ajx3LruCache implements Cache<Image> {
    private bjv mCache;

    public Ajx3LruCache(@NonNull bjv bjv) {
        this.mCache = bjv;
    }

    public Image get(String str) {
        Bitmap a = this.mCache.a(str);
        if (a != null) {
            StringBuilder sb = new StringBuilder("Ajx3LruCache: 命中 url = ");
            sb.append(str);
            sb.append(", bitmap = ");
            sb.append(a);
            LogHelper.d(sb.toString());
            Image image = new Image();
            image.bitmap = a;
            image.realUrl = str;
            image.gif = null;
            return image;
        }
        LogHelper.d("Ajx3LruCache: 未命中 url = ".concat(String.valueOf(str)));
        return null;
    }

    public void set(String str, Image image) {
        if (image != null) {
            Bitmap bitmap = image.bitmap;
            if (bitmap != null) {
                StringBuilder sb = new StringBuilder("Ajx3LruCache: 保存 url = ");
                sb.append(str);
                sb.append(" bitmap = ");
                sb.append(image.bitmap);
                LogHelper.d(sb.toString());
                this.mCache.a(str, bitmap);
            }
        }
    }

    public int size() {
        return this.mCache.a();
    }

    public int maxSize() {
        return this.mCache.b();
    }

    public void clear() {
        this.mCache.c();
    }

    public void clearKeyUri(String str) {
        this.mCache.b(str);
    }
}
