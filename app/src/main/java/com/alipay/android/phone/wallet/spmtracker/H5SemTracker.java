package com.alipay.android.phone.wallet.spmtracker;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Map;

public class H5SemTracker {
    public static void semClick(String pageId, String semId, Map<String, String> param4, String bizCode) {
        LoggerFactory.getLogContext().getSemMonitor().semClick(pageId, semId, param4, bizCode);
    }

    public static void semExpo(String pageId, String semId, Map<String, String> param4, String bizCode) {
        LoggerFactory.getLogContext().getSemMonitor().semExpo(pageId, semId, param4, bizCode);
    }
}
