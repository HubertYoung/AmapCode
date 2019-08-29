package defpackage;

import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import org.json.JSONObject;

/* renamed from: dml reason: default package */
/* compiled from: CallPhoneNumberAction */
public class dml extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        if (a() != null) {
            try {
                String string = jSONObject.getJSONObject("data").getString("phoneNumbers");
                DoNotUseTool.getContext();
                bnz.a(string);
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
