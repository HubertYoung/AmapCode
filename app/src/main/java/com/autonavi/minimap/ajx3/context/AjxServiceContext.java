package com.autonavi.minimap.ajx3.context;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.AjxConfig;
import com.autonavi.minimap.ajx3.AjxEngineProvider;
import com.autonavi.minimap.ajx3.IHandleBackPressedView;
import com.autonavi.minimap.ajx3.IPageLifeCircleView;
import com.autonavi.minimap.ajx3.JsRunInfo;
import com.autonavi.minimap.ajx3.analyzer.IUiEventAnalyzer;
import com.autonavi.minimap.ajx3.core.EventInfo;
import com.autonavi.minimap.ajx3.core.JsContextRef;
import com.autonavi.minimap.ajx3.dom.AjxDomTree;
import com.autonavi.minimap.ajx3.dom.JsDomEvent;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import java.util.HashMap;

public class AjxServiceContext implements IAjxContext {
    private AjxEngineProvider mAjxEngineProvider;
    private HashMap<String, Integer> mBundlesIndex = new HashMap<>();
    private String mBundlesIndexString;
    private Context mContext;
    private AjxContextHandlerHelper mHandlerHelper;
    private boolean mHasRuntimeException = false;
    private JsContextRef mJsContextRef;
    private final int mPatchIndex;
    private IUiEventAnalyzer mUiEventAnalyzer;

    public void addHandleBackPressedView(IHandleBackPressedView iHandleBackPressedView) {
    }

    public void addPageLifeCircleView(IPageLifeCircleView iPageLifeCircleView) {
    }

    public AjxDomTree getDomTree() {
        return null;
    }

    public String getJsPath() {
        return null;
    }

    public JsRunInfo getJsRunInfo() {
        return null;
    }

    public Object getRunParam(String str) {
        return null;
    }

    public boolean handleBackPressed() {
        return false;
    }

    public boolean hasDestroy() {
        return false;
    }

    public void invokePageDestroy() {
    }

    public void invokePageResume() {
    }

    public void invokePageStop() {
    }

    public void onNewIntent() {
    }

    public void onUiEvent(JsDomEvent jsDomEvent) {
    }

    public void onUiListEvent(long j, long j2) {
    }

    public void release() {
    }

    public void sendJsMessage(String str) {
    }

    public void setSoftInputMode(int i) {
    }

    public AjxServiceContext(Context context, @NonNull AjxEngineProvider ajxEngineProvider, @NonNull JsContextRef jsContextRef, int i, String str, HashMap<String, Integer> hashMap) {
        this.mContext = context;
        this.mAjxEngineProvider = ajxEngineProvider;
        this.mJsContextRef = jsContextRef;
        this.mPatchIndex = i;
        this.mBundlesIndexString = str;
        this.mBundlesIndex.putAll(hashMap);
        this.mHandlerHelper = new AjxContextHandlerHelper(this.mContext);
    }

    public JsContextRef getJsContext() {
        return this.mJsContextRef;
    }

    public Context getNativeContext() {
        return this.mContext;
    }

    public int getPatchIndex() {
        return this.mPatchIndex;
    }

    public String getPatchIndexString() {
        return this.mBundlesIndexString;
    }

    public int getPatchIndex(String str) {
        if (TextUtils.isEmpty(str) || !this.mBundlesIndex.containsKey(str)) {
            return 0;
        }
        return this.mBundlesIndex.get(str).intValue();
    }

    public AjxConfig getAjxConfig() {
        return this.mAjxEngineProvider.getAjxConfig();
    }

    public void destroy() {
        this.mUiEventAnalyzer = null;
        this.mHandlerHelper.destroy();
    }

    public void invokeJsEvent(String str, long j, Parcel parcel, Parcel parcel2) {
        this.mJsContextRef.invokeEvent(str, j, parcel, parcel2);
    }

    public void invokeJsEvent(EventInfo eventInfo) {
        this.mJsContextRef.invokeEvent(eventInfo);
    }

    public void setAttribute(long j, String str, String str2) {
        this.mJsContextRef.setAttribute(j, str, str2);
    }

    public void setRuntimeException(String str) {
        this.mHasRuntimeException = true;
    }

    public boolean hasRuntimeException() {
        return this.mHasRuntimeException;
    }

    public long getId() {
        return this.mJsContextRef.shadow();
    }

    public void setUiEventAnalyzer(IUiEventAnalyzer iUiEventAnalyzer) {
        this.mUiEventAnalyzer = iUiEventAnalyzer;
    }

    public IUiEventAnalyzer getUiEventAnalyzer() {
        return this.mUiEventAnalyzer;
    }

    public boolean post(AjxContextHandlerCallback ajxContextHandlerCallback, Message message, long j) {
        return this.mHandlerHelper.post(ajxContextHandlerCallback, message, j);
    }
}
