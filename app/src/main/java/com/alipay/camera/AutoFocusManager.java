package com.alipay.camera;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Handler;
import android.os.Message;
import com.alipay.mobile.bqcscanservice.Logger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.RejectedExecutionException;

public final class AutoFocusManager implements AutoFocusCallback {
    private static final Collection<String> a;
    private boolean b;
    /* access modifiers changed from: private */
    public boolean c;
    /* access modifiers changed from: private */
    public boolean d = true;
    private final boolean e;
    /* access modifiers changed from: private */
    public final Camera f;
    private AsyncTask<?, ?, ?> g;
    private AsyncTask<?, ?, ?> h;
    /* access modifiers changed from: private */
    public long i = 2000;
    private Handler j;

    final class AutoFocusTask extends AsyncTask<Object, Object, Object> {
        private AutoFocusTask() {
        }

        /* access modifiers changed from: protected */
        public final Object doInBackground(Object... voids) {
            try {
                Thread.sleep(AutoFocusManager.this.i);
            } catch (InterruptedException e) {
            }
            AutoFocusManager.this.b();
            return null;
        }
    }

    final class CheckAutoFocusTask extends AsyncTask<Object, Object, Object> {
        private CheckAutoFocusTask() {
        }

        /* access modifiers changed from: protected */
        public final Object doInBackground(Object... params) {
            try {
                Thread.sleep(AutoFocusManager.this.i);
            } catch (InterruptedException e) {
                Logger.e("AutoFocusManager", "InterruptedException");
            }
            if (AutoFocusManager.this.d) {
                try {
                    AutoFocusManager.this.f.cancelAutoFocus();
                } catch (RuntimeException re) {
                    Logger.e("AutoFocusManager", "exception while cancel autofocus:" + re.getMessage());
                }
                AutoFocusManager.this.c = false;
                AutoFocusManager.this.d = false;
                AutoFocusManager.this.b();
            }
            return null;
        }
    }

    static {
        ArrayList arrayList = new ArrayList(2);
        a = arrayList;
        arrayList.add("auto");
        a.add("macro");
    }

    public AutoFocusManager(Context context, Camera camera) {
        this.f = camera;
        this.j = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                AutoFocusManager.this.b();
            }
        };
        String currentFocusMode = camera.getParameters().getFocusMode();
        this.e = a.contains(currentFocusMode);
        Logger.i("AutoFocusManager", "Current focus mode '" + currentFocusMode + "'; use auto focus? " + this.e);
    }

    public final synchronized void onAutoFocus(boolean success, Camera theCamera) {
        this.c = false;
        this.d = false;
        a();
    }

    private synchronized void a() {
        if (!this.b && this.g == null) {
            AutoFocusTask newTask = new AutoFocusTask();
            try {
                newTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
                this.g = newTask;
            } catch (RejectedExecutionException ree) {
                Logger.e("AutoFocusManager", "Could not request auto focus:" + ree.getMessage());
            }
        }
        return;
    }

    public final synchronized void restart() {
        this.b = false;
        b();
    }

    /* access modifiers changed from: private */
    public synchronized void b() {
        if (this.e) {
            this.g = null;
            if (!this.b && !this.c) {
                try {
                    Logger.d("AutoFocusManager", "camera.autoFocus");
                    this.f.autoFocus(this);
                    this.c = true;
                    if (this.d) {
                        this.h = new CheckAutoFocusTask();
                        try {
                            this.h.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
                        } catch (RejectedExecutionException ree) {
                            Logger.e("AutoFocusManager", "CheckAutoFocusTask exception:" + ree.getMessage());
                        }
                    }
                } catch (RuntimeException e2) {
                    Logger.e("AutoFocusManager", "Unexpected exception while focusing");
                    a();
                }
            }
        }
        return;
    }

    private synchronized void c() {
        if (this.g != null) {
            if (this.g.getStatus() != Status.FINISHED) {
                this.g.cancel(true);
            }
            this.g = null;
        }
    }

    private synchronized void d() {
        if (this.h != null) {
            if (this.h.getStatus() != Status.FINISHED) {
                this.h.cancel(true);
            }
            this.h = null;
        }
    }

    public final synchronized void stop() {
        this.b = true;
        if (this.e) {
            c();
            d();
            try {
                this.f.cancelAutoFocus();
                this.c = false;
            } catch (RuntimeException re) {
                Logger.e("AutoFocusManager", "Unexpected exception while cancelling focusing:" + re.getMessage());
            }
        }
        return;
    }

    public final void startAutoFocus() {
        if (this.j != null) {
            this.j.sendEmptyMessage(0);
        }
    }

    public final void setAutoFocusInterval(long interval) {
        Logger.d("AutoFocusManager", "setAutoFocusInterval:" + interval);
        if (interval >= 0) {
            this.i = interval;
        }
    }
}
