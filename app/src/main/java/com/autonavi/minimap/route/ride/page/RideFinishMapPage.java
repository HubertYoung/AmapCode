package com.autonavi.minimap.route.ride.page;

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
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType;
import com.autonavi.minimap.route.ride.page.RideFinishMapPage;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class RideFinishMapPage extends AbstractBaseMapPage<eem> implements OnClickListener, OnTouchListener, OnPreDrawListener, LocationNone {
    public LinearLayout a;
    public ProgressDlg b;
    public View c;
    public LinearLayout d;
    public TextView e;
    public TextView f;
    public TextView g;
    public TextView h;
    public TextView i;
    public View j;
    private ImageView k;
    private ImageView l;
    private ImageButton m;
    private TextView n;
    /* access modifiers changed from: private */
    public TextView o;
    private TextView p;
    private MvpImageView q;
    private LinearLayout r;
    private coi s = new coi() {
        public final void doReportError(String str) {
            IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
            if (iErrorReportStarter != null) {
                PageBundle a2 = ebc.a(RideFinishMapPage.this.getContext(), ((eem) RideFinishMapPage.this.mPresenter).b);
                a2.putString("error_pic_path", str);
                a2.putInt("sourcepage", 35);
                a2.putString("naviid", ((eem) RideFinishMapPage.this.mPresenter).j);
                a2.putString("car_used", ebm.c() ? "3" : "1");
                iErrorReportStarter.startFeedback(a2);
            }
        }
    };

    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public eem createPresenter() {
        return new eem(this);
    }

    public View getMapSuspendView() {
        ccv ccv = new ccv(AMapPageUtil.getAppContext());
        this.q = new MvpImageView(AMapPageUtil.getAppContext());
        this.q.setScaleType(ScaleType.CENTER_INSIDE);
        this.q.setImageResource(R.drawable.icon_c18_selector);
        this.q.setBackgroundResource(R.drawable.rt_bus_around_refresh_bg_selector);
        this.q.setContentDescription("报错");
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        LayoutParams layoutParams = new LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.rightMargin = agn.a(AMapPageUtil.getAppContext(), 9.0f);
        layoutParams.topMargin = agn.a(AMapPageUtil.getAppContext(), 9.0f);
        ccv.addWidget(this.q, layoutParams, 4);
        this.q.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RideFinishMapPage.a(RideFinishMapPage.this);
            }
        });
        this.q.setVisibility(8);
        return ccv.getSuspendView();
    }

    public final void a(boolean z) {
        if (this.q != null) {
            this.q.setVisibility(z ? 0 : 8);
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.ride_finish_map_layout);
        View contentView = getContentView();
        if (contentView != null && euk.a()) {
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + euk.a(getContext()), contentView.getPaddingRight(), contentView.getPaddingBottom());
        }
        this.a = (LinearLayout) findViewById(R.id.run_distance_bottom_view);
        if (this.a != null) {
            this.a.setOnTouchListener(this);
        }
        this.k = (ImageView) findViewById(R.id.shareButton);
        NoDBClickUtil.a((View) this.k, (OnClickListener) this);
        this.e = (TextView) findViewById(R.id.run_distance);
        this.f = (TextView) findViewById(R.id.run_time);
        this.g = (TextView) findViewById(R.id.run_carlor);
        this.h = (TextView) findViewById(R.id.run_speed);
        this.i = (TextView) findViewById(R.id.run_share_time);
        this.l = (ImageView) findViewById(R.id.img_logo);
        efx.a(this.f);
        efx.a(this.e);
        efx.a(this.h);
        efx.a(this.g);
        a(this.i);
        this.i.setVisibility(8);
        this.c = contentView.findViewById(R.id.run_finish_title);
        this.c.setOnTouchListener(this);
        this.d = (LinearLayout) contentView.findViewById(R.id.rainbow_flag);
        this.m = (ImageButton) contentView.findViewById(R.id.route_title_back);
        this.n = (TextView) contentView.findViewById(R.id.route_title_run);
        a(this.n);
        this.o = (TextView) contentView.findViewById(R.id.route_title_foot_from);
        this.p = (TextView) contentView.findViewById(R.id.route_title_foot_to);
        a(this.o);
        a(this.p);
        this.r = (LinearLayout) contentView.findViewById(R.id.share_logo);
        NoDBClickUtil.a((View) this.m, (OnClickListener) this);
        this.j = contentView.findViewById(R.id.line_division);
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
        int id = view.getId();
        if (id == R.id.shareButton) {
            eem eem = (eem) this.mPresenter;
            RideFinishMapPage rideFinishMapPage = (RideFinishMapPage) eem.mPage;
            String string = ((RideFinishMapPage) eem.mPage).getString(R.string.route_foot_navi_end_getting_share_content);
            try {
                rideFinishMapPage.a();
                if (TextUtils.isEmpty(string)) {
                    string = "";
                }
                rideFinishMapPage.b = new ProgressDlg(rideFinishMapPage.getActivity(), string, "");
                rideFinishMapPage.b.setCancelable(false);
                rideFinishMapPage.b.show();
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
            eem.a();
            ebr.a(true).postDelayed(new Runnable() {
                public final void run() {
                    ((RideFinishMapPage) eem.this.mPage).getMapView().a(((RideFinishMapPage) eem.this.mPage).getMapView().al(), ((RideFinishMapPage) eem.this.mPage).getMapView().am(), (defpackage.bty.a) new defpackage.bty.a() {
                        public final void onCallBack(final Bitmap bitmap) {
                            ebr.a(true).post(new Runnable() {
                                public final void run() {
                                    eem.a(eem.this, bitmap);
                                }
                            });
                        }
                    });
                }
            }, 500);
            if (((eem) this.mPresenter).h) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", ebm.c() ? "ddc" : "zxc");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                LogManager.actionLogV2("P00271", "B001", jSONObject);
            } else if (((eem) this.mPresenter).i) {
                LogManager.actionLogV2("P00328", "B001");
            }
        } else if (id == R.id.route_title_back) {
            b();
        }
    }

    public final void b() {
        int i2;
        PageBundle arguments = getArguments();
        if (arguments == null || arguments.getBoolean("pagefrom")) {
            i2 = 0;
        } else {
            i2 = arguments.getInt("where");
        }
        switch (i2) {
            case 0:
                finish();
                return;
            case 1:
                finish();
                startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
                break;
            case 2:
                finish();
                return;
        }
    }

    public boolean onPreDraw() {
        int i2;
        if (!isStarted() || !((eem) this.mPresenter).e || this.a == null || !this.a.isShown()) {
            return true;
        }
        this.a.getViewTreeObserver().removeOnPreDrawListener(this);
        eem eem = (eem) this.mPresenter;
        int height = ((RideFinishMapPage) eem.mPage).a.getHeight();
        if (height < 10) {
            height = 738;
        }
        int height2 = ((RideFinishMapPage) eem.mPage).c.getHeight();
        if (height2 < 10) {
            height2 = 132;
        }
        int a2 = height + agn.a(AMapPageUtil.getAppContext(), 52.0f);
        int height3 = (((ags.a(AMapPageUtil.getAppContext()).height() - a2) - height2) - ((int) (((float) ags.a(AMapPageUtil.getAppContext()).width()) * 0.75f))) / 2;
        int a3 = agn.a(AMapPageUtil.getAppContext(), 20.0f);
        int a4 = agn.a(AMapPageUtil.getAppContext(), 10.0f);
        if (eem.b == null || eem.b.k != RideType.RIDE_TYPE) {
            i2 = agn.a(AMapPageUtil.getAppContext(), 50.0f);
        } else {
            i2 = a4;
        }
        eem.a.a(a3, height2 + height3 + i2, a3, a2 + height3 + a4);
        getMapView().X();
        ((eem) this.mPresenter).a();
        ((eem) this.mPresenter).c = true;
        int i3 = 0;
        getMapView().f(false);
        eem eem2 = (eem) this.mPresenter;
        if (eem2.b != null) {
            RideFinishMapPage rideFinishMapPage = (RideFinishMapPage) eem2.mPage;
            String a5 = efv.a((long) eem2.b.b);
            efx.a(rideFinishMapPage.f);
            TextView textView = rideFinishMapPage.f;
            efx.a();
            efx.a(a5, textView);
            rideFinishMapPage.f.setText(a5);
            if (eem2.b.e >= eem2.b.f) {
                eem2.b.f = eem2.b.e * 1.12d;
            }
            RideFinishMapPage rideFinishMapPage2 = (RideFinishMapPage) eem2.mPage;
            String b2 = efv.b(eem2.b.f);
            TextView textView2 = rideFinishMapPage2.h;
            efx.a();
            efx.a(b2, textView2);
            efx.a(rideFinishMapPage2.h);
            rideFinishMapPage2.h.setText(b2);
            RideFinishMapPage rideFinishMapPage3 = (RideFinishMapPage) eem2.mPage;
            int i4 = eem2.b.c;
            String[] a6 = efv.a(i4);
            StringBuilder sb = new StringBuilder();
            sb.append(a6[0]);
            sb.append(a6[1]);
            String sb2 = sb.toString();
            SpannableString spannableString = new SpannableString(sb2);
            spannableString.setSpan(new AbsoluteSizeSpan(agn.a(AMapPageUtil.getAppContext(), 24.0f)), a6[0].length(), sb2.length(), 33);
            rideFinishMapPage3.e.setText(spannableString);
            String str = efv.a(i4)[0];
            TextView textView3 = rideFinishMapPage3.e;
            efx.a();
            efx.a(str, textView3);
            RideFinishMapPage rideFinishMapPage4 = (RideFinishMapPage) eem2.mPage;
            String b3 = efv.b(eem2.b.e);
            TextView textView4 = rideFinishMapPage4.g;
            efx.a();
            efx.a(b3, textView4);
            efx.a(rideFinishMapPage4.g);
            rideFinishMapPage4.g.setText(b3);
            String str2 = eem2.b.l;
            String str3 = eem2.b.m;
            if (!(eem2.b.j == null || eem2.b.j.a == null)) {
                str2 = eem2.b.j.a.getName();
            }
            if (!(eem2.b.j == null || eem2.b.j.b == null)) {
                str3 = eem2.b.j.b.getName();
            }
            RideFinishMapPage rideFinishMapPage5 = (RideFinishMapPage) eem2.mPage;
            String b4 = efv.b(eem2.b.h);
            boolean z = eem2.b.k == RideType.RIDE_TYPE;
            rideFinishMapPage5.n.setText(b4);
            if (z) {
                rideFinishMapPage5.p.setVisibility(8);
                rideFinishMapPage5.o.setVisibility(8);
            } else {
                rideFinishMapPage5.p.setVisibility(0);
                rideFinishMapPage5.o.setVisibility(0);
                if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                    rideFinishMapPage5.o.setText(str2);
                    rideFinishMapPage5.p.setText(str3);
                }
                if (!((!TextUtils.isEmpty(str2) && !rideFinishMapPage5.getString(R.string.route_my_position).equals(str2)) || ((eem) rideFinishMapPage5.mPresenter).b == null || ((eem) rideFinishMapPage5.mPresenter).b.j == null || ((eem) rideFinishMapPage5.mPresenter).b.j.a == null)) {
                    new edc().a(((eem) rideFinishMapPage5.mPresenter).b.j.a.getPoint(), new a() {
                        public final void a(String str) {
                            if (!TextUtils.isEmpty(str)) {
                                RideFinishMapPage.this.o.setText(str);
                            }
                        }
                    });
                }
            }
            RideFinishMapPage rideFinishMapPage6 = (RideFinishMapPage) eem2.mPage;
            boolean z2 = eem2.b.k == RideType.RIDE_TYPE;
            rideFinishMapPage6.d.setVisibility(z2 ? 0 : 8);
            View view = rideFinishMapPage6.j;
            if (z2) {
                i3 = 8;
            }
            view.setVisibility(i3);
            ((RideFinishMapPage) eem2.mPage).i.setText(efv.c(eem2.b.h));
        }
        return true;
    }

    static /* synthetic */ void a(RideFinishMapPage rideFinishMapPage) {
        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
        if (iErrorReportStarter != null) {
            rideFinishMapPage.a(false);
            iErrorReportStarter.doReportError(rideFinishMapPage.getMapManager(), rideFinishMapPage.s);
        }
    }
}
