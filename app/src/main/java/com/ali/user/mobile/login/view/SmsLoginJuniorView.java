package com.ali.user.mobile.login.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.login.ui.AliUserLoginActivity;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APTitleBar;
import com.ali.user.mobile.util.StringUtil;

public class SmsLoginJuniorView extends SmsLoginView {
    public String myName() {
        return "fromsmslogin2";
    }

    public SmsLoginJuniorView(AliUserLoginActivity aliUserLoginActivity) {
        super(aliUserLoginActivity);
    }

    /* access modifiers changed from: protected */
    public void uiCustomize() {
        super.uiCustomize();
        this.mLoginButton.setText(this.mAttatchActivity.getString(R.string.cB));
    }

    public void onViewStart() {
        LogAgent.a((String) "UC-LOG-161225-14", (String) "smslogintwopage", (String) "", (String) "");
        initPhoneAccountJunior();
        this.mMainTip.setVisibility(0);
        this.mMainTip.setText(this.mAttatchActivity.getString(R.string.cz));
        this.mAttatchActivity.setRightCornerViewEnable(false);
        this.mAccountImageView.setVisibility(8);
        this.mSmsRegView.setVisibility(8);
        APTitleBar titleBar = this.mAttatchActivity.getTitleBar();
        titleBar.setVisibility(0);
        if (titleBar.getTitlebarBg() != null) {
            titleBar.getTitlebarBg().setBackgroundColor(0);
        }
        this.mPhoneInputBox.getPhoneInput().addTextChangedListener(getPhoneChangedListener());
        setScrollBound();
        requestAccountFocus();
    }

    public void onViewRestart() {
        updateParam(this.mAttatchActivity.getIntent());
        onViewStart();
    }

    public void onViewStop() {
        super.onViewStop();
        this.mAttatchActivity.getIntent().putExtra("accountBetweenView", getLoginAccount());
        this.mAttatchActivity.getIntent().putExtra("facadeBetweenView", getShownAccount());
    }

    /* access modifiers changed from: protected */
    public void initPhoneAccountJunior() {
        String str = "";
        try {
            str = this.mAttatchActivity.getIntent().getStringExtra("accountBetweenView");
        } catch (Throwable th) {
            AliUserLog.b("SmsLoginJuniorView", "get intent", th);
        }
        if (TextUtils.isEmpty(str) || !StringUtil.d(str)) {
            this.mLoginAccount = "";
            setFacade("");
            return;
        }
        this.mLoginAccount = str;
        String str2 = "";
        try {
            str2 = this.mAttatchActivity.getIntent().getStringExtra("facadeBetweenView");
        } catch (Throwable th2) {
            AliUserLog.b("SmsLoginJuniorView", "get intent", th2);
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = this.mLoginAccount;
        }
        setFacade(str2);
    }

    /* access modifiers changed from: protected */
    public void doInflate(Context context) {
        super.doInflate(context);
        this.mPhoneInputBox.setPageName("fromsms2");
    }

    public void onClick(View view) {
        if (R.id.ax == view.getId()) {
            LogAgent.a((String) "UC-LOG-161225-15", (String) "smslogintwo", (String) "", (String) "");
            this.mAttatchActivity.onRdsControlClick("smsLoginBtn");
            initMsgLogin(getLoginAccount(), null, null);
            return;
        }
        super.onClick(view);
    }
}
