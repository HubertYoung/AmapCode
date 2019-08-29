package com.autonavi.minimap.route.coach.util;

import android.text.TextUtils;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;

public class CoachStartEndPoiTextUpdater$1 implements Callback<ReverseGeocodeResponser> {
    final /* synthetic */ POI a;
    final /* synthetic */ RouteType b;
    final /* synthetic */ POI c;
    final /* synthetic */ dzt d;

    public CoachStartEndPoiTextUpdater$1(dzt dzt, POI poi, RouteType routeType, POI poi2) {
        this.d = dzt;
        this.a = poi;
        this.b = routeType;
        this.c = poi2;
    }

    public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
        final String adCode = reverseGeocodeResponser.getAdCode();
        ReverseGeocodeManager.getReverseGeocodeResult(this.a.getPoint(), new Callback<ReverseGeocodeResponser>() {
            public void callback(ReverseGeocodeResponser reverseGeocodeResponser) {
                String adCode = reverseGeocodeResponser.getAdCode();
                int i = (TextUtils.isEmpty(adCode) || TextUtils.isEmpty(adCode) || !adCode.equals(adCode)) ? 0 : 1;
                if (bno.a) {
                    StringBuilder sb = new StringBuilder("(ReverseGeocodeManager) >> (StartAdCode) ");
                    sb.append(adCode);
                    sb.append(" (endAdCode) ");
                    sb.append(adCode);
                    sb.append(" (status) ");
                    sb.append(i);
                    eao.a((String) "CoachPOI", sb.toString());
                }
                CoachStartEndPoiTextUpdater$1.this.d.a(CoachStartEndPoiTextUpdater$1.this.b, CoachStartEndPoiTextUpdater$1.this.c, CoachStartEndPoiTextUpdater$1.this.a, i, i, false);
            }

            public void error(Throwable th, boolean z) {
                CoachStartEndPoiTextUpdater$1.this.d.a(CoachStartEndPoiTextUpdater$1.this.b, CoachStartEndPoiTextUpdater$1.this.c, CoachStartEndPoiTextUpdater$1.this.a, 0, 1, false);
            }
        });
    }

    public void error(Throwable th, boolean z) {
        this.d.a(this.b, this.c, this.a, 0, 1, false);
    }
}
