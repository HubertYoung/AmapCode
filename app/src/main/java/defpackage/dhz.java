package defpackage;

import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.PageBundle;
import com.autonavi.jni.ae.route.route.Route;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import org.json.JSONObject;

/* renamed from: dhz reason: default package */
/* compiled from: IDriveRouteManager */
public interface dhz extends bie {
    a a(dhx dhx);

    a a(dhx dhx, Callback<int[]> callback);

    a a(dhx dhx, CalcRouteScene calcRouteScene, dhy dhy);

    Class a();

    Object a(Route route);

    Object a(JSONObject jSONObject, boolean z);

    String a(Object obj);

    void a(PageBundle pageBundle);

    Class b();

    Class c();

    Class d();
}
