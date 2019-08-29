package com.alipay.android.phone.inside.api.model;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.result.ResultCode;
import com.alipay.android.phone.inside.api.utils.IBundleModel;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import org.json.JSONObject;

public abstract class BaseIotModel<T extends ResultCode> extends BaseModel<T> {
    protected String apdidToken;
    protected String deviceId;

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getApdidToken() {
        return this.apdidToken;
    }

    public void setApdidToken(String str) {
        this.apdidToken = str;
    }

    public static String modelToJsonString(IBundleModel iBundleModel) {
        if (iBundleModel != null) {
            try {
                Bundle bundle = iBundleModel.toBundle();
                if (bundle != null && bundle.size() > 0) {
                    JSONObject jSONObject = new JSONObject();
                    for (String str : bundle.keySet()) {
                        jSONObject.put(str, bundle.get(str));
                    }
                    if (jSONObject.length() > 0) {
                        TraceLogger f = LoggerFactory.f();
                        StringBuilder sb = new StringBuilder("modelJson=");
                        sb.append(jSONObject.toString());
                        f.b((String) "BaseIotModel", sb.toString());
                        return jSONObject.toString();
                    }
                }
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "BaseIotModel|jsonModel", th);
            }
        }
        return "";
    }
}
