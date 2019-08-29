package com.alipay.multimedia.js.bundle;

import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.android.phone.mobilecommon.dynamicrelease.DynamicReleaseApi;
import com.alipay.android.phone.mobilecommon.dynamicrelease.callback.DynamicReleaseCallback;
import com.alipay.mobile.common.transport.Request;
import com.alipay.mobile.common.transport.Response;
import com.alipay.mobile.common.transport.TransportCallback;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.multimedia.js.base.MMH5SimplePlugin;
import com.alipay.multimedia.js.utils.Logger;
import com.alipay.multimedia.js.utils.Utils;
import com.autonavi.minimap.onekeycheck.module.UploadDataResult;
import java.util.HashMap;

public class H5ModuleDownloadPlugin extends MMH5SimplePlugin {

    public class Params {
        @JSONField(name = "action")
        public int action;
        @JSONField(name = "name")
        public String name;
        @JSONField(name = "type")
        public int type;

        public Params() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public class ReturnParams {
        @JSONField(name = "exist")
        public int exist;
        @JSONField(name = "result")
        public HashMap map;
        @JSONField(name = "progress")
        public int progress;

        public ReturnParams() {
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }
    }

    public H5ModuleDownloadPlugin() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction("loadDynamicModule");
    }

    public boolean handleEvent(H5Event event, final H5BridgeContext context) {
        Logger.debug("H5ModuleDownloadPlugin", "handleEvent action: " + event.getAction() + ", params: " + event.getParam());
        final Params params = (Params) parseParams(event, Params.class);
        if (!a(params)) {
            return context.sendError(event, Error.INVALID_PARAM);
        }
        Utils.execute(new Runnable() {
            {
                if (Boolean.FALSE.booleanValue()) {
                    Log.v("hackbyte ", ClassVerifier.class.toString());
                }
            }

            public void run() {
                int i = 1;
                if (params.action == 2) {
                    boolean exist = H5ModuleDownloadPlugin.b(params.name);
                    ReturnParams returnParams = new ReturnParams();
                    if (!exist) {
                        i = 0;
                    }
                    returnParams.exist = i;
                    returnParams.progress = -1;
                    returnParams.map = new HashMap();
                    context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams));
                } else if (params.action == 1) {
                    DynamicReleaseApi.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).requireBundle(params.name, new DynamicReleaseCallback(new TransportCallback() {
                        {
                            if (Boolean.FALSE.booleanValue()) {
                                Log.v("hackbyte ", ClassVerifier.class.toString());
                            }
                        }

                        public void onCancelled(Request request) {
                            Logger.info("H5ModuleDownloadPlugin", "onCancelled");
                        }

                        public void onPreExecute(Request request) {
                            Logger.info("H5ModuleDownloadPlugin", "onPreExecute");
                        }

                        public void onPostExecute(Request request, Response response) {
                            Logger.info("H5ModuleDownloadPlugin", "onPostExecute");
                        }

                        public void onProgressUpdate(Request request, double v) {
                            Logger.info("H5ModuleDownloadPlugin", "onProgressUpdate.v=" + v);
                            ReturnParams returnParams = new ReturnParams();
                            returnParams.exist = 0;
                            returnParams.progress = (int) (100.0d * v);
                            returnParams.map = new HashMap();
                            returnParams.map.put("code", "2");
                            returnParams.map.put("msg", "downloading");
                            context.sendBridgeResultWithCallbackKept((JSONObject) JSON.toJSON(returnParams));
                        }

                        public void onFailed(Request request, int i, String s) {
                            Logger.info("H5ModuleDownloadPlugin", "onFailed");
                            ReturnParams returnParams = new ReturnParams();
                            returnParams.exist = 0;
                            returnParams.progress = -1;
                            returnParams.map = new HashMap();
                            returnParams.map.put("code", "-1");
                            returnParams.map.put("msg", UploadDataResult.FAIL_MSG);
                            context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams));
                        }
                    }) {
                        {
                            if (Boolean.FALSE.booleanValue()) {
                                Log.v("hackbyte ", ClassVerifier.class.toString());
                            }
                        }

                        public void onFinish() {
                            Logger.info("H5ModuleDownloadPlugin", "onFinish");
                            ReturnParams returnParams = new ReturnParams();
                            returnParams.exist = 0;
                            returnParams.progress = 100;
                            returnParams.map = new HashMap();
                            returnParams.map.put("code", "1");
                            returnParams.map.put("msg", "success");
                            context.sendBridgeResult((JSONObject) JSON.toJSON(returnParams));
                        }
                    });
                }
            }
        });
        return true;
    }

    private static boolean a(Params params) {
        return params != null && !TextUtils.isEmpty(params.name);
    }

    /* access modifiers changed from: private */
    public static boolean b(String name) {
        if (DynamicReleaseApi.getInstance(LauncherApplicationAgent.getInstance().getApplicationContext()).isBundleExist(name)) {
            return true;
        }
        Logger.info("H5ModuleDownloadPlugin", name + " is not exist..");
        return false;
    }
}
