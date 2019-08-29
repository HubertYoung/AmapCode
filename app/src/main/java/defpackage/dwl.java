package defpackage;

import android.content.Context;
import android.content.SharedPreferences;

/* renamed from: dwl reason: default package */
/* compiled from: RouteFavoriteBuslinePreferencesUtil */
public final class dwl {
    public static void a(Context context, String str, String str2) {
        if (context != null && str != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("route_favorite_busline_data", 0);
            if (str2 == null) {
                sharedPreferences.edit().remove(str);
            } else {
                sharedPreferences.edit().putString(str, str2).apply();
            }
        }
    }
}
