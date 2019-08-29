package com.tencent.connect.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.TDialog;
import com.tencent.open.a.f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ProGuard */
public abstract class BaseApi {
    protected static final String ACTION_CHECK_TOKEN = "action_check_token";
    protected static final String ACTIVITY_AGENT = "com.tencent.open.agent.AgentActivity";
    protected static final String ACTIVITY_ENCRY_TOKEN = "com.tencent.open.agent.EncryTokenActivity";
    protected static final String DEFAULT_PF = "openmobile_android";
    private static final String KEY_REQUEST_CODE = "key_request_code";
    private static final int MSG_COMPLETE = 0;
    protected static final String PARAM_ENCRY_EOKEN = "encry_token";
    protected static final String PLATFORM = "desktop_m_qq";
    protected static final String PREFERENCE_PF = "pfStore";
    private static final String TAG = "openSDK_LOG.BaseApi";
    protected static final String VERSION = "android";
    public static String businessId = null;
    public static String installChannel = null;
    public static boolean isOEM = false;
    public static String registerChannel;
    protected ProgressDialog mProgressDialog;
    protected QQAuth mQQAuth;
    public QQToken mToken;

    /* compiled from: ProGuard */
    public class TempRequestListener implements IRequestListener {
        private final Handler mHandler;
        /* access modifiers changed from: private */
        public final IUiListener mListener;

        public TempRequestListener(IUiListener iUiListener) {
            this.mListener = iUiListener;
            this.mHandler = new Handler(Global.getContext().getMainLooper(), BaseApi.this) {
                public void handleMessage(Message message) {
                    if (message.what == 0) {
                        TempRequestListener.this.mListener.onComplete(message.obj);
                    } else {
                        TempRequestListener.this.mListener.onError(new UiError(message.what, (String) message.obj, null));
                    }
                }
            };
        }

