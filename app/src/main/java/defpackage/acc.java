package defpackage;

import android.content.Context;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.pay.payment.PayInfo;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth.Req;
import com.tencent.mm.opensdk.modelmsg.SendAuth.Resp;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: acc reason: default package */
/* compiled from: WechatLogin */
public final class acc extends aca implements abs {
    private aou a = new aou() {
        public final void a(Resp resp) {
            AMapLog.info("paas.pay", acc.this.TAG, "setWxAuthResult ".concat(String.valueOf(resp)));
            if (resp == null || resp.errCode != 0) {
                acc.this.callbackError(acc.this.a(resp), PayInfo.CODE_FAIL, "");
            } else {
                acc.this.callbackSuccess(acc.this.a(resp));
            }
        }
    };

    public acc(Context context) {
        super(context);
    }

    public final boolean a(abw abw) {
        AMapLog.info("paas.pay", this.TAG, "login ");
        this.mCallback = abw;
        this.mInfo = new PayInfo("");
        return a();
    }

    public final void triggerWxShare(BaseResp baseResp) {
        super.triggerWxShare(baseResp);
    }

    private boolean a() {
        try {
            aoj.d().a(this.a);
            Req req = new Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_amap_login";
            AMapLog.info("paas.pay", this.TAG, "login sendSDKRequest");
            boolean a2 = aoj.d().a((BaseReq) req);
            if (!a2) {
                callbackError(PayInfo.CODE_FAIL_UNKNOWN, "sendReq return false");
            }
            return a2;
        } catch (Exception e) {
            callbackError(PayInfo.CODE_FAIL_UNKNOWN, e.toString());
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public final String a(Resp resp) {
        JSONObject commonResult = getCommonResult(resp);
        try {
            commonResult.put("code", resp.code);
            commonResult.put("state", resp.state);
            commonResult.put("url", resp.url);
            commonResult.put("lang", resp.lang);
            commonResult.put("country", resp.country);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return commonResult.toString();
    }
}
