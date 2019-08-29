package com.autonavi.minimap.bundle.locationselect.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.NotMapSkinPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.locationselect.overlay.SelectPoiPointOverlay;
import com.autonavi.minimap.bundle.locationselect.view.ChoosePointBottomBar;
import com.autonavi.minimap.bundle.locationselect.view.ChoosePointBottomBar.a;
import com.autonavi.minimap.bundle.locationselect.view.ChoosePointBottomBar.b;
import com.autonavi.widget.ui.AlertView;

@PageAction("amap.basemap.action.base_select_poi_from_map_page")
public class SelectPoiFromMapPage extends AbstractBaseMapPage<cyt> implements a, a, b {
    public ChoosePointBottomBar a;
    public bty b;
    public SelectPoiPointOverlay c;
    private TextView d;
    private View e;
    /* access modifiers changed from: private */
    public ImageView f;
    /* access modifiers changed from: private */
    public TranslateAnimation g;
    private cde h;
    private MapManager i;
    private cyq j;
    private OnClickListener k = new OnClickListener() {
        public final void onClick(View view) {
            ((cyt) SelectPoiFromMapPage.this.mPresenter).b = null;
            SelectPoiFromMapPage.this.a(ResultType.CANCEL, (PageBundle) null);
        }
    };

    /* access modifiers changed from: private */
    /* renamed from: e */
    public cyt createPresenter() {
        return new cyt(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.fragment_map_select_point);
        View contentView = getContentView();
        getSuspendManager().b().disableView(256);
        getSuspendManager().b().disableView(1024);
        this.h = getSuspendManager();
        this.i = getMapManager();
        if (!(this.h == null || this.i == null)) {
            f();
            this.i.getOverlayManager().setGPSShowMode(0);
        }
        this.j = new cyq(this);
        this.b = getMapView();
        contentView.findViewById(R.id.title_btn_left).setOnClickListener(this.k);
        contentView.findViewById(R.id.title_btn_right).setVisibility(4);
        this.a = (ChoosePointBottomBar) contentView.findViewById(R.id.mapBottomInteractiveView);
        this.f = (ImageView) contentView.findViewById(R.id.iv_center);
        this.e = contentView.findViewById(R.id.map_select_center_offset_layout);
        this.d = (TextView) contentView.findViewById(R.id.title_text_name);
        this.a.setSelectType(((cyt) this.mPresenter).a);
        this.a.registerCallback(this);
        this.a.setOnRequestDoneCallback(this);
        this.d.setText(R.string.v4_mapclick);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.addRule(2, R.id.mapBottomInteractiveView);
        layoutParams.addRule(3, R.id.mapTopInteractiveView);
        this.e.setLayoutParams(layoutParams);
        this.e.setVisibility(0);
        b();
        if (this.g == null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 0, -30.0f);
            this.g = translateAnimation;
            this.g.setDuration(250);
            this.g.setInterpolator(new AccelerateInterpolator());
        }
        if (this.c == null) {
            this.c = new SelectPoiPointOverlay(this.i.getMapView());
            addOverlay(this.c);
        }
        getSuspendWidgetHelper().a((a) this);
    }

    public View getMapSuspendView() {
        return this.j.a();
    }

    public final void b() {
        if (this.a != null) {
            this.a.post(new Runnable() {
                public final void run() {
                    if (SelectPoiFromMapPage.this.a != null) {
                        int height = SelectPoiFromMapPage.this.a.getHeight();
                        int dimension = (int) SelectPoiFromMapPage.this.a.getResources().getDimension(R.dimen.selectpoi_top_title_height);
                        if (SelectPoiFromMapPage.this.b != null) {
                            SelectPoiFromMapPage.this.b.b(SelectPoiFromMapPage.this.b.al() / 2, dimension + (((SelectPoiFromMapPage.this.b.am() - height) - dimension) / 2));
                        }
                    }
                }
            });
        }
    }

    public final void a(final int i2) {
        getContentView().postDelayed(new Runnable() {
            public final void run() {
                if (SelectPoiFromMapPage.this.f != null) {
                    SelectPoiFromMapPage.this.f.setImageResource(i2);
                }
            }
        }, 50);
    }

    public final GLGeoPoint c() {
        if (this.b != null) {
            return this.b.n();
        }
        return null;
    }

    public final void a(String str, cyo cyo) {
        POI poi;
        cyt cyt = (cyt) this.mPresenter;
        if (!cyt.d) {
            if (str != null) {
                cyt.e = str;
            }
            if (cyo != null) {
                poi = POIFactory.createPOI(cyt.e, cyo.d);
                poi.setId(cyo.e);
                poi.setAddr(cyo.c);
                poi.setEntranceList(cyo.i);
                poi.setEndPoiExtension(cyo.g);
                poi.setTransparent(cyo.h);
                ((FavoritePOI) poi.as(FavoritePOI.class)).setNewType(cyo.f);
            } else {
                SelectPoiFromMapPage selectPoiFromMapPage = (SelectPoiFromMapPage) cyt.mPage;
                GeoPoint geoPoint = null;
                if (selectPoiFromMapPage.a != null) {
                    geoPoint = selectPoiFromMapPage.a.getListRequestPoint();
                }
                if (geoPoint == null) {
                    geoPoint = cyt.c;
                }
                if (geoPoint == null) {
                    geoPoint = new GeoPoint();
                }
                poi = POIFactory.createPOI(cyt.e, geoPoint);
            }
            cyt.b = poi.clone();
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            String b2 = iAccountService != null ? iAccountService.b() : "";
            com com2 = (com) ank.a(com.class);
            if (com2 != null) {
                cop b3 = com2.b(b2);
                if (cyt.a != SelectFor.SAVE_POI || !b3.c(poi)) {
                    cyt.a(false);
                } else {
                    SelectPoiFromMapPage selectPoiFromMapPage2 = (SelectPoiFromMapPage) cyt.mPage;
                    AlertView.a aVar = new AlertView.a(selectPoiFromMapPage2.getActivity());
                    aVar.a(R.string.dulicate_save_point_modify);
                    aVar.b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.cancel), (a) new a() {
                        public final void onClick(AlertView alertView, int i) {
                            SelectPoiFromMapPage.this.dismissViewLayer(alertView);
                        }
                    });
                    aVar.a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.modify_confirm), (a) new a() {
                        public final void onClick(AlertView alertView, int i) {
                            SelectPoiFromMapPage.this.dismissViewLayer(alertView);
                            ((cyt) SelectPoiFromMapPage.this.mPresenter).a(true);
                        }
                    });
                    aVar.b = new a() {
                        public final void onClick(AlertView alertView, int i) {
                        }
                    };
                    aVar.c = new a() {
                        public final void onClick(AlertView alertView, int i) {
                        }
                    };
                    aVar.a(false);
                    AlertView a2 = aVar.a();
                    selectPoiFromMapPage2.showViewLayer(a2);
                    a2.startAnimation();
                }
            }
        }
    }

    public final void a(GeoPoint geoPoint) {
        if (this.b != null) {
            if (geoPoint != null) {
                this.b.a(geoPoint.x, geoPoint.y);
            } else {
                GeoPoint geoPoint2 = ((cyt) this.mPresenter).c;
                if (geoPoint2 != null) {
                    this.b.a(geoPoint2.x, geoPoint2.y);
                }
            }
        }
        f();
    }

    private void f() {
        if (this.h != null) {
            this.h.d().f();
        }
    }

    public final void d() {
        ((cyt) this.mPresenter).d = false;
    }

    public final void b(GeoPoint geoPoint) {
        if (isAlive()) {
            ((cyt) this.mPresenter).d = true;
            this.a.requestPoint(geoPoint);
        }
    }

    public final void a(POI poi, int i2) {
        if (this.c != null) {
            this.c.addPoiPointItem(poi, i2);
        }
    }

    public final void a(ResultType resultType, PageBundle pageBundle) {
        if (this.e != null) {
            this.e.setVisibility(8);
        }
        setResult(resultType, pageBundle);
        finish();
    }

    public final void a() {
        ((cyt) this.mPresenter).f = true;
    }

    public void resetMapSkinState() {
        if (!(this instanceof NotMapSkinPage)) {
            int l = bim.aa().l((String) "101");
            bty mapView = getMapManager().getMapView();
            if (l != 0) {
                if (mapView != null) {
                    mapView.a(l, mapView.ae(), 0);
                }
                return;
            }
            if (mapView != null) {
                mapView.a(l, mapView.ae(), 0);
            }
        }
    }
}
