package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.common.Callback;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.businfo.BusInfoRequestHolder;
import com.autonavi.minimap.businfo.param.CleanRequest;
import com.autonavi.minimap.businfo.param.SubscribeRequest;
import com.autonavi.minimap.route.bus.realtimebus.net.parser.AosRealTimeAttentionCleanParser;
import com.autonavi.minimap.route.bus.realtimebus.net.parser.AosRealTimeAttentionCleanParser.RealTimeAttentionCleanCallback;
import com.autonavi.minimap.route.bus.realtimebus.net.parser.AosRealTimeAttentionParser;
import com.autonavi.minimap.route.bus.realtimebus.net.parser.AosRealTimeAttentionParser.RealTimeAttentionCallback;

/* renamed from: dyj reason: default package */
/* compiled from: RealTimeAttentionManage */
public final class dyj {
    public static AosRequest a(String str, String str2, String str3, String str4, String str5, Callback<AosRealTimeAttentionParser> callback) {
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.b = NetworkParam.getDiu();
        subscribeRequest.c = NetworkParam.getTaobaoID();
        subscribeRequest.d = NetworkParam.getDiv();
        subscribeRequest.e = NetworkParam.getDeviceToken(AMapPageUtil.getAppContext());
        subscribeRequest.f = str;
        subscribeRequest.g = str2;
        subscribeRequest.h = str3;
        subscribeRequest.i = str4;
        subscribeRequest.j = str5;
        BusInfoRequestHolder.getInstance().sendSubscribe(subscribeRequest, new RealTimeAttentionCallback(callback));
        return subscribeRequest;
    }

    public static AosRequest a(Callback<AosRealTimeAttentionCleanParser> callback) {
        CleanRequest cleanRequest = new CleanRequest();
        cleanRequest.b = NetworkParam.getDiu();
        BusInfoRequestHolder.getInstance().sendClean(cleanRequest, new RealTimeAttentionCleanCallback(callback));
        return cleanRequest;
    }
}
