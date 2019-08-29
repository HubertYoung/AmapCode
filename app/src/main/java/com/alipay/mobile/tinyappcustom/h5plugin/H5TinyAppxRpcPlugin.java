package com.alipay.mobile.tinyappcustom.h5plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin;
import com.mpaas.nebula.NebulaBiz;

public class H5TinyAppxRpcPlugin extends H5SimplePlugin {
    private static final String a = H5TinyAppxRpcPlugin.class.getName();
    private static JSONArray b;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("appxrpc");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String appId;
        if ("appxrpc".equals(event.getAction())) {
            try {
                H5Page h5Page = event.getH5page();
                if (h5Page == null) {
                    context.sendError(event, Error.UNKNOWN_ERROR);
                } else {
                    JSONObject eventParams = event.getParam();
                    if (eventParams == null) {
                        context.sendError(event, Error.INVALID_PARAM);
                    } else {
                        String operationType = H5Utils.getString(eventParams, (String) TransportConstants.KEY_OPERATION_TYPE);
                        if (operationType == null) {
                            context.sendError(event, Error.INVALID_PARAM);
                        } else {
                            a();
                            if (b == null || b.contains(operationType)) {
                                if (TinyAppMiniServicePlugin.appIsMiniService(h5Page)) {
                                    appId = H5Utils.getString(h5Page.getParams(), (String) "parentAppId");
                                } else {
                                    String webViewTag = H5Utils.getString(h5Page.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG");
                                    appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
                                    if (!TextUtils.isEmpty(webViewTag) && TextUtils.isEmpty(appId)) {
                                        appId = webViewTag;
                                    }
                                }
                                String parentAppId = H5Utils.getString(h5Page.getParams(), (String) "parentAppId");
                                H5Service h5Service = H5ServiceUtils.getH5Service();
                                if (h5Service == null) {
                                    context.sendError(event, Error.UNKNOWN_ERROR);
                                } else {
                                    JSONObject headers = eventParams.getJSONObject("headers");
                                    if (headers == null) {
                                        headers = new JSONObject();
                                    }
                                    headers.put((String) "TINY_APP_ID", (Object) appId);
                                    if (!TextUtils.isEmpty(parentAppId)) {
                                        headers.put((String) "TINY_APP_PARENT_ID", (Object) parentAppId);
                                    }
                                    eventParams.put((String) "headers", (Object) headers);
                                    eventParams.put((String) TransportConstants.KEY_OPERATION_TYPE, (Object) operationType);
                                    event.setAction("rpc");
                                    h5Service.sendEvent(event, context);
                                }
                            } else {
                                context.sendError(event, Error.FORBIDDEN);
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                context.sendError(event, Error.UNKNOWN_ERROR);
            }
        }
        return true;
    }

    private static void a() {
        if (b == null) {
            try {
                b = JSON.parseArray(NebulaBiz.getConfig("ta_appx_rpc_whitelist_cfg"));
            } catch (Exception e) {
                H5Log.e(a, "parse string to json array exception", e);
            }
        }
    }
}
