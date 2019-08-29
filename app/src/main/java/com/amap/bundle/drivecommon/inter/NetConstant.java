package com.amap.bundle.drivecommon.inter;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class NetConstant {
    public static final int CODE_ACTIVITY_END = 103;
    public static final int CODE_FAILURE = 2;
    public static final int CODE_NOT_LOGIN = 14;
    public static final int CODE_PARAMETERS_ERROR = 3;
    public static final int CODE_POIID_REPEAT = 101;
    public static final int CODE_SIGNATURE_ERROR = 4;
    public static final int CODE_SUCCESS = 1;
    public static final int CODE_TID_BLACK_LIST = 102;
    public static final int CODE_TID_ERROR = 100;
    public static final int CODE_UNKNOWN_ERROR = 0;
    public static final int CODE_UPLOAD_PIC_FAIL = 104;
    public static final String KEY_ACTIVITY_END = "is_end";
    public static final String KEY_CODE = "code";
    public static final String KEY_DONE_DAYS = "done_days";
    public static final String KEY_MONEY_ACCOUNT = "account";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_TOTAL = "total";
    public static final String KEY_USER_APPLIED_NAVI_LIST = "lists";
}
