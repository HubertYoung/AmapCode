package defpackage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.drive.setting.navisetting.controller.NaviTruckSettingManager$3;
import com.amap.bundle.drive.setting.navisetting.controller.NaviTruckSettingManager$6;
import com.amap.bundle.drivecommon.route.result.view.RoutingPreferenceView;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.PageBundle;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AmapSwitch;
import java.lang.ref.WeakReference;

/* renamed from: qh reason: default package */
/* compiled from: NaviTruckSettingManager */
public final class qh {
    public WeakReference<AbstractBasePage> a;
    AmapSwitch b;
    AmapSwitch c;
    public a d;
    public boolean e;
    private View f;
    private boolean g;
    private TextView h;
    private TextView i;
    private TextView j;
    private RoutingPreferenceView k;
    private RelativeLayout l;
    private RelativeLayout m;
    private RelativeLayout n;
    private TextView o;
    private LinearLayout p;
    private Car q;
    private String r = "  ";

    /* renamed from: qh$a */
    /* compiled from: NaviTruckSettingManager */
    public interface a {
        void a(boolean z);
    }

    public qh(AbstractBasePage abstractBasePage, View view) {
        this.a = new WeakReference<>(abstractBasePage);
        this.f = view;
        this.g = NetworkReachability.b();
        this.p = (LinearLayout) this.f.findViewById(R.id.truck_layout);
        this.h = (TextView) this.f.findViewById(R.id.truck_info_tv);
        this.i = (TextView) this.f.findViewById(R.id.setting_plate_tv);
        this.j = (TextView) this.f.findViewById(R.id.truck_style_tv);
        this.k = (RoutingPreferenceView) this.f.findViewById(R.id.truck_setting_routing_preference_view);
        this.k.setChoiceType(1);
        this.l = (RelativeLayout) this.f.findViewById(R.id.truck_limit_layout);
        NoDBClickUtil.a((View) this.l, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (qh.this.b != null) {
                    qh.this.b.toggle();
                }
            }
        });
        this.o = (TextView) this.f.findViewById(R.id.truck_strategy_tv);
        NoDBClickUtil.a((View) this.o, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("bundle_key_car_or_truck", 1);
                if (qh.this.a.get() != null) {
                    ((AbstractBasePage) qh.this.a.get()).startPageForResult((String) "amap.basemap.action.car_restrict_city_list", pageBundle, 65543);
                }
            }
        });
        this.b = (AmapSwitch) this.f.findViewById(R.id.truck_limit_checkbox);
        this.b.setOnCheckedChangeListener(new NaviTruckSettingManager$3(this));
        this.m = (RelativeLayout) this.f.findViewById(R.id.truck_info_layout);
        NoDBClickUtil.a((View) this.m, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                try {
                    if (qh.this.a.get() != null) {
                        DriveUtil.startCarList(2, (AbstractBasePage) qh.this.a.get());
                        LogManager.actionLogV2("P00475", "B001");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.n = (RelativeLayout) this.f.findViewById(R.id.truck_weight_layout);
        NoDBClickUtil.a((View) this.n, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (qh.this.c != null) {
                    qh.this.c.toggle();
                }
            }
        });
        this.c = (AmapSwitch) this.f.findViewById(R.id.weight_limit_checkbox);
        this.c.setOnCheckedChangeListener(new NaviTruckSettingManager$6(this));
    }

    public final boolean a() {
        this.q = DriveUtil.getCarTruckInfo();
        boolean z = false;
        if (this.q != null) {
            this.i.setText(this.q.plateNum);
            this.j.setText(DriveUtil.getTruckType(this.q.truckType));
            TextView textView = this.h;
            Car car = this.q;
            String str = TextUtils.isEmpty(car.length) ? "--" : car.length;
            String str2 = TextUtils.isEmpty(car.width) ? "--" : car.width;
            String str3 = TextUtils.isEmpty(car.height) ? "0" : car.height;
            String str4 = TextUtils.isEmpty(car.weight) ? "0" : car.weight;
            String a2 = "0".equals(str3) ? "--" : sf.a(str3);
            String a3 = "0".equals(str4) ? "--" : sf.a(str4);
            StringBuilder sb = new StringBuilder("总重");
            sb.append(a3);
            sb.append("吨");
            sb.append(this.r);
            sb.append("长");
            sb.append(str);
            sb.append("米");
            sb.append(this.r);
            sb.append("宽");
            sb.append(str2);
            sb.append("米");
            sb.append(this.r);
            sb.append("高");
            sb.append(a2);
            sb.append("米");
            textView.setText(sb.toString());
            a(false);
            this.b.setChecked(DriveUtil.getTruckAvoidSwitch());
            this.c.setChecked(DriveUtil.getTruckAvoidLimitedLoad());
            if (this.k != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject(RoutingPreferenceView.BUNDLE_KEY_OBJ_ORIGIN, this);
                if (!this.g || DriveSpUtil.shouldRouteOffline()) {
                    z = true;
                }
                pageBundle.putBoolean(RoutingPreferenceView.BUNDLE_KEY_BOOL_IS_OFFLINE, z);
                this.k.setPrebuiltData(pageBundle);
            }
            return true;
        }
        this.i.setText("");
        this.j.setText("");
        this.h.setText("");
        a(true);
        return false;
    }

    private void a(boolean z) {
        if (this.d != null) {
            this.d.a(z);
        }
    }

    public final void a(int i2) {
        if (this.p != null) {
            boolean a2 = a();
            if (this.d != null && i2 == 0) {
                this.d.a(!a2);
            }
            this.p.setVisibility(i2);
        }
    }

    public final pj b() {
        pj pjVar = new pj();
        pjVar.a = this.q;
        pjVar.b = DriveUtil.getTruckRoutingChoice();
        pjVar.c = DriveUtil.getTruckAvoidSwitch();
        pjVar.d = DriveUtil.getTruckAvoidLimitedLoad();
        return pjVar;
    }
}
