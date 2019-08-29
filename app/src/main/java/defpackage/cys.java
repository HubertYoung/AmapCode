package defpackage;

import android.util.DisplayMetrics;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.bundle.locationselect.page.SelectFixPoiFromMapPage;
import com.autonavi.minimap.search.model.SearchConst;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.sync.beans.GirfFavoritePoint;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* renamed from: cys reason: default package */
/* compiled from: SelectFixPoiFromMapPresenter */
public final class cys extends AbstractBaseMapPagePresenter<SelectFixPoiFromMapPage> {
    public POI a;
    public GeoPoint b;
    public String c = SearchConst.b;
    public boolean d;
    public boolean e = false;
    private POI f = null;
    private POI g;
    private POI h;
    private SelectPoiFromMapBean i;
    private boolean j;
    private dta k;
    private List<BaseOverlayDelegate> l;
    private List<LineOverlayItem> m;
    private int n = -1;
    private GLGeoPoint o;

    public cys(SelectFixPoiFromMapPage selectFixPoiFromMapPage) {
        super(selectFixPoiFromMapPage);
    }

    public final void onPageCreated() {
        ArrayList arrayList;
        super.onPageCreated();
        PageBundle arguments = ((SelectFixPoiFromMapPage) this.mPage).getArguments();
        if (arguments.containsKey("SelectPoiFromMapBean")) {
            this.i = (SelectPoiFromMapBean) arguments.getObject("SelectPoiFromMapBean");
            if (this.i != null) {
                int lineOverlayItemStyle = this.i.getLineOverlayItemStyle();
                if (arguments.containsKey("overlay_style")) {
                    this.k = (dta) arguments.getObject("overlay_style");
                } else if (lineOverlayItemStyle == 1) {
                    this.k = dta.a;
                }
                if (this.k != null) {
                    this.l = this.k.a((bid) this.mPage, ((SelectFixPoiFromMapPage) this.mPage).getMapManager().getMapView());
                }
                if (this.l != null && this.l.size() > 0) {
                    for (BaseOverlayDelegate addOverlay : this.l) {
                        ((SelectFixPoiFromMapPage) this.mPage).addOverlay(addOverlay, false);
                    }
                }
                this.f = this.i.getOldPOI();
                if (this.f != null) {
                    this.b = this.i.getOldPOI().getPoint();
                }
                this.j = this.i.isHideOldPoi();
                GeoPoint[] points = this.i.getPoints();
                if (points == null || points.length <= 0 || this.k == null || !this.k.b()) {
                    arrayList = null;
                } else {
                    arrayList = new ArrayList();
                    List<LineOverlayItem> a2 = this.k.a(((SelectFixPoiFromMapPage) this.mPage).getContext(), points);
                    if (a2 != null && a2.size() > 0) {
                        arrayList.addAll(a2);
                    }
                }
                this.m = arrayList;
                if (this.m != null && this.m.size() > 0) {
                    SelectFixPoiFromMapPage selectFixPoiFromMapPage = (SelectFixPoiFromMapPage) this.mPage;
                    selectFixPoiFromMapPage.addOverlay(selectFixPoiFromMapPage.c);
                }
                this.g = this.i.getFromPOI();
                this.h = this.i.getToPOI();
                if (this.i.getLevel() > 0) {
                    ((SelectFixPoiFromMapPage) this.mPage).getMapView().e(this.i.getLevel());
                }
            }
        }
        if (arguments.containsKey("select_poi_from_map_argments_custom_icon_key")) {
            this.n = arguments.getInt("select_poi_from_map_argments_custom_icon_key", -1);
        }
    }

