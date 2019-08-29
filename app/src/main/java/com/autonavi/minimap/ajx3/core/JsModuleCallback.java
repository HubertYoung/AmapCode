package com.autonavi.minimap.ajx3.core;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface JsModuleCallback {
    Object onModuleCall(long j, String str, int i, Object... objArr);

    Object onModuleGetter(long j, String str, int i);

    void onModuleSetter(long j, String str, int i, Object obj);
}
