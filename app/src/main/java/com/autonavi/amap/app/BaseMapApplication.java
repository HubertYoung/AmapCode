package com.autonavi.amap.app;

import android.content.Context;
import com.autonavi.wing.WingApplication;

public abstract class BaseMapApplication extends WingApplication {
    public void onAttachBaseContext(Context context) {
        AMapAppGlobal.setApplication(this);
        b.a(bno.a);
        a.a(bno.a);
        super.onAttachBaseContext(context);
    }
}
