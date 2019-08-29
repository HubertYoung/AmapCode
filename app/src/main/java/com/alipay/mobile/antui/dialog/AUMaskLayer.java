package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUDialog;

public class AUMaskLayer extends AUDialog {
    private View mContentView;

    public AUMaskLayer(Context context, View contentView) {
        super(context, R.style.maskLayerDialogStyle);
        this.mContentView = contentView;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.mContentView);
    }
}
