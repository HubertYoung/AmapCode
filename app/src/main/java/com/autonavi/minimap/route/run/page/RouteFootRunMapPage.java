package com.autonavi.minimap.route.run.page;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.datamodel.poi.POIBase;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.route.health.HealthPoint;
import com.autonavi.jni.route.health.HealthPointStatus;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.map.core.LocationMode.LocationIgnore;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.run.beans.RunTraceHistory;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.RunType;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.b;
import com.autonavi.minimap.route.run.presenter.RouteFootRunPresenter;
import com.autonavi.minimap.route.run.view.PullSectionCustomView;
import com.autonavi.widget.ui.AlertView;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@LocationPreference(availableOnBackground = true)
public class RouteFootRunMapPage extends AbstractBaseMapPage<RouteFootRunPresenter> implements IVoiceCmdResponder, LocationIgnore, edd, b, defpackage.efz.a {
    public efq a;
    public AlertView b;
    public Button c;
    public efz d;
    public View e;
    public View f;
    public PullSectionCustomView g;
    public long h;
    public String i = "0";
    public String j = "1";
    public String k = "2";
    public boolean l = false;
    private OnClickListener m = new a(this, 0);
    private efy n;
    private LayoutInflater o;
    private View p;
    /* access modifiers changed from: private */
    public View q;
    private ImageView r;
    private Timer s = null;
    private TimerTask t;
    /* access modifiers changed from: private */
    public long u;
    /* access modifiers changed from: private */
    public long v = 0;

    class a implements OnClickListener {
        private a() {
        }

