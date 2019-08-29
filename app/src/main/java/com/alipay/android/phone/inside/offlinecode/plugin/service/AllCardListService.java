package com.alipay.android.phone.inside.offlinecode.plugin.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.offlinecode.rpc.ScardCenterRpcProvider;
import com.alipay.android.phone.inside.offlinecode.rpc.model.ScardCenterRes;
import org.json.JSONArray;
import org.json.JSONObject;

public class AllCardListService extends AbstractInsideService<Bundle, Bundle> {
    public Bundle startForResult(Bundle bundle) throws Exception {
        String string = bundle.getString("cityCode");
        ScardCenterRes queryCardList = new ScardCenterRpcProvider().queryCardList(getContext(), string, "ALL");
        Bundle bundle2 = new Bundle();
        bundle2.putString("code", queryCardList.code);
        bundle2.putString("result", getResult(queryCardList.getJSONIndicator(), queryCardList.getJSONArrayResult()));
        return bundle2;
    }

    private String getResult(JSONObject jSONObject, JSONArray jSONArray) throws Exception {
        JSONObject jSONObject2 = new JSONObject();
        if (jSONObject != null) {
            jSONObject2.put("indicator", jSONObject);
        }
        if (jSONArray != null) {
            jSONObject2.put("cards", jSONArray);
        }
        return jSONObject2.toString();
    }
}
