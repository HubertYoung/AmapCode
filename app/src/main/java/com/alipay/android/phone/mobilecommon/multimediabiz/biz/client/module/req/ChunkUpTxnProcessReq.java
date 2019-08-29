package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.ChunkTransferredListener;
import java.io.File;

public class ChunkUpTxnProcessReq extends AbstractHttpReq {
    private long chunkNumber;
    private long chunkSize;
    private ChunkTransferredListener chunkTransListener;
    private byte[] data;
    private File file;
    private String fileId;
    private String gcid;
    private String md5;
    private long realChunkSize;
    private int sequence;

    public ChunkUpTxnProcessReq(String fileId2, File file2, int sequence2) {
        this.chunkSize = 4194304;
        this.fileId = fileId2;
        this.file = file2;
        this.sequence = sequence2;
    }

    public ChunkUpTxnProcessReq(String fileId2, byte[] data2, int sequence2) {
        this.chunkSize = 4194304;
        this.fileId = fileId2;
        this.data = data2;
        this.sequence = sequence2;
    }

    public ChunkUpTxnProcessReq(String fileId2, File file2, int sequence2, ChunkTransferredListener chunkTransListener2) {
        this(fileId2, file2, sequence2);
        this.chunkTransListener = chunkTransListener2;
    }

    public long getChunkNumber() {
        return this.chunkNumber;
    }

    public void setChunkNumber(long chunkNumber2) {
        this.chunkNumber = chunkNumber2;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(String fileId2) {
        this.fileId = fileId2;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md52) {
        this.md5 = md52;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setSequence(int sequence2) {
        this.sequence = sequence2;
    }

    public long getChunkSize() {
        return this.chunkSize;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file2) {
        this.file = file2;
    }

    public void setChunkSize(long chunkSize2) {
        this.chunkSize = chunkSize2;
    }

    public long getRealChunkSize() {
        return this.realChunkSize;
    }

    public void setRealChunkSize(long realChunkSize2) {
        this.realChunkSize = realChunkSize2;
    }

    public ChunkTransferredListener getChunkTransListener() {
        return this.chunkTransListener;
    }

    public void setChunkTransListener(ChunkTransferredListener chunkTransListener2) {
        this.chunkTransListener = chunkTransListener2;
    }

    public String getGcid() {
        return this.gcid;
    }

    public void setGcid(String gcid2) {
        this.gcid = gcid2;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data2) {
        this.data = data2;
    }

    public String toString() {
        return "ChunkUpTxnProcessReq{fileId='" + this.fileId + '\'' + ", md5='" + this.md5 + '\'' + ", chunkNumber=" + this.chunkNumber + ", sequence=" + this.sequence + ", chunkSize=" + this.chunkSize + ", file=" + this.file + ", data=" + this.data + ", gcid=" + this.gcid + '}';
    }
}
