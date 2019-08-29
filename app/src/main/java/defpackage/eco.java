package defpackage;

import com.amap.bundle.aosservice.request.AosRequest;
import com.autonavi.minimap.route.foot.net.param.WeatherCareRequestor$1;
import com.autonavi.minimap.weather.WeatherRequestHolder;
import com.autonavi.minimap.weather.param.MojiRequest;

/* renamed from: eco reason: default package */
/* compiled from: WeatherCareRequestor */
public final class eco {

    /* renamed from: eco$a */
    /* compiled from: WeatherCareRequestor */
    public interface a {
        void a();

        void a(String str);
    }

    public static AosRequest a(double d, double d2, a aVar) {
        MojiRequest mojiRequest = new MojiRequest();
        mojiRequest.b = String.valueOf(d);
        mojiRequest.c = String.valueOf(d2);
        mojiRequest.d = "3";
        mojiRequest.e = "0";
        mojiRequest.f = "0";
        mojiRequest.g = "0";
        mojiRequest.h = "1";
        mojiRequest.i = "1";
        mojiRequest.j = "1";
        mojiRequest.k = "0";
        WeatherRequestHolder.getInstance().sendMoji(mojiRequest, new WeatherCareRequestor$1(aVar));
        return mojiRequest;
    }
}
