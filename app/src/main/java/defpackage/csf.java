package defpackage;

import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import org.json.JSONObject;

/* renamed from: csf reason: default package */
/* compiled from: TrafficDetailResultInfo */
public final class csf {
    public boolean a = false;
    public TrafficTopic b;

    public final int a() {
        if (this.b == null) {
            return 0;
        }
        return this.b.getId();
    }

    public final void a(JSONObject jSONObject) {
        boolean z = true;
        if (1 != jSONObject.optInt("code")) {
            z = false;
        }
        this.a = z;
        if (this.a) {
            JSONObject optJSONObject = jSONObject.optJSONObject("eventdetail");
            if (optJSONObject != null) {
                this.b = new TrafficTopic(optJSONObject);
            } else {
                this.a = false;
            }
        }
    }
}