    public final void onStart() {
        super.onStart();
        SelectFixPoiFromMapPage selectFixPoiFromMapPage = (SelectFixPoiFromMapPage) this.mPage;
        selectFixPoiFromMapPage.getContentView().postDelayed(new Runnable(this.n == -1 ? R.drawable.b_poi : this.n) {
            final /* synthetic */ int a;

            {
                this.a = r2;
            }

            public final void run() {
                if (SelectFixPoiFromMapPage.this.g != null) {
                    SelectFixPoiFromMapPage.this.g.setImageResource(this.a);
                }
            }
        }, 50);
        if (this.b != null) {
            ((SelectFixPoiFromMapPage) this.mPage).a(this.b.x, this.b.y);
        } else if (((SelectFixPoiFromMapPage) this.mPage).getMapView() != null) {
            if (this.f != null) {
                ((SelectFixPoiFromMapPage) this.mPage).a(this.f.getPoint().x, this.f.getPoint().y);
            }
            SelectFixPoiFromMapPage selectFixPoiFromMapPage2 = (SelectFixPoiFromMapPage) this.mPage;
            this.b = GeoPoint.glGeoPoint2GeoPoint(selectFixPoiFromMapPage2.b.c(selectFixPoiFromMapPage2.b.al() / 2, selectFixPoiFromMapPage2.b.am() / 2));
        }
        ((SelectFixPoiFromMapPage) this.mPage).b();
        if (this.f != null && !this.j) {
            ((SelectFixPoiFromMapPage) this.mPage).a(this.f, R.drawable.bubble_wrongcheck);
        }
        if (this.b != null) {
            ((SelectFixPoiFromMapPage) this.mPage).a(this.b);
        }
        if (this.b != null) {
            a(this.b);
        }
        if (this.m != null && this.m.size() > 0) {
            for (LineOverlayItem next : this.m) {
                SelectFixPoiFromMapPage selectFixPoiFromMapPage3 = (SelectFixPoiFromMapPage) this.mPage;
                if (selectFixPoiFromMapPage3.c != null) {
                    selectFixPoiFromMapPage3.c.addItem(next);
                }
            }
        }
        if (!(this.g == null || this.h == null)) {
            ((SelectFixPoiFromMapPage) this.mPage).b();
            if (this.k != null && this.k.a()) {
                ((SelectFixPoiFromMapPage) this.mPage).a(this.g, R.drawable.bubble_start);
                ((SelectFixPoiFromMapPage) this.mPage).a(this.h, R.drawable.bubble_end);
            }
        }
        ((SelectFixPoiFromMapPage) this.mPage).resetMapSkinState();
    }

    public final void onMapSurfaceChanged(int i2, int i3) {
        super.onMapSurfaceChanged(i2, i3);
        ((SelectFixPoiFromMapPage) this.mPage).c();
    }

    public final boolean onMapLevelChange(boolean z) {
        super.onMapLevelChange(z);
        if (this.k != null) {
            this.k.b((bid) this.mPage, ((SelectFixPoiFromMapPage) this.mPage).getMapManager().getMapView());
        }
        return false;
    }

