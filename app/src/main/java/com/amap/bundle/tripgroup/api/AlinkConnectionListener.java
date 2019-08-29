package com.amap.bundle.tripgroup.api;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface AlinkConnectionListener {
    void onConnected();

    void onDisconnected();
}
