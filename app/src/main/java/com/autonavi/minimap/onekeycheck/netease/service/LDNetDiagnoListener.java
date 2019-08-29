package com.autonavi.minimap.onekeycheck.netease.service;

import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public interface LDNetDiagnoListener {
    void OnNetDiagnoFinished();

    void OnNetDiagnoUpdated(dsr dsr);
}
