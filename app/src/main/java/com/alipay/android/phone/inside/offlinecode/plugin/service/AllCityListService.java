package com.alipay.android.phone.inside.offlinecode.plugin.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.offlinecode.rpc.ScardCenterRpcProvider;
import com.alipay.android.phone.inside.offlinecode.rpc.model.ScardCenterRes;
import org.json.JSONArray;
import org.json.JSONObject;

public class AllCityListService extends AbstractInsideService<Bundle, Bundle> {
    public Bundle startForResult(Bundle bundle) throws Exception {
        ScardCenterRes querySubScene = new ScardCenterRpcProvider().querySubScene(getContext());
        Bundle bundle2 = new Bundle();
        bundle2.putString("code", querySubScene.code);
        bundle2.putString("result", getResult(querySubScene.getJSONIndicator(), querySubScene.getJSONArrayResult()));
        return bundle2;
    }

    private String getResult(JSONObject jSONObject, JSONArray jSONArray) throws Exception {
        JSONObject jSONObject2 = new JSONObject();
        if (jSONObject != null) {
            jSONObject2.put("indicator", jSONObject);
        }
        if (jSONArray != null) {
            jSONObject2.put("cities", jSONArray);
        }
        return jSONObject2.toString();
    }
}
