package defpackage;

import android.content.Context;
import android.content.Intent;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.pay.payment.PayInfo;
import com.autonavi.bundle.account.api.IThirdAuth.b;
import com.autonavi.common.utils.Constant;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aca reason: default package */
/* compiled from: BaseWechatPay */
public abstract class aca implements b {
    protected final String TAG = getClass().getSimpleName();
    protected aoj mApi;
    protected abw mCallback;
    protected Context mContext;
    protected PayInfo mInfo;
    protected boolean mIsDebug = false;

    public aca(Context context) {
        this.mContext = context;
        this.mApi = aoj.d();
        this.mApi.a = this;
    }

    public void destroy() {
        this.mApi.a = null;
    }

    public void setDebug(boolean z) {
        AMapLog.info("paas.pay", this.TAG, "setDebug ".concat(String.valueOf(z)));
        this.mIsDebug = z;
    }

    /* access modifiers changed from: protected */
    public void callbackError(int i, String str) {
        callbackError("", i, str);
    }

    /* access modifiers changed from: protected */
    public void callbackError(String str, int i, String str2) {
        String str3 = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(", code=");
        sb.append(i);
        sb.append(", ");
        sb.append(str2);
        AMapLog.info("paas.pay", str3, sb.toString());
        if (!(this.mCallback == null || this.mInfo == null)) {
            this.mInfo.setResult(str, i, str2);
            this.mCallback.a(this.mInfo);
        }
        destroy();
    }

    /* access modifiers changed from: protected */
    public void callbackSuccess(String str) {
        AMapLog.info("paas.pay", this.TAG, str);
        if (!(this.mCallback == null || this.mInfo == null)) {
            this.mInfo.setResult(str, 100000, "");
            this.mCallback.a(this.mInfo);
        }
        destroy();
    }

    /* access modifiers changed from: protected */
    public JSONObject getCommonResult(BaseResp baseResp) {
        JSONObject jSONObject = new JSONObject();
        if (baseResp == null) {
            return jSONObject;
        }
        try {
            jSONObject.put("errCode", baseResp.errCode);
            jSONObject.put("errStr", baseResp.errStr);
            jSONObject.put("transaction", baseResp.transaction);
            jSONObject.put("openId", baseResp.openId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    /* access modifiers changed from: protected */
    public String getResult(BaseResp baseResp) {
        return getCommonResult(baseResp).toString();
    }

    public void triggerWxShare(BaseResp baseResp) {
        Intent intent = new Intent();
        intent.setClassName(this.mContext.getApplicationContext(), Constant.LAUNCHER_ACTIVITY_NAME);
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }
}
