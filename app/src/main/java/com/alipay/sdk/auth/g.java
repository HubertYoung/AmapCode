package com.alipay.sdk.auth;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

final class g implements OnClickListener {
    final /* synthetic */ e a;

    g(e eVar) {
        this.a = eVar;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.a.a.cancel();
        AuthActivity.this.g = false;
        StringBuilder sb = new StringBuilder();
        sb.append(AuthActivity.this.d);
        sb.append("?resultCode=150");
        h.a((Activity) AuthActivity.this, sb.toString());
        AuthActivity.this.finish();
    }
}
