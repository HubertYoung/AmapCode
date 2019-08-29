package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;
import java.util.List;

@Keep
public class ATApkPackage {
    private int code;
    private List<ATApkMemo> memo;
    private String message;
    private boolean result;
    private String timestamp;
    private String version;

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public boolean isResult() {
        return this.result;
    }

    public void setResult(boolean z) {
        this.result = z;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }

    public List<ATApkMemo> getMemo() {
        return this.memo;
    }

    public void setMemo(List<ATApkMemo> list) {
        this.memo = list;
    }
}
