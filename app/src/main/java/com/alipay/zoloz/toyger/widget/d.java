package com.alipay.zoloz.toyger.widget;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: GenenalDialog */
final class d implements OnClickListener {
    final /* synthetic */ GenenalDialog a;

    d(GenenalDialog genenalDialog) {
        this.a = genenalDialog;
    }

    public final void onClick(View view) {
        this.a._self.dismiss();
        if (this.a.positiveListener != null) {
            this.a.positiveListener.onClick(this.a._self, -1);
        }
    }
}
