package com.ali.auth.third.accountlink;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.ali.auth.third.accountlink.ui.BindResultActivity;
import com.ali.auth.third.accountlink.ui.UnbindWebViewActivity;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.config.AuthOption;
import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.cookies.LoginCookieUtils;
import com.ali.auth.third.core.model.LoginReturnData;
import com.ali.auth.third.core.model.RpcRequest;
import com.ali.auth.third.core.model.RpcResponse;
import com.ali.auth.third.core.service.RpcService;
import com.ali.auth.third.core.service.StorageService;
import com.ali.auth.third.core.service.impl.CredentialManager;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.core.util.JSONUtils;
import com.ali.auth.third.core.util.SystemUtils;
import com.ali.auth.third.login.RequestCode;
import com.ali.auth.third.login.task.LoginByReTokenTask;
import com.ali.auth.third.ui.LoginWebViewActivity;
import com.ali.auth.third.ui.context.CallbackContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    public static final a a = new a();

    private a() {
    }

    public RpcResponse<LoginReturnData> a(String str, String str2) {
        String str3;
        String deviceId;
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.target = "mtop.taobao.havana.mlogin.bindtokenlogin";
        rpcRequest.version = "1.0";
        try {
            JSONObject jSONObject = new JSONObject();
            if (KernelContext.isMini) {
                str3 = "app_id";
                StringBuilder sb = new StringBuilder();
                sb.append(KernelContext.context.getPackageName());
                sb.append(MergeUtil.SEPARATOR_KV);
                sb.append(SystemUtils.getApkPublicKeyDigest());
                deviceId = sb.toString();
            } else {
                str3 = "utdid";
                deviceId = ((RpcService) KernelContext.getService(RpcService.class)).getDeviceId();
            }
            jSONObject.put(str3, deviceId);
            jSONObject.put("appName", KernelContext.getAppKey());
            jSONObject.put("token", str);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(System.currentTimeMillis());
            jSONObject.put(LogItem.MM_C15_K4_TIME, sb2.toString());
            jSONObject.put(H5AppUtil.scene, str2);
            jSONObject.put(Constants.KEY_SDK_VERSION, KernelContext.sdkVersion);
            jSONObject.put("clientIp", CommonUtils.getLocalIPAddress());
            try {
                JSONObject keyValues = LoginCookieUtils.getKeyValues("alimm_");
                keyValues.put(com.xiaomi.mipush.sdk.Constants.EXTRA_KEY_MIID, LoginCookieUtils.getValue(com.xiaomi.mipush.sdk.Constants.EXTRA_KEY_MIID));
                jSONObject.put(ProcessInfo.ALIAS_EXT, keyValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
            rpcRequest.addParam("tokenInfo", jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(DictionaryKeys.V2_UMID, ((StorageService) KernelContext.getService(StorageService.class)).getUmid());
            rpcRequest.addParam("riskControlInfo", jSONObject2);
            rpcRequest.addParam(ProcessInfo.ALIAS_EXT, JSONUtils.toJsonObject(new HashMap()));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return com.ali.auth.third.accountlink.a.a.c.invoke(rpcRequest, LoginReturnData.class);
    }

    public void a(int i, int i2, String str, LoginCallback loginCallback) {
        StringBuilder sb = new StringBuilder("handleBindEvent code=");
        sb.append(i2);
        sb.append(" ibb=");
        sb.append(str);
        sb.append(" type=");
        sb.append(i);
        SDKLogger.d("login", sb.toString());
        CallbackContext.loginCallback = loginCallback;
        Intent intent = new Intent();
        intent.setClass(KernelContext.context, BindResultActivity.class);
        intent.putExtra("code", i2);
        intent.putExtra("ibb", str);
        intent.putExtra("type", i);
        intent.setFlags(268435456);
        KernelContext.context.startActivity(intent);
    }

    public void a(int i, String str, LoginCallback loginCallback) {
        StringBuilder sb = new StringBuilder("handleBindEvent code=");
        sb.append(i);
        sb.append(" ibb=");
        sb.append(str);
        SDKLogger.d("login", sb.toString());
        if (TextUtils.isEmpty(CredentialManager.INSTANCE.getInternalSession().autoLoginToken) || CredentialManager.INSTANCE.getInternalSession().user == null || TextUtils.isEmpty(CredentialManager.INSTANCE.getInternalSession().user.userId)) {
            a(1, i, str, loginCallback);
            return;
        }
        SDKLogger.d("login", "handleBindEvent auto login");
        new LoginByReTokenTask(null, new b(this, loginCallback, i, str)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
    }

    public void a(Activity activity) {
        Intent intent = new Intent(activity, UnbindWebViewActivity.class);
        intent.putExtra("url", ConfigManager.unbindUrl);
        activity.startActivityForResult(intent, RequestCode.OPEN_H5_UNBIND);
        if (SDKLogger.isDebugEnabled()) {
            SDKLogger.d("login", "open unbind page");
        }
    }

    public void a(Activity activity, int i, String str) {
        StringBuilder sb = new StringBuilder("showBind code=");
        sb.append(i);
        sb.append(" ibb=");
        sb.append(str);
        SDKLogger.d("login", sb.toString());
        if (KernelContext.authOption == AuthOption.H5ONLY) {
            b(activity, i, str);
            return;
        }
        f fVar = new f(this, SystemUtils.getApkSignNumber(), i, activity, str);
        fVar.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
    }

    public void a(Activity activity, String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            new com.ali.auth.third.accountlink.b.a(activity, new c(this, activity)).execute(new String[]{str, ""});
            return;
        }
        if (CallbackContext.loginCallback != null) {
            a.a(AccountLinkType.COOPERATION_TB_LOGIN, str2, (LoginCallback) CallbackContext.loginCallback);
        }
    }

    public void b(Activity activity, int i, String str) {
        StringBuilder sb = new StringBuilder("showH5Bind code=");
        sb.append(i);
        sb.append(" ibb=");
        sb.append(str);
        SDKLogger.d("login", sb.toString());
        Intent intent = new Intent(activity, LoginWebViewActivity.class);
        intent.putExtra("url", String.format(ConfigManager.bindUrl, new Object[]{String.valueOf(i), str, KernelContext.getAppKey()}));
        int i2 = RequestCode.OPEN_H5_LOGIN;
        com.ali.auth.third.accountlink.a.a.f = false;
        if (i == AccountLinkType.COOPERATION_TB_BIND) {
            com.ali.auth.third.accountlink.a.a.f = true;
            i2 = RequestCode.OPEN_H5_LOGIN;
        }
        activity.startActivityForResult(intent, i2);
        SDKLogger.d("login", "open H5 bind");
    }
}
