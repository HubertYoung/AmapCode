package com.autonavi.minimap.ajx3.dom;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class KeyDefine {
    private static native String nativeGetNameByType(int i);

    private static native int nativeGetTypeByName(String str);

    private static native boolean nativeIsStyleProperty(int i);

    private KeyDefine() {
    }

    public static int name2Type(String str) {
        return nativeGetTypeByName(str);
    }

    public static String type2Name(int i) {
        return nativeGetNameByType(i);
    }

    public static boolean isStyleProperty(int i) {
        return nativeIsStyleProperty(i);
    }
}
