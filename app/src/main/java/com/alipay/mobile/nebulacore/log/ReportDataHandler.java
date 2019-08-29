package com.alipay.mobile.nebulacore.log;

import android.app.Activity;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.monitor.track.TrackPageConfig;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReportDataHandler {
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

    public void handleReportData(H5Event event, H5BridgeContext context, boolean isH5Activity) {
        JSONObject param = event.getParam();
        JSONObject spmJson = H5Utils.getJSONObject(param, "spm", null);
        if (spmJson != null && !spmJson.isEmpty()) {
            this.a = H5Utils.getString(spmJson, (String) "spmId");
            this.b = H5Utils.getString(spmJson, (String) "bizType");
            this.e = H5Utils.getString(spmJson, (String) "abTestInfo");
            this.c = H5Utils.getString(spmJson, (String) "chInfo");
            this.f = H5Utils.getBoolean(spmJson, (String) "isSPM", true);
            this.d = H5Utils.getString(spmJson, (String) "url");
            H5Log.d("ReportDataHandler", "logPageStartWithSpmId spmId:" + this.a + " spmBizType:" + this.b + " chInfo:" + this.c + " token:" + this.g.getPageToken() + " isSPM:" + this.f + " spmUrl:" + this.d + " abTestInfo:" + this.e);
            startSpm(event, isH5Activity);
        }
        if (!b()) {
            clearSpmDetail();
        }
        JSONObject jsonObject = H5Utils.getJSONObject(param, "spmDetail", null);
        if (jsonObject != null && !jsonObject.isEmpty()) {
            for (String key : jsonObject.keySet()) {
                try {
                    if (jsonObject.get(key) instanceof String) {
                        this.i.put(key, jsonObject.get(key).toString());
                    }
                } catch (Throwable throwable) {
                    H5Log.e((String) "ReportDataHandler", throwable);
                }
            }
        }
        context.sendSuccess();
    }

    public void startSpm(H5Event event, boolean isH5Activity) {
        Activity activity = event.getActivity();
        if (activity != null && !(activity instanceof H5Activity) && a() && (activity instanceof TrackPageConfig) && !((TrackPageConfig) event.getActivity()).isTrackPage()) {
            H5Log.d("ReportDataHandler", "startSpm isTrackPage : false ,return");
        } else if (H5Logger.enableStockTradeLog()) {
            if (!isH5Activity) {
                H5Log.d("ReportDataHandler", "is not H5Activity not log");
            }
            if (this.g != null) {
                H5Logger.putContextParam(LogContext.STORAGE_VIEWID, H5Utils.getCleanUrl(this.g.getPageUrl()));
                H5Log.d("ReportDataHandler", "isSPM:" + this.f + " h5LogProvider:" + this.h);
                if (this.f) {
                    this.g.setSpmId(this.a);
                    if (this.h != null) {
                        this.h.logAutoBehavorPageStart(this.a, this.g.getPageToken());
                    }
                } else if (this.h != null) {
                    H5Log.d("ReportDataHandler", "logAutoBehavorPageStart !isSPM  spmBizType " + this.b + " spmUrl:" + this.d + " token:" + this.g.getPageToken());
                    this.h.logAutoBehavorPageStart(this.d, this.g.getPageToken(), false);
                }
            }
        }
    }

    public void endSpm(H5Event event) {
        if (this.g != null) {
            Activity activity = event.getActivity();
            if (activity != null && !(activity instanceof H5Activity) && a() && (activity instanceof TrackPageConfig) && !((TrackPageConfig) event.getActivity()).isTrackPage()) {
                H5Log.d("ReportDataHandler", "endSpm isTrackPage false ,return");
            } else if (H5Logger.enableStockTradeLog()) {
                if (TextUtils.isEmpty(this.a)) {
                    if ("yes".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_filterEmptySpmId"))) {
                        H5Log.d("ReportDataHandler", "return empty spmId : " + this.a);
                        return;
                    }
                    this.a = this.g.getPageUrl();
                }
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
                    this.h.logAutoBehavorPageEnd(this.a, this.e, this.g.getPageToken(), this.b, tempSpmDetail);
                }
            }
        }
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
            H5Log.e((String) "ReportDataHandler", throwable);
        }
    }

    private static boolean a() {
        return !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_enableSpmTrackPage"));
    }

    private static boolean b() {
        return !BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_endSpmClearSpmDetail"));
    }

    public String getSpmId() {
        return this.a;
    }

    public String getSpmBizType() {
        return this.b;
    }

    public String getChInfo() {
        return this.c;
    }

    public String getABTestInfo() {
        return this.e;
    }
}
