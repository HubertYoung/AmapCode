package com.alipay.android.phone.inside.offlinecode.rpc.response.base;

import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.mobile.scansdk.constant.Constants;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import org.json.JSONObject;

public class ErrorIndicator {
    static final String SCHEME_FORMAT = "alipays://platformapi/startapp?appId=60000098&url=%s";
    public static final String TYPE_BANNER = "banner";
    public static final String TYPE_DIALOG = "dialog";
    public static final String TYPE_PAGE = "page";
    public static final String TYPE_TOAST = "toast";
    public String actionButton;
    public String actionUrl;
    public boolean cleanCard = false;
    public String defaultButton;
    public String defaultUrl;
    public String desc;
    public String errorCode;
    public String tips;
    public String type;

    public JSONObject serializeJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("errorCode", this.errorCode);
            jSONObject.put(ModulePoi.TIPS, this.tips);
            jSONObject.put("type", this.type);
            jSONObject.put("cleanCard", this.cleanCard);
            jSONObject.put("defaultButton", this.defaultButton);
            if (!TextUtils.isEmpty(this.defaultUrl)) {
                jSONObject.put("defaultUrl", String.format(SCHEME_FORMAT, new Object[]{Uri.encode(this.defaultUrl)}));
            }
            jSONObject.put("actionButton", this.actionButton);
            if (!TextUtils.isEmpty(this.actionUrl)) {
                jSONObject.put(Constants.SERVICE_ACTION_URL, String.format(SCHEME_FORMAT, new Object[]{Uri.encode(this.actionUrl)}));
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject;
    }
}
