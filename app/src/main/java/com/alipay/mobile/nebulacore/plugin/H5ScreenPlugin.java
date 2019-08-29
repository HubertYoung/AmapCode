package com.alipay.mobile.nebulacore.plugin;

import android.app.Activity;
import android.graphics.Rect;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5StatusBarUtils;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5ScreenPlugin extends H5SimplePlugin {
    private Boolean a = null;
    private JSONArray b = null;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("setLandscape");
        filter.addAction("setPortrait");
        filter.addAction("allowSystemSnapshot");
        filter.addAction("setScreenAutolock");
        filter.addAction("getTitleAndStatusBarHeight");
        filter.addAction("getTitleAndStatusbarHeight");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if ("getTitleAndStatusbarHeight".equals(action)) {
            return b(context, event);
        }
        Activity activity = event.getActivity();
        if (activity != null && !activity.isFinishing()) {
            try {
                if ("setLandscape".equals(action)) {
                    if (activity.getRequestedOrientation() != 0) {
                        activity.setRequestedOrientation(0);
                    }
                } else if ("setPortrait".equals(action)) {
                    if (activity.getRequestedOrientation() != 1) {
                        activity.setRequestedOrientation(1);
                    }
                } else if ("allowSystemSnapshot".equals(action)) {
                    if (!H5Utils.getBoolean(event.getParam(), (String) "allow", true)) {
                        activity.getWindow().setFlags(8192, 8192);
                    } else {
                        activity.getWindow().clearFlags(8192);
                    }
                } else if ("setScreenAutolock".equals(action)) {
                    String actionType = H5Utils.getString(event.getParam(), (String) "actionType");
                    if (TextUtils.equals(actionType, "disable")) {
                        activity.getWindow().setFlags(128, 128);
                    } else if (TextUtils.equals(actionType, "enable")) {
                        activity.getWindow().clearFlags(128);
                    } else {
                        context.sendError(event, Error.INVALID_PARAM);
                        return true;
                    }
                } else if ("getTitleAndStatusBarHeight".equals(action)) {
                    return a(context, event);
                }
            } catch (Exception e) {
                H5Log.e((String) "", (Throwable) e);
            }
        }
        context.sendSuccess();
        return true;
    }

    private static boolean a(H5BridgeContext context, H5Event h5Event) {
        int density = H5DimensionUtil.dip2px(H5Environment.getContext(), 1.0f);
        JSONObject json = new JSONObject();
        json.put((String) "titleBarHeight", (Object) Float.valueOf(H5Environment.getContext().getResources().getDimension(R.dimen.h5_title_height) / ((float) density)));
        json.put((String) "density", (Object) Integer.valueOf(density));
        Rect frame = new Rect();
        Activity topActivity = h5Event.getActivity();
        if (topActivity != null && density > 0) {
            topActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            json.put((String) "statusBarHeight", (Object) Integer.valueOf(frame.top / density));
        }
        context.sendBridgeResult(json);
        return true;
    }

    private boolean b(H5BridgeContext bridgeContext, H5Event h5Event) {
        JSONObject json = new JSONObject();
        json.put((String) "titleBarHeight", (Object) Integer.valueOf(48));
        float density = LauncherApplicationAgent.getInstance().getApplicationContext().getResources().getDisplayMetrics().density;
        json.put((String) "density", (Object) Float.valueOf(density));
        if (this.a == null) {
            JSONObject getStatusBarWithNewConfigJson = H5Utils.parseObject(H5Environment.getConfigWithProcessCache("h5_getStatusBarHeightWithNewMethod"));
            this.a = Boolean.valueOf(H5Utils.getBoolean(getStatusBarWithNewConfigJson, (String) "enable", false));
            this.b = H5Utils.getJSONArray(getStatusBarWithNewConfigJson, "blackAppId", null);
        }
        if (h5Event != null) {
            H5Page h5Page = (H5Page) h5Event.getTarget();
            if (h5Page != null) {
                String appId = H5Utils.getString(h5Page.getParams(), (String) "appId");
                if (this.a.booleanValue() && (this.b == null || !this.b.contains(appId))) {
                    json.put((String) "statusBarHeight", (Object) Float.valueOf(((float) H5StatusBarUtils.getStatusBarHeight(H5Environment.getContext())) / density));
                    bridgeContext.sendBridgeResult(json);
                    return true;
                }
            }
        }
        Rect frame = new Rect();
        Activity topActivity = (Activity) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopActivity().get();
        if (topActivity != null && density > 0.0f) {
            topActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            json.put((String) "statusBarHeight", (Object) Float.valueOf(((float) frame.top) / density));
        }
        bridgeContext.sendBridgeResult(json);
        return true;
    }
}
