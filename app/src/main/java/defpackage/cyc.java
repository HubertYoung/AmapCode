package defpackage;

import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.jni.bedstone.BaseMapFrequentLocationsJni;
import com.autonavi.jni.bedstone.model.FrequentLocationDBInfo;
import com.autonavi.minimap.bedstone.model.FrequentLocationInfo;
import com.autonavi.minimap.bundle.frequentlocation.view.FrequentLocationAdapter2;
import java.util.ArrayList;
import java.util.List;

@BundleInterface(bia.class)
/* renamed from: cyc reason: default package */
/* compiled from: FrequentLocationService */
public class cyc extends esi implements bia {
    private static bia a;
    private final String b = "FRE_LOC_SEQ_SP_KEY";
    private long c = 0;
    private volatile int e = -1;
    private MapSharePreference f = null;

    public static bia e() {
        if (a == null) {
            a = (bia) a.a.a(bia.class);
        }
        return a;
    }

    public cyc() {
        if (this.c == 0) {
            csz.a();
            csz.a(a.a);
            if (!FrequentLocationAdapter2.mIsTesting) {
                BaseMapFrequentLocationsJni b2 = csz.a().b();
                int intValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getIntValue("sp_key_open_frequent_location_aocs_interval", -1);
                this.c = b2.initFrequentLocations(2000, 7776000, intValue >= 0 ? intValue * 24 * 60 * 60 : 432000, cye.b());
            } else {
                this.c = csz.a().b().initFrequentLocations(2000, 7776000, 0, cye.b());
            }
        }
        this.f = new MapSharePreference(SharePreferenceName.SharedPreferences);
        this.e = this.f.getIntValue("FRE_LOC_SEQ_SP_KEY", -1);
        a = this;
    }

    private void f() {
        if (this.e < 0) {
            this.e = 0;
        }
        int i = this.e + 1;
        this.e = i;
        this.f.putIntValue("FRE_LOC_SEQ_SP_KEY", i);
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        super.finalize();
        if (this.c != 0) {
            csz.a().b().uninit(this.c);
            this.c = 0;
        }
    }

    public final int a(FrequentLocationDBInfo frequentLocationDBInfo) {
        if (this.c != 0) {
            new cyj();
            frequentLocationDBInfo.infoJsonString = cyj.a(frequentLocationDBInfo.FrequentLocation);
            csz.a().b().addData(this.c, frequentLocationDBInfo);
            f();
        }
        return 1;
    }

    public final int a(String str) {
        if (this.c == 0) {
            return 0;
        }
        f();
        return csz.a().b().delItem(this.c, str);
    }

    public final int a(String[] strArr) {
        if (this.c == 0) {
            return 0;
        }
        f();
        return csz.a().b().delItems(this.c, strArr);
    }

    public final List<FrequentLocationDBInfo> b(String[] strArr) {
        if (strArr == null) {
            strArr = new String[0];
        }
        ArrayList arrayList = new ArrayList();
        if (this.c != 0) {
            FrequentLocationDBInfo[] topPlace = csz.a().b().getTopPlace(this.c, strArr);
            new cyj();
            for (FrequentLocationDBInfo frequentLocationDBInfo : topPlace) {
                if (frequentLocationDBInfo != null && !TextUtils.isEmpty(frequentLocationDBInfo.infoJsonString) && (frequentLocationDBInfo.FrequentLocation == null || TextUtils.isEmpty(frequentLocationDBInfo.FrequentLocation.name))) {
                    FrequentLocationInfo a2 = cyj.a(frequentLocationDBInfo.infoJsonString);
                    if (a2 != null) {
                        frequentLocationDBInfo.FrequentLocation = a2;
                    }
                }
                arrayList.add(frequentLocationDBInfo);
            }
        }
        return arrayList;
    }

    public final int a() {
        if (this.c == 0) {
            return 0;
        }
        f();
        return csz.a().b().clearAll(this.c);
    }

    public final int b() {
        return this.e;
    }

    public final boolean d() {
        return cye.a();
    }

    public final boolean c() {
        return cye.a;
    }

    public final void a(boolean z) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("sp_key_open_frequent_location_local", z);
    }
}
