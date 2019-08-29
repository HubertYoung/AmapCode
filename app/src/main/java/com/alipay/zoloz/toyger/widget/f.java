package com.alipay.zoloz.toyger.widget;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: GenenalDialog */
final class f implements OnClickListener {
    final /* synthetic */ GenenalDialog a;

    f(GenenalDialog genenalDialog) {
        this.a = genenalDialog;
    }

    public final void onClick(View view) {
        this.a._self.dismiss();
        if (this.a.negativeListener != null) {
            this.a.negativeListener.onClick(this.a._self, -2);
        }
    }
}
