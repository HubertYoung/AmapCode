package com.alipay.android.phone.inside.main.action;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.sharetoken.ShareTokenCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.offlinecode.plugin.service.GenBusCodeService;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import org.json.JSONObject;

public class ShareTokenAction implements SdkAction {
    public final String a() {
        return ActionEnum.SHARE_TOKEN.getActionName();
    }

    public final OperationResult<ShareTokenCode> a(JSONObject jSONObject) {
        OperationResult<ShareTokenCode> operationResult = new OperationResult<>(ShareTokenCode.FAILED, ActionEnum.SHARE_TOKEN.getActionName());
        String str = "";
        switch (ApkVerifyTool.checkAlipayStatus(LauncherApplication.a(), 110)) {
            case SUCCESS:
                str = "ALIPAY_LEGAL";
                break;
            case NOT_INSTALL:
                str = GenBusCodeService.CODE_ALIPAY_NOT_INSTALL;
                break;
            case SIGN_ERROR:
                str = GenBusCodeService.CODE_ALIPAY_SIGN_ERROR;
                break;
            case VERSION_UNMATCH:
                str = "ALIPAY_VERSION_UNMATCH";
                break;
        }
        LoggerFactory.f().b((String) "inside", "ShareTokenAction::getAlipayStatus > alipayStatus:".concat(String.valueOf(str)));
        Bundle bundle = new Bundle();
        bundle.putString("content", jSONObject.optString("content"));
        try {
            Bundle bundle2 = (Bundle) ServiceExecutor.b("SERVICE_SHARETOKEN", bundle);
            String string = bundle2.getString("token");
            if (!TextUtils.isEmpty(string)) {
                operationResult.setResult(a(string, str));
                operationResult.setCode(ShareTokenCode.SUCCESS);
            } else {
                operationResult.setResult(a("", str));
                operationResult.setCode(ShareTokenCode.FAILED);
            }
            LoggerFactory.f().b((String) "sharetoken", "result:".concat(String.valueOf(bundle2)));
        } catch (Throwable th) {
            LoggerFactory.f().b((String) "sharetoken", th);
        }
        return operationResult;
    }

    private static String a(String str, String str2) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("token", str);
        jSONObject.put("alipayStatus", str2);
        return jSONObject.toString();
    }
}
