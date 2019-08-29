package com.alipay.mobile.nebula.appcenter.apphandler;

public class H5PreferAppInfo {
    private String nbsn;
    private String nbsv;
    private long saveTime;

    public H5PreferAppInfo() {
    }

    public H5PreferAppInfo(String version, long time, String nbsn2) {
        this.nbsv = version;
        this.saveTime = time;
        this.nbsn = nbsn2;
    }

    public String getNbsv() {
        return this.nbsv;
    }

    public void setNbsv(String nbsv2) {
        this.nbsv = nbsv2;
    }

    public long getSaveTime() {
        return this.saveTime;
    }

    public void setSaveTime(long saveTime2) {
        this.saveTime = saveTime2;
    }

    public String getNbsn() {
        return this.nbsn;
    }

    public void setNbsn(String nbsn2) {
        this.nbsn = nbsn2;
    }

    public String toString() {
        return "H5PreferAppInfo{nbsv='" + this.nbsv + '\'' + ", saveTime=" + this.saveTime + ", nbsn='" + this.nbsn + '\'' + '}';
    }
}
