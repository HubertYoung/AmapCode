package com.sina.weibo.sdk.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.WeiboAppManager;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.sso.WeiboSsoManager;
import com.sina.weibo.sdk.statistic.WBAgent;
import com.sina.weibo.sdk.utils.LogUtil;
import com.sina.weibo.sdk.utils.NetworkHelper;
import com.sina.weibo.sdk.utils.SecurityHelper;
import com.sina.weibo.sdk.utils.UIUtils;
import com.sina.weibo.sdk.utils.Utility;
import com.sina.weibo.sdk.utils.WbAuthConstants;
import com.sina.weibo.sdk.utils.WbSdkVersion;
import com.sina.weibo.sdk.web.WebRequestType;
import com.sina.weibo.sdk.web.WeiboCallbackManager;
import com.sina.weibo.sdk.web.WeiboSdkWebActivity;
import com.sina.weibo.sdk.web.param.AuthWebViewRequestParam;

public class BaseSsoHandler {
    public static final String OAUTH2_BASE_URL = "https://open.weibo.cn/oauth2/authorize?";
    protected final int SSO_TYPE_INVALID = 3;
    protected WbAuthListener authListener;
    protected Context mAuthActivity;
    protected int ssoRequestCode = -1;
    protected int ssoRequestType = 3;

    public enum AuthType {
        ALL,
        SsoOnly,
        WebOnly
    }

    /* access modifiers changed from: protected */
    public void fillExtraIntent(Intent intent, int i) {
    }

    public BaseSsoHandler(Activity activity) {
        this.mAuthActivity = activity;
        WeiboSsoManager.getInstance().init(activity, WbSdk.getAuthInfo().getAppKey());
    }

    public BaseSsoHandler(Context context) {
        this.mAuthActivity = context;
        WeiboSsoManager.getInstance().init(context, WbSdk.getAuthInfo().getAppKey());
    }

    public void authorize(WbAuthListener wbAuthListener) {
        authorize(WbAuthConstants.REQUEST_CODE_SSO_AUTH, wbAuthListener, AuthType.ALL);
    }

    public void authorizeClientSso(WbAuthListener wbAuthListener) {
        authorize(WbAuthConstants.REQUEST_CODE_SSO_AUTH, wbAuthListener, AuthType.SsoOnly);
    }

    public void authorizeWeb(WbAuthListener wbAuthListener) {
        authorize(WbAuthConstants.REQUEST_CODE_SSO_AUTH, wbAuthListener, AuthType.WebOnly);
    }

