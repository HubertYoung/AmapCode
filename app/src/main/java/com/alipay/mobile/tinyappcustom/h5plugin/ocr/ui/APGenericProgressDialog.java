package com.alipay.mobile.tinyappcustom.h5plugin.ocr.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alipay.mobile.a.a.a.C0098a;
import com.alipay.mobile.a.a.a.b;

public class APGenericProgressDialog extends AlertDialog {
    private ProgressBar a;
    private TextView b;
    private CharSequence c;
    private boolean d;
    private boolean e;

    public APGenericProgressDialog(Context context) {
        super(context);
    }

    public APGenericProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(b.generic_progress_dialog);
        this.a = (ProgressBar) findViewById(16908301);
        this.b = (TextView) findViewById(C0098a.message);
        a();
        setIndeterminate(this.d);
    }

    private void a() {
        int i = 8;
        this.b.setText(this.c);
        if (this.c == null || "".equals(this.c)) {
            this.b.setVisibility(8);
        }
        ProgressBar progressBar = this.a;
        if (this.e) {
            i = 0;
        }
        progressBar.setVisibility(i);
    }

    public void setMessage(CharSequence message) {
        this.c = message;
    }

    public TextView getMessageView() {
        return this.b;
    }

    public void setProgressVisiable(boolean progressVisiable) {
        this.e = progressVisiable;
    }

    public void setIndeterminate(boolean indeterminate) {
        if (this.a != null) {
            this.a.setIndeterminate(indeterminate);
        } else {
            this.d = indeterminate;
        }
    }
}
