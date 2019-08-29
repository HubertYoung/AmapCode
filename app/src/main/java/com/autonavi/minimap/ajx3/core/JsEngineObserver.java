package com.autonavi.minimap.ajx3.core;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface JsEngineObserver {

    public interface InspectorHandler {
        void handleMessage(long j, String str);
    }

    String getAllBundlesIndexSnapshot();

    JsContextObserver getJsServiceContextObserver();

    int getPatchIndex(String str);

    void onEngineDestroyed();

    void onEngineInitialized(int i);

    void onJsCodeCoverageDataCollection(String str, Object obj, String str2);

    void onLog(int i, String str);

    void onLogPrint(String str, String str2, int i);

    void onReceiveInspectorMessage(long j, String str);

    void onRuntimeException(long j, int i, String str, String str2);

    JsContextRef onServiceCreated(long j, String str);

    void onServiceDestroyed(long j);

    void onStartWaittingDebugger(int i, long j, String str);
}
