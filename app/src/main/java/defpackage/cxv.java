package defpackage;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.commonui.lottie.LottieView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.featureguide.api.GuideStartType;
import com.autonavi.minimap.bundle.featureguide.bean.LottieData;
import com.autonavi.minimap.bundle.featureguide.widget.SplashyFragment;
import com.autonavi.minimap.bundle.featureguide.widget.SplashyFragment.PAGE_TYPE;
import com.autonavi.minimap.bundle.featureguide.widget.SplashyFragmentPagerAdapter2;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cxv reason: default package */
/* compiled from: GuideSplashManager */
public class cxv implements cxs {
    /* access modifiers changed from: private */
    public static boolean q = false;
    public cxu a;
    boolean b;
    boolean c;
    private Activity d;
    private FragmentManager e;
    private cxt f = new cxt();
    /* access modifiers changed from: private */
    public ViewPager g;
    /* access modifiers changed from: private */
    public SplashyFragmentPagerAdapter2 h;
    private GuideStartType i = GuideStartType.DEFAULT;
    private GuideStartType j = GuideStartType.DEFAULT;
    private LinearLayout k;
    /* access modifiers changed from: private */
    public View[] l;
    private a[] m;
    private int n;
    /* access modifiers changed from: private */
    public int o;
    private int p = 8;
    /* access modifiers changed from: private */
    public View r;
    private TextView s;
    private TextView t;
    private TextView u;
    private Button v;
    private JSONObject w = null;
    private List<LottieData> x = new ArrayList();
    private String y = "lottie";
    private String z = "config.json";

    /* renamed from: cxv$a */
    /* compiled from: GuideSplashManager */
    static class a {
        public String a;
        public String b;
        public String c;

        public a(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }
    }

    public cxv(Activity activity) {
        this.d = activity;
    }

    public final void a(FragmentManager fragmentManager) {
        this.e = fragmentManager;
    }

    public final void a(GuideStartType guideStartType) {
        this.i = guideStartType;
    }

    public final void c() {
        q = false;
        this.c = true;
        if (this.g != null) {
            this.g.removeAllViews();
            this.g.destroyDrawingCache();
            this.g = null;
        }
        this.d = null;
        this.e = null;
        this.i = null;
        this.j = null;
    }

