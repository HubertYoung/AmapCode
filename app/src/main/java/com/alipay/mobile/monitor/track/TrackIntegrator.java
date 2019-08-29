package com.alipay.mobile.monitor.track;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Pair;
import android.view.TouchDelegate;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import com.alipay.android.phone.wallet.spmtracker.Constant;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LogDAUTracker;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.util.LoggingUtil;
import com.alipay.mobile.monitor.track.interceptor.AutoClickInterceptor;
import com.alipay.mobile.monitor.track.interceptor.ClickInterceptor;
import com.alipay.mobile.monitor.track.interceptor.ClickInterceptorManager;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.monitor.track.xpath.XPathFinder;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public class TrackIntegrator {
    public static final String END_SEPARATOR_CHAR = "_";
    public static final String SEPARATOR_CHAR = "__";
    public static final String TAG = TrackIntegrator.class.getSimpleName();
    public static String TAG_DISABLE_AUTOTRACK = "disable_auto_track";
    public static int entityContentTagId = 0;
    private static Handler j = new Handler(Looper.getMainLooper());
    private static TrackIntegrator k;
    public static String lastTrackPage = "first";
    public static String lastViewName = "first";
    public static long mLastActiveTime = -1;
    public static int semTagId = 0;
    private PageInfo a = null;
    private PageInfo b;
    private OnAutoClickListener c = null;
    private Pair<WeakReference<Object>, ActionInfo> d = null;
    private Pair<WeakReference<Object>, ActionInfo> e = null;
    private Pair<WeakReference<Object>, ActionInfo> f = null;
    private final String g = "pageMonitor";
    /* access modifiers changed from: private */
    public HashMap<String, PageInfo> h = new HashMap<>();
    public long handleClickTime;
    private HashMap<String, String> i = new HashMap<>();
    private ClickInterceptorManager l = new ClickInterceptorManager();
    public long lastClickTime;
    public String lastClickViewId = "first";
    public String lastClickViewSpm = "";
    public long lastClickViewTime = 0;
    private boolean m = false;
    RecyclerViewIndexGetter mRecyclerViewIndexGetter;
    private boolean n = false;
    private View o;
    private String p;
    private String q;
    private WeakReference<Activity> r = null;
    public PageInfo referPageInfo;
    public long respond;
    public long respondOnResume;
    public long resumeHandleClickTime;
    private long s = -1;
    private long t = -1;

    class ActionInfo {
        public String actionDesc;
        public String actionId;
        public String actionToken;
        public String appId;
        public String entryTime;
        public long launchTime;
        public String preViewId;
        public String sourceId;
        public long startTimestamp;

        private ActionInfo() {
            this.launchTime = -1;
        }
    }

    public interface OnAutoClickListener {
        void onClick(String str, Object obj);
    }

    public class PageInfo {
        public String className;
        public boolean isEnd = false;
        public String lastClickSem;
        public String lastClickSpm;
        public String miniPageId;
        public boolean needRpc = true;
        public String pageId;
        public long pageStartTime10;
        public String pageStartTime64;
        public long pageStayTime;
        public String refer;
        public String referClickSpm;
        public PageInfo referPageInfo;
        public String spm;
        public String spmStatus = "0";
        public String srcSpm;

        public String getRefer() {
            if (this.referPageInfo != null) {
                return this.referPageInfo.spm + MergeUtil.SEPARATOR_KV + this.referPageInfo.pageId;
            }
            return "first";
        }

        public static PageInfo clonePageInfo(PageInfo pageInfo) {
            PageInfo pageInfoClone = new PageInfo();
            pageInfoClone.pageStartTime10 = pageInfo.pageStartTime10;
            pageInfoClone.pageStartTime64 = pageInfo.pageStartTime64;
            pageInfoClone.pageId = pageInfo.pageId;
            pageInfoClone.pageStayTime = pageInfo.pageStayTime;
            pageInfoClone.spm = pageInfo.spm;
            pageInfoClone.refer = pageInfo.refer;
            pageInfoClone.isEnd = pageInfo.isEnd;
            pageInfoClone.spmStatus = pageInfo.spmStatus;
            pageInfoClone.miniPageId = pageInfo.miniPageId;
            pageInfoClone.needRpc = pageInfo.needRpc;
            pageInfoClone.referClickSpm = pageInfo.referClickSpm;
            pageInfoClone.className = pageInfo.className;
            pageInfoClone.srcSpm = pageInfo.srcSpm;
            pageInfoClone.lastClickSpm = pageInfo.lastClickSpm;
            pageInfoClone.lastClickSem = pageInfo.lastClickSem;
            pageInfoClone.referPageInfo = pageInfo.referPageInfo;
            return pageInfoClone;
        }
    }

    public interface RecyclerViewIndexGetter {
        int getFirstVisibleItemPosition(View view);
    }

    private TrackIntegrator() {
        AutoTrackerAdapter adapter = AutoTracker.getImpl().getAutoTrackerAdapter();
        if (adapter != null) {
            adapter.onTrackIntegratorInit(this);
        }
    }

    public static synchronized TrackIntegrator getInstance() {
        TrackIntegrator trackIntegrator;
        synchronized (TrackIntegrator.class) {
            try {
                if (k == null) {
                    k = new TrackIntegrator();
                }
                trackIntegrator = k;
            }
        }
        return trackIntegrator;
    }

    public void addClickInterceptor(ClickInterceptor interceptor) {
        this.l.addClickInterceptor(interceptor);
    }

    public void autoTrackClick(boolean isAutoTrack) {
        this.m = isAutoTrack;
    }

    public void autoTrackPage(boolean isAutoTrack) {
        this.n = isAutoTrack;
    }

    public void createActivity(Activity activity) {
        if (activity != null && !activity.isFinishing() && a(activity.getClass().getName())) {
            this.r = new WeakReference<>(activity);
            this.s = System.currentTimeMillis();
            this.t = -1;
            TrackAutoHelper.getInstance().logAutoBehavorPageStart(null, activity, false);
        }
    }

    public void displayActivity(Activity activity) {
        if (activity != null && !activity.isFinishing() && a(activity.getClass().getName()) && this.d != null) {
            Object activeActivity = ((WeakReference) this.d.first).get();
            Activity storedActivity = this.r == null ? null : (Activity) this.r.get();
            if (activeActivity != null && activeActivity == activity && activity == storedActivity) {
                if (this.t == -1) {
                    this.t = System.currentTimeMillis() - this.s;
                }
                ActionInfo info = (ActionInfo) this.d.second;
                if (info.launchTime == -1) {
                    info.launchTime = this.t;
                }
            }
        }
    }

    public void enterActivity(Activity activity) {
        if (activity != null && !activity.isFinishing() && a(activity.getClass().getName())) {
            LoggerFactory.getLogContext().notifyClientEvent(LogContext.ENVENT_VIEWSWITCH, activity.getClass().getName());
            ActionInfo info = new ActionInfo();
            info.startTimestamp = System.currentTimeMillis();
            info.actionId = LoggerFactory.getLogContext().getLocalParam(LogContext.LOCAL_STORAGE_ACTIONID);
            info.actionToken = LoggerFactory.getLogContext().getLocalParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN);
            info.actionDesc = LoggerFactory.getLogContext().getLocalParam(LogContext.LOCAL_STORAGE_ACTIONDESC);
            info.entryTime = LoggingUtil.getNowTime();
            AutoTrackerAdapter adapter = AutoTracker.getImpl().getAutoTrackerAdapter();
            if (adapter != null) {
                info.appId = adapter.getAppId(activity);
                info.sourceId = adapter.getSourceId(activity);
            }
            a();
            LoggerFactory.getTraceLogger().debug(TAG, "进入页面:" + activity.getClass().getName() + "\r\n点击来源:" + info.actionId + "(" + info.actionDesc + ")\r\n业务来源:" + info.sourceId + "\r\n所在应用:" + info.appId + " respond = " + this.respond);
            if ("test".equals(LoggerFactory.getLogContext().getReleaseType())) {
                if (TextUtils.isEmpty(info.appId)) {
                    LoggerFactory.getTraceLogger().warn(TAG, (String) "appid为空");
                }
                if (info.appId != null && !info.appId.equals(LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_APPID))) {
                    LoggerFactory.getTraceLogger().warn(TAG, "当前页面应用和appID不一致：" + LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_APPID));
                }
                String refViewID = LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_REFVIEWID);
                if (!(info.actionId == null || refViewID == null || info.actionId.startsWith(refViewID))) {
                    LoggerFactory.getTraceLogger().warn(TAG, "来源控件和和refViewID不一致：" + refViewID);
                }
            }
            this.d = new Pair<>(new WeakReference(activity), info);
            lastTrackPage = activity.getClass().getName();
            TrackAutoHelper.getInstance().logAutoBehavorPageStart(null, activity, false);
            trackClick(activity.findViewById(16908290), activity.getClass().getName(), info.appId, false);
        }
    }

    public void leaveActivity(Activity activity) {
        if (activity != null) {
            if (a(activity.getClass().getName())) {
                lastViewName = activity.getClass().getName();
                LoggerFactory.getTraceLogger().info(TAG, "leaveActivity lastViewName = " + lastViewName);
                StringBuilder pathBuilder = new StringBuilder();
                boolean hasAddTopAc = false;
                String activityTrackId = null;
                AutoTrackerAdapter adapter = AutoTracker.getImpl().getAutoTrackerAdapter();
                if (adapter != null) {
                    activityTrackId = adapter.getActivityTrackId(activity);
                    int activityCount = adapter.getStartActivityCount(activity);
                    for (int index = 0; index < activityCount; index++) {
                        Activity ac = adapter.getActivityAt(activity, index);
                        if (ac == activity) {
                            hasAddTopAc = true;
                        }
                        pathBuilder.append(ac != null ? ac.getTitle() : "unkown");
                        pathBuilder.append('>');
                    }
                }
                if (!hasAddTopAc) {
                    pathBuilder.append(activity.getTitle());
                    pathBuilder.append('>');
                }
                Behavor behavor = null;
                if (this.d != null) {
                    Object activeActivity = ((WeakReference) this.d.first).get();
                    if (activeActivity != null && activeActivity == activity) {
                        behavor = new Behavor();
                        ActionInfo info = (ActionInfo) this.d.second;
                        behavor.setTrackId(info.actionId);
                        behavor.setTrackToken(info.actionToken);
                        behavor.setTrackDesc(info.actionDesc);
                        behavor.setViewID(activityTrackId);
                        behavor.setAppID(info.appId);
                        long time = System.currentTimeMillis() - info.startTimestamp;
                        behavor.setParam1(Long.toString(TimeUnit.MILLISECONDS.toSeconds(time)));
                        behavor.setParam2(pathBuilder.toString());
                        behavor.setParam3(String.valueOf(info.launchTime));
                        behavor.addExtParam("sourceappid", info.sourceId);
                        behavor.addExtParam("staytime", time + "");
                        behavor.addExtParam("openpagetime", info.entryTime);
                        if (this.respond > 0) {
                            behavor.addExtParam("respond", this.respond + "");
                        }
                        if (adapter != null) {
                            adapter.onAssembleBehavior(behavor);
                        }
                    }
                }
                TrackAutoHelper.getInstance().logAutoBehavorPageEnd(null, activity, null, null, behavor, false);
                this.respond = 0;
                if (this.lastClickTime == this.resumeHandleClickTime || this.lastClickTime == this.handleClickTime) {
                    this.lastClickTime = 0;
                }
            }
        }
    }

    public void enterFragment(Fragment fragment, boolean isHookInvoke) {
        if (fragment != null && isCollectFragment(fragment.getClass().getName())) {
            LoggerFactory.getLogContext().notifyClientEvent(LogContext.ENVENT_VIEWSWITCH, fragment.getClass().getName());
            ActionInfo info = new ActionInfo();
            info.startTimestamp = System.currentTimeMillis();
            info.actionId = LoggerFactory.getLogContext().getLocalParam(LogContext.LOCAL_STORAGE_ACTIONID);
            info.actionToken = LoggerFactory.getLogContext().getLocalParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN);
            info.actionDesc = LoggerFactory.getLogContext().getLocalParam(LogContext.LOCAL_STORAGE_ACTIONDESC);
            info.entryTime = LoggingUtil.getNowTime();
            LoggerFactory.getTraceLogger().debug(TAG, "进入页面(如果isHookInvoke=true可能不会自动化埋点，根据TrackPageConfig判断):" + fragment.getClass().getName() + " isHookInvoke = " + isHookInvoke);
            LoggerFactory.getTraceLogger().debug(TAG, "来源控件:" + info.actionId + "(" + info.actionDesc + ")");
            this.e = new Pair<>(new WeakReference(fragment), info);
            lastTrackPage = fragment.getClass().getName();
            TrackAutoHelper.getInstance().logAutoBehavorPageStart(null, fragment, isHookInvoke);
            trackClick(fragment.getView(), fragment.getClass().getName(), null, false);
        }
    }

    public void enterFragment(Fragment fragment) {
        enterFragment(fragment, false);
    }

    public void leaveFragment(Fragment fragment) {
        leaveFragment(fragment, false);
    }

    public void leaveFragment(Fragment fragment, boolean isHookInvoke) {
        if (fragment != null && isCollectFragment(fragment.getClass().getName())) {
            Behavor behavor = null;
            if (this.e != null) {
                Object activeFragement = ((WeakReference) this.e.first).get();
                if (activeFragement != null && activeFragement == fragment) {
                    behavor = new Behavor();
                    ActionInfo info = (ActionInfo) this.e.second;
                    long stayTime = System.currentTimeMillis() - info.startTimestamp;
                    behavor.setParam1(Long.toString(TimeUnit.MILLISECONDS.toSeconds(stayTime)));
                    behavor.setTrackId(info.actionId);
                    behavor.setTrackToken(info.actionToken);
                    behavor.setTrackDesc(info.actionDesc);
                    behavor.addExtParam("staytime", stayTime + "");
                    behavor.addExtParam("openpagetime", info.entryTime);
                    AutoTrackerAdapter adapter = AutoTracker.getImpl().getAutoTrackerAdapter();
                    if (adapter != null) {
                        adapter.onAssembleBehavior(behavor);
                    }
                }
            }
            TrackAutoHelper.getInstance().logAutoBehavorPageEnd(null, fragment, null, null, behavor, isHookInvoke);
        }
    }

    public void destoryFragment(Fragment fragment) {
        TrackAutoHelper.getInstance().dropPageInfo(fragment);
    }

    public void destoryActivity(Activity activity) {
        TrackAutoHelper.getInstance().dropPageInfo(activity);
        if (activity != null) {
            final String key = TrackAutoHelper.getInstance().getViewKey(activity);
            if (key != null && this.h.get(key) != null) {
                j.postDelayed(new Runnable() {
                    public void run() {
                        TrackIntegrator.this.h.remove(key);
                    }
                }, 1000);
            }
        }
    }

    public void logAutoBehavorPageStart(String spm, Object view) {
        logAutoBehavorPageStart(spm, view, true);
    }

    public void logAutoBehavorPageStart(String spm, Object view, boolean needRpc) {
        TrackAutoHelper.getInstance().logAutoBehavorPageStart(spm, view, false, needRpc);
    }

    public void logAutoBehavorPageEnd(String spm, Object view, String bizCode, HashMap<String, String> map) {
        TrackAutoHelper.getInstance().logAutoBehavorPageEnd(spm, view, bizCode, map, null, false);
    }

    public void logAutoBehavorPageEnd(String spm, Object view, String bizCode, HashMap<String, String> map, Behavor behavor) {
        TrackAutoHelper.getInstance().logAutoBehavorPageEnd(spm, view, bizCode, map, behavor, false);
    }

    public void logPageStartWithSpmId(String spm, Object view) {
        if (view == null || TextUtils.isEmpty(spm)) {
            LoggerFactory.getTraceLogger().info(TAG, "Start_view is null or spm is null");
            return;
        }
        final String key = TrackAutoHelper.getInstance().getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(TAG, "Start_view.toString() is null");
            return;
        }
        PageInfo lastInfo = this.h.get(key);
        if (lastInfo == null || lastInfo.isEnd) {
            PageInfo pageInfo = this.h.get(key);
            if (pageInfo == null) {
                pageInfo = new PageInfo();
            }
            pageInfo.isEnd = false;
            pageInfo.pageStartTime10 = System.currentTimeMillis();
            pageInfo.pageStartTime64 = IntUtil.a(pageInfo.pageStartTime10);
            pageInfo.pageId = spm + "__" + LoggerFactory.getLogContext().getDeviceId() + "__" + pageInfo.pageStartTime64 + "_";
            pageInfo.referPageInfo = this.referPageInfo;
            pageInfo.spm = spm;
            pageInfo.miniPageId = spm + "__" + pageInfo.pageStartTime64 + "_";
            pageInfo.referClickSpm = getLastClickViewSpm();
            this.referPageInfo = PageInfo.clonePageInfo(pageInfo);
            this.h.put(key, pageInfo);
            this.i.put(view.getClass().getName(), "");
            this.b = pageInfo;
            LoggerFactory.getTraceLogger().info(TAG, "page start " + key + " name = " + view.getClass().getName() + " spm = " + spm);
            try {
                if (view instanceof View) {
                    ((View) view).addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                        public void onViewDetachedFromWindow(View view) {
                            TrackIntegrator.this.h.remove(key);
                        }

                        public void onViewAttachedToWindow(View view) {
                        }
                    });
                }
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error(TAG, e2);
            }
        } else {
            LoggerFactory.getTraceLogger().info(TAG, "Start_not call end,and start twice,update spm");
            a(lastInfo, spm);
        }
    }

    private void a(PageInfo lastInfo, String spm) {
        if (lastInfo == null || TextUtils.isEmpty(spm)) {
            LoggerFactory.getTraceLogger().info(TAG, "updateLastInfoSpm spm or lastInfo is null");
        } else {
            lastInfo.spm = spm;
        }
    }

    public PageInfo logPageEndWithSpmId(String spm, Object view, String bizCode, HashMap<String, String> map) {
        if (view == null || TextUtils.isEmpty(spm)) {
            LoggerFactory.getTraceLogger().info(TAG, "End_View is null or spm is null");
            return null;
        }
        String key = TrackAutoHelper.getInstance().getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(TAG, "End_view.toString() is null");
            return null;
        }
        PageInfo pageInfo = this.h.get(key);
        if (pageInfo == null) {
            LoggerFactory.getTraceLogger().info(TAG, "End_pageInfo is null");
            return null;
        } else if (pageInfo.isEnd) {
            LoggerFactory.getTraceLogger().info(TAG, "is already call pageEnd");
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
            behavor.setParam1(pageInfo.getRefer());
            long stayTime = System.currentTimeMillis() - pageInfo.pageStartTime10;
            pageInfo.pageStayTime = stayTime;
            behavor.setParam2(stayTime + "");
            behavor.setParam3(pageInfo.pageStartTime64);
            behavor.setPageId(pageInfo.pageId);
            behavor.setBehaviourPro(bizCode);
            behavor.setSeedID(pageInfo.spm);
            if (lastViewName.equals("first") || this.i.containsKey(lastViewName)) {
                behavor.setSpmStatus("1");
            } else {
                behavor.setSpmStatus("0");
            }
            behavor.addExtParam(Constant.KEY_REFER_SPM, pageInfo.referClickSpm);
            if (map != null) {
                for (Entry entry : map.entrySet()) {
                    try {
                        behavor.addExtParam((String) entry.getKey(), (String) entry.getValue());
                    } catch (Throwable e2) {
                        LoggerFactory.getTraceLogger().error(TAG, e2);
                    }
                }
            }
            LoggerFactory.getBehavorLogger().event("pageMonitor", behavor);
            if (LoggerFactory.getLogContext().getLogDAUTracker() == null || isUploadToday) {
                return pageInfo;
            }
            LoggerFactory.getLogContext().getLogDAUTracker().updateSpmUploadState(pageInfo.spm);
            return pageInfo;
        }
    }

    public String getPageIdByView(Object view) {
        if (view == null) {
            LoggerFactory.getTraceLogger().info(TAG, "getPageId_View is null or spm is null");
            return null;
        }
        String key = TrackAutoHelper.getInstance().getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(TAG, "getPageId_view.toString() is null");
            return null;
        }
        PageInfo pageInfo = this.h.get(key);
        if (pageInfo != null) {
            return pageInfo.pageId;
        }
        LoggerFactory.getTraceLogger().info(TAG, "getPageId_pageInfo is null");
        return null;
    }

    public String getPageStartTimeByView(Object view) {
        if (view == null) {
            LoggerFactory.getTraceLogger().info(TAG, "getPageTime_View is null or spm is null");
            return null;
        }
        String key = TrackAutoHelper.getInstance().getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(TAG, "getPageTime_view.toString() is null");
            return null;
        }
        PageInfo pageInfo = this.h.get(key);
        if (pageInfo != null) {
            return pageInfo.pageStartTime64;
        }
        LoggerFactory.getTraceLogger().info(TAG, "getPageTime_pageInfo is null");
        return null;
    }

    public PageInfo getAutoPageInfoByView(Object view) {
        return TrackAutoHelper.getInstance().getAutoPageInfoByView(view);
    }

    public PageInfo getPageInfoByView(Object view) {
        if (view == null) {
            LoggerFactory.getTraceLogger().info(TAG, "getPageInfoByView is null or spm is null");
            return null;
        }
        String key = TrackAutoHelper.getInstance().getViewKey(view);
        if (TextUtils.isEmpty(key)) {
            LoggerFactory.getTraceLogger().info(TAG, "getPageInfoByView() is null");
            return null;
        }
        PageInfo pageInfo = this.h.get(key);
        if (pageInfo != null) {
            return pageInfo;
        }
        LoggerFactory.getTraceLogger().info(TAG, "getPageInfoByView view is null");
        return null;
    }

    public void tagViewSpm(View view, String spm) {
        if (view == null) {
            try {
                LoggerFactory.getTraceLogger().info(TAG, "tagViewSpm..View is null");
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error(TAG, e2);
            }
        } else if (spm == null) {
            LoggerFactory.getTraceLogger().info(TAG, "tagViewSpm..spm is null");
        } else {
            LoggerFactory.getTraceLogger().info(TAG, "tagViewSpm..spm = " + spm + "view= " + view);
            view.setTag(AutoClickInterceptor.TAG_ID, spm);
        }
    }

    public void setTagViewId(int tagViewId) {
        AutoClickInterceptor.TAG_ID = tagViewId;
    }

    public String getLastClickViewSpm() {
        return this.lastClickViewSpm;
    }

    public void setLastClickViewSpm(String lastClickViewSpm2, Object semInfo) {
        this.lastClickViewSpm = lastClickViewSpm2;
        if (this.b != null) {
            this.b.lastClickSpm = lastClickViewSpm2;
        }
        if (this.c != null) {
            this.c.onClick(lastClickViewSpm2, semInfo);
        }
    }

    public long getLastClickViewTime() {
        return this.lastClickViewTime;
    }

    public String getViewTag(View view) {
        if (view == null) {
            return null;
        }
        Object tag = view.getTag(AutoClickInterceptor.TAG_ID);
        if (tag != null) {
            return tag.toString();
        }
        return null;
    }

    public boolean isDisableAutoTrackView(View clickView) {
        if (clickView != null) {
            try {
                if (TAG_DISABLE_AUTOTRACK.equals(getInstance().getViewTag(clickView))) {
                    return true;
                }
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error(TAG, e2);
            }
        }
        return false;
    }

    public void tagViewEntityContentId(View view, String entityContentId) {
        if (view == null) {
            try {
                LoggerFactory.getTraceLogger().info(TAG, "entityContentId..View is null");
            } catch (Throwable e2) {
                LoggerFactory.getTraceLogger().error(TAG, e2);
            }
        } else if (entityContentId == null) {
            LoggerFactory.getTraceLogger().info(TAG, "entityContentId..entityContentId is null");
        } else if (entityContentTagId != 0) {
            view.setTag(entityContentTagId, entityContentId);
        } else {
            LoggerFactory.getTraceLogger().info(TAG, "entityContentTagId = " + entityContentTagId);
        }
    }

    public void clickView(View view, String pageId, String appId) {
        this.l.handleOnClick(view, pageId, appId);
    }

    public void enterH5Page(String url, String tile, String appId, String sourceId) {
    }

    public void leaveH5Page(String url) {
    }

    public void enterView(View view, String viewId) {
        enterView(view, viewId, null, null);
    }

    public void enterView(View view, String viewId, String appId, String sourceId) {
        enterView(view, viewId, appId, sourceId, null);
    }

    public void enterView(View view, String viewId, String appId, String sourceId, Map<String, String> extParams) {
        if (view != null && !TextUtils.isEmpty(viewId)) {
            LoggerFactory.getLogContext().notifyClientEvent(LogContext.ENVENT_VIEWSWITCH, viewId);
            ActionInfo info = new ActionInfo();
            info.startTimestamp = System.currentTimeMillis();
            info.actionId = LoggerFactory.getLogContext().getLocalParam(LogContext.LOCAL_STORAGE_ACTIONID);
            info.actionToken = LoggerFactory.getLogContext().getLocalParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN);
            info.actionDesc = LoggerFactory.getLogContext().getLocalParam(LogContext.LOCAL_STORAGE_ACTIONDESC);
            info.appId = appId;
            info.sourceId = sourceId;
            info.entryTime = LoggingUtil.getNowTime();
            if (extParams != null) {
                try {
                    info.launchTime = Long.valueOf(extParams.get("launchTime")).longValue();
                } catch (NumberFormatException e2) {
                    LoggerFactory.getTraceLogger().warn(TAG, (String) "launchTime cast Exception");
                }
            }
            a();
            LoggerFactory.getTraceLogger().debug(TAG, "进入页面:" + viewId + "\r\n点击来源:" + info.actionId + "(" + info.actionDesc + ")\r\n业务来源:" + info.sourceId + "\r\n所在应用:" + info.appId + " respond = " + this.respond);
            if ("test".equals(LoggerFactory.getLogContext().getReleaseType())) {
                if (TextUtils.isEmpty(info.appId)) {
                    LoggerFactory.getTraceLogger().warn(TAG, (String) "appid为空");
                }
                if (info.appId != null && !info.appId.equals(LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_APPID))) {
                    LoggerFactory.getTraceLogger().warn(TAG, "当前页面应用和appID不一致：" + LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_APPID));
                }
                String refViewID = LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_REFVIEWID);
                if (!(info.actionId == null || refViewID == null || info.actionId.startsWith(refViewID))) {
                    LoggerFactory.getTraceLogger().warn(TAG, "来源控件和和refViewID不一致：" + refViewID);
                }
                if (("com.alipay.android.phone.home.widget.HomeWidgetGroup".equals(viewId) && !"20000002".equals(appId)) || (("com.alipay.android.phone.discovery.o2ohome.O2oWidgetGroup".equals(viewId) && !"20000238".equals(appId)) || (("com.alipay.mobile.socialwidget.ui.SocialHomePage".equals(viewId) && !"20000217".equals(appId)) || ("com.alipay.android.widgets.asset.AssetWidgetGroup".equals(viewId) && !"20000004".equals(appId))))) {
                    LoggerFactory.getTraceLogger().warn(TAG, "appId不正确：" + appId);
                }
            }
            this.f = new Pair<>(new WeakReference(viewId), info);
            lastTrackPage = viewId;
            TrackAutoHelper.getInstance().logAutoBehavorPageStart(null, view, false);
            trackClick(view, viewId, appId, true);
        }
    }

    public void leaveView(View view, String viewId) {
        if (view != null && !TextUtils.isEmpty(viewId)) {
            lastViewName = viewId;
            LoggerFactory.getTraceLogger().info(TAG, "leaveView lastViewName = " + lastViewName);
            String title = "";
            if ((view.getContext() instanceof Activity) && ((Activity) view.getContext()).getTitle() != null) {
                title = ((Activity) view.getContext()).getTitle().toString();
            }
            Behavor behavor = null;
            if (this.f != null) {
                Object activeViewId = ((WeakReference) this.f.first).get();
                if (viewId != null && viewId.equals(activeViewId)) {
                    behavor = new Behavor();
                    ActionInfo info = (ActionInfo) this.f.second;
                    long stayTime = System.currentTimeMillis() - info.startTimestamp;
                    behavor.setAppID(info.appId);
                    behavor.setParam1(Long.toString(TimeUnit.MILLISECONDS.toSeconds(stayTime)));
                    behavor.setParam2(title);
                    behavor.setParam3(String.valueOf(info.launchTime));
                    behavor.addExtParam("sourceappid", info.sourceId);
                    behavor.addExtParam("staytime", stayTime + "");
                    behavor.addExtParam("openpagetime", info.entryTime);
                    behavor.setTrackId(info.actionId);
                    behavor.setTrackToken(info.actionToken);
                    behavor.setTrackDesc(info.actionDesc);
                    if (this.respond > 0) {
                        behavor.addExtParam("respond", this.respond + "");
                    }
                    AutoTrackerAdapter adapter = AutoTracker.getImpl().getAutoTrackerAdapter();
                    if (adapter != null) {
                        adapter.onAssembleBehavior(behavor);
                    }
                }
            }
            TrackAutoHelper.getInstance().logAutoBehavorPageEnd(null, view, null, null, behavor, false);
            this.respond = 0;
            if (this.lastClickTime == this.resumeHandleClickTime || this.lastClickTime == this.handleClickTime) {
                this.lastClickTime = 0;
            }
        }
    }

    public void onPageFinishInitializing() {
        if (this.lastClickTime > 0 && this.handleClickTime < this.lastClickTime) {
            this.respond = System.currentTimeMillis() - this.lastClickTime;
            this.handleClickTime = this.lastClickTime;
        }
    }

    private void a() {
        if (this.lastClickTime > 0 && this.resumeHandleClickTime < this.lastClickTime) {
            this.respondOnResume = System.currentTimeMillis() - this.lastClickTime;
            this.resumeHandleClickTime = this.lastClickTime;
            if (this.respondOnResume > 0 && this.respond == 0) {
                this.respond = this.respondOnResume;
            }
        }
    }

    public void trackClick(final View rootContentView, String pageId, String appId, boolean isBindingImmediately) {
        if (rootContentView != null && rootContentView.getViewTreeObserver() != null && this.m) {
            this.o = rootContentView;
            this.p = appId;
            this.q = pageId;
            addDelegate(rootContentView, pageId, appId, isBindingImmediately);
            final View view = rootContentView;
            final String str = pageId;
            final String str2 = appId;
            final boolean z = isBindingImmediately;
            final OnGlobalLayoutListener globalLayoutListener = new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    TrackIntegrator.this.postAddDelegate(view, str, str2, z, 0);
                }
            };
            rootContentView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
            rootContentView.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                public void onViewDetachedFromWindow(View view) {
                    try {
                        if (VERSION.SDK_INT < 16) {
                            rootContentView.getViewTreeObserver().removeGlobalOnLayoutListener(globalLayoutListener);
                        } else {
                            rootContentView.getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
                        }
                    } catch (Throwable th) {
                    }
                }

                public void onViewAttachedToWindow(View view) {
                }
            });
        }
    }

    public String getXpath(View view) {
        return XPathFinder.a(view);
    }

    public void postAddDelegate(View rootContentView, String pageId, String appId, boolean isBindingImmediately, long time) {
        final View view = rootContentView;
        final String str = pageId;
        final String str2 = appId;
        final boolean z = isBindingImmediately;
        j.postDelayed(new Runnable() {
            public void run() {
                TrackIntegrator.this.addDelegate(view, str, str2, z);
            }
        }, time);
    }

    public void addDelegate(View rootContentView, String pageId, String appId, boolean isBindingImmediately) {
        try {
            List touchables = rootContentView.getTouchables();
            int i2 = 0;
            while (touchables != null && i2 < touchables.size()) {
                View touchable = touchables.get(i2);
                if (touchable != null) {
                    TouchDelegate delegate = touchable.getTouchDelegate();
                    if (!(delegate instanceof TrackTouchDelegate)) {
                        AdapterView adapterView = null;
                        ViewParent parentView = touchable.getParent();
                        if (parentView != null && (parentView instanceof View)) {
                            if (((View) parentView) instanceof AdapterView) {
                                adapterView = (AdapterView) touchable.getParent();
                            }
                            touchable.setTouchDelegate(new TrackTouchDelegate(adapterView, touchable, rootContentView, this.l, delegate, pageId, appId, isBindingImmediately));
                        }
                    }
                }
                i2++;
            }
        } catch (Throwable t2) {
            LoggerFactory.getTraceLogger().error(TAG, t2);
        }
    }

    public void refreshViewDelegate() {
        addDelegate(this.o, this.q, this.p, false);
    }

    public void updateCurrentPageInfo(PageInfo pageInfo) {
        this.a = pageInfo;
    }

    public PageInfo getCurrentPageInfo() {
        return this.a;
    }

    public PageInfo getPageMonitorCurrentPageInfo() {
        return this.b;
    }

    public void setEntityContentTagId(int tagId) {
        entityContentTagId = tagId;
    }

    public int getEntityContentTagId() {
        return entityContentTagId;
    }

    public static int getSemTagId() {
        return semTagId;
    }

    public static void setSemTagId(int semTagId2) {
        semTagId = semTagId2;
    }

    public HashMap<String, PageInfo> getPageInfos() {
        return this.h;
    }

    private boolean a(String activityName) {
        if ("com.alipay.android.launcher.TabLauncher".equals(activityName)) {
            return false;
        }
        return this.n;
    }

    public RecyclerViewIndexGetter getmRecyclerViewIndexGetter() {
        return this.mRecyclerViewIndexGetter;
    }

    public void setmRecyclerViewIndexGetter(RecyclerViewIndexGetter mRecyclerViewIndexGetter2) {
        this.mRecyclerViewIndexGetter = mRecyclerViewIndexGetter2;
    }

    public boolean isCollectFragment(String fragmentName) {
        return this.n;
    }

    public OnAutoClickListener getOnAutoClickListener() {
        return this.c;
    }

    public static long getLastActiveTime() {
        return mLastActiveTime;
    }

    public static void setLastActiveTime(long mLastActiveTime2) {
        mLastActiveTime = mLastActiveTime2;
    }

    public void setOnAutoClickListener(OnAutoClickListener onAutoClickListener) {
        this.c = onAutoClickListener;
    }
}
