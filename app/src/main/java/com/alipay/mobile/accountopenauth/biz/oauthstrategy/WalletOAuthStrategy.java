package com.alipay.mobile.accountopenauth.biz.oauthstrategy;

import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.text.TextUtils;
import com.alipay.android.phone.inside.cashier.PhoneCashierPlugin;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.wallet.JumpAlipaySchemeProvider;
import com.alipay.android.phone.inside.wallet.model.NotifyResult;
import com.alipay.android.phone.inside.wallet.model.TimeoutException;
import com.alipay.mobile.accountopenauth.api.OAuthStrategy;
import com.alipay.mobile.accountopenauth.common.CommonUtil;
import com.alipay.mobile.accountopenauth.common.OAuthBehaviorLogger;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.HashMap;
import java.util.Map;

public class WalletOAuthStrategy implements OAuthStrategy {
    private String a;

    public WalletOAuthStrategy(String str) {
        this.a = str;
    }

    public final Bundle a(String str, String str2, Bundle bundle) {
        Bundle bundle2;
        String str3;
        Bundle bundle3 = new Bundle();
        try {
            if (!CommonUtil.a(LauncherApplication.a(), TransportMediator.KEYCODE_MEDIA_PLAY)) {
                OAuthTraceLogger.a((String) "WalletOAuthStrategy", (String) "isAlipayVersionMatch false");
                OAuthBehaviorLogger.a("action", "OpenAuthLogin_Wallet_Failed", this.a, "alipayVersionUnMatch", "", BehaviorType.EVENT);
                bundle3.putString("resultCode", "AUTH_ALIPAY_VERSION_UNMATCH");
                return bundle3;
            }
            NotifyResult a2 = a(str);
            if (a2 == null) {
                bundle2 = null;
            } else {
                bundle2 = a2.getBundle("authResult");
                OAuthTraceLogger.a((String) "WalletOAuthStrategy", "getAuthResult:".concat(String.valueOf(bundle2)));
            }
            if (bundle2 == null) {
                OAuthBehaviorLogger.a("action", "OpenAuthLogin_Wallet_Failed", this.a, "authResultNull", "", BehaviorType.EVENT);
                bundle3.putString("resultCode", "AUTH_FAILED");
            } else {
                OAuthTraceLogger.a((String) "WalletOAuthStrategy", "walletAuthResult:".concat(String.valueOf(bundle2)));
                if (bundle2 == null) {
                    str3 = "";
                } else {
                    str3 = bundle2.getString("insAuthCheck");
                    if (TextUtils.isEmpty(str3)) {
                        str3 = bundle2.getString("result_code");
                    }
                }
                CommonUtil.b(bundle2);
                if (!TextUtils.isEmpty(str3) && TextUtils.equals("ILLEAGAL_TID", str3)) {
                    OAuthBehaviorLogger.a("action", "OpenAuthLogin_Wallet_Failed", this.a, "ILLEAGAL_TID", "", BehaviorType.EVENT);
                    ServiceExecutor.a((String) PhoneCashierPlugin.KEY_SERVICE_RESET_TID, new Bundle());
                    bundle3.putString("resultCode", "AUTH_FAILED");
                } else if (!TextUtils.isEmpty(bundle2.getString("auth_code"))) {
                    OAuthBehaviorLogger.a("action", "OpenAuthLogin_Wallet_Success", this.a, "", "", BehaviorType.EVENT);
                    bundle3.putAll(bundle2);
                    bundle3.putString("resultCode", "AUTH_SUCCESS");
                } else if (TextUtils.equals("USER_CANCEL_AUTH", bundle2.getString("result_code"))) {
                    OAuthBehaviorLogger.a("action", "OpenAuthLogin_Wallet_User_Cancel", this.a, "", "", BehaviorType.EVENT);
                    bundle3.putString("resultCode", "AUTH_CANCELLED");
                } else {
                    OAuthBehaviorLogger.a("action", "OpenAuthLogin_Wallet_Failed", this.a, "", "", BehaviorType.EVENT);
                }
            }
            return bundle3;
        } catch (Throwable th) {
            OAuthTraceLogger.a("WalletOAuthStrategy", "walletoauth", th);
            if (th instanceof TimeoutException) {
                bundle3.putString("resultCode", "AUTH_TIMEOUT");
                OAuthBehaviorLogger.a("action", "OpenAuthLogin_Wallet_Timeout", this.a, "", "", BehaviorType.EVENT);
            } else {
                bundle3.putString("resultCode", "AUTH_FAILED");
                OAuthBehaviorLogger.a("action", "OpenAuthLogin_Wallet_Failed", this.a, "walletAuthEx", "", BehaviorType.EVENT);
            }
        }
    }

    private static NotifyResult a(String str) throws Exception {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("authURL", str);
            hashMap.put("bizURL", "");
            Map<String, String> d = RunningConfig.d();
            d.put("NEED_CHECK", "false");
            hashMap.put("deviceEnv", CommonUtil.a(d));
            hashMap.put("insideEnv", RunningConfig.b(false));
            hashMap.put("mqpScheme", Token.SEPARATOR);
            return new JumpAlipaySchemeProvider().jumpScheme(LauncherApplication.a(), JumpAlipaySchemeProvider.BIZ_COMMON_BIZ_AUTH, "", hashMap, null);
        } catch (Exception e) {
            OAuthTraceLogger.a((String) "WalletOAuthStrategy", (Throwable) e);
            throw e;
        }
    }
}
