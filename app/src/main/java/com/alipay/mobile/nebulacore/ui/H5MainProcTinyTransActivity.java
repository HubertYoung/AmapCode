package com.alipay.mobile.nebulacore.ui;

import android.content.Intent;
import android.os.Bundle;
import com.alipay.mobile.nebula.util.H5Log;

public class H5MainProcTinyTransActivity extends H5TransActivity {
    private H5KeepAliveActivityDelegate c = new H5KeepAliveActivityDelegate();
    private volatile boolean d = false;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.c.afterCreate(this);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.c.removeDestroyRunnable();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        H5Log.d("H5MainProcTinyActivity", "onNewIntent");
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        super.a();
        this.c.putRunningTinyActivity(getActivityApplication().getAppId(), this);
    }

    /* access modifiers changed from: protected */
    public void onUserLeaveHint() {
        super.onUserLeaveHint();
        this.c.onUserLeaveHint(getH5Session());
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (!this.d) {
            this.c.onDestroy(this);
            this.d = true;
        }
        super.onDestroy();
    }

    public void finish() {
        if (!this.d) {
            this.c.onDestroy(this);
            this.d = true;
        }
        super.finish();
    }
}
