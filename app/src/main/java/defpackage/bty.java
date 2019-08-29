package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherAnimationState;
import com.autonavi.ae.gmap.utils.GLMapStaticValue.WeatherType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.jni.ae.gmap.gloverlay.GLGpsOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLNaviOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle;
import com.autonavi.jni.ae.gmap.scenic.Label3rd;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.VMapPage;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.VMapSurface;
import java.util.ArrayList;

/* renamed from: bty reason: default package */
/* compiled from: IMapView */
public interface bty {

    /* renamed from: bty$a */
    /* compiled from: IMapView */
    public interface a {
        void onCallBack(Bitmap bitmap);
    }

    /* renamed from: bty$b */
    /* compiled from: IMapView */
    public interface b {
        void a();

        void b();
    }

    boolean A();

    void B();

    boolean C();

    boolean D();

    void E();

    btm F();

    void G();

    Rect H();

    float I();

    float J();

    void K();

    void L();

    void M();

    void N();

    boolean O();

    void P();

    void Q();

    void R();

    void S();

    void T();

    float U();

    void V();

    void W();

    void X();

    void Y();

    WeatherAnimationState Z();

    float a(int i, int i2, int i3, int i4);

    float a(int i, int i2, int i3, int i4, int i5, int i6);

    alt a(alu alu);

    Resources a();

    Point a(GLGeoPoint gLGeoPoint, Point point);

    GLMapState a(int i);

    void a(float f);

    void a(float f, float f2);

    void a(float f, int i);

    void a(int i, float f, int i2, int i3, int i4, int i5);

    void a(int i, float f, int i2, int i3, int i4, int i5, boolean z);

    void a(int i, int i2, float f, float f2, float f3);

    void a(int i, int i2, int i3);

    void a(int i, int i2, int i3, float f, float f2, float f3, float f4, String str, int i4);

    void a(int i, int i2, int i3, float f, float f2, String str);

    void a(int i, int i2, int i3, float f, float f2, String str, int i4);

    void a(int i, int i2, int i3, int i4, int i5);

    void a(int i, int i2, a aVar);

    void a(int i, Bitmap bitmap, int i2, float f, float f2);

    void a(int i, GLGeoPoint gLGeoPoint, Point point, boolean z);

    void a(int i, boolean z);

    void a(int i, Label3rd[] label3rdArr, boolean z);

    void a(alp alp, alp alp2);

    void a(alv alv);

    void a(amh amh);

    void a(defpackage.ami.a aVar);

    void a(amk amk);

    void a(aml aml);

    void a(amm amm);

    void a(amn amn);

    void a(amo amo);

    void a(amw amw);

    void a(View view);

    void a(View view, LayoutParams layoutParams);

    void a(b bVar);

    @Deprecated
    void a(bty bty);

    void a(GLGeoPoint gLGeoPoint);

    void a(WeatherType weatherType, Bitmap bitmap);

    void a(GLGpsOverlay gLGpsOverlay, boolean z);

    void a(GLNaviOverlay gLNaviOverlay, amf amf, GLGeoPoint gLGeoPoint, int i, int i2, int i3, int i4, GLGeoPoint gLGeoPoint2);

    void a(Runnable runnable);

    void a(Runnable runnable, int i);

    void a(String str);

    void a(String str, int i, String str2);

    void a(boolean z);

    void a(byte[] bArr);

    void a(String[] strArr);

    boolean a(int i, int i2);

    GLOverlayBundle aa();

    void ab();

    void ac();

    int ad();

    int ae();

    void af();

    boolean ag();

    boolean ah();

    boolean ai();

    void aj();

    String ak();

    int al();

    int am();

    long an();

    VMapSurface ao();

    VMapPage ap();

    aky b();

    PointF b(int i, int i2, int i3);

    void b(float f);

    void b(int i);

    void b(int i, float f, int i2, int i3, int i4, int i5);

    void b(int i, int i2);

    void b(int i, int i2, int i3, int i4);

    void b(int i, boolean z);

    void b(amk amk);

    void b(b bVar);

    void b(Runnable runnable);

    void b(boolean z);

    akq c();

    GLGeoPoint c(int i, int i2);

    void c(float f);

    void c(int i);

    void c(int i, boolean z);

    void c(Runnable runnable);

    void c(boolean z);

    boolean c(int i, int i2, int i3, int i4);

    Context d();

    ArrayList<als> d(int i, int i2);

    void d(int i);

    void d(boolean z);

    boolean d(float f);

    float e(int i, int i2);

    bty e();

    void e(float f);

    void e(int i);

    void e(boolean z);

    PointF f(int i, int i2);

    GLMapState f();

    void f(float f);

    void f(int i);

    void f(boolean z);

    void g(float f);

    void g(int i);

    void g(int i, int i2);

    void g(boolean z);

    boolean g();

    int h();

    void h(float f);

    void h(int i);

    void h(boolean z);

    int i();

    void i(float f);

    void i(int i);

    void i(boolean z);

    int j();

    int j(boolean z);

    boolean j(int i);

    int k(boolean z);

    boolean k();

    boolean k(int i);

    int l();

    int l(boolean z);

    void l(int i);

    int m();

    int m(int i);

    void m(boolean z);

    GLGeoPoint n();

    void n(boolean z);

    int o(boolean z);

    GeoPoint o();

    int p();

    int p(boolean z);

    int q();

    void q(boolean z);

    void r(boolean z);

    boolean r();

    void s(boolean z);

    boolean s();

    void t();

    void t(boolean z);

    void u(boolean z);

    boolean u();

    float v();

    int w();

    void x();

    void y();

    void z();
}
