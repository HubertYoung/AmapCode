package com.autonavi.minimap.ajx3.modules.platform;

import android.text.TextUtils;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.falcon.AbstractModuleAos;
import java.util.HashMap;
import java.util.Map;

public class AjxModuleAos extends AbstractModuleAos {
    private static final String AOS_TS_HTTPS_URL = "ts_polling_https_url";
    private static final String AOS_TS_URL = "ts_polling_url";
    private static String DEFAULT_URL = null;
    private static final String KEY_AOS_ADIU = "$aos.adiu$";
    private static final String KEY_AOS_AWAKEN = "$aos.awaken$";
    private static final String KEY_AOS_COMMENT = "$aos.comment$";
    private static final String KEY_AOS_DRIVE = "$aos.drive$";
    private static final String KEY_AOS_LOGUPLOAD = "$aos.logUpload$";
    private static final String KEY_AOS_MAPS = "$aos.maps$";
    private static final String KEY_AOS_OSS = "$aos.oss$";
    private static final String KEY_AOS_PASSPORT = "$aos.passport$";
    private static final String KEY_AOS_SEARCH = "$aos.search$";
    private static final String KEY_AOS_SNS = "$aos.sns$";
    private static final String KEY_AOS_SYNC = "$aos.sync$";
    private static final String KEY_AOS_TRACK = "$aos.track$";
    private static final String KEY_AOS_TRAFFIC = "$aos.traffic$";
    private static final String KEY_AOS_TS = "$aos.ts$";
    private static final String KEY_AOS_URL = "$aos.url$";
    private static final String KEY_AOS_WB = "$aos.wb";
    private static final String KEY_H5_3DBANNER = "$h5.3DBanner$";
    private static final String KEY_H5_3DQA = "$h5.3DQA$";
    private static final String KEY_H5_AGREEMENT = "$h5.agreement$";
    private static final String KEY_H5_BUSLAMB = "$h5.busLamb$";
    private static final String KEY_H5_COMMENT = "$h5.comment$";
    private static final String KEY_H5_COMMENTSUCCESS = "$h5.commentSuccess$";
    private static final String KEY_H5_DIGITALDESC = "$h5.digitalDesc$";
    private static final String KEY_H5_DIGITALSHARE = "$h5.digitalShare$";
    private static final String KEY_H5_FEEDBACKBUSSTATION = "$h5.feedbackBusstation$";
    private static final String KEY_H5_FEEDBACKEXAMPLE = "$h5.feedbackExample$";
    private static final String KEY_H5_FEEDBACKHELP = "$h5.feedbackHelp$";
    private static final String KEY_H5_FEEDBACKHELPCENTER = "$h5.feedbackHelpCenter$";
    private static final String KEY_H5_FEEDBACKLIST = "$h5.feedbackList$";
    private static final String KEY_H5_FEEDBACKPHOTORULE = "$h5.feedbackPhotoRule$";
    private static final String KEY_H5_FEEDBACKSUBMIT = "$h5.feedbackSubmit$";
    private static final String KEY_H5_GREEN = "$h5.green$";
    private static final String KEY_H5_HELP = "$h5.help$";
    private static final String KEY_H5_LETTERDETAIL = "$h5.letterDetail$";
    private static final String KEY_H5_OFFLINEFAQ = "$h5.offlineFAQ$";
    private static final String KEY_H5_PRIVACY = "$h5.privacy$";
    private static final String KEY_H5_PRIVACYRIGHT = "$h5.privacyRight$";
    private static final String KEY_H5_SERVICEITEM = "$h5.serviceItem$";
    private static final String KEY_H5_THANKS = "$h5.thanks$";
    private static final String KEY_H5_USERCHECKIN = "$h5.userCheckin$";
    private static final String KEY_H5_USERCONTRIBUTION = "$h5.userContribution$";
    private static final String KEY_H5_USERLEVEL = "$h5.userLevel$";
    private static final String KEY_H5_WAIFU = "$h5.waifu$";
    private static final String KEY_H5_WZCX = "$h5.wzcx$";
    private static final Map<String, String> keyMappingMap;

