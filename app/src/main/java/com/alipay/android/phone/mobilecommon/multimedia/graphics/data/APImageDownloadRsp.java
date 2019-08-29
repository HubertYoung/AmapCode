package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import com.alipay.android.phone.mobilecommon.multimedia.api.data.APMultimediaTaskModel;

public class APImageDownloadRsp {
    public static final int FROM_DISK_CACHE = 1;
    public static final int FROM_LOCAL = 2;
    public static final int FROM_MEM = 0;
    public static final int FROM_NET = 3;
    public static final int FROM_UNKNOWN = -1;
    private String cacheId;
    public byte[] imageData;
    public int loadFrom = -1;
    public APImageRetMsg originalRetMsg;
    private APImageRetMsg retmsg;
    private String sourcePath;
    private String storeFilePath;
    public APMultimediaTaskModel taskModel;

    public APImageRetMsg getRetmsg() {
        return this.retmsg;
    }

    public void setRetmsg(APImageRetMsg retmsg2) {
        this.retmsg = retmsg2;
    }

    public String getSourcePath() {
        return this.sourcePath;
    }

    public void setSourcePath(String sourcePath2) {
        this.sourcePath = sourcePath2;
    }

    public String getCacheId() {
        return this.cacheId;
    }

    public void setCacheId(String cacheId2) {
        this.cacheId = cacheId2;
    }

    public String getStoreFilePath() {
        return this.storeFilePath;
    }

    public void setStoreFilePath(String storeFilePath2) {
        this.storeFilePath = storeFilePath2;
    }

    public String toString() {
        return "APImageDownloadRsp{retmsg=" + this.retmsg + ", sourcePath='" + this.sourcePath + '\'' + ", cacheId='" + this.cacheId + '\'' + ", storeFilePath='" + this.storeFilePath + '\'' + ", originalRetMsg=" + this.originalRetMsg + ", imageData=" + this.imageData + ", loadFrom=" + this.loadFrom + ", taskModel=" + this.taskModel + '}';
    }
}
