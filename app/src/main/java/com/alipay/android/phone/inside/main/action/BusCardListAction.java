package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.buscode.BusCardListCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.AuthParamsChecker;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class BusCardListAction implements SdkAction {
    public final String a() {
        return ActionEnum.BUS_CARD_LIST_ACTION.getActionName();
    }

    public final OperationResult<BusCardListCode> a(JSONObject jSONObject) {
        OperationResult<BusCardListCode> operationResult = new OperationResult<>(BusCardListCode.SUCCESS, ActionEnum.BUS_CARD_LIST_ACTION.getActionName());
        new AuthParamsChecker();
        if (AuthParamsChecker.a(jSONObject)) {
            return AuthParamsChecker.a(operationResult, BusCardListCode.PARAMS_ILLEGAL);
        }
        final Bundle bundle = new Bundle();
        final Object obj = new Object();
        ServiceExecutor.a("BUS_CODE_PLUGIN_ISSUED_CARD", new Bundle(), new IInsideServiceCallback<Bundle>() {
            public /* synthetic */ void onComplted(Object obj) {
                Bundle bundle = (Bundle) obj;
                if (bundle != null) {
                    bundle.putAll(bundle);
                }
                synchronized (obj) {
                    obj.notifyAll();
                }
            }

            public void onException(Throwable th) {
                synchronized (obj) {
                    obj.notifyAll();
                }
            }
        });
        synchronized (obj) {
            try {
                obj.wait(20000);
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
            }
        }
        String string = bundle.getString("code");
        String string2 = bundle.getString("result");
        if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
            operationResult.setCode(BusCardListCode.SUCCESS);
        } else if (TextUtils.equals(string, GenBusCodeService.CODE_UNAUTH)) {
            operationResult.setCode(BusCardListCode.UNAUTH);
        } else {
            TextUtils.equals(string, GenBusCodeService.CODE_FAILED);
            operationResult.setCode(BusCardListCode.FAILED);
        }
        operationResult.setResult(string2);
        return operationResult;
    }
}
