package com.autonavi.minimap.bundle.locationselect.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.NotMapSkinPage;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.locationselect.overlay.SelectPoiLineOverlay;
import com.autonavi.minimap.bundle.locationselect.overlay.SelectPoiPointOverlay;
import com.autonavi.minimap.bundle.locationselect.view.ChooseFixPointContainer;
import com.autonavi.minimap.bundle.locationselect.view.ChooseFixPointContainer.a;
import com.autonavi.sync.beans.GirfFavoritePoint;
import com.autonavi.widget.ui.TitleBar;

@PageAction("amap.basemap.action.base_select_fix_poi_from_map_page")
public class SelectFixPoiFromMapPage extends AbstractBaseMapPage<cys> implements a, a {
    public ChooseFixPointContainer a;
    public bty b;
    public SelectPoiLineOverlay c;
    public boolean d;
    public int e;
    private TitleBar f;
    /* access modifiers changed from: private */
    public ImageView g;
    /* access modifiers changed from: private */
    public TranslateAnimation h;
    private SelectPoiPointOverlay i;
    private cde j;
    private MapManager k;
    private cyq l;

    /* access modifiers changed from: private */
    /* renamed from: e */
    public cys createPresenter() {
        return new cys(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        if (d()) {
            this.e = getActivity().getRequestedOrientation();
            requestScreenOrientation(1);
        }
        setContentView(R.layout.fragment_map_select_fixpoint);
        View contentView = getContentView();
        getSuspendManager().b().disableView(256);
        getSuspendManager().b().disableView(1024);
        this.j = getSuspendManager();
        this.k = getMapManager();
        if (!(this.j == null || this.k == null)) {
            if (this.j != null) {
                this.j.d().f();
            }
            this.k.getOverlayManager().setGPSShowMode(0);
        }
        this.l = new cyq(this);
        this.b = getMapView();
        this.f = (TitleBar) contentView.findViewById(R.id.title);
        this.g = (ImageView) contentView.findViewById(R.id.iv_center_center);
        this.f.setTitle(getString(R.string.v4_mapclick));
        this.f.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ((cys) SelectFixPoiFromMapPage.this.mPresenter).a();
            }
        });
        this.a = (ChooseFixPointContainer) contentView.findViewById(R.id.mapBottomInteractiveView);
        this.a.init(d());
        this.a.registerCallback(this);
        c();
        if (this.h == null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 0, -30.0f);
            this.h = translateAnimation;
            this.h.setDuration(250);
            this.h.setInterpolator(new AccelerateInterpolator());
        }
        if (this.i == null) {
            this.i = new SelectPoiPointOverlay(this.k.getMapView());
            addOverlay(this.i);
        }
        if (this.c == null) {
            this.c = new SelectPoiLineOverlay(this.k.getMapView());
        }
        getSuspendWidgetHelper().a((a) this);
        if (d()) {
            this.d = bim.aa().k((String) UploadConstants.STATUS_TASK_BY_PUSH);
            bim.aa().a((String) UploadConstants.STATUS_TASK_BY_PUSH, 1);
            cdd.n().h();
        }
    }

    public View getMapSuspendView() {
        return this.l.a();
    }

    public final void b() {
        if (this.i != null) {
            this.i.clear();
        }
    }

    public final void a(POI poi, int i2) {
        if (this.i != null) {
            this.i.addPoiPointItem(poi, i2);
        }
    }

    public final void a(GeoPoint geoPoint) {
        this.a.requestPoint(geoPoint);
    }

    public final void a(int i2, int i3) {
        if (this.b != null) {
            this.b.a(i2, i3);
        }
    }

    public final void a(String str, Object obj) {
        cys cys = (cys) this.mPresenter;
        if (!cys.d) {
            cys.d = true;
            if (str != null) {
                cys.c = str;
            }
            cys.a = POIFactory.createPOI(cys.c, cys.b != null ? cys.b : new GeoPoint()).clone();
            if (!cys.b()) {
                final PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("SelectFixPoiFromMapFragment.MapClickResult", cys.a);
                if (obj != null) {
                    pageBundle.putObject("SelectFixPoiFromMapFragment.MapClickResultObj", obj);
                }
                PageBundle arguments = ((SelectFixPoiFromMapPage) cys.mPage).getArguments();
                if (arguments == null || !arguments.containsKey("NeedTakeScreen")) {
                    ((SelectFixPoiFromMapPage) cys.mPage).a(ResultType.OK, pageBundle);
                } else {
                    SelectFixPoiFromMapPage selectFixPoiFromMapPage = (SelectFixPoiFromMapPage) cys.mPage;
                    cfc.a().a(selectFixPoiFromMapPage.getMapManager(), (a) new a() {
                        public final void onPrepare() {
                        }

                        public final void onFailure() {
                            SelectFixPoiFromMapPage.this.setResult(ResultType.OK, pageBundle);
                            SelectFixPoiFromMapPage.this.getContentView().post(new Runnable() {
                                public final void run() {
                                    SelectFixPoiFromMapPage.this.finish();
                                }
                            });
                        }

                        public final void onScreenShotFinish(String str) {
                            if (!TextUtils.isEmpty(str)) {
                                pageBundle.putString("SelectFixPoiFromMapFragment.ScreenShootPath", str);
                                SelectFixPoiFromMapPage.this.setResult(ResultType.OK, pageBundle);
                                SelectFixPoiFromMapPage.this.getContentView().post(new Runnable() {
                                    public final void run() {
                                        SelectFixPoiFromMapPage.this.finish();
                                    }
                                });
                                return;
                            }
                            pageBundle.putString("SelectFixPoiFromMapFragment.ScreenShootPath", "");
                            SelectFixPoiFromMapPage.this.setResult(ResultType.OK, pageBundle);
                            SelectFixPoiFromMapPage.this.getContentView().post(new Runnable() {
                                public final void run() {
                                    SelectFixPoiFromMapPage.this.finish();
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    public final void c() {
        if (this.a != null) {
            this.a.post(new Runnable() {
                public final void run() {
                    if (SelectFixPoiFromMapPage.this.a != null) {
                        int height = SelectFixPoiFromMapPage.this.a.getHeight();
                        int dimension = (int) AMapAppGlobal.getApplication().getResources().getDimension(R.dimen.selectpoi_top_title_height);
                        if (SelectFixPoiFromMapPage.this.b != null) {
                            SelectFixPoiFromMapPage.this.b.b(SelectFixPoiFromMapPage.this.b.al() / 2, dimension + (((SelectFixPoiFromMapPage.this.b.am() - height) - dimension) / 2));
                        }
                    }
                }
            });
        }
    }

    public final void a(ResultType resultType, PageBundle pageBundle) {
        setResult(resultType, pageBundle);
        finish();
    }

    public final void a() {
        ((cys) this.mPresenter).e = true;
    }

    public final boolean d() {
        PageBundle arguments = getArguments();
        if (arguments == null || !arguments.getBoolean(GirfFavoritePoint.JSON_FIELD_POI_NEW_TYPE, false)) {
            return false;
        }
        return true;
    }

    public void resetMapSkinState() {
        if (!(this instanceof NotMapSkinPage)) {
            int l2 = bim.aa().l((String) "101");
            bty mapView = getMapManager().getMapView();
            if (l2 != 0) {
                if (mapView != null) {
                    mapView.a(l2, mapView.ae(), 0);
                }
                return;
            }
            if (mapView != null) {
                mapView.a(l2, mapView.ae(), 0);
            }
        }
    }
}
