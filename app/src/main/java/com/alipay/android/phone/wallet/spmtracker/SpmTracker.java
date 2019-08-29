package com.alipay.android.phone.wallet.spmtracker;

import android.os.Parcelable;
import android.util.Pair;
import android.view.View;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.List;
import java.util.Map;

public class SpmTracker {
    public static void setIsDebug(boolean isDebug) {
        LoggerFactory.getLogContext().getSpmMonitor().setIsDebug(isDebug);
    }

    public static void onPageCreate(Object page, String spmId) {
        LoggerFactory.getLogContext().getSpmMonitor().pageOnCreate(page, spmId);
    }

    public static void onPageResume(Object page, String spmId) {
        LoggerFactory.getLogContext().getSpmMonitor().pageOnResume(page, spmId);
    }

    public static void onPagePause(Object page, String spmId, String bizCode, Map<String, String> params, String chInfo) {
        LoggerFactory.getLogContext().getSpmMonitor().pageOnPause(page, spmId, bizCode, params, chInfo);
    }

    public static void onPagePause(Object page, String spmId, String bizCode, Map<String, String> map) {
        LoggerFactory.getLogContext().getSpmMonitor().pageOnPause(page, spmId, bizCode, map);
    }

    public static void onPageDestroy(Object page) {
        LoggerFactory.getLogContext().getSpmMonitor().pageOnDestroy(page);
    }

    public static void updateSrcSpm(Object page, String srcSpm) {
        LoggerFactory.getLogContext().getSpmMonitor().upateSrcSpm(page, srcSpm);
    }

    public static String getSrcSpm(Object page) {
        return LoggerFactory.getLogContext().getSpmMonitor().getSrcSpm(page);
    }

    public static void click(Object page, String spmId, String bizCode) {
        click(page, spmId, bizCode, null);
    }

    public static void click(Object page, String spmId, String bizCode, Map<String, String> param4) {
        click(page, spmId, bizCode, 2, param4);
    }

    public static void click(Object page, String spmId, String bizCode, int loggerLevel, Map<String, String> param4) {
        LoggerFactory.getLogContext().getSpmMonitor().behaviorClick(page, spmId, bizCode, loggerLevel, null, param4);
    }

    public static void clickWithEntityId(Object page, String spmId, String bizCode, String entityId) {
        clickWithEntityId(page, spmId, bizCode, entityId, null);
    }

    public static void clickWithEntityId(Object page, String spmId, String bizCode, String entityId, Map<String, String> param4) {
        clickWithEntityId(page, spmId, bizCode, 2, entityId, param4);
    }

    public static void clickWithEntityId(Object page, String spmId, String bizCode, int loggerLevel, String entityId, Map<String, String> param4) {
        LoggerFactory.getLogContext().getSpmMonitor().behaviorClick(page, spmId, bizCode, loggerLevel, entityId, param4);
    }

    public static void expose(Object page, String spmId, String bizCode) {
        expose(page, spmId, bizCode, null);
    }

    public static void expose(Object page, String spmId, String bizCode, Map<String, String> param4) {
        expose(page, spmId, bizCode, 2, param4);
    }

    public static void expose(Object page, String spmId, String bizCode, int loggerLevel, Map<String, String> param4) {
        LoggerFactory.getLogContext().getSpmMonitor().behaviorExpose(page, spmId, bizCode, loggerLevel, null, param4);
    }

    public static void exposeWithEntityId(Object page, String spmId, String bizCode, String entityId) {
        exposeWithEntityId(page, spmId, bizCode, entityId, null);
    }

    public static void exposeWithEntityId(Object page, String spmId, String bizCode, String entityId, Map<String, String> param4) {
        exposeWithEntityId(page, spmId, bizCode, 2, entityId, param4);
    }

    public static void exposeWithEntityId(Object page, String spmId, String bizCode, int loggerLevel, String entityId, Map<String, String> param4) {
        LoggerFactory.getLogContext().getSpmMonitor().behaviorExpose(page, spmId, bizCode, loggerLevel, entityId, param4);
    }

    public static void mergeExpose(Object page, String spmId, String bizCode, Map<String, String> param4, int position) {
        mergeExpose(page, spmId, bizCode, "", param4, position);
    }

    public static void mergeExpose(Object page, String spmId, String bizCode, String rpcId, Map<String, String> param4, int position) {
        mergeExposeWithEntityId(page, spmId, bizCode, null, rpcId, param4, position);
    }

    public static void mergeExposeWithEntityId(Object page, String spmId, String bizCode, String entityId, Map<String, String> param4, int position) {
        mergeExposeWithEntityId(page, spmId, bizCode, entityId, "", param4, position);
    }

    public static void mergeExposeWithEntityId(Object page, String spmId, String bizCode, String entityId, String rpcId, Map<String, String> param4, int position) {
        LoggerFactory.getLogContext().getSpmMonitor().mergeExpose(page, spmId, bizCode, 2, entityId, param4, rpcId, position);
    }

