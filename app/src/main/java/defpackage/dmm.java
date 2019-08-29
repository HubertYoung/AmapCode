package defpackage;

import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import org.json.JSONObject;

/* renamed from: dmm reason: default package */
/* compiled from: CallSMSAction */
public class dmm extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        if (a() != null) {
            bnz.a(DoNotUseTool.getContext(), (String) "", jSONObject.optString("message"));
        }
    }
}
