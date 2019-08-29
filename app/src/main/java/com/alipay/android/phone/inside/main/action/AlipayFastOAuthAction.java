package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.accountopenauth.AFastOAuthCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.utils.CommonUtil;
import org.json.JSONObject;

public class AlipayFastOAuthAction implements SdkAction {
    public final String a() {
        return ActionEnum.ALIPAY_FAST_OAUTH.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        final Object obj = new Object();
        LoggerFactory.f().e("OAuth_AlipayFastOAuthAction", "AlipayFastOAuthAction::startAction");
        final OperationResult operationResult = new OperationResult(AFastOAuthCode.FAILED, ActionEnum.ALIPAY_FAST_OAUTH.getActionName());
        try {
            ServiceExecutor.a("FAST_OPEN_AUTH_SERVICE", jSONObject, new IInsideServiceCallback<Bundle>() {
                public /* synthetic */ void onComplted(Object obj) {
                    Bundle bundle = (Bundle) obj;
                    if (bundle != null) {
                        String string = bundle.getString("resultCode");
                        if (TextUtils.equals("AUTH_SUCCESS", string)) {
                            operationResult.setCode(AFastOAuthCode.SUCCESS);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("auth_code", bundle.getString("auth_code"));
                            bundle2.putString("app_id", bundle.getString("app_id"));
                            operationResult.setResult(CommonUtil.a(bundle2));
                        } else if (TextUtils.equals("AUTH_TIMEOUT", string)) {
                            operationResult.setCode(AFastOAuthCode.INTERRUPTED);
                        } else if (TextUtils.equals("AUTH_CANCELLED", string)) {
                            operationResult.setCode(AFastOAuthCode.CANCELLED);
                        } else if (TextUtils.equals("AUTH_FAILED_ST_INVALID", string)) {
                            operationResult.setCode(AFastOAuthCode.ST_INVALID_FAILED);
                        } else if (TextUtils.equals("AUTH_ALIPAY_VERSION_UNMATCH", string)) {
                            operationResult.setCode(AFastOAuthCode.ALIPAY_VERSION_UNMATCH);
                        } else if (TextUtils.equals("AUTH_MC_CANCELLED", string)) {
                            operationResult.setCode(AFastOAuthCode.MC_CANCELLED);
                        } else {
                            operationResult.setCode(AFastOAuthCode.FAILED);
                        }
                    } else {
                        operationResult.setCode(AFastOAuthCode.FAILED);
                    }
                    AlipayFastOAuthAction.a(obj);
                }

                public void onException(Throwable th) {
                    operationResult.setCode(AFastOAuthCode.FAILED);
                    AlipayFastOAuthAction.a(obj);
                }
            });
            synchronized (obj) {
                try {
                    obj.wait();
                } catch (Throwable th) {
                    operationResult.setCode(AFastOAuthCode.FAILED);
                    LoggerFactory.e().a((String) "openauth", (String) "AFastOAuthWaitEx", th);
                }
            }
        } catch (Throwable th2) {
            LoggerFactory.e().a((String) "fastoauth", (String) "doAction", th2);
            LoggerFactory.f().a((String) "OAuth_AlipayFastOAuthAction", th2);
        }
        return operationResult;
    }

    static /* synthetic */ void a(Object obj) {
        synchronized (obj) {
            obj.notifyAll();
        }
    }
}
