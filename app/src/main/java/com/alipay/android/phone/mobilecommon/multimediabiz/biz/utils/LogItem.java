package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.LogConf;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import java.util.HashMap;
import java.util.Map;

public class LogItem {
    public static final String APP_ID_MULTIMEDIA = "APMultiMedia";
    public static final String CLICKED = "clicked";
    public static final String DEFAULT_PARAM = "";
    public static final String EVENT = "event";
    public static final String KEY_AUTH_EXP = "exp";
    public static final String KEY_TIME_COST = "tc";
    public static final String MM_C01_K4_BUSINESS = "bz";
    public static final String MM_C01_K4_CO = "co";
    public static final String MM_C01_K4_EXP = "exp";
    public static final String MM_C01_K4_IT = "it";
    public static final String MM_C01_K4_MD = "md";
    public static final String MM_C01_K4_RD = "ra";
    public static final String MM_C01_K4_UNM = "unm";
    public static final String MM_C02_K4_BUSINESS = "bz";
    public static final String MM_C02_K4_EXP = "exp";
    public static final String MM_C02_K4_ID = "id";
    public static final String MM_C02_K4_LEN = "le";
    public static final String MM_C02_K4_SM = "sm";
    public static final int MM_C02_K4_SM_FILE = 0;
    public static final int MM_C02_K4_SM_SYNC = 1;
    public static final String MM_C02_K4_UNM = "unm";
    public static final String MM_C03_K4_BUSINESS = "bz";
    public static final String MM_C03_K4_EXP = "exp";
    public static final String MM_C03_K4_MD5 = "md";
    public static final String MM_C03_K4_UNM = "unm";
    public static final String MM_C03_K4_UPLOAD_TYPE = "ut";
    public static final int MM_C03_K4_UPLOAD_TYPE_NORMAL = 0;
    public static final int MM_C03_K4_UPLOAD_TYPE_RANGE = 2;
    public static final int MM_C03_K4_UPLOAD_TYPE_SLICE = 1;
    public static final String MM_C04_K4_BUSINESS = "bz";
    public static final String MM_C04_K4_EXP = "exp";
    public static final String MM_C04_K4_TYPE = "it";
    public static final String MM_C04_K4_UNM = "unm";
    public static final String MM_C04_K4_ZO = "zo";
    public static final String MM_C05_K4_BUSINESS = "bz";
    public static final String MM_C05_K4_ID = "id";
    public static final String MM_C05_K4_UNM = "unm";
    public static final String MM_C06_K4_BUSINESS = "bz";
    public static final String MM_C06_K4_EXP = "exp";
    public static final String MM_C06_K4_FI = "fi";
    public static final String MM_C06_K4_TYPE = "tp";
    public static final String MM_C06_K4_UNM = "unm";
    public static final String MM_C06_K4_ZI = "zi";
    public static final String MM_C07_K4_TYPE = "tp";
    public static final String MM_C10_K4_TYPE = "tp";
    public static final String MM_C11_K4_EXP = "exp";
    public static final String MM_C11_K4_SP = "sp";
    public static final String MM_C11_K4_ST = "st";
    public static final String MM_C12_K4_EXP = "exp";
    public static final String MM_C12_K4_ID = "ci";
    public static final String MM_C13_K4_BUSNIESS = "bz";
    public static final String MM_C13_K4_DT = "dt";
    public static final String MM_C13_K4_EXP = "exp";
    public static final String MM_C13_K4_FPS = "fps";
    public static final String MM_C13_K4_ID = "id";
    public static final String MM_C13_K4_MD5 = "md5";
    public static final String MM_C13_K4_RP = "ra";
    public static final String MM_C13_K4_STEP = "st";
    public static final String MM_C13_K4_TRACE = "ti";
    public static final String MM_C13_K4_TYPE = "tp";
    public static final String MM_C13_K4_UNM = "unm";
    public static final String MM_C14_K4_BUSINESS = "bz";
    public static final String MM_C14_K4_EXP = "exp";
    public static final String MM_C14_K4_ID = "id";
    public static final String MM_C14_K4_STEP = "st";
    public static final String MM_C14_K4_TRACE = "ti";
    public static final String MM_C14_K4_UNM = "unm";
    public static final String MM_C15_K4_C_HEIGHT = "ch";
    public static final String MM_C15_K4_C_SIZE = "cs";
    public static final String MM_C15_K4_C_WIDTH = "cw";
    public static final String MM_C15_K4_EXP = "exp";
    public static final String MM_C15_K4_HEIGHT = "h";
    public static final String MM_C15_K4_TIME = "t";
    public static final String MM_C15_K4_TYPE = "tp";
    public static final String MM_C15_K4_WIDTH = "w";
    public static final String MM_C18_K4_BT = "bt";
    public static final String MM_C18_K4_ID = "id";
    public static final String MM_C18_K4_NM = "nm";
    public static final String MM_C18_K4_VD = "vd";
    public static final String MM_C19_K4_AUDIO = "au";
    public static final String MM_C19_K4_FILE = "fi";
    public static final String MM_C19_K4_IMAGE = "im";
    public static final String MM_C19_K4_PHONE_AVAILABLE = "pa";
    public static final String MM_C19_K4_PHONE_TOTAL = "pt";
    public static final String MM_C19_K4_SD_AVAILABLE = "ca";
    public static final String MM_C19_K4_SD_TOTAL = "na";
    public static final String MM_C19_K4_VIDEO = "vi";
    public static final String MM_C20_K4_EXP = "exp";
    public static final String MM_C20_K4_URI = "uri";
    public static final String MM_C21_K4_BV = "bv";
    public static final String MM_C21_K4_ERR = "err";
    public static final String MM_C21_K4_LS = "ls";
    public static final String MM_C21_K4_ST = "st";
    public static final String MM_C21_K4_TI = "ti";
    public static final String MM_C21_K4_TP = "tp";
    public static final String MM_C21_K4_URL = "url";
    public static final String MM_C21_ST_LI_BUF = "li_buf";
    public static final String MM_C21_ST_LI_E = "li_e";
    public static final String MM_C21_ST_LI_ERR = "li_err";
    public static final String MM_C21_ST_LI_S = "li_s";
    public static final String MM_C21_ST_LI_TO = "li_to";
    public static final String MM_C21_ST_RE_E = "re_e";
    public static final String MM_C21_ST_RE_ERR = "re_err";
    public static final String MM_C21_ST_RE_S = "re_s";
    public static final String MM_C21_ST_RE_TO = "re_to";
    public static final String MM_C21_ST_RE_TO_RTMP = "re_to_rtmp";
    public static final String MM_C21_ST_VO_BUF = "vo_buf";
    public static final String MM_C21_ST_VO_E = "vo_e";
    public static final String MM_C21_ST_VO_ERR = "vo_err";
    public static final String MM_C21_ST_VO_S = "vo_s";
    public static final String MM_C21_ST_VO_TO = "vo_to";
    public static final String MM_C21_TP_LI = "li";
    public static final String MM_C21_TP_RE = "re";
    public static final String MM_C21_TP_REH = "reh";
    public static final String MM_C21_TP_VO = "vo";
    public static final String MM_C22_K4_BEAUTY_VAL = "bv";
    public static final String MM_C22_K4_MODE = "mo";
    public static final String MM_C22_K4_OPEN = "op";
    public static final String MM_C22_K4_TYPE = "tp";
    public static final String MM_C23_K4_BIZ_ID = "bi";
    public static final String MM_C23_K4_DURATION = "le";
    public static final String MM_C23_K4_ENCODE_ERR = "ee";
    public static final String MM_C23_K4_ENCODE_TIME = "et";
    public static final String MM_C23_K4_ID = "id";
    public static final String MM_C23_K4_IS_CANCEL = "ic";
    public static final String MM_C23_K4_PREPARE_TIME = "pt";
    public static final String MM_C23_K4_TRACE_ID = "ti";
    public static final String MM_C24_K4_BIZ_ID = "bi";
    public static final String MM_C24_K4_DECODE_ERR = "de";
    public static final String MM_C24_K4_DECODE_TIME = "dt";
    public static final String MM_C24_K4_TRACE_ID = "ti";
    public static final String MM_C25_K4_LOADING_COUNT_DISK = "lcd";
    public static final String MM_C25_K4_LOADING_COUNT_MEM = "lcm";
    public static final String MM_C25_K4_LOADING_COUNT_NONE = "lcn";
    public static final String MM_C25_K4_LOADING_TIME_DISK = "ltd";
    public static final String MM_C25_K4_LOADING_TIME_MEM = "ltm";
    public static final String MM_C25_K4_LOADING_TIME_NONE = "ltn";
    public static final String MM_C43_K4_BUFFER_TIME = "bt";
    public static final String MM_C43_K4_CAMERA_FPS = "cf";
    public static final String MM_C43_K4_CAMERA_SURFACE_READY_TIME = "cst";
    public static final String MM_C43_K4_CAMERA_TIME = "ct";
    public static final String MM_C43_K4_ENCODE_METHOD = "em";
    public static final String MM_C43_K4_ENCODE_TIME = "et";
    public static final String MM_C43_K4_FINISH_TIME = "ft";
    public static final String MM_C43_K4_PREVIEW_TIME = "pt";
    public static final String MM_C43_K4_START_TIME = "st";
    public static final String MM_C43_K4_VIDEO_FPS = "vf";
    public static final String MM_C44_K4_BUFFER_TIME = "bt";
    public static final String MM_C44_K4_DECODE_TIME = "dt";
    public static final String MM_C44_K4_GL_TIME = "gt";
    public static final String MM_C44_K4_PREPARE_TIME = "pt";
    public static final String MM_C44_K4_RENDER_TIME = "rt";
    public static final String MM_C45_K4_LOADING_COUNT_DISK_T = "tlcd";
    public static final String MM_C45_K4_LOADING_COUNT_DISK_V = "vlcd";
    public static final String MM_C45_K4_LOADING_COUNT_MEM_T = "tlcm";
    public static final String MM_C45_K4_LOADING_COUNT_MEM_V = "vlcm";
    public static final String MM_C45_K4_LOADING_COUNT_NONE_T = "tlcn";
    public static final String MM_C45_K4_LOADING_COUNT_NONE_V = "vlcn";
    public static final String MM_C45_K4_LOADING_TIME_DISK_T = "tltd";
    public static final String MM_C45_K4_LOADING_TIME_DISK_V = "vltd";
    public static final String MM_C45_K4_LOADING_TIME_MEM_T = "tltm";
    public static final String MM_C45_K4_LOADING_TIME_MEM_V = "vltm";
    public static final String MM_C45_K4_LOADING_TIME_NONE_T = "tltn";
    public static final String MM_C45_K4_LOADING_TIME_NONE_V = "vltn";
    public static final String MM_K4_FILEID = "fi";
    public static final String MM_K4_HOST = "uh";
    public static final String MM_K4_NO_NETWORK = "nnt";
    public static final String MM_K4_TRACE = "ti";
    public static final String SUCCESS = "0";
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private Map<String, String> g;
    private Integer h = null;
    private String i = null;