        public void onComplete(JSONObject jSONObject) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = jSONObject;
            obtainMessage.what = 0;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onIOException(IOException iOException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = iOException.getMessage();
            obtainMessage.what = -2;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onMalformedURLException(MalformedURLException malformedURLException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = malformedURLException.getMessage();
            obtainMessage.what = -3;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onJSONException(JSONException jSONException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = jSONException.getMessage();
            obtainMessage.what = -4;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onConnectTimeoutException(ConnectTimeoutException connectTimeoutException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = connectTimeoutException.getMessage();
            obtainMessage.what = -7;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = socketTimeoutException.getMessage();
            obtainMessage.what = -8;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onNetworkUnavailableException(NetworkUnavailableException networkUnavailableException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = networkUnavailableException.getMessage();
            obtainMessage.what = -10;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onHttpStatusException(HttpStatusException httpStatusException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = httpStatusException.getMessage();
            obtainMessage.what = -9;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onUnknowException(Exception exc) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = exc.getMessage();
            obtainMessage.what = -6;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public void releaseResource() {
    }

    public BaseApi(QQAuth qQAuth, QQToken qQToken) {
        this.mQQAuth = qQAuth;
        this.mToken = qQToken;
    }

    public BaseApi(QQToken qQToken) {
        this(null, qQToken);
    }

    /* access modifiers changed from: protected */
    public Bundle composeCGIParams() {
        Bundle bundle = new Bundle();
        bundle.putString(IjkMediaMeta.IJKM_KEY_FORMAT, "json");
        bundle.putString("status_os", VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        bundle.putString("status_version", VERSION.SDK);
        bundle.putString("sdkv", Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
        if (this.mToken != null && this.mToken.isSessionValid()) {
            bundle.putString("access_token", this.mToken.getAccessToken());
            bundle.putString("oauth_consumer_key", this.mToken.getAppId());
            bundle.putString("openid", this.mToken.getOpenId());
            bundle.putString("appid_for_getting_config", this.mToken.getAppId());
        }
        SharedPreferences sharedPreferences = Global.getContext().getSharedPreferences("pfStore", 0);
        if (isOEM) {
            StringBuilder sb = new StringBuilder("desktop_m_qq-");
            sb.append(installChannel);
            sb.append("-android-");
            sb.append(registerChannel);
            sb.append("-");
            sb.append(businessId);
            bundle.putString(Constants.PARAM_PLATFORM_ID, sb.toString());
        } else {
            bundle.putString(Constants.PARAM_PLATFORM_ID, sharedPreferences.getString(Constants.PARAM_PLATFORM_ID, "openmobile_android"));
        }
        return bundle;
    }

    /* access modifiers changed from: protected */
    public String getCommonDownloadQQUrl(String str) {
        Bundle composeCGIParams = composeCGIParams();
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            composeCGIParams.putString("need_version", str);
        }
        sb.append(ServerSetting.NEED_QQ_VERSION_TIPS_URL);
        sb.append(Util.encodeUrl(composeCGIParams));
        return sb.toString();
    }

    public Bundle composeActivityParams() {
        Bundle bundle = new Bundle();
        bundle.putString("appid", this.mToken.getAppId());
        if (this.mToken.isSessionValid()) {
            bundle.putString(Constants.PARAM_KEY_STR, this.mToken.getAccessToken());
            bundle.putString("keytype", "0x80");
        }
        String openId = this.mToken.getOpenId();
        if (openId != null) {
            bundle.putString("hopenid", openId);
        }
        bundle.putString("platform", "androidqz");
        SharedPreferences sharedPreferences = Global.getContext().getSharedPreferences("pfStore", 0);
        if (isOEM) {
            StringBuilder sb = new StringBuilder("desktop_m_qq-");
            sb.append(installChannel);
            sb.append("-android-");
            sb.append(registerChannel);
            sb.append("-");
            sb.append(businessId);
            bundle.putString(Constants.PARAM_PLATFORM_ID, sb.toString());
        } else {
            bundle.putString(Constants.PARAM_PLATFORM_ID, sharedPreferences.getString(Constants.PARAM_PLATFORM_ID, "openmobile_android"));
            bundle.putString(Constants.PARAM_PLATFORM_ID, "openmobile_android");
        }
        bundle.putString("sdkv", Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
        return bundle;
    }

    private Intent getAssitIntent(Activity activity, Intent intent) {
        Intent intent2 = new Intent(activity.getApplicationContext(), AssistActivity.class);
        intent2.putExtra(SystemUtils.IS_LOGIN, true);
        intent2.putExtra(AssistActivity.EXTRA_INTENT, intent);
        return intent2;
    }

    /* access modifiers changed from: protected */
    public void startAssistActivity(Activity activity, int i, Intent intent, boolean z) {
        Intent intent2 = new Intent(activity.getApplicationContext(), AssistActivity.class);
        if (z) {
            intent2.putExtra(SystemUtils.IS_QQ_MOBILE_SHARE, true);
        }
        intent2.putExtra(AssistActivity.EXTRA_INTENT, intent);
        activity.startActivityForResult(intent2, i);
    }

    /* access modifiers changed from: protected */
    public void startAssistActivity(Activity activity, Bundle bundle, int i, Intent intent) {
        Intent intent2 = new Intent(activity.getApplicationContext(), AssistActivity.class);
        intent2.putExtra(SystemUtils.H5_SHARE_DATA, bundle);
        intent2.putExtra(AssistActivity.EXTRA_INTENT, intent);
        activity.startActivityForResult(intent2, i);
    }

    /* access modifiers changed from: protected */
    public void startAssitActivity(Activity activity, Intent intent, int i) {
        intent.putExtra("key_request_code", i);
        activity.startActivityForResult(getAssitIntent(activity, intent), i);
    }

    /* access modifiers changed from: protected */
    public void startAssitActivity(Fragment fragment, Intent intent, int i) {
        intent.putExtra("key_request_code", i);
        fragment.startActivityForResult(getAssitIntent(fragment.getActivity(), intent), i);
    }

    /* access modifiers changed from: protected */
    public boolean hasActivityForIntent(Intent intent) {
        if (intent != null) {
            return SystemUtils.isActivityExist(Global.getContext(), intent);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public Intent getTargetActivityIntent(String str) {
        Intent intent = new Intent();
        if (Util.isTablet(Global.getContext())) {
            intent.setClassName(Constants.PACKAGE_QQ_PAD, str);
            if (SystemUtils.isActivityExist(Global.getContext(), intent)) {
                return intent;
            }
        }
        intent.setClassName("com.tencent.mobileqq", str);
        if (SystemUtils.isActivityExist(Global.getContext(), intent)) {
            return intent;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void handleDownloadLastestQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(TAG, "--handleDownloadLastestQQ");
        StringBuilder sb = new StringBuilder();
        sb.append(ServerSetting.DOWNLOAD_QQ_URL);
        sb.append(Util.encodeUrl(bundle));
        TDialog tDialog = new TDialog(activity, "", sb.toString(), null, this.mToken);
        tDialog.show();
    }

    /* access modifiers changed from: protected */
    public void showProgressDialog(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            str = "请稍候";
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "正在加载...";
        }
        this.mProgressDialog = ProgressDialog.show(context, str, str2);
        this.mProgressDialog.setCancelable(true);
    }

    /* access modifiers changed from: protected */
    public Intent getAgentIntent() {
        return getTargetActivityIntent(ACTIVITY_AGENT);
    }

    /* access modifiers changed from: protected */
    public Intent getAgentIntentWithTarget(String str) {
        Intent intent = new Intent();
        Intent targetActivityIntent = getTargetActivityIntent(str);
        if (targetActivityIntent == null || targetActivityIntent.getComponent() == null) {
            return null;
        }
        intent.setClassName(targetActivityIntent.getComponent().getPackageName(), ACTIVITY_AGENT);
        return intent;
    }
}
