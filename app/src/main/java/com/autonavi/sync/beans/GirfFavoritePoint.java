package com.autonavi.sync.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class GirfFavoritePoint {
    public static final String JSON_FIELD_POI_ADDRESS = "address";
    public static final String JSON_FIELD_POI_CITY_CODE = "city_code";
    public static final String JSON_FIELD_POI_CITY_NAME = "city_name";
    public static final String JSON_FIELD_POI_CLASSIFICATION = "classification";
    public static final String JSON_FIELD_POI_COMMON_NAME = "common_name";
    public static final String JSON_FIELD_POI_CREATE_TIME = "create_time";
    public static final String JSON_FIELD_POI_CUSTOM_NAME = "custom_name";
    public static final String JSON_FIELD_POI_ID = "_id";
    public static final String JSON_FIELD_POI_ITEM_ID = "item_id";
    public static final String JSON_FIELD_POI_NAME = "name";
    public static final String JSON_FIELD_POI_NEW_TYPE = "newType";
    public static final String JSON_FIELD_POI_POINT_X = "point_x";
    public static final String JSON_FIELD_POI_POINT_Y = "point_y";
    public static final String JSON_FIELD_POI_TAG = "tag";
    public static final String JSON_FIELD_POI_TOP_TIME = "top_time";
    public static final String JSON_FIELD_POI_TYPE = "type";
    public String address = "";
    public String cityCode = "";
    public String cityName = "";
    public String classification = "";
    public String commonName = "";
    public String customName = "";
    public int id = -1;
    public String item_id = "";
    public String name = "";
    public String newType = "";
    public String px = "";
    public String py = "";
    public String tag = "";
    public int topTime = 0;
    public String type = "";

    public static GirfFavoritePoint makePoi(int i, String str) {
        GirfFavoritePoint girfFavoritePoint = new GirfFavoritePoint();
        try {
            JSONObject jSONObject = new JSONObject(str);
            girfFavoritePoint.id = i;
            if (jSONObject.has("item_id")) {
                girfFavoritePoint.item_id = jSONObject.getString("item_id");
            }
            if (jSONObject.has("name")) {
                girfFavoritePoint.name = jSONObject.getString("name");
            }
            if (jSONObject.has(JSON_FIELD_POI_POINT_X)) {
                girfFavoritePoint.px = jSONObject.getString(JSON_FIELD_POI_POINT_X);
            }
            if (jSONObject.has(JSON_FIELD_POI_POINT_Y)) {
                girfFavoritePoint.py = jSONObject.getString(JSON_FIELD_POI_POINT_Y);
            }
            if (jSONObject.has("city_code")) {
                girfFavoritePoint.cityCode = jSONObject.getString("city_code");
            }
            if (jSONObject.has("tag")) {
                girfFavoritePoint.tag = jSONObject.getString("tag");
            }
            if (jSONObject.has(JSON_FIELD_POI_NEW_TYPE)) {
                girfFavoritePoint.newType = jSONObject.getString(JSON_FIELD_POI_NEW_TYPE);
            }
            if (jSONObject.has(JSON_FIELD_POI_CLASSIFICATION)) {
                girfFavoritePoint.classification = jSONObject.getString(JSON_FIELD_POI_CLASSIFICATION);
            }
            if (jSONObject.has(JSON_FIELD_POI_COMMON_NAME)) {
                girfFavoritePoint.commonName = jSONObject.getString(JSON_FIELD_POI_COMMON_NAME);
            }
            if (jSONObject.has(JSON_FIELD_POI_CUSTOM_NAME)) {
                girfFavoritePoint.customName = jSONObject.getString(JSON_FIELD_POI_CUSTOM_NAME);
            }
            if (jSONObject.has(JSON_FIELD_POI_TOP_TIME)) {
                girfFavoritePoint.topTime = jSONObject.getInt(JSON_FIELD_POI_TOP_TIME);
            }
            if (jSONObject.has("address")) {
                girfFavoritePoint.address = jSONObject.getString("address");
            }
            if (jSONObject.has("type")) {
                girfFavoritePoint.type = jSONObject.getString("type");
            }
            if (jSONObject.has("city_name")) {
                girfFavoritePoint.cityName = jSONObject.getString("city_name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return girfFavoritePoint;
    }
}
