package com.alipay.mobile.tinyappcommon.a;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam;
import com.alipay.mobile.nebula.appcenter.api.H5UpdateAppParam.Builder;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.callback.H5UpdateAppCallback;
import com.alipay.mobile.nebula.provider.H5AppProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ThreadType;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.tinyappcommon.utils.ipc.TinyAppIpcTask;
import java.util.HashMap;
import java.util.Map;

/* compiled from: PreparePkgResTask */
public final class d extends TinyAppIpcTask {
    public final JSONObject run(JSONObject param) {
        final JSONObject result = null;
        if (param != null) {
            result = new JSONObject((Map<String, Object>) param);
            if (TextUtils.isEmpty(H5Utils.getString(param, (String) "appId"))) {
                result.put((String) "error", (Object) Integer.valueOf(2));
                if (isAsync()) {
                    replyResult(result);
                }
            } else {
                final String resAppId = H5Utils.getString(param, (String) "resAppId");
                if (TextUtils.isEmpty(resAppId)) {
                    result.put((String) "error", (Object) Integer.valueOf(2));
                    if (isAsync()) {
                        replyResult(result);
                    }
                } else {
                    H5Utils.runNotOnMain(H5ThreadType.IO, new Runnable() {
                        public final void run() {
                            boolean z;
                            boolean forceRpc;
                            boolean z2 = true;
                            final H5AppProvider provider = (H5AppProvider) H5Utils.getProvider(H5AppProvider.class.getName());
                            if (provider == null) {
                                result.put((String) "error", (Object) Integer.valueOf(3));
                                if (d.this.isAsync()) {
                                    d.this.replyResult(result);
                                    return;
                                }
                                return;
                            }
                            String queryVersion = provider.getWalletConfigNebulaVersion(resAppId);
                            Map appIdMap = new HashMap();
                            appIdMap.put(resAppId, queryVersion);
                            H5UpdateAppCallback h5UpdateAppCallback = new H5UpdateAppCallback() {
                                public final void onResult(boolean success, boolean limit) {
                                    H5Log.d("PreparePkgResTask", "prepareUpdate...result: " + success);
                                    if (!success) {
                                        if (provider.getAppInfo(resAppId) != null) {
                                            H5Log.d("PreparePkgResTask", "success is false but res pkg is available.");
                                            result.put((String) "success", (Object) Boolean.valueOf(true));
                                        } else {
                                            result.put((String) "error", (Object) Integer.valueOf(3));
                                        }
                                    } else {
                                        result.put((String) "success", (Object) Boolean.valueOf(true));
                                    }
                                    d.this.replyResult(result);
                                }
                            };
                            if (provider.getAppInfo(resAppId) != null) {
                                z = true;
                            } else {
                                z = false;
                            }
                            if (!z || "1".equals(H5Environment.getConfigWithProcessCache("ta_pkgRes_enableforce"))) {
                                forceRpc = true;
                            } else {
                                forceRpc = false;
                            }
                            AppReq appReq = new AppReq();
                            appReq.reqmode = forceRpc ? "syncforce" : "async";
                            Builder appReq2 = H5UpdateAppParam.newBuilder().setAppMap(appIdMap).setForceRpc(forceRpc).setAppReq(appReq);
                            if ("0".equals(H5Environment.getConfigWithProcessCache("ta_pkgRes_download_amr"))) {
                                z2 = false;
                            }
                            provider.updateApp(appReq2.setDownLoadAmr(z2).setStartTime(System.currentTimeMillis()).setUpdateCallback(h5UpdateAppCallback).build());
                        }
                    });
                }
            }
        } else if (isAsync()) {
            replyResult(null);
        }
        return result;
    }
}
