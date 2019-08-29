package com.autonavi.server;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.minimap.R;
import org.json.JSONObject;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMemberNames;

@KeepPublicClassMemberNames
@KeepName
public class ShortURLResponser extends AbstractAOSParser {
    public static final String NET_ERROR = AMapAppGlobal.getApplication().getString(R.string.error_network_failure_retry);
    private final String a = "ShortURLResponser";
    public String mErrorMsg;
    public String transfer_url = null;
    public String value_url = null;

    public static final class ShortURLCallback implements AosResponseCallbackOnUi<AosByteResponse> {
        private Callback<ShortURLResponser> a;

        public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
            AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
            if (aosByteResponse.getResult() == null) {
                if (this.a != null) {
                    this.a.error(new AosResponseException(ShortURLResponser.NET_ERROR), true);
                }
                return;
            }
            ShortURLResponser shortURLResponser = new ShortURLResponser();
            shortURLResponser.parser((byte[]) aosByteResponse.getResult());
            if (this.a != null) {
                this.a.callback(shortURLResponser);
            }
        }

        public ShortURLCallback(Callback<ShortURLResponser> callback) {
            this.a = callback;
        }

        public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            if (this.a != null) {
                this.a.error(aosResponseException, false);
            }
        }
    }

    public void parser(byte[] bArr) {
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
            if (jSONObject.getInt("code") != 1) {
                this.mErrorMsg = jSONObject.getString("message");
                return;
            }
            this.value_url = jSONObject.getString("transfer_url");
            this.transfer_url = jSONObject.getString("value");
        } catch (Exception e) {
            this.mErrorMsg = NET_ERROR;
            kf.a((Throwable) e);
        }
    }

    public String getErrorDesc(int i) {
        return this.errorMessage;
    }
}
