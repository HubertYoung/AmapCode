package com.autonavi.minimap.ajx3.DebugAgent;

public class AwDevToolsServer {
    private static AwDevToolsServer sInstance;
    private long mNativeDevToolsServer = 0;

    private native void nativeDestroyRemoteDebugging(long j);

    private native long nativeInitRemoteDebugging(long j);

    private native void nativeSetRemoteDebuggingEnabled(long j, boolean z, String str);

    public static AwDevToolsServer getInstance(long j) {
        if (sInstance == null) {
            sInstance = new AwDevToolsServer(j);
        }
        return sInstance;
    }

    public static void destroy() {
        if (sInstance != null) {
            sInstance.destroyDebug();
            sInstance = null;
        }
    }

    private AwDevToolsServer(long j) {
        this.mNativeDevToolsServer = nativeInitRemoteDebugging(j);
    }

    public void destroyDebug() {
        nativeDestroyRemoteDebugging(this.mNativeDevToolsServer);
        this.mNativeDevToolsServer = 0;
    }

    public void setRemoteDebuggingEnabled(boolean z, String str) {
        nativeSetRemoteDebuggingEnabled(this.mNativeDevToolsServer, z, str);
    }
}
