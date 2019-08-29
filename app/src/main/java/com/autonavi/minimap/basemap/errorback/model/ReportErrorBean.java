package com.autonavi.minimap.basemap.errorback.model;

import android.content.res.Resources;
import android.text.TextUtils;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Result;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.SQLiteMapper.SQLiteEntry;
import com.autonavi.common.SQLiteMapper.SQLiteProperty;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@SQLiteEntry(name = "ReportErrorBean", version = 1)
@Keep
@KeepClassMembers
public class ReportErrorBean implements Serializable {
    private static final long serialVersionUID = 1023408621093182851L;
    @SQLiteProperty
    public String categort;
    public String chechStr;
    public String contact;
    public String content_options;
    @SQLiteProperty
    public long date;
    @SQLiteProperty
    public String description;
    public POI endPoi;
    @SQLiteProperty
    public String endPoiJson;
    @SQLiteProperty
    public String errorImgUrl;
    private String errorText;
    @SQLiteProperty
    public int errortype = -1;
    @SQLiteProperty
    public String expand;
    public POI fromPoi;
    @SQLiteProperty
    public String fromPoiJson;
    @SQLiteProperty("PRIMARY KEY AUTOINCREMENT")
    public int id;
    @SQLiteProperty
    public String naviId;
    public String plate;
    public POI positionPoi;
    @SQLiteProperty
    public String positionPoiJson;
    @SQLiteProperty
    public int reported = 0;
    @SQLiteProperty
    public String throughPoiJson;
    public List<POI> throughPois;
    @SQLiteProperty
    public String title = AMapAppGlobal.getApplication().getResources().getString(R.string.report_error_location);
    private String typeText;

    public ReportErrorBean() {
    }

    public ReportErrorBean(String str, String str2, POI poi, POI poi2, List<POI> list, POI poi3, String str3) {
        this.naviId = str;
        this.errorImgUrl = str2;
        this.date = new Date().getTime();
        this.fromPoi = poi;
        this.endPoi = poi2;
        this.throughPois = list;
        this.positionPoi = poi3;
        this.categort = str3;
        this.fromPoiJson = putPOIToJson(poi);
        this.endPoiJson = putPOIToJson(poi2);
        this.throughPoiJson = putPOIListToJson(list);
        this.positionPoiJson = putPOIToJson(poi3);
    }

    public void covertfromPoi() {
        this.fromPoiJson = putPOIToJson(this.fromPoi);
    }

    public void beanCovertPoi() {
        this.fromPoi = getPoiFromJsonString(this.fromPoiJson);
        this.endPoi = getPoiFromJsonString(this.endPoiJson);
        this.throughPois = getPoiListFromJson(this.throughPoiJson);
        this.positionPoi = getPoiFromJsonString(this.positionPoiJson);
    }

    public String getErrorText() {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        switch (this.errortype) {
            case 0:
                this.errorText = resources.getString(R.string.report_error_bean_routing_error);
                break;
            case 1:
                this.errorText = resources.getString(R.string.report_error_bean_camera_error);
                break;
            case 2:
                this.errorText = resources.getString(R.string.report_error_bean_broadcast_error);
                break;
            case 3:
                this.errorText = resources.getString(R.string.report_error_bean_road_data_error);
                break;
            default:
                this.errorText = resources.getString(R.string.report_error_bean_other_error);
                break;
        }
        return this.errorText;
    }

    public String getTypeText() {
        switch (this.errortype) {
            case 0:
                this.typeText = Result.RUBBISH_ACCOUNT;
                break;
            case 1:
                this.typeText = "6000";
                break;
            case 2:
                this.typeText = "6003";
                break;
            case 3:
                this.typeText = "4001";
                break;
            default:
                this.typeText = "4001";
                break;
        }
        return this.typeText;
    }

