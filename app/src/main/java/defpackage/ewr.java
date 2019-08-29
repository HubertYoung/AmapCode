package defpackage;

import android.content.Context;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ewr reason: default package */
/* compiled from: UploadService */
public class ewr {
    private static final String e = "ewr";
    public JSONObject a = new JSONObject();
    public String b = "";
    public String c = "";
    public Context d;

    public ewr(Context context, List<ewj> list, String str, String str2) {
        this.b = str;
        this.c = str2;
        this.d = context.getApplicationContext();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ewj ewj = list.get(i);
            try {
                if (this.a.isNull(ewj.b)) {
                    this.a.put(ewj.b, new JSONArray());
                }
                this.a.getJSONArray(ewj.b).put(new JSONObject(ewj.c));
            } catch (JSONException e2) {
                StringBuilder sb = new StringBuilder("Collected:");
                sb.append(e2.getMessage());
                euw.a(sb.toString());
            }
        }
    }
}
