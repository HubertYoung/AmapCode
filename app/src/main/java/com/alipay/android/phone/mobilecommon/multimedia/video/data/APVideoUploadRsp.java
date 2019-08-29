package com.alipay.android.phone.mobilecommon.multimedia.video.data;

import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileUploadRsp;

public class APVideoUploadRsp {
    public String mId;
    private APFileUploadRsp mRsp;
    public String mThumbId;
    public String mVideoId;

    public void setRsp(APFileUploadRsp rsp) {
        this.mRsp = rsp;
    }

    public int getRetCode() {
        return this.mRsp.getRetCode();
    }

    public String getMsg() {
        return this.mRsp.getMsg();
    }

    public APFileReq getFileReq() {
        return this.mRsp.getFileReq();
    }

    public APFileUploadRsp getRsp() {
        return this.mRsp;
    }

    public String toString() {
        return "APVideoUploadRsp {fileReq='" + this.mRsp.getFileReq() + '\'' + ", retCode=" + this.mRsp.getRetCode() + ", msg=" + this.mRsp.getMsg() + '}';
    }
}
