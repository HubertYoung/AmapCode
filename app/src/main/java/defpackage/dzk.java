package defpackage;

import android.os.Handler;
import android.os.Message;
import com.amap.bundle.network.util.NetworkReachability;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.route.coach.manager.CoachPoiNetManager$1;
import com.autonavi.minimap.route.net.combine.RouteReverseGeocodeRequest;
import java.lang.ref.WeakReference;

/* renamed from: dzk reason: default package */
/* compiled from: CoachPoiNetManager */
public final class dzk {
    public static int a = 0;
    public static int b = 1;
    public static int c = 2;
    private static int f = 2000;
    public POI d;
    public POI e;
    private boolean g = false;
    private boolean h = false;
    /* access modifiers changed from: private */
    public String i;
    /* access modifiers changed from: private */
    public String j;
    /* access modifiers changed from: private */
    public String k;
    /* access modifiers changed from: private */
    public String l;
    /* access modifiers changed from: private */
    public a m = new a(this);

    /* renamed from: dzk$a */
    /* compiled from: CoachPoiNetManager */
    public static class a extends Handler {
        WeakReference<b> a;
        WeakReference<b> b;
        private WeakReference<dzk> c;

        public a(dzk dzk) {
            this.c = new WeakReference<>(dzk);
        }

        public final void handleMessage(Message message) {
            if (message.what == dzk.a) {
                if (this.a != null) {
                    b bVar = (b) this.a.get();
                    if (bVar != null) {
                        bVar.a();
                    }
                }
            } else if (message.what != dzk.b) {
                if (this.a != null) {
                    b bVar2 = (b) this.a.get();
                    if (bVar2 != null) {
                        bVar2.a();
                    }
                }
                if (this.b != null) {
                    b bVar3 = (b) this.b.get();
                    if (bVar3 != null) {
                        bVar3.a();
                    }
                }
            } else if (this.b != null) {
                b bVar4 = (b) this.b.get();
                if (bVar4 != null) {
                    bVar4.a();
                }
            }
        }
    }

    /* renamed from: dzk$b */
    /* compiled from: CoachPoiNetManager */
    public interface b {
        void a();

        void a(int i);

        void a(String str, String str2);
    }

    public final void a() {
        this.i = "";
        this.j = "";
        this.d = null;
        this.e = null;
        this.m.removeMessages(a);
        this.m.removeMessages(b);
    }

    public final zz a(POI poi, b bVar, boolean z) {
        String str;
        if (poi == null) {
            if (z) {
                this.d = null;
                this.i = "";
            } else {
                this.e = null;
                this.j = "";
            }
            bVar.a();
            return null;
        }
        if (bnx.a(poi, z ? this.d : this.e)) {
            if (z) {
                this.d = poi;
            } else {
                this.e = poi;
            }
            String str2 = z ? this.i : "";
            if (z) {
                str = "";
            } else {
                str = this.j;
            }
            bVar.a(str2, str);
            return null;
        }
        if (bnx.a(poi, z ? this.e : this.d)) {
            POI poi2 = this.d;
            this.d = this.e;
            this.e = poi2;
            String str3 = this.i;
            this.i = this.j;
            this.j = str3;
            bVar.a(this.i, this.j);
            return null;
        } else if (!NetworkReachability.b()) {
            if (z) {
                this.d = poi;
                this.i = "";
            } else {
                this.e = poi;
                this.j = "";
            }
            bVar.a();
            return null;
        } else {
            bVar.a(z ? a : b);
            RouteReverseGeocodeRequest routeReverseGeocodeRequest = new RouteReverseGeocodeRequest(z);
            GeoPoint point = poi.getPoint();
            if (point != null) {
                DPoint a2 = cfg.a((long) point.x, (long) point.y);
                routeReverseGeocodeRequest.addReqParam("latitude", Double.valueOf(a2.y).toString());
                routeReverseGeocodeRequest.addReqParam("longitude", Double.valueOf(a2.x).toString());
            }
            if (z) {
                this.m.a = new WeakReference<>(bVar);
            } else {
                this.m.b = new WeakReference<>(bVar);
            }
            this.m.sendEmptyMessageDelayed(z ? a : b, (long) f);
            zz zzVar = new zz();
            zzVar.a(routeReverseGeocodeRequest, new CoachPoiNetManager$1(this, z, poi, bVar));
            zzVar.a();
            return zzVar;
        }
    }
}
