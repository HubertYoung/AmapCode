package com.ali.auth.third.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.ResourceUtils;
import com.ali.auth.third.login.LoginComponent;
import com.ali.auth.third.login.util.LoginStatus;
import com.ali.auth.third.ui.context.CallbackContext;

public class LoginActivity extends Activity {
    public static final String TAG = "login.LoginActivity";
    LinearLayout a;

    /* access modifiers changed from: protected */
    public void auth() {
        LoginComponent.INSTANCE.showLogin(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        StringBuilder sb = new StringBuilder("onActivityResult requestCode = ");
        sb.append(i);
        sb.append(" resultCode=");
        sb.append(i2);
        SDKLogger.d(TAG, sb.toString());
        if (!KernelContext.checkServiceValid()) {
            finish();
            return;
        }
        this.a.setClickable(true);
        this.a.setLongClickable(true);
        super.onActivityResult(i, i2, intent);
        if (CallbackContext.activity == null) {
            CallbackContext.setActivity(this);
        }
        CallbackContext.onActivityResult(i, i2, intent);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((LinearLayout) getLayoutInflater().inflate(ResourceUtils.getRLayout(this, "login_hidden"), null));
        KernelContext.context = getApplicationContext();
        this.a = (LinearLayout) findViewById(ResourceUtils.getIdentifier(this, "id", "login_layout"));
        this.a.setOnClickListener(new a(this));
        this.a.setClickable(false);
        this.a.setLongClickable(false);
        if (!KernelContext.checkServiceValid()) {
            SDKLogger.d(TAG, "static field null");
            LoginStatus.resetLoginFlag();
            finish();
            return;
        }
        CallbackContext.setActivity(this);
        SDKLogger.e(TAG, "before mtop call showLogin");
        auth();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!KernelContext.checkServiceValid()) {
            finish();
        }
    }
}
