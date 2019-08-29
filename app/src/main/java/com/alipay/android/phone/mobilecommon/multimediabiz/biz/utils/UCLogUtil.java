package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.data.APConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.sdk.app.statistic.c;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UCLogUtil {
    public static final int HOST_NEED_ADD_TIME = 5000;
    public static String KEY_MD5 = "md5";
    private static int a = 20;
    private static Random b = new Random();
    private static HashMap<String, LogUnAvailbleItem> c = null;
    private static SharedPreferences d;
    private static Editor e;
    private static DecimalFormat f = new DecimalFormat(DiskFormatter.FORMAT);

    private static int a() {
        return b.nextInt(99) + 1;
    }

    public static void UC_MM_C01(String ret, long size, int time, int rapid, int compress, int type, String md5, String exp, String traceId, String business) {
        if (ret == null) {
            ret = "";
        }
        LogItem logItem = new LogItem("UC-MM-C01", "clicked", "UploadImage", ret, String.valueOf(size), String.valueOf(time));
        logItem.addExtParam("ra", String.valueOf(rapid));
        logItem.addExtParam(LogItem.MM_C01_K4_CO, String.valueOf(compress));
        logItem.addExtParam("it", String.valueOf(type));
        if ((ret.startsWith("s") || !"0".equalsIgnoreCase(ret)) && !TextUtils.isEmpty(md5)) {
            logItem.addExtParam("md", md5);
        }
        if (!TextUtils.isEmpty(exp)) {
            logItem.addExtParam("exp", exp);
        }
        if (!TextUtils.isEmpty(traceId)) {
            logItem.addExtParam("ti", traceId);
        }
        logItem.addExtParam("unm", f(business));
        if (business == null) {
            business = "";
        }
        logItem.addExtParam("bz", business);
        logItem.log(logItem);
        UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_UPLOAD, ret, "UploadImage", ret, String.valueOf(size), String.valueOf(time), logItem.getExtParams());
    }

    public static void UC_MM_C02(int ret, long size, int time, long timeLen, String traceId, String exp, String business, String cloudId, boolean noNetwork) {
        UC_MM_C02(ret, size, time, timeLen, traceId, exp, 0, business, cloudId, noNetwork);
    }

    public static void UC_MM_C02(int ret, long size, int time, long timeLen, String traceId, String exp, int uploadType, String business, String cloudId, boolean noNetwork) {
        String valueOf;
        if (ret == 0) {
            valueOf = "0";
        } else {
            valueOf = String.valueOf(ret);
        }
        LogItem logItem = new LogItem("UC-MM-C02", "clicked", "UploadVoice", valueOf, String.valueOf(size), String.valueOf(time));
        logItem.addExtParam("le", String.valueOf(timeLen));
        if (!TextUtils.isEmpty(traceId)) {
            logItem.addExtParam("ti", traceId);
        }
        if (!TextUtils.isEmpty(exp)) {
            logItem.addExtParam("exp", exp);
        }
        logItem.addExtParam(LogItem.MM_C02_K4_SM, String.valueOf(uploadType));
        if (cloudId == null) {
            cloudId = "";
        }
        logItem.addExtParam("id", cloudId);
        String unm = "1";
        if (uploadType == 0) {
            unm = f(business);
        }
        logItem.addExtParam(LogItem.MM_K4_NO_NETWORK, noNetwork ? "1" : "0");
        logItem.addExtParam("unm", unm);
        if (business == null) {
            business = "";
        }
        logItem.addExtParam("bz", business);
        if (uploadType == 0) {
            logItem.log(logItem);
        }
        UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_UPLOAD, String.valueOf(ret), "UploadVoice", String.valueOf(ret), String.valueOf(size), String.valueOf(time), logItem.getExtParams());
    }

    public static void UC_MM_C03(String ret, long size, int time, String exp, String traceId, String md5, String business, boolean noNetwork) {
        UC_MM_C03(ret, size, time, 0, exp, traceId, md5, business, noNetwork);
    }

    public static void UC_MM_C03(String ret, long size, int time, int type, String exp, String traceId, String md5, String business, boolean noNetwork) {
        if (ret == null) {
            ret = "";
        }
        LogItem logItem = new LogItem("UC-MM-C03", "clicked", "UploadFile", ret, String.valueOf(size), String.valueOf(time));
        logItem.addExtParam(LogItem.MM_C03_K4_UPLOAD_TYPE, String.valueOf(type));
        if (!TextUtils.isEmpty(traceId)) {
            logItem.addExtParam("ti", traceId);
        }
        if (!TextUtils.isEmpty(exp)) {
            logItem.addExtParam("exp", exp);
        }
        if (!TextUtils.isEmpty(md5)) {
            logItem.addExtParam("md", md5);
        }
        logItem.addExtParam("unm", f(business));
        if (business == null) {
            business = APConstants.DEFAULT_BUSINESS;
        }
        logItem.addExtParam("bz", business);
        logItem.addExtParam(LogItem.MM_K4_NO_NETWORK, noNetwork ? "1" : "0");
        logItem.log(logItem);
        UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_UPLOAD, ret, "UploadFile", ret, String.valueOf(size), String.valueOf(time), logItem.getExtParams());
    }

    public static void UC_MM_C04(String ret, long size, int time, String zoom, int type, boolean bLocal, String exp, String traceId, String business, boolean noNetwork) {
        UC_MM_C04(ret, size, time, zoom, type, bLocal, exp, traceId, null, business, noNetwork);
    }

    public static void UC_MM_C04(String ret, long size, int time, String zoom, int type, boolean bLocal, String exp, String traceId, String fileId, String business, boolean noNetwork) {
        UC_MM_C04(ret, size, time, zoom, type, bLocal, exp, traceId, fileId, null, business, noNetwork, null);
    }

    public static void UC_MM_C04(String ret, long size, int time, String zoom, int type, boolean bLocal, String exp, String traceId, String fileId, String uriHost, String business, boolean noNetwork, String unm) {
        if (ret == null) {
            ret = "";
        }
        if ("original".equalsIgnoreCase(zoom)) {
            zoom = "0x0";
        }
        LogItem logItem = new LogItem("UC-MM-C04", "clicked", "DownloadImage", ret, String.valueOf(size), String.valueOf(time));
        logItem.addExtParam(LogItem.MM_C04_K4_ZO, zoom);
        logItem.addExtParam("it", String.valueOf(type));
        a(logItem, (String) "exp", exp);
        a(logItem, (String) "ti", traceId);
        a(logItem, (String) "fi", fileId);
        a(logItem, (String) LogItem.MM_K4_HOST, uriHost);
        String lastUnm = unm;
        if (TextUtils.isEmpty(unm)) {
            lastUnm = (TextUtils.isEmpty(zoom) || !zoom.equalsIgnoreCase("url")) ? e(business) : "1";
        }
        logItem.addExtParam("unm", lastUnm);
        if (business == null) {
            business = APConstants.DEFAULT_BUSINESS;
        }
        logItem.addExtParam("bz", business);
        logItem.addExtParam(LogItem.MM_K4_NO_NETWORK, noNetwork ? "1" : "0");
        logItem.log(logItem);
        if (!noNetwork) {
            UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_DOWNLOAD, ret, "DownloadImage", ret, String.valueOf(size), String.valueOf(time), logItem.getExtParams());
        }
    }

    private static void a(LogItem logItem, String key, String val) {
        if (!TextUtils.isEmpty(val)) {
            logItem.addExtParam(key, val);
        }
    }

    public static void UC_MM_C05(int ret, long size, int time, String traceId, String business, String cloudId, boolean noNetwork) {
        String valueOf;
        if (ret == 0) {
            valueOf = "0";
        } else {
            valueOf = String.valueOf(ret);
        }
        LogItem logItem = new LogItem("UC-MM-C05", "clicked", "DownloadVoice", valueOf, String.valueOf(size), String.valueOf(time));
        if (!TextUtils.isEmpty(traceId)) {
            logItem.addExtParam("ti", traceId);
        }
        logItem.addExtParam("unm", e(business));
        if (business == null) {
            business = APConstants.DEFAULT_BUSINESS;
        }
        logItem.addExtParam("bz", business);
        if (cloudId == null) {
            cloudId = "";
        }
        logItem.addExtParam("id", cloudId);
        logItem.addExtParam(LogItem.MM_K4_NO_NETWORK, noNetwork ? "1" : "0");
        logItem.log(logItem);
        UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_DOWNLOAD, String.valueOf(ret), "DownloadVoice", String.valueOf(ret), String.valueOf(size), String.valueOf(time), logItem.getExtParams());
    }

    public static void UC_MM_C06(String ret, long size, int time, int isZip, String exp, String traceId, String fileId, boolean isHttp, String business, boolean noNetwork, String unm) {
        if (ret == null) {
            ret = "";
        }
        LogItem logItem = new LogItem("UC-MM-C06", "clicked", "DownloadFile", ret, String.valueOf(size), String.valueOf(time));
        logItem.addExtParam(LogItem.MM_C06_K4_ZI, String.valueOf(isZip));
        if (!TextUtils.isEmpty(exp)) {
            logItem.addExtParam("exp", exp);
        }
        if (!TextUtils.isEmpty(traceId)) {
            logItem.addExtParam("ti", traceId);
        }
        if (!TextUtils.isEmpty(fileId)) {
            logItem.addExtParam("fi", fileId);
        }
        logItem.addExtParam("tp", isHttp ? "1" : "0");
        if (TextUtils.isEmpty(unm)) {
            unm = e(business);
        }
        logItem.addExtParam("unm", unm);
        if (business == null) {
            business = APConstants.DEFAULT_BUSINESS;
        }
        logItem.addExtParam("bz", business);
        logItem.addExtParam(LogItem.MM_K4_NO_NETWORK, noNetwork ? "1" : "0");
        logItem.log(logItem);
        if (!noNetwork) {
            UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_DOWNLOAD, ret, "DownloadFile", ret, String.valueOf(size), String.valueOf(time), logItem.getExtParams());
        }
    }

    public static void UC_MM_C07(int ret, long size, int time, int tp) {
        if (tp != 1 || b()) {
            LogItem logItem = new LogItem("UC-MM-C07", "clicked", "CompressImage", String.valueOf(ret), String.valueOf(size), String.valueOf(time));
            logItem.addExtParam("tp", String.valueOf(tp));
            logItem.log(logItem);
        }
    }

    public static void UC_MM_C08(int ret, long size, int time) {
    }

    public static void UC_MM_C09(int ret, long size, int time) {
    }

    public static void UC_MM_C10(int ret, int type) {
        String valueOf;
        if (ret == 0) {
            valueOf = "0";
        } else {
            valueOf = String.valueOf(ret);
        }
        LogItem logItem = new LogItem("UC-MM-C10", "event", "CleanMemory", valueOf, "", "");
        logItem.addExtParam("tp", String.valueOf(type));
        logItem.log(logItem);
    }

    public static void UC_MM_C11(int ret, String err) {
        String valueOf;
        if (ret == 0) {
            valueOf = "0";
        } else {
            valueOf = String.valueOf(ret);
        }
        LogItem logItem = new LogItem("UC-MM-C11", "clicked", "RecordVoice", valueOf, "", "");
        if (err == null) {
            err = "";
        }
        logItem.addExtParam("exp", err);
        if (ret != 0) {
            if (!"mounted".equals(Environment.getExternalStorageState())) {
                logItem.addExtParam("st", "0");
                logItem.addExtParam("sp", FileUtils.getPhoneAvailableSize());
            } else {
                logItem.addExtParam("st", "1");
                logItem.addExtParam("sp", FileUtils.getSdAvailableSize());
            }
        }
        logItem.log(logItem);
        UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_COLLECT_AR, String.valueOf(ret), "RecordVoice", String.valueOf(ret), "", "", logItem.getExtParams());
    }

    public static void UC_MM_C12(int ret, String id, String e2) {
        String valueOf;
        if (ret == 0) {
            valueOf = "0";
        } else {
            valueOf = String.valueOf(ret);
        }
        LogItem logItem = new LogItem("UC-MM-C12", "clicked", "PlayVoice", valueOf, "", "");
        if (ret == 0) {
            id = "";
        }
        logItem.addExtParam(LogItem.MM_C12_K4_ID, id);
        if (e2 == null) {
            e2 = "";
        }
        logItem.addExtParam("exp", e2);
        logItem.log(logItem);
        UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_PLAY_AD, String.valueOf(ret), "PlayVoice", String.valueOf(ret), "", "", logItem.getExtParams());
    }

    public static void UC_MM_C13(int ret, long size, int time, int fps, int duration, String id, String md5, String ti, int rap, int type, int step, String e2, String business, boolean noNetwork) {
        String valueOf;
        if (d(String.valueOf(ret))) {
            if (ret == 0) {
                valueOf = "0";
            } else {
                valueOf = String.valueOf(ret);
            }
            LogItem logItem = new LogItem("UC-MM-C13", "clicked", "UploadVideo", valueOf, String.valueOf(size), String.valueOf(time));
            logItem.addExtParam(LogItem.MM_C13_K4_FPS, String.valueOf(fps));
            logItem.addExtParam("dt", String.valueOf(duration));
            if (!TextUtils.isEmpty(id)) {
                logItem.addExtParam("id", id);
            }
            logItem.addExtParam("md5", md5);
            logItem.addExtParam("tp", String.valueOf(type));
            if (ti == null) {
                ti = "";
            }
            logItem.addExtParam("ti", ti);
            logItem.addExtParam("ra", String.valueOf(rap));
            logItem.addExtParam("st", String.valueOf(step));
            if (e2 == null) {
                e2 = "";
            }
            logItem.addExtParam("exp", e2);
            logItem.addExtParam("unm", f(business));
            if (business == null) {
                business = APConstants.DEFAULT_BUSINESS;
            }
            logItem.addExtParam("bz", business);
            logItem.addExtParam(LogItem.MM_K4_NO_NETWORK, noNetwork ? "1" : "0");
            logItem.log(logItem);
            UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_UPLOAD, String.valueOf(ret), "UploadVideo", String.valueOf(ret), String.valueOf(size), String.valueOf(time), logItem.getExtParams());
        }
    }

    public static void UC_MM_C14(int ret, long size, int time, String id, String ti, int step, String e2, String business, boolean noNetwork) {
        String valueOf;
        if (d(String.valueOf(ret))) {
            if (ret == 0) {
                valueOf = "0";
            } else {
                valueOf = String.valueOf(ret);
            }
            LogItem logItem = new LogItem("UC-MM-C14", "clicked", "DownloadVideo", valueOf, String.valueOf(size), String.valueOf(time));
            logItem.addExtParam("id", id);
            if (ti == null) {
                ti = "";
            }
            logItem.addExtParam("ti", ti);
            logItem.addExtParam("st", String.valueOf(step));
            if (e2 == null) {
                e2 = "";
            }
            logItem.addExtParam("exp", e2);
            logItem.addExtParam("unm", e(business));
            if (business == null) {
                business = APConstants.DEFAULT_BUSINESS;
            }
            logItem.addExtParam("bz", business);
            logItem.addExtParam(LogItem.MM_K4_NO_NETWORK, noNetwork ? "1" : "0");
            logItem.log(logItem);
            UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_DOWNLOAD, String.valueOf(ret), "DownloadVideo", String.valueOf(ret), String.valueOf(size), String.valueOf(time), logItem.getExtParams());
        }
    }

    public static void UC_MM_C15(int ret, long size, int time, int tp, int t, int w, int h, int cw, int ch, long cs, String e2) {
        String valueOf;
        if (ret == 0) {
            valueOf = "0";
        } else {
            valueOf = String.valueOf(ret);
        }
        LogItem logItem = new LogItem("UC-MM-C15", "clicked", "CompressVideo", valueOf, String.valueOf(size), String.valueOf(time));
        logItem.addExtParam("tp", String.valueOf(tp));
        logItem.addExtParam(LogItem.MM_C15_K4_TIME, String.valueOf(t));
        logItem.addExtParam("w", String.valueOf(w));
        logItem.addExtParam("h", String.valueOf(h));
        logItem.addExtParam(LogItem.MM_C15_K4_C_WIDTH, String.valueOf(cw));
        logItem.addExtParam(LogItem.MM_C15_K4_C_HEIGHT, String.valueOf(ch));
        logItem.addExtParam(LogItem.MM_C15_K4_C_SIZE, String.valueOf(cs));
        if (e2 == null) {
            e2 = "";
        }
        logItem.addExtParam("exp", e2);
        logItem.log(logItem);
        UC_MM_BIZ_UNAVAILBLE(LogUnAvailbleItem.SUB_CP_VD, String.valueOf(ret), "CompressVideo", String.valueOf(ret), String.valueOf(size), String.valueOf(time), logItem.getExtParams());
    }

    public static void UC_MM_C16(int ret, String errorMsg) {
        String str;
        String valueOf = String.valueOf(ret);
        if (errorMsg == null) {
            str = "";
        } else {
            str = errorMsg;
        }
        LogItem logItem = new LogItem("UC-MM-C16", "clicked", "setHintExp", valueOf, str, "");
        logItem.log(logItem);
    }

    public static void UC_MM_C17(int ret) {
        LogItem logItem = new LogItem("UC-MM-C17", "clicked", "EncoderName", String.valueOf(ret), "", "");
        logItem.log(logItem);
    }

    private static boolean b() {
        int rd = a();
        int intValue = ConfigManager.getInstance().getIntValue(ConfigConstants.LOG_SAMPLE_INTERVAL_KEY, 20);
        a = intValue;
        if (intValue > 100) {
            a = 100;
        }
        return rd < a;
    }

    public static void UC_MM_BIZ_UNAVAILBLE(String subName, String code) {
        UC_MM_BIZ_UNAVAILBLE(subName, code, "", "", "", "", "");
    }

    public static void UC_MM_BIZ_UNAVAILBLE(String subName, String code, String subtype, String ret, String size, String time, String extend) {
        if (ConfigManager.getInstance().getCommonConfigItem().logConf.uaswitch == 1) {
            a(subName, code, subtype, ret, size, time, extend);
        } else {
            a(subName, code);
        }
    }

    private static void a(String subName, String code) {
        if (c == null) {
            c = new HashMap<>();
        }
        String key = subName;
        boolean bSuccess = "0".equalsIgnoreCase(code);
        LogUnAvailbleItem item = c.get(key);
        if (item == null) {
            item = b(key);
            if (item == null) {
                item = new LogUnAvailbleItem(subName, code);
                if (!bSuccess) {
                    item.mFirstTime = System.currentTimeMillis();
                }
            }
            c.put(key, item);
        }
        if (!bSuccess) {
            item.mCode = code;
            item.mCount++;
            if (item.mFirstTime == 0) {
                item.mFirstTime = System.currentTimeMillis();
            }
            item.parseConfig(ConfigManager.getInstance().getUnAvailbleConfig(key));
            if (item.checkUnAvailble()) {
                item.reset();
                a(subName, code, (Map<String, String>) null);
            }
            a(key, item);
        } else if (item.mCount > 0) {
            item.reset();
            a(key, item);
        }
    }

    private static void a(String subName, String code, String subtype, String ret, String size, String time, String extend) {
        if (!TextUtils.isEmpty(subName) && !"0".equalsIgnoreCase(code)) {
            if (!c() || !LogUnAvailbleItem.isNonSensitive(subtype)) {
                LogUnAvailbleItem item = new LogUnAvailbleItem(subName, code);
                a(subtype, ret, size, time, extend, item);
                a(item.mSubName, item.mCode, (Map<String, String>) item.getExtra());
                return;
            }
            b(subName, code, subtype, ret, size, time, extend);
        }
    }

    private static void b(String subName, String code, String subtype, String ret, String size, String time, String extend) {
        if (!TextUtils.isEmpty(subName) && !TextUtils.isEmpty(subtype) && !"0".equalsIgnoreCase(code)) {
            if (c == null) {
                c = new HashMap<>();
            }
            try {
                synchronized (c) {
                    String key = subtype;
                    LogUnAvailbleItem item = c.get(key);
                    if (item == null) {
                        item = c(key);
                        if (item == null) {
                            item = new LogUnAvailbleItem(subName, code);
                        }
                        item.mCode = code;
                        a(subtype, ret, size, time, extend, item);
                        c.put(key, item);
                    } else {
                        item.mCode = code;
                        a(subtype, ret, size, time, extend, item);
                    }
                    item.mCount++;
                    if (item.mFirstTime == 0) {
                        item.mFirstTime = System.currentTimeMillis();
                    }
                    item.mMinTime = ConfigManager.getInstance().getCommonConfigItem().logConf.reportPeriod;
                    item.mMinCount = ConfigManager.getInstance().getCommonConfigItem().logConf.maxErrorCount;
                    if (item.checkUnAvailble()) {
                        a(item.mSubName, item.mCode, (Map<String, String>) item.getExtra());
                        c.remove(key);
                        a(key);
                        return;
                    }
                    b(key, item);
                }
            } catch (Exception e2) {
                Logger.E((String) "UCLogUtil", (Throwable) e2, (String) "reportNonSensitiveUnAvailbleBiz exp!", new Object[0]);
            }
        }
    }

    private static boolean c() {
        return ConfigManager.getInstance().getCommonConfigItem().logConf.uaNewSwitch == 1;
    }

    private static void a(String subtype, String ret, String size, String time, String extend, LogUnAvailbleItem item) {
        if (item != null) {
            item.putToExtra(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, subtype);
            item.putToExtra("result", ret);
            item.putToExtra("size", size);
            item.putToExtra("time", time);
            item.putToExtra(LogUnAvailbleItem.EXTRA_KEY_EXTEND, extend);
        }
    }

    private static void a(String subName, String code, Map<String, String> extParams) {
        try {
            Logger.D("UCLogUtil", "reportUnAvailbleBiz subName=" + subName + ";code=" + code, new Object[0]);
            LoggerFactory.getMonitorLogger().mtBizReport("BIZ_MEDIA", subName, code, extParams);
        } catch (Exception e2) {
            Logger.D("UCLogUtil", "reportUnAvailbleBiz exp=" + e2.getMessage(), new Object[0]);
        }
    }

    private static void a(String key, LogUnAvailbleItem item) {
        if (!TextUtils.isEmpty(key) && item != null) {
            Context context = AppUtils.getApplicationContext();
            if (e == null) {
                e = context.getSharedPreferences("multimedia_unavailble_pref", 0).edit();
            }
            e.putString(key, item.mFirstTime + MergeUtil.SEPARATOR_KV + item.mCode + MergeUtil.SEPARATOR_KV + item.mCount);
            e.commit();
        }
    }

    private static void b(String key, LogUnAvailbleItem item) {
        if (!TextUtils.isEmpty(key) && item != null) {
            Context context = AppUtils.getApplicationContext();
            if (e == null) {
                e = context.getSharedPreferences("multimedia_unavailble_pref", 0).edit();
            }
            e.putString(key, item.convertToJson());
            e.commit();
        }
    }

    private static void a(String key) {
        if (!TextUtils.isEmpty(key)) {
            if (e == null) {
                e = AppUtils.getApplicationContext().getSharedPreferences("multimedia_unavailble_pref", 0).edit();
            }
            e.remove(key);
            e.commit();
        }
    }

    private static LogUnAvailbleItem b(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        try {
            String val = AppUtils.getApplicationContext().getSharedPreferences("multimedia_unavailble_pref", 0).getString(key, "");
            if (TextUtils.isEmpty(val)) {
                return null;
            }
            String[] items = val.split("\\|");
            LogUnAvailbleItem item = new LogUnAvailbleItem(key, items[1]);
            try {
                item.mCount = Integer.valueOf(items[2]).intValue();
                item.mFirstTime = Long.valueOf(items[0]).longValue();
                return item;
            } catch (Exception e2) {
                return item;
            }
        } catch (Exception e3) {
            return null;
        }
    }

    private static LogUnAvailbleItem c(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        try {
            String val = AppUtils.getApplicationContext().getSharedPreferences("multimedia_unavailble_pref", 0).getString(key, "");
            if (!TextUtils.isEmpty(val)) {
                return LogUnAvailbleItem.convertToItem(val);
            }
            return null;
        } catch (Exception e2) {
            return null;
        }
    }

    public static void UC_MM_C18(int ret, long size, int duration, int time, String id, String name, String bizType) {
        String valueOf;
        if (ret == 0) {
            valueOf = "0";
        } else {
            valueOf = String.valueOf(ret);
        }
        LogItem logItem = new LogItem("UC-MM-C18", "clicked", "FilterVideo", valueOf, String.valueOf(size), String.valueOf(time));
        logItem.addExtParam("id", id);
        logItem.addExtParam(LogItem.MM_C18_K4_NM, name);
        logItem.addExtParam("bt", bizType);
        logItem.addExtParam(LogItem.MM_C18_K4_VD, String.valueOf(duration));
        logItem.log(logItem);
    }

    public static void UC_MM_C19(long fileSize, long imageSize, long audioSize, long videoSize, long sdTotal, long sdAvailable, long phoneTotal, long phoneAvailable) {
        LogItem logItem = new LogItem("UC-MM-C19", "clicked", "CacheStatistics", "0", "0", "0");
        logItem.addExtParam("fi", String.valueOf(fileSize));
        logItem.addExtParam("im", String.valueOf(imageSize));
        logItem.addExtParam(LogItem.MM_C19_K4_AUDIO, String.valueOf(audioSize));
        logItem.addExtParam(LogItem.MM_C19_K4_VIDEO, String.valueOf(videoSize));
        logItem.addExtParam(LogItem.MM_C19_K4_SD_TOTAL, String.valueOf(sdTotal));
        logItem.addExtParam(LogItem.MM_C19_K4_SD_AVAILABLE, String.valueOf(sdAvailable));
        logItem.addExtParam("pt", String.valueOf(phoneTotal));
        logItem.addExtParam("pa", String.valueOf(phoneAvailable));
        logItem.log(logItem);
    }

    public static void UC_MM_C20(int ret, int time, String uri, String exp) {
        String valueOf;
        if (ret == 0) {
            valueOf = "0";
        } else {
            valueOf = String.valueOf(ret);
        }
        LogItem logItem = new LogItem("UC-MM-C20", "clicked", "NetTimeOut", valueOf, "", String.valueOf(time));
        if (!TextUtils.isEmpty(uri)) {
            logItem.addExtParam("uri", uri);
        }
        if (!TextUtils.isEmpty(exp)) {
            logItem.addExtParam("exp", exp);
        }
        logItem.log(logItem);
    }

    public static void UC_MM_C21(int ret, long time, String url, String type, String state, int bv, long ti, String error, int loseCount) {
        String valueOf;
        if (ret == 0) {
            valueOf = "0";
        } else {
            valueOf = String.valueOf(ret);
        }
        LogItem logItem = new LogItem("UC-MM-C21", "clicked", "VideoLive", valueOf, "", String.valueOf(time));
        if (!TextUtils.isEmpty(url)) {
            logItem.addExtParam("url", url);
        }
        logItem.addExtParam("tp", type);
        logItem.addExtParam("st", state);
        logItem.addExtParam("bv", String.valueOf(bv));
        logItem.addExtParam("ti", String.valueOf(ti));
        if (TextUtils.isEmpty(error)) {
            error = "";
        }
        logItem.addExtParam("err", error);
        if (loseCount > 0) {
            logItem.addExtParam("ls", String.valueOf(loseCount));
        }
        logItem.log(logItem);
    }

    public static void UC_MM_C22(float time, int open, int type, int mode, int beautyval) {
        LogItem logItem = new LogItem("UC-MM-C22", "clicked", "BeautyBenchmark", "0", "", String.valueOf(time));
        logItem.addExtParam("op", String.valueOf(open));
        logItem.addExtParam("tp", String.valueOf(type));
        logItem.addExtParam(LogItem.MM_C22_K4_MODE, String.valueOf(mode));
        logItem.addExtParam("bv", String.valueOf(beautyval));
        logItem.log(logItem);
    }

    public static void UC_MM_C37(boolean success, long file_size, long finish_time, boolean is_cancel, long prepare_time, long encode_time, int encode_err, String traceId, String bizId, long duration, String cloudId, boolean noNetwork) {
        LogItem logItem = new LogItem("UC-MM-C37", "event", "UploadAudioStream", success ? "1" : "0", String.valueOf(file_size), String.valueOf(f.format(((double) finish_time) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C23_K4_IS_CANCEL, String.valueOf(is_cancel));
        logItem.addExtParam("pt", String.valueOf(f.format(((double) prepare_time) / 1000000.0d)));
        logItem.addExtParam("et", String.valueOf(f.format(((double) encode_time) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C23_K4_ENCODE_ERR, String.valueOf(encode_err));
        logItem.addExtParam("ti", traceId);
        logItem.addExtParam("bi", bizId);
        logItem.addExtParam("le", String.valueOf(duration));
        if (cloudId == null) {
            cloudId = "";
        }
        logItem.addExtParam("id", cloudId);
        logItem.addExtParam(LogItem.MM_K4_NO_NETWORK, noNetwork ? "1" : "0");
        logItem.log(logItem);
    }

    public static void UC_MM_C38(boolean success, long file_size, long download_time, long decode_time, int decode_err, String traceId, String bizId, boolean noNetwork) {
        LogItem logItem = new LogItem("UC-MM-C38", "event", "DownloadAudioStream", success ? "1" : "0", String.valueOf(file_size), String.valueOf(f.format(((double) download_time) / 1000000.0d)));
        logItem.addExtParam("dt", String.valueOf(f.format(((double) decode_time) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C24_K4_DECODE_ERR, String.valueOf(decode_err));
        logItem.addExtParam("ti", traceId);
        logItem.addExtParam("bi", bizId);
        logItem.addExtParam(LogItem.MM_K4_NO_NETWORK, noNetwork ? "1" : "0");
        logItem.log(logItem);
    }

    public static void UC_MM_C39(boolean success, int[] loading_time, int[] loading_count) {
        LogItem logItem = new LogItem("UC-MM-C39", "event", "AudioCacheLoadingPerf", success ? "0" : "1", "", "");
        logItem.addExtParam(LogItem.MM_C25_K4_LOADING_TIME_NONE, String.valueOf(f.format(((double) loading_time[0]) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C25_K4_LOADING_COUNT_NONE, String.valueOf(loading_count[0]));
        logItem.addExtParam(LogItem.MM_C25_K4_LOADING_TIME_MEM, String.valueOf(f.format(((double) loading_time[1]) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C25_K4_LOADING_COUNT_MEM, String.valueOf(loading_count[1]));
        logItem.addExtParam(LogItem.MM_C25_K4_LOADING_TIME_DISK, String.valueOf(f.format(((double) loading_time[2]) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C25_K4_LOADING_COUNT_DISK, String.valueOf(loading_count[2]));
        logItem.log(logItem);
    }

    public static void UC_MM_C43(boolean success, long file_size, long video_duration, long buffer_time, long camera_time, long preview_time, long start_time, long finish_time, long encode_time, int video_fps, String camera_fps, int encode_method, long camera_surface_ready_time) {
        LogItem logItem = new LogItem("UC-MM-C43", "event", "RecordVideoPerf", success ? "0" : "1", String.valueOf(file_size), String.valueOf(video_duration));
        logItem.addExtParam("bt", String.valueOf(f.format(((double) buffer_time) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C43_K4_CAMERA_TIME, String.valueOf(f.format(((double) camera_time) / 1000000.0d)));
        logItem.addExtParam("pt", String.valueOf(f.format(((double) preview_time) / 1000000.0d)));
        logItem.addExtParam("st", String.valueOf(f.format(((double) start_time) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C43_K4_FINISH_TIME, String.valueOf(f.format(((double) finish_time) / 1000000.0d)));
        logItem.addExtParam("et", String.valueOf(f.format(((double) encode_time) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C43_K4_VIDEO_FPS, String.valueOf(video_fps));
        logItem.addExtParam("cf", camera_fps);
        logItem.addExtParam("em", String.valueOf(encode_method));
        logItem.addExtParam(LogItem.MM_C43_K4_CAMERA_SURFACE_READY_TIME, String.valueOf(f.format(((double) camera_surface_ready_time) / 1000000.0d)));
        logItem.log(logItem);
    }

    public static void UC_MM_C44(boolean success, long v_render_time, long buffer_time, long gl_time, long decode_time, long t_render_time, long prepare_time) {
        LogItem logItem = new LogItem("UC-MM-C44", "event", "PlayVideoPerf", success ? "0" : "1", "", String.valueOf(v_render_time));
        logItem.addExtParam("bt", String.valueOf(f.format(((double) buffer_time) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C44_K4_GL_TIME, String.valueOf(f.format(((double) gl_time) / 1000000.0d)));
        logItem.addExtParam("dt", String.valueOf(f.format(((double) decode_time) / 1000000.0d)));
        logItem.addExtParam("rt", String.valueOf(f.format(((double) t_render_time) / 1000000.0d)));
        logItem.addExtParam("pt", String.valueOf(f.format(((double) prepare_time) / 1000000.0d)));
        logItem.log(logItem);
    }

    public static void UC_MM_C45(boolean success, int[] loading_time, int[] loading_count) {
        LogItem logItem = new LogItem("UC-MM-C45", "event", "VideoCacheLoadingPerf", success ? "0" : "1", "", "");
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_TIME_NONE_T, String.valueOf(f.format(((double) loading_time[0]) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_COUNT_NONE_T, String.valueOf(loading_count[0]));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_TIME_MEM_T, String.valueOf(f.format(((double) loading_time[1]) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_COUNT_MEM_T, String.valueOf(loading_count[1]));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_TIME_DISK_T, String.valueOf(f.format(((double) loading_time[2]) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_COUNT_DISK_T, String.valueOf(loading_count[2]));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_TIME_NONE_V, String.valueOf(f.format(((double) loading_time[3]) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_COUNT_NONE_V, String.valueOf(loading_count[3]));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_TIME_MEM_V, String.valueOf(f.format(((double) loading_time[4]) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_COUNT_MEM_V, String.valueOf(loading_count[4]));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_TIME_DISK_V, String.valueOf(f.format(((double) loading_time[5]) / 1000000.0d)));
        logItem.addExtParam(LogItem.MM_C45_K4_LOADING_COUNT_DISK_V, String.valueOf(loading_count[5]));
        logItem.log(logItem);
    }

    public static void UC_MM_C47(String code, long size, int from, String id, String type, String bizId, String unm, String exp, String zoom, String net, boolean noNetwork) {
        if (ConfigManager.getInstance().getCommonConfigItem().loadDiskLog != 0) {
            LogItem logItem = new LogItem("UC-MM-C47", "event", "storeCachePerf", code, String.valueOf(size), "0");
            logItem.setLogLevel(Integer.valueOf(3));
            logItem.setBizPro("APMultiMedia");
            logItem.addExtParam("fm", String.valueOf(from));
            logItem.addExtParam("id", id);
            logItem.addExtParam("tp", type);
            logItem.addExtParam("bid", bizId);
            logItem.addExtParam("unm", unm);
            logItem.addExtParam("exp", exp);
            logItem.addExtParam("zm", zoom);
            logItem.addExtParam(c.a, net);
            logItem.addExtParam(LogItem.MM_K4_NO_NETWORK, noNetwork ? "1" : "0");
            logItem.log(logItem);
        }
    }

    public static void UC_MM_48(String code, String id, String type) {
        LogItem logItem = new LogItem("UC-MM-C48", "event", "PreloadHitRate", code, "", "");
        logItem.setLogLevel(Integer.valueOf(1));
        logItem.setBizPro("ResourcePreDownloader");
        logItem.addExtParam("id", id);
        logItem.addExtParam("tp", type);
        logItem.log(logItem);
        b(id, code);
        Logger.D("UCLogUtil", "UC_MM_48 code=" + code + ";id=" + id + ";type=" + type, new Object[0]);
    }

    private static boolean d(String ret) {
        return !"5".equalsIgnoreCase(ret) || ConfigManager.getInstance().getCommonConfigItem().logConf.cancelswitch != 0;
    }

    private static String e(String business) {
        if (ConfigManager.getInstance().getMdnBizConfig().checkBusiness(business)) {
            return "3";
        }
        if (NBNetUtils.getNBNetDLSwitch(business)) {
            return "2";
        }
        return "1";
    }

    private static String f(String business) {
        if (NBNetUtils.getNBNetUPSwitch(business)) {
            return "2";
        }
        return "1";
    }

    private static void b(String fileId, String ret) {
        if (!TextUtils.isEmpty(fileId) && !TextUtils.isEmpty(ret)) {
            if (d == null) {
                d = AppUtils.getApplicationContext().getSharedPreferences("multimedia_preload_pref", 0);
            }
            d.edit().putString(fileId, ret).apply();
        }
    }

    public static boolean isPreloadIdInSp(String fileId) {
        if (d == null) {
            d = AppUtils.getApplicationContext().getSharedPreferences("multimedia_preload_pref", 0);
        }
        return d.contains(fileId);
    }

    public static void UC_MM_C49(boolean success, String path) {
        synchronized (UCLogUtil.class) {
            SharedPreferences sp = AppUtils.getApplicationContext().getSharedPreferences("multimedia_unavailble_pref", 0);
            if (!sp.getBoolean("reportTakePic", false)) {
                LogItem logItem = new LogItem("UC-MM-C49", "event", "TakePicture", success ? "0" : "-1", "0", "0");
                logItem.addExtParam("path", path);
                logItem.log(logItem);
                Logger.D("UCLogUtil", "UC_MM_C49 logItem: " + logItem, new Object[0]);
                sp.edit().putBoolean("reportTakePic", true).apply();
            }
        }
    }

    public static void UC_MM_C52(int blockCount, int totalCount, String name) {
        LogItem item = new LogItem("UC-MM-C52", "event", "TaskStatistics", "", "", "");
        item.addExtParam("name", String.valueOf(name));
        item.addExtParam("block", String.valueOf(blockCount));
        item.addExtParam("total", String.valueOf(totalCount));
        item.log(item);
    }

    public static void UC_MM_C53(int code, int size, int cost, Map<String, String> ext4) {
        LogItem item = new LogItem("UC-MM-C53", "event", "VideoEditor", String.valueOf(code), String.valueOf(size), String.valueOf(cost));
        item.addExtParams(ext4);
        item.log(item);
    }

    public static void UC_MM_C501(int code, int cost, Map<String, String> ext4) {
        LogItem item = new LogItem("UC-MM-C501", "event", "ResLoad", String.valueOf(code), "0", String.valueOf(cost));
        item.addExtParams(ext4);
        try {
            item.log(item);
        } catch (Throwable th) {
        }
    }

    public static void UC_MM_C50(String err_code, String time, Map<String, String> ext4) {
        LogItem item = new LogItem("UC-MM-C50", "event", "MediaPlayerInfo", err_code, "", time);
        item.addExtParams(ext4);
        item.log(item);
    }

    public static void UC_MM_C54(int result, String businessId, int volumeAdjusted) {
        LogItem item = new LogItem("UC-MM-C54", "event", RPCDataItems.TTS, String.valueOf(result), "", "");
        item.addExtParam("bz", businessId);
        item.addExtParam("va", String.valueOf(volumeAdjusted));
        item.log(item);
    }

    public static void UC_MM_C55(int operationType, int result, long sourceFileSize, long cost, String sourceUrl, String businessId) {
        LogItem item = new LogItem("UC-MM-C55", "event", "FileSecure", String.valueOf(result), String.valueOf(sourceFileSize), String.valueOf(cost));
        item.addExtParam("bz", businessId);
        item.addExtParam("op", String.valueOf(operationType));
        if (TextUtils.isEmpty(sourceUrl)) {
            sourceUrl = "";
        }
        item.addExtParam("url", sourceUrl);
        item.log(item);
    }

    public static String getTypeByCallGroup(int callCroup) {
        if (callCroup == 1002) {
            return "ad";
        }
        if (callCroup == 1001) {
            return "im";
        }
        if (callCroup == 1003) {
            return LogItem.MM_C18_K4_VD;
        }
        return "fl";
    }
}
