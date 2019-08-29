package com.alipay.mobile.monitor.track.spm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.alipay.android.phone.wallet.spmtracker.Constant;
import com.alipay.android.phone.wallet.spmtracker.ISpmMonitor;
import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import com.alipay.mobile.common.logging.api.behavor.BehavorID;
import com.alipay.mobile.monitor.track.TrackIntegrator;
import com.alipay.mobile.monitor.track.TrackIntegrator.PageInfo;
import com.alipay.mobile.monitor.track.spm.merge.MergeCenter;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.monitor.track.spm.monitor.TrackerExecutor;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.ClickTracker;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.ExposeTracker;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.MergeTracker;
import com.alipay.mobile.monitor.track.spm.monitor.tracker.SlideTracker;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum SpmMonitor implements ISpmMonitor {
    INTANCE;
    
    private static int pageBack;
    /* access modifiers changed from: private */
    public final String TAG;
    private boolean isDataboardValid;
    private Map<String, String> mChInfoMap;
    /* access modifiers changed from: private */
    public Context mContext;
    private List<String> mHPTabSpmIds;
    /* access modifiers changed from: private */
    public boolean mIsLeaveHint;
    /* access modifiers changed from: private */
    public LeaveHintReceiver mLeaveHintReceiver;
    private WeakReference<Object> mTopPage;
    private TrackerExecutor mTrackExcutor;

    class LeaveHintReceiver extends BroadcastReceiver {
        LeaveHintReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            SpmLogCator.debug(SpmMonitor.this.TAG, "LeaveHintReceiver onReceive:" + intent.getAction());
            SpmMonitor.this.mIsLeaveHint = true;
        }
    }

    static {
        pageBack = 0;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    private void a() {
        try {
            Class.forName("com.alipay.android.phone.wallet.databoard.AdapterForDataboard");
        } catch (ClassNotFoundException e) {
            Log.e(this.TAG, "checkIsDataboardValid E:" + e.toString());
            this.isDataboardValid = false;
        }
    }

    public void setIsDebug(boolean isDebug) {
        SpmUtils.isDebug = isDebug;
    }

    public void pageOnCreate(Object page, String spmId) {
        SpmLogCator.debug(this.TAG, "pageOnCreate spmId:" + spmId + ";window:" + page);
        this.mTopPage = new WeakReference<>(page);
        if (!TextUtils.isEmpty(spmId)) {
            TrackIntegrator.getInstance().logPageStartWithSpmId(spmId, page);
            e(page);
            d(page);
        }
    }

    public void pageOnResume(Object page, String spmId) {
        SpmLogCator.debug(this.TAG, "pageOnResume spmId:" + spmId + ";window:" + page);
        if (!TextUtils.isEmpty(spmId)) {
            a(page);
            TrackIntegrator.getInstance().logPageStartWithSpmId(spmId, page);
            PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
            if (pageInfo != null && TextUtils.isEmpty(pageInfo.srcSpm)) {
                d(page);
            }
        }
        this.mTopPage = new WeakReference<>(page);
    }

    public void pageOnPause(Object page, String spmId, String bizCode, Map<String, String> map, String chInfo) {
        SpmLogCator.debug(this.TAG, "pageOnPause spmId:" + spmId + ";window:" + page + ";chInfo:" + chInfo);
        if (!TextUtils.isEmpty(spmId)) {
            if (TextUtils.isEmpty(chInfo) && this.mChInfoMap != null) {
                chInfo = this.mChInfoMap.get(SpmUtils.objectToString(page));
            }
            TrackIntegrator.getInstance().logPageEndWithSpmId(spmId, page, bizCode, a(map, chInfo, getSrcSpm(page)));
            MergeCenter.INSTANCE.onPageEnd(getPageId(page));
            f(page);
        }
        this.mIsLeaveHint = false;
    }

    public void pageOnPause(Object page, String spmId, String bizCode, Map<String, String> map) {
        pageOnPause(page, spmId, bizCode, map, null);
    }

    public void pageOnDestroy(Object page) {
        f(page);
    }

    private void a(Object page) {
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        PageInfo topPageInfo = TrackIntegrator.getInstance().getPageMonitorCurrentPageInfo();
        if (topPageInfo == null || pageInfo == null || topPageInfo.referPageInfo == null || !topPageInfo.referPageInfo.pageId.equals(pageInfo.pageId) || b(page)) {
            pageBack = 0;
        } else {
            pageBack = 1;
        }
    }

    private boolean b(Object page) {
        if (page == null || getTopPage() == null) {
            return false;
        }
        if ("com.alipay.android.launcher.core.IBaseWidgetGroup".equals(page.getClass().getSuperclass().getName()) && "com.alipay.android.launcher.core.IBaseWidgetGroup".equals(getTopPage().getClass().getSuperclass().getName())) {
            return true;
        }
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        PageInfo topPageInfo = TrackIntegrator.getInstance().getPageMonitorCurrentPageInfo();
        if (topPageInfo == null || pageInfo == null || !this.mHPTabSpmIds.contains(pageInfo.spm) || !this.mHPTabSpmIds.contains(topPageInfo.spm)) {
            return false;
        }
        return true;
    }

    public void upateSrcSpm(Object page, String srcSpm) {
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        if (pageInfo != null) {
            pageInfo.srcSpm = srcSpm;
        }
    }

    public String getSrcSpm(Object page) {
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        if (pageInfo != null) {
            return pageInfo.srcSpm == null ? "" : pageInfo.srcSpm;
        }
        return "";
    }

    public Object getTopPage() {
        if (this.mTopPage != null) {
            return this.mTopPage.get();
        }
        return null;
    }

    public void behaviorClick(Object page, String spmId, String bizCode, int loggerLevel, String entityId, Map<String, String> param4) {
        if (!TextUtils.isEmpty(spmId)) {
            this.mTrackExcutor.commitTracker(new ClickTracker(a(page, spmId, bizCode, entityId, loggerLevel, param4, new String[0])));
        }
    }

    public void behaviorExpose(Object page, String spmId, String bizCode, int loggerLevel, String entityId, Map<String, String> param4) {
        if (!TextUtils.isEmpty(spmId)) {
            this.mTrackExcutor.commitTracker(new ExposeTracker(a(page, spmId, bizCode, entityId, loggerLevel, param4, new String[0])));
        }
    }

    public void mergeExpose(Object page, String spmId, String bizCode, int loggerLevel, String entityId, Map<String, String> param4, String rid, int position) {
        SpmLogCator.debug(this.TAG, "mergeExpose spmId:" + spmId + ";window:" + page);
        if (!TextUtils.isEmpty(spmId)) {
            if (param4 == null) {
                param4 = new HashMap<>();
            }
            param4.put(MergeUtil.KEY_RID, rid);
            if (position >= 0) {
                param4.put("pos", Integer.toString(position));
            }
            Builder builder = a(page, spmId, bizCode, entityId, loggerLevel, param4, new String[0]);
            if ("1".equals(MergeUtil.isMergeActived())) {
                MergeCenter.INSTANCE.merge(new MergeTracker(BehavorID.MERGEEXPOSURE, builder));
            } else if ("0".equals(MergeUtil.isMergeActived())) {
                behaviorExpose(page, spmId, bizCode, loggerLevel, entityId, param4);
            }
        }
    }

    public void behaviorSlide(Object page, String spmId, String bizCode, int loggerLevel, String entityId, Map<String, String> param4) {
        if (!TextUtils.isEmpty(spmId)) {
            this.mTrackExcutor.commitTracker(new SlideTracker(a(page, spmId, bizCode, loggerLevel, param4, new String[0])));
        }
    }

    @TargetApi(17)
    public String getMiniPageId(Object page) {
        if (page == null) {
            return "C_NULL_WINDOW";
        }
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        if (pageInfo != null) {
            return pageInfo.miniPageId != null ? pageInfo.miniPageId : "C_PID";
        }
        return c(page);
    }

    public String getPageId(Object page) {
        if (page == null) {
            return "C_NULL_WINDOW";
        }
        String pageId = TrackIntegrator.getInstance().getPageIdByView(page);
        SpmLogCator.debug(this.TAG, "getPageId pageId:" + pageId);
        return pageId == null ? c(page) : pageId;
    }

    public String getPageSpm(Object page) {
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        if (pageInfo != null) {
            return pageInfo.spm;
        }
        return "";
    }

    public String getLastClickSpmId() {
        String spmId = TrackIntegrator.getInstance().getLastClickViewSpm();
        if (TextUtils.isEmpty(spmId)) {
            return "";
        }
        return spmId;
    }

    public String getLastClickSpmIdByPage(Object page) {
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        if (pageInfo != null) {
            return pageInfo.lastClickSpm;
        }
        return "";
    }

    public Map<String, String> getTracerInfo(Object page) {
        Map tracerInfo = new HashMap();
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        if (pageInfo != null) {
            tracerInfo.put("pagets", getMiniPageId(page));
            tracerInfo.put("srcSpm", pageInfo.srcSpm == null ? "" : pageInfo.srcSpm);
        }
        tracerInfo.put("lastClickSpm", getLastClickSpmId());
        return tracerInfo;
    }

    public void setLastClickSpm(String spm) {
        TrackIntegrator.getInstance().setLastClickViewSpm(spm, null);
    }

    public void setMergeConfig(String config) {
        MergeUtil.MERGE_CONFIG = config;
    }

    public void setSpmTag(View view, String spmid, boolean needSaveView) {
        SpmLogCator.debug(this.TAG, "setSpmTag spmId:" + spmid + ";view:" + view + ";needSaveView:" + needSaveView);
        TrackIntegrator.getInstance().tagViewSpm(view, spmid);
        if (needSaveView) {
            a(view, spmid);
        }
    }

    public void setContentTag(View view, String scmId, boolean needSaveView) {
        SpmLogCator.debug(this.TAG, "setContentTag scmId:" + scmId + ";view:" + view + ";needSaveView:" + needSaveView);
        TrackIntegrator.getInstance().tagViewEntityContentId(view, scmId);
        if (needSaveView) {
            b(view, scmId);
        }
    }

    public String getPageChInfo(Object o) {
        if (this.mChInfoMap == null) {
            return null;
        }
        return this.mChInfoMap.get(SpmUtils.objectToString(o));
    }

    public void setHomePageTabSpms(List<String> list) {
        if (list != null) {
            this.mHPTabSpmIds = list;
        }
    }

    public boolean isPageStarted(Object page) {
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        return pageInfo != null && !pageInfo.isEnd;
    }

    private void a(View view, String spmId) {
        if (this.isDataboardValid) {
            try {
                SpmLogCator.debug(this.TAG, "hookSetViewTag spmId:" + spmId);
                Class c = Class.forName("com.alipay.android.phone.wallet.databoard.AdapterForDataboard");
                c.getMethod("setSpmTagForPopWin", new Class[]{View.class, String.class}).invoke(c, new Object[]{view, spmId});
            } catch (Exception e) {
                SpmLogCator.debug(this.TAG, e.toString());
            }
        }
    }

    private void b(View view, String spmId) {
        if (this.isDataboardValid) {
            try {
                SpmLogCator.debug(this.TAG, "hookSetViewTag spmId:" + spmId);
                Class c = Class.forName("com.alipay.android.phone.wallet.databoard.AdapterForDataboard");
                c.getMethod("setContentTagForPopWin", new Class[]{View.class, String.class, Boolean.TYPE}).invoke(c, new Object[]{view, spmId});
            } catch (Exception e) {
                SpmLogCator.debug(this.TAG, e.toString());
            }
        }
    }

    private String c(Object page) {
        if (page == null || page.getClass() == null || page.getClass().getSimpleName() == null) {
            return "C_NULL";
        }
        StringBuilder sb = new StringBuilder("C_");
        sb.append(page.getClass().getSimpleName().replace("Activity", "").replace("Fragment", ""));
        if ((page instanceof Activity) && VERSION.SDK_INT >= 17) {
            sb.append(((Activity) page).isDestroyed() ? "_Y" : "_N");
        }
        return sb.toString();
    }

    private HashMap<String, String> a(Map<String, String> bizParams, String chInfo, String srcSpm) {
        HashMap outParams = new HashMap();
        if (bizParams != null && !bizParams.isEmpty()) {
            if (SpmUtils.isDebug && bizParams.containsKey(Constant.KEY_FROMHOME)) {
                throw new IllegalArgumentException("\"fromHome\"为保留字段，扩展参数中key不能使用\"fromHome\"");
            } else if (SpmUtils.isDebug && bizParams.containsKey(Constant.KEY_PAGEBACK)) {
                throw new IllegalArgumentException("\"pageBack\"为保留字段，扩展参数中key不能使用\"pageBack\"");
            } else if (SpmUtils.isDebug && bizParams.containsKey("chInfo")) {
                throw new IllegalArgumentException("\"chInfo\"为保留字段，扩展参数中key不能使用\"chInfo\"");
            } else if (!SpmUtils.isDebug || !bizParams.containsKey("srcSpm")) {
                outParams.putAll(bizParams);
            } else {
                throw new IllegalArgumentException("\"srcSpm\"为保留字段，扩展参数中key不能使用\"srcSpm\"");
            }
        }
        outParams.put(Constant.KEY_FROMHOME, this.mIsLeaveHint ? "1" : "0");
        outParams.put(Constant.KEY_PAGEBACK, pageBack + "");
        outParams.put("srcSpm", srcSpm);
        if (!TextUtils.isEmpty(chInfo)) {
            outParams.put("chInfo", chInfo);
        }
        return outParams;
    }

    private void d(Object page) {
        Bundle bundle = null;
        if (page instanceof Activity) {
            Intent intent = ((Activity) page).getIntent();
            if (intent != null) {
                bundle = intent.getExtras();
            }
        } else if (page instanceof Fragment) {
            bundle = ((Fragment) page).getArguments();
        }
        String srcSpm = "";
        if (bundle != null) {
            try {
                bundle.setClassLoader(page.getClass().getClassLoader());
                srcSpm = bundle.getString("srcSpm");
            } catch (Exception e) {
                SpmLogCator.warn(this.TAG, "checkSrcSpm exception:" + e.toString());
            }
        }
        if (!TextUtils.isEmpty(srcSpm)) {
            upateSrcSpm(page, srcSpm);
            return;
        }
        PageInfo referPageInfo = g(page);
        if (referPageInfo != null) {
            upateSrcSpm(page, TextUtils.isEmpty(referPageInfo.lastClickSpm) ? referPageInfo.spm : referPageInfo.lastClickSpm);
        }
    }

    private void e(Object page) {
        Bundle bundle = null;
        try {
            if (page instanceof Activity) {
                Intent intent = ((Activity) page).getIntent();
                if (intent != null) {
                    bundle = intent.getExtras();
                }
            } else if (page instanceof Fragment) {
                bundle = ((Fragment) page).getArguments();
            }
            if (bundle != null) {
                bundle.setClassLoader(page.getClass().getClassLoader());
                String chInfo = bundle.getString("chInfo");
                if (!TextUtils.isEmpty(chInfo)) {
                    if (this.mChInfoMap == null) {
                        this.mChInfoMap = new HashMap();
                    }
                    this.mChInfoMap.put(SpmUtils.objectToString(page), chInfo);
                }
            }
        } catch (Exception e) {
            SpmLogCator.warn(this.TAG, "parseChInfo exception:" + e.toString());
        }
    }

    private void f(Object page) {
        String pageKey = SpmUtils.objectToString(page);
        if (this.mChInfoMap != null && this.mChInfoMap.containsKey(pageKey)) {
            this.mChInfoMap.remove(pageKey);
        }
    }

    private PageInfo g(Object page) {
        PageInfo pageInfo = TrackIntegrator.getInstance().getPageInfoByView(page);
        if (pageInfo != null) {
            return pageInfo.referPageInfo;
        }
        return null;
    }

    private Builder a(Object page, String spmId, String bizCode, String entityId, int loggerLevel, Map<String, String> map, String... params) {
        Builder builder = new Builder(Constant.UCID).setSeedID(spmId);
        builder.setBehaviourPro(bizCode).setPageId(getPageId(page)).setEntityContentId(entityId).setLoggerLevel(loggerLevel);
        if (params != null && params.length > 0) {
            if (1 <= params.length) {
                builder.setParam1(params[0]);
            }
            if (2 <= params.length) {
                builder.setParam2(params[1]);
            }
            if (3 <= params.length) {
                builder.setParam3(params[2]);
            }
        }
        if (map != null) {
            for (String key : map.keySet()) {
                builder.addExtParam(key, map.get(key));
            }
        }
        return builder;
    }

    private Builder a(Object page, String spmId, String bizCode, int loggerLevel, Map<String, String> map, String... params) {
        return a(page, spmId, bizCode, null, loggerLevel, map, params);
    }

    private synchronized void b() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                try {
                    if (SpmMonitor.this.mLeaveHintReceiver == null && SpmMonitor.this.mContext != null) {
                        SpmMonitor.this.mLeaveHintReceiver = new LeaveHintReceiver();
                        LocalBroadcastManager.getInstance(SpmMonitor.this.mContext).registerReceiver(SpmMonitor.this.mLeaveHintReceiver, new IntentFilter("com.alipay.mobile.framework.USERLEAVEHINT"));
                    }
                } catch (Exception e) {
                    Log.e(SpmMonitor.this.TAG, "registerHomePressReceiver exception:" + e.toString());
                } catch (Error error) {
                    Log.e(SpmMonitor.this.TAG, "registerHomePressReceiver error:" + error.toString());
                }
            }
        }, 1000);
    }
}
