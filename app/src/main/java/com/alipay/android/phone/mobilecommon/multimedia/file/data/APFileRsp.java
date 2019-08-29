package com.alipay.android.phone.mobilecommon.multimedia.file.data;

import android.text.TextUtils;
import java.util.HashMap;

public class APFileRsp {
    public static final int CODE_ERR_AUTH_FAIL = -403;
    public static final int CODE_ERR_CURRENT_LIMITED = 2000;
    public static final int CODE_ERR_ENCRYPT_FAIL = 13;
    public static final int CODE_ERR_EXCEPTION = 1;
    public static final int CODE_ERR_FILE_ID_NOT_EXIST = 11;
    public static final int CODE_ERR_FILE_MD5_WRONG = 4;
    public static final int CODE_ERR_FILE_SIZE_WRONG = 6;
    public static final int CODE_ERR_FILE_SIZE_ZERO = 3;
    public static final int CODE_ERR_NETWORK_ERR = 10;
    public static final int CODE_ERR_NO_NETWORK = 9;
    public static final int CODE_ERR_PATH_EMPTY = 7;
    public static final int CODE_ERR_RSP_NULL = 2;
    public static final int CODE_ERR_SPACE_NOT_ENOUGH = 12;
    public static final int CODE_ERR_TASK_CANCELED = 5;
    public static final int CODE_ERR_TIMEOUT = 14;
    public static final int CODE_ERR_VIEW_REUSED = 8;
    public static final int CODE_SUCCESS = 0;
    private HashMap<String, String> extra;
    private APFileReq fileReq;
    private String msg;
    private int retCode;
    private String traceId;

    public int getRetCode() {
        return this.retCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }

    public void setMsg(String msg2) {
        this.msg = msg2;
    }

    public APFileReq getFileReq() {
        return this.fileReq;
    }

    public void setFileReq(APFileReq fileReq2) {
        this.fileReq = fileReq2;
    }

    public String toString() {
        return "APFileRsp {fileReq='" + this.fileReq + '\'' + ", retCode=" + this.retCode + ", msg=" + this.msg + '}';
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String traceId2) {
        this.traceId = traceId2;
    }

    public void addExtra(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            if (this.extra == null) {
                this.extra = new HashMap<>();
            }
            this.extra.put(key, value);
        }
    }

    public String getExtra(String key) {
        if (this.extra == null || TextUtils.isEmpty(key)) {
            return null;
        }
        return this.extra.get(key);
    }
}
