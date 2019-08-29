package com.alibaba.baichuan.android.jsbridge.a;

import android.text.TextUtils;
import com.alibaba.baichuan.android.jsbridge.AlibcJsCallbackContext;
import com.alibaba.baichuan.android.jsbridge.AlibcJsResult;
import com.alibaba.baichuan.android.jsbridge.plugin.AlibcApiPlugin;
import com.alibaba.baichuan.android.trade.adapter.mtop.AlibcMtop;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkRequest;
import com.alibaba.baichuan.android.trade.utils.StringUtils;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.tao.remotebusiness.js.MtopJSBridge.MtopJSParam;
import java.util.HashMap;
import java.util.Map;

public class d extends AlibcApiPlugin {
    private NetworkRequest a(Map map) {
        NetworkRequest networkRequest = new NetworkRequest();
        networkRequest.apiName = StringUtils.obj2String(map.get(MtopJSParam.API));
        networkRequest.apiVersion = StringUtils.obj2String(map.get("version"));
        networkRequest.needLogin = StringUtils.obj2Boolean(map.get(MtopJSParam.NEED_LOGIN));
        networkRequest.needWua = StringUtils.obj2Boolean(map.get("needWua"));
        networkRequest.needAuth = StringUtils.obj2Boolean(map.get("needAuth"));
        networkRequest.isPost = StringUtils.obj2Boolean(map.get("isPost"));
        networkRequest.extHeaders = StringUtils.obj2MapString(map.get(MtopJSParam.EXT_HEADERS));
        networkRequest.timeOut = StringUtils.obj2Long(map.get("timeout")).intValue();
        networkRequest.requestType = networkRequest.hashCode();
        JSONObject parseObject = JSONObject.parseObject(StringUtils.obj2String(map.get("params")));
        if (parseObject != null) {
            HashMap hashMap = new HashMap();
            for (String next : parseObject.keySet()) {
                Object obj = parseObject.get(next);
                if (obj != null) {
                    hashMap.put(next, obj.toString());
                }
            }
            networkRequest.paramMap = hashMap;
        }
        return networkRequest;
    }

    public boolean execute(String str, String str2, AlibcJsCallbackContext alibcJsCallbackContext) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str) || alibcJsCallbackContext == null) {
            AlibcJsResult alibcJsResult = new AlibcJsResult("6");
            alibcJsResult.setResultCode("2");
            if (alibcJsCallbackContext != null) {
                alibcJsCallbackContext.error(alibcJsResult);
            }
            return false;
        }
        try {
            NetworkRequest a = a(StringUtils.obj2MapObject(JSON.parseObject(str2)));
            if (a == null || !a.check()) {
                AlibcJsResult alibcJsResult2 = new AlibcJsResult("6");
                alibcJsResult2.setResultCode("2");
                alibcJsCallbackContext.error(alibcJsResult2);
                return false;
            }
            AlibcMtop.getInstance().sendRequest(new e(this, alibcJsCallbackContext), a);
            return true;
        } catch (Exception unused) {
            AlibcLogger.e("AliBCNetWork", "isInstall parse params error, params: ".concat(String.valueOf(str2)));
            return false;
        }
    }
}