    public final ON_BACK_TYPE onBackPressed() {
        a();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void a() {
        this.a = null;
        ((SelectFixPoiFromMapPage) this.mPage).a(ResultType.CANCEL, (PageBundle) null);
    }

    public final boolean onMapMotionStop() {
        SelectFixPoiFromMapPage selectFixPoiFromMapPage = (SelectFixPoiFromMapPage) this.mPage;
        GLGeoPoint n2 = selectFixPoiFromMapPage.b != null ? selectFixPoiFromMapPage.b.n() : null;
        if (this.o != null && this.o.equals(n2)) {
            return true;
        }
        this.o = n2;
        long j2 = 0;
        if (this.e) {
            j2 = 500;
        }
        SelectFixPoiFromMapPage selectFixPoiFromMapPage2 = (SelectFixPoiFromMapPage) this.mPage;
        selectFixPoiFromMapPage2.getContentView().postDelayed(new Runnable() {
            public final void run() {
                ((cys) SelectFixPoiFromMapPage.this.mPresenter).a(SelectFixPoiFromMapPage.this.getMapManager().getMapView().o());
                if (SelectFixPoiFromMapPage.this.g != null && SelectFixPoiFromMapPage.this.h != null) {
                    SelectFixPoiFromMapPage.this.g.startAnimation(SelectFixPoiFromMapPage.this.h);
                    SelectFixPoiFromMapPage.this.h.startNow();
                }
            }
        }, j2);
        this.e = false;
        return true;
    }

    public final void onDestroy() {
        this.n = -1;
        SelectFixPoiFromMapPage selectFixPoiFromMapPage = (SelectFixPoiFromMapPage) this.mPage;
        if (selectFixPoiFromMapPage.a != null) {
            selectFixPoiFromMapPage.a.cancelNetWork();
            DisplayMetrics displayMetrics = AMapAppGlobal.getApplication().getResources().getDisplayMetrics();
            if (selectFixPoiFromMapPage.b != null) {
                selectFixPoiFromMapPage.getMapView().b(displayMetrics.widthPixels / 2, displayMetrics.heightPixels / 2);
            }
        }
        if (selectFixPoiFromMapPage.a != null) {
            selectFixPoiFromMapPage.a.registerCallback(null);
        }
        selectFixPoiFromMapPage.getSuspendWidgetHelper().a((a) null);
        if (selectFixPoiFromMapPage.d()) {
            bim.aa().a((String) UploadConstants.STATUS_TASK_BY_PUSH, selectFixPoiFromMapPage.d ? 1 : 0);
            cdd.n().h();
        }
        if (selectFixPoiFromMapPage.d()) {
            selectFixPoiFromMapPage.requestScreenOrientation(selectFixPoiFromMapPage.e);
        }
        if (this.l != null) {
            for (BaseOverlayDelegate next : this.l) {
                next.clear();
                ((SelectFixPoiFromMapPage) this.mPage).removeOverlay(next);
            }
        }
        super.onDestroy();
    }

    public final void a(GeoPoint geoPoint) {
        this.c = SearchConst.b;
        this.b = geoPoint;
        ((SelectFixPoiFromMapPage) this.mPage).a(geoPoint);
    }

    public final boolean b() {
        if (((SelectFixPoiFromMapPage) this.mPage).getRequestCode() == 107) {
            ajw ajw = (ajw) ank.a(ajw.class);
            if (ajw != null) {
                PageBundle pageBundle = new PageBundle();
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("poiName", this.a.getName());
                    jSONObject.put(DictionaryKeys.CTRLXY_X, this.a.getPoint().x);
                    jSONObject.put(DictionaryKeys.CTRLXY_Y, this.a.getPoint().y);
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.a.getPoint().getLongitude());
                    sb.append(",");
                    sb.append(this.a.getPoint().getLatitude());
                    jSONObject.put("points", sb.toString());
                } catch (Exception unused) {
                }
                pageBundle.putString(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, jSONObject.toString());
                ArrayList arrayList = new ArrayList();
                arrayList.add("amapuri://feedback/location");
                ajw.a(arrayList, (bid) this.mPage, ResultType.OK, pageBundle);
            }
            ((SelectFixPoiFromMapPage) this.mPage).finish();
            return true;
        }
        PageBundle arguments = ((SelectFixPoiFromMapPage) this.mPage).getArguments();
        if (arguments == null || !arguments.getBoolean(GirfFavoritePoint.JSON_FIELD_POI_NEW_TYPE, false)) {
            return false;
        }
        final GeoPoint geoPoint = null;
        if (this.a != null) {
            geoPoint = this.a.getPoint();
        }
        if (geoPoint == null) {
            return false;
        }
        try {
            final Callback callback = (Callback) arguments.getObject("resultCallback");
            if (callback == null) {
                return false;
            }
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager == null) {
                return false;
            }
            cfc a2 = cfc.a();
            AnonymousClass1 r7 = new a() {
                public final void onPrepare() {
                }

                public final void onFailure() {
                    if (cys.this.mPage != null && ((SelectFixPoiFromMapPage) cys.this.mPage).isAlive()) {
                        callback.callback("");
                        aho.a(new Runnable() {
                            public final void run() {
                                if (cys.this.mPage != null && ((SelectFixPoiFromMapPage) cys.this.mPage).isAlive()) {
                                    ((SelectFixPoiFromMapPage) cys.this.mPage).finish();
                                }
                            }
                        });
                    }
                }

                public final void onScreenShotFinish(String str) {
                    if (cys.this.mPage != null && ((SelectFixPoiFromMapPage) cys.this.mPage).isAlive()) {
                        JSONObject jSONObject = new JSONObject();
                        if (str == null) {
                            str = "";
                        }
                        try {
                            jSONObject.put("filePath", str);
                            jSONObject.put(DictionaryKeys.CTRLXY_X, geoPoint.getLongitude());
                            jSONObject.put(DictionaryKeys.CTRLXY_Y, geoPoint.getLatitude());
                            jSONObject.put("poiName", cys.this.a.getName());
                            callback.callback(jSONObject.toString());
                            aho.a(new Runnable() {
                                public final void run() {
                                    if (cys.this.mPage != null && ((SelectFixPoiFromMapPage) cys.this.mPage).isAlive()) {
                                        ((SelectFixPoiFromMapPage) cys.this.mPage).finish();
                                    }
                                }
                            });
                        } catch (Exception unused) {
                            callback.callback("");
                            aho.a(new Runnable() {
                                public final void run() {
                                    if (cys.this.mPage != null && ((SelectFixPoiFromMapPage) cys.this.mPage).isAlive()) {
                                        ((SelectFixPoiFromMapPage) cys.this.mPage).finish();
                                    }
                                }
                            });
                        }
                    }
                }
            };
            cfd a3 = cfd.a(mapManager.getMapView(), geoPoint, arguments.getInt("w", 0), arguments.getInt("h", 0));
            a3.i = true;
            a2.a(mapManager, r7, a3);
            return true;
        } catch (Exception unused2) {
            return false;
        }
    }
}
