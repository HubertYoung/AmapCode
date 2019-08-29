package com.autonavi.minimap.route.ride.voice;

import android.util.Pair;
import com.amap.bundle.voiceservice.dispatch.IVoiceDispatchMethod;
import com.amap.bundle.voiceservice.dispatch.IVoiceRideDispatcher;
import com.uc.webview.export.internal.SDKFactory;

public class VoiceRideDispatcherImp implements IVoiceRideDispatcher {
    private aii a;

    @IVoiceDispatchMethod(methodName = "startNavi")
    public void startNavi(int i, String str) {
        if (this.a != null) {
            this.a.a(i);
            return;
        }
        if (i != -1) {
            aia aia = (aia) a.a.a(aia.class);
            if (aia != null) {
                aia.a(i, (int) SDKFactory.getCoreType, (Pair<String, Object>) null);
            }
        }
    }

    public void setRideVoiceListener(aii aii) {
        this.a = aii;
    }
}
