package com.alipay.mobile.tinyappcustom.b;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.commonservice.RpcService;
import com.alipay.inside.android.phone.mrpc.core.RpcInvokeContext;
import com.alipay.inside.mobile.framework.service.ext.SimpleRpcService;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.log.H5Logger;
import com.alipay.mobile.nebula.provider.H5LogProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.mpaas.nebula.rpc.H5RpcUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: H5RpcUtil */
public final class c {
    public static String a(String operationType, String requestData, String gateway, boolean compress, JSONObject joHeaders, String appKey) {
        return a(operationType, requestData, gateway, compress, joHeaders, appKey, null, "json").b();
    }

    public static a a(String operationType, String requestData, String gateway, boolean compress, JSONObject joHeaders, String appKey, H5Page h5Page, String type) {
        return a(operationType, requestData, gateway, compress, joHeaders, appKey, h5Page, 0, type);
    }

    public static a a(String operationType, String requestData, String gateway, boolean compress, JSONObject joHeaders, String appKey, H5Page h5Page, int timeout, String type) {
        String url;
        H5Log.d(H5RpcUtil.TAG, "rpcCall, type " + type);
        boolean isPBFormat = b(type);
        Context context = LauncherApplicationAgent.getInstance().getApplicationContext();
        RpcService rpcService = CommonServiceFactory.getInstance().getRpcService();
        Map extParams = new HashMap();
        extParams.put("OpenAuthLogin", "YES");
        extParams.put("needOpenAuth", "NO");
        extParams.put("bizSource", "tinyapp");
        extParams.put("cAuthUUID", UUID.randomUUID().toString());
        SimpleRpcService simpleRpcService = (SimpleRpcService) rpcService.getRpcProxy(SimpleRpcService.class, extParams);
        String rpcResult = null;
        if (!TextUtils.isEmpty(gateway)) {
            url = gateway;
        } else {
            url = ReadSettingServerUrl.getInstance().getGWFURL(context);
        }
        H5Log.d(H5RpcUtil.TAG, "set rpc url " + url);
        RpcInvokeContext rpcInvokeContext = rpcService.getRpcInvokeContext(simpleRpcService);
        rpcInvokeContext.setGwUrl(url);
        rpcInvokeContext.setCompress(compress);
        if (timeout != 0) {
            try {
                rpcInvokeContext.setTimeout((long) (timeout * 1000));
            } catch (Exception e) {
                H5Log.e((String) H5RpcUtil.TAG, (Throwable) e);
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
                            H5Log.debug(H5RpcUtil.TAG, "miniPageId:" + miniPageId);
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
                    H5Log.debug(H5RpcUtil.TAG, "key:" + key);
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
                H5Log.e((String) H5RpcUtil.TAG, (Throwable) e2);
            }
        } else {
            if (TextUtils.isEmpty(requestData)) {
                requestData = "[{}]";
            }
            rpcResult = simpleRpcService.executeRPC(operationType, requestData, (Map<String, String>) null);
        }
        Map<String, String> responseHeaders = rpcInvokeContext.getResponseHeaders();
        H5Log.d(H5RpcUtil.TAG, "threadId " + Thread.currentThread().getId() + ", isPBFormat: " + isPBFormat + " rpc response " + rpcResult);
        a aVar = new a(responseHeaders, rpcResult);
        return aVar;
    }

    private static Map<String, String> a(JSONObject joHeaders) {
        if (joHeaders == null || joHeaders.isEmpty()) {
            return new HashMap();
        }
        Map headers = new HashMap();
        for (String key : joHeaders.keySet()) {
            String value = H5Utils.getString(joHeaders, key);
            if (!TextUtils.isEmpty(value)) {
                H5Log.d(H5RpcUtil.TAG, "add rpc header " + key + Token.SEPARATOR + value);
                headers.put(key, value);
            }
        }
        return headers;
    }

    public static String a(String gateway) {
        if (!TextUtils.isEmpty(gateway)) {
            String config = null;
            if (TextUtils.isEmpty(null)) {
                config = H5RpcUtil.DEFAULT_RPC_APPKEYCONFIG;
            }
            H5Log.d(H5RpcUtil.TAG, "getAppKey online config is " + config);
            JSONObject configObj = H5Utils.parseObject(config);
            if (configObj != null) {
                for (String key : configObj.keySet()) {
                    Matcher matcher = Pattern.compile(key).matcher(gateway);
                    if (matcher != null && matcher.matches()) {
                        H5Log.d(H5RpcUtil.TAG, "getAppKey match key " + key);
                        return configObj.getString(key);
                    }
                }
            }
        }
        return "";
    }

    static boolean b(String type) {
        return "pb".equals(type);
    }
}
