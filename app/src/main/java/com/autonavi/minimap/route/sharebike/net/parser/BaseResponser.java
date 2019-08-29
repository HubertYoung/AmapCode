package com.autonavi.minimap.route.sharebike.net.parser;

import android.util.Log;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseResponser extends AbstractAOSParser {
    protected AosResponseCallback<AosByteResponse> callback;
    /* access modifiers changed from: private */
    public a mListener;
    /* access modifiers changed from: private */
    public Class mRequestClazz;
    private BaseNetResult mResult;

    public class BaseCancelCallback extends FalconAosPrepareResponseCallback {
        public BaseCancelCallback() {
        }

        public final Object a(AosByteResponse aosByteResponse) {
            byte[] bArr = (byte[]) aosByteResponse.getResult();
            String str = "";
            if (bArr != null && bArr.length > 0) {
                str = new String(bArr);
            }
            StringBuilder sb = new StringBuilder("ShareBikeNet -- >");
            sb.append(BaseResponser.this.mRequestClazz.getName());
            eao.a(sb.toString(), " callback --->".concat(String.valueOf(str)));
            try {
                BaseResponser.this.parser(bArr);
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        public final void a(Object obj) {
            if (BaseResponser.this.mListener != null) {
                BaseResponser.this.mListener.a(BaseResponser.this.getResult());
            }
            a.a(BaseResponser.this.mRequestClazz);
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            StringBuilder sb = new StringBuilder("ShareBikeNet -- >");
            sb.append(BaseResponser.this.mRequestClazz.getName());
            eao.a(sb.toString(), " error Throwable ex --->".concat(String.valueOf(aosResponseException)));
            if (aosRequest == null || !aosRequest.isCanceled()) {
                if (BaseResponser.this.mListener != null) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(BaseResponser.this.mRequestClazz.getName());
                    sb2.append(this);
                    sb2.append(".error");
                    eao.d("BaseResponser", sb2.toString());
                    BaseResponser.this.mListener.a(null);
                }
                a.a(BaseResponser.this.mRequestClazz);
            }
        }
    }

    public String getErrorDesc(int i) {
        if (i == 7) {
            return "帐号未绑定";
        }
        if (i == 14) {
            return "帐号未登陆";
        }
        switch (i) {
            case 0:
                return "未知错误";
            case 1:
                return "成功";
            case 2:
                return "访问失败";
            case 3:
                return "参数错误";
            case 4:
                return "签名错误";
            default:
                return "";
        }
    }

    private BaseResponser() {
    }

    public BaseResponser(Class cls, a aVar) {
        this.callback = new BaseCancelCallback();
        this.mRequestClazz = cls;
        this.mListener = aVar;
    }

    /* access modifiers changed from: protected */
    public String jsonOptString(JSONObject jSONObject, String str) {
        String optString = jSONObject.optString(str);
        return "null".equals(optString) ? "" : optString;
    }

    /* access modifiers changed from: protected */
    public final void setResult(BaseNetResult baseNetResult) {
        this.mResult = baseNetResult;
    }

    public BaseNetResult getResult() {
        if (this.mResult == null) {
            if (bno.a) {
                StringBuilder sb = new StringBuilder("mResult need setResult first!! stack=");
                sb.append(Log.getStackTraceString(new Throwable()));
                eao.d("BaseResponser", sb.toString());
            }
            return null;
        }
        this.mResult.errorToast = this.errorMessage;
        return this.mResult;
    }

    public AosResponseCallback<AosByteResponse> getCallback() {
        return this.callback;
    }
}
