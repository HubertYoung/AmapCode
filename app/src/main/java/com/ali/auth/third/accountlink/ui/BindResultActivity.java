package com.ali.auth.third.accountlink.ui;

import android.app.Activity;
import android.os.Bundle;
import com.ali.auth.third.accountlink.a;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.ui.LoginActivity;

public class BindResultActivity extends LoginActivity {
    public void auth() {
        switch (getIntent().getIntExtra("type", 1)) {
            case 1:
                a.a.a((Activity) this, getIntent().getIntExtra("code", 0), getIntent().getStringExtra("ibb"));
                return;
            case 2:
                a.a.a(this);
                return;
            case 3:
                a.a.a((Activity) this, getIntent().getStringExtra("token"), getIntent().getStringExtra("ibb"));
                return;
            default:
                return;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SDKLogger.d(LoginActivity.TAG, "BindResultActivity");
    }
}
