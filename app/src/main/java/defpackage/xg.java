package defpackage;

import android.support.annotation.Nullable;
import com.amap.bundle.lotuspool.internal.model.bean.CommandResult;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: xg reason: default package */
/* compiled from: CommandResults */
public final class xg {
    public final String a;
    public final long b;
    public long c;
    public int d;
    private int e = 1;
    private List<CommandResult> f = new ArrayList();

    public xg(String str, long j, long j2, int i) {
        this.a = str;
        this.b = j;
        this.c = j2;
        this.d = i;
    }

    public final String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append("_");
        sb.append(this.c);
        return sb.toString();
    }

    @Nullable
    public final JSONObject b() throws JSONException {
        if (this.f == null || this.f.isEmpty()) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("dispatch_id", this.a);
        jSONObject.put("result_code", this.e);
        jSONObject.put("dispatch_time", this.c);
        jSONObject.put("dispatch_type", this.d);
        JSONArray jSONArray = new JSONArray();
        for (CommandResult next : this.f) {
            if (next != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("command_id", next.h);
                jSONObject2.put("sequence", next.f);
                jSONObject2.put("code", next.i);
                jSONObject2.put("message", next.j);
                jSONArray.put(jSONObject2);
            }
        }
        jSONObject.put("detail", jSONArray);
        return jSONObject;
    }

    public final void a(CommandResult commandResult) {
        if (commandResult != null) {
            if (commandResult.i != 1) {
                this.e++;
            }
            this.f.add(commandResult);
        }
    }
}
