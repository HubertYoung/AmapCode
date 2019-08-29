package com.autonavi.map.search.js.action;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchSuggestAction$1 implements AosResponseCallback<AosStringResponse> {
    final /* synthetic */ wa a;
    final /* synthetic */ bys b;

    public SearchSuggestAction$1(bys bys, wa waVar) {
        this.b = bys;
        this.a = waVar;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        try {
            bys.a(this.b, new JSONObject((String) ((AosStringResponse) aosResponse).getResult()), this.a);
        } catch (JSONException e) {
            e.printStackTrace();
            bys.a(this.b, (Exception) e, this.a);
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        bys.a(this.b, (Exception) aosResponseException, this.a);
    }
}
