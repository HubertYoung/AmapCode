package com.alipay.mobile.common.logging.api;

public interface DeviceProperty {
    public static final String ALIAS_COOLPAD = "coolpad";
    public static final String ALIAS_HUAWEI = "huawei";
    public static final String ALIAS_LEECO = "leeco";
    public static final String ALIAS_LENOVO = "lenovo";
    public static final String ALIAS_MEIZU = "meizu";
    public static final String ALIAS_NUBIA = "nubia";
    public static final String ALIAS_ONEPLUS = "oneplus";
    public static final String ALIAS_OPPO = "oppo";
    public static final String ALIAS_QIKU = "qiku";
    public static final String ALIAS_SAMSUNG = "samsung";
    public static final String ALIAS_VIVO = "vivo";
    public static final String ALIAS_XIAOMI = "xiaomi";
    public static final String ALIAS_ZTE = "zte";

    String getBrandName();

    String getDeviceAlias();

    String getDisplayID();

    String getFingerPrint();

    String getManufacturer();

    String getRomVersion();

    boolean isCoolpadDevice();

    boolean isHuaweiDevice();

    boolean isLeEcoDevice();

    boolean isLenovoDevice();

    boolean isMeizuDevice();

    boolean isNubiaDevice();

    boolean isOnePlusDevice();

    boolean isOppoDevice();

    boolean isQikuDevice();

    boolean isSamsungDevice();

    boolean isVivoDevice();

    boolean isXiaomiDevice();

    boolean isZteDevice();
}
