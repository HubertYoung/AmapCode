package com.alipay.mobile.nebulacore.plugin;

import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.startParam.H5StartParamManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.ArrayList;
import java.util.List;

public class H5StartParamPlugin extends H5SimplePlugin {
    private static List<String> b = new ArrayList();
    private static List<String> c = new ArrayList();
    private H5Page a;

    static {
        b.add(CommonEvents.HIDE_CLOSE_BUTTON);
        b.add(H5Param.LONG_BOUNCE_TOP_COLOR);
        c.add(H5Param.CUSTOM_PARAMS);
        c.add(H5Param.LONG_BIZ_SCENARIO);
        c.add(H5Param.LONG_BACK_BEHAVIOR);
        c.add("gestureBack");
        c.add(H5Param.LONG_BOUNCE_TOP_COLOR);
        c.add("bounceBottomColor");
        c.add(H5Param.PULL_REFRESH_STYLE);
        c.add(H5Param.LONG_TITLE_IMAGE);
        c.add(H5Param.LONG_DEFAULT_TITLE);
        c.add(H5Param.LONG_TRANSPARENT_TITLE);
        c.add(H5Param.LONG_TRANSPARENT_TITLE_TEXTAUTO);
        c.add(H5Param.LONG_TITLE_BAR_COLOR);
        c.add(H5Param.LONG_TITLE_SCROLLDISTANCE);
        c.add(H5Param.LONG_NAV_SEARCH_BAR_TYPE);
        c.add(H5Param.LONG_NAV_SEARCH_BAR_PLACEHOLDER);
        c.add(H5Param.LONG_NAV_SEARCH_BAR_VALUE);
        c.add(H5Param.LONG_NAV_SEARCH_BAR_MAX_LENGTH);
        c.add(H5Param.LONG_FULLSCREEN);
        c.add(H5Param.LONG_LANDSCAPE);
        c.add(H5Param.LONG_TITLE_COLOR);
        c.add("hideCloseButton");
        c.add(H5Param.LONG_REPORTURL);
        c.add(H5Param.FEEDBACK_EXT_PARAMS);
        c.add("showDomain");
        c.add("pullRefresh");
        c.add("showOptionMenu");
        c.add("bz");
        c.add(H5Param.BACK_BEHAVIOR);
        c.add("gb");
        c.add(H5Param.BOUNCE_TOP_COLOR);
        c.add("bbc");
        c.add("pr");
        c.add("prs");
        c.add("ti");
        c.add("dt");
        c.add(H5Param.SHOW_OPTION_MENU);
        c.add(H5Param.TRANSPARENT_TITLE);
        c.add(H5Param.TRANSPARENT_TITLE_TEXTAUTO);
        c.add(H5Param.TITLE_BAR_COLOR);
        c.add(H5Param.TITLE_SCROLLDISTANCE);
        c.add(H5Param.NAV_SEARCH_BAR_TYPE);
        c.add(H5Param.NAV_SEARCH_BAR_PLACEHOLDER);
        c.add(H5Param.NAV_SEARCH_BAR_VALUE);
        c.add(H5Param.NAV_SEARCH_BAR_MAX_LENGTH);
        c.add(H5Param.FULLSCREEN);
        c.add("ls");
        c.add("tc");
        c.add("hcb");
        c.add("ru");
        c.add("fbp");
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("setStartParam");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (event.getTarget() instanceof H5Page) {
            this.a = (H5Page) event.getTarget();
        }
        if (!"setStartParam".equals(action)) {
            return false;
        }
        if (this.a == null || this.a.getParams() == null) {
            context.sendError(event, Error.INVALID_PARAM);
            return true;
        }
        try {
            String content = H5Utils.getString(event.getParam(), (String) "content");
            if (!TextUtils.isEmpty(content) && this.a != null) {
                for (String split : content.split("&")) {
                    String[] values = split.split("=");
                    if (values.length >= 2) {
                        String key = values[0];
                        String value = values[1];
                        if (!c.contains(key)) {
                            context.sendError(Error.INVALID_PARAM.ordinal(), "无效的api入参: " + key);
                            return true;
                        }
                        synchronized (this.a.getParams()) {
                            H5ParamParser.remove(this.a.getParams(), key);
                            this.a.getParams().putString(key, value);
                            H5Log.d("H5StartParamPlugin", "set startParam [key] " + key + " [value] " + value);
                            if (b.contains(key)) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put((String) "value", (Object) value);
                                this.a.sendEvent(key, jsonObject);
                            }
                        }
                    }
                }
            }
            String launchParamsTag = H5Utils.getString(event.getParam(), (String) H5StartParamManager.launchParamsTag);
            if (!TextUtils.isEmpty(launchParamsTag)) {
                Bundle launcherParam = H5StartParamManager.getInstance().getH5StartParam(H5Utils.getString(this.a.getParams(), (String) "appId"), launchParamsTag);
                if (!(launcherParam == null || launcherParam.isEmpty() || this.a == null || this.a.getParams() == null)) {
                    H5Log.d("H5StartParamPlugin", "launchParamsTag " + launcherParam);
                    this.a.getParams().putAll(launcherParam);
                }
            }
            H5ParamParser.parse(this.a.getParams(), false);
        } catch (Throwable t) {
            H5Log.e((String) "H5StartParamPlugin", t);
        }
        context.sendSuccess();
        return true;
    }
}
