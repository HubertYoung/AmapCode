package com.alipay.android.phone.inside.main.action;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.wallet.SafeJumpAlipaySchemeCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.wallet.api.AlipayServiceBinder;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import org.json.JSONObject;

public class SafeJumpAlipaySchemeAction implements SdkAction {
    public final String a() {
        return ActionEnum.SAFE_JUMP_ALIPAY_SCHEME.getActionName();
    }

    public final OperationResult<SafeJumpAlipaySchemeCode> a(JSONObject jSONObject) {
        OperationResult<SafeJumpAlipaySchemeCode> operationResult = new OperationResult<>(SafeJumpAlipaySchemeCode.SUCCESS, ActionEnum.SAFE_JUMP_ALIPAY_SCHEME.getActionName());
        int parseInt = Integer.parseInt(jSONObject.optString("minVersionCode", "0"));
        if (parseInt <= 0) {
            parseInt = TransportMediator.KEYCODE_MEDIA_PLAY;
        }
        Application a = LauncherApplication.a();
        try {
            WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(a, parseInt);
            if (checkAlipayStatus != WalletStatusEnum.SUCCESS) {
                SafeJumpAlipaySchemeCode safeJumpAlipaySchemeCode = SafeJumpAlipaySchemeCode.SUCCESS;
                switch (checkAlipayStatus) {
                    case SUCCESS:
                        safeJumpAlipaySchemeCode = SafeJumpAlipaySchemeCode.SUCCESS;
                        break;
                    case NOT_INSTALL:
                        safeJumpAlipaySchemeCode = SafeJumpAlipaySchemeCode.ALIPAY_NOT_INSTALL;
                        break;
                    case SIGN_ERROR:
                        safeJumpAlipaySchemeCode = SafeJumpAlipaySchemeCode.ALIPAY_SIGN_ERROR;
                        break;
                    case VERSION_UNMATCH:
                        safeJumpAlipaySchemeCode = SafeJumpAlipaySchemeCode.ALIPAY_VERSION_UNMATCH;
                        break;
                }
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("SafeJumpAlipaySchemeAction::adapterWalletStatus > code:");
                sb.append(safeJumpAlipaySchemeCode.getValue());
                f.b((String) "inside", sb.toString());
                operationResult.setCode(safeJumpAlipaySchemeCode);
            } else {
                String optString = jSONObject.optString("alipayScheme", "");
                LoggerFactory.d().a("wallet", BehaviorType.EVENT, "SafeJumpAlipayScheme").g = optString;
                if (TextUtils.isEmpty(optString)) {
                    LoggerFactory.e().a((String) "wallet", (String) "SafeJumpAlipaySchemeEmpty", "alipayScheme:".concat(String.valueOf(optString)));
                    operationResult.setCode(SafeJumpAlipaySchemeCode.PARAMS_ILLEGAL);
                } else {
                    StringBuilder sb2 = new StringBuilder("SAFEJUMP_");
                    sb2.append(StaticConfig.b());
                    String sb3 = sb2.toString();
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(optString);
                    sb4.append("&sourceId=");
                    sb4.append(sb3);
                    String sb5 = sb4.toString();
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append(sb5);
                    sb6.append("&chInfo=");
                    sb6.append(sb3);
                    String sb7 = sb6.toString();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isDirectScheme", true);
                    bundle.putString("directScheme", sb7);
                    AlipayServiceBinder.getInstance().invokeAlipayService(a, bundle);
                    operationResult.setCode(SafeJumpAlipaySchemeCode.SUCCESS);
                }
            }
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "wallet", (String) "SafeJumpAlipaySchemeActionEx", th);
            operationResult.setCode(SafeJumpAlipaySchemeCode.FAILED);
        }
        return operationResult;
    }
}
