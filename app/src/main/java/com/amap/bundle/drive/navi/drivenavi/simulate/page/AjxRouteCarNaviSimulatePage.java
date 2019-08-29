package com.amap.bundle.drive.navi.drivenavi.simulate.page;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drive.common.basepage.AjxRouteCarNaviBasePage;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager.NaviType;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.os.GravitySensor;
import com.autonavi.minimap.ajx3.modules.os.GravitySensor.GravityListener;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@SuppressFBWarnings({"DB_DUPLICATE_BRANCHES"})
public class AjxRouteCarNaviSimulatePage extends AjxRouteCarNaviBasePage implements Callback<Status>, LocationNone, launchModeSingleTask {
    int a;
    private GravitySensor gravitySensor;

    public void adjustLightness() {
    }

    public void callback(Status status) {
    }

    public void error(Throwable th, boolean z) {
    }

    public void initLightness() {
    }

    public boolean isLightnessSwitchOn() {
        return false;
    }

    public void recoverOriginalLightness() {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        registerGravityListener();
        if (!to.a(this)) {
            to.a(this, true);
        }
    }

    public Ajx3PagePresenter createPresenter() {
        return new nx(this);
    }

    public NaviType getNaviType() {
        if (isCarNavi()) {
            return NaviType.CAR_SIMULATE;
        }
        return NaviType.TRUCK_SIMULATE;
    }

    public void destroy() {
        super.destroy();
        if (this.gravitySensor != null) {
            this.gravitySensor.destroy();
        }
    }

    public void loadJs() {
        String str;
        if (getArguments() != null) {
            String string = getArguments().getString("jsData");
            try {
                JSONObject jSONObject = new JSONObject();
                if (!TextUtils.isEmpty(string)) {
                    jSONObject = new JSONObject(string);
                }
                this.a = getArguments().getInt("route_car_type_key", RouteType.CAR.getValue());
                jSONObject.put("routeType", this.a);
                str = jSONObject.toString();
            } catch (Exception e) {
                e.printStackTrace();
                str = string;
            }
            DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
            this.mAjxView.load(ModuleRouteDriveResult.CAR_MOCK_NAVI, str, "CAR_NAVI_SIMULATE", displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        tu.a("ajx_on_end_load");
        if (isAlive()) {
        }
    }

    public boolean getSpeakTTSMode() {
        return DriveSpUtil.getBool(AMapAppGlobal.getApplication(), DriveSpUtil.CALLING_SPEAK_TTS, false);
    }

    public boolean getTtsMixMusicMode() {
        return re.l();
    }

    public boolean isCarNavi() {
        return this.a != RouteType.TRUCK.getValue();
    }

    private void registerGravityListener() {
        if (ro.g()) {
            this.gravitySensor = new GravitySensor(getContext());
            this.gravitySensor.setGravityListener(new GravityListener() {
                public final void onOrientationChanged(String str) {
                    if (str.equals(GravitySensor.PORTRAIT_PRIMARY)) {
                        AjxRouteCarNaviSimulatePage.this.requestScreenOrientation(1);
                    } else if (str.equals(GravitySensor.LANDSCAPE_PRIMARY)) {
                        AjxRouteCarNaviSimulatePage.this.requestScreenOrientation(8);
                    } else {
                        if (str.equals(GravitySensor.LANDSCAPE_SECONDARY)) {
                            AjxRouteCarNaviSimulatePage.this.requestScreenOrientation(0);
                        }
                    }
                }
            });
        }
    }
}
