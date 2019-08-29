package com.alipay.mobile.worker.multiworker;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TinyAppMultiJsWorkerPlugin extends H5SimplePlugin {
    private static Map<String, Map<String, MultiJsWorker>> a = new ConcurrentHashMap();

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("createWorker");
        filter.addAction("multiWorkerPostMessage");
        filter.addAction("multiWorkerTerminate");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if ("createWorker".equals(event.getAction())) {
            a(event, context);
            return true;
        } else if ("multiWorkerPostMessage".equals(event.getAction())) {
            b(event, context);
            return true;
        } else if (!"multiWorkerTerminate".equals(event.getAction())) {
            return super.handleEvent(event, context);
        } else {
            c(event, context);
            return true;
        }
    }

    private static void a(H5Event event, H5BridgeContext context) {
        String url;
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        if (TextUtils.isEmpty(appId)) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "createWorker...appId is null");
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String workerId = H5Utils.getString(event.getParam(), (String) "workerId");
        if (TextUtils.isEmpty(workerId)) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "createWorker...workerId is null");
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        Map jsWorkerMap = a.get(appId);
        if (jsWorkerMap == null) {
            jsWorkerMap = new HashMap();
        } else if (!a(jsWorkerMap.size())) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "createWorker...jsWorker already exit");
            context.sendError(43, (String) "超过最大允许创建的worker数量");
            return;
        }
        String scriptPath = H5Utils.getString(event.getParam(), (String) "scriptPath");
        if (TextUtils.isEmpty(scriptPath)) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "createWorker...path is null");
            context.sendError(event, Error.INVALID_PARAM);
        } else if (scriptPath.startsWith("./") || scriptPath.startsWith("../")) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "createWorker...startsWith ./ or ../ is error");
            context.sendError(40, (String) "scriptPath请使用绝对路径，不以./或者../开头");
        } else {
            try {
                String url2 = H5Utils.getString(h5Page.getParams(), (String) "url");
                if (TextUtils.isEmpty(url2)) {
                    String url3 = new StringBuilder(AjxHttpLoader.DOMAIN_HTTPS).append(appId).append(".hybrid.alipay-eco.com").toString();
                    if (!scriptPath.startsWith("/")) {
                        url3 = url3 + "/";
                    }
                    url = url3 + scriptPath;
                } else {
                    url = H5Utils.getAbsoluteUrlV2(url2, scriptPath, null);
                }
                H5Log.d("TinyAppMultiJsWorkerPlugin", "createWorker...url:" + url);
                jsWorkerMap.put(workerId, new MultiJsWorker(url, workerId));
                a.put(appId, jsWorkerMap);
            } catch (Throwable e) {
                H5Log.e((String) "TinyAppMultiJsWorkerPlugin", "createWorker...e:" + e);
                context.sendError(42, (String) "worker创建失败");
            }
        }
    }

    private static void b(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        if (TextUtils.isEmpty(appId)) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "multiWorkerPostMessage...appId is null");
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String workerId = H5Utils.getString(event.getParam(), (String) "workerId");
        if (TextUtils.isEmpty(workerId)) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "createWorker...workerId is null");
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        Map jsWorkerMap = a.get(appId);
        if (jsWorkerMap == null || jsWorkerMap.get(workerId) == null) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "multiWorkerPostMessage...jsWorker is null");
            context.sendError(41, (String) "worker不存在");
            return;
        }
        JSONObject message = H5Utils.getJSONObject(event.getParam(), "message", null);
        if (message == null) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "multiWorkerPostMessage...message is null");
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        ((MultiJsWorker) jsWorkerMap.get(workerId)).sendToWorker(message.toJSONString());
    }

    private static void c(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        if (h5Page == null) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
        if (TextUtils.isEmpty(appId)) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "multiWorkerTerminate...appId is null");
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        String workerId = H5Utils.getString(event.getParam(), (String) "workerId");
        if (TextUtils.isEmpty(workerId)) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "createWorker...workerId is null");
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        Map jsWorkerMap = a.get(appId);
        if (jsWorkerMap == null || jsWorkerMap.get(workerId) == null) {
            H5Log.d("TinyAppMultiJsWorkerPlugin", "multiWorkerPostMessage...jsWorker is null");
            context.sendError(41, (String) "worker不存在");
            return;
        }
        ((MultiJsWorker) jsWorkerMap.remove(workerId)).onRelease();
        if (a.isEmpty()) {
            a.remove(appId);
        }
    }

    private static boolean a(int currentCount) {
        H5ApiManager h5TinyAppService = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
        if (h5TinyAppService == null) {
            if (currentCount <= 0) {
                return true;
            }
            return false;
        } else if (currentCount >= h5TinyAppService.getAllowCreatedWorkerMaxCount()) {
            return false;
        } else {
            return true;
        }
    }
}
