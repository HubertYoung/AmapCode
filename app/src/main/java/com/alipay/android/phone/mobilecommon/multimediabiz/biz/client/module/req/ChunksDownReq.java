package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

public class ChunksDownReq extends AbstractHttpReq {
    private String chunkIds;

    public ChunksDownReq(String chunkIds2) {
        this.chunkIds = chunkIds2;
    }

    public String getChunkIds() {
        return this.chunkIds;
    }

    public void setChunkIds(String chunkIds2) {
        this.chunkIds = chunkIds2;
    }
}
