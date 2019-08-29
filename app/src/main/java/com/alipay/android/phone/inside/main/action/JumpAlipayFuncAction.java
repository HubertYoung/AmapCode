package com.alipay.android.phone.inside.main.action;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.action.ActionEnum;
import com.alipay.android.phone.inside.api.result.OperationResult;
import com.alipay.android.phone.inside.api.result.afunc.JumpAlipayFuncCode;
import com.alipay.android.phone.inside.commonbiz.action.SdkAction;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.Behavior;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.android.phone.inside.log.api.ex.ExceptionLogger;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.main.action.utils.AFuncListProvider;
import com.alipay.android.phone.inside.wallet.api.AlipayServiceBinder;
import com.alipay.android.phone.inside.wallet.api.ApkVerifyTool;
import com.alipay.android.phone.inside.wallet.api.JumpWalletUtils;
import com.alipay.android.phone.inside.wallet.api.WalletStatusEnum;
import com.alipay.mobile.common.logging.api.DeviceProperty;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class JumpAlipayFuncAction implements SdkAction {
    private static final List<String> a;

    static {
        ArrayList arrayList = new ArrayList();
        a = arrayList;
        arrayList.add(DeviceProperty.ALIAS_OPPO);
    }

    public final String a() {
        return ActionEnum.JUMP_ALIPAY_FUNC.getActionName();
    }

    public final OperationResult<JumpAlipayFuncCode> a(JSONObject jSONObject) {
        OperationResult<JumpAlipayFuncCode> operationResult = new OperationResult<>(JumpAlipayFuncCode.SUCCESS, ActionEnum.JUMP_ALIPAY_FUNC.getActionName());
        int parseInt = Integer.parseInt(jSONObject.optString("minVersionCode", "0"));
        if (parseInt <= 0) {
            parseInt = TransportMediator.KEYCODE_MEDIA_PLAY;
        }
        String optString = jSONObject.optString("source", "");
        Application a2 = LauncherApplication.a();
        try {
            WalletStatusEnum checkAlipayStatus = ApkVerifyTool.checkAlipayStatus(a2, parseInt);
            if (checkAlipayStatus != WalletStatusEnum.SUCCESS) {
                JumpAlipayFuncCode jumpAlipayFuncCode = JumpAlipayFuncCode.SUCCESS;
                switch (checkAlipayStatus) {
                    case SUCCESS:
                        jumpAlipayFuncCode = JumpAlipayFuncCode.SUCCESS;
                        break;
                    case NOT_INSTALL:
                        jumpAlipayFuncCode = JumpAlipayFuncCode.ALIPAY_NOT_INSTALL;
                        break;
                    case SIGN_ERROR:
                        jumpAlipayFuncCode = JumpAlipayFuncCode.ALIPAY_SIGN_ERROR;
                        break;
                    case VERSION_UNMATCH:
                        jumpAlipayFuncCode = JumpAlipayFuncCode.ALIPAY_VERSION_UNMATCH;
                        break;
                }
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("JumpAlipayFuncAction::adapterWalletStatus > code:");
                sb.append(jumpAlipayFuncCode.getValue());
                f.b((String) "inside", sb.toString());
                operationResult.setCode(jumpAlipayFuncCode);
            } else {
                String optString2 = jSONObject.optString("alipayFunc", "");
                LoggerFactory.d().a("afunc", BehaviorType.EVENT, "JumpAlipayFunc").g = optString2;
                new AFuncListProvider();
                String a3 = AFuncListProvider.a(optString2);
                if (TextUtils.isEmpty(a3)) {
                    ExceptionLogger e = LoggerFactory.e();
                    StringBuilder sb2 = new StringBuilder("alipayFunc:");
                    sb2.append(optString2);
                    sb2.append(",alipayScheme:");
                    sb2.append(a3);
                    e.a((String) "wallet", (String) "JumpAlipayFuncNoMatch", sb2.toString());
                    operationResult.setCode(JumpAlipayFuncCode.PARAMS_ILLEGAL);
                } else {
                    String e2 = StaticConfig.e();
                    if (a.contains(e2)) {
                        JumpWalletUtils.jumpWalletNewTask(a2, a3);
                        operationResult.setCode(JumpAlipayFuncCode.SUCCESS);
                        Behavior a4 = LoggerFactory.d().a("afunc", BehaviorType.EVENT, "JumpAlipayByIntent");
                        a4.g = optString2;
                        a4.h = e2;
                    } else {
                        if (!TextUtils.isEmpty(optString)) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(a3);
                            sb3.append("&sourceId=");
                            sb3.append(optString);
                            String sb4 = sb3.toString();
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(sb4);
                            sb5.append("&chInfo=");
                            sb5.append(optString);
                            a3 = sb5.toString();
                        }
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isDirectScheme", true);
                        bundle.putString("directScheme", a3);
                        AlipayServiceBinder.getInstance().invokeAlipayService(a2, bundle);
                        operationResult.setCode(JumpAlipayFuncCode.SUCCESS);
                    }
                }
            }
        } catch (Throwable th) {
            LoggerFactory.e().a((String) "wallet", (String) "JumpAlipayFuncActionEx", th);
            operationResult.setCode(JumpAlipayFuncCode.FAILED);
        }
        return operationResult;
    }
}
