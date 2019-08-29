package com.alipay.mobile.security.bio.config.bean;

public class FaceTips {
    private String a;
    private String b;
    private String c;
    private String d = "";
    private String e = "";
    private AlertConfig f = new AlertConfig();
    private AlertConfig g = new AlertConfig();
    private AlertConfig h = new AlertConfig();
    private AlertConfig i = new AlertConfig();
    private AlertConfig j = new AlertConfig();
    private AlertConfig k = new AlertConfig();
    private AlertConfig l = new AlertConfig();
    private AlertConfig m = new AlertConfig();
    private AlertConfig n = new AlertConfig();
    private AlertConfig o = new AlertConfig();

    public FaceTips() {
        this.f.setReturnCode(102);
        this.g.setReturnCode(105);
        this.h.setReturnCode(205);
        this.i.setReturnCode(100);
        this.j.setReturnCode(202);
        this.k.setReturnCode(203);
        this.l.setReturnCode(208);
        this.m.setReturnCode(209);
        this.n.setReturnCode(207);
        this.o.setReturnCode(202);
    }

    public void setNoFaceText(String str) {
        this.a = str;
    }

    public String getNoFaceText() {
        return this.a;
    }

    public void setNoBlinkText(String str) {
        this.b = str;
    }

    public String getNoBlinkText() {
        return this.b;
    }

    public void setAdjustPoseText(String str) {
        this.c = str;
    }

    public String getAdjustPoseText() {
        return this.c;
    }

    public AlertConfig getUnsurpportAlert() {
        return this.f;
    }

    public void setUnsurpportAlert(AlertConfig alertConfig) {
        this.f = alertConfig;
    }

    public AlertConfig getSystemVersionErrorAlert() {
        return this.g;
    }

    public void setSystemVersionErrorAlert(AlertConfig alertConfig) {
        this.g = alertConfig;
    }

    public AlertConfig getSystemErrorAlert() {
        return this.h;
    }

    public void setSystemErrorAlert(AlertConfig alertConfig) {
        this.h = alertConfig;
    }

    public AlertConfig getCameraNoPermissionAlert() {
        return this.i;
    }

    public void setCameraNoPermissionAlert(AlertConfig alertConfig) {
        this.i = alertConfig;
    }

    public AlertConfig getExitAlert() {
        return this.j;
    }

    public void setExitAlert(AlertConfig alertConfig) {
        this.j = alertConfig;
    }

    public AlertConfig getTimeoutAlert() {
        return this.k;
    }

    public void setTimeoutAlert(AlertConfig alertConfig) {
        this.k = alertConfig;
    }

    public AlertConfig getFailAlert() {
        return this.l;
    }

    public void setFailAlert(AlertConfig alertConfig) {
        this.l = alertConfig;
    }

    public AlertConfig getLimitAlert() {
        return this.m;
    }

    public void setLimitAlert(AlertConfig alertConfig) {
        this.m = alertConfig;
    }

    public AlertConfig getNetworkErrorAlert() {
        return this.n;
    }

    public void setNetworkErrorAlert(AlertConfig alertConfig) {
        this.n = alertConfig;
    }

    public AlertConfig getInterruptAlert() {
        return this.o;
    }

    public void setInterruptAlert(AlertConfig alertConfig) {
        this.o = alertConfig;
    }

    public String getBrandTip() {
        return this.d;
    }

    public void setBrandTip(String str) {
        this.d = str;
    }

    public String getStopScanTip() {
        return this.e;
    }

    public void setStopScanTip(String str) {
        this.e = str;
    }

    public String toString() {
        return "FaceTips{noFaceText='" + this.a + '\'' + ", noBlinkText='" + this.b + '\'' + ", adjustPoseText='" + this.c + '\'' + ", brandTip='" + this.d + '\'' + ", stopScanTip='" + this.e + '\'' + ", unsurpportAlert=" + this.f + ", systemVersionErrorAlert=" + this.g + ", systemErrorAlert=" + this.h + ", cameraNoPermissionAlert=" + this.i + ", exitAlert=" + this.j + ", timeoutAlert=" + this.k + ", failAlert=" + this.l + ", limitAlert=" + this.m + ", networkErrorAlert=" + this.n + ", interruptAlert=" + this.o + '}';
    }
}
