package com.alipay.android.phone.mobilecommon.multimedia.audio.data;

public class APAudioRsp {
    public static final int CODE_ERROR = 1;
    public static final int CODE_ERROR_CURRENT_LIMIT = 2000;
    public static final int CODE_ERROR_ILLEGAL_STATE = 4;
    public static final int CODE_ERROR_PREPARED = 2;
    public static final int CODE_ERROR_START = 3;
    public static final int CODE_ERROR_TIMEOUT = 14;
    public static final int CODE_SUCCESS = 0;
    private APAudioInfo audioInfo;
    private String msg;
    private int retCode;

    public APAudioInfo getAudioInfo() {
        return this.audioInfo;
    }

    public void setAudioInfo(APAudioInfo audioInfo2) {
        this.audioInfo = audioInfo2;
    }

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg2) {
        this.msg = msg2;
    }

    public boolean isSuccess() {
        return this.retCode == 0;
    }

    public String toString() {
        return "APAudioRsp{audioInfo=" + this.audioInfo + ", retCode=" + this.retCode + ", msg='" + this.msg + '\'' + '}';
    }
}
