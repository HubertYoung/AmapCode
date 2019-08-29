package com.autonavi.mine.feedbackv2.drivenavigationissues;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import java.util.ArrayList;

public class DrivingHistoryNetManager$1 implements AosResponseCallbackOnUi<AosStringResponse> {
    final /* synthetic */ a a;
    final /* synthetic */ cgl b;

    public DrivingHistoryNetManager$1(cgl cgl, a aVar) {
        this.b = cgl;
        this.a = aVar;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosStringResponse aosStringResponse = (AosStringResponse) aosResponse;
        if (this.a != null) {
            try {
                JSONObject parseObject = JSON.parseObject((String) aosStringResponse.getResult());
                if (parseObject == null || parseObject.getIntValue("status") != 0) {
                    a aVar = this.a;
                    if (parseObject != null) {
                        parseObject.getString("errorMsg");
                    }
                    aVar.a();
                    return;
                }
                JSONArray jSONArray = parseObject.getJSONArray("data");
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.size(); i++) {
                    arrayList.add(new cgk(jSONArray.getJSONObject(i)));
                }
                this.a.a(arrayList);
            } catch (Exception e) {
                a aVar2 = this.a;
                e.getMessage();
                aVar2.a();
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            a aVar = this.a;
            aosResponseException.getMessage();
            aVar.a();
        }
    }
}
