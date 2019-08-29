package com.alipay.mobile.nebulacore.dev.provider;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.res.H5ResourceManager;
import com.alipay.mobile.nebula.dev.H5BugmeIdGenerator;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.provider.H5DevDebugProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.autonavi.minimap.bundle.apm.internal.report.ReportManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class H5DevPlugin extends H5SimplePlugin {
    public static final String TAG = "H5DevPlugin";
    /* access modifiers changed from: private */
    public H5DevDebugProvider a = null;
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "";
    private String f = "";
    private APWebView g = null;
    private H5Page h = null;

    public void onInitialize(H5CoreNode coreNode) {
        this.a = (H5DevDebugProvider) H5Utils.getProvider(H5DevDebugProvider.class.getName());
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("NBDebugReport");
        filter.addAction(H5Param.H5_PAGE_CREATE_WEBVIEW);
        filter.addAction(CommonEvents.H5_PAGE_FINISHED);
        filter.addAction(CommonEvents.H5_PAGE_STARTED);
        filter.addAction(CommonEvents.H5_PAGE_CLOSE);
        filter.addAction(CommonEvents.H5_DEV_CONSOLE);
        filter.addAction(CommonEvents.H5_DEV_CONSOLE_EXCEPTION);
        filter.addAction(CommonEvents.H5_DEV_WEBVIEW_CREATE);
        filter.addAction(CommonEvents.H5_DEV_WEBVIEW_RELEASE);
        filter.addAction(CommonEvents.H5_DEV_JS_API_TO_NATIVE);
        filter.addAction(CommonEvents.H5_DEV_NETWORK_SRART);
        filter.addAction(CommonEvents.H5_DEV_NETWORK_FINISH);
        filter.addAction(CommonEvents.H5_DEV_SCREENSHOT_UPLOAD);
    }

    public void onRelease() {
        this.a = null;
        this.g = null;
        this.h = null;
        H5DebugConsolePool.getInstance().release(this.e);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (this.a == null) {
            return false;
        }
        if (!TextUtils.isEmpty(this.e)) {
            if (!TextUtils.isEmpty(this.b)) {
                this.a.setUserAgent(this.e, this.b);
            }
            if (!TextUtils.isEmpty(this.d)) {
                this.a.setTitle(this.e, this.d);
            }
            if (!TextUtils.isEmpty(this.c)) {
                this.a.setPageUrl(this.e, this.c);
            }
            if (!TextUtils.isEmpty(this.f)) {
                this.a.setSessionId(this.e, this.f);
            }
        }
        if (CommonEvents.H5_DEV_CONSOLE.equals(event.getAction())) {
            String content = H5Utils.getString(event.getParam(), (String) "content");
            String func = H5Utils.getString(event.getParam(), (String) "func", (String) null);
            if (TextUtils.isEmpty(func) && !TextUtils.isEmpty(content)) {
                func = a(content);
            }
            this.a.consoleLog(ReportManager.LOG_PATH, this.e, content, func);
            return true;
        } else if (CommonEvents.H5_DEV_CONSOLE_EXCEPTION.equals(event.getAction())) {
            this.a.consoleLog("error", this.e, H5Utils.getString(event.getParam(), (String) "content"), null);
            return true;
        } else if (CommonEvents.H5_DEV_WEBVIEW_CREATE.equals(event.getAction())) {
            this.a.pageLog("create", this.e, this.b, this.c, this.d, null);
            return true;
        } else if (CommonEvents.H5_DEV_WEBVIEW_RELEASE.equals(event.getAction())) {
            this.a.pageLog("destroy", this.e, this.b, this.c, this.d, null);
            this.g = null;
            this.h = null;
            return true;
        } else if (CommonEvents.H5_DEV_JS_API_TO_NATIVE.equals(event.getAction())) {
            String subType = H5Utils.getString(event.getParam(), (String) "subType");
            String request = H5Utils.getString(event.getParam(), (String) "request");
            this.a.jsApiLog(subType, this.e, H5Utils.getString(event.getParam(), (String) "eventId"), request, "");
            return true;
        } else if (CommonEvents.H5_DEV_NETWORK_SRART.equals(event.getAction())) {
            this.a.netWorkLog(H5PageData.KEY_UC_START, this.e, event.getParam());
            return true;
        } else if (CommonEvents.H5_DEV_NETWORK_FINISH.equals(event.getAction())) {
            this.a.netWorkLog("finish", this.e, event.getParam());
            return true;
        } else if (CommonEvents.H5_DEV_SCREENSHOT_UPLOAD.equals(event.getAction())) {
            this.a.screenshot("upload", this.e);
            return true;
        } else if (!"NBDebugReport".equals(event.getAction())) {
            return false;
        } else {
            JSONObject params = event.getParam();
            if (params != null && "extra".equals(H5Utils.getString(params, (String) "type"))) {
                this.a.pageLog("sync_state", this.e, "", "", "", H5Utils.getJSONObject(params, "content", null));
            }
            return true;
        }
    }

    private static String a(String content) {
        String func = null;
        try {
            Pattern callbackPattern = H5PatternHelper.compile("invokeJS msgType callback func (.*)");
            if (callbackPattern == null) {
                return null;
            }
            Matcher matcher = callbackPattern.matcher(content);
            if (matcher.find()) {
                func = matcher.group(1);
            }
            return func;
        } catch (Throwable th) {
            return null;
        }
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        if (this.a != null) {
            String action = event.getAction();
            a(event);
            String subType = null;
            if (H5Param.H5_PAGE_CREATE_WEBVIEW.equals(action)) {
                subType = "create";
            } else if (CommonEvents.H5_PAGE_STARTED.equals(action)) {
                subType = "update";
                if (this.a.getScheduler() != null) {
                    this.a.getScheduler().pause();
                }
            } else if (CommonEvents.H5_PAGE_FINISHED.equals(action)) {
                subType = "finish";
                H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                    public void run() {
                        if (H5DevPlugin.this.a != null && H5DevPlugin.this.a.getScheduler() != null) {
                            H5DevPlugin.this.a.getScheduler().resume();
                        }
                    }
                });
                if (H5DevConfig.getBooleanConfig(H5DevConfig.H5_BUG_ME_DOM_DEBUG, false)) {
                    a(this.g);
                }
            } else if (CommonEvents.H5_PAGE_CLOSE.equals(action) && this.a.getScheduler() != null) {
                this.a.getScheduler().resume();
            }
            if (subType != null) {
                H5Log.d(TAG, "type:page subType:" + subType + " viewId:" + this.e + " userAgent:" + this.b + " url:" + this.c + " title:" + this.d);
                this.a.pageLog(subType, this.e, this.b, this.c, this.d, null);
            }
        }
        return false;
    }

    private void a(H5Event event) {
        if (event.getTarget() instanceof H5Page) {
            H5Page h5Page = (H5Page) event.getTarget();
            JSONObject param = event.getParam();
            String parsedUrl = null;
            if (param != null) {
                parsedUrl = param.getString("url");
            }
            if (parsedUrl == null) {
                parsedUrl = h5Page.getUrl();
            }
            this.c = parsedUrl;
            this.d = h5Page.getTitle();
            if (h5Page != this.h) {
                this.g = h5Page.getWebView();
                this.e = H5BugmeIdGenerator.getBugmeViewId(h5Page);
                if (!(this.g == null || this.g.getSettings() == null)) {
                    this.b = this.g.getSettings().getUserAgentString();
                }
                H5Session session = h5Page.getSession();
                if (session != null) {
                    this.f = session.hashCode();
                }
            }
            this.h = h5Page;
        }
    }

    private void a(APWebView webView) {
        if (webView != null) {
            String data = String.format(H5ResourceManager.getRaw(R.raw.h5_dev_vorlon), new Object[]{this.e, "https://bugme.anyproxy.io:5680", "https://hpmweb.alipay.com/bugme/domScript"});
            webView.loadUrl("javascript:" + data);
            H5Log.debug(TAG, "vorlon script:" + data);
            this.a.pageLog("sync_state", this.e, "", "", "", null);
        }
    }
}
