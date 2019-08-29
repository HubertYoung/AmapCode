package com.amap.bundle.voiceservice.dispatch;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IVoiceCmdResponder {
    long getScene();
}
