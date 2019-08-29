package com.alipay.mobile.monitor.track.interceptor;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.monitor.Performance.Builder;
import com.alipay.mobile.common.logging.api.monitor.PerformanceID;
import com.alipay.mobile.common.logging.util.MD5Util;
import com.alipay.mobile.common.monitor.api.reflect.DeviceInfoReflector;
import com.alipay.mobile.monitor.track.AutoTracker;
import com.alipay.mobile.monitor.track.AutoTrackerHolder;
import com.alipay.mobile.monitor.track.TrackAutoHelper;
import com.alipay.mobile.monitor.track.TrackIntegrator;

public class AutoClickInterceptor {
    public static int TAG_ID = 621215851;

    public void reportViewTrackEvent(View view, String defaultControlId, String pageId, String appId, String xPath) {
        if (view != null && !TrackIntegrator.getInstance().isDisableAutoTrackView(view)) {
            TrackIntegrator.getInstance().lastClickTime = System.currentTimeMillis();
            Object spm = view.getTag(TAG_ID);
            if (spm != null) {
                TrackIntegrator.getInstance().lastClickViewSpm = spm.toString();
            } else {
                TrackIntegrator.getInstance().lastClickViewSpm = "";
            }
            StringBuilder viewDescriptionBuilder = new StringBuilder();
            if ("test".equals(LoggerFactory.getLogContext().getReleaseType())) {
                a(view, viewDescriptionBuilder);
            }
            String actionDesc = viewDescriptionBuilder.toString();
            String actionTimestamp = Long.toString(System.currentTimeMillis());
            AutoTrackerHolder holder = AutoTracker.getImpl().getAutoTrackerHolder();
            String actionToken = "A" + MD5Util.encrypt(DeviceInfoReflector.getmDid(holder != null ? holder.getContext() : null) + actionTimestamp);
            String controlId = getControlId(view, defaultControlId);
            if (TextUtils.isEmpty(controlId)) {
                LoggerFactory.getTraceLogger().warn(TrackIntegrator.TAG, (String) "控件唯一标识获取失败，请检查是否设置id属性或者控件描述");
            }
            Object semInfo = null;
            TrackIntegrator.getInstance();
            int semTagId = TrackIntegrator.getSemTagId();
            if (semTagId != 0) {
                semInfo = view.getTag(semTagId);
            }
            TrackIntegrator.getInstance().lastClickViewId = controlId;
            TrackIntegrator.getInstance().lastClickViewTime = System.currentTimeMillis();
            TrackIntegrator.getInstance().setLastClickViewSpm(TrackIntegrator.getInstance().getViewTag(view), semInfo);
            TrackIntegrator.getInstance();
            TrackIntegrator.setLastActiveTime(System.currentTimeMillis());
            String actionId = pageId + '|' + controlId;
            LoggerFactory.getLogContext().putContextParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN, actionToken);
            LoggerFactory.getLogContext().putContextParam(LogContext.LOCAL_STORAGE_ACTIONID, actionId);
            LoggerFactory.getLogContext().putContextParam(LogContext.LOCAL_STORAGE_ACTIONTIMESTAMP, actionTimestamp);
            LoggerFactory.getLogContext().putContextParam(LogContext.LOCAL_STORAGE_ACTIONDESC, actionDesc);
            LoggerFactory.getLogContext().putLocalParam(LogContext.LOCAL_STORAGE_ACTIONTOKEN, actionToken);
            LoggerFactory.getLogContext().putLocalParam(LogContext.LOCAL_STORAGE_ACTIONID, actionId);
            LoggerFactory.getLogContext().putLocalParam(LogContext.LOCAL_STORAGE_ACTIONTIMESTAMP, actionTimestamp);
            LoggerFactory.getLogContext().putLocalParam(LogContext.LOCAL_STORAGE_ACTIONDESC, actionDesc);
            LoggerFactory.getTraceLogger().debug(TrackIntegrator.TAG, "控件点击:" + actionId + "(" + actionDesc + "), appid=" + appId);
            if ("test".equals(LoggerFactory.getLogContext().getReleaseType())) {
                if (TextUtils.isEmpty(appId)) {
                    LoggerFactory.getTraceLogger().warn(TrackIntegrator.TAG, (String) "appid为空");
                }
                if (appId == null || !appId.equals(LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_APPID))) {
                    LoggerFactory.getTraceLogger().warn(TrackIntegrator.TAG, "当前控件和appID不一致：" + LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_APPID));
                }
                String viewID = LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_VIEWID);
                if (viewID != null && !actionId.startsWith(viewID)) {
                    LoggerFactory.getTraceLogger().warn(TrackIntegrator.TAG, "当前控件和viewID不一致：" + viewID);
                }
            }
            String contentId = "";
            int tagId = TrackIntegrator.getInstance().getEntityContentTagId();
            if (tagId != 0) {
                Object entityContentId = view.getTag(tagId);
                if (entityContentId != null) {
                    try {
                        contentId = entityContentId.toString();
                    } catch (Throwable th) {
                    }
                }
            }
            Behavor behavor = new Behavor();
            behavor.setAppID(appId);
            behavor.setSeedID(controlId);
            behavor.setEntityContentId(contentId);
            behavor.setxPath(xPath);
            behavor.setViewID(pageId);
            behavor.setBehaviourPro(TrackAutoHelper.AUTO_TRACK_TYPE);
            behavor.addExtParam(TrackAutoHelper.PARAMS_HEADER, TrackAutoHelper.PARAMS_HEADER_AUTO);
            LoggerFactory.getBehavorLogger().autoClick(behavor);
            new Builder().setParam2("action_start").performance(PerformanceID.MONITORPOINT_SDKMONITOR);
        }
    }

    public static String getControlId(View view, String defaultControlId) {
        String controlId = null;
        Object tag = view.getTag(TAG_ID);
        if (tag != null) {
            controlId = tag.toString();
        }
        if (TextUtils.isEmpty(controlId)) {
            CharSequence contentDesc = view.getContentDescription();
            controlId = contentDesc != null ? contentDesc.toString() : null;
        }
        if (TextUtils.isEmpty(controlId)) {
            try {
                controlId = -1 != view.getId() ? view.getResources().getResourceEntryName(view.getId()) : "";
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().warn(TrackIntegrator.TAG, "id的资源名称获获取失败：" + view.getId());
            }
        }
        if (TextUtils.isEmpty(controlId)) {
            return defaultControlId;
        }
        return controlId;
    }

    public void trackWindowManagerView(View clickView) {
        LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "trackWindowManagerView");
        if (!TrackIntegrator.getInstance().isDisableAutoTrackView(clickView)) {
            View[] views = WindowManagerHook.getManager().getWindowViews();
            if (views != null && views.length > 0) {
                View topView = views[views.length - 1];
                if (topView != null) {
                    LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "isAddDialog track dialog views " + views.length + " topView = " + topView.getClass().getName());
                    TrackIntegrator.getInstance().trackClick(topView, TrackIntegrator.lastTrackPage, LoggerFactory.getLogContext().getContextParam(LogContext.STORAGE_APPID), true);
                    return;
                }
                LoggerFactory.getTraceLogger().info(TrackIntegrator.TAG, "topWindowView is null");
            }
        }
    }

    private void a(View view, StringBuilder result) {
        if (result != null && result.length() <= 10 && view.getVisibility() == 0 && view.isEnabled()) {
            CharSequence contentDescription = view.getContentDescription();
            if (!TextUtils.isEmpty(contentDescription)) {
                result.append(contentDescription);
            } else if (view instanceof TextView) {
                result.append(((TextView) view).getText());
            } else if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    a(vg.getChildAt(i), result);
                }
            }
        }
    }
}
