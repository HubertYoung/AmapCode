package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;

@Keep
public class ATApkPackageResponse {
    private String appver;
    private String build;
    private int code;
    private String dic;
    private String dip;
    private String diu;
    private String div;
    private String msg;

    public String getDiv() {
        return this.div;
    }

    public void setDiv(String str) {
        this.div = str;
    }

    public String getAppver() {
        return this.appver;
    }

    public void setAppver(String str) {
        this.appver = str;
    }

    public String getBuild() {
        return this.build;
    }

    public void setBuild(String str) {
        this.build = str;
    }

    public String getDip() {
        return this.dip;
    }

    public void setDip(String str) {
        this.dip = str;
    }

    public String getDic() {
        return this.dic;
    }

    public void setDic(String str) {
        this.dic = str;
    }

    public String getDiu() {
        return this.diu;
    }

    public void setDiu(String str) {
        this.diu = str;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }
}
