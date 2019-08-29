package com.alipay.mobile.tinyappcustom.api;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager;
import com.alipay.android.phone.inside.api.model.BaseModel;
import com.alipay.android.phone.inside.api.model.account.OAuthAccountUniformityModel;
import com.alipay.android.phone.inside.api.model.tinyapp.TinyAppJumpModel;
import com.alipay.android.phone.inside.service.InsideOperationService;
import com.alipay.mobile.antui.dialog.AUNoticeDialog;
import com.alipay.mobile.antui.dialog.AUNoticeDialog.OnClickPositiveListener;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcustom.biz.AlipayMiniProgramAuthActivity;
import com.alipay.mobile.tinyappcustom.biz.a;
import com.alipay.mobile.tinyappcustom.biz.auth.TinyAppAuthBridge;

public class MiniProgramAuthService extends BroadcastReceiver {
    public static final String LOGIN_TOKEN_INVALID = "com.alipay.android.phone.inside.LOGIN_TOKEN_INVALID";
    private static final String TAG = "MiniProgramAuthService";
    private static MiniProgramAuthService sInstance;
    private AUNoticeDialog dialog;
    private MiniProgramLoginTokenCallback loginTokenCallback;
    /* access modifiers changed from: private */
    public MiniProgramAuthCallback mAuthCallback;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public a mStorageManager;
    private String mUserId;
    private String tokenInvalidInfo = "Token过期";
    private String tokenInvalidPositiveMsg = "确认";
    private String tokenInvalidTitle = "注意";

    private MiniProgramAuthService(Context context) {
        this.mContext = context;
        this.mStorageManager = a.a(context);
        AccountOAuthServiceManager.getInstance().setOAuthService(TinyAppAuthBridge.get());
        H5Log.d(TAG, "register OAuthService to inside");
        try {
            Class clazz = Class.forName("com.alipay.mobile.tinyappcustom.MiniProgramInitService");
            clazz.getDeclaredMethod("init", new Class[0]).invoke(clazz.getDeclaredMethod("get", new Class[0]).invoke(null, new Object[0]), new Object[0]);
        } catch (Throwable e) {
            H5Log.e((String) TAG, "MiniProgramAuthService...e=" + e);
        }
        LocalBroadcastManager.getInstance(context).registerReceiver(this, new IntentFilter(LOGIN_TOKEN_INVALID));
    }

