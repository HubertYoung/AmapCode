package com.alipay.android.phone.inside.main.action;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.accountopenauth.McAccountChangeCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class McAccountChangeAction implements SdkAction {
    private final Object a = new Object();

    public final String a() {
        return ActionEnum.MC_ACCOUNT_STATUS_CHANGE.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        LoggerFactory.f().e("OAuth_McAccountChangeAction", "McAccountChangeAction::startAction");
        final OperationResult operationResult = new OperationResult(McAccountChangeCode.FAILED, ActionEnum.MC_ACCOUNT_STATUS_CHANGE.getActionName());
        try {
            ServiceExecutor.a("MC_ACCOUNT_CHANGE_SERVICE", jSONObject, new IInsideServiceCallback<String>() {
                public void onException(Throwable th) {
                    operationResult.setCode(McAccountChangeCode.FAILED);
                    McAccountChangeAction.a(McAccountChangeAction.this);
                }

                public /* synthetic */ void onComplted(Object obj) {
                    operationResult.setCode(McAccountChangeCode.SUCCESS);
                    McAccountChangeAction.a(McAccountChangeAction.this);
                }
            });
            synchronized (this.a) {
                try {
                    this.a.wait(5000);
                } catch (Throwable th) {
                    operationResult.setCode(McAccountChangeCode.SUCCESS);
                    LoggerFactory.e().a((String) "auth", (String) "McAcountChangeWaitEx", th);
                }
            }
        } catch (Throwable th2) {
            LoggerFactory.e().a((String) "mcaccchange", (String) "doAction", th2);
            LoggerFactory.f().a((String) "OAuth_McAccountChangeAction", th2);
        }
        return operationResult;
    }

    static /* synthetic */ void a(McAccountChangeAction mcAccountChangeAction) {
        synchronized (mcAccountChangeAction.a) {
            mcAccountChangeAction.a.notifyAll();
        }
    }
}
