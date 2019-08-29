package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

public class GetChunksMetaReq extends AbstractHttpReq {
    private String chunkIds;

    public GetChunksMetaReq(String chunkIds2) {
        this.chunkIds = chunkIds2;
    }

    public String getChunkIds() {
        return this.chunkIds;
    }

    public void setChunkIds(String chunkIds2) {
        this.chunkIds = chunkIds2;
    }
}
