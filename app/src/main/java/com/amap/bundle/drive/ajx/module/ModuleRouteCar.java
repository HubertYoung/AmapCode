package com.amap.bundle.drive.ajx.module;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drive.ajx.tools.DriveCarOwnerAjxTools;
import com.amap.bundle.drive.ajx.tools.DriveRouteAjxTools;
import com.amap.bundle.drive.ajx.tools.DriveRouteHomeCompanyManager;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.db.model.Car;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPageHost;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@AjxModule("route_car")
@Keep
public final class ModuleRouteCar extends AbstractModule {
    private static final String CARINFO = "carInfo";
    private static final String CARINFOMINE = "carInfoMine";
    private static final String CARINFONATIVE = "carInfoNative";
    private static final String CARINFOREGISTER = "carInfoRegister";
    private static final String CARTOOLBOX = "carToolBox";
    public static final String MODULE_NAME = "route_car";
    private static final String NAVINFO = "navInfo";
    private static final String POI_COMPANY = "company";
    private static final String POI_HOME = "home";
    private static final String TAG = "ModuleDriveRoute";
    private JsFunctionCallback mCarOwnerCallback;
    private JsFunctionCallback mCompanyHomeCallback;
    /* access modifiers changed from: private */
    public IRouteCarModuleListener mModuleListener;
    private bid mPageContext = AMapPageUtil.getPageContext();

    public interface IRouteCarModuleListener {
        boolean startRouteCarResultPage(String str);
    }

    public ModuleRouteCar(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setSettingInfo")
    public final void setSettingInfo(String str, JsFunctionCallback jsFunctionCallback) {
        tj.b("ModuleDriveRoute", str);
        if (!TextUtils.isEmpty(str)) {
            if (str.equalsIgnoreCase(NAVINFO)) {
                DriveRouteAjxTools.startCarSettingPage();
            } else if (str.equalsIgnoreCase(CARINFOMINE)) {
                this.mCarOwnerCallback = jsFunctionCallback;
                Car car = null;
                ato ato = (ato) a.a.a(ato.class);
                if (ato != null) {
                    car = ato.a().b(1);
                }
                if (car == null) {
                    DriveUtil.startAddCarPage(1, AMapPageUtil.getPageContext());
                    return;
                }
                Intent intent = new Intent();
                intent.setData(Uri.parse("amapuri://carownerservice/homepage"));
                DoNotUseTool.startScheme(intent);
            } else {
                if (str.equalsIgnoreCase(CARINFOREGISTER)) {
                    this.mCarOwnerCallback = jsFunctionCallback;
                }
            }
        }
    }

    @AjxMethod("getSettingInfo")
    public final void getSettingInfo(String str, JsFunctionCallback jsFunctionCallback) {
        tj.b("ModuleDriveRoute", str);
        if (!TextUtils.isEmpty(str)) {
            if (str.equalsIgnoreCase(NAVINFO)) {
                String a = rc.a();
                if (jsFunctionCallback != null) {
                    jsFunctionCallback.callback(a);
                }
            } else if (str.equalsIgnoreCase(CARINFONATIVE)) {
                this.mCarOwnerCallback = jsFunctionCallback;
                DriveCarOwnerAjxTools.getCarDBData(jsFunctionCallback);
            } else {
                if (str.equalsIgnoreCase(CARTOOLBOX)) {
                    DriveRouteAjxTools.getCarToolBox(getNativeContext(), jsFunctionCallback);
                }
            }
        }
    }

    @AjxMethod("getNativeImgPath")
    public final void getNativeImgPath(String str, JsFunctionCallback jsFunctionCallback) {
        tj.b("ModuleDriveRoute", str);
        if (TextUtils.isEmpty(str)) {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback("");
            }
            return;
        }
        DriveRouteHomeCompanyManager.getInstace().requestTMCAndSavePic(0, str, jsFunctionCallback);
    }

