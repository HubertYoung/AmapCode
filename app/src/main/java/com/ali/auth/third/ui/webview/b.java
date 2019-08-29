package com.ali.auth.third.ui.webview;

import android.view.View;
import android.view.View.OnClickListener;

class b implements OnClickListener {
    final /* synthetic */ BaseWebViewActivity a;

    b(BaseWebViewActivity baseWebViewActivity) {
        this.a = baseWebViewActivity;
    }

    public void onClick(View view) {
        this.a.finish();
    }
}
