package com.ali.auth.third.accountlink;

import android.os.AsyncTask;
import android.text.TextUtils;
import com.ali.auth.third.accountlink.a.a;
import com.ali.auth.third.accountlink.ui.UnbindWebViewActivity;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.config.ConfigManager;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.exception.SecRuntimeException;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.model.SystemMessageConstants;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.core.util.ResourceUtils;
import com.ali.auth.third.login.util.LoginStatus;
import java.util.regex.Pattern;

public class g implements AccountLinkService {
    private transient Pattern[] a;
    private transient Pattern[] b;
    /* access modifiers changed from: private */
    public BindCallback c;

    private void a(LoginCallback loginCallback) {
        SDKLogger.d("bind", "dispatchBindEvent");
        a.f = false;
        if (this.c != null) {
            SDKLogger.d("bind", "dispatchBindEvent mBindCallback not null");
            new h(this, loginCallback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
            return;
        }
        a.e.auth(loginCallback);
    }

    public void bind(LoginCallback loginCallback) {
        SDKLogger.d("bind", "bind start");
        if (!KernelContext.checkServiceValid()) {
            SDKLogger.d("bind", "static field is null");
            if (loginCallback != null) {
                loginCallback.onFailure(SystemMessageConstants.NPE_ERROR, "NullPointException");
            }
        } else if (!CommonUtils.isNetworkAvailable()) {
            SDKLogger.d("bind", "network not available");
            loginCallback.onFailure(SystemMessageConstants.NET_WORK_ERROR, ResourceUtils.getString("com_taobao_tae_sdk_network_not_available_message"));
            LoginStatus.resetLoginFlag();
        } else {
            try {
                KernelContext.getAppKey();
                a(loginCallback);
            } catch (SecRuntimeException e) {
                StringBuilder sb = new StringBuilder("SecurityGuard error:");
                sb.append(e.getMessage());
                SDKLogger.d("bind", sb.toString());
                LoginStatus.resetLoginFlag();
                if (loginCallback != null) {
                    ResultCode create = ResultCode.create(10010, e.getMessage());
                    loginCallback.onFailure(create.code, create.message);
                }
            }
        }
    }

    public BindCallback getBindCallback() {
        return this.c;
    }

    public boolean isBind() {
        return a.f;
    }

    public boolean isLoginUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (this.a == null && !TextUtils.isEmpty(ConfigManager.LOGIN_URLS)) {
            String[] split = ConfigManager.LOGIN_URLS.split("[,]");
            this.a = new Pattern[split.length];
            int length = this.a.length;
            for (int i = 0; i < length; i++) {
                this.a[i] = Pattern.compile(split[i]);
            }
        }
        for (Pattern matcher : this.a) {
            if (matcher.matcher(str).matches()) {
                return true;
            }
        }
        return false;
    }

    public boolean isLogoutUrl(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (this.b == null && !TextUtils.isEmpty(ConfigManager.LOGOUT_URLS)) {
            String[] split = ConfigManager.LOGOUT_URLS.split("[,]");
            this.b = new Pattern[split.length];
            int length = this.b.length;
            for (int i = 0; i < length; i++) {
                this.b[i] = Pattern.compile(split[i]);
            }
        }
        for (Pattern matcher : this.b) {
            if (matcher.matcher(str).matches()) {
                return true;
            }
        }
        return false;
    }

    public void setBindCallback(BindCallback bindCallback) {
        this.c = bindCallback;
    }

    public void unBind(LoginCallback loginCallback) {
        UnbindWebViewActivity.b = loginCallback;
        a.a.a(2, 0, "", loginCallback);
    }
}
