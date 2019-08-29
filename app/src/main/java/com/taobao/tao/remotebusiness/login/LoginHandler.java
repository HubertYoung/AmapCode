package com.taobao.tao.remotebusiness.login;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.taobao.tao.remotebusiness.RequestPool;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import mtopsdk.common.util.TBSdkLog;
import mtopsdk.common.util.TBSdkLog.LogEnable;
import mtopsdk.mtop.intf.Mtop;

class LoginHandler extends Handler implements onLoginListener {
    private static final String DEFAULT_USERINFO = "DEFAULT";
    public static final int LOGIN_CANCEL = 911103;
    public static final int LOGIN_FAILED = 911102;
    public static final int LOGIN_SUCCESS = 911101;
    public static final int LOGIN_TIMEOUT = 911104;
    private static final String TAG = "mtopsdk.LoginHandler";
    private static Map<String, LoginHandler> mtopLoginHandlerMap = new ConcurrentHashMap();
    @NonNull
    private Mtop mtopInstance;
    @Nullable
    private String userInfo;

    private LoginHandler(@NonNull Mtop mtop, @Nullable String str, Looper looper) {
        super(looper);
        this.mtopInstance = mtop;
        this.userInfo = str;
    }

    public static LoginHandler instance(@NonNull Mtop mtop, @Nullable String str) {
        Mtop a = mtop == null ? Mtop.a((Context) null) : mtop;
        if (fdd.b(str)) {
            str = DEFAULT_USERINFO;
        }
        String key = getKey(mtop, str);
        LoginHandler loginHandler = mtopLoginHandlerMap.get(key);
        if (loginHandler == null) {
            synchronized (LoginHandler.class) {
                try {
                    loginHandler = mtopLoginHandlerMap.get(key);
                    if (loginHandler == null) {
                        loginHandler = new LoginHandler(a, str, Looper.getMainLooper());
                        mtopLoginHandlerMap.put(key, loginHandler);
                    }
                }
            }
        }
        return loginHandler;
    }

    @Deprecated
    public static LoginHandler instance() {
        return instance(Mtop.a((Context) null), null);
    }

    public void handleMessage(Message message) {
        String key = getKey(this.mtopInstance, this.userInfo);
        if (TBSdkLog.a(LogEnable.InfoEnable)) {
            StringBuilder sb = new StringBuilder();
            sb.append(key);
            sb.append(" [handleMessage]The MtopBusiness LoginHandler receive message .");
            TBSdkLog.b(TAG, sb.toString());
        }
        switch (message.what) {
            case LOGIN_SUCCESS /*911101*/:
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(key);
                    sb2.append(" [handleMessage]onReceive: NOTIFY_LOGIN_SUCCESS.");
                    TBSdkLog.b(TAG, sb2.toString());
                }
                updateXStateSessionInfo(key);
                RequestPool.retryAllRequest(this.mtopInstance, this.userInfo);
                removeMessages(LOGIN_TIMEOUT);
                return;
            case LOGIN_FAILED /*911102*/:
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(key);
                    sb3.append("[handleMessage]onReceive: NOTIFY_LOGIN_FAILED.");
                    TBSdkLog.b(TAG, sb3.toString());
                }
                RequestPool.failAllRequest(this.mtopInstance, this.userInfo, "ANDROID_SYS_LOGIN_FAIL", "登录失败");
                removeMessages(LOGIN_TIMEOUT);
                return;
            case LOGIN_CANCEL /*911103*/:
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(key);
                    sb4.append("[handleMessage]onReceive: NOTIFY_LOGIN_CANCEL.");
                    TBSdkLog.b(TAG, sb4.toString());
                }
                RequestPool.failAllRequest(this.mtopInstance, this.userInfo, "ANDROID_SYS_LOGIN_CANCEL", "登录被取消");
                removeMessages(LOGIN_TIMEOUT);
                return;
            case LOGIN_TIMEOUT /*911104*/:
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(key);
                    sb5.append("[handleMessage]onReceive: NOTIFY_LOGIN_TIMEOUT.");
                    TBSdkLog.b(TAG, sb5.toString());
                }
                if (RemoteLogin.isSessionValid(this.mtopInstance, this.userInfo)) {
                    if (TBSdkLog.a(LogEnable.InfoEnable)) {
                        TBSdkLog.b(TAG, "Session valid, Broadcast may missed!");
                    }
                    updateXStateSessionInfo(key);
                    RequestPool.retryAllRequest(this.mtopInstance, this.userInfo);
                    break;
                }
                break;
        }
    }

    private void updateXStateSessionInfo(String str) {
        LoginContext loginContext = RemoteLogin.getLoginContext(this.mtopInstance, this.userInfo);
        if (loginContext == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" [updateXStateSessionInfo] LoginContext is null.");
            TBSdkLog.d(TAG, sb.toString());
            return;
        }
        try {
            if (fdd.a(loginContext.sid) && !loginContext.sid.equals(this.mtopInstance.c(this.userInfo))) {
                this.mtopInstance.a(this.userInfo, loginContext.sid, loginContext.userId);
                if (TBSdkLog.a(LogEnable.InfoEnable)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(" [updateXStateSessionInfo] invoked.");
                    TBSdkLog.b(TAG, sb2.toString());
                }
            }
        } catch (Exception e) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(" [updateXStateSessionInfo] error.");
            TBSdkLog.b((String) TAG, sb3.toString(), (Throwable) e);
        }
    }

    private static String getKey(@NonNull Mtop mtop, @Nullable String str) {
        if (fdd.b(str)) {
            str = DEFAULT_USERINFO;
        }
        return fdd.a(mtop.b, str);
    }

    public void onLoginSuccess() {
        sendEmptyMessage(LOGIN_SUCCESS);
    }

    public void onLoginFail() {
        sendEmptyMessage(LOGIN_FAILED);
    }

    public void onLoginCancel() {
        sendEmptyMessage(LOGIN_CANCEL);
    }
}
