package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import java.util.Date;

/* renamed from: ayu reason: default package */
/* compiled from: RouteCommuteSharedPreferenceHelper */
public final class ayu {
    private MapSharePreference a = new MapSharePreference(SharePreferenceName.SharedPreferences);

    public final int a() {
        return this.a.getIntValue("ROUTE_COMMUTE_GUIDE_TIP_CANCEL_COUNT", 0);
    }

    public final void b() {
        int intValue = this.a.getIntValue("ROUTE_COMMUTE_GUIDE_TIP_CANCEL_COUNT", 0) + 1;
        this.a.edit().putInt("ROUTE_COMMUTE_GUIDE_TIP_CANCEL_COUNT", intValue).apply();
        StringBuilder sb = new StringBuilder("increase cancel count to ");
        sb.append(intValue);
        sb.append(" times and save to SP.");
        azb.a("RouteCommuteSharedPreferenceHelper", sb.toString());
    }

    public final void c() {
        long currentTimeMillis = System.currentTimeMillis();
        this.a.edit().putLong("ROUTE_COMMUTE_GUIDE_TIP_LAST_CANCEL_TIMESTAMP", currentTimeMillis).apply();
        StringBuilder sb = new StringBuilder("save last cancel time at ");
        sb.append(new Date(currentTimeMillis).toString());
        azb.a("RouteCommuteSharedPreferenceHelper", sb.toString());
    }

    public final long d() {
        return this.a.getLongValue("ROUTE_COMMUTE_GUIDE_TIP_LAST_CANCEL_TIMESTAMP", 0);
    }
}
