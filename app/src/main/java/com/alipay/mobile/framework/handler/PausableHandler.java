package com.alipay.mobile.framework.handler;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import com.alipay.mobile.framework.pipeline.PausableRunnable;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

public class PausableHandler extends Handler {
    private static WeakReference<PausableHandler>[] a = new WeakReference[30];
    private static AtomicInteger b = new AtomicInteger(0);
    private static Runnable c = new Runnable() {
        public final void run() {
        }
    };

    public PausableHandler() {
        a();
    }

    public PausableHandler(Callback callback) {
        super(callback);
        a();
    }

    public PausableHandler(Looper looper) {
        super(looper);
        a();
    }

    public PausableHandler(Looper looper, Callback callback) {
        super(looper, callback);
        a();
    }

    private void a() {
        if (b.get() < a.length) {
            a[b.getAndIncrement()] = new WeakReference<>(this);
        }
    }

    public static void pauseAll() {
        WeakReference[] weakReferenceArr;
        for (WeakReference prWR : a) {
            if (prWR != null) {
                PausableHandler handler = (PausableHandler) prWR.get();
                if (handler != null) {
                    handler.pause();
                }
            }
        }
    }

    public static void resumeAll() {
    }

    public final void pause() {
        postAtFrontOfQueue(new PausableRunnable(c));
    }

    public final void resume() {
    }
}