    public static MiniProgramAuthService get(Context context) {
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        if (sInstance == null) {
            synchronized (MiniProgramAuthService.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new MiniProgramAuthService(context);
                    }
                }
            }
        }
        return sInstance;
    }

    @Deprecated
    public void openMiniProgram(String appName, int appIconResId, String sourceAppId, String targetAppId, String userId, String token, Bundle params) {
        AccountOAuthServiceManager.getInstance().setOAuthService(TinyAppAuthBridge.get());
        H5Log.d(TAG, "register OAuthService to inside");
        final String str = targetAppId;
        final String str2 = appName;
        final String str3 = userId;
        final String str4 = token;
        final String str5 = sourceAppId;
        final Bundle bundle = params;
        new Thread(new Runnable() {
            public void run() {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str4)) {
                    try {
                        MiniProgramAuthService.this.openMiniProgramByInside(MiniProgramAuthService.this.mContext, str5, str, str3, str4, bundle);
                    } catch (Throwable e) {
                        H5Log.e((String) MiniProgramAuthService.TAG, "openMiniProgram...e=" + e);
                    }
                }
            }
        }).start();
    }

    @Deprecated
    public void openMiniProgram(String appName, int appIconResId, String appId, MiniProgramAuthCallback callback) {
        AccountOAuthServiceManager.getInstance().setOAuthService(TinyAppAuthBridge.get());
        H5Log.d(TAG, "register OAuthService to inside");
        final String str = appId;
        final String str2 = appName;
        final MiniProgramAuthCallback miniProgramAuthCallback = callback;
        final int i = appIconResId;
        new Thread(new Runnable() {
            public void run() {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && miniProgramAuthCallback != null) {
                    try {
                        MiniProgramAuthService.this.mAuthCallback = miniProgramAuthCallback;
                        MiniProgramAuthService.this.mStorageManager.a();
                        Intent intent = new Intent(MiniProgramAuthService.this.mContext, AlipayMiniProgramAuthActivity.class);
                        intent.putExtra("appName", str2);
                        intent.putExtra("appId", str);
                        intent.putExtra("appIconResId", i);
                        intent.setFlags(268435456);
                        MiniProgramAuthService.this.mContext.startActivity(intent);
                    } catch (Throwable e) {
                        H5Log.e((String) MiniProgramAuthService.TAG, "openMiniProgram...e=" + e);
                    }
                }
            }
        }).start();
    }

    public void openMiniProgram(final String sourceAppId, final String targetAppId, final Bundle params) {
        AccountOAuthServiceManager.getInstance().setOAuthService(TinyAppAuthBridge.get());
        H5Log.d(TAG, "register OAuthService to inside");
        H5Utils.runNotOnMain(H5ThreadType.URGENT, new Runnable() {
            public void run() {
                MiniProgramAuthService.this.openMiniProgramByInside(MiniProgramAuthService.this.mContext, sourceAppId, targetAppId, null, null, params);
            }
        });
    }

    public void openMiniProgramByInside(Context context, String sourceAppId, String targetAppId, String userId, String token, Bundle params) {
        try {
            this.mUserId = userId;
            TinyAppJumpModel model = new TinyAppJumpModel();
            model.setOpenAuthLogin(true);
            model.setAppId(targetAppId);
            model.setAlipayUserId(userId);
            model.setAuthToken(token);
            if (params != null) {
                for (String key : params.keySet()) {
                    Object value = params.get(key);
                    if (value instanceof String) {
                        model.putKV(key, (String) value);
                    }
                }
            }
            String logStr = "";
            TinyAppAuthCallback authCallback = TinyAppAuthManager.get().getAuthCallback();
            if (authCallback != null) {
                OAuthAccountUniformityModel authModel = new OAuthAccountUniformityModel();
                authModel.setMcBindAlipayUid(authCallback.getMcBindAlipayUid());
                logStr = InsideOperationService.getInstance().startAction(context, (BaseModel<T>) authModel).toJsonString();
            }
            H5Log.d(TAG, "openMiniProgramByInside...result=" + InsideOperationService.getInstance().startAction(context, (BaseModel<T>) model).toJsonString() + ", " + logStr);
        } catch (Throwable e) {
            H5Log.e((String) TAG, "openMiniProgramByInside...e=" + e);
        }
    }

    public synchronized MiniProgramAuthCallback getMiniProgramAuthCallback() {
        return this.mAuthCallback;
    }

    public String getUserId() {
        return TinyappUtils.getUserId();
    }

    public void openMiniProgramWithNonLogin(String sourceAppId, String targetAppId, Bundle params) {
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startApp(sourceAppId, targetAppId, params);
    }

    public void onReceive(Context context, Intent intent) {
        if (LOGIN_TOKEN_INVALID.equalsIgnoreCase(intent.getAction())) {
            try {
                if (this.loginTokenCallback != null) {
                    H5Log.e((String) TAG, (String) "login token callback");
                    this.loginTokenCallback.onLoginTokenInvalid();
                }
            } catch (Throwable th) {
                H5Log.e((String) TAG, (String) "login token callback error");
            }
        }
    }

    public void setLoginTokenInvalidMsg(String title, String info, String positiveMsg) {
        this.tokenInvalidTitle = title;
        this.tokenInvalidInfo = info;
        this.tokenInvalidPositiveMsg = positiveMsg;
    }

    public void setLoginTokenCallback(MiniProgramLoginTokenCallback loginTokenCallback2) {
        this.loginTokenCallback = loginTokenCallback2;
    }

    private void handleLoginTokenInvalid() {
        LoggerFactory.getTraceLogger().info(TAG, "handleLoginTokenInvalid");
        MicroApplicationContext microApp = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        if (microApp.getTopActivity() == null) {
            LoggerFactory.getTraceLogger().info(TAG, "handleLoginTokenInvalid no need " + microApp.getTopActivity());
        } else if (microApp.getTopActivity().get() == null || (!((Activity) microApp.getTopActivity().get()).getClass().getName().startsWith(H5Activity.class.getName()) && !((Activity) microApp.getTopActivity().get()).getClass().getName().equals("com.ali.user.mobile.external.OpenAuthTokenLoginActivity"))) {
            LoggerFactory.getTraceLogger().info(TAG, "handleLoginTokenInvalid no need " + microApp.getTopActivity().get());
        } else if (this.dialog == null || !this.dialog.isShowing()) {
            showDialog(microApp);
        } else {
            LoggerFactory.getTraceLogger().info(TAG, "handleLoginTokenInvalid dialog already " + this.dialog);
        }
    }

    private void showDialog(MicroApplicationContext microApp) {
        this.dialog = new AUNoticeDialog((Context) microApp.getTopActivity().get(), this.tokenInvalidTitle, this.tokenInvalidInfo, this.tokenInvalidPositiveMsg, "", false);
        this.dialog.setPositiveListener(new OnClickPositiveListener() {
            public void onClick() {
                LoggerFactory.getTraceLogger().info(MiniProgramAuthService.TAG, "handleLoginTokenInvalid click positive");
                MiniProgramAuthService.this.closeAppAndNotify();
            }
        });
        this.dialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface d) {
                LoggerFactory.getTraceLogger().info(MiniProgramAuthService.TAG, "handleLoginTokenInvalid click cancel");
                MiniProgramAuthService.this.closeAppAndNotify();
            }
        });
        this.dialog.show();
        LoggerFactory.getTraceLogger().info(TAG, "handleLoginTokenInvalid show dialog " + this.dialog + Token.SEPARATOR + microApp.getTopActivity().get());
    }

    /* access modifiers changed from: private */
    public void closeAppAndNotify() {
        MicroApplicationContext microApp = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        if (microApp.getTopApplication() != null) {
            microApp.getTopApplication().destroy(new Bundle());
            LoggerFactory.getTraceLogger().info(TAG, "closeAppAndNotify closeTopApp");
        } else {
            microApp.finishAllApps();
            LoggerFactory.getTraceLogger().info(TAG, "closeAppAndNotify finishAllApps");
        }
        if (this.loginTokenCallback != null) {
            LoggerFactory.getTraceLogger().info(TAG, "closeAppAndNotify onLoginTokenInvalid");
            this.loginTokenCallback.onLoginTokenInvalid();
        }
    }
}
