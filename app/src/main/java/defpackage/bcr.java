package defpackage;

import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import org.json.JSONObject;

/* renamed from: bcr reason: default package */
/* compiled from: PoiLayoutTemplateBaseParser */
public abstract class bcr implements bcq<PoiLayoutTemplate> {
    public abstract PoiLayoutTemplate a(JSONObject jSONObject);

    public final /* synthetic */ Object b(JSONObject jSONObject) {
        PoiLayoutTemplate a = a(jSONObject);
        a.setId(jSONObject.optInt("id"));
        a.setName(jSONObject.optString("name"));
        a.setType(jSONObject.optString("type"));
        return a;
    }
}
