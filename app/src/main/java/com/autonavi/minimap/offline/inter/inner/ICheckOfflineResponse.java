package com.autonavi.minimap.offline.inter.inner;

import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface ICheckOfflineResponse {
    void callback(boolean z);
}
