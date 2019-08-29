package com.ali.user.mobile.register.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.common.ui.AbsVerifySmsActivity;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.register.LogUtils;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.register.model.SimpleRequest;
import com.ali.user.mobile.register.model.State;
import com.ali.user.mobile.register.router.IRouterHandler;
import com.ali.user.mobile.register.router.RouterPages;
import com.ali.user.mobile.register.store.ActionCenter;

public class RegManualSmsActivity extends AbsVerifySmsActivity implements IRouterHandler {
    private static final String sTag = "Reg_ManualSms";

    public void afterDialog() {
    }

    public BaseActivity getActivity() {
        return this;
    }

    public void handleVerifySuccess(String str) {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LogUtils.b("UC-ZC-161223-01", "zcsms");
        LoggerUtils.a("", "RegManualSmsActivity", "login", "");
    }

    public void onResume() {
        super.onResume();
        RouterPages.a(this);
    }

    public void onBackPressed() {
        super.onBackPressed();
        LogUtils.a("UC-ZC-150512-11", "zcsmsback");
    }

    public void verifySms(String str) {
        AliUserLog.c(sTag, "verify sms");
        hideError();
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter == null) {
            AliUserLog.d(sTag, "verify sms, null action center");
            return;
        }
        SimpleRequest simpleRequest = new SimpleRequest();
        simpleRequest.c = str;
        simpleRequest.b = "verifySms";
        actionCenter.a(simpleRequest, (BaseActivity) this);
        LogUtils.a("UC-ZC-150512-13", "zcsmsnext");
    }

    public void sendSms() {
        AliUserLog.c(sTag, "send sms");
        LogUtils.a("UC-ZC-150512-12", "zcsmsredo");
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter == null) {
            AliUserLog.d(sTag, "send sms, null action center");
            return;
        }
        SimpleRequest simpleRequest = new SimpleRequest();
        simpleRequest.b = "sendSms";
        actionCenter.a(simpleRequest, (BaseActivity) this);
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
        finish();
    }

    public void setAppId() {
        this.mAppId = "20000009";
    }

    public boolean handleStateChange(State state) {
        showHint();
        AliUserLog.c(sTag, "handle state ".concat(String.valueOf(state)));
        if (state == null) {
            return false;
        }
        if (state.c == null) {
            showHint();
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
        if (TextUtils.equals(state.d, "verifySms") && 200 != intValue) {
            AliUserLog.d(sTag, "verify sms, failed clear input");
            clearInput();
        }
        if (TextUtils.equals(state.d, "sendSms") && 200 == intValue) {
            startCountDown();
            callUpKeyboard();
            return true;
        } else if (!TextUtils.equals(state.d, "verifySms") || 2007 != intValue) {
            return false;
        } else {
            showError(str);
            return true;
        }
    }

    public void onGoBack() {
        super.onGoBack();
        LogUtils.a("UC-ZC-161225-06", "zcmessageback");
    }

    public void onWait() {
        super.onWait();
        LogUtils.a("UC-ZC-161225-05", "zcmessagewait");
    }
}
