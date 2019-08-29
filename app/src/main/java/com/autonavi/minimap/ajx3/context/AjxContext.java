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
import com.autonavi.minimap.ajx3.core.PageConfig;
import com.autonavi.minimap.ajx3.dom.AjxDomTree;
import com.autonavi.minimap.ajx3.dom.JsDomEvent;
import com.autonavi.minimap.ajx3.dom.JsListEvent;
import com.autonavi.minimap.ajx3.log.LifecycleLogAction;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.widget.AjxView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class AjxContext implements IAjxContext {
    private static final String DEFAULT_BUNDLE_NAME = "unresolved";
    private AjxDomTree mAjxDomTree;
    private AjxView mAjxView;
    private HashMap<String, Integer> mBundlesIndex = new HashMap<>();
    private String mBundlesIndexString;
    private Context mContext;
    private HashSet<WeakReference<IHandleBackPressedView>> mHandleBackPressedViews = new HashSet<>();
    private AjxContextHandlerHelper mHandlerHelper;
    private boolean mHasRuntimeException = false;
    private boolean mIsDestroy = false;
    private JsContextRef mJsContext;
    private AjxEngineProvider mJsEngineProvider;
    private JsRunInfo mJsRunInfo;
    private HashSet<WeakReference<IPageLifeCircleView>> mPageLifeCircleViews = new HashSet<>();
    private final int mPatchIndex;
    private IUiEventAnalyzer mUiEventAnalyzer;
    private int mUiEventBatch = 0;
    private long mUiEventLastTime;

    public AjxContext(@NonNull AjxView ajxView, @NonNull AjxEngineProvider ajxEngineProvider, @NonNull JsContextRef jsContextRef, @NonNull JsRunInfo jsRunInfo, int i, String str, HashMap<String, Integer> hashMap) {
        this.mAjxView = ajxView;
        this.mJsEngineProvider = ajxEngineProvider;
        this.mJsContext = jsContextRef;
        this.mContext = ajxView.getContext();
        this.mAjxDomTree = new AjxDomTree(this, ajxView);
        this.mJsRunInfo = jsRunInfo;
        this.mPatchIndex = i;
        this.mBundlesIndexString = str;
        this.mBundlesIndex.putAll(hashMap);
        this.mHandlerHelper = new AjxContextHandlerHelper(this.mContext);
    }

    public JsContextRef getJsContext() {
        return this.mJsContext;
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
        if (!TextUtils.isEmpty(str) && this.mBundlesIndex.containsKey(str)) {
            return this.mBundlesIndex.get(str).intValue();
        }
        if (this.mBundlesIndex.containsKey(DEFAULT_BUNDLE_NAME)) {
            return this.mBundlesIndex.get(DEFAULT_BUNDLE_NAME).intValue();
        }
        return 0;
    }

    public void onUiEvent(JsDomEvent jsDomEvent) {
        if (!this.mIsDestroy) {
            System.currentTimeMillis();
            do {
                this.mAjxDomTree.bind(jsDomEvent);
                jsDomEvent = jsDomEvent.getNext();
            } while (jsDomEvent != null);
        }
    }

    private void onUiEventFinish(long j) {
        IUiEventAnalyzer uiEventAnalyzer = getUiEventAnalyzer();
        if (uiEventAnalyzer != null) {
            uiEventAnalyzer.onUiEventFinish(this, this.mUiEventBatch);
        }
        this.mUiEventBatch++;
        this.mUiEventLastTime = j;
    }

    public AjxDomTree getDomTree() {
        return this.mAjxDomTree;
    }

    public void onUiListEvent(long j, long j2) {
        if (!this.mIsDestroy) {
            while (j2 != 0) {
                JsListEvent jsListEvent = new JsListEvent(j2);
                this.mAjxDomTree.getAjxStricyListManager().bindStrictly(jsListEvent);
                j2 = jsListEvent.getPtrNextEvent();
            }
        }
    }

    public void setSoftInputMode(int i) {
        this.mAjxView.setSoftInputMode(i);
    }

    public void sendJsMessage(String str) {
        if (!this.mIsDestroy && this.mJsContext != null) {
            this.mJsContext.sendMessage(str);
        }
    }

    public boolean hasDestroy() {
        return this.mIsDestroy;
    }

    public void destroy() {
        this.mUiEventAnalyzer = null;
        this.mIsDestroy = true;
        invokePageDestroy();
        LogManager.lifecycleLog(LifecycleLogAction.PAGE_WILL_DESTROY);
        this.mJsEngineProvider.invokeJsContextDestroyEvent(this.mJsContext);
        this.mHandlerHelper.destroy();
    }

    public void release() {
        this.mAjxDomTree.destroy();
        this.mJsEngineProvider.destroyAjxContext(getJsContext().shadow());
        this.mContext = null;
        LogManager.lifecycleLog(LifecycleLogAction.PAGE_DID_DESTROY);
    }

    public AjxConfig getAjxConfig() {
        return this.mJsEngineProvider.getAjxConfig();
    }

    public String getJsPath() {
        PageConfig pageConfig = this.mJsRunInfo.getPageConfig();
        if (pageConfig != null) {
            String url = pageConfig.getUrl();
            if (!TextUtils.isEmpty(url)) {
                return url;
            }
        }
        return this.mJsRunInfo.getUrl();
    }

    public JsRunInfo getJsRunInfo() {
        return this.mJsRunInfo;
    }

    @Deprecated
    public void invokeJsEvent(String str, long j, Parcel parcel, Parcel parcel2) {
        this.mJsContext.invokeEvent(str, j, parcel, parcel2);
    }

    public void invokeJsEvent(EventInfo eventInfo) {
        this.mJsContext.invokeEvent(eventInfo);
    }

    public void setAttribute(long j, String str, String str2) {
        this.mJsContext.setAttribute(j, str, str2);
    }

    public void addPageLifeCircleView(IPageLifeCircleView iPageLifeCircleView) {
        if (iPageLifeCircleView != null) {
            this.mPageLifeCircleViews.add(new WeakReference(iPageLifeCircleView));
        }
    }

    public void addHandleBackPressedView(IHandleBackPressedView iHandleBackPressedView) {
        if (iHandleBackPressedView != null) {
            this.mHandleBackPressedViews.add(new WeakReference(iHandleBackPressedView));
        }
    }

    public void onNewIntent() {
        Iterator<WeakReference<IPageLifeCircleView>> it = this.mPageLifeCircleViews.iterator();
        ArrayList arrayList = null;
        while (it.hasNext()) {
            WeakReference next = it.next();
            IPageLifeCircleView iPageLifeCircleView = (IPageLifeCircleView) next.get();
            if (iPageLifeCircleView != null) {
                iPageLifeCircleView.onNewIntent();
            } else {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(next);
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.mPageLifeCircleViews.remove((WeakReference) it2.next());
            }
        }
    }

    public void invokePageResume() {
        Iterator<WeakReference<IPageLifeCircleView>> it = this.mPageLifeCircleViews.iterator();
        ArrayList arrayList = null;
        while (it.hasNext()) {
            WeakReference next = it.next();
            IPageLifeCircleView iPageLifeCircleView = (IPageLifeCircleView) next.get();
            if (iPageLifeCircleView != null) {
                iPageLifeCircleView.onPageResume();
            } else {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(next);
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.mPageLifeCircleViews.remove((WeakReference) it2.next());
            }
        }
    }

    public void invokePageStop() {
        Iterator<WeakReference<IPageLifeCircleView>> it = this.mPageLifeCircleViews.iterator();
        ArrayList arrayList = null;
        while (it.hasNext()) {
            WeakReference next = it.next();
            IPageLifeCircleView iPageLifeCircleView = (IPageLifeCircleView) next.get();
            if (iPageLifeCircleView != null) {
                iPageLifeCircleView.onPageStop();
            } else {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(next);
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.mPageLifeCircleViews.remove((WeakReference) it2.next());
            }
        }
    }

    public void invokePageDestroy() {
        Iterator<WeakReference<IPageLifeCircleView>> it = this.mPageLifeCircleViews.iterator();
        ArrayList arrayList = null;
        while (it.hasNext()) {
            WeakReference next = it.next();
            IPageLifeCircleView iPageLifeCircleView = (IPageLifeCircleView) next.get();
            if (iPageLifeCircleView != null) {
                iPageLifeCircleView.onPageDestroy();
            } else {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(next);
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.mPageLifeCircleViews.remove((WeakReference) it2.next());
            }
        }
    }

    public boolean handleBackPressed() {
        Iterator<WeakReference<IHandleBackPressedView>> it = this.mHandleBackPressedViews.iterator();
        boolean z = false;
        ArrayList arrayList = null;
        while (it.hasNext()) {
            WeakReference next = it.next();
            IHandleBackPressedView iHandleBackPressedView = (IHandleBackPressedView) next.get();
            if (iHandleBackPressedView != null) {
                z = iHandleBackPressedView.onBackPressed();
                if (z) {
                    break;
                }
            } else {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(next);
            }
        }
        if (arrayList != null && arrayList.size() > 0) {
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                this.mHandleBackPressedViews.remove((WeakReference) it2.next());
            }
        }
        return z;
    }

    public void setRuntimeException(String str) {
        this.mHasRuntimeException = true;
    }

    public boolean hasRuntimeException() {
        return this.mHasRuntimeException;
    }

    public long getId() {
        return getJsContext().shadow();
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

    public Object getRunParam(String str) {
        HashMap<String, Object> runParams = this.mJsRunInfo.getRunParams();
        if (runParams == null) {
            return null;
        }
        return runParams.get(str);
    }
}
