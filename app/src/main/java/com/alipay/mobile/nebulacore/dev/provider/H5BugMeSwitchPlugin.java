package com.alipay.mobile.nebulacore.dev.provider;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.dev.H5BugMeManager;
import com.alipay.mobile.nebula.dev.H5DevConfig;
import com.alipay.mobile.nebula.provider.H5BugMeRpcAuthProvider;
import com.alipay.mobile.nebula.provider.H5BugMeRpcAuthProvider.AuthRpcCallback;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.env.H5Environment;

public class H5BugMeSwitchPlugin extends H5SimplePlugin {
    public void onPrepare(H5EventFilter filter) {
        filter.addAction("NBDebugSwitch");
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext context) {
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!"NBDebugSwitch".equals(event.getAction())) {
            return false;
        }
        a(event.getParam(), context);
        return true;
    }

    private boolean a(JSONObject param, final H5BridgeContext context) {
        String token = H5Utils.getString(param, (String) "token");
        String action = H5Utils.getString(param, (String) "action");
        final boolean silent = H5Utils.getBoolean(param, (String) "silent", false);
        if (TextUtils.equals(action, DataflowMonitorModel.METHOD_NAME_CLOSE)) {
            H5DevConfig.resetBugMeSettings();
            context.sendBridgeResult("success", "true");
        } else if (TextUtils.isEmpty(token)) {
            JSONObject object = new JSONObject();
            object.put((String) "error", (Object) "1");
            object.put((String) "errorMessage", (Object) H5Environment.getResources().getString(R.string.h5_bug_me_error_param));
            context.sendBridgeResult(object);
        } else {
            H5BugMeRpcAuthProvider h5BugMeRpcAuthProvider = (H5BugMeRpcAuthProvider) H5Utils.getProvider(H5BugMeRpcAuthProvider.class.getName());
            if (h5BugMeRpcAuthProvider != null) {
                h5BugMeRpcAuthProvider.rpcAuth(null, null, null, token, new AuthRpcCallback() {
                    public void onResponse(boolean pass, boolean isSuperUser, String[] domainWhiteList) {
                        H5Log.d("H5BugMeSwitchPlugin", "pass: " + pass + " isSuperUser: " + isSuperUser);
                        H5BugMeManager bugmeManager = Nebula.getService().getBugMeManager();
                        if (pass) {
                            if (context != null) {
                                context.sendBridgeResult("success", "true");
                            }
                            bugmeManager.setDomainWhiteList(domainWhiteList);
                            H5DevConfig.setBooleanConfig(H5DevConfig.H5_BUG_ME_SUPER_USER, isSuperUser);
                            H5DevConfig.debugSwitch(true, !silent, true, true, false);
                            if (!silent) {
                                bugmeManager.openSettingPanel(false);
                                return;
                            }
                            return;
                        }
                        bugmeManager.release();
                        JSONObject object = new JSONObject();
                        object.put((String) "error", (Object) "1");
                        object.put((String) "errorMessage", (Object) H5Environment.getResources().getString(R.string.h5_bug_me_err_user));
                        if (context != null) {
                            context.sendBridgeResult(object);
                        }
                    }
                });
            }
        }
        return true;
    }
}
