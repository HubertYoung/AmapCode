package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.account.AccountInfoCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.proxy.util.BundleUtils;
import com.amap.bundle.drivecommon.inter.NetConstant;
import org.json.JSONObject;

public class AccountInfoAction implements SdkAction {
    public final String a() {
        return ActionEnum.ACCOUNT_INFO_ACTION.getActionName();
    }

    public final OperationResult<AccountInfoCode> a(JSONObject jSONObject) {
        OperationResult<AccountInfoCode> operationResult = new OperationResult<>(AccountInfoCode.SUCCESS, ActionEnum.ACCOUNT_INFO_ACTION.getActionName());
        try {
            operationResult.setResult(BundleUtils.a((Bundle) ServiceExecutor.b("LOGIN_USERINFO_SERVICE", null)));
        } catch (Throwable th) {
            operationResult.setCode(AccountInfoCode.FAILED);
            LoggerFactory.e().a((String) NetConstant.KEY_MONEY_ACCOUNT, (String) "GetUserInfoEx", th);
        }
        return operationResult;
    }
}
