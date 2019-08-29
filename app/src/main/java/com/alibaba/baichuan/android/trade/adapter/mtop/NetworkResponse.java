package com.alibaba.baichuan.android.trade.adapter.mtop;

import java.io.Serializable;
import java.util.Map;

public class NetworkResponse implements Serializable {
    public byte[] byteData;
    public Map data;
    public String errorCode;
    public String errorMsg;
    public String httpCode;
    public boolean isSuccess;
    public String jsonData;
    public Object object;

    public boolean isSuccess() {
        return this.isSuccess;
    }
}
