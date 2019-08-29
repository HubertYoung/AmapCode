package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;

/* renamed from: cif reason: default package */
/* compiled from: AGroupGuideHelper */
public final class cif implements cui {
    private bid a;
    private Context b;
    private MapSharePreference c = new MapSharePreference(SharePreferenceName.SharedPreferences);

    public cif(@NonNull bid bid) {
        this.a = bid;
        this.b = bid.getContext();
    }
}
