package com.autonavi.minimap.ajx3.core;

public abstract class BaseJsServiceContextObserver implements JsContextObserver {
    public void onAddLayer(String str, String str2, Object obj) {
    }

    public final void onBack(Object obj, String str) {
    }

    public final void onCommandByInspector(String str) {
    }

    public final void onDestroy(long j) {
    }

    public final void onDismissSub(long j) {
    }

    public final void onGetDebugData(String str) {
    }

    public final void onGetDebugDataForInspector(String str) {
    }

    public final void onInvokeNodeMethod(long j, long j2, String str, Object... objArr) {
    }

    public final void onNodeUniqueId(String str, String str2) {
    }

    public abstract void onOpen(String str, String str2, Object obj, String str3);

    public final void onPresentSub(String str, Object obj) {
    }

    public void onRemoveLayer(String str) {
    }

    public final void onReplace(String str, String str2, Object obj, String str3) {
    }

    public final void onUiEvent(long j, long j2) {
    }

    public final void onUiListEvent(long j, long j2) {
    }
}
