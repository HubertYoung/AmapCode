package com.autonavi.minimap.drive.inter.impl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class DriveIntentDispatcherImpl extends BaseIntentDispatcher implements dfk {
    private static final String HOST_CAR_RESTRICT = "carRestrict";
    private static final String HOST_KEYWORD_NAVI = "keywordNavi";
    private static final String HOST_NAVI = "navi";
    private static final String HOST_NAVI_USEFULADDRESS = "navi2SpecialDest";
    private static final String HOST_NEW_DRIVE = "drive";
    private static final String HOST_SCHOOLBUS = "schoolbus";
    private static final String HOST_TRAFFIC = "showTraffic";
    private static final String HOST_TRUCK_RESTRICT = "truckRestrict";
    private static final String PAGE_RECORD_CUSTOMIZED_VOICE = "naviVoiceRecord";
    private static final String PARAMS_ALI_CAR_CONNECTION = "openCarConnection";
    private static final String PARAMS_CAR_SERVICE = "carservice";
    private static final String PARAMS_COMMUTE = "commute";
    private static final String PARAMS_COMMUTE_MAIN_FEED = "MainFeed";
    private static final String PARAMS_COMMUTE_MAIN_LBP = "LBP";
    private static final String PARAMS_DIRECT_NAVI = "DirectNavigation";
    private static final String PARAMS_MINE = "Mine";
    private static final String PARAMS_NAVI_DEST_CORP = "corp";
    private static final String PARAMS_NAVI_DEST_HOME = "home";
    private static final String PARAMS_NEW_SETTINGS = "settings";
    private static final String PARAMS_NEW_SHORT_CUT = "navi";
    private static final String PARAMS_NEW_STICKERS = "avoidparkingticket";
    private static final String PARAMS_NEW_TEST_NAVIMESSAGE = "testNaviMessage";
    private static final String PARAMS_OPEN_TRAFFICRADIO = "TrafficRadio";
    private static final String PARAMS_RESTRICT_CITIES = "openRestrictCities";
    private static final String PARAMS_RESTRICT_DETAIL = "showRestrictDetail";
    private static final String PARAMS_ROAD_CAREMA_PAYMENT = "TRCCompensate";
    private static final String PARAMS_STICKERS = "openStickers";
    private static final String PARAMS_TAKETAXI = "TakeTaxi";
    private static final String PARAMS_TRAFFICBOARD = "openTrafficTopBoard";
    private static final String PARAMS_TRAFFICEDOG = "openTrafficEdog";
    private static final String PARAMS_TRAFFICREMIND = "openTrafficRemind";
    private static final String PARAMS_VOICE_SQUARE = "tts";
    private Activity mActivity;
    private boolean mHasShortCutNaviIntentProcessed = false;
    private String mShortCutNaviLastData = null;
    private long mShortCutNaviProcessTime = 0;

    public boolean dispatch(@NonNull Intent intent) {
        return false;
    }

    public void shortCutNavi(Intent intent) {
    }

    public void shortCutNavi(Intent intent, boolean z) {
    }

    public void startNavi(Uri uri) {
    }

    public void updateYunConfig() {
    }

    public DriveIntentDispatcherImpl(Activity activity) {
        this.mActivity = activity;
    }
}
