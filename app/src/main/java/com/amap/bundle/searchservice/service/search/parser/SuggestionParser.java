package com.amap.bundle.searchservice.service.search.parser;

import android.graphics.Color;
import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class SuggestionParser {
    public static final int CODE_SUCCESS = 1;
    public static final int TYPE_HIS = 0;
    public static final int TYPE_TIP = 1;
    public int searchType = 0;
    public List<TipItem> tipItems = null;

    public static aux parser(byte[] bArr) {
        aux aux = new aux();
        try {
            JSONObject jSONObject = new JSONObject(new String(bArr, "UTF-8"));
            if (jSONObject.optInt("code") == 1) {
                aux.a = jSONObject.optInt("is_general_search");
                JSONArray optJSONArray = jSONObject.optJSONArray("tip_list");
                if (optJSONArray != null) {
                    int length = optJSONArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject optJSONObject = optJSONArray.getJSONObject(i).optJSONObject("tip");
                        if (optJSONObject != null) {
                            TipItem parserTipItem = parserTipItem(optJSONObject);
                            if (parserTipItem != null) {
                                parserTipItem.type = 1;
                                if (!TextUtils.isEmpty(parserTipItem.name)) {
                                    aux.b.add(parserTipItem);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        return aux;
    }

    protected static TipItem parserTipItem(JSONObject jSONObject) throws JSONException {
        TipItem tipItem = new TipItem();
        if (jSONObject.has("datatype")) {
            try {
                tipItem.dataType = Integer.parseInt(jSONObject.optString("datatype"));
            } catch (Exception e) {
                kf.a((Throwable) e);
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
        tipItem.addr = jSONObject.optString("address");
        tipItem.name = jSONObject.optString("name");
        tipItem.adcode = jSONObject.optString(AutoJsonUtils.JSON_ADCODE);
        tipItem.district = jSONObject.optString("district");
        tipItem.poiid = jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
        tipItem.super_address = jSONObject.optString("super_address");
        JSONArray optJSONArray = jSONObject.optJSONArray("child_nodes");
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            tipItem.tipItemList = new ArrayList();
            for (int i = 0; i < length; i++) {
                TipItem parserTipItem = parserTipItem(optJSONArray.getJSONObject(i));
                if (TextUtils.isEmpty(parserTipItem.parent)) {
                    parserTipItem.parent = tipItem.poiid;
                }
                parserTipItem.type = 1;
                if (!TextUtils.isEmpty(parserTipItem.name)) {
                    tipItem.tipItemList.add(parserTipItem);
                }
            }
        }
        try {
            String optString = jSONObject.optString(DictionaryKeys.CTRLXY_X);
            if (!TextUtils.isEmpty(optString)) {
                tipItem.x = Double.valueOf(optString).doubleValue();
            }
        } catch (Exception e2) {
            kf.a((Throwable) e2);
        }
        try {
            String optString2 = jSONObject.optString(DictionaryKeys.CTRLXY_Y);
            if (!TextUtils.isEmpty(optString2)) {
                tipItem.y = Double.valueOf(optString2).doubleValue();
            }
        } catch (Exception e3) {
            kf.a((Throwable) e3);
        }
        if (jSONObject.has("poiinfo")) {
            tipItem.poiinfo = jSONObject.optString("poiinfo");
        } else if (jSONObject.has("deep_info")) {
            tipItem.poiinfo = jSONObject.optString("deep_info");
        }
        if (jSONObject.has("poiinfo_color")) {
            String optString3 = jSONObject.optString("poiinfo_color");
            if (!TextUtils.isEmpty(optString3)) {
                try {
                    if (!optString3.startsWith(MetaRecord.LOG_SEPARATOR)) {
                        optString3 = MetaRecord.LOG_SEPARATOR.concat(String.valueOf(optString3));
                    }
                    tipItem.poiinfoColor = Color.parseColor(optString3);
                } catch (Exception e4) {
                    kf.a((Throwable) e4);
                }
            }
        }
        tipItem.poiTag = jSONObject.optString("poi_tag");
        tipItem.funcText = jSONObject.optString("func_text");
        if (jSONObject.has("datatype_spec")) {
            String optString4 = jSONObject.optString("datatype_spec");
            if (!TextUtils.isEmpty(optString4)) {
                try {
                    tipItem.iconinfo = Integer.parseInt(optString4);
                } catch (Exception e5) {
                    kf.a((Throwable) e5);
                }
            }
        }
        tipItem.searchQuery = jSONObject.optString("search_query");
        tipItem.searchType = jSONObject.optString("search_type");
        tipItem.newType = jSONObject.optString("category");
        tipItem.taginfo = jSONObject.optString("taginfo");
        if (jSONObject.has("rich_rating")) {
            String optString5 = jSONObject.optString("rich_rating");
            if (tipItem.isRating(optString5)) {
                tipItem.richRating = optString5;
            } else {
                tipItem.richRating = "";
            }
            if (jSONObject.has("num_review")) {
                String optString6 = jSONObject.optString("num_review");
                if (tipItem.richRating == null || tipItem.richRating.isEmpty()) {
                    tipItem.numReview = "";
                } else {
                    tipItem.numReview = optString6;
                }
            }
        }
        if (jSONObject.has("x_entr")) {
            tipItem.x_entr = Double.valueOf(jSONObject.getDouble("x_entr")).doubleValue();
        }
        if (jSONObject.has("y_entr")) {
            tipItem.y_entr = Double.valueOf(jSONObject.getDouble("y_entr")).doubleValue();
        }
        tipItem.parent = jSONObject.optString("parent");
        if (jSONObject.has("childType")) {
            tipItem.childType = jSONObject.optString("childType");
        } else if (jSONObject.has("childtype")) {
            tipItem.childType = jSONObject.optString("childtype");
        }
        tipItem.f_nona = jSONObject.optString("f_nona");
        tipItem.towardsAngle = jSONObject.optString("towards_angle");
        tipItem.endPoiExtension = jSONObject.optString("end_poi_extension");
        tipItem.transparent = jSONObject.optString(H5Param.LONG_TRANSPARENT);
        tipItem.extensionType = jSONObject.optString("extension_type");
        tipItem.needHistory = jSONObject.optString("need_history");
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
                    if (jSONObject2.has(ActionConstant.SCHEMA)) {
                        auy.c = jSONObject2.optString(ActionConstant.SCHEMA);
                    }
                    tipItem.extensionInfo = auy;
                }
            } catch (JSONException unused) {
            }
        }
        return tipItem;
    }
}
