package defpackage;

import com.amap.bundle.searchservice.callback.AbsSearchCallBack;
import com.amap.bundle.searchservice.service.offline.OfflinePoiEngineFactoryImpl;
import com.autonavi.ae.search.model.GADAREAEXTRAINFO;
import com.autonavi.ae.search.model.GPoiBase;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.entity.common.OfflineSearchMode;
import com.autonavi.common.model.POI;

@BundleInterface(adz.class)
/* renamed from: aeg reason: default package */
/* compiled from: OfflineSearchExporter */
public class aeg implements adz {
    public static void a(OfflineSearchMode offlineSearchMode, AbsSearchCallBack absSearchCallBack) {
        aed.a().a(new a(offlineSearchMode, absSearchCallBack));
    }

    public final POI a(GPoiBase gPoiBase) {
        return aee.a(gPoiBase);
    }

    public final GADAREAEXTRAINFO a(int i) {
        aee.a();
        return aee.b(i);
    }

    public final void a() {
        OfflinePoiEngineFactoryImpl c = OfflinePoiEngineFactoryImpl.c();
        if (c != null) {
            afa.a();
            c.a();
        }
    }

    public final ady b() {
        aee a = aee.a();
        if (a == null) {
            return null;
        }
        return new aeh(a);
    }

    public final ady a(String str, String str2, String str3) {
        a.a.a(adz.class);
        aee a = aee.a(aez.a(1, str, str2, str3));
        if (a == null) {
            return null;
        }
        return new aeh(a);
    }
}
