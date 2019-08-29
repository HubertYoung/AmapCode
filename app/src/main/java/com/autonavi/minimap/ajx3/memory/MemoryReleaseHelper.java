package com.autonavi.minimap.ajx3.memory;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class MemoryReleaseHelper {
    public static final int JS_DOM_EVENT = 2;
    public static final int JS_DOM_LIST_CELL_DATA = 1;
    public static final int JS_DOM_NODE = 0;

    private static native void nativeRelease(long j, int i);

    static void release(long j, int i) {
        nativeRelease(j, i);
    }
}
