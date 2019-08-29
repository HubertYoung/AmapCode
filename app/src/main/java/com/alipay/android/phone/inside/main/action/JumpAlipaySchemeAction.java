package com.alipay.android.phone.inside.main.action;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.wallet.JumpAlipaySchemeCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import java.net.URLEncoder;
import org.json.JSONObject;

public class JumpAlipaySchemeAction implements SdkAction {
    public final String a() {
        return ActionEnum.JUMP_ALIPAY_SCHEME.getActionName();
    }

    public final OperationResult<JumpAlipaySchemeCode> a(JSONObject jSONObject) {
        OperationResult<JumpAlipaySchemeCode> operationResult = new OperationResult<>(JumpAlipaySchemeCode.SUCCESS, ActionEnum.JUMP_ALIPAY_SCHEME.getActionName());
        int parseInt = Integer.parseInt(jSONObject.optString("minVersionCode", "0"));
        if (parseInt <= 0) {
            parseInt = 110;
        }
        Application a = LauncherApplication.a();
        try {
            WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(a, parseInt);
            if (checkAlipayStatus != WalletStatusEnum.SUCCESS) {
                JumpAlipaySchemeCode jumpAlipaySchemeCode = JumpAlipaySchemeCode.SUCCESS;
                switch (checkAlipayStatus) {
                    case SUCCESS:
                        jumpAlipaySchemeCode = JumpAlipaySchemeCode.SUCCESS;
                        break;
                    case NOT_INSTALL:
                        jumpAlipaySchemeCode = JumpAlipaySchemeCode.ALIPAY_NOT_INSTALL;
                        break;
                    case SIGN_ERROR:
                        jumpAlipaySchemeCode = JumpAlipaySchemeCode.ALIPAY_SIGN_ERROR;
                        break;
                    case VERSION_UNMATCH:
                        jumpAlipaySchemeCode = JumpAlipaySchemeCode.ALIPAY_VERSION_UNMATCH;
                        break;
                }
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("JumpAlipayAction::adapterWalletStatus > code:");
                sb.append(jumpAlipaySchemeCode.getValue());
                f.b((String) "inside", sb.toString());
                operationResult.setCode(jumpAlipaySchemeCode);
            } else {
                String optString = jSONObject.optString("alipayScheme", "");
                if (optString.startsWith("http")) {
                    StringBuilder sb2 = new StringBuilder("alipays://platformapi/startapp?appId=20000067&url=");
                    sb2.append(URLEncoder.encode(optString, "utf-8"));
                    optString = sb2.toString();
                    LoggerFactory.d().a("wallet", BehaviorType.EVENT, "JumpWalletHttp", "scheme:".concat(String.valueOf(optString)));
                }
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(optString));
                intent.setFlags(268435456);
                a.startActivity(intent);
                operationResult.setCode(JumpAlipaySchemeCode.SUCCESS);
            }
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "wallet", (String) "JumpAlipayActionEx", th);
            operationResult.setCode(JumpAlipaySchemeCode.FAILED);
        }
        return operationResult;
    }
}
