package com.autonavi.miniapp.plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.autonavi.miniapp.biz.network.response.AbsResponse;
import java.util.List;
import mtopsdk.mtop.domain.MtopResponse;

public abstract class BasePlugin extends H5SimplePlugin {
    private static final int ERROR_CUSTOM = 3;
    private static final int ERROR_MTOP_FAIL = 1;
    private static final int ERROR_USER_NOT_LOGIN = 2;
    public static String SCOPE_PAGE = "page";
    public static String SCOPE_SERVICE = "service";
    private static String SEP = "|";

    public abstract String getEvents();

    public abstract String getScope();

    @Deprecated
    public void callMtopBizJsError(String str, H5BridgeContext h5BridgeContext) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "success", (Object) Boolean.FALSE);
        jSONObject.put((String) "resultCode", (Object) "-1");
        jSONObject.put((String) "resultMsg", (Object) str);
        jSONObject.put((String) "error", (Object) Integer.valueOf(3));
        jSONObject.put((String) "errorMessage", (Object) Integer.valueOf(3));
        h5BridgeContext.sendBridgeResult(jSONObject);
    }

    @Deprecated
    public void callMtopSysJsError(MtopResponse mtopResponse, H5BridgeContext h5BridgeContext) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "success", (Object) Boolean.FALSE);
        jSONObject.put((String) "resultCode", (Object) mtopResponse.getRetCode());
        jSONObject.put((String) "resultMsg", (Object) mtopResponse.getRetMsg());
        jSONObject.put((String) "error", (Object) Integer.valueOf(1));
        jSONObject.put((String) "errorMessage", (Object) mtopResponse.getRetMsg());
        h5BridgeContext.sendBridgeResult(jSONObject);
    }

    @Deprecated
    public void callMtopBizJsError(AbsResponse absResponse, MtopResponse mtopResponse, H5BridgeContext h5BridgeContext) {
        if (!(absResponse == null || absResponse.getData().result || absResponse.getData().message == null)) {
            mtopResponse.setRetMsg(absResponse.getData().message);
        }
        callMtopSysJsError(mtopResponse, h5BridgeContext);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void callNotLoginError(H5BridgeContext h5BridgeContext) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "success", (Object) Boolean.FALSE);
        jSONObject.put((String) "resultCode", (Object) "USER_NOT_LOGIN");
        jSONObject.put((String) "resultMsg", (Object) "登录后方可使用此功能");
        jSONObject.put((String) "error", (Object) Integer.valueOf(2));
        jSONObject.put((String) "errorMessage", (Object) "登录后方可使用此功能");
        h5BridgeContext.sendBridgeResult(jSONObject);
    }

    /* access modifiers changed from: protected */
    public String getEvents(List<String> list) {
        if (list == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (!TextUtils.isEmpty(list.get(i))) {
                stringBuffer.append(list.get(i));
                if (i < list.size() - 1) {
                    stringBuffer.append(SEP);
                }
            }
        }
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public final JSONObject buildResult(Object obj, Boolean bool, String str) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "data", obj);
        jSONObject.put((String) "success", (Object) bool);
        jSONObject.put((String) "resultMsg", (Object) str);
        return jSONObject;
    }

    /* access modifiers changed from: protected */
    public JSONObject buildCommonSuccess() {
        return buildCommonSuccess(null);
    }

    public JSONObject buildCommonSuccess(JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        jSONObject.put((String) "success", (Object) Integer.valueOf(1));
        return jSONObject;
    }

    public JSONObject buildCommonError(Error error, String str) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "error", (Object) Integer.valueOf(error.ordinal()));
        jSONObject.put((String) "errorMessage", (Object) str);
        jSONObject.put((String) "success", (Object) Integer.valueOf(0));
        return jSONObject;
    }

    public JSONObject buildBusinessError(int i, String str) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "error", (Object) Integer.valueOf(i));
        jSONObject.put((String) "errorMessage", (Object) str);
        jSONObject.put((String) "success", (Object) Integer.valueOf(0));
        return jSONObject;
    }
}
