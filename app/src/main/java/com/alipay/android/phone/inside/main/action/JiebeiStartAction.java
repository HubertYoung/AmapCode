package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.jiebei.JiebeiOperationCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import org.json.JSONObject;

public class JiebeiStartAction implements SdkAction {
    public final String a() {
        return ActionEnum.JIEBEI_START_ACTION.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(JiebeiOperationCode.FAILED, ActionEnum.JIEBEI_START_ACTION.getActionName());
        int parseInt = Integer.parseInt(jSONObject.optString("minVersionCode", "0"));
        if (parseInt <= 0) {
            parseInt = TransportMediator.KEYCODE_MEDIA_PLAY;
        }
        WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(LauncherApplication.a(), parseInt);
        if (checkAlipayStatus != WalletStatusEnum.SUCCESS) {
            JiebeiOperationCode jiebeiOperationCode = JiebeiOperationCode.SUCCESS;
            switch (checkAlipayStatus) {
                case SUCCESS:
                    jiebeiOperationCode = JiebeiOperationCode.SUCCESS;
                    break;
                case NOT_INSTALL:
                    jiebeiOperationCode = JiebeiOperationCode.ALIPAY_NOT_INSTALL;
                    break;
                case SIGN_ERROR:
                    jiebeiOperationCode = JiebeiOperationCode.ALIPAY_SIGN_ERROR;
                    break;
                case VERSION_UNMATCH:
                    jiebeiOperationCode = JiebeiOperationCode.ALIPAY_VERSION_UNMATCH;
                    break;
            }
            TraceLogger f = LoggerFactory.f();
            StringBuilder sb = new StringBuilder("JiebeiStartAction::adapterWalletStatus > code:");
            sb.append(jiebeiOperationCode.getValue());
            f.b((String) "inside", sb.toString());
            operationResult.setCode(jiebeiOperationCode);
        } else {
            try {
                Bundle bundle = (Bundle) ServiceExecutor.b("JIEBEI_PLUGIN_START", jSONObject);
                String string = bundle.getString("code");
                String string2 = bundle.getString("result");
                if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                    operationResult.setCode(JiebeiOperationCode.SUCCESS);
                } else if (TextUtils.equals(string, "AUTH_TIMEOUT")) {
                    operationResult.setCode(JiebeiOperationCode.TIMEOUT);
                } else if (TextUtils.equals(string, "PARAMS_ERROR")) {
                    operationResult.setCode(JiebeiOperationCode.PARAMS_ILLEGAL);
                } else {
                    operationResult.setCode(JiebeiOperationCode.FAILED);
                }
                operationResult.setResult(string2);
            } catch (Throwable th) {
                operationResult.setCode(JiebeiOperationCode.FAILED);
                LoggerFactory.f().c((String) "inside", th);
            }
        }
        return operationResult;
    }
}
