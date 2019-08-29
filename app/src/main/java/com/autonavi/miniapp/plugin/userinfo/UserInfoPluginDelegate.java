package com.autonavi.miniapp.plugin.userinfo;

import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.miniapp.plugin.util.AMapUserInfoUtil;
import com.autonavi.sdk.location.LocationInstrument;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.util.HashMap;

public class UserInfoPluginDelegate {
    public void getUserInfo(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (h5Event != null && h5BridgeContext != null) {
            String diu = NetworkParam.getDiu();
            if (TextUtils.isEmpty(diu)) {
                diu = "";
            }
            String adiu = NetworkParam.getAdiu();
            if (TextUtils.isEmpty(adiu)) {
                adiu = "";
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "success", (Object) Boolean.TRUE);
            jSONObject.put((String) LocationParams.PARA_COMMON_DIU, (Object) diu);
            jSONObject.put((String) LocationParams.PARA_COMMON_ADIU, (Object) adiu);
            jSONObject.put((String) "isLogin", (Object) Boolean.valueOf(AMapUserInfoUtil.getInstance().isLogin()));
            ant userInfo = AMapUserInfoUtil.getInstance().getUserInfo();
            if (userInfo != null) {
                jSONObject.put((String) Oauth2AccessToken.KEY_UID, (Object) userInfo.a);
                jSONObject.put((String) "alipayUID", (Object) TextUtils.isEmpty(userInfo.u) ? userInfo.u : "");
            }
            h5BridgeContext.sendBridgeResult(jSONObject);
        }
    }

    public void getUserFavoritePoi(H5Event h5Event, H5BridgeContext h5BridgeContext) {
        if (h5Event != null && h5BridgeContext != null) {
            FavoritePOI homePoi = AMapUserInfoUtil.getInstance().getHomePoi();
            FavoritePOI companyPoi = AMapUserInfoUtil.getInstance().getCompanyPoi();
            JSONObject jSONObject = new JSONObject();
            if (homePoi != null) {
                jSONObject.put((String) "home", JSON.parse(getPoiJsonStr(homePoi)));
            }
            if (companyPoi != null) {
                jSONObject.put((String) "company", JSON.parse(getPoiJsonStr(companyPoi)));
            }
            h5BridgeContext.sendBridgeResult(jSONObject);
        }
    }

    private String getPoiJsonStr(FavoritePOI favoritePOI) {
        if (favoritePOI == null) {
            return "";
        }
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("longitude", Double.valueOf(favoritePOI.getPoint().getLongitude()));
            hashMap.put("latitude", Double.valueOf(favoritePOI.getPoint().getLatitude()));
            hashMap.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, favoritePOI.getPid());
            hashMap.put("name", favoritePOI.getName());
            hashMap.put("address", favoritePOI.getAddr());
            return JSON.toJSONString(hashMap);
        } catch (Throwable unused) {
            return "";
        }
    }
}
