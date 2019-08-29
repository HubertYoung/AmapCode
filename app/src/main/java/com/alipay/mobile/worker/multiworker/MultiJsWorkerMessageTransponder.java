package com.alipay.mobile.worker.multiworker;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin;
import java.lang.reflect.Method;
import java.util.Stack;

public class MultiJsWorkerMessageTransponder {
    private MultiJsWorker a;

    public MultiJsWorkerMessageTransponder(MultiJsWorker worker) {
        this.a = worker;
    }

    public void handleMessageFromWorker(String message) {
        if (this.a == null) {
            H5Log.d("MultiJsWorker", "handleMessageFromWorker...worker is null");
        } else if (TextUtils.isEmpty(message)) {
            H5Log.d("MultiJsWorker", "handleMessageFromWorker...message is null");
        } else {
            if (message.startsWith("jserror:")) {
                message = message.replaceFirst("jserror:", "");
            }
            if (message.startsWith("create_worker:")) {
                String message2 = message.replaceFirst("create_worker:", "");
                if (TextUtils.isEmpty(message2)) {
                    H5Log.d("MultiJsWorker", "handleMessageFromWorker...after message is null");
                    return;
                }
                H5Page h5Page = a();
                if (h5Page == null || h5Page.getBridge() == null) {
                    H5Log.d("MultiJsWorker", "handleMessageFromWorker...top H5Page is null");
                    return;
                }
                H5Log.d("MultiJsWorker", "handleMessageFromWorker...message: " + message2);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put((String) "message", (Object) JSON.parseObject(message2));
                } catch (Throwable th) {
                    H5Log.e((String) "MultiJsWorker", "handleMessageFromWorker...e: " + message2);
                    jsonObject.put((String) "message", (Object) message2);
                }
                jsonObject.put((String) "workerId", (Object) this.a.getWorkerId());
                h5Page.getBridge().sendDataWarpToWeb("multiWorkerMessage", jsonObject, null);
            } else if (message.equals(MultiJsWorker.MULTI_WORKER_READY)) {
                this.a.setMultiWorkerReady();
            } else {
                try {
                    Method method = Class.forName("com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin").getDeclaredMethod("sendWebWorkerLog", new Class[]{String.class});
                    method.setAccessible(true);
                    method.invoke(null, new Object[]{message});
                } catch (Exception e) {
                    H5Log.e((String) "MultiJsWorker", "handleMessageFromWorker...e:" + e);
                }
            }
        }
    }

    private static H5Page a() {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            return null;
        }
        try {
            Stack sessions = h5Service.getSessions();
            if (sessions == null) {
                return null;
            }
            synchronized (sessions) {
                for (int index = sessions.size() - 1; index >= 0; index--) {
                    H5Session session = (H5Session) sessions.get(index);
                    if (session != null) {
                        String id = session.getId();
                        H5Log.d("MultiJsWorker", "sessionId:" + id);
                        if (!a(id)) {
                            H5Page h5Page = session.getTopPage();
                            if (h5Page == null) {
                                continue;
                            } else {
                                if (!(!TextUtils.isEmpty(H5Utils.getString(h5Page.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG")))) {
                                    return h5Page;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        } catch (Throwable throwable) {
            H5Log.e((String) "MultiJsWorker", throwable);
        }
        return null;
    }

    private static boolean a(String id) {
        return !TextUtils.isEmpty(id) && id.contains(H5VConsolePlugin.DEBUG_PANEL_PACKAGE_APPID);
    }
}
