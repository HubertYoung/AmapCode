package com.autonavi.bl.search;

import java.io.PrintStream;

public class search_serviceJNI {
    private static final native void swig_module_init();

    public static void SwigDirector_InfoliteCallback_callBack(InfoliteCallback infoliteCallback, long j, InfoliteResult infoliteResult) {
        infoliteCallback.callBack(j, infoliteResult);
    }

    public static void SwigDirector_InfoliteCallback_error(InfoliteCallback infoliteCallback, long j, int i) {
        infoliteCallback.error(j, i);
    }

    static void swig_jni_init() {
        PrintStream printStream = System.out;
    }

    static {
        swig_module_init();
    }
}
