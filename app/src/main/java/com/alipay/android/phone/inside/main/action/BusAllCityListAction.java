package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.buscode.BusCitiesCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.AuthParamsChecker;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import org.json.JSONObject;

public class BusAllCityListAction implements SdkAction {
    public final String a() {
        return ActionEnum.BUS_CITIES_ACTION.getActionName();
    }

    public final OperationResult<BusCitiesCode> a(JSONObject jSONObject) {
        OperationResult<BusCitiesCode> operationResult = new OperationResult<>(BusCitiesCode.SUCCESS, ActionEnum.BUS_CITIES_ACTION.getActionName());
        new AuthParamsChecker();
        if (AuthParamsChecker.a(jSONObject)) {
            return AuthParamsChecker.a(operationResult, BusCitiesCode.PARAMS_ILLEGAL);
        }
        final Object obj = new Object();
        final Bundle bundle = new Bundle();
        ServiceExecutor.a("BUS_CODE_PLUGIN_ALL_CITY", new Bundle(), new IInsideServiceCallback<Bundle>() {
            public /* synthetic */ void onComplted(Object obj) {
                Bundle bundle = (Bundle) obj;
                bundle.putAll(bundle);
                LoggerFactory.f().e("inside", bundle.toString());
                synchronized (obj) {
                    obj.notifyAll();
                }
            }

            public void onException(Throwable th) {
                LoggerFactory.f().c((String) "inside", th);
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
            operationResult.setCode(BusCitiesCode.SUCCESS);
        } else {
            operationResult.setCode(BusCitiesCode.FAILED);
        }
        operationResult.setResult(string2);
        return operationResult;
    }
}
