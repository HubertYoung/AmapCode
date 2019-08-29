package defpackage;

import android.app.Activity;
import android.hardware.SensorEvent;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.drive.route.CalcRouteScene;

/* renamed from: dfm reason: default package */
/* compiled from: IDriveNaviManager */
public interface dfm extends bie {
    void a();

    void a(int i, String str);

    void a(Activity activity);

    void a(SensorEvent sensorEvent);

    void a(POI poi);

    void a(POI poi, POI poi2, String str);

    void a(POI poi, String str, boolean z);

    void a(CalcRouteScene calcRouteScene);

    void a(String str);

    void a(boolean z);

    void b(String str);

    void b(boolean z);

    boolean b();

    String c(String str);

    boolean c();

    void d();

    void e();

    dfl[] f();

    void g();
}
