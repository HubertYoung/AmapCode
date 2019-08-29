package com.ali.user.mobile.account.bean;

import java.io.Serializable;

public class CellIdInfo implements Serializable {
    private static final long serialVersionUID = -3642647561168398482L;
    private int cid;
    private int lac;
    private int mcc;
    private int mnc;
    private String type;

    public int getMcc() {
        return this.mcc;
    }

    public void setMcc(int i) {
        this.mcc = i;
    }

    public int getMnc() {
        return this.mnc;
    }

    public void setMnc(int i) {
        this.mnc = i;
    }

    public int getLac() {
        return this.lac;
    }

    public void setLac(int i) {
        this.lac = i;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int i) {
        this.cid = i;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }
}
