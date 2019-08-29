package com.alipay.mobile.accountopenauth.biz.insideplugin.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.mobile.accountopenauth.common.OAuthBehaviorLogger;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import org.json.JSONObject;

public class MCAccountChangeService implements IInsideService<JSONObject, String> {
    public /* synthetic */ void start(final IInsideServiceCallback iInsideServiceCallback, Object obj) throws Exception {
        JSONObject jSONObject = (JSONObject) obj;
        String str = "unknown";
        String str2 = "";
        if (jSONObject != null) {
            str = jSONObject.optString("accountStatus", "unknown");
            str2 = jSONObject.optString(Key.DELETE_ALI_LOGIN_COOKIE);
        }
        OAuthBehaviorLogger.a("OPENAUTH_MC_ACCOUNT_CHANGE_20181119", "mc_account_change", str, null, null);
        try {
            IInsideService b = PluginManager.b("GET_USER_ID_SERVICE");
            if (b != null) {
                String str3 = (String) b.startForResult(null);
                OAuthTraceLogger.a((String) "MCAccountChangeService", "clear uid=".concat(String.valueOf(str3)));
                Bundle bundle = new Bundle();
                bundle.putString(Key.DELETE_ALI_LOGIN_COOKIE, str2);
                bundle.putString(Key.ALIPAY_UID, str3);
                ServiceExecutor.a((String) "ALI_AUTO_LOGIN_CLEAR_TBSESSION_SERVICE", bundle);
            }
        } catch (Throwable th) {
            LoggerFactory.f().a("MCAccountChangeService", "getVerifiedSSOInfo", th);
            return;
        }
        ServiceExecutor.a("LOGOUT_EXTERNAL_SERVICE", jSONObject, new IInsideServiceCallback() {
            public void onComplted(Object obj) {
                OAuthTraceLogger.a((String) "MCAccountChangeService", (String) "logout complete");
                if (iInsideServiceCallback != null) {
                    iInsideServiceCallback.onComplted("");
                }
            }

            public void onException(Throwable th) {
                OAuthTraceLogger.a("MCAccountChangeService", "logout", th);
                iInsideServiceCallback.onException(th);
            }
        });
    }

    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ void start(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }
}
