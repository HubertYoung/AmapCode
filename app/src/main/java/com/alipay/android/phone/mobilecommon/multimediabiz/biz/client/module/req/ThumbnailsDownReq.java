package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.req;

import android.text.TextUtils;

public class ThumbnailsDownReq extends AbstractHttpReq {
    private boolean bHttps = false;
    private String bizId = "";
    private String fileIds;
    private long range;
    private String source;
    private boolean webp = false;
    private String zoom;
    private String zoom2;

    public ThumbnailsDownReq(String fileIds2, String zoom3) {
        this.fileIds = fileIds2;
        this.zoom = zoom3;
        if (TextUtils.isEmpty(zoom3) || "original".equalsIgnoreCase(zoom3)) {
            this.zoom = "original";
        } else {
            this.webp = zoom3.contains(".webp");
        }
    }

    public boolean isWebp() {
        return this.webp;
    }

    public String getFileIds() {
        return this.fileIds;
    }

    public void setFileIds(String fileIds2) {
        this.fileIds = fileIds2;
    }

    public String getZoom() {
        return this.zoom;
    }

    public void setZoom(String zoom3) {
        this.zoom = zoom3;
    }

    public long getRange() {
        return this.range;
    }

    public void setRange(long range2) {
        this.range = range2;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source2) {
        this.source = source2;
    }

    public void setBizId(String bizId2) {
        this.bizId = bizId2;
    }

    public String getBizId() {
        return this.bizId;
    }

    public boolean isbHttps() {
        return this.bHttps;
    }

    public void setbHttps(boolean bHttps2) {
        this.bHttps = bHttps2;
    }

    public String getZoom2() {
        return this.zoom2;
    }

    public ThumbnailsDownReq setZoom2(String zoom22) {
        this.zoom2 = zoom22;
        return this;
    }

    public String toString() {
        return "ThumbnailsDownReq{fileIds='" + this.fileIds + '\'' + ", zoom='" + this.zoom + '\'' + ", zoom2='" + this.zoom2 + '\'' + ", source='" + this.source + '\'' + ", range=" + this.range + ", webp=" + this.webp + ", bizId='" + this.bizId + '\'' + ", bHttps=" + this.bHttps + '}';
    }
}
