package defpackage;

import com.autonavi.jni.bedstone.BaseMapFrequentLocationsJni;

/* renamed from: csz reason: default package */
/* compiled from: BaseMapBedStoneSDK */
public final class csz {
    private static final csz a = new csz();
    private BaseMapFrequentLocationsJni b;

    private csz() {
    }

    public static csz a() {
        return a;
    }

    public static void a(String str) {
        BaseMapFrequentLocationsJni.initDb(str);
    }

    public final BaseMapFrequentLocationsJni b() {
        if (this.b == null) {
            this.b = new BaseMapFrequentLocationsJni();
        }
        return this.b;
    }
}
