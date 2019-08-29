package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.autonavi.minimap.bicycle.param.ShareBikeRideStateRequest;
import com.autonavi.minimap.bicycle.param.ShareBikeUserInfoRequest;
import com.autonavi.minimap.order.param.BikeCheckOrderRequest;
import com.autonavi.minimap.order.param.BikeOrderDetailRequest;
import com.autonavi.minimap.route.sharebike.net.parser.BaseResponser;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest;
import com.autonavi.minimap.route.sharebike.net.request.CheckOrderRequest;
import com.autonavi.minimap.route.sharebike.net.request.OrderDetailRequest;
import com.autonavi.minimap.route.sharebike.net.request.RideStateRequest;
import com.autonavi.minimap.route.sharebike.net.request.UserInfoRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: egu reason: default package */
/* compiled from: RequestManager */
public final class egu {

    /* renamed from: egu$a */
    /* compiled from: RequestManager */
    public static class a {
        private static Map<Class, AosRequest> a = new ConcurrentHashMap();

        public static boolean a(BaseRequest baseRequest) {
            synchronized (baseRequest.getClass()) {
                AosRequest aosRequest = a.get(baseRequest.getClass());
                if (aosRequest != null && !aosRequest.isCanceled()) {
                    aosRequest.cancel();
                    a.remove(baseRequest.getClass());
                }
                BaseResponser initResponser = baseRequest.initResponser();
                AosResponseCallback<AosByteResponse> aosResponseCallback = null;
                if (initResponser != null) {
                    aosResponseCallback = initResponser.getCallback();
                }
                baseRequest.send(aosResponseCallback);
                a.put(baseRequest.getClass(), baseRequest.getEntity());
            }
            return true;
        }

        public static boolean b(BaseRequest baseRequest) {
            synchronized (baseRequest.getClass()) {
                a.get(baseRequest.getClass());
                BaseResponser initResponser = baseRequest.initResponser();
                AosResponseCallback<AosByteResponse> aosResponseCallback = null;
                if (initResponser != null) {
                    aosResponseCallback = initResponser.getCallback();
                }
                baseRequest.send(aosResponseCallback);
                a.put(baseRequest.getClass(), baseRequest.getEntity());
            }
            return true;
        }

        public static void a(Class cls) {
            if (cls != null) {
                synchronized (cls) {
                    a.remove(cls);
                }
            }
        }
    }

    public static boolean a(ShareBikeUserInfoRequest shareBikeUserInfoRequest, com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a aVar) {
        return a.a((BaseRequest) new UserInfoRequest(shareBikeUserInfoRequest, aVar));
    }

    public static boolean a(ShareBikeRideStateRequest shareBikeRideStateRequest, com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a aVar) {
        return a.a((BaseRequest) new RideStateRequest(shareBikeRideStateRequest, aVar));
    }

    public static boolean a(BikeCheckOrderRequest bikeCheckOrderRequest, com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a aVar) {
        return a.a((BaseRequest) new CheckOrderRequest(bikeCheckOrderRequest, aVar));
    }

    public static boolean a(BikeOrderDetailRequest bikeOrderDetailRequest, com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a aVar) {
        return a.b(new OrderDetailRequest(bikeOrderDetailRequest, aVar));
    }
}
