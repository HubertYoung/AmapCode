package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.buscode.BusUnauthCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.AuthParamsChecker;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import org.json.JSONObject;

public class BusCloseAction implements SdkAction {
    public final String a() {
        return ActionEnum.BUS_UNAUTH_ACTION.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        LoggerFactory.f().e("inside", "BusCloseAction start");
        OperationResult operationResult = new OperationResult(BusUnauthCode.SUCCESS, ActionEnum.BUS_UNAUTH_ACTION.getActionName());
        new AuthParamsChecker();
        if (AuthParamsChecker.a(jSONObject)) {
            return AuthParamsChecker.a(operationResult, BusUnauthCode.PARAMS_ILLEGAL);
        }
        try {
            final Object obj = new Object();
            final Bundle bundle = new Bundle();
            ServiceExecutor.a("BUS_CODE_PLUGIN_CLOSE", new Bundle(), new IInsideServiceCallback<Bundle>() {
                public /* synthetic */ void onComplted(Object obj) {
                    Bundle bundle = (Bundle) obj;
                    LoggerFactory.f().e("inside", "BusCloseAction onComplted");
                    if (bundle != null) {
                        bundle.putAll(bundle);
                    }
                    synchronized (obj) {
                        obj.notifyAll();
                    }
                }

                public void onException(Throwable th) {
                    LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "BuscodeUnAuthEx", th);
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
            if (bundle.getBoolean("success")) {
                operationResult.setCode(BusUnauthCode.SUCCESS);
            } else {
                operationResult.setCode(BusUnauthCode.FAILED);
            }
        } catch (Throwable th2) {
            LoggerFactory.f().c((String) "inside", th2);
            operationResult.setCode(BusUnauthCode.FAILED);
        }
        return operationResult;
    }
}
