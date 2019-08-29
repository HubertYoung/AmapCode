package com.autonavi.bundle.airticket.module;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("route_airticket")
public class ModuleAirTicket extends AbstractModule {
    public static final String MODULE_NAME = "route_airticket";
    private apn mCalcRouteStateChangeListener = null;
    private axi mHistoryItemClickListener;
    private JsFunctionCallback mJsCalcRouteCallBack;
    private JsFunctionCallback mTabCallBack;
    private AbstractBasePage page;

    @AjxMethod("openTicketDetail")
    public void openTicketDetail(String str) {
    }

    public ModuleAirTicket(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("registerAirTicketRequestCallback")
    public void registerAirTicketRequestCallback(JsFunctionCallback jsFunctionCallback) {
        this.mJsCalcRouteCallBack = jsFunctionCallback;
    }

    @AjxMethod("registerTabChangeListener")
    public void registerTabChangeListener(JsFunctionCallback jsFunctionCallback) {
        this.mTabCallBack = jsFunctionCallback;
    }

    @AjxMethod("requestRoute")
    public void requestRoute(String str) {
        chk chk = new chk();
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("start_poi");
            String optString2 = jSONObject.optString("end_poi");
            chk.n = createPoiFormJson(optString);
            chk.p = createPoiFormJson(optString2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.mHistoryItemClickListener != null) {
            this.mHistoryItemClickListener.a(chk);
        }
    }

    @AjxMethod("calcRouteStateChange")
    public void calcRouteStateChange(int i) {
        if (this.mCalcRouteStateChangeListener != null) {
            this.mCalcRouteStateChangeListener.a(i);
        }
    }

    public void registerCalcRouteStateChangeListener(apn apn) {
        this.mCalcRouteStateChangeListener = apn;
    }

    private POI createPoiFormJson(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            POI createPOI = POIFactory.createPOI(jSONObject.getString("name"), new GeoPoint(jSONObject.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON), jSONObject.getDouble("lat")));
            createPOI.setId(jSONObject.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
            createPOI.getPoiExtra().put("IATA_CODE", jSONObject.optString("citycode"));
            return createPOI;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @AjxMethod("saveAirPlanHistory")
    public void saveAirPlanHistory() {
        if (this.page != null) {
            axd axd = (axd) this.page.getContentView().getParent();
            if (axd != null) {
                IRouteUI routeInputUI = axd.getRouteInputUI();
                POI d = routeInputUI.d();
                POI f = routeInputUI.f();
                if (d != null) {
                    d = d.clone();
                    d.setName(app.a(d));
                }
                if (f != null) {
                    f = f.clone();
                    f.setName(app.a(f));
                }
                if (d != null && !TextUtils.equals(d.getName(), "") && f != null && !TextUtils.equals(f.getName(), "") && !TextUtils.equals(d.getName(), f.getName())) {
                    btf btf = new btf();
                    btf.c = Integer.valueOf(RouteType.AIRTICKET.getValue());
                    btf.e = Integer.valueOf(d.getPoint().x);
                    btf.f = Integer.valueOf(d.getPoint().y);
                    btf.g = Integer.valueOf(f.getPoint().x);
                    btf.h = Integer.valueOf(f.getPoint().y);
                    btf.i = btf.a(d);
                    btf.k = btf.a(f);
                    StringBuilder sb = new StringBuilder();
                    sb.append(d.getName());
                    sb.append(" → ");
                    sb.append(f.getName());
                    btf.b = sb.toString();
                    ebm.a(btf, RouteType.AIRTICKET);
                }
            }
        }
    }

    public void setHistoryItemClickListener(axi axi) {
        this.mHistoryItemClickListener = axi;
    }

    public void requestAirTicketList(String str) {
        if (this.mJsCalcRouteCallBack != null && !TextUtils.isEmpty(str)) {
            this.mJsCalcRouteCallBack.callback(str);
        }
    }

    public void onTabChanged() {
        if (this.mTabCallBack != null) {
            this.mTabCallBack.callback(new Object[0]);
        }
    }

    public void attachPage(AbstractBasePage abstractBasePage) {
        this.page = abstractBasePage;
    }
}
