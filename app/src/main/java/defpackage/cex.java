package defpackage;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/* renamed from: cex reason: default package */
/* compiled from: TripGuideManager */
public final class cex {
    public static a a;
    String b;
    boolean c = false;
    LottieAnimationView d;
    LinearLayout e;
    final AnimatorSet f = new AnimatorSet();
    final AnimatorSet g = new AnimatorSet();
    final AnimatorSet h = new AnimatorSet();
    RelativeLayout i;
    ImageView j;
    ImageView k;
    TextView l;
    final Handler m = new b(this, 0);
    private Activity n;
    private cey o;
    private AnimatorSet p = new AnimatorSet();
    private RelativeLayout q;
    private RelativeLayout r;

    /* renamed from: cex$a */
    /* compiled from: TripGuideManager */
    public interface a {
        void a();
    }

    /* renamed from: cex$b */
    /* compiled from: TripGuideManager */
    static class b extends Handler {
        private WeakReference<cex> a;

        /* synthetic */ b(cex cex, byte b) {
            this(cex);
        }

        private b(cex cex) {
            this.a = new WeakReference<>(cex);
        }

        public final void handleMessage(@NotNull Message message) {
            if (this.a != null) {
                cex cex = (cex) this.a.get();
                if (cex != null && message.what == 1) {
                    cex.i.setVisibility(0);
                }
            }
        }
    }

    public cex(Activity activity, cey cey) {
        this.n = activity;
        this.o = cey;
    }

