package com.autonavi.minimap.route.ride.page;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.datamodel.poi.POIBase;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.HealthPointStatus;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.map.core.LocationMode.LocationIgnore;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.b;
import com.autonavi.minimap.route.ride.presenter.RouteFootRidePresenter;
import com.autonavi.minimap.route.ride.view.RidePullSectionCustomView;
import com.autonavi.widget.ui.AlertView;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@LocationPreference(availableOnBackground = true)
public class RouteFootRideMapPage extends AbstractBaseMapPage<RouteFootRidePresenter> implements IVoiceCmdResponder, LocationIgnore, edd, b, defpackage.eev.a {
    public eeq a;
    public AlertView b;
    public Button c;
    public eev d;
    public View e;
    public View f;
    public RidePullSectionCustomView g;
    public long h;
    public long i = 0;
    public String j = "0";
    public String k = "1";
    public String l = "2";
    public boolean m;
    public boolean n = false;
    private OnClickListener o = new a(this, 0);
    private eeu p;
    private LayoutInflater q;
    private View r;
    /* access modifiers changed from: private */
    public View s;
    private ImageView t;
    private ImageView u;
    private Timer v = null;
    private TimerTask w;
    private long x;

    class a implements OnClickListener {
        private a() {
        }

        /* synthetic */ a(RouteFootRideMapPage routeFootRideMapPage, byte b) {
            this();
        }

        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.btn_start) {
                RouteFootRideMapPage.this.i();
            } else if (id == R.id.btn_exit_done) {
                if (TextUtils.equals((CharSequence) RouteFootRideMapPage.this.c.getTag(), RouteFootRideMapPage.this.l)) {
                    RouteFootRideMapPage.this.h();
                    RouteFootRideMapPage.this.finish();
                    return;
                }
                RouteFootRideMapPage.this.a();
            } else if (id == R.id.setting && RouteFootRideMapPage.this.s == null) {
                RouteFootRideMapPage.this.b(true);
                RouteFootRideMapPage.this.d.a();
            }
        }
    }

    public long getScene() {
        return IjkMediaMeta.AV_CH_SURROUND_DIRECT_LEFT;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        bdf bdf = (bdf) defpackage.esb.a.a.a(bdf.class);
        Class a2 = bdf != null ? bdf.b().a(3) : null;
        if (Boolean.parseBoolean(ehs.b("share_bike_riding_status_id"))) {
            finish();
            if (NetworkReachability.b()) {
                startPage(bdf != null ? bdf.b().a(1) : null, (PageBundle) null);
            } else {
                startPage(a2, (PageBundle) null);
            }
        }
        if (bdf != null && bdf.a().b()) {
            finish();
            startPage(a2, (PageBundle) null);
        }
        requestScreenOrientation(1);
        setContentView(R.layout.route_foot_ride_page_fragment);
        View contentView = getContentView();
        if (contentView != null && euk.a()) {
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + euk.a(getContext()), contentView.getPaddingRight(), contentView.getPaddingBottom());
        }
        this.r = contentView;
        this.c = (Button) contentView.findViewById(R.id.btn_start);
        NoDBClickUtil.a((View) this.c, this.o);
        this.t = (ImageView) contentView.findViewById(R.id.btn_exit_done);
        NoDBClickUtil.a((View) this.t, this.o);
        this.u = (ImageView) contentView.findViewById(R.id.gps_status);
        NoDBClickUtil.a((View) (ImageView) contentView.findViewById(R.id.setting), this.o);
        this.g = (RidePullSectionCustomView) contentView.findViewById(R.id.pull_section_custom_id);
        this.g.setmOnDownListener(new com.autonavi.minimap.route.ride.view.RidePullSectionCustomView.a() {
            public final void a() {
                RouteFootRideMapPage.this.d();
            }
        });
        b(false);
        ((RouteFootRidePresenter) this.mPresenter).n = this.d.b();
        this.p = new eeu(contentView);
        this.p.i = this;
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("bundle_key_page_from")) {
            String string = arguments.getString("bundle_key_page_from");
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("from", string);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00276", "B002", jSONObject);
        }
        ebm.a(getString(R.string.voice_log_ride_health_navi));
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public RouteFootRidePresenter createPresenter() {
        return new RouteFootRidePresenter(this);
    }

    public View getMapSuspendView() {
        this.a = new eeq(this);
        this.a.b.c = new defpackage.eep.a() {
            public final void a(boolean z) {
                RouteFootRidePresenter routeFootRidePresenter = (RouteFootRidePresenter) RouteFootRideMapPage.this.mPresenter;
                GeoPoint i = routeFootRidePresenter.i();
                if (i != null) {
                    ((RouteFootRideMapPage) routeFootRidePresenter.mPage).getMapView().a(500, -9999.0f, z ? 0 : routeFootRidePresenter.h(), z ? 0 : 39, i.x, i.y, false);
                }
            }
        };
        return this.a.a;
    }

    public final void a(int i2) {
        Drawable drawable;
        switch (i2) {
            case 1:
                drawable = ContextCompat.getDrawable(AMapPageUtil.getAppContext(), R.drawable.run_gps_strong);
                break;
            case 2:
            case 3:
                drawable = ContextCompat.getDrawable(AMapPageUtil.getAppContext(), R.drawable.run_gps_weak);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(AMapPageUtil.getAppContext(), R.drawable.run_gps_no);
                break;
            default:
                drawable = null;
                break;
        }
        this.u.setImageDrawable(drawable);
        if (i2 > 1) {
            ((RouteFootRidePresenter) this.mPresenter).g();
        }
    }

    public final void a(String str) {
        if (this.m) {
            this.g.setSpeed(efv.b(0.0d));
        } else {
            this.g.setSpeed(str);
        }
    }

    public final void b() {
        HealthPoint[] healthPointArr;
        ((RouteFootRidePresenter) this.mPresenter).e();
        if (this.b != null && isViewLayerShowing(this.b)) {
            dismissViewLayer(this.b);
        }
        this.x = System.currentTimeMillis();
        h();
        TraceStatistics j2 = ((RouteFootRidePresenter) this.mPresenter).j();
        long j3 = this.h;
        long j4 = this.x;
        RideTraceHistory rideTraceHistory = new RideTraceHistory();
        rideTraceHistory.e = j2.average_speed;
        rideTraceHistory.d = j2.calorie;
        rideTraceHistory.b = (int) j2.trace_time;
        rideTraceHistory.c = j2.trace_length;
        rideTraceHistory.g = j3;
        rideTraceHistory.h = j4;
        rideTraceHistory.f = j2.max_speed;
        ArrayList<com.autonavi.minimap.route.ride.beans.RideTraceHistory.a> arrayList = new ArrayList<>();
        if (j2.gps_array != null) {
            for (HealthPoint healthPoint : j2.gps_array) {
                com.autonavi.minimap.route.ride.beans.RideTraceHistory.a aVar = new com.autonavi.minimap.route.ride.beans.RideTraceHistory.a();
                POIBase pOIBase = new POIBase();
                pOIBase.setPoint(new GeoPoint(healthPoint.longitude, healthPoint.latitude));
                aVar.a = pOIBase;
                aVar.b = healthPoint.status == HealthPointStatus.HPS_PAUSE ? 1 : 0;
                aVar.c = (int) healthPoint.speed;
                arrayList.add(aVar);
            }
        }
        b bVar = new b();
        bVar.e = arrayList;
        bVar.a = null;
        bVar.b = null;
        bVar.c = null;
        bVar.d = false;
        rideTraceHistory.j = bVar;
        rideTraceHistory.i = ees.a();
        rideTraceHistory.k = RideType.RIDE_TYPE;
        StringBuilder sb = new StringBuilder();
        sb.append(rideTraceHistory.g);
        sb.append(Token.SEPARATOR);
        sb.append(rideTraceHistory.h);
        rideTraceHistory.a = agy.a(sb.toString());
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("data", rideTraceHistory);
        pageBundle.putBoolean("isfromRidePage", true);
        pageBundle.putInt("where", 2);
        defpackage.eao.a.b();
        finish();
        pageBundle.putString("bundle_key_page_jump_from", "PAGE_JUMP_FROM_RIDE");
        startPage(RideFinishMapPage.class, pageBundle);
    }

    public final boolean b(String str) {
        if (this.e == null) {
            this.q = LayoutInflater.from(AMapPageUtil.getAppContext());
            this.e = this.q.inflate(R.layout.ride_map_toast_layout, null);
            if (this.e == null) {
                return false;
            }
            ((FrameLayout) this.r).addView(this.e);
            if (!(this.f == null || this.f.getVisibility() == 8)) {
                this.f.bringToFront();
                this.f.requestLayout();
            }
        }
        ((TextView) this.e.findViewById(R.id.auto_stop_tv_toast)).setText(str);
        return true;
    }

    public final boolean c() {
        if (this.e != null) {
            ((FrameLayout) this.r).removeView(this.e);
            this.e = null;
        }
        return true;
    }

    public final void d() {
        if (this.s != null && this.s.isShown()) {
            ((FrameLayout) this.r).removeView(this.s);
            this.s = null;
        }
    }

    public final void a(boolean z) {
        ((RouteFootRidePresenter) this.mPresenter).n = z;
        if (!z) {
            PlaySoundUtils.getInstance().clear();
        }
    }

    public final void e() {
        if (this.e != null) {
            this.e.setVisibility(0);
        }
    }

    public final void f() {
        if (!isViewLayerShowing(this.b)) {
            g();
        }
        ((RouteFootRidePresenter) this.mPresenter).b();
    }

    /* access modifiers changed from: private */
    public void b(boolean z) {
        if (this.d == null) {
            this.q = LayoutInflater.from(AMapPageUtil.getAppContext());
            this.f = this.q.inflate(R.layout.ride_nav_setting, null);
            this.d = new eev(this, this.f);
            this.d.a = this;
            ((FrameLayout) this.r).addView(this.f);
        }
        if (this.e != null && this.e.isShown()) {
            this.e.setVisibility(4);
        }
        this.f.setVisibility(z ? 0 : 8);
    }

    public final void g() {
        if (this.v == null) {
            this.v = new Timer();
            this.w = new TimerTask() {
                public final void run() {
                    RouteFootRideMapPage.this.i = RouteFootRideMapPage.this.i + 1;
                    String a2 = efv.a(RouteFootRideMapPage.this.i);
                    Handler handler = ((RouteFootRidePresenter) RouteFootRideMapPage.this.mPresenter).m;
                    if (handler != null) {
                        handler.sendMessage(handler.obtainMessage(7, a2));
                    }
                }
            };
            this.v.schedule(this.w, new Date(), 1000);
        }
    }

    public final void h() {
        if (this.w != null) {
            this.w.cancel();
        }
        if (this.v != null) {
            this.v.cancel();
            this.v = null;
        }
    }

    public final void i() {
        if (TextUtils.equals((String) this.c.getTag(), this.j) || TextUtils.equals((String) this.c.getTag(), this.l)) {
            this.m = false;
            LogManager.actionLogV2("P00274", "B001");
            this.c.setBackgroundResource(R.drawable.route_run_btn_pause_selector);
            if (TextUtils.equals((String) this.c.getTag(), this.l)) {
                dys.a("P00274", "B002");
                eeu eeu = this.p;
                eeu.h.setVisibility(0);
                eeu.a(eeu.g);
                TextView textView = eeu.f;
                textView.setVisibility(0);
                d dVar = new d(eeu, 0);
                defpackage.eeu.a aVar = new defpackage.eeu.a(eeu, 0);
                eeu.a = new AnimatorSet();
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.2f, 1.0f});
                ofFloat.setDuration(200);
                ofFloat.setInterpolator(new AccelerateInterpolator(1.0f));
                ofFloat.addUpdateListener(dVar);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(textView, "alpha", new float[]{0.0f, 0.76f});
                ofFloat2.setDuration(500);
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(textView, "alpha", new float[]{0.76f, 0.0f});
                ofFloat3.setDuration(500);
                ofFloat3.setStartDelay(500);
                ValueAnimator ofFloat4 = ValueAnimator.ofFloat(new float[]{1.0f, 0.6f});
                ofFloat4.setDuration(800);
                ofFloat4.setStartDelay(200);
                ofFloat4.setInterpolator(new DecelerateInterpolator(1.0f));
                ofFloat4.addUpdateListener(dVar);
                eeu.a.playTogether(new Animator[]{ofFloat, ofFloat2, ofFloat3, ofFloat4});
                eeu.a.addListener(aVar);
                eeu.a.start();
                eeu.b = new AnimatorSet();
                ValueAnimator ofFloat5 = ValueAnimator.ofFloat(new float[]{0.2f, 1.0f});
                ofFloat5.setDuration(200);
                ofFloat5.setInterpolator(new AccelerateInterpolator(2.0f));
                ofFloat5.addUpdateListener(dVar);
                ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(textView, "alpha", new float[]{0.0f, 0.76f});
                ofFloat6.setDuration(500);
                ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(textView, "alpha", new float[]{0.76f, 0.0f});
                ofFloat7.setDuration(500);
                ofFloat7.setStartDelay(500);
                ValueAnimator ofFloat8 = ValueAnimator.ofFloat(new float[]{1.0f, 0.6f});
                ofFloat8.setDuration(800);
                ofFloat8.setStartDelay(200);
                ofFloat8.setInterpolator(new DecelerateInterpolator(1.0f));
                ofFloat8.addUpdateListener(dVar);
                eeu.b.playTogether(new Animator[]{ofFloat5, ofFloat6, ofFloat7, ofFloat8});
                eeu.b.addListener(aVar);
                eeu.b.setStartDelay(1000);
                eeu.b.start();
                eeu.c = new AnimatorSet();
                ValueAnimator ofFloat9 = ValueAnimator.ofFloat(new float[]{0.2f, 1.0f});
                ofFloat9.setDuration(200);
                ofFloat9.setInterpolator(new AccelerateInterpolator(1.0f));
                ofFloat9.addUpdateListener(dVar);
                ObjectAnimator ofFloat10 = ObjectAnimator.ofFloat(textView, "alpha", new float[]{0.0f, 0.76f});
                ofFloat10.setDuration(500);
                ObjectAnimator ofFloat11 = ObjectAnimator.ofFloat(textView, "alpha", new float[]{0.76f, 0.0f});
                ofFloat11.setDuration(500);
                ofFloat11.setStartDelay(500);
                ValueAnimator ofFloat12 = ValueAnimator.ofFloat(new float[]{1.0f, 0.6f});
                ofFloat12.setDuration(800);
                ofFloat12.setStartDelay(200);
                ofFloat12.setInterpolator(new DecelerateInterpolator(1.0f));
                ofFloat12.addUpdateListener(dVar);
                eeu.c.playTogether(new Animator[]{ofFloat9, ofFloat10, ofFloat11, ofFloat12});
                eeu.c.addListener(aVar);
                eeu.c.setStartDelay(2000);
                eeu.c.start();
                TextView textView2 = eeu.d;
                AnimatorSet animatorSet = new AnimatorSet();
                e eVar = new e(eeu, 0);
                ValueAnimator ofFloat13 = ValueAnimator.ofFloat(new float[]{1.0f, 1.8f});
                ofFloat13.setDuration(500);
                ofFloat13.setInterpolator(new AccelerateInterpolator(1.5f));
                ofFloat13.addUpdateListener(eVar);
                ObjectAnimator ofFloat14 = ObjectAnimator.ofFloat(textView2, "alpha", new float[]{0.4f, 0.0f});
                ofFloat14.setDuration(500);
                animatorSet.playTogether(new Animator[]{ofFloat13, ofFloat14});
                animatorSet.addListener(new AnimatorListener(textView2) {
                    final /* synthetic */ View a;

                    public final void onAnimationCancel(Animator animator) {
                    }

                    public final void onAnimationRepeat(Animator animator) {
                    }

                    {
                        this.a = r2;
                    }

                    public final void onAnimationStart(Animator animator) {
                        this.a.setVisibility(0);
                        AMapLog.i(eeu.j, "wave step 1 start --- >>");
                    }

                    public final void onAnimationEnd(Animator animator) {
                        AMapLog.i(eeu.j, "wave step 1 finish --- >>");
                    }
                });
                animatorSet.start();
                AnimatorSet animatorSet2 = new AnimatorSet();
                ValueAnimator ofFloat15 = ValueAnimator.ofFloat(new float[]{1.0f, 1.8f});
                ofFloat15.setDuration(500);
                ofFloat15.setInterpolator(new AccelerateInterpolator(1.5f));
                ofFloat15.addUpdateListener(eVar);
                ObjectAnimator ofFloat16 = ObjectAnimator.ofFloat(textView2, "alpha", new float[]{0.4f, 0.0f});
                ofFloat16.setDuration(500);
                animatorSet2.playTogether(new Animator[]{ofFloat15, ofFloat16});
                animatorSet2.setStartDelay(1000);
                animatorSet2.addListener(new AnimatorListener() {
                    public final void onAnimationCancel(Animator animator) {
                    }

                    public final void onAnimationRepeat(Animator animator) {
                    }

                    public final void onAnimationStart(Animator animator) {
                        AMapLog.e(eeu.j, "wave step 2 start...");
                    }

                    public final void onAnimationEnd(Animator animator) {
                        AMapLog.e(eeu.j, "wave step 2 start...");
                    }
                });
                animatorSet2.start();
                eeu.e.setVisibility(0);
                AnimatorSet animatorSet3 = new AnimatorSet();
                c cVar = new c(eeu, 0);
                ValueAnimator ofFloat17 = ValueAnimator.ofFloat(new float[]{1.0f, 1.2f});
                ofFloat17.setDuration(200);
                ofFloat17.setInterpolator(new AccelerateInterpolator(1.0f));
                ValueAnimator ofFloat18 = ValueAnimator.ofFloat(new float[]{1.2f, 1.0f});
                ofFloat18.setDuration(800);
                ValueAnimator ofFloat19 = ValueAnimator.ofFloat(new float[]{1.0f, 1.2f});
                ofFloat19.setDuration(200);
                ofFloat19.setInterpolator(new AccelerateInterpolator(1.0f));
                ValueAnimator ofFloat20 = ValueAnimator.ofFloat(new float[]{1.2f, 1.0f});
                ofFloat20.setDuration(800);
                ValueAnimator ofFloat21 = ValueAnimator.ofFloat(new float[]{1.0f, 5.0f});
                ofFloat21.setDuration(200);
                ofFloat21.setInterpolator(new AccelerateInterpolator(1.0f));
                animatorSet3.playSequentially(new Animator[]{ofFloat17, ofFloat18, ofFloat19, ofFloat20, ofFloat21});
                ofFloat17.addUpdateListener(cVar);
                ofFloat18.addUpdateListener(cVar);
                ofFloat19.addUpdateListener(cVar);
                ofFloat20.addUpdateListener(cVar);
                ofFloat21.addUpdateListener(cVar);
                animatorSet3.start();
                requestScreenOn(true);
            } else {
                ((RouteFootRidePresenter) this.mPresenter).d();
                g();
                c();
            }
            this.c.setTag(this.k);
            if (this.h == 0) {
                this.h = System.currentTimeMillis();
            }
        } else if (TextUtils.equals((String) this.c.getTag(), this.k)) {
            this.m = true;
            a(efv.b(0.0d));
            this.c.setTag(this.j);
            this.c.setBackgroundResource(R.drawable.route_run_btn_start_selector);
            ((RouteFootRidePresenter) this.mPresenter).c();
            h();
        }
    }

    public final void a() {
        h();
        this.c.setTag(this.j);
        this.c.setBackgroundResource(R.drawable.route_run_btn_start_selector);
        boolean z = !((RouteFootRidePresenter) this.mPresenter).f();
        com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(AMapPageUtil.getAppContext());
        if (z) {
            aVar.b((CharSequence) AMapPageUtil.getAppContext().getString(R.string.ride_normal_exit_msg_2)).b((CharSequence) AMapPageUtil.getAppContext().getString(R.string.run_keep_running), (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    RouteFootRideMapPage.this.c.performClick();
                    RouteFootRideMapPage.this.dismissViewLayer(alertView);
                }
            }).a((CharSequence) AMapPageUtil.getAppContext().getString(R.string.run_exit), (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    RouteFootRideMapPage.this.b();
                }
            });
        } else {
            aVar.b((CharSequence) AMapPageUtil.getAppContext().getString(R.string.ride_too_short_exit_msg)).b((CharSequence) AMapPageUtil.getAppContext().getString(R.string.run_keep_running), (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    RouteFootRideMapPage.this.c.performClick();
                    RouteFootRideMapPage.this.dismissViewLayer(alertView);
                }
            }).a((CharSequence) AMapPageUtil.getAppContext().getString(R.string.run_exit), (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    ((RouteFootRidePresenter) RouteFootRideMapPage.this.mPresenter).e();
                    PlaySoundUtils.getInstance().clear();
                    RouteFootRideMapPage.this.h();
                    RouteFootRideMapPage.this.dismissViewLayer(alertView);
                    RouteFootRideMapPage.f(RouteFootRideMapPage.this);
                }
            });
        }
        this.b = aVar.a();
        showViewLayer(this.b);
        this.b.startAnimation();
    }

    static /* synthetic */ void f(RouteFootRideMapPage routeFootRideMapPage) {
        defpackage.eao.a.b();
        routeFootRideMapPage.finish();
    }
}
