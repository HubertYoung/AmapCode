package com.autonavi.jni.ae.guide.observer;

import com.autonavi.jni.ae.guide.model.CruiseCongestionInfo;
import com.autonavi.jni.ae.guide.model.CruiseFacilityInfo;
import com.autonavi.jni.ae.guide.model.CruiseInfo;
import com.autonavi.jni.ae.guide.model.CruiseTimeAndDistInfo;
import com.autonavi.jni.ae.guide.model.LaneInfo;

public interface GCruiseObserver {
    void onHideCruiseLaneInfo();

    void onShowCruiseLaneInfo(LaneInfo laneInfo);

    void onUpdateCruiseCongestionInfo(CruiseCongestionInfo cruiseCongestionInfo);

    void onUpdateCruiseFacility(CruiseFacilityInfo[] cruiseFacilityInfoArr);

    void onUpdateCruiseInfo(CruiseInfo cruiseInfo);

    void onUpdateCruiseTimeAndDist(CruiseTimeAndDistInfo cruiseTimeAndDistInfo);

    void onUpdateElecCameraInfo(CruiseFacilityInfo[] cruiseFacilityInfoArr);
}
