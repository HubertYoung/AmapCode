package com.autonavi.minimap.ajx3.core;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface JsFunctionCallback {
    Object callback(Object... objArr);

    boolean isForMock();
}
