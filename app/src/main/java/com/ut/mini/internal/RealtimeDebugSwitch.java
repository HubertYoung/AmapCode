package com.ut.mini.internal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.alibaba.analytics.core.Constants.RealTimeDebug;
import com.alibaba.analytics.utils.Logger;
import com.ut.mini.module.appstatus.UTAppStatusCallbacks;
import java.util.HashMap;

public class RealtimeDebugSwitch implements UTAppStatusCallbacks {
    static int i;

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onSwitchBackground() {
    }

    public void onSwitchForeground() {
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (i == 0) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                Uri data = intent.getData();
                if (data != null) {
                    String scheme = data.getScheme();
                    if (scheme != null && scheme.startsWith("ut.")) {
                        String queryParameter = data.getQueryParameter("debugkey");
                        String queryParameter2 = data.getQueryParameter("from");
                        if (scheme == null || !scheme.startsWith("ut.")) {
                            Logger.w((String) null, "scheme", scheme);
                        } else {
                            HashMap hashMap = new HashMap();
                            hashMap.put(RealTimeDebug.DEBUG_API_URL, "http://muvp.alibaba-inc.com/online/UploadRecords.do");
                            hashMap.put(RealTimeDebug.DEBUG_KEY, queryParameter);
                            hashMap.put("from", queryParameter2);
                            hashMap.put(RealTimeDebug.DEBUG_SAMPLING_OPTION, "true");
                            UTTeamWork.getInstance().turnOnRealTimeDebug(hashMap);
                            return;
                        }
                    }
                    return;
                }
                Logger.w((String) null, "uri", data);
                return;
            }
            Logger.w((String) null, "i ", intent);
        }
    }

    public void onActivityPaused(Activity activity) {
        i--;
    }

    public void onActivityResumed(Activity activity) {
        i++;
    }
}
