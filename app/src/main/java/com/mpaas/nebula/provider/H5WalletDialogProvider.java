package com.mpaas.nebula.provider;

import android.app.Activity;
import android.app.Dialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider.OnClickNegativeListener;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider.OnClickPositiveListener;
import com.alipay.mobile.nebula.util.H5Log;

public class H5WalletDialogProvider implements H5DialogManagerProvider {
    public static final String TAG = "H5WalletDialogProvider";
    private AUNoticeDialog a = null;

    public Dialog createDialog(Activity context, String title, String message, String positiveString, String negativeString, String align) {
        if (context == null || context.isFinishing()) {
            return null;
        }
        try {
            this.a = new AUNoticeDialog(context, title, message, positiveString, negativeString, false);
        } catch (Exception e) {
            H5Log.e((String) TAG, (Throwable) e);
        }
        return this.a;
    }

    public void setNegativeListener(final OnClickNegativeListener listener) {
        if (this.a != null && listener != null) {
            this.a.setNegativeListener(new AUNoticeDialog.OnClickNegativeListener() {
                public void onClick() {
                    listener.onClick();
                }
            });
        }
    }

    public void setPositiveListener(final OnClickPositiveListener listener) {
        if (this.a != null && listener != null) {
            this.a.setPositiveListener(new AUNoticeDialog.OnClickPositiveListener() {
                public void onClick() {
                    listener.onClick();
                }
            });
        }
    }

    public void showDialog() {
        disMissDialog();
        if (this.a != null) {
            try {
                this.a.show();
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
    }

    public void disMissDialog() {
        if (this.a != null) {
            try {
                this.a.dismiss();
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
    }

    public void release() {
        this.a = null;
    }

    public void setPositiveTextColor(String positiveTextColor) {
        if (this.a != null) {
            this.a.setPositiveTextColor(positiveTextColor);
        }
    }

    public void setNegativeTextColor(String negativeTextColor) {
        if (this.a != null) {
            this.a.setNegativeTextColor(negativeTextColor);
        }
    }
}
