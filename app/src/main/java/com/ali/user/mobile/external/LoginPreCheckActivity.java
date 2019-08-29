package com.ali.user.mobile.external;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.ali.user.mobile.accountbiz.extservice.ServerConfigService;
import com.ali.user.mobile.accountbiz.extservice.manager.AntExtServiceManager;
import com.ali.user.mobile.authlogin.AlipaySsoAPIFactory;
import com.ali.user.mobile.authlogin.auth.IAlipaySSOPreHandler;
import com.ali.user.mobile.biz.AliUserSdkLoginBiz;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.external.facade.PreUserInfoFacade;
import com.ali.user.mobile.external.model.ExternMap;
import com.ali.user.mobile.external.model.SuggestLoginUserReqPb;
import com.ali.user.mobile.external.model.SuggestLoginUserResPb;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.app.LoginAppService;
import com.ali.user.mobile.login.sso.impl.SsoServiceImpl;
import com.ali.user.mobile.login.sso.info.SsoLoginInfo;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.utils.CommonUtil;
import com.alipay.android.phone.inside.cashier.PhoneCashierPlugin;
import com.alipay.android.phone.inside.cashier.service.InsideServiceGetTid;
import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class LoginPreCheckActivity extends BaseExternalActivity {
    private static final int DO_ALIPAY_AUTH_LOGIN = 2003;
    private static final int DO_ALIPAY_NOT_INSTALL = 2006;
    private static final int DO_CALLBACK_FAILED = 2005;
    private static final int DO_SND_LOGIN = 2004;
    private static final int DO_SSO_TOKEN_LOGIN = 2002;
    private static final String LOGIN_BY_SSO_TOKEN = "BY_SSO_TOKEN";
    private static final int PRE_CHECK_RESULT = 11;
    /* access modifiers changed from: private */
    public static final String TAG = "LoginPreCheckActivity";
    private static final long WAIT_TIME = 2000;
    Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i != 11) {
                switch (i) {
                    case 2002:
                        LoginPreCheckActivity.this.doSsoTokenLogin((SsoLoginInfo) message.obj);
                        return;
                    case 2003:
                        LoginPreCheckActivity.this.doAlipayAuthLogin();
                        return;
                    case 2004:
                        LoginPreCheckActivity.this.startRemoteCheck();
                        return;
                    case 2005:
                        LoginPreCheckActivity.this.doCallback("other_failed");
                        break;
                    case 2006:
                        LoginPreCheckActivity.this.doCallback("alipay_not_install");
                        return;
                }
                return;
            }
            LoginPreCheckActivity.this.processSuggestReponse(message.obj);
        }
    };
    private String mCurrentLoginValidateType;
    /* access modifiers changed from: private */
    public String mInsideLoginSceneType;
    private String mOpenMCMobileNumber;
    private String mOpenMcAccount;
    private String mOpenMobileNumber;
    private SsoServiceImpl mSsoService;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (LauncherApplication.a() == null) {
            AliUserLog.c(TAG, "LauncherApplication.getInstance() == null finish");
            finish();
            return;
        }
        showProgress("");
        Intent intent = getIntent();
        if (intent != null) {
            this.mOpenMobileNumber = intent.getStringExtra("openMobileNumber");
            this.mOpenMCMobileNumber = intent.getStringExtra("openMcMobileNumber");
            this.mOpenMcAccount = intent.getStringExtra("openMcAccount");
            this.mInsideLoginSceneType = intent.getStringExtra("insideLoginType");
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder("init login pre check,mInsideLoginSceneType=");
        sb.append(this.mInsideLoginSceneType);
        AliUserLog.c(str, sb.toString());
        this.mSsoService = SsoServiceImpl.a(getApplicationContext());
        AliUserSdkLoginBiz.a((Context) LauncherApplication.a());
        LoggerUtils.a("", TAG, "login", this.mInsideLoginSceneType);
        initRdsPage("");
        loginFlowJudge();
    }

    public void onStart() {
        super.onStart();
    }

    private void loginFlowJudge() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if ("withoutPwd".equals(LoginPreCheckActivity.this.mInsideLoginSceneType)) {
                        if (LoginPreCheckActivity.this.isAlipayInstall()) {
                            LoginPreCheckActivity.this.doAuthOrSsoLogin();
                        } else {
                            LoginPreCheckActivity.this.handler.sendEmptyMessage(2006);
                        }
                    } else if (LoginPreCheckActivity.this.isAlipayInstall()) {
                        LoginPreCheckActivity.this.doAuthOrSsoLogin();
                    } else {
                        LoginPreCheckActivity.this.handler.sendEmptyMessage(2004);
                    }
                } catch (Throwable th) {
                    AliUserLog.b(LoginPreCheckActivity.TAG, "loginFlowJudge thread error", th);
                    LoginPreCheckActivity.this.doCallback("other_failed");
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void doAuthOrSsoLogin() {
        SsoLoginInfo sSOLoginInfo = getSSOLoginInfo();
        if (sSOLoginInfo == null) {
            this.handler.sendEmptyMessage(2003);
        } else {
            this.handler.sendMessage(this.handler.obtainMessage(2002, sSOLoginInfo));
        }
    }

    /* access modifiers changed from: private */
    public boolean isAlipayInstall() {
        return CommonUtil.a(LauncherApplication.a().getApplicationContext());
    }

    private SsoLoginInfo getSSOLoginInfo() {
        SsoLoginInfo a = this.mSsoService.a();
        if (a == null || TextUtils.isEmpty(a.loginToken) || TextUtils.isEmpty(a.loginId)) {
            return null;
        }
        SsoLoginInfo a2 = this.mSsoService.a(a);
        if (a2 == null || !a2.isDirectLogin.booleanValue() || TextUtils.isEmpty(a2.loginToken) || TextUtils.isEmpty(a2.loginId)) {
            return null;
        }
        return a2;
    }

    /* access modifiers changed from: private */
    public void startRemoteCheck() {
        AliUserLog.c(TAG, "startRemoteCheck");
        runOnUiThread(new Runnable() {
            public void run() {
                AliUserLog.c(LoginPreCheckActivity.TAG, "startCheckUserInfo");
                if (TextUtils.isEmpty(AppInfo.getInstance().getUmid())) {
                    AliUserLog.c(LoginPreCheckActivity.TAG, "tk is null");
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        public void run() {
                            LoginPreCheckActivity.this.startCheckUserInfo();
                        }
                    }, 200);
                    return;
                }
                LoginPreCheckActivity.this.startCheckUserInfo();
            }
        });
    }

    /* access modifiers changed from: private */
    public void startCheckUserInfo() {
        try {
            getResultEvenTimeout(new FutureTask(new Callable<Object>() {
                public Object call() {
                    try {
                        long currentTimeMillis = System.currentTimeMillis();
                        SuggestLoginUserResPb suggestLoginUserPb = ((PreUserInfoFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(PreUserInfoFacade.class)).suggestLoginUserPb(LoginPreCheckActivity.this.buildSuggestReq());
                        long currentTimeMillis2 = System.currentTimeMillis();
                        String access$300 = LoginPreCheckActivity.TAG;
                        StringBuilder sb = new StringBuilder("LOGIN precheck  cost [");
                        sb.append(currentTimeMillis2 - currentTimeMillis);
                        sb.append("]ms!");
                        AliUserLog.c(access$300, sb.toString());
                        return suggestLoginUserPb;
                    } catch (Throwable th) {
                        AliUserLog.a(LoginPreCheckActivity.TAG, "LOGIN precheck got exception!", th);
                        return null;
                    }
                }
            }));
        } catch (Throwable th) {
            AliUserLog.b(TAG, "login pre check error", th);
            onSuggestFail();
        }
    }

    /* access modifiers changed from: private */
    public SuggestLoginUserReqPb buildSuggestReq() {
        SuggestLoginUserReqPb suggestLoginUserReqPb = new SuggestLoginUserReqPb();
        suggestLoginUserReqPb.productId = AppInfo.getInstance().getProductId();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append(this.mOpenMobileNumber);
        arrayList.add(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.mOpenMCMobileNumber);
        arrayList.add(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(this.mOpenMcAccount);
        arrayList.add(sb3.toString());
        suggestLoginUserReqPb.loginIds = arrayList;
        getSecurityParams(suggestLoginUserReqPb);
        return suggestLoginUserReqPb;
    }

    private void getSecurityParams(SuggestLoginUserReqPb suggestLoginUserReqPb) {
        if (suggestLoginUserReqPb != null) {
            suggestLoginUserReqPb.productId = com.alipay.android.phone.inside.common.info.AppInfo.a().e();
            suggestLoginUserReqPb.productVersion = com.alipay.android.phone.inside.common.info.AppInfo.a().g();
            suggestLoginUserReqPb.utdid = DeviceInfo.a().d();
            suggestLoginUserReqPb.apdid = AppInfo.getInstance().getApdid();
            suggestLoginUserReqPb.tid = getMspTid();
            suggestLoginUserReqPb.umidToken = AppInfo.getInstance().getUmid();
        }
    }

    private void getResultEvenTimeout(final FutureTask<Object> futureTask) {
        new Thread(new Runnable() {
            /* JADX WARNING: Can't wrap try/catch for region: R(3:7|8|9) */
            /* JADX WARNING: Code restructure failed: missing block: B:10:0x004a, code lost:
                r0.shutdown();
                r7.this$0.handler.sendMessage(r7.this$0.handler.obtainMessage(11, null));
             */
            /* JADX WARNING: Code restructure failed: missing block: B:11:0x005c, code lost:
                throw r3;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:6:0x0032, code lost:
                r3 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
                r3.cancel(true);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:9:0x003a, code lost:
                r0.shutdown();
                r0 = r7.this$0.handler;
                r1 = r7.this$0.handler.obtainMessage(11, null);
             */
            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0034 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r7 = this;
                    java.util.concurrent.ExecutorService r0 = java.util.concurrent.Executors.newSingleThreadExecutor()
                    r1 = 0
                    r2 = 11
                    java.lang.String r3 = com.ali.user.mobile.external.LoginPreCheckActivity.TAG     // Catch:{ Throwable -> 0x0034 }
                    java.lang.String r4 = "begin exec"
                    com.ali.user.mobile.log.AliUserLog.c(r3, r4)     // Catch:{ Throwable -> 0x0034 }
                    java.util.concurrent.FutureTask r3 = r3     // Catch:{ Throwable -> 0x0034 }
                    r0.execute(r3)     // Catch:{ Throwable -> 0x0034 }
                    java.util.concurrent.FutureTask r3 = r3     // Catch:{ Throwable -> 0x0034 }
                    r4 = 2000(0x7d0, double:9.88E-321)
                    java.util.concurrent.TimeUnit r6 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ Throwable -> 0x0034 }
                    java.lang.Object r3 = r3.get(r4, r6)     // Catch:{ Throwable -> 0x0034 }
                    r0.shutdown()
                    com.ali.user.mobile.external.LoginPreCheckActivity r0 = com.ali.user.mobile.external.LoginPreCheckActivity.this
                    android.os.Handler r0 = r0.handler
                    com.ali.user.mobile.external.LoginPreCheckActivity r1 = com.ali.user.mobile.external.LoginPreCheckActivity.this
                    android.os.Handler r1 = r1.handler
                    android.os.Message r1 = r1.obtainMessage(r2, r3)
                L_0x002e:
                    r0.sendMessage(r1)
                    return
                L_0x0032:
                    r3 = move-exception
                    goto L_0x004a
                L_0x0034:
                    java.util.concurrent.FutureTask r3 = r3     // Catch:{ all -> 0x0032 }
                    r4 = 1
                    r3.cancel(r4)     // Catch:{ all -> 0x0032 }
                    r0.shutdown()
                    com.ali.user.mobile.external.LoginPreCheckActivity r0 = com.ali.user.mobile.external.LoginPreCheckActivity.this
                    android.os.Handler r0 = r0.handler
                    com.ali.user.mobile.external.LoginPreCheckActivity r3 = com.ali.user.mobile.external.LoginPreCheckActivity.this
                    android.os.Handler r3 = r3.handler
                    android.os.Message r1 = r3.obtainMessage(r2, r1)
                    goto L_0x002e
                L_0x004a:
                    r0.shutdown()
                    com.ali.user.mobile.external.LoginPreCheckActivity r0 = com.ali.user.mobile.external.LoginPreCheckActivity.this
                    android.os.Handler r0 = r0.handler
                    com.ali.user.mobile.external.LoginPreCheckActivity r4 = com.ali.user.mobile.external.LoginPreCheckActivity.this
                    android.os.Handler r4 = r4.handler
                    android.os.Message r1 = r4.obtainMessage(r2, r1)
                    r0.sendMessage(r1)
                    throw r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.external.LoginPreCheckActivity.AnonymousClass5.run():void");
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public void processSuggestReponse(Object obj) {
        try {
            AliUserLog.c(TAG, "begin process pre check result");
            if (obj == null) {
                AliUserLog.c(TAG, "pre check result is null");
                onSuggestFail();
                return;
            }
            SuggestLoginUserResPb suggestLoginUserResPb = (SuggestLoginUserResPb) obj;
            setSuggestSwitchState(suggestLoginUserResPb);
            if (!suggestLoginUserResPb.success.booleanValue()) {
                onSuggestFail();
            } else {
                onSuggestSuccess(suggestLoginUserResPb);
            }
        } catch (Throwable th) {
            AliUserLog.a(TAG, "processSuggestReponse error", th);
            onSuggestFail();
        }
    }

    private void onSuggestSuccess(SuggestLoginUserResPb suggestLoginUserResPb) {
        String str = suggestLoginUserResPb.loginId;
        AliUserLog.c(TAG, "suggestLoginId = ".concat(String.valueOf(str)));
        Bundle bundle = new Bundle();
        bundle.putString("loginId", str);
        LoginAppService.getInstance().startLoginPage(LauncherApplication.a(), bundle);
        dismissProgress();
        delayFinishCurrentPage();
    }

    private void onSuggestFail() {
        dismissProgress();
        LoginAppService.getInstance().startLoginPage(LauncherApplication.a(), null);
        delayFinishCurrentPage();
    }

    private void setSuggestSwitchState(SuggestLoginUserResPb suggestLoginUserResPb) {
        try {
            ServerConfigService configService = AntExtServiceManager.getConfigService(LauncherApplication.a().getApplicationContext());
            List<ExternMap> list = suggestLoginUserResPb.clientConfigMap;
            if (list != null) {
                if (!list.isEmpty()) {
                    String str = TAG;
                    StringBuilder sb = new StringBuilder("setSuggestSwitchState:");
                    sb.append(list.toString());
                    AliUserLog.c(str, sb.toString());
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        ExternMap externMap = list.get(i);
                        configService.putConfig(externMap.key, externMap.value);
                    }
                }
            }
        } catch (Throwable th) {
            AliUserLog.a(TAG, "process drm error", th);
        }
    }

    /* access modifiers changed from: private */
    public void delayFinishCurrentPage() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                try {
                    LoginPreCheckActivity.this.dismissProgress();
                    LoginPreCheckActivity.this.finish();
                } catch (Throwable th) {
                    AliUserLog.b(LoginPreCheckActivity.TAG, "LoginPreCheckActivity finish error", th);
                }
            }
        }, 200);
    }

    private String getMspTid() {
        try {
            Bundle bundle = (Bundle) PluginManager.b(PhoneCashierPlugin.KEY_SERVICE_GET_TID).startForResult(null);
            String str = "";
            if (bundle == null) {
                AliUserLog.d(TAG, "调用移动快捷获取tid=null");
            } else {
                str = bundle.getString(InsideServiceGetTid.CASHIER_TID);
            }
            return str;
        } catch (Throwable unused) {
            return "";
        }
    }

    /* access modifiers changed from: private */
    public void doSsoTokenLogin(SsoLoginInfo ssoLoginInfo) {
        this.mCurrentLoginValidateType = LOGIN_BY_SSO_TOKEN;
        AliUserLog.c(TAG, "doSsoTokenLogin");
        LoggerUtils.a("clicked", "prerouterpage_ssologin", "UC-PREROUTER-LOG-170401-5", LOGIN_BY_SSO_TOKEN);
        showProgress("");
        LoginParam loginParam = new LoginParam();
        loginParam.loginAccount = ssoLoginInfo.loginId;
        loginParam.token = ssoLoginInfo.loginToken;
        loginParam.validateTpye = "withlogintoken";
        doUnifyLogin(loginParam);
    }

    /* access modifiers changed from: private */
    public void doAlipayAuthLogin() {
        showProgress(getResources().getString(R.string.bx));
        AliUserLog.c(TAG, "doAlipayAuthLogin");
        try {
            AlipaySsoAPIFactory.a(LauncherApplication.a()).a((Activity) this, (IAlipaySSOPreHandler) new IAlipaySSOPreHandler() {
                public void dismissPreProgress() {
                }

                public void showPreProgress() {
                }

                public void preAuthFailed(int i) {
                    AliUserLog.c(LoginPreCheckActivity.TAG, "preAuthFailed = ".concat(String.valueOf(i)));
                    LoginPreCheckActivity.this.doCallback("login_failed");
                }

                public void openAlipaySuccess() {
                    LoginPreCheckActivity.this.delayFinishCurrentPage();
                }
            });
        } catch (Throwable th) {
            AliUserLog.b(TAG, "goAlipayAuthLogin failed", th);
            doCallback("login_failed");
        }
    }

    public void getLoginParams(LoginParam loginParam) {
        if (TextUtils.isEmpty(loginParam.loginAccount)) {
            loginParam.loginAccount = getLoginAccount();
        }
        if (TextUtils.isEmpty(loginParam.loginPassword)) {
            loginParam.loginPassword = getLoginPassword();
        }
        if (TextUtils.isEmpty(loginParam.validateTpye)) {
            loginParam.validateTpye = "withsndpwd";
        }
        if (TextUtils.isEmpty(loginParam.loginType)) {
            loginParam.loginType = getLoginType();
        }
        this.mLoginParam = loginParam;
    }

    public void handleRpcException(RpcException rpcException) {
        LoggerUtils.a("clicked", "login_action_callback", "UC-ACTION-CALLBACK-170401-5", "rpc_error");
        toast(getResources().getString(R.string.cu), 0);
        Bundle bundle = new Bundle();
        bundle.putString("loginStatus", "cancel");
        IInsideServiceCallback g = AliuserLoginContext.g();
        if (g != null) {
            g.onComplted(bundle);
            AliuserLoginContext.a((IInsideServiceCallback) null);
        }
        delayFinishCurrentPage();
    }

    public void onLoginResponseSuccess(UnifyLoginRes unifyLoginRes) {
        super.onLoginResponseSuccess(unifyLoginRes);
        setSuggestSwitchState(unifyLoginRes);
        dismissProgress();
        delayFinishCurrentPage();
    }

    public void onLoginResponseError(UnifyLoginRes unifyLoginRes) {
        if (LOGIN_BY_SSO_TOKEN.equals(this.mCurrentLoginValidateType)) {
            this.handler.sendEmptyMessage(2003);
        } else {
            this.handler.sendEmptyMessage(2005);
        }
    }

    /* access modifiers changed from: private */
    public void doCallback(String str) {
        IInsideServiceCallback g = AliuserLoginContext.g();
        if (g != null) {
            Bundle bundle = new Bundle();
            bundle.putString("loginStatus", str);
            g.onComplted(bundle);
            AliuserLoginContext.a((IInsideServiceCallback) null);
        }
        delayFinishCurrentPage();
    }

    private void setSuggestSwitchState(UnifyLoginRes unifyLoginRes) {
        if (unifyLoginRes == null) {
            try {
                AliUserLog.c(TAG, "信登返回信息为空");
            } catch (Throwable th) {
                AliUserLog.a(TAG, "process drm error", th);
            }
        } else {
            AntExtServiceManager.getConfigService(LauncherApplication.a().getApplicationContext()).putConfig("sms_login_enable", getStringFromExtResAttrs(unifyLoginRes, "sms_login_enable"));
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mCurrentLoginValidateType = null;
    }
}
