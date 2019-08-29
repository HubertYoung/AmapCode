package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.aliautologin.AliAutoLoginCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.CommonUtil;
import com.alipay.sdk.util.j;
import org.json.JSONObject;

public class AliAutoLoginAction implements SdkAction {
    private final String a = "AliAutoLoginAction";

    public final String a() {
        return ActionEnum.ALI_AUTO_LOGIN_DO_LOGIN.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        LoggerFactory.f().e("AliAutoLoginAction", "AliAutoLoginAction::startAction");
        final Object obj = new Object();
        final OperationResult operationResult = new OperationResult(AliAutoLoginCode.FAILED, ActionEnum.ALI_AUTO_LOGIN_DO_LOGIN.getActionName());
        try {
            ServiceExecutor.a("ALI_AUTO_LOGIN_SERVICE", jSONObject, new IInsideServiceCallback<Bundle>() {
                public /* synthetic */ void onComplted(Object obj) {
                    Bundle bundle = (Bundle) obj;
                    if (bundle != null) {
                        operationResult.setResult(CommonUtil.a(bundle));
                        if (TextUtils.equals("1000", bundle.getString(j.a))) {
                            operationResult.setCode(AliAutoLoginCode.SUCCESS);
                        } else {
                            operationResult.setCode(AliAutoLoginCode.FAILED);
                        }
                    } else {
                        operationResult.setCode(AliAutoLoginCode.FAILED);
                    }
                    AliAutoLoginAction.a(obj);
                }

                public void onException(Throwable th) {
                    operationResult.setCode(AliAutoLoginCode.FAILED);
                    AliAutoLoginAction.a(obj);
                }
            });
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (Throwable th) {
                    operationResult.setCode(AliAutoLoginCode.FAILED);
                    LoggerFactory.e().a((String) "aliautologin", (String) "AliAutoLoginActionWaitEx", th);
                }
            }
        } catch (Throwable th2) {
            LoggerFactory.e().a((String) "aliautologin", (String) "doActionAliAutoLoginAction", th2);
            LoggerFactory.f().a((String) "AliAutoLoginAction", th2);
        }
        return operationResult;
    }

    static /* synthetic */ void a(Object obj) {
        synchronized (obj) {
            obj.notifyAll();
        }
    }
}
