package defpackage;

import com.alibaba.appmonitor.offline.TempEvent;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: eka reason: default package */
/* compiled from: FootNaviReview */
public final class eka {
    public String a;
    public String b;
    public String c;
    public int d;
    public String e;
    public List<Integer> f;
    public int g;
    public String h;
    public String i;

    public final JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Oauth2AccessToken.KEY_UID, this.a);
            jSONObject.put("source", this.d);
            jSONObject.put("naviid", this.b);
            jSONObject.put("suggest", this.c);
            jSONObject.put(TempEvent.TAG_COMMITTIME, this.e);
            JSONArray jSONArray = new JSONArray();
            if (this.f != null) {
                for (int i2 = 0; i2 < this.f.size(); i2++) {
                    jSONArray.put(i2, this.f.get(i2));
                }
            }
            jSONObject.put("tags", jSONArray);
            jSONObject.put("star", this.g);
            jSONObject.put("tid", this.h);
            jSONObject.put("channel", this.i);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }
}