    public LogItem(String caseId, String behaviorId, String seedId, String extParam1, String extParam2, String extParam3) {
        this.a = caseId;
        this.b = behaviorId;
        this.c = seedId;
        this.d = extParam1;
        this.e = extParam2;
        this.f = extParam3;
    }

    public void addExtParam(String key, String value) {
        if (this.g == null) {
            this.g = new HashMap();
        }
        this.g.put(key, value);
    }

    public void addExtParams(Map<String, String> extParams) {
        if (this.g == null) {
            this.g = new HashMap();
        }
        if (extParams != null) {
            this.g.putAll(extParams);
        }
    }

    public void setBizPro(String bizPro) {
        this.i = bizPro;
    }

    public void setLogLevel(Integer level) {
        this.h = level;
    }

    public void log(LogItem logItem) {
        if (logItem != null) {
            LogConf conf = ConfigManager.getInstance().getCommonConfigItem().logConf;
            if (conf == null || !CompareUtils.arrayContains(logItem.a, conf.logBlacklist)) {
                Behavor behavor = new Behavor();
                behavor.setAppID("APMultiMedia");
                behavor.setUserCaseID(logItem.a);
                behavor.setSeedID(logItem.c);
                behavor.setParam1(logItem.d);
                behavor.setParam2(logItem.e);
                behavor.setParam3(logItem.f);
                fillBasicBehavor(behavor);
                if (logItem.g != null) {
                    for (String key : logItem.g.keySet()) {
                        behavor.addExtParam(key, logItem.g.get(key));
                    }
                }
                if ("clicked".equals(logItem.b)) {
                    LoggerFactory.getBehavorLogger().click(behavor);
                } else {
                    LoggerFactory.getBehavorLogger().event("", behavor);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void fillBasicBehavor(Behavor behavor) {
        if (!TextUtils.isEmpty(this.i)) {
            behavor.setBehaviourPro(this.i);
        }
        if (this.h != null) {
            behavor.setLoggerLevel(this.h.intValue());
        }
    }

    public String getExtParams() {
        if (this.g == null || this.g.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(this.g);
        return sb.toString();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("caseId:" + this.a + ",");
        sb.append("behaviorID:" + this.b + ",");
        sb.append("seedId:" + this.c + ",");
        sb.append("extParam1:" + this.d + ",");
        sb.append("extParam2:" + this.e + ",");
        sb.append("extParam3:" + this.f + ",");
        sb.append("extParams:" + this.g);
        return sb.toString();
    }
}
