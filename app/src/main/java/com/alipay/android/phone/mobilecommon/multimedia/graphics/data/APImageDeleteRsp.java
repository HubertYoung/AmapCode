package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APImageDeleteRsp {
    private String fileId;
    private APImageRetMsg retmsg;

    public APImageRetMsg getRetmsg() {
        return this.retmsg;
    }

    public void setRetmsg(APImageRetMsg retmsg2) {
        this.retmsg = retmsg2;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(String fileId2) {
        this.fileId = fileId2;
    }
}
