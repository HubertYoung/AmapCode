package com.alipay.mobile.beehive.capture;

import android.os.Bundle;
import com.alipay.mobile.framework.app.ActivityApplication;

public class CaptureApp extends ActivityApplication {
    private Bundle params;

    public String getEntryClassName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.params = bundle;
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        dispatchParams();
    }

    /* access modifiers changed from: protected */
    public void onRestart(Bundle bundle) {
        this.params = bundle;
        dispatchParams();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        this.params = null;
    }

    private void dispatchParams() {
    }
}