    private String putPOIToJson(POI poi) {
        if (poi == null) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        agd.a(jSONObject, (String) "mId", poi.getId());
        agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, poi.getName());
        agd.a(jSONObject, (String) "mAddr", poi.getAddr());
        agd.a(jSONObject, (String) "mCityCode", poi.getCityCode());
        agd.a(jSONObject, (String) "mCityName", poi.getCityName());
        agd.a(jSONObject, (String) "mEndPoiExtension", poi.getEndPoiExtension());
        agd.a(jSONObject, (String) "mTransparent", poi.getTransparent());
        agd.a(jSONObject, (String) "mx", poi.getPoint().x);
        agd.a(jSONObject, (String) "my", poi.getPoint().y);
        return jSONObject.toString();
    }

    private String putPOIListToJson(List<POI> list) {
        if (list == null || list.size() <= 0) {
            return "";
        }
        JSONArray jSONArray = new JSONArray();
        for (POI next : list) {
            if (next != null) {
                JSONObject jSONObject = new JSONObject();
                agd.a(jSONObject, (String) "mId", next.getId());
                agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME, next.getName());
                agd.a(jSONObject, (String) "mAddr", next.getAddr());
                agd.a(jSONObject, (String) "mCityCode", next.getCityCode());
                agd.a(jSONObject, (String) "mCityName", next.getCityName());
                agd.a(jSONObject, (String) "mEndPoiExtension", next.getEndPoiExtension());
                agd.a(jSONObject, (String) "mTransparent", next.getTransparent());
                agd.a(jSONObject, (String) "mx", next.getPoint().x);
                agd.a(jSONObject, (String) "my", next.getPoint().y);
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray.toString();
    }

    private POI getPoiFromJsonString(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(str);
            POI createPOI = POIFactory.createPOI();
            createPOI.setId(agd.e(jSONObject, "mId"));
            createPOI.setName(agd.e(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME));
            createPOI.setAddr(agd.e(jSONObject, "mAddr"));
            createPOI.setCityCode(agd.e(jSONObject, "mCityCode"));
            createPOI.setCityName(agd.e(jSONObject, "mCityName"));
            createPOI.setEndPoiExtension(agd.e(jSONObject, "mEndPoiExtension"));
            createPOI.setTransparent(agd.e(jSONObject, "mTransparent"));
            createPOI.setPoint(new GeoPoint());
            createPOI.getPoint().x = agd.a(jSONObject, "mx");
            createPOI.getPoint().y = agd.a(jSONObject, "my");
            return createPOI;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private POI getPoiFromJsonObject(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        POI createPOI = POIFactory.createPOI();
        createPOI.setId(agd.e(jSONObject, "mId"));
        createPOI.setName(agd.e(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_POI_NAME));
        createPOI.setAddr(agd.e(jSONObject, "mAddr"));
        createPOI.setCityCode(agd.e(jSONObject, "mCityCode"));
        createPOI.setCityName(agd.e(jSONObject, "mCityName"));
        createPOI.setEndPoiExtension(agd.e(jSONObject, "mEndPoiExtension"));
        createPOI.setTransparent(agd.e(jSONObject, "mTransparent"));
        createPOI.setPoint(new GeoPoint());
        createPOI.getPoint().x = agd.a(jSONObject, "mx");
        createPOI.getPoint().y = agd.a(jSONObject, "my");
        return createPOI;
    }

    private ArrayList<POI> getPoiListFromJson(String str) {
        ArrayList<POI> arrayList = new ArrayList<>();
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Object nextValue = new JSONTokener(str).nextValue();
            if (nextValue instanceof JSONObject) {
                POI poiFromJsonObject = getPoiFromJsonObject((JSONObject) nextValue);
                if (poiFromJsonObject != null) {
                    arrayList.add(poiFromJsonObject);
                }
                return arrayList;
            } else if (!(nextValue instanceof JSONArray)) {
                return arrayList;
            } else {
                JSONArray jSONArray = (JSONArray) nextValue;
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        POI poiFromJsonObject2 = getPoiFromJsonObject(optJSONObject);
                        if (poiFromJsonObject2 != null) {
                            arrayList.add(poiFromJsonObject2);
                        }
                    }
                }
                return arrayList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long.valueOf(this.date));
    }

    public String getTime() {
        return new SimpleDateFormat("HH:mm").format(Long.valueOf(this.date));
    }
}
