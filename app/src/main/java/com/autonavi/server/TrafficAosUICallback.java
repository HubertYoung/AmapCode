package com.autonavi.server;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class TrafficAosUICallback extends FalconAosPrepareResponseCallback<JSONObject> {
    public abstract void a(int i, String str);

    public abstract void a(JSONObject jSONObject);

    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* synthetic */ void a(Object obj) {
        JSONObject jSONObject = (JSONObject) obj;
        if (jSONObject != null) {
            int optInt = jSONObject.optInt("code", 1);
            jSONObject.optString("message", "");
            boolean optBoolean = jSONObject.optBoolean("result", true);
            if (optInt != 1 || !optBoolean) {
                a(optInt, AbstractAOSParser.aosResponseCodeToMessage(optInt));
                return;
            }
            a(jSONObject);
        }
    }

    private static JSONObject b(AosByteResponse aosByteResponse) {
        try {
            return new JSONObject(aosByteResponse.getResponseBodyString());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        a(0, (String) AbstractAOSParser.DEFAULT_ERROR_MSG);
    }
}
