package com.alipay.android.phone.inside.main.action;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.account.AccountLogoutCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class AccountLogoutAction implements SdkAction {
    private final String a = "inside";
    private final Object b = new Object();

    public final String a() {
        return ActionEnum.ACCOUNT_LOGOUT_ACTION.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        final OperationResult operationResult = new OperationResult(AccountLogoutCode.SUCCESS, ActionEnum.ACCOUNT_LOGOUT_ACTION.getActionName());
        ServiceExecutor.a("LOGOUT_EXTERNAL_SERVICE", jSONObject, new IInsideServiceCallback() {
            public void onComplted(Object obj) {
                operationResult.setCode(AccountLogoutCode.SUCCESS);
                AccountLogoutAction.a(AccountLogoutAction.this);
            }

            public void onException(Throwable th) {
                operationResult.setCode(AccountLogoutCode.FAILED);
                AccountLogoutAction.a(AccountLogoutAction.this);
            }
        });
        synchronized (this.b) {
            try {
                this.b.wait(5000);
            } catch (Throwable th) {
                operationResult.setCode(AccountLogoutCode.TIMEOUT);
                LoggerFactory.e().a((String) "auth", (String) "LogoutWaitEx", th);
            }
        }
        return operationResult;
    }

    static /* synthetic */ void a(AccountLogoutAction accountLogoutAction) {
        synchronized (accountLogoutAction.b) {
            accountLogoutAction.b.notifyAll();
        }
    }
}
