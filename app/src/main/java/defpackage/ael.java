package defpackage;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;

/* renamed from: ael reason: default package */
/* compiled from: CommonPoiInfoResquest */
public final class ael extends aem {
    public String a;
    public String b;
    public Rect c;
    public int d;
    public String e;
    public String f;
    public String g;
    public int h;
    public String i;
    public aeo j;
    public int k;
    public String l;

    public ael(String str) {
        this.d = 1;
        this.h = 1;
        this.k = 10;
        this.m = 0;
        this.a = str;
        this.m = 0;
    }

    public ael(String str, Rect rect) {
        this.d = 1;
        this.h = 1;
        this.k = 10;
        this.m = 1;
        this.b = str;
        this.c = rect;
        this.m = 1;
    }

    public ael(String str, GeoPoint geoPoint) {
        this.d = 1;
        this.h = 1;
        this.k = 10;
        this.m = 3;
        this.b = str;
        this.o = geoPoint;
        this.m = 3;
    }
}
