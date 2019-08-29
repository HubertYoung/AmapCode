package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.a.f;
import com.tencent.open.c.b;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.OpenConfig;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class SocialApiIml extends BaseApi {
    /* access modifiers changed from: private */
    public Activity a;

    /* compiled from: ProGuard */
    class a implements IUiListener {
        private IUiListener b;
        private String c;
        private String d;
        private Bundle e;
        private Activity f;

        a(Activity activity, IUiListener iUiListener, String str, String str2, Bundle bundle) {
            this.b = iUiListener;
            this.c = str;
            this.d = str2;
            this.e = bundle;
        }

        public void onComplete(Object obj) {
            String str;
            try {
                str = ((JSONObject) obj).getString(SocialConstants.PARAM_ENCRY_EOKEN);
            } catch (JSONException e2) {
                e2.printStackTrace();
                f.b("openSDK_LOG.SocialApiIml", "OpenApi, EncrytokenListener() onComplete error", e2);
                str = null;
            }
            this.e.putString("encrytoken", str);
            SocialApiIml.this.a((Context) SocialApiIml.this.a, this.c, this.e, this.d, this.b);
            if (TextUtils.isEmpty(str)) {
                f.b("openSDK_LOG.SocialApiIml", "The token get from qq or qzone is empty. Write temp token to localstorage.");
                SocialApiIml.this.writeEncryToken(this.f);
            }
        }

        public void onError(UiError uiError) {
            StringBuilder sb = new StringBuilder("OpenApi, EncryptTokenListener() onError");
            sb.append(uiError.errorMessage);
            f.b("openSDK_LOG.SocialApiIml", sb.toString());
            this.b.onError(uiError);
        }

        public void onCancel() {
            this.b.onCancel();
        }
    }

    public SocialApiIml(QQToken qQToken) {
        super(qQToken);
    }

    public SocialApiIml(QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
    }

    public void gift(Activity activity, Bundle bundle, IUiListener iUiListener) {
        a(activity, SocialConstants.ACTION_GIFT, bundle, iUiListener);
    }

    public void ask(Activity activity, Bundle bundle, IUiListener iUiListener) {
        a(activity, SocialConstants.ACTION_ASK, bundle, iUiListener);
    }

    private void a(Activity activity, String str, Bundle bundle, IUiListener iUiListener) {
        this.a = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_FRIEND_CHOOSER);
        if (agentIntentWithTarget == null) {
            f.c("openSDK_LOG.SocialApiIml", "--askgift--friend chooser not found");
            agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_ASK_GIFT);
        }
        Intent intent = agentIntentWithTarget;
        bundle.putAll(composeActivityParams());
        if (SocialConstants.ACTION_ASK.equals(str)) {
            bundle.putString("type", "request");
        } else if (SocialConstants.ACTION_GIFT.equals(str)) {
            bundle.putString("type", SocialConstants.TYPE_FREEGIFT);
        }
        a(activity, intent, str, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), "http://qzs.qq.com/open/mobile/request/sdk_request.html?"), iUiListener, false);
    }

    public void invite(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.a = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_FRIEND_CHOOSER);
        if (agentIntentWithTarget == null) {
            f.c("openSDK_LOG.SocialApiIml", "--invite--friend chooser not found");
            agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_INVITE);
        }
        bundle.putAll(composeActivityParams());
        a(activity, agentIntentWithTarget, SocialConstants.ACTION_INVITE, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_INVITE), iUiListener, false);
    }

    public void story(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.a = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_STORY);
        bundle.putAll(composeActivityParams());
        a(activity, agentIntentWithTarget, SocialConstants.ACTION_STORY, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_SEND_STORY), iUiListener, false);
    }

    private void a(Activity activity, Intent intent, String str, Bundle bundle, String str2, IUiListener iUiListener, boolean z) {
        StringBuilder sb = new StringBuilder("-->handleIntent action = ");
        sb.append(str);
        sb.append(", activityIntent = null ? ");
        boolean z2 = false;
        sb.append(intent == null);
        f.c("openSDK_LOG.SocialApiIml", sb.toString());
        if (intent != null) {
            a(activity, intent, str, bundle, iUiListener);
            return;
        }
        OpenConfig instance = OpenConfig.getInstance(Global.getContext(), this.mToken.getAppId());
        if (z || instance.getBoolean("C_LoginH5")) {
            z2 = true;
        }
        if (z2) {
            a(activity, str, bundle, str2, iUiListener);
        } else {
            handleDownloadLastestQQ(activity, bundle, iUiListener);
        }
    }

    private void a(Activity activity, Intent intent, String str, Bundle bundle, IUiListener iUiListener) {
        f.c("openSDK_LOG.SocialApiIml", "-->handleIntentWithAgent action = ".concat(String.valueOf(str)));
        intent.putExtra(Constants.KEY_ACTION, str);
        intent.putExtra(Constants.KEY_PARAMS, bundle);
        UIListenerManager.getInstance().setListenerWithRequestcode(Constants.REQUEST_SOCIAL_API, iUiListener);
        startAssitActivity(activity, intent, (int) Constants.REQUEST_SOCIAL_API);
    }

    private void a(Activity activity, String str, Bundle bundle, String str2, IUiListener iUiListener) {
        f.c("openSDK_LOG.SocialApiIml", "-->handleIntentWithH5 action = ".concat(String.valueOf(str)));
        Intent targetActivityIntent = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
        a aVar = new a(activity, iUiListener, str, str2, bundle);
        Intent targetActivityIntent2 = getTargetActivityIntent("com.tencent.open.agent.EncryTokenActivity");
        if (targetActivityIntent2 == null || targetActivityIntent == null || targetActivityIntent.getComponent() == null || targetActivityIntent2.getComponent() == null || !targetActivityIntent.getComponent().getPackageName().equals(targetActivityIntent2.getComponent().getPackageName())) {
            f.c("openSDK_LOG.SocialApiIml", "-->handleIntentWithH5--token activity not found");
            StringBuilder sb = new StringBuilder();
            sb.append("tencent&sdk&qazxc***14969%%");
            sb.append(this.mToken.getAccessToken());
            sb.append(this.mToken.getAppId());
            sb.append(this.mToken.getOpenId());
            sb.append("qzone3.4");
            String encrypt = Util.encrypt(sb.toString());
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(SocialConstants.PARAM_ENCRY_EOKEN, encrypt);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            aVar.onComplete(jSONObject);
        } else {
            targetActivityIntent2.putExtra("oauth_consumer_key", this.mToken.getAppId());
            targetActivityIntent2.putExtra("openid", this.mToken.getOpenId());
            targetActivityIntent2.putExtra("access_token", this.mToken.getAccessToken());
            targetActivityIntent2.putExtra(Constants.KEY_ACTION, SocialConstants.ACTION_CHECK_TOKEN);
            if (hasActivityForIntent(targetActivityIntent2)) {
                f.c("openSDK_LOG.SocialApiIml", "-->handleIntentWithH5--found token activity");
                UIListenerManager.getInstance().setListenerWithRequestcode(11106, aVar);
                startAssitActivity(activity, targetActivityIntent2, 11106);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(Context context, String str, Bundle bundle, String str2, IUiListener iUiListener) {
        f.a("openSDK_LOG.SocialApiIml", "OpenUi, showDialog --start");
        CookieSyncManager.createInstance(context);
        bundle.putString("oauth_consumer_key", this.mToken.getAppId());
        if (this.mToken.isSessionValid()) {
            bundle.putString("access_token", this.mToken.getAccessToken());
        }
        String openId = this.mToken.getOpenId();
        if (openId != null) {
            bundle.putString("openid", openId);
        }
        try {
            bundle.putString(Constants.PARAM_PLATFORM_ID, Global.getContext().getSharedPreferences(Constants.PREFERENCE_PF, 0).getString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF));
        } catch (Exception e) {
            e.printStackTrace();
            bundle.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(Util.encodeUrl(bundle));
        String sb2 = sb.toString();
        f.b("openSDK_LOG.SocialApiIml", "OpenUi, showDialog TDialog");
        if (SocialConstants.ACTION_CHALLENGE.equals(str) || SocialConstants.ACTION_BRAG.equals(str)) {
            f.b("openSDK_LOG.SocialApiIml", "OpenUi, showDialog PKDialog");
            PKDialog pKDialog = new PKDialog(this.a, str, sb2, iUiListener, this.mToken);
            pKDialog.show();
            return;
        }
        TDialog tDialog = new TDialog(this.a, str, sb2, iUiListener, this.mToken);
        tDialog.show();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public void writeEncryToken(Context context) {
        String str;
        String accessToken = this.mToken.getAccessToken();
        String appId = this.mToken.getAppId();
        String openId = this.mToken.getOpenId();
        if (accessToken == null || accessToken.length() <= 0 || appId == null || appId.length() <= 0 || openId == null || openId.length() <= 0) {
            str = null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("tencent&sdk&qazxc***14969%%");
            sb.append(accessToken);
            sb.append(appId);
            sb.append(openId);
            sb.append("qzone3.4");
            str = Util.encrypt(sb.toString());
        }
        b bVar = new b(context);
        WebSettings settings = bVar.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        StringBuilder sb2 = new StringBuilder("<!DOCTYPE HTML><html lang=\"en-US\"><head><meta charset=\"UTF-8\"><title>localStorage Test</title><script type=\"text/javascript\">document.domain = 'qq.com';localStorage[\"");
        sb2.append(this.mToken.getOpenId());
        sb2.append("_");
        sb2.append(this.mToken.getAppId());
        sb2.append("\"]=\"");
        sb2.append(str);
        sb2.append("\";</script></head><body></body></html>");
        String sb3 = sb2.toString();
        String envUrl = ServerSetting.getInstance().getEnvUrl(context, ServerSetting.DEFAULT_LOCAL_STORAGE_URI);
        bVar.loadDataWithBaseURL(envUrl, sb3, "text/html", "utf-8", envUrl);
    }

    public Intent getTargetActivityIntent(String str) {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_QZONE, str);
        Intent intent2 = new Intent();
        intent2.setClassName("com.tencent.mobileqq", str);
        if (SystemUtils.isActivityExist(Global.getContext(), intent2) && SystemUtils.compareQQVersion(Global.getContext(), "4.7") >= 0) {
            return intent2;
        }
        if (!SystemUtils.isActivityExist(Global.getContext(), intent) || SystemUtils.compareVersion(SystemUtils.getAppVersionName(Global.getContext(), Constants.PACKAGE_QZONE), "4.2") < 0 || !SystemUtils.isAppSignatureValid(Global.getContext(), intent.getComponent().getPackageName(), Constants.SIGNATRUE_QZONE)) {
            return null;
        }
        return intent;
    }
}
