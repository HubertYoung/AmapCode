package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.accountopenauth.AOAuthCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.CommonUtil;
import org.json.JSONObject;

public class AlipayOAuthAction implements SdkAction {
    public final String a() {
        return ActionEnum.ALIPAY_OAUTH.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        final Object obj = new Object();
        LoggerFactory.f().e("OAuth_AlipayOAuthAction", "AlipayOAuthAction::startAction");
        final OperationResult operationResult = new OperationResult(AOAuthCode.FAILED, ActionEnum.ALIPAY_OAUTH.getActionName());
        try {
            ServiceExecutor.a("ACCOUNT_OPEN_AUTH_SERVICE", jSONObject, new IInsideServiceCallback<Bundle>() {
                public /* synthetic */ void onComplted(Object obj) {
                    Bundle bundle = (Bundle) obj;
                    if (bundle != null) {
                        String string = bundle.getString("resultCode");
                        if (TextUtils.equals("AUTH_SUCCESS", string)) {
                            operationResult.setCode(AOAuthCode.SUCCESS);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("auth_code", bundle.getString("auth_code"));
                            bundle2.putString("app_id", bundle.getString("app_id"));
                            operationResult.setResult(CommonUtil.a(bundle2));
                        } else if (TextUtils.equals("AUTH_TIMEOUT", string)) {
                            operationResult.setCode(AOAuthCode.INTERRUPTED);
                        } else if (TextUtils.equals("AUTH_CANCELLED", string)) {
                            operationResult.setCode(AOAuthCode.CANCELLED);
                        } else if (TextUtils.equals("AUTH_ALIPAY_VERSION_UNMATCH", string)) {
                            operationResult.setCode(AOAuthCode.ALIPAY_VERSION_UNMATCH);
                        } else {
                            operationResult.setCode(AOAuthCode.FAILED);
                        }
                    } else {
                        operationResult.setCode(AOAuthCode.FAILED);
                    }
                    AlipayOAuthAction.a(obj);
                }

                public void onException(Throwable th) {
                    operationResult.setCode(AOAuthCode.FAILED);
                    AlipayOAuthAction.a(obj);
                }
            });
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (Throwable th) {
                    operationResult.setCode(AOAuthCode.FAILED);
                    LoggerFactory.e().a((String) "auth", (String) "AOAuthWaitEx", th);
                }
            }
        } catch (Throwable th2) {
            LoggerFactory.e().a((String) "oauth", (String) "doAction", th2);
            LoggerFactory.f().a((String) "OAuth_AlipayOAuthAction", th2);
        }
        return operationResult;
    }

    static /* synthetic */ void a(Object obj) {
        synchronized (obj) {
            obj.notifyAll();
        }
    }
}
