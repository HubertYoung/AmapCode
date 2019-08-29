package com.autonavi.jni.alc;

import com.autonavi.jni.alc.inter.IALCCloudStrategy;
import com.autonavi.jni.alc.inter.IALCNetwork;
import com.autonavi.jni.alc.inter.IALCRecordAppender;
import com.autonavi.jni.alc.inter.IALCRecordNetwork;
import com.autonavi.minimap.alc.model.ALCLogLevel;
import com.autonavi.minimap.alc.model.ALCTriggerType;

public final class ALCManager {
    private static volatile ALCManager instance;
    private boolean sIsInited = false;

    private native void nativeAddRecordAppender(IALCRecordAppender iALCRecordAppender, boolean z);

    private native void nativeClearRecordStorage(int i);

    private native String nativeGetALCVersion();

    private native void nativeInit(String str, String str2, int i, int i2, IALCCloudStrategy iALCCloudStrategy, IALCNetwork iALCNetwork, long j, int i3, String str3);

    private native void nativeLog(int i, String str, String str2, String str3, String str4, String str5);

    private native void nativeRecord(int i, long j, String str, String str2, int i2, String str3);

    private native void nativeRecordUpload(int i);

    private native void nativeRecordWithSubTag(int i, int i2, long j, String str, String str2, String str3, int i3, String str4);

    private native void nativeRemoveRecordAppender(IALCRecordAppender iALCRecordAppender);

    private native void nativeSetCloudStategy(IALCCloudStrategy iALCCloudStrategy);

    private native void nativeSetCustomGroup(long j, boolean z);

    private native void nativeSetRecordCloudStategy(IALCCloudStrategy iALCCloudStrategy);

    private native void nativeSetRecordGroupMask(long j);

    private native void nativeSetRecordLogLevelMask(int i);

    private native void nativeSetRecordNetwork(IALCRecordNetwork iALCRecordNetwork);

    private native void nativeSetUploadStorageEncrypt(boolean z);

    private native void nativeSwitchRecordByGroupName(String str, boolean z);

    private native void nativeSwitchRecordConsole(boolean z);

    private native void nativeSwitchRecordStorage(boolean z);

    private native void nativeUninit();

    private native void nativeUpload(int i);

    private ALCManager() {
    }

    public static ALCManager getInstance() {
        if (instance == null) {
            synchronized (ALCManager.class) {
                try {
                    if (instance == null) {
                        instance = new ALCManager();
                    }
                }
            }
        }
        return instance;
    }

    public final void init(wq wqVar) {
        if (wqVar != null && !this.sIsInited) {
            this.sIsInited = true;
            nativeInit(wqVar.b, wqVar.c, wqVar.e, wqVar.d, wqVar.k, wqVar.i, wqVar.g, wqVar.f, "");
            setRecordNetwork(wqVar.j);
            setUploadStorageEncrypt(wqVar.a);
        }
    }

    public final void uninit() {
        nativeUninit();
        this.sIsInited = false;
    }

    public final void upload(ALCTriggerType aLCTriggerType) {
        nativeUpload(aLCTriggerType.getNum());
        recordUpload(aLCTriggerType);
    }

    public final void log(ALCLogLevel aLCLogLevel, String str, String str2, String str3, String str4, String str5) {
        nativeLog(aLCLogLevel.getNum(), str, str2, str3, str4, str5);
    }

    public final void setRecordGroupMask(long j) {
        nativeSetRecordGroupMask(j);
    }

    public final void setRecordLogLevelMask(int i) {
        nativeSetRecordLogLevelMask(i);
    }

    public final void setRecordListener(IALCRecordAppender iALCRecordAppender, boolean z) {
        nativeAddRecordAppender(iALCRecordAppender, z);
    }

    public final void removeRecordListener(IALCRecordAppender iALCRecordAppender) {
        nativeRemoveRecordAppender(iALCRecordAppender);
    }

    public final void record(ALCLogLevel aLCLogLevel, long j, String str, String str2, int i, String str3) {
        nativeRecord(aLCLogLevel.getNum(), j, str, str2, i, str3);
    }

    public final void recordWithSubTag(ALCLogLevel aLCLogLevel, int i, long j, String str, String str2, String str3, int i2, String str4) {
        nativeRecordWithSubTag(aLCLogLevel.getNum(), i, j, str, str2, str3, i2, str4);
    }

    public final void setCustomGroup(long j, boolean z) {
        nativeSetCustomGroup(j, z);
    }

    public final void setCloudStategy(IALCCloudStrategy iALCCloudStrategy) {
        nativeSetCloudStategy(iALCCloudStrategy);
    }

    public final void setRecordCloudStategy(IALCCloudStrategy iALCCloudStrategy) {
        nativeSetRecordCloudStategy(iALCCloudStrategy);
    }

    public final void setRecordNetwork(IALCRecordNetwork iALCRecordNetwork) {
        nativeSetRecordNetwork(iALCRecordNetwork);
    }

    public final void recordUpload(ALCTriggerType aLCTriggerType) {
        nativeRecordUpload(aLCTriggerType.getNum());
    }

    public final void setUploadStorageEncrypt(boolean z) {
        nativeSetUploadStorageEncrypt(z);
    }

    public final void setSwitchRecordConsole(boolean z) {
        nativeSwitchRecordConsole(z);
    }

    public final void setSwitchRecordStorage(boolean z) {
        nativeSwitchRecordStorage(z);
    }

    public final String getALCVersion() {
        return nativeGetALCVersion();
    }

    public final void setExpirationTime(int i) {
        nativeClearRecordStorage(i);
    }
}
