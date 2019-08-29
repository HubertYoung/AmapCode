package com.alipay.mobile.monitor.track;

import android.content.Context;
import com.alipay.mobile.monitor.track.agent.TrackAgent;

public interface AutoTrackerHolder {
    Context getContext();

    TrackAgent getTrackAgent();
}
