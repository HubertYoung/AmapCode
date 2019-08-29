package com.alipay.mobile.worker.remotedebug;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.transportext.biz.spdy.apache.OkApacheClient;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.mobile.h5container.api.H5ServiceWorkerHook4Bridge;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.worker.H5WorkerControllerProvider;
import com.alipay.mobile.worker.WebWorker;
import java.util.Set;

public class RemoteDebugWorkerControllerProvider extends H5WorkerControllerProvider {
    public RemoteDebugWorkerControllerProvider(WebWorker worker) {
        super(worker);
    }

    public boolean handleMsgFromWorker(String message) {
        if (TextUtils.isEmpty(message)) {
            return false;
        }
        if (message.startsWith(H5WorkerControllerProvider.KYLIN_BRIDGE)) {
            return a(message);
        }
        if (!message.startsWith("CMD:DEBUG_MSG:")) {
            return super.handleMsgFromWorker(message);
        }
        JSONObject joMessage = H5Utils.parseObject(message.replaceFirst("CMD:DEBUG_MSG:", ""));
        if (joMessage == null || joMessage.isEmpty()) {
            return false;
        }
        return a(joMessage);
    }

    private boolean a(String message) {
        Uri uri = Uri.parse(message);
        H5Log.d(this.a, uri.toString());
        String scheme = uri.getScheme();
        if (H5WorkerControllerProvider.KYLIN_BRIDGE.equalsIgnoreCase(scheme + "://" + uri.getHost())) {
            if (this.b) {
                this.b = false;
                H5PageData.swFirstJsApiCallTime = System.currentTimeMillis();
            }
            String data = uri.getQueryParameter("data");
            H5Log.d(this.a, "handleSyncJsApiCall data " + data);
            JSONObject jsonObject = H5Utils.parseObject(data);
            if (jsonObject == null || jsonObject.isEmpty()) {
                return false;
            }
            final String action = H5Utils.getString(jsonObject, (String) "action");
            int requestId = H5Utils.getInt(jsonObject, (String) OkApacheClient.REQUESTID);
            String applicationId = H5Utils.getString(jsonObject, (String) "applicationId");
            final String callback = H5Utils.getString(jsonObject, (String) "callback");
            if (!TextUtils.isEmpty(callback)) {
                boolean hasPermission = false;
                Set<String> a = a();
                JSONObject syncConfig = H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_swSyncJsApiConfig"));
                JSONArray addList = H5Utils.getJSONArray(syncConfig, "added", null);
                if (addList != null) {
                    int size = addList.size();
                    for (int i = 0; i < size; i++) {
                        a.add(addList.getString(i));
                    }
                }
                JSONArray blackList = H5Utils.getJSONArray(syncConfig, "black_list", null);
                if (addList != null) {
                    int size2 = addList.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        a.remove(blackList.getString(i2));
                    }
                }
                if (a != null && !a.isEmpty()) {
                    hasPermission = a.contains(action);
                }
                H5Log.d(this.a, "sync hasPermission " + hasPermission);
                if (hasPermission) {
                    try {
                        final int i3 = requestId;
                        AnonymousClass1 r0 = new H5ServiceWorkerHook4Bridge() {
                            String a = new String(action.getBytes("utf-8"));
                            long b = System.currentTimeMillis();

                            public void onReceiveJsapiResult(JSONObject result) {
                                try {
                                    long cost = System.currentTimeMillis() - this.b;
                                    H5Log.d(RemoteDebugWorkerControllerProvider.this.a, "tinyAppTimeCostLog:" + action + " onReceiveJsapiResult cost " + cost);
                                    if (cost > 2000) {
                                        H5LogProvider h5LogProvider = (H5LogProvider) H5Utils.getProvider(H5LogProvider.class.getName());
                                        if (h5LogProvider != null) {
                                            h5LogProvider.log("h5_work_sync_timeout", action, null, null, null);
                                        }
                                    }
                                    if (result != null) {
                                        JSONObject resp = new JSONObject();
                                        resp.put((String) "type", (Object) "sync");
                                        resp.put((String) "responseId", (Object) Integer.valueOf(i3));
                                        resp.put((String) "responseData", (Object) result);
                                        resp.put((String) "callback", (Object) callback);
                                        RemoteDebugWorkerControllerProvider.this.c.sendMessageToWorker(null, null, resp.toJSONString());
                                        H5Log.d(RemoteDebugWorkerControllerProvider.this.a, "sync onReceiveJsapiResult action ");
                                    }
                                } catch (Exception e2) {
                                    H5Log.e(RemoteDebugWorkerControllerProvider.this.a, "sync failed to get byte array", e2);
                                }
                            }
                        };
                        a(action, jsonObject, r0, applicationId);
                    } catch (Exception e) {
                        H5Log.e(this.a, "handleSyncJsApiCall...e=" + e);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(JSONObject joMessage) {
        H5Page h5Page;
        JSONObject data = H5Utils.getJSONObject(joMessage, "data", null);
        if (!data.containsKey("viewId")) {
            h5Page = a(-1, this.c.getWorkerId());
        } else {
            h5Page = a(H5Utils.getInt(data, (String) "viewId"), this.c.getWorkerId());
        }
        if (h5Page == null) {
            H5Log.e(this.a, (String) "error! can't find target H5Page");
            return false;
        }
        h5Page.getBridge().sendToWeb(new Builder().action("socketMessage").param(joMessage).type("call").build());
        return true;
    }
}
