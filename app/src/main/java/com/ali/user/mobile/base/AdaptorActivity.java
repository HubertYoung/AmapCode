package com.ali.user.mobile.base;

import android.content.Intent;
import android.os.Bundle;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.framework.base.BaseInsideActivity;

public abstract class AdaptorActivity extends BaseInsideActivity {
    protected String mAppId = "";

    /* access modifiers changed from: protected */
    public abstract void setAppId();

    public void onCreate(Bundle bundle) {
        setAppId();
        prepareIntent(getIntent());
        super.onCreate(bundle);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        prepareIntent(intent);
        super.onNewIntent(intent);
    }

    private void prepareIntent(Intent intent) {
        try {
            intent.putExtra("app_id", this.mAppId);
            intent.addFlags(262144);
        } catch (Throwable th) {
            AliUserLog.b("AdaptorActivity", "prepareIntent error", th);
            finish();
        }
    }
}
