package com.autonavi.miniapp.biz.network;

public class ResultDTO<T> extends BaseDTO {
    private static final long serialVersionUID = 5087960551822842313L;
    public String code;
    public T data;
    public String message;
    public boolean result;
    public String timestamp;
    public String version;

    public boolean getResult() {
        return this.result;
    }

    public void setResult(boolean z) {
        this.result = z;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T t) {
        this.data = t;
    }
}
