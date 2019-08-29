package com.alipay.mobile.security.bio.api;

import com.alipay.mobile.security.bio.utils.StringUtil;
import java.io.Serializable;
import java.util.Map;

public class BioResponse implements Serializable {
    private static final long serialVersionUID = 110;
    private Map<String, String> ext;
    private boolean isSuccess = false;
    private int result = 0;
    private String resultMessage = "";
    public String subCode;
    public String subMsg;
    private String tag = "";
    private String token = "";

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    public void setResultMessage(String str) {
        this.resultMessage = str;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int i) {
        this.result = i;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean z) {
        this.isSuccess = z;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public Map<String, String> getExt() {
        return this.ext;
    }

    public void setExt(Map<String, String> map) {
        this.ext = map;
    }

    public String toString() {
        String collection2String;
        StringBuilder append = new StringBuilder("BioResponse{token='").append(this.token).append('\'').append(", resultMessage='").append(this.resultMessage).append('\'').append(", result=").append(this.result).append(", isSuccess=").append(this.isSuccess).append(", tag='").append(this.tag).append('\'').append(", ext=");
        if (this.ext == null) {
            collection2String = "null";
        } else {
            collection2String = StringUtil.collection2String(this.ext.keySet());
        }
        return append.append(collection2String).append('}').toString();
    }
}
