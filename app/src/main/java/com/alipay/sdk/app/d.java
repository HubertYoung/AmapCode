package com.alipay.sdk.app;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

final class d implements OnClickListener {
    final /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.a.b.d = true;
        this.a.a.proceed();
        dialogInterface.dismiss();
    }
}
