package com.taobao.agoo.control.data;

public abstract class BaseDO {
    public static final String JSON_CMD = "cmd";
    public static final String JSON_DEVICE_ID = "deviceId";
    public static final String JSON_ERRORCODE = "resultCode";
    public static final String JSON_SUCCESS = "success";
    public static final String JSON_UTDID = "utdid";
    public String cmd;

    public abstract byte[] buildData();
}
