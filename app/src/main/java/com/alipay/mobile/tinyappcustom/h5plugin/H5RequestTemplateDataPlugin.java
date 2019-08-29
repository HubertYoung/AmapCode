package com.alipay.mobile.tinyappcustom.h5plugin;

import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.h5plugin.TinyAppMiniServicePlugin;
import com.alipay.mobile.tinyappcustom.b.c;
import com.alipay.mobile.tinyappcustom.c.a;
import java.util.Random;

public class H5RequestTemplateDataPlugin extends H5SimplePlugin {
    public static final String REQUEST_TEMPLATE_DATA = "requestTemplateData";
    /* access modifiers changed from: private */
    public static final String a = H5RequestTemplateDataPlugin.class.getSimpleName();
    private final String b = "alipay.openservice.mini.templatemessage.submitFormId";
    private final String c = "formId";
    private final String d = "userId";
    private final String e = "appId";
    private final String f = "receiverFormId";
    private final String g = "receiverUserId";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(REQUEST_TEMPLATE_DATA);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (REQUEST_TEMPLATE_DATA.equals(event.getAction())) {
            a(event, context);
        }
        return true;
    }

    private void a(H5Event event, H5BridgeContext bridgeContext) {
        final String userId = a.a();
        if (TextUtils.isEmpty(userId)) {
            JSONObject result = new JSONObject();
            result.put((String) "error", (Object) Integer.valueOf(12));
            result.put((String) "errorMessage", (Object) "用户未登录");
            bridgeContext.sendBridgeResult(result);
            return;
        }
        try {
            String receiverUserId = H5Utils.getString(event.getParam(), (String) "receiverUserId");
            if (TextUtils.isEmpty(receiverUserId)) {
                JSONObject result2 = new JSONObject();
                result2.put((String) "e_invalid_params", (Object) "无效的api入参");
                bridgeContext.sendBridgeResult(result2);
                return;
            }
            if (TextUtils.equals(userId, receiverUserId)) {
                receiverUserId = null;
            }
            String receiverFormId = a(receiverUserId);
            final String tempReceiverUserId = receiverUserId;
            final String tempReceiverFormId = receiverFormId;
            final String formId = a(userId);
            H5Log.d(a, "requestTemplateData...formId=" + formId);
            JSONObject object = new JSONObject();
            object.put((String) "formId", (Object) formId);
            if (!TextUtils.isEmpty(receiverFormId)) {
                object.put((String) "receiverFormId", (Object) receiverFormId);
            }
            bridgeContext.sendBridgeResult(object);
            final H5Event h5Event = event;
            ((TaskScheduleService) H5Utils.findServiceByInterface(TaskScheduleService.class.getName())).acquireExecutor(ScheduleType.NORMAL).execute(new Runnable() {
                public void run() {
                    try {
                        H5Page h5Page = h5Event.getH5page();
                        if (h5Page != null) {
                            String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
                            if (TinyAppMiniServicePlugin.appIsMiniService(h5Page)) {
                                appId = H5Utils.getString(h5Page.getParams(), (String) "parentAppId");
                            }
                            H5RequestTemplateDataPlugin.b(H5RequestTemplateDataPlugin.b(formId, userId, appId, h5Event), "formId");
                            if (!TextUtils.isEmpty(tempReceiverFormId)) {
                                H5RequestTemplateDataPlugin.b(H5RequestTemplateDataPlugin.b(tempReceiverFormId, tempReceiverUserId, appId, h5Event), "tempReceiverFormId");
                            }
                        }
                    } catch (Throwable e2) {
                        H5Log.e(H5RequestTemplateDataPlugin.a, "requestTemplateData...rpc e=" + e2);
                    }
                }
            });
        } catch (Throwable e2) {
            H5Log.d(a, "requestTemplateData...e=" + e2);
        }
    }

    /* access modifiers changed from: private */
    public static void b(com.alipay.mobile.tinyappcustom.b.a h5Response, String flag) {
        if (h5Response == null || TextUtils.isEmpty(h5Response.b())) {
            H5Log.d(a, "requestTemplateData...rpc failed. (" + flag + " sendRpcMessage)");
            return;
        }
        JSONObject result = JSONObject.parseObject(h5Response.b());
        if (result == null || !result.getBoolean("success").booleanValue()) {
            H5Log.d(a, "requestTemplateData...rpc failed. (" + flag + " get result)");
        } else {
            H5Log.d(a, "requestTemplateData...rpc success. (" + flag + ")");
        }
    }

    /* access modifiers changed from: private */
    public static com.alipay.mobile.tinyappcustom.b.a b(String formId, String userId, String appId, H5Event event) {
        JSONObject rpcRequest = new JSONObject();
        rpcRequest.put((String) "formId", (Object) formId);
        rpcRequest.put((String) "userId", (Object) userId);
        rpcRequest.put((String) "appId", (Object) appId);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(rpcRequest);
        return c.a("alipay.openservice.mini.templatemessage.submitFormId", jsonArray.toString(), "", false, new JSONObject(), null, event.getH5page(), null);
    }

    private static String a(String userId) {
        if (TextUtils.isEmpty(userId)) {
            return null;
        }
        return Base64.encodeToString((userId + "_" + System.currentTimeMillis() + "_" + (new Random().nextInt(SecExceptionCode.SEC_ERROR_PKG_VALID_UNKNOWN_ERR) + 100)).getBytes(), 2);
    }
}
