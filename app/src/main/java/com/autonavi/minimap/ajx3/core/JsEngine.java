package com.autonavi.minimap.ajx3.core;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public final class JsEngine {
    private long mCurrJsContext;
    private JsModuleCallback mJsModuleCallback;
    private long mShadow;

    private native boolean isDebuggerSupported(long j);

    private native void nativeAddTimestamp(long j, String str);

    private native long nativeAlloc(long j);

    private native void nativeBack(long j, String str);

    private native void nativeDestroyContext(long j, long j2);

    private native void nativeDestroyMemoryStorageRef(long j, long j2);

    private native boolean nativeGetEagleEyeEnable(long j);

    private native long nativeGetMemoryStorageRef(long j, String str);

    private native Parcel nativeGetPageConfig(long j, String str, int i);

    private native boolean nativeGetPerformanceLogEnabled(long j);

    private native boolean nativeIsPerformanceLogSupported(long j);

    private native void nativeJniLog(long j, String str);

    private native void nativePrepare(long j);

    private native void nativePrepare(long j, String str, int i, String str2);

    private native void nativeRegisterNativeModule(long j, String str);

    private native void nativeRegisterNativeModule(long j, String str, String str2, boolean z);

    private native int nativeRun(long j, long j2, JsContextRef jsContextRef, String str, String str2, Object obj, String str3, JsContextObserver jsContextObserver, float f, float f2, float f3, int i, String str4, String str5, long j3);

    private native void nativeSetEagleEyeEnable(long j, boolean z);

    private native void nativeSetGlobalProperty(long j, String str, String str2);

    private native void nativeSetPerformanceLogEnabled(long j, boolean z);

    private native void nativeStartService(String str, long j, String str2, String str3, Object obj, int i, String str4, String str5);

    private native void nativeStopService(long j, String str);

    private native void sendInspectorMessageToFrondend(long j, long j2, String str);

    private native void startDebug(long j, String str, String str2);

    private native void stopDebug(long j);

    private native void trigJsCodeCoverageDataCollection(long j, long j2);

    public final native void nativeCancelDebuggerWait(long j, long j2);

    JsEngine(long j) {
        this.mShadow = j;
    }

    public final void setJsModuleCallback(JsModuleCallback jsModuleCallback) {
        this.mJsModuleCallback = jsModuleCallback;
    }

    public final void prepare() {
        nativePrepare(this.mShadow);
    }

    public final void prepare(String str) {
        String bundleName = AjxFileInfo.getBundleName(str);
        String str2 = str;
        nativePrepare(this.mShadow, str2, AjxFileInfo.getLatestPatchIndex(bundleName), AjxFileInfo.getAllBundlesIndexSnapshot());
    }

    public final JsContextRef alloc() {
        this.mCurrJsContext = nativeAlloc(this.mShadow);
        new StringBuilder("--JsEngine.alloc :").append(this.mCurrJsContext);
        return new JsContextRef(this.mCurrJsContext);
    }

    public final int run(JsContextRef jsContextRef, @NonNull String str, @NonNull Object obj, @NonNull String str2, float f, float f2, int i, String str3, @NonNull JsContextObserver jsContextObserver, String str4, long j) {
        return nativeRun(this.mShadow, jsContextRef.shadow(), jsContextRef, str, str, obj, str2, jsContextObserver, f, f2, DimensionUtils.getDensisty(), i, str3, str4, j);
    }

    public final long getCurrJsContext() {
        return this.mCurrJsContext;
    }

    public final PageConfig getPageConfig(String str, int i) {
        Parcel nativeGetPageConfig = nativeGetPageConfig(this.mShadow, str, i);
        if (nativeGetPageConfig == null) {
            return null;
        }
        PageConfig pageConfig = new PageConfig();
        pageConfig.readFromParcel(nativeGetPageConfig);
        return pageConfig;
    }

    public final void back(@NonNull String str) {
        nativeBack(this.mShadow, str);
    }

    public final void destroyContext(@NonNull JsContextRef jsContextRef) {
        nativeDestroyContext(this.mShadow, jsContextRef.shadow());
    }

    public final void setGlobalProperty(@NonNull String str, String str2) {
        nativeSetGlobalProperty(this.mShadow, str, str2);
    }

    public final void registerModule(@NonNull String str, String str2, boolean z) {
        nativeRegisterNativeModule(this.mShadow, str, str2, z);
    }

    public final void registerModule(@NonNull String str) {
        nativeRegisterNativeModule(this.mShadow, str);
    }

    public final boolean isDebuggerSupported() {
        return isDebuggerSupported(this.mShadow);
    }

    public final void startDebug(String str, String str2) {
        startDebug(this.mShadow, str, str2);
    }

    public final void stopDebug() {
        stopDebug(this.mShadow);
    }

    public final void cancelDebuggerWait(long j) {
        nativeCancelDebuggerWait(this.mShadow, j);
    }

    public final void sendInspectorMessage(long j, String str) {
        sendInspectorMessageToFrondend(this.mShadow, j, str);
    }

    public final void trigJsCodeCoverageDataCollection(long j) {
        trigJsCodeCoverageDataCollection(this.mShadow, j);
    }

    public final MemoryStorageRef getMemoryStorageRef(String str) {
        return new MemoryStorageRef(nativeGetMemoryStorageRef(this.mShadow, str));
    }

    public final void destroyMemoryStorageRef(MemoryStorageRef memoryStorageRef) {
        nativeDestroyMemoryStorageRef(this.mShadow, memoryStorageRef.getShadow());
    }

    public final void addTimestampWithoutContext(String str) {
        nativeAddTimestamp(0, str);
    }

    public final void addTimestamp(String str) {
        nativeAddTimestamp(this.mCurrJsContext, str);
    }

    public final void jniLog(String str) {
        nativeJniLog(this.mShadow, str);
    }

    public final boolean isPerformanceLogSupported() {
        return nativeIsPerformanceLogSupported(this.mShadow);
    }

    public final boolean getPerformanceLogEnabled() {
        return nativeGetPerformanceLogEnabled(this.mShadow);
    }

    public final void setPerformanceLogEnabled(boolean z) {
        nativeSetPerformanceLogEnabled(this.mShadow, z);
    }

    public final void setEagleEyeEnable(boolean z) {
        nativeSetEagleEyeEnable(this.mShadow, z);
    }

    public final boolean isEagleEyeEnable() {
        return nativeGetEagleEyeEnable(this.mShadow);
    }

    private Object onNativeModuleCall(long j, String str, int i, Object... objArr) {
        if (this.mJsModuleCallback != null) {
            return this.mJsModuleCallback.onModuleCall(j, str, i, objArr);
        }
        return null;
    }

    private Object onNativeModuleGetter(long j, String str, int i) {
        if (this.mJsModuleCallback != null) {
            return this.mJsModuleCallback.onModuleGetter(j, str, i);
        }
        return null;
    }

    private void onNativeModuleSetter(long j, String str, int i, Object obj) {
        if (this.mJsModuleCallback != null) {
            this.mJsModuleCallback.onModuleSetter(j, str, i, obj);
        }
    }

    public final void startService(@NonNull String str, @NonNull String str2, @NonNull Object obj, int i, String str3, String str4) {
        nativeStartService(str, this.mShadow, str2, str2, obj, i, str3, str4);
    }

    public final void stopService(@NonNull String str) {
        nativeStopService(this.mShadow, str);
    }
}
