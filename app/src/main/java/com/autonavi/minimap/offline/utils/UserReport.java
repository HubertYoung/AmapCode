package com.autonavi.minimap.offline.utils;

import android.content.Context;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.offline.externalimport.IExternalService;
import org.json.JSONException;
import org.json.JSONObject;

public class UserReport {
    public static final String BTN_MYNAVITTS_NAVITTS_RECORD_CUSTOM = "B001";
    public static final String BTN_MYNAVITTS_NAVITTS_RECORD_CUSTOM_CANCEL = "B002";
    public static final String BTN_MYNAVITTS_NAVITTS_RECORD_CUSTOM_DELETE = "B005";
    public static final String BTN_MYNAVITTS_NAVITTS_RECORD_CUSTOM_SWITCHBUTTON = "B004";
    public static final String BTN_MYNAVITTS_NAVITTS_RECORD_CUSTOM_USE = "B003";
    public static final String BTN_NAVITTS_BACK_NOT_SAVED_ON_DIALOG = "B005";
    public static final String BTN_NAVITTS_BACK_SAVED_ON_DIALOG = "B006";
    public static final String BTN_NAVITTS_CANCEL_DOWNLOADING = "B015";
    public static final String BTN_NAVITTS_CURRENT_VOICE_MD5_CHECK = "B021";
    public static final String BTN_NAVITTS_DEFAULT_VOICE_DECOMPRESSION = "B020";
    public static final String BTN_NAVITTS_DETELE_CURRENT_VOICE = "B014";
    public static final String BTN_NAVITTS_DOWLOAD_VOICE = "B011";
    public static final String BTN_NAVITTS_MY_VOICES = "B010";
    public static final String BTN_NAVITTS_NEXT_SENTENCE = "B004";
    public static final String BTN_NAVITTS_PAUSE_CURRENT_PLAYING = "B019";
    public static final String BTN_NAVITTS_PAUSE_DOWNLOADING = "B012";
    public static final String BTN_NAVITTS_PLAY_CURRENT_VOICE = "B018";
    public static final String BTN_NAVITTS_RANDOM_CHANGE = "B002";
    public static final String BTN_NAVITTS_RECORDING_BUTTON_UP = "B007";
    public static final String BTN_NAVITTS_RECORD_AGAIN = "B003";
    public static final String BTN_NAVITTS_REPLAY = "B008";
    public static final String BTN_NAVITTS_RESUME_DOWNLOADING = "B013";
    public static final String BTN_NAVITTS_SQUARE = "B009";
    public static final String BTN_NAVITTS_SQUARE_DOWNLOAD = "B001";
    public static final String BTN_NAVITTS_UPDATE_CURRENT_VOICE = "B017";
    public static final String BTN_NAVITTS_USE_CURRENT_VOICE = "B016";
    public static final String BTN_NAVITTS_VOICE_COUNT = "B022";
    public static final String BTN_NAVITTS_VOICE_PACKAGE_SAVING_SUCCESSFULLY = "B001";
    public static final String BTN_OFFLINEDATA_ALONGWAYDOWNLOAD_ADD = "B002";
    public static final String BTN_OFFLINEDATA_ALONGWAYDOWNLOAD_DOWNLOAD = "B001";
    public static final String BTN_OFFLINEDATA_ALONGWAYDOWNLOAD_NORESULT = "B003";
    public static final String BTN_OFFLINEDATA_ALONGWAYDOWNLOAD_NORESULT_LINK = "B004";
    public static final String BTN_OFFLINEDATA_ALONGWAYQUERY_ADD = "B003";
    public static final String BTN_OFFLINEDATA_ALONGWAYQUERY_DOWNLOAD = "B002";
    public static final String BTN_OFFLINEDATA_ALONGWAYQUERY_ENDINPUT = "B005";
    public static final String BTN_OFFLINEDATA_ALONGWAYQUERY_NORESULT = "B004";
    public static final String BTN_OFFLINEDATA_ALONGWAYQUERY_STARTINPUT = "B001";
    public static final String BTN_OFFLINEDATA_CITYLIST_ALONGWAY_QUERY = "B023";
    public static final String BTN_OFFLINEDATA_CITYLIST_DOWNLOAD = "B001";
    public static final String BTN_OFFLINEDATA_CITYLIST_DOWNLOAD_PROVINCE = "B021";
    public static final String BTN_OFFLINEDATA_CITYLIST_ITEM_CLICK = "B012";
    public static final String BTN_OFFLINEDATA_CITYLIST_ITEM_CLICK_CANCEL = "B013";
    public static final String BTN_OFFLINEDATA_CITYLIST_ITEM_CLICK_CANCELDOWNLOAD = "B014";
    public static final String BTN_OFFLINEDATA_CITYLIST_ITEM_CLICK_DOWNLOADMAP = "B016";
    public static final String BTN_OFFLINEDATA_CITYLIST_ITEM_CLICK_DOWNLOADMAPNAVI = "B018";
    public static final String BTN_OFFLINEDATA_CITYLIST_ITEM_CLICK_DOWNLOADNAVI = "B017";
    public static final String BTN_OFFLINEDATA_CITYLIST_ITEM_CLICK_PAUSEDOWNLOAD = "B015";
    public static final String BTN_OFFLINEDATA_CITYLIST_MOBILE_CANCLE = "B032";
    public static final String BTN_OFFLINEDATA_CITYLIST_MOBILE_OK = "B033";
    public static final String BTN_OFFLINEDATA_CITYLIST_PAUSE = "B003";
    public static final String BTN_OFFLINEDATA_CITYLIST_RESUME = "B005";
    public static final String BTN_OFFLINEDATA_CITYLIST_RETRY = "B007";
    public static final String BTN_OFFLINEDATA_CITYLIST_SEARCHEDIT = "B022";
    public static final String BTN_OFFLINEDATA_CITYLIST_TO_DOWNLOADMGR_TAB = "B019";
    public static final String BTN_OFFLINEDATA_CITYLIST_UPDATE = "B002";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_DOWNLOADALL = "B023";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_ITEM_CLICK = "B012";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_ITEM_CLICK_CANCEL = "B013";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_ITEM_CLICK_CANCELDOWNLOAD = "B014";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_ITEM_CLICK_DELMAP = "B019";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_ITEM_CLICK_DELMAPNAVI = "B021";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_ITEM_CLICK_DELNAVI = "B020";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_ITEM_CLICK_DOWNLOADMAP = "B016";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_ITEM_CLICK_DOWNLOADMAPNAVI = "B018";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_ITEM_CLICK_DOWNLOADNAVI = "B017";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_ITEM_CLICK_PAUSEDOWNLOAD = "B015";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_MAPDOWNLOADERROR = "B031";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_MAPDOWNLOADSUCCESS = "B038";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_MAPENGINEMD5ERROR = "B041";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_MAPMD5ERROR = "B034";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_MAPUNZIPCANCEL = "B036";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_MAPUNZIPERROR = "B032";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_MAP_FINISH = "B009";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_MAP_ZIPPING = "B008";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_MOREPAGE = "B028";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_NAVIDOWNLOADERROR = "B030";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_NAVIDOWNLOADSUCCESS = "B039";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_NAVIMD5ERROR = "B035";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_NAVIUNZIPCANCEL = "B037";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_NAVIUNZIPERROR = "B033";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_NAVI_FINISH = "B011";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_NAVI_ZIPPING = "B010";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_OPENMAP = "B053";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_PAUSE = "B003";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_PAUSEALL = "B024";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_PAUSE_AUTO = "B004";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_RECOMMEND_DOWNLOAD = "B001";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_RESUME = "B005";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_RESUME_AUTO = "B006";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_RETRY = "B007";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_SWITCHSDCARD = "B025";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_TO_ALLCITY_TAB = "B026";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_UPDATE = "B002";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_UPDATEALL = "B022";
    public static final String BTN_OFFLINEDATA_DOWNLOADMGR_UPDATE_AUTO = "B027";
    public static final String BTN_OFFLINEDATA_MOREPAGE_FAQ = "B002";
    public static final String BTN_OFFLINEDATA_MOREPAGE_FEEDBACK = "B001";
    public static final String BTN_OFFLINEDATA_MOREPAGE_OFFKINEMAP_PRIORI_CLOSE = "B005";
    public static final String BTN_OFFLINEDATA_MOREPAGE_OFFKINEMAP_PRIORI_OPEN = "B004";
    public static final String BTN_OFFLINEDATA_MOREPAGE_OFFLINENAVI_PRIORI_CLOSE = "B007";
    public static final String BTN_OFFLINEDATA_MOREPAGE_OFFLINENAVI_PRIORI_OPEN = "B006";
    public static final String BTN_OFFLINEDATA_STORAGEINFO_BACK = "B004";
    public static final String BTN_OFFLINEDATA_STORAGEINFO_SWITCH = "B003";
    public static final String BTN_SQUARE_NAVITTS_RECORD_CUSTOM = "B001";
    public static final String PAGE_NAVITTS_FRAGMENT = "P00067";
    public static final String PAGE_NAVITTS_MYNAVITTS = "P00068";
    public static final String PAGE_NAVITTS_RECORDING_FRAGMENT = "P00069";
    public static final String PAGE_NAVITTS_SQUARE = "P00067";
    public static final String PAGE_NAVITTS_SQUARE_DOWNLOAD = "P00027";
    public static final String PAGE_NAVITTS_VOICE_PACKAGE_SAVING_DIALOG_FRAGMENT = "P00070";
    public static final String PAGE_OFFLINEDATA_ALONGWAYDOWNLOAD = "P00074";
    public static final String PAGE_OFFLINEDATA_ALONGWAYQUERY = "P00075";
    public static final String PAGE_OFFLINEDATA_CITYLIST = "P00047";
    public static final String PAGE_OFFLINEDATA_DOWNLOADEDMGR = "P00046";
    public static final String PAGE_OFFLINEDATA_MOREPAGE = "P00048";
    public static final String PAGE_OFFLINEDATA_STORAGEINFO = "P00060";

