package com.autonavi.minimap.route.sharebike.page;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.ae.gmap.utils.GLMapStaticValue;
import com.autonavi.bundle.sharebike.ajx.ModuleBicycle;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.route.common.view.CircleTabView;
import com.autonavi.minimap.route.common.view.CircleTabView.a;
import com.autonavi.minimap.route.common.view.RouteTabScrollView;
import com.autonavi.minimap.route.sharebike.presenter.ShareBikePresenter$22;
import com.autonavi.minimap.route.sharebike.utils.ShareBikeLogin;
import com.autonavi.minimap.route.sharebike.utils.ShareBikeLogin.OpenPageType;
import com.autonavi.minimap.route.sharebike.view.ShareBikeIndicatorView;
import com.autonavi.minimap.widget.ConfirmDlg;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.LoadingViewBL;
import com.autonavi.widget.ui.TitleBar;
import java.io.FileInputStream;
import java.util.List;
import org.json.JSONObject;

public class ShareBikePage extends AbstractBaseMapPage<ehd> implements OnClickListener, OnPreDrawListener, a, ShareBikeIndicatorView.a {
    /* access modifiers changed from: private */
    public ImageView A;
    /* access modifiers changed from: private */
    public LinearLayout B;
    private ImageView C;
    private LoadingViewBL D;
    private LinearLayout E;
    private RelativeLayout F;
    private RelativeLayout G;
    private RelativeLayout H;
    private ImageView I;
    private TitleBar J;
    private View K;
    private int L = -1;
    private int M = -1;
    /* access modifiers changed from: private */
    public AlertView N;
    private int O;
    /* access modifiers changed from: private */
    public long P;
    private int Q = -1;
    private int R = -1;
    private AnimatorListener S = new AnimatorListener() {
        private boolean b = false;

        public final void onAnimationCancel(Animator animator) {
        }

        public final void onAnimationRepeat(Animator animator) {
        }

        public final void onAnimationStart(Animator animator) {
        }

        public final void onAnimationEnd(Animator animator) {
            if (ShareBikePage.this.l.getVisibility() != 8) {
                if (((ehd) ShareBikePage.this.mPresenter).e == 1) {
                    this.b = true;
                }
                if (((ehd) ShareBikePage.this.mPresenter).e == 0) {
                    if (this.b) {
                        this.b = false;
                        return;
                    }
                    ShareBikePage.this.i = false;
                }
            }
        }
    };
    public ImageView a;
    public ImageView b;
    public AmapAjxView c;
    public ModuleBicycle d;
    public ModuleBicycle e;
    public ShareBikeIndicatorView f;
    public ConfirmDlg g;
    public int h;
    public boolean i = false;
    public dzv j;
    public boolean k = false;
    /* access modifiers changed from: private */
    public RelativeLayout l;
    /* access modifiers changed from: private */
    public AmapAjxView m;
    private ImageView n;
    private ImageView o;
    private RouteTabScrollView p;
    private CircleTabView q;
    private LinearLayout r;
    private TextView s;
    private TextView t;
    private TextView u;
    private View v;
    private FrameLayout w;
    private View x;
    private int y = 432;
    private ImageView z;

