package com.mpaas.nebula.adapter.api;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilecommon.dynamicrelease.nebula.NebulaCenterOperator;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.model.AppRes;
import com.alipay.mobile.nebula.provider.H5AppBizRpcProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobileappcommon.biz.rpc.dynamic.model.pb.UnionExtraData;
import com.alipay.mobileappcommon.biz.rpc.dynamic.model.pb.UnionResourceInfo;
import com.alipay.mobileappcommon.biz.rpc.dynamic.model.pb.UnionResourceItem;
import com.alipay.mobileappcommon.biz.rpc.dynamic.model.pb.UnionResourceResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MPAppBizRpcImpl implements H5AppBizRpcProvider {
    public static final String FAIL = "Fail";
    public static final String START = "Start";
    public static final String START_FAIL = "Start|Fail";
    public static final int TYPE_TINYAPP = 4;

    public AppRes app(AppReq req) {
        try {
            return rpcCall(req);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "MPH5Adapter_MPAppBizRpcImpl", e);
            return null;
        }
    }

    public String rpcCall(String s, AppReq appReq) {
        return null;
    }

    public AppRes handlerPKgJson(String s) {
        return null;
    }

    private static Map<String, String> a(AppReq appReq) {
        HashMap hashMap = new HashMap();
        if (!(appReq == null || appReq.query == null)) {
            JSONObject headerJsonOb = H5Utils.parseObject(appReq.query);
            if (headerJsonOb != null && !headerJsonOb.isEmpty()) {
                for (String key : headerJsonOb.keySet()) {
                    try {
                        hashMap.put(key, a(headerJsonOb, key));
                    } catch (Exception e) {
                        H5Log.e((String) "MPH5Adapter_MPAppBizRpcImpl", (Throwable) e);
                    }
                }
            }
        }
        return hashMap;
    }

    private static String a(JSONObject object, String key) {
        String json = object.get(key).toString();
        if (TextUtils.isEmpty(json)) {
            return "0";
        }
        String version = new org.json.JSONObject(json).optString("version");
        if (TextUtils.isEmpty(version)) {
            return "0";
        }
        return version;
    }

    public AppRes rpcCall(AppReq appReq) {
        AppRes appRes;
        NebulaCenterOperator nebulaCenterOperator = new NebulaCenterOperator(LauncherApplicationAgent.getInstance().getApplicationContext());
        AppRes appRes2 = null;
        try {
            log("start rpc");
            UnionResourceResult unionResourceResult = nebulaCenterOperator.rpcRequest(a(appReq));
            if (unionResourceResult == null) {
                writeRPCLog(START_FAIL, new IllegalStateException("result == null"));
            } else if (!unionResourceResult.success.booleanValue()) {
                appRes = new AppRes();
                try {
                    RuntimeException e = new IllegalStateException("result.success=false");
                    appRes.data = new ArrayList();
                    appRes.rpcFailDes = unionResourceResult.message;
                    writeRPCLog(START_FAIL, e);
                    appRes2 = appRes;
                } catch (Throwable th) {
                    throwable = th;
                    appRes2 = appRes;
                    writeRPCLog(START_FAIL, throwable);
                    appRes2.rpcFailDes = throwable.getMessage();
                    return appRes2;
                }
            } else {
                appRes = new AppRes();
                List data = new ArrayList();
                appRes.data = data;
                a(unionResourceResult, data);
                AppRes appRes3 = appRes;
                return appRes;
            }
        } catch (Throwable th2) {
            throwable = th2;
            writeRPCLog(START_FAIL, throwable);
            appRes2.rpcFailDes = throwable.getMessage();
            return appRes2;
        }
        return appRes2;
    }

    private void a(UnionResourceResult unionResourceResult, List<AppInfo> data) {
        if (unionResourceResult.info != null && !unionResourceResult.info.isEmpty()) {
            UnionResourceInfo unionResourceInfo = (UnionResourceInfo) unionResourceResult.info.get(0);
            if (unionResourceInfo != null) {
                List<UnionResourceItem> unionResourceItemList = unionResourceInfo.item;
                if (unionResourceItemList != null) {
                    for (UnionResourceItem unionResourceItem : unionResourceItemList) {
                        if (unionResourceItem != null) {
                            AppInfo appInfo = transUnionResource2AppInfo(unionResourceItem);
                            if (appInfo != null) {
                                data.add(appInfo);
                            }
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public AppInfo transUnionResource2AppInfo(UnionResourceItem unionResourceItem) {
        if (unionResourceItem == null) {
            return null;
        }
        AppInfo appInfo = new AppInfo();
        appInfo.app_id = unionResourceItem.resId;
        appInfo.package_url = unionResourceItem.fileUrl;
        appInfo.version = unionResourceItem.resVersion;
        appInfo.patch = unionResourceItem.diffUrl;
        List<UnionExtraData> resExtraData = unionResourceItem.resExtraData;
        if (resExtraData != null) {
            for (UnionExtraData unionExtraData : resExtraData) {
                if (unionExtraData != null) {
                    a(appInfo, unionExtraData);
                    if ("mainurl".equals(unionExtraData.key)) {
                        appInfo.main_url = unionExtraData.value;
                    } else if ("vhost".equals(unionExtraData.key)) {
                        appInfo.vhost = unionExtraData.value;
                    } else if ("autoinstall".equals(unionExtraData.key)) {
                        appInfo.auto_install = c(unionExtraData.value);
                    } else if ("extendinfo".equals(unionExtraData.key)) {
                        appInfo.extend_info_jo = unionExtraData.value;
                    } else if ("iconUrl".equals(unionExtraData.key)) {
                        appInfo.icon_url = unionExtraData.value;
                    } else if ("resName".equals(unionExtraData.key)) {
                        appInfo.name = unionExtraData.value;
                    } else if ("resType".equals(unionExtraData.key)) {
                        appInfo.app_channel = a(unionExtraData.value);
                    } else if ("size".equals(unionExtraData.key)) {
                        appInfo.size = b(unionExtraData.value);
                    }
                }
            }
        }
        a(appInfo);
        log(appInfo.toString());
        return appInfo;
    }

    private static void a(AppInfo appInfo) {
        if (appInfo.app_channel == 4) {
            try {
                JSONObject extend_info_json = JSON.parseObject(appInfo.extend_info_jo);
                JSONObject launchParams = extend_info_json.getJSONObject(H5Param.LAUNCHER_PARAM);
                if (launchParams == null) {
                    launchParams = new JSONObject();
                }
                launchParams.put((String) H5Param.ENABLE_DSL, (Object) "YES");
                launchParams.put((String) "tinyPubRes", (Object) "YES");
                launchParams.put((String) H5Param.LONG_NB_OFFLINE, (Object) "sync");
                extend_info_json.put((String) H5Param.LAUNCHER_PARAM, (Object) launchParams);
                appInfo.extend_info_jo = extend_info_json.toJSONString();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private static int a(String unionExtraData) {
        int i = 1;
        if (TextUtils.isEmpty(unionExtraData)) {
            return i;
        }
        try {
            return Integer.parseInt(unionExtraData);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return i;
        }
    }

    private static long b(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            log(e.getMessage());
            return 0;
        }
    }

    private static void a(AppInfo appInfo, UnionExtraData unionExtraData) {
        if ("feedbackurl".equals(unionExtraData.key)) {
            appInfo.fallback_base_url = unionExtraData.value;
        }
        if (TextUtils.isEmpty(appInfo.fallback_base_url) && "fallbackbaseurl".equals(unionExtraData.key)) {
            appInfo.fallback_base_url = unionExtraData.value;
        }
    }

    private static int c(String value) {
        if (TextUtils.isEmpty(value)) {
            return 1;
        }
        return Integer.parseInt(value);
    }

    public static void log(String log) {
        LoggerFactory.getTraceLogger().debug("appinfo", log);
    }

    public static void writeRPCLog(String status, Throwable exception) {
        LoggerFactory.getTraceLogger().error("RPC" + status, exception);
    }
}