    static {
        HashMap hashMap = new HashMap();
        keyMappingMap = hashMap;
        hashMap.put(KEY_AOS_ADIU, "adiu_url");
        keyMappingMap.put(KEY_AOS_AWAKEN, ConfigerHelper.H5_LOG_URL);
        keyMappingMap.put(KEY_AOS_COMMENT, "aos_ugc_comment_url");
        keyMappingMap.put(KEY_AOS_DRIVE, ConfigerHelper.DRIVE_AOS_URL_KEY);
        keyMappingMap.put(KEY_AOS_LOGUPLOAD, ConfigerHelper.LOG_URL_KEY);
        keyMappingMap.put(KEY_AOS_MAPS, ConfigerHelper.SEARCH_AOS_URL_KEY);
        keyMappingMap.put(KEY_AOS_URL, ConfigerHelper.AOS_URL_KEY);
        keyMappingMap.put(KEY_AOS_OSS, ConfigerHelper.OPERATIONAL_URL_KEY);
        keyMappingMap.put(KEY_AOS_PASSPORT, ConfigerHelper.AOS_PASSPORT_URL_KEY);
        keyMappingMap.put(KEY_AOS_SEARCH, ConfigerHelper.SEARCH_AOS_URL_KEY);
        keyMappingMap.put(KEY_AOS_SNS, ConfigerHelper.AOS_SNS_URL_KEY);
        keyMappingMap.put(KEY_AOS_SYNC, "aos_sync_url");
        keyMappingMap.put(KEY_AOS_TRACK, ConfigerHelper.LOG_URL_KEY);
        keyMappingMap.put(KEY_AOS_TRAFFIC, "aos_traffic_url");
        keyMappingMap.put(KEY_AOS_WB, ConfigerHelper.WB_URL_KEY);
        keyMappingMap.put(KEY_H5_3DBANNER, "offline_roadenlarge_3d_banner");
        keyMappingMap.put(KEY_H5_3DQA, "offline_roadenlarge_3d_qa");
        keyMappingMap.put(KEY_H5_AGREEMENT, ConfigerHelper.WZCX_AGREEMENT_URL);
        keyMappingMap.put(KEY_H5_BUSLAMB, ConfigerHelper.GONGJIAOPAIPAI_URL);
        keyMappingMap.put(KEY_H5_COMMENT, ConfigerHelper.COMMENT_CALLBACK_URL);
        keyMappingMap.put(KEY_H5_COMMENTSUCCESS, ConfigerHelper.COMMENTSUCESS_CALLBACK_URL);
        keyMappingMap.put(KEY_H5_DIGITALDESC, ConfigerHelper.RDCAMERA_PAYMENT_KNOW_MORE_RULE);
        keyMappingMap.put(KEY_H5_DIGITALSHARE, ConfigerHelper.RDCAMERA_PAYMENT_SHARING_LINK);
        keyMappingMap.put(KEY_H5_FEEDBACKBUSSTATION, "feed_tip_example");
        keyMappingMap.put(KEY_H5_FEEDBACKEXAMPLE, ConfigerHelper.FEEDBACK_ADD_POI_EXAMPLE_URL);
        keyMappingMap.put(KEY_H5_FEEDBACKHELP, "feedback_locatioN_error_tips");
        keyMappingMap.put(KEY_H5_FEEDBACKHELPCENTER, ConfigerHelper.FEEDBACK_HELPCENTER_URL);
        keyMappingMap.put(KEY_H5_FEEDBACKLIST, ConfigerHelper.FEEDBACK_LIST_URL);
        keyMappingMap.put(KEY_H5_FEEDBACKPHOTORULE, ConfigerHelper.FEED_BACK_TAKE_PICTURE_RULE);
        keyMappingMap.put(KEY_H5_FEEDBACKSUBMIT, ConfigerHelper.FEED_BACK_SUCCESS_PAGE);
        keyMappingMap.put(KEY_H5_GREEN, "activity_green_out");
        keyMappingMap.put(KEY_H5_HELP, ConfigerHelper.KEY_USER_DIRECTIONS_URL);
        keyMappingMap.put(KEY_H5_LETTERDETAIL, ConfigerHelper.SPRING_ACTIVITY);
        keyMappingMap.put(KEY_H5_OFFLINEFAQ, ConfigerHelper.OFFLINEMAP_FAQ);
        keyMappingMap.put(KEY_H5_PRIVACY, ConfigerHelper.SERVICE_AND_PRIVACY_RIGHT_URL);
        keyMappingMap.put(KEY_H5_PRIVACYRIGHT, ConfigerHelper.PRIVACY_RIGHT_URL);
        keyMappingMap.put(KEY_H5_SERVICEITEM, ConfigerHelper.NEW_SERVICE_URL);
        keyMappingMap.put(KEY_H5_THANKS, ConfigerHelper.THANKS_URL);
        keyMappingMap.put(KEY_H5_USERCHECKIN, ConfigerHelper.USER_CHECKIN_URL);
        keyMappingMap.put(KEY_H5_USERCONTRIBUTION, ConfigerHelper.CONTRIBUTION_HOST_URL);
        keyMappingMap.put(KEY_H5_USERLEVEL, ConfigerHelper.USER_LEVEL_URL);
        keyMappingMap.put(KEY_H5_WAIFU, ConfigerHelper.ROUTE_OUTSIDE_CAR_LIMIT_URL);
        keyMappingMap.put(KEY_H5_WZCX, ConfigerHelper.ILLEGAL_URL);
    }

    public AjxModuleAos(IAjxContext iAjxContext) {
        super(iAjxContext);
        DEFAULT_URL = ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_URL_KEY);
    }

    public String getHost(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String str2 = null;
        String str3 = keyMappingMap.get(str);
        if (str3 != null) {
            str2 = ConfigerHelper.getInstance().getKeyValue(str3);
        } else if (KEY_AOS_TS.equalsIgnoreCase(str)) {
            cuh cuh = (cuh) ank.a(cuh.class);
            str2 = ConfigerHelper.getInstance().getKeyValue(cuh == null || cuh.r() ? AOS_TS_HTTPS_URL : "ts_polling_url");
        } else if (str.startsWith("$aos")) {
            str2 = DEFAULT_URL;
        }
        if (!TextUtils.isEmpty(str2) && str.startsWith("$aos") && !str2.endsWith("/")) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("/");
            str2 = sb.toString();
        }
        return str2 == null ? "" : str2;
    }
}
