package defpackage;

import android.os.AsyncTask;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.jni.ae.route.route.Route;
import java.util.ArrayList;
import java.util.List;

/* renamed from: djp reason: default package */
/* compiled from: NearestPointRouteAsyncTask */
public final class djp extends AsyncTask<Object, Integer, GeoPoint> {
    public final List<a> a = new ArrayList();
    private GeoPoint b;

    /* renamed from: djp$a */
    /* compiled from: NearestPointRouteAsyncTask */
    public interface a {
        void a(GeoPoint geoPoint);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        GeoPoint geoPoint = (GeoPoint) obj;
        super.onPostExecute(geoPoint);
        if (!(this.a == null || geoPoint == null)) {
            for (a next : this.a) {
                if (next != null) {
                    next.a(geoPoint);
                }
            }
        }
        if (this.a != null) {
            this.a.clear();
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object doInBackground(Object[] objArr) {
        boolean z = true;
        this.b = objArr[1];
        Route route = objArr[0];
        GeoPoint geoPoint = this.b;
        if (!(route == null || this.b == null)) {
            CalcRouteResult calcRouteResult = tn.a().b.get(Long.valueOf(route.getRouteId()));
            if (calcRouteResult == null || calcRouteResult.mResultInfo == null || !calcRouteResult.mResultInfo.containsKey("valid") || !((Boolean) calcRouteResult.mResultInfo.get("valid")).booleanValue()) {
                z = false;
            }
            if (z) {
                com.autonavi.jni.ae.route.model.GeoPoint closestPoint = route.getClosestPoint(this.b.getLongitude(), this.b.getLatitude());
                if (closestPoint != null) {
                    geoPoint = new GeoPoint(closestPoint.x, closestPoint.y);
                }
                return geoPoint;
            }
        }
        return null;
    }
}
