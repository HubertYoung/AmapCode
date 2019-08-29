package com.autonavi.map.wallet;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.common.Callback;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import org.json.JSONException;
import org.json.JSONObject;

public class WalletNetManager$1 extends FalconAosPrepareResponseCallback<JSONObject> {
    final /* synthetic */ Callback a;

    public WalletNetManager$1(Callback callback) {
        this.a = callback;
    }

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* synthetic */ void a(Object obj) {
        JSONObject jSONObject = (JSONObject) obj;
        if (this.a != null) {
            if (jSONObject != null) {
                this.a.callback(jSONObject);
                return;
            }
            this.a.error(new RuntimeException("result is null!"), true);
        }
    }

    private static JSONObject b(AosByteResponse aosByteResponse) {
        if (aosByteResponse.getResult() == null) {
            return null;
        }
        try {
            return AbstractAOSParser.aosByteResponseToJSONObject(aosByteResponse);
        } catch (JSONException unused) {
            return null;
        }
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            this.a.error(aosResponseException, false);
        }
    }
}
