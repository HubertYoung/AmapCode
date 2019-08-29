package com.autonavi.common.tool.thirdparty.df;

public final class AmapUInfo {
    private long avail;
    private int code;
    private String device;
    private String mountPoint;
    private int opt;
    private int precentUsed;
    private long total;

    public AmapUInfo(int i, int i2, String str, String str2, long j, long j2, int i3) {
        this.code = i;
        this.opt = i2;
        this.device = str;
        this.mountPoint = str2;
        this.total = j;
        this.avail = j2;
        this.precentUsed = i3;
    }

    public final int getCode() {
        return this.code;
    }

    public final void setCode(int i) {
        this.code = i;
    }

    public final int getOpt() {
        return this.opt;
    }

    public final void setOpt(int i) {
        this.opt = i;
    }

    public final String getDevice() {
        return this.device;
    }

    public final void setDevice(String str) {
        this.device = str;
    }

    public final String getMountPoint() {
        return this.mountPoint;
    }

    public final void setMountPoint(String str) {
        this.mountPoint = str;
    }

    public final long getTotal() {
        return this.total;
    }

    public final void setTotal(long j) {
        this.total = j;
    }

    public final long getAvail() {
        return this.avail;
    }

    public final void setAvail(long j) {
        this.avail = j;
    }

    public final int getPrecentUsed() {
        return this.precentUsed;
    }

    public final void setPrecentUsed(int i) {
        this.precentUsed = i;
    }
}
