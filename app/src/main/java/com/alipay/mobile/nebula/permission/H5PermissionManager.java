package com.alipay.mobile.nebula.permission;

public interface H5PermissionManager {
    public static final String h5_jsapiPermission = "h5_jsapiPermission";
    public static final String level = "level";
    public static final String whitelist = "whitelist";

    String getAliLevel(String str);

    void putOffLineConfig(String str);

    void putOnLineConfig(String str);

    void setDefaultPermissionConfig();

    boolean urlInWhiteList(String str, String str2);
}
