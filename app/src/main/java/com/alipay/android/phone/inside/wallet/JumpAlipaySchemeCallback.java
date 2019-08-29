package com.alipay.android.phone.inside.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.alipay.android.phone.inside.log.api.LoggerFactory;

public class JumpAlipaySchemeCallback extends Activity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature();
        handleIntent(getIntent());
    }

    private void requestWindowFeature() {
        try {
            requestWindowFeature(1);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(final Intent intent) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    JumpAlipaySchemeCallback.this.handleIntentBackground(intent);
                    JumpAlipaySchemeCallback.this.finish();
                } catch (Throwable th) {
                    LoggerFactory.f().c((String) "inside", th);
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void handleIntentBackground(Intent intent) {
        new JumpAlipaySchemeProvider().notifyJumpResult(intent.getExtras());
    }
}
