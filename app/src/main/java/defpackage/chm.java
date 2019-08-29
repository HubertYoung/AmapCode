package defpackage;

import com.autonavi.minimap.account.base.model.AccountProfile;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: chm reason: default package */
/* compiled from: CommonResponse */
public final class chm extends dkm {
    public int a = 0;
    public int b = 0;
    public String c = null;
    public AccountProfile d = new AccountProfile();
    public String e = null;
    public String f = null;
    public String g = null;
    public JSONObject h = null;
    public String i = null;
    public String j = null;

    public final void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            this.a = jSONObject.optInt("credit");
            this.b = jSONObject.optInt("insecure");
            this.c = jSONObject.optString("repwd");
            JSONObject optJSONObject = jSONObject.optJSONObject("profile");
            if (optJSONObject != null) {
                int optInt = optJSONObject.optInt("uss_level", 0);
                if (optInt > 0) {
                    this.i = String.valueOf(optInt);
                }
                int optInt2 = optJSONObject.optInt("emblem_num", 0);
                if (optInt2 > 0) {
                    this.j = String.valueOf(optInt2);
                }
                AccountProfile accountProfile = new AccountProfile();
                accountProfile.fromJson(optJSONObject);
                this.d = accountProfile;
            }
            this.e = jSONObject.optString("remain");
            this.f = jSONObject.optString("mobile");
            this.g = jSONObject.optString("verify_token");
            this.h = jSONObject.optJSONObject("order_conf");
        }
    }

    public final JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        json.put("credit", this.a);
        json.put("insecure", this.b);
        json.put("repwd", this.c);
        json.put("profile", this.d.toJson());
        json.put("remain", this.e);
        json.put("mobile", this.f);
        json.put("verify_token", this.g);
        json.put("order_conf", this.h);
        json.put("uss_level", this.i);
        json.put("emblem_num", this.j);
        return json;
    }
}
