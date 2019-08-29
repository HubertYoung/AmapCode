package com.amap.bundle.pay.wechat;

import android.content.Context;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.pay.payment.PayInfo;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXOpenBusinessView.Req;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class WechatSign extends aca implements abv {
    public WechatSign(Context context) {
        super(context);
    }

    public boolean sign(PayInfo payInfo, abw abw) {
        AMapLog.info("paas.pay", this.TAG, "sign ".concat(String.valueOf(payInfo)));
        this.mCallback = abw;
        this.mInfo = payInfo;
        if (this.mInfo == null || !this.mInfo.isValid() || this.mInfo.requestJson == null) {
            callbackError(PayInfo.CODE_PARAM_ERROR, "request is not valid: mInfo");
            return false;
        }
        JSONObject optJSONObject = this.mInfo.requestJson.optJSONObject("sign_params");
        if (optJSONObject == null) {
            callbackError(PayInfo.CODE_PARAM_ERROR, "request is not valid: signParams ");
            return false;
        }
        String optString = optJSONObject.optString("businessType", null);
        String optString2 = optJSONObject.optString("extInfo", null);
        String optString3 = optJSONObject.optString("query", null);
        String optString4 = this.mInfo.requestJson.optString("auth_id", null);
        if (optString == null || optString3 == null || optString2 == null) {
            callbackError(PayInfo.CODE_PARAM_ERROR, "request is not valid: businessType,extInfo,query");
            return false;
        }
        int wXAppSupportAPI = this.mApi.b.getWXAppSupportAPI();
        if (wXAppSupportAPI >= 620889344) {
            boolean sendRequest = sendRequest(optString3, optString, optString2, optString4);
            if (!sendRequest) {
                callbackError(PayInfo.CODE_FAIL_UNKNOWN, "wx sendReq return false");
            }
            return sendRequest;
        }
        StringBuilder sb = new StringBuilder("wechat.getWXAppSupportAPI = ");
        sb.append(wXAppSupportAPI);
        sb.append(", required=620889344");
        callbackError(PayInfo.CODE_PAYMENT_UNSUPPORT, sb.toString());
        return false;
    }

    private boolean sendRequest(String str, String str2, String str3, String str4) {
        Req req = new Req();
        req.businessType = str2;
        req.query = str;
        if (this.mIsDebug) {
            str3 = "{\"miniProgramType\":1}";
        }
        req.extInfo = str3;
        req.openId = str4;
        String str5 = this.TAG;
        StringBuilder sb = new StringBuilder("sign sendRequest query=");
        sb.append(str);
        sb.append(",businessType=");
        sb.append(req.businessType);
        sb.append(", extInfo=");
        sb.append(req.extInfo);
        AMapLog.info("paas.pay", str5, sb.toString());
        return this.mApi.a((BaseReq) req);
    }

    public void triggerWxShare(BaseResp baseResp) {
        super.triggerWxShare(baseResp);
        AMapLog.info("paas.pay", this.TAG, "onPayFinish, resp = ".concat(String.valueOf(baseResp)));
        if (baseResp == null || baseResp.errCode != 0) {
            callbackError(getResult(baseResp), PayInfo.CODE_FAIL, "");
        } else {
            callbackSuccess(getResult(baseResp));
        }
    }
}
