package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.account.OAuthAccountUniformityCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class OAuthAccountUniformityAction implements SdkAction {
    public final String a() {
        return ActionEnum.OAUTH_ACCOUNT_UNIFORMITY.getActionName();
    }

    public final OperationResult<OAuthAccountUniformityCode> a(JSONObject jSONObject) {
        OperationResult<OAuthAccountUniformityCode> operationResult = new OperationResult<>(OAuthAccountUniformityCode.SUCCESS, ActionEnum.OAUTH_ACCOUNT_UNIFORMITY.getActionName());
        try {
            Bundle bundle = new Bundle();
            if (jSONObject != null) {
                bundle.putString("mcBindAlipayUid", jSONObject.optString("mcBindAlipayUid"));
            }
            if (TextUtils.equals("YES", (String) ServiceExecutor.b("OAUTH_ACCOUNT_CONSISTENCY_SERVICE", bundle))) {
                operationResult.setCode(OAuthAccountUniformityCode.SUCCESS);
            } else {
                operationResult.setCode(OAuthAccountUniformityCode.UNCONFORMITY);
            }
        } catch (Exception e) {
            LoggerFactory.e().a((String) "inside", (String) "oauth_checkau", (Throwable) e);
            operationResult.setCode(OAuthAccountUniformityCode.INNER_EX);
        }
        return operationResult;
    }
}
