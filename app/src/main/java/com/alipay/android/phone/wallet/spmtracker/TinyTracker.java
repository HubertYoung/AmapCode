package com.alipay.android.phone.wallet.spmtracker;

import android.os.Parcelable;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Map;

public class TinyTracker {
    public static void onPageResume(Object page, String spmId) {
        LoggerFactory.getLogContext().getTinyPageMonitor().pageOnResume(page, spmId);
    }

    public static void onPagePause(Object page, String spmId, String bizCode, Map<String, String> params, String chInfo) {
        LoggerFactory.getLogContext().getTinyPageMonitor().pageOnPause(page, spmId, bizCode, params, chInfo);
    }

    public static void onPagePause(Object page, String spmId, String bizCode, Map<String, String> map) {
        LoggerFactory.getLogContext().getTinyPageMonitor().pageOnPause(page, spmId, bizCode, map);
    }

    public static void onPageDestroy(Object page) {
        LoggerFactory.getLogContext().getTinyPageMonitor().pageOnDestroy(page);
    }

    public static void setPageParams(String params) {
        setPageParams(params, 0);
    }

    public static void setPageParams(String params, int step) {
        LoggerFactory.getLogContext().getTinyPageMonitor().setPageParams(params, step);
    }

    public static void setCurrentPageInfo(Parcelable pageInfo) {
        LoggerFactory.getLogContext().getTinyPageMonitor().setCurrentPageInfo(pageInfo);
    }
}
