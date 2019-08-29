package com.autonavi.minimap.route.sharebike.net.parser;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import com.autonavi.minimap.route.sharebike.model.BicycleBasicItem;
import com.autonavi.minimap.route.sharebike.model.BicycleStatus;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BicycleStatusResponser extends BaseResponser {
    public BicycleStatusResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        BicycleStatus bicycleStatus = new BicycleStatus();
        String str = new String(bArr);
        try {
            bicycleStatus.errorCode = this.mDataObject.getInt("code");
        } catch (Exception unused) {
            bicycleStatus.errorCode = 1;
        }
        JSONObject optJSONObject = new JSONObject(str).optJSONObject("bicycle");
        if (optJSONObject != null) {
            ego ego = new ego();
            ego.b = optJSONObject.optInt("code");
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
                            BicycleBasicItem bicycleBasicItem = new BicycleBasicItem();
                            bicycleBasicItem.setSource(jsonOptString(optJSONObject3, "source"));
                            bicycleBasicItem.setIconCode(jsonOptString(optJSONObject3, "icon_code"));
                            bicycleBasicItem.setX(optJSONObject3.optDouble(DictionaryKeys.CTRLXY_X));
                            bicycleBasicItem.setY(optJSONObject3.optDouble(DictionaryKeys.CTRLXY_Y));
                            bicycleBasicItem.setId(jsonOptString(optJSONObject3, "id"));
                            arrayList.add(bicycleBasicItem);
                        }
                    }
                    egp.c = arrayList;
                }
                ego.a = egp;
            }
            bicycleStatus.setBicycle(ego);
            setResult(bicycleStatus);
        }
    }
}
