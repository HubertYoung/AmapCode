package com.amap.bundle.voiceservice.dispatch;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IVoiceDriveDispatcher extends bie {
    void addMidPois(int i, String str);

    void adjustVolume(int i, String str);

    void enterRadarMode(int i, String str);

    void exitNavi(int i, String str);

    void getCurrentLocationInfo(int i, String str);

    void getHistoryRoutes(int i, String str);

    void handleFactoryVoiceCommand(int i, String str);

    void handleVoiceCommand(String str, String str2);

    void hasTruckInfo(int i, String str);

    void operateMap(int i, String str);

    void previewMap(int i, String str);

    void refreshRouteInCarRoutePage(int i, String str);

    void refreshRouteInNavi(int i, String str);

    void refreshRouteInTruckRoutePage(int i, String str);

    void requestGuideInfo(int i, String str);

    void requestNaviInfo(int i, String str);

    void requestRoute(int i, String str);

    void requestTrafficMessage(int i, String str);

    void requestTruckRoute(int i, String str);

    void searchAlongInNavi(int i, String str);

    void searchAlongInRoutePage(int i, String str);

    void setNaviApiControlListener(aig aig);

    void setRouteApiControlListener(aij aij);

    void setRouteParamsInCarRoutePage(int i, String str);

    void setRouteParamsInNavi(int i, String str);

    void setTraffic(int i, String str);

    void setTrafficRoutePage(int i, String str);

    void startNavi(int i, String str);

    void swapStartEndPoi(int i, String str);

    void switchRoute(int i, String str);
}
