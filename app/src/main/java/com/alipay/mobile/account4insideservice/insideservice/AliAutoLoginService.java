package com.alipay.mobile.account4insideservice.insideservice;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.mobile.account4insideservice.AliAutoLoginProviderImpl;
import com.alipay.mobile.account4insideservice.common.Account4InsideBehaviorLogger;
import com.alipay.mobile.account4insideservice.common.Account4InsideTraceLogger;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import com.alipay.mobile.securitycommon.aliauth.AliAuthResult;
import com.alipay.mobile.securitycommon.aliauth.AliAuthService;
import com.alipay.sdk.util.j;
import com.taobao.accs.common.Constants;
import org.json.JSONObject;

public class AliAutoLoginService implements IInsideService<JSONObject, Bundle> {
    public /* synthetic */ void start(IInsideServiceCallback iInsideServiceCallback, Object obj) throws Exception {
        String str;
        String str2;
        String str3;
        boolean z;
        boolean z2;
        JSONObject jSONObject = (JSONObject) obj;
        try {
            Account4InsideBehaviorLogger.a("action", "AliAutoLoginServiceStart");
            AliAuthService service = AliAuthService.getService();
            service.setAuthProvider(AliAutoLoginProviderImpl.a());
            String str4 = "";
            String str5 = "";
            boolean z3 = false;
            if (jSONObject != null) {
                str4 = jSONObject.optString(Key.SOURCE_TYPE);
                str5 = jSONObject.optString("targetUrl");
                z3 = jSONObject.optBoolean(Key.SHOW_UI);
                z2 = jSONObject.optBoolean(Key.BIND_TAOBAO);
                z = jSONObject.optBoolean(Key.FORCE_AUTH);
                str3 = jSONObject.optString("source");
                str2 = jSONObject.optString(Key.BIZ_SCENE);
                str = jSONObject.optString(Key.SAVE_ALI_LOGIN_COOKIE);
            } else {
                str2 = "";
                str = "";
                z = false;
                str3 = "";
                z2 = false;
            }
            Bundle bundle = new Bundle();
            bundle.putString(Key.SOURCE_TYPE, str4);
            bundle.putString("targetUrl", str5);
            bundle.putBoolean(Key.SHOW_UI, z3);
            bundle.putBoolean(Key.BIND_TAOBAO, z2);
            bundle.putBoolean(Key.FORCE_AUTH, z);
            bundle.putString("source", str3);
            bundle.putString(Key.BIZ_SCENE, str2);
            bundle.putString(Key.SAVE_ALI_LOGIN_COOKIE, str);
            AliAuthResult autoLogin = service.autoLogin(bundle);
            Bundle bundle2 = new Bundle();
            if (autoLogin != null) {
                bundle2.putBoolean("success", autoLogin.success);
                bundle2.putString("memo", autoLogin.memo);
                bundle2.putString("ecode", autoLogin.ecode);
                bundle2.putString("noticeUrl", autoLogin.noticeUrl);
                bundle2.putString("redirectUrl", autoLogin.redirectUrl);
                bundle2.putString(Constants.KEY_SID, autoLogin.sid);
                bundle2.putString("statusAction", autoLogin.statusAction);
                bundle2.putString(j.a, autoLogin.resultStatus);
                bundle2.putString("tbNick", autoLogin.tbNick);
                bundle2.putString("tbUserId", autoLogin.tbUserId);
                bundle2.putLong("timeStamp", autoLogin.timeStamp);
            }
            Account4InsideBehaviorLogger.a("action", "AliAutoLoginResultCode", autoLogin != null ? autoLogin.resultStatus : "resIsNull", "", "");
            iInsideServiceCallback.onComplted(bundle2);
        } catch (Throwable th) {
            Account4InsideTraceLogger.a("AliAutoLoginService", "AliAutoLoginService_start", th);
            Account4InsideBehaviorLogger.a("aliautologin", "AliAutoLoginService_start", th);
            iInsideServiceCallback.onException(th);
        }
    }

    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ void start(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }
}
