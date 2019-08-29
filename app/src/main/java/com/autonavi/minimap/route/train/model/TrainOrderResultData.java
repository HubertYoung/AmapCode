package com.autonavi.minimap.route.train.model;

import com.autonavi.sync.beans.GirfFavoriteRoute;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TrainOrderResultData implements IOrderSearchResult {
    private static final long serialVersionUID = -5181011572483336727L;
    String a = "FLAG_GRAY";
    public int curPage = 0;
    public ArrayList<eit> invalidOrdersList = new ArrayList<>();
    private String key;
    public int total = 0;

    public boolean hasData() {
        return false;
    }

    public void reset() {
    }

    public void restoreData() {
    }

    public void saveData() {
    }

    public void setKey(String str) {
    }

    public TrainOrderResultData(String str) {
        this.key = str;
    }

    public String getKey() {
        return this.key;
    }

    public Class<?> getClassType() {
        return TrainOrderResultData.class;
    }

    public boolean parse(JSONObject jSONObject) {
        this.total = jSONObject.optInt("total");
        this.invalidOrdersList.addAll(parseOrders(jSONObject.optJSONArray("orders"), this.a));
        return true;
    }

    public ArrayList<eit> getTotalOrdersList() {
        return this.invalidOrdersList;
    }

    public ArrayList<eiu> parseOrders(JSONArray jSONArray, String str) {
        ArrayList<eiu> arrayList = new ArrayList<>();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                eiu eiu = new eiu();
                eiu.i = jSONObject.optString("action_url");
                eiu.a = jSONObject.optString(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME);
                eiu.e = jSONObject.optString("start_station");
                eiu.f = jSONObject.optString("end_station");
                eiu.g = jSONObject.optInt("status");
                eiu.h = jSONObject.optString("status_name");
                eiu.b = jSONObject.optString("train_num");
                eiu.c = jSONObject.optString("ticket_num");
                eiu.d = jSONObject.optInt("seat");
                arrayList.add(eiu);
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
        return arrayList;
    }

    public void resetAll() {
        this.invalidOrdersList.clear();
    }

    public void setPage(int i) {
        this.curPage = i;
    }

    public int getPage() {
        return this.curPage;
    }

    public int getTotalOrderSize() {
        return this.total;
    }
}
