package defpackage;

import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.page.mappage.TipsView.IGeoCodeChecker;
import com.autonavi.map.mapinterface.IMapRequestManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.tipsview.GeoCodeCheckerImpl$1;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* renamed from: dao reason: default package */
/* compiled from: GeoCodeCheckerImpl */
public class dao implements IGeoCodeChecker {
    /* access modifiers changed from: private */
    public AosRequest a;
    /* access modifiers changed from: private */
    public List<POI> b = new ArrayList();
    /* access modifiers changed from: private */
    public Timer c;
    /* access modifiers changed from: private */
    public a d;
    /* access modifiers changed from: private */
    public int e = 0;
    /* access modifiers changed from: private */
    public String f = null;
    /* access modifiers changed from: private */
    public GeocodePOI g = null;
    /* access modifiers changed from: private */
    public Handler h = new Handler(Looper.getMainLooper());

    /* renamed from: dao$a */
    /* compiled from: GeoCodeCheckerImpl */
    public class a extends TimerTask {
        /* access modifiers changed from: private */
        public Callback<PageBundle> b;

        public a(Callback<PageBundle> callback) {
            this.b = callback;
        }

        public final void run() {
            if (dao.this.h != null) {
                dao.this.h.post(new Runnable() {
                    public final void run() {
                        if (dao.this.b != null && dao.this.b.size() > 0) {
                            dao.this.e = dao.this.e + 1;
                            if (dao.this.e > dao.this.b.size() - 1) {
                                dao.this.e = 0;
                            }
                            dao.a(dao.this, ((POI) dao.this.b.get(dao.this.e)).getName(), a.this.b);
                        }
                    }
                });
            }
        }
    }

    public void request(GeoPoint geoPoint, Callback<PageBundle> callback) {
        this.e = 0;
        this.b.clear();
        if (this.a != null) {
            this.a.cancel();
        }
        if (this.c != null) {
            this.c.cancel();
            this.c = null;
        }
        if (this.d != null) {
            this.d.cancel();
            this.d = null;
        }
        this.f = AMapAppGlobal.getApplication().getString(R.string.select_point_from_map);
        this.g = (GeocodePOI) POIFactory.createPOI(this.f, geoPoint).as(GeocodePOI.class);
        IMapRequestManager iMapRequestManager = (IMapRequestManager) ank.a(IMapRequestManager.class);
        if (iMapRequestManager != null) {
            this.a = iMapRequestManager.getReverseGeocodeResult(geoPoint, new GeoCodeCheckerImpl$1(this, callback, geoPoint));
        }
    }

    public static /* synthetic */ void a(dao dao, String str, Callback callback) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("mainTitle", dao.f);
        String string = AMapAppGlobal.getApplication().getString(R.string.something_nearby, new Object[]{str});
        pageBundle.putString("viceTitle", string);
        dao.g.setAddr(string);
        pageBundle.putObject("POI", dao.g);
        if (callback != null) {
            callback.callback(pageBundle);
        }
    }
}
