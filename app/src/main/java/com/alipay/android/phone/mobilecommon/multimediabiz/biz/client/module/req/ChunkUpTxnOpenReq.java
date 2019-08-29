package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

public class ChunkUpTxnOpenReq extends AbstractHttpReq {
    private long chunkSize = 4194304;
    private String extension;
    private String md5;
    private int number;
    private Boolean setPublic;
    private long size;

    public ChunkUpTxnOpenReq(long size2) {
        this.size = size2;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md52) {
        this.md5 = md52;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size2) {
        this.size = size2;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number2) {
        this.number = number2;
    }

    public long getChunkSize() {
        return this.chunkSize;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension2) {
        this.extension = extension2;
    }

    public Boolean getPublic() {
        return this.setPublic;
    }

    public void setPublic(Boolean setPublic2) {
        this.setPublic = setPublic2;
    }
}
