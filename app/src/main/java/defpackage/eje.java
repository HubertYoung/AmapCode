package defpackage;

import android.content.Context;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.falcon.base.FalconAosResponseCallback.WorkThread;
import com.autonavi.minimap.route.train.net.RouteTrainResultCallback;
import com.autonavi.minimap.route.train.net.TrainRequestHelper$1;
import com.autonavi.minimap.train.TrainRequestHolder;
import com.autonavi.minimap.train.param.TicketsRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: eje reason: default package */
/* compiled from: TrainRequestHelper */
public final class eje {
    public static AosRequest a(Context context, POI poi, POI poi2, boolean z, String str, axa axa) {
        if (!NetworkReachability.b()) {
            RouteType routeType = RouteType.TRAIN;
            axa.a((Throwable) new Exception(context.getString(R.string.train_plan_network_status_error_not_reach)), false);
            return null;
        }
        RouteTrainResultCallback routeTrainResultCallback = new RouteTrainResultCallback(context, new TrainRequestHelper$1(axa), poi, poi2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-mm", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        String str2 = "00-00";
        if (str.compareTo(new SimpleDateFormat("yyyy-MM-dd").format(date)) == 0) {
            str2 = simpleDateFormat.format(date);
        }
        String str3 = "0";
        if (z) {
            str3 = "1";
        }
        TicketsRequest ticketsRequest = new TicketsRequest();
        if (poi == null || poi.getPoint() == null || poi2 == null || poi2.getPoint() == null) {
            TrainRequestHolder.getInstance().sendTickets(ticketsRequest, routeTrainResultCallback);
            return ticketsRequest;
        }
        ticketsRequest.b = String.valueOf(poi.getPoint().getLongitude());
        ticketsRequest.c = String.valueOf(poi.getPoint().getLatitude());
        ticketsRequest.d = String.valueOf(poi2.getPoint().getLongitude());
        ticketsRequest.e = String.valueOf(poi2.getPoint().getLatitude());
        ticketsRequest.f = poi.getId();
        ticketsRequest.g = poi2.getId();
        ticketsRequest.h = poi.getName();
        ticketsRequest.i = poi2.getName();
        ticketsRequest.j = "0";
        ticketsRequest.k = str;
        ticketsRequest.l = str2;
        ticketsRequest.m = "0";
        ticketsRequest.n = "0";
        ticketsRequest.o = "3";
        ticketsRequest.p = str3;
        TrainRequestHolder.getInstance().sendTickets(ticketsRequest, new dkn(WorkThread.UI), routeTrainResultCallback);
        return ticketsRequest;
    }
}
