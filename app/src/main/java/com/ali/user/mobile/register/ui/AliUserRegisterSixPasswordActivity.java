package com.ali.user.mobile.register.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.ali.user.mobile.base.BaseSixPasswordActivity;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.rpc.vo.mobilegw.GwCommonRes;
import com.ali.user.mobile.security.ui.R;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.autonavi.map.core.MapCustomizeManager;
import java.util.Map;

public class AliUserRegisterSixPasswordActivity extends BaseSixPasswordActivity {
    private static final String TAG = "AliUserRegisterSixPasswordActivity";

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LoggerUtils.a("", TAG, "login", "");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mTitleBar.hideBackButton();
    }

    public void onSupplement(String str) {
        AliUserLog.c(TAG, "start Supplement");
        LogAgent.b("UC-ZC-150512-14", "zcpwdok", "RegisterSetSixPassword", null, this.mToken);
        if (str != null) {
            try {
                final GwCommonRes a = AliuserLoginContext.e().a(this.mToken, str, "1", "", "", this.optionStatus);
                runOnUiThread(new Runnable() {
                    public void run() {
                        AliUserRegisterSixPasswordActivity.this.afterSupplement(a);
                    }
                });
            } catch (RpcException e) {
                dismissProgress();
                throw e;
            }
        } else {
            toast(getResources().getString(R.string.cD), 3000);
        }
    }

    /* access modifiers changed from: protected */
    public void afterSupplement(GwCommonRes gwCommonRes) {
        dismissProgress();
        if (gwCommonRes != null) {
            StringBuilder sb = new StringBuilder("Supplement result:");
            sb.append(gwCommonRes.resultStatus);
            sb.append(",msg:");
            sb.append(gwCommonRes.memo);
            AliUserLog.c(TAG, sb.toString());
            LogAgent.b("YWUC-JTTYZH-C26", "setPayPassword", "SetPassword", gwCommonRes.mobileNo, this.mToken);
            this.mToken = gwCommonRes.token;
            if (gwCommonRes.resultStatus == 200) {
                onSupplySuccess(gwCommonRes);
            } else {
                onSupplyFail(gwCommonRes);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSupplySuccess(GwCommonRes gwCommonRes) {
        Intent intent = new Intent(this, AliuserRegisterSuccessActivity.class);
        intent.putExtra("token", this.mToken);
        intent.putExtra("mobile_for_sms", gwCommonRes.mobileNo);
        Map<String, String> map = gwCommonRes.extInfos;
        if (!(map == null || map.get("enterText") == null)) {
            intent.putExtra("enterText", map.get(""));
        }
        startActivity(intent);
    }

    /* access modifiers changed from: protected */
    public void onSupplyFail(final GwCommonRes gwCommonRes) {
        this.mSixNumberInput.clearInput();
        if (gwCommonRes.resultStatus == 207) {
            AliUserLog.c(TAG, "token timeout");
            alert(null, gwCommonRes.memo, getResources().getString(R.string.H), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    AliUserRegisterSixPasswordActivity.this.goRegister();
                }
            }, null, null);
        } else if (gwCommonRes.resultStatus == 3032 || gwCommonRes.resultStatus == 3031) {
            alert(null, gwCommonRes.memo, getResources().getString(R.string.bp), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    AliUserRegisterSixPasswordActivity.this.goLogin(gwCommonRes.mobileNo, null, null);
                    AliUserRegisterSixPasswordActivity.this.finish();
                }
            }, null, null);
        } else {
            AliUserLog.c(TAG, "Supplement other error");
            toast(gwCommonRes.memo, 3000);
        }
    }

    /* access modifiers changed from: protected */
    public void goRegister() {
        RegContext.a().a(this, null, null);
    }

    /* access modifiers changed from: protected */
    public void goLogin(String str, String str2, String str3) {
        LoginParam loginParam = new LoginParam();
        loginParam.loginAccount = str;
        loginParam.token = str2;
        loginParam.validateTpye = str3;
        Intent a = AliuserLoginContext.a(getApplicationContext());
        a.putExtra("login_param", loginParam);
        a.putExtra("from_register", true);
        a.addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
        a.addFlags(536870912);
        startActivity(a);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public void setAppId() {
        this.mAppId = "20000009";
    }
}
