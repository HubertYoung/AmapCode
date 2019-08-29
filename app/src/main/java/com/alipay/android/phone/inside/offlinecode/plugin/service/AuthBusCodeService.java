package com.alipay.android.phone.inside.offlinecode.plugin.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.android.phone.inside.bizadapter.service.InteractionManager;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.wallet.JumpAlipaySchemeProvider;
import com.alipay.android.phone.inside.wallet.model.INotifyChecker;
import com.alipay.android.phone.inside.wallet.model.NotifyResult;
import com.alipay.android.phone.inside.wallet.model.TimeoutException;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData;
import com.alipay.mobile.tinyappcustom.api.MiniProgramAuthService;
import com.alipay.sdk.app.statistic.c;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.miniapp.plugin.lbs.H5Location;
import java.util.HashMap;
import org.json.JSONObject;

public class AuthBusCodeService extends AbstractInsideService<JSONObject, Bundle> {
    static final String CASHIER_SERVICE_INSIDE_ENV = "com.alipay.android.phone.inside.INSIDE_ENV";
    static final String CODE_FAILED = "FAILED";
    static final String CODE_SUCCESS = "SUCCESS";
    static final String CODE_TIMEOUT = "TIMEOUT";
    static final String KEY_SERVICE_RESET_TID = "com.alipay.android.phone.inside.PHONE_CASHIER_RESET_TID";
    static final String LOGIN_PROCESS_ALIPAY_PARAMS_SERVICE = "LOGIN_PROCESS_ALIPAY_PARAMS_SERVICE";
    static final String LOGIN_SERVICE_GET_USERINFO = "LOGIN_USERINFO_SERVICE";
    static final String LOGIN_SERVICE_LOGIN = "LOGIN_EXTERNAL_SERVICE";
    static final int SEC_SERVICE_TIMEOUT = 20;

    private void dealUniformity(JSONObject jSONObject) {
        try {
            ServiceExecutor.b("COMMONBIZ_SERVICE_ACCOUNTUNIFORMITY", jSONObject);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    public Bundle startForResult(JSONObject jSONObject) throws Exception {
        String str;
        dealUniformity(jSONObject);
        String str2 = "";
        String authInfo = getAuthInfo(jSONObject);
        if (isThirdTokenLogin(jSONObject)) {
            Bundle startServiceForResult = startServiceForResult(LOGIN_SERVICE_LOGIN, getOpenAuthTokenLoginParams(jSONObject));
            if (!isThirdTokenLoginSuccess(startServiceForResult)) {
                Behavior a = LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusAuthFailCode");
                a.g = "Bus3rdTokenLoginFail";
                StringBuilder sb = new StringBuilder("authToken : ");
                sb.append(jSONObject.optString("authToken"));
                sb.append(" || alipayUserId : ");
                sb.append(jSONObject.optString("alipayUserId"));
                a.a(sb.toString());
                notifyIfThirdTokenExpire(startServiceForResult);
                return buildResult("FAILED", "");
            }
            str2 = getLoginId();
        }
        String str3 = str2;
        try {
            LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusJumpWalletAuth");
            HashMap hashMap = new HashMap();
            hashMap.put("loginId", str3);
            hashMap.put("authLoginInfo", "");
            hashMap.put("authInfo", authInfo);
            NotifyResult jumpScheme = new JumpAlipaySchemeProvider().jumpScheme(getContext(), JumpAlipaySchemeProvider.BIZ_AUTH, str3, hashMap, getNotifyChecker());
            if (hasLoginResultToken(jumpScheme)) {
                Bundle bundle = jumpScheme.getBundle("authLoginResult");
                Bundle bundle2 = new Bundle();
                bundle2.putString(H5Location.REQUEST_TYPE, "processAuthLoginResponse");
                bundle2.putBundle("authLoginResult", bundle);
                startServiceForResult(LOGIN_PROCESS_ALIPAY_PARAMS_SERVICE, bundle2);
            }
            String authResult = getAuthResult(jumpScheme);
            String authResultCode = getAuthResultCode(authResult);
            if (TextUtils.equals(AlibcAlipay.PAY_SUCCESS_CODE, authResultCode)) {
                str = "SUCCESS";
            } else if (TextUtils.equals(H5AppPrepareData.PREPARE_FAIL, authResultCode)) {
                authResult = resetAuthResult(authResult);
                str = "SUCCESS";
            } else {
                Behavior a2 = LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusAuthFailCode");
                a2.g = authResultCode;
                a2.a(authResult);
                str = "FAILED";
            }
            if (isTidIllegal(authResult)) {
                LoggerFactory.d().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "BusAuthTidIllegal").a("will reset tid");
                ServiceExecutor.a((String) "com.alipay.android.phone.inside.PHONE_CASHIER_RESET_TID", new Bundle());
            }
            LoggerFactory.f().e("inside", "authResult:".concat(String.valueOf(authResult)));
            return buildResult(str, authResult);
        } catch (TimeoutException unused) {
            return buildResult("TIMEOUT", "");
        }
    }

    private boolean isTidIllegal(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("resultCode");
            String optString2 = jSONObject.optString("result");
            if (!TextUtils.equals(optString, "4000") || TextUtils.isEmpty(optString2) || !optString2.contains("success=false") || !optString2.contains("result_code=907")) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return false;
        }
    }

