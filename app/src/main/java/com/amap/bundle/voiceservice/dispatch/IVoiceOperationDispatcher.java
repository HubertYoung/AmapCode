package com.amap.bundle.voiceservice.dispatch;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IVoiceOperationDispatcher extends bie {
    void getCurrentLocationInfo(int i, String str);

    void moveMapView(int i, String str);

    void openFavoritePage(int i, String str);

    void operateMap(int i, String str);

    void setFavoritePoi(int i, String str);

    void setTraffic(int i, String str);

    void setZoomDiff(int i, String str);
}
