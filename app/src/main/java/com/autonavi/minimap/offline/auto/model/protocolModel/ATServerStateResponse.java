package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;
import java.io.Serializable;

@Keep
public class ATServerStateResponse implements ResponseValue, Serializable {
    private static final long serialVersionUID = -429259803322443507L;
    private long availableSpace;
    private String code;
    private String msg;
    private long totalSpace;

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public long getAvailableSpace() {
        return this.availableSpace;
    }

    public void setAvailableSpace(long j) {
        this.availableSpace = j;
    }

    public long getTotalSpace() {
        return this.totalSpace;
    }

    public void setTotalSpace(long j) {
        this.totalSpace = j;
    }
}
