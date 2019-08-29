package defpackage;

import android.graphics.Color;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bxs reason: default package */
/* compiled from: SearchHomeSuggDataHelper */
public final class bxs {
    public static TipItem a(JSONObject jSONObject) throws JSONException {
        TipItem tipItem = new TipItem();
        tipItem.origJson = jSONObject;
        if (jSONObject.has("datatype")) {
            tipItem.dataType = Integer.parseInt(jSONObject.getString("datatype"));
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("child_nodes");
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            tipItem.tipItemList = new ArrayList();
            for (int i = 0; i < length; i++) {
                TipItem a = a(optJSONArray.getJSONObject(i));
                a.type = 1;
                if (!TextUtils.isEmpty(a.name)) {
                    tipItem.tipItemList.add(a);
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
        tipItem.label = jSONObject.optString("label");
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
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        if (jSONObject.has(DictionaryKeys.CTRLXY_Y)) {
            try {
                String string2 = jSONObject.getString(DictionaryKeys.CTRLXY_Y);
                if (!TextUtils.isEmpty(string2)) {
                    tipItem.y = Double.valueOf(string2).doubleValue();
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
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
                } catch (Exception e3) {
                    kf.a((Throwable) e3);
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
                } catch (Exception e4) {
                    kf.a((Throwable) e4);
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
        if (jSONObject.has("parent")) {
            tipItem.parent = jSONObject.optString("parent");
        }
        if (jSONObject.has("childType")) {
            tipItem.childType = jSONObject.optString("childType");
        } else if (jSONObject.has("childtype")) {
            tipItem.childType = jSONObject.optString("childtype");
        }
        if (jSONObject.has("f_nona")) {
            tipItem.f_nona = jSONObject.optString("f_nona");
        }
        if (jSONObject.has("towards_angle")) {
            tipItem.towardsAngle = jSONObject.optString("towards_angle");
        }
        if (jSONObject.has("end_poi_extension")) {
            tipItem.endPoiExtension = jSONObject.optString("end_poi_extension");
        }
        if (jSONObject.has(H5Param.LONG_TRANSPARENT)) {
            tipItem.transparent = jSONObject.optString(H5Param.LONG_TRANSPARENT);
        }
        if (jSONObject.has("extension_type")) {
            tipItem.extensionType = jSONObject.optString("extension_type");
        }
        if (jSONObject.has("need_history")) {
            tipItem.needHistory = jSONObject.optString("need_history");
        }
        if (jSONObject.has("extension_info")) {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("extension_info");
                if (jSONObject2 != null) {
                    auy auy = new auy();
                    if (jSONObject2.has("tag")) {
                        auy.a = jSONObject2.optString("tag");
                    }
                    if (jSONObject2.has("color")) {
                        auy.b = jSONObject2.optString("color");
                    }
                    if (jSONObject2.has("need_history")) {
                        auy.c = jSONObject2.optString("need_history");
                    }
                    tipItem.extensionInfo = auy;
                }
            } catch (JSONException unused) {
            }
        }
        tipItem.type = 1;
        if (jSONObject.has("isSugChildClick")) {
            tipItem.isSugChildClick = jSONObject.optBoolean("isSugChildClick");
        }
        return tipItem;
    }

    public static TipItem b(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        TipItem tipItem = new TipItem();
        try {
            tipItem.type = jSONObject.optInt("type");
            tipItem.dataType = jSONObject.optInt("datatype");
            tipItem.name = jSONObject.optString("name");
            tipItem.adcode = jSONObject.optString(AutoJsonUtils.JSON_ADCODE);
            tipItem.district = jSONObject.optString("district");
            tipItem.poiid = jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
            tipItem.addr = jSONObject.optString("address");
            tipItem.x = jSONObject.optDouble(DictionaryKeys.CTRLXY_X);
            tipItem.y = jSONObject.optDouble(DictionaryKeys.CTRLXY_Y);
            tipItem.poiTag = jSONObject.optString("poi_tag");
            tipItem.funcText = jSONObject.optString("func_text");
            tipItem.shortname = jSONObject.optString("short_name");
            tipItem.displayInfo = jSONObject.optString("display_info");
            tipItem.searchQuery = jSONObject.optString("search_query");
            tipItem.terminals = jSONObject.optString("terminals");
            tipItem.ignoreDistrict = jSONObject.optInt("ignore_district");
            tipItem.parent = jSONObject.optString("parent");
            if (jSONObject.has("childType")) {
                tipItem.childType = jSONObject.optString("childType");
            } else if (jSONObject.has("childtype")) {
                tipItem.childType = jSONObject.optString("childtype");
            }
            tipItem.towardsAngle = jSONObject.optString("towards_angle");
            tipItem.endPoiExtension = jSONObject.optString("end_poi_extension");
            tipItem.transparent = jSONObject.optString(H5Param.LONG_TRANSPARENT);
            tipItem.sndtFloorName = jSONObject.optString("sndt_fl_nona");
            tipItem.f_nona = jSONObject.optString("f_nona");
            JSONArray optJSONArray = jSONObject.optJSONArray("search_tag");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    String optString = optJSONArray.optString(i);
                    if (!TextUtils.isEmpty(optString)) {
                        tipItem.addInputs(optString);
                    }
                }
            }
            int size = tipItem.inputs.size();
            tipItem.getClass();
            if (size < 3) {
                JSONArray optJSONArray2 = jSONObject.optJSONArray("search_query_set");
                if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        String optString2 = optJSONArray2.optString(i2);
                        if (!TextUtils.isEmpty(optString2)) {
                            tipItem.addInputs(optString2);
                        }
                    }
                }
            }
            int size2 = tipItem.inputs.size();
            StringBuilder sb = new StringBuilder();
            for (int i3 = 0; i3 < size2; i3++) {
                sb.append(tipItem.inputs.get(i3));
                if (i3 < size2) {
                    sb.append(SearchHistoryHelper.S);
                }
            }
            tipItem.searchTag = sb.toString();
            tipItem.time = new Date(jSONObject.optLong("update_time") * 1000);
            tipItem.historyType = jSONObject.optInt("history_type");
            tipItem.richRating = jSONObject.optString("rich_rating");
            tipItem.numReview = jSONObject.optString("num_review");
            tipItem.newType = jSONObject.optString("category");
            tipItem.x_entr = jSONObject.optDouble("x_entr");
            tipItem.y_entr = jSONObject.optDouble("y_entr");
            tipItem.super_address = jSONObject.optString("super_address");
            tipItem.iconinfo = jSONObject.optInt("datatype_spec");
            tipItem.origJson = jSONObject.optJSONObject("origJson");
        } catch (Exception unused) {
        }
        return tipItem;
    }

    public static JSONObject a(TipItem tipItem) {
        if (tipItem == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", agy.a(tipItem.name));
            jSONObject.put("type", tipItem.type);
            jSONObject.put("datatype", tipItem.dataType);
            jSONObject.put("name", tipItem.name);
            jSONObject.put(AutoJsonUtils.JSON_ADCODE, tipItem.adcode);
            jSONObject.put("district", tipItem.district);
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, tipItem.poiid);
            jSONObject.put("address", tipItem.addr);
            jSONObject.put(DictionaryKeys.CTRLXY_X, String.valueOf(tipItem.x));
            jSONObject.put(DictionaryKeys.CTRLXY_Y, String.valueOf(tipItem.y));
            jSONObject.put("poi_tag", tipItem.poiTag);
            jSONObject.put("func_text", tipItem.funcText);
            jSONObject.put("short_name", tipItem.shortname);
            jSONObject.put("display_info", tipItem.displayInfo);
            jSONObject.put("search_query", tipItem.searchQuery);
            jSONObject.put("terminals", tipItem.terminals);
            jSONObject.put("ignore_district", String.valueOf(tipItem.ignoreDistrict));
            JSONArray jSONArray = new JSONArray();
            if (tipItem.inputs != null && !tipItem.inputs.isEmpty()) {
                for (String put : tipItem.inputs) {
                    jSONArray.put(put);
                }
            }
            jSONObject.put("search_tag", jSONArray);
            if (tipItem.time != null) {
                jSONObject.put("update_time", tipItem.time.getTime() / 1000);
            }
            jSONObject.put("history_type", tipItem.historyType);
            jSONObject.put("rich_rating", tipItem.richRating);
            jSONObject.put("num_review", tipItem.numReview);
            jSONObject.put("category", tipItem.newType);
            jSONObject.put("x_entr", String.valueOf(tipItem.x_entr));
            jSONObject.put("y_entr", String.valueOf(tipItem.y_entr));
            jSONObject.put("super_address", tipItem.super_address);
            jSONObject.put("datatype_spec", tipItem.iconinfo);
            jSONObject.put("parent", tipItem.parent);
            jSONObject.put("childType", tipItem.childType);
            jSONObject.put("towards_angle", tipItem.towardsAngle);
            jSONObject.put("end_poi_extension", tipItem.endPoiExtension);
            jSONObject.put(H5Param.LONG_TRANSPARENT, tipItem.transparent);
            jSONObject.put("sndt_fl_nona", tipItem.sndtFloorName);
            jSONObject.put("f_nona", tipItem.f_nona);
            jSONObject.put("origJson", tipItem.origJson);
        } catch (Exception unused) {
        }
        return jSONObject;
    }
}