    private void authorize(int i, WbAuthListener wbAuthListener, AuthType authType) {
        resetIntentFillData();
        if (wbAuthListener == null) {
            throw new RuntimeException("please set auth listener");
        }
        this.authListener = wbAuthListener;
        if (authType == AuthType.WebOnly) {
            startWebAuth();
            return;
        }
        boolean z = false;
        if (authType == AuthType.SsoOnly) {
            z = true;
        }
        WbAppInfo wbAppInfo = WeiboAppManager.getInstance(this.mAuthActivity).getWbAppInfo();
        if (isWbAppInstalled() && wbAppInfo != null) {
            startClientAuth(i);
        } else if (z) {
            this.authListener.onFailure(new WbConnectErrorMessage());
        } else {
            startWebAuth();
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0071, code lost:
        if (r4.authListener != null) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0073, code lost:
        r4.authListener.onFailure(new com.sina.weibo.sdk.auth.WbConnectErrorMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x007d, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x006f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void startClientAuth(int r5) {
        /*
            r4 = this;
            android.content.Context r0 = r4.mAuthActivity     // Catch:{ Exception -> 0x007e }
            com.sina.weibo.sdk.WeiboAppManager r0 = com.sina.weibo.sdk.WeiboAppManager.getInstance(r0)     // Catch:{ Exception -> 0x007e }
            com.sina.weibo.sdk.auth.WbAppInfo r0 = r0.getWbAppInfo()     // Catch:{ Exception -> 0x007e }
            android.content.Intent r1 = new android.content.Intent     // Catch:{ Exception -> 0x007e }
            r1.<init>()     // Catch:{ Exception -> 0x007e }
            java.lang.String r2 = r0.getPackageName()     // Catch:{ Exception -> 0x007e }
            java.lang.String r0 = r0.getAuthActivityName()     // Catch:{ Exception -> 0x007e }
            r1.setClassName(r2, r0)     // Catch:{ Exception -> 0x007e }
            com.sina.weibo.sdk.auth.AuthInfo r0 = com.sina.weibo.sdk.WbSdk.getAuthInfo()     // Catch:{ Exception -> 0x007e }
            android.os.Bundle r0 = r0.getAuthBundle()     // Catch:{ Exception -> 0x007e }
            r1.putExtras(r0)     // Catch:{ Exception -> 0x007e }
            java.lang.String r0 = "_weibo_command_type"
            r2 = 3
            r1.putExtra(r0, r2)     // Catch:{ Exception -> 0x007e }
            java.lang.String r0 = "_weibo_transaction"
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x007e }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x007e }
            r1.putExtra(r0, r2)     // Catch:{ Exception -> 0x007e }
            java.lang.String r0 = "aid"
            android.content.Context r2 = r4.mAuthActivity     // Catch:{ Exception -> 0x007e }
            com.sina.weibo.sdk.auth.AuthInfo r3 = com.sina.weibo.sdk.WbSdk.getAuthInfo()     // Catch:{ Exception -> 0x007e }
            java.lang.String r3 = r3.getAppKey()     // Catch:{ Exception -> 0x007e }
            java.lang.String r2 = com.sina.weibo.sdk.utils.Utility.getAid(r2, r3)     // Catch:{ Exception -> 0x007e }
            r1.putExtra(r0, r2)     // Catch:{ Exception -> 0x007e }
            android.content.Context r0 = r4.mAuthActivity     // Catch:{ Exception -> 0x007e }
            boolean r0 = com.sina.weibo.sdk.utils.SecurityHelper.validateAppSignatureForIntent(r0, r1)     // Catch:{ Exception -> 0x007e }
            if (r0 != 0) goto L_0x0062
            com.sina.weibo.sdk.auth.WbAuthListener r5 = r4.authListener     // Catch:{ Exception -> 0x007e }
            com.sina.weibo.sdk.auth.WbConnectErrorMessage r0 = new com.sina.weibo.sdk.auth.WbConnectErrorMessage     // Catch:{ Exception -> 0x007e }
            java.lang.String r1 = "your install weibo app is counterfeit"
            java.lang.String r2 = "8001"
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x007e }
            r5.onFailure(r0)     // Catch:{ Exception -> 0x007e }
            return
        L_0x0062:
            r4.fillExtraIntent(r1, r5)     // Catch:{ Exception -> 0x007e }
            android.content.Context r5 = r4.mAuthActivity     // Catch:{ Exception -> 0x006f }
            android.app.Activity r5 = (android.app.Activity) r5     // Catch:{ Exception -> 0x006f }
            int r0 = r4.ssoRequestCode     // Catch:{ Exception -> 0x006f }
            r5.startActivityForResult(r1, r0)     // Catch:{ Exception -> 0x006f }
            return
        L_0x006f:
            com.sina.weibo.sdk.auth.WbAuthListener r5 = r4.authListener     // Catch:{ Exception -> 0x007e }
            if (r5 == 0) goto L_0x007d
            com.sina.weibo.sdk.auth.WbAuthListener r5 = r4.authListener     // Catch:{ Exception -> 0x007e }
            com.sina.weibo.sdk.auth.WbConnectErrorMessage r0 = new com.sina.weibo.sdk.auth.WbConnectErrorMessage     // Catch:{ Exception -> 0x007e }
            r0.<init>()     // Catch:{ Exception -> 0x007e }
            r5.onFailure(r0)     // Catch:{ Exception -> 0x007e }
        L_0x007d:
            return
        L_0x007e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.auth.BaseSsoHandler.startClientAuth(int):void");
    }

    /* access modifiers changed from: protected */
    public void resetIntentFillData() {
        this.ssoRequestCode = WbAuthConstants.REQUEST_CODE_SSO_AUTH;
    }

    /* access modifiers changed from: protected */
    public void startWebAuth() {
        String str;
        AuthInfo authInfo = WbSdk.getAuthInfo();
        WeiboParameters weiboParameters = new WeiboParameters(authInfo.getAppKey());
        weiboParameters.put((String) "client_id", authInfo.getAppKey());
        weiboParameters.put((String) WBConstants.AUTH_PARAMS_REDIRECT_URL, authInfo.getRedirectUrl());
        weiboParameters.put((String) "scope", authInfo.getScope());
        weiboParameters.put((String) WBConstants.AUTH_PARAMS_RESPONSE_TYPE, (String) "code");
        weiboParameters.put((String) "version", (String) WbSdkVersion.WEIBO_SDK_VERSION_CODE);
        weiboParameters.put((String) "luicode", (String) "10000360");
        Oauth2AccessToken readAccessToken = AccessTokenKeeper.readAccessToken(this.mAuthActivity);
        if (readAccessToken != null && !TextUtils.isEmpty(readAccessToken.getToken())) {
            weiboParameters.put((String) "trans_token", readAccessToken.getToken());
            weiboParameters.put((String) "trans_access_token", readAccessToken.getToken());
        }
        StringBuilder sb = new StringBuilder("OP_");
        sb.append(authInfo.getAppKey());
        weiboParameters.put((String) "lfid", sb.toString());
        String aid = Utility.getAid(this.mAuthActivity, authInfo.getAppKey());
        if (!TextUtils.isEmpty(aid)) {
            weiboParameters.put((String) "aid", aid);
        }
        weiboParameters.put((String) "packagename", authInfo.getPackageName());
        weiboParameters.put((String) "key_hash", authInfo.getKeyHash());
        StringBuilder sb2 = new StringBuilder(OAUTH2_BASE_URL);
        sb2.append(weiboParameters.encodeUrl());
        String sb3 = sb2.toString();
        if (!NetworkHelper.hasInternetPermission(this.mAuthActivity)) {
            UIUtils.showAlert(this.mAuthActivity, (String) "Error", (String) "Application requires permission to access the Internet");
            return;
        }
        if (this.authListener != null) {
            WeiboCallbackManager instance = WeiboCallbackManager.getInstance();
            String genCallbackKey = instance.genCallbackKey();
            instance.setWeiboAuthListener(genCallbackKey, this.authListener);
            str = genCallbackKey;
        } else {
            str = null;
        }
        AuthWebViewRequestParam authWebViewRequestParam = new AuthWebViewRequestParam(authInfo, WebRequestType.AUTH, str, "微博登录", sb3, this.mAuthActivity);
        Intent intent = new Intent(this.mAuthActivity, WeiboSdkWebActivity.class);
        Bundle bundle = new Bundle();
        authWebViewRequestParam.fillBundle(bundle);
        intent.putExtras(bundle);
        this.mAuthActivity.startActivity(intent);
    }

    public void authorizeCallBack(int i, int i2, Intent intent) {
        if (32973 == i) {
            if (i2 == -1) {
                if (!SecurityHelper.checkResponseAppLegal(this.mAuthActivity, WeiboAppManager.getInstance(this.mAuthActivity).getWbAppInfo(), intent)) {
                    this.authListener.onFailure(new WbConnectErrorMessage(WbAuthConstants.AUTH_FAILED_INSTALL_APP_COUNTERFEIT_MESSAGE, WbAuthConstants.AUTH_FAILED_INSTALL_APP_COUNTERFEIT_CODE));
                    return;
                }
                String safeString = Utility.safeString(intent.getStringExtra("error"));
                String safeString2 = Utility.safeString(intent.getStringExtra("error_type"));
                String safeString3 = Utility.safeString(intent.getStringExtra("error_description"));
                StringBuilder sb = new StringBuilder("error: ");
                sb.append(safeString);
                sb.append(", error_type: ");
                sb.append(safeString2);
                sb.append(", error_description: ");
                sb.append(safeString3);
                LogUtil.d(WBAgent.TAG, sb.toString());
                if (TextUtils.isEmpty(safeString) && TextUtils.isEmpty(safeString2) && TextUtils.isEmpty(safeString3)) {
                    Oauth2AccessToken parseAccessToken = Oauth2AccessToken.parseAccessToken(intent.getExtras());
                    if (parseAccessToken != null && parseAccessToken.isSessionValid()) {
                        StringBuilder sb2 = new StringBuilder("Login Success! ");
                        sb2.append(parseAccessToken.toString());
                        LogUtil.d(WBAgent.TAG, sb2.toString());
                        AccessTokenKeeper.writeAccessToken(this.mAuthActivity, parseAccessToken);
                        this.authListener.onSuccess(parseAccessToken);
                    }
                } else if ("access_denied".equals(safeString) || "OAuthAccessDeniedException".equals(safeString)) {
                    LogUtil.d(WBAgent.TAG, "Login canceled by user.");
                    this.authListener.cancel();
                } else {
                    LogUtil.d(WBAgent.TAG, "Login failed: ".concat(String.valueOf(safeString)));
                    this.authListener.onFailure(new WbConnectErrorMessage(safeString2, safeString3));
                }
            } else if (i2 == 0) {
                this.authListener.cancel();
            }
        }
    }

    @Deprecated
    public boolean isWbAppInstalled() {
        return WbSdk.isWbInstall(this.mAuthActivity);
    }
}
