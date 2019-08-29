package com.alipay.mobile.antui.basic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import com.alipay.mobile.antui.utils.AuiLogger;

public class AUDialog extends Dialog implements AUViewInterface {
    private Boolean isAP;
    private Context mContext;

    public AUDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public AUDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    public AUDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public void dismiss() {
        if (Looper.myLooper() != Looper.getMainLooper() && (this.mContext instanceof Activity) && (((Activity) this.mContext).isFinishing() || ((Activity) this.mContext).isDestroyed())) {
            AuiLogger.info("AUDialog", "Activity is finish");
        } else if (isShowing()) {
            try {
                AuiLogger.info("AUDialog", "isShowing() == true, dismiss");
                super.dismiss();
            } catch (IllegalArgumentException e) {
                AuiLogger.error("AUDialog", "IllegalArgumentException: e" + e);
            }
        }
    }

    public void show() {
        if (!isLiving()) {
            AuiLogger.info("AUDialog", "Activity is finish");
            return;
        }
        AuiLogger.info("AUDialog", "show:" + this.mContext);
        super.show();
    }

    private boolean isLiving() {
        if (this.mContext == null) {
            return false;
        }
        if (this.mContext instanceof Activity) {
            Activity activity = (Activity) this.mContext;
            if (activity.isFinishing() || activity.isDestroyed()) {
                AuiLogger.info("AUDialog", "Activity is finish,name=" + activity.getClass().getName());
                return false;
            }
        }
        return true;
    }

    public Boolean isAP() {
        return this.isAP;
    }

    public void setAP(Boolean isAP2) {
        this.isAP = isAP2;
    }

    /* access modifiers changed from: protected */
    public void setWindowMaxWidth(int padding) {
        LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = -1;
        getWindow().setAttributes(layoutParams);
        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            decorView.setPadding(padding, 0, padding, 0);
        }
    }
}
