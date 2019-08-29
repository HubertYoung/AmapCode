package com.autonavi.minimap.life.order.viewpoint.model;

import android.support.annotation.NonNull;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.autonavi.minimap.life.order.base.model.IOrderSearchResult;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewPointOrderResultData implements IOrderSearchResult {
    private static final long serialVersionUID = 1;
    public int curPage = 0;
    public ArrayList<dpl> invalidOrdersList = new ArrayList<>();
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

    public ViewPointOrderResultData(String str) {
        this.key = str;
    }

    public boolean parse(JSONObject jSONObject) {
        if (this.key == "VIEWPOINT_ORDER_SEARCH_RESULT_NEW") {
            this.total = jSONObject.optInt("total");
        } else {
            this.total = jSONObject.optInt(NewHtcHomeBadger.COUNT);
        }
        this.invalidOrdersList.addAll(parseOrders(jSONObject.optJSONArray("orders")));
        return true;
    }

    public ArrayList<dpl> getTotalOrdersList() {
        return this.invalidOrdersList;
    }

    public ArrayList<dqk> parseOrders(JSONArray jSONArray) {
        dqk dqk;
        ArrayList<dqk> arrayList = new ArrayList<>();
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            try {
                JSONObject jSONObject = (JSONObject) jSONArray.get(i);
                if (this.key == "VIEWPOINT_ORDER_SEARCH_RESULT_NEW") {
                    dqk = getViewPointOrderListEntityNew(jSONObject);
                } else {
                    dqk = getViewPointOrderListEntity(jSONObject);
                }
                arrayList.add(dqk);
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
        return arrayList;
    }

    @NonNull
    private dqk getViewPointOrderListEntity(JSONObject jSONObject) {
        dqk dqk = new dqk();
        dqk.a = jSONObject.optString("amap_order_id");
        dqk.d = jSONObject.optString("scenic_name");
        dqk.h = jSONObject.optString("ticket_name");
        dqk.b = jSONObject.optString(BioDetector.EXT_KEY_AMOUNT);
        dqk.c = jSONObject.optString("status");
        dqk.g = jSONObject.optString("total_in_cents");
        dqk.f = jSONObject.optString("ticket_date");
        return dqk;
    }

    @NonNull
    private dqk getViewPointOrderListEntityNew(JSONObject jSONObject) {
        dqk dqk = new dqk();
        dqk.l = true;
        dqk.a = jSONObject.optString("order_id");
        dqk.d = jSONObject.optString("poi_name");
        dqk.i = jSONObject.optString("poi_id");
        dqk.e = jSONObject.optString("poi_tel");
        dqk.j = jSONObject.optString("longitude");
        dqk.k = jSONObject.optString("latitude");
        dqk.b = jSONObject.optString(BioDetector.EXT_KEY_AMOUNT);
        dqk.c = jSONObject.optString("status");
        dqk.f = jSONObject.optString("ticket_date");
        return dqk;
    }

    public int getTotalOrderSize() {
        return this.total;
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

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public Class<?> getClassType() {
        return IOrderSearchResult.class;
    }
}