    public static void actionLogV2(String str, String str2, String str3) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cityName", str3);
            jSONObject.put("netType", aaw.a((Context) ((IExternalService) ank.a(IExternalService.class)).getApplication()));
            LogManager.actionLogV2(str, str2, jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void actionLogV2(String str, String str2) {
        LogManager.actionLogV2(str, str2, 0, 0);
    }

    public static void actionLogV2(String str, String str2, int i, int i2) {
        LogManager.actionLogV2(str, str2, i, i2);
    }

    public static void actionLogV2(String str, String str2, JSONObject jSONObject) {
        LogManager.actionLogV2(str, str2, jSONObject);
    }

    public static void actionLogV2(String str, String str2, int i, int i2, JSONObject jSONObject) {
        LogManager.actionLogV2(str, str2, i, i2, jSONObject);
    }

    public static void actionLog(int i, int i2, JSONObject jSONObject) {
        LogManager.actionLog(i, i2, jSONObject);
    }

    public static JSONObject createJSONObj(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(str, str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject createJSONObjVoiceAction(String str, String str2, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("touchType", str);
            jSONObject.put("nameType", str2);
            jSONObject.put("enterType", str3);
            jSONObject.put("netType", str4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject createJSONObjSmallActions(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("touchType", str);
            jSONObject.put("pageType", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject createJSONObjNamePage(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", str);
            jSONObject.put("pageType", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public static JSONObject createJSONObjCancelDownloading(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("state", str);
            jSONObject.put("pageType", str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
