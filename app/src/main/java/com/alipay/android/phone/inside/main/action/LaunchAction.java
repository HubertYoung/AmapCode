package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.generalh5biz.LaunchCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import org.json.JSONObject;

public class LaunchAction implements SdkAction {
    public final String a() {
        return ActionEnum.LAUNCH_ACTION.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        OperationResult operationResult = new OperationResult(LaunchCode.FAILED, ActionEnum.LAUNCH_ACTION.getActionName());
        int parseInt = Integer.parseInt(jSONObject.optString("minVersionCode", "0"));
        if (parseInt <= 0) {
            parseInt = TransportMediator.KEYCODE_MEDIA_PLAY;
        }
        WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(LauncherApplication.a(), parseInt);
        try {
            jSONObject.put("externalAuth", true);
            if (checkAlipayStatus == WalletStatusEnum.SUCCESS) {
                jSONObject.put("externalAuth", false);
            }
            Bundle bundle = (Bundle) ServiceExecutor.b("GENERAL_H5BIZ_PLUGIN_START", jSONObject);
            String string = bundle.getString("code");
            String string2 = bundle.getString("result");
            if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
                operationResult.setCode(LaunchCode.SUCCESS);
            } else if (TextUtils.equals(string, "AUTH_TIMEOUT")) {
                operationResult.setCode(LaunchCode.TIMEOUT);
            } else if (TextUtils.equals(string, "PARAMS_ERROR")) {
                operationResult.setCode(LaunchCode.PARAMS_ILLEGAL);
            } else {
                operationResult.setCode(LaunchCode.FAILED);
            }
            operationResult.setResult(string2);
        } catch (Throwable th) {
            LoggerFactory.f().d("LaunchAction", th.toString());
        }
        return operationResult;
    }
}
