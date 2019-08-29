package com.autonavi.minimap.onekeycheck.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.minimap.onekeycheck.module.PackData;
import com.autonavi.minimap.onekeycheck.response.InterfResponse;

public class UploadDatasAction$1 implements AosResponseCallback<InterfResponse> {
    final /* synthetic */ PackData a;
    final /* synthetic */ dsq b;

    public UploadDatasAction$1(dsq dsq, PackData packData) {
        this.b = dsq;
        this.a = packData;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        JSONObject parseObject = JSON.parseObject(((InterfResponse) aosResponse).getResponseBodyString());
        int intValue = parseObject.getIntValue("code");
        String string = parseObject.getString("message");
        if (1 != intValue || !"success".equalsIgnoreCase(string)) {
            dsq.a(this.b, false, this.a.isLashPack());
        } else {
            dsq.a(this.b, true, this.a.isLashPack());
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        dsq.a(this.b, false, this.a.isLashPack());
    }
}
