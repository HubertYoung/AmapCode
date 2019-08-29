package defpackage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.drive.setting.navisetting.controller.NaviCarSettingManager$1;
import com.amap.bundle.drive.setting.navisetting.controller.NaviCarSettingManager$7;
import com.amap.bundle.drive.setting.navisetting.controller.NaviCarSettingManager$9;
import com.amap.bundle.drive.setting.navisetting.page.NaviSettingPage;
import com.amap.bundle.drivecommon.route.result.view.RoutingPreferenceView;
import com.amap.bundle.drivecommon.setting.NaviSwitchParentLayout;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.imageloader.MemoryPolicy;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AmapSwitch;
import java.lang.ref.WeakReference;

/* renamed from: qe reason: default package */
/* compiled from: NaviCarSettingManager */
public final class qe {
    public WeakReference<AbstractBasePage> a;
    AmapSwitch b;
    public AmapSwitch c;
    public boolean d = false;
    public a e;
    private View f;
    private ImageView g;
    private TextView h;
    private TextView i;
    private TextView j;
    private RoutingPreferenceView k;
    private LinearLayout l;
    private LinearLayout m;
    private RelativeLayout n;
    private NaviSwitchParentLayout o;
    private TextView p;
    private boolean q;
    private LinearLayout r;
    private Car s;

    /* renamed from: qe$a */
    /* compiled from: NaviCarSettingManager */
    public interface a {
        void a();

        boolean b();

        void c();
    }

