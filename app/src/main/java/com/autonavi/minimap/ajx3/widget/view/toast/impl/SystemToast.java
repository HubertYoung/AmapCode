package com.autonavi.minimap.ajx3.widget.view.toast.impl;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.autonavi.minimap.ajx3.widget.view.toast.IToast;
import com.autonavi.minimap.ajx3.widget.view.toast.SafelyHandlerWrapper;
import com.autonavi.widget.ui.BalloonLayout;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class SystemToast extends AbstractToast {
    /* access modifiers changed from: private */
    public static Toast sToast;
    private boolean isNeedTimer;
    private boolean isShowing;
    private ScheduledFuture mShower;
    private ScheduledThreadPoolExecutor mTimer = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread("Thread-AjxSystemToast-Timer");
        }
    });

    public /* bridge */ /* synthetic */ void cancel() {
        super.cancel();
    }

    public /* bridge */ /* synthetic */ int getAnimation() {
        return super.getAnimation();
    }

    public /* bridge */ /* synthetic */ long getDuration() {
        return super.getDuration();
    }

    public /* bridge */ /* synthetic */ int getGravity() {
        return super.getGravity();
    }

    public /* bridge */ /* synthetic */ int getPriority() {
        return super.getPriority();
    }

    public /* bridge */ /* synthetic */ long getTimestamp() {
        return super.getTimestamp();
    }

    public /* bridge */ /* synthetic */ View getView() {
        return super.getView();
    }

    public /* bridge */ /* synthetic */ int getXOffset() {
        return super.getXOffset();
    }

    public /* bridge */ /* synthetic */ int getYOffset() {
        return super.getYOffset();
    }

    public /* bridge */ /* synthetic */ IToast setAboveKeyboard(boolean z) {
        return super.setAboveKeyboard(z);
    }

    public /* bridge */ /* synthetic */ IToast setAnimation(int i) {
        return super.setAnimation(i);
    }

    public /* bridge */ /* synthetic */ IToast setDuration(long j) {
        return super.setDuration(j);
    }

    public /* bridge */ /* synthetic */ IToast setGravity(int i) {
        return super.setGravity(i);
    }

    public /* bridge */ /* synthetic */ IToast setGravity(int i, int i2, int i3) {
        return super.setGravity(i, i2, i3);
    }

    public /* bridge */ /* synthetic */ IToast setPriority(int i) {
        return super.setPriority(i);
    }

    public /* bridge */ /* synthetic */ IToast setText(String str) {
        return super.setText(str);
    }

    public /* bridge */ /* synthetic */ void setTimestamp(long j) {
        super.setTimestamp(j);
    }

    public /* bridge */ /* synthetic */ IToast setView(View view) {
        return super.setView(view);
    }

    public /* bridge */ /* synthetic */ void show() {
        super.show();
    }

    public SystemToast(Context context) {
        super(context);
        initToastInNeeded(context);
    }

    private static void initToastInNeeded(Context context) {
        if (sToast == null) {
            synchronized (SystemToast.class) {
                if (sToast == null) {
                    Toast makeText = Toast.makeText(context, "", 0);
                    sToast = makeText;
                    hookHandler(makeText);
                }
            }
        }
    }

    public boolean display() {
        this.isShowing = true;
        sToast.setGravity(getGravity(), getXOffset(), getYOffset());
        sToast.setDuration(calcDuration());
        sToast.setView(getView());
        setupToastAnim(sToast, getAnimation());
        if (this.isNeedTimer) {
            this.mShower = this.mTimer.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    SystemToast.sToast.show();
                }
            }, 0, BalloonLayout.DEFAULT_DISPLAY_DURATION, TimeUnit.MILLISECONDS);
        } else {
            sToast.show();
        }
        return true;
    }

    private int calcDuration() {
        boolean z = true;
        int i = getDuration() >= 2000 ? 1 : 0;
        if (getDuration() < 3500) {
            z = false;
        }
        this.isNeedTimer = z;
        return i;
    }

    public boolean hide() {
        this.isShowing = false;
        if (this.mShower != null) {
            this.mShower.cancel(true);
            this.mShower = null;
        }
        hideInternal();
        return true;
    }

    public boolean isShowing() {
        return this.isShowing;
    }

    private static void hookHandler(Toast toast) {
        if (VERSION.SDK_INT < 26) {
            try {
                Field declaredField = Toast.class.getDeclaredField("mTN");
                declaredField.setAccessible(true);
                Field declaredField2 = declaredField.getType().getDeclaredField("mHandler");
                declaredField2.setAccessible(true);
                Object obj = declaredField.get(toast);
                declaredField2.set(obj, new SafelyHandlerWrapper((Handler) declaredField2.get(obj)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void setupToastAnim(Toast toast, int i) {
        try {
            Object field = getField(toast, "mTN");
            if (field != null) {
                Object field2 = getField(field, "mParams");
                if (field2 != null && (field2 instanceof LayoutParams)) {
                    ((LayoutParams) field2).windowAnimations = i;
                }
            }
        } catch (Throwable unused) {
        }
    }

    private void hideInternal() {
        try {
            Object field = getField(sToast, "mTN");
            if (field != null) {
                invokeMethod(field, ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_HIDE, new Class[0], new Object[0]);
            }
        } catch (Throwable unused) {
        }
    }

    private static Object getField(Object obj, String str) throws Exception {
        Field declaredField = obj.getClass().getDeclaredField(str);
        if (declaredField == null) {
            return null;
        }
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    private static void invokeMethod(Object obj, String str, Class[] clsArr, Object... objArr) throws Exception {
        Method declaredMethod = obj.getClass().getDeclaredMethod(str, clsArr);
        if (declaredMethod != null) {
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(obj, objArr);
        }
    }
}
