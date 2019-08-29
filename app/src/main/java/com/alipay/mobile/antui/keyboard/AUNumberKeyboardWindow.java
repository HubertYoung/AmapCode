package com.alipay.mobile.antui.keyboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUPopupWindow;
import com.alipay.mobile.antui.keyboard.AUNumberKeyboardView.OnActionClickListener;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;

public class AUNumberKeyboardWindow {
    private AUNumberKeyboardView keyboardView;
    private AUPopupWindow keyboardWindow;
    private Context mContext;
    private WindowStateChangeListener windowStateChangeListener;

    public AUNumberKeyboardWindow(Context context) {
        this(context, 1);
    }

    public AUNumberKeyboardWindow(Context context, int style) {
        this(context, style, null);
    }

    public AUNumberKeyboardWindow(Context context, int style, OnActionClickListener listener) {
        this.mContext = context;
        this.keyboardView = new AUNumberKeyboardView(context, style, listener);
        this.keyboardWindow = new AUPopupWindow((View) this.keyboardView, -1, -2);
        this.keyboardWindow.setAnimationStyle(R.style.keyboard_anim_style);
        this.keyboardWindow.setFocusable(false);
    }

    public boolean isShowing() {
        if (this.keyboardWindow == null || !this.keyboardWindow.isShowing()) {
            return false;
        }
        return true;
    }

    public void show() {
        if (this.mContext != null && (this.mContext instanceof Activity) && !((Activity) this.mContext).isFinishing() && this.keyboardWindow != null) {
            View rootView = ((Activity) this.mContext).getWindow().getDecorView();
            if (rootView == null || rootView.getWindowToken() == null) {
                AuiLogger.info("AUNumberKeyboardWindow", "rootView is null :");
                return;
            }
            this.keyboardWindow.showAtLocation(rootView, 81, 0, 0);
            if (this.windowStateChangeListener != null) {
                this.windowStateChangeListener.stateChange(true, DensityUtil.dip2px(this.mContext, 222.0f));
            }
        }
    }

    public void dismiss() {
        if (this.mContext != null && (this.mContext instanceof Activity) && !((Activity) this.mContext).isFinishing() && this.keyboardWindow != null) {
            this.keyboardWindow.dismiss();
            if (this.windowStateChangeListener != null) {
                this.windowStateChangeListener.stateChange(false, 0);
            }
        }
    }

    public void setActionClickListener(OnActionClickListener listener) {
        this.keyboardView.setActionClickListener(listener);
    }

    public void setWindowStateChangeListener(WindowStateChangeListener windowStateChangeListener2) {
        this.windowStateChangeListener = windowStateChangeListener2;
    }

    public AUNumberKeyboardView getKeyboardView() {
        return this.keyboardView;
    }
}
