package com.alipay.android.phone.inside.cashier.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.cashier.utils.InsideEnvBuilder;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class ExtendParamsService extends AbstractInsideService<Bundle, String> {
    static final String BIZ_TYPE_DEFAULT = "default";
    static final String BIZ_TYPE_INNER_OFFLINE_PAGE = "innerOfflinePage";
    static final String BIZ_TYPE_INNER_OUTTRADE_PAY = "innerOutTradePay";
    static final String BIZ_TYPE_INNER_QRCODE_AUTH = "innerQrCodeAuth";
    static final String BIZ_TYPE_INNER_QRCODE_TO_ONLINE = "innerQrCodeToOnline";
    static final String KEY_BIZ_TYPE = "bizType";
    static final String KEY_NEED_INIT_OTP = "needInitOtp";
    static final String KEY_RENDER_DATA = "renderData";

    public String startForResult(Bundle bundle) throws Exception {
        String string = bundle.getString("bizType");
        if (TextUtils.equals(string, BIZ_TYPE_INNER_OFFLINE_PAGE)) {
            return getInnerOfflinePageExt(bundle);
        }
        if (TextUtils.equals(string, BIZ_TYPE_INNER_QRCODE_TO_ONLINE)) {
            return getInnerQrCodeToOnlineExt(bundle);
        }
        if (TextUtils.equals(string, BIZ_TYPE_INNER_QRCODE_AUTH)) {
            return getInnerQrcodeAuthExt();
        }
        if (TextUtils.equals(string, BIZ_TYPE_INNER_OUTTRADE_PAY)) {
            return getInnerOutTradePay();
        }
        if (TextUtils.equals(string, "default")) {
            return getDefaultExt();
        }
        throw new Exception("unkown biz type:".concat(String.valueOf(string)));
    }

    public static String getDefaultExt() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("inside_env", new InsideEnvBuilder().getInsideEnv());
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "inside", (String) "GetDefaultExtEx", th);
        }
        return jSONObject.toString();
    }

    private String getInnerQrcodeAuthExt() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("inside_env", new InsideEnvBuilder().getInnerQrcodeAuthEnv());
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "inside", (String) "GetDefaultExtEx", th);
        }
        return jSONObject.toString();
    }

    private String getInnerOutTradePay() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("inside_env", new InsideEnvBuilder().getOutTradePayEnv());
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "inside", (String) "GetInnerOutTradePayEx", th);
        }
        return jSONObject.toString();
    }

    private String getInnerQrCodeToOnlineExt(Bundle bundle) {
        boolean z = bundle.getBoolean(KEY_NEED_INIT_OTP);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("inside_env", new InsideEnvBuilder().getInnerQrCodeToOnlineEnv(z));
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "inside", (String) "GetQrCodeToOnlineExtEx", th);
        }
        return jSONObject.toString();
    }

    private String getInnerOfflinePageExt(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("inside_env", new InsideEnvBuilder().getInsideEnv());
            jSONObject.put("render_data", bundle.getString(KEY_RENDER_DATA, ""));
            jSONObject.put("render_local", "true");
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "inside", (String) "GetOfflinePageExtEx", th);
        }
        return jSONObject.toString();
    }
}
