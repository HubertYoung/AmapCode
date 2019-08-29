package com.autonavi.minimap.bundle.maphome;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxField;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;

@AjxModule("mapHome")
public class ModuleMapHome extends AbstractModule {
    private static final int IN_POI_TRAFFIC_MAIN_PAGE = 2;
    private static final int IN_REAL_MAP_HOME_PAGE = 1;
    static final String MODULE_NAME = "mapHome";
    private static final int NOT_IN_MAP_HOME_PAGE = 0;
    @AjxField("tabBarHeight")
    public Float tabBarHeight = Float.valueOf(DimensionUtils.pixelToStandardUnit((float) DimensionUtils.dipToPixel(50.0f)));

    public ModuleMapHome(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setMapTrafficState")
    public void setMapTrafficState(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("traffic", Float.parseFloat(str) == 1.0f);
            } catch (NumberFormatException e) {
                AMapLog.d("ModuleMapHome", e.getMessage());
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getMapTrafficState")
    public String getMapTrafficState() {
        bqx bqx = (bqx) ank.a(bqx.class);
        return bqx != null ? String.valueOf(bqx.a() ? 1 : 0) : "";
    }

    @AjxMethod(invokeMode = "sync", value = "isRedesign")
    public boolean isRedesign() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("key_ab_page_switch", getRedesignABCloudState());
    }

    public boolean getRedesignABCloudState() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("key_redesign_ab_cloud_state", false);
    }

    @AjxMethod("getMainMapShowStatus")
    public void getMainMapShowStatus(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            apr apr = (apr) a.a.a(apr.class);
            if (apr == null) {
                jsFunctionCallback.callback(Integer.valueOf(0));
            } else if (apr.a(AMapPageUtil.getPageContext())) {
                jsFunctionCallback.callback(Integer.valueOf(1));
            } else if (AMapPageUtil.isHomePage()) {
                jsFunctionCallback.callback(Integer.valueOf(2));
            } else {
                jsFunctionCallback.callback(Integer.valueOf(0));
            }
        }
    }
}
