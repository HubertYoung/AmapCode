package defpackage;

import android.content.DialogInterface.OnDismissListener;
import android.text.TextUtils;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import java.util.List;

/* renamed from: ta reason: default package */
/* compiled from: RouteCarRequestParam */
public final class ta {
    public CalcRouteScene A;
    public String B;
    public String C;
    public int D;
    public boolean E;
    public POI a;
    public POI b;
    public List<POI> c;
    public String d;
    public String e;
    public String f;
    public OnDismissListener g;
    public boolean h;
    public float i;
    public float j;
    public String k;
    public float l;
    public float m;
    public float n;
    public float o;
    public float p;
    public String q;
    public String r;
    public int s;
    public boolean t;
    public boolean u;
    public boolean v;
    public boolean w;
    public POI x;
    public int y;
    public boolean z;

    public ta(POI poi, POI poi2, List<POI> list, CalcRouteScene calcRouteScene) {
        this.g = null;
        this.h = true;
        this.i = -1.0f;
        this.j = 0.0f;
        this.k = "";
        this.t = false;
        this.u = true;
        this.v = false;
        this.w = true;
        this.z = true;
        this.C = "";
        this.E = false;
        this.a = poi;
        this.b = poi2;
        this.c = list;
        this.d = calcRouteScene.getAosInvoker();
        this.A = calcRouteScene;
    }

    public ta(POI poi, POI poi2, CalcRouteScene calcRouteScene) {
        this(poi, poi2, null, calcRouteScene);
    }

    public ta(dhx dhx) {
        this(dhx.a, dhx.c, dhx.b, CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN);
        this.i = (float) dhx.f;
        this.z = dhx.d;
        this.v = dhx.e;
        this.B = dhx.h;
    }

    public ta(dhx dhx, CalcRouteScene calcRouteScene) {
        this(dhx.a, dhx.c, dhx.b, calcRouteScene);
        this.i = (float) dhx.f;
        this.z = dhx.d;
        this.v = dhx.e;
        this.B = dhx.h;
        if (!TextUtils.isEmpty(dhx.g)) {
            this.C = dhx.g;
        }
        this.g = null;
    }
}
