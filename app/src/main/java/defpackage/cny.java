package defpackage;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;

/* renamed from: cny reason: default package */
/* compiled from: DebugSPUtil */
public final class cny {
    public static void a(Context context, String str) {
        context.getSharedPreferences("ajx_debugger", 0).edit().putString("ajx_debugger_url", str).apply();
    }

    public static String a(Context context) {
        return context.getSharedPreferences("ajx_debugger", 0).getString("ajx_debugger_url", "");
    }

    public static JSONArray b(Context context) {
        try {
            return new JSONArray(context.getSharedPreferences("ajx_debugger", 0).getString("scan_history", ""));
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
}
