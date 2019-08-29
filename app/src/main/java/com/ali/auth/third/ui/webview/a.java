package com.ali.auth.third.ui.webview;

import android.view.View;
import android.view.View.OnClickListener;

class a implements OnClickListener {
    final /* synthetic */ BaseWebViewActivity a;

    a(BaseWebViewActivity baseWebViewActivity) {
        this.a = baseWebViewActivity;
    }

    public void onClick(View view) {
        this.a.onBackHistory();
    }
}
