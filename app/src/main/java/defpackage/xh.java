package defpackage;

import com.amap.bundle.lotuspool.internal.model.bean.Command;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: xh reason: default package */
/* compiled from: Commands */
public final class xh {
    public String a;
    public long b;
    public long c;
    public int d;
    public List<Command> e;

    public xh(long j, String str, long j2, int i) {
        this.b = j;
        this.a = str;
        this.d = i;
        this.c = j2;
    }

    public xh(JSONObject jSONObject) throws JSONException {
        this.a = jSONObject.optString("dispatchId");
        this.b = jSONObject.optLong("dispatchSequence");
        this.c = jSONObject.optLong("dispatchTime");
        this.d = jSONObject.optInt("dispatchType");
        JSONArray optJSONArray = jSONObject.optJSONArray("commands");
        int length = optJSONArray.length();
        if (length != 0) {
            this.e = new ArrayList(length);
            for (int i = 0; i < length; i++) {
                Command command = new Command(this.a, this.b, this.c, this.d, optJSONArray.getJSONObject(i));
                this.e.add(command);
            }
        }
    }
}
