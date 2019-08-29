package com.autonavi.sync.beans;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GirfFavoriteRoute {
    public static final String JSON_FIELD_ROUTE_ALIAS = "route_alias";
    public static final String JSON_FIELD_ROUTE_BUS_PATH_SECTION = "busPathSection";
    public static final String JSON_FIELD_ROUTE_COST_TIME = "mCostTime";
    public static final String JSON_FIELD_ROUTE_CREATE_TIME = "create_time";
    public static final String JSON_FIELD_ROUTE_END_TIME = "end_time";
    public static final String JSON_FIELD_ROUTE_FROM_POI = "from_poi";
    public static final String JSON_FIELD_ROUTE_ID = "_id";
    public static final String JSON_FIELD_ROUTE_ITEM_ID = "id";
    public static final String JSON_FIELD_ROUTE_LEN = "route_len";
    public static final String JSON_FIELD_ROUTE_NAME = "route_name";
    public static final String JSON_FIELD_ROUTE_POI_NAME = "mName";
    public static final String JSON_FIELD_ROUTE_SECTION_NAME = "mSectionName";
    public static final String JSON_FIELD_ROUTE_START_TIME = "start_time";
    public static final String JSON_FIELD_ROUTE_TO_POI = "to_poi";
    public static final String JSON_FIELD_ROUTE_TYPE = "route_type";
    public String busPathSection = "";
    public ArrayList<String> busSectionNames = new ArrayList<>();
    public String costTime = "";
    public int createTime = 0;
    public String endTime = "";
    public String fromName = "";
    public int id = -1;
    public String itemId = "";
    public String len = "";
    public String routeAlias = "";
    public String routeName = "";
    public int routeType = 0;
    public String startTime = "";
    public String toName = "";

    public static GirfFavoriteRoute makeRoute(int i, String str) {
        GirfFavoriteRoute girfFavoriteRoute = new GirfFavoriteRoute();
        try {
            JSONObject jSONObject = new JSONObject(str);
            girfFavoriteRoute.id = i;
            if (jSONObject.has("id")) {
                girfFavoriteRoute.itemId = jSONObject.getString("id");
            }
            if (jSONObject.has("route_name")) {
                girfFavoriteRoute.routeName = jSONObject.getString("route_name");
            }
            if (jSONObject.has("route_type")) {
                girfFavoriteRoute.routeType = Integer.valueOf(jSONObject.getString("route_type")).intValue();
            }
            if (jSONObject.has("route_len")) {
                girfFavoriteRoute.len = jSONObject.getString("route_len");
            }
            if (jSONObject.has("create_time")) {
                girfFavoriteRoute.createTime = jSONObject.getInt("create_time");
            }
            if (jSONObject.has(JSON_FIELD_ROUTE_FROM_POI)) {
                girfFavoriteRoute.fromName = jSONObject.getString(JSON_FIELD_ROUTE_FROM_POI);
            }
            if (jSONObject.has(JSON_FIELD_ROUTE_TO_POI)) {
                girfFavoriteRoute.toName = jSONObject.getString(JSON_FIELD_ROUTE_TO_POI);
            }
            if (jSONObject.has(JSON_FIELD_ROUTE_START_TIME)) {
                girfFavoriteRoute.startTime = jSONObject.getString(JSON_FIELD_ROUTE_START_TIME);
            }
            if (jSONObject.has(JSON_FIELD_ROUTE_END_TIME)) {
                girfFavoriteRoute.endTime = jSONObject.getString(JSON_FIELD_ROUTE_END_TIME);
            }
            if (jSONObject.has(JSON_FIELD_ROUTE_COST_TIME)) {
                girfFavoriteRoute.costTime = jSONObject.getString(JSON_FIELD_ROUTE_COST_TIME);
            }
            girfFavoriteRoute.busPathSection = jSONObject.optString(JSON_FIELD_ROUTE_BUS_PATH_SECTION);
            girfFavoriteRoute.busSectionNames = parseBusSectionNames(girfFavoriteRoute.busPathSection);
            girfFavoriteRoute.routeAlias = jSONObject.optString("route_alias");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return girfFavoriteRoute;
    }

    private static ArrayList<String> parseBusSectionNames(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                if (jSONObject != null) {
                    String optString = jSONObject.optString(JSON_FIELD_ROUTE_SECTION_NAME);
                    if (optString.length() > 0) {
                        arrayList.add(optString);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
