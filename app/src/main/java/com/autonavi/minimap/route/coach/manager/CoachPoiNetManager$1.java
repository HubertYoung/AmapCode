package com.autonavi.minimap.route.coach.manager;

import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.common.model.POI;

public class CoachPoiNetManager$1 implements Callback {
    final /* synthetic */ boolean a;
    final /* synthetic */ POI b;
    final /* synthetic */ b c;
    final /* synthetic */ dzk d;

    public CoachPoiNetManager$1(dzk dzk, boolean z, POI poi, b bVar) {
        this.d = dzk;
        this.a = z;
        this.b = poi;
        this.c = bVar;
    }

    public void callback(Object obj) {
        this.d.m.removeMessages(this.a ? dzk.a : dzk.b);
        if (obj instanceof String) {
            ReverseGeocodeResponser reverseGeocodeResponser = new ReverseGeocodeResponser();
            try {
                reverseGeocodeResponser.parser(((String) obj).getBytes());
                if (this.a) {
                    this.d.d = this.b;
                    dzk dzk = this.d;
                    StringBuilder sb = new StringBuilder();
                    sb.append(reverseGeocodeResponser.getCity());
                    sb.append(reverseGeocodeResponser.getDistrict());
                    dzk.i = sb.toString();
                    this.d.k = reverseGeocodeResponser.getAdCode();
                } else {
                    this.d.e = this.b;
                    dzk dzk2 = this.d;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(reverseGeocodeResponser.getCity());
                    sb2.append(reverseGeocodeResponser.getDistrict());
                    dzk2.j = sb2.toString();
                    this.d.l = reverseGeocodeResponser.getAdCode();
                }
                String b2 = this.a ? this.d.i : "";
                String c2 = this.a ? this.d.k : "";
                String d2 = this.a ? "" : this.d.j;
                String e = this.a ? "" : this.d.l;
                if (bno.a) {
                    StringBuilder sb3 = new StringBuilder("(callback) >>>>>  startName: ");
                    sb3.append(b2);
                    sb3.append(" startAdCode: ");
                    sb3.append(c2);
                    sb3.append(" endName: ");
                    sb3.append(d2);
                    sb3.append(" endAdCode: ");
                    sb3.append(e);
                    eao.a((String) "CoachPOI", sb3.toString());
                }
                if (this.c != null) {
                    this.c.a(b2, d2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void error(Throwable th, boolean z) {
        if (this.a) {
            this.d.d = null;
            this.d.i = "";
        } else {
            this.d.e = null;
            this.d.j = "";
        }
        this.d.m.removeMessages(this.a ? dzk.a : dzk.b);
        if (this.c != null) {
            this.c.a();
        }
    }
}
