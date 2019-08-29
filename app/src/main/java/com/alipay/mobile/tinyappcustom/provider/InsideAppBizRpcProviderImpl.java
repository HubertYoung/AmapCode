package com.alipay.mobile.tinyappcustom.provider;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.commonservice.RpcService;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.RpcInvokeContext;
import com.alipay.inside.mobile.framework.service.ext.SimpleRpcService;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.nebula.appcenter.H5RpcFailResult;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.baseprovider.H5BaseAppBizRpcProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Map;

public class InsideAppBizRpcProviderImpl extends H5BaseAppBizRpcProvider {
    private static final String RPC_OPTYPE_INSIDE = "alipay.mappconfig.queryAppInfoGroup";
    private static final String RPC_PKGCORE_OPTYPE_INSIDE = "alipay.openservice.pkgcore.deploypackage.download";
    private static final String TAG = "H5AppRpc";

    public String rpcCall(String rpc, AppReq req) {
        boolean isDebugSource = "debug".equalsIgnoreCase(req.nbsource);
        if (!isDebugSource) {
            rpc = RPC_PKGCORE_OPTYPE_INSIDE;
        }
        if (H5Utils.isDebuggable(H5Utils.getContext())) {
            String rpcUrl = ReadSettingServerUrl.getInstance().getGWFURL(H5Utils.getContext());
            if (rpcUrl == null || !rpcUrl.contains("mobilegwpre.alipay.com")) {
                req.env = "production";
            } else {
                req.env = "prepub";
            }
        }
        if (TextUtils.isEmpty(req.env)) {
            req.env = "production";
        }
        String reqStr = H5Utils.toJSONString(req);
        if (!isDebugSource) {
            JSONObject reqObject = H5Utils.parseObject(reqStr);
            reqObject.remove("stableRpc");
            reqObject.remove("preferLocal");
            JSONObject bodyObject = new JSONObject();
            bodyObject.put((String) "packInfoReq", (Object) reqObject);
            bodyObject.put((String) "reqType", (Object) Integer.valueOf(2));
            reqStr = H5Utils.toJSONString(bodyObject);
        }
        return rpc(isDebugSource, rpc, "[" + reqStr + "]");
    }

    private String rpc(boolean isDebugSource, String operationType, String requestData) {
        String gwUrl;
        H5Log.d(TAG, "operationType:" + operationType + " req:" + requestData);
        Context context = H5Utils.getContext();
        RpcService rpcService = CommonServiceFactory.getInstance().getRpcService();
        SimpleRpcService simpleRpcService = (SimpleRpcService) rpcService.getRpcProxy(SimpleRpcService.class);
        H5EnvProvider h5EnvProvider = (H5EnvProvider) H5Utils.getProvider(H5EnvProvider.class.getName());
        if (h5EnvProvider == null || TextUtils.isEmpty(h5EnvProvider.getRpcUrl())) {
            gwUrl = ReadSettingServerUrl.getInstance().getGWFURL(context);
        } else {
            gwUrl = h5EnvProvider.getRpcUrl();
        }
        RpcInvokeContext rpcInvokeContext = rpcService.getRpcInvokeContext(simpleRpcService);
        rpcInvokeContext.setGwUrl(gwUrl);
        rpcInvokeContext.setCompress(true);
        if (TextUtils.isEmpty(requestData)) {
            requestData = "[{}]";
        }
        String rpcResult = doRpcCall(simpleRpcService, operationType, requestData);
        if (!TextUtils.isEmpty(rpcResult)) {
            H5Log.d(TAG, "doRpcCall rpcResult " + rpcResult);
        }
        JSONObject resultObject = H5Utils.parseObject(rpcResult);
        if (H5Utils.getInt(resultObject, (String) "resultCode", 0) == 9999) {
            resultObject = H5Utils.parseObject(doRpcCall(simpleRpcService, RPC_OPTYPE_INSIDE, requestData));
        }
        if (resultObject == null) {
            return null;
        }
        H5Log.d(TAG, "result: " + resultObject);
        if (isDebugSource) {
            return resultObject.toString();
        }
        return resultObject.getString("packJson");
    }

    private String doRpcCall(SimpleRpcService simpleRpcService, String operationType, String requestData) {
        boolean z = false;
        try {
            return simpleRpcService.executeRPC(operationType, requestData, (Map<String, String>) null);
        } catch (Exception exception) {
            if (exception instanceof RpcException) {
                RpcException e = (RpcException) exception;
                H5AppUtil.appCenterLog("H5_APP_REQUEST", new AppInfo(), "^step=fail^err=[" + e.getCode() + "]" + e.getMsg());
                if (e.getCode() == 1002) {
                    return H5RpcFailResult.LIMIT;
                }
            }
            H5Log.d(TAG, String.valueOf(exception));
            return z;
        }
    }
}
