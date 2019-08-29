package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

public class FilesDownReq extends AbstractHttpReq {
    private boolean bHttps = false;
    private String fileIds;
    private boolean isForceUrl = false;
    private String range;
    private String source;
    private String urlParameter = "default";

    public FilesDownReq(String fileIds2) {
        this.fileIds = fileIds2;
    }

    public String getRange() {
        return this.range;
    }

    public void setRange(String range2) {
        this.range = range2;
    }

    public String getFileIds() {
        return this.fileIds;
    }

    public void setFileIds(String fileIds2) {
        this.fileIds = fileIds2;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source2) {
        this.source = source2;
    }

    public void setUrlParameter(String urlParameter2) {
        this.urlParameter = urlParameter2;
    }

    public String getUrlParameter() {
        return this.urlParameter;
    }

    public boolean isbHttps() {
        return this.bHttps;
    }

    public void setbHttps(boolean bHttps2) {
        this.bHttps = bHttps2;
    }

    public boolean getForceUrl() {
        return this.isForceUrl;
    }

    public void setForceUrl(boolean forceUrl) {
        this.isForceUrl = forceUrl;
    }
}
