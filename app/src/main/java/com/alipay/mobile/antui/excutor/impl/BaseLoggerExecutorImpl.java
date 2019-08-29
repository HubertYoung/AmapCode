package com.alipay.mobile.antui.excutor.impl;

import android.util.Log;
import com.alipay.mobile.antui.excutor.LoggerExecutor;
import java.util.Map;

public class BaseLoggerExecutorImpl implements LoggerExecutor {
    public void mtBizReport(String tag, String content) {
        Log.d(tag, "mtBizReport: content : " + content);
    }

    public void debug(String tag, String content) {
        Log.d(tag, content);
    }

    public void info(String tag, String content) {
        Log.i(tag, content);
    }

    public void error(String tag, String content) {
        Log.e(tag, content);
    }

    public void eventBehavor(String ucId, String seedId) {
        Log.d(ucId, "eventBehavor: seedId: " + seedId);
    }

    public void eventBehavor(String ucId, String seedId, String param1, String param2, String param3, Map<String, String> param4) {
        Log.d(ucId, "eventBehavor: seedId: " + seedId);
    }
}
