package com.mpaas.nebula.rpc;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.helper.ReadSettingServerUrl;
import com.alipay.mobile.common.rpc.RpcInvokeContext;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.RpcService;
import com.alipay.mobile.framework.service.common.ThirdpartyRpcService;
import com.alipay.mobile.framework.service.ext.SimpleRpcService;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class H5RpcUtil {
    public static final String DEFAULT_RPC_APPKEYCONFIG = "{\"http(s?):\\\\/\\\\/bkmobilegw(.*)\\\\/mgw\\\\.htm\":\"23387407\"}";
    public static final String RPC_HEADER_SPM_ID = "pagets";
    public static final String TAG = "H5RpcUtil";

    public static String rpcCall(String operationType, String requestData, String gateway, boolean compress, JSONObject joHeaders, String appKey, boolean retryable, H5Page h5Page, int timeout) {
        return rpcCall(operationType, requestData, gateway, compress, joHeaders, appKey, retryable, h5Page, timeout, "json").getResponse();
    }

    public static H5Response rpcCall(String operationType, String requestData, String gateway, boolean compress, JSONObject joHeaders, String appKey, boolean retryable, H5Page h5Page, int timeout, String type) {
        return rpcCall(operationType, requestData, gateway, compress, joHeaders, appKey, retryable, h5Page, timeout, type, false, -1);
    }

    public static H5Response rpcCall(String operationType, String requestData, String gateway, boolean compress, JSONObject joHeaders, String appKey, boolean retryable, H5Page h5Page, int timeout, String type, boolean isHttpGet, int signType) {
        RpcService rpcService;
        String url;
        H5Log.d(TAG, "rpcCall, type " + type);
        boolean isPBFormat = isPbFormat(type);
        Context context = LauncherApplicationAgent.getInstance().getApplicationContext();
        if (!TextUtils.isEmpty(appKey)) {
            H5Log.d(TAG, "rpcCall use ThirdpartyRpcService");
            rpcService = (RpcService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(ThirdpartyRpcService.class.getName());
        } else {
            H5Log.d(TAG, "rpcCall use RpcService");
            rpcService = (RpcService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(RpcService.class.getName());
        }
        SimpleRpcService simpleRpcService = (SimpleRpcService) rpcService.getRpcProxy(SimpleRpcService.class);
        String rpcResult = null;
        if (!TextUtils.isEmpty(gateway)) {
            url = gateway;
        } else {
            url = ReadSettingServerUrl.getInstance().getGWFURL(context);
        }
        H5Log.d(TAG, "set rpc url " + url);
        RpcInvokeContext rpcInvokeContext = rpcService.getRpcInvokeContext(simpleRpcService);
        rpcInvokeContext.setGwUrl(url);
        if (signType >= 0) {
            rpcInvokeContext.setSignType(signType);
        }
        rpcInvokeContext.setCompress(compress);
        if (isHttpGet) {
            rpcInvokeContext.setGetMethod(isHttpGet);
        }
        if (retryable) {
            rpcInvokeContext.setAllowRetry(true);
        }
        if (timeout != 0) {
            try {
                rpcInvokeContext.setTimeout((long) (timeout * 1000));
            } catch (Exception e) {
                H5Log.e((String) TAG, (Throwable) e);
            }
        }
        boolean isAlreadyPut = false;
        Map headers = a(joHeaders);
        if (!(h5Page == null || h5Page.getPageData() == null)) {
            if (h5Page.getParams() != null) {
                String extLogInfoS = H5Utils.getString(h5Page.getParams(), (String) "extLogInfo");
                if (!TextUtils.isEmpty(extLogInfoS)) {
                    JSONObject extLogInfoJ = JSONObject.parseObject(extLogInfoS);
                    if (extLogInfoJ != null) {
                        String miniPageId = extLogInfoJ.getString("miniPageId");
                        if (!TextUtils.isEmpty(miniPageId) && !headers.containsKey("pagets")) {
                            headers.put("pagets", miniPageId);
                            H5Log.debug(TAG, "miniPageId:" + miniPageId);
                            isAlreadyPut = true;
                        }
                    }
                }
            }
            H5LogProvider logProvider = (H5LogProvider) H5Utils.getProvider(H5LogProvider.class.getName());
            if (!isAlreadyPut && logProvider != null) {
                String key = H5Logger.getSpmRpcId(h5Page.getPageData().getPageToken());
                if (!headers.containsKey("pagets") && !TextUtils.isEmpty(key)) {
                    headers.put("pagets", key);
                    H5Log.debug(TAG, "key:" + key);
                }
            }
        }
        if (!TextUtils.isEmpty(appKey)) {
            rpcInvokeContext.setAppKey(appKey);
        }
        if (!headers.isEmpty()) {
            rpcInvokeContext.setRequestHeaders(headers);
        }
        if (isPBFormat) {
            try {
                rpcResult = Base64.encodeToString(simpleRpcService.executeRPC(operationType, Base64.decode(requestData, 0), (Map<String, String>) null), 0);
            } catch (IllegalArgumentException e2) {
                H5Log.e((String) TAG, (Throwable) e2);
            }
        } else {
            if (TextUtils.isEmpty(requestData)) {
                requestData = "[{}]";
            }
            rpcResult = simpleRpcService.executeRPC(operationType, requestData, (Map<String, String>) null);
        }
        Map response = rpcInvokeContext.getResponseHeaders();
        H5Log.d(TAG, "threadId " + Thread.currentThread().getId() + ", isPBFormat: " + isPBFormat + " rpc response " + rpcResult);
        H5Response h5Response = new H5Response(response, rpcResult);
        return h5Response;
    }

    private static Map<String, String> a(JSONObject joHeaders) {
        if (joHeaders == null || joHeaders.isEmpty()) {
            return new HashMap();
        }
        Map headers = new HashMap();
        for (String key : joHeaders.keySet()) {
            String value = H5Utils.getString(joHeaders, key);
            if (!TextUtils.isEmpty(value)) {
                H5Log.d(TAG, "add rpc header " + key + Token.SEPARATOR + value);
                headers.put(key, value);
            }
        }
        return headers;
    }

    public static String getAppKey(String gateway) {
        if (!TextUtils.isEmpty(gateway)) {
            String config = null;
            if (TextUtils.isEmpty(null)) {
                config = DEFAULT_RPC_APPKEYCONFIG;
            }
            H5Log.d(TAG, "getAppKey online config is " + config);
            JSONObject configObj = H5Utils.parseObject(config);
            if (configObj != null) {
                for (String key : configObj.keySet()) {
                    Matcher matcher = Pattern.compile(key).matcher(gateway);
                    if (matcher != null && matcher.matches()) {
                        H5Log.d(TAG, "getAppKey match key " + key);
                        return configObj.getString(key);
                    }
                }
            }
        }
        return "";
    }

    static boolean isPbFormat(String type) {
        return "pb".equals(type);
    }
}
