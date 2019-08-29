package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.model.request.PushRequestModel.PushChannel;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.code.LogoutCode;
import com.alipay.android.phone.inside.api.result.code.PushCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.main.action.util.QueryCodeResultUtil;
import com.alipay.android.phone.inside.proxy.util.UserIdUtil;
import com.alipay.android.phone.inside.security.util.DesCBC;
import com.taobao.accs.common.Constants;
import java.util.Iterator;
import org.json.JSONObject;

public class PushAction implements SdkAction {
    public final String a() {
        return ActionEnum.PUSH.getActionName();
    }

    public final OperationResult a(JSONObject jSONObject) {
        JSONObject jSONObject2;
        try {
            String string = jSONObject.getString("pushContext");
            if (TextUtils.equals(jSONObject.getString("pushChannel"), PushChannel.ALIPAY_SYNC.getValue())) {
                jSONObject2 = new JSONObject();
                jSONObject2.put("bizData", string);
            } else {
                jSONObject2 = new JSONObject(string);
            }
            String optString = jSONObject2.optString("bizData");
            StringBuilder sb = new StringBuilder();
            sb.append(UserIdUtil.a(OutsideConfig.o()));
            sb.append("alipay_inside_push");
            boolean z = false;
            JSONObject jSONObject3 = new JSONObject(DesCBC.b(sb.toString().substring(0, 24), optString));
            Iterator<String> keys = jSONObject3.keys();
            if (keys != null) {
                while (keys.hasNext()) {
                    String next = keys.next();
                    jSONObject.putOpt(next, jSONObject3.opt(next));
                }
            }
            if (jSONObject != null && TextUtils.equals(jSONObject.optString("action", null), "inside_close")) {
                z = true;
            }
            if (z) {
                LoggerFactory.d().b("push", BehaviorType.EVENT, "BarcodePushUnauth");
                ServiceExecutor.b("BARCODE_PLUGIN_DELETE_SEED", new Bundle());
                return new OperationResult(LogoutCode.SUCCESS, ActionEnum.LOGIN_OUT.getActionName());
            }
            LoggerFactory.d().b("push", BehaviorType.EVENT, "BarcodePushQuery");
            jSONObject.put("appName", RunningConfig.i());
            jSONObject.put("externalToken", jSONObject.optString(Constants.KEY_SID, ""));
            return QueryCodeResultUtil.a(ActionEnum.QUERY_PAY_RESULT, jSONObject);
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "push", (String) "TaoPushEx", th);
            return new OperationResult(PushCode.FAILED, ActionEnum.PUSH.getActionName());
        }
    }
}
