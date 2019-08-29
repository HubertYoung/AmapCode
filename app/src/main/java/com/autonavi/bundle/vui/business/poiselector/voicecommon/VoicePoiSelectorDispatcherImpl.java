package com.autonavi.bundle.vui.business.poiselector.voicecommon;

import com.amap.bundle.voiceservice.dispatch.IVoiceDispatchMethod;
import com.amap.bundle.voiceservice.dispatch.IVoicePoiSelectorDispatcher;

public class VoicePoiSelectorDispatcherImpl implements IVoicePoiSelectorDispatcher {
    private aif a;

    @IVoiceDispatchMethod(methodName = "cancel")
    public void cancel(int i, String str) {
        if (this.a != null) {
            this.a.a(i);
        } else {
            bft.a(i);
        }
    }

    @IVoiceDispatchMethod(methodName = "selectPoi")
    public void selectPoi(int i, String str) {
        if (this.a != null) {
            this.a.a(i, str);
        } else {
            bft.a(i);
        }
    }

    public void setPoiSelectorApiControlListener(aif aif) {
        this.a = aif;
    }
}
