package com.alipay.mobile.security.zim.a;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import com.alipay.mobile.security.bio.utils.BioLog;

/* compiled from: ZimComponentCallbacks */
public class c implements ComponentCallbacks2 {
    private static c b;
    protected Context a;

    private c(Context context) {
        this.a = context.getApplicationContext();
    }

    public static c a(Application application) {
        if (b == null) {
            synchronized (c.class) {
                if (b == null) {
                    c cVar = new c(application);
                    BioLog.w((String) "ZimPlatform", (String) "application.registerComponentCallbacks(ZimComponentCallbacks)");
                    application.registerComponentCallbacks(cVar);
                    b = cVar;
                }
            }
        }
        return b;
    }

    public void onTrimMemory(int i) {
        BioLog.d("ZimPlatform", "onTrimMemory(level=" + i + ")");
    }

    public void onConfigurationChanged(Configuration configuration) {
        BioLog.d("ZimPlatform", "onConfigurationChanged(newConfig=" + configuration + ")");
    }

    public void onLowMemory() {
        BioLog.d("ZimPlatform", "onLowMemory()");
    }
}
