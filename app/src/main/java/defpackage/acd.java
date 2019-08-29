package defpackage;

import android.content.Context;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.pay.payment.PayInfo;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.modelpay.PayResp;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: acd reason: default package */
/* compiled from: WechatPay */
public final class acd extends aca implements abt {
    public acd(Context context) {
        super(context);
    }

    public final boolean a(PayInfo payInfo, abw abw) {
        AMapLog.info("paas.pay", this.TAG, "pay ".concat(String.valueOf(payInfo)));
        this.mCallback = abw;
        this.mInfo = payInfo;
        if (this.mInfo == null || !payInfo.isValid() || payInfo.requestJson == null) {
            callbackError(PayInfo.CODE_PARAM_ERROR, "request is not valid");
            return false;
        }
        JSONObject jSONObject = payInfo.requestJson;
        PayReq payReq = new PayReq();
        payReq.appId = jSONObject.optString("appid");
        payReq.partnerId = jSONObject.optString("partnerid");
        payReq.prepayId = jSONObject.optString("prepayid");
        payReq.nonceStr = jSONObject.optString("noncestr");
        payReq.timeStamp = jSONObject.optString("timestamp");
        payReq.packageValue = jSONObject.optString("package");
        payReq.sign = jSONObject.optString("sign");
        AMapLog.info("paas.pay", this.TAG, "sendRequest ".concat(String.valueOf(jSONObject)));
        boolean a = this.mApi.a((BaseReq) payReq);
        if (!a) {
            callbackError(PayInfo.CODE_FAIL_UNKNOWN, "wx sendReq return false");
        }
        return a;
    }

    public final void triggerWxShare(BaseResp baseResp) {
        super.triggerWxShare(baseResp);
        AMapLog.info("paas.pay", this.TAG, "onPayFinish, resp = ".concat(String.valueOf(baseResp)));
        if (baseResp == null || baseResp.errCode != 0) {
            callbackError(getResult(baseResp), PayInfo.CODE_FAIL, "");
        } else if (baseResp.getType() != 5 || !(baseResp instanceof PayResp)) {
            callbackSuccess(getResult(baseResp));
        } else {
            callbackSuccess(a((PayResp) baseResp));
        }
    }

    private String a(PayResp payResp) {
        JSONObject commonResult = getCommonResult(payResp);
        try {
            commonResult.put("prepayId", payResp.prepayId);
            commonResult.put("returnKey", payResp.returnKey);
            commonResult.put("extData", payResp.extData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commonResult.toString();
    }
}
