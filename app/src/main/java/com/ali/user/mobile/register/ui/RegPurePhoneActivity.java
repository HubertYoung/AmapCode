package com.ali.user.mobile.register.ui;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.biz.ReportLocationServiceWrapper;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.rds.RDSWraper;
import com.ali.user.mobile.register.Account;
import com.ali.user.mobile.register.LogUtils;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.register.TaoUrlSpan;
import com.ali.user.mobile.register.model.SimpleRequest;
import com.ali.user.mobile.register.model.State;
import com.ali.user.mobile.register.model.StateUtils;
import com.ali.user.mobile.register.router.IRouterHandler;
import com.ali.user.mobile.register.router.RouterPages;
import com.ali.user.mobile.register.store.ActionCenter;
import com.ali.user.mobile.resolver.ConfigResolver;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APTitleBar;
import com.ali.user.mobile.ui.widget.AUPhoneInputBox;
import com.ali.user.mobile.ui.widget.AUPhoneInputBox.IPhoneChangeListener;
import com.ali.user.mobile.util.ResizeScrollView;
import com.ali.user.mobile.util.ShowRegionHelper;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.location.common.model.AmapLoc;

public class RegPurePhoneActivity extends BaseActivity implements OnClickListener, IRouterHandler {
    private static final String sTag = "Reg_PurePhone";
    private Button mConfirm;
    private AUPhoneInputBox mPhone;
    private EditText mPhoneInput;
    protected TextView mProtocol;
    protected RDSWraper mRdsWraper;
    private ResizeScrollView mScrollView;

