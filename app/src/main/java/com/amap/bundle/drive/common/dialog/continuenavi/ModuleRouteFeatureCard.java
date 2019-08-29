package com.amap.bundle.drive.common.dialog.continuenavi;

import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("route_featurecard")
public class ModuleRouteFeatureCard extends AbstractModule {
    public static final String MODULE_NAME = "route_featurecard";
    private static final String TAG = "ModuleRouteFeatureCard";
    private POI mContinueNavInfoCache = null;

    public ModuleRouteFeatureCard(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "getContinueNavigationInfo")
    public String getContinueNavigationInfo() {
        JSONObject jSONObject;
        POI continueNavigationPOI = getContinueNavigationPOI();
        if (continueNavigationPOI == null) {
            return "";
        }
        try {
            jSONObject = new JSONObject();
            jSONObject.put("end", bnx.b(continueNavigationPOI));
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject == null) {
            return "";
        }
        this.mContinueNavInfoCache = continueNavigationPOI;
        return jSONObject.toString();
    }

    private POI getContinueNavigationPOI() {
        new mx();
        if (mx.a(AMapPageUtil.getAppContext())) {
            return DriveSpUtil.getDestinationAtException(AMapPageUtil.getAppContext());
        }
        return null;
    }

    @AjxMethod(invokeMode = "sync", value = "deleteContinueNavigationInfo")
    public void deleteContinueNavigationInfo() {
        this.mContinueNavInfoCache = null;
        new mx();
        mx.a();
    }

    @AjxMethod(invokeMode = "sync", value = "startContinueCarNavigation")
    public boolean startContinueCarNavigation() {
        POI continueNavigationPOI = this.mContinueNavInfoCache != null ? this.mContinueNavInfoCache : getContinueNavigationPOI();
        if (continueNavigationPOI == null) {
            return false;
        }
        new mx();
        mx.a(continueNavigationPOI);
        return true;
    }
}
