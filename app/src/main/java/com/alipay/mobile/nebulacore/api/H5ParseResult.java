package com.alipay.mobile.nebulacore.api;

public class H5ParseResult {
    public static final int CERT_PATH_NOT_EXIST = 4;
    public static final int EMPTY_RES = 8;
    public static final int INVALID_PARAM = 1;
    public static final int OFFLINE_PATH_NOT_EXIST = 2;
    public static final int SUCCESS = 0;
    public static final int TAR_PATH_NOT_EXIST = 3;
    public static final int TAR_SIGNATURE_IS_EMPTY = 5;
    public static final int UN_KNOW_EXCEPTION = 7;
    public static final int VERIFY_FAIL = 6;
    public int code;
    public String msg;
}
