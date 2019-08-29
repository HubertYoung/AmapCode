package com.alipay.mobile.tinyappcustom.h5plugin.ui;

import android.content.Context;
import android.view.KeyEvent;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.nebula.util.H5Log;

public class H5OpenAuthNoticeDialog extends AUNoticeDialog {
    public H5OpenAuthNoticeDialog(Context context, String title, String msg, String positiveString, String negativeString, boolean isAutoCancel) {
        super(context, title, msg, positiveString, negativeString, isAutoCancel);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (4 != event.getKeyCode()) {
            return super.onKeyDown(keyCode, event);
        }
        H5Log.d("H5OpenAuthNoticeDialog", "do nothing");
        return true;
    }
}
