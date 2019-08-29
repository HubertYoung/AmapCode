package com.alipay.mobile.nebula.provider;

public interface H5JSApiPermissionProvider {
    public static final String LEVEL_ABOVE_MEDIUM = "level_abovemedium";
    public static final String LEVEL_HIGH = "level_high";
    public static final String LEVEL_LOW = "level_low";
    public static final String LEVEL_MEDIUM = "level_medium";
    public static final String LEVEL_NONE = "level_none";

    boolean hasDomainPermission(String str, String str2);

    boolean hasThisPermission(String str, String str2);
}
