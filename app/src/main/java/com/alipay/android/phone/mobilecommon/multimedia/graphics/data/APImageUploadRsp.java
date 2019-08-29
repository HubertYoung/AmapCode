package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;

public class APImageUploadRsp {
    private long createTime;
    private long fileSize;
    private String filename;
    private int filetype;
    private String md5;
    private String orgFilePath;
    private String orgMd5;
    private String publicUrl;
    private APImageRetMsg retmsg;
    private APMultimediaTaskModel taskStatus;

    public APImageRetMsg getRetmsg() {
        return this.retmsg;
    }

    public void setRetmsg(APImageRetMsg retmsg2) {
        this.retmsg = retmsg2;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime2) {
        this.createTime = createTime2;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md52) {
        this.md5 = md52;
    }

    public int getFiletype() {
        return this.filetype;
    }

    public void setFiletype(int filetype2) {
        this.filetype = filetype2;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename2) {
        this.filename = filename2;
    }

    public String getOrgFilePath() {
        return this.orgFilePath;
    }

    public void setOrgFilePath(String orgFilePath2) {
        this.orgFilePath = orgFilePath2;
    }

    public String getOrgMd5() {
        return this.orgMd5;
    }

    public void setOrgMd5(String orgMd52) {
        this.orgMd5 = orgMd52;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(long size) {
        this.fileSize = size;
    }

    public APMultimediaTaskModel getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(APMultimediaTaskModel taskStatus2) {
        this.taskStatus = taskStatus2;
    }

    public String getPublicUrl() {
        return this.publicUrl;
    }

    public void setPublicUrl(String publicUrl2) {
        this.publicUrl = publicUrl2;
    }
}
