package com.alipay.mobile.common.logging.api.monitor;

import java.util.Map;

public class MTBizReportFilter {
    private static Entity a;

    public interface Entity {
        Map<String, String> onBeforeReportForUeo(String str, String str2, String str3, Map<String, String> map, Map<String, String> map2);
    }

    public static void setEntity(Entity entity) {
        if (a == null) {
            a = entity;
        }
    }

    public static Entity getEntity() {
        return a;
    }
}
