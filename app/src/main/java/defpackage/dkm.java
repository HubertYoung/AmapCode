package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dkm reason: default package */
/* compiled from: BaseResponse */
public abstract class dkm {
    public int code;
    public String err_order_id;
    public String errmsg;
    public String message;
    public boolean result;
    public String timestamp;
    public String version;

    public void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.result = jSONObject.optBoolean("result");
            this.code = jSONObject.optInt("code");
            this.message = jSONObject.optString("message");
            this.errmsg = jSONObject.optString("errmsg");
            this.timestamp = jSONObject.optString("timestamp");
            this.version = jSONObject.optString("version");
            this.err_order_id = jSONObject.optString("err_order_id");
        }
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("result", this.result);
        jSONObject.put("code", this.code);
        jSONObject.put("message", this.message);
        jSONObject.put("errmsg", this.errmsg);
        jSONObject.put("timestamp", this.timestamp);
        jSONObject.put("version", this.version);
        jSONObject.put("err_order_id", this.err_order_id);
        return jSONObject;
    }
}
