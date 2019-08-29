package com.autonavi.jni.alc;

import com.amap.bundle.logs.AMapLog;

public final class OptRecordMan {
    private static final String TAG = "OptRecordMan";
    private static volatile OptRecordMan sInstance;
    private volatile boolean mBInited = false;

    private native void nativeAppAction(int i);

    private native boolean nativeInit(String str, int i, int i2, int i3, String str2);

    private native boolean nativeRecordJson(long j, int i, int i2, String str);

    private native boolean nativeRecordMsgPack(long j, int i, int i2, byte[] bArr);

    private native void nativeUninit();

    private native void nativeUploadByFeedback(String str);

    public static OptRecordMan getInstance() {
        if (sInstance == null) {
            synchronized (OptRecordMan.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new OptRecordMan();
                    }
                }
            }
        }
        return sInstance;
    }

    public final boolean init(String str, int i, int i2, int i3, String str2) {
        if (this.mBInited) {
            return false;
        }
        boolean nativeInit = nativeInit(str, i, i2, i3, str2);
        if (nativeInit) {
            this.mBInited = true;
        }
        return nativeInit;
    }

    public final boolean playback(long j, int i, int i2, String str) {
        if (this.mBInited) {
            return nativeRecordJson(j, i, i2, str);
        }
        StringBuilder sb = new StringBuilder("play back() have not init!!!mainType:");
        sb.append(i);
        sb.append(",subType:");
        sb.append(i2);
        AMapLog.error("paas.logs", TAG, sb.toString());
        return false;
    }

    public final void feedback(String str) {
        if (this.mBInited) {
            nativeUploadByFeedback(str);
        } else {
            AMapLog.error("paas.logs", TAG, "feedback() have not init!!!");
        }
    }

    public final void appAction(int i) {
        if (this.mBInited) {
            nativeAppAction(i);
        } else {
            AMapLog.error("paas.logs", TAG, "appAction() play back have not init!!!");
        }
    }

    public final void unInit() {
        if (this.mBInited) {
            nativeUninit();
            this.mBInited = false;
            return;
        }
        AMapLog.error("paas.logs", TAG, "unInit() play back have not init!!!");
    }

    private OptRecordMan() {
    }
}
