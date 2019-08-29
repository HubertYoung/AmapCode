package com.autonavi.bundle.routecommute.modlue;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.bundle.routecommute.bus.details.BusCommuteListPage;
import com.autonavi.bundle.routecommute.common.CommuteHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("commute_common")
public class ModuleCommuteCommon extends AbstractModule {
    public static final String MODULE_NAME = "commute_common";
    public Map<String, bat> mDialogModuleProviderMap = new HashMap();

    public ModuleCommuteCommon(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "getRouteCommuteType")
    public String getRouteCommuteType() {
        String g = azi.g();
        azb.a(CommuteHelper.a, "getRouteCommuteType type = ".concat(String.valueOf(g)));
        return TextUtils.isEmpty(g) ? "0" : g;
    }

    @AjxMethod(invokeMode = "sync", value = "setRouteCommuteType")
    public void setRouteCommuteType(String str) {
        azb.a(CommuteHelper.a, "setRouteCommuteType commuteType = ".concat(String.valueOf(str)));
        azi.a(str);
        aho.a(new Runnable() {
            public final void run() {
                axv axv = (axv) a.a.a(axv.class);
                if (axv != null) {
                    axv.k();
                }
            }
        }, 100);
    }

    @AjxMethod("setCommuteMapSwitch")
    public void setCommuteMapSwitch(String str) {
        azb.a(CommuteHelper.a, "setCommuteMapSwitch mapSwitch = ".concat(String.valueOf(str)));
        azi.a(Boolean.valueOf(str).booleanValue());
    }

    @AjxMethod(invokeMode = "sync", value = "getCommuteMapSwitch")
    public String getCommuteMapSwitch() {
        return String.valueOf(azi.o());
    }

    @AjxMethod("requestCommuteOperationActivities")
    public void requestCommuteOperationActivities(String str) {
        bat bat = this.mDialogModuleProviderMap.get(str);
        if (bat != null) {
            bat.a();
        }
    }

    @AjxMethod("handleScheme")
    public void handleScheme(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                azb.a(CommuteHelper.a, "handleScheme paramString".concat(String.valueOf(str)));
                return;
            }
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("type");
            String optString2 = jSONObject.optString("dest");
            String optString3 = jSONObject.optString("from");
            if (TextUtils.isEmpty(optString3)) {
                optString3 = "1";
            }
            axv axv = (axv) a.a.a(axv.class);
            if (axv != null) {
                axv.a(optString, optString2, optString3);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @AjxMethod("openPage")
    public void openPage(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                azb.a(CommuteHelper.a, "openPage paramString".concat(String.valueOf(str)));
            } else if (new JSONObject(str).optInt("type") == 0) {
                axw axw = (axw) a.a.a(axw.class);
                if (axw != null) {
                    axw.a(AMapPageUtil.getPageContext(), str);
                }
            } else {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString(ays.a, str);
                AMapPageUtil.getPageContext().startPage(BusCommuteListPage.class, pageBundle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addDialogModuleProvider(String str, bat bat) {
        this.mDialogModuleProviderMap.put(str, bat);
    }

    public void removeDialogModuleProvider(String str) {
        this.mDialogModuleProviderMap.remove(str);
    }

    @AjxMethod(invokeMode = "sync", value = "getDriveEnterCommuteCount")
    public int getDriveEnterCommuteCount() {
        return new MapSharePreference((String) "SharedPreferences").getIntValue("drive_enter_commute_count", 0);
    }

    @AjxMethod(invokeMode = "sync", value = "isOperateEventEnable")
    public boolean isOperateEventEnable(String str) {
        axv axv = (axv) a.a.a(axv.class);
        if (axv != null) {
            return axv.a(str);
        }
        return false;
    }

    @AjxMethod("closeGuideView")
    public void closeGuideView(String str) {
        axv axv = (axv) a.a.a(axv.class);
        if (axv != null) {
            axv.l();
        }
    }

    @AjxMethod("clearupViewStackForCommute")
    public void clearupViewStackForCommute(String str) {
        axv axv = (axv) a.a.a(axv.class);
        if (axv != null) {
            axv.b(str);
        }
    }
}
