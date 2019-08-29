package com.amap.bundle.voiceservice.dispatch;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IVoicePoiSelectorDispatcher extends bie {
    void cancel(int i, String str);

    void selectPoi(int i, String str);

    void setPoiSelectorApiControlListener(aif aif);
}
