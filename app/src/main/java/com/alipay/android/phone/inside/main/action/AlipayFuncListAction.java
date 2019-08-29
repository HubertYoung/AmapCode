package com.alipay.android.phone.inside.main.action;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.afunc.AlipayFuncListCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.AFuncListProvider;
import org.json.JSONObject;

public class AlipayFuncListAction implements SdkAction {
    public final String a() {
        return ActionEnum.ALIPAY_FUNC_LIST.getActionName();
    }

    public final OperationResult<AlipayFuncListCode> a(JSONObject jSONObject) {
        OperationResult<AlipayFuncListCode> operationResult = new OperationResult<>(AlipayFuncListCode.SUCCESS, ActionEnum.ALIPAY_FUNC_LIST.getActionName());
        try {
            new AFuncListProvider();
            operationResult.setResult(AFuncListProvider.a());
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "wallet", (String) "BuildFuncListEx", th);
            operationResult.setCode(AlipayFuncListCode.FAILED);
        }
        return operationResult;
    }
}
