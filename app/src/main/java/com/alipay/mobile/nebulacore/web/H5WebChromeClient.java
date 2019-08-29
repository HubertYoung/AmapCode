package com.alipay.mobile.nebulacore.web;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.ValueCallback;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5Data;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5WebDriverHelper;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DevDebugProvider;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider.OnClickNegativeListener;
import com.alipay.mobile.nebula.provider.H5DialogManagerProvider.OnClickPositiveListener;
import com.alipay.mobile.nebula.provider.H5LocationDialogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APJsPromptResult;
import com.alipay.mobile.nebula.webview.APJsResult;
import com.alipay.mobile.nebula.webview.APWebChromeClient;
import com.alipay.mobile.nebula.webview.APWebChromeClient.CustomViewCallback;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.alipay.mobile.nebulacore.ui.H5Activity;
import com.alipay.mobile.nebulacore.ui.H5FileChooserActivity;
import com.alipay.mobile.nebulacore.ui.H5PromptDialog;
import com.alipay.mobile.nebulacore.view.H5Dialog;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;

public class H5WebChromeClient implements APWebChromeClient {
    public static final String FILE_CHOOSER_RESULT = "FILE_CHOOSER_RESULT";
    private static JSONArray h = null;
    public String TAG = "H5WebChromeClient";
    /* access modifiers changed from: private */
    public H5Page a;
    private H5PageData b;
    /* access modifiers changed from: private */
    public BroadcastReceiver c;
    private H5WebDriverHelper d;
    private Handler e;
    private boolean f = true;
    private boolean g = true;
    public CustomViewCallback mCustomViewCallback;

    public H5WebChromeClient(H5PageImpl page) {
        this.TAG += hashCode();
        if (page != null && H5Utils.getBoolean(page.getParams(), (String) H5Param.LONG_ISPRERENDER, false)) {
            this.TAG += "_preRender";
        }
        this.a = page;
        this.b = page.getPageData();
        this.d = Nebula.getService().getWebDriverHelper();
        if ("NO".equals(H5Environment.getConfigWithProcessCache("h5_asyncConsoleMessage"))) {
            this.f = false;
        }
        if ("NO".equalsIgnoreCase(H5Environment.getConfigWithProcessCache("h5_handleUncaughtJsError"))) {
            this.g = false;
        }
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        this.d.onConsoleMessage(consoleMessage);
        if (consoleMessage == null) {
            return false;
        }
        String message = consoleMessage.message();
        int lineNumber = consoleMessage.lineNumber();
        H5Log.d(this.TAG, "onConsoleMessage " + message + " lineNumber " + lineNumber);
        String tmpMessage = message;
        if ("[RENDER] setData".equalsIgnoreCase(message)) {
            H5Utils.handleTinyAppKeyEvent((String) "main", (String) "render_setData");
        }
        if (this.g && H5Utils.isUCM57() && consoleMessage.messageLevel() == MessageLevel.ERROR) {
            tmpMessage = a(message, lineNumber);
        }
        if (H5BridgePolicy.get() == 0) {
            return a(tmpMessage);
        }
        return false;
    }

    private String a(String message, int lineNumber) {
        try {
            if (TextUtils.isEmpty(message) || !message.startsWith("jserror:")) {
                return message;
            }
            JSONObject jsErrorContent = new JSONObject();
            jsErrorContent.put((String) "name", (Object) H5PageData.JS_ERRORS);
            jsErrorContent.put((String) "value", (Object) message);
            jsErrorContent.put((String) "lineno", (Object) Integer.valueOf(lineNumber));
            JSONArray dataContent = new JSONArray();
            dataContent.add(jsErrorContent);
            JSONObject dataMsg = new JSONObject();
            dataMsg.put((String) "data", (Object) dataContent);
            JSONObject fakePerformanceMsg = new JSONObject();
            fakePerformanceMsg.put((String) "func", (Object) H5Param.MONITOR_PERFORMANCE);
            fakePerformanceMsg.put((String) "param", (Object) dataMsg);
            fakePerformanceMsg.put((String) "msgType", (Object) "call");
            fakePerformanceMsg.put((String) "clientId", (Object) "default_clientId");
            return "h5container.message: " + fakePerformanceMsg.toString();
        } catch (Throwable e2) {
            H5Log.e(this.TAG, "handleUncaughtJsError error : ", e2);
            return message;
        }
    }

