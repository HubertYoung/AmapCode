package com.autonavi.minimap.route.net.combine;

import com.amap.bundle.network.component.mergerequest.MergeRequest;
import com.amap.bundle.network.request.param.builder.AosURLBuilder;
import com.amap.bundle.network.request.param.builder.URLBuilder.Path;
import com.amap.location.sdk.fusion.LocationParams;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@Path(builder = AosURLBuilder.class, host = "search_aos_url", sign = {"longitude", "latitude"}, url = "ws/mapapi/geo/reversecode?")
@KeepName
public class RouteReverseGeocodeRequest extends MergeRequest {
    public RouteReverseGeocodeRequest(boolean z) {
        setUrl(edi.a);
        setPath("ws/mapapi/geo/reversecode?");
        setKey(z ? "reversecode_start" : "reversecode_end");
        addSignParam(LocationParams.PARA_COMMON_DIU);
        addSignParam(LocationParams.PARA_COMMON_DIV);
        addReqParam("crossnum", "1");
        addReqParam("roadnum", "1");
        addReqParam("near", "true");
        addReqParam("desctype", "0");
        addReqParam("poinum", "5");
    }
}