    public BaseActivity getActivity() {
        return this;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.j);
        initContext();
        initView();
        initRds();
        this.mPhone.setRdsWrapper(this.mRdsWraper);
        LogUtils.c("UC-ZC-150512-01", "registerpage", AmapLoc.TYPE_NEW, null);
        LogUtils.b("UC-ZC-150512-01-old", "registerpage-old", AmapLoc.TYPE_NEW, null);
        LoggerUtils.a("", "RegPurePhoneActivity", "login", "");
    }

    public void setAppId() {
        this.mAppId = "20000009";
    }

    public void onStart() {
        super.onStart();
        resetToken();
        preFillPhoneNum();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        RouterPages.a(this);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        if (this.mPhoneInput != null) {
            closeInputMethod(this.mPhoneInput);
        }
        super.onStop();
    }

    private void resetToken() {
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter == null) {
            AliUserLog.d(sTag, "null action center");
        } else {
            actionCenter.a(false);
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void onBackPressed() {
        super.onBackPressed();
        StateUtils.c();
        LogUtils.a("UC-ZC-150512-04", "zcback", TextUtils.isEmpty(getInputPhoneNo()) ? "YES" : "NO", null);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    private void initRds() {
        this.mRdsWraper.initPage(getApplicationContext(), getInputPhoneNo(), "RegisterPage");
        this.mRdsWraper.initTextChange(this.mPhoneInput, "UsernameET");
        this.mRdsWraper.initFocusChange(this.mPhoneInput, "UsernameET");
        this.mRdsWraper.initScreenTouch(this.mScrollView, "RegisterViewContainer");
    }

    public void finish() {
        if (this.mRdsWraper != null) {
            this.mRdsWraper.onControlClick("RetLoginBtn");
        }
        super.finish();
    }

    private void initContext() {
        this.mRdsWraper = new RDSWraper(getApplicationContext(), "RegisterPage");
    }

    private void initView() {
        initTitleBar();
        initPhoneAndRegion();
        initButton();
        initNewPrivacyConfig();
        initProtocol();
        initWrapper();
    }

    private void initWrapper() {
        this.mScrollView = (ResizeScrollView) findViewById(R.id.bz);
        new ShowRegionHelper(this.mScrollView).a(this.mPhoneInput, this.mConfirm, true);
        View findViewById = findViewById(R.id.cM);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    RegPurePhoneActivity.this.closeInputMethod(view);
                }
            });
        }
    }

    private void initNewPrivacyConfig() {
        try {
            String a = ConfigResolver.a(getApplicationContext(), "new_privacy_enable");
            RegContext.a().a(a);
            AliUserLog.c(sTag, "new privacy enable ".concat(String.valueOf(a)));
        } catch (Throwable th) {
            AliUserLog.b(sTag, "initNewPrivacyConfig error", th);
        }
    }

    private void initProtocol() {
        int i;
        int i2;
        int i3;
        int i4;
        this.mProtocol = (TextView) findViewById(R.id.bR);
        String string = getResources().getString(R.string.cc);
        String string2 = getResources().getString(R.string.cv);
        String string3 = getResources().getString(R.string.cQ);
        String string4 = getResources().getString(R.string.cO);
        String string5 = getResources().getString(R.string.G);
        if (string.contains("$alipay")) {
            if (RegContext.a().b()) {
                string = string.replace("$alipay", string5);
            } else {
                string = string.replace("$alipay", string2);
            }
        }
        if (string.contains("$taobao")) {
            string = string.replace("$taobao", string3);
        }
        if (string.contains("$ant")) {
            if (RegContext.a().b()) {
                string = string.replace("$ant", string4);
            } else {
                string = string.replace("、$ant", "").replace(", $ant", "");
            }
        }
        if (RegContext.a().b()) {
            i2 = string.indexOf(string5);
            i = string5.length() + i2;
        } else {
            int indexOf = string.indexOf(string2);
            int i5 = indexOf;
            i = string2.length() + indexOf;
            i2 = i5;
        }
        int indexOf2 = string.indexOf(string3);
        int length = string3.length() + indexOf2;
        if (RegContext.a().b()) {
            i3 = string.indexOf(string4);
            i4 = string4.length() + i3;
        } else {
            i4 = 0;
            i3 = 0;
        }
        int e = UIConfigManager.e();
        if (e == Integer.MAX_VALUE) {
            e = getResources().getColor(R.color.k);
        }
        SpannableString spannableString = new SpannableString(string);
        if (RegContext.a().b()) {
            spannableString.setSpan(new TaoUrlSpan("https://render.alipay.com/p/f/fd-jldmt3yw/index.html", "alipayclientagreement").setContext(this), i2, i, 33);
            spannableString.setSpan(new ForegroundColorSpan(e), i2, i, 33);
        } else {
            spannableString.setSpan(new TaoUrlSpan("https://ds.alipay.com/fd-inhm9zcq/index.html", "alipayagreement").setContext(this), i2, i, 33);
            spannableString.setSpan(new ForegroundColorSpan(e), i2, i, 33);
        }
        spannableString.setSpan(new TaoUrlSpan("https://tms.alicdn.com/go/chn/member/agreement.php", "taobaoagreement").setContext(this), indexOf2, length, 33);
        spannableString.setSpan(new ForegroundColorSpan(e), indexOf2, length, 33);
        if (RegContext.a().b()) {
            spannableString.setSpan(new TaoUrlSpan("https://render.alipay.com/p/f/fd-iztnkm19/index.html", "antagreement").setContext(this), i3, i4, 33);
            spannableString.setSpan(new ForegroundColorSpan(e), i3, i4, 33);
        }
        this.mProtocol.setText(spannableString);
        this.mProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        this.mProtocol.setVisibility(0);
    }

    private void initPhoneAndRegion() {
        this.mPhone = (AUPhoneInputBox) findViewById(R.id.bk);
        this.mPhone.setPageName("fromregfirstpage");
        this.mPhoneInput = this.mPhone.getPhoneInput();
        this.mPhone.setPhoneChangeListener(new IPhoneChangeListener() {
            public final void a(String str, String str2, String str3) {
                ActionCenter actionCenter = RegContext.a().c;
                if (actionCenter == null) {
                    AliUserLog.d(RegPurePhoneActivity.sTag, "update account, null action center");
                } else {
                    actionCenter.a(str, str2, str3);
                }
            }
        });
        this.mPhone.attatchActivity(this);
        showInputMethodPannel(this.mPhoneInput);
    }

    private void initButton() {
        this.mConfirm = (Button) findViewById(R.id.aZ);
        this.mConfirm.setOnClickListener(this);
        UIConfigManager.a(this.mConfirm);
        this.mPhone.addNeedEnabledButton(this.mConfirm);
    }

    private void initTitleBar() {
        ((APTitleBar) findViewById(R.id.cE)).getTitlebarBg().setBackgroundColor(-1);
    }

    private void preFillPhoneNum() {
        State state;
        ActionCenter actionCenter = RegContext.a().c;
        Account account = null;
        if (actionCenter == null) {
            state = null;
        } else {
            state = actionCenter.a();
        }
        if (state != null) {
            account = state.a();
        }
        if (account == null || account.isAllEmpty()) {
            AliUserLog.c(sTag, "prefill, auto fill");
            this.mPhone.setContent(getResources().getString(R.string.bM), getResources().getString(R.string.C), "");
            return;
        }
        AliUserLog.c(sTag, "prefill, use account from state");
        String str = "";
        if (!TextUtils.isEmpty(account.getAccountForRPC())) {
            str = account.getAccountForRPC();
        }
        if (!TextUtils.isEmpty(account.getAreaCodeForRPC())) {
            this.mPhone.setContent(account.getFullAreaCode(), account.getAreaName(), str);
        }
    }

    private String getInputPhoneNo() {
        return (this.mPhone == null || this.mPhone.getInputPhoneNo() == null) ? "" : this.mPhone.getInputPhoneNo().replace(Token.SEPARATOR, "");
    }

    public void onClick(View view) {
        if (view != null && R.id.aZ == view.getId()) {
            this.mConfirm.setEnabled(false);
            closeInputMethod(this.mPhoneInput);
            if (this.mRdsWraper != null) {
                this.mRdsWraper.onControlClick("RegisterBtn");
            }
            verifyAndSendSms(null);
            LogUtils.a("UC-ZC-150512-06", "zcnext");
        }
    }

    private void verifyAndSendSms(String str) {
        ActionCenter actionCenter = RegContext.a().c;
        if (actionCenter == null) {
            AliUserLog.d(sTag, "send sms, null action center");
            return;
        }
        SimpleRequest simpleRequest = new SimpleRequest();
        simpleRequest.f = str;
        simpleRequest.b = "registerPreVerify";
        if (!(this.mRdsWraper == null || this.mPhoneInput == null)) {
            simpleRequest.d = this.mRdsWraper.getRdsData(this, this.mPhoneInput.getText().toString());
        }
        reportDeviceLocation();
        actionCenter.a(simpleRequest, (BaseActivity) this);
    }

    private void reportDeviceLocation() {
        new ReportLocationServiceWrapper();
        ReportLocationServiceWrapper.a("regist");
    }

    public boolean handleStateChange(State state) {
        if (state == null || -2 != state.b || state.c == null || state.c.resultStatus == null) {
            return false;
        }
        int intValue = state.c.resultStatus.intValue();
        String str = state.c.memo;
        if (!(intValue == 2001 || intValue == 2003 || intValue == 3003)) {
            if (intValue != 3059) {
                if (intValue != 3081) {
                    switch (intValue) {
                        case 1121:
                        case 1122:
                            break;
                        default:
                            return false;
                    }
                }
            }
            alert(str, getResources().getString(R.string.bt));
            return true;
        }
        resetToken();
        return false;
    }

    public void alert(String str, String str2) {
        alert(str, "", str2, null, "", null);
    }

    public void handleVerifySuccess(String str) {
        verifyAndSendSms(str);
    }

    public void afterDialog() {
        if (this.mConfirm != null) {
            this.mConfirm.setEnabled(true);
        }
    }
}
