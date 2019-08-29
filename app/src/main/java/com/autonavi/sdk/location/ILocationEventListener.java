package com.autonavi.sdk.location;

import android.location.Location;

public interface ILocationEventListener {
    void onLocateStart();

    void onLocateStop();

    void onLocationChanged(Location location);
}
