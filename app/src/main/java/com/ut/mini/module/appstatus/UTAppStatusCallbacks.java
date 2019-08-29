package com.ut.mini.module.appstatus;

import android.app.Activity;
import android.os.Bundle;

public interface UTAppStatusCallbacks {
    void onActivityCreated(Activity activity, Bundle bundle);

    void onActivityDestroyed(Activity activity);

    void onActivityPaused(Activity activity);

    void onActivityResumed(Activity activity);

    void onActivitySaveInstanceState(Activity activity, Bundle bundle);

    void onActivityStarted(Activity activity);

    void onActivityStopped(Activity activity);

    void onSwitchBackground();

    void onSwitchForeground();
}
