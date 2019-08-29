package com.alipay.android.phone.inside.main.action;

import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.LogoutCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.main.action.provider.AlipayLogoutProvider;
import org.json.JSONObject;

public class LoginOutAction implements SdkAction {
    public final String a() {
        return ActionEnum.LOGIN_OUT.getActionName();
    }

    public final OperationResult<LogoutCode> a(JSONObject jSONObject) {
        if (!StaticConfig.l()) {
            return new AlipayLogoutProvider().a(ActionEnum.LOGIN_OUT.getActionName(), jSONObject);
        }
        try {
            jSONObject.put("authBizData", "app_name=inside&biz_type=inside&biz_sub_type=closeInsidePay");
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return new AlipayLogoutProvider().a(ActionEnum.LOGIN_OUT.getActionName(), jSONObject);
    }
}
