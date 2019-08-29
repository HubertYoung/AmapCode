package com.autonavi.miniapp.monitor.api.analysis.diagnose;

public interface UploadTaskStatus {
    public static final String KEY_ACCOUNT_NAME = "accountName";
    public static final String KEY_BIZ_TYPE = "bizType";
    public static final String KEY_FROM_TIME = "fromTime";
    public static final String KEY_NETWORK_CONDITION = "networkCondition";
    public static final String KEY_TASK_TYPE = "taskType";
    public static final String KEY_TO_TIME = "toTime";
    public static final String KEY_ZIPPED_LEN_LIMIT = "zippedLenLimit";
    public static final String NETWORK_ANY = "any";
    public static final String NETWORK_MOBILE = "mobile";
    public static final String NETWORK_WIFI = "wifi";
    public static final String TYPE_ANRLOG = "anrLog";
    public static final String TYPE_ANRTRACE = "anrtrace";
    public static final String TYPE_APPLOG = "applog";
    public static final String TYPE_LOGCAT = "logcat";
    public static final String TYPE_RETRIEVE_FILE = "retrieveFile";
    public static final String TYPE_STACKTRACER_PUSH = "stacktracerPush";
    public static final String TYPE_STACKTRACER_WALLET = "stacktracerWallet";
    public static final String TYPE_STORAGETRACE = "storagetrace";
    public static final String TYPE_TRACEVIEW_PUSH = "traceviewPush";
    public static final String TYPE_TRACEVIEW_Wallet = "traceviewWallet";
    public static final String TYPE_TRAFFICLOG = "trafficLog";

    public enum Code {
        NONE,
        ZIPPING_ERROR,
        TRAFIC_LIMIT,
        NO_SPACE,
        NO_TARGET_FILE,
        NET_NOT_MATCH,
        NO_SDCARD,
        PARAM_INVALID,
        NETWORK_ERROR,
        UNKNOWN_ERROR,
        RESULT_SUCCESS,
        RESULT_FAILURE,
        TASK_BY_PUSH,
        TASK_BY_CONFIG,
        TASK_BY_POSITIVE,
        TASK_BY_MANUAL,
        FILE_UPLOADING,
        FILE_UPLOADING_RETRY,
        FILE_ZIPPING
    }

    void onFail(Code code, String str);

    void onSuccess(String str);
}
