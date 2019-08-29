package com.alipay.mobile.nebula.baseprovider;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.bqcscanservice.BQCCameraParam;
import com.alipay.mobile.nebula.appcenter.H5RpcFailResult;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.model.AppRes;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5AppBizRpcProvider;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5LoginProvider;
import com.alipay.mobile.nebula.provider.H5RpcProxyProvider;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.wallet.H5WalletWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class H5BaseAppBizRpcProvider implements H5AppBizRpcProvider {
    private static String TAG = "H5AppRpc";
    public static final String bugMeRpcName = "com.alipay.hpmweb.app";
    private static final String normalRpcName = "com.alipay.wapcenter.rpc.clientService.app";

    public AppRes app(AppReq req) {
        String rpcResult;
        if (req == null) {
            AppRes appRes = new AppRes();
            appRes.rpcFailDes = H5RpcFailResult.INVALID_PARAM;
            return appRes;
        }
        H5LoginProvider h5LoginProvider = (H5LoginProvider) H5Utils.getProvider(H5LoginProvider.class.getName());
        if (h5LoginProvider == null || h5LoginProvider.isLogin()) {
            String rpcName = normalRpcName;
            if (TextUtils.equals(req.nbsource, "debug")) {
                H5ApiManager h5ApiManager = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
                if (h5ApiManager != null) {
                    rpcName = h5ApiManager.getDebugAppInfoRpcName();
                } else {
                    rpcName = bugMeRpcName;
                }
            }
            long rpcCallTime = System.currentTimeMillis();
            H5RpcProxyProvider h5RpcProxyProvider = (H5RpcProxyProvider) H5Utils.getProvider(H5RpcProxyProvider.class.getName());
            if (h5RpcProxyProvider == null || !normalRpcName.equals(rpcName) || !enableUse()) {
                rpcResult = rpcCall(rpcName, req);
            } else {
                if (bugMeRpcName.equals(rpcName)) {
                    req.openPlatReqMode = 3;
                }
                H5Log.d(TAG, " req:" + req.toString());
                rpcResult = h5RpcProxyProvider.getPkgJson(req);
            }
            long handlerJsonTime = System.currentTimeMillis();
            AppRes appRes2 = handlerPKgJson(rpcResult);
            H5Log.d(TAG, "RpcTime rpcCall cost " + (handlerJsonTime - rpcCallTime) + " handlerJsonTime：" + (System.currentTimeMillis() - handlerJsonTime));
            return appRes2;
        }
        H5Log.d(TAG, "!h5LoginProvider.isLogin() not handle rpc data ");
        AppRes appRes3 = new AppRes();
        appRes3.rpcFailDes = H5RpcFailResult.NOT_LOGIN;
        return appRes3;
    }

    public AppRes handlerPKgJson(String rpcResult) {
        if (H5RpcFailResult.LIMIT.equals(rpcResult)) {
            AppRes appRes = new AppRes();
            appRes.rpcFailDes = H5RpcFailResult.LIMIT;
            return appRes;
        }
        JSONObject result = H5Utils.parseObject(rpcResult);
        if (result == null || result.isEmpty()) {
            return null;
        }
        H5Log.d(TAG, "result:" + result);
        if (H5Utils.getInt(result, (String) "resultCode", 0) == 100) {
            AppRes appRes2 = new AppRes();
            JSONArray appInfoList = H5Utils.getJSONArray(result, "data", null);
            if (appInfoList != null) {
                List infoList = new ArrayList();
                for (int index = 0; index <= appInfoList.size() - 1; index++) {
                    AppInfo appInfo = H5AppUtil.toAppInfo((JSONObject) appInfoList.get(index));
                    if (appInfo != null) {
                        infoList.add(appInfo);
                    }
                }
                H5Log.d(TAG, "get rpc result appInfo size:" + infoList.size());
                appRes2.data = infoList;
            }
            JSONArray removeAppIdList = H5Utils.getJSONArray(result, "removeAppIdList", null);
            H5Log.d(TAG, "removeAppIdList:" + removeAppIdList);
            if (removeAppIdList != null && !removeAppIdList.isEmpty()) {
                List list = new ArrayList();
                for (int index2 = 0; index2 <= removeAppIdList.size() - 1; index2++) {
                    if (removeAppIdList.get(index2) instanceof String) {
                        list.add((String) removeAppIdList.get(index2));
                    }
                }
                appRes2.removeAppIdList = list;
            }
            if (!BQCCameraParam.VALUE_NO.equalsIgnoreCase(H5WalletWrapper.getConfig("h5_handleDiscardData"))) {
                JSONObject discardDataObject = H5Utils.getJSONObject(result, "discardData", null);
                H5Log.d(TAG, "discardData: " + discardDataObject);
                if (discardDataObject != null && discardDataObject.size() > 0) {
                    Map discardData = new HashMap();
                    for (String key : discardDataObject.keySet()) {
                        JSONArray discardVersions = H5Utils.getJSONArray(discardDataObject, key, null);
                        if (discardVersions != null && discardVersions.size() > 0) {
                            discardData.put(key, H5Utils.toStringArray(discardVersions));
                        }
                    }
                    appRes2.discardData = discardData;
                }
            }
            H5AppUtil.setConfig(result, appRes2);
            return appRes2;
        }
        AppRes appRes3 = new AppRes();
        appRes3.rpcFailDes = H5RpcFailResult.RESULT_CODE_NOT_100;
        H5Log.d(TAG, "resultCode !== 100");
        return appRes3;
    }

    private boolean enableUse() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider == null || !BQCCameraParam.VALUE_NO.equalsIgnoreCase(h5ConfigProvider.getConfig("h5_nbmergerpc"))) {
            return true;
        }
        return false;
    }
}
