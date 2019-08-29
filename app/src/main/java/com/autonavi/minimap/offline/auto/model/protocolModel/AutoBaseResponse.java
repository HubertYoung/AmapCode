package com.autonavi.minimap.offline.auto.model.protocolModel;

import android.support.annotation.Keep;
import com.autonavi.minimap.offline.dataaccess.UseCase.ResponseValue;

@Keep
public class AutoBaseResponse implements ResponseValue {
    public int allowToDownloadState;
    public int code;
    public String msg;

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

    public int getAllowToDownloadState() {
        return this.allowToDownloadState;
    }

    public void setAllowToDownloadState(int i) {
        this.allowToDownloadState = i;
    }
}
