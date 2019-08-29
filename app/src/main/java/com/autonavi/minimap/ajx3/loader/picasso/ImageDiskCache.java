package com.autonavi.minimap.ajx3.loader.picasso;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;

public class ImageDiskCache extends AbstractDiskCache {
    private static final String CACHE_DIR;
    private static final float DEFAULT_CACHE_PERCENT = 0.1f;
    private static final int DEFAULT_FILE_COUNT = 2000;
    private static final String HTTP_AUTONAVI = "autonavi";
    private static final String HTTP_CACHE = "httpcache";
    private static final String IMAGE = "imageajx";

    /* access modifiers changed from: protected */
    public int calculateDiskCacheCount(File file) {
        return 2000;
    }

    static {
        StringBuilder sb = new StringBuilder("autonavi");
        sb.append(File.separator);
        sb.append(HTTP_CACHE);
        sb.append(File.separator);
        sb.append(IMAGE);
        CACHE_DIR = sb.toString();
    }

    public ImageDiskCache(Context context, bqa bqa) {
        super(context, bqa);
    }

    public ImageDiskCache(Context context, bqa bqa, String str) {
        super(context, bqa, str);
    }

    /* access modifiers changed from: protected */
    public long calculateDiskCacheSize(File file) {
        return Utils.calculateDiskCacheSize(file, DEFAULT_CACHE_PERCENT);
    }

    /* access modifiers changed from: protected */
    public File createCacheDir(Context context) {
        String a = bpw.a(context);
        if (TextUtils.isEmpty(a)) {
            return null;
        }
        if (TextUtils.isEmpty(this.mCachePath)) {
            return new File(a, CACHE_DIR);
        }
        return new File(a, this.mCachePath);
    }
}
