package defpackage;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: evn reason: default package */
/* compiled from: SaveInfoExec */
public class evn {
    private static final String a = "evn";

    public static void a(Context context, JSONObject jSONObject, String str) {
        euw.a((String) "saveExec 3");
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            try {
                evl a2 = evl.a(context);
                JSONArray jSONArray = jSONObject.getJSONArray(next);
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    a2.a(next, jSONArray.get(i).toString(), str);
                }
            } catch (JSONException e) {
                StringBuilder sb = new StringBuilder("Collected:");
                sb.append(e.getMessage());
                euw.a(sb.toString());
            } catch (SQLiteException e2) {
                StringBuilder sb2 = new StringBuilder("Collected:");
                sb2.append(e2.getMessage());
                euw.a(sb2.toString());
            }
        }
    }
}
