package com.alipay.mobile.tinyappcommon.utils.ipc;

import android.os.Messenger;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcommon.api.TinyAppLiteProcessService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.utils.Callback;

public abstract class TinyAppIpcTask {
    private static final String TAG = "TinyAppIpcTask";
    private boolean async;
    private boolean callFromLiteProcess;
    private Callback<JSONObject> callback;
    private String ipcId;
    private JSONObject param;
    boolean replayedAsyncResultOnMain;
    private Messenger replyMessenger;
    private JSONObject result;

    public abstract JSONObject run(JSONObject jSONObject);

    public String getIpcId() {
        return this.ipcId;
    }

    public TinyAppIpcTask setIpcId(String ipcId2) {
        this.ipcId = ipcId2;
        return this;
    }

    public JSONObject getParam() {
        return this.param;
    }

    public JSONObject getResult() {
        return this.result;
    }

    public TinyAppIpcTask setParam(JSONObject param2) {
        this.param = param2;
        return this;
    }

    public TinyAppIpcTask setResult(JSONObject result2) {
        this.result = result2;
        return this;
    }

    public Callback<JSONObject> getCallback() {
        return this.callback;
    }

    public TinyAppIpcTask setCallback(Callback<JSONObject> callback2) {
        this.callback = callback2;
        return this;
    }

    public boolean isAsync() {
        return this.async;
    }

    public TinyAppIpcTask setAsync(boolean async2) {
        this.async = async2;
        return this;
    }

    public boolean isCallFromLiteProcess() {
        return this.callFromLiteProcess;
    }

    public TinyAppIpcTask setCallFromLiteProcess(boolean callFromLiteProcess2) {
        this.callFromLiteProcess = callFromLiteProcess2;
        return this;
    }

    public Messenger getReplyMessenger() {
        return this.replyMessenger;
    }

    public TinyAppIpcTask setReplyMessenger(Messenger messenger) {
        this.replyMessenger = messenger;
        return this;
    }

    public void replyResult(JSONObject result2) {
        setResult(result2);
        replyResult();
    }

    public void replyResult() {
        if (this.async) {
            if (this.callFromLiteProcess && this.replyMessenger != null) {
                TinyAppLiteProcessService service = TinyAppService.get().getLiteProcessService();
                if (service != null && !service.isLiteProcess()) {
                    service.replyDataToLiteProcess(this.replyMessenger, 8, TinyAppIpcUtils.taskToBundleForReply(this));
                }
            } else if (!this.replayedAsyncResultOnMain) {
                this.replayedAsyncResultOnMain = true;
                try {
                    if (this.callback != null) {
                        this.callback.callback(this.result);
                    }
                } catch (Throwable e) {
                    H5Log.e((String) TAG, e);
                }
            }
        }
    }
}
