package com.alipay.android.phone.inside.wallet.plugin.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.alipay.AlibcAlipay;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.wallet.JumpAlipaySchemeProvider;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import com.alipay.android.phone.inside.wallet.model.INotifyChecker;
import com.alipay.android.phone.inside.wallet.model.NotifyResult;
import com.alipay.android.phone.inside.wallet.model.TimeoutException;
import com.alipay.android.phone.inside.wallet.utils.LoginUitls;
import com.alipay.sdk.sys.a;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class CashierPayService extends AbstractInsideService<Bundle, Bundle> {
    static final String CODE_FAILED = "FAILED";
    static final String CODE_SUCCESS = "SUCCESS";
    static final String CODE_TIMEOUT = "TIMEOUT";
    static final String RE_PARAMS_INSIDE_ENV = "insideEnv";
    static final String RE_PARAMS_PAY_INFO = "payInfo";

    public Bundle startForResult(Bundle bundle) throws Exception {
        Bundle bundle2;
        String loginId = LoginUitls.getLoginId(true);
        String buildPayInfo = buildPayInfo(bundle.getString(RE_PARAMS_PAY_INFO), bundle.getString(RE_PARAMS_INSIDE_ENV));
        WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(getContext(), 127);
        if (checkAlipayStatus != WalletStatusEnum.SUCCESS) {
            StringBuilder sb = new StringBuilder("{\"errorCode\":\"");
            sb.append(checkAlipayStatus.name());
            sb.append("\"}");
            return buildResult("FAILED", sb.toString());
        }
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("loginId", loginId);
            hashMap.put(RE_PARAMS_PAY_INFO, buildPayInfo);
            try {
                String payResult = getPayResult(new JumpAlipaySchemeProvider(true).jumpScheme(getContext(), JumpAlipaySchemeProvider.BIZ_CASHIER_PAY, loginId, hashMap, getNotifyChecker()));
                if (TextUtils.equals(getPayResultCode(payResult), AlibcAlipay.PAY_SUCCESS_CODE)) {
                    bundle2 = buildResult("SUCCESS", payResult);
                } else {
                    bundle2 = buildResult("FAILED", payResult);
                }
            } catch (Exception e) {
                LoggerFactory.f().b((String) "inside", (Throwable) e);
                bundle2 = buildResult("FAILED", "");
            }
            return bundle2;
        } catch (TimeoutException unused) {
            return buildResult("TIMEOUT", "");
        } catch (Exception unused2) {
            return buildResult("FAILED", "");
        }
    }

    private String buildPayInfo(String str, String str2) throws Exception {
        if (TextUtils.isEmpty(str2)) {
            return str;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(RE_PARAMS_INSIDE_ENV, str2);
        if (str.indexOf(a.a) > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("&bizcontext=\"");
            sb.append(jSONObject.toString());
            sb.append("\"");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("&bizcontext=");
        sb2.append(jSONObject.toString());
        return sb2.toString();
    }

    private String getPayResult(NotifyResult notifyResult) throws JSONException {
        return new JSONObject(notifyResult.getString(JumpAlipaySchemeProvider.KEY_INS_BIZDATA)).optString("payResult", "");
    }

    private String getPayResultCode(String str) throws JSONException {
        return new JSONObject(str).optString("resultCode");
    }

    private INotifyChecker getNotifyChecker() {
        return new INotifyChecker() {
            public boolean illegel(Bundle bundle) {
                if (bundle == null || bundle.getBoolean("insideFlag", false)) {
                    return false;
                }
                LoggerFactory.e().a("jump", "PayNotifyInsideFlagIllegel");
                return true;
            }
        };
    }

    private Bundle buildResult(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("resultCode", str);
        bundle.putString("resultValue", str2);
        return bundle;
    }
}
