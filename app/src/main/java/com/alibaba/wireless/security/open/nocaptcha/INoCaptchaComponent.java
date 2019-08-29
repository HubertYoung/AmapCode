package com.alibaba.wireless.security.open.nocaptcha;

import android.os.Handler;
import android.view.MotionEvent;
import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;
import java.util.HashMap;

@InterfacePluginInfo(pluginName = "nocaptcha")
public interface INoCaptchaComponent extends IComponent {
    public static final int NC_INIT_STAGE = 1;
    public static final int NC_VERIFY_SATGE = 2;
    public static final int SG_ERROR_NOCAPTCHA = 1200;
    public static final int SG_NC_FAILED = 105;
    public static final int SG_NC_HTTP_NO_TOKEN = 1206;
    public static final int SG_NC_HTTP_REQUEST_FAIL = 1207;
    public static final int SG_NC_INIT_SUCCEED = 101;
    public static final int SG_NC_INVALID_PARA = 1201;
    public static final int SG_NC_NOT_INIT_YET = 1203;
    public static final int SG_NC_NO_MEMORY = 1202;
    public static final int SG_NC_QUEUE_FULL = 1204;
    public static final int SG_NC_RETRY = 103;
    public static final int SG_NC_RETRY_TO_MAX = 1205;
    public static final int SG_NC_SERVER_FAULT = 104;
    public static final int SG_NC_SERVER_RETURN_ERROR = 1208;
    public static final int SG_NC_START = 100;
    public static final int SG_NC_UNKNOWN_ERROR = 1299;
    public static final int SG_NC_VERI_APPKEY_MISMATCH = 1211;
    public static final int SG_NC_VERI_GET_TRACE_FAIL = 1210;
    public static final int SG_NC_VERI_GET_WUA_FAIL = 1209;
    public static final int SG_NC_VERI_SESSION_EXPIRED = 1212;
    public static final int SG_NC_VERI_SUCCEED = 102;
    public static final int SG_NC_VERI_WUA_DATA_FILE_MISMATCHA = 1214;
    public static final int SG_NC_VERI_WUA_INCORRECT_DATA_FILE = 1216;
    public static final int SG_NC_VERI_WUA_INVALID_PARAM = 1213;
    public static final int SG_NC_VERI_WUA_KEY_NOT_EXIST = 1217;
    public static final int SG_NC_VERI_WUA_LOW_VERSION_DATA_FILE = 1218;
    public static final int SG_NC_VERI_WUA_NO_DATA_FILE = 1215;
    public static final String errorCode = "errorCode";
    public static final String sessionId = "sessionId";
    public static final String sig = "sig";
    public static final String status = "status";
    public static final String token = "token";
    public static final String x1 = "x1";
    public static final String x2 = "x2";
    public static final String y1 = "y1";
    public static final String y2 = "y2";

    void initNoCaptcha(String str, String str2, int i, int i2, int i3, Handler handler, String str3) throws SecException;

    String noCaptchaForwardAuth(String str, HashMap<String, String> hashMap, String str2, int i) throws SecException;

    void noCaptchaVerification(String str) throws SecException;

    boolean putNoCaptchaTraceRecord(MotionEvent motionEvent) throws SecException;
}
