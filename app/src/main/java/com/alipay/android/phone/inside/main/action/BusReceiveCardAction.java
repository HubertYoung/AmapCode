package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.buscode.BusReceiveCardCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.main.action.utils.AuthParamsChecker;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import org.json.JSONObject;

public class BusReceiveCardAction implements SdkAction {
    public final String a() {
        return ActionEnum.BUS_RECEIVE_CARD_ACTION.getActionName();
    }

    public final OperationResult<BusReceiveCardCode> a(JSONObject jSONObject) {
        OperationResult<BusReceiveCardCode> operationResult = new OperationResult<>(BusReceiveCardCode.SUCCESS, ActionEnum.BUS_RECEIVE_CARD_ACTION.getActionName());
        new AuthParamsChecker();
        if (AuthParamsChecker.a(jSONObject)) {
            return AuthParamsChecker.a(operationResult, BusReceiveCardCode.PARAMS_ILLEGAL);
        }
        int parseInt = Integer.parseInt(jSONObject.optString("minVersionCode", "0"));
        if (parseInt <= 0) {
            parseInt = 121;
        }
        WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(LauncherApplication.a(), parseInt);
        if (checkAlipayStatus != WalletStatusEnum.SUCCESS) {
            BusReceiveCardCode busReceiveCardCode = BusReceiveCardCode.SUCCESS;
            switch (checkAlipayStatus) {
                case SUCCESS:
                    busReceiveCardCode = BusReceiveCardCode.SUCCESS;
                    break;
                case NOT_INSTALL:
                    busReceiveCardCode = BusReceiveCardCode.ALIPAY_NOT_INSTALL;
                    break;
                case SIGN_ERROR:
                    busReceiveCardCode = BusReceiveCardCode.ALIPAY_SIGN_ERROR;
                    break;
                case VERSION_UNMATCH:
                    busReceiveCardCode = BusReceiveCardCode.ALIPAY_UNMATCH;
                    break;
            }
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("BusReceiveCardAction::adapterWalletStatus > code:");
            sb.append(busReceiveCardCode.getValue());
            f.b((String) "inside", sb.toString());
            operationResult.setCode(busReceiveCardCode);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("cardType", jSONObject.optString("cardType"));
            bundle.putString("cardNo", jSONObject.optString("cardNo"));
            try {
                String string = ((Bundle) ServiceExecutor.b("BUS_CODE_PLUGIN_RECEIVE_CARD", bundle)).getString("code", "");
                if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                    operationResult.setCode(BusReceiveCardCode.SUCCESS);
                } else if (TextUtils.equals(string, GenBusCodeService.CODE_TIMEOUT)) {
                    operationResult.setCode(BusReceiveCardCode.TIMEOUT);
                } else {
                    operationResult.setCode(BusReceiveCardCode.FAILED);
                }
            } catch (Throwable th) {
                operationResult.setCode(BusReceiveCardCode.FAILED);
                LoggerFactory.f().c((String) "inside", th);
            }
        }
        return operationResult;
    }
}
