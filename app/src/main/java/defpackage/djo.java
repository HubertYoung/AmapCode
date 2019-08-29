package defpackage;

import android.os.AsyncTask;
import com.autonavi.common.model.GeoPoint;
import java.util.ArrayList;
import java.util.List;

/* renamed from: djo reason: default package */
/* compiled from: NearestPointAsyncTask */
public final class djo extends AsyncTask<List<GeoPoint>, Integer, GeoPoint> {
    public final List<a> a = new ArrayList();
    private GeoPoint b;

    /* renamed from: djo$a */
    /* compiled from: NearestPointAsyncTask */
    public interface a {
        void a(GeoPoint geoPoint);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object doInBackground(Object[] objArr) {
        List[] listArr = (List[]) objArr;
        this.b = (GeoPoint) ((ArrayList) listArr[1]).get(0);
        ArrayList arrayList = (ArrayList) listArr[0];
        GeoPoint geoPoint = this.b;
        if (geoPoint == null) {
            return null;
        }
        if (arrayList == null || arrayList.size() == 0) {
            return geoPoint;
        }
        int size = arrayList.size();
        if (size == 1) {
            return (GeoPoint) arrayList.get(0);
        }
        int a2 = (int) ((((double) cfe.a((GeoPoint) arrayList.get(0), (GeoPoint) arrayList.get(arrayList.size() - 2))) * 1.609344d) / 1000.0d);
        int i = a2 < 50 ? 1 : (a2 / 50) + (a2 % 50 > 25 ? 1 : 0);
        float a3 = cfe.a(geoPoint, (GeoPoint) arrayList.get(0));
        int i2 = 0;
        for (int i3 = 1; i3 < size; i3 += i) {
            float a4 = cfe.a(geoPoint, (GeoPoint) arrayList.get(i3));
            if (a4 < a3) {
                i2 = i3;
                a3 = a4;
            }
        }
        if (i2 == 0) {
            return djn.a((GeoPoint) arrayList.get(0), (GeoPoint) arrayList.get(1), geoPoint);
        }
        if (i2 == size - 1) {
            return djn.a((GeoPoint) arrayList.get(i2), (GeoPoint) arrayList.get(i2 - 1), geoPoint);
        }
        GeoPoint geoPoint2 = (GeoPoint) arrayList.get(i2);
        GeoPoint a5 = djn.a(geoPoint2, (GeoPoint) arrayList.get(i2 - 1), geoPoint);
        GeoPoint a6 = djn.a(geoPoint2, (GeoPoint) arrayList.get(i2 + 1), geoPoint);
        return cfe.a(geoPoint, a5) < cfe.a(geoPoint, a6) ? a5 : a6;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void onPostExecute(Object obj) {
        GeoPoint geoPoint = (GeoPoint) obj;
        super.onPostExecute(geoPoint);
        if (this.a != null) {
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
}
