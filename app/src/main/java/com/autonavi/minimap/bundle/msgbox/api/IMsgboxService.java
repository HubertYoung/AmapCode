package com.autonavi.minimap.bundle.msgbox.api;

import android.app.Activity;
import com.autonavi.minimap.bundle.msgbox.entity.AmapMessage;
import java.util.ArrayList;
import java.util.List;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepImplementations
@KeepName
public interface IMsgboxService extends esc {
    public static final int MAIN_PAGE_ID = 0;
    public static final int MAIN_PAGE_MAPLAYER_LOCATION = 4;
    public static final int MAIN_PAGE_MORE_LOCATION = 3;
    public static final int MAIN_PAGE_NAVI_LOCATION = 2;
    public static final int MAIN_PAGE_NEARBY_LOCATION = 0;
    public static final int MAIN_PAGE_REAL_SCENE_LOCATION = 5;
    public static final int MAIN_PAGE_ROUTE_LOCATION = 1;
    public static final int MORE_PAGE_ACTIVITY_ZONE = 0;
    public static final int MORE_PAGE_ID = 1;
    public static final int MSG_SOURCE_LOCATION = 2;
    public static final int ROUTE_BUS_RESULT_LIST_PAGE_ID = 2;
    public static final String ROUTE_BUS_RESULT_LIST_PAGE_TYPE_ONE = "1";
    public static final int ROUTE_BUS_RESULT_MAP_PAGE_ID = 3;
    public static final int ROUTE_FOOT_RESULT_MAP_PAGE_ID = 4;
    public static final String SP_KEY_MsgboxNaviTtsVer = "MsgboxNaviTtsVer";

    @KeepClassMembers
    @KeepName
    public interface MainMapUIUpdater {
        void updateUI(List<AmapMessage> list, boolean z, int i, ArrayList<AmapMessage> arrayList, AmapMessage amapMessage);
    }

    @KeepClassMembers
    @KeepName
    public interface UIUpdater {
        void updateUI(AmapMessage amapMessage, boolean z, int i);
    }

    public interface a {
    }

    void executeAction(AmapMessage amapMessage);

    void fetchMessage(int i, boolean z, UIUpdater uIUpdater);

    void getMessageInBackground(Activity activity, boolean z);

    void getMessageSize(a aVar, int i);

    dap getMsgboxStorageService();

    int getNewComingUnRead_MsgNumFromDB();

    void handlePush(String str);

    void jumpToMainPage();

    void notifyOfflineMapInformed();

    void reset();

    void setRead(String... strArr);

    void setSubRead(String... strArr);
}
