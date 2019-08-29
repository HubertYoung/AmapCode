package mtopsdk.mtop.util;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 8852253200756618077L;
    protected String errCode;
    protected String errInfo;
    protected String errType;
    protected T model;
    protected int statusCode;
    protected boolean success;

    public Result(T t) {
        this.success = true;
        this.model = t;
    }

    public Result() {
        this.success = true;
    }

    public Result(boolean z, String str, String str2) {
        this(z, null, str, str2);
    }

    public Result(boolean z, String str, String str2, String str3) {
        this.success = true;
        this.success = z;
        this.errType = str;
        this.errCode = str2;
        this.errInfo = str3;
    }

    public T getModel() {
        return this.model;
    }

    public void setModel(T t) {
        this.model = t;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String str) {
        this.errCode = str;
    }

    public String getErrInfo() {
        return this.errInfo;
    }

    public void setErrInfo(String str) {
        this.errInfo = str;
    }

    public String getErrType() {
        return this.errType;
    }

    public void setErrType(String str) {
        this.errType = str;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean z) {
        this.success = z;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int i) {
        this.statusCode = i;
    }
}
