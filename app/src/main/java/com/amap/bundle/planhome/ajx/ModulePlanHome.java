package com.amap.bundle.planhome.ajx;

import android.text.TextUtils;
import android.view.View;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.exception.InternalJsException;
import com.autonavi.minimap.ajx3.exception.InvalidParamJsException;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("planHome")
public class ModulePlanHome extends AbstractModule implements acy, acz, ada {
    public static final String MODULE_NAME = "planHome";
    private RouteType bindedType = RouteType.DEFAULT;
    private axi mHistoryItemClickListener;
    private HashMap<String, JsFunctionCallback> mPlaHomeLifecycleCallbackMap;
    private JsFunctionCallback mPlanDataChangeCallback;
    private HashMap<String, JsFunctionCallback> mPlanTypeChangeCallbackMap;

    static class a extends JSONObject {
        a() {
        }

        /* access modifiers changed from: private */
        public a a(String str, POI poi) {
            if (acj.a(poi)) {
                try {
                    put(str, bnx.b(poi));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return this;
        }

        /* access modifiers changed from: private */
        public a a(String str, List<POI> list) {
            if (list != null && !list.isEmpty()) {
                JSONArray jSONArray = new JSONArray();
                for (POI poi : list) {
                    if (bnx.a(poi)) {
                        jSONArray.put(bnx.b(poi));
                    }
                }
                if (jSONArray.length() > 0) {
                    try {
                        put(str, jSONArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return this;
        }
    }

    public ModulePlanHome(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    public void setHistoryItemClickListener(axi axi) {
        this.mHistoryItemClickListener = axi;
    }

    @AjxMethod(invokeMode = "sync", value = "bindPlanType")
    public void bindPlanType(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.bindedType = RouteType.getType(str);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getTopHeight")
    public String getTopHeight() {
        View j = acq.a().j();
        if (j == null) {
            return "0";
        }
        return String.valueOf(DimensionUtils.pixelToStandardUnit((float) j.getMeasuredHeight()));
    }

    @AjxMethod("setStartPOI")
    public void setStartPOI(String str) {
        ade.a().a(bnx.a(str));
    }

    @AjxMethod("setEndPOI")
    public void setEndPOI(String str) {
        ade.a().b(bnx.a(str));
    }

    @AjxMethod("setStartEndPOI")
    public void setStartEndPOI(String str, String str2) {
        ade.a().a(bnx.a(str), bnx.a(str2));
    }

    @AjxMethod(invokeMode = "sync", value = "getStartPOI")
    public String getStartPOI() {
        POI b = ade.a().b(false);
        return acj.a(b) ? bnx.b(b).toString() : "";
    }

    @AjxMethod(invokeMode = "sync", value = "getMiddlePOIs")
    public String getMiddlePOIs() {
        if (!bno.a) {
            return null;
        }
        throw new RuntimeException("Not implemented yet");
    }

    @AjxMethod(invokeMode = "sync", value = "getEndPOI")
    public String getEndPOI() {
        POI d = ade.a().d(false);
        return acj.a(d) ? bnx.b(d).toString() : "";
    }

    @AjxMethod(invokeMode = "sync", value = "getStartEndPOI")
    public String getStartEndPOI() {
        JSONObject jSONObject = new JSONObject();
        POI b = ade.a().b(false);
        if (acj.a(b)) {
            try {
                jSONObject.put("start_poi", bnx.b(b));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        POI d = ade.a().d(false);
        if (acj.a(d)) {
            try {
                jSONObject.put("end_poi", bnx.b(d));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return jSONObject.toString();
    }

    @AjxMethod(invokeMode = "sync", value = "getAllPOIs")
    public String getAllPOIs() {
        return getLastPOIJsonObj(ade.a().b(false), ade.a().c(), ade.a().d(false)).toString();
    }

    @AjxMethod(invokeMode = "sync", value = "getCurrPlanType")
    public String getCurrPlanType() {
        RouteType b = adf.a().b();
        if (b == null) {
            b = RouteType.DEFAULT;
        }
        return b.getKeyName();
    }

    @AjxMethod("updateStartViewContent")
    public void updateStartViewContent(String str) {
        if (!invalidOperation()) {
            acq.a().a(str);
        }
    }

    @AjxMethod("updateEndViewContent")
    public void updateEndViewContent(String str) {
        if (!invalidOperation()) {
            acq.a().b(str);
        }
    }

    @AjxMethod("updateStartEndViewHint")
    public void updateStartEndViewHint(String str, String str2) {
        if (!invalidOperation()) {
            acq.a().a(new String[]{str, str2});
        }
    }

    @AjxMethod("setEditPOIEnable")
    public void setEditPOIEnable(String str) {
        if (!invalidOperation()) {
            boolean z = !TextUtils.isEmpty(str) && !str.equalsIgnoreCase("0");
            acq a2 = acq.a();
            if (a2.b != null) {
                a2.b.setEditPOIEnable(z);
            }
        }
    }

    @AjxMethod("setExchangePOIEnable")
    public void setExchangePOIEnable(String str) {
        if (!invalidOperation()) {
            boolean z = !TextUtils.isEmpty(str) && !str.equalsIgnoreCase("0");
            RouteType b = adf.a().b();
            acq a2 = acq.a();
            if (a2.b != null && b != null) {
                a2.b.enableExchange(b, z);
            }
        }
    }

    @AjxMethod("setInTaxiOrder")
    public void setInTaxiOrder(String str) {
        boolean z = !TextUtils.isEmpty(str) && !str.equalsIgnoreCase("0");
        aco a2 = aco.a();
        StringBuilder sb = new StringBuilder("setInTaxiOrder() called with: inTaxiOrder = [");
        sb.append(z);
        sb.append("]");
        aco.c = z;
        a2.b();
    }

    @AjxMethod("setGPSLayerVisible")
    public void setGPSLayerVisible(String str) {
        MapManager mapManager = DoNotUseTool.getMapManager();
        if (mapManager != null) {
            if (TextUtils.isEmpty(str) || str.equals("0")) {
                mapManager.getOverlayManager().getGpsLayer().setVisible(false);
            } else {
                mapManager.getOverlayManager().getGpsLayer().setVisible(true);
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getGPSLayerVisible")
    public String getGPSLayerVisible() {
        MapManager mapManager = DoNotUseTool.getMapManager();
        return mapManager != null ? mapManager.getOverlayManager().isGPSVisible() : false ? "1" : "0";
    }

    @AjxMethod("openSearchPOIPage")
    public void openSearchPOIPage(String str) {
        if (str != null) {
            if (str.equalsIgnoreCase("0")) {
                acm.a().a(acq.a().h(), 1001, getNativeContext().getString(R.string.act_fromto_from_input_hint), SelectFor.FROM_POI, false, -1);
                return;
            }
            if (str.equalsIgnoreCase("1")) {
                acm.a().a(acq.a().i(), 1002, getNativeContext().getString(R.string.act_fromto_to_input_hint), SelectFor.TO_POI, false, -1);
            }
        }
    }

    @AjxMethod("registerPlanDataChangeCallback")
    public void registerPlanDataChangeCallback(JsFunctionCallback jsFunctionCallback) {
        if (!invalidOperation()) {
            this.mPlanDataChangeCallback = jsFunctionCallback;
            ade.a().c = this;
        }
    }

    @AjxMethod("addPlanTypeChangeCallback")
    public void addPlanTypeChangeCallback(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && jsFunctionCallback != null) {
            if (this.mPlanTypeChangeCallbackMap == null) {
                this.mPlanTypeChangeCallbackMap = new HashMap<>();
                adf.a().a((ada) this);
            }
            this.mPlanTypeChangeCallbackMap.put(str, jsFunctionCallback);
        }
    }

    @AjxMethod("removePlanTypeChangeCallback")
    public void removePlanTypeChangeCallback(String str) {
        if (!TextUtils.isEmpty(str) && this.mPlanTypeChangeCallbackMap != null) {
            this.mPlanTypeChangeCallbackMap.remove(str);
        }
    }

    @AjxMethod("addPlanHomeLifecycleCallback")
    public void addPlanHomeLifecycleCallback(String str, JsFunctionCallback jsFunctionCallback) {
        if (!TextUtils.isEmpty(str) && jsFunctionCallback != null) {
            if (this.mPlaHomeLifecycleCallbackMap == null) {
                this.mPlaHomeLifecycleCallbackMap = new HashMap<>();
                acw.a().a.add(this);
            }
            this.mPlaHomeLifecycleCallbackMap.put(str, jsFunctionCallback);
        }
    }

    @AjxMethod("removePlanHomeLifecycleCallback")
    public void removePlanHomeLifecycleCallback(String str) {
        if (!TextUtils.isEmpty(str) && this.mPlaHomeLifecycleCallbackMap != null) {
            this.mPlaHomeLifecycleCallbackMap.remove(str);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "isPlanHomeActive")
    public String isPlanHomeActive() {
        return acw.a().b ? "1" : "0";
    }

    @AjxMethod("requestRoute")
    public void requestRoute(String str) {
        chk chk = new chk();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("start_poi");
            String optString2 = jSONObject.optString("end_poi");
            chk.n = bnx.a(optString);
            chk.p = bnx.a(optString2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.mHistoryItemClickListener != null) {
            this.mHistoryItemClickListener.a(chk);
        }
    }

    public void onDataChange(POI poi, List list, POI poi2) {
        JSONObject jSONObject;
        if (this.mPlanDataChangeCallback != null) {
            if (this.bindedType == RouteType.TAXI) {
                jSONObject = getAllPOIJsonObj(ade.a().a(false), ade.a().b(false), ade.a().b(), list, ade.a().c(false), ade.a().d(false));
            } else {
                jSONObject = getAllPOIJsonObj(ade.a().a(true), poi, ade.a().b(), list, ade.a().c(true), poi2);
            }
            new StringBuilder("onDataChange() - ").append(jSONObject.toString());
            this.mPlanDataChangeCallback.callback(jSONObject.toString());
        }
    }

    public void onTypeChange(RouteType routeType, RouteType routeType2) {
        if (this.mPlanTypeChangeCallbackMap != null && !this.mPlanTypeChangeCallbackMap.isEmpty()) {
            for (String str : this.mPlanTypeChangeCallbackMap.keySet()) {
                JsFunctionCallback jsFunctionCallback = this.mPlanTypeChangeCallbackMap.get(str);
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(routeType.getKeyName(), routeType2.getKeyName());
                }
            }
        }
    }

    public void onCreate() {
        if (this.mPlaHomeLifecycleCallbackMap != null && !this.mPlaHomeLifecycleCallbackMap.isEmpty()) {
            for (String str : this.mPlaHomeLifecycleCallbackMap.keySet()) {
                JsFunctionCallback jsFunctionCallback = this.mPlaHomeLifecycleCallbackMap.get(str);
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback("onCreate");
                }
            }
        }
    }

    public void onDestroy() {
        if (this.mPlaHomeLifecycleCallbackMap != null && !this.mPlaHomeLifecycleCallbackMap.isEmpty()) {
            for (String str : this.mPlaHomeLifecycleCallbackMap.keySet()) {
                JsFunctionCallback jsFunctionCallback = this.mPlaHomeLifecycleCallbackMap.get(str);
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback("onDestroy");
                }
            }
        }
    }

    public void onModuleDestroy() {
        super.onModuleDestroy();
        adf.a().b(this);
        acw.a().a.remove(this);
    }

    private boolean invalidOperation() {
        return adf.a().b() != this.bindedType;
    }

    private JSONObject getLastPOIJsonObj(POI poi, List list, POI poi2) {
        a aVar = new a();
        aVar.a((String) "start_poi", poi).a((String) "mid_poi", list).a((String) "end_poi", poi2);
        return aVar;
    }

    private JSONObject getAllPOIJsonObj(POI poi, POI poi2, List list, List list2, POI poi3, POI poi4) {
        a aVar = new a();
        aVar.a((String) "old_start_poi", poi).a((String) "start_poi", poi2).a((String) "old_mid_poi", list).a((String) "mid_poi", list2).a((String) "old_end_poi", poi3).a((String) "end_poi", poi4);
        return aVar;
    }

    @AjxMethod("startRoute")
    public void startRoute(String str, JsFunctionCallback jsFunctionCallback) {
        bax bax = (bax) defpackage.esb.a.a.a(bax.class);
        if (bax == null) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(new InternalJsException(new String[0]));
            }
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Object a2 = jSONObject.has("startPoi") ? bnx.a(jSONObject.get("startPoi").toString()) : null;
            Object a3 = jSONObject.has("endPoi") ? bnx.a(jSONObject.get("endPoi").toString()) : null;
            String string = jSONObject.has("fromPage") ? jSONObject.getString("fromPage") : null;
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_poi_start", a2);
            pageBundle.putObject("bundle_key_poi_end", a3);
            pageBundle.putBoolean("bundle_key_auto_route", true);
            pageBundle.putString("bundle_key_from_page", string);
            bax.a(pageBundle);
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(null);
            }
        } catch (Exception unused) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback(new InvalidParamJsException(str));
            }
        }
    }
}
