package com.alipay.mobile.nebula.provider;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.model.H5BizModel;

public interface H5BizProvider {
    public static final String AUTH_CODE_OPEN_APP_ID = "getAuthCodeOpenAppId";
    public static final String AUTH_CODE_RESULT = "authCodeResult";
    public static final int BIZ_SERVICE_CALL = 200011964;
    public static final int BIZ_SERVICE_CALL_RESULT = 200011965;

    void addBizCallback(String str, H5BizModel h5BizModel);

    void cancelBizTimeoutCheck(String str);

    void saveBizResult(String str, JSONObject jSONObject);

    void triggerBizCallback(String str);
}
