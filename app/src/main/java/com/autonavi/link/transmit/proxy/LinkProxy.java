package com.autonavi.link.transmit.proxy;

import com.autonavi.link.transmit.inter.Connection;
import com.autonavi.link.transmit.inter.LinkProxyError;
import java.util.concurrent.atomic.AtomicBoolean;

public class LinkProxy {
    private static AtomicBoolean mInit = new AtomicBoolean(false);
    private static LinkProxy mSelf;
    private int mProxyPort;
    private long mPtr = nativeConstruct();

    private native int nativeAddConnection(long j, Connection connection, LinkProxyError linkProxyError);

    private native boolean nativeCheckConnectId(long j, String str);

    private static native long nativeConstruct();

    private static native void nativeEnableLog(boolean z);

    private native String nativeGetConnectId(long j);

    private native void nativeRelease(long j);

    private native void nativeRemoveConnection(long j, Connection connection);

    public static void init() {
        if (!mInit.get()) {
            System.loadLibrary("linkProxy-1.0.0");
            mInit.set(true);
            enableNativeLog(false);
        }
    }

    private static void checkInit() {
        if (!mInit.get()) {
            throw new IllegalStateException("LinkProxy must be init before using");
        }
    }

    public static synchronized LinkProxy getInstance() {
        LinkProxy linkProxy;
        synchronized (LinkProxy.class) {
            try {
                checkInit();
                if (mSelf == null) {
                    mSelf = new LinkProxy();
                }
                linkProxy = mSelf;
            }
        }
        return linkProxy;
    }

    public static void enableNativeLog(boolean z) {
        checkInit();
        nativeEnableLog(z);
    }

    private LinkProxy() {
        checkInit();
    }

    public void release() {
        checkInit();
    }

    public void addConnection(Connection connection, LinkProxyError linkProxyError) {
        checkInit();
        this.mProxyPort = nativeAddConnection(this.mPtr, connection, linkProxyError);
    }

    public int getProxyPort() {
        checkInit();
        return this.mProxyPort;
    }

    public void removeConnection(Connection connection) {
        checkInit();
        nativeRemoveConnection(this.mPtr, connection);
    }

    public String getConnectId() {
        checkInit();
        return nativeGetConnectId(this.mPtr);
    }

    public boolean checkConnectId(String str) {
        checkInit();
        return nativeCheckConnectId(this.mPtr, str);
    }
}
