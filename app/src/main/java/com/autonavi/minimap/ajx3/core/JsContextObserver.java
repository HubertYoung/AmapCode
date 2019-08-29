package com.autonavi.minimap.ajx3.core;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface JsContextObserver {
    void onAddLayer(String str, String str2, Object obj);

    void onBack(Object obj, String str);

    void onCommandByInspector(String str);

    void onDestroy(long j);

    void onDismissSub(long j);

    void onGetDebugData(String str);

    void onGetDebugDataForInspector(String str);

    void onInvokeNodeMethod(long j, long j2, String str, Object... objArr);

    void onNodeUniqueId(String str, String str2);

    void onOpen(String str, String str2, Object obj, String str3);

    void onPresentSub(String str, Object obj);

    void onRemoveLayer(String str);

    void onReplace(String str, String str2, Object obj, String str3);

    void onUiEvent(long j, long j2);

    void onUiListEvent(long j, long j2);
}
