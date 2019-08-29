package com.alipay.mobile.beehive.compositeui.wheelview.widget;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.support.annotation.CallSuper;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public abstract class BottomPopup<V extends View> implements OnKeyListener {
    public static final int MATCH_PARENT = -1;
    public static final int WRAP_CONTENT = -2;
    protected Activity activity;
    private int height = 0;
    private boolean isFillScreen = false;
    private boolean isHalfScreen = false;
    private Popup popup;
    protected int screenHeightPixels;
    protected int screenWidthPixels;
    private int width = 0;

    /* access modifiers changed from: protected */
    public abstract V makeContentView();

    public BottomPopup(Activity activity2) {
        this.activity = activity2;
        DisplayMetrics displayMetrics = activity2.getResources().getDisplayMetrics();
        this.screenWidthPixels = displayMetrics.widthPixels;
        this.screenHeightPixels = displayMetrics.heightPixels;
        this.popup = new Popup(activity2);
        this.popup.setOnKeyListener(this);
    }

    private void onShowPrepare() {
        setContentViewBefore();
        View view = makeContentView();
        this.popup.setContentView(view);
        setContentViewAfter(view);
        LoggerFactory.getTraceLogger().debug("compositeui", "do something before popup show");
        if (this.width == 0 && this.height == 0) {
            this.width = this.screenWidthPixels;
            if (this.isFillScreen) {
                this.height = -1;
            } else if (this.isHalfScreen) {
                this.height = this.screenHeightPixels / 2;
            } else {
                this.height = -2;
            }
        }
        this.popup.setSize(this.width, this.height);
    }

    public void setFillScreen(boolean fillScreen) {
        this.isFillScreen = fillScreen;
    }

    public void setHalfScreen(boolean halfScreen) {
        this.isHalfScreen = halfScreen;
    }

    /* access modifiers changed from: protected */
    public void setContentViewBefore() {
    }

    /* access modifiers changed from: protected */
    public void setContentViewAfter(V contentView) {
    }

    public void setAnimationStyle(@StyleRes int animRes) {
        this.popup.setAnimationStyle(animRes);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.popup.setOnDismissListener(onDismissListener);
        LoggerFactory.getTraceLogger().debug("compositeui", "popup setOnDismissListener");
    }

    public void setSize(int width2, int height2) {
        this.width = width2;
        this.height = height2;
    }

    public void setWidth(int width2) {
        this.width = width2;
    }

    public void setHeight(int height2) {
        this.height = height2;
    }

    public boolean isShowing() {
        return this.popup.isShowing();
    }

    @CallSuper
    public void show() {
        onShowPrepare();
        this.popup.show();
        LoggerFactory.getTraceLogger().debug("compositeui", "popup show");
    }

    public void dismiss() {
        this.popup.dismiss();
        LoggerFactory.getTraceLogger().debug("compositeui", "popup dismiss");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public final boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (event.getAction() == 0) {
            return onKeyDown(keyCode, event);
        }
        return false;
    }

    public Window getWindow() {
        return this.popup.getWindow();
    }

    public ViewGroup getRootView() {
        return this.popup.getRootView();
    }
}
