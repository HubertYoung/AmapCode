package com.autonavi.map.permission;

import java.util.List;

public final class PermissionModel {
    public String a;
    public String b;
    public PermissionGroup c = PermissionGroup.NONE;

    public enum PermissionGroup {
        NONE,
        STORAGE,
        LOCATION,
        PHONE,
        MICROPHONE
    }

    public PermissionModel(String str, String str2, PermissionGroup permissionGroup) {
        this.a = str;
        this.b = str2;
        this.c = permissionGroup;
    }

    public static PermissionModel a(List<PermissionModel> list, String str) {
        if (list == null || list.size() == 0) {
            return null;
        }
        for (PermissionModel next : list) {
            if (next.a.equals(str)) {
                return next;
            }
        }
        return null;
    }
}
