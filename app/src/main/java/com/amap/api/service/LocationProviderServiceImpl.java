package com.amap.api.service;

import android.os.Bundle;
import android.os.RemoteException;
import com.amap.api.service.locationprovider.ILocationProviderService.Stub;
import com.amap.location.sdk.a.e;

public class LocationProviderServiceImpl extends Stub {
    private e a;

    public LocationProviderServiceImpl(e eVar) {
        this.a = eVar;
    }

    public int a(Bundle bundle) throws RemoteException {
        return this.a.a(bundle);
    }
}