    private INotifyChecker getNotifyChecker() {
        return new INotifyChecker() {
            public boolean illegel(Bundle bundle) {
                if (bundle == null || bundle.getBoolean("insideFlag", false)) {
                    return false;
                }
                LoggerFactory.e().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "AuthNotifyInsideFlagIllegel");
                return true;
            }
        };
    }

    private String getAuthResult(NotifyResult notifyResult) {
        return notifyResult.getString("authResult");
    }

    private String resetAuthResult(String str) {
        JSONObject jSONObject;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject = new JSONObject(str);
            try {
                jSONObject.put("result", "");
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            jSONObject = jSONObject2;
            LoggerFactory.f().c((String) "inside", th);
            return jSONObject.toString();
        }
        return jSONObject.toString();
    }

    private String getAuthResultCode(String str) {
        try {
            return new JSONObject(str).optString("resultCode");
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            r4 = "";
            return "";
        }
    }

    private String buildAuthLoginInfo(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            for (String str : bundle.keySet()) {
                jSONObject.put(str, bundle.getString(str));
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject.toString();
    }

    private Bundle buildResult(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("code", str);
        bundle.putString("result", str2);
        return bundle;
    }

    private boolean hasLoginResultToken(NotifyResult notifyResult) {
        return (notifyResult == null || notifyResult.getBundle("authLoginResult") == null) ? false : true;
    }

    private String getLoginId() throws Exception {
        return ((Bundle) ServiceExecutor.b(LOGIN_SERVICE_GET_USERINFO, null)).getString("loginId", "");
    }

    private Bundle startServiceForResult(String str, Bundle bundle) {
        final Object obj = new Object();
        final Bundle bundle2 = new Bundle();
        ServiceExecutor.a(str, bundle, new IInsideServiceCallback<Bundle>() {
            public void onComplted(Bundle bundle) {
                LoggerFactory.f().e("auth", "startLogin result：".concat(String.valueOf(bundle)));
                bundle2.putAll(bundle);
                synchronized (obj) {
                    obj.notifyAll();
                }
            }

            public void onException(Throwable th) {
                LoggerFactory.e().a((String) "auth", (String) "LoginAuthEndEx", th);
                synchronized (obj) {
                    obj.notifyAll();
                }
            }
        });
        synchronized (obj) {
            try {
                obj.wait(20000);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "StartServiceWaitEx", th);
            }
        }
        return bundle2;
    }

    private String getAuthInfo(JSONObject jSONObject) throws Exception {
        String optString = jSONObject.optString("authBizData", "");
        JSONObject jSONObject2 = new JSONObject();
        Bundle bundle = new Bundle();
        bundle.putString(c.b, "busCodeAuth");
        jSONObject2.put("insideEnv", (String) ServiceExecutor.b("com.alipay.android.phone.inside.INSIDE_ENV", bundle));
        StringBuilder sb = new StringBuilder();
        sb.append(optString);
        sb.append("&bizcontext=");
        sb.append(jSONObject2.toString());
        return sb.toString();
    }

    private Bundle getOpenAuthTokenLoginParams(JSONObject jSONObject) {
        Bundle bundle = new Bundle();
        try {
            bundle.putString("openAuthToken", jSONObject.optString("authToken"));
            bundle.putString("openAuthUserId", jSONObject.optString("alipayUserId"));
            bundle.putString("insideLoginType", "openAuthTokenLogin");
        } catch (Throwable th) {
            LoggerFactory.f().b((String) "inside", th);
        }
        return bundle;
    }

    private boolean isThirdTokenLoginSuccess(Bundle bundle) {
        return bundle != null && TextUtils.equals(bundle.getString("loginStatus"), "success");
    }

    private boolean isThirdTokenLogin(JSONObject jSONObject) {
        return jSONObject.optBoolean("isOpenAuthLogin", false);
    }

    private void notifyIfThirdTokenExpire(Bundle bundle) {
        String string = bundle.getString("loginStatus");
        LoggerFactory.f().b((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "loginStatus: ".concat(String.valueOf(string)));
        if (TextUtils.equals(string, "openAuthTokenInvalid")) {
            LoggerFactory.e().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "OpenAuthTokenInvalid|BusThirdAuth");
            try {
                Bundle bundle2 = new Bundle();
                bundle2.putString("KEY_TYPE", "BROADCAST");
                bundle2.putString("KEY_ACTION", MiniProgramAuthService.LOGIN_TOKEN_INVALID);
                bundle2.putBundle("KEY_VALUE", new Bundle());
                InteractionManager.a(bundle2);
                LoggerFactory.d().b(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, BehaviorType.EVENT, "OpenAuthTokenInvalidBr");
            } catch (Throwable th) {
                LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "OpenAuthTokenInvalidBrEx", th);
            }
        }
    }
}
