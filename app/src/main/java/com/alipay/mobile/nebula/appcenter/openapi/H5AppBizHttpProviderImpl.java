package com.alipay.mobile.nebula.appcenter.openapi;

import android.text.TextUtils;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest.Builder;
import com.alipay.mobile.nebula.baseprovider.H5BaseAppBizRpcProvider;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class H5AppBizHttpProviderImpl extends H5BaseAppBizRpcProvider {
    private static final String TAG = "H5AppBizHttpProviderImpl";

    public String rpcCall(String url, AppReq data) {
        return rpcCall(data);
    }

    private String rpcCall(AppReq req) {
        String appReqUrl = H5AppOpenApiUrl.ENVIRONMENT_ONLINE;
        if (!TextUtils.isEmpty(req.httpReqUrl)) {
            appReqUrl = req.httpReqUrl;
        }
        String reqString = appReqToParam(req);
        String data = new Builder().url(appReqUrl).body(reqString).build().execute();
        H5Log.d(TAG, "reqString " + reqString + " data " + data);
        return data;
    }

    private String appReqToParam(AppReq appReq) {
        Map map = new HashMap();
        if (!TextUtils.isEmpty(appReq.system)) {
            map.put("system", appReq.system);
        }
        if (!TextUtils.isEmpty(appReq.client)) {
            map.put("client", appReq.client);
        }
        if (!TextUtils.isEmpty(appReq.sdk)) {
            map.put(GlobalConstants.EXCEPTIONTYPE, appReq.sdk);
        }
        if (!TextUtils.isEmpty(appReq.platform)) {
            map.put("platform", appReq.platform);
        }
        if (!TextUtils.isEmpty(appReq.env)) {
            map.put("env", appReq.env);
        }
        if (!TextUtils.isEmpty(appReq.channel)) {
            map.put("channel", appReq.channel);
        }
        if (!TextUtils.isEmpty(appReq.query)) {
            map.put("query", appReq.query);
        }
        if (!TextUtils.isEmpty(appReq.bundleid)) {
            map.put("bundleid", appReq.bundleid);
        }
        if (!TextUtils.isEmpty(appReq.existed)) {
            map.put("existed", appReq.existed);
        }
        if (!TextUtils.isEmpty(appReq.grayRules)) {
            map.put("grayRules", appReq.grayRules);
        }
        if (!TextUtils.isEmpty(appReq.stableRpc)) {
            map.put("stableRpc", appReq.stableRpc);
        } else {
            map.put("stableRpc", "NO");
        }
        StringBuffer sb = new StringBuffer();
        if (!map.isEmpty()) {
            for (Entry e : map.entrySet()) {
                sb.append((String) e.getKey());
                sb.append("=");
                sb.append((String) e.getValue());
                sb.append("&");
            }
            sb = sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
