package com.ali.user.mobile.login.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import com.ali.user.mobile.account.bean.UserInfo;
import com.ali.user.mobile.accountbiz.SecurityUtil;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.biz.ReportLocationServiceWrapper;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.context.LoginHandler;
import com.ali.user.mobile.db.UserInfoDaoHelper;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.log.LoginMonitor;
import com.ali.user.mobile.log.PerformanceLogAgent;
import com.ali.user.mobile.login.AbsNotifyFinishCaller;
import com.ali.user.mobile.login.LoginActivityCollections;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.login.OnLoginCaller;
import com.ali.user.mobile.login.rds.RDSWraper;
import com.ali.user.mobile.register.Account;
import com.ali.user.mobile.register.RegContext;
import com.ali.user.mobile.rpc.vo.mobilegw.login.UnifyLoginRes;
import com.ali.user.mobile.rsa.Rsa;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.service.UserLoginService;
import com.ali.user.mobile.ui.widget.PopMenuItem;
import com.ali.user.mobile.utils.CommonUtil;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.mobile.security.zim.plugin.ZIMH5Plugin;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.alipay.sdk.util.e;
import com.amap.bundle.drivecommon.inter.NetConstant;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public abstract class BaseLoginActivity extends BaseActivity implements LoginHandler {
    public static final String CALLBACK = "https://www.alipay.com/webviewbridge";
    private static final int GET_USERINFO_LIST_COUNT = 5;
    protected static final int LOGIN_RESPONSE = 1;
    private static final String TAG = "BaseLoginActivity";
    protected OnClickListener clearPasswordListener = new OnClickListener() {
        public void onClick(DialogInterface dialogInterface, int i) {
            BaseLoginActivity.this.clearPassword();
        }
    };
    protected String confirm;
    protected OnClickListener forgetPasswordListener = new OnClickListener() {
        public void onClick(DialogInterface dialogInterface, int i) {
            BaseLoginActivity.this.writeClickLog("UC-LOG-150512-09", "logingetpwd");
            BaseLoginActivity.this.clearPassword();
            BaseLoginActivity.this.toForgetPassword(BaseLoginActivity.this.getLoginAccount(), null);
        }
    };
    protected String logging;
    protected Context mApplicationContext;
    protected Handler mHandler = new UnifyLoginHandler(Looper.getMainLooper());
    public String mInsideAccount;
    protected boolean mIsFromRegist = false;
    protected boolean mIsLoginSuccess = false;
    private boolean mIsWindowFirstFocus = true;
    protected List<UserInfo> mLoginHistorys;
    protected LoginParam mLoginParam;
    protected RDSWraper mRdsWraper;
    protected String mToken;
    protected UserLoginService mUserLoginService;
    protected PerformanceLogAgent performanceLogAgent;
    protected OnClickListener registerListener = new OnClickListener() {
        public void onClick(DialogInterface dialogInterface, int i) {
            BaseLoginActivity.this.toRegist(null);
            BaseLoginActivity.this.clearPassword();
        }
    };
    protected OnClickListener registerWithAccountListener = new OnClickListener() {
        public void onClick(DialogInterface dialogInterface, int i) {
            BaseLoginActivity.this.toRegist(new Account(BaseLoginActivity.this.getLoginAccount()));
            BaseLoginActivity.this.clearPassword();
        }
    };
    protected String systemError;
    protected String verifyFail;

    class UnifyLoginHandler extends Handler {
        public UnifyLoginHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            AliUserLog.c(BaseLoginActivity.TAG, String.format("UnifyLoginHandler receive msg: %s", new Object[]{Integer.valueOf(message.what)}));
            if (message.what == 1) {
                BaseLoginActivity.this.onLoginPreFinish((UnifyLoginRes) message.obj);
            }
        }
    }

    public void addFaceLoginSyncConfig(LoginParam loginParam) {
    }

    /* access modifiers changed from: protected */
    public abstract void clearAccount();

    /* access modifiers changed from: protected */
    public abstract void clearPassword();

    /* access modifiers changed from: protected */
    public abstract String getLoginAccount();

    /* access modifiers changed from: protected */
    public abstract String getLoginPassword();

    /* access modifiers changed from: protected */
    public String getLoginType() {
        return BehavorReporter.PROVIDE_BY_ALIPAY;
    }

    /* access modifiers changed from: protected */
    public abstract String getShownAccount();

    /* access modifiers changed from: protected */
    public abstract void onNewAccount(String str, int i);

    /* access modifiers changed from: protected */
    public void saveLoginHistory(UnifyLoginRes unifyLoginRes) {
    }

    public void toSmsLogin() {
    }

    public void setLoginResult(boolean z) {
        this.mIsLoginSuccess = z;
    }

    public boolean isFromAccountManager() {
        return getIntent().getBooleanExtra("source_accountSelectAccount", false);
    }

    public void finishAndNotify() {
        dismissProgress();
        LogAgent.f("UC-LOG-161225-03", "loginpageback", isFromAccountManager() ? "accountmanager" : "firstpage", null, null);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("com.ali.user.sdk.login.CANCEL"));
        OnLoginCaller i = AliuserLoginContext.i();
        if (i != null) {
            i.a(null);
        }
        finish();
    }

    /* access modifiers changed from: protected */
    public void onKeyBack() {
        if (AliuserLoginContext.a()) {
            AliUserLog.c(TAG, "onKeyDown - 允许退出登陆，直接退出");
            finishAndNotify();
            return;
        }
        callBackInsideOnKeyBack();
        finish();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        onKeyBack();
        return true;
    }

    /* access modifiers changed from: protected */
    public void callBackInsideOnKeyBack() {
        try {
            synchronized (BaseLoginActivity.class) {
                IInsideServiceCallback g = AliuserLoginContext.g();
                if (g != null) {
                    AliUserLog.c(TAG, "callback inside baseActivity");
                    Bundle bundle = new Bundle();
                    bundle.putString("loginStatus", "cancel");
                    g.onComplted(bundle);
                    LoggerUtils.a("clicked", "login_action_callback", "UC-ACTION-CALLBACK-170401-5", "cancel");
                    AliuserLoginContext.a((IInsideServiceCallback) null);
                }
            }
        } catch (Throwable th) {
            AliUserLog.a(TAG, "callback inside error", th);
        }
    }

    private void doPreGetRsa() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Rsa.a(BaseLoginActivity.this.getApplicationContext());
                } catch (Exception unused) {
                    AliUserLog.c(BaseLoginActivity.TAG, "Exception when doPreGetRsa");
                }
            }
        }, "AliuserSdk.preGetRsa").start();
    }

    public void onCreate(Bundle bundle) {
        AliUserLog.c(TAG, "onCreate:".concat(String.valueOf(bundle)));
        super.onCreate(bundle);
        this.mApplicationContext = getApplicationContext();
        this.mUserLoginService = AliuserLoginContext.d();
        this.mRdsWraper = new RDSWraper(this.mApplicationContext, "LoginPage");
        this.mLoginHistorys = getLoginHistoryManager().a(5);
        AliuserLoginContext.a(false);
        AliuserLoginContext.a((LoginHandler) this);
        this.mIsLoginSuccess = false;
        this.mIsFromRegist = getIntent().getBooleanExtra("from_register", false);
        initResource();
        doPreGetRsa();
        initInisdeAccount(getIntent());
    }

    private void logRenderPerformance() {
        try {
            if (this.mIsWindowFirstFocus) {
                this.mIsWindowFirstFocus = false;
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    long j = extras.getLong("RenderStartTime");
                    if (j > 0) {
                        getIntent().putExtra("RenderStartTime", 0);
                        String string = extras.getString("LoginSource");
                        long elapsedRealtime = SystemClock.elapsedRealtime() - j;
                        StringBuilder sb = new StringBuilder("login performance end LoginSource: ");
                        sb.append(string);
                        sb.append(" renderTime: ");
                        sb.append(elapsedRealtime);
                        AliUserLog.c(TAG, sb.toString());
                        HashMap hashMap = new HashMap();
                        hashMap.put("totaltime", String.valueOf(elapsedRealtime));
                        hashMap.put("pfid", "login_".concat(String.valueOf(string)));
                        LogAgent.a((String) "AS-PERFORMANCE-161206-01", (String) "alilogin", (String) null, (String) null, (String) null, (Map<String, String>) hashMap);
                    }
                }
            }
        } catch (Throwable th) {
            AliUserLog.a((String) TAG, th);
        }
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        AliUserLog.c(TAG, String.format("onNewIntent:%s", new Object[]{intent}));
        AliuserLoginContext.a(false);
        this.mIsLoginSuccess = false;
        this.mIsFromRegist = intent.getBooleanExtra("from_register", false);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        logRenderPerformance();
        AliUserLog.c(TAG, "send login open broadcast");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(new Intent("com.ali.user.sdk.login.OPEN"));
    }

    public void onDestroy() {
        super.onDestroy();
        AliUserLog.c(TAG, "onDestroy");
        AliuserLoginContext.a(false);
    }

    public RDSWraper getRdsWraper() {
        return this.mRdsWraper;
    }

    /* access modifiers changed from: protected */
    public void initInisdeAccount(Intent intent) {
        String str;
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                LoginParam loginParam = (LoginParam) extras.get("login_param");
                if (loginParam == null) {
                    str = "";
                } else {
                    str = loginParam.loginAccount;
                }
                this.mInsideAccount = str;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initResource() {
        this.confirm = getString(R.string.H);
        this.logging = "";
        this.systemError = getString(R.string.cE);
        this.verifyFail = getString(R.string.cL);
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

    public void unifyLogin() {
        AliUserLog.c(TAG, "loginInCurrentEnv");
        startPerformanceLog();
        LoginParam loginParam = new LoginParam();
        getLoginParams(loginParam);
        doUnifyLogin(loginParam);
    }

    /* access modifiers changed from: protected */
    public void unifyLoginWithToken(String str, String str2) {
        AliUserLog.c(TAG, "loginWithToken");
        LoginParam loginParam = new LoginParam();
        getLoginParams(loginParam);
        loginParam.token = str;
        loginParam.validateTpye = str2;
        doUnifyLogin(loginParam);
    }

    public void doUnifyLogin(final LoginParam loginParam) {
        showProgress(this.logging);
        this.mLoginParam = loginParam;
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                AliUserLog.c(BaseLoginActivity.TAG, "login ing...");
                try {
                    BaseLoginActivity.this.doReportDeviceInfo();
                    BaseLoginActivity.this.addAccountMonitors(loginParam);
                    loginParam.alipayEnvJson = BaseLoginActivity.this.mRdsWraper.getRdsData(BaseLoginActivity.this.mApplicationContext, loginParam.loginAccount);
                    BaseLoginActivity.this.afterLogin(loginParam, BaseLoginActivity.this.mUserLoginService.a(loginParam));
                } catch (RpcException e) {
                    BaseLoginActivity.this.handleRpcException(e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void doReportDeviceInfo() {
        new ReportLocationServiceWrapper();
        ReportLocationServiceWrapper.a("login");
    }

    /* access modifiers changed from: protected */
    public void addAccountMonitors(LoginParam loginParam) {
        if (hasLoginHistory()) {
            loginParam.addMonitorParam("historyCount", String.valueOf(this.mLoginHistorys.size()));
            boolean z = false;
            Iterator<UserInfo> it = this.mLoginHistorys.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String logonId = it.next().getLogonId();
                if (!TextUtils.isEmpty(logonId) && logonId.equals(loginParam.loginAccount)) {
                    z = true;
                    break;
                }
            }
            loginParam.addMonitorParam("matchHistory", z ? "1" : "0");
        } else {
            loginParam.addMonitorParam("historyCount", "0");
            loginParam.addMonitorParam("matchHistory", "0");
        }
        loginParam.addMonitorParam(NetConstant.KEY_MONEY_ACCOUNT, loginParam.loginAccount);
        if (getIntent().getExtras() != null) {
            loginParam.addMonitorParam("LoginSource", getIntent().getExtras().getString("LoginSource"));
        }
    }

    public List<UserInfo> getLoginHistoryList() {
        return this.mLoginHistorys;
    }

    public boolean hasLoginHistory() {
        return this.mLoginHistorys != null && !this.mLoginHistorys.isEmpty();
    }

    public void afterLogin(LoginParam loginParam, UnifyLoginRes unifyLoginRes) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, unifyLoginRes));
    }

    public void onLoginPreFinish(final UnifyLoginRes unifyLoginRes) {
        AliUserLog.c(TAG, "onLoginPreFinish");
        if (isFinishing() || isActivityDestroy()) {
            AliUserLog.c(TAG, "activity isFinishing or isDestroy, do nothing");
        } else if (onLoginResponse(unifyLoginRes)) {
            endPerformanceLog();
            setLoginResult(true);
            OnLoginCaller i = AliuserLoginContext.i();
            if (i == null) {
                AliUserLog.c(TAG, "loginCaller == null， do onLoginPostFinish");
                onLoginPostFinish(unifyLoginRes);
                return;
            }
            writeClickLog("YWUC-JTTYZH-C101", "postFinishLogin");
            i.c(unifyLoginRes, new AbsNotifyFinishCaller() {
                public final void a() {
                    AliUserLog.c(BaseLoginActivity.TAG, "biz notifyPacelable to do onLoginPostFinish");
                    BaseLoginActivity.this.onLoginPostFinish(unifyLoginRes);
                }
            });
        } else {
            disablePerformanceLog();
            setLoginResult(false);
            onLoginFail(unifyLoginRes);
        }
    }

    public boolean onLoginResponse(UnifyLoginRes unifyLoginRes) {
        StringBuilder sb = new StringBuilder("onLoginResponse,code:");
        sb.append(unifyLoginRes.code);
        sb.append(",msg:");
        sb.append(unifyLoginRes.msg);
        AliUserLog.c(TAG, sb.toString());
        this.mToken = unifyLoginRes.token;
        String str = unifyLoginRes.code;
        if ("200".equals(str) || "1000".equals(str)) {
            onLoginResponseSuccess(unifyLoginRes);
            return true;
        }
        onLoginResponseError(unifyLoginRes);
        return false;
    }

    /* access modifiers changed from: protected */
    public void onLoginResponseSuccess(UnifyLoginRes unifyLoginRes) {
        if (this.mLoginParam != null && "withmsg".equalsIgnoreCase(this.mLoginParam.validateTpye)) {
            LogAgent.c("smsLogin_H5ToLoginSuccess", "UC-LOG-150512-T02", "loginsuccess", null, null);
            LoginMonitor.b("smsLogin_H5ToLoginSuccess");
        }
        LogAgent.a();
    }

    /* access modifiers changed from: protected */
    public void onLoginResponseError(UnifyLoginRes unifyLoginRes) {
        dismissProgress();
        String str = unifyLoginRes.code;
        if ("6304".equals(str) || "6303".equals(str)) {
            alert(null, unifyLoginRes.msg, getString(R.string.co), this.clearPasswordListener, getString(R.string.bs), this.forgetPasswordListener);
        } else if ("6321".equals(str) || "5138".equals(str) || "6297".equals(str)) {
            alert(null, unifyLoginRes.msg, getString(R.string.ck), this.registerListener, getString(R.string.bt), this.clearPasswordListener);
        } else if ("6305".equals(str)) {
            alert(null, unifyLoginRes.msg, getString(R.string.bp), this.forgetPasswordListener, getString(R.string.bt), this.clearPasswordListener);
        } else if ("6326".equals(str)) {
            alert(null, unifyLoginRes.msg, getString(R.string.bt), null, null, null);
        } else if ("5134".equals(str) || "6211".equals(str)) {
            alert(null, unifyLoginRes.msg, getString(R.string.bt), null, getString(R.string.cN), this.registerListener);
        } else if ("6210".equals(str)) {
            alert(null, unifyLoginRes.msg, getString(R.string.J), null, getString(R.string.P), this.registerWithAccountListener);
        } else if ("6272".equals(str)) {
            Intent intent = new Intent(this, AliUserLoginSixPasswordActivity.class);
            intent.putExtra("login_param", this.mLoginParam);
            intent.putExtra("token", unifyLoginRes.token);
            intent.putExtra("showOptionalInfo", getStringFromExtResAttrs(unifyLoginRes, "showOptionalInfo"));
            intent.putExtra("agreementURL", getStringFromExtResAttrs(unifyLoginRes, "agreementURL"));
            intent.putExtra("optionStatus", "true".equalsIgnoreCase(getStringFromExtResAttrs(unifyLoginRes, "optionStatus")));
            startActivityForResult(intent, 36864);
            clearPassword();
        } else if ("6207".equals(str)) {
            toSecurityCore(unifyLoginRes);
        } else if ("6202".equals(str)) {
            commonProcess();
        } else if ("6301".equals(str)) {
            commonProcess();
        } else if ("6302".equals(str)) {
            toVerifyIdentity(unifyLoginRes);
        } else if ("6232".equals(str)) {
            commonProcess();
        } else if ("5039".equals(str)) {
            alert(null, unifyLoginRes.msg, getString(R.string.co), this.clearPasswordListener, getString(R.string.cj), this.registerWithAccountListener);
        } else if ("5018".equals(str) || "5019".equals(str) || "5037".equals(str) || "5141".equals(str) || "6306".equals(str) || "1026".equals(str) || "5133".equals(str) || "6291".equals(str) || "6262".equals(str) || "6292".equals(str) || "6293".equals(str) || "6294".equals(str) || "6295".equals(str) || "6296".equals(str) || "5007".equals(str) || "1019".equals(str) || "5144".equals(str) || UploadConstants.STATUS_PUSH_RECEIVED.equals(str)) {
            alert("", unifyLoginRes.msg, this.confirm, this.clearPasswordListener, null, null);
        } else {
            toast(unifyLoginRes.msg, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void toVerifyIdentity(UnifyLoginRes unifyLoginRes) {
        AliUserLog.c(TAG, "call VerifyIdentity");
        final String str = unifyLoginRes.token;
        String stringFromExtResAttrs = getStringFromExtResAttrs(unifyLoginRes, "tokenId");
        try {
            Bundle bundle = new Bundle();
            bundle.putString("RELEASE_RISK_TYPE", "NEED_VERIFY");
            bundle.putString(ZIMH5Plugin.ZIM_IDENTIFY_VERIFYID, stringFromExtResAttrs);
            addLoginId(unifyLoginRes, bundle);
            ServiceExecutor.a("COMMON_SERVICE_VERIFY", bundle, new IInsideServiceCallback<Bundle>() {
                public /* synthetic */ void onComplted(Object obj) {
                    final Bundle bundle = (Bundle) obj;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            AliUserLog.c(BaseLoginActivity.TAG, "toVerifyIdentity onComplted");
                            if (bundle == null) {
                                AliUserLog.c(BaseLoginActivity.TAG, "VerifyIdentity fail");
                                BaseLoginActivity.this.onProcessVerifyUnSuccessResult(e.b, "6302");
                            } else if ("success".equals(bundle.getString("verifyState"))) {
                                AliUserLog.c(BaseLoginActivity.TAG, "toVerifyIdentity onComplted success");
                                BaseLoginActivity.this.unifyLoginWithToken(str, "withchecktoken");
                            } else if (e.b.equals(bundle.getString("verifyState"))) {
                                AliUserLog.c(BaseLoginActivity.TAG, "toVerifyIdentity onComplted failed");
                                BaseLoginActivity.this.onProcessVerifyUnSuccessResult(e.b, "6302");
                            } else if ("alipay_not_install".equals(bundle.getString("verifyState"))) {
                                AliUserLog.c(BaseLoginActivity.TAG, "toVerifyIdentity onComplted alipay not install");
                                BaseLoginActivity.this.onProcessVerifyUnSuccessResult("alipay_not_install", "6302");
                            }
                        }
                    });
                }

                public void onException(Throwable th) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            AliUserLog.c(BaseLoginActivity.TAG, "toVerifyIdentity onException");
                            BaseLoginActivity.this.onProcessVerifyUnSuccessResult(e.b, "6302");
                        }
                    });
                }
            });
        } catch (Throwable unused) {
            AliUserLog.c(TAG, "call toVerifyIdentity error");
        }
    }

    /* access modifiers changed from: protected */
    public void toSecurityCore(UnifyLoginRes unifyLoginRes) {
        AliUserLog.c(TAG, "6207 toSecurityCore");
        final String str = unifyLoginRes.token;
        try {
            String decode = URLDecoder.decode(addSecurityCallbackToUrl(unifyLoginRes.h5Url, "%3Faction%3DcontinueLogin"), "utf-8");
            AliUserLog.c(TAG, "securityCheckUrl:".concat(String.valueOf(decode)));
            Bundle bundle = new Bundle();
            bundle.putString("RELEASE_RISK_TYPE", "SECURITY_NEED_CHECK");
            bundle.putString("securityCheckUrl", decode);
            addLoginId(unifyLoginRes, bundle);
            ServiceExecutor.a("COMMON_SERVICE_VERIFY", bundle, new IInsideServiceCallback<Bundle>() {
                public /* synthetic */ void onComplted(Object obj) {
                    final Bundle bundle = (Bundle) obj;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            AliUserLog.c(BaseLoginActivity.TAG, "toVerifyIdentity onComplted");
                            if (bundle == null) {
                                AliUserLog.c(BaseLoginActivity.TAG, "VerifyIdentity fail");
                                BaseLoginActivity.this.onProcessVerifyUnSuccessResult(e.b, "6207");
                            } else if ("success".equals(bundle.getString("verifyState"))) {
                                AliUserLog.c(BaseLoginActivity.TAG, "toSecurityCore onComplted success");
                                BaseLoginActivity.this.unifyLoginWithToken(str, "withchecktoken");
                            } else if (e.b.equals(bundle.getString("verifyState"))) {
                                AliUserLog.c(BaseLoginActivity.TAG, "toSecurityCore onComplted failed");
                                BaseLoginActivity.this.onProcessVerifyUnSuccessResult(e.b, "6207");
                            } else if ("alipay_not_install".equals(bundle.getString("verifyState"))) {
                                AliUserLog.c(BaseLoginActivity.TAG, "toSecurityCore onComplted alipay not install");
                                BaseLoginActivity.this.onProcessVerifyUnSuccessResult("alipay_not_install", "6207");
                            }
                        }
                    });
                }

                public void onException(Throwable th) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        public void run() {
                            AliUserLog.c(BaseLoginActivity.TAG, "toVerifyIdentity onException");
                            BaseLoginActivity.this.onProcessVerifyUnSuccessResult(e.b, "6207");
                        }
                    });
                }
            });
        } catch (Throwable th) {
            AliUserLog.a((String) TAG, th);
        }
    }

    /* access modifiers changed from: protected */
    public void addLoginId(UnifyLoginRes unifyLoginRes, Bundle bundle) {
        if (bundle == null) {
            try {
                AliUserLog.c(TAG, "params is null");
            } catch (Throwable th) {
                AliUserLog.a(TAG, "addLoginId error", th);
            }
        } else {
            String jsonString = getJsonString(new JSONObject(unifyLoginRes.data), "loginId");
            AliUserLog.c(TAG, "loginId:".concat(String.valueOf(jsonString)));
            if (!TextUtils.isEmpty(jsonString)) {
                bundle.putString("loginId", jsonString);
            }
        }
    }

    private static String getJsonString(JSONObject jSONObject, String str) {
        try {
            return jSONObject.getString(str);
        } catch (Throwable th) {
            AliUserLog.a(TAG, "getJsonString error", th);
            return "";
        }
    }

    /* access modifiers changed from: protected */
    public void onProcessVerifyUnSuccessResult(String str, String str2) {
        AliUserLog.c(TAG, "onProcessVerifyUnSuccessResult source:".concat(String.valueOf(str2)));
        onProcessVerifyUnSuccessResult(str);
    }

    /* access modifiers changed from: protected */
    public void onProcessVerifyUnSuccessResult(String str) {
        AliUserLog.c(TAG, "onProcessVerifyUnSuccessResult result：".concat(String.valueOf(str)));
        if (e.b.equals(str)) {
            toast(getResources().getString(R.string.cL), 0);
            return;
        }
        try {
            IInsideService b = PluginManager.b("WALLET_PLUGIN_INSTALL_GUIDE_SERVICE");
            Bundle bundle = new Bundle();
            bundle.putString("installTips", getResources().getString(R.string.cK));
            b.start(bundle);
        } catch (Throwable th) {
            AliUserLog.a(TAG, "installGuideService error", th);
        }
    }

    public static String addSecurityCallbackToUrl(String str, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(str.indexOf(63) >= 0 ? "&callbackUrl=" : "?callbackUrl=");
        sb.append("https://www.alipay.com/webviewbridge");
        sb.append(str2);
        return sb.toString();
    }

    private void commonProcess() {
        showListDialogWithTitle(getString(R.string.cp), assembleParams());
    }

    public void performDialogAction(PopMenuItem popMenuItem) {
        super.performDialogAction(popMenuItem);
        if (popMenuItem != null && !TextUtils.isEmpty(popMenuItem.a())) {
            String charSequence = popMenuItem.a().toString();
            if (getString(R.string.cr).equals(charSequence)) {
                clearPassword();
                clearAccount();
            } else if (getString(R.string.cs).equals(charSequence)) {
                clearPassword();
                toAlipay();
            } else {
                getString(R.string.cq).equals(charSequence);
            }
        }
    }

    private ArrayList<PopMenuItem> assembleParams() {
        ArrayList<PopMenuItem> arrayList = new ArrayList<>();
        PopMenuItem popMenuItem = new PopMenuItem(getString(R.string.cr));
        PopMenuItem popMenuItem2 = new PopMenuItem(getString(R.string.cs));
        PopMenuItem popMenuItem3 = new PopMenuItem(getString(R.string.cq));
        arrayList.add(popMenuItem);
        arrayList.add(popMenuItem2);
        arrayList.add(popMenuItem3);
        return arrayList;
    }

    public void onLoginPostFinish(UnifyLoginRes unifyLoginRes) {
        AliUserLog.c(TAG, "onLoginPostFinish");
        if (!AliuserLoginContext.j()) {
            dismissProgress();
            AliUserLog.c(TAG, "app do not finish biz process, only stop progress");
            return;
        }
        saveLoginHistory(unifyLoginRes);
        dismissProgress();
        AliUserLog.c(TAG, "base login dismiss");
        AliuserLoginContext.k();
        LoginActivityCollections.a().b();
        Intent intent = new Intent("com.ali.user.sdk.login.SUCCESS");
        intent.putExtra("from_register", this.mIsFromRegist);
        intent.putExtra("havanaId", String.valueOf(unifyLoginRes.hid));
        sendBroadCast(intent);
        callBackInside();
    }

    private void callBackInside() {
        LoggerUtils.a("clicked", "login_action_callback", "UC-ACTION-CALLBACK-170401-5", "success");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                try {
                    synchronized (BaseLoginActivity.class) {
                        IInsideServiceCallback g = AliuserLoginContext.g();
                        if (g != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("loginStatus", "success");
                            AliUserLog.c(BaseLoginActivity.TAG, "登录成功,回调业务方");
                            g.onComplted(bundle);
                            AliuserLoginContext.a((IInsideServiceCallback) null);
                        }
                    }
                } catch (Throwable th) {
                    AliUserLog.a(BaseLoginActivity.TAG, "callback inside error", th);
                }
            }
        }, 300);
    }

    public void onLoginFail(UnifyLoginRes unifyLoginRes) {
        AliUserLog.c(TAG, "onLoginFail");
        OnLoginCaller i = AliuserLoginContext.i();
        if (i != null) {
            i.b(unifyLoginRes, null);
        }
        sendBroadCast(new Intent("com.ali.user.sdk.login.FAIL"));
    }

    public void toAlipay() {
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                CommonUtil.a((Activity) BaseLoginActivity.this);
            }
        });
    }

    public void toForgetPassword(final String str, String str2) {
        clearPassword();
        SecurityUtil.a((Runnable) new Runnable() {
            public void run() {
                CommonUtil.a(BaseLoginActivity.this, str);
            }
        });
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        StringBuilder sb = new StringBuilder("onActivityResult, requestCode:");
        sb.append(i);
        sb.append(",resultCode:");
        sb.append(i2);
        sb.append(",data:");
        sb.append(intent);
        AliUserLog.c(TAG, sb.toString());
        if (i != 45056) {
            if (i2 == 1000) {
                onLoginPreFinish((UnifyLoginRes) intent.getSerializableExtra("login_response"));
                return;
            } else if (i2 == 1998) {
                onLoginResponse((UnifyLoginRes) intent.getSerializableExtra("login_response"));
            } else if (i2 == 0) {
                clearPassword();
            } else if (i2 == 8194) {
                this.mLoginParam = (LoginParam) intent.getSerializableExtra("login_param");
                onLoginResponse((UnifyLoginRes) intent.getSerializableExtra("login_response"));
            }
        }
        super.onActivityResult(i, i2, intent);
    }

    /* access modifiers changed from: protected */
    public UserInfoDaoHelper getLoginHistoryManager() {
        return UserInfoDaoHelper.a(this.mApplicationContext);
    }

    public void startActivityForResult(Class<?> cls, int i, UnifyLoginRes unifyLoginRes) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("login_param", this.mLoginParam);
        intent.putExtra("token", unifyLoginRes.token);
        startActivityForResult(intent, i);
    }

    /* access modifiers changed from: protected */
    public String getStringFromExtResAttrs(UnifyLoginRes unifyLoginRes, String str) {
        try {
            return new JSONObject(unifyLoginRes.data).getJSONObject("extResAttrs").getString(str);
        } catch (Exception unused) {
            AliUserLog.d(TAG, "extResAttrs does not contains ".concat(String.valueOf(str)));
            return "";
        }
    }

    public void initRdsPage(String str) {
        this.mRdsWraper.initPage(this.mApplicationContext, str, getLoginType());
    }

    public void initRdsTextChange(EditText editText, String str) {
        this.mRdsWraper.initTextChange(editText, str);
    }

    public void initRdsFocusChange(View view, String str) {
        this.mRdsWraper.initFocusChange(view, str);
    }

    public void initRdsScreenTouch(View view, String str) {
        this.mRdsWraper.initScreenTouch(view, str);
    }

    public void onRdsControlClick(String str) {
        this.mRdsWraper.onControlClick(str);
    }

    /* access modifiers changed from: protected */
    public void writeClickLog(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getLoginType());
        sb.append(UserTrackerConstants.U_LOGIN);
        writeClickLog(str, str2, sb.toString());
    }

    /* access modifiers changed from: protected */
    public void writeClickLog(String str, String str2, String str3) {
        LogAgent.b(str, str2, str3, getLoginAccount(), this.mToken);
    }

    /* access modifiers changed from: protected */
    public void writeOpenkLog(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(getLoginType());
        sb.append(UserTrackerConstants.U_LOGIN);
        LogAgent.a(str, str2, sb.toString(), getLoginAccount(), this.mToken);
    }

    public void sendBroadCast(Intent intent) {
        LocalBroadcastManager.getInstance(this.mApplicationContext).sendBroadcast(intent);
    }

    public void startPerformanceLog() {
        this.performanceLogAgent = new PerformanceLogAgent();
        this.performanceLogAgent.a();
    }

    private void endPerformanceLog() {
        if (this.performanceLogAgent != null) {
            this.performanceLogAgent.b();
        }
        this.performanceLogAgent = null;
    }

    private void disablePerformanceLog() {
        if (this.performanceLogAgent != null) {
            this.performanceLogAgent.a(false);
        }
        this.performanceLogAgent = null;
    }

    public void toRegist(Account account) {
        writeClickLog("yhzc-1227-01", "djzc");
        RegContext.a().a(this, null, account);
    }

    public void setAppId() {
        this.mAppId = "20000008";
    }

    public void finish() {
        super.finish();
    }
}
