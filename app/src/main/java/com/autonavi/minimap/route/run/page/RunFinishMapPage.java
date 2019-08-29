package com.autonavi.minimap.route.run.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.RunType;
import com.autonavi.minimap.route.run.page.RunFinishMapPage;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class RunFinishMapPage extends AbstractBaseMapPage<efn> implements OnClickListener, OnTouchListener, OnPreDrawListener {
    public LinearLayout a;
    public ProgressDlg b;
    public View c;
    public LinearLayout d;
    public TextView e;
    public TextView f;
    public TextView g;
    public TextView h;
    public TextView i;
    public TextView j;
    public TextView k;
    public TextView l;
    public TextView m;
    public TextView n;
    public ImageView o;
    private ImageView p;
    private ImageView q;
    private TextView r;
    private LinearLayout s;
    private ImageButton t;

    public View getMapSuspendView() {
        return null;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public efn createPresenter() {
        return new efn(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        eft.a("performance-", "OldFootEndPage  onCreate");
        setContentView(R.layout.run_finish_map_layout);
        View contentView = getContentView();
        if (contentView != null && euk.a()) {
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + euk.a(getContext()), contentView.getPaddingRight(), contentView.getPaddingBottom());
        }
        this.a = (LinearLayout) findViewById(R.id.run_distance_bottom_view);
        if (this.a != null) {
            this.a.setOnTouchListener(this);
        }
        this.p = (ImageView) findViewById(R.id.shareButton);
        if (this.p != null) {
            this.p.setOnClickListener(this);
        }
        this.e = (TextView) findViewById(R.id.run_distance);
        this.f = (TextView) findViewById(R.id.run_time);
        this.g = (TextView) findViewById(R.id.run_carlor);
        this.r = (TextView) findViewById(R.id.run_carlor_unit);
        this.h = (TextView) findViewById(R.id.run_speed);
        this.i = (TextView) findViewById(R.id.run_speed_unit);
        this.m = (TextView) findViewById(R.id.run_share_time);
        this.q = (ImageView) findViewById(R.id.img_logo);
        efx.a(this.f);
        efx.a(this.e);
        efx.a(this.h);
        efx.a(this.g);
        a(this.m);
        this.m.setVisibility(8);
        this.c = contentView.findViewById(R.id.run_finish_title);
        this.c.setOnTouchListener(this);
        this.d = (LinearLayout) contentView.findViewById(R.id.rainbow_flag);
        this.j = (TextView) contentView.findViewById(R.id.route_title_run);
        a(this.j);
        this.k = (TextView) contentView.findViewById(R.id.route_title_foot_from);
        this.l = (TextView) contentView.findViewById(R.id.route_title_foot_to);
        a(this.k);
        a(this.l);
        this.t = (ImageButton) contentView.findViewById(R.id.route_title_back);
        this.t.setOnClickListener(this);
        this.s = (LinearLayout) contentView.findViewById(R.id.share_logo);
        this.n = (TextView) contentView.findViewById(R.id.kcal_text);
        this.o = (ImageView) contentView.findViewById(R.id.run_finish_page_icon);
    }

    private void a(TextView textView) {
        textView.getPaint().setShadowLayer(5.0f, 0.0f, 0.0f, getResources().getColor(R.color.f_c_1));
    }

    public final void a() {
        if (this.b != null) {
            this.b.dismiss();
            this.b = null;
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.shareButton) {
            LogManager.actionLogV2("P00270", "B002");
            efn efn = (efn) this.mPresenter;
            RunFinishMapPage runFinishMapPage = (RunFinishMapPage) efn.mPage;
            String string = ((RunFinishMapPage) efn.mPage).getString(R.string.route_foot_navi_end_getting_share_content);
            try {
                runFinishMapPage.a();
                if (TextUtils.isEmpty(string)) {
                    string = "";
                }
                runFinishMapPage.b = new ProgressDlg(runFinishMapPage.getActivity(), string, "");
                runFinishMapPage.b.setCancelable(false);
                runFinishMapPage.b.show();
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
            efn.a();
            ebr.a(true).postDelayed(new Runnable() {
                public final void run() {
                    ((RunFinishMapPage) efn.this.mPage).getMapView().a(((RunFinishMapPage) efn.this.mPage).getMapView().al(), ((RunFinishMapPage) efn.this.mPage).getMapView().am(), (defpackage.bty.a) new defpackage.bty.a() {
                        public final void onCallBack(final Bitmap bitmap) {
                            ebr.a(true).post(new Runnable() {
                                public final void run() {
                                    efn.a(efn.this, bitmap);
                                }
                            });
                        }
                    });
                }
            }, 500);
        }
        if (view.getId() == R.id.route_title_back) {
            finish();
            b();
        }
    }

    public final void b() {
        if (((efn) this.mPresenter).g) {
            startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_route_type", RouteType.ONFOOT);
            startPage((String) "amap.extra.route.route", pageBundle);
        }
    }

    public boolean onPreDraw() {
        int i2;
        if (!isStarted() || !((efn) this.mPresenter).f || this.a == null || !this.a.isShown()) {
            return true;
        }
        this.a.getViewTreeObserver().removeOnPreDrawListener(this);
        efn efn = (efn) this.mPresenter;
        int height = ((RunFinishMapPage) efn.mPage).a.getHeight();
        if (height < 10) {
            height = 738;
        }
        int height2 = ((RunFinishMapPage) efn.mPage).c.getHeight();
        if (height2 < 10) {
            height2 = 132;
        }
        int a2 = height + agn.a(AMapPageUtil.getAppContext(), 52.0f);
        int height3 = (((ags.a(AMapPageUtil.getAppContext()).height() - a2) - height2) - ((int) (((float) ags.a(AMapPageUtil.getAppContext()).width()) * 0.75f))) / 2;
        int a3 = agn.a(AMapPageUtil.getAppContext(), 20.0f);
        int a4 = agn.a(AMapPageUtil.getAppContext(), 10.0f);
        if (efn.b.j == RunType.RUN_TYPE) {
            i2 = a4;
        } else {
            i2 = agn.a(AMapPageUtil.getAppContext(), 30.0f);
        }
        efl efl = efn.a;
        int i3 = a3 + 50;
        efl.d = i3;
        efl.e = height2 + height3 + i2 + 50;
        efl.f = i3;
        efl.g = a2 + height3 + a4 + 50;
        efl.h = efl.e + efl.g;
        efl.i = efl.d + efl.f;
        getMapView().X();
        ((efn) this.mPresenter).a();
        ((efn) this.mPresenter).d = true;
        getMapView().f(false);
        efn efn2 = (efn) this.mPresenter;
        if (efn2.b != null) {
            RunFinishMapPage runFinishMapPage = (RunFinishMapPage) efn2.mPage;
            String a5 = efv.a((long) efn2.b.b);
            efx.a(runFinishMapPage.f);
            TextView textView = runFinishMapPage.f;
            efx.a();
            efx.a(a5, textView);
            runFinishMapPage.f.setText(a5);
            RunFinishMapPage runFinishMapPage2 = (RunFinishMapPage) efn2.mPage;
            int i4 = efn2.b.d;
            if (i4 > 99999) {
                i4 = 99999;
            }
            String valueOf = String.valueOf(i4);
            TextView textView2 = runFinishMapPage2.g;
            efx.a();
            efx.a(valueOf, textView2);
            runFinishMapPage2.g.setText(String.valueOf(i4));
            RunFinishMapPage runFinishMapPage3 = (RunFinishMapPage) efn2.mPage;
            int i5 = efn2.b.b;
            int i6 = efn2.b.c;
            if (efn2.b.j == RunType.RUN_TYPE) {
                String a6 = efv.a((double) (((float) i5) / (((float) i6) / 1000.0f)));
                TextView textView3 = runFinishMapPage3.h;
                efx.a();
                efx.a(a6, textView3);
                efx.a(runFinishMapPage3.h);
                runFinishMapPage3.h.setText(a6);
                runFinishMapPage3.i.setText(runFinishMapPage3.getString(R.string.foot_end_speed_unit));
            } else {
                runFinishMapPage3.h.setText(String.valueOf((int) (((double) i6) / (((double) i5) / 60.0d))));
                runFinishMapPage3.i.setText(runFinishMapPage3.getString(R.string.foot_end_average_speed_unit));
            }
            RunFinishMapPage runFinishMapPage4 = (RunFinishMapPage) efn2.mPage;
            int i7 = efn2.b.c;
            String[] a7 = efv.a(i7);
            StringBuilder sb = new StringBuilder();
            sb.append(a7[0]);
            sb.append(a7[1]);
            String sb2 = sb.toString();
            SpannableString spannableString = new SpannableString(sb2);
            spannableString.setSpan(new AbsoluteSizeSpan(agn.a(AMapPageUtil.getAppContext(), 24.0f)), a7[0].length(), sb2.length(), 33);
            runFinishMapPage4.e.setText(spannableString);
            String str = efv.a(i7)[0];
            TextView textView4 = runFinishMapPage4.e;
            efx.a();
            efx.a(str, textView4);
            String str2 = "";
            String str3 = "";
            if (!(efn2.b.i == null || efn2.b.i.a == null)) {
                str2 = efn2.b.i.a.getName();
            }
            if (!(efn2.b.i == null || efn2.b.i.b == null)) {
                str3 = efn2.b.i.b.getName();
            }
            RunFinishMapPage runFinishMapPage5 = (RunFinishMapPage) efn2.mPage;
            String b2 = efv.b(efn2.b.g);
            boolean z = efn2.b.j == RunType.RUN_TYPE;
            runFinishMapPage5.j.setText(b2);
            if (z) {
                runFinishMapPage5.l.setVisibility(8);
                runFinishMapPage5.k.setVisibility(8);
            } else {
                runFinishMapPage5.l.setVisibility(0);
                runFinishMapPage5.k.setVisibility(0);
                runFinishMapPage5.k.setText(str2);
                runFinishMapPage5.l.setText(str3);
            }
            ((RunFinishMapPage) efn2.mPage).m.setText(efv.c(efn2.b.g));
            boolean z2 = efn2.b.j == RunType.RUN_TYPE;
            efn2.h = null;
            if (!z2) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(((RunFinishMapPage) efn2.mPage).getString(R.string.foot_end_kcal_text_head));
                sb3.append(ecm.a(efn2.b.d));
                efn2.h = sb3.toString();
            }
            RunFinishMapPage runFinishMapPage6 = (RunFinishMapPage) efn2.mPage;
            String str4 = efn2.h;
            runFinishMapPage6.d.setVisibility(z2 ? 0 : 8);
            runFinishMapPage6.n.setVisibility(z2 ? 8 : 0);
            runFinishMapPage6.o.setImageResource(z2 ? R.drawable.run_finish_page_icon : R.drawable.route_foot_end_icon);
            if (z2 || TextUtils.isEmpty(str4)) {
                runFinishMapPage6.n.setVisibility(8);
            } else {
                runFinishMapPage6.n.setVisibility(0);
                runFinishMapPage6.n.setText(str4);
            }
        }
        return true;
    }
}
