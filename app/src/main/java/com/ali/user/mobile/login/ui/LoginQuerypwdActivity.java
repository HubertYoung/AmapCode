package com.ali.user.mobile.login.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.common.ui.AbsSupplyLoginPwdActivity;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.SupplyQueryPasswordService;
import com.ali.user.mobile.rpc.vo.mobilegw.RSAPKeyResult;
import com.ali.user.mobile.rpc.vo.mobilegw.login.SupplyLoginPwdResPb;
import com.ali.user.mobile.rsa.Rsa;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.toast.SimpleToast;
import com.ali.user.mobile.util.StringUtil;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;

public class LoginQuerypwdActivity extends AbsSupplyLoginPwdActivity {
    private static final String TAG = "LoginQuerypwdActivity";
    private String mLoginId;
    private String mScene;
    private int mSupplyResult = -1;
    private String[] toastCodes = {"6451", "6457"};

    public String getPageName() {
        return "login";
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            this.mLoginId = getIntent().getStringExtra("loginId");
            this.mScene = getIntent().getStringExtra(H5AppUtil.scene);
        } catch (Throwable th) {
            AliUserLog.b(TAG, "get intent", th);
        }
        initTitleBarView();
        initMainTip();
        setExclude(true);
        SupplyQueryPasswordService.a().a(this);
        LoggerUtils.a("", TAG, "login", "");
    }

    public String getInputHint() {
        return getResources().getString(R.string.cf);
    }

    private void initTitleBarView() {
        this.mTitle.setVisibility(0);
        if (this.mTitle.getTitlebarBg() != null) {
            this.mTitle.getTitlebarBg().setBackgroundColor(-1);
        }
        this.mTitle.setBackButtonListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    ((InputMethodManager) LoginQuerypwdActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                } catch (Throwable th) {
                    AliUserLog.a(LoginQuerypwdActivity.TAG, "hide softinput error", th);
                }
                LoginQuerypwdActivity.this.onSupplyPasswordCancel();
            }
        });
    }

    private void initMainTip() {
        String a = StringUtil.e(this.mLoginId) ? StringUtil.a(this.mLoginId, 4) : this.mLoginId;
        String format = String.format(getString(R.string.bD), new Object[]{a});
        int indexOf = format.indexOf(a);
        SpannableString spannableString = new SpannableString(format);
        spannableString.setSpan(new ForegroundColorSpan(-16777216), indexOf, a.length() + indexOf, 33);
        spannableString.setSpan(new StyleSpan(1), indexOf, a.length() + indexOf, 33);
        setMainTip(spannableString);
    }

    public void doSupply(final String str) {
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                LoginQuerypwdActivity.this.supplyBackground(str);
            }
        });
    }

    /* access modifiers changed from: private */
    public void supplyBackground(String str) {
        AliUserLog.c(TAG, "start to supply querypwd");
        try {
            showProgress("");
            setButtonEnable(this.mConfirm, true);
            RSAPKeyResult a = Rsa.a(getApplicationContext());
            if (a == null) {
                dismissProgress();
                throw new IllegalArgumentException("get rsa from server failed!!!");
            }
            final SupplyLoginPwdResPb a2 = AliuserLoginContext.d().a(this.mLoginId, this.mScene, Rsa.a(str, a.rsaPK));
            dismissProgress();
            runOnUiThread(new Runnable() {
                public void run() {
                    LoginQuerypwdActivity.this.onSupplyResponse(a2);
                }
            });
        } catch (RpcException e) {
            dismissProgress();
            throw e;
        }
    }

    /* access modifiers changed from: private */
    public void onSupplyResponse(SupplyLoginPwdResPb supplyLoginPwdResPb) {
        if ("1000".equals(supplyLoginPwdResPb.resultCode)) {
            SimpleToast.a(this, R.drawable.Q, supplyLoginPwdResPb.memo, 0).show();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                public void run() {
                    LoginQuerypwdActivity.this.onSupplyPasswordSuccess();
                }
            }, 1000);
        } else if (isToastCode(supplyLoginPwdResPb.resultCode)) {
            toast(supplyLoginPwdResPb.memo);
        } else {
            showErrorTip(supplyLoginPwdResPb.memo, false);
        }
    }

    private boolean isToastCode(String str) {
        for (String equals : this.toastCodes) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void onSupplyPasswordSuccess() {
        this.mSupplyResult = 1000;
        SupplyQueryPasswordService.a().a(this.mSupplyResult);
    }

    /* access modifiers changed from: private */
    public void onSupplyPasswordCancel() {
        this.mSupplyResult = 1001;
        SupplyQueryPasswordService.a().a(this.mSupplyResult);
        finish();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        onSupplyPasswordCancel();
        return true;
    }

    public void setAppId() {
        this.mAppId = "20000008";
    }
}
