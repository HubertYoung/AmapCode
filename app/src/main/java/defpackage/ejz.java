package defpackage;

import com.alibaba.appmonitor.offline.TempEvent;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ejz reason: default package */
/* compiled from: BusNaviReview */
public final class ejz {
    public String a;
    public int b;
    public String c;
    public String d;
    public String e;
    public String f;
    public int g;
    public int h;
    public List<Integer> i;
    public int j;
    public String k;
    public String l;

    public final JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(Oauth2AccessToken.KEY_UID, this.a);
            jSONObject.put("source", this.b);
            jSONObject.put("bsid", this.c);
            jSONObject.put("suggest", this.d);
            jSONObject.put(TempEvent.TAG_COMMITTIME, this.e);
            jSONObject.put("path_info", this.f);
            jSONObject.put("project_no", this.g);
            jSONObject.put("title_no", this.h);
            JSONArray jSONArray = new JSONArray();
            if (this.i != null) {
                for (int i2 = 0; i2 < this.i.size(); i2++) {
                    jSONArray.put(i2, this.i.get(i2));
                }
            }
            jSONObject.put("tags", jSONArray);
            jSONObject.put("star", this.j);
            jSONObject.put("tid", this.k);
            jSONObject.put("channel", this.l);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }
}
