package com.alipay.android.phone.wallet.tinytracker;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import com.alipay.android.phone.wallet.spmtracker.Constant;
import com.alipay.android.phone.wallet.spmtracker.SpmTracker;
import com.alipay.mobile.common.logging.api.LogDAUTracker;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class TinyTrackIntegrator {
    public static final String END_SEPARATOR_CHAR = "_";
    static final int MAX_TRACE_STEP = 5;
    public static final int PAGE_INFO_MAX_SIZE = 30;
    public static final String SEPARATOR_CHAR = "__";
    private static final String b = TinyTrackIntegrator.class.getSimpleName();
    private static TinyTrackIntegrator c;
    private static Handler d = new Handler(Looper.getMainLooper());
    private int a = 621215851;
    /* access modifiers changed from: private */
    public Map<String, PageInfo> e = new ConcurrentHashMap();
    private ReferenceQueue<WeakReference> f = new ReferenceQueue<>();
    private Map<WeakReference, String> g = new ConcurrentHashMap();
    private PageInfo h;
    private boolean i;
    private String j;
    private int k;
    private String l;
    public String lastClickViewSpm = "";
    private String m;
    private PendingTraceParams n;

    private class PendingTraceParams {
        /* access modifiers changed from: private */
        public String a;
        /* access modifiers changed from: private */
        public String b;
        /* access modifiers changed from: private */
        public int c;

        public PendingTraceParams(String key, String params, int tracestep) {
            this.a = key;
            this.b = params;
            this.c = tracestep;
        }
    }

    public static synchronized TinyTrackIntegrator getInstance() {
        TinyTrackIntegrator tinyTrackIntegrator;
        synchronized (TinyTrackIntegrator.class) {
            try {
                if (c == null) {
                    c = new TinyTrackIntegrator();
                }
                tinyTrackIntegrator = c;
            }
        }
        return tinyTrackIntegrator;
    }

    public void setmIsLeaveHint(boolean isLeaveHint) {
        this.i = isLeaveHint;
    }

    public void logPageStartWithSpmId(String spm, Object view) {
        String str = null;
        boolean z = true;
        if (view == null || TextUtils.isEmpty(spm)) {
            LoggerFactory.getTraceLogger().info(b, "Start_view is null or spm is null");
            return;
        }
        final String key = SpmUtils.getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(b, "Start_view.toString() is null");
            return;
        }
        PageInfo lastInfo = this.e.get(key);
        if (lastInfo == null || lastInfo.isEnd) {
            PageInfo pageInfo = lastInfo;
            if (lastInfo == null) {
                c();
                if (!TinyPageMonitor.isH5Page(view)) {
                    this.g.put(new WeakReference(view, this.f), key);
                }
                pageInfo = new PageInfo();
                pageInfo.pageKey = key;
                if (this.h != null) {
                    pageInfo.referPageInfo = PageInfo.clonePageInfo(this.h);
                }
                if (this.h == null || !this.h.spm.equals(spm)) {
                    z = false;
                }
                pageInfo.pageRefresh = z;
                if (pageInfo.pageRefresh) {
                    a(pageInfo, this.h);
                    a();
                    pageInfo.refer2 = this.h == null ? null : this.h.refer2;
                    cleanNextPageNewChinfo();
                } else {
                    a(pageInfo);
                    if (this.h == null || "first".equals(this.h.refer)) {
                        pageInfo.refer2 = null;
                    } else {
                        pageInfo.refer2 = this.h.refer;
                    }
                }
                TrackerHelper.instance.onPageCreate(view, pageInfo);
                b();
            } else {
                pageInfo.isEnd = false;
                pageInfo.pageBack = false;
                pageInfo.pageRefresh = false;
                pageInfo.multistepBack = false;
                pageInfo.pageRepeat = false;
                a();
                cleanNextPageNewChinfo();
                if (pageInfo == this.h) {
                    pageInfo.pageRepeat = true;
                    b();
                } else if (TrackerHelper.instance.checkIsPageBack(view)) {
                    pageInfo.pageBack = true;
                } else if (TrackerHelper.instance.checkIsMultistepBack(view)) {
                    pageInfo.multistepBack = true;
                } else {
                    pageInfo.tabSwitch = true;
                }
                TrackerHelper.instance.onPageResume(view);
            }
            pageInfo.refer = pageInfo.getRefer();
            if (this.h != null) {
                str = this.h.spm + MergeUtil.SEPARATOR_KV + this.h.pageId;
            }
            pageInfo.lastPage = str;
            pageInfo.pageStartTime10 = System.currentTimeMillis();
            pageInfo.pageStartTime64 = SpmUtils.c10to64(pageInfo.pageStartTime10);
            pageInfo.pageId = spm + "__" + LoggerFactory.getLogContext().getDeviceId() + "__" + pageInfo.pageStartTime64 + "_";
            pageInfo.spm = spm;
            pageInfo.miniPageId = spm + "__" + pageInfo.pageStartTime64 + "_";
            pageInfo.referClickSpm = getLastClickViewSpm();
            this.e.put(key, pageInfo);
            this.h = pageInfo;
            if (!pageInfo.pageRepeat && !pageInfo.pageBack && !pageInfo.multistepBack && !pageInfo.pageRefresh && !pageInfo.tabSwitch) {
                TrackerParams trackerParams = TrackerHelper.instance.getTrackerParams(view);
                if (this.j != null) {
                    setPageParams(this.j, view, this.k);
                    a();
                } else if (!(trackerParams == null || trackerParams.pageParams == null)) {
                    setPageParams(trackerParams.pageParams, view, trackerParams.tracestep);
                }
                if (this.l != null) {
                    setPageNewChinfo(view, this.l, this.m);
                    cleanNextPageNewChinfo();
                }
            }
            LoggerFactory.getTraceLogger().info(b, "page start " + key + " name = " + view.getClass().getName() + " spm = " + spm);
            try {
                if (view instanceof View) {
                    ((View) view).addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                        public void onViewDetachedFromWindow(View view) {
                            TinyTrackIntegrator.this.e.remove(key);
                        }

                        public void onViewAttachedToWindow(View view) {
                        }
                    });
                }
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error(b, e2);
            }
            TinyPageMonitor.INTANCE.setmTopPage(view);
            try {
                if (this.n != null) {
                    if (this.n.a.equals(key)) {
                        setPageParams(this.n.b, view, this.n.c);
                    } else {
                        LoggerFactory.getTraceLogger().info(b, "pendingSetPageParams not match, pending key: " + this.n.a + ", current key: " + key);
                    }
                    this.n = null;
                }
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error(b, "pendingSetPageParams error", t);
            }
        } else {
            LoggerFactory.getTraceLogger().info(b, "Start_not call end,and start twice,update spm");
            a(lastInfo, spm);
        }
    }

    private void a(PageInfo pageInfo) {
        if (this.h != null) {
            for (int i2 = 0; i2 < this.h.traceParams.length; i2++) {
                if (i2 + 1 < this.h.traceSteps[i2]) {
                    pageInfo.traceParams[i2 + 1] = this.h.traceParams[i2];
                    pageInfo.traceSteps[i2 + 1] = this.h.traceSteps[i2];
                }
            }
        }
    }

    private static void a(PageInfo pageInfo, PageInfo mCurrentPageInfo) {
        if (mCurrentPageInfo != null) {
            pageInfo.traceParams = mCurrentPageInfo.traceParams;
            pageInfo.traceSteps = mCurrentPageInfo.traceSteps;
        }
    }

    public PageInfo logPageEndWithSpmId(String spm, Object view, String bizCode, HashMap<String, String> map) {
        if (view == null || TextUtils.isEmpty(spm)) {
            LoggerFactory.getTraceLogger().info(b, "End_View is null or spm is null");
            return null;
        }
        String key = SpmUtils.getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(b, "End_view.toString() is null");
            return null;
        }
        PageInfo pageInfo = this.e.get(key);
        if (pageInfo == null) {
            LoggerFactory.getTraceLogger().info(b, "End_pageInfo is null");
            return null;
        } else if (pageInfo.isEnd) {
            LoggerFactory.getTraceLogger().info(b, "is already call pageEnd");
            return pageInfo;
        } else {
            pageInfo.isEnd = true;
            Behavor behavor = new Behavor();
            boolean isUploadToday = false;
            if (LoggerFactory.getLogContext().getLogDAUTracker() != null) {
                isUploadToday = LoggerFactory.getLogContext().getLogDAUTracker().isUploadedToday(pageInfo.spm);
                if (!isUploadToday) {
                    behavor.setRenderBizType(bizCode);
                    behavor.addExtParam("kDAUTag", "Y");
                    bizCode = LogDAUTracker.BIZ_TYPE;
                }
            }
            behavor.setParam1(pageInfo.refer);
            long stayTime = System.currentTimeMillis() - pageInfo.pageStartTime10;
            pageInfo.pageStayTime = stayTime;
            behavor.setParam2(String.valueOf(stayTime));
            behavor.setParam3(pageInfo.pageStartTime64);
            behavor.setPageId(pageInfo.pageId);
            behavor.setBehaviourPro(bizCode);
            behavor.setSeedID(pageInfo.spm);
            if (map != null) {
                for (Entry entry : map.entrySet()) {
                    try {
                        behavor.addExtParam((String) entry.getKey(), (String) entry.getValue());
                    } catch (Throwable e2) {
                        LoggerFactory.getTraceLogger().error(b, e2);
                    }
                }
            }
            a(behavor, pageInfo, view);
            LoggerFactory.getBehavorLogger().event("pageMonitor", behavor);
            if (LoggerFactory.getLogContext().getLogDAUTracker() != null && !isUploadToday) {
                LoggerFactory.getLogContext().getLogDAUTracker().updateSpmUploadState(pageInfo.spm);
            }
            TrackerHelper.instance.onPagePause(view);
            this.i = false;
            return pageInfo;
        }
    }

    /* access modifiers changed from: 0000 */
    public void pageOnDestroy(final String key) {
        if (key != null && this.e.get(key) != null) {
            d.postDelayed(new Runnable() {
                public void run() {
                    TinyTrackIntegrator.this.e.remove(key);
                }
            }, 1000);
        }
    }

    public String getLastClickViewSpm() {
        Log.e(b, "getLastClickViewSpm");
        return this.lastClickViewSpm;
    }

    public PageInfo getPageInfoByView(Object view) {
        if (view == null) {
            LoggerFactory.getTraceLogger().info(b, "getPageInfoByView is null or spm is null");
            return null;
        }
        String key = SpmUtils.getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(b, "getPageInfoByView() is null");
            return null;
        }
        PageInfo pageInfo = this.e.get(key);
        if (pageInfo != null) {
            return pageInfo;
        }
        LoggerFactory.getTraceLogger().info(b, "getPageInfoByView view is null");
        return null;
    }

    public PageInfo getPageMonitorCurrentPageInfo() {
        return this.h;
    }

    private static void a(PageInfo lastInfo, String spm) {
        if (lastInfo == null || TextUtils.isEmpty(spm)) {
            LoggerFactory.getTraceLogger().info(b, "updateLastInfoSpm spm or lastInfo is null");
            return;
        }
        lastInfo.spm = spm;
        if (lastInfo.referPageInfo != null && spm.equals(lastInfo.referPageInfo.spm)) {
            lastInfo.pageRefresh = true;
            a(lastInfo, lastInfo.referPageInfo);
            lastInfo.refer2 = lastInfo.referPageInfo.refer2;
        }
    }

    public void setPageParams(String params, Object view, int tracestep) {
        LoggerFactory.getTraceLogger().info(b, "setPageParams, params: " + params + ", step: " + tracestep);
        if (!TextUtils.isEmpty(params) && view != null) {
            if (tracestep > 5) {
                LoggerFactory.getTraceLogger().info(b, "setPageParams, step too large, set to 5, step: " + tracestep);
                tracestep = 5;
            } else if (tracestep < 3) {
                LoggerFactory.getTraceLogger().info(b, "setPageParams, step too small, set to 3, step: " + tracestep);
                tracestep = 3;
            }
            String key = SpmUtils.getViewKey(view);
            PageInfo pageInfo = this.e.get(key);
            if (pageInfo == null) {
                this.n = new PendingTraceParams(key, params, tracestep);
                LoggerFactory.getTraceLogger().error(b, (String) "setPageParams, pageInfo not exist, pending!");
            } else if (pageInfo != this.h) {
                LoggerFactory.getTraceLogger().error(b, (String) "setPageParams, pageInfo do not match current page !");
            } else {
                String pageParamRoot = pageInfo.traceParams[0];
                if (pageParamRoot == null) {
                    String targetPageParam = SpmUtils.refreshParam(params, pageParamRoot);
                    if (!TextUtils.isEmpty(targetPageParam)) {
                        pageInfo.traceParams[0] = targetPageParam;
                        pageInfo.traceSteps[0] = tracestep;
                    }
                }
            }
        }
    }

    private void a() {
        this.j = null;
    }

    private void b() {
        Pair pageParamsPair = SpmTracker.getNextPageParams();
        if (pageParamsPair != null) {
            this.j = (String) pageParamsPair.first;
            this.k = ((Integer) pageParamsPair.second).intValue();
        }
        Pair nextNewChinfo = SpmTracker.getNextPageNewChinfo();
        if (nextNewChinfo != null) {
            this.l = (String) nextNewChinfo.first;
            this.m = (String) nextNewChinfo.second;
        }
    }

    /* access modifiers changed from: 0000 */
    public void cleanNextPageNewChinfo() {
        this.l = null;
        this.m = null;
    }

    private static Map<String, String> b(PageInfo pageInfo) {
        Map result = null;
        if (pageInfo == null) {
            LoggerFactory.getTraceLogger().info(b, "getPageParams pageInfo is null");
        } else if (pageInfo.traceParams == null) {
            LoggerFactory.getTraceLogger().info(b, "getPageParams traceParams is null");
        } else {
            result = new HashMap();
            for (int step = 0; step < pageInfo.traceParams.length; step++) {
                if (pageInfo.traceParams[step] != null) {
                    if (step == 0) {
                        result.put("p-root", pageInfo.traceParams[step]);
                    } else if (step == 1) {
                        result.put("p-pre", pageInfo.traceParams[step]);
                    } else {
                        result.put("p-pre" + step, pageInfo.traceParams[step]);
                    }
                }
            }
        }
        return result;
    }

    private void a(Behavor behavor, PageInfo pageInfo, Object page) {
        Map pageParams = b(pageInfo);
        if (pageParams != null) {
            behavor.getExtParams().putAll(pageParams);
        }
        if (!TextUtils.isEmpty(pageInfo.refer2)) {
            behavor.addExtParam("refer2", pageInfo.refer2);
        }
        if (pageInfo.pageRepeat) {
            behavor.addExtParam("pageRepeat", "1");
        }
        if (pageInfo.multistepBack) {
            behavor.addExtParam("multistepBack", "1");
        }
        if (pageInfo.pageRefresh) {
            behavor.addExtParam("pageRefresh", "1");
        }
        if (!"first".equals(pageInfo.refer)) {
            behavor.addExtParam("spmtracker_refer_page", pageInfo.refer);
        }
        if (!TextUtils.isEmpty(pageInfo.lastPage)) {
            behavor.addExtParam("spmtracker_last_page", pageInfo.lastPage);
        }
        behavor.addExtParam5("isTinyTracker", "1");
        behavor.addExtParam(Constant.KEY_PAGEBACK, pageInfo.pageBack ? "1" : "0");
        behavor.addExtParam(Constant.KEY_FROMHOME, this.i ? "1" : "0");
        behavor.addExtParam("srcSpm", pageInfo.srcSpm == null ? "" : pageInfo.srcSpm);
        behavor.addExtParam(Constant.KEY_REFER_SPM, pageInfo.referClickSpm == null ? "" : pageInfo.referClickSpm);
        TrackerParams trackerParams = TrackerHelper.instance.getTrackerParams(page);
        if (trackerParams != null) {
            if (!TextUtils.isEmpty(trackerParams.chInfo) && !behavor.getExtParams().containsKey("chInfo")) {
                behavor.addExtParam("chInfo", trackerParams.chInfo);
            }
            if (!TinyPageMonitor.isH5Page(page) && !TextUtils.isEmpty(trackerParams.lanInfo)) {
                behavor.addExtParam(Constant.KEY_LANINFO, trackerParams.lanInfo);
            }
        }
        if (pageInfo.newChinfo != null) {
            behavor.addExtParam5(Constant.KEY_NEW_CHINFO, pageInfo.newChinfo);
        }
        if (pageInfo.scm != null) {
            behavor.addExtParam5("scm", pageInfo.scm);
        }
    }

    private void c() {
        if (this.g.size() > 30) {
            while (true) {
                WeakReference weakPage = (WeakReference) this.f.poll();
                if (weakPage != null) {
                    String pageKey = this.g.get(weakPage);
                    TrackerHelper.instance.onPageDestroy(pageKey);
                    pageOnDestroy(pageKey);
                    this.g.remove(weakPage);
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setPageNewChinfo(Object page, String newChinfo, String scm) {
        PageInfo pageInfo = this.e.get(SpmUtils.getViewKey(page));
        if (pageInfo == null || pageInfo != this.h) {
            LoggerFactory.getTraceLogger().error(b, "setPageNewChinfo, pageInfo not exist or is not current page, pageInfo is null: " + (pageInfo == null));
            return;
        }
        pageInfo.newChinfo = newChinfo;
        pageInfo.scm = scm;
    }
}
