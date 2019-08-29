package com.autonavi.minimap.ajx3.widget.view.toast.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewManager;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.autonavi.minimap.ajx3.widget.view.toast.IToast;
import java.lang.ref.WeakReference;

public class ActivityToast extends AbstractToast {
    private boolean isShowing;
    private WeakReference<Activity> mActivity;

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

    public ActivityToast(Context context) {
        super(context.getApplicationContext());
        this.mActivity = new WeakReference<>((Activity) context);
    }

    private LayoutParams getWMParams(Activity activity) {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.flags = 8;
        layoutParams.format = -3;
        layoutParams.type = getLayoutParamType();
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.windowAnimations = getAnimation();
        layoutParams.token = activity.getWindow().getDecorView().getWindowToken();
        layoutParams.x = getXOffset();
        layoutParams.y = getYOffset();
        layoutParams.gravity = getGravity();
        return layoutParams;
    }

    private int getLayoutParamType() {
        return (!"samsung".equalsIgnoreCase(Build.MANUFACTURER) || (!"SM-G9250".equalsIgnoreCase(Build.MODEL) && !"SM-G9200".equalsIgnoreCase(Build.MODEL))) ? 1003 : 1005;
    }

    private WindowManager getWMManager(Activity activity) {
        return activity.getWindowManager();
    }

    public boolean display() {
        Activity activity = (Activity) this.mActivity.get();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return false;
        }
        WindowManager wMManager = getWMManager(activity);
        if (wMManager == null) {
            return false;
        }
        View view = getView();
        if (view == null) {
            return false;
        }
        ViewParent parent = view.getParent();
        if (parent instanceof ViewManager) {
            ((ViewManager) parent).removeView(view);
        }
        try {
            wMManager.addView(view, getWMParams(activity));
            this.isShowing = true;
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean hide() {
        Activity activity = (Activity) this.mActivity.get();
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || !this.isShowing) {
            return false;
        }
        WindowManager wMManager = getWMManager(activity);
        if (wMManager != null) {
            try {
                wMManager.removeView(getView());
            } catch (Exception unused) {
            }
        }
        this.isShowing = false;
        return true;
    }

    public boolean isShowing() {
        return this.isShowing;
    }
}
