package defpackage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.auto.AutoConnectionTypeEnum;
import com.autonavi.minimap.drive.auto.page.AliCarLinkManagerPage;
import com.autonavi.minimap.drive.auto.page.AutoConnectionManagerFragment;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.offline.map.inter.IOfflineManager.AutoApkUpdateCallback;
import com.autonavi.widget.ui.TitleBar;
import java.util.List;

/* renamed from: deo reason: default package */
/* compiled from: AutoConnectionManagerPresenter */
public final class deo extends sw<AutoConnectionManagerFragment, dej> {
    public deo(AutoConnectionManagerFragment autoConnectionManagerFragment) {
        super(autoConnectionManagerFragment);
    }

    public final void onPageCreated() {
        AutoConnectionManagerFragment autoConnectionManagerFragment = (AutoConnectionManagerFragment) this.mPage;
        View contentView = autoConnectionManagerFragment.getContentView();
        autoConnectionManagerFragment.p = (TextView) contentView.findViewById(R.id.connection_style_text);
        autoConnectionManagerFragment.m = (LinearLayout) contentView.findViewById(R.id.connected_text_info);
        autoConnectionManagerFragment.n = (TextView) contentView.findViewById(R.id.disconnected_text_info);
        autoConnectionManagerFragment.f = (TextView) contentView.findViewById(R.id.apk_version_number);
        autoConnectionManagerFragment.g = (TextView) contentView.findViewById(R.id.update_city_list);
        autoConnectionManagerFragment.a = (RelativeLayout) contentView.findViewById(R.id.connection_limition_tips_layout);
        autoConnectionManagerFragment.b = (RelativeLayout) contentView.findViewById(R.id.bluetooth_function_limition);
        NoDBClickUtil.a((View) autoConnectionManagerFragment.b, autoConnectionManagerFragment.z);
        autoConnectionManagerFragment.c = (RelativeLayout) contentView.findViewById(R.id.auto_send_route_to_auto);
        NoDBClickUtil.a((View) autoConnectionManagerFragment.c, autoConnectionManagerFragment.z);
        autoConnectionManagerFragment.d = (RelativeLayout) contentView.findViewById(R.id.auto_send_apk_to_auto);
        NoDBClickUtil.a((View) autoConnectionManagerFragment.d, autoConnectionManagerFragment.z);
        autoConnectionManagerFragment.e = (RelativeLayout) contentView.findViewById(R.id.auto_send_mapdata_to_auto);
        NoDBClickUtil.a((View) autoConnectionManagerFragment.e, autoConnectionManagerFragment.z);
        autoConnectionManagerFragment.j = (ImageView) contentView.findViewById(R.id.auto_state_icon);
        autoConnectionManagerFragment.o = (TextView) contentView.findViewById(R.id.connection_limition_tips_text);
        autoConnectionManagerFragment.q = (TextView) contentView.findViewById(R.id.auto_send_route_title);
        autoConnectionManagerFragment.r = (TextView) contentView.findViewById(R.id.auto_send_mapdata_title);
        autoConnectionManagerFragment.s = (TextView) contentView.findViewById(R.id.auto_send_apk_title);
        autoConnectionManagerFragment.t = (ImageView) contentView.findViewById(R.id.send_route_to_auto_icon);
        autoConnectionManagerFragment.u = (ImageView) contentView.findViewById(R.id.auto_send_mapdata_to_auto_icon);
        autoConnectionManagerFragment.v = (ImageView) contentView.findViewById(R.id.auto_send_apk_to_auto_icon);
        NoDBClickUtil.a(contentView.findViewById(R.id.mapdata_function_limition), autoConnectionManagerFragment.z);
        autoConnectionManagerFragment.w = (TitleBar) contentView.findViewById(R.id.title);
        autoConnectionManagerFragment.w.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (((deo) AutoConnectionManagerFragment.this.mPresenter).d()) {
                    AutoConnectionManagerFragment.this.finishAllFragmentsWithoutRoot();
                } else {
                    AutoConnectionManagerFragment.this.finish();
                }
            }
        });
        autoConnectionManagerFragment.w.setOnActionClickListener(new OnClickListener() {
            public final void onClick(View view) {
                ((AutoConnectionManagerFragment) ((deo) AutoConnectionManagerFragment.this.mPresenter).mPage).startPageForResult(DeleteAutoConnectionFragment.class, new PageBundle(), 1001);
            }
        });
        dej dej = (dej) this.b;
        PageBundle arguments = ((AutoConnectionManagerFragment) this.mPage).getArguments();
        if (arguments != null) {
            dej.d = arguments.getBoolean("firstConnected", false);
            if (arguments.containsKey("connectionType")) {
                Object object = arguments.getObject("connectionType");
                if (object != null) {
                    dej.e = (AutoConnectionTypeEnum) object;
                    ((AutoConnectionManagerFragment) ((deo) dej.a).mPage).h = dej.e;
                }
            }
            if (dej.d) {
                dej.b();
            }
        }
    }

    public final void onStart() {
        List<String> list;
        AutoConnectionManagerFragment autoConnectionManagerFragment = (AutoConnectionManagerFragment) this.mPage;
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            String d = agb.d();
            if ("amap_wifi_20".equals(d)) {
                autoConnectionManagerFragment.c.setVisibility(0);
                autoConnectionManagerFragment.d.setVisibility(0);
                autoConnectionManagerFragment.e.setVisibility(0);
                autoConnectionManagerFragment.i = iAutoRemoteController.IsWifiConnected();
                iAutoRemoteController.setRemoteControlConnectListener(autoConnectionManagerFragment.y);
            } else if ("amap_wifi".equals(d)) {
                iAutoRemoteController.setRemoteControlConnectListener(autoConnectionManagerFragment.y);
                autoConnectionManagerFragment.i = iAutoRemoteController.IsWifiConnected();
            } else if ("amap_bluetooth_20".equals(d)) {
                autoConnectionManagerFragment.c.setVisibility(0);
                autoConnectionManagerFragment.d.setVisibility(8);
                autoConnectionManagerFragment.e.setVisibility(8);
                iAutoRemoteController.setRemoteControlConnectListener(autoConnectionManagerFragment.y);
                autoConnectionManagerFragment.i = iAutoRemoteController.IsBtConnected();
            } else if ("amap_bluetooth".equals(d)) {
                autoConnectionManagerFragment.c.setVisibility(0);
                autoConnectionManagerFragment.d.setVisibility(8);
                autoConnectionManagerFragment.e.setVisibility(8);
                iAutoRemoteController.setRemoteControlConnectListener(autoConnectionManagerFragment.y);
                autoConnectionManagerFragment.i = iAutoRemoteController.IsBtConnected();
            } else if ("ali_auto_wifi".equals(d)) {
                dej dej = (dej) ((deo) autoConnectionManagerFragment.mPresenter).b;
                dej.f = fbl.a(AMapPageUtil.getAppContext());
                dej.f.a(dej.g);
                dej.c = dej.f.b();
                autoConnectionManagerFragment.i = ((deo) autoConnectionManagerFragment.mPresenter).e();
            }
        }
        String d2 = agb.d();
        if (!TextUtils.isEmpty(d2)) {
            if (d2.equals("amap_wifi_20")) {
                autoConnectionManagerFragment.h = AutoConnectionTypeEnum.AMAP_WIFI_20;
            } else if (d2.equals("amap_wifi")) {
                autoConnectionManagerFragment.h = AutoConnectionTypeEnum.AMAP_WIFI_10;
            } else if (d2.equals("amap_bluetooth_20")) {
                autoConnectionManagerFragment.h = AutoConnectionTypeEnum.AMAP_BLUETOOTH_20;
            } else if (d2.equals("amap_bluetooth")) {
                autoConnectionManagerFragment.h = AutoConnectionTypeEnum.AMAP_BLUETOOTH_10;
            } else if (d2.equals("ali_auto_wifi")) {
                autoConnectionManagerFragment.h = AutoConnectionTypeEnum.ALI_AUTO;
            }
        }
        autoConnectionManagerFragment.a(autoConnectionManagerFragment.i);
        autoConnectionManagerFragment.a(autoConnectionManagerFragment.h);
        if (autoConnectionManagerFragment.h == AutoConnectionTypeEnum.AMAP_WIFI_20) {
            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
            if (iOfflineManager != null) {
                iOfflineManager.checkAutoApkUpdate(new AutoApkUpdateCallback() {
                    public final void onUpdate(String str) {
                        if (AutoConnectionManagerFragment.this.isAlive()) {
                            if (TextUtils.isEmpty(str)) {
                                AutoConnectionManagerFragment.this.f.setVisibility(8);
                                return;
                            }
                            AutoConnectionManagerFragment.this.f.setVisibility(0);
                            AutoConnectionManagerFragment.this.f.setText(AutoConnectionManagerFragment.this.getContext().getString(R.string.update_apk_info, new Object[]{str}));
                        }
                    }
                });
            }
        }
        IOfflineManager iOfflineManager2 = (IOfflineManager) ank.a(IOfflineManager.class);
        if (iOfflineManager2 != null) {
            list = iOfflineManager2.getDownloadCityUpgradeList();
        } else {
            list = null;
        }
        if (list == null || list.size() == 0 || TextUtils.isEmpty(list.get(0))) {
            autoConnectionManagerFragment.g.setVisibility(8);
        } else {
            autoConnectionManagerFragment.g.setVisibility(0);
            autoConnectionManagerFragment.g.setText(autoConnectionManagerFragment.getContext().getString(R.string.update_city_info, new Object[]{list.get(0), Integer.valueOf(list.size())}));
        }
        autoConnectionManagerFragment.a();
    }

    public final void onDestroy() {
        dej dej = (dej) this.b;
        if (dej.f != null) {
            dej.f.b(dej.h);
            dej.h = null;
        }
        AutoConnectionManagerFragment autoConnectionManagerFragment = (AutoConnectionManagerFragment) ((deo) dej.a).mPage;
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.removeRemoteControlConnectListener(autoConnectionManagerFragment.y);
        }
        autoConnectionManagerFragment.y = null;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean("firstConnected", dej.d);
        ((AutoConnectionManagerFragment) ((deo) dej.a).mPage).setResult(ResultType.OK, pageBundle);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (i == 1001 && ResultType.OK == resultType) {
            ((AutoConnectionManagerFragment) this.mPage).startPageForResult(AliCarLinkManagerPage.class, new PageBundle(), 1002);
            return;
        }
        if (i == 1002 && ResultType.OK != resultType) {
            ((AutoConnectionManagerFragment) this.mPage).finish();
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        AutoConnectionManagerFragment autoConnectionManagerFragment = (AutoConnectionManagerFragment) this.mPage;
        if (autoConnectionManagerFragment.isViewLayerShowing(autoConnectionManagerFragment.k)) {
            ((AutoConnectionManagerFragment) this.mPage).dismissAllViewLayers();
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        AutoConnectionManagerFragment autoConnectionManagerFragment2 = (AutoConnectionManagerFragment) this.mPage;
        if (autoConnectionManagerFragment2.isViewLayerShowing(autoConnectionManagerFragment2.l)) {
            ((AutoConnectionManagerFragment) this.mPage).dismissAllViewLayers();
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        AutoConnectionManagerFragment autoConnectionManagerFragment3 = (AutoConnectionManagerFragment) this.mPage;
        if (autoConnectionManagerFragment3.x != null && autoConnectionManagerFragment3.isViewLayerShowing(autoConnectionManagerFragment3.x)) {
            ((AutoConnectionManagerFragment) this.mPage).dismissAllViewLayers();
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else if (!d()) {
            return super.onBackPressed();
        } else {
            ((AutoConnectionManagerFragment) this.mPage).finishAllFragmentsWithoutRoot();
            return super.onBackPressed();
        }
    }

    public final boolean c() {
        return ((dej) this.b).c();
    }

    public final boolean d() {
        return ((dej) this.b).d && c();
    }

    public final void a(AutoConnectionTypeEnum autoConnectionTypeEnum) {
        ((AutoConnectionManagerFragment) this.mPage).a(autoConnectionTypeEnum);
    }

    public final boolean e() {
        return ((dej) this.b).c;
    }

    public final /* synthetic */ su a() {
        return new dej(this);
    }
}
