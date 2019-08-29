package com.alipay.streammedia.encode;

public class RecordVideoResult {
    private int code;
    private boolean crop;
    private int orgHeight;
    private int orgWidth;
    private int queueSize;
    private long usedTime;
    private boolean zoom;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public long getUsedTime() {
        return this.usedTime;
    }

    public void setUsedTime(long usedTime2) {
        this.usedTime = usedTime2;
    }

    public int getQueueSize() {
        return this.queueSize;
    }

    public void setQueueSize(int queueSize2) {
        this.queueSize = queueSize2;
    }

    public int getOrgWidth() {
        return this.orgWidth;
    }

    public void setOrgWidth(int orgWidth2) {
        this.orgWidth = orgWidth2;
    }

    public int getOrgHeight() {
        return this.orgHeight;
    }

    public void setOrgHeight(int orgHeight2) {
        this.orgHeight = orgHeight2;
    }

    public boolean isZoom() {
        return this.zoom;
    }

    public void setZoom(boolean zoom2) {
        this.zoom = zoom2;
    }

    public boolean isCrop() {
        return this.crop;
    }

    public void setCrop(boolean crop2) {
        this.crop = crop2;
    }

    public String toString() {
        return "RecordVideoResult{code=" + this.code + ", usedTime=" + this.usedTime + ", queueSize=" + this.queueSize + ", orgWidth=" + this.orgWidth + ", orgHeight=" + this.orgHeight + ", zoom=" + this.zoom + ", crop=" + this.crop + '}';
    }
}
