package com.alipay.mobile.antui.pop;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: AUPopBar */
final class b implements OnClickListener {
    final /* synthetic */ AUPopBar a;

    b(AUPopBar this$0) {
        this.a = this$0;
    }

    public final void onClick(View v) {
        this.a.disappearAction();
    }
}
