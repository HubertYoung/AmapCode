package com.amap.location.uptunnel.core.db.a;

import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;

/* compiled from: BinaryTable */
public class a {
    public static final String[] a = {AutoJsonUtils.JSON_ID, "type", "value", "time", "size"};

    public static String a(String str) {
        StringBuilder sb = new StringBuilder("create table if not exists ");
        sb.append(str);
        sb.append(" (ID integer PRIMARY KEY AUTOINCREMENT NOT NULL, type integer, value blob, time long, size integer);");
        return sb.toString();
    }
}
