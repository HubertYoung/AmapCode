package com.autonavi.minimap.ajx3.loader.picasso;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;

public class WebImageDiskCache extends AbstractDiskCache {
    private static final String CACHE_DIR = "ajx_web_resources";
    private static final float DEFAULT_CACHE_PERCENT = 1.0f;
    private static final int DEFAULT_FILE_COUNT = Integer.MAX_VALUE;

    /* access modifiers changed from: protected */
    public int calculateDiskCacheCount(File file) {
        return Integer.MAX_VALUE;
    }

    public WebImageDiskCache(Context context, bqa bqa) {
        super(context, bqa);
    }

    public WebImageDiskCache(Context context, bqa bqa, String str) {
        super(context, bqa, str);
    }

    /* access modifiers changed from: protected */
    public File createCacheDir(Context context) {
        File filesDir = context.getFilesDir();
        if (TextUtils.isEmpty(this.mCachePath)) {
            return new File(filesDir, CACHE_DIR);
        }
        return new File(filesDir, this.mCachePath);
    }

    /* access modifiers changed from: protected */
    public long calculateDiskCacheSize(File file) {
        return Utils.calculateDiskCacheSize(file, 1.0f);
    }
}
