package com.autonavi.map.core;

import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@Deprecated
@KeepName
public class MapCustomizeManager {
    public static final int ACTION_COMPASS_CLICK = 4;
    public static final int ACTION_GPS_POSITION_DETAIL_VIEW = 2;
    public static final int ACTION_POI_DETAIL_VIEW = 1;
    public static final int BUS_NAVI_MODE = 2;
    public static final int CAR_NAVI_MODE = 1;
    public static final int DEFAULT_ACTION = 1;
    public static final int ONFOOT_NAVI_MODE = 3;
    public static final int SUSPEND_ACTION_COMPANY = 128;
    public static final int SUSPEND_ACTION_COMPASS = 8;
    public static final int SUSPEND_ACTION_GOHOME = 64;
    public static final int SUSPEND_ACTION_GPS = 4;
    public static final int SUSPEND_ACTION_MAP_LAYER = 16;
    public static final int SUSPEND_ACTION_TRAFFIC = 32;
    public static final int SUSPEND_ACTION_ZOOM_IN = 1;
    public static final int SUSPEND_ACTION_ZOOM_OUT = 2;
    public static final int SUSPEN_DEFAULT_ACTION = 65535;
    public static final int VIEW_ALL = -1;
    public static final int VIEW_AUTO_REMOTE = 33554432;
    public static final int VIEW_BACK_TO_MAIN = 65536;
    public static final int VIEW_COMPASS = 2;
    public static final int VIEW_DEFAULT = -381358241;
    public static final int VIEW_GPS = 4;
    public static final int VIEW_GUIDE = 268435456;
    public static final int VIEW_GUIDE_MAP = 1048576;
    public static final int VIEW_HOT_IMAGE_ON_OFF = 2097152;
    public static final int VIEW_MAPLAYER_DIALOG_INDOOR = 512;
    public static final int VIEW_MAPLAYER_DIALOG_SAVE = 1024;
    public static final int VIEW_MAPLAYER_DIALOG_TRAFFIC = 256;
    public static final int VIEW_MAPLAYER_SWITCH = 64;
    public static final int VIEW_MAP_INDOOR = 32768;
    public static final int VIEW_MAP_RQBXY_SEARCH = 4096;
    public static final int VIEW_NONE = 0;
    public static final int VIEW_REFRESH = 8388608;
    public static final int VIEW_REPORT = 16384;
    public static final int VIEW_REPORT_ERROR = 128;
    public static final int VIEW_ROUTE_PREVIEW = 131072;
    public static final int VIEW_SCALELINE = 8192;
    public static final int VIEW_SEARCH_ALONG = 67108864;
    public static final int VIEW_SPECIFIC = -381376418;
    public static final int VIEW_TAXI_LAYOUT = 32;
    public static final int VIEW_TRAFFIC_CONDITION = 2048;
    public static final int VIEW_TRAFFIC_HELP = 1;
    public static final int VIEW_TRAFFIC_LAYOUT = 16;
    public static final int VIEW_VOICE_GUIDE = 524288;
    public static final int VIEW_ZOOM = 8;
    private int a = 1;
    private int b = 65535;
    private int c = VIEW_SPECIFIC;
    private int d = 0;
    private ceo e = new ceo();

    public void enableAction(int i) {
        this.a = i | this.a;
    }

    public void disableAction(int i) {
        this.a = (~i) & this.a;
    }

    public boolean isActionEnable(int i) {
        return (i & this.a) > 0;
    }

    public void enableSuspendAction(int i) {
        this.b = i | this.b;
    }

    public void disableSuspendAction(int i) {
        this.b = (~i) & this.b;
    }

    public boolean isSuspendActionEnable(int i) {
        return (i & this.b) > 0;
    }

    public void enableView(int i) {
        this.c = i | this.c;
        cdd.n().e();
    }

    public void disableView(int i) {
        this.c = (~i) & this.c;
        cdd.n().e();
    }

    public void enableCustomView(int i) {
        this.c = i | this.c;
    }

    public void disableCustomView(int i) {
        this.c = (~i) & this.c;
    }

    public int getNaviMode() {
        return this.d;
    }

    public void setNaviMode(int i) {
        this.d = i;
    }

    public boolean isViewEnable(int i) {
        return (i & this.c) > 0;
    }

    public void resume() {
        cdd.n().e();
    }

    public ceo getMapLayerDialogCustomActions() {
        return this.e;
    }
}
