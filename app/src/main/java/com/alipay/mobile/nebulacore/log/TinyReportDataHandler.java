package com.alipay.mobile.nebulacore.log;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TinyReportDataHandler {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private boolean f;
    private H5PageData g;
    private H5LogProvider h = Nebula.getH5LogHandler();
    private final Map<String, String> i = new ConcurrentHashMap();

    public void setPageData(H5PageData pageData) {
        this.g = pageData;
    }

    public void handleReportData(H5Event event, H5BridgeContext context) {
        JSONObject param = event.getParam();
        JSONObject spmJson = H5Utils.getJSONObject(param, "spm", null);
        if (spmJson != null && !spmJson.isEmpty()) {
            this.a = H5Utils.getString(spmJson, (String) "spmId");
            this.b = H5Utils.getString(spmJson, (String) "bizType");
            this.e = H5Utils.getString(spmJson, (String) "abTestInfo");
            this.c = H5Utils.getString(spmJson, (String) "chInfo");
            this.f = H5Utils.getBoolean(spmJson, (String) "isSPM", true);
            this.d = H5Utils.getString(spmJson, (String) "url");
            H5Log.d("TinyReportDataHandler", "logPageStartWithSpmId spmId:" + this.a + " spmBizType:" + this.b + " chInfo:" + this.c + " token:" + this.g.getPageToken() + " isSPM:" + this.f + " spmUrl:" + this.d + " abTestInfo:" + this.e);
            start();
        }
        clearSpmDetail();
        JSONObject jsonObject = H5Utils.getJSONObject(param, "spmDetail", null);
        if (jsonObject != null && !jsonObject.isEmpty()) {
            for (String key : jsonObject.keySet()) {
                try {
                    if (jsonObject.get(key) instanceof String) {
                        this.i.put(key, jsonObject.get(key).toString());
                    }
                } catch (Throwable throwable) {
                    H5Log.e((String) "TinyReportDataHandler", throwable);
                }
            }
        }
        context.sendSuccess();
    }

    public void handlePageResume() {
        this.i.put("logStartFrom", "resume");
    }

    public void clearSpmDetail() {
        try {
            synchronized (this.i) {
                this.i.clear();
            }
        } catch (Throwable throwable) {
            H5Log.e((String) "TinyReportDataHandler", throwable);
        }
    }

    public void start() {
        if (this.g == null || !H5Logger.enableStockTradeLog()) {
            return;
        }
        if (this.f) {
            if (this.h != null) {
                H5Log.d("TinyReportDataHandler", String.format("start token: %s  mSpmId: %s", new Object[]{this.g.getPageToken(), this.a}));
                this.h.logTinyTrackerStart(this.g.getPageToken(), this.a);
            }
        } else if (this.h != null) {
            H5Log.d("TinyReportDataHandler", String.format("start token: %s  mSpmUrl: %s", new Object[]{this.g.getPageToken(), this.d}));
            this.h.logTinyTrackerStart(this.g.getPageToken(), this.d);
        }
    }

    public void end() {
        if (this.g != null && H5Logger.enableStockTradeLog()) {
            if (TextUtils.isEmpty(this.a)) {
                if ("yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_filterEmptySpmId"))) {
                    H5Log.d("TinyReportDataHandler", "return empty spmId : " + this.a);
                    return;
                }
                this.a = this.g.getPageUrl();
            }
            if (this.g != null) {
                if (!TextUtils.isEmpty(this.c)) {
                    this.i.put("chInfo", this.c);
                }
                if (!this.i.containsKey("url")) {
                    this.i.put("url", this.g.getPageUrl());
                }
                if (!this.i.containsKey("appId")) {
                    this.i.put("appId", this.g.getAppId());
                }
                if (!this.i.containsKey("version")) {
                    this.i.put("version", this.g.getAppVersion());
                }
                this.i.put("h5pageurl", this.g.getPageUrl());
                if (!TextUtils.isEmpty(this.g.getReleaseType())) {
                    this.i.put("log_release_type", this.g.getReleaseType());
                }
                HashMap tempSpmDetail = new HashMap(this.i);
                if (this.h != null) {
                    H5Log.d("TinyReportDataHandler", "logAutoBehavorPageStart !isSPM  spmBizType " + this.b + " spmUrl:" + this.a + " token:" + this.g.getPageToken());
                    H5Log.d("TinyReportDataHandler", String.format("end  token:%s spmId:%s bizType:%s spmDetail:%s chInfo:%s", new Object[]{this.g.getPageToken(), this.a, this.b, this.i.toString(), this.c}));
                    this.h.logTinyTrackerEnd(this.g.getPageToken(), this.a, this.b, tempSpmDetail);
                }
            }
        }
    }

    private static boolean a() {
        return !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("antlog_tinytracker_reportdata_main_enable"));
    }

    private static Boolean a(String appId) {
        if (TextUtils.isEmpty(appId)) {
            H5Log.e((String) "TinyReportDataHandler", (String) "appid is null!");
            return null;
        }
        String config = H5Environment.getConfigWithProcessCache("antlog_tinytracker_reportdata_list");
        if (TextUtils.isEmpty(config)) {
            H5Log.e((String) "TinyReportDataHandler", (String) "antlog_tinytracker_reportdata_list is null!");
            return null;
        }
        try {
            JSONObject json = JSON.parseObject(config);
            JSONArray blackList = json.getJSONArray("black_list");
            if (blackList == null || !blackList.contains(appId)) {
                JSONArray templateWhite = json.getJSONArray("template_whilte_list");
                String templateAppId = b(appId);
                H5Log.d("TinyReportDataHandler", "tinyTrackerListSwitch getTemplateAppId: " + templateAppId);
                if (templateWhite == null || TextUtils.isEmpty(templateAppId) || !templateWhite.contains(templateAppId)) {
                    JSONArray whiteList = json.getJSONArray("white_list");
                    if (whiteList != null && whiteList.contains(appId)) {
                        H5Log.d("TinyReportDataHandler", "tinyTrackerListSwitch white_list " + appId + "  bingo.");
                        return Boolean.valueOf(true);
                    }
                    H5Log.d("TinyReportDataHandler", "tinyTrackerListSwitch " + appId + " not match anything, return null");
                    return null;
                }
                H5Log.d("TinyReportDataHandler", "tinyTrackerListSwitch template_whilte_list " + templateAppId + " bingo.");
                return Boolean.valueOf(true);
            }
            H5Log.d("TinyReportDataHandler", "tinyTrackerListSwitch black_list" + appId + " bingo.");
            return Boolean.valueOf(false);
        } catch (Exception e2) {
            H5Log.e("TinyReportDataHandler", "tinyTrackerListSwitch error.", e2);
        }
    }

    private static String b(String appId) {
        H5AppProvider h5AppProvider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
        if (h5AppProvider != null) {
            AppInfo appInfo = h5AppProvider.getAppInfo(appId);
            if (appInfo != null) {
                return H5AppUtil.getTemplateAppId(appInfo);
            }
        }
        return null;
    }

    private static boolean b() {
        return "yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("antlog_tinytracker_reportdata_beta_enable"));
    }

    public static boolean shouldUseTinyTracker(String appId) {
        if (!a()) {
            return false;
        }
        Boolean listSwitch = a(appId);
        if (listSwitch != null) {
            return listSwitch.booleanValue();
        }
        return b();
    }
}
