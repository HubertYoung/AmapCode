package defpackage;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.openlayer.net.callback.Callback;
import com.amap.bundle.openlayer.net.parameter.LayerParameter;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@BundleInterface(awo.class)
/* renamed from: abn reason: default package */
/* compiled from: OpenLayerService */
public class abn extends esi implements awo {
    public static WeakReference<bty> a = null;
    public static boolean b = false;
    private static boolean e = false;

    private static boolean f(int i) {
        return i == 9001 || i == 9003;
    }

    public final boolean d(int i) {
        return i == 9000004 || i == 9000003;
    }

    public static void a(bty bty) {
        a = new WeakReference<>(bty);
    }

    public final void a(boolean z) {
        e = z;
    }

    public final boolean a() {
        return b || e;
    }

    public final void a(String str) {
        bty bty = (bty) a.get();
        if (bty != null && !TextUtils.isEmpty(str)) {
            bty.a(str.getBytes());
        }
    }

    public final void a(int i) {
        bty bty = (bty) a.get();
        if (bty != null) {
            bty.l(i);
        }
    }

    public final void b() {
        bty bty = (bty) a.get();
        if (bty != null) {
            bty.aj();
        }
    }

    public final void b(int i) {
        b();
        bty bty = (bty) a.get();
        if (bty != null) {
            if (f(i)) {
                b = true;
            }
            bty.c(i, true);
        }
    }

    public final void a(List<Integer> list) {
        if (((bty) a.get()) != null && list != null && list.size() > 0) {
            for (Integer intValue : list) {
                b(intValue.intValue());
            }
        }
    }

    public final void c(int i) {
        bty bty = (bty) a.get();
        if (bty != null) {
            if (f(i)) {
                b = false;
            }
            bty.c(i, false);
        }
    }

    public final void c() {
        if (((bty) a.get()) != null) {
            c(9001);
            c(9003);
            List<Integer> e2 = abm.e();
            if (((bty) a.get()) != null && e2.size() > 0) {
                for (Integer intValue : e2) {
                    c(intValue.intValue());
                }
            }
        }
    }

    public final void b(boolean z) {
        if (z != d()) {
            bin.a.d((String) "103", z ? 1 : 0);
            if (z) {
                e();
            } else {
                f();
            }
            ToastHelper.showToast(AMapAppGlobal.getApplication().getResources().getString(d() ? R.string.map_layer_traffic_layer_is_opened : R.string.map_layer_traffic_layer_is_closed));
        }
    }

    public final void e() {
        a(awo.d);
        b(9003);
    }

    public final void f() {
        a(9003);
        c(9003);
    }

    public final void a(int i, boolean z) {
        abm.a(i, z);
    }

    public final boolean e(int i) {
        return abm.b(i);
    }

    public final long h() {
        return abm.d();
    }

    public final ArrayList<LayerItem> i() {
        return abm.a();
    }

    public final ArrayList<Integer> j() {
        return abm.b();
    }

    public final void a(a aVar) {
        abl.a().a.add(aVar);
    }

    public final void a(b bVar) {
        abl.a().b.add(bVar);
    }

    public final boolean d() {
        return bin.a.o("103");
    }

    public final void g() {
        String str = "";
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            GLGeoPoint n = mapView.n();
            if (n != null) {
                str = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(n).getAdCode());
            }
        }
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        String str2 = str;
        AMapLog.d("OpenLayerService", "Requester getAdCode:".concat(String.valueOf(str2)));
        LayerParameter layerParameter = new LayerParameter(str2, NetworkParam.getDiv(), NetworkParam.getDic(), 0, abm.c());
        AosGetRequest a2 = aax.a(layerParameter);
        yq.a();
        yq.a((AosRequest) a2, (AosResponseCallback<T>) new Callback<T>());
    }
}
