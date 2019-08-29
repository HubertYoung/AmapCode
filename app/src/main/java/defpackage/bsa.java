package defpackage;

import com.autonavi.common.model.GeoPoint;
import java.util.ArrayList;

/* renamed from: bsa reason: default package */
/* compiled from: TrafficEventAffectModel */
public final class bsa {
    public a a;
    public b b;

    /* renamed from: bsa$a */
    /* compiled from: TrafficEventAffectModel */
    public static class a {
        public boolean a;
        public ArrayList<ArrayList<GeoPoint>> b;
        public int c;
        public int d;
        public boolean e;

        public a(ArrayList<ArrayList<GeoPoint>> arrayList) {
            this.a = true;
            this.c = -217993;
            this.d = 871984657;
            this.e = true;
            this.b = arrayList;
        }

        public a(ArrayList<ArrayList<GeoPoint>> arrayList, byte b2) {
            this.a = true;
            this.c = -217993;
            this.d = 871984657;
            this.e = true;
            this.a = false;
            this.b = arrayList;
            this.c = -16776961;
            this.d = 1879113472;
            this.e = false;
        }

        public final boolean a() {
            return this.b != null && this.b.size() > 0;
        }
    }

    /* renamed from: bsa$b */
    /* compiled from: TrafficEventAffectModel */
    public static class b {
        public ArrayList<ArrayList<GeoPoint>> a;
        public boolean b;
        public int c;
        public int d;
        private boolean e;

        public b(ArrayList<ArrayList<GeoPoint>> arrayList) {
            this(arrayList, 8, -217993);
            this.e = true;
        }

        public b(ArrayList<ArrayList<GeoPoint>> arrayList, int i, int i2) {
            this.b = false;
            this.a = arrayList;
            this.b = false;
            this.c = i2;
            this.d = i <= 0 ? 8 : i;
            this.e = false;
        }

        public final boolean a() {
            return this.a != null && this.a.size() > 0;
        }
    }
}
