package com.alipay.mobile.antui.dialog;

import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.antui.dialog.AUImageDialog.ButtonListAdapter;

/* compiled from: AUImageDialog */
final class x implements OnClickListener {
    final /* synthetic */ int a;
    final /* synthetic */ ButtonListAdapter b;

    x(ButtonListAdapter this$1, int i) {
        this.b = this$1;
        this.a = i;
    }

    public final void onClick(View v) {
        if (AUImageDialog.this.itemListener != null) {
            AUImageDialog.this.itemListener.onItemClick(this.a);
        }
    }
}
