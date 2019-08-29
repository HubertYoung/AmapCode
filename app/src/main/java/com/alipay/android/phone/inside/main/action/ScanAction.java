package com.alipay.android.phone.inside.main.action;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.ScanCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.proxy.util.BundleUtils;
import com.alipay.android.phone.inside.wallet.api.AlipayServiceBinder;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import org.json.JSONObject;

public class ScanAction implements SdkAction {
    public final String a() {
        return ActionEnum.SCAN_CODE.getActionName();
    }

    private static ScanCode a(WalletStatusEnum walletStatusEnum) {
        ScanCode scanCode = ScanCode.SUCCESS;
        switch (walletStatusEnum) {
            case SUCCESS:
                scanCode = ScanCode.SUCCESS;
                break;
            case NOT_INSTALL:
                scanCode = ScanCode.ALIPAY_NOT_INSTALL;
                break;
            case SIGN_ERROR:
                scanCode = ScanCode.ALIPAY_SIGN_ERROR;
                break;
            case VERSION_UNMATCH:
                scanCode = ScanCode.ALIPAY_VERSION_UNMATCH;
                break;
        }
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("ScanAction::adapterWalletStatus > ScanCode:");
        sb.append(scanCode.getValue());
        f.b((String) "inside", sb.toString());
        return scanCode;
    }

    private static OperationResult<ScanCode> a(Bundle bundle) {
        if (bundle == null) {
            throw new NullPointerException("bundle is null");
        }
        OperationResult<ScanCode> operationResult = new OperationResult<>(ScanCode.SUCCESS, ActionEnum.SCAN_CODE.getActionName());
        int i = bundle.getInt("statusCode", -1);
        LoggerFactory.d().b("scan", BehaviorType.EVENT, "ScanServiceResult|".concat(String.valueOf(i)));
        switch (i) {
            case 0:
            case 1:
                operationResult.setCode(ScanCode.SUCCESS);
                break;
            case 2:
                operationResult.setCode(ScanCode.CODE_UNKNOWN);
                break;
            default:
                operationResult.setCode(ScanCode.FAILED);
                break;
        }
        return operationResult;
    }

    public final OperationResult<ScanCode> a(JSONObject jSONObject) {
        Application a = LauncherApplication.a();
        OperationResult operationResult = new OperationResult(ScanCode.SUCCESS, ActionEnum.SCAN_CODE.getActionName());
        boolean booleanValue = Boolean.valueOf(jSONObject.optString("useInsideMode")).booleanValue();
        int parseInt = Integer.parseInt(jSONObject.optString("minVersionCode", "0"));
        if (parseInt <= 0) {
            parseInt = 113;
        }
        WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(LauncherApplication.a(), parseInt);
        if (checkAlipayStatus == WalletStatusEnum.SUCCESS) {
            LoggerFactory.f().b((String) "inside", (String) "ScanAction::doAction > scan by wallet");
            try {
                return a(AlipayServiceBinder.getInstance().invokeAlipayService(a, BundleUtils.a(jSONObject)));
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "inside", (String) "InvokeAlipayServiceEx", th);
                operationResult.setCode(ScanCode.INNER_EX);
            }
        } else {
            if (booleanValue) {
                LoggerFactory.f().b((String) "inside", (String) "ScanAction::doAction > scan by inside");
                try {
                    OperationResult<ScanCode> a2 = a((Bundle) ServiceExecutor.b("SCAN_CODE_SERVICE", jSONObject));
                    if (!TextUtils.equals(a2.getCodeValue(), ScanCode.SUCCESS.getValue())) {
                        return a2;
                    }
                    operationResult.setCode(a(checkAlipayStatus));
                } catch (Throwable th2) {
                    LoggerFactory.e().a((String) "scan", (String) "ScanByInsideEx", th2);
                    if (!(th2 instanceof RpcException) || ((RpcException) th2).getCode() != 2000) {
                        operationResult.setCode(ScanCode.INNER_EX);
                    } else {
                        operationResult.setCode(ScanCode.AUTH_INVALID);
                    }
                }
            } else {
                LoggerFactory.d().b("scan", BehaviorType.EVENT, "UnUserInside");
                operationResult.setCode(a(checkAlipayStatus));
            }
            return operationResult;
        }
    }
}
