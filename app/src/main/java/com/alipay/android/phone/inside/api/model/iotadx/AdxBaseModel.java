package com.alipay.android.phone.inside.api.model.iotadx;

import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.result.iotadx.IotAdxResultCode;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AdxBaseModel extends BaseModel<IotAdxResultCode> {
    private JSONObject mParams;

    public void addParams(String str, String str2) {
        if (this.mParams == null) {
            this.mParams = new JSONObject();
        }
        try {
            this.mParams.put(str, str2);
        } catch (JSONException unused) {
        }
    }

    public void putParams(JSONObject jSONObject) {
        this.mParams = jSONObject;
    }
}
