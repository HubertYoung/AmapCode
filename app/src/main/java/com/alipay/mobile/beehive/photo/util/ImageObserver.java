package com.alipay.mobile.beehive.photo.util;

import android.content.Intent;
import android.net.Uri;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ImageObserver {
    private static ImageObserver INSTANCE = null;
    private static final String TAG = "ImageObserver";
    private volatile boolean isInit;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public List<a> mObservers;
    private HandlerThread mWorkThread = new HandlerThread("BeeImageObserverThread");

    class a extends FileObserver {
        String a;

        a(String path) {
            super(path, 136);
            this.a = path;
        }

        public final void onEvent(int event, String path) {
            if (128 == event || 8 == event) {
                ImageObserver.this.postScanImage(this.a + File.separator + path);
            }
        }
    }

    class b implements Runnable {
        Runnable a;

        public b(Runnable r) {
            this.a = r;
        }

        public final void run() {
            try {
                if (this.a != null) {
                    this.a.run();
                }
            } catch (Throwable tr) {
                PhotoLogger.debug(ImageObserver.TAG, "SafeRunnable exception:" + tr.getMessage());
            }
        }
    }

    private ImageObserver() {
        this.mWorkThread.start();
        this.mHandler = new Handler(this.mWorkThread.getLooper());
    }

    public static synchronized ImageObserver getInstance() {
        ImageObserver imageObserver;
        synchronized (ImageObserver.class) {
            try {
                if (INSTANCE == null) {
                    INSTANCE = new ImageObserver();
                }
                imageObserver = INSTANCE;
            }
        }
        return imageObserver;
    }

    public void active(final List paths) {
        if (!this.isInit) {
            this.isInit = true;
            if (!CloudConfig.isConfigToDisableImageObserver()) {
                safeRunAtWorkThread(new Runnable() {
                    public final void run() {
                        ImageObserver.this.doActive(paths);
                    }
                });
            }
        }
    }

    /* access modifiers changed from: private */
    public void doActive(List<Object> paths) {
        PhotoLogger.debug(TAG, "doActive### count = " + (paths == null ? 0 : paths.size()));
        this.mObservers = new LinkedList();
        if (paths != null && !paths.isEmpty()) {
            for (Object o : paths) {
                a observer = new a(String.valueOf(o));
                this.mObservers.add(observer);
                observer.startWatching();
            }
        }
    }

    public void destroy() {
        safeRunAtWorkThread(new Runnable() {
            public final void run() {
                if (ImageObserver.this.mObservers != null && !ImageObserver.this.mObservers.isEmpty()) {
                    for (a stopWatching : ImageObserver.this.mObservers) {
                        stopWatching.stopWatching();
                    }
                    ImageObserver.this.mObservers.clear();
                }
            }
        });
    }

    private void safeRunAtWorkThread(Runnable r) {
        safeRunAtWorkThread(r, 0);
    }

    private void safeRunAtWorkThread(Runnable r, int delay) {
        if (this.mHandler == null) {
            return;
        }
        if (delay > 0) {
            this.mHandler.postDelayed(new b(r), (long) delay);
        } else {
            this.mHandler.post(new b(r));
        }
    }

    /* access modifiers changed from: private */
    public void postScanImage(final String path) {
        safeRunAtWorkThread(new Runnable() {
            public final void run() {
                ImageObserver.this.doScanImage(path);
            }
        }, 3000);
    }

    /* access modifiers changed from: private */
    public void doScanImage(String path) {
        PhotoLogger.debug(TAG, "doScanImage### path = " + path);
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        if (!isValidImageType(suffix)) {
            PhotoLogger.debug(TAG, "Ignore invalid file suffix = " + suffix);
            return;
        }
        File f = new File(path);
        if (!f.exists() || !f.isFile() || !f.canRead()) {
            PhotoLogger.debug(TAG, "Ignore path = " + path);
            return;
        }
        PhotoLogger.debug(TAG, "Do scan path = " + path);
        LauncherApplicationAgent.getInstance().getApplicationContext().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(f)));
    }

    private boolean isValidImageType(String suffix) {
        return suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png") || suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("bmp") || suffix.equalsIgnoreCase("gif");
    }
}