        /* synthetic */ a(RouteFootRunMapPage routeFootRunMapPage, byte b) {
            this();
        }

        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.btn_start) {
                RouteFootRunMapPage.this.i();
            } else if (id == R.id.btn_exit_done) {
                if (TextUtils.equals((CharSequence) RouteFootRunMapPage.this.c.getTag(), RouteFootRunMapPage.this.k)) {
                    RouteFootRunMapPage.this.g();
                    RouteFootRunMapPage.this.finish();
                    return;
                }
                RouteFootRunMapPage.this.a();
            } else if (id == R.id.setting && RouteFootRunMapPage.this.q == null) {
                RouteFootRunMapPage.this.b(true);
                RouteFootRunMapPage.this.d.a();
            }
        }
    }

    public long getScene() {
        return 17179869184L;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.route_foot_run_page_fragment);
        View contentView = getContentView();
        if (contentView != null && euk.a()) {
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + euk.a(getContext()), contentView.getPaddingRight(), contentView.getPaddingBottom());
        }
        this.p = contentView;
        this.c = (Button) contentView.findViewById(R.id.btn_start);
        NoDBClickUtil.a((View) this.c, this.m);
        NoDBClickUtil.a((View) (ImageView) contentView.findViewById(R.id.btn_exit_done), this.m);
        this.r = (ImageView) contentView.findViewById(R.id.gps_status);
        NoDBClickUtil.a((View) (ImageView) contentView.findViewById(R.id.setting), this.m);
        this.g = (PullSectionCustomView) contentView.findViewById(R.id.pull_section_custom_id);
        this.g.setmOnDownListener(new com.autonavi.minimap.route.run.view.PullSectionCustomView.a() {
            public final void a() {
                RouteFootRunMapPage.this.c();
            }
        });
        b(false);
        if (this.d != null) {
            ((RouteFootRunPresenter) this.mPresenter).q = this.d.b();
        }
        this.n = new efy(contentView);
        this.n.i = this;
        ebm.a(getString(R.string.voice_log_run_health_navi));
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public RouteFootRunPresenter createPresenter() {
        return new RouteFootRunPresenter(this);
    }

    public View getMapSuspendView() {
        this.a = new efq(this);
        this.a.b.c = new defpackage.efr.a() {
            public final void a(boolean z) {
                RouteFootRunPresenter routeFootRunPresenter = (RouteFootRunPresenter) RouteFootRunMapPage.this.mPresenter;
                GeoPoint m = routeFootRunPresenter.m();
                if (m != null) {
                    int l = routeFootRunPresenter.l();
                    routeFootRunPresenter.e();
                    routeFootRunPresenter.j();
                    ((RouteFootRunMapPage) routeFootRunPresenter.mPage).getMapView().a(500, routeFootRunPresenter.o > 0.0f ? routeFootRunPresenter.o : -9999.0f, z ? 0 : l, z ? 0 : 39, m.x, m.y, false);
                }
                if (RouteFootRunMapPage.this.a.b()) {
                    RouteFootRunMapPage.this.a.c();
                }
            }
        };
        if (((RouteFootRunPresenter) this.mPresenter).o()) {
            efq efq = this.a;
            efq.d = new MvpImageView(efq.e);
            efq.c = new efs();
            efq.c.a(efq.d);
            efq.d.setContentDescription("全览/退出全览");
            int a2 = agn.a(efq.e, 2.0f);
            efq.d.setPadding(a2, a2, a2, a2);
            LayoutParams a3 = efq.a();
            int i2 = efq.f;
            a3.rightMargin = i2;
            a3.bottomMargin = i2;
            efq.a.addView(efq.d, a3, 6);
            this.a.c.c = new defpackage.efs.a() {
                public final void a(boolean z) {
                    if (z) {
                        ((RouteFootRunPresenter) RouteFootRunMapPage.this.mPresenter).b();
                    } else {
                        ((RouteFootRunPresenter) RouteFootRunMapPage.this.mPresenter).c();
                    }
                }
            };
        }
        return this.a.a;
    }

    public final void a(int i2) {
        Drawable drawable;
        switch (i2) {
            case 1:
                drawable = getResources().getDrawable(R.drawable.run_gps_strong);
                break;
            case 2:
            case 3:
                drawable = getResources().getDrawable(R.drawable.run_gps_weak);
                break;
            case 4:
                drawable = getResources().getDrawable(R.drawable.run_gps_no);
                break;
            default:
                drawable = null;
                break;
        }
        this.r.setImageDrawable(drawable);
    }

    public final boolean a(String str) {
        if (this.e == null) {
            this.o = LayoutInflater.from(getContext());
            this.e = this.o.inflate(R.layout.running_map_toast_layout, null);
            if (this.e == null) {
                return false;
            }
            ((FrameLayout) this.p).addView(this.e);
            if (!(this.f == null || this.f.getVisibility() == 8)) {
                this.f.bringToFront();
                this.f.requestLayout();
            }
        }
        ((TextView) this.e.findViewById(R.id.auto_stop_tv_toast)).setText(str);
        return true;
    }

    public final boolean b() {
        if (this.e != null) {
            ((FrameLayout) this.p).removeView(this.e);
            this.e = null;
        }
        return true;
    }

    public final void c() {
        if (this.q != null && this.q.isShown()) {
            ((FrameLayout) this.p).removeView(this.q);
            this.q = null;
        }
    }

    public final void a(boolean z) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("action", z ? 1 : 0);
            LogManager.actionLogV2("P00269", "B007", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        ((RouteFootRunPresenter) this.mPresenter).q = z;
        if (!z) {
            PlaySoundUtils.getInstance().clear();
        }
    }

    public final void d() {
        if (this.e != null) {
            this.e.setVisibility(0);
        }
    }

    public final void e() {
        if (isAlive()) {
            if (!isViewLayerShowing(this.b)) {
                f();
            }
            ((RouteFootRunPresenter) this.mPresenter).f();
            ((RouteFootRunPresenter) this.mPresenter).d();
        }
    }

    /* access modifiers changed from: private */
    public void b(boolean z) {
        if (this.d == null) {
            this.o = LayoutInflater.from(getContext());
            this.f = this.o.inflate(R.layout.running_nav_setting, null);
            this.d = new efz(this, this.f);
            this.d.a = this;
            ((FrameLayout) this.p).addView(this.f);
        }
        if (this.e != null && this.e.isShown()) {
            this.e.setVisibility(4);
        }
        this.f.setVisibility(z ? 0 : 8);
    }

    public final void f() {
        if (this.s == null) {
            this.s = new Timer();
            this.t = new TimerTask() {
                public final void run() {
                    RouteFootRunMapPage.this.v = RouteFootRunMapPage.this.v + 1;
                    String a2 = efv.a(RouteFootRunMapPage.this.v);
                    Handler handler = ((RouteFootRunPresenter) RouteFootRunMapPage.this.mPresenter).p;
                    if (handler != null) {
                        handler.sendMessage(handler.obtainMessage(7, a2));
                    }
                }
            };
            this.s.schedule(this.t, new Date(), 1000);
        }
    }

    public final void g() {
        if (this.t != null) {
            this.t.cancel();
        }
        if (this.s != null) {
            this.s.cancel();
            this.s = null;
        }
    }

    public final boolean h() {
        return this.a != null && this.a.b();
    }

    public final void i() {
        if (TextUtils.equals((String) this.c.getTag(), this.i) || TextUtils.equals((String) this.c.getTag(), this.k)) {
            if (!((RouteFootRunPresenter) this.mPresenter).n) {
                LogManager.actionLogV2("P00269", "B002");
            }
            this.c.setBackgroundResource(R.drawable.route_run_btn_pause_selector);
            if (TextUtils.equals((String) this.c.getTag(), this.k)) {
                dys.a("P00269", "B011");
                efy efy = this.n;
                efy.h.setVisibility(0);
                efy.a(efy.g);
                TextView textView = efy.f;
                textView.setVisibility(0);
                d dVar = new d(efy, 0);
                defpackage.efy.a aVar = new defpackage.efy.a(efy, 0);
                efy.a = new AnimatorSet();
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
                efy.a.playTogether(new Animator[]{ofFloat, ofFloat2, ofFloat3, ofFloat4});
                efy.a.addListener(aVar);
                efy.a.start();
                efy.b = new AnimatorSet();
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
                efy.b.playTogether(new Animator[]{ofFloat5, ofFloat6, ofFloat7, ofFloat8});
                efy.b.addListener(aVar);
                efy.b.setStartDelay(1000);
                efy.b.start();
                efy.c = new AnimatorSet();
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
                efy.c.playTogether(new Animator[]{ofFloat9, ofFloat10, ofFloat11, ofFloat12});
                efy.c.addListener(aVar);
                efy.c.setStartDelay(2000);
                efy.c.start();
                TextView textView2 = efy.d;
                AnimatorSet animatorSet = new AnimatorSet();
                e eVar = new e(efy, 0);
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
                        AMapLog.i(efy.j, "wave step 1 start --- >>");
                    }

                    public final void onAnimationEnd(Animator animator) {
                        AMapLog.i(efy.j, "wave step 1 finish --- >>");
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
                        AMapLog.e(efy.j, "wave step 2 start...");
                    }

                    public final void onAnimationEnd(Animator animator) {
                        AMapLog.e(efy.j, "wave step 2 start...");
                    }
                });
                animatorSet2.start();
                efy.e.setVisibility(0);
                AnimatorSet animatorSet3 = new AnimatorSet();
                c cVar = new c(efy, 0);
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
                ((RouteFootRunPresenter) this.mPresenter).h();
                f();
                b();
            }
            this.c.setTag(this.j);
            if (this.h == 0) {
                this.h = System.currentTimeMillis();
            }
        } else if (TextUtils.equals((String) this.c.getTag(), this.j)) {
            this.c.setTag(this.i);
            this.c.setBackgroundResource(R.drawable.route_run_btn_start_selector);
            LogManager.actionLogV2("P00269", "B003");
            ((RouteFootRunPresenter) this.mPresenter).g();
            g();
        }
    }

    public final void a() {
        g();
        this.c.setTag(this.i);
        this.c.setBackgroundResource(R.drawable.route_run_btn_start_selector);
        boolean z = !((RouteFootRunPresenter) this.mPresenter).k();
        com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
        if (z) {
            aVar.b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.run_normal_exit_msg)).b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.run_keep_running), (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    RouteFootRunMapPage.this.c.performClick();
                    RouteFootRunMapPage.this.dismissViewLayer(alertView);
                }
            }).a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.run_exit), (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    ((RouteFootRunPresenter) RouteFootRunMapPage.this.mPresenter).i();
                    RouteFootRunMapPage.this.dismissViewLayer(alertView);
                    RouteFootRunMapPage.this.u = System.currentTimeMillis();
                    RouteFootRunMapPage.this.g();
                    TraceStatistics n = ((RouteFootRunPresenter) RouteFootRunMapPage.this.mPresenter).n();
                    long g = RouteFootRunMapPage.this.h;
                    long h = RouteFootRunMapPage.this.u;
                    RunTraceHistory runTraceHistory = new RunTraceHistory();
                    runTraceHistory.e = (double) ((int) n.average_speed);
                    runTraceHistory.d = n.calorie == 0 ? 1 : n.calorie;
                    runTraceHistory.b = (int) n.trace_time;
                    runTraceHistory.c = n.trace_length;
                    runTraceHistory.f = g;
                    runTraceHistory.g = h;
                    ArrayList<com.autonavi.minimap.route.run.beans.RunTraceHistory.a> arrayList = new ArrayList<>();
                    ArrayList<Double> arrayList2 = new ArrayList<>();
                    if (n.gps_array != null) {
                        HealthPoint[] healthPointArr = n.gps_array;
                        for (HealthPoint healthPoint : healthPointArr) {
                            com.autonavi.minimap.route.run.beans.RunTraceHistory.a aVar = new com.autonavi.minimap.route.run.beans.RunTraceHistory.a();
                            POIBase pOIBase = new POIBase();
                            pOIBase.setPoint(new GeoPoint(healthPoint.longitude, healthPoint.latitude));
                            aVar.a = pOIBase;
                            aVar.b = healthPoint.status == HealthPointStatus.HPS_PAUSE ? 1 : 0;
                            arrayList.add(aVar);
                            arrayList2.add(Double.valueOf(healthPoint.speed));
                        }
                    }
                    b bVar = new b();
                    bVar.e = arrayList;
                    bVar.f = arrayList2;
                    bVar.a = null;
                    bVar.b = null;
                    bVar.c = null;
                    bVar.d = false;
                    runTraceHistory.i = bVar;
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder("runshot");
                    sb2.append(System.currentTimeMillis());
                    sb.append(agy.a(sb2.toString()));
                    sb.append(".png");
                    runTraceHistory.h = sb.toString();
                    runTraceHistory.j = RunType.RUN_TYPE;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(runTraceHistory.f);
                    sb3.append(Token.SEPARATOR);
                    sb3.append(runTraceHistory.g);
                    runTraceHistory.a = agy.a(sb3.toString());
                    efu.a(runTraceHistory);
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("data", runTraceHistory);
                    pageBundle.putBoolean("isfromRunPage", true);
                    pageBundle.putBoolean("isfromRecommend", ((RouteFootRunPresenter) RouteFootRunMapPage.this.mPresenter).n);
                    RouteFootRunMapPage.j(RouteFootRunMapPage.this);
                    LogManager.actionLogV2("P00270", "B003");
                    RouteFootRunMapPage.this.startPage(RunFinishMapPage.class, pageBundle);
                }
            });
        } else {
            aVar.b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.run_too_short_exit_msg)).b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.run_keep_running), (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    RouteFootRunMapPage.this.c.performClick();
                    RouteFootRunMapPage.this.dismissViewLayer(alertView);
                }
            }).a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.run_exit), (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    ((RouteFootRunPresenter) RouteFootRunMapPage.this.mPresenter).i();
                    PlaySoundUtils.getInstance().clear();
                    RouteFootRunMapPage.this.g();
                    RouteFootRunMapPage.this.dismissViewLayer(alertView);
                    RouteFootRunMapPage.j(RouteFootRunMapPage.this);
                }
            });
        }
        this.b = aVar.a();
        showViewLayer(this.b);
        this.b.startAnimation();
    }

    static /* synthetic */ void j(RouteFootRunMapPage routeFootRunMapPage) {
        defpackage.eao.a.b();
        routeFootRunMapPage.finish();
    }
}
