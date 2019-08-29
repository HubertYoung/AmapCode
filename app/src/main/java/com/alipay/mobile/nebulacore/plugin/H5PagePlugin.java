package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5PageLoader;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.model.H5Refer;
import com.alipay.mobile.nebula.data.H5Trace;
import com.alipay.mobile.nebula.dev.H5BugmeIdGenerator;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.log.H5MainLinkMonitor;
import com.alipay.mobile.nebula.provider.H5CardShareProvider;
import com.alipay.mobile.nebula.provider.H5DisClaimerProvider;
import com.alipay.mobile.nebula.provider.H5WarningTipProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.api.BackBehavior;
import com.alipay.mobile.nebulacore.api.PageStatus;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.view.H5FontBar;
import com.alipay.mobile.nebulacore.web.H5WebView;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import java.util.HashMap;
import java.util.Map;

public class H5PagePlugin extends H5SimplePlugin {
    public static final String SHOW_SOFT_INPUT = "showSoftInput";
    public static final String TAG = "H5PagePlugin";
    public static final String TOGGLE_SOFT_INPUT = "toggleSoftInput";
    /* access modifiers changed from: private */
    public H5WebView a;
    private H5PageImpl b;
    private H5Bridge c;
    private int d = PageStatus.NONE;
    private H5BackHandler e = new H5BackHandler();
    private int f;

    class H5BackHandler implements H5CallBack {
        public long lastBack = 0;
        public boolean waiting = false;

        public H5BackHandler() {
        }

        public void onCallBack(JSONObject param) {
            this.waiting = false;
            boolean prevent = H5Utils.getBoolean(param, (String) "prevent", false);
            H5Log.d(H5PagePlugin.TAG, "back event prevent " + prevent);
            if (!prevent) {
                H5PagePlugin.this.b();
            }
        }
    }

    public H5PagePlugin(H5PageImpl h5Page) {
        this.b = h5Page;
        this.a = h5Page.getWebView();
        this.c = h5Page.getBridge();
        a(H5Utils.getString(h5Page.getParams(), (String) H5Param.LONG_BACK_BEHAVIOR));
    }

