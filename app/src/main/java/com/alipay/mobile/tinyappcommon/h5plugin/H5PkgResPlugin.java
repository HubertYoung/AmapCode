package com.alipay.mobile.tinyappcommon.h5plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.appcenter.center.H5GlobalPackage;
import com.alipay.mobile.nebulacore.appcenter.parse.H5ContentPackage;
import com.alipay.mobile.tinyappcommon.a.d;
import com.alipay.mobile.tinyappcommon.utils.Callback;
import com.alipay.mobile.tinyappcommon.utils.TinyAppParamUtils;
import com.alipay.mobile.tinyappcommon.utils.ipc.TinyAppIpcUtils;
import java.util.Map.Entry;

public class H5PkgResPlugin extends H5SimplePlugin {
    public static final String ACTION_ADD_PKG_RES = "addPkgRes";
    private static final String TAG = "H5PkgResPlugin";

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(ACTION_ADD_PKG_RES);
    }

    public boolean handleEvent(final H5Event event, final H5BridgeContext context) {
        String action = event.getAction();
        H5Log.d(TAG, "action is " + action);
        if (!ACTION_ADD_PKG_RES.equals(action)) {
            return false;
        }
        H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
            public void run() {
                H5PkgResPlugin.this.addPkgRes(event, context);
            }
        });
        return true;
    }

    /* access modifiers changed from: private */
    public void addPkgRes(H5Event event, H5BridgeContext context) {
        H5Page h5Page = event.getH5page();
        JSONObject eventParams = event.getParam();
        if (h5Page == null || eventParams == null) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        final String appId = TinyAppParamUtils.getAppId(h5Page);
        if (TextUtils.isEmpty(appId)) {
            context.sendError(event, Error.UNKNOWN_ERROR);
            return;
        }
        final String resAppId = H5Utils.getString(eventParams, (String) "resAppId");
        if (TextUtils.isEmpty(resAppId)) {
            context.sendError(event, Error.INVALID_PARAM);
            return;
        }
        final H5BridgeContext h5BridgeContext = context;
        final H5Event h5Event = event;
        Callback prepareCallback = new Callback<JSONObject>() {
            public void callback(JSONObject result) {
                if (h5BridgeContext == null) {
                    H5Log.d(H5PkgResPlugin.TAG, "addPkgRes ipc callback result " + result + ", context is null");
                    return;
                }
                H5Log.d(H5PkgResPlugin.TAG, "addPkgRes ipc callback result " + result);
                if (H5Utils.getBoolean(result, (String) "success", false)) {
                    H5GlobalPackage.addResourcePackageSync(appId, resAppId, true, false, true);
                    H5ContentPackage resContentPackage = H5GlobalPackage.getResContentByAppId(appId, resAppId);
                    if (resContentPackage == null) {
                        h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
                        return;
                    }
                    JSONArray array = new JSONArray();
                    for (Entry key : resContentPackage.entrySet()) {
                        String url = (String) key.getKey();
                        if (!TextUtils.isEmpty(url)) {
                            array.add(url);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put((String) "urls", (Object) array);
                    h5BridgeContext.sendBridgeResult(jsonObject);
                    return;
                }
                h5BridgeContext.sendError(h5Event, Error.UNKNOWN_ERROR);
            }
        };
        JSONObject prepareParams = new JSONObject();
        prepareParams.put((String) "appId", (Object) appId);
        prepareParams.put((String) "resAppId", (Object) resAppId);
        TinyAppIpcUtils.runOnMainProcess(new d().setAsync(true).setParam(prepareParams).setCallback(prepareCallback));
    }
}
