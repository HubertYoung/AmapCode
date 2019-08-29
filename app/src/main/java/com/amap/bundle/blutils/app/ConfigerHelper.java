package com.amap.bundle.blutils.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.blutils.PathManager;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ConfigerHelper {
    public static final String ACCS_APPKEY = "ACCS_APPKEY";
    public static final String ACCS_MODE = "ACCS_MODE";
    public static final String ACTIVITIES_URL = "activities_url";
    public static final String AETRAFFIC_KEY = "aetraffic";
    public static final String ALIBAB_SOFT_RECOMMEND = "alibaba_recommend";
    public static final String ALIPAY_ENV_ONLINE = "online";
    public static final String AOS_PASSPORT_URL_KEY = "aos_passport_url";
    public static final String AOS_SNS_URL_KEY = "aos_sns_url";
    public static final String AOS_TS_POLLING_URL_KEY = "ts_polling_url";
    public static final String AOS_UGC_COMMENT_URL = "aos_ugc_comment_url";
    public static final String AOS_UGC_COMMENT_URL_KEY = "aos_ugc_comment_url";
    public static final String AOS_URL_KEY = "aos_url";
    public static final float BUSLINE_ROUTE_WIDTH = 2.5f;
    public static final int BUS_ROUTE_WIDTH = 4;
    public static final int BUS_WALK_WIDTH = 3;
    public static final int CAR_ROUTE_COLOR = -585594113;
    public static final int CAR_ROUTE_COMMUTE_WIDTH = 5;
    public static final int CAR_ROUTE_DOTT_COLOR = -1;
    public static final int CAR_ROUTE_WIDTH = 4;
    public static final String CLOUD_SYNC_FILE_LOG = "cloud_sync_file_log";
    public static final String COMMENTSUCESS_CALLBACK_URL = "commentSuccess_callback_url";
    public static final String COMMENT_CALLBACK_URL = "comment_callback_url";
    private static final String CONFIGER_FILE;
    public static final String CONF_DIB_KEY = "dib";
    public static final String CONTRIBUTION_HOST_URL = "contribution_host_url";
    public static final String CUSTOM_ID_KEY = "CustomID";
    public static final String DIG_DETAIL_URL = "dig_detail_url";
    public static final String DIG_LOOK_TREASURE_URL = "dig_lookup_treasure_url";
    public static final String DIG_SHARE_URL = "dig_share_url";
    public static final String DRIVE_AOS_URL_KEY = "drive_aos_url";
    public static final String EASY_DRIVING_URL = "http://h5.edaijia.cn/amap/";
    public static final String ENVIRONMENT_GRID_URL = "environment_grid_url";
    public static final int EXTBUS_ROUTE_WIDTH = 3;
    public static final String E_JIAJIE_URL = "http://admin.1jiajie.com/gaode/index.php?online=1";
    public static final String FEEDBACK_ADD_POI_EXAMPLE_URL = "feedback_add_poi_example";
    public static final String FEEDBACK_HELPCENTER_URL = "feedback_helpcenter_url";
    public static final String FEEDBACK_LIST_URL = "feedback_list_url";
    private static final String FEEDBACK_LOCATION_ERROR_TIPS = "feedback_locatioN_error_tips";
    public static final String FEED_BACK_SUCCESS_PAGE = "feedback_commit_succes";
    public static final String FEED_BACK_TAKE_PICTURE_RULE = "feedback_takepicture_rule";
    public static final String FILE_LOG = "file_log";
    public static final int FOOT_ROUTE_WIDTH = 4;
    public static final String GOLDCOIN_URL = "goldcoin_url";
    public static final String GONGJIAOPAIPAI_URL = "gongjiaopaipai_url";
    public static final String H5_LOG_URL = "h5_log_url";
    public static final String ILLEGAL_URL = "illegal_url";
    public static final String JS_AUTH_ENABLE = "js_auth_enable";
    public static final String KEY_USER_DIRECTIONS_URL = "user_directions_url";
    public static final String LOAD_POI_PAGE_FROM_INTERNET = "load_poi_page_from_internet";
    public static final String LOG_IN_ALL_URL = "loginall_url";
    public static final String LOG_URL_KEY = "log_url";
    public static final String LOTUSPOOL_UPLOAD_URL = "lotuspool_upload_url";
    public static final String MAP_NET_CONDITION_KEY = "map_net_condition";
    private static final String MM = "mmfilelog";
    public static final String MOTOR_TBT_ACCOUNT = "motor_tbt_account";
    public static final String MOTOR_TBT_PASSWORD = "motor_tbt_password";
    public static final String MY_AWARD_TEST = "my_award_test";
    public static final String NET_CONDITION_KEY = "net_condition";
    public static final String NEW_SERVICE_URL = "user_inclusive_url";
    public static final String NEW_YEAR_ACTIVITY_HONGBAO_URL = "newyear_activity_hongbao_url";
    public static final String OFFLINEMAP_FAQ = "offlinemap_faq";
    public static final String OFFLINE_AOS_URL_KEY = "offline_aos_url";
    public static final String OPERATIONAL_URL_KEY = "operational_url";
    public static final String PRIVACY_RIGHT_URL = "privacy_right_url";
    public static final String PRODUCT_ID_KEY = "ProductID";
    public static final String PUSH_KEY = "PUSH_KEY";
    public static final String PUSH_SECRET = "PUSH_SECRET";
    public static final String RDCAMERA_PAYMENT_KNOW_MORE_ACTIVITIES = "rdcamera_payment_know_more_activities";
    public static final String RDCAMERA_PAYMENT_KNOW_MORE_RULE = "rdcamera_payment_know_more_rule";
    public static final String RDCAMERA_PAYMENT_SHARING_LINK = "rdcamera_payment_sharing_link";
    public static final String REAL_SCENE_ACTIVITY = "real_scene_activity";
    public static final String RECOMMEND_SOFT_KEY = "recommend_soft";
    public static final String REDIRECT = "redirect_url";
    public static final String REDIRECT_URL = "http://passport.amap.com/sina/callback.php";
    public static final String ROUTE_OUTSIDE_CAR_LIMIT_URL = "outside_car_limit_url";
    public static final String SEARCH_AOS_URL_KEY = "search_aos_url";
    public static final String SEARCH_API_VERSION = "SearchApiVersion";
    public static final String SEND_CAR = "send_car";
    public static final String SERVICE_AND_PRIVACY_RIGHT_URL = "service_and_privacy_right_url";
    public static final String SOFT_RECOMMEND = "Recommend";
    public static final String SOFT_UPDATE = "Update";
    public static final String SPRING_ACTIVITY = "spring_activity";
    public static final String SUBWAY_URL = "subway_url";
    public static final String TBT_ACCOUNT = "tbt_account";
    public static final String TBT_PASSWORD = "tbt_password";
    private static final String TEMP_FILE = "custom.txt";
    public static final String TERMINAL_ID_KEY = "TerminalID";
    public static final String THANKS_URL = "thanks_url";
    public static final String TRAFFIC_EVENT_ENGINE_URL = "traffic_event_engine_url";
    public static final String TRUCK_STATUTE_URL = "truck_statute_url";
    public static final String TTS_COMMON_KEY = "tts_common";
    public static final String TTS_LZL_KEY = "tts_lzl";
    public static final String TTS_XIAOYAN_KEY = "tts_xiaoyan";
    public static final String UNINSTALL_URL = "uninstall_url";
    public static final String USER_CENTER_URL = "user_center_url";
    public static final String USER_CHECKIN_URL = "user_checkin_url";
    public static final String USER_LEVEL_URL = "user_level_url";
    private static String VERIFYUSER_FILE = "verifyuser";
    public static final String WAKE_UP_URL = "wake_up_url";
    public static final String WB_URL_KEY = "wburl";
    public static final String WEB_DEBUG = "web_debug";
    public static final String WEB_TEMPLATE_DOMAIN = "web_template_domain";
    public static final String WZCX_AGREEMENT_URL = "wzcx_agreement_url";
    public static final String alipay_env = "alipay_env";
    public static final String alipay_env_new = "alipay_env_new";
    private static ConfigerHelper instance;
    private Context appContext = AMapAppGlobal.getApplication();
    private String basePath = PathManager.a().b();
    HashMap<String, String> customList = new HashMap<>();
    private boolean isLoadPoiPageFromInternet = false;
    private boolean isOpenCloudSyncFilelog = false;
    private boolean isOpenFilelog = false;
    HashMap<String, String> mConfStrList = new HashMap<>();

    static {
        int i = bno.d;
        if (i == 1) {
            CONFIGER_FILE = "internal_configer.data";
        } else if (i == 2) {
            CONFIGER_FILE = "pre_configer.data";
        } else {
            CONFIGER_FILE = "amap_configer.data";
        }
    }

    public static ConfigerHelper getInstance() {
        if (instance == null) {
            synchronized (ConfigerHelper.class) {
                try {
                    if (instance == null) {
                        instance = new ConfigerHelper();
                    }
                }
            }
        }
        return instance;
    }

    private ConfigerHelper() {
        readConfiger();
        getDefaultCustomKey();
        readPoipageLoadConfig();
        readFileLog();
        readCloudSyncFileLog();
    }

    private void readFileLog() {
        this.isOpenFilelog = Boolean.parseBoolean(this.mConfStrList.get(FILE_LOG));
    }

    public boolean isOpenFilelog() {
        return this.isOpenFilelog;
    }

    private void readCloudSyncFileLog() {
        this.isOpenCloudSyncFilelog = Boolean.parseBoolean(this.mConfStrList.get(CLOUD_SYNC_FILE_LOG));
    }

    private void readPoipageLoadConfig() {
        this.isLoadPoiPageFromInternet = Boolean.parseBoolean(this.mConfStrList.get(LOAD_POI_PAGE_FROM_INTERNET));
    }

    public boolean isLoadPoiPageFromInternet() {
        return this.isLoadPoiPageFromInternet;
    }

    public String getKeyValue(String str) {
        if (str.equals(CUSTOM_ID_KEY)) {
            String str2 = this.customList.get(CUSTOM_ID_KEY);
            if (str2 != null) {
                return str2;
            }
        }
        String str3 = this.mConfStrList.get(str);
        if (str3 == null) {
            str3 = "";
        }
        return str3;
    }

    public String getActivitiesUrl() {
        return getKeyValue(ACTIVITIES_URL);
    }

    public void CopyConfigerFileToSdCard() {
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        sb.append(this.basePath);
        sb.append("/autonavi/verifyconfiger/");
        sb.append(VERIFYUSER_FILE);
        String sb2 = sb.toString();
        File file = new File(sb2);
        if (!file.exists()) {
            try {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.basePath);
                sb3.append("/autonavi/verifyconfiger/");
                File file2 = new File(sb3.toString());
                if (!file2.exists()) {
                    file2.mkdir();
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedWriter bufferedWriter = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(this.appContext.getResources().getAssets().open(CONFIGER_FILE), "UTF-8"));
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(sb2));
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            bufferedWriter2.write(readLine);
                            bufferedWriter2.newLine();
                        } catch (Exception e2) {
                            e = e2;
                            bufferedWriter = bufferedWriter2;
                            try {
                                e.printStackTrace();
                                ahe.a((Closeable) bufferedWriter);
                                ahe.a((Closeable) bufferedReader);
                            } catch (Throwable th) {
                                th = th;
                                ahe.a((Closeable) bufferedWriter);
                                ahe.a((Closeable) bufferedReader);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedWriter = bufferedWriter2;
                            ahe.a((Closeable) bufferedWriter);
                            ahe.a((Closeable) bufferedReader);
                            throw th;
                        }
                    }
                    bufferedWriter2.flush();
                    ahe.a((Closeable) bufferedWriter2);
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    ahe.a((Closeable) bufferedWriter);
                    ahe.a((Closeable) bufferedReader);
                }
            } catch (Exception e4) {
                e = e4;
                bufferedReader = null;
                e.printStackTrace();
                ahe.a((Closeable) bufferedWriter);
                ahe.a((Closeable) bufferedReader);
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
                ahe.a((Closeable) bufferedWriter);
                ahe.a((Closeable) bufferedReader);
                throw th;
            }
            ahe.a((Closeable) bufferedReader);
        }
    }

    public InputStream GetConfigerFile() {
        InputStream inputStream;
        GetConfigerFileName();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(this.basePath);
            sb.append("/autonavi/verifyconfiger/");
            sb.append(VERIFYUSER_FILE);
            File file = new File(sb.toString());
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            } else {
                inputStream = this.appContext.getResources().getAssets().open(CONFIGER_FILE);
            }
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return this.appContext.getResources().getAssets().open(CONFIGER_FILE);
            } catch (IOException e2) {
                e2.printStackTrace();
                inputStream = null;
            }
        }
    }

    public String GetConfigerFileName() {
        try {
            PackageInfo packageInfo = AMapAppGlobal.getApplication().getPackageManager().getPackageInfo(AMapAppGlobal.getApplication().getPackageName(), 0);
            if (packageInfo != null) {
                VERIFYUSER_FILE = "verifyuser";
                String replace = packageInfo.versionName.replace(Token.SEPARATOR, "_").replace(".", "_");
                int i = packageInfo.versionCode;
                StringBuilder sb = new StringBuilder();
                sb.append(VERIFYUSER_FILE);
                sb.append("_");
                sb.append(replace);
                sb.append("_");
                sb.append(i);
                sb.append(".data");
                VERIFYUSER_FILE = sb.toString();
            }
        } catch (NameNotFoundException e) {
            kf.a((Throwable) e);
        }
        return VERIFYUSER_FILE;
    }

    public String GetConfigerFilePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.basePath);
        sb.append("/autonavi/verifyconfiger/");
        sb.append(VERIFYUSER_FILE);
        return sb.toString();
    }

    public boolean IsConfigerFileExist() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(this.basePath);
            sb.append("/autonavi/verifyconfiger/");
            sb.append(VERIFYUSER_FILE);
            if (new File(sb.toString()).exists()) {
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    private void readConfiger() {
        try {
            InputStream GetConfigerFile = GetConfigerFile();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(GetConfigerFile, "UTF-8"));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    GetConfigerFile.close();
                    return;
                } else if (readLine != null && readLine.length() > 0 && !readLine.startsWith(MetaRecord.LOG_SEPARATOR)) {
                    String[] split = readLine.split("=");
                    if (split != null && split.length >= 2) {
                        String str = split[0];
                        StringBuffer stringBuffer = new StringBuffer(split[1]);
                        for (int i = 0; i < split.length - 2; i++) {
                            stringBuffer.append("=");
                            stringBuffer.append(split[i + 2]);
                        }
                        String stringBuffer2 = stringBuffer.toString();
                        if (!(str == null || stringBuffer2 == null)) {
                            String trim = str.trim();
                            String trim2 = stringBuffer2.toString().trim();
                            if (bno.e && !TextUtils.isEmpty(trim2) && trim2.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
                                trim2 = trim2.replace(AjxHttpLoader.DOMAIN_HTTPS, AjxHttpLoader.DOMAIN_HTTP);
                            }
                            if (trim.length() > 0 && trim2.length() > 0) {
                                this.mConfStrList.put(trim, trim2);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNetCondition() {
        return this.mConfStrList.get(NET_CONDITION_KEY);
    }

    public String getMapNetCondition() {
        return this.mConfStrList.get(MAP_NET_CONDITION_KEY);
    }

    public String getAccsMode() {
        return this.mConfStrList.get(ACCS_MODE);
    }

    public String getAccsAppkey() {
        return this.mConfStrList.get(ACCS_APPKEY);
    }

    public String getShareMsgUrl() {
        return this.mConfStrList.get(WB_URL_KEY);
    }

    public boolean getWebDebug() {
        String str = this.mConfStrList.get(WEB_DEBUG);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Boolean.parseBoolean(str);
    }

    public boolean getMyAwardTest() {
        String str = this.mConfStrList.get(MY_AWARD_TEST);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Boolean.parseBoolean(str);
    }

    public String getGoldcoinUrl() {
        return this.mConfStrList.get(GOLDCOIN_URL);
    }

    public String getUninstallUrl() {
        return this.mConfStrList.get(UNINSTALL_URL);
    }

    public String getRdcameraPaymentKnowMoreRuleUrl() {
        return this.mConfStrList.get(RDCAMERA_PAYMENT_KNOW_MORE_RULE);
    }

    public String getRdcameraPaymentKnowMoreActivitiesUrl() {
        return this.mConfStrList.get(RDCAMERA_PAYMENT_KNOW_MORE_ACTIVITIES);
    }

    public String getRdcameraPaymentSharingLinkUrl() {
        return this.mConfStrList.get(RDCAMERA_PAYMENT_SHARING_LINK);
    }

    public String getServiceItemUrl() {
        return this.mConfStrList.get(NEW_SERVICE_URL);
    }

    public String getPrivacyRightUrl() {
        return this.mConfStrList.get(PRIVACY_RIGHT_URL);
    }

    public String getIllegalUrl() {
        return getKeyValue(ILLEGAL_URL);
    }

    public String getSubwayUrl() {
        return getKeyValue(SUBWAY_URL);
    }

    public String getChannel() {
        return getKeyValue(CUSTOM_ID_KEY);
    }

    public String getTrafficEventEngineUrl() {
        return getKeyValue(TRAFFIC_EVENT_ENGINE_URL);
    }

    private void getDefaultCustomKey() {
        try {
            FileInputStream openFileInput = this.appContext.openFileInput(TEMP_FILE);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    openFileInput.close();
                    return;
                } else if (readLine != null && readLine.length() > 0 && !readLine.startsWith(MetaRecord.LOG_SEPARATOR)) {
                    String[] split = readLine.split("=");
                    if (split != null && split.length >= 2) {
                        String str = split[0];
                        StringBuffer stringBuffer = new StringBuffer(split[1]);
                        for (int i = 0; i < split.length - 2; i++) {
                            stringBuffer.append("=");
                            stringBuffer.append(split[i + 2]);
                        }
                        String stringBuffer2 = stringBuffer.toString();
                        if (!(str == null || stringBuffer2 == null)) {
                            String trim = str.trim();
                            String trim2 = stringBuffer2.trim();
                            if (trim.length() > 0 && trim2.length() > 0) {
                                this.customList.put(trim, trim2);
                            }
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    public boolean getMMLogConfiger() {
        if (this.mConfStrList.get(MM) != null) {
            return Boolean.parseBoolean(this.mConfStrList.get(MM));
        }
        return false;
    }

    public boolean isJsAuthEnable() {
        return !"false".equalsIgnoreCase(getKeyValue(JS_AUTH_ENABLE));
    }

    public boolean getAlipayTest() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences.toString()).sharedPrefs().getBoolean(alipay_env, false);
    }

    public String getAlipayEnv() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences.toString()).sharedPrefs().getString(alipay_env_new, "online");
    }

    public String getWebTemplateDomain() {
        return getKeyValue(WEB_TEMPLATE_DOMAIN);
    }

    public String getFeedBackTakePictureRule() {
        return getKeyValue(FEED_BACK_TAKE_PICTURE_RULE);
    }

    public String getFeedbackAddPoiExampleUrl() {
        return this.mConfStrList.get(FEEDBACK_ADD_POI_EXAMPLE_URL);
    }

    public String getFeedBackSuccessPage() {
        return getKeyValue(FEED_BACK_SUCCESS_PAGE);
    }

    public String getFeedbackLocationErrorTips() {
        return getKeyValue(FEEDBACK_LOCATION_ERROR_TIPS);
    }

    public String getFeedbackListUrl() {
        return this.mConfStrList.get(FEEDBACK_LIST_URL);
    }

    public String getFeedbackHelpcenterUrl() {
        return this.mConfStrList.get(FEEDBACK_HELPCENTER_URL);
    }

    public String getThanksUrl() {
        return this.mConfStrList.get(THANKS_URL);
    }
}
