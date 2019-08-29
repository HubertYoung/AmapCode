package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.TransferredListener;

public class BaseUpReq<T> extends AbstractHttpReq {
    public static final int DEFAULT_SLICE_LENGTH = 32768;
    protected int endPos = -1;
    protected String ext;
    protected String fileName;
    protected String gcid;
    protected T inputSource;
    protected String md5;
    protected Boolean setPublic;
    protected boolean skipRapid;
    protected int startPos;
    protected long totalLength;
    protected TransferredListener transferedListener;

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md52) {
        this.md5 = md52;
    }

    public T getInputSource() {
        return this.inputSource;
    }

    public void setInputSource(T inputSource2) {
        this.inputSource = inputSource2;
    }

    public TransferredListener getTransferedListener() {
        return this.transferedListener;
    }

    public void setTransferedListener(TransferredListener transferedListener2) {
        this.transferedListener = transferedListener2;
    }

    public String getGcid() {
        return this.gcid;
    }

    public void setGcid(String gcid2) {
        this.gcid = gcid2;
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext2) {
        this.ext = ext2;
    }

    public int getStartPos() {
        return this.startPos;
    }

    public void setStartPos(int startPos2) {
        this.startPos = startPos2;
    }

    public int getEndPos() {
        return this.endPos;
    }

    public void setEndPos(int endPos2) {
        this.endPos = endPos2;
    }

    public boolean isSkipRapid() {
        return this.skipRapid;
    }

    public void setSkipRapid(boolean skipRapid2) {
        this.skipRapid = skipRapid2;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName2) {
        this.fileName = fileName2;
    }

    public long getTotalLength() {
        return this.totalLength;
    }

    public void setTotalLength(long totalLength2) {
        this.totalLength = totalLength2;
    }

    public Boolean getPublic() {
        return this.setPublic;
    }

    public void setPublic(Boolean setPublic2) {
        this.setPublic = setPublic2;
    }

    public String toString() {
        return "BaseUpReq{md5='" + this.md5 + '\'' + ", gcid='" + this.gcid + '\'' + ", ext='" + this.ext + '\'' + ", inputSource=" + this.inputSource + ", fileName='" + this.fileName + '\'' + ", startPos=" + this.startPos + ", endPos=" + this.endPos + ", totalLength=" + this.totalLength + ", skipRapid=" + this.skipRapid + ", transferedListener=" + this.transferedListener + ", setPublic=" + this.setPublic + '}';
    }
}
