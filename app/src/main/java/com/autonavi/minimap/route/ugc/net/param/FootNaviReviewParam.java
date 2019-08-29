package com.autonavi.minimap.route.ugc.net.param;

import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.network.request.param.builder.ParamEntity;
import com.autonavi.minimap.comment.param.WalkCreateRequest;
import com.autonavi.server.aos.serverkey;
import org.json.JSONArray;

public class FootNaviReviewParam implements ParamEntity {
    public static WalkCreateRequest buildParam(eka eka) {
        WalkCreateRequest walkCreateRequest = new WalkCreateRequest();
        walkCreateRequest.b = NetworkParam.getTaobaoID();
        walkCreateRequest.c = serverkey.getAosChannel();
        eka.h = walkCreateRequest.b;
        eka.i = walkCreateRequest.c;
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(eka.a());
        walkCreateRequest.d = jSONArray.toString();
        return walkCreateRequest;
    }

    public static WalkCreateRequest buildParam(String str) {
        WalkCreateRequest walkCreateRequest = new WalkCreateRequest();
        walkCreateRequest.b = NetworkParam.getTaobaoID();
        walkCreateRequest.c = serverkey.getAosChannel();
        walkCreateRequest.d = str;
        return walkCreateRequest;
    }
}
