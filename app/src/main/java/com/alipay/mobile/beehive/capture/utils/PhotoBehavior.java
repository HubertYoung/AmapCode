package com.alipay.mobile.beehive.capture.utils;

import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PhotoBehavior {
    public static final String PARAM_1 = "param1";
    public static final String PARAM_2 = "param2";
    public static final String PARAM_3 = "param3";
    private static final String TAG = "UserBehavior";

    public static void event(String actionCode, String seedId, String userCaseId, Map<String, String> extras) {
        if (extras == null) {
            try {
                extras = new HashMap<>();
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().error((String) TAG, (Throwable) e);
                return;
            }
        }
        extras.put("ACTION_CODE", actionCode);
        Behavor behavor = new Behavor();
        behavor.setUserCaseID(userCaseId);
        behavor.setBehaviourPro("Sight");
        behavor.setSeedID(seedId);
        if (!extras.isEmpty()) {
            for (Entry e2 : extras.entrySet()) {
                if (TextUtils.equals(PARAM_1, (CharSequence) e2.getKey())) {
                    behavor.setParam1((String) e2.getValue());
                } else if (TextUtils.equals(PARAM_2, (CharSequence) e2.getKey())) {
                    behavor.setParam2((String) e2.getValue());
                } else if (TextUtils.equals(PARAM_3, (CharSequence) e2.getKey())) {
                    behavor.setParam3((String) e2.getValue());
                } else {
                    behavor.addExtParam((String) e2.getKey(), (String) e2.getValue());
                }
            }
        }
        LoggerFactory.getBehavorLogger().event(null, behavor);
    }
}
