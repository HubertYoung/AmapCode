package com.amap.bundle.drive.result.autonavisearchmanager.inter;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.common.model.POI;

public class CarSceneSearchAosCallback implements AosResponseCallback<AosByteResponse> {
    public POI a;
    /* access modifiers changed from: private */
    public op b;

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
        if (aosByteResponse != null && aosByteResponse.getResult() != null) {
            oq oqVar = new oq();
            try {
                oqVar.parser((byte[]) aosByteResponse.getResult());
                final oo ooVar = oqVar.a;
                if (!(ooVar == null || this.b == null)) {
                    String type = this.a.getType();
                    if (type != null) {
                        if (type.startsWith(DriveUtil.POI_TYPE_RAILWAY)) {
                            ooVar.g = 2;
                        }
                        if (type.startsWith(DriveUtil.POI_TYPE_AIRPORT)) {
                            ooVar.g = 1;
                        }
                    }
                    if (this.b != null) {
                        aho.a(new Runnable() {
                            public final void run() {
                                CarSceneSearchAosCallback.this.b.a(ooVar);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    public CarSceneSearchAosCallback(POI poi, op opVar) {
        this.b = opVar;
        this.a = poi;
    }
}
