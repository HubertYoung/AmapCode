package com.alipay.android.phone.wallet.spmtracker;

import android.view.View;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.Map;

public class SemTracker {
    public static void setSemTag(View view, String bizCode, String semId, String rpcId, int pos, Map<String, String> entityInfo) {
        LoggerFactory.getLogContext().getSemMonitor().setSemTag(view, bizCode, semId, rpcId, pos, entityInfo);
    }

    public static void clearSemTag(View view) {
        LoggerFactory.getLogContext().getSemMonitor().clearSemTag(view);
    }

    public static String getSemInfo(View view) {
        return LoggerFactory.getLogContext().getSemMonitor().getSemInfo(view);
    }

    public static String createSemInfo(String semId, String rpcId) {
        return LoggerFactory.getLogContext().getSemMonitor().createSemInfo(semId, rpcId);
    }

    public static String getLastClickSemInfo(Object page) {
        return LoggerFactory.getLogContext().getSemMonitor().getLastClickSemInfo(page);
    }
}
