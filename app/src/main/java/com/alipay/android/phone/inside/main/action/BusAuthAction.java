package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.buscode.BusAuthCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import org.json.JSONObject;

public class BusAuthAction implements SdkAction {
    public final String a() {
        return ActionEnum.BUS_AUTH_ACTION.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(BusAuthCode.SUCCESS, ActionEnum.BUS_AUTH_ACTION.getActionName());
        int parseInt = Integer.parseInt(jSONObject.optString("minVersionCode", "0"));
        if (parseInt <= 0) {
            parseInt = 121;
        }
        WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(LauncherApplication.a(), parseInt);
        if (checkAlipayStatus != WalletStatusEnum.SUCCESS) {
            BusAuthCode busAuthCode = BusAuthCode.SUCCESS;
            switch (checkAlipayStatus) {
                case SUCCESS:
                    busAuthCode = BusAuthCode.SUCCESS;
                    break;
                case NOT_INSTALL:
                    busAuthCode = BusAuthCode.ALIPAY_NOT_INSTALL;
                    break;
                case SIGN_ERROR:
                    busAuthCode = BusAuthCode.ALIPAY_SIGN_ERROR;
                    break;
                case VERSION_UNMATCH:
                    busAuthCode = BusAuthCode.ALIPAY_VERSION_UNMATCH;
                    break;
            }
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("BusAuthAction::adapterWalletStatus > code:");
            sb.append(busAuthCode.getValue());
            f.b((String) "inside", sb.toString());
            operationResult.setCode(busAuthCode);
        } else {
            new Bundle();
            try {
                Bundle bundle = (Bundle) ServiceExecutor.b("BUS_CODE_PLUGIN_AUTH", jSONObject);
                String string = bundle.getString("code");
                String string2 = bundle.getString("result");
                if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                    operationResult.setCode(BusAuthCode.SUCCESS);
                } else if (TextUtils.equals(string, GenBusCodeService.CODE_TIMEOUT)) {
                    operationResult.setCode(BusAuthCode.TIMEOUT);
                } else {
                    operationResult.setCode(BusAuthCode.FAILED);
                }
                operationResult.setResult(string2);
            } catch (Throwable th) {
                operationResult.setCode(BusAuthCode.FAILED);
                LoggerFactory.f().c((String) "inside", th);
            }
        }
        return operationResult;
    }
}
