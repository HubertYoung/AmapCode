package com.autonavi.minimap.ivs;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback;
import com.autonavi.minimap.ivs.param.VehicleSyncRequest;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class IvsRequestHolder {
    private static volatile IvsRequestHolder instance;

    private IvsRequestHolder() {
    }

    public static IvsRequestHolder getInstance() {
        if (instance == null) {
            synchronized (IvsRequestHolder.class) {
                try {
                    if (instance == null) {
                        instance = new IvsRequestHolder();
                    }
                }
            }
        }
        return instance;
    }

    public void sendVehicleSync(VehicleSyncRequest vehicleSyncRequest, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        sendVehicleSync(vehicleSyncRequest, new dkn(), aosResponseCallback);
    }

    public void cancel(AosRequest aosRequest) {
        if (aosRequest != null) {
            in.a().a(aosRequest);
        }
    }

    public void sendVehicleSync(VehicleSyncRequest vehicleSyncRequest, dkn dkn, AosResponseCallback<AosByteResponse> aosResponseCallback) {
        if (dkn != null) {
            vehicleSyncRequest.addHeaders(dkn.d);
            vehicleSyncRequest.setTimeout(dkn.b);
            vehicleSyncRequest.setRetryTimes(dkn.c);
        }
        vehicleSyncRequest.setUrl(VehicleSyncRequest.a);
        vehicleSyncRequest.addSignParam(LocationParams.PARA_COMMON_DIU);
        vehicleSyncRequest.addSignParam(LocationParams.PARA_COMMON_DIV);
        vehicleSyncRequest.addReqParam("tparam", vehicleSyncRequest.b);
        if (dkn != null) {
            in.a().a((AosRequest) vehicleSyncRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(dkn.a, aosResponseCallback));
        } else {
            in.a().a((AosRequest) vehicleSyncRequest, (AosResponseCallback<T>) new FalconAosResponseCallback<T>(aosResponseCallback));
        }
    }
}
