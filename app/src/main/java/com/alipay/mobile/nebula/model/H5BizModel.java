package com.alipay.mobile.nebula.model;

import android.os.Messenger;
import com.alibaba.fastjson.JSONObject;

public class H5BizModel {
    private Messenger messenger;
    private JSONObject result;
    private Runnable runnable;

    public H5BizModel() {
    }

    public H5BizModel(Messenger messenger2) {
        this.messenger = messenger2;
    }

    public Messenger getMessenger() {
        return this.messenger;
    }

    public void setMessenger(Messenger messenger2) {
        this.messenger = messenger2;
    }

    public JSONObject getResult() {
        return this.result;
    }

    public void setResult(JSONObject result2) {
        this.result = result2;
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

    public void setRunnable(Runnable runnable2) {
        this.runnable = runnable2;
    }
}
