package com.ali.user.mobile.external.accountmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ali.user.mobile.accountbiz.extservice.AccountService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.accountbiz.sp.SecurityShareStore;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.external.LoginPreCheckActivity;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.ui.widget.APListView;
import com.ali.user.mobile.ui.widget.APNoticePopDialog;
import com.ali.user.mobile.ui.widget.APNoticePopDialog.OnClickPositiveListener;
import com.ali.user.mobile.ui.widget.APTitleBar;
import com.ali.user.mobile.ui.widget.ImageLoader;
import com.ali.user.mobile.util.StringUtil;
import com.ali.user.mobile.utils.CommonUtil;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.util.List;

public class AccountManagerActivity extends BaseActivity {
    private static final int ACTION_GUIDE_INSTALL = 1001;
    private static final int ACTION_LOGOUT = 1000;
    private static final String TAG = "AccountManagerActivity";
    private AccountService mAccountService;
    private AccountManagerListAdapter mAdapter;
    private List<AuthInfo> mAuthInfoList;
    private APListView mAuthListView;
    private TextView mAuthTip;
    private ImageView mAvatar;
    private View mHasLoginView;
    private View mHasNotLoginView;
    private String mInsideLoginType;
    private Button mLoginBtn;
    private TextView mLoginIdTV;
    private Button mLogoutBtn;

    public void setAppId() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (LauncherApplication.a() == null) {
            AliUserLog.c(TAG, "LauncherApplication.getInstance() == null finish");
            finish();
            return;
        }
        setContentView(R.layout.d);
        initParams();
        showProgress("");
        findViews();
        setClickListener();
        this.mAdapter = new AccountManagerListAdapter(getApplicationContext());
        initViews();
        setExclude(true);
    }

    private void initParams() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                this.mInsideLoginType = extras.getString("insideLoginType");
                SecurityShareStore.a(LauncherApplication.a().getApplicationContext(), (String) "insideLoginType", this.mInsideLoginType);
                StringBuilder sb = new StringBuilder("mInsideLoginType = ");
                sb.append(this.mInsideLoginType);
                AliUserLog.c(TAG, sb.toString());
            }
        }
    }

    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    private void findViews() {
        ((APTitleBar) findViewById(R.id.a)).setEnabled(true);
        this.mHasNotLoginView = findViewById(R.id.v);
        this.mLoginBtn = (Button) this.mHasNotLoginView.findViewById(R.id.s);
        this.mHasLoginView = findViewById(R.id.r);
        this.mAuthListView = (APListView) this.mHasLoginView.findViewById(R.id.o);
        this.mAuthTip = (TextView) this.mHasLoginView.findViewById(R.id.p);
        this.mLogoutBtn = (Button) this.mHasLoginView.findViewById(R.id.u);
        this.mLoginIdTV = (TextView) this.mHasLoginView.findViewById(R.id.t);
        this.mAvatar = (ImageView) this.mHasLoginView.findViewById(R.id.x);
        this.mAccountService = AntExtServiceManager.getAccountService(LauncherApplication.a());
    }

    private void setClickListener() {
        this.mLoginBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AccountManagerActivity.this.doLogin();
            }
        });
        this.mLogoutBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                AccountManagerActivity.this.showLogoutOrInstallDialog(1000, AccountManagerActivity.this.getResources().getString(R.string.n), AccountManagerActivity.this.getResources().getString(R.string.k), AccountManagerActivity.this.getResources().getString(R.string.j));
            }
        });
    }

    /* access modifiers changed from: private */
    public void doLogin() {
        boolean a = CommonUtil.a(LauncherApplication.a().getApplicationContext());
        if (!"withoutPwd".equals(this.mInsideLoginType) || a) {
            AliuserLoginContext.a((IInsideServiceCallback) new IInsideServiceCallback() {
                public void onException(Throwable th) {
                }

                public void onComplted(Object obj) {
                    AccountManagerActivity.this.showProgress("");
                    if (obj instanceof Bundle) {
                        String string = ((Bundle) obj).getString("loginStatus");
                        if ("success".equals(string)) {
                            AccountManagerActivity.this.refreshViews(true);
                        } else if ("cancel".equals(string)) {
                            AliUserLog.c(AccountManagerActivity.TAG, "login result : cancel");
                            AccountManagerActivity.this.dismissProgress();
                        } else {
                            AccountManagerActivity.this.toast(AccountManagerActivity.this.getResources().getString(R.string.bE));
                            AccountManagerActivity.this.dismissProgress();
                        }
                    }
                }
            });
            Intent intent = new Intent(LauncherApplication.a(), LoginPreCheckActivity.class);
            intent.putExtra("insideLoginType", this.mInsideLoginType);
            intent.setFlags(268435456);
            LauncherApplication.a().startActivity(intent);
            return;
        }
        AliUserLog.c(TAG, "alipay not install,guide download install");
        showLogoutOrInstallDialog(1001, getResources().getString(R.string.m), getResources().getString(R.string.l), getResources().getString(R.string.j));
    }

    /* access modifiers changed from: private */
    public void showLogoutOrInstallDialog(final int i, String str, String str2, String str3) {
        APNoticePopDialog aPNoticePopDialog = new APNoticePopDialog(this, null, str, str2, str3);
        aPNoticePopDialog.a((OnClickPositiveListener) new OnClickPositiveListener() {
            public void onClick() {
                if (1000 == i) {
                    AccountManagerActivity.this.showProgress("");
                    AccountManagerActivity.this.doLogout();
                    AccountManagerActivity.this.refreshViews(false);
                    return;
                }
                if (1001 == i) {
                    CommonUtil.b(AccountManagerActivity.this);
                }
            }
        });
        aPNoticePopDialog.show();
        aPNoticePopDialog.setCanceledOnTouchOutside(true);
        aPNoticePopDialog.setCancelable(true);
    }

    /* access modifiers changed from: private */
    public void doLogout() {
        AntExtServiceManager.getLogoutService(LauncherApplication.a()).logout(null);
    }

    private void initViews() {
        boolean isLogin = AntExtServiceManager.getAuthService(LauncherApplication.a()).isLogin();
        AliUserLog.c(TAG, "isLogin:".concat(String.valueOf(isLogin)));
        refreshViews(isLogin);
    }

    /* access modifiers changed from: private */
    public void refreshViews(boolean z) {
        if (z) {
            this.mAuthListView.setAdapter(this.mAdapter);
            this.mAuthInfoList = AuthInfoListHelper.getAuthInfoList();
            this.mAdapter.setData(this.mAuthInfoList);
            this.mLoginIdTV.setText(StringUtil.a(this.mAccountService.getCurrentLoginLogonId(), (String) BehavorReporter.PROVIDE_BY_ALIPAY));
            ImageLoader.a(this.mAccountService.queryAccountDetailInfoByUserId(this.mAccountService.getCurrentLoginUserId()).getUserAvatar(), this.mAvatar, getResources().getDrawable(R.drawable.y));
            if (this.mAuthInfoList == null || this.mAuthInfoList.isEmpty()) {
                this.mAuthTip.setText(getResources().getString(R.string.e));
            } else {
                this.mAuthTip.setText(getResources().getString(R.string.d));
            }
            this.mHasLoginView.setVisibility(0);
            this.mHasNotLoginView.setVisibility(8);
        } else {
            this.mAvatar.setImageResource(R.drawable.y);
            this.mLoginIdTV.setText(getResources().getString(R.string.f));
            this.mHasNotLoginView.setVisibility(0);
            this.mHasLoginView.setVisibility(8);
        }
        dismissProgress();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
