package com.alipay.android.phone.mobilecommon.multimedia.video.data;

public class APVideoCutRsp {
    public String destFilePath;
    public long end;
    public int errCode;
    public String id;
    public int progress;
    public int rotation;
    public String sourcePath;
    public long start;
    public int targetHeight;
    public int targetWidht;

    public String toString() {
        return "APVideoCutRsp{sourcePath='" + this.sourcePath + '\'' + ", targetWidht=" + this.targetWidht + ", targetHeight=" + this.targetHeight + ", start=" + this.start + ", end=" + this.end + ", errCode=" + this.errCode + ", destFilePath='" + this.destFilePath + '\'' + ", id='" + this.id + '\'' + ", rotation='" + this.rotation + '\'' + ", progress='" + this.progress + '\'' + '}';
    }
}
