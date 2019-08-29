package com.ali.auth.third.ui;

import android.view.View;
import android.view.View.OnClickListener;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.login.util.LoginStatus;

class a implements OnClickListener {
    final /* synthetic */ LoginActivity a;

    a(LoginActivity loginActivity) {
        this.a = loginActivity;
    }

    public void onClick(View view) {
        SDKLogger.e(LoginActivity.TAG, "click to destroy");
        this.a.finish();
        LoginStatus.resetLoginFlag();
    }
}
