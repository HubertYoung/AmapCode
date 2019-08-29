package com.autonavi.jni.ae.guide.model;

public class GuideParam {
    public static final int GuideParamCamera = 8;
    public static final int GuideParamCommon = 1;
    public static final int GuideParamCompany = 21;
    public static final int GuideParamCrossing = 10;
    public static final int GuideParamCruise = 6;
    public static final int GuideParamEasy3d = 14;
    public static final int GuideParamEmulator = 15;
    public static final int GuideParamExit = 9;
    public static final int GuideParamHome = 20;
    public static final int GuideParamIntervalCamera = 19;
    public static final int GuideParamInvalid = 0;
    public static final int GuideParamJiliAuto = 16;
    public static final int GuideParamLane = 5;
    public static final int GuideParamNavi = 7;
    public static final int GuideParamNaviWeather = 17;
    public static final int GuideParamNetwork = 13;
    public static final int GuideParamSAPA = 4;
    public static final int GuideParamTMC = 2;
    public static final int GuideParamTR = 3;
    public static final int GuideParamTTSPlay = 11;
    public static final int GuideParamTollGateQuickPay = 18;
    public static final int GuideParamVehicle = 12;
    public static final int TravelParamTrace = 1002;
    public static final int TravelParamVoice = 1000;
    public static final int TravelParamWeather = 1001;
    private CameraParam cameraParam;
    private NaviPointInfo companyInfo;
    private NaviPointInfo homeInfo;
    private int type;

    public void setCameraParam(CameraParam cameraParam2) {
        if (cameraParam2 == null) {
            this.type = 0;
            return;
        }
        this.cameraParam = cameraParam2;
        this.type = 8;
    }

    public void setHomeInfo(NaviPointInfo naviPointInfo) {
        this.type = 20;
        if (naviPointInfo != null) {
            this.homeInfo = naviPointInfo;
        }
    }

    public void setCompanyInfo(NaviPointInfo naviPointInfo) {
        this.type = 21;
        if (naviPointInfo != null) {
            this.companyInfo = naviPointInfo;
        }
    }
}