    public final void a() {
        this.n.getWindow().getDecorView().setBackgroundColor(-1182466);
        this.n.getWindow().setBackgroundDrawable(null);
        this.n.setContentView(R.layout.trip_guide_dialog_layout);
        this.d = (LottieAnimationView) this.n.findViewById(R.id.trip_choose_lottie);
        this.d.setImageAssetsFolder("preference_images");
        c();
        this.n.findViewById(R.id.skip_btn).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!"0".equals(cex.this.b)) {
                    cex.this.b = "0";
                    if (cex.this.d.isAnimating()) {
                        cex.this.d.cancelAnimation();
                    }
                    cex.a(cex.this);
                }
            }
        });
        this.i = (RelativeLayout) this.n.findViewById(R.id.trip_choose_btn_layout);
        this.n.findViewById(R.id.trip_choose_btn).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!"0".equals(cex.this.b)) {
                    if (cex.this.h.isRunning()) {
                        cex.this.h.cancel();
                    }
                    if (cex.this.f.isRunning()) {
                        cex.this.f.cancel();
                    }
                    if (cex.this.g.isRunning()) {
                        cex.this.g.cancel();
                    }
                    cex.a(cex.this);
                }
            }
        });
        this.e = (LinearLayout) this.n.findViewById(R.id.guide_layout);
        this.j = (ImageView) this.n.findViewById(R.id.trip_car_choose);
        this.k = (ImageView) this.n.findViewById(R.id.trip_bus_choose);
        this.j.setAlpha(0.2f);
        this.k.setAlpha(0.2f);
        this.q = (RelativeLayout) this.n.findViewById(R.id.trip_car_layout);
        this.r = (RelativeLayout) this.n.findViewById(R.id.trip_bus_layout);
        this.q.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!cex.this.c && !"0".equals(cex.this.b) && !"1".equals(cex.this.b)) {
                    cex.this.l.setText(R.string.trip_guide_car);
                    cex.this.c = true;
                    if (TextUtils.isEmpty(cex.this.b)) {
                        cex.this.i.setVisibility(0);
                        cex.this.h.start();
                        cex.this.j.setAlpha(1.0f);
                        cex.this.k.setAlpha(1.0f);
                    }
                    cex.this.b = "1";
                    cex.this.d.setMinAndMaxProgress(0.34f, 0.66f);
                    cex.this.g.start();
                }
            }
        });
        this.r.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (!cex.this.c && !"0".equals(cex.this.b) && !"2".equals(cex.this.b)) {
                    cex.this.l.setText(R.string.trip_guide_bus);
                    cex.this.c = true;
                    if (TextUtils.isEmpty(cex.this.b)) {
                        cex.this.i.setVisibility(0);
                        cex.this.h.start();
                        cex.this.j.setAlpha(1.0f);
                        cex.this.k.setAlpha(1.0f);
                    }
                    cex.this.b = "2";
                    cex.this.d.setMinAndMaxProgress(0.67f, 1.0f);
                    cex.this.f.start();
                }
            }
        });
        this.l = (TextView) this.n.findViewById(R.id.trip_guide_tip);
        b();
    }

    private void b() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.i, "alpha", new float[]{0.0f, 1.0f});
        ofFloat.setDuration(500);
        this.h.play(ofFloat);
        this.h.addListener(new AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
                Message obtainMessage = cex.this.m.obtainMessage();
                obtainMessage.what = 1;
                cex.this.m.sendMessage(obtainMessage);
            }

            public final void onAnimationEnd(Animator animator) {
                Message obtainMessage = cex.this.m.obtainMessage();
                obtainMessage.what = 2;
                cex.this.m.sendMessage(obtainMessage);
            }
        });
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.q, "scaleX", new float[]{1.0f, 0.98f});
        ofFloat2.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.q, "scaleY", new float[]{1.0f, 0.98f});
        ofFloat3.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.q, "alpha", new float[]{1.0f, 0.5f});
        ofFloat4.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(this.q, "scaleX", new float[]{1.0f, 1.02f});
        ofFloat5.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(this.q, "scaleY", new float[]{1.0f, 1.02f});
        ofFloat6.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(this.q, "alpha", new float[]{0.5f, 1.0f});
        ofFloat7.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(this.r, "scaleX", new float[]{1.0f, 0.98f});
        ofFloat8.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat9 = ObjectAnimator.ofFloat(this.r, "scaleY", new float[]{1.0f, 0.98f});
        ofFloat9.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat10 = ObjectAnimator.ofFloat(this.r, "alpha", new float[]{1.0f, 0.5f});
        ofFloat10.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat11 = ObjectAnimator.ofFloat(this.r, "scaleX", new float[]{1.0f, 1.02f});
        ofFloat11.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat12 = ObjectAnimator.ofFloat(this.r, "scaleY", new float[]{1.0f, 1.02f});
        ofFloat12.setInterpolator(new DecelerateInterpolator());
        ObjectAnimator ofFloat13 = ObjectAnimator.ofFloat(this.r, "alpha", new float[]{0.5f, 1.0f});
        ofFloat13.setInterpolator(new DecelerateInterpolator());
        this.g.play(ofFloat5).with(ofFloat6).with(ofFloat7).with(ofFloat8).with(ofFloat9).with(ofFloat10);
        this.g.setDuration(200);
        this.g.addListener(new AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
            }

            public final void onAnimationEnd(Animator animator) {
                cex.this.c = false;
            }
        });
        this.f.play(ofFloat11).with(ofFloat12).with(ofFloat13).with(ofFloat2).with(ofFloat3).with(ofFloat4);
        this.f.setDuration(200);
        this.f.addListener(new AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
            }

            public final void onAnimationEnd(Animator animator) {
                cex.this.c = false;
            }
        });
        float translationY = this.e.getTranslationY();
        ObjectAnimator ofFloat14 = ObjectAnimator.ofFloat(this.e, "translationY", new float[]{40.0f + translationY, translationY});
        ofFloat14.setStartDelay(300);
        ofFloat14.setDuration(500);
        ObjectAnimator ofFloat15 = ObjectAnimator.ofFloat(this.e, "alpha", new float[]{0.0f, 1.0f});
        ofFloat15.setStartDelay(300);
        ofFloat15.setDuration(500);
        ObjectAnimator ofFloat16 = ObjectAnimator.ofFloat(this.d, "scaleX", new float[]{1.1f, 1.0f});
        ofFloat16.setDuration(1000);
        ObjectAnimator ofFloat17 = ObjectAnimator.ofFloat(this.d, "scaleY", new float[]{1.1f, 1.0f});
        ofFloat17.setDuration(1000);
        ObjectAnimator ofFloat18 = ObjectAnimator.ofFloat(this.d, "alpha", new float[]{0.0f, 1.0f});
        ofFloat18.setDuration(1000);
        this.p.play(ofFloat16).with(ofFloat17).with(ofFloat18).with(ofFloat14).with(ofFloat15);
        ofFloat15.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                cex.this.d.setVisibility(0);
                cex.this.e.setVisibility(0);
            }
        });
        this.d.setMinAndMaxProgress(0.0f, 0.33f);
        this.d.playAnimation();
        this.p.start();
    }

    private void c() {
        DisplayMetrics displayMetrics = this.n.getBaseContext().getResources().getDisplayMetrics();
        float f2 = displayMetrics.density;
        int i2 = displayMetrics.heightPixels;
        int d2 = ags.d(this.n);
        LayoutParams layoutParams = (LayoutParams) this.d.getLayoutParams();
        layoutParams.setMargins(0, d2, 0, 0);
        this.d.setLayoutParams(layoutParams);
        float f3 = f2 * 16.0f;
        ((LayoutParams) this.n.findViewById(R.id.skip_btn).getLayoutParams()).setMargins(0, (int) (((float) d2) + f3), (int) f3, 0);
    }

    static /* synthetic */ void a(cex cex) {
        cex.o.a.edit().putBoolean("trip_guide_dialog_show_flag", true).apply();
        bim.aa().c((String) "602", cex.b);
        cey cey = cex.o;
        String str = cex.b;
        if (cey.b == null) {
            cey.b = new MapSharePreference((String) "basemap");
        }
        cey.b.putStringValue("userIndividualityType", str);
        String str2 = cex.b;
        if (!TextUtils.isEmpty(str2)) {
            if ("1".equals(str2)) {
                bim.aa().a((String) "101", 0);
            } else if ("2".equals(str2)) {
                bim.aa().a((String) "101", 2);
            }
        }
        String str3 = cex.b;
        String str4 = LogConstant.SPLASH_SCREEN_SKIPPED;
        if (str3.equals("1")) {
            str4 = "car";
        } else if (str3.equals("2")) {
            str4 = ShowRouteActionProcessor.SEARCH_TYPE_BUS;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", str4);
            LogManager.actionLogV2("P00001", LogConstant.MAIN_TRIP_TOOL_SELECTED, jSONObject);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        HashMap hashMap = new HashMap();
        hashMap.put("type", String.valueOf(str4));
        kd.a((String) "amap.P00001.0.B292", (Map<String, String>) hashMap);
        if (!"0".equals(cex.b)) {
            Intent intent = new Intent("com.autonavi.minimap.action.TRIP_TOOL_SELECTED");
            intent.setPackage("com.autonavi.minimap");
            cex.n.sendBroadcast(intent);
        }
        if (a != null) {
            a.a();
        }
    }
}