    private void a(String message, String func) {
        if (this.a != null && H5ProviderManagerImpl.getInstance().getProvider(H5DevDebugProvider.class.getName()) != null) {
            final JSONObject param = new JSONObject();
            param.put((String) "content", (Object) message);
            if (func != null) {
                param.put((String) "func", (Object) func);
            }
            if (this.e == null) {
                this.e = new Handler();
            }
            this.e.post(new Runnable() {
                public void run() {
                    if (H5WebChromeClient.this.a != null) {
                        H5WebChromeClient.this.a.sendEvent(CommonEvents.H5_DEV_CONSOLE, param);
                    }
                }
            });
        }
    }

    private boolean a(String message) {
        if (TextUtils.isEmpty(message)) {
            return false;
        }
        String msgText = null;
        if (message.startsWith("h5container.message: ")) {
            msgText = message.replaceFirst("h5container.message: ", "");
        }
        if (this.a == null || !TextUtils.isEmpty(msgText)) {
            JSONObject joMessage = H5Utils.parseObject(msgText);
            if (joMessage == null || joMessage.isEmpty()) {
                return false;
            }
            String clientId = H5Utils.getString(joMessage, (String) "clientId");
            String name = H5Utils.getString(joMessage, (String) "func");
            String msgType = H5Utils.getString(joMessage, (String) "msgType");
            boolean keep = H5Utils.getBoolean(joMessage, (String) H5Param.KEEP_CALLBACK, false);
            a(message, name);
            if (TextUtils.isEmpty(clientId)) {
                return false;
            }
            JSONObject params = H5Utils.getJSONObject(joMessage, "param", null);
            if (this.a == null) {
                return true;
            }
            final H5Bridge bridge = this.a.getBridge();
            if (bridge == null) {
                H5Log.e(this.TAG, (String) "bridge == null");
                return false;
            }
            Builder builder = new Builder();
            builder.action(name).param(params).target(this.a).type(msgType).id(clientId).keepCallback(keep);
            final H5Event event = builder.build();
            if (this.f) {
                if (h == null) {
                    H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) Nebula.getProviderManager().getProvider(H5ConfigProvider.class.getName());
                    if (h5ConfigProvider != null) {
                        h = H5Utils.parseArray(h5ConfigProvider.getConfig("h5_syncJsApi"));
                    }
                }
                if (h == null || !h.contains(event.getAction())) {
                    if (this.e == null) {
                        this.e = new Handler();
                    }
                    this.e.post(new Runnable() {
                        public void run() {
                            bridge.sendToNative(event);
                        }
                    });
                    return true;
                }
            }
            bridge.sendToNative(event);
            return true;
        }
        if (!message.startsWith("invokeJS msgType") && !message.contains("load AlipayJSBridge")) {
            H5Log.d("NBH5AppContentLog", "AppId/" + H5Utils.getString(this.a.getParams(), (String) "appId") + "," + message);
        }
        a(message, (String) null);
        return false;
    }

    public Bitmap getDefaultVideoPoster() {
        return null;
    }

    public void onProgressChanged(APWebView view, int newProgress) {
        if (Nebula.DEBUG) {
            H5Log.d(this.TAG, "onProgressChanged [progress] " + newProgress);
        }
        if (this.a != null) {
            JSONObject param = new JSONObject();
            param.put((String) "progress", (Object) Integer.valueOf(newProgress));
            this.a.sendEvent(CommonEvents.H5_PAGE_PROGRESS, param);
        }
    }

    public void onReceivedTitle(APWebView view, String title) {
        H5Log.d(this.TAG, "onReceivedTitle [title] " + title + Token.SEPARATOR + view.getUrl());
        this.b.setTitle(title);
        if (this.a != null) {
            view.loadUrl("javascript:{window.__alipayConsole__ = window.console}");
            JSONObject param = new JSONObject();
            param.put((String) "url", (Object) view.getUrl());
            param.put((String) "title", (Object) title);
            this.a.sendEvent(CommonEvents.H5_PAGE_RECEIVED_TITLE, param);
        }
    }

    public void onReceivedIcon(APWebView view, Bitmap icon) {
        if (Nebula.DEBUG) {
            H5Log.d(this.TAG, "onReceivedIcon");
        }
    }

    public void onReceivedTouchIconUrl(APWebView view, String url, boolean precomposed) {
        if (Nebula.DEBUG) {
            H5Log.d(this.TAG, "onReceivedTouchIconUrl. [url] " + url + " [precomposed] " + precomposed);
        }
    }

    public boolean onJsBeforeUnload(APWebView view, String url, String message, APJsResult result) {
        if (Nebula.DEBUG) {
            H5Log.d(this.TAG, "onJsBeforeUnload [url] " + url + " [message] " + message);
        }
        return false;
    }

    public void onGeolocationPermissionsShowPrompt(final String origin, final Callback callback) {
        H5Log.d(this.TAG, "onGeolocationPermissionsShowPrompt " + origin);
        if (this.a == null) {
            callback.invoke(origin, true, true);
            return;
        }
        final String finalDomain = H5UrlHelper.parseUrl(this.a.getUrl()).getHost();
        H5ConfigProvider configProvider = (H5ConfigProvider) H5ProviderManagerImpl.getInstance().getProvider(H5ConfigProvider.class.getName());
        H5Data data = Nebula.getService().getData();
        boolean isH5app = H5Utils.getBoolean(this.a.getParams(), (String) H5Param.isH5app, false);
        if (TextUtils.isEmpty(finalDomain) || ((data != null && "Y".equals(data.get(finalDomain))) || isH5app || (configProvider != null && configProvider.permitLocation(this.a.getUrl())))) {
            callback.invoke(origin, true, true);
            return;
        }
        OnClickListener listener = new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == -1) {
                    callback.invoke(origin, true, true);
                    H5Data data = Nebula.getService().getData();
                    if (data != null) {
                        data.set(finalDomain, "Y");
                        return;
                    }
                    return;
                }
                callback.invoke(origin, false, false);
            }
        };
        if (!(this.a.getContext().getContext() instanceof Activity)) {
            H5Log.d(this.TAG, "not H5Activity");
            callback.invoke(origin, true, true);
            return;
        }
        Activity activity = (Activity) this.a.getContext().getContext();
        if (activity.isFinishing()) {
            H5Log.d(this.TAG, "isFinishing");
            callback.invoke(origin, true, true);
            return;
        }
        a(activity, finalDomain, listener);
    }

    private static void a(Activity activity, String finalDomain, OnClickListener listener) {
        H5LocationDialogProvider dialogProvider = (H5LocationDialogProvider) Nebula.getProviderManager().getProvider(H5LocationDialogProvider.class.getName());
        if (dialogProvider != null) {
            AlertDialog dialog = dialogProvider.createLocationDialog(activity, finalDomain, listener);
            if (dialog != null) {
                dialog.show();
                return;
            }
            return;
        }
        listener.onClick(null, -1);
    }

    public void onGeolocationPermissionsHidePrompt() {
    }

    public View getVideoLoadingProgressView() {
        H5Log.debug(this.TAG, "getVideoLoadingProgressView");
        return null;
    }

    public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
    }

    public void openFileChooser(ValueCallback callback, boolean array) {
        if (this.a != null && this.a.getContentView() != null) {
            try {
                final Activity activity = getActivityFromContext(this.a.getContentView().getContext());
                if (activity != null) {
                    final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(H5Environment.getContext());
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(FILE_CHOOSER_RESULT);
                    if (this.c != null) {
                        manager.unregisterReceiver(this.c);
                        this.c = null;
                    }
                    final ValueCallback valueCallback = callback;
                    final boolean z = array;
                    this.c = new BroadcastReceiver() {
                        public void onReceive(Context context, Intent resultIntent) {
                            Object object;
                            Object object2;
                            if (resultIntent != null && resultIntent.getAction() != null && resultIntent.getExtras() != null && manager != null) {
                                manager.unregisterReceiver(this);
                                H5WebChromeClient.this.c = null;
                                String action = resultIntent.getAction();
                                H5Log.d(H5WebChromeClient.this.TAG, "onReceive action " + action);
                                if (H5WebChromeClient.FILE_CHOOSER_RESULT.equals(action)) {
                                    Cursor cursor = null;
                                    try {
                                        Uri resultUri = (Uri) resultIntent.getExtras().get("fileUri");
                                        if (resultUri == 0) {
                                            resultUri = Uri.parse("");
                                        }
                                        if (!(activity == null || activity.isFinishing() || resultUri == 0)) {
                                            cursor = activity.getContentResolver().query(resultUri, null, null, null, null);
                                        }
                                        H5Log.d(H5WebChromeClient.this.TAG, "onReceive result " + resultUri);
                                        if (valueCallback != null) {
                                            if (!z) {
                                                object2 = resultUri;
                                            } else {
                                                object2 = new Uri[]{resultUri};
                                            }
                                            valueCallback.onReceiveValue(object2);
                                        }
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                    } catch (Throwable th) {
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                        throw th;
                                    }
                                }
                            }
                        }
                    };
                    manager.registerReceiver(this.c, filter);
                    Intent intent = new Intent(activity, H5FileChooserActivity.class);
                    H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
                    if (H5Utils.isInTinyProcess() && h5ConfigProvider != null && !"yes".equalsIgnoreCase(h5ConfigProvider.getConfig("h5_notSupportTinyChooseFile"))) {
                        String appId = H5Utils.getString(this.a.getParams(), (String) "appId");
                        if (TextUtils.isEmpty(appId)) {
                            appId = H5Utils.getString(this.a.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG");
                        }
                        if (!TextUtils.isEmpty(appId)) {
                            intent.putExtra("appId", appId);
                        }
                    }
                    activity.startActivity(intent);
                }
            } catch (ClassCastException e2) {
                H5Log.e(this.TAG, "exception detail", e2);
            } catch (ActivityNotFoundException e3) {
                H5Log.e(this.TAG, "exception detail", e3);
            }
        }
    }

    public static Activity getActivityFromContext(Context ctx) {
        int times = 5;
        while (ctx != null) {
            if (ctx instanceof Activity) {
                int i = times;
                return (Activity) ctx;
            }
            if (ctx instanceof ContextWrapper) {
                ctx = ((ContextWrapper) ctx).getBaseContext();
            }
            int times2 = times - 1;
            if (times <= 0) {
                return null;
            }
            times = times2;
        }
        int i2 = times;
        return null;
    }

    public void onShowCustomView(View view, CustomViewCallback callback) {
        H5Log.d(this.TAG, "onShowCustomView [SDK Version] " + VERSION.SDK_INT);
        this.mCustomViewCallback = callback;
    }

    public void onHideCustomView() {
        H5Log.d(this.TAG, "onShowCustomView [SDK Version] " + VERSION.SDK_INT);
        this.mCustomViewCallback = null;
    }

    public boolean onCreateWindow(APWebView apWebView, boolean b2, boolean b22, Message message) {
        return false;
    }

    public void onRequestFocus(APWebView apWebView) {
    }

    public void onCloseWindow(APWebView apWebView) {
    }

    public boolean onJsAlert(APWebView apWebView, String url, String message, final APJsResult result) {
        H5Log.d(this.TAG, "onJsAlert " + url + Token.SEPARATOR + message);
        if (this.a == null) {
            H5Log.d(this.TAG, "h5Page == null");
            result.cancel();
            return true;
        }
        Activity activity = (Activity) this.a.getContext().getContext();
        if (activity == null || activity.isFinishing()) {
            H5Log.d(this.TAG, " isFinishing");
            result.cancel();
            return false;
        } else if (!(activity instanceof H5Activity)) {
            H5Log.d(this.TAG, "not H5Activity");
            return false;
        } else {
            final H5DialogManagerProvider h5DialogManagerProvider = (H5DialogManagerProvider) H5ProviderManagerImpl.getInstance().getProvider(H5DialogManagerProvider.class.getName());
            if (h5DialogManagerProvider == null) {
                final H5Dialog h5Dialog = new H5Dialog(this.a.getContext().getContext());
                h5Dialog.setMessage((CharSequence) message).setPositiveButton(R.string.h5_default_confirm, (View.OnClickListener) new View.OnClickListener() {
                    public void onClick(View v) {
                        result.confirm();
                        h5Dialog.dismiss();
                    }
                }).show();
            } else {
                Dialog dialog = h5DialogManagerProvider.createDialog(activity, null, message, H5Environment.getResources().getString(R.string.h5_default_confirm), null, null);
                h5DialogManagerProvider.setPositiveListener(new OnClickPositiveListener() {
                    public void onClick() {
                        if (result != null) {
                            result.confirm();
                        }
                        h5DialogManagerProvider.disMissDialog();
                        h5DialogManagerProvider.release();
                    }
                });
                dialog.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialogInterface) {
                        if (result != null) {
                            result.confirm();
                        }
                        h5DialogManagerProvider.release();
                    }
                });
                h5DialogManagerProvider.showDialog();
            }
            return true;
        }
    }

    public boolean onJsConfirm(APWebView apWebView, String url, String message, final APJsResult apJsResult) {
        H5Log.d(this.TAG, "onJsConfirm " + url + Token.SEPARATOR + message);
        if (this.a == null) {
            H5Log.d(this.TAG, "h5Page == null");
            apJsResult.cancel();
            return true;
        }
        Activity activity = (Activity) this.a.getContext().getContext();
        if (activity == null || activity.isFinishing()) {
            H5Log.d(this.TAG, "  isFinishing");
            apJsResult.cancel();
            return false;
        } else if (!(activity instanceof H5Activity)) {
            H5Log.d(this.TAG, "not  H5activity");
            return false;
        } else {
            final H5DialogManagerProvider h5DialogManagerProvider = (H5DialogManagerProvider) H5ProviderManagerImpl.getInstance().getProvider(H5DialogManagerProvider.class.getName());
            if (h5DialogManagerProvider == null) {
                final H5Dialog h5Dialog = new H5Dialog(this.a.getContext().getContext());
                h5Dialog.setMessage((CharSequence) message).setPositiveButton(R.string.h5_default_confirm, (View.OnClickListener) new View.OnClickListener() {
                    public void onClick(View v) {
                        apJsResult.confirm();
                        h5Dialog.dismiss();
                    }
                }).setNegativeButton(R.string.h5_default_cancel, (View.OnClickListener) new View.OnClickListener() {
                    public void onClick(View v) {
                        apJsResult.cancel();
                        h5Dialog.dismiss();
                    }
                }).show();
            } else {
                Dialog dialog = h5DialogManagerProvider.createDialog(activity, null, message, H5Environment.getResources().getString(R.string.h5_default_confirm), H5Environment.getResources().getString(R.string.h5_default_cancel), null);
                h5DialogManagerProvider.setPositiveListener(new OnClickPositiveListener() {
                    public void onClick() {
                        if (apJsResult != null) {
                            apJsResult.confirm();
                        }
                        h5DialogManagerProvider.disMissDialog();
                    }
                });
                h5DialogManagerProvider.setNegativeListener(new OnClickNegativeListener() {
                    public void onClick() {
                        if (apJsResult != null) {
                            apJsResult.cancel();
                        }
                        h5DialogManagerProvider.disMissDialog();
                    }
                });
                dialog.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialogInterface) {
                        if (apJsResult != null) {
                            apJsResult.cancel();
                        }
                    }
                });
                h5DialogManagerProvider.showDialog();
            }
            return true;
        }
    }

    public boolean onJsPrompt(APWebView apWebView, String url, String message, String defaultValue, final APJsPromptResult apJsPromptResult) {
        H5Log.d(this.TAG, "onJsPrompt： url:" + url + " message:" + message + " defaultValue:" + defaultValue);
        if (H5BridgePolicy.get() == 1 && a(message)) {
            apJsPromptResult.confirm("true");
            return true;
        } else if (this.a == null) {
            apJsPromptResult.cancel();
            return true;
        } else {
            Activity activity = (Activity) this.a.getContext().getContext();
            if (activity == null || activity.isFinishing()) {
                apJsPromptResult.cancel();
                return false;
            } else if (!(activity instanceof H5Activity)) {
                return false;
            } else {
                final H5PromptDialog h5PromptDialog = new H5PromptDialog(activity, null, message, H5Environment.getResources().getString(R.string.h5_default_confirm), H5Environment.getResources().getString(R.string.h5_default_cancel));
                h5PromptDialog.show();
                h5PromptDialog.getInputContent().setHint(defaultValue);
                h5PromptDialog.setPositiveListener(new H5PromptDialog.OnClickPositiveListener() {
                    public void onClick(String msg) {
                        if (apJsPromptResult != null) {
                            apJsPromptResult.confirm(h5PromptDialog.getInputContent().getText().toString());
                        }
                    }
                });
                h5PromptDialog.setNegativeListener(new H5PromptDialog.OnClickNegativeListener() {
                    public void onClick() {
                        if (apJsPromptResult != null) {
                            apJsPromptResult.cancel();
                        }
                    }
                });
                h5PromptDialog.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        if (apJsPromptResult != null) {
                            apJsPromptResult.cancel();
                        }
                    }
                });
                return true;
            }
        }
    }

    public void onRelease() {
        this.a = null;
    }
}