    @AjxMethod(invokeMode = "sync", value = "setHomeAndCompanyPOI")
    public final boolean setHomeAndCompanyPOI(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (bno.a) {
                eao.a((String) "Daniel-27", (String) " (ModuleRouteCar) setHomeAndCompanyPOI param illegal");
            }
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str2);
            POI createPOI = POIFactory.createPOI();
            if (createPOI == null) {
                if (bno.a) {
                    eao.a((String) "Daniel-27", (String) " (ModuleRouteCar) setHomeAndCompanyPOI poi illegal");
                }
                return false;
            }
            String optString = jSONObject.optString(AutoJsonUtils.JSON_ADCODE);
            createPOI.setName(jSONObject.optString("name"));
            createPOI.setAddr(jSONObject.optString("address"));
            createPOI.setId(jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
            GeoPoint point = createPOI.getPoint();
            String optString2 = jSONObject.optString(DictionaryKeys.CTRLXY_X);
            String optString3 = jSONObject.optString(DictionaryKeys.CTRLXY_Y);
            int parseInt = !TextUtils.isEmpty(optString2) ? Integer.parseInt(optString2) : 0;
            int parseInt2 = !TextUtils.isEmpty(optString3) ? Integer.parseInt(optString3) : 0;
            double parseDouble = jSONObject.has(LocationParams.PARA_FLP_AUTONAVI_LON) ? Double.parseDouble(jSONObject.optString(LocationParams.PARA_FLP_AUTONAVI_LON)) : 0.0d;
            double parseDouble2 = jSONObject.has("lat") ? Double.parseDouble(jSONObject.optString("lat")) : 0.0d;
            if (point == null) {
                point = new GeoPoint();
                createPOI.setPoint(point);
            }
            if (!(parseInt == 0 || parseInt2 == 0)) {
                point.x = parseInt;
                point.y = parseInt2;
            }
            if (!(parseDouble2 == 0.0d || parseDouble == 0.0d)) {
                point.setLonLat(parseDouble, parseDouble2);
            }
            if (!TextUtils.isEmpty(optString)) {
                createPOI.setAdCode(optString);
            } else {
                createPOI.setAdCode(String.valueOf(point.getAdCode()));
            }
            com com2 = (com) ank.a(com.class);
            if (com2 != null) {
                cop b = com2.b(com2.a());
                if (b != null) {
                    if (POI_HOME.equals(str)) {
                        b.f(createPOI);
                    } else if (!POI_COMPANY.equals(str)) {
                        return false;
                    } else {
                        b.e(createPOI);
                    }
                }
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (NumberFormatException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @AjxMethod("startSelectPoiWithCallback")
    public final void startSelectPoiWithCallback(JsFunctionCallback jsFunctionCallback) {
        this.mPageContext.startPageForResult((String) "amap.basemap.action.base_select_poi_from_map_page", new PageBundle(), 1);
    }

    @AjxMethod("setHomeAndCompany")
    public final void setHomeAndCompany(String str, JsFunctionCallback jsFunctionCallback) {
        tj.b("ModuleDriveRoute", str);
        if (jsFunctionCallback != null) {
            this.mCompanyHomeCallback = jsFunctionCallback;
        }
        if (TextUtils.isEmpty(str) && jsFunctionCallback != null) {
            jsFunctionCallback.callback("");
        }
        if (str.contains(POI_HOME)) {
            DriveRouteHomeCompanyManager.getInstace().startSetHome(this.mPageContext);
        } else if (str.contains(POI_COMPANY)) {
            DriveRouteHomeCompanyManager.getInstace().startSetCompany(this.mPageContext);
        } else {
            if (jsFunctionCallback != null) {
                jsFunctionCallback.callback("");
            }
        }
    }

    @AjxMethod("requestRoute")
    public final void requestRoute(final String str) {
        tj.b("ModuleDriveRoute", str);
        aho.a(new Runnable() {
            public void run() {
                bid pageContext = AMapPageUtil.getPageContext();
                IPageHost iPageHost = (pageContext == null || pageContext.getActivity() == null) ? null : (IPageHost) pageContext.getActivity();
                if ((iPageHost == null || !iPageHost.isHostPaused()) && ModuleRouteCar.this.mModuleListener != null) {
                    ModuleRouteCar.this.mModuleListener.startRouteCarResultPage(str);
                }
            }
        });
    }

    @AjxMethod("jump")
    public final void jump(String str, String str2) {
        StringBuilder sb = new StringBuilder("key=");
        sb.append(str);
        sb.append(", params=");
        sb.append(str2);
        tj.b("ModuleDriveRoute", sb.toString());
    }

    public final void updateCarOwner() {
        tj.b("ModuleDriveRoute", "");
        if (this.mCarOwnerCallback != null) {
            DriveCarOwnerAjxTools.getCarDBData(this.mCarOwnerCallback);
        }
    }

    public final void updateCompany() {
        tj.b("ModuleDriveRoute", "");
        if (this.mCompanyHomeCallback != null) {
            String companyPOI = DriveRouteHomeCompanyManager.getInstace().getCompanyPOI();
            this.mCompanyHomeCallback.callback(companyPOI);
        }
    }

    public final void updateHome() {
        tj.b("ModuleDriveRoute", "");
        if (this.mCompanyHomeCallback != null) {
            String homePOI = DriveRouteHomeCompanyManager.getInstace().getHomePOI();
            this.mCompanyHomeCallback.callback(homePOI);
        }
    }

    public final void setContext(bid bid) {
        this.mPageContext = bid;
    }

    public final void setManagerListener(IRouteCarModuleListener iRouteCarModuleListener) {
        this.mModuleListener = iRouteCarModuleListener;
    }

    public final void release() {
        this.mCarOwnerCallback = null;
        this.mCompanyHomeCallback = null;
    }
}
