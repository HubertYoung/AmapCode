package com.autonavi.minimap.route.coach.model;

import com.autonavi.minimap.route.train.model.IOrderSearchResult;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CoachOrderResultData implements IOrderSearchResult {
    private static final String JSON_ORDERS = "orders";
    private static final String JSON_ORDER_ACTION_URL = "action_url";
    private static final String JSON_ORDER_ARRIVE_CITY = "arrive_city";
    private static final String JSON_ORDER_ARRIVE_STATION = "arrive_station";
    private static final String JSON_ORDER_BUS_NUMBER = "bus_number";
    private static final String JSON_ORDER_DEPART_CITY = "depart_city";
    private static final String JSON_ORDER_DEPART_STATION = "depart_station";
    private static final String JSON_ORDER_DEPART_TIME = "depart_time";
    private static final String JSON_ORDER_ID = "amap_order_id";
    private static final String JSON_ORDER_IS_SHIFT = "is_shift";
    private static final String JSON_ORDER_STATUS = "status";
    private static final String JSON_ORDER_STATUS_NAME = "status_name";
    private static final String JSON_ORDER_TICKET_COUNT = "ticket_count";
    private static final String JSON_TOTAL = "total";
    private static final long serialVersionUID = 8934314824156278149L;
    public int curPage = 0;
    private String key;
    private ArrayList<eit> listData = new ArrayList<>();
    public int total = 0;

    public void reset() {
    }

    public void restoreData() {
    }

    public void saveData() {
    }

    public CoachOrderResultData(String str) {
        this.key = str;
    }

    public boolean parse(JSONObject jSONObject) {
        try {
            this.total = jSONObject.optInt("total");
            JSONArray optJSONArray = jSONObject.optJSONArray(JSON_ORDERS);
            int length = optJSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = (JSONObject) optJSONArray.get(i);
                dzc dzc = new dzc();
                dzc.a = jSONObject2.optString(JSON_ORDER_ID);
                dzc.b = jSONObject2.optString(JSON_ORDER_ACTION_URL);
                dzc.c = jSONObject2.optString(JSON_ORDER_DEPART_TIME);
                dzc.f = jSONObject2.optString(JSON_ORDER_DEPART_CITY);
                dzc.d = jSONObject2.optString(JSON_ORDER_DEPART_STATION);
                dzc.g = jSONObject2.optString(JSON_ORDER_ARRIVE_CITY);
                dzc.e = jSONObject2.optString(JSON_ORDER_ARRIVE_STATION);
                dzc.h = jSONObject2.optString(JSON_ORDER_BUS_NUMBER);
                dzc.j = jSONObject2.optString(JSON_ORDER_TICKET_COUNT);
                dzc.i = jSONObject2.optString(JSON_ORDER_STATUS_NAME);
                dzc.l = jSONObject2.optInt("status");
                dzc.k = jSONObject2.optBoolean(JSON_ORDER_IS_SHIFT);
                this.listData.add(dzc);
            }
        } catch (JSONException unused) {
        }
        return true;
    }

    public ArrayList<eit> getTotalOrdersList() {
        return this.listData;
    }

    public int getTotalOrderSize() {
        return this.total;
    }

    public void resetAll() {
        if (this.listData != null) {
            this.listData.clear();
        }
    }

    public void setPage(int i) {
        this.curPage = i;
    }

    public int getPage() {
        return this.curPage;
    }

    public boolean hasData() {
        return this.listData != null && this.listData.size() > 0;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public Class<?> getClassType() {
        return CoachOrderResultData.class;
    }
}
