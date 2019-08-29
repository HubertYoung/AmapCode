package com.autonavi.minimap.ajx3.context;

import android.content.Context;
import android.os.Message;
import com.autonavi.minimap.ajx3.AjxConfig;
import com.autonavi.minimap.ajx3.IHandleBackPressedView;
import com.autonavi.minimap.ajx3.IPageLifeCircleView;
import com.autonavi.minimap.ajx3.JsRunInfo;
import com.autonavi.minimap.ajx3.analyzer.IUiEventAnalyzer;
import com.autonavi.minimap.ajx3.core.EventInfo;
import com.autonavi.minimap.ajx3.core.JsContextRef;
import com.autonavi.minimap.ajx3.dom.AjxDomTree;
import com.autonavi.minimap.ajx3.dom.JsDomEvent;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;

public interface IAjxContext {
    void addHandleBackPressedView(IHandleBackPressedView iHandleBackPressedView);

    void addPageLifeCircleView(IPageLifeCircleView iPageLifeCircleView);

    void destroy();

    AjxConfig getAjxConfig();

    AjxDomTree getDomTree();

    long getId();

    JsContextRef getJsContext();

    String getJsPath();

    JsRunInfo getJsRunInfo();

    Context getNativeContext();

    int getPatchIndex();

    int getPatchIndex(String str);

    String getPatchIndexString();

    Object getRunParam(String str);

    IUiEventAnalyzer getUiEventAnalyzer();

    boolean handleBackPressed();

    boolean hasDestroy();

    boolean hasRuntimeException();

    void invokeJsEvent(EventInfo eventInfo);

    @Deprecated
    void invokeJsEvent(String str, long j, Parcel parcel, Parcel parcel2);

    void invokePageDestroy();

    void invokePageResume();

    void invokePageStop();

    void onNewIntent();

    void onUiEvent(JsDomEvent jsDomEvent);

    void onUiListEvent(long j, long j2);

    boolean post(AjxContextHandlerCallback ajxContextHandlerCallback, Message message, long j);

    void release();

    void sendJsMessage(String str);

    void setAttribute(long j, String str, String str2);

    void setRuntimeException(String str);

    void setSoftInputMode(int i);

    void setUiEventAnalyzer(IUiEventAnalyzer iUiEventAnalyzer);
}
