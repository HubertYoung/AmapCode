package defpackage;

import android.graphics.Rect;
import com.autonavi.common.model.POI;

/* renamed from: bxf reason: default package */
/* compiled from: CommonMapData */
public final class bxf {
    public int a = -1;
    public int b = -1;
    public int c = -1;
    public POI d;
    public Rect e;
    public a f = new a(0);
    public bzx g;

    /* renamed from: bxf$a */
    /* compiled from: CommonMapData */
    public static class a {
        public int a;
        public int b;
        public int c;
        public POI d;
        public bzx e;

        a() {
            this.a = -1;
            this.b = -1;
            this.c = -1;
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public final void a(int i) {
        this.a = i;
        this.f.a = i;
    }

    public final void b(int i) {
        this.b = i;
        this.f.b = i;
    }

    public final void c(int i) {
        this.c = i;
        this.f.c = i;
    }

    public final void a(POI poi) {
        this.d = poi;
        this.f.d = poi;
    }

    public final bxf a() {
        bxf bxf = new bxf();
        bxf.b = this.b;
        bxf.c = this.c;
        bxf.d = this.d;
        bxf.a = this.a;
        bxf.g = this.g;
        a aVar = this.f;
        a aVar2 = new a();
        aVar2.b = aVar.b;
        aVar2.e = aVar.e;
        aVar2.d = aVar.d;
        aVar2.c = aVar.c;
        aVar2.a = aVar.a;
        bxf.f = aVar2;
        return bxf;
    }

    public final void b() {
        this.a = -1;
        this.g = null;
        this.c = -1;
        this.b = -1;
        this.d = null;
    }

    public final void c() {
        this.a = -1;
        this.g = null;
        this.c = -1;
        this.b = -1;
        this.d = null;
        this.f.a = -1;
        this.f.e = null;
        this.f.c = -1;
        this.f.b = -1;
        this.f.d = null;
    }

    public final void d() {
        this.f.b = this.b;
        this.f.c = this.c;
        this.f.d = this.d != null ? this.d.clone() : null;
        this.f.a = this.a;
        this.f.e = this.g;
    }
}
