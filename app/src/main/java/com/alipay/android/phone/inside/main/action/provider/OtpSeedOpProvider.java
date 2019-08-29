package com.alipay.android.phone.inside.main.action.provider;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.proxy.util.UserIdUtil;
import com.alipay.android.phone.inside.storage.pref.EncryptPrefUtil;
import java.net.URLDecoder;
import org.json.JSONObject;

public class OtpSeedOpProvider {
    static String a = "encryptedFactors";
    static String b = "serverTimespan";
    static String c = "alipayUserId";

    public static boolean a() {
        try {
            return ((Bundle) ServiceExecutor.b("BARCODE_PLUGIN_DELETE_SEED", new Bundle())).getBoolean("success", false);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return false;
        }
    }

    public static boolean a(String str) {
        if (!b(str)) {
            return false;
        }
        boolean z = true;
        try {
            String decode = URLDecoder.decode(str, "UTF-8");
            if (decode.startsWith("{{") && decode.endsWith("}}")) {
                decode = decode.substring(1, decode.length() - 1);
            }
            JSONObject jSONObject = new JSONObject(decode);
            String optString = jSONObject.optString(c, null);
            if (TextUtils.isEmpty(optString)) {
                LoggerFactory.e().a((String) "auth", (String) "AuthMemoAlipayUserIdEmpty", jSONObject.toString());
                throw new Exception("alipayUserId is empty");
            }
            UserIdUtil.a(OutsideConfig.o(), optString);
            String optString2 = jSONObject.optString(a, null);
            if (TextUtils.isEmpty(optString2)) {
                LoggerFactory.e().a((String) "auth", (String) "AuthMemoEncryptedFactorsEmpty", jSONObject.toString());
                throw new Exception("encryptedFactors is empty");
            }
            Bundle bundle = new Bundle();
            bundle.putBoolean("update_check_params", false);
            bundle.putString("otp_seed", optString2);
            boolean booleanValue = ((Boolean) ServiceExecutor.b("BARCODE_PLUGIN_UPDATE_SEED", bundle)).booleanValue();
            LoggerFactory.d().b("auth", BehaviorType.EVENT, "TaoAuthUpdateOtp|".concat(String.valueOf(booleanValue)));
            if (!booleanValue) {
                throw new Exception("update otp ex");
            }
            String optString3 = jSONObject.optString(b, null);
            if (!TextUtils.isEmpty(optString3)) {
                EncryptPrefUtil.a("alipay_inside_keys", "server_timespan", optString3);
                LoggerFactory.d().a("taoauth", BehaviorType.EVENT, "CashierUpdateServerTime", "serverTimespan:".concat(String.valueOf(optString3)));
            }
            return z;
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "otp", (String) "SaveOtpSeedOpEx", th);
            LoggerFactory.f().c((String) "inside", th);
            z = false;
        }
    }

    private static boolean b(String str) {
        try {
            String decode = URLDecoder.decode(str, "UTF-8");
            if (decode.startsWith("{{") && decode.endsWith("}}")) {
                decode = decode.substring(1, decode.length() - 1);
            }
            return new JSONObject(decode).has(a);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return false;
        }
    }
}
