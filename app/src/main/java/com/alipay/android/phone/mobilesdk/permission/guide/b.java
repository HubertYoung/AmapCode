package com.alipay.android.phone.mobilesdk.permission.guide;

import com.alipay.android.phone.mobilesdk.permission.utils.h;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: BehaveLogger */
public final class b {
    public static void a(String bizType, String permissionName, String status) {
        HashMap map = new HashMap(1);
        map.put(permissionName, status);
        a(bizType, permissionName, map);
    }

    public static void a(String bizType, String permissionName, HashMap<String, String> statuses) {
        String msg;
        try {
            Behavor behavor = new Behavor();
            behavor.setSeedID("AUTH_GUIDE");
            behavor.setUserCaseID(bizType);
            behavor.setBehaviourPro("PermissionGuide");
            behavor.setParam1(bizType);
            behavor.setParam2(permissionName);
            if (1 == statuses.size()) {
                String status = statuses.get(permissionName);
                behavor.setParam3(status);
                msg = "writeLog(" + bizType + "," + permissionName + "," + status + ")";
            } else {
                behavor.setParam3("0");
                for (Entry entry : statuses.entrySet()) {
                    behavor.addExtParam((String) entry.getKey(), (String) entry.getValue());
                }
                msg = "writeLog(" + bizType + "," + permissionName + ",0," + h.a((Map<K, V>) statuses) + ")";
            }
            behavor.setLoggerLevel(2);
            LoggerFactory.getTraceLogger().info("Permissions", msg);
            LoggerFactory.getBehavorLogger().event(null, behavor);
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("Permissions", "writeLog", tr);
        }
    }
}
