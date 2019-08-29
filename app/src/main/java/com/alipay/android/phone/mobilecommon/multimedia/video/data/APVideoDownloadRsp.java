package com.alipay.android.phone.mobilecommon.multimedia.video.data;

import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileDownloadRsp;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileReq;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageDownloadRsp;

public class APVideoDownloadRsp extends APFileDownloadRsp {
    private String fullVideoId;

    public String getFullVideoId() {
        return this.fullVideoId;
    }

    public void setFullVideoId(String fullVideoId2) {
        this.fullVideoId = fullVideoId2;
    }

    public void from(APFileDownloadRsp rsp) {
        if (rsp == null) {
            setRetCode(2);
            return;
        }
        setRetCode(rsp.getRetCode());
        setMsg(rsp.getMsg());
        setTraceId(rsp.getTraceId());
        setFileReq(rsp.getFileReq());
    }

    public void from(APImageDownloadRsp rsp) {
        if (rsp == null) {
            setRetCode(2);
            return;
        }
        setRetCode(rsp.getRetmsg().getCode().ordinal());
        setMsg(rsp.getRetmsg().getMsg());
        APFileReq req = new APFileReq();
        req.setCloudId(rsp.getSourcePath());
        setFileReq(req);
    }

    public String toString() {
        return "APVideoDownloadRsp{fullVideoId='" + this.fullVideoId + '\'' + super.toString() + '}';
    }
}
