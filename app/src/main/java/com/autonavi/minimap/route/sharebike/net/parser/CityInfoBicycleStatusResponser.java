package com.autonavi.minimap.route.sharebike.net.parser;

import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import com.autonavi.minimap.route.sharebike.model.CityInfo;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CityInfoBicycleStatusResponser extends BaseResponser {
    public CityInfoBicycleStatusResponser(Class cls, a aVar) {
        super(cls, aVar);
    }

    public void parser(byte[] bArr) throws UnsupportedEncodingException, JSONException {
        CityInfo cityInfo = new CityInfo();
        JSONObject optJSONObject = new JSONObject(new String(bArr)).optJSONObject("bicycle");
        if (optJSONObject != null) {
            cityInfo.errorCode = optJSONObject.optInt("code");
            cityInfo.result = cityInfo.errorCode == 0;
            JSONObject optJSONObject2 = optJSONObject.optJSONObject("city");
            if (optJSONObject2 != null) {
                JSONArray optJSONArray = optJSONObject2.optJSONArray(RouteItem.ITEM_TAG);
                if (optJSONArray != null) {
                    cityInfo.cpInfo = new ArrayList();
                    for (int i = 0; !optJSONArray.isNull(i); i++) {
                        JSONObject optJSONObject3 = optJSONArray.optJSONObject(0);
                        if (optJSONObject3 != null) {
                            JSONArray optJSONArray2 = optJSONObject3.optJSONArray("type");
                            if (optJSONArray2 != null) {
                                for (int i2 = 0; !optJSONArray2.isNull(i2); i2++) {
                                    JSONObject optJSONObject4 = optJSONArray2.optJSONObject(i2);
                                    if (optJSONObject4 != null) {
                                        egr egr = new egr();
                                        egr.a = jsonOptString(optJSONObject4, "source");
                                        egr.b = jsonOptString(optJSONObject4, "name");
                                        cityInfo.cpInfo.add(egr);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        setResult(cityInfo);
    }
}
