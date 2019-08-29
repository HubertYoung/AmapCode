package com.alipay.android.phone.inside.offlinecode.rpc.response.base;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.offlinecode.rpc.utils.ExtInfoUtil;
import com.alipay.mobile.nebula.appcenter.config.H5NebulaAppConfigs;
import com.alipay.sdk.util.h;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

public class OfflineDataInfo {
    public String authMode;
    public String certType;
    public Map<String, String> config;
    public long expireTime = 0;
    public String offlineData;
    public String privateKey;
    public String qrcode;
    public String scriptMac;
    public String scriptName;
    public String scriptType;
    public boolean uploadRawCode;
    public boolean useScript = false;

    public JSONObject serializeJSON(long j) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("privateKey", this.privateKey);
            jSONObject.put(H5NebulaAppConfigs.EXPIRE_TIME, this.expireTime);
            jSONObject.put("offlineData", buildOfflineData(j));
            jSONObject.put("certType", this.certType);
            jSONObject.put("authMode", this.authMode);
            jSONObject.put("qrcode", this.qrcode);
            jSONObject.put("useScript", this.useScript);
            jSONObject.put("scriptType", this.scriptType);
            jSONObject.put("scriptName", this.scriptName);
            jSONObject.put("scriptMac", this.scriptMac);
            jSONObject.put("uploadRawCode", this.uploadRawCode);
            jSONObject.put("config", ExtInfoUtil.buildExtInfo(this.config));
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject;
    }

    private String buildOfflineData(long j) {
        if (TextUtils.isEmpty(this.offlineData)) {
            return this.offlineData;
        }
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("OfflineData:");
        sb.append(this.offlineData);
        f.b((String) "inside", sb.toString());
        if (this.offlineData.startsWith("{") && this.offlineData.endsWith(h.d)) {
            try {
                JSONObject jSONObject = new JSONObject(this.offlineData);
                long optLong = jSONObject.optLong("mer_server_date", -1);
                if (optLong > 0) {
                    LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "OfflineDataMerServerDate").g = String.valueOf(optLong);
                    String valueOf = String.valueOf((optLong + (j / 2)) - (System.currentTimeMillis() / 1000));
                    LoggerFactory.f().b((String) "inside", "timeDiffStr:".concat(String.valueOf(valueOf)));
                    jSONObject.put("server_time_diff", valueOf);
                }
                this.offlineData = jSONObject.toString();
            } catch (Exception e) {
                LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "buidOfflineDataEx", (Throwable) e);
            }
        }
        return this.offlineData;
    }

    public static OfflineDataInfo parse(String str) {
        OfflineDataInfo offlineDataInfo;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("privateKey");
            long optLong = jSONObject.optLong(H5NebulaAppConfigs.EXPIRE_TIME);
            String optString2 = jSONObject.optString("offlineData");
            String optString3 = jSONObject.optString("certType");
            String optString4 = jSONObject.optString("authMode");
            String optString5 = jSONObject.optString("qrcode");
            boolean optBoolean = jSONObject.optBoolean("useScript");
            String optString6 = jSONObject.optString("scriptType");
            String optString7 = jSONObject.optString("scriptName");
            String optString8 = jSONObject.optString("scriptMac");
            boolean optBoolean2 = jSONObject.optBoolean("uploadRawCode");
            JSONObject optJSONObject = jSONObject.optJSONObject("config");
            offlineDataInfo = new OfflineDataInfo();
            try {
                offlineDataInfo.privateKey = optString;
                offlineDataInfo.expireTime = optLong;
                offlineDataInfo.offlineData = optString2;
                offlineDataInfo.certType = optString3;
                offlineDataInfo.authMode = optString4;
                offlineDataInfo.qrcode = optString5;
                offlineDataInfo.useScript = optBoolean;
                offlineDataInfo.scriptType = optString6;
                offlineDataInfo.scriptName = optString7;
                offlineDataInfo.scriptMac = optString8;
                offlineDataInfo.uploadRawCode = optBoolean2;
                if (optJSONObject != null) {
                    offlineDataInfo.config = new HashMap();
                    Iterator<String> keys = optJSONObject.keys();
                    while (keys.hasNext()) {
                        String next = keys.next();
                        offlineDataInfo.config.put(next, optJSONObject.optString(next));
                    }
                }
            } catch (Throwable th) {
                th = th;
                LoggerFactory.f().c((String) "inside", th);
                return offlineDataInfo;
            }
        } catch (Throwable th2) {
            th = th2;
            offlineDataInfo = null;
            LoggerFactory.f().c((String) "inside", th);
            return offlineDataInfo;
        }
        return offlineDataInfo;
    }
}
