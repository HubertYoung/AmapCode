package com.alipay.mobile.h5container.api;

import java.io.InputStream;

public class H5PreSetPkgInfo {
    String appId;
    String downloadUrl;
    boolean forceSync;
    InputStream inputStream;
    String version;

    public H5PreSetPkgInfo(String appId2, String version2, InputStream inputStream2, boolean forceSync2) {
        this.appId = appId2;
        this.version = version2;
        this.inputStream = inputStream2;
        this.forceSync = forceSync2;
    }

    public H5PreSetPkgInfo(String appId2, String version2, InputStream inputStream2, boolean forceSync2, String downLoadUrl) {
        this.appId = appId2;
        this.version = version2;
        this.inputStream = inputStream2;
        this.forceSync = forceSync2;
        this.downloadUrl = downLoadUrl;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl2) {
        this.downloadUrl = downloadUrl2;
    }

    public void setInputStream(InputStream inputStream2) {
        this.inputStream = inputStream2;
    }

    public void setAppId(String appId2) {
        this.appId = appId2;
    }

    public void setVersion(String version2) {
        this.version = version2;
    }

    public InputStream getInputStream() {
        return this.inputStream;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getVersion() {
        return this.version;
    }

    public boolean getForceSync() {
        return this.forceSync;
    }

    public void setForceSync(boolean forceSync2) {
        this.forceSync = forceSync2;
    }
}
