package com.autonavi.miniapp.plugin.lbs;

import android.location.Location;
import android.text.TextUtils;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationRequest;
import com.alipay.mobile.framework.service.LBSLocationManagerService;
import com.alipay.mobile.framework.service.OnLBSLocationListener;
import com.alipay.mobile.framework.service.OnReGeocodeListener;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.sdk.location.LocationInstrument;

public class AdapterLBSLocationManagerService extends LBSLocationManagerService {
    private static final String TAG = "AdapterLBS";

    public void locationWithRequest(LBSLocationRequest lBSLocationRequest, OnLBSLocationListener onLBSLocationListener, OnReGeocodeListener onReGeocodeListener) {
        AMapLog.debug("infoservice.miniapp", TAG, "locationWithRequest");
    }

    public void locationWithRequest(LBSLocationRequest lBSLocationRequest, OnLBSLocationListener onLBSLocationListener) {
        AMapLog.debug("infoservice.miniapp", TAG, "locationWithRequest");
        if (onLBSLocationListener != null) {
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            LBSLocation lBSLocation = new LBSLocation(latestLocation);
            li a = li.a();
            if (a != null) {
                lj b = a.b(latestLocation.getLongitude(), latestLocation.getLatitude());
                if (b != null) {
                    if (!TextUtils.isEmpty(b.a)) {
                        lBSLocation.setCity(b.a);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(AdcodeConverter.convert2MiniApp(b.j));
                    lBSLocation.setAdCode(sb.toString());
                }
            }
            lBSLocation.setLatitude(latestLocation.getLatitude());
            lBSLocation.setLongitude(latestLocation.getLongitude());
            onLBSLocationListener.onLocationUpdate(lBSLocation);
        }
    }

    public void locationWithRequest(LBSLocationRequest lBSLocationRequest, OnReGeocodeListener onReGeocodeListener) {
        AMapLog.debug("infoservice.miniapp", TAG, "locationWithRequest");
    }

    public void stopLocation(OnLBSLocationListener onLBSLocationListener) {
        AMapLog.debug("infoservice.miniapp", TAG, "stopLocation");
    }

    public LBSLocation getLastKnownLocation() {
        return new LBSLocation(LocationInstrument.getInstance().getLatestLocation());
    }

    public LBSLocation getLastKnownLocation(LBSLocationRequest lBSLocationRequest) {
        return new LBSLocation(LocationInstrument.getInstance().getLatestLocation());
    }
}
