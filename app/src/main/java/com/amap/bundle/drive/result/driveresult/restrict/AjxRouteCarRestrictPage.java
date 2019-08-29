package com.amap.bundle.drive.result.driveresult.restrict;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drive.ajx.inter.OnJsOpenCarSettingCallback;
import com.amap.bundle.drive.ajx.module.ModuleDriveCommonBusiness;
import com.amap.bundle.drivecommon.restrictedarea.RestrictedAreaParam;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import defpackage.ov;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.basemap.action.car_restrict")
public class AjxRouteCarRestrictPage<Presenter extends ov> extends Ajx3Page implements mr {
    public a a = new a(0);
    public ModuleDriveCommonBusiness b = null;
    private FrameLayout c;
    private FrameLayout d;
    private OnJsOpenCarSettingCallback e = new OnJsOpenCarSettingCallback() {
        public final void onOpenCarSetting() {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putBoolean("bundle_key_from_navigation", false);
            AjxRouteCarRestrictPage.this.startPageForResult((String) "amap.basemap.action.car_plate_input", pageBundle, 65536);
        }
    };

    public static class a {
        public boolean a;
        public int b;
        public int c;
        public int d;

        private a() {
            this.a = false;
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public Ajx3PagePresenter createPresenter() {
        this.mPresenter = new ov(this);
        return (Ajx3PagePresenter) this.mPresenter;
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.ajx_route_car_restrict_page, null);
        viewGroup.addView(amapAjxView, new LayoutParams(-1, -1));
        this.c = (FrameLayout) viewGroup.findViewById(R.id.compass_container);
        Context context = getContext();
        FrameLayout frameLayout = this.c;
        if (!(context == null || frameLayout == null || !euk.a())) {
            frameLayout.setPadding(frameLayout.getPaddingLeft(), frameLayout.getPaddingTop() + euk.a(context), frameLayout.getPaddingRight(), frameLayout.getPaddingBottom());
        }
        this.d = (FrameLayout) viewGroup.findViewById(R.id.scale_container);
        cde suspendManager = getSuspendManager();
        if (suspendManager != null) {
            MapCustomizeManager b2 = suspendManager.b();
            if (b2 != null) {
                b2.setNaviMode(1);
                b2.enableView(2);
            }
            View view = suspendManager.e().a(true, getContext()).a.getView();
            if (view != null) {
                ViewGroup viewGroup2 = (ViewGroup) view.getParent();
                if (viewGroup2 != null) {
                    viewGroup2.removeView(view);
                }
                this.c.removeAllViews();
                this.c.addView(view);
                if (ts.a()) {
                    ViewGroup.LayoutParams layoutParams = this.c.getLayoutParams();
                    if (layoutParams != null) {
                        ((MarginLayoutParams) layoutParams).topMargin = euk.a(getContext()) + getContext().getResources().getDimensionPixelSize(R.dimen.restrict_compass_margin_top);
                        this.c.setLayoutParams(layoutParams);
                    }
                }
            }
            ScaleView f = getSuspendWidgetHelper().f();
            if (f != null) {
                ViewGroup viewGroup3 = (ViewGroup) f.getParent();
                if (viewGroup3 != null) {
                    viewGroup3.removeView(f);
                }
                this.d.removeAllViews();
                this.d.addView(f);
                f.setScaleStatus(0);
                f.changeLogoStatus(true);
            }
        }
        return viewGroup;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.b = (ModuleDriveCommonBusiness) this.mAjxView.getJsModule(ModuleDriveCommonBusiness.MODULE_NAME);
        if (this.b != null) {
            this.b.setOnJsOpenCarSettingCallback(this.e);
        }
    }

    public void onCreate(Context context) {
        bty mapView = getMapView();
        if (mapView != null) {
            this.a.a = mapView.s();
            this.a.b = mapView.o(false);
            this.a.c = mapView.ae();
            this.a.d = mapView.p(false);
        }
        PageBundle arguments = getArguments();
        if (arguments != null) {
            int i = arguments.getInt("cartype");
            int i2 = arguments.getInt("source");
            long j = arguments.getLong("resultId");
            long j2 = arguments.getLong("pathId");
            String str = "";
            RestrictedAreaParam restrictedAreaParam = (RestrictedAreaParam) arguments.getObject(AutoJsonUtils.JSON_ADCODE);
            if (restrictedAreaParam != null) {
                str = restrictedAreaParam.adcodes;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("cartype", i);
                jSONObject.put("source", i2);
                jSONObject.put(AutoJsonUtils.JSON_ADCODE, str);
                jSONObject.put("result_id", j);
                jSONObject.put("path_id", j2);
                jSONObject.put(ConfigerHelper.AOS_URL_KEY, ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.DRIVE_AOS_URL_KEY));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            arguments.putObject("jsData", jSONObject.toString());
        }
        super.onCreate(context);
    }
}
