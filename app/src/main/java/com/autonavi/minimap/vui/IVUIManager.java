package com.autonavi.minimap.vui;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMemberNames;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepClassMemberNames
@Keep
@KeepName
public interface IVUIManager {
    String getVersionInfo();

    void tryRestartListening();
}
