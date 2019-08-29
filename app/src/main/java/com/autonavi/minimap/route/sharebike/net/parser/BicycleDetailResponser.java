package com.autonavi.minimap.route.sharebike.net.parser;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import com.autonavi.minimap.route.sharebike.model.BicycleDetailItem;
import com.autonavi.minimap.route.sharebike.model.BicycleStatus;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BicycleDetailResponser extends BaseResponser {
    public BicycleDetailResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        parseHeader(bArr);
        BicycleStatus bicycleStatus = new BicycleStatus();
        JSONObject optJSONObject = new JSONObject(new String(bArr)).optJSONObject("bicycle");
        if (optJSONObject != null) {
            JSONObject optJSONObject2 = optJSONObject.optJSONObject("bicycles");
            if (optJSONObject2 != null) {
                egp egp = new egp();
                egp.a = optJSONObject2.optString("icon_version");
                egp.b = optJSONObject2.optString("scope");
                ArrayList arrayList = new ArrayList();
                JSONArray optJSONArray = optJSONObject2.optJSONArray(RouteItem.ITEM_TAG);
                if (optJSONArray != null) {
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject optJSONObject3 = optJSONArray.optJSONObject(i);
                        if (optJSONObject3 != null) {
                            BicycleDetailItem bicycleDetailItem = new BicycleDetailItem();
                            bicycleDetailItem.partnerName = jsonOptString(optJSONObject3, "partner_name");
                            bicycleDetailItem.iconCode = jsonOptString(optJSONObject3, "icon_code");
                            bicycleDetailItem.logoCode = jsonOptString(optJSONObject3, "logo_code");
                            bicycleDetailItem.fees = jsonOptString(optJSONObject3, "fees");
                            bicycleDetailItem.feesInterval = jsonOptString(optJSONObject3, "fees_interval");
                            bicycleDetailItem.favourableDesc = jsonOptString(optJSONObject3, "favourable_description");
                            bicycleDetailItem.x = optJSONObject3.optDouble(DictionaryKeys.CTRLXY_X);
                            bicycleDetailItem.y = optJSONObject3.optDouble(DictionaryKeys.CTRLXY_Y);
                            bicycleDetailItem.status = jsonOptString(optJSONObject3, "status");
                            bicycleDetailItem.cpCode = jsonOptString(optJSONObject3, "cp_code");
                            arrayList.add(bicycleDetailItem);
                        }
                    }
                    egp.c = arrayList;
                }
                ego ego = new ego();
                ego.a = egp;
                bicycleStatus.setBicycle(ego);
                setResult(bicycleStatus);
            }
        }
    }
}
