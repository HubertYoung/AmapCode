package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheLoader;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.task.ImageTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class ImageLoadEngine {
    public static final int TYPE_ASSET = 4;
    public static final int TYPE_DATA = 2;
    public static final int TYPE_DJANGO = 1;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_ORIGINAL = 3;
    private static volatile ImageLoadEngine c;
    private ExecutorService a;
    private ExecutorService b;
    private BitmapCacheLoader d;
    private final AtomicBoolean e = new AtomicBoolean(false);
    private final Object f = new Object();

    protected ImageLoadEngine() {
    }

    public static ImageLoadEngine getInstance() {
        if (c == null) {
            synchronized (ImageLoadEngine.class) {
                try {
                    if (c == null) {
                        ImageLoadEngine imageLoadEngine = new ImageLoadEngine();
                        c = imageLoadEngine;
                        imageLoadEngine.init();
                    }
                }
            }
        }
        return c;
    }

    public boolean hasInitCacheLoader() {
        return this.d != null;
    }

    public BitmapCacheLoader getCacheLoader() {
        if (this.d == null) {
            synchronized (this) {
                try {
                    if (this.d == null) {
                        this.d = new BitmapCacheLoader();
                    }
                }
            }
        }
        return this.d;
    }

    public synchronized void init() {
        this.a = TaskScheduleManager.get().localSingleExecutor();
        this.b = TaskScheduleManager.get().loadImageExecutor();
    }

    public Future submit(ImageTask task) {
        return this.b.submit(task);
    }

    public Future submit(ImageUpHandler upTask) {
        return this.a.submit(upTask);
    }

    public Future submit(Runnable runnable) {
        return this.b.submit(runnable);
    }

    public void resume() {
        this.e.set(false);
        synchronized (this.f) {
            this.f.notifyAll();
        }
    }

    public void pause() {
        this.e.set(true);
    }

    public AtomicBoolean getPause() {
        return this.e;
    }

    public Object getPauseLock() {
        return this.f;
    }
}
