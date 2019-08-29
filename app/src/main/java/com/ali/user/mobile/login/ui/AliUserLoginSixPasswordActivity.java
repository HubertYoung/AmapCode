package com.ali.user.mobile.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.ali.user.mobile.base.BaseSixPasswordActivity;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.rpc.vo.mobilegw.SupplyPassGwReq;
import com.ali.user.mobile.rpc.vo.mobilegw.SupplyPassGwRes;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.security.ui.R;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.inside.android.phone.mrpc.core.RpcException;

public class AliUserLoginSixPasswordActivity extends BaseSixPasswordActivity {
    private static final String TAG = "AliUserLoginSixPasswordActivity";

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LoggerUtils.a("", TAG, "login", "");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mTitleBar.setBackButtonListener(new OnClickListener() {
            public void onClick(View view) {
                AliUserLoginSixPasswordActivity.this.setResult(0);
                AliUserLoginSixPasswordActivity.this.finish();
            }
        });
    }

    public void onSupplement(String str) {
        AliUserLog.c(TAG, "start doSupplement");
        if (str != null) {
            try {
                SupplyPassGwReq supplyPassGwReq = new SupplyPassGwReq();
                supplyPassGwReq.token = this.mToken;
                supplyPassGwReq.paymentPassword = str;
                supplyPassGwReq.simplePassword = "1";
                supplyPassGwReq.optionStatus = this.optionStatus;
                final SupplyPassGwRes a = this.mUserLoginService.a(supplyPassGwReq);
                runOnUiThread(new Runnable() {
                    public void run() {
                        AliUserLoginSixPasswordActivity.this.afterSupplyment(a);
                    }
                });
            } catch (RpcException e) {
                dismissProgress();
                throw e;
            }
        } else {
            dismissProgress();
            toast(getResources().getString(R.string.cD), 3000);
        }
    }

    /* access modifiers changed from: protected */
    public void afterSupplyment(SupplyPassGwRes supplyPassGwRes) {
        StringBuilder sb = new StringBuilder("afterSupplyment,code:");
        sb.append(supplyPassGwRes.code);
        sb.append(",msg:");
        sb.append(supplyPassGwRes.msg);
        AliUserLog.c(TAG, sb.toString());
        if (supplyPassGwRes.success) {
            AliUserLog.c(TAG, "Supplyment success");
            doBackgroundLogin();
            return;
        }
        AliUserLog.c(TAG, "Supplyment fail");
        dismissProgress();
        this.mSixNumberInput.clearInput();
        if ("6213".equals(supplyPassGwRes.code)) {
            AliUserLog.c(TAG, "session timeout");
            alertResult(supplyPassGwRes.msg, SecExceptionCode.SEC_ERROR_GENERIC_AVMP_UNKNOWN_ERROR);
            return;
        }
        AliUserLog.c(TAG, "Supplyment other error");
        toast(supplyPassGwRes.msg, 3000);
    }

    public boolean onLoginFail(UnifyLoginRes unifyLoginRes) {
        if (!"6203".equals(unifyLoginRes.code) && !"6202".equals(unifyLoginRes.code) && !"6207".equals(unifyLoginRes.code)) {
            return false;
        }
        Intent intent = new Intent();
        intent.putExtra("login_response", unifyLoginRes);
        setResult(1998, intent);
        finish();
        return true;
    }

    public void closeKeyboard() {
        closeInputMethod(this.mSixNumberInput.getEditText());
    }

    public void setAppId() {
        this.mAppId = "20000008";
    }
}
