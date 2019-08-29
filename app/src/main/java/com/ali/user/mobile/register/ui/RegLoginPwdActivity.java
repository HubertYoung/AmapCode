package com.ali.user.mobile.register.ui;

import android.content.Intent;
import android.os.Bundle;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.common.ui.AbsSupplyLoginPwdActivity;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.register.model.SimpleRequest;
import com.ali.user.mobile.register.model.State;
import com.ali.user.mobile.register.router.IRouterHandler;
import com.ali.user.mobile.register.router.RouterPages;
import com.ali.user.mobile.register.store.ActionCenter;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import java.util.HashMap;

public class RegLoginPwdActivity extends AbsSupplyLoginPwdActivity implements IRouterHandler {
    private static final String sTag = "Reg_LoginPwd";

    public BaseActivity getActivity() {
        return this;
    }

    public void handleVerifySuccess(String str) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LoggerUtils.a("", "RegLoginPwdActivity", "login", "");
    }

    public String getPageName() {
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter == null) {
            return null;
        }
        State a = actionCenter.a();
        if (a == null || a.c == null) {
            return null;
        }
        return String.valueOf(a.c.resultStatus);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        RouterPages.a(this);
    }

    public void doSupply(String str) {
        AliUserLog.c(sTag, "do supply");
        hideErrorTip();
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter == null) {
            AliUserLog.d(sTag, "null action center");
            return;
        }
        SimpleRequest simpleRequest = new SimpleRequest();
        simpleRequest.e = str;
        simpleRequest.b = "setLoginPassword";
        try {
            if (simpleRequest.h == null) {
                simpleRequest.h = new HashMap();
                simpleRequest.h.put("isPrisonBreak", String.valueOf(OutsideConfig.i()));
                simpleRequest.h.put("mobileModel", DeviceInfo.a().j());
                simpleRequest.h.put("isTrojan", String.valueOf(OutsideConfig.j()));
                simpleRequest.h.put("currentOperateMobile", OutsideConfig.h());
            }
        } catch (Throwable th) {
            AliUserLog.a(sTag, "reg-supply loginpwd error", th);
        }
        actionCenter.a(simpleRequest, (BaseActivity) this);
    }

    public void setAppId() {
        this.mAppId = "20000009";
    }

    public boolean handleStateChange(State state) {
        AliUserLog.c(sTag, "handle state ".concat(String.valueOf(state)));
        if (state == null || -2 != state.b || state.c == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder("handle state, result code ");
        sb.append(state.c.resultStatus);
        AliUserLog.c(sTag, sb.toString());
        if (state.c.resultStatus == null) {
            return false;
        }
        int intValue = state.c.resultStatus.intValue();
        String str = state.c.memo;
        if (intValue != 3013 && intValue != 3021 && intValue != 3020 && intValue != 3019 && intValue != 3028 && intValue != 3030 && intValue != 3079 && intValue != 3039 && intValue != 3017) {
            return false;
        }
        showErrorTip(str, false);
        return true;
    }

    public void afterDialog() {
        if (this.mConfirm != null) {
            this.mConfirm.setEnabled(true);
        }
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (!this.mCanComeBack) {
            finish();
        }
    }
}
