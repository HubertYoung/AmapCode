package com.alipay.android.phone.inside.main.action;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.wallet.CheckAlipayStatusCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import org.json.JSONObject;

public class CheckAlipayStatusAction implements SdkAction {
    public final String a() {
        return ActionEnum.CHECK_ALIPAY_STATUS.getActionName();
    }

    public final OperationResult<CheckAlipayStatusCode> a(JSONObject jSONObject) {
        OperationResult<CheckAlipayStatusCode> operationResult = new OperationResult<>(CheckAlipayStatusCode.LOGIN, ActionEnum.CHECK_ALIPAY_STATUS.getActionName());
        int parseInt = Integer.parseInt(jSONObject.optString("minVersionCode", "0"));
        if (parseInt <= 0) {
            parseInt = 110;
        }
        try {
            WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(LauncherApplication.a(), parseInt);
            if (checkAlipayStatus != WalletStatusEnum.SUCCESS) {
                CheckAlipayStatusCode checkAlipayStatusCode = CheckAlipayStatusCode.LOGIN;
                switch (checkAlipayStatus) {
                    case SUCCESS:
                        checkAlipayStatusCode = CheckAlipayStatusCode.LOGIN;
                        break;
                    case NOT_INSTALL:
                        checkAlipayStatusCode = CheckAlipayStatusCode.ALIPAY_NOT_INSTALL;
                        break;
                    case SIGN_ERROR:
                        checkAlipayStatusCode = CheckAlipayStatusCode.ALIPAY_SIGN_ERROR;
                        break;
                    case VERSION_UNMATCH:
                        checkAlipayStatusCode = CheckAlipayStatusCode.ALIPAY_VERSION_UNMATCH;
                        break;
                }
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("CheckAlipayStatusAction::adapterWalletStatus > code:");
                sb.append(checkAlipayStatusCode.getValue());
                f.b((String) "inside", sb.toString());
                operationResult.setCode(checkAlipayStatusCode);
            } else {
                String str = (String) ServiceExecutor.b("ALIPAY_LOGIN_STATE_SERVICE", null);
                if (TextUtils.equals(str, "1")) {
                    operationResult.setCode(CheckAlipayStatusCode.LOGIN);
                } else {
                    operationResult.setCode(CheckAlipayStatusCode.UNLOGIN);
                }
                LoggerFactory.f().b((String) "inside", "CheckAlipayStatusAction::doAction > status:".concat(String.valueOf(str)));
            }
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "action", (String) "GetLoginStatusEx", th);
            operationResult.setCode(CheckAlipayStatusCode.INNER_EX);
        }
        return operationResult;
    }
}
