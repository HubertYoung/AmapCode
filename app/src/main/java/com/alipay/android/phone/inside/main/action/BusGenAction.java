package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.buscode.BusGenCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.AuthParamsChecker;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class BusGenAction implements SdkAction {
    public final String a() {
        return ActionEnum.BUS_GEN_ACTION.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(BusGenCode.SUCCESS, ActionEnum.BUS_GEN_ACTION.getActionName());
        new AuthParamsChecker();
        if (AuthParamsChecker.a(jSONObject)) {
            return AuthParamsChecker.a(operationResult, BusGenCode.PARAMS_ILLEGAL);
        }
        try {
            Bundle bundle = (Bundle) ServiceExecutor.b("BUS_CODE_PLUGIN_GEN_CODE", jSONObject);
            String string = bundle.getString("code");
            String string2 = bundle.getString("value");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(BusGenCode.SUCCESS);
            } else {
                if (!TextUtils.equals(string, GenBusCodeService.CODE_FAILED)) {
                    if (TextUtils.equals(string, GenBusCodeService.CODE_RETRY_IN_ALIPAY)) {
                        operationResult.setCode(BusGenCode.RETRY_IN_ALIPAY);
                    } else if (TextUtils.equals(string, GenBusCodeService.CODE_UNAUTH)) {
                        operationResult.setCode(BusGenCode.UNAUTH);
                    } else if (TextUtils.equals(string, GenBusCodeService.CODE_NOCARD)) {
                        operationResult.setCode(BusGenCode.NOCARD);
                    } else if (TextUtils.equals(string, GenBusCodeService.CODE_TIMEOUT)) {
                        operationResult.setCode(BusGenCode.VERIFY_TIMOUT);
                    } else if (TextUtils.equals(string, GenBusCodeService.CODE_ALIPAY_NOT_INSTALL)) {
                        operationResult.setCode(BusGenCode.ALIPAY_NOT_INSTALL);
                    } else if (TextUtils.equals(string, GenBusCodeService.CODE_ALIPAY_SIGN_ERROR)) {
                        operationResult.setCode(BusGenCode.ALIPAY_SIGN_ERROR);
                    } else if (TextUtils.equals(string, GenBusCodeService.CODE_ALIPAY_UNMATCH)) {
                        operationResult.setCode(BusGenCode.ALIPAY_VERSION_UNMATCH);
                    }
                }
                operationResult.setCode(BusGenCode.FAILED);
            }
            operationResult.setResult(string2);
        } catch (Throwable th) {
            operationResult.setCode(BusGenCode.FAILED);
            LoggerFactory.f().c((String) "inside", th);
        }
        return operationResult;
    }
}
