package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.tinyapp.TinyAppJumpCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.Iterator;
import org.json.JSONObject;

public class JumpTinyAppAction implements SdkAction {
    public final String a() {
        return ActionEnum.TINY_APP_JUMP.getActionName();
    }

    public final OperationResult<TinyAppJumpCode> a(JSONObject jSONObject) {
        OperationResult<TinyAppJumpCode> operationResult = new OperationResult<>(TinyAppJumpCode.SUCCESS, ActionEnum.TINY_APP_JUMP.getActionName());
        try {
            LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp("", jSONObject.optString("appId", ""), a(jSONObject.optString("paramsMap", "")));
        } catch (Throwable unused) {
            operationResult.setCode(TinyAppJumpCode.FAILED);
        }
        return operationResult;
    }

    private static Bundle a(String str) {
        Bundle bundle = new Bundle();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator<String> keys = jSONObject.keys();
            while (keys.hasNext()) {
                String next = keys.next();
                bundle.putString(next, jSONObject.optString(next, ""));
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "", th);
        }
        return bundle;
    }
}
