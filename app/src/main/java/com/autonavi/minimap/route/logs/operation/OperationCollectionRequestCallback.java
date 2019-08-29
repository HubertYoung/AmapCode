package com.autonavi.minimap.route.logs.operation;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;

public class OperationCollectionRequestCallback implements AosResponseCallback<AosByteResponse> {
    public a a;

    public interface a {
        void a(int i);
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        eao.a((String) "## upload OperationCollectionRequestCallback  success");
        final ecz ecz = new ecz();
        try {
            ecz.parser((byte[]) aosByteResponse.getResult());
            aho.a(new Runnable() {
                public final void run() {
                    if (OperationCollectionRequestCallback.this.a != null) {
                        OperationCollectionRequestCallback.this.a.a(ecz.a);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        eao.a((String) "## upload OperationCollectionRequestCallback  error");
    }
}
