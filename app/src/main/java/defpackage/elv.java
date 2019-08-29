package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.template.PoiTaobaoTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.util.ArrayList;
import org.json.JSONObject;

/* renamed from: elv reason: default package */
/* compiled from: PoiTextTemplateParser */
final class elv extends bcr {
    private SearchPoi a;

    public elv(SearchPoi searchPoi) {
        this.a = searchPoi;
    }

    public final PoiLayoutTemplate a(JSONObject jSONObject) {
        PoiTextTemplate poiTextTemplate = new PoiTextTemplate();
        String optString = jSONObject.optString("value");
        poiTextTemplate.setValue(optString);
        int optInt = jSONObject.optInt("id");
        int i = 0;
        if (optInt == 3002) {
            String optString2 = jSONObject.optString("value");
            ArrayList arrayList = new ArrayList();
            String[] split = optString2.split("\\|");
            int length = split.length;
            while (i < length) {
                ArrayList<GeoPoint> d = bcx.d(split[i]);
                if (d != null) {
                    arrayList.add(d);
                }
                i++;
            }
            this.a.getPoiExtra().put("poi_polygon_bounds", arrayList);
        } else if (optInt == 3004) {
            poiTextTemplate = new PoiTextTemplate();
            String optString3 = jSONObject.optString("value");
            if (!TextUtils.isEmpty(optString3)) {
                String[] split2 = optString3.split("\\|");
                int length2 = split2.length;
                ArrayList arrayList2 = new ArrayList(length2);
                while (i < length2) {
                    ArrayList<GeoPoint> d2 = bcx.d(split2[i]);
                    if (d2 != null) {
                        arrayList2.add(d2);
                    }
                    i++;
                }
                this.a.getPoiExtra().put("poi_roadaoi_bounds", arrayList2);
            }
        } else if (optInt == 2001) {
            this.a.setName(optString);
            poiTextTemplate.setColor(jSONObject.optString("color"));
        } else if (optInt == 2009) {
            this.a.setAddr(optString);
        } else if (optInt == 2032) {
            PoiTaobaoTemplate poiTaobaoTemplate = new PoiTaobaoTemplate();
            poiTaobaoTemplate.setName(jSONObject.optString("name"));
            poiTaobaoTemplate.setType(jSONObject.optString("type"));
            poiTaobaoTemplate.setSellScore(jSONObject.optString("sell_score"));
            poiTaobaoTemplate.setmCatlist(jSONObject.optString("m_catlist"));
            poiTaobaoTemplate.setDescCom(jSONObject.optString("desc_com"));
            poiTaobaoTemplate.setDescScore(jSONObject.optString("desc_score"));
            poiTaobaoTemplate.setServiceCom(jSONObject.optString("service_com"));
            poiTaobaoTemplate.setServiceScore(jSONObject.optString("service_score"));
            poiTaobaoTemplate.setDeliveryCom(jSONObject.optString("delivery_com"));
            poiTaobaoTemplate.setDeliveryScore(jSONObject.optString("delivery_score"));
            return poiTaobaoTemplate;
        }
        return poiTextTemplate;
    }
}
