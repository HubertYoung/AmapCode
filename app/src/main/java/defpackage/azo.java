package defpackage;

import com.amap.bundle.cloudconfig.appinit.request.AppInitCallback;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: azo reason: default package */
/* compiled from: CommuteConfigTipsRule */
public final class azo {
    public double a = 1500.0d;
    public double b = 50000.0d;
    public String c = "";
    private List<Integer> d;
    private List<a> e;

    public final void a(JSONObject jSONObject) {
        JSONArray optJSONArray = jSONObject.optJSONArray("time");
        if (optJSONArray != null) {
            this.e = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                this.e.add(a.a(optJSONObject.optString("startTime"), optJSONObject.optString(AppInitCallback.SP_KEY_endTime)));
            }
        }
    }

    public final void b(JSONObject jSONObject) {
        JSONArray optJSONArray = jSONObject.optJSONArray("week");
        if (optJSONArray != null) {
            this.d = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                this.d.add(Integer.valueOf(optJSONArray.optInt(i)));
            }
        }
    }
}