    public final void a() {
        File file;
        String str;
        q = true;
        this.d.getWindow().getDecorView().setBackgroundColor(-1182466);
        this.d.getWindow().setBackgroundDrawable(null);
        this.d.setContentView(R.layout.v6_splash_guide);
        this.r = this.d.findViewById(R.id.title_layout);
        this.k = (LinearLayout) this.d.findViewById(R.id.pos_points_layout);
        this.s = (TextView) this.d.findViewById(R.id.title_textView);
        this.t = (TextView) this.d.findViewById(R.id.subTitle_textView);
        this.u = (TextView) this.d.findViewById(R.id.bottomTitle_textView);
        this.v = (Button) this.d.findViewById(R.id.button_startMap);
        this.v.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (cxv.this.h != null) {
                    SplashyFragmentPagerAdapter2 a2 = cxv.this.h;
                    if (a2.b != null) {
                        ((SplashyFragment) a2.b).onGoMapClick();
                    }
                    View childAt = cxv.this.g.getChildAt(cxv.this.g.getCurrentItem());
                    if (childAt != null) {
                        View findViewById = childAt.findViewById(R.id.lottieView);
                        if (findViewById != null) {
                            LottieView lottieView = (LottieView) findViewById;
                            if (lottieView.isAnimating()) {
                                lottieView.cancelPlay();
                            }
                        }
                    }
                }
            }
        });
        this.g = (ViewPager) this.d.findViewById(R.id.viewpager);
        this.g.setDrawingCacheEnabled(false);
        this.g.setPersistentDrawingCache(0);
        this.g.setOnPageChangeListener(new OnPageChangeListener() {
            public final void onPageScrollStateChanged(int i) {
                if (i == 0) {
                    cxv.a(cxv.this, cxv.this.g.getCurrentItem());
                }
            }

            public final void onPageScrolled(int i, float f, int i2) {
                if (f > 0.0f && cxv.this.l != null) {
                    boolean z = i == cxv.this.o;
                    if (z && cxv.this.l.length - 1 == cxv.this.o) {
                        return;
                    }
                    if (z || cxv.this.o != 0) {
                        cxv cxv = cxv.this;
                        if (!z) {
                            i++;
                        }
                        cxv.a(cxv, i, f, z);
                    }
                }
            }

            public final void onPageSelected(int i) {
                cxv.b(i);
            }
        });
        this.g.setOnKeyListener(new OnKeyListener() {
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == 4) {
                    cxv.q = false;
                }
                return false;
            }
        });
        this.h = new SplashyFragmentPagerAdapter2(this.d, this.e);
        View findViewById = this.d.findViewById(R.id.userControlArea_layout);
        findViewById.measure(0, 0);
        this.h.c = findViewById.getMeasuredHeight();
        this.g.setAdapter(this.h);
        b(0);
        switch (this.i) {
            case DEFAULT:
                try {
                    if (bno.a) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(FileUtil.getCacheDir().getAbsolutePath());
                        sb.append(File.separator);
                        sb.append(this.y);
                        str = sb.toString();
                        file = new File(str);
                    } else {
                        str = null;
                        file = null;
                    }
                    if (file == null || !file.exists()) {
                        str = this.y;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(this.y);
                        sb2.append(File.separator);
                        sb2.append(this.z);
                        this.w = b(sb2.toString());
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str);
                        sb3.append(File.separator);
                        sb3.append(this.z);
                        this.w = new JSONObject(FileUtil.readData(sb3.toString()));
                    }
                    a(str);
                    if (this.x.size() > 0) {
                        this.f.a = this.x;
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                if (this.f.a == null) {
                    ArrayList arrayList = new ArrayList();
                    Resources resources = this.d.getResources();
                    for (int i2 = 1; i2 <= 5; i2++) {
                        int identifier = resources.getIdentifier(String.format("splash_page%s_body", new Object[]{Integer.valueOf(i2)}), ResUtils.DRAWABLE, this.d.getPackageName());
                        if (identifier > 0) {
                            arrayList.add(Integer.valueOf(identifier));
                        }
                    }
                    if (arrayList.size() > 0) {
                        this.f.b = arrayList;
                    }
                }
                if (!(this.d != null && this.f.a == null && this.f.b == null)) {
                    SplashyFragmentPagerAdapter2 splashyFragmentPagerAdapter2 = this.h;
                    cxt cxt = this.f;
                    splashyFragmentPagerAdapter2.a.clear();
                    List<LottieData> list = cxt.a;
                    if (list == null || list.size() <= 0) {
                        List list2 = cxt.b;
                        for (int i3 = 0; i3 < list2.size(); i3++) {
                            com.autonavi.minimap.bundle.featureguide.widget.SplashyFragmentPagerAdapter2.a aVar = new com.autonavi.minimap.bundle.featureguide.widget.SplashyFragmentPagerAdapter2.a();
                            aVar.h = true;
                            Object obj = list2.get(i3);
                            if (obj instanceof Integer) {
                                aVar.b = ((Integer) obj).intValue();
                            } else if (obj instanceof String) {
                                aVar.c = (String) obj;
                            }
                            aVar.a = R.layout.guide_view;
                            aVar.g = PAGE_TYPE.DEFAULT;
                            aVar.d = null;
                            aVar.e = 0;
                            aVar.f = false;
                            splashyFragmentPagerAdapter2.a.add(aVar);
                        }
                    } else {
                        for (LottieData lottieData : list) {
                            com.autonavi.minimap.bundle.featureguide.widget.SplashyFragmentPagerAdapter2.a aVar2 = new com.autonavi.minimap.bundle.featureguide.widget.SplashyFragmentPagerAdapter2.a();
                            aVar2.h = true;
                            aVar2.a = R.layout.guide_view;
                            aVar2.g = PAGE_TYPE.DEFAULT;
                            aVar2.i = lottieData;
                            aVar2.d = null;
                            aVar2.e = 0;
                            aVar2.f = false;
                            splashyFragmentPagerAdapter2.a.add(aVar2);
                        }
                    }
                    this.j = GuideStartType.DEFAULT;
                    break;
                }
                break;
            case BETA:
                SplashyFragmentPagerAdapter2 splashyFragmentPagerAdapter22 = this.h;
                splashyFragmentPagerAdapter22.a.clear();
                com.autonavi.minimap.bundle.featureguide.widget.SplashyFragmentPagerAdapter2.a aVar3 = new com.autonavi.minimap.bundle.featureguide.widget.SplashyFragmentPagerAdapter2.a();
                aVar3.a = R.layout.splashy_view_3;
                aVar3.g = PAGE_TYPE.LAST;
                aVar3.b = R.drawable.auto_navi_logo;
                splashyFragmentPagerAdapter22.a.add(aVar3);
                this.j = GuideStartType.BETA;
                break;
        }
        this.h.notifyDataSetChanged();
        try {
            this.n = this.h.getCount();
            this.g.setOffscreenPageLimit(this.n - 1);
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            List<LottieData> list3 = this.f.a;
            if (list3 != null && list3.size() > 0) {
                for (int i4 = 0; i4 < list3.size(); i4++) {
                    arrayList2.add(list3.get(i4).a);
                    arrayList3.add(list3.get(i4).b);
                    arrayList4.add(list3.get(i4).c);
                }
            }
            int size = arrayList2.size();
            if (size == this.n) {
                if (arrayList2.size() == arrayList3.size()) {
                    this.m = new a[size];
                    boolean z2 = false;
                    for (int i5 = 0; i5 < size; i5++) {
                        String str2 = (String) arrayList4.get(i5);
                        if (!z2 && !TextUtils.isEmpty(str2)) {
                            z2 = true;
                        }
                        this.m[i5] = new a((String) arrayList2.get(i5), (String) arrayList3.get(i5), (String) arrayList4.get(i5));
                    }
                    this.s.setText(this.m[0].a);
                    this.t.setText(this.m[0].b);
                    this.u.setText(this.m[0].c);
                    this.s.setVisibility(0);
                    this.t.setVisibility(0);
                    if (z2) {
                        this.u.setVisibility(0);
                    }
                    if (this.n >= 2) {
                        this.l = new View[this.n];
                        int dimensionPixelOffset = this.d.getResources().getDimensionPixelOffset(R.dimen.guide_dot_width_6dp);
                        LayoutParams layoutParams = new LayoutParams(dimensionPixelOffset, dimensionPixelOffset);
                        layoutParams.rightMargin = this.d.getResources().getDimensionPixelOffset(R.dimen.guide_dot_margin_right);
                        this.k.removeAllViews();
                        for (int i6 = 0; i6 < this.n; i6++) {
                            this.l[i6] = new View(this.d);
                            this.l[i6].setLayoutParams(layoutParams);
                            this.l[i6].setBackgroundResource(R.drawable.guide_pos_points);
                            this.l[i6].setAlpha(0.2f);
                            this.l[i6].setTag(Integer.valueOf(i6));
                            this.k.addView(this.l[i6], i6);
                        }
                        this.o = 0;
                        this.l[this.o].setAlpha(1.0f);
                        return;
                    }
                    return;
                }
            }
            throw new RuntimeException("Guide Title Count is miss or surplus");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public static void b(int i2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("action", String.valueOf(i2 + 1));
            LogManager.actionLogV2(LogConstant.PAGE_GUIDE, "B001", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final boolean b() {
        return q;
    }

    private void a(String str) {
        if (this.w != null) {
            JSONArray optJSONArray = this.w.optJSONArray("lottie");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                    JSONObject optJSONObject = optJSONArray.optJSONObject(i2);
                    if (optJSONObject != null) {
                        LottieData lottieData = new LottieData();
                        lottieData.a = optJSONObject.optString("mainTitle");
                        lottieData.b = optJSONObject.optString("subTitle");
                        lottieData.c = optJSONObject.optString("bottomTitle");
                        lottieData.f = optJSONObject.optDouble("frontAnimationDuration");
                        lottieData.g = optJSONObject.optDouble("loopingAnimationDuration");
                        lottieData.h = optJSONObject.optDouble("transitionAnimationDuration");
                        lottieData.i = optJSONObject.optDouble("totalDuration");
                        boolean z2 = true;
                        if (optJSONObject.optInt("autoPlay") != 1) {
                            z2 = false;
                        }
                        lottieData.j = z2;
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(optJSONObject.optString("resource"));
                        sb.append(File.separator);
                        lottieData.e = sb.toString();
                        if (TextUtils.equals(this.y, str)) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(lottieData.e);
                            sb2.append(optJSONObject.optString("json"));
                            lottieData.d = sb2.toString();
                        } else {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(lottieData.e);
                            sb3.append(optJSONObject.optString("json"));
                            lottieData.d = FileUtil.readData(sb3.toString());
                        }
                        this.x.add(lottieData);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0050 A[SYNTHETIC, Splitter:B:32:0x0050] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0058 A[Catch:{ Throwable -> 0x0054 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0063 A[SYNTHETIC, Splitter:B:42:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x006b A[Catch:{ Throwable -> 0x0067 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.json.JSONObject b(java.lang.String r7) {
        /*
            r6 = this;
            r0 = 0
            android.app.Activity r1 = r6.d     // Catch:{ Throwable -> 0x0048, all -> 0x0043 }
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch:{ Throwable -> 0x0048, all -> 0x0043 }
            java.io.InputStream r7 = r1.open(r7)     // Catch:{ Throwable -> 0x0048, all -> 0x0043 }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0040, all -> 0x003b }
            r1.<init>()     // Catch:{ Throwable -> 0x0040, all -> 0x003b }
            r2 = 4096(0x1000, float:5.74E-42)
            byte[] r2 = new byte[r2]     // Catch:{ Throwable -> 0x0039 }
        L_0x0014:
            int r3 = r7.read(r2)     // Catch:{ Throwable -> 0x0039 }
            r4 = -1
            if (r3 == r4) goto L_0x0020
            r4 = 0
            r1.write(r2, r4, r3)     // Catch:{ Throwable -> 0x0039 }
            goto L_0x0014
        L_0x0020:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0039 }
            java.lang.String r3 = r1.toString()     // Catch:{ Throwable -> 0x0039 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0039 }
            if (r7 == 0) goto L_0x0031
            r7.close()     // Catch:{ Throwable -> 0x002f }
            goto L_0x0031
        L_0x002f:
            r7 = move-exception
            goto L_0x0035
        L_0x0031:
            r1.close()     // Catch:{ Throwable -> 0x002f }
            goto L_0x0038
        L_0x0035:
            r7.printStackTrace()
        L_0x0038:
            return r2
        L_0x0039:
            r2 = move-exception
            goto L_0x004b
        L_0x003b:
            r1 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x0061
        L_0x0040:
            r2 = move-exception
            r1 = r0
            goto L_0x004b
        L_0x0043:
            r7 = move-exception
            r1 = r0
            r0 = r7
            r7 = r1
            goto L_0x0061
        L_0x0048:
            r2 = move-exception
            r7 = r0
            r1 = r7
        L_0x004b:
            r2.printStackTrace()     // Catch:{ all -> 0x0060 }
            if (r7 == 0) goto L_0x0056
            r7.close()     // Catch:{ Throwable -> 0x0054 }
            goto L_0x0056
        L_0x0054:
            r7 = move-exception
            goto L_0x005c
        L_0x0056:
            if (r1 == 0) goto L_0x005f
            r1.close()     // Catch:{ Throwable -> 0x0054 }
            goto L_0x005f
        L_0x005c:
            r7.printStackTrace()
        L_0x005f:
            return r0
        L_0x0060:
            r0 = move-exception
        L_0x0061:
            if (r7 == 0) goto L_0x0069
            r7.close()     // Catch:{ Throwable -> 0x0067 }
            goto L_0x0069
        L_0x0067:
            r7 = move-exception
            goto L_0x006f
        L_0x0069:
            if (r1 == 0) goto L_0x0072
            r1.close()     // Catch:{ Throwable -> 0x0067 }
            goto L_0x0072
        L_0x006f:
            r7.printStackTrace()
        L_0x0072:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cxv.b(java.lang.String):org.json.JSONObject");
    }

    static /* synthetic */ void a(cxv cxv, int i2) {
        if (i2 >= 0) {
            try {
                if (i2 <= cxv.n - 1) {
                    for (View alpha : cxv.l) {
                        alpha.setAlpha(0.2f);
                    }
                    cxv.l[i2].setAlpha(1.0f);
                    if (cxv.o == i2) {
                        cxv.r.setAlpha(1.0f);
                        return;
                    }
                    View childAt = cxv.g.getChildAt(cxv.g.getCurrentItem());
                    if (childAt != null) {
                        LottieView lottieView = (LottieView) childAt.findViewById(R.id.lottieView);
                        if (lottieView.getVisibility() == 0 && !lottieView.isAnimating()) {
                            lottieView.startPlay(true);
                            int childCount = cxv.g.getChildCount();
                            for (int i3 = 0; i3 < childCount; i3++) {
                                if (i3 != i2) {
                                    View childAt2 = cxv.g.getChildAt(i3);
                                    if (childAt2 != null) {
                                        LottieView lottieView2 = (LottieView) childAt2.findViewById(R.id.lottieView);
                                        if (lottieView2.isAnimating()) {
                                            lottieView2.cancelPlay();
                                            lottieView2.setInitialProgress();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    cxv.s.setText(cxv.m[i2].a);
                    cxv.t.setText(cxv.m[i2].b);
                    cxv.u.setText(cxv.m[i2].c);
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setDuration(500);
                    alphaAnimation.setAnimationListener(new AnimationListener() {
                        public final void onAnimationEnd(Animation animation) {
                        }

                        public final void onAnimationRepeat(Animation animation) {
                        }

                        public final void onAnimationStart(Animation animation) {
                            cxv.this.r.setAlpha(1.0f);
                        }
                    });
                    cxv.r.startAnimation(alphaAnimation);
                    cxv.o = i2;
                    if (cxv.b && cxv.a != null && cxv.a.errorCode == 1 && !TextUtils.isEmpty(cxv.a.c) && !TextUtils.isEmpty(cxv.a.b) && cxv.h.b != null) {
                        ((SplashyFragment) cxv.h.b).setAppMessage(cxv.a);
                        ((SplashyFragment) cxv.h.b).setAppIcon(cxv.a);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    static /* synthetic */ void a(cxv cxv, int i2, float f2, boolean z2) {
        if (z2) {
            float f3 = 1.0f - f2;
            if (i2 == cxv.o) {
                cxv.r.setAlpha(f3);
            }
            cxv.l[i2].setAlpha(1.2f - f2);
            int i3 = i2 + 1;
            if (i3 < cxv.l.length) {
                cxv.l[i3].setAlpha(f2 + 0.2f);
            }
            return;
        }
        if (i2 == cxv.o) {
            cxv.r.setAlpha(f2);
        }
        cxv.l[i2].setAlpha(f2 + 0.2f);
        int i4 = i2 - 1;
        if (i4 >= 0) {
            cxv.l[i4].setAlpha((1.0f - f2) + 0.2f);
        }
    }
}
