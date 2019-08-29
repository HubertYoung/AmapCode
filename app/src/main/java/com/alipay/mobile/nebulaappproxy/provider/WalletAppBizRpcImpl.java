package com.alipay.mobile.nebulaappproxy.provider;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.rpc.RpcException;
import com.alipay.mobile.common.rpc.RpcInvokeContext;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.framework.service.ext.SimpleRpcService;
import com.alipay.mobile.nebula.appcenter.H5RpcFailResult;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.baseprovider.H5BaseAppBizRpcProvider;
import com.alipay.mobile.nebula.provider.H5EnvProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.Map;

public class WalletAppBizRpcImpl extends H5BaseAppBizRpcProvider {
    public String rpcCall(String rpc, AppReq req) {
        JSONObject bodyObject = new JSONObject();
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
        JSONObject reqObject = H5Utils.parseObject(H5Utils.toJSONString(req));
        reqObject.remove("stableRpc");
        reqObject.remove("preferLocal");
        bodyObject.put((String) "packInfoReq", (Object) reqObject);
        bodyObject.put((String) "reqType", (Object) Integer.valueOf(2));
        return a("alipay.mappconfig.queryAppInfoGroup", "[" + H5Utils.toJSONString(bodyObject) + "]");
    }

    private static String a(String operationType, String requestData) {
        String gwUrl;
        H5Log.d("H5AppRpc", "operationType:" + operationType + " req:" + requestData);
        Context context = H5Utils.getContext();
        RpcService rpcService = (RpcService) H5Utils.findServiceByInterface(RpcService.class.getName());
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
        try {
            return H5Utils.parseObject(simpleRpcService.executeRPC(operationType, requestData, (Map<String, String>) null)).getString("packJson");
        } catch (Exception exception) {
            if (exception instanceof RpcException) {
                RpcException e = (RpcException) exception;
                H5AppUtil.appCenterLog("H5_APP_REQUEST", new AppInfo(), "^step=fail^err=[" + e.getCode() + "]" + e.getMsg());
                if (e.getCode() == 1002) {
                    return H5RpcFailResult.LIMIT;
                }
            }
            H5Log.d("H5AppRpc", String.valueOf(exception));
            return null;
        }
    }
}
