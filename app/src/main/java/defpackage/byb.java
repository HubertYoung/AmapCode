package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.utils.SearchUtils;
import java.util.List;

/* renamed from: byb reason: default package */
/* compiled from: SearchResultSceneHelper */
public final class byb {

    /* renamed from: byb$a */
    /* compiled from: SearchResultSceneHelper */
    public static class a {
        public static boolean a(InfoliteResult infoliteResult) {
            return bcy.d(infoliteResult) && !TextUtils.isEmpty(infoliteResult.searchInfo.a.v) && infoliteResult.mWrapper.pagenum == 1;
        }

        public static boolean b(InfoliteResult infoliteResult) {
            return bcy.c(infoliteResult) && infoliteResult.searchInfo.f != null && infoliteResult.searchInfo.f.size() > 0 && infoliteResult.mWrapper.pagenum == 1;
        }

        public static boolean c(InfoliteResult infoliteResult) {
            return bcy.d(infoliteResult) && !TextUtils.isEmpty(infoliteResult.searchInfo.a.d) && infoliteResult.mWrapper.pagenum == 1;
        }

        public static boolean d(InfoliteResult infoliteResult) {
            if (!bcy.d(infoliteResult) || infoliteResult.searchInfo.a.e != 1 || TextUtils.isEmpty(infoliteResult.searchInfo.a.f) || infoliteResult.mWrapper.pagenum != 1) {
                return false;
            }
            return true;
        }

        public static boolean e(InfoliteResult infoliteResult) {
            List<POI> h = bcy.h(infoliteResult);
            if (h.size() == 0) {
                return false;
            }
            POI poi = h.get(0);
            if (poi == null || !TextUtils.isEmpty(poi.getId()) || (((SearchPoi) poi.as(SearchPoi.class)).getReferenceRltFlag() & 1) != 1 || h(infoliteResult)) {
                return false;
            }
            return true;
        }

        public static boolean f(InfoliteResult infoliteResult) {
            return (h(infoliteResult) && TextUtils.isEmpty(infoliteResult.searchInfo.a.y)) || (bcy.c(infoliteResult) && bxz.a((List<POI>) infoliteResult.searchInfo.l));
        }

        public static boolean g(InfoliteResult infoliteResult) {
            return bcy.c(infoliteResult) && infoliteResult.searchInfo.d != null && infoliteResult.searchInfo.d.d != null && infoliteResult.searchInfo.d.d.length > 0;
        }

        private static boolean h(InfoliteResult infoliteResult) {
            if (!bcy.d(infoliteResult)) {
                return false;
            }
            boolean z = infoliteResult.searchInfo.a.e != 1 && TextUtils.isEmpty(infoliteResult.searchInfo.a.f) && TextUtils.isEmpty(infoliteResult.searchInfo.a.d);
            if (infoliteResult.searchInfo.a.J == null || !infoliteResult.searchInfo.a.J.equals("rqbxy") || !z) {
                return false;
            }
            return true;
        }
    }

    public static boolean a(InfoliteResult infoliteResult) {
        if (!bcy.d(infoliteResult)) {
            return false;
        }
        if (a(infoliteResult.mWrapper.superid)) {
            return true;
        }
        if (a.f(infoliteResult) || a.e(infoliteResult) || a.c(infoliteResult) || a.a(infoliteResult) || a.d(infoliteResult) || a.b(infoliteResult) || a.g(infoliteResult) || infoliteResult.mWrapper.search_operate != 1 || infoliteResult.searchInfo.w != 1) {
            return false;
        }
        return SearchUtils.checkCategory(infoliteResult.searchInfo.a.O, new String[]{"10"}, null);
    }

    private static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] split = str.split("_");
        if (split.length < 4) {
            return false;
        }
        return "06".equals(split[3]);
    }
}
