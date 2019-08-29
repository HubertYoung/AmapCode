package com.alipay.android.phone.mobilecommon.multimedia.audio.data;

public class APAudioUploadRsp extends APAudioRsp {
    public static final int CODE_FILE_UPLOAD_ERROR = 101;
    public static final int CODE_SYNC_UPLOAD_ERROR = 100;
    public static final int CODE_UPLOAD_TIMEOUT = 102;
    public static final int STATE_RECORD_CANCEL = 2;
    public static final int STATE_RECORD_ERROR = 1;
    public static final int STATE_RECORD_FINISHED = 0;
    public static final int STATE_RECORD_RECORDING = 3;
    public static final int STATE_RECORD_UNKNOWN = -1;
    public int extCode;
    public String extMsg;
    public int recordState = -1;

    public String toString() {
        return "APAudioUploadRsp{extCode=" + this.extCode + ", extMsg='" + this.extMsg + '\'' + ", recordState='" + this.recordState + '\'' + ", super=" + super.toString() + '}';
    }
}
