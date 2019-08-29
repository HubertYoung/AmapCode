package com.autonavi.minimap.ajx3.core;

import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import java.util.ArrayList;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class JsContextRef {
    public static final int RESIZE_REASON_COMMON = 0;
    public static final int RESIZE_REASON_ORIRNTATION = 1;
    private ArrayList<String> mLocalStringPool = new ArrayList<>();
    private long mShadow;

    private static native void nativeDebugCommand(String str);

    private native void nativeFinishRun(long j);

    private native void nativeHardwareBack(long j);

    private native void nativeHidePage(long j, boolean z);

    private native void nativeInvokeAnimation(long j, long j2, String str, String str2);

    private native void nativeInvokeEvent(long j, String str, long j2, long j3, long j4, Parcel parcel, Parcel parcel2, JSONObject jSONObject);

    private native void nativeInvokeRelativeAnimation(long j, long j2, String str, String str2, String str3);

    private native boolean nativeIsRunOnUI(long j);

    private native void nativeOnNewIntent(long j, Object obj);

    private native void nativePageBecomeActive(long j);

    private native void nativePageResignActive(long j);

    private native void nativeResize(long j, float f, float f2, int i);

    private native void nativeSendMessage(long j, String str);

    private native void nativeSetAttribute(long j, long j2, String str, String str2);

    private native void nativeShowPage(long j, boolean z, Object obj);

    public JsContextRef(long j) {
        this.mShadow = j;
    }

    private int putToStringPool(String str) {
        this.mLocalStringPool.add(str);
        return this.mLocalStringPool.size() - 1;
    }

    private String getFromStringPool(int i) {
        if (this.mLocalStringPool == null || i < 0 || i >= this.mLocalStringPool.size()) {
            return null;
        }
        return this.mLocalStringPool.get(i);
    }

    public long shadow() {
        return this.mShadow;
    }

    public boolean isRunOnUI() {
        return nativeIsRunOnUI(this.mShadow);
    }

    public void finishRun() {
        nativeFinishRun(this.mShadow);
    }

    public void resize(float f, float f2, int i) {
        nativeResize(this.mShadow, f, f2, i);
    }

    @Deprecated
    public void invokeEvent(String str, long j, Parcel parcel, Parcel parcel2) {
        if (j != -1) {
            nativeInvokeEvent(this.mShadow, str, j, j, -1, parcel, parcel2, null);
            return;
        }
    }

    public void invokeEvent(EventInfo eventInfo) {
        if (eventInfo.getNodeId() != -1) {
            nativeInvokeEvent(this.mShadow, eventInfo.getEventName(), eventInfo.getNodeId(), eventInfo.getAltNodeId(), eventInfo.getHoverNodeId(), eventInfo.getAttribute(), eventInfo.getTouch(), eventInfo.getContent());
        }
    }

    public void setAttribute(long j, String str, String str2) {
        nativeSetAttribute(this.mShadow, j, str, str2);
    }

    public void invokeAnimation(long j, String str, String str2) {
        nativeInvokeAnimation(this.mShadow, j, str, str2);
    }

    public void invokeRelativeAnimation(long j, String str, String str2, String str3) {
        nativeInvokeRelativeAnimation(this.mShadow, j, str, str2, str3);
    }

    public void sendMessage(String str) {
        nativeSendMessage(this.mShadow, str);
    }

    public void showPage(boolean z, Object obj) {
        nativeShowPage(this.mShadow, z, obj);
        LogManager.lifecycleLog("page_show");
    }

    public void onNewIntent(Object obj) {
        nativeOnNewIntent(this.mShadow, obj);
    }

    public void hidePage(boolean z) {
        nativeHidePage(this.mShadow, z);
        LogManager.lifecycleLog("page_hide");
    }

    public void pageBecomeActive() {
        nativePageBecomeActive(this.mShadow);
    }

    public void pageResignActive() {
        nativePageResignActive(this.mShadow);
    }

    public static void debugCommand(String str) {
        nativeDebugCommand(str);
    }

    public void hardwareBack() {
        nativeHardwareBack(this.mShadow);
    }
}
