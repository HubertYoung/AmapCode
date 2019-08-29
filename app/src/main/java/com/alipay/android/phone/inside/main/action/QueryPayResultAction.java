package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.QueryPayCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.main.action.util.QueryCodeResultUtil;
import com.taobao.accs.common.Constants;
import org.json.JSONObject;

public class QueryPayResultAction implements SdkAction {
    public final String a() {
        return ActionEnum.QUERY_PAY_RESULT.getActionName();
    }

    public final OperationResult<QueryPayCode> a(JSONObject jSONObject) {
        OperationResult<QueryPayCode> operationResult = new OperationResult<>(QueryPayCode.QUERY_UNKNOWN, ActionEnum.QUERY_PAY_RESULT.getActionName());
        String optString = jSONObject.optString("payCode");
        if (TextUtils.isEmpty(optString)) {
            operationResult.setCode(QueryPayCode.PARAMS_ILLEGAL);
            return operationResult;
        }
        String optString2 = jSONObject.optString("appName", null);
        String optString3 = jSONObject.optString(Constants.KEY_SID, null);
        try {
            Bundle bundle = new Bundle();
            bundle.putString("dynamicId", optString);
            bundle.putBoolean("isForAlipay", StaticConfig.i());
            JSONObject jSONObject2 = (JSONObject) ServiceExecutor.b("BARCODE_PLUGIN_QUERY_CODE", bundle);
            LoggerFactory.f().b((String) "inside", "TaoQueryPayResult::doAction > queryResult:".concat(String.valueOf(jSONObject2)));
            boolean z = true;
            if (jSONObject2 != null) {
                String optString4 = jSONObject2.optString("action", null);
                String optString5 = jSONObject2.optString("attachAction", null);
                if (!TextUtils.isEmpty(optString4) || !TextUtils.isEmpty(optString5)) {
                    z = false;
                }
            }
            if (!z) {
                jSONObject2.put("dynamicIds", jSONObject2.optString("dynamicId", null));
                jSONObject2.put("appName", optString2);
                jSONObject2.put("externalToken", optString3);
                OperationResult<QueryPayCode> a = QueryCodeResultUtil.a(ActionEnum.QUERY_PAY_RESULT, jSONObject2);
                try {
                    LoggerFactory.f().a((String) "inside", "TaoQueryPayResult::doAction > result: ".concat(String.valueOf(a)));
                    operationResult = a;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    operationResult = a;
                    th = th2;
                    LoggerFactory.e().a((String) "barcode", (String) "DoTaoQueryPayResultEx", th);
                    operationResult.setCode(QueryPayCode.QUERY_FAILED);
                    return operationResult;
                }
            } else {
                operationResult.setCode(QueryPayCode.QUERY_UNKNOWN);
                LoggerFactory.d().a("querycode", BehaviorType.EVENT, "QueryCodeUnknown", "queryResult: ".concat(String.valueOf(jSONObject2)));
            }
        } catch (Throwable th3) {
            th = th3;
        }
        return operationResult;
    }
}
