package com.alipay.mobile.nebulacore.core;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CoreNode;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventDispatchHandler;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.api.NebulaService;
import java.util.ArrayList;
import java.util.List;

public class H5EventDispatcher {
    public static final String TAG = "H5EventDispatcher";

    public enum Policy {
        SYNC,
        UI,
        IO
    }

    public Error sendEvent(String action) {
        return sendEvent(action, null);
    }

    public Error sendEvent(String action, JSONObject param) {
        return sendEvent(action, param, null);
    }

    public Error sendEvent(String action, JSONObject param, H5CoreNode target) {
        if (TextUtils.isEmpty(action)) {
            return Error.INVALID_PARAM;
        }
        Builder builder = new Builder();
        builder.action(action);
        builder.param(param);
        builder.target(target);
        return dispatch(builder.build(), null);
    }

    public Error dispatch(H5Event event) {
        return dispatch(event, null, Policy.UI);
    }

    public Error dispatch(H5Event event, H5BridgeContext context) {
        return dispatch(event, context, Policy.UI);
    }

    public Error dispatch(final H5Event event, final H5BridgeContext context, Policy policy) {
        if (!a(event) || policy == null) {
            return Error.INVALID_PARAM;
        }
        if (event.isDispatcherOnWorkerThread()) {
            policy = Policy.IO;
        }
        switch (policy) {
            case UI:
                H5Utils.runOnMain(new Runnable() {
                    public void run() {
                        H5EventDispatcher.b(event, context);
                    }
                });
                break;
            case IO:
                if (!H5Utils.isMain()) {
                    b(event, context);
                    break;
                } else {
                    H5EventDispatchHandler.getAsyncHandler().post(new Runnable() {
                        public void run() {
                            H5EventDispatcher.b(event, context);
                        }
                    });
                    break;
                }
        }
        return Error.NONE;
    }

    /* access modifiers changed from: private */
    public static void b(H5Event event, H5BridgeContext context) {
        long time = System.currentTimeMillis();
        try {
            c(event, context);
        } catch (Throwable e) {
            H5Log.e((String) TAG, e);
            if (Nebula.enableThrow()) {
                throw e;
            }
        }
        if (Nebula.DEBUG) {
            long time2 = System.currentTimeMillis() - time;
            String actionName = event.getAction();
            if (time2 > 10) {
                H5Log.d(TAG, "Nebula cost time [" + actionName + "] dispatch elapse " + time2);
            }
        }
    }

    private static boolean c(H5Event event, H5BridgeContext context) {
        if (event == null) {
            H5Log.e((String) TAG, "invalid params, event: " + event);
            return false;
        }
        if (context == null && event.getTarget() != null && (event.getTarget() instanceof H5Page)) {
            context = new H5BridgeContextImpl(((H5Page) event.getTarget()).getBridge(), event.getId(), event.getAction());
        }
        String actionName = event.getAction();
        if (TextUtils.isEmpty(actionName)) {
            H5Log.w(TAG, "invalid action name");
            if (context != null) {
                context.sendError(event, Error.INVALID_PARAM);
            }
            return false;
        } else if (Nebula.dispatchProcess(event, context)) {
            return true;
        } else {
            List targetList = new ArrayList();
            for (H5CoreNode target = event.getTarget(); target != null; target = target.getParent()) {
                targetList.add(target);
            }
            if (targetList.isEmpty()) {
                H5Log.w(TAG, "no event target!");
                if (context != null) {
                    context.sendError(event, Error.INVALID_PARAM);
                }
                return false;
            }
            if (Nebula.DEBUG) {
                H5Log.d(TAG, "routing event " + actionName);
            }
            int size = targetList.size();
            int index = size - 1;
            while (index >= 0) {
                H5Plugin p = (H5Plugin) targetList.get(index);
                if (event.isCanceled()) {
                    H5Log.d(TAG, "event been canceled on intercept!");
                    return false;
                } else if (p.interceptEvent(event, context)) {
                    return true;
                } else {
                    index--;
                }
            }
            int index2 = 0;
            while (index2 < size) {
                if (event.isCanceled()) {
                    H5Log.d(TAG, "event been canceled on handle!");
                    return false;
                } else if (((H5Plugin) targetList.get(index2)).handleEvent(event, context)) {
                    return true;
                } else {
                    index2++;
                }
            }
            if (context != null) {
                context.sendError(event, Error.NOT_FOUND);
            }
            H5Log.d(TAG, "[" + actionName + "] handled by nobody");
            return false;
        }
    }

    private static boolean a(H5Event event) {
        if (event == null) {
            H5Log.w(TAG, "invalid event body!");
            return false;
        }
        H5CoreNode target = event.getTarget();
        if (target == 0) {
            NebulaService service = Nebula.getService();
            if (service != 0) {
                target = service;
                H5Session session = service.getTopSession();
                if (session != 0) {
                    target = session;
                    H5CoreNode topPage = session.getTopPage();
                    if (topPage != 0) {
                        target = topPage;
                    }
                }
            }
        }
        if (target == 0) {
            H5Log.w(TAG, "invalid event target!");
            return false;
        }
        event.setTarget(target);
        return true;
    }
}
