package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config;

public class ServerAddress {
    public static final String SERVER_ADDR_FROMAT = "%1$s:%2$d";
    protected String apiServerHost;
    protected int apiServerPort = 80;
    protected String dlServerHost;
    protected int dlServerPort = 80;
    protected String upServerHost;
    protected int upServerPort = 80;

    public enum ServerType {
        UPLOAD,
        DOWNLOAD,
        API
    }

    public String getUploadServerHost() {
        return this.upServerHost;
    }

    public int getUploadServerPort() {
        return this.upServerPort;
    }

    public String getUploadServerAddr() {
        return String.format(SERVER_ADDR_FROMAT, new Object[]{getUploadServerHost(), Integer.valueOf(getUploadServerPort())});
    }

    public String getDownloadServerHost() {
        return this.dlServerHost;
    }

    public int getDownloandServerPort() {
        return this.dlServerPort;
    }

    public String getDownloadServerAddr() {
        return String.format(SERVER_ADDR_FROMAT, new Object[]{getDownloadServerHost(), Integer.valueOf(getDownloandServerPort())});
    }

    public String getApiServerHost() {
        return this.apiServerHost;
    }

    public int getApiServerPort() {
        return this.apiServerPort;
    }

    public String getApiServerAddr() {
        return String.format(SERVER_ADDR_FROMAT, new Object[]{getApiServerHost(), Integer.valueOf(getApiServerPort())});
    }
}
