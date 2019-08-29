package com.alipay.mobile.monitor.track;

import android.app.Activity;
import com.alipay.mobile.common.logging.api.behavor.Behavor;

public interface AutoTrackerAdapter {
    Activity getActivityAt(Activity activity, int i);

    String getActivityTrackId(Activity activity);

    String getAppId(Activity activity);

    String getSourceId(Activity activity);

    int getStartActivityCount(Activity activity);

    void onAssembleBehavior(Behavor behavor);

    void onTrackIntegratorInit(TrackIntegrator trackIntegrator);
}
