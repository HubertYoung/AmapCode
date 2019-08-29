package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;

/* renamed from: bcy reason: default package */
/* compiled from: SearchResultUtils */
public final class bcy {
    public static boolean a(InfoliteResult infoliteResult) {
        return (infoliteResult == null || infoliteResult.searchInfo == null || infoliteResult.searchInfo.o == null) ? false : true;
    }

    public static boolean b(InfoliteResult infoliteResult) {
        return (infoliteResult == null || infoliteResult.searchInfo == null || infoliteResult.searchInfo.l == null) ? false : true;
    }

    public static boolean c(InfoliteResult infoliteResult) {
        return (infoliteResult == null || infoliteResult.searchInfo == null) ? false : true;
    }

    public static boolean d(InfoliteResult infoliteResult) {
        return c(infoliteResult) && infoliteResult.searchInfo.a != null;
    }

    public static boolean e(InfoliteResult infoliteResult) {
        return (infoliteResult == null || infoliteResult.responseHeader == null) ? false : true;
    }

    public static boolean f(InfoliteResult infoliteResult) {
        return (infoliteResult == null || infoliteResult.mWrapper == null) ? false : true;
    }

    private static boolean s(InfoliteResult infoliteResult) {
        if (!f(infoliteResult) || infoliteResult.mWrapper.pagenum > 1) {
            return false;
        }
        if (!a(infoliteResult) || infoliteResult.searchInfo.o.c) {
            return true;
        }
        return false;
    }

    public static boolean a(String str) {
        return TextUtils.isEmpty(str) || "poi".equalsIgnoreCase(str) || BioDetector.EXT_KEY_GEO.equalsIgnoreCase(str) || "citycard".equalsIgnoreCase(str);
    }

    public static boolean a(String str, int i) {
        return "toplist".equals(str) && i == 1;
    }

    public static boolean b(String str, int i) {
        return "toplist".equals(str) && i == 2;
    }

    public static int g(InfoliteResult infoliteResult) {
        if (infoliteResult == null || infoliteResult.searchInfo == null || infoliteResult.mWrapper == null || infoliteResult.mWrapper.pagenum != 1) {
            return -1;
        }
        return infoliteResult.searchInfo.t;
    }

    public static boolean a(POI poi) {
        if (poi != null && poi.getPoiExtra().containsKey("SrcType")) {
            String str = (String) poi.getPoiExtra().get("SrcType");
            if (!TextUtils.isEmpty(str) && "nativepoi".equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static List<POI> h(InfoliteResult infoliteResult) {
        List<POI> p = p(infoliteResult);
        POI c = c(p);
        if (c != null) {
            a(p);
        }
        ArrayList arrayList = null;
        if (p.size() != 0) {
            int size = p.size();
            if (((size + 10) - 1) / 10 > 0 && size > 0) {
                int i = size - 1;
                if (9 <= i) {
                    i = 9;
                }
                int i2 = (i - 0) + 1;
                arrayList = new ArrayList();
                for (int i3 = 0; i3 < i2; i3++) {
                    arrayList.add(p.get(i3 + 0));
                }
            }
        }
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        if (s(infoliteResult) && c != null) {
            arrayList.add(0, c);
        }
        return arrayList;
    }

    public static List<POI> i(InfoliteResult infoliteResult) {
        List<POI> p = p(infoliteResult);
        POI c = c(p);
        if (c != null) {
            a(p);
        }
        if (s(infoliteResult) && c != null) {
            p.add(0, c);
        }
        return p;
    }

    public static void a(List<POI> list) {
        if (list != null && list.size() != 0) {
            for (int size = list.size() - 1; size >= 0; size--) {
                if (b(list.get(size))) {
                    list.remove(size);
                    return;
                }
            }
        }
    }

    public static boolean j(InfoliteResult infoliteResult) {
        if (!c(infoliteResult)) {
            return false;
        }
        ArrayList<aue> arrayList = infoliteResult.searchInfo.r;
        if (arrayList == null || arrayList.size() <= 0) {
            return false;
        }
        return true;
    }

    public static String k(InfoliteResult infoliteResult) {
        return d(infoliteResult) ? infoliteResult.searchInfo.a.O : "";
    }

    public static String l(InfoliteResult infoliteResult) {
        return d(infoliteResult) ? infoliteResult.searchInfo.a.R : "";
    }

    public static int m(InfoliteResult infoliteResult) {
        if (infoliteResult == null) {
            return 0;
        }
        return ((infoliteResult.searchInfo.p + 10) - 1) / 10;
    }

    public static boolean n(InfoliteResult infoliteResult) {
        if (!b(infoliteResult) || !e(infoliteResult) || infoliteResult.responseHeader.f || infoliteResult.searchInfo.l.isEmpty()) {
            return false;
        }
        String city = LocationInstrument.getInstance().getLatestPosition().getCity();
        if (TextUtils.isEmpty(city)) {
            return false;
        }
        if (!city.equals(infoliteResult.searchInfo.l.get(0).getPoint().getCity()) && infoliteResult.mWrapper.search_operate == 1) {
            return false;
        }
        return true;
    }

    public static List<POI> b(List<POI> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        POI c = c(list);
        if (c == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (POI as : list) {
            SearchPoi searchPoi = (SearchPoi) as.as(SearchPoi.class);
            if ((searchPoi.getRecommendFlag() & 100) == 4) {
                arrayList.add(searchPoi);
            }
        }
        SearchPoi searchPoi2 = (SearchPoi) c.as(SearchPoi.class);
        if (searchPoi2.getPoiChildrenInfo() == null) {
            searchPoi2.setPoiChildrenInfo(new ChildrenPoiData());
        }
        searchPoi2.getPoiChildrenInfo().geoList = arrayList;
        return arrayList;
    }

    public static List<aup> o(InfoliteResult infoliteResult) {
        if (c(infoliteResult) && infoliteResult.searchInfo.h != null) {
            return new ArrayList(infoliteResult.searchInfo.h);
        }
        return null;
    }

    public static List<POI> p(InfoliteResult infoliteResult) {
        if (!b(infoliteResult)) {
            return new ArrayList();
        }
        return new ArrayList(infoliteResult.searchInfo.l);
    }

    public static POI q(InfoliteResult infoliteResult) {
        return c(p(infoliteResult));
    }

    public static POI c(List<POI> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        for (POI next : list) {
            if (b(next)) {
                return next;
            }
        }
        return null;
    }

    public static boolean b(POI poi) {
        return TextUtils.isEmpty(poi.getId());
    }

    public static void r(InfoliteResult infoliteResult) {
        if (c(infoliteResult)) {
            if (infoliteResult.searchInfo.o == null) {
                infoliteResult.searchInfo.o = new auu();
            }
            infoliteResult.searchInfo.o.e = null;
            List<POI> p = p(infoliteResult);
            infoliteResult.searchInfo.o.e = new ArrayList(p);
        }
    }

    public static boolean a(InfoliteResult infoliteResult, List<POI> list) {
        if (!(d(infoliteResult) && infoliteResult.searchInfo.a.n == 1)) {
            return false;
        }
        if (list == null || list.size() == 0) {
            return true;
        }
        for (POI as : list) {
            if ((((SearchPoi) as.as(SearchPoi.class)).getRecommendFlag() & 1) == 1) {
                return true;
            }
        }
        return false;
    }
}
