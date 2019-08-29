package com.ali.user.mobile.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ali.user.mobile.security.ui.R;

public class APGenericProgressDialog extends AlertDialog {
    private ProgressBar a;
    private TextView b;
    private CharSequence c;
    private boolean d;
    private boolean e;

    public APGenericProgressDialog(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.v);
        this.a = (ProgressBar) findViewById(16908301);
        this.b = (TextView) findViewById(R.id.aC);
        this.b.setText(this.c);
        int i = 8;
        if (this.c == null || "".equals(this.c)) {
            this.b.setVisibility(8);
        }
        ProgressBar progressBar = this.a;
        if (this.e) {
            i = 0;
        }
        progressBar.setVisibility(i);
        boolean z = this.d;
        if (this.a != null) {
            this.a.setIndeterminate(z);
        } else {
            this.d = z;
        }
    }

    public void setMessage(CharSequence charSequence) {
        this.c = charSequence;
    }

    public final void a(boolean z) {
        this.e = z;
    }
}
