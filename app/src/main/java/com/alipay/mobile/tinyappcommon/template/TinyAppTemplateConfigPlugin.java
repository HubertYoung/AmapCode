package com.alipay.mobile.tinyappcommon.template;

import android.support.annotation.Keep;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.TinyappUtils;

@Keep
public class TinyAppTemplateConfigPlugin extends H5SimplePlugin {
    private static final String GET_EXT_CONFIG = "getExtConfig";
    private static final String TAG = "TinyAppTemplateConfigPlugin";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(GET_EXT_CONFIG);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (GET_EXT_CONFIG.equals(event.getAction())) {
            getExtConfig(event, context);
        }
        return true;
    }

    private void getExtConfig(final H5Event event, final H5BridgeContext context) {
        H5Utils.runNotOnMain(H5ThreadType.NORMAL, new Runnable() {
            public final void run() {
                String appId = TinyappUtils.getAppId(event);
                if (TextUtils.isEmpty(appId)) {
                    H5Log.d(TinyAppTemplateConfigPlugin.TAG, "getExtConfig...appId is null");
                    context.sendError(event, Error.UNKNOWN_ERROR);
                } else if (event.getH5page() == null) {
                    context.sendError(event, Error.UNKNOWN_ERROR);
                    H5Log.d(TinyAppTemplateConfigPlugin.TAG, "getExtConfig...event is null");
                } else {
                    try {
                        JSONObject config = TemplateTinyApp.getInstance().readTemplateConfig(appId, event.getH5page().getParams());
                        if (config == null) {
                            H5Log.d(TinyAppTemplateConfigPlugin.TAG, "getExtConfig...config is null");
                            context.sendError(20, (String) "templateConfig is null");
                            return;
                        }
                        Boolean extEnabled = config.getBoolean(TemplateTinyApp.EXT_ENABLE_KEY);
                        if (extEnabled == null || extEnabled.booleanValue()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put((String) "data", (Object) config.getJSONObject(ProcessInfo.ALIAS_EXT));
                            context.sendBridgeResult(jsonObject);
                            return;
                        }
                        H5Log.d(TinyAppTemplateConfigPlugin.TAG, "getExtConfig...extEnable false");
                        context.sendError(21, (String) "templateConfig disabled");
                    } catch (Throwable e) {
                        H5Log.e((String) TinyAppTemplateConfigPlugin.TAG, "getExtConfig...e: " + e);
                        context.sendError(20, (String) "templateConfig is null");
                    }
                }
            }
        });
    }
}
