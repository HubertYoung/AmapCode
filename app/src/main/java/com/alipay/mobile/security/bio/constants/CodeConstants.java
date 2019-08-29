package com.alipay.mobile.security.bio.constants;

import java.util.Map;

public class CodeConstants {
    public static String ANDROID_VERSION_LOW = "Z1004";
    public static String ERROR_CAMERA = "Z1002";
    public static String ERROR_LOAD_SO = "Z1001";
    public static String INVALID_ARGUMENT = "Z1010";
    public static String NETWORK_ERROR = "Z1012";
    public static String NETWORK_TIMEOUT = "Z1011";
    public static String OUT_TIME = "Z1006";
    public static String OVER_TIME = "Z1005";
    public static String SERVER_FAIL = "Z1007";
    public static String SERVER_PARAM_ERROR = "Z1000";
    public static String USER_BACK = "Z1009";
    public static String USER_QUITE = "Z1008";
    public static String USER_UNSURPPORT_CPU = "Z1003";
    private static Map a = new a();

    public static String getMessage(String str) {
        return String.format("%s(%s)", new Object[]{a.get(str), str});
    }
}
