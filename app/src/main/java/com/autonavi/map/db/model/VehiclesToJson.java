package com.autonavi.map.db.model;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class VehiclesToJson {
    private int checkReminder = 0;
    private String engineNum = "";
    private String frameNum = "";
    private int limitReminder = 0;
    private String ocr_request_id;
    private String oftenUse = "0";
    private String plateNum = "";
    private String syncTime = "";
    private String telphone = "";
    private String validityPeriod = "";
    private String vehiclecode = "";
    private int violationReminder = 0;

    public String getPlateNum() {
        return this.plateNum;
    }

    public void setPlateNum(String str) {
        this.plateNum = str;
    }

    public String getFrameNum() {
        return this.frameNum;
    }

    public void setFrameNum(String str) {
        this.frameNum = str;
    }

    public String getEngineNum() {
        return this.engineNum;
    }

    public void setEngineNum(String str) {
        this.engineNum = str;
    }

    public String getVehiclecode() {
        return this.vehiclecode;
    }

    public void setVehiclecode(String str) {
        this.vehiclecode = str;
    }

    public String getValidityPeriod() {
        return this.validityPeriod;
    }

    public void setValidityPeriod(String str) {
        this.validityPeriod = str;
    }

    public String getTelphone() {
        return this.telphone;
    }

    public void setTelphone(String str) {
        this.telphone = str;
    }

    public String getOftenUse() {
        return this.oftenUse;
    }

    public void setOftenUse(String str) {
        this.oftenUse = str;
    }

    public int getCheckReminder() {
        return this.checkReminder;
    }

    public void setCheckReminder(int i) {
        this.checkReminder = i;
    }

    public int getViolationReminder() {
        return this.violationReminder;
    }

    public void setViolationReminder(int i) {
        this.violationReminder = i;
    }

    public int getLimitReminder() {
        return this.limitReminder;
    }

    public void setLimitReminder(int i) {
        this.limitReminder = i;
    }

    public String getOcr_request_id() {
        return this.ocr_request_id;
    }

    public void setOcr_request_id(String str) {
        this.ocr_request_id = str;
    }

    public String getSyncTime() {
        return this.syncTime;
    }

    public void setSyncTime(String str) {
        this.syncTime = str;
    }
}
