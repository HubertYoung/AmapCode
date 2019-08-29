package com.ali.auth.third.core.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;

public class TaeProgressDialog extends ProgressDialog {
    private ProgressBar a;
    private TextView b;
    private CharSequence c;
    private boolean d;
    private boolean e;

    public TaeProgressDialog(Context context) {
        super(context);
    }

    public TaeProgressDialog(Context context, int i) {
        super(context, i);
    }

    private void a() {
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
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(ResourceUtils.getIdentifier(ResUtils.LAYOUT, "com_taobao_tae_sdk_progress_dialog"));
        getWindow().setBackgroundDrawableResource(17170445);
        this.a = (ProgressBar) findViewById(16908301);
        this.b = (TextView) findViewById(ResourceUtils.getIdentifier("id", "com_taobao_tae_sdk_progress_dialog_message"));
        a();
        setIndeterminate(this.d);
    }

    public void setIndeterminate(boolean z) {
        if (this.a != null) {
            this.a.setIndeterminate(z);
        } else {
            this.d = z;
        }
    }

    public void setMessage(CharSequence charSequence) {
        this.c = charSequence;
    }

    public void setProgressVisiable(boolean z) {
        this.e = z;
    }
}
