package defpackage;

import android.text.TextUtils;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.bundle.statistics.LogManager;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bgb reason: default package */
/* compiled from: VoiceCMD */
public final class bgb {
    public int a;
    public String b;
    public String c;
    public String d;
    public long e;
    public String f = "";
    public int g;
    public String h;
    public String i;
    public int j;
    public String k;
    public int l = 1;
    public String m = "";
    public boolean n = false;
    private JSONObject o;
    private String p;

    public bgb(int i2, String str) {
        this.a = i2;
        this.b = str;
        if (!a()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("itemid", NetworkParam.getDiu());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            LogManager.actionLogV2("P00462", "B008", jSONObject);
        }
    }

    private boolean a() {
        if (TextUtils.isEmpty(this.b)) {
            bfp.a(e.a, 1, "cmdjson空");
            return false;
        }
        try {
            JSONObject optJSONObject = new JSONObject(this.b).optJSONObject("voiceCommandResponse");
            if (optJSONObject == null) {
                bfp.a(e.a, 1, "cmdbody空");
                return false;
            }
            this.c = optJSONObject.optString("keywords");
            this.h = optJSONObject.optString("tipText");
            this.i = optJSONObject.optString("errText");
            this.p = optJSONObject.optString("toastText");
            this.d = optJSONObject.optString("operate");
            this.o = optJSONObject.optJSONObject("paramStr");
            this.j = optJSONObject.optInt("type");
            this.k = optJSONObject.optString("type1");
            this.e = optJSONObject.optLong("scene_id");
            this.g = optJSONObject.optInt("autoListen");
            this.l = optJSONObject.optInt("breakTTS", 1);
            this.m = optJSONObject.optString("ctrlBits", "");
            this.f = optJSONObject.optString("taskId", "");
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            bfq bfq = e.a;
            StringBuilder sb = new StringBuilder("无关键字段");
            sb.append(e2.getMessage());
            bfp.a(bfq, 1, sb.toString());
            return false;
        }
    }
}
