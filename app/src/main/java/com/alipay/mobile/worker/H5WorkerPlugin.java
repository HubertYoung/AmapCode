package com.alipay.mobile.worker;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.embedview.H5EmbedWebView;
import com.alipay.mobile.worker.WebWorker.WorkerReadyListener;
import com.alipay.mobile.worker.remotedebug.RemoteDebugConstants;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptor;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptorManager;

public class H5WorkerPlugin extends H5SimplePlugin {
    private final String a = "H5WorkerPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("registerWorker");
        filter.addAction(H5EmbedWebView.ACTION_TYPE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        H5Page h5Page = null;
        if (event.getTarget() instanceof H5Page) {
            h5Page = (H5Page) event.getTarget();
        }
        String action = event.getAction();
        if ("registerWorker".equals(action)) {
            JSONObject object = event.getParam();
            if (object == null || TextUtils.isEmpty(object.getString("worker"))) {
                context.sendError(event, Error.INVALID_PARAM);
            } else if (h5Page == null || TextUtils.isEmpty(h5Page.getSession().getServiceWorkerID())) {
                String worker = object.getString("worker");
                String worker2 = worker.substring(0, worker.indexOf("?"));
                H5Log.d("H5WorkerPlugin", "REGISTER_WORKER worker: " + worker2);
                JSONObject callbackResult = new JSONObject();
                callbackResult.put((String) "state", (Object) "installed");
                context.sendBridgeResult(callbackResult);
                if ("true".equals(H5Utils.getString(h5Page.getParams(), (String) RemoteDebugConstants.IS_REMOTE_DEBUG_MODE))) {
                    TinyAppRemoteDebugInterceptor interceptor = TinyAppRemoteDebugInterceptorManager.get().getTinyAppRemoteDebugInterceptor();
                    TinyAppRemoteDebugInterceptorManager.get().setRemoteDebug(true);
                    if (interceptor == null) {
                        H5Log.d("H5WorkerPlugin", "REGISTER_WORKER...remoteDebug launch failed");
                    } else {
                        interceptor.registerWorker(worker2, event);
                    }
                } else {
                    TinyAppRemoteDebugInterceptorManager.get().setRemoteDebug(false);
                }
                WorkerManager.registerWorker(worker2, h5Page.getParams());
                H5Session h5Session = h5Page.getSession();
                if (h5Session != null) {
                    h5Session.setServiceWorkerID(worker2);
                }
                H5Log.d("H5WorkerPlugin", "success setServiceWorkerID " + worker2);
            } else {
                JSONObject callbackResult2 = new JSONObject();
                callbackResult2.put((String) "state", (Object) "installed");
                context.sendBridgeResult(callbackResult2);
                return true;
            }
            return true;
        } else if (!TextUtils.equals(action, H5EmbedWebView.ACTION_TYPE)) {
            return super.handleEvent(event, context);
        } else {
            JSONObject params = event.getParam();
            if (h5Page != null) {
                final JSONObject jo = new JSONObject();
                jo.put((String) "handlerName", (Object) "message");
                jo.put((String) "data", (Object) params);
                jo.getJSONObject("data").put((String) "pageId", (Object) Integer.valueOf(h5Page.getPageId()));
                jo.getJSONObject("data").put((String) "viewId", (Object) Integer.valueOf(h5Page.getWebViewId()));
                if (!TextUtils.isEmpty(h5Page.getSession().getServiceWorkerID()) && WorkerManager.getWorker(h5Page.getSession().getServiceWorkerID()) != null) {
                    final WebWorker worker3 = WorkerManager.getWorker(h5Page.getSession().getServiceWorkerID());
                    if (worker3.isWorkerReady()) {
                        worker3.sendMessageToWorker(null, null, jo.toJSONString());
                    } else {
                        worker3.registerWorkerReadyListener(new WorkerReadyListener() {
                            public void onWorkerReady() {
                                worker3.sendMessageToWorker(null, null, jo.toJSONString());
                            }
                        });
                    }
                }
            }
            return true;
        }
    }
}
