package com.ali.auth.third.login.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.webkit.WebView;
import com.ali.auth.third.core.broadcast.LoginAction;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.message.Message;
import com.ali.auth.third.core.message.MessageUtils;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.service.UserTrackerService;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.login.LoginComponent;
import com.ali.auth.third.login.RequestCode;
import com.ali.auth.third.login.UTConstants;
import com.ali.auth.third.login.a.a;
import com.ali.auth.third.login.task.LoginAfterOpenTb;
import com.ali.auth.third.login.task.LoginByIVTokenTask;
import com.ali.auth.third.login.task.RefreshPageAfterOpenTb;
import com.ali.auth.third.login.util.LoginStatus;
import com.ali.auth.third.ui.LoginActivity;
import com.ali.auth.third.ui.context.CallbackContext;
import com.ali.auth.third.ui.support.BaseActivityResultHandler;
import com.ali.auth.third.ui.webview.BaseWebViewActivity;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class LoginActivityResultHandler extends BaseActivityResultHandler {
    /* access modifiers changed from: private */
    public void a(Activity activity, String str, LoginCallback loginCallback) {
        SDKLogger.d("login", "onLoginSuccess ");
        if (loginCallback != null) {
            loginCallback.onSuccess(a.b.getSession());
            ((UserTrackerService) KernelContext.getService(UserTrackerService.class)).send(UTConstants.E_H5_LOGIN_SUCCESS, null);
        }
        if (CallbackContext.mGlobalLoginCallback != null) {
            CallbackContext.mGlobalLoginCallback.onSuccess(a.b.getSession());
        }
        CommonUtils.sendBroadcast(LoginAction.NOTIFY_LOGIN_SUCCESS);
        if (activity != null && (activity instanceof LoginActivity)) {
            CallbackContext.activity = null;
            activity.finish();
            CallbackContext.loginCallback = null;
        }
    }

    /* access modifiers changed from: private */
    public void a(Activity activity, String str, LoginCallback loginCallback, int i) {
        HashMap hashMap;
        UserTrackerService userTrackerService;
        String str2;
        UserTrackerService userTrackerService2;
        String str3;
        SDKLogger.d("login", "onLoginFailure ");
        if (loginCallback != null) {
            Message createMessage = MessageUtils.createMessage(i, new Object[0]);
            loginCallback.onFailure(createMessage.code, createMessage.message);
            if (!UTConstants.E_IV_LOGIN_FAILURE.equals(str)) {
                if (i == 10003) {
                    userTrackerService2 = (UserTrackerService) KernelContext.getService(UserTrackerService.class);
                    str3 = UTConstants.E_H5_LOGIN_CANCEL;
                } else if (i == 10004) {
                    userTrackerService2 = (UserTrackerService) KernelContext.getService(UserTrackerService.class);
                    str3 = UTConstants.E_TB_LOGIN_CANCEL;
                } else {
                    if (i == 10005) {
                        hashMap = new HashMap();
                        int i2 = createMessage.code;
                        String str4 = createMessage.message;
                        hashMap.put("code", String.valueOf(i2));
                        if (!TextUtils.isEmpty(str4)) {
                            hashMap.put("message", str4);
                        }
                        userTrackerService = (UserTrackerService) KernelContext.getService(UserTrackerService.class);
                        str2 = UTConstants.E_TB_LOGIN_FAILURE;
                    } else {
                        hashMap = new HashMap();
                        int i3 = createMessage.code;
                        String str5 = createMessage.message;
                        hashMap.put("code", String.valueOf(i3));
                        if (!TextUtils.isEmpty(str5)) {
                            hashMap.put("message", str5);
                        }
                        userTrackerService = (UserTrackerService) KernelContext.getService(UserTrackerService.class);
                        str2 = UTConstants.E_H5_LOGIN_FAILURE;
                    }
                    userTrackerService.send(str2, hashMap);
                }
                userTrackerService2.send(str3, null);
            }
        }
        if (CallbackContext.mGlobalLoginCallback != null) {
            Message createMessage2 = MessageUtils.createMessage(i, new Object[0]);
            CallbackContext.mGlobalLoginCallback.onFailure(createMessage2.code, createMessage2.message);
        }
        CommonUtils.sendBroadcast((i == 10003 || i == 10004) ? LoginAction.NOTIFY_LOGIN_CANCEL : LoginAction.NOTIFY_LOGIN_FAILED);
        if (activity != null && (activity instanceof LoginActivity)) {
            CallbackContext.activity = null;
            activity.finish();
        }
    }

    private void a(Intent intent, LoginCallback loginCallback) {
        SDKLogger.d("login", "handleCheck");
        WeakReference<Activity> weakReference = CallbackContext.activity;
        if (weakReference == null || weakReference.get() == null || intent == null) {
            KernelContext.executorService.postUITask(new e(this, weakReference, loginCallback));
        } else if (TextUtils.isEmpty(intent.getStringExtra("token"))) {
            KernelContext.executorService.postUITask(new a(this, weakReference, loginCallback));
        } else {
            new LoginByIVTokenTask((Activity) weakReference.get(), new b(this, weakReference, loginCallback)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{intent.getStringExtra("token"), intent.getStringExtra(H5AppUtil.scene), intent.getStringExtra("aliusersdk_h5querystring")});
        }
    }

    public void onCallbackContext(int i, int i2, Intent intent, Activity activity, Map<Class<?>, Object> map, WebView webView) {
        StringBuilder sb = new StringBuilder("onCallbackContext requestCode=");
        sb.append(i);
        sb.append(" resultCode = ");
        sb.append(i2);
        sb.append(" authCode = ");
        sb.append(intent == null ? "" : intent.getStringExtra("result"));
        SDKLogger.d("login", sb.toString());
        LoginStatus.resetLoginFlag();
        LoginCallback loginCallback = (LoginCallback) CallbackContext.loginCallback;
        if (i != RequestCode.OPEN_H5_LOGIN || loginCallback == null) {
            if (i == RequestCode.OPEN_TAOBAO && loginCallback != null) {
                ((UserTrackerService) KernelContext.getService(UserTrackerService.class)).send("AUTH_TAOBAO", null);
                if (i2 == -1 && intent != null) {
                    new LoginAfterOpenTb((Activity) CallbackContext.activity.get(), loginCallback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{intent.getStringExtra("result")});
                } else if (i2 == 0) {
                    a(activity, (String) "E_TB_LOGIN_CANCEL", loginCallback, 10004);
                } else {
                    StringBuilder sb2 = new StringBuilder("result from taobao : ");
                    sb2.append(intent == null ? "" : intent.getStringExtra("result"));
                    SDKLogger.d("login", sb2.toString());
                    a(activity, (String) "E_TB_LOGIN_FAILURE", loginCallback, 10005);
                }
            } else if (i == RequestCode.OPEN_DOUBLE_CHECK) {
                a(intent, loginCallback);
            } else if (i == RequestCode.OPEN_H5_UNBIND) {
                if (i2 == ResultCode.SUCCESS.code) {
                    a(activity, "E_H5_UNBIND_SUCCESS", loginCallback);
                } else {
                    a(activity, (String) "E_H5_UNBIND_FAILURE", loginCallback, 10003);
                }
                CallbackContext.loginCallback = null;
            }
        } else if (i2 == ResultCode.SUCCESS.code) {
            a(activity, "E_H5_LOGIN_SUCCESS", loginCallback);
        } else if (i2 != ResultCode.IGNORE.code) {
            if (i2 == ResultCode.CHECK.code) {
                a(intent, loginCallback);
            } else {
                a(activity, (String) "E_H5_CANCEL_FAILURE", loginCallback, 10003);
            }
        }
    }

    public void onTaeSDKActivity(int i, int i2, Intent intent, BaseWebViewActivity baseWebViewActivity, Map<Class<?>, Object> map, WebView webView) {
        StringBuilder sb = new StringBuilder("onTaeSDKActivity requestCode=");
        sb.append(i);
        sb.append(" resultCode = ");
        sb.append(i2);
        sb.append(" authCode = ");
        sb.append(intent == null ? "" : intent.getStringExtra("result"));
        SDKLogger.d("login", sb.toString());
        LoginCallback loginCallback = (LoginCallback) CallbackContext.loginCallback;
        LoginStatus.resetLoginFlag();
        if (i == RequestCode.OPEN_H5_LOGIN) {
            if (i2 == ResultCode.SUCCESS.code) {
                webView.reload();
            } else {
                baseWebViewActivity.setResult(ResultCode.create(10003, new Object[0]));
            }
        } else if (i != RequestCode.OPEN_TAOBAO) {
            if (i == RequestCode.OPEN_H5_UNBIND) {
                if (i2 == ResultCode.SUCCESS.code) {
                    a(baseWebViewActivity, "E_H5_UNBIND_SUCCESS", loginCallback);
                } else {
                    a((Activity) baseWebViewActivity, (String) "E_H5_UNBIND_FAILURE", loginCallback, 10003);
                }
                CallbackContext.loginCallback = null;
            }
        } else if (i2 == -1 && intent != null) {
            new RefreshPageAfterOpenTb(baseWebViewActivity, webView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{intent.getStringExtra("result")});
        } else if (i2 == 0) {
            baseWebViewActivity.setResult(ResultCode.create(10003, new Object[0]));
        } else {
            SDKLogger.e("login", "taobao return ".concat(String.valueOf(i2)));
            LoginComponent.INSTANCE.showH5Login(baseWebViewActivity);
        }
    }
}
