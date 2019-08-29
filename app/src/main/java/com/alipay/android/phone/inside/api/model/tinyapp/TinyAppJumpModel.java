package com.alipay.android.phone.inside.api.model.tinyapp;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.BaseOpenAuthModel;
import com.alipay.android.phone.inside.api.model.IBizOperation;
import com.alipay.android.phone.inside.api.result.tinyapp.TinyAppJumpCode;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class TinyAppJumpModel extends BaseOpenAuthModel<TinyAppJumpCode> {
    private boolean animated;
    private String appId;
    private JSONObject paramsJson = new JSONObject();
    private String paramsMap;

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public boolean isAnimated() {
        return this.animated;
    }

    public void setAnimated(boolean z) {
        this.animated = z;
    }

    public void putKV(String str, String str2) {
        try {
            this.paramsJson.put(str, str2);
            this.paramsMap = this.paramsJson.toString();
        } catch (Throwable th) {
            LoggerFactory.f().b((String) "inside", th);
        }
    }

    public String getParamsMap() {
        return this.paramsMap;
    }

    public IBizOperation<TinyAppJumpCode> getOperaion() {
        return new IBizOperation<TinyAppJumpCode>() {
            public TinyAppJumpCode parseResultCode(String str, String str2) {
                return TinyAppJumpCode.parse(str2);
            }

            public ActionEnum getAction() {
                return ActionEnum.TINY_APP_JUMP;
            }
        };
    }
}
