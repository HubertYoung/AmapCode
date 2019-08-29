package com.alipay.mobile.monitor.track.spm;

import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.logging.api.behavor.Behavor.Builder;
import java.util.Map.Entry;

public class SpmUtils {
    public static boolean isDebug;

    public static String objectToString(Object object) {
        if (object != null) {
            return object.getClass().getName() + '@' + Integer.toHexString(object.hashCode());
        }
        return null;
    }

    public static void printBehaviour(String tag, Builder builder, String event) {
        if (isDebug) {
            Behavor behaviour = builder.build();
            StringBuilder sb = new StringBuilder(event).append(", [seedId]").append(behaviour.getSeedID()).append(", [pageId]").append(behaviour.getPageId());
            if (behaviour.getParam1() != null) {
                sb.append(", [param1]").append(behaviour.getParam1());
            }
            if (behaviour.getParam2() != null) {
                sb.append(", [param2]").append(behaviour.getParam2());
            }
            if (behaviour.getParam3() != null) {
                sb.append(", [param3]").append(behaviour.getParam3());
            }
            for (Entry entry : behaviour.getExtParams().entrySet()) {
                sb.append(", [").append((String) entry.getKey()).append("]").append((String) entry.getValue());
            }
            SpmLogCator.debug(tag, sb.toString());
        }
    }
}
