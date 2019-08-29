package com.autonavi.minimap.route.run.page;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.route.run.view.RouteHorizontalSelectorView;
import com.autonavi.minimap.route.run.view.RouteHorizontalSelectorView.a;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONObject;

public class RunRecommendPage extends AbstractBaseMapPage<efo> implements OnClickListener, OnTouchListener, OnPreDrawListener, a {
    public int a;
    public TitleBar b;
    public View c;
    public TextView d;
    public TextView e;
    public TextView f;
    public View g;
    public String h = "";
    private RouteHorizontalSelectorView i;
    private Button j;
    private LinearLayout k;
    private ImageView l;
    private AnimationDrawable m;
    private MapSharePreference n;
    private View o;
    private LinearLayout p;
    private LinearLayout q;

    /* access modifiers changed from: private */
    /* renamed from: c */
    public efo createPresenter() {
        return new efo(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.run_recommend_layout);
        requestScreenOrientation(1);
        a();
        this.n = new MapSharePreference(SharePreferenceName.SharedPreferences);
        this.i = (RouteHorizontalSelectorView) findViewById(R.id.horizontal_scroll_view_id);
        this.i.setOnHorScrollSelectListener(this);
        RouteHorizontalSelectorView routeHorizontalSelectorView = this.i;
        int i2 = -1;
        if (this.n != null) {
            i2 = this.n.getIntValue("run_recommend_select_diatance", -1);
        }
        routeHorizontalSelectorView.setDefaultSelectItem(i2);
        this.c = findViewById(R.id.mapBottomInteractiveView);
        this.c.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.d = (TextView) findViewById(R.id.run_recommend_distance);
        this.e = (TextView) findViewById(R.id.run_recommend_time);
        this.f = (TextView) findViewById(R.id.run_recommend_kcal);
        this.j = (Button) findViewById(R.id.run_recommend_start_run);
        this.j.setOnClickListener(this);
        this.g = findViewById(R.id.run_recommend_guide);
        if (!this.n.getBooleanValue("run_recommend_guide", false)) {
            d();
            this.n.putBooleanValue("run_recommend_guide", true);
        }
        this.k = (LinearLayout) findViewById(R.id.ll_desc);
        this.l = (ImageView) findViewById(R.id.iv_loading);
        this.m = (AnimationDrawable) this.l.getDrawable();
        this.b = (TitleBar) findViewById(R.id.title_bar);
        this.b.setSubTitle(getString(R.string.run_recommend_start_run_poi));
        this.b.setOnItemClickListener(new TitleBar.a() {
            public final void onClick(TitleBar titleBar, int i) {
                switch (i) {
                    case 17:
                    case 18:
                        RunRecommendPage.a(RunRecommendPage.this, ((efo) RunRecommendPage.this.mPresenter).a);
                        return;
                    default:
                        return;
                }
            }
        });
        this.b.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RunRecommendPage.this.finish();
            }
        });
        this.b.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RunRecommendPage.this.d();
            }
        });
        this.o = findViewById(R.id.run_recommend_loading_status);
        this.p = (LinearLayout) findViewById(R.id.run_recommend_net_status_no_data);
        this.q = (LinearLayout) findViewById(R.id.run_recommend_net_status_no_network);
        this.q.setOnClickListener(this);
    }

    public final void a() {
        if (getMapManager() != null) {
            bqx bqx = (bqx) ank.a(bqx.class);
            if (bqx != null) {
                bqx.a(false, false, false, getMapManager(), getContext());
            }
            bty mapView = getMapManager().getMapView();
            if (mapView != null && 3 != mapView.o(false)) {
                ebf.a(mapView, mapView.p(false), mapView.ae(), 3);
            }
        }
    }

    public View getMapSuspendView() {
        Context context = getContext();
        ccy suspendWidgetHelper = getSuspendWidgetHelper();
        ccv ccv = new ccv(context);
        ccv.addWidget(suspendWidgetHelper.l(), suspendWidgetHelper.m(), 6);
        ccv.addWidget(suspendWidgetHelper.f(), suspendWidgetHelper.g(), 7);
        ccv.addWidget(suspendWidgetHelper.n(), suspendWidgetHelper.q(), 2);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        LayoutParams layoutParams = new LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.leftMargin = agn.a(getContext(), 4.0f);
        layoutParams.bottomMargin = agn.a(getContext(), 3.0f);
        suspendWidgetHelper.a(suspendWidgetHelper.d());
        suspendWidgetHelper.a(ccv.getSuspendView(), suspendWidgetHelper.d(), layoutParams, 3);
        return ccv.getSuspendView();
    }

    public final void a(int i2, String str) {
        this.h = str;
        ((efo) this.mPresenter).a(str);
        if (this.n != null) {
            this.n.putIntValue("run_recommend_select_diatance", i2);
        }
    }

    public final void a(String str) {
        if (this.b != null) {
            this.b.setTitle(str);
        }
    }

    public boolean onPreDraw() {
        if (!this.c.isShown()) {
            return true;
        }
        this.c.getViewTreeObserver().removeOnPreDrawListener(this);
        int height = (((ags.a(getContext()).height() - this.c.getHeight()) - this.b.getHeight()) / 2) + this.b.getHeight();
        this.a = (ags.a(getContext()).height() / 2) - height;
        getMapView().b(ags.a(getContext()).width() / 2, height);
        efo efo = (efo) this.mPresenter;
        int height2 = ((RunRecommendPage) efo.mPage).c.getHeight();
        if (height2 < 10) {
            height2 = 738;
        }
        int height3 = ((RunRecommendPage) efo.mPage).b.getHeight();
        if (height3 < 10) {
            height3 = 132;
        }
        int width = ags.a(AMapPageUtil.getAppContext()).width();
        int a2 = height2 + agn.a(AMapPageUtil.getAppContext(), 52.0f);
        int height4 = (((ags.a(AMapPageUtil.getAppContext()).height() - a2) - height3) - ((int) (((float) width) * 0.75f))) / 2;
        int a3 = agn.a(AMapPageUtil.getAppContext(), 20.0f);
        int a4 = agn.a(AMapPageUtil.getAppContext(), 10.0f);
        efo.b.setScreenDisplayMargin(a3, height3 + height4 + agn.a(AMapPageUtil.getAppContext(), 30.0f) + ((RunRecommendPage) efo.mPage).a, a3, ((a2 + height4) + a4) - ((RunRecommendPage) efo.mPage).a);
        ((efo) this.mPresenter).b.zoomBoundMap();
        return true;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.run_recommend_start_run) {
            GeoPoint[] a2 = ((efo) this.mPresenter).a();
            int i2 = (a2 == null || a2.length == 0) ? 2 : 1;
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", i2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00289", "B001", jSONObject);
            if (ebm.a(getActivity())) {
                GeoPoint[] a3 = ((efo) this.mPresenter).a();
                PageBundle pageBundle = new PageBundle();
                if (a3 != null) {
                    pageBundle.putObject("key_recommend_line_points", a3);
                }
                pageBundle.putBoolean("key_is_from_recommend", true);
                avo avo = (avo) a.a.a(avo.class);
                Class<? extends AbstractBasePage> cls = null;
                if (avo != null) {
                    cls = avo.a().a(1);
                }
                startPage(cls, pageBundle);
                return;
            }
            return;
        }
        if (id == R.id.run_recommend_net_status_no_network) {
            ((efo) this.mPresenter).a(this.h);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.run_recommend_guide) {
            b();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void d() {
        this.g.setVisibility(0);
        this.g.setOnTouchListener(this);
    }

    public final void b() {
        if (this.g != null) {
            this.g.setVisibility(8);
            getContentView().setOnTouchListener(null);
        }
    }

    public final void a(int i2) {
        if (i2 == 0) {
            this.o.setVisibility(0);
            this.k.setVisibility(8);
            this.p.setVisibility(0);
            this.q.setVisibility(8);
        } else if (i2 == 1) {
            this.o.setVisibility(0);
            this.k.setVisibility(8);
            this.p.setVisibility(8);
            this.q.setVisibility(0);
        } else if (i2 == 2) {
            this.o.setVisibility(8);
            this.k.setVisibility(0);
            this.p.setVisibility(8);
            this.q.setVisibility(8);
        } else if (i2 == 3) {
            this.k.setVisibility(8);
            this.o.setVisibility(8);
            this.l.setVisibility(0);
            this.m.start();
        } else {
            if (i2 == 4) {
                this.k.setVisibility(0);
                this.l.setVisibility(8);
                this.m.stop();
            }
        }
    }

    static /* synthetic */ void a(RunRecommendPage runRecommendPage, POI poi) {
        String str = "";
        if (poi != null) {
            str = poi.getName();
        }
        String string = runRecommendPage.getString(R.string.run_recommend_start_poi);
        SelectFor selectFor = SelectFor.FROM_POI;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("search_for", 1);
        pageBundle.putString("hint", string);
        if (TextUtils.isEmpty(str) || !str.equalsIgnoreCase("我的位置")) {
            pageBundle.putString(TrafficUtil.KEYWORD, str);
        } else {
            pageBundle.putString(TrafficUtil.KEYWORD, "");
        }
        pageBundle.putBoolean("isHideMyPosition", false);
        pageBundle.putObject("selectedfor", selectFor);
        pageBundle.putInt("from_page", 12400);
        pageBundle.putString("SUPER_ID", UploadQueueMgr.MSGTYPE_REALTIME);
        pageBundle.putBoolean("auto_search", false);
        runRecommendPage.startPageForResult((String) "search.fragment.SearchCallbackFragment", pageBundle, 1001);
    }
}
