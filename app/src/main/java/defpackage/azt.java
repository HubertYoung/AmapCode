package defpackage;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: azt reason: default package */
/* compiled from: EtaDataInfo */
public final class azt {
    public String a = null;
    public int b = 0;
    public List<azx> c = new ArrayList();

    public final JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("distance", this.a);
        jSONObject.put("travel_time", this.b);
        JSONArray jSONArray = new JSONArray();
        if (this.c != null && this.c.size() > 0) {
            for (int i = 0; i < this.c.size(); i++) {
                azx azx = this.c.get(i);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("status", azx.a);
                jSONObject2.put("ratio", azx.b);
                jSONObject2.put("color", azx.c);
                jSONArray.put(jSONObject2);
            }
        }
        jSONObject.put("detail", jSONArray);
        return jSONObject;
    }
}
