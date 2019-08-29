package com.jiuyan.inimage;

import android.app.Application;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.framework.LauncherApplicationAgent;

public class MockLauncherApplicationAgent extends LauncherApplicationAgent {
    public MockLauncherApplicationAgent(Application application, Object obj) {
        super(application, obj);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void preInit() {
        super.preInit();
    }

    public void postInit() {
        super.postInit();
    }
}