    public View getMapSuspendView() {
        return null;
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public ehd createPresenter() {
        return new ehd(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.sharebike_page);
        bty mapView = getMapView();
        getSuspendManager().d().f();
        this.k = false;
        if (mapView != null) {
            dzv dzv = new dzv(mapView.I(), mapView.v(), mapView.J(), mapView.o(), mapView.j(false), mapView.k(false));
            this.j = dzv;
        }
        a();
        this.h = GLMapStaticValue.b;
        this.O = ags.a(AMapPageUtil.getAppContext()).width();
        this.J = (TitleBar) findViewById(R.id.sharebike_title);
        this.J.setDivideVisibility(8);
        this.J.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ehd ehd = (ehd) ShareBikePage.this.mPresenter;
                boolean z = false;
                if (ehd.e == 1) {
                    ehd.a(0);
                    ehd.b(true);
                } else {
                    z = true;
                }
                if (z) {
                    ShareBikePage.this.finish();
                }
            }
        });
        this.J.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ShareBikePage.d(ShareBikePage.this);
            }
        });
        this.f = (ShareBikeIndicatorView) findViewById(R.id.route_indicator);
        this.f.setOnIndicatorRefreshClickListener(this);
        this.r = (LinearLayout) findViewById(R.id.sharebike_action_select_list);
        this.s = (TextView) findViewById(R.id.tv_sharebike_myroute);
        this.t = (TextView) findViewById(R.id.tv_sharebike_mywallet);
        this.u = (TextView) findViewById(R.id.tv_sharebike_help);
        this.v = findViewById(R.id.sharebike_mask);
        this.v.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    ShareBikePage.this.a(true);
                }
                return true;
            }
        });
        a(false);
        NoDBClickUtil.a((View) this.s, (OnClickListener) this);
        NoDBClickUtil.a((View) this.t, (OnClickListener) this);
        NoDBClickUtil.a((View) this.u, (OnClickListener) this);
        this.G = (RelativeLayout) findViewById(R.id.sharebike_bottom_view);
        this.H = (RelativeLayout) findViewById(R.id.gps_root);
        this.w = (FrameLayout) findViewById(R.id.sharebike_scan_btn);
        this.x = findViewById(R.id.sharebike_scan_btn_press_area);
        this.A = (ImageView) this.w.findViewById(R.id.sharebike_btn_halo);
        if (this.A != null) {
            this.A.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
            this.y = this.A.getMeasuredHeight();
        }
        this.B = (LinearLayout) this.w.findViewById(R.id.button_layer);
        this.x.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action != 3) {
                    switch (action) {
                        case 0:
                            Animation loadAnimation = AnimationUtils.loadAnimation(ShareBikePage.this.getContext(), R.anim.scan_btn_down_scale);
                            ShareBikePage.a(ShareBikePage.this, loadAnimation, true);
                            ShareBikePage.this.A.startAnimation(loadAnimation);
                            break;
                        case 1:
                            break;
                    }
                }
                Animation loadAnimation2 = AnimationUtils.loadAnimation(ShareBikePage.this.getContext(), R.anim.scan_btn_up_scale);
                ShareBikePage.a(ShareBikePage.this, loadAnimation2, false);
                ShareBikePage.this.A.startAnimation(loadAnimation2);
                return true;
            }
        });
        this.b = (ImageView) this.w.findViewById(R.id.sharebike_vip_info);
        this.z = (ImageView) this.w.findViewById(R.id.image_path_icon);
        this.a = (ImageView) this.w.findViewById(R.id.sharebike_ribbon_icon);
        this.I = (ImageView) findViewById(R.id.gpsBtn);
        NoDBClickUtil.a((View) this.I, (OnClickListener) this);
        if (this.H != null) {
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.bottomMargin = agn.a(AMapPageUtil.getAppContext(), 2.0f);
            layoutParams.addRule(1, R.id.gpsBtn_mian);
            getSuspendWidgetHelper().f().setLogoSize((int) getResources().getDimension(R.dimen.sharebick_logo_width), (int) getResources().getDimension(R.dimen.sharebick_logo_height));
            this.H.addView(getSuspendWidgetHelper().f(), layoutParams);
        }
        this.E = (LinearLayout) findViewById(R.id.sharebike_bottom_refresh_ll);
        this.F = (RelativeLayout) findViewById(R.id.refresh_root);
        if (this.F != null) {
            this.F.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }
        NoDBClickUtil.a((View) this.E, (OnClickListener) this);
        this.C = (ImageView) findViewById(R.id.sharebike_refresh);
        this.D = (LoadingViewBL) findViewById(R.id.sharebike_refresh_btn_loading);
        this.G.getViewTreeObserver().addOnPreDrawListener(this);
        this.K = findViewById(R.id.title_line);
        this.l = (RelativeLayout) findViewById(R.id.sharebike_tab_rel);
        this.n = (ImageView) findViewById(R.id.sharebike_tab_left);
        this.o = (ImageView) findViewById(R.id.sharebike_tab_right);
        this.n.setVisibility(8);
        this.o.setVisibility(8);
        this.p = (RouteTabScrollView) findViewById(R.id.tab_holder);
        this.p.setShadows(this.n, this.o);
        this.p.setAlphaChangeStyle(false);
        this.q = (CircleTabView) findViewById(R.id.tab);
        this.m = (AmapAjxView) getContentView().findViewById(R.id.sharebike_ajx_banner);
        if (this.m != null) {
            this.m.setVisibility(0);
            this.m.onAjxContextCreated(new Callback<AmapAjxView>() {
                public void error(Throwable th, boolean z) {
                }

                public void callback(AmapAjxView amapAjxView) {
                    if (ShareBikePage.this.isAlive() && ShareBikePage.this.m.getAjxContext() != null) {
                        ShareBikePage.this.e = (ModuleBicycle) ShareBikePage.this.m.getJsModule(ModuleBicycle.MODULE_NAME);
                    }
                }
            });
            this.m.load("path://amap_bundle_tripgroup/src/share_bike/BannerPage.page.js", null, "ShareBanner");
        }
        this.c = (AmapAjxView) getContentView().findViewById(R.id.share_bike_ajxview);
        if (this.c != null) {
            this.c.onAjxContextCreated(new Callback<AmapAjxView>() {
                public void error(Throwable th, boolean z) {
                }

                public void callback(AmapAjxView amapAjxView) {
                    if (ShareBikePage.this.isAlive() && ShareBikePage.this.c.getAjxContext() != null) {
                        ShareBikePage.this.d = (ModuleBicycle) ShareBikePage.this.c.getJsModule(ModuleBicycle.MODULE_NAME);
                    }
                }
            });
            this.c.load("path://amap_bundle_tripgroup/src/share_bike/TipIndex.page.js", null, "ShareBikeTip");
        }
    }

    public final void a() {
        if (getMapManager() != null) {
            bqx bqx = (bqx) ank.a(bqx.class);
            if (bqx != null) {
                bqx.a(false, false, false, getMapManager(), getContext());
            }
            bty mapView = getMapManager().getMapView();
            if (mapView != null) {
                if (11 != mapView.o(false)) {
                    ebf.a(mapView, mapView.p(false), mapView.ae(), 11);
                }
                mapView.d(true);
                mapView.e(true);
            }
        }
    }

    public final void a(GeoPoint geoPoint) {
        if (geoPoint != null && getMapManager() != null) {
            bty mapView = getMapManager().getMapView();
            if (mapView != null) {
                mapView.a(400, 16.0f, 0, 0, geoPoint.x, geoPoint.y, true);
            }
        }
    }

    public final void a(List<egr> list) {
        this.p.setVisibility(0);
        if (list != null && list.size() > 0) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d));
            int size = list.size();
            this.q.setMaxScreenTabCount(4.5f);
            this.q.setScreenTabCount(size);
            this.p.setShadowIsShow(((float) size) > 4.5f);
            for (int i2 = 0; i2 < list.size(); i2++) {
                egr egr = list.get(i2);
                if (egr != null) {
                    this.q.addTab(egr, egr.b, false);
                }
            }
        }
        int currentIndex = this.q.getCurrentIndex();
        if (currentIndex == -1) {
            if (list != null && list.size() != 0) {
                list.size();
                MapSharePreference mapSharePreference = new MapSharePreference((String) "SHARE_BIKE_SETTING");
                egr egr2 = new egr();
                egr2.a = mapSharePreference.getStringValue("cpinfo_source", "all");
                egr2.b = mapSharePreference.getStringValue("cpinfo_name", AMapPageUtil.getAppContext().getString(R.string.sharebike_type_total_bikes));
                currentIndex = 0;
                while (true) {
                    if (currentIndex >= list.size()) {
                        currentIndex = 0;
                        break;
                    }
                    egr egr3 = list.get(currentIndex);
                    if (egr3 != null && TextUtils.equals(egr2.a, egr3.a) && TextUtils.equals(egr2.b, egr3.b)) {
                        break;
                    }
                    currentIndex++;
                }
            } else {
                currentIndex = -1;
            }
        }
        this.q.setOnTabSelectedListener(this);
        b(currentIndex);
    }

    public final void b(int i2) {
        if (this.q != null) {
            boolean selectTab = this.q.setSelectTab(i2);
            h(i2);
            if (selectTab) {
                a((egr) this.q.getCurrentType());
            }
        }
    }

    private static void a(egr egr) {
        if (egr != null) {
            MapSharePreference mapSharePreference = new MapSharePreference((String) "SHARE_BIKE_SETTING");
            mapSharePreference.putStringValue("cpinfo_source", egr.a);
            mapSharePreference.putStringValue("cpinfo_name", egr.b);
        }
    }

    private void h(int i2) {
        if (this.p != null) {
            int width = ags.a(getContext()).width();
            float maxTabCount = this.q.getMaxTabCount();
            float f2 = ((float) width) / maxTabCount;
            int i3 = (int) (((((float) i2) - (maxTabCount / 2.0f)) * f2) + (f2 / 2.0f));
            if (i3 <= 0) {
                i3 = 0;
            }
            this.p.smoothScrollTo(i3, 0);
        }
    }

    public final void a(int i2) {
        if (this.q != null && i2 < this.q.getTabCount() && i2 >= 0) {
            ehd ehd = (ehd) this.mPresenter;
            if (ehd.b != null && !ehd.b.isEmpty() && ehd.b.size() > i2 && !TextUtils.isEmpty(ehd.b.get(i2).a)) {
                ehd.d = ehd.b.get(i2).a;
            }
            ehd ehd2 = (ehd) this.mPresenter;
            if (ehd2.a != null) {
                ehd2.a.c();
                ehd2.a.b();
                ehd2.a.g();
            }
            b(i2);
            int i3 = 1;
            ((ehd) this.mPresenter).b(true);
            ehd ehd3 = (ehd) this.mPresenter;
            ehd.a((JSONObject) null, (String) "B004");
            if (!TextUtils.equals("mobike", ehd3.d)) {
                i3 = TextUtils.equals("bluegogo", ehd3.d) ? 2 : 0;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("itemId", i3);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            ehd.a(jSONObject, (String) "B001");
        }
    }

    public final void a(boolean z2) {
        this.v.setVisibility(8);
        if (z2) {
            a((View) this.r, false);
        } else {
            this.r.setVisibility(8);
        }
    }

    public final boolean b() {
        if (this.v != null && this.v.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    private static void a(View view, boolean z2) {
        ScaleAnimation scaleAnimation;
        View view2 = view;
        view2.setVisibility(z2 ? 0 : 8);
        if (z2) {
            scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 1.0f, 1, 0.0f);
        } else {
            scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, 1.0f, 1, 0.0f);
        }
        scaleAnimation.setDuration(400);
        view2.setAnimation(scaleAnimation);
        scaleAnimation.startNow();
    }

    public void onClick(View view) {
        int id = view.getId();
        boolean z2 = false;
        if (id == R.id.tv_sharebike_myroute || id == R.id.tv_sharebike_mywallet) {
            new ShareBikeLogin(new Object[0]).a(getPageContext(), id == R.id.tv_sharebike_myroute ? OpenPageType.PAGE_HISTORY : OpenPageType.PAGE_WALLET_LIST, true);
            a(false);
        } else if (id == R.id.sharebike_bottom_refresh_ll) {
            ((ehd) this.mPresenter).c();
        } else if (id == R.id.gpsBtn) {
            ehd ehd = (ehd) this.mPresenter;
            switch (ehd.e) {
                case 0:
                    if (!TextUtils.isEmpty(ehd.h)) {
                        ehd.b();
                    }
                    if (ehd.c == null) {
                        ehd.c = LocationInstrument.getInstance().getLatestPosition();
                        ((ShareBikePage) ehd.mPage).a(ehd.c);
                        ehd.a(true);
                        return;
                    }
                    boolean a2 = bnx.a(POIFactory.createPOI("centerPoi", ehd.c), POIFactory.createPOI("centerPoi", LocationInstrument.getInstance().getLatestPosition()));
                    int b2 = cfe.b(ehd.c, LocationInstrument.getInstance().getLatestPosition());
                    if (!a2 && b2 > 100) {
                        z2 = true;
                    }
                    if (z2) {
                        ehd.c = LocationInstrument.getInstance().getLatestPosition();
                        ((ShareBikePage) ehd.mPage).a(ehd.c);
                        ehd.a(true);
                        return;
                    }
                    ((ShareBikePage) ehd.mPage).h();
                    return;
                case 1:
                    ((ShareBikePage) ehd.mPage).h();
                    break;
            }
        } else {
            if (id == R.id.tv_sharebike_help) {
                a(false);
                ehc.a(4, null);
                LogManager.actionLogV2("P00298", "B005");
            }
        }
    }

    private void h() {
        getMapManager().getMapView().a((GLGeoPoint) LocationInstrument.getInstance().getLatestPosition());
    }

    public final void c(int i2) {
        if (this.f != null) {
            this.f.setVisibility(i2);
        }
    }

    public final void d(int i2) {
        this.K.setVisibility(i2);
    }

    public final void e(int i2) {
        if (!(this.l == null || i2 == this.M)) {
            if (i2 == 0) {
                if (this.m != null) {
                    ebv.a((View) this.m, (AnimatorListener) null);
                }
                this.l.setVisibility(0);
                ebv.a((View) this.l, this.S);
            } else {
                RelativeLayout relativeLayout = this.l;
                if (this.m != null) {
                    AmapAjxView amapAjxView = this.m;
                    amapAjxView.setTranslationY((float) relativeLayout.getHeight());
                    amapAjxView.post(new Runnable(amapAjxView) {
                        final /* synthetic */ View a;

                        {
                            this.a = r1;
                        }

                        public final void run() {
                            ViewPropertyAnimator animate = this.a.animate();
                            animate.alpha(1.0f).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator()).translationY(1.0f);
                            animate.start();
                        }
                    });
                }
                this.l.setVisibility(8);
                ebv.a(this.l);
            }
        }
        this.M = i2;
    }

    public final void f(int i2) {
        if (!(this.c == null || i2 == this.L)) {
            if (i2 == 0) {
                this.c.setVisibility(0);
                ebv.a((View) this.c, (AnimatorListener) null);
            } else {
                ebv.a(this.c);
            }
        }
        this.L = i2;
    }

    public final void b(boolean z2) {
        if (!z2) {
            this.C.setVisibility(0);
            this.D.setVisibility(8);
            this.E.setClickable(true);
        } else if (!this.D.isShown()) {
            this.C.setVisibility(8);
            this.D.setVisibility(0);
            this.E.setClickable(false);
        }
    }

    public final void g(int i2) {
        if (this.f != null) {
            this.f.setNetStatus(i2);
        }
    }

    public final void c() {
        if (this.f != null) {
            this.f.playTipPinDownAnimator();
        }
    }

    public final void a(View view) {
        if (view.getTag() != null) {
            this.k = true;
        }
        ((ehd) this.mPresenter).a(true);
    }

    public boolean onPreDraw() {
        this.G.getViewTreeObserver().removeOnPreDrawListener(this);
        ebv.a((View) this.J, (View) this.G);
        return false;
    }

    public final void d() {
        aho.a(new Runnable() {
            public final void run() {
                if (ShareBikePage.this.N != null) {
                    ShareBikePage.o(ShareBikePage.this);
                }
                ShareBikePage shareBikePage = ShareBikePage.this;
                ShareBikePage shareBikePage2 = ShareBikePage.this;
                String string = ShareBikePage.this.getContext().getString(R.string.text_dialog_rescan_msg);
                String string2 = ShareBikePage.this.getContext().getString(R.string.text_dialog_rescan_btn);
                AnonymousClass1 r4 = new a() {
                    public final void onClick(AlertView alertView, int i) {
                        ShareBikePage.o(ShareBikePage.this);
                        ((ehd) ShareBikePage.this.mPresenter).a(0);
                        ((ehd) ShareBikePage.this.mPresenter).b(true);
                    }
                };
                AnonymousClass2 r5 = new a() {
                    public final void onClick(AlertView alertView, int i) {
                        ShareBikePage.o(ShareBikePage.this);
                    }
                };
                AlertView.a aVar = new AlertView.a(shareBikePage2.getContext());
                aVar.b((CharSequence) string);
                aVar.a((CharSequence) string2, (a) r4);
                aVar.c = r5;
                AlertView a2 = aVar.a();
                shareBikePage2.showViewLayer(a2);
                a2.startAnimation();
                shareBikePage.N = a2;
            }
        });
    }

    public final void e() {
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            if (this.Q == -1) {
                this.Q = mapView.al() / 2;
            }
            if (this.R == -1) {
                this.R = ((getActivity().getWindow().getDecorView().getHeight() - ags.d(getContext())) - (this.y / 3)) / 2;
            }
            mapView.b(this.Q, this.R);
        }
    }

    public final void a(String str) {
        this.f.showPoiNameViewWithAnim(str);
    }

    public final void f() {
        if (isAlive() && this.z != null) {
            boolean z2 = false;
            Bitmap b2 = b(eht.a((String) "scan_btn_normal"));
            if (b2 != null) {
                z2 = true;
                String name = getClass().getName();
                StringBuilder sb = new StringBuilder("updateScanIcon width:");
                sb.append(b2.getWidth());
                sb.append(" height:");
                sb.append(b2.getHeight());
                eao.e(name, sb.toString());
                float a2 = (float) agn.a(getContext(), ((float) b2.getHeight()) / 2.0f);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.z.getLayoutParams();
                layoutParams.height = (int) a2;
                layoutParams.width = (int) ((float) agn.a(getContext(), ((float) b2.getWidth()) / 2.0f));
                this.z.setLayoutParams(layoutParams);
                this.z.setImageBitmap(b2);
                this.z.setTranslationY((a2 - ((float) agn.a(getContext(), 176.0f))) / 2.0f);
            }
            if (!z2) {
                int a3 = agn.a(getContext(), 176.0f);
                FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.z.getLayoutParams();
                layoutParams2.height = a3;
                layoutParams2.width = a3;
                this.z.setLayoutParams(layoutParams2);
                this.z.requestLayout();
                this.z.setImageResource(R.drawable.sharebike_scan_btn);
                this.z.setTranslationY(0.0f);
            }
        }
    }

    private static Bitmap b(String str) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(str));
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    static /* synthetic */ void d(ShareBikePage shareBikePage) {
        shareBikePage.v.setVisibility(0);
        a((View) shareBikePage.r, true);
    }

    static /* synthetic */ void a(ShareBikePage shareBikePage, Animation animation, final boolean z2) {
        if (animation != null) {
            animation.setAnimationListener(new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                    if (z2) {
                        ShareBikePage.this.B.setVisibility(0);
                    }
                }

                public final void onAnimationEnd(Animation animation) {
                    if (!z2) {
                        ShareBikePage.this.B.setVisibility(8);
                        long currentTimeMillis = System.currentTimeMillis();
                        if (Math.abs(currentTimeMillis - ShareBikePage.this.P) >= 800) {
                            ShareBikePage.this.P = currentTimeMillis;
                            ((ehd) ShareBikePage.this.mPresenter).f = true;
                            ehd ehd = (ehd) ShareBikePage.this.mPresenter;
                            OpenPageType openPageType = OpenPageType.PAGE_QRCODE_SCAN;
                            new ShareBikePresenter$22(ehd);
                            new ShareBikeLogin("bike", ehd.g).a((bid) ehd.mPage, openPageType, false);
                            LogManager.actionLogV2("P00298", "B007");
                        }
                    }
                }
            });
        }
    }

    static /* synthetic */ void o(ShareBikePage shareBikePage) {
        if (shareBikePage.N != null) {
            shareBikePage.dismissViewLayer(shareBikePage.N);
            shareBikePage.N = null;
        }
    }

    static /* synthetic */ void r(ShareBikePage shareBikePage) {
        if (shareBikePage.g != null) {
            shareBikePage.g.dismiss();
            shareBikePage.g = null;
        }
        if (shareBikePage.mPresenter != null) {
            ((ehd) shareBikePage.mPresenter).c();
        }
    }
}
