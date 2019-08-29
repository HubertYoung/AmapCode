package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.SwitchUserCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class SwitchUserAction implements SdkAction {
    public final String a() {
        return ActionEnum.SWITCH_USER.getActionName();
    }

    public final OperationResult<SwitchUserCode> a(JSONObject jSONObject) {
        final OperationResult<SwitchUserCode> operationResult = new OperationResult<>(SwitchUserCode.SUCCESS, ActionEnum.SWITCH_USER.getActionName());
        try {
            PluginManager.b("CHANGE_ACCOUNT_EXTERNAL_SERVICE").start(new IInsideServiceCallback() {
                public void onComplted(Object obj) {
                    SwitchUserCode switchUserCode = SwitchUserCode.CANCEL;
                    if (obj instanceof Bundle) {
                        String string = ((Bundle) obj).getString("loginStatus");
                        if (!TextUtils.isEmpty(string)) {
                            if (string.equals("success")) {
                                switchUserCode = SwitchUserCode.SUCCESS;
                            } else if (string.equals("cancel")) {
                                switchUserCode = SwitchUserCode.CANCEL;
                            }
                        }
                    }
                    operationResult.setCode(switchUserCode);
                    synchronized (operationResult) {
                        operationResult.notifyAll();
                    }
                }

                public void onException(Throwable th) {
                    operationResult.setCode(SwitchUserCode.INNER_EX);
                    synchronized (operationResult) {
                        operationResult.notifyAll();
                    }
                }
            }, jSONObject);
        } catch (Exception e) {
            operationResult.setCode(SwitchUserCode.INNER_EX);
            LoggerFactory.f().b((String) "inside", (Throwable) e);
        }
        synchronized (operationResult) {
            try {
                operationResult.wait();
            } catch (InterruptedException e2) {
                LoggerFactory.f().b((String) "SwitchUserAction", (Throwable) e2);
            }
        }
        return operationResult;
    }
}
