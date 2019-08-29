package com.alipay.android.phone.inside.api.result;

import com.alipay.android.phone.inside.api.result.ResultCode;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class OperationResult<T extends ResultCode> {
    private T code = null;
    private Map<String, String> mExtParams;
    private String op = "";
    private String result = "";

    public T getCode() {
        return this.code;
    }

    public String getExtParams(String str) {
        if (this.mExtParams == null) {
            return null;
        }
        return this.mExtParams.get(str);
    }

    public void addExtParams(String str, String str2) {
        if (this.mExtParams == null) {
            this.mExtParams = new HashMap();
        }
        this.mExtParams.put(str, str2);
    }

    public String getCodeValue() {
        return this.code == null ? "" : this.code.getValue();
    }

    public String getCodeMemo() {
        return this.code == null ? "" : this.code.getMemo();
    }

    public OperationResult(T t, String str) {
        this.code = t;
        this.op = str;
    }

    public OperationResult(T t, String str, String str2) {
        this.code = t;
        this.op = str;
        this.result = str2;
    }

    public void setCode(T t) {
        this.code = t;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public String getResult() {
        return this.result;
    }

    public String getOp() {
        return this.op;
    }

    public void setOp(String str) {
        this.op = str;
    }

    public String toJsonString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("result", getResult());
            jSONObject.put("code", getCodeValue());
            jSONObject.put("memo", getCodeMemo());
            jSONObject.put("op", getOp());
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject.toString();
    }
}
