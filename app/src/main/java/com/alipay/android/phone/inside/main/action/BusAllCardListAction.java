package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.buscode.BusAllCardListCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.AuthParamsChecker;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class BusAllCardListAction implements SdkAction {
    public final String a() {
        return ActionEnum.BUS_ALL_CARD_LIST_ACTION.getActionName();
    }

    public final OperationResult<BusAllCardListCode> a(JSONObject jSONObject) {
        OperationResult<BusAllCardListCode> operationResult = new OperationResult<>(BusAllCardListCode.SUCCESS, ActionEnum.BUS_ALL_CARD_LIST_ACTION.getActionName());
        new AuthParamsChecker();
        if (AuthParamsChecker.a(jSONObject)) {
            return AuthParamsChecker.a(operationResult, BusAllCardListCode.PARAMS_ILLEGAL);
        }
        Bundle bundle = new Bundle();
        bundle.putString("cityCode", jSONObject.optString("cityCode", ""));
        final Bundle bundle2 = new Bundle();
        final Object obj = new Object();
        ServiceExecutor.a("BUS_CODE_PLUGIN_ALL_CARD", bundle, new IInsideServiceCallback<Bundle>() {
            public /* synthetic */ void onComplted(Object obj) {
                Bundle bundle = (Bundle) obj;
                if (bundle != null) {
                    bundle2.putAll(bundle);
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
        String string = bundle2.getString("code");
        String string2 = bundle2.getString("result");
        if (TextUtils.equals(string, GenBusCodeService.CODE_SUCESS)) {
            operationResult.setCode(BusAllCardListCode.SUCCESS);
        } else if (TextUtils.equals(string, GenBusCodeService.CODE_UNAUTH)) {
            operationResult.setCode(BusAllCardListCode.UNAUTH);
        } else {
            TextUtils.equals(string, GenBusCodeService.CODE_FAILED);
            operationResult.setCode(BusAllCardListCode.FAILED);
        }
        operationResult.setResult(string2);
        return operationResult;
    }
}
