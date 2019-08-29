package com.amap.bundle.drive.ajx.module;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drive.ajx.inter.JsCommandCallback;
import com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.gdtaojin.basemap.UiExecutor;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.widget.ConfirmDlg;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ModuleDriveCommonBusinessImpl {
    private static final String TAG = "ModuleDriveCommonBusinessImpl";
    private JsCommandCallback mJsCommandCallback;

    public void saveRouteHistory(String str) {
        AMapLog.d(TAG, "saveRouteHistory--JSON=".concat(String.valueOf(str)));
        try {
            NavigationDataResult navigationDataResult = new NavigationDataResult();
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject(H5PageData.KEY_UC_START);
            JSONObject optJSONObject2 = jSONObject.optJSONObject("end");
            JSONArray optJSONArray = jSONObject.optJSONArray("via");
            POI a = bnx.a(optJSONObject.toString());
            POI a2 = bnx.a(optJSONObject2.toString());
            ArrayList arrayList = new ArrayList();
            if (optJSONArray != null && optJSONArray.length() > 0) {
                int length = optJSONArray.length();
                for (int i = 0; i < length; i++) {
                    arrayList.add(bnx.a(((JSONObject) optJSONArray.get(i)).toString()));
                }
            }
            navigationDataResult.setFromPOI(a);
            navigationDataResult.setToPOI(a2);
            navigationDataResult.setMidPOIs(arrayList);
            bax bax = (bax) a.a.a(bax.class);
            if (bax != null) {
                bax.a(navigationDataResult, RouteType.CAR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean usingMockLocation() {
        Application application = AMapAppGlobal.getApplication();
        if (application == null) {
            AMapLog.e("isMockLocationSettingsON", "context == null, ==> true");
            return true;
        } else if ("0".equals(Secure.getString(application.getContentResolver(), "mock_location"))) {
            AMapLog.e("isMockLocationSettingsON", "context != null, switch is OFF ==> false");
            return false;
        } else {
            AMapLog.e("isMockLocationSettingsON", "context != null, switch is ON ==> true");
            return true;
        }
    }

    public void saveContinueNavi(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONArray optJSONArray = jSONObject.optJSONArray("via");
            ArrayList arrayList = new ArrayList();
            int length = optJSONArray != null ? optJSONArray.length() : 0;
            for (int i = 0; i < length; i++) {
                arrayList.add(parsePOIInfoToPoi(((JSONObject) optJSONArray.get(i)).toString()));
            }
            String optString = jSONObject.optString("end");
            POI parsePOIInfoToPoi = parsePOIInfoToPoi(optString);
            String optString2 = jSONObject.optString("vehicleType");
            int optInt = jSONObject.optInt("remainTime");
            String str2 = "car";
            if (!TextUtils.equals("1", optString2) && !TextUtils.equals("3", optString2)) {
                if (!TextUtils.equals("5", optString2)) {
                    if (TextUtils.equals("11", optString2)) {
                        str2 = DriveUtil.NAVI_TYPE_MOTORBIKE;
                    }
                    String optString3 = new JSONObject(optString).optString("jumpSa");
                    new re();
                    re.a(lf.a(), optInt);
                    re.a(parsePOIInfoToPoi);
                    re.a((List<POI>) arrayList);
                    re.b((String) "navigation_navitype_at_exception", str2);
                    re.b((String) "car_navi_sourceapplication", optString3);
                }
            }
            str2 = DriveUtil.NAVI_TYPE_TRUCK;
            String optString32 = new JSONObject(optString).optString("jumpSa");
            new re();
            re.a(lf.a(), optInt);
            re.a(parsePOIInfoToPoi);
            re.a((List<POI>) arrayList);
            re.b((String) "navigation_navitype_at_exception", str2);
            re.b((String) "car_navi_sourceapplication", optString32);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getHomeAndCompanyParamInfo() {
        return rl.a();
    }

    private static POI parsePOIInfoToPoi(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        POI createPOI = POIFactory.createPOI();
        ISearchPoiData iSearchPoiData = (ISearchPoiData) createPOI.as(ISearchPoiData.class);
        try {
            JSONObject jSONObject = new JSONObject(str);
            iSearchPoiData.setType(jSONObject.optString("type"));
            iSearchPoiData.setId(jSONObject.optString("poiID"));
            iSearchPoiData.setName(jSONObject.optString("name"));
            iSearchPoiData.setChildType(jSONObject.optString("parentRel"));
            iSearchPoiData.setParent(jSONObject.optString("parentID"));
            iSearchPoiData.setPid(jSONObject.optString("parentID"));
            createPOI.setInoorFloorNoName(jSONObject.optString("floor"));
            iSearchPoiData.setTowardsAngle(jSONObject.optString("angel"));
            iSearchPoiData.setFnona(jSONObject.optString("floorName"));
            iSearchPoiData.setType(jSONObject.optString("typeCode"));
            iSearchPoiData.setEndPoiExtension(jSONObject.optString("naviExtCode"));
            double optDouble = jSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON, 0.0d);
            double optDouble2 = jSONObject.optDouble("lat", 0.0d);
            GeoPoint point = createPOI.getPoint();
            if (!(optDouble == 0.0d || optDouble2 == 0.0d)) {
                if (point == null) {
                    point = new GeoPoint();
                    createPOI.setPoint(point);
                }
                point.setLonLat(optDouble, optDouble2);
            }
            ArrayList arrayList = new ArrayList();
            JSONObject optJSONObject = jSONObject.optJSONObject("naviPos");
            int optInt = optJSONObject.optInt(DictionaryKeys.CTRLXY_X);
            int optInt2 = optJSONObject.optInt(DictionaryKeys.CTRLXY_Y);
            GeoPoint geoPoint = new GeoPoint();
            if (optInt == 0 || optInt2 == 0) {
                double optDouble3 = optJSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON, 0.0d);
                double optDouble4 = optJSONObject.optDouble("lat", 0.0d);
                if (!(optDouble3 == 0.0d || optDouble4 == 0.0d)) {
                    geoPoint = new GeoPoint(optDouble3, optDouble4);
                }
            } else {
                geoPoint = new GeoPoint(optInt, optInt2);
            }
            arrayList.add(geoPoint);
            createPOI.setEntranceList(arrayList);
        } catch (JSONException e) {
            kf.a((Throwable) e);
        }
        return createPOI;
    }

    public void startFootPage(final String str) {
        UiExecutor.post(new Runnable() {
            public void run() {
                if (!agr.b(AMapAppGlobal.getApplication())) {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.carowner_check_network));
                    return;
                }
                final MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                if (mapSharePreference.getBooleanValue("agree_onfoot_declare", false)) {
                    ModuleDriveCommonBusinessImpl.this.gotoFootNavi(str);
                } else {
                    ModuleDriveCommonBusinessImpl.this.showConfirmDlg(new OnClickListener() {
                        public void onClick(View view) {
                            if (view.getId() == R.id.confirm) {
                                ModuleDriveCommonBusinessImpl.this.gotoFootNavi(str);
                                mapSharePreference.putBooleanValue("agree_onfoot_declare", true);
                            }
                        }
                    });
                }
            }
        });
    }

    public PageBundle getFootNaviBundle(String str) {
        String str2;
        double d;
        double d2;
        try {
            JSONObject jSONObject = new JSONObject(str);
            d2 = jSONObject.optDouble("endLon");
            try {
                d = jSONObject.optDouble("endLat");
                try {
                    str2 = jSONObject.optString("endName");
                } catch (JSONException e) {
                    e = e;
                    e.printStackTrace();
                    str2 = "";
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("viewmode", 16);
                    pageBundle.putObject("startPoint", LocationInstrument.getInstance().getLatestPosition());
                    pageBundle.putObject("endPoint", new GeoPoint(d2, d));
                    pageBundle.putString("endPointName", str2);
                    pageBundle.putInt("pagefrom", 1);
                    return pageBundle;
                }
            } catch (JSONException e2) {
                e = e2;
                d = 0.0d;
                e.printStackTrace();
                str2 = "";
                PageBundle pageBundle2 = new PageBundle();
                pageBundle2.putInt("viewmode", 16);
                pageBundle2.putObject("startPoint", LocationInstrument.getInstance().getLatestPosition());
                pageBundle2.putObject("endPoint", new GeoPoint(d2, d));
                pageBundle2.putString("endPointName", str2);
                pageBundle2.putInt("pagefrom", 1);
                return pageBundle2;
            }
        } catch (JSONException e3) {
            e = e3;
            d2 = 0.0d;
            d = 0.0d;
            e.printStackTrace();
            str2 = "";
            PageBundle pageBundle22 = new PageBundle();
            pageBundle22.putInt("viewmode", 16);
            pageBundle22.putObject("startPoint", LocationInstrument.getInstance().getLatestPosition());
            pageBundle22.putObject("endPoint", new GeoPoint(d2, d));
            pageBundle22.putString("endPointName", str2);
            pageBundle22.putInt("pagefrom", 1);
            return pageBundle22;
        }
        PageBundle pageBundle222 = new PageBundle();
        pageBundle222.putInt("viewmode", 16);
        pageBundle222.putObject("startPoint", LocationInstrument.getInstance().getLatestPosition());
        if (!(d2 == 0.0d || d == 0.0d)) {
            pageBundle222.putObject("endPoint", new GeoPoint(d2, d));
        }
        pageBundle222.putString("endPointName", str2);
        pageBundle222.putInt("pagefrom", 1);
        return pageBundle222;
    }

    /* access modifiers changed from: private */
    public void showConfirmDlg(final OnClickListener onClickListener) {
        UiExecutor.post(new Runnable() {
            public void run() {
                new ConfirmDlg(AMapAppGlobal.getTopActivity(), onClickListener, R.layout.onfoot_declare).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void gotoFootNavi(String str) {
        if (!agr.b(AMapAppGlobal.getApplication())) {
            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.network_error_message));
            return;
        }
        avi avi = (avi) a.a.a(avi.class);
        if (avi != null) {
            avi.a(getFootNaviBundle(str));
        }
    }

    public void openTaxi(boolean z) {
        if (z) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("amapuri://drive/takeTaxi?sourceApplication=main"));
            Activity activity = DoNotUseTool.getActivity();
            if (activity != null) {
                activity.startActivity(intent);
            }
            return;
        }
        if (this.mJsCommandCallback != null) {
            this.mJsCommandCallback.callback(7, new String[0]);
        }
    }

    public void setJsCommandCallback(JsCommandCallback jsCommandCallback) {
        this.mJsCommandCallback = jsCommandCallback;
    }

    public String syncGetIndividuationCar() {
        String str = "";
        atf atf = (atf) a.a.a(atf.class);
        if (atf == null) {
            return str;
        }
        atj a = atf.a();
        if (a != null && a.d) {
            String str2 = a.g;
            String str3 = a.f;
            JSONObject jSONObject = new JSONObject();
            if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                try {
                    jSONObject.put("normal", "file:/".concat(String.valueOf(str2)));
                    jSONObject.put("weak", "file:/".concat(String.valueOf(str3)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            str = jSONObject.toString();
        }
        return str;
    }
}
