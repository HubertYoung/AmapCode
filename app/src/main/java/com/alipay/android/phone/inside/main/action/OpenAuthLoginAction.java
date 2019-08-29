package com.alipay.android.phone.inside.main.action;

import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.accountopenauth.OAuthLoginCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class OpenAuthLoginAction implements SdkAction {
    public final String a() {
        return ActionEnum.ALIPAY_OPEN_AUTH_LOGIN.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        LoggerFactory.f().e("OAuth_OpenAuthLoginAction", "OpenAuthLoginAction::startAction");
        final Object obj = new Object();
        final OperationResult operationResult = new OperationResult(OAuthLoginCode.FAILED, ActionEnum.ALIPAY_OPEN_AUTH_LOGIN.getActionName());
        try {
            ServiceExecutor.a("OPEN_AUTH_LOGIN_SERVICE", jSONObject, new IInsideServiceCallback<String>() {
                public /* synthetic */ void onComplted(Object obj) {
                    String str = (String) obj;
                    if (!TextUtils.isEmpty(str)) {
                        if (TextUtils.equals("AUTH_LOGIN_SUCCESS", str)) {
                            operationResult.setCode(OAuthLoginCode.SUCCESS);
                        } else if (TextUtils.equals("AUTH_LOGIN_PARAM_ILEEGAL", str)) {
                            operationResult.setCode(OAuthLoginCode.PARAMS_ILLEGAL);
                        } else if (TextUtils.equals("AUTH_LOGIN_TOKEN_INVALID", str)) {
                            operationResult.setCode(OAuthLoginCode.TOKEN_INVALID);
                        } else {
                            TextUtils.equals("AUTH_LOGIN_COMMON_FAILED", str);
                        }
                        OpenAuthLoginAction.a(obj);
                    }
                    operationResult.setCode(OAuthLoginCode.FAILED);
                    OpenAuthLoginAction.a(obj);
                }

                public void onException(Throwable th) {
                    operationResult.setCode(OAuthLoginCode.FAILED);
                    OpenAuthLoginAction.a(obj);
                }
            });
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (Throwable th) {
                    operationResult.setCode(OAuthLoginCode.FAILED);
                    LoggerFactory.e().a((String) "openauthlogin", (String) "OpenAuthLoginWaitEx", th);
                }
            }
        } catch (Throwable th2) {
            LoggerFactory.e().a((String) "openauthlogin", (String) "doAction", th2);
            LoggerFactory.f().a((String) "OAuth_OpenAuthLoginAction", th2);
        }
        return operationResult;
    }

    static /* synthetic */ void a(Object obj) {
        synchronized (obj) {
            obj.notifyAll();
        }
    }
}
