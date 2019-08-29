package com.autonavi.bundle.feedback.contribution;

import android.graphics.Color;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.network.response.AosParserResponse;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetInputSuggestionResponse extends AosParserResponse {
    public int a = 0;
    public List<TipItem> l = null;

    public final TipItem a(JSONObject jSONObject) throws JSONException {
        TipItem tipItem = new TipItem();
        if (jSONObject.has("datatype")) {
            try {
                tipItem.dataType = Integer.parseInt(jSONObject.getString("datatype"));
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("child_nodes");
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            tipItem.tipItemList = new ArrayList();
            for (int i = 0; i < length; i++) {
                TipItem a2 = a(optJSONArray.getJSONObject(i));
                a2.type = 1;
                if (!TextUtils.isEmpty(a2.name)) {
                    tipItem.tipItemList.add(a2);
                }
            }
        }
        tipItem.setChildNodeIconUrl(jSONObject.optString("icon_url"));
        tipItem.setChildNodeName(jSONObject.optString("shortname"));
        JSONObject optJSONObject = jSONObject.optJSONObject("top_list");
        if (optJSONObject != null) {
            tipItem.setTopListType(optJSONObject.optString("toplist_type"));
            tipItem.setTopListName(optJSONObject.optString("toplist_name"));
            tipItem.setTopListUrl(optJSONObject.optString("toplist_url"));
        }
        tipItem.column = jSONObject.optInt("column");
        tipItem.terminals = jSONObject.optString("terminals");
        tipItem.ignoreDistrict = jSONObject.optInt("ignore_district");
        tipItem.displayInfo = jSONObject.optString("display_info");
        tipItem.shortname = jSONObject.optString("shortname");
        if (jSONObject.has("name")) {
            tipItem.name = jSONObject.getString("name");
        }
        if (jSONObject.has(AutoJsonUtils.JSON_ADCODE)) {
            tipItem.adcode = jSONObject.getString(AutoJsonUtils.JSON_ADCODE);
        }
        if (jSONObject.has("district")) {
            tipItem.district = jSONObject.getString("district");
        }
        if (jSONObject.has(LocationInstrument.LOCATION_EXTRAS_KEY_POIID)) {
            tipItem.poiid = jSONObject.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
        }
        if (jSONObject.has("address")) {
            tipItem.addr = jSONObject.getString("address");
        }
        if (jSONObject.has("super_address")) {
            tipItem.super_address = jSONObject.getString("super_address");
        }
        if (jSONObject.has(DictionaryKeys.CTRLXY_X)) {
            try {
                String string = jSONObject.getString(DictionaryKeys.CTRLXY_X);
                if (!TextUtils.isEmpty(string)) {
                    tipItem.x = Double.valueOf(string).doubleValue();
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
        if (jSONObject.has(DictionaryKeys.CTRLXY_Y)) {
            try {
                String string2 = jSONObject.getString(DictionaryKeys.CTRLXY_Y);
                if (!TextUtils.isEmpty(string2)) {
                    tipItem.y = Double.valueOf(string2).doubleValue();
                }
            } catch (Exception e3) {
                kf.a((Throwable) e3);
            }
        }
        if (jSONObject.has("poiinfo")) {
            tipItem.poiinfo = jSONObject.getString("poiinfo");
        } else if (jSONObject.has("deep_info")) {
            tipItem.poiinfo = jSONObject.getString("deep_info");
        }
        if (jSONObject.has("poiinfo_color")) {
            String string3 = jSONObject.getString("poiinfo_color");
            if (!TextUtils.isEmpty(string3)) {
                try {
                    if (!string3.startsWith(MetaRecord.LOG_SEPARATOR)) {
                        string3 = MetaRecord.LOG_SEPARATOR.concat(String.valueOf(string3));
                    }
                    tipItem.poiinfoColor = Color.parseColor(string3);
                } catch (Exception e4) {
                    kf.a((Throwable) e4);
                }
            }
        }
        if (jSONObject.has("poi_tag")) {
            tipItem.poiTag = jSONObject.getString("poi_tag");
        }
        if (jSONObject.has("func_text")) {
            tipItem.funcText = jSONObject.getString("func_text");
        }
        if (jSONObject.has("datatype_spec")) {
            String string4 = jSONObject.getString("datatype_spec");
            if (!TextUtils.isEmpty(string4)) {
                try {
                    tipItem.iconinfo = Integer.parseInt(string4);
                } catch (Exception e5) {
                    kf.a((Throwable) e5);
                }
            }
        }
        if (jSONObject.has("search_query")) {
            tipItem.searchQuery = jSONObject.getString("search_query");
        }
        if (jSONObject.has("search_type")) {
            tipItem.searchType = jSONObject.getString("search_type");
        }
        if (jSONObject.has("category")) {
            tipItem.newType = jSONObject.getString("category");
        }
        if (jSONObject.has("taginfo")) {
            tipItem.taginfo = jSONObject.getString("taginfo");
        }
        if (jSONObject.has("rich_rating")) {
            String string5 = jSONObject.getString("rich_rating");
            if (tipItem.isRating(string5)) {
                tipItem.richRating = string5;
            } else {
                tipItem.richRating = "";
            }
            if (jSONObject.has("num_review")) {
                String string6 = jSONObject.getString("num_review");
                if (tipItem.richRating == null || tipItem.richRating.isEmpty()) {
                    tipItem.numReview = "";
                } else {
                    tipItem.numReview = string6;
                }
            }
        }
        if (jSONObject.has("x_entr")) {
            tipItem.x_entr = Double.valueOf(jSONObject.getDouble("x_entr")).doubleValue();
        }
        if (jSONObject.has("y_entr")) {
            tipItem.y_entr = Double.valueOf(jSONObject.getDouble("y_entr")).doubleValue();
        }
        return tipItem;
    }

    public final String b() {
        return this.g;
    }
}
