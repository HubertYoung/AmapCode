package com.autonavi.bundle.carownerservice.api;

import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface ISyncVehicles {
    int getLocalVehicleCount();

    a pull(Callback<Boolean> callback);

    void syncLocalVehicles();
}
