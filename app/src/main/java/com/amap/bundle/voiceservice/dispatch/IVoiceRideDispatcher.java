package com.amap.bundle.voiceservice.dispatch;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IVoiceRideDispatcher extends bie {
    void setRideVoiceListener(aii aii);

    void startNavi(int i, String str);
}
