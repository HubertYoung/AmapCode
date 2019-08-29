package com.alipay.mobile.monitor.track;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.monitor.track.TrackIntegrator.PageInfo;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.util.HashMap;
import java.util.Map.Entry;

public class TrackAutoHelper {
    public static String AUTO_TRACK_TYPE = "autotrack";
    public static String PARAMS_HEADER = Performance.KEY_LOG_HEADER;
    public static String PARAMS_HEADER_AUTO = "D-AM";
    public static final String TAG = "TrackAutoHelper";
    private static TrackAutoHelper a;
    private static Handler e = new Handler(Looper.getMainLooper());
    public String H5_PAGE_URL = "h5pageurl";
    /* access modifiers changed from: private */
    public HashMap<String, PageInfo> b = new HashMap<>();
    private HashMap<String, String> c = new HashMap<>();
    private String d = "first";
    public long pageLastEndTime = 0;

    public TrackAutoHelper() {
        a();
    }

    private void a() {
        if (TextUtils.isEmpty(LoggerFactory.getLogContext().getContextParam(LogContext.LOCAL_STORAGE_REFER))) {
            LoggerFactory.getLogContext().putContextParam(LogContext.LOCAL_STORAGE_REFER, this.d);
        }
    }

    public static TrackAutoHelper getInstance() {
        if (a == null) {
            synchronized (TrackAutoHelper.class) {
                try {
                    if (a == null) {
                        a = new TrackAutoHelper();
                    }
                }
            }
        }
        return a;
    }

    public void logAutoBehavorPageStart(String spm, Object view, boolean isHookInvoke) {
        logAutoBehavorPageStart(spm, view, isHookInvoke, true);
    }

