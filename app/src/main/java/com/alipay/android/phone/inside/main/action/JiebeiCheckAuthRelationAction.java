package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.jiebei.JiebeiCheckAuthRelationCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class JiebeiCheckAuthRelationAction implements SdkAction {
    public final String a() {
        return ActionEnum.JIEBEI_CHECK_AUTH_RELATION_ACTION.getActionName();
    }

    public final OperationResult<JiebeiCheckAuthRelationCode> a(JSONObject jSONObject) {
        OperationResult<JiebeiCheckAuthRelationCode> operationResult = new OperationResult<>(JiebeiCheckAuthRelationCode.FAILED, ActionEnum.JIEBEI_CHECK_AUTH_RELATION_ACTION.getActionName());
        try {
            String string = ((Bundle) ServiceExecutor.b("JIEBEI_PLUGIN_CHECK_AUTH_RELATION", jSONObject)).getString("authRelation");
            if (TextUtils.equals("TRUE", string)) {
                operationResult.setCode(JiebeiCheckAuthRelationCode.SUCCESS);
            } else if (TextUtils.equals("FALSE", string)) {
                operationResult.setCode(JiebeiCheckAuthRelationCode.NO_AUTH);
            } else if (TextUtils.equals("NO_LOGIN", string)) {
                operationResult.setCode(JiebeiCheckAuthRelationCode.NO_LOGIN);
            } else if (TextUtils.equals("NO_UNIFORMITY", string)) {
                operationResult.setCode(JiebeiCheckAuthRelationCode.ACCOUNT_UNMATCH);
            } else {
                operationResult.setCode(JiebeiCheckAuthRelationCode.FAILED);
            }
        } catch (Throwable th) {
            operationResult.setCode(JiebeiCheckAuthRelationCode.FAILED);
            LoggerFactory.e().a((String) "jiebei", (String) "JiebeiCheckAuthRelationEx", th);
        }
        return operationResult;
    }
}
