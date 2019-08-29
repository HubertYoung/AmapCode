package com.amap.bundle.lotuspool;

import com.amap.bundle.logs.AMapLog;

public class LotusPoolVApp extends esh {
    private static final String a = "LotusPoolVApp";

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppEnterForeground() {
        if (bno.a) {
            AMapLog.d(a, "onActivityResume()", true);
        }
        wr.a(getApplicationContext(), 3);
    }

    public void vAppMapLoadCompleted() {
        if (bno.a) {
            AMapLog.d(a, "vAppMapLoadCompleted()", true);
        }
        wr.a(getApplicationContext(), 2);
    }

    public void vAppEnterBackground() {
        if (bno.a) {
            AMapLog.d(a, "onActivityStop()", true);
        }
        wr.a(getApplicationContext(), 4);
    }
}
