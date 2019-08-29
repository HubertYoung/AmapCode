package com.alipay.mobile.tinyappcommon.h5plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.tinyappcommon.TinyappUtils;
import com.alipay.mobile.tinyappcommon.mode.TinyAppEnvMode;

public class TinyAppRunScenePlugin extends H5SimplePlugin {
    private static final String GET_RUN_SCENE = "getRunScene";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(GET_RUN_SCENE);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (TextUtils.equals(GET_RUN_SCENE, event.getAction())) {
            String scene = TinyappUtils.getScene(event.getH5page());
            if (TextUtils.isEmpty(scene) || TextUtils.equals("ONLINE", scene) || TextUtils.equals(TinyAppEnvMode.EXAMINE_NEBULA, scene)) {
                sendSuccess(context, "release");
            } else if (TextUtils.equals(scene, "DEBUG")) {
                sendSuccess(context, TinyAppEnvMode.DEVELOP_TINY_APP);
            } else if (TextUtils.equals(scene, TinyAppEnvMode.TRIAL_NEBULA)) {
                sendSuccess(context, TinyAppEnvMode.TRIAL_TINY_APP);
            } else {
                context.sendError(3, (String) "发生未知错误");
            }
        }
        return true;
    }

    private void sendSuccess(H5BridgeContext context, String tinyScene) {
        JSONObject result = new JSONObject();
        result.put((String) TinyAppEnvMode.PARAM_ENV_TINY_APP, (Object) tinyScene);
        context.sendBridgeResult(result);
    }
}
