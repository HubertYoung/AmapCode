package com.autonavi.bundle.amaphome.log;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;

public class RedesignOnLineLogCallback extends FalconAosPrepareResponseCallback<ari> {
    public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
        return b(aosByteResponse);
    }

    public final /* synthetic */ void a(Object obj) {
        ari ari = (ari) obj;
        if (ari == null || !ari.result) {
            AMapLog.d("redesign-online", "--fail--data is null");
        } else {
            AMapLog.d("redesign-online", "--success--");
        }
    }

    private static ari b(AosByteResponse aosByteResponse) {
        ari ari = new ari();
        try {
            ari.parser((byte[]) aosByteResponse.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ari;
    }

    public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
        String str;
        if (aosResponseException == null) {
            str = " no msg";
        } else {
            try {
                str = aosResponseException.getMessage();
            } catch (Exception unused) {
                return;
            }
        }
        AMapLog.d("redesign-online", "--error:".concat(String.valueOf(str)));
    }
}