    public void logAutoBehavorPageStart(String spm, Object view, boolean isHookInvoke, boolean needRpc) {
        if (view == null) {
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefix Start_view view is null");
            return;
        }
        if (TextUtils.isEmpty(spm)) {
            spm = a(view);
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefix Start_view spm = " + spm);
        }
        if (!a(view, isHookInvoke)) {
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefix is not track page(start) " + view.getClass().getName());
            return;
        }
        String key = getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefix Start_view.getViewKey() is null");
            return;
        }
        PageInfo lastInfo = this.b.get(key);
        if (lastInfo == null || lastInfo.isEnd) {
            PageInfo pageInfo = new PageInfo();
            pageInfo.pageStartTime10 = System.currentTimeMillis();
            pageInfo.pageStartTime64 = IntUtil.a(pageInfo.pageStartTime10);
            pageInfo.pageId = spm + "__" + LoggerFactory.getLogContext().getDeviceId() + "__" + pageInfo.pageStartTime64 + "_";
            pageInfo.refer = this.d;
            pageInfo.spm = spm;
            pageInfo.needRpc = needRpc;
            pageInfo.miniPageId = spm + "__" + pageInfo.pageStartTime64 + "_";
            pageInfo.className = b(view);
            this.d = spm + MergeUtil.SEPARATOR_KV + pageInfo.pageId;
            this.b.put(key, pageInfo);
            this.c.put(view.getClass().getName(), "");
            TrackIntegrator.getInstance().updateCurrentPageInfo(pageInfo);
            LoggerFactory.getTraceLogger().info(TAG, "page start " + key + " name = " + view.getClass().getName() + " spm = " + spm);
            try {
                if (view instanceof View) {
                    ((View) view).addOnAttachStateChangeListener(new c(this, key));
                }
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error((String) TAG, e2);
            }
        } else {
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefixStart_not call end,and start twice,update spm");
            a(lastInfo, spm, needRpc);
        }
    }

    public PageInfo logAutoBehavorPageEnd(String spm, Object view, String bizCode, HashMap<String, String> hashMap, Behavor behavor, boolean isHookInvoke) {
        if (view == null) {
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefixEnd_View is null");
            return null;
        }
        if (TextUtils.isEmpty(spm)) {
            spm = a(view);
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefix End_View spm = " + spm);
        }
        if (!a(view, isHookInvoke)) {
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefix is not track page(end) " + view.getClass().getName());
            return null;
        }
        String key = getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefixEnd_view.getViewKey() is null");
            return null;
        }
        PageInfo pageInfo = this.b.get(key);
        if (pageInfo == null) {
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefixEnd_pageInfo is null");
            return null;
        } else if (pageInfo.isEnd) {
            LoggerFactory.getTraceLogger().info(TAG, "auto_prefix key = " + key + " is already call pageEnd");
            return pageInfo;
        } else {
            long endTimeInterval = 0;
            if (this.pageLastEndTime != 0) {
                endTimeInterval = System.currentTimeMillis() - this.pageLastEndTime;
            }
            this.pageLastEndTime = System.currentTimeMillis();
            pageInfo.isEnd = true;
            if (behavor == null) {
                behavor = new Behavor();
            }
            HashMap map = null;
            if (hashMap != null) {
                map = (HashMap) hashMap.clone();
            }
            String spm2 = a(spm, view, pageInfo);
            LoggerFactory.getLogContext().putContextParam(LogContext.LOCAL_STORAGE_REFER, pageInfo.refer);
            behavor.setxPath("//" + a(view, map));
            behavor.setSeedID(spm2);
            behavor.setRefer(pageInfo.refer);
            behavor.setPageId(pageInfo.pageId);
            behavor.setBehaviourPro(AUTO_TRACK_TYPE);
            long stayTime = System.currentTimeMillis() - pageInfo.pageStartTime10;
            pageInfo.pageStayTime = stayTime;
            behavor.setPageStayTime(stayTime + "");
            behavor.setAppVersion(pageInfo.pageStartTime64);
            if (TrackIntegrator.lastViewName.equals("first") || this.c.containsKey(TrackIntegrator.lastViewName)) {
                behavor.setSpmStatus("1");
            } else {
                behavor.setSpmStatus("0");
            }
            HashMap configParam = null;
            if (view instanceof TrackPageConfig) {
                try {
                    configParam = (HashMap) ((TrackPageConfig) view).getExtParam();
                } catch (Throwable e2) {
                    LoggerFactory.getTraceLogger().error((String) TAG, e2);
                }
            }
            if (map != null && configParam != null) {
                map.putAll(configParam);
            } else if (map == null) {
                map = configParam;
            }
            if (map != null) {
                String entityId = (String) map.get(TrackPageConfig.KEY_ENTITY_CONTENT_TAGID);
                if (!TextUtils.isEmpty(entityId)) {
                    behavor.setEntityContentId(entityId);
                }
                if (TextUtils.isEmpty((String) map.get(PARAMS_HEADER))) {
                    map.put(PARAMS_HEADER, PARAMS_HEADER_AUTO);
                }
            } else {
                map = new HashMap();
                map.put(PARAMS_HEADER, PARAMS_HEADER_AUTO);
            }
            if (map != null) {
                map.put("endTimeInterval", endTimeInterval + "");
            }
            for (Entry entry : map.entrySet()) {
                try {
                    behavor.addExtParam((String) entry.getKey(), (String) entry.getValue());
                } catch (Throwable e3) {
                    LoggerFactory.getTraceLogger().error((String) TAG, e3);
                }
            }
            LoggerFactory.getTraceLogger().info(TAG, "page end " + key + " name = " + view.getClass().getName() + " refer = " + pageInfo.refer + " spm = " + spm2 + " endTimeInterval = " + endTimeInterval + " stayTime = " + stayTime);
            LoggerFactory.getBehavorLogger().autoOpenPage(behavor);
            return pageInfo;
        }
    }

    private String a(String spm, Object view, PageInfo pageInfo) {
        if (view == null) {
            LoggerFactory.getTraceLogger().info(TAG, "updateSpmAndRefer view is null");
            return spm;
        }
        LoggerFactory.getTraceLogger().info(TAG, "updateSpmAndRefer view is " + view.getClass().getName());
        if (view instanceof TrackPageConfig) {
            LoggerFactory.getTraceLogger().info(TAG, "updateSpmAndRefer is TrackPageConfig");
            return spm;
        }
        HashMap pageInfos = TrackIntegrator.getInstance().getPageInfos();
        if (pageInfos == null) {
            LoggerFactory.getTraceLogger().info(TAG, "updateSpmAndRefer pageInfos is null");
            return spm;
        }
        String key = getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(TAG, "updateSpmAndRefergetViewKey() is null");
            return spm;
        }
        PageInfo pageInfoLogMonitor = pageInfos.get(key);
        if (pageInfoLogMonitor == null) {
            LoggerFactory.getTraceLogger().info(TAG, "updateSpmAndRefer pageInfoLogMonitor is null");
            return spm;
        }
        LoggerFactory.getTraceLogger().info(TAG, "updateSpmAndRefer autoSpm = " + spm + " update to " + pageInfoLogMonitor.spm);
        pageInfo.spm = pageInfoLogMonitor.spm;
        String spm2 = pageInfoLogMonitor.spm;
        int index = this.d.indexOf(MergeUtil.SEPARATOR_KV);
        if (index >= 0) {
            this.d = pageInfo.spm + this.d.substring(index);
        }
        return spm2;
    }

    private String a(Object view) {
        if (view == null) {
            return null;
        }
        if (view instanceof TrackPageConfig) {
            return ((TrackPageConfig) view).getPageSpmId();
        }
        if (view instanceof View) {
            String tag = TrackIntegrator.getInstance().getViewTag((View) view);
            if (!TextUtils.isEmpty(tag)) {
                return tag;
            }
        }
        return view.getClass().getName();
    }

    private boolean a(Object view, boolean isHookInvoke) {
        if (view == null) {
            return false;
        }
        if (view instanceof Fragment) {
            if (!isHookInvoke) {
                return true;
            }
            if (view instanceof TrackPageConfig) {
                return ((TrackPageConfig) view).isTrackPage();
            }
            return false;
        } else if (view instanceof TrackPageConfig) {
            return ((TrackPageConfig) view).isTrackPage();
        } else {
            return true;
        }
    }

    public void dropPageInfo(Object view) {
        if (view != null) {
            String key = getViewKey(view);
            if (key != null && this.b.get(key) != null) {
                e.postDelayed(new d(this, key), 1000);
            }
        }
    }

    public PageInfo getAutoPageInfoByView(Object view) {
        if (view == null) {
            LoggerFactory.getTraceLogger().info(TAG, "getPageInfoByView is null or spm is null");
            return null;
        }
        String key = getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(TAG, "getPageInfoByView() is null");
            return null;
        }
        PageInfo pageInfo = this.b.get(key);
        if (pageInfo != null) {
            return pageInfo;
        }
        LoggerFactory.getTraceLogger().info(TAG, "getPageInfoByView view is null");
        return null;
    }

    private String a(Object view, HashMap<String, String> extParams) {
        if (extParams != null) {
            String url = extParams.get(this.H5_PAGE_URL);
            if (!TextUtils.isEmpty(url)) {
                extParams.remove(this.H5_PAGE_URL);
                return url;
            }
        }
        if (view == null) {
            return null;
        }
        if (view instanceof String) {
            return view.toString();
        }
        return view.getClass().getName();
    }

    private String b(Object view) {
        if (view == null) {
            return null;
        }
        try {
            if (view instanceof String) {
                return (String) view;
            }
            return view.getClass().getName();
        } catch (Throwable e2) {
            LoggerFactory.getTraceLogger().error((String) TAG, e2);
            return null;
        }
    }

    public String getViewKey(Object view) {
        if (view == null) {
            return null;
        }
        if (view instanceof String) {
            return view.toString() + view.hashCode();
        }
        return view.getClass().getName() + view.hashCode();
    }

    private void a(PageInfo lastInfo, String spm, boolean needRpc) {
        if (lastInfo == null || TextUtils.isEmpty(spm)) {
            LoggerFactory.getTraceLogger().info(TAG, "updateLastInfoSpm spm or lastInfo is null");
            return;
        }
        lastInfo.spm = spm;
        lastInfo.needRpc = needRpc;
        this.d = spm + MergeUtil.SEPARATOR_KV + lastInfo.pageId;
        TrackIntegrator.getInstance().updateCurrentPageInfo(lastInfo);
    }
}
