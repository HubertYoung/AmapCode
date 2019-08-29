package com.autonavi.minimap.life.marketdetail;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;

public class MarketDetailManager$2 implements AosResponseCallbackOnUi<AosByteResponse> {
    final /* synthetic */ Callback a;

    public MarketDetailManager$2(Callback callback) {
        this.a = callback;
    }

    public final /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse != null) {
            byte[] bArr = (byte[]) aosByteResponse.getResult();
            if (bArr != null) {
                try {
                    this.a.callback(a.a(new String(bArr, "UTF-8")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        this.a.error(aosResponseException, false);
    }
}