    public qe(AbstractBasePage abstractBasePage, View view) {
        boolean z = false;
        this.a = new WeakReference<>(abstractBasePage);
        this.f = view;
        this.q = NetworkReachability.b();
        this.r = (LinearLayout) this.f.findViewById(R.id.car_layout);
        this.g = (ImageView) this.f.findViewById(R.id.car_icon_iv);
        this.h = (TextView) this.f.findViewById(R.id.car_plate_tv);
        this.i = (TextView) this.f.findViewById(R.id.car_info_tv);
        this.k = (RoutingPreferenceView) this.f.findViewById(R.id.car_setting_routing_preference_view);
        this.k.setChoiceType(0);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(RoutingPreferenceView.BUNDLE_KEY_OBJ_ORIGIN, this);
        pageBundle.putBoolean(RoutingPreferenceView.BUNDLE_KEY_BOOL_IS_OFFLINE, (!this.q || DriveSpUtil.shouldRouteOffline()) ? true : z);
        this.k.setPrebuiltData(pageBundle);
        this.b = (AmapSwitch) this.f.findViewById(R.id.car_limit_checkbox);
        this.b.setOnCheckedChangeListener(new NaviCarSettingManager$1(this));
        this.n = (RelativeLayout) this.f.findViewById(R.id.car_limit_layout);
        NoDBClickUtil.a((View) this.n, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (qe.this.b != null) {
                    qe.this.b.toggle();
                }
            }
        });
        this.p = (TextView) this.f.findViewById(R.id.car_strategy_tv);
        NoDBClickUtil.a((View) this.p, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putInt("bundle_key_car_or_truck", 0);
                AbstractBasePage abstractBasePage = (AbstractBasePage) qe.this.a.get();
                if (abstractBasePage != null && abstractBasePage.isAlive()) {
                    abstractBasePage.startPageForResult((String) "amap.basemap.action.car_restrict_city_list", pageBundle, 65543);
                }
            }
        });
        this.l = (LinearLayout) this.f.findViewById(R.id.car_info_layout);
        NoDBClickUtil.a((View) this.l, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                try {
                    AbstractBasePage abstractBasePage = (AbstractBasePage) qe.this.a.get();
                    if (abstractBasePage != null && abstractBasePage.isAlive()) {
                        DriveUtil.startCarList(1, abstractBasePage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.j = (TextView) this.f.findViewById(R.id.car_setting_changetype_tv);
        NoDBClickUtil.a((View) this.j, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (qe.this.e != null) {
                    qe.this.e.a();
                }
            }
        });
        this.m = (LinearLayout) this.f.findViewById(R.id.car_add_item);
        NoDBClickUtil.a((View) this.m, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                AbstractBasePage abstractBasePage = (AbstractBasePage) qe.this.a.get();
                if (abstractBasePage != null && abstractBasePage.isAlive()) {
                    DriveUtil.startAddCarPage(1, abstractBasePage);
                    if (abstractBasePage instanceof NaviSettingPage) {
                        LogManager.actionLogV2("P00181", "B019");
                        return;
                    }
                    LogManager.actionLogV2("P00015", "B012");
                }
            }
        });
        this.o = (NaviSwitchParentLayout) this.f.findViewById(R.id.car_offline_layout);
        this.o.setIntercept(true);
        this.c = (AmapSwitch) this.f.findViewById(R.id.car_offline_checkbox);
        this.c.setOnCheckedChangeListener(new NaviCarSettingManager$7(this));
        NoDBClickUtil.a((View) this.o, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (qe.this.c != null) {
                    if (qe.this.c.isChecked()) {
                        qe.this.c.toggle();
                        return;
                    }
                    AbstractBasePage abstractBasePage = (AbstractBasePage) qe.this.a.get();
                    if (abstractBasePage != null && abstractBasePage.isAlive()) {
                        qe.this.d = true;
                        qe qeVar = qe.this;
                        AbstractBasePage abstractBasePage2 = (AbstractBasePage) qeVar.a.get();
                        if (abstractBasePage2 != null) {
                            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
                            if (iOfflineManager != null) {
                                iOfflineManager.checkOfflineNavi(abstractBasePage2, new NaviCarSettingManager$9(qeVar, abstractBasePage2), new NodeDialogFragmentOnClickListener() {
                                    public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                                        qe.this.d = true;
                                    }
                                }, new NodeDialogFragmentOnClickListener() {
                                    public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                                        qe.this.d = true;
                                    }
                                });
                            }
                        }
                    }
                }
            }
        });
    }

    public final void a() {
        AbstractBasePage abstractBasePage = (AbstractBasePage) this.a.get();
        this.s = DriveUtil.getCarInfo();
        if (this.s != null) {
            if (abstractBasePage != null && abstractBasePage.isAlive()) {
                if (!TextUtils.isEmpty(this.s.vehicleLogo)) {
                    ImageLoader.a(abstractBasePage.getContext()).a(this.s.vehicleLogo).a(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).a((bjo) new kq()).a(R.drawable.drive_car_default_logo).a(this.g, (bjl) null);
                } else {
                    this.g.setImageResource(R.drawable.drive_car_default_logo);
                }
                this.h.setText(this.s.plateNum);
                this.i.setVisibility(TextUtils.isEmpty(this.s.vehicleMsg) ? 8 : 0);
                this.i.setText(this.s.vehicleMsg);
            }
            this.b.setChecked(DriveUtil.isAvoidLimitedPath());
            a(false);
        } else {
            this.g.setImageDrawable(this.f.getResources().getDrawable(R.drawable.drive_car_default_logo));
            this.h.setText("");
            this.i.setText("");
            this.b.setChecked(false);
            a(true);
        }
        if (abstractBasePage != null && abstractBasePage.isAlive()) {
            this.c.setChecked(!DriveSpUtil.getSearchRouteInNetMode(abstractBasePage.getContext()));
        }
    }

    private void a(boolean z) {
        int i2 = 8;
        this.m.setVisibility(z ? 0 : 8);
        LinearLayout linearLayout = this.l;
        if (!z) {
            i2 = 0;
        }
        linearLayout.setVisibility(i2);
    }

    public final void b() {
        final AbstractBasePage abstractBasePage = (AbstractBasePage) this.a.get();
        if (abstractBasePage != null) {
            com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
            aVar.a((CharSequence) abstractBasePage.getString(R.string.navi_settings_offline_dialog_title));
            aVar.b((CharSequence) abstractBasePage.getString(R.string.navi_settings_offline_dialog_msg));
            aVar.b((CharSequence) abstractBasePage.getString(R.string.navi_settings_offline_dialog_negative), (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    qe.this.d = false;
                    DriveSpUtil.setSearchRouteInNeMode(abstractBasePage.getContext(), true);
                    abstractBasePage.dismissViewLayer(alertView);
                }
            });
            aVar.a((CharSequence) abstractBasePage.getString(R.string.navi_settings_offline_dialog_positive), (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    qe.this.d = false;
                    DriveSpUtil.setSearchRouteInNeMode(abstractBasePage.getContext(), false);
                    if (qe.this.c != null) {
                        qe.this.c.toggle();
                    }
                    abstractBasePage.dismissViewLayer(alertView);
                }
            });
            aVar.a(true);
            abstractBasePage.showViewLayer(aVar.a());
        }
    }

    public final void a(int i2) {
        if (this.r != null) {
            a();
            this.r.setVisibility(i2);
        }
    }

    public final pj c() {
        pj pjVar = new pj();
        pjVar.a = this.s;
        pjVar.b = DriveUtil.getLastRoutingChoice();
        pjVar.c = DriveUtil.isAvoidLimitedPath();
        return pjVar;
    }
}