    public void onRelease() {
        H5CardShareProvider cardShareProvider = (H5CardShareProvider) Nebula.getProviderManager().getProvider(H5CardShareProvider.class.getName());
        if (cardShareProvider != null) {
            cardShareProvider.release();
        }
        H5DisClaimerProvider provider = (H5DisClaimerProvider) H5Utils.getProvider(H5DisClaimerProvider.class.getName());
        if (provider != null) {
            provider.hideDisclaimer(this.b);
        }
        H5WarningTipProvider tipProvider = (H5WarningTipProvider) H5Utils.getProvider(H5WarningTipProvider.class.getName());
        if (tipProvider != null) {
            tipProvider.hideWarningTip(this.b);
        }
        this.c = null;
        this.a = null;
        this.b = null;
        this.e = null;
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_BACK_BEHAVIOR);
        filter.addAction(CommonEvents.H5_PAGE_RECEIVED_TITLE);
        filter.addAction(CommonEvents.H5_PAGE_LOAD_URL);
        filter.addAction(CommonEvents.H5_PAGE_LOAD_DATA);
        filter.addAction(CommonEvents.H5_PAGE_RELOAD);
        filter.addAction(CommonEvents.H5_PAGE_FONT_SIZE);
        filter.addAction(CommonEvents.H5_PAGE_RESUME);
        filter.addAction(CommonEvents.H5_PAGE_ERROR);
        filter.addAction(CommonEvents.H5_PAGE_BACK);
        filter.addAction(CommonEvents.H5_PAGE_STARTED);
        filter.addAction(CommonEvents.H5_PAGE_PROGRESS);
        filter.addAction(CommonEvents.H5_PAGE_FINISHED);
        filter.addAction(CommonEvents.H5_PAGE_CLOSE);
        filter.addAction("h5PageClose_tab");
        filter.addAction(CommonEvents.H5_PAGE_BACKGROUND);
        filter.addAction(CommonEvents.H5_TOOLBAR_MENU_BT);
        filter.addAction(CommonEvents.H5_PAGE_DO_LOAD_URL);
        filter.addAction(TOGGLE_SOFT_INPUT);
        filter.addAction(SHOW_SOFT_INPUT);
        filter.addAction(CommonEvents.H5_PAGE_PAUSE);
        filter.addAction("stopLoading");
        filter.addAction("inputFocus4Android");
        filter.addAction("getStartupParams");
        filter.addAction("coolLoadingCtrl");
        filter.addAction("setGestureBack");
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (this.b == null) {
            return false;
        }
        if (CommonEvents.H5_PAGE_ERROR.equals(action)) {
            this.d = PageStatus.ERROR;
            return true;
        }
        CommonEvents.H5_PAGE_BACKGROUND.equals(action);
        return false;
    }

    private void a(String behavior) {
        H5Log.d(TAG, "setBackBehavior " + behavior);
        if ("pop".equals(behavior)) {
            this.f = BackBehavior.POP;
        } else {
            this.f = BackBehavior.BACK;
        }
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        JSONObject param = event.getParam();
        if (CommonEvents.H5_PAGE_BACK_BEHAVIOR.equals(action)) {
            a(H5Utils.getString(param, (String) H5Param.LONG_BACK_BEHAVIOR));
        } else if (CommonEvents.H5_PAGE_LOAD_URL.equals(action)) {
            if (TextUtils.isEmpty(this.a.getUrl())) {
                Bundle startbundle = this.b.getParams();
                if (startbundle != null) {
                    param.put((String) "appId", (Object) H5Utils.getString(startbundle, (String) "appId"));
                    String preSSOLogin = H5Utils.getString(startbundle, (String) H5Param.LONG_PRESSO_LOGIN);
                    String preSSOLoginBindingPage = H5Utils.getString(startbundle, (String) H5Param.LONG_PRESSO_LOGIN_BINDINGPAGE);
                    String preSSOLoginUrl = H5Utils.getString(startbundle, (String) H5Param.LONG_PRESSO_LOGIN_URL);
                    param.put((String) H5Param.PRESSO_LOGIN, (Object) preSSOLogin);
                    param.put((String) H5Param.PRESSO_LOGIN_BINDINGPAGE, (Object) preSSOLoginBindingPage);
                    param.put((String) H5Param.PRESSO_LOGIN_URL, (Object) preSSOLoginUrl);
                }
                param.put((String) H5Param.START_URL, (Object) Boolean.valueOf(true));
                this.b.sendEvent(CommonEvents.H5_PAGE_SHOULD_LOAD_URL, param);
            } else {
                a(event);
            }
        } else if (CommonEvents.H5_PAGE_LOAD_DATA.equals(action)) {
            b(event);
        } else if (CommonEvents.H5_PAGE_DO_LOAD_URL.equals(action)) {
            String url = H5Utils.getString(param, (String) "url");
            String current = this.a.getUrl();
            boolean force = H5Utils.getBoolean(param, (String) "force", false);
            if (!TextUtils.isEmpty(current)) {
                this.b.getWebViewClient().setCheckingUrl(url);
            }
            if (TextUtils.isEmpty(current) || current.equals(url) || force) {
                a(event);
            }
        } else if (CommonEvents.H5_PAGE_RELOAD.equals(action)) {
            this.a.reload();
        } else if (CommonEvents.H5_PAGE_BACK.equals(action)) {
            a();
        } else if (CommonEvents.H5_PAGE_PAUSE.equals(action)) {
            H5Log.d(TAG, "sendToWeb page event pause");
            if (this.c != null) {
                this.c.sendToWeb(AudioUtils.CMDPAUSE, null, null);
            }
            if (this.b != null) {
                this.b.hideLoadingView();
            }
        } else if (CommonEvents.H5_PAGE_RESUME.equals(action)) {
            if (this.b == null) {
                H5Log.w(TAG, "resume fatal error");
                return true;
            }
            String popParam = this.b.getSession().getData().remove(H5Param.H5_SESSION_POP_PARAM);
            String resumeParam = this.b.getSession().getData().remove(H5Param.H5_SESSION_RESUME_PARAM);
            String pageResume = this.b.getSession().getData().remove(Nebula.H5_PAGE_RESUME);
            JSONObject data = new JSONObject();
            if (!TextUtils.isEmpty(popParam)) {
                JSONObject objData = H5Utils.parseObject(popParam);
                if (objData != null) {
                    data.put((String) "data", (Object) objData);
                } else {
                    JSONArray arrayData = H5Utils.parseArray(popParam);
                    if (arrayData != null) {
                        data.put((String) "data", (Object) arrayData);
                    } else {
                        data.put((String) "data", (Object) popParam);
                    }
                }
            }
            if (!TextUtils.isEmpty(resumeParam)) {
                data.put((String) "resumeParams", (Object) H5Utils.parseObject(resumeParam));
            }
            H5Log.d(TAG, "sendToWeb page event resume");
            if (!TextUtils.isEmpty(pageResume)) {
                H5Log.d(TAG, "sendToWeb page event pageResume");
                this.c.sendToWeb("pageResume", data, null);
            }
            this.c.sendToWeb("resume", data, null);
            if (H5PageLoader.isPageClose) {
                this.b.getPageData().setReferUrl(H5Refer.referUrl);
                if (Nebula.DEBUG) {
                    H5Log.d(TAG, "h5page resume, getRefer : " + H5Refer.referUrl);
                }
                this.b.sendEvent(H5Logger.H5_AL_PAGE_RESUME, null);
                H5PageLoader.isPageClose = false;
            }
        } else if (CommonEvents.H5_PAGE_FONT_SIZE.equals(action)) {
            int size = H5Utils.getInt(param, (String) "size", -1);
            if (size != -1) {
                this.a.setTextSize(size);
            }
            if (!(this.b == null || this.b.getSession() == null || this.b.getSession().getScenario() == null || this.b.getSession().getScenario().getData() == null)) {
                this.b.getSession().getScenario().getData().set(H5Param.FONT_SIZE, String.valueOf(size));
            }
        } else if (CommonEvents.H5_PAGE_STARTED.equals(action)) {
            this.d = PageStatus.LOADING;
            Bundle startParams = this.b.getParams();
            if (H5Utils.getBoolean(startParams, (String) "showLoading", false)) {
                this.b.sendEvent("showLoading", null);
            } else if (H5Utils.getBoolean(startParams, (String) "showTitleLoading", false)) {
                this.b.sendEvent("showTitleLoading", null);
            }
        } else if (CommonEvents.H5_PAGE_FINISHED.equals(action)) {
            this.d = PageStatus.FINISHED;
            if (this.b.getAutoHideLoading()) {
                this.b.hideLoadingView();
            }
            this.b.sendEvent(CommonEvents.HIDE_LOADING, null);
            this.b.sendEvent(CommonEvents.HIDE_TITLE_LOADING, null);
            if (H5Utils.getInt(param, (String) "historySize") > 1 && BackBehavior.BACK == this.f) {
                if ("yes".equalsIgnoreCase(H5Utils.getString(this.b.getParams(), CommonEvents.HIDE_CLOSE_BUTTON))) {
                    H5Log.d(TAG, "set hideCloseButton yes");
                } else {
                    JSONObject data2 = new JSONObject();
                    data2.put((String) ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW, (Object) Boolean.valueOf(true));
                    this.b.sendEvent(CommonEvents.H5_PAGE_SHOW_CLOSE, data2);
                }
            }
        } else if (CommonEvents.H5_PAGE_RECEIVED_TITLE.equals(action)) {
            this.d = PageStatus.FINISHED;
        } else if (CommonEvents.H5_PAGE_CLOSE.equals(action) || "h5PageClose_tab".equals(action)) {
            if (CommonEvents.H5_PAGE_CLOSE.equals(action)) {
                a(false);
            } else {
                a(true);
            }
        } else if (CommonEvents.H5_TOOLBAR_MENU_BT.equals(action)) {
            String tag = H5Utils.getString(param, (String) "tag");
            if (H5Param.MENU_FONT.equals(tag)) {
                this.b.sendEvent(H5FontBar.SHOW_FONT_BAR, null);
            } else if ("refresh".equals(tag)) {
                this.b.sendEvent(CommonEvents.H5_PAGE_RELOAD, null);
            } else if (H5Param.MENU_COPY.equals(tag)) {
                JSONObject data3 = new JSONObject();
                String url2 = this.b.getShareUrl();
                if (!(this.b == null || this.b.getParams() == null)) {
                    String onlineHost = H5Utils.getString(this.b.getParams(), (String) H5Param.ONLINE_HOST, (String) "");
                    if (!TextUtils.isEmpty(onlineHost) && url2.startsWith(onlineHost)) {
                        try {
                            url2 = "https://ds.alipay.com/?scheme=" + H5UrlHelper.encode(H5Utils.getShareLoadingScheme(url2, H5Utils.getString(this.b.getParams(), (String) "appId"), this.b));
                        } catch (Throwable throwable) {
                            H5Log.e((String) TAG, throwable);
                        }
                    }
                }
                data3.put((String) "text", (Object) url2);
                this.b.sendEvent(CommonEvents.SET_CLIPBOARD, data3);
                H5Environment.showToast(this.b.getContext().getContext(), H5Environment.getResources().getString(R.string.h5_copied), 0);
            } else if (!"openInBrowser".equals(tag)) {
                return false;
            } else {
                Uri uri = H5UrlHelper.parseUrl(this.b.getShareUrl());
                if (uri == null) {
                    JSONObject result = new JSONObject();
                    result.put((String) "error", (Object) Integer.valueOf(2));
                    bridgeContext.sendBridgeResult(result);
                }
                String url3 = this.b.getShareUrl();
                if (TextUtils.isEmpty(url3)) {
                    return false;
                }
                if (!(this.b == null || this.b.getParams() == null)) {
                    String onlineHost2 = H5Utils.getString(this.b.getParams(), (String) H5Param.ONLINE_HOST, (String) "");
                    if (!TextUtils.isEmpty(onlineHost2) && url3.startsWith(onlineHost2)) {
                        try {
                            url3 = "https://ds.alipay.com/?scheme=" + H5UrlHelper.encode(H5Utils.getShareLoadingScheme(url3, H5Utils.getString(this.b.getParams(), (String) "appId"), this.b));
                            uri = Uri.parse(url3);
                        } catch (Throwable throwable2) {
                            H5Log.e((String) TAG, throwable2);
                        }
                    }
                }
                String ucmPkgName = Nebula.getUCMPackageName(Nebula.getPackageInfos(this.b.getContext().getContext()));
                if (url3.startsWith("https://ds.alipay.com/error/securityLink.htm") || url3.startsWith("https://ds.alipay.com/error/redirectLink.htm")) {
                    uri = Uri.parse(Uri.parse(url3).getQueryParameter("url"));
                    H5Log.d(TAG, "competitive link special text is " + uri.getHost());
                }
                if (TextUtils.isEmpty(ucmPkgName) || this.b == null || this.b.getContext() == null) {
                    Intent intent = new Intent("android.intent.action.VIEW", uri);
                    Intent chooser = Intent.createChooser(intent, H5Environment.getResources().getString(R.string.h5_menu_open_in_browser));
                    chooser.setFlags(268435456);
                    if (intent.resolveActivity(H5Environment.getContext().getPackageManager()) != null) {
                        H5Environment.startActivity(null, chooser);
                    }
                } else {
                    Nebula.startUCMIntentLoadUrl(this.b.getContext().getContext(), uri, ucmPkgName, null);
                    H5LogUtil.logNebulaTech(H5LogData.seedId("H5_OPEN_URL_UC").param3().add("url", url3).param4().addUniteParam(this.b.getPageData()));
                    H5Log.d(TAG, "log open url in uc");
                }
                this.b.sendEvent(CommonEvents.H5_OPEN_IN_BROWSER, null);
            }
        } else if (TOGGLE_SOFT_INPUT.equals(action)) {
            ((InputMethodManager) H5Environment.getContext().getSystemService("input_method")).toggleSoftInput(0, 2);
        } else if (SHOW_SOFT_INPUT.equals(action)) {
            if (!param.containsKey(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW) || this.b == null || this.b.getWebView() == null) {
                JSONObject result2 = new JSONObject();
                result2.put((String) "error", (Object) Integer.valueOf(2));
                H5Log.e((String) TAG, (String) "you should specify whether to or not to show soft input or internal error occurred!");
                bridgeContext.sendBridgeResult(result2);
            } else {
                boolean show = param.getBooleanValue(ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW);
                InputMethodManager imm = (InputMethodManager) H5Environment.getContext().getSystemService("input_method");
                View view = this.a.getView();
                if (show) {
                    imm.showSoftInput(view, 2);
                } else {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        } else if ("stopLoading".equals(action)) {
            if (this.a != null) {
                this.a.stopLoading();
            }
        } else if ("inputFocus4Android".equals(action)) {
            b(event, bridgeContext);
        } else if ("setGestureBack".equals(action)) {
            H5Log.d(TAG, "This is an empty implementation for SET_GESTURE_BACK");
            bridgeContext.sendSuccess();
        } else if ("getStartupParams".equals(action)) {
            a(event, bridgeContext);
        } else if (!"coolLoadingCtrl".equals(action)) {
            return false;
        } else {
            if (DataflowMonitorModel.METHOD_NAME_CLOSE.equals(H5Utils.getString(param, (String) "action"))) {
                this.b.hideLoadingView();
                bridgeContext.sendSuccess();
            } else {
                a(bridgeContext, Error.INVALID_PARAM.ordinal(), "无效的api入参");
            }
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        if (this.b == null) {
            a(bridgeContext, 12, "page is null");
            return;
        }
        JSONObject startParams = H5Utils.toJSONObject(this.b.getParams());
        if (startParams == null || startParams.isEmpty()) {
            a(bridgeContext, 12, "params is null");
            return;
        }
        Nebula.removeBridgeTimeParam(startParams);
        JSONObject param = event.getParam();
        if (!param.containsKey("key")) {
            a(bridgeContext, startParams);
            return;
        }
        JSONArray keys = H5Utils.getJSONArray(param, "key", null);
        if (keys == null || keys.isEmpty()) {
            a(bridgeContext, Error.INVALID_PARAM.ordinal(), "无效的api入参");
            return;
        }
        JSONObject resultObj = new JSONObject();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.getString(i);
            if (startParams.containsKey(key)) {
                resultObj.put(key, H5Utils.getValue(startParams, key, new Object()));
            }
        }
        a(bridgeContext, resultObj);
    }

    private static void a(H5BridgeContext bridgeContext, int e2, String s) {
        if (bridgeContext != null) {
            bridgeContext.sendError(e2, s);
        }
    }

    private static void a(H5BridgeContext bridgeContext, JSONObject obj) {
        if (bridgeContext != null) {
            bridgeContext.sendBridgeResult(obj);
        }
    }

    private void b(H5Event event, H5BridgeContext bridgeContext) {
        if (this.a == null) {
            H5Log.d(TAG, "inputFocus4Android h5WebView == null");
            if (bridgeContext != null) {
                bridgeContext.sendBridgeResult("success", Boolean.valueOf(false));
                return;
            }
            return;
        }
        View realWebView = this.a.getView();
        if (realWebView == null) {
            H5Log.d(TAG, "inputFocus4Android realWebView == null");
            if (bridgeContext != null) {
                bridgeContext.sendBridgeResult("success", Boolean.valueOf(false));
                return;
            }
            return;
        }
        JSONObject params = event.getParam();
        String xstr = H5Utils.getString(params, (String) "coordinateX");
        String ystr = H5Utils.getString(params, (String) "coordinateY");
        H5Log.d(TAG, "x " + xstr + ", y " + ystr);
        try {
            float x = Float.valueOf(xstr).floatValue();
            float y = Float.valueOf(ystr).floatValue();
            long downTime = SystemClock.uptimeMillis();
            MotionEvent downEvent = MotionEvent.obtain(downTime, downTime, 0, x, y, 0);
            long j = downTime + 300;
            MotionEvent upEvent = MotionEvent.obtain(j, j, 1, x, y, 0);
            realWebView.dispatchTouchEvent(downEvent);
            realWebView.dispatchTouchEvent(upEvent);
            downEvent.recycle();
            upEvent.recycle();
            if (bridgeContext != null) {
                bridgeContext.sendSuccess();
            }
        } catch (Throwable throwable) {
            H5Log.e((String) TAG, throwable);
            if (bridgeContext != null) {
                bridgeContext.sendError(2, (String) "invalid parameter!");
            }
        }
    }

    private void a(boolean exitTabScene) {
        if (this.b != null) {
            if (H5Logger.enableStockTradeLog()) {
                H5Refer.referUrl = this.b.getUrl();
            }
            if (Nebula.DEBUG) {
                H5Log.d(TAG, "h5page close, setRefer : " + H5Refer.referUrl);
            }
            if (exitTabScene) {
                this.b.exitTabPage();
            } else {
                this.b.exitPage();
            }
            H5PageLoader.isPageClose = true;
        }
    }

    private void a() {
        boolean sendIntent;
        boolean enoughElapse;
        boolean ignoreBridge;
        long time = System.currentTimeMillis();
        if (this.d != PageStatus.FINISHED || this.e.waiting) {
            sendIntent = false;
        } else {
            sendIntent = true;
        }
        if (time - this.e.lastBack > 500) {
            enoughElapse = true;
        } else {
            enoughElapse = false;
        }
        if (!sendIntent || !enoughElapse) {
            ignoreBridge = true;
        } else {
            ignoreBridge = false;
        }
        boolean haveKeptAlive = false;
        if (this.b != null && this.b.isTinyApp() && this.b.getContext() != null && (this.b.getContext().getContext() instanceof Activity)) {
            Activity activity = (Activity) this.b.getContext().getContext();
            if (Nebula.needPageKeepAlive(this.b, activity)) {
                if (this.f == BackBehavior.POP) {
                    haveKeptAlive = Nebula.doKeepAlive(activity, this.b.getParams());
                } else if (this.a == null || !this.a.canGoBack()) {
                    H5Log.d(TAG, "webview can't go back and do exit!");
                    haveKeptAlive = Nebula.doKeepAlive(activity, this.b.getParams());
                } else if (this.a.copyBackForwardList().getCurrentIndex() <= 0) {
                    haveKeptAlive = Nebula.doKeepAlive(activity, this.b.getParams());
                }
            }
        }
        if (!haveKeptAlive) {
            if (!ignoreBridge) {
                H5Log.d(TAG, "send back event to bridge!");
                this.e.waiting = true;
                this.e.lastBack = time;
                this.c.sendToWeb(H5Param.DEFAULT_LONG_BACK_BEHAVIOR, null, this.e);
                return;
            }
            H5Log.d(TAG, "ignore bridge, perform back!");
            b();
        }
    }

    private void a(H5Event event) {
        JSONObject param = event.getParam();
        String url = H5Utils.getString(param, (String) "url");
        if (TextUtils.isEmpty(url)) {
            H5Log.w(TAG, "h5_url_isnull");
            return;
        }
        H5Trace.event("loadUrl", H5BugmeIdGenerator.getBugmeViewId(this.b), "url", url);
        Nebula.getH5BugMeManager().setWebViewDebugging(url, this.a);
        H5MainLinkMonitor.triggerLoadUrlLink(this.b);
        if (this.b != null) {
            Bundle startParams = this.b.getParams();
            if (TextUtils.equals(H5Utils.getString(startParams, (String) "openUrlMethod"), "POST")) {
                String postParams = H5Utils.getString(startParams, (String) "openUrlPostParams");
                if (startParams != null) {
                    startParams.putString("openUrlMethod", "GET");
                    startParams.putString("openUrlPostParams", "");
                }
                this.a.postUrl(url, postParams.getBytes());
                return;
            }
        }
        if (param.containsKey(H5Param.REFERER)) {
            Map additionalHeaders = new HashMap();
            additionalHeaders.put(H5Param.REFERER, param.getString(H5Param.REFERER));
            this.a.loadUrl(url, additionalHeaders);
            return;
        }
        this.a.loadUrl(url);
    }

    private void b(H5Event event) {
        JSONObject param = event.getParam();
        final String baseUrl = H5Utils.getString(param, (String) "baseUrl");
        final String data = H5Utils.getString(param, (String) "data");
        final String mimeType = H5Utils.getString(param, (String) "mimeType");
        final String encoding = H5Utils.getString(param, (String) "encoding");
        final String historyUrl = H5Utils.getString(param, (String) "historyUrl");
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                H5PagePlugin.this.a.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
            }
        });
    }

    /* access modifiers changed from: private */
    public void b() {
        H5Log.d(TAG, "perform back behavior " + this.f);
        if (this.f == BackBehavior.POP) {
            this.b.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
        } else if (this.f != BackBehavior.BACK) {
        } else {
            if (this.a == null || !this.a.canGoBack()) {
                H5Log.d(TAG, "webview can't go back and do exit!");
                this.b.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
            } else if (this.a.copyBackForwardList().getCurrentIndex() <= 0) {
                H5Log.d(TAG, "webview with no history and do exit!");
                this.b.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
            } else {
                this.a.goBack();
            }
        }
    }
}
