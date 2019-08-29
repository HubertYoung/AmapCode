package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.bundle.activities.widget.ActivityPreloadView;
import com.autonavi.minimap.bundle.activities.widget.ActivityPreloadView.c;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cua reason: default package */
/* compiled from: ActivityPreloadManager */
public final class cua {
    public ActivityPreloadView a;
    public boolean b;
    private Context c;
    private WeakReference<bty> d;
    private Handler e;
    private Hashtable<ImageView, String> f;
    private List<c> g;
    private ctp h;
    private List<a> i;
    private Runnable j;

    /* renamed from: cua$a */
    /* compiled from: ActivityPreloadManager */
    static class a implements bkf {
        private WeakReference<ImageView> a;
        private WeakReference<cua> b;

        public final void onPrepareLoad(Drawable drawable) {
        }

        public a(cua cua, ImageView imageView) {
            this.a = new WeakReference<>(imageView);
            this.b = new WeakReference<>(cua);
        }

        public final void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
            ImageView imageView = (ImageView) this.a.get();
            cua cua = (cua) this.b.get();
            if (imageView != null && cua != null) {
                cua.a(cua, imageView);
            }
        }

        public final void onBitmapFailed(Drawable drawable) {
            ImageView imageView = (ImageView) this.a.get();
            cua cua = (cua) this.b.get();
            if (imageView != null && cua != null) {
                cua.a(cua, imageView);
            }
        }
    }

    public final void a(JSONObject jSONObject) {
        if (jSONObject == null) {
            try {
                jSONObject = new JSONObject("{\"code\" : \"0\"}");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        AMapLog.i("ActivityPreload", "deliverPoiRange = ".concat(String.valueOf(jSONObject)));
        if (this.h != null) {
            ctp ctp = this.h;
            if (jSONObject != null) {
                if (ctp.a != null) {
                    ctp.a(ctp.a, jSONObject);
                    ctp.a = null;
                    return;
                }
                ctp.b = jSONObject;
            }
        }
    }

    public final void a(List<ctz> list) {
        int i2;
        GeoPoint geoPoint;
        List<ctz> list2 = list;
        if (this.a != null) {
            bty bty = (bty) this.d.get();
            if (list2 == null || list.isEmpty() || bty == null) {
                this.e.removeCallbacks(this.j);
                this.a.setNails(null);
                return;
            }
            ArrayList arrayList = new ArrayList();
            this.f.clear();
            int i3 = 0;
            while (i3 < list.size()) {
                ctz ctz = list2.get(i3);
                if (ctz == null || TextUtils.isEmpty(ctz.e)) {
                    i2 = i3;
                } else {
                    try {
                        geoPoint = new GeoPoint(Double.parseDouble(ctz.d), Double.parseDouble(ctz.c));
                    } catch (Throwable unused) {
                        geoPoint = null;
                    }
                    if (geoPoint != null) {
                        ImageView imageView = new ImageView(this.c);
                        imageView.setScaleType(ScaleType.FIT_CENTER);
                        PointF f2 = bty.f(geoPoint.x, geoPoint.y);
                        i2 = i3;
                        c cVar = new c(imageView, (int) (((double) f2.x) + 0.5d), (int) (((double) f2.y) + 0.5d));
                        cVar.e = ctz.e;
                        this.f.put(imageView, ctz.e);
                        arrayList.add(cVar);
                    } else {
                        return;
                    }
                }
                i3 = i2 + 1;
            }
            if (arrayList.isEmpty()) {
                this.e.removeCallbacks(this.j);
                this.a.setNails(null);
                return;
            }
            this.g = arrayList;
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                c cVar2 = (c) arrayList.get(i4);
                a aVar = new a(this, cVar2.b);
                this.i.add(aVar);
                ko.a(cVar2.b, cVar2.e, null, 0, aVar);
            }
        }
    }

    public static /* synthetic */ void a(cua cua) {
        if (!cua.b) {
            cua.a((List<ctz>) null);
            cua.a((JSONObject) null);
        }
    }

    static /* synthetic */ void a(cua cua, ImageView imageView) {
        if (!cua.b) {
            cua.f.remove(imageView);
            if (cua.f.isEmpty()) {
                AMapLog.i("ActivityPreload", "onImageLoadComplete");
                cua.e.removeCallbacks(cua.j);
                if (cua.a != null && cua.g != null) {
                    cua.a.setNails(cua.g);
                    cua.g = null;
                }
            }
        }
    }
}
