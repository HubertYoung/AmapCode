package com.alipay.mobile.antui.picker;

import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.CallSuper;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUDialog;

public class AUPopup {
    private FrameLayout contentLayout;
    private AUDialog dialog;

    public AUPopup(Context context) {
        init(context);
    }

    private void init(Context context) {
        this.contentLayout = new FrameLayout(context);
        this.contentLayout.setLayoutParams(new LayoutParams(-2, -2));
        this.contentLayout.setFocusable(true);
        this.contentLayout.setFocusableInTouchMode(true);
        this.dialog = new AUDialog(context);
        this.dialog.setCanceledOnTouchOutside(true);
        this.dialog.setCancelable(true);
        Window window = this.dialog.getWindow();
        window.setGravity(17);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.requestFeature(1);
        window.setContentView(this.contentLayout);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = -1;
        window.setAttributes(layoutParams);
        View decorView = window.getDecorView();
        if (decorView != null) {
            int padding = context.getResources().getDimensionPixelSize(R.dimen.AU_SPACE10);
            decorView.setPadding(padding, 0, padding, 0);
        }
    }

    public Context getContext() {
        return this.contentLayout.getContext();
    }

    public void setAnimationStyle(@StyleRes int animRes) {
        this.dialog.getWindow().setWindowAnimations(animRes);
    }

    public boolean isShowing() {
        return this.dialog.isShowing();
    }

    @CallSuper
    public void show() {
        this.dialog.show();
    }

    @CallSuper
    public void dismiss() {
        this.dialog.dismiss();
    }

    public void setContentView(View view) {
        this.contentLayout.removeAllViews();
        this.contentLayout.addView(view);
    }

    public View getContentView() {
        return this.contentLayout.getChildAt(0);
    }

    public void setSize(int width, int height) {
        Log.d("compositeui", String.format("will set popup width/height to: %s/%s", new Object[]{Integer.valueOf(width), Integer.valueOf(height)}));
        LayoutParams params = this.contentLayout.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(width, height);
        } else {
            params.width = width;
            params.height = height;
        }
        this.contentLayout.setLayoutParams(params);
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.dialog.setOnDismissListener(onDismissListener);
    }

    public void setOnKeyListener(OnKeyListener onKeyListener) {
        this.dialog.setOnKeyListener(onKeyListener);
    }

    public Window getWindow() {
        return this.dialog.getWindow();
    }

    public ViewGroup getRootView() {
        return this.contentLayout;
    }
}
