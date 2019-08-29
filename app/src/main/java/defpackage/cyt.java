package defpackage;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import com.autonavi.miniapp.plugin.constant.ConstantCompat.SaveSearchResultMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.bundle.locationselect.page.SelectPoiFromMapPage;
import com.autonavi.minimap.search.model.SearchConst;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import java.util.ArrayList;
import org.json.JSONObject;

/* renamed from: cyt reason: default package */
/* compiled from: SelectPoiFromMapPresenter */
public final class cyt extends AbstractBaseMapPagePresenter<SelectPoiFromMapPage> {
    public SelectFor a = SelectFor.DEFAULT_POI;
    public POI b;
    public GeoPoint c;
    public boolean d = false;
    public String e = SearchConst.b;
    public boolean f = false;
    private POI g;
    private ArrayList<POI> h;
    private POI i;
    private POI j;
    private POI k;
    private int l;
    private GLGeoPoint m;

    public cyt(SelectPoiFromMapPage selectPoiFromMapPage) {
        super(selectPoiFromMapPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        PageBundle arguments = ((SelectPoiFromMapPage) this.mPage).getArguments();
        if (arguments.containsKey("FetchFor") && arguments.getObject("FetchFor") != null) {
            this.a = (SelectFor) arguments.getObject("FetchFor");
        }
        if (arguments.containsKey("SelectPoiFromMapBean")) {
            SelectPoiFromMapBean selectPoiFromMapBean = (SelectPoiFromMapBean) arguments.getObject("SelectPoiFromMapBean");
            if (selectPoiFromMapBean != null) {
                if (this.a == SelectFor.FROM_POI || this.a == SelectFor.TO_POI || this.a == SelectFor.MID_POI || this.a == SelectFor.MID_POI_1 || this.a == SelectFor.MID_POI_2 || this.a == SelectFor.MID_POI_3) {
                    this.g = selectPoiFromMapBean.getFromPOI();
                    this.h = selectPoiFromMapBean.getMidPOIs();
                    POI poi = null;
                    if (this.a == SelectFor.MID_POI || this.a == SelectFor.MID_POI_1) {
                        if (this.h != null && this.h.size() > 0) {
                            if (bnx.a(this.h.get(0))) {
                                poi = this.h.get(0);
                            }
                            this.k = poi;
                        }
                    } else if (this.a == SelectFor.MID_POI_2) {
                        if (this.h != null && this.h.size() > 1) {
                            if (bnx.a(this.h.get(1))) {
                                poi = this.h.get(1);
                            }
                            this.k = poi;
                        }
                    } else if (this.a == SelectFor.MID_POI_3 && this.h != null && this.h.size() > 2) {
                        if (bnx.a(this.h.get(2))) {
                            poi = this.h.get(2);
                        }
                        this.k = poi;
                    }
                    this.i = selectPoiFromMapBean.getToPOI();
                }
                this.c = selectPoiFromMapBean.getMapCenter();
                this.j = selectPoiFromMapBean.getOldPOI();
                this.l = selectPoiFromMapBean.getLevel();
                if (this.l > 0) {
                    bty mapView = ((SelectPoiFromMapPage) this.mPage).getMapView();
                    if (mapView != null) {
                        mapView.f((float) this.l);
                    }
                }
            }
        }
    }

    public final void onStart() {
        super.onStart();
        switch (this.a) {
            case FIX_POI:
            case DEFAULT_POI:
            case SAVE_POI:
                ((SelectPoiFromMapPage) this.mPage).a(R.drawable.b_poi);
                break;
            case FROM_POI:
                ((SelectPoiFromMapPage) this.mPage).a(R.drawable.bubble_start);
                if (this.g != null) {
                    this.g = this.g.clone();
                    this.c = this.g.getPoint();
                    if (this.g.getName().equalsIgnoreCase(((SelectPoiFromMapPage) this.mPage).getString(R.string.LocationMe))) {
                        this.c = GeoPoint.glGeoPoint2GeoPoint(((SelectPoiFromMapPage) this.mPage).c());
                        break;
                    }
                }
                break;
            case MID_POI:
                ((SelectPoiFromMapPage) this.mPage).a(R.drawable.bubble_midd);
                if (this.k != null) {
                    this.k = this.k.clone();
                    this.c = this.k.getPoint();
                    break;
                }
                break;
            case MID_POI_1:
                ((SelectPoiFromMapPage) this.mPage).a(R.drawable.bubble_midd1);
                if (this.k != null) {
                    this.k = this.k.clone();
                    this.c = this.k.getPoint();
                    break;
                }
                break;
            case MID_POI_2:
                ((SelectPoiFromMapPage) this.mPage).a(R.drawable.bubble_midd2);
                if (this.k != null) {
                    this.k = this.k.clone();
                    this.c = this.k.getPoint();
                    break;
                }
                break;
            case MID_POI_3:
                ((SelectPoiFromMapPage) this.mPage).a(R.drawable.bubble_midd3);
                if (this.k != null) {
                    this.k = this.k.clone();
                    this.c = this.k.getPoint();
                    break;
                }
                break;
            case TO_POI:
                ((SelectPoiFromMapPage) this.mPage).a(R.drawable.bubble_end);
                if (this.i != null) {
                    this.i = this.i.clone();
                    this.c = this.i.getPoint();
                    break;
                }
                break;
        }
        if (this.c == null) {
            SelectPoiFromMapPage selectPoiFromMapPage = (SelectPoiFromMapPage) this.mPage;
            this.c = GeoPoint.glGeoPoint2GeoPoint(selectPoiFromMapPage.b.c(selectPoiFromMapPage.b.al() / 2, selectPoiFromMapPage.b.am() / 2));
        } else {
            SelectPoiFromMapPage selectPoiFromMapPage2 = (SelectPoiFromMapPage) this.mPage;
            int i2 = this.c.x;
            int i3 = this.c.y;
            if (selectPoiFromMapPage2.b != null) {
                selectPoiFromMapPage2.b.a(i2, i3);
            }
        }
        SelectPoiFromMapPage selectPoiFromMapPage3 = (SelectPoiFromMapPage) this.mPage;
        if (selectPoiFromMapPage3.c != null) {
            selectPoiFromMapPage3.c.clear();
        }
        if (!(this.g == null || this.a == SelectFor.FROM_POI)) {
            ((SelectPoiFromMapPage) this.mPage).a(this.g, R.drawable.bubble_start);
        }
        if (this.h != null && this.h.size() > 0) {
            int i4 = 0;
            if (this.a == SelectFor.MID_POI || this.a == SelectFor.MID_POI_1 || this.a == SelectFor.MID_POI_2 || this.a == SelectFor.MID_POI_3) {
                if (this.a != SelectFor.MID_POI_1) {
                    if (this.a == SelectFor.MID_POI_2) {
                        i4 = 1;
                    } else if (this.a == SelectFor.MID_POI_3) {
                        i4 = 2;
                    }
                }
                a(i4);
            } else if (this.h.size() == 1) {
                POI poi = this.h.get(0);
                if (poi != null) {
                    ((SelectPoiFromMapPage) this.mPage).a(poi, R.drawable.bubble_midd);
                }
            } else {
                a(-1);
            }
        }
        if (!(this.i == null || this.a == SelectFor.TO_POI)) {
            ((SelectPoiFromMapPage) this.mPage).a(this.i, R.drawable.bubble_end);
        }
        if (this.j != null) {
            ((SelectPoiFromMapPage) this.mPage).a(this.j, R.drawable.bubble_wrongcheck);
        }
        if (this.c != null) {
            ((SelectPoiFromMapPage) this.mPage).b(this.c);
        }
        if (this.c != null) {
            a(this.c);
        }
        ((SelectPoiFromMapPage) this.mPage).resetMapSkinState();
    }

    public final void onStop() {
        super.onStop();
        ((SelectPoiFromMapPage) this.mPage).getSuspendManager().b().getMapLayerDialogCustomActions().a = 1;
    }

    public final void onDestroy() {
        super.onDestroy();
        SelectPoiFromMapPage selectPoiFromMapPage = (SelectPoiFromMapPage) this.mPage;
        if (selectPoiFromMapPage.a != null) {
            selectPoiFromMapPage.a.cancleNetWork();
        }
        if (selectPoiFromMapPage.a != null) {
            DisplayMetrics displayMetrics = selectPoiFromMapPage.a.getContext().getResources().getDisplayMetrics();
            if (selectPoiFromMapPage.b != null) {
                int i2 = displayMetrics.widthPixels / 2;
                int i3 = displayMetrics.heightPixels / 2;
                GLGeoPoint c2 = selectPoiFromMapPage.b.c(i2, i3);
                selectPoiFromMapPage.b.b(i2, i3);
                selectPoiFromMapPage.b.a(c2.x, c2.y);
            }
        }
        if (selectPoiFromMapPage.a != null) {
            selectPoiFromMapPage.a.setOnRequestDoneCallback(null);
            selectPoiFromMapPage.a.registerCallback(null);
        }
        selectPoiFromMapPage.getSuspendWidgetHelper().a((a) null);
    }

    public final void onMapSurfaceChanged(int i2, int i3) {
        super.onMapSurfaceChanged(i2, i3);
        ((SelectPoiFromMapPage) this.mPage).b();
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        return super.onMapTouchEvent(motionEvent);
    }

    public final boolean onMapMotionStop() {
        GLGeoPoint c2 = ((SelectPoiFromMapPage) this.mPage).c();
        if (this.m == null || !this.m.equals(c2)) {
            this.m = c2;
            long j2 = 0;
            if (this.f) {
                j2 = 500;
            }
            SelectPoiFromMapPage selectPoiFromMapPage = (SelectPoiFromMapPage) this.mPage;
            selectPoiFromMapPage.getContentView().postDelayed(new Runnable() {
                public final void run() {
                    ((cyt) SelectPoiFromMapPage.this.mPresenter).a(GeoPoint.glGeoPoint2GeoPoint(SelectPoiFromMapPage.this.b.n()));
                    if (SelectPoiFromMapPage.this.f != null && SelectPoiFromMapPage.this.g != null) {
                        SelectPoiFromMapPage.this.f.startAnimation(SelectPoiFromMapPage.this.g);
                        SelectPoiFromMapPage.this.g.startNow();
                    }
                }
            }, j2);
            this.f = false;
            return true;
        }
        this.d = false;
        return true;
    }

    public final void a(boolean z) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("SelectPoiFromMapFragment.MapClickResult", this.b);
        pageBundle.putBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY, z);
        JSONObject b2 = bnx.b(this.b);
        pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, b2 != null ? b2.toString() : null);
        ((SelectPoiFromMapPage) this.mPage).a(ResultType.OK, pageBundle);
    }

    private void a(int i2) {
        for (int i3 = 0; i3 < this.h.size(); i3++) {
            if (i3 != i2) {
                int i4 = R.drawable.bubble_midd1;
                POI poi = this.h.get(i3);
                if (poi != null) {
                    switch (i3) {
                        case 0:
                            i4 = R.drawable.bubble_midd1;
                            break;
                        case 1:
                            i4 = R.drawable.bubble_midd2;
                            break;
                        case 2:
                            i4 = R.drawable.bubble_midd3;
                            break;
                    }
                    ((SelectPoiFromMapPage) this.mPage).a(poi, i4);
                }
            }
        }
    }

    public final void a(GeoPoint geoPoint) {
        this.e = SearchConst.b;
        this.c = geoPoint;
        switch (this.a) {
            case FROM_POI:
                if (this.g == null) {
                    this.g = POIFactory.createPOI();
                }
                this.g.setPoint(geoPoint);
                break;
            case MID_POI:
            case MID_POI_1:
            case MID_POI_2:
            case MID_POI_3:
                if (this.k == null) {
                    this.k = POIFactory.createPOI();
                }
                this.k.setPoint(geoPoint);
                break;
            case TO_POI:
                if (this.i == null) {
                    this.i = POIFactory.createPOI();
                }
                this.i.setPoint(geoPoint);
                break;
        }
        ((SelectPoiFromMapPage) this.mPage).b(geoPoint);
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((SelectPoiFromMapPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }
}
