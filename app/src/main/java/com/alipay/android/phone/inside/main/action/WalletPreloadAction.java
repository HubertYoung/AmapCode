package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.wallet.WalletPreloadCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class WalletPreloadAction implements SdkAction {
    public final String a() {
        return ActionEnum.WALLET_PRELOAD.getActionName();
    }

    public final OperationResult<WalletPreloadCode> a(JSONObject jSONObject) {
        OperationResult<WalletPreloadCode> operationResult = new OperationResult<>(WalletPreloadCode.SUCCESS, ActionEnum.WALLET_PRELOAD.getActionName());
        try {
            ServiceExecutor.b("WALLET_PLUGIN_PRELOAD_WALLET_SERVICE", new Bundle());
        } catch (Throwable th) {
            operationResult.setCode(WalletPreloadCode.FAILED);
            LoggerFactory.e().a((String) "wallet", (String) "PreloadWalletEx", th);
        }
        return operationResult;
    }
}
