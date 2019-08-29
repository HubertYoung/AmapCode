package com.alipay.mobile.nebulacore.plugin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5PluginManager;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;

public class H5SystemPlugin extends H5SimplePlugin {
    public static final String TAG = "H5SystemPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("openInBrowser");
        filter.addAction(CommonEvents.SEND_SMS);
        filter.addAction(CommonEvents.IS_INSTALLED_APP);
        filter.addAction(CommonEvents.CHECK_JS_API);
        filter.addAction("startPackage");
        filter.addAction("openSystemSetting");
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext bridgeContext) {
        String action = event.getAction();
        if (CommonEvents.SEND_SMS.equals(action)) {
            a(event);
        } else if (CommonEvents.IS_INSTALLED_APP.equals(action)) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    try {
                        H5SystemPlugin.d(event, bridgeContext);
                    } catch (Throwable throwable) {
                        H5Log.e((String) H5SystemPlugin.TAG, throwable);
                    }
                }
            });
        } else if (CommonEvents.CHECK_JS_API.equals(action)) {
            e(event, bridgeContext);
        } else if ("openInBrowser".equals(action)) {
            c(event, bridgeContext);
        } else if ("startPackage".equals(action)) {
            H5Utils.getExecutor(H5ThreadType.IO).execute(new Runnable() {
                public void run() {
                    try {
                        H5SystemPlugin.f(event, bridgeContext);
                    } catch (Throwable throwable) {
                        H5Log.e((String) H5SystemPlugin.TAG, throwable);
                    }
                }
            });
        } else if ("openSystemSetting".equals(action)) {
            g(event, bridgeContext);
        }
        return true;
    }

    private static void c(H5Event event, H5BridgeContext bridgeContext) {
        String url = H5Utils.getString(event.getParam(), (String) "url");
        H5Page h5Page = null;
        H5CoreNode node = event.getTarget();
        if (node instanceof H5Page) {
            h5Page = (H5Page) node;
        }
        Nebula.openInBrowser(h5Page, url, bridgeContext);
    }

    /* access modifiers changed from: private */
    public static void d(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param != null && param.containsKey("packagename")) {
            PackageInfo packageInfo = H5Utils.getPackageInfo(H5Environment.getContext(), param.getString("packagename"));
            JSONObject data = new JSONObject();
            data.put((String) "installed", (Object) Boolean.valueOf(packageInfo != null));
            bridgeContext.sendBridgeResult(data);
        }
    }

    private static void a(H5Event event) {
        JSONObject parseObject = event.getParam();
        String contact = parseObject.getString("mobile");
        String message = parseObject.getString("content");
        Intent smsIntent = new Intent("android.intent.action.SENDTO", H5UrlHelper.parseUrl("smsto:" + contact));
        smsIntent.putExtra("sms_body", message);
        smsIntent.putExtra("android.intent.extra.TEXT", message);
        smsIntent.setFlags(268435456);
        H5Environment.startActivity(null, smsIntent);
    }

    private static void e(H5Event event, H5BridgeContext bridgeContext) {
        String apiName = H5Utils.getString(event.getParam(), (String) MtopJSParam.API, (String) null);
        boolean available = false;
        H5CoreNode target = event.getTarget();
        while (!TextUtils.isEmpty(apiName) && !available && target != null) {
            H5PluginManager pluginManager = target.getPluginManager();
            target = target.getParent();
            available = pluginManager.canHandle(apiName);
        }
        JSONObject result = new JSONObject();
        result.put((String) "available", (Object) Boolean.valueOf(available));
        bridgeContext.sendBridgeResult(result);
    }

    /* access modifiers changed from: private */
    public static void f(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject data = new JSONObject();
        JSONObject param = event.getParam();
        String packageName = H5Utils.getString(param, (String) "packagename");
        Context context = H5Environment.getContext();
        if (H5Utils.getPackageInfo(context, packageName) != null) {
            if (H5Utils.getBoolean(param, (String) "closeCurrentApp", false)) {
                H5CoreNode target = event.getTarget();
                if (target instanceof H5Page) {
                    ((H5Page) target).getSession().exitSession();
                }
            }
            H5Environment.startActivity(null, context.getPackageManager().getLaunchIntentForPackage(packageName));
            data.put((String) "startPackage", (Object) "true");
            bridgeContext.sendBridgeResult(data);
            return;
        }
        bridgeContext.sendError(event, Error.INVALID_PARAM);
    }

    private static void g(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        String url = H5Utils.getString(param, (String) "url");
        String ext = H5Utils.getString(param, (String) ProcessInfo.ALIAS_EXT);
        Intent openIntent = new Intent(url);
        if (!TextUtils.isEmpty(ext)) {
            openIntent.setData(H5UrlHelper.parseUrl(ext));
        }
        if (openIntent.resolveActivity(H5Environment.getContext().getPackageManager()) != null) {
            H5Environment.startActivity(null, openIntent);
            if (bridgeContext != null) {
                bridgeContext.sendSuccess();
            }
        } else if (bridgeContext != null) {
            bridgeContext.sendBridgeResult("success", Boolean.valueOf(false));
        }
    }
}
