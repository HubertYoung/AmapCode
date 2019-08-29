package defpackage;

import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: elu reason: default package */
/* compiled from: PoiTemplateParserUtils */
public final class elu {
    public static void a(JSONObject jSONObject, auk auk, SearchPoi searchPoi) {
        try {
            bcp bcp = new bcp();
            bcp.a("text");
            bcp.a((String) "text", (bcq<PoiLayoutTemplate>) new elv<PoiLayoutTemplate>(searchPoi));
            bcp.a((String) PoiLayoutTemplate.ARRAY, (bcq<PoiLayoutTemplate>) new elt<PoiLayoutTemplate>(auk, searchPoi));
            bcp.a(searchPoi, jSONObject);
            bcx.a(searchPoi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
