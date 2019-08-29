package com.autonavi.minimap.route.ugc.net.param;

import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.autonavi.minimap.comment.param.BusBsCreateRequest;
import com.autonavi.server.aos.serverkey;
import org.json.JSONArray;

public class BusNaviReviewParam implements ParamEntity {
    public static BusBsCreateRequest buildParam(ejz ejz) {
        BusBsCreateRequest busBsCreateRequest = new BusBsCreateRequest();
        busBsCreateRequest.b = NetworkParam.getTaobaoID();
        busBsCreateRequest.c = serverkey.getAosChannel();
        ejz.k = busBsCreateRequest.b;
        ejz.l = busBsCreateRequest.c;
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(ejz.a());
        busBsCreateRequest.d = jSONArray.toString();
        return busBsCreateRequest;
    }

    public static BusBsCreateRequest buildParam(String str) {
        BusBsCreateRequest busBsCreateRequest = new BusBsCreateRequest();
        busBsCreateRequest.b = NetworkParam.getTaobaoID();
        busBsCreateRequest.c = serverkey.getAosChannel();
        busBsCreateRequest.d = str;
        return busBsCreateRequest;
    }
}
