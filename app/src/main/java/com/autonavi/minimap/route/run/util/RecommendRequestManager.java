package com.autonavi.minimap.route.run.util;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.datamodel.poi.POIBase;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.falcon.base.FalconAosPrepareResponseCallback;
import com.autonavi.minimap.recommend.RecommendRequestHolder;
import com.autonavi.minimap.recommend.param.RouteRrunRequest;
import com.autonavi.minimap.route.foot.RouteFootResultData;
import com.autonavi.server.aos.serverkey;

public class RecommendRequestManager {
    public static final String a = "RecommendRequestManager";
    public a b;
    private RouteRrunRequest c;
    private RunRecommendCacheCallback d;

    class RunRecommendCacheCallback extends FalconAosPrepareResponseCallback<byte[]> {
        private RunRecommendCacheCallback() {
        }

        /* synthetic */ RunRecommendCacheCallback(RecommendRequestManager recommendRequestManager, byte b) {
            this();
        }

        public final /* synthetic */ void a(Object obj) {
            AMapLog.e(RecommendRequestManager.a, "callback result...:");
            RecommendRequestManager.a(RecommendRequestManager.this, (byte[]) obj);
        }

        public final void a(AosRequest aosRequest, AosResponseException aosResponseException) {
            AMapLog.e(RecommendRequestManager.a, "error...");
            if (RecommendRequestManager.this.b != null) {
                RecommendRequestManager.this.b.a(2);
            }
        }

        public final /* synthetic */ Object a(AosByteResponse aosByteResponse) {
            return (byte[]) aosByteResponse.getResult();
        }
    }

    public interface a {
        void a(int i);

        void a(efb efb);
    }

    public final void a(String str, String str2, String str3) {
        if (this.c != null && !this.c.isCanceled()) {
            this.c.cancel();
        }
        if (this.d == null) {
            this.d = new RunRecommendCacheCallback(this, 0);
        }
        RouteRrunRequest routeRrunRequest = new RouteRrunRequest();
        String amapEncodeV2 = serverkey.amapEncodeV2(eah.a(str, str2, str3));
        if (!TextUtils.isEmpty(amapEncodeV2)) {
            routeRrunRequest.setBody(amapEncodeV2.getBytes());
        }
        RecommendRequestHolder.getInstance().sendRouteRrun(routeRrunRequest, this.d);
        this.c = routeRrunRequest;
    }

    static /* synthetic */ void a(RecommendRequestManager recommendRequestManager, byte[] bArr) {
        RouteFootResultData routeFootResultData = new RouteFootResultData(AMapPageUtil.getAppContext());
        routeFootResultData.setIsRecommend(true);
        routeFootResultData.setFromPOI(new POIBase());
        routeFootResultData.setToPOI(new POIBase());
        ecp ecp = new ecp(routeFootResultData);
        ecp.parser(bArr);
        if (recommendRequestManager.b != null) {
            if (ecp.result && ecp.errorCode == 0) {
                recommendRequestManager.b.a(routeFootResultData.getRecommendLine());
            } else if (ecp.result) {
                a aVar = recommendRequestManager.b;
                ecp.a();
                aVar.a(3);
            } else {
                a aVar2 = recommendRequestManager.b;
                ecp.a();
                aVar2.a(2);
            }
        }
    }
}
