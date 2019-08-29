package com.autonavi.minimap.ajx3.debug;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public final class Yoga {
    private static native void nativePrintTree(boolean z);

    public static void printTreeLogEnabled(boolean z) {
        nativePrintTree(z);
    }
}
