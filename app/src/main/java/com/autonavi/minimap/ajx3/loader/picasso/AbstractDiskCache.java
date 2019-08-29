package com.autonavi.minimap.ajx3.loader.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.support.annotation.NonNull;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.Log;
import com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Editor;
import com.autonavi.minimap.ajx3.loader.picasso.DiskLruCache.Snapshot;
import com.autonavi.minimap.ajx3.util.LogHelper;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public abstract class AbstractDiskCache {
    public static final int DEFAULT_BUFFER_SIZE = 32768;
    public static final CompressFormat DEFAULT_COMPRESS_FORMAT = CompressFormat.PNG;
    public static final int DEFAULT_COMPRESS_QUALITY = 100;
    protected int bufferSize = 32768;
    /* access modifiers changed from: private */
    public DiskLruCache cache;
    protected CompressFormat compressFormat = DEFAULT_COMPRESS_FORMAT;
    protected int compressQuality = 100;
    protected String mCachePath;

    /* access modifiers changed from: protected */
    public abstract int calculateDiskCacheCount(File file);

    /* access modifiers changed from: protected */
    public abstract long calculateDiskCacheSize(File file);

    /* access modifiers changed from: protected */
    public abstract File createCacheDir(Context context);

    public AbstractDiskCache(Context context, bqa bqa) {
        init(context, bqa);
    }

    public AbstractDiskCache(Context context, bqa bqa, String str) {
        this.mCachePath = str;
        init(context, bqa);
    }

    private void init(final Context context, bqa bqa) {
        AnonymousClass1 r0 = new Runnable() {
            public void run() {
                try {
                    File createCacheDir = AbstractDiskCache.this.createCacheDir(context);
                    if (createCacheDir == null) {
                        throw new IOException("AbstractDiskCache::init #create cache dir error!!!");
                    }
                    AbstractDiskCache.this.cache = DiskLruCache.open(createCacheDir, 1, 1, AbstractDiskCache.this.calculateDiskCacheSize(createCacheDir), AbstractDiskCache.this.calculateDiskCacheCount(createCacheDir));
                } catch (IOException e) {
                    LogHelper.d(Log.getStackTraceString(e));
                }
            }
        };
        if (bqa != null) {
            bqa.a(r0, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, "cache init");
        } else {
            new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
                public Thread newThread(@NonNull Runnable runnable) {
                    return new Thread(runnable, "AsyncTask#cache init");
                }
            }).submit(r0);
        }
    }

    public boolean save(String str, Bitmap bitmap) throws IOException {
        if (this.cache == null || this.cache.isFull()) {
            return false;
        }
        Editor edit = this.cache.edit(str);
        if (edit == null) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(edit.newOutputStream(0), this.bufferSize);
        try {
            boolean compress = bitmap.compress(this.compressFormat, this.compressQuality, bufferedOutputStream);
            if (compress) {
                edit.commit();
            } else {
                edit.abort();
            }
            return compress;
        } finally {
            Utils.closeSilently(bufferedOutputStream);
        }
    }

    public InputStream get(String str) {
        Snapshot snapshot;
        InputStream inputStream;
        if (this.cache == null) {
            return null;
        }
        try {
            snapshot = this.cache.get(str);
            if (snapshot == null) {
                inputStream = null;
            } else {
                try {
                    inputStream = snapshot.getInputStream(0);
                } catch (IOException unused) {
                } catch (Throwable th) {
                    th = th;
                    Utils.closeQuietly((Closeable) snapshot);
                    throw th;
                }
            }
            if (inputStream != null) {
                InputStream readFileInputStream = readFileInputStream(inputStream);
                Utils.closeQuietly((Closeable) snapshot);
                return readFileInputStream;
            }
        } catch (IOException unused2) {
            snapshot = null;
        } catch (Throwable th2) {
            th = th2;
            snapshot = null;
            Utils.closeQuietly((Closeable) snapshot);
            throw th;
        }
        Utils.closeQuietly((Closeable) snapshot);
        return null;
    }

    public synchronized void shutdown() {
        try {
            if (this.cache != null) {
                this.cache.close();
            }
        } catch (IOException unused) {
        }
    }

    public synchronized void delete() {
        if (this.cache != null) {
            try {
                this.cache.delete();
            } catch (IOException unused) {
            }
        }
    }

    public synchronized boolean clearCache() {
        if (this.cache == null) {
            return false;
        }
        try {
            this.cache.clearCache();
            return true;
        } catch (IOException unused) {
            return false;
        }
    }

    public long getSize() {
        if (this.cache != null) {
            return this.cache.size();
        }
        return 0;
    }

    private InputStream readFileInputStream(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            while (true) {
                try {
                    int read = inputStream.read(bArr);
                    if (-1 != read) {
                        byteArrayOutputStream2.write(bArr, 0, read);
                    } else {
                        Utils.closeQuietly((Closeable) byteArrayOutputStream2);
                        return new ByteArrayInputStream(byteArrayOutputStream2.toByteArray());
                    }
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    Utils.closeQuietly((Closeable) byteArrayOutputStream);
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            Utils.closeQuietly((Closeable) byteArrayOutputStream);
            throw th;
        }
    }
}
