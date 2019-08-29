package com.alipay.mobile.nebula.provider;

import android.location.Location;

public interface H5LastKnowLocationProvider {
    Location getLocation();

    boolean isInChina();
}
