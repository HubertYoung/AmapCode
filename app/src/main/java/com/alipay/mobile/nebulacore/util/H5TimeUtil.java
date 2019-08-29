package com.alipay.mobile.nebulacore.util;

import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.util.H5Log;

public class H5TimeUtil {
    public static final String CREATE_PAGE = "create_page";
    public static final String CREATE_WEBVIEW = "create_webView";
    public static final String INIT_PLUGIN = "init_plugin";
    public static final String PARSER_APP = "parser_app";
    public static final String PREPARE_APP = "prepare_app";
    public static final String START_APP = "start_app";
    public static final String TAG = "H5TimeLog";

    public static void timeLog(String phase, long startTime) {
        H5Log.d(TAG, phase + MergeUtil.SEPARATOR_KV + (System.currentTimeMillis() - startTime));
    }

    public static void timeLog(String phase, String subPhase, long startTime) {
        H5Log.d(TAG, phase + MergeUtil.SEPARATOR_KV + subPhase + MergeUtil.SEPARATOR_KV + (System.currentTimeMillis() - startTime));
    }
}