    @Deprecated
    public static void mergeExpose(Object page, String spmId, String bizCode, Map<String, String> param4) {
        mergeExpose(page, spmId, bizCode, 2, param4);
    }

    @Deprecated
    public static void mergeExpose(Object page, String spmId, String bizCode, int loggerLevel, Map<String, String> param4) {
        LoggerFactory.getLogContext().getSpmMonitor().mergeExpose(page, spmId, bizCode, loggerLevel, null, param4, null, -1);
    }

    @Deprecated
    public static void mergeExposeWithEntityId(Object page, String spmId, String bizCode, String entityId, Map<String, String> param4) {
        mergeExposeWithEntityId(page, spmId, bizCode, 2, entityId, param4);
    }

    @Deprecated
    public static void mergeExposeWithEntityId(Object page, String spmId, String bizCode, int loggerLevel, String entityId, Map<String, String> param4) {
        LoggerFactory.getLogContext().getSpmMonitor().mergeExpose(page, spmId, bizCode, loggerLevel, entityId, param4, null, -1);
    }

    public static void slide(Object page, String spmId, String bizCode) {
        slide(page, spmId, bizCode, null);
    }

    public static void slide(Object page, String spmId, String bizCode, Map<String, String> param4) {
        slide(page, spmId, bizCode, 2, param4);
    }

    public static void slide(Object page, String spmId, String bizCode, int loggerLevel, Map<String, String> param4) {
        LoggerFactory.getLogContext().getSpmMonitor().behaviorSlide(page, spmId, bizCode, loggerLevel, null, param4);
    }

    public static void slideWithEntityId(Object page, String spmId, String bizCode, String entityId, Map<String, String> param4) {
        slideWithEntityId(page, spmId, bizCode, 2, entityId, param4);
    }

    public static void slideWithEntityId(Object page, String spmId, String bizCode, int loggerLevel, String entityId, Map<String, String> param4) {
        LoggerFactory.getLogContext().getSpmMonitor().behaviorSlide(page, spmId, bizCode, loggerLevel, entityId, param4);
    }

    public static String getMiniPageId(Object page) {
        return LoggerFactory.getLogContext().getSpmMonitor().getMiniPageId(page);
    }

    public static String getPageId(Object page) {
        return LoggerFactory.getLogContext().getSpmMonitor().getPageId(page);
    }

    public static String getPageSpm(Object page) {
        return LoggerFactory.getLogContext().getSpmMonitor().getPageSpm(page);
    }

    public static Object getTopPage() {
        return LoggerFactory.getLogContext().getSpmMonitor().getTopPage();
    }

    public static void setViewSpmTag(View view, String spmId) {
        setViewSpmTag(view, spmId, false);
    }

    public static void setViewSpmTag(View view, String spmId, boolean isPopupView) {
        LoggerFactory.getLogContext().getSpmMonitor().setSpmTag(view, spmId, isPopupView);
    }

    public static void setViewContentTag(View view, String scmId) {
        setViewContentTag(view, scmId, false);
    }

    public static void setViewContentTag(View view, String scmId, boolean isPopupView) {
        LoggerFactory.getLogContext().getSpmMonitor().setContentTag(view, scmId, isPopupView);
    }

    public static void clearViewSpmTag(View view) {
        setViewSpmTag(view, "");
    }

    public static String getLastClickSpmId() {
        return LoggerFactory.getLogContext().getSpmMonitor().getLastClickSpmId();
    }

    public static String getLastClickSpmIdByPage(Object page) {
        return LoggerFactory.getLogContext().getSpmMonitor().getLastClickSpmIdByPage(page);
    }

    public static Map<String, String> getTracerInfo(Object page) {
        return LoggerFactory.getLogContext().getSpmMonitor().getTracerInfo(page);
    }

    public static void setLastClickSpm(String spm) {
        LoggerFactory.getLogContext().getSpmMonitor().setLastClickSpm(spm);
    }

    protected static void setMergeConfig(String config) {
        LoggerFactory.getLogContext().getSpmMonitor().setMergeConfig(config);
    }

    public static boolean isPageStarted(Object page) {
        return LoggerFactory.getLogContext().getSpmMonitor().isPageStarted(page);
    }

    public static String getPageChInfo(Object page) {
        return LoggerFactory.getLogContext().getSpmMonitor().getPageChInfo(page);
    }

    public static void setHomePageTabSpms(List<String> spmIds) {
        LoggerFactory.getLogContext().getSpmMonitor().setHomePageTabSpms(spmIds);
    }

    public static Pair<String, Integer> getNextPageParams() {
        return null;
    }

    public static Pair<String, String> getNextPageNewChinfo() {
        return null;
    }

    public static Parcelable getCurrentSpmPageInfo() {
        return null;
    }
}
