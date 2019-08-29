package defpackage;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: etd reason: default package */
public final class etd extends ete {
    public static final String a = null;
    public String b;
    public String c;
    public int d;
    public String e;
    public int f = -2;
    private String g;
    private String h;

    public final int a() {
        return 4105;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("type:4105,messageID:");
        sb.append(this.j);
        sb.append(",taskID:");
        sb.append(this.l);
        sb.append(",appPackage:");
        sb.append(this.k);
        sb.append(",registerID:");
        sb.append(this.g);
        sb.append(",sdkVersion:");
        sb.append(this.h);
        sb.append(",command:");
        sb.append(this.d);
        sb.append(",responseCode:");
        sb.append(this.f);
        sb.append(",content:");
        sb.append(this.e);
        return sb.toString();
    }

    public static List<etg> a(String str, String str2, String str3, String str4) {
        List<etg> list;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray(str2);
            list = new ArrayList<>();
            int i = 0;
            while (i < jSONArray.length()) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    etg etg = new etg();
                    etg.b = jSONObject.getString(str4);
                    etg.a = jSONObject.getString(str3);
                    list.add(etg);
                    i++;
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                    esx.a("parseToSubscribeResultList--".concat(String.valueOf(list)));
                    return list;
                }
            }
        } catch (JSONException e3) {
            e = e3;
            list = null;
            e.printStackTrace();
            esx.a("parseToSubscribeResultList--".concat(String.valueOf(list)));
            return list;
        }
        esx.a("parseToSubscribeResultList--".concat(String.valueOf(list)));
        return list;
    }
}
