package com.autonavi.jni.drive.ajx;

import android.content.Context;

public class AjxDriveService {
    private Context mContext;
    private long mPtr;
    private GTraceObserver mTraceObserver;

    private final native void nativeInit();

    public final native void nativeDestroy();

    public AjxDriveService(Context context) {
        this.mContext = context;
        nativeInit();
    }

    public void setTraceObserver(GTraceObserver gTraceObserver) {
        this.mTraceObserver = gTraceObserver;
    }
}
