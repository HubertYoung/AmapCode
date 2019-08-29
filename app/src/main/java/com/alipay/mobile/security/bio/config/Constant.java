package com.alipay.mobile.security.bio.config;

public class Constant {
    public static final String BIOLOGY_CALLBACK_ACTION = "com.alipay.mobile.security.bio.action.callback";
    public static final String BIOLOGY_CALLBACK_PROGRESS_ACTION = "com.alipay.mobile.security.bio.action.callback.progress";
    public static final String BIOLOGY_DES_KEY_SEED = "BIOLOGY_DES_KEY_SEED";
    public static final String BIOLOGY_FLAG_AUTOCLOSE = "com.alipay.mobile.security.bio.autoclose";
    public static final String BIOLOGY_FLAG_SERVER_CONTINUE = "com.alipay.mobile.security.bio.server.continue";
    public static final String BIOLOGY_FLAG_SERVER_FAIL = "com.alipay.mobile.security.bio.server.fail";
    public static final String BIOLOGY_FLAG_SERVER_RETRY = "com.alipay.mobile.security.bio.server.retry";
    public static final String BIOLOGY_FLAG_SERVER_SUCCESS = "com.alipay.mobile.security.bio.server.success";
    public static final String BIOLOGY_INTENT_ACTION_INFO = "com.alipay.mobile.security.bio.action.intent.app";
    public static final String BIOLOGY_INTENT_ACTION_REV = "com.alipay.mobile.security.bio.action.intent.rev";
    public static final String BIOLOGY_SEND_AVATAR_ACTION = "com.alipay.mobile.security.bio.action.send.avatar";
    public static final boolean DEBUG = false;
    public static final String DEBUG_FILE_PATH = "/sdcard/BIOLOGY/bioLog.txt";
    public static final String DEBUG_LOG_TAG = "BIOLOGY";
    public static final String DETECT_FACE_PUB_KEY_NAME = "bid-log-key-public.key";
    @Deprecated
    public static final String DETECT_FACE_PUB_KEY_NAME_B = "bid-log-key-public.key";
    public static final String DETECT_FACE_PUB_KEY_NAME_T = "bid-log-key-public_t.key";
    public static final String DETECT_FACE_SETTING_NAME = "facesetting.json";
    public static final int DEVICE_ANGLE = 45;
    public static final boolean ENABLE_FACE_DETECT = true;
    @Deprecated
    public static final String FRAMEWORK_VERSION = "2.3.0";
    public static final boolean LOG = true;
}
