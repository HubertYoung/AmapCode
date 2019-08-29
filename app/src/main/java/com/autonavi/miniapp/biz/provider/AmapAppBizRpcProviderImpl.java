package com.autonavi.miniapp.biz.provider;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.nebula.appcenter.model.AppReq;
import com.alipay.mobile.tinyappcustom.provider.InsideAppBizRpcProviderImpl;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.miniapp.plugin.safe.TinyAppSafeUtils;
import com.autonavi.sdk.location.LocationInstrument;

public class AmapAppBizRpcProviderImpl extends InsideAppBizRpcProviderImpl {
    private static String TAG = "H5AppRpc";

    public String rpcCall(String str, AppReq appReq) {
        appReq.diu = NetworkParam.getAdiu();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) LocationParams.PARA_COMMON_DIV, (Object) NetworkParam.getDiv());
        jSONObject.put((String) LocationParams.PARA_COMMON_DIBV, (Object) NetworkParam.getDibv());
        jSONObject.put((String) LocationParams.PARA_COMMON_DIC, (Object) NetworkParam.getDic());
        jSONObject.put((String) LocationParams.PARA_COMMON_DIU, (Object) NetworkParam.getDiu());
        jSONObject.put((String) LocationParams.PARA_COMMON_ADIU, (Object) NetworkParam.getAdiu());
        jSONObject.put((String) LocationParams.PARA_COMMON_CIFA, (Object) NetworkParam.getCifa());
        jSONObject.put((String) "csid", (Object) NetworkParam.getCsid());
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition != null) {
            jSONObject.put((String) LocationParams.PARA_FLP_AUTONAVI_LON, (Object) Double.valueOf(latestPosition.getLongitude()));
            jSONObject.put((String) "lat", (Object) Double.valueOf(latestPosition.getLatitude()));
        }
        try {
            appReq.clientExtra = TinyAppSafeUtils.encrypt(jSONObject.toJSONString());
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error(TAG, (Throwable) e);
        }
        return super.rpcCall(str, appReq);
    }
}
