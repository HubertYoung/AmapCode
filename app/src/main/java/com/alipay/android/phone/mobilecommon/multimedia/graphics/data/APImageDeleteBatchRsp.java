package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APImageDeleteBatchRsp {
    private String fileIds;
    private APImageRetMsg retmsg;

    public APImageRetMsg getRetmsg() {
        return this.retmsg;
    }

    public void setRetmsg(APImageRetMsg retmsg2) {
        this.retmsg = retmsg2;
    }

    public String getFileIds() {
        return this.fileIds;
    }

    public void setFileIds(String fileIds2) {
        this.fileIds = fileIds2;
    }
}
