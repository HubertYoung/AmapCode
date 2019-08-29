package defpackage;

import com.amap.bundle.network.response.AbstractAOSParser;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: nu reason: default package */
/* compiled from: AosEtripResponser */
public final class nu extends AbstractAOSParser {
    protected ns a;

    public final String getErrorDesc(int i) {
        return null;
    }

    public final void parser(byte[] bArr) {
        try {
            JSONObject parseHeader = parseHeader(bArr);
            if (parseHeader != null) {
                this.a = a(parseHeader);
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    private static ns a(JSONObject jSONObject) {
        ns nsVar = new ns();
        String optString = jSONObject.optString("version");
        String optString2 = jSONObject.optString("message");
        String optString3 = jSONObject.optString(NewHtcHomeBadger.COUNT);
        String optString4 = jSONObject.optString("code");
        String optString5 = jSONObject.optString("bsid");
        nsVar.b = optString4;
        nsVar.a = optString;
        nsVar.d = optString3;
        nsVar.c = optString2;
        nsVar.e = optString5;
        JSONArray optJSONArray = jSONObject.optJSONArray("routelist");
        if (optJSONArray == null || optJSONArray.length() == 0) {
            return nsVar;
        }
        LinkedHashMap<String, nt> linkedHashMap = new LinkedHashMap<>();
        for (int i = 0; i < optJSONArray.length(); i++) {
            nt ntVar = new nt();
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            ntVar.b = optJSONObject.optString("datapacket");
            ntVar.a = String.valueOf(i);
            try {
                optJSONObject.put("datapacket", "");
                optJSONObject.put("datapacketKey", ntVar.a);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ntVar.c = optJSONObject;
            linkedHashMap.put(ntVar.a, ntVar);
        }
        nsVar.f = linkedHashMap;
        return nsVar;
    }

    public final ns a() {
        return this.a;
    }

    public final String b() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.a != null) {
                jSONObject.putOpt("version", this.a.a);
                jSONObject.putOpt("message", this.a.c);
                jSONObject.putOpt(NewHtcHomeBadger.COUNT, this.a.d);
                jSONObject.putOpt("code", this.a.b);
                jSONObject.putOpt("bsid", this.a.e);
                if ("1".equals(this.a.b)) {
                    LinkedHashMap<String, nt> linkedHashMap = this.a.f;
                    JSONArray jSONArray = new JSONArray();
                    for (Entry<String, nt> value : linkedHashMap.entrySet()) {
                        jSONArray.put(((nt) value.getValue()).c);
                    }
                    jSONObject.put("routelist", jSONArray);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
