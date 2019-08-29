package com.alipay.biometrics.ui.widget;

import android.app.Dialog;
import android.content.Context;
import com.alipay.android.phone.mobilecommon.biometric.a.a.d;
import com.alipay.android.phone.mobilecommon.biometric.a.a.e;

public class LoadingProgressDialog extends Dialog {
    public LoadingProgressDialog(Context context) {
        this(context, e.LoadingDialog);
    }

    public LoadingProgressDialog(Context context, int i) {
        super(context, i);
        setContentView(d.bio_dialog_loading_layout);
        getWindow().getAttributes().gravity = 17;
    }
}
