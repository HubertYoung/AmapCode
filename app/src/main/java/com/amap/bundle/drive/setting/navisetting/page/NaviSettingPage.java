package com.amap.bundle.drive.setting.navisetting.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.ui.TitleBar.a;
import com.autonavi.widget.ui.TitleBar.b;

@PageAction("amap.drive.action.navigation.prefer")
public class NaviSettingPage extends DriveBasePage<qj> {
    public static int f = 1;
    private static int m = 0;
    private static int n = 2;
    public qe a;
    public qh b;
    public qg c;
    public qf d;
    public boolean e = false;
    public int g = 2;
    public int h = 0;
    public a i = new a() {
        public final void a() {
            NaviSettingPage.this.o.setVisibility(0);
        }

        public final boolean b() {
            return NaviSettingPage.this.o.getVisibility() == 0;
        }

        public final void c() {
            NaviSettingPage.this.o.setVisibility(8);
        }
    };
    a j = new a() {
        public final void a(boolean z) {
            if (NaviSettingPage.this.isAlive() && NaviSettingPage.this.p != null && NaviSettingPage.this.l != null && NaviSettingPage.this.h == NaviSettingPage.f) {
                int i = 8;
                NaviSettingPage.this.p.setVisibility(z ? 0 : 8);
                ScrollView f = NaviSettingPage.this.l;
                if (!z) {
                    i = 0;
                }
                f.setVisibility(i);
            }
        }
    };
    private TitleBar k;
    /* access modifiers changed from: private */
    public ScrollView l;
    /* access modifiers changed from: private */
    public RelativeLayout o;
    /* access modifiers changed from: private */
    public LinearLayout p;
    private Button q;
    private pj r;
    private String s = "";
    private boolean t = false;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.navi_setting_layout);
        PageBundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey("amap.extra.prefer.from")) {
            this.g = 2;
        } else {
            this.g = arguments.getInt("amap.extra.prefer.from");
        }
        if (this.g == 1) {
            this.h = m;
            this.e = true;
        } else if (this.g == 3) {
            this.h = f;
            this.e = true;
        } else if (this.g == 4) {
            this.h = n;
            this.e = true;
        }
        this.s = DriveUtil.getMotorInfo();
        this.t = DriveUtil.isMotorAvoidLimitedPath();
        if (this.e) {
            this.k = (TitleBar) findViewById(R.id.navi_setting_single_bar);
            this.k.setTitle(this.g == 3 ? "货车导航设置" : "导航设置");
            this.k.setOnBackClickListener((a) new a() {
                public final void onClick(TitleBar titleBar, int i) {
                    NaviSettingPage.this.a();
                }
            });
            findViewById(R.id.navi_setting_title_bar).setVisibility(8);
        } else {
            this.k = (TitleBar) findViewById(R.id.navi_setting_title_bar);
            this.k.addTab(new b((CharSequence) "小客车"), true);
            this.k.addTab(new b((CharSequence) "货车"), false);
            this.k.setOnTabSelectedListener(new erq() {
                public final void b(int i) {
                }

                public final void a(int i) {
                    NaviSettingPage.this.h = i;
                    NaviSettingPage.this.a(i);
                    NaviSettingPage.this.d();
                    NaviSettingPage.this.d.a();
                }
            });
            findViewById(R.id.navi_setting_single_bar).setVisibility(8);
            this.k.setActionImgVisibility(8);
        }
        this.k.setVisibility(0);
        this.k.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (NaviSettingPage.this.isAlive()) {
                    NaviSettingPage.this.finish();
                }
            }
        });
        this.a = new qe(this, getContentView());
        this.b = new qh(this, getContentView());
        this.c = new qg(this, getContentView());
        this.d = new qf(this, getContentView(), this.h, this.g);
        this.b.d = this.j;
        d();
        this.d.a();
        this.a.e = this.i;
        this.o = (RelativeLayout) findViewById(R.id.truck_guide_layout);
        NoDBClickUtil.a((View) this.o, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                NaviSettingPage.this.o.setVisibility(8);
            }
        });
        this.o.setVisibility(8);
        this.p = (LinearLayout) findViewById(R.id.navi_truckguide_layout);
        this.q = (Button) findViewById(R.id.navi_truckguide_addtruck_btn);
        NoDBClickUtil.a((View) this.q, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (NaviSettingPage.this.isAlive()) {
                    DriveUtil.startAddCarPage(2, NaviSettingPage.this.getPageContext());
                }
            }
        });
        this.l = (ScrollView) findViewById(R.id.navi_setting_sv);
        if (this.e) {
            if (this.g == 4) {
                a(n);
            } else if (this.g == 3) {
                a(f);
            } else {
                a(m);
            }
        }
        if (!this.e) {
            Car carTruckInfo = DriveUtil.getCarTruckInfo();
            Car carInfo = DriveUtil.getCarInfo();
            if ((carTruckInfo == null || carInfo == null) && carTruckInfo != null) {
                this.k.setSelectTab(1);
                a(f);
            } else {
                this.k.setSelectTab(0);
                a(m);
            }
        }
        this.r = c();
    }

    private pj c() {
        if (this.g == 1) {
            return this.a.c();
        }
        if (this.g == 4) {
            return qg.b();
        }
        return this.b.b();
    }

    /* access modifiers changed from: private */
    public void d() {
        if (this.d != null) {
            if (this.h == m) {
                this.d.d = 1;
                this.d.e = 0;
            } else if (this.h == f) {
                this.d.d = 3;
                this.d.e = 1;
            } else {
                this.d.d = 4;
                this.d.e = 2;
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        if (i2 == m || i2 == n) {
            this.p.setVisibility(8);
            this.l.setVisibility(0);
        }
        if (i2 == n) {
            this.a.a(8);
            this.b.a(8);
            this.c.a(0);
        } else if (i2 == f) {
            this.c.a(8);
            this.a.a(8);
            this.b.a(0);
        } else {
            this.c.a(8);
            this.b.a(8);
            this.a.a(0);
        }
    }

    public final void a() {
        PageBundle pageBundle = new PageBundle();
        pj c2 = c();
        StringBuilder sb = new StringBuilder("Last: ");
        sb.append(this.s);
        sb.append(" Now: ");
        sb.append(DriveUtil.getMotorInfo());
        AMapLog.d("Daniel-27", sb.toString());
        boolean z = true;
        if ((this.h != n || (this.s.equals(DriveUtil.getMotorInfo()) && this.t == DriveUtil.isMotorAvoidLimitedPath())) && c2.equals(this.r)) {
            z = false;
        }
        pageBundle.putBoolean("setting_selected_has_Changed", z);
        setResult(ResultType.OK, pageBundle);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new qj(this);
    }
}
