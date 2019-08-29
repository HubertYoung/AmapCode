package com.alipay.mobile.nebulacore.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alipay.mobile.nebula.R;

public class H5LoadingDialog extends Dialog {
    private ProgressBar a;
    private TextView b;
    private String c;
    private Activity d;

    public H5LoadingDialog(Activity context) {
        this(context, R.style.h5_loading_style);
        this.d = context;
    }

    public H5LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        View view = LayoutInflater.from(this.d).inflate(R.layout.h5_loading, null);
        this.a = (ProgressBar) view.findViewById(R.id.h5_loading_progress);
        this.b = (TextView) view.findViewById(R.id.h5_loading_message);
        LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.99f;
        getWindow().setAttributes(lp);
        setContentView(view);
        this.a.setVisibility(0);
        setCancelable(true);
        setOnCancelListener(null);
        this.a.setIndeterminate(false);
        setCanceledOnTouchOutside(false);
        a();
        super.onCreate(savedInstanceState);
    }

    public void setMessage(String text) {
        this.c = text;
        if (this.b != null) {
            a();
        }
    }

    private void a() {
        this.b.setText(this.c);
        if (TextUtils.isEmpty(this.c)) {
            this.b.setVisibility(8);
        } else {
            this.b.setVisibility(0);
        }
    }
}
