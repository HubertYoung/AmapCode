package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;
import com.autonavi.common.annotation.Ignore;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Keep
public class UploadProgressInfo implements ResponseValue, Serializable {
    private static final long serialVersionUID = 8068412444345728610L;
    public double alreadySendSize;
    public int alreadySentCityNum;
    public int code;
    @Ignore
    public List<String> failCityList = new ArrayList();
    @Ignore
    public int faileCityNum;
    public String failedCityList;
    public int progress;
    public int sendState;
    @Ignore
    public int singleCitysendStatus;
    @Ignore
    public List<String> succCityList = new ArrayList();
    public String successCityList;
    public int totalCityNum;
    public double totalSize;

    public int getSendState() {
        return this.sendState;
    }

    public void setSendState(int i) {
        this.sendState = i;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public int getProgress(double d) {
        return (int) (((this.alreadySendSize + d) / this.totalSize) * 100.0d);
    }

    public void setProgress(int i) {
        this.progress = i;
    }

    public int getAlreadySentCityNum() {
        return this.alreadySentCityNum;
    }

    public void setAlreadySentCityNum(int i) {
        this.alreadySentCityNum = i;
    }

    public int getTotalCityNum() {
        return this.totalCityNum;
    }

    public void setTotalCityNum(int i) {
        this.totalCityNum = i;
    }

    @Ignore
    public String getFailedCityList() {
        return this.failedCityList;
    }

    public void setFailedCityList(String str) {
        this.failedCityList = str;
    }

    public double getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(double d) {
        this.totalSize = d;
    }

    public double getAlreadySendSize() {
        return this.alreadySendSize;
    }

    public void setAlreadySendSize(double d) {
        this.alreadySendSize = d;
    }

    @Ignore
    public int getSingleCitysendStatus() {
        return this.singleCitysendStatus;
    }

    public void setSingleCitysendStatus(int i) {
        this.singleCitysendStatus = i;
    }

    @Ignore
    public int getFaileCityNum() {
        return this.faileCityNum;
    }

    public void setFaileCityNum(int i) {
        this.faileCityNum = i;
    }

    public void setFailedCityNumIncrease() {
        this.faileCityNum++;
    }

    @Ignore
    public List<String> getFailCityList() {
        return this.failCityList;
    }

    public void setFailCityList(List<String> list) {
        this.failCityList = list;
    }

    public void addFailCityList(String str) {
        this.failCityList.add(str);
    }

    public void addSuccCityList(String str) {
        this.succCityList.add(str);
    }

    @Ignore
    public List<String> getSuccCityList() {
        return this.succCityList;
    }

    @Ignore
    public String getSuccessCityList() {
        return this.successCityList;
    }

    public void setSuccessCityList(String str) {
        this.successCityList = str;
    }
}
