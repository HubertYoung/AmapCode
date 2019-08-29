package com.autonavi.amap.app;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

public abstract class AMapBaseActivity extends FragmentActivity {
    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        AMapAppGlobal.setActivity(this);
        super.attachBaseContext(context);
    }

    public void onResume() {
        AMapAppGlobal.setActivity(this);
        super.onResume();
    }
}
