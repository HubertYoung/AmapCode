package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

public class GetFilesMetaReq extends AbstractHttpReq {
    private String fileIds;

    public GetFilesMetaReq(String fileIds2) {
        this.fileIds = fileIds2;
    }

    public String getFileIds() {
        return this.fileIds;
    }

    public void setFileIds(String fileIds2) {
        this.fileIds = fileIds2;
    }
}
