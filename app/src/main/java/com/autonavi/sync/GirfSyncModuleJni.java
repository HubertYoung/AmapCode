package com.autonavi.sync;

class GirfSyncModuleJni {
    protected static native GirfSyncJni createSyncInstance(String str, String str2, INetwork iNetwork, ICallback iCallback);

    protected static native void destroySyncInstance(GirfSyncJni girfSyncJni);

    public static void emptyMethod() {
    }

    protected static native int moduleInit(String str);

    protected static native int moduleUninit();

    protected static native void test();

    GirfSyncModuleJni() {
    }

    static {
        System.loadLibrary("sync_jni");
    }
}
