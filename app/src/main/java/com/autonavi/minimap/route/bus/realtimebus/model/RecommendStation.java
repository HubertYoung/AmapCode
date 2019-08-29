package com.autonavi.minimap.route.bus.realtimebus.model;

import java.io.Serializable;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class RecommendStation implements Serializable {
    private String code;
    private RecommendStationData data;
    private String message;
    private String result;
    private String timestamp;
    private String version;

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public RecommendStationData getData() {
        return this.data;
    }

    public void setData(RecommendStationData recommendStationData) {
        this.data = recommendStationData;
    }
}
