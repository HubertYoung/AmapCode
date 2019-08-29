package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;

@Keep
public class ATCityDataFile {
    private long currentSize;
    private long currentVersion;
    private long downloadingSize;
    private long downloadingVersion;
    private String fileType;
    private String md5;
    private String path;

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String str) {
        this.fileType = str;
    }

    public long getCurrentVersion() {
        return this.currentVersion;
    }

    public void setCurrentVersion(long j) {
        this.currentVersion = j;
    }

    public long getCurrentSize() {
        return this.currentSize;
    }

    public void setCurrentSize(long j) {
        this.currentSize = j;
    }

    public long getDownloadingVersion() {
        return this.downloadingVersion;
    }

    public void setDownloadingVersion(long j) {
        this.downloadingVersion = j;
    }

    public long getDownloadingSize() {
        return this.downloadingSize;
    }

    public void setDownloadingSize(long j) {
        this.downloadingSize = j;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getPath() {
        return this.path;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String str) {
        this.md5 = str;
    }
}
