package defpackage;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.tbt.basemap.AbsFavoriteDataProvider;
import com.autonavi.jni.eyrie.amap.tbt.basemap.FavoritePOIInfo;
import com.autonavi.sync.beans.GirfFavoritePoint;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: cou reason: default package */
/* compiled from: FavoriteDataProvider */
public final class cou extends AbsFavoriteDataProvider {
    List<FavoritePOIInfo> a;
    private List<FavoritePOIInfo> b;
    private List<FavoritePOIInfo> c;

    /* access modifiers changed from: 0000 */
    public final void a(List<GirfFavoritePoint> list) {
        StringBuilder sb = new StringBuilder("setHomeData: ");
        sb.append((list == null || list.size() == 0) ? null : list.get(0).name);
        AMapLog.debug("basemap.favorites", "FavoriteDataProvider", sb.toString());
        this.b = a(list, 1);
    }

    /* access modifiers changed from: 0000 */
    public final void b(List<GirfFavoritePoint> list) {
        StringBuilder sb = new StringBuilder("setCompanyData: ");
        sb.append((list == null || list.size() == 0) ? null : list.get(0).name);
        AMapLog.debug("basemap.favorites", "FavoriteDataProvider", sb.toString());
        this.c = a(list, 2);
    }

    /* access modifiers changed from: 0000 */
    public final void c(List<GirfFavoritePoint> list) {
        StringBuilder sb = new StringBuilder("setFavoriteData: ");
        sb.append((list == null || list.size() == 0) ? 0 : list.size());
        AMapLog.debug("basemap.favorites", "FavoriteDataProvider", sb.toString());
        this.a = a(list, 0);
    }

    private static CopyOnWriteArrayList<FavoritePOIInfo> a(List<GirfFavoritePoint> list, int i) {
        if (list == null || list.size() == 0) {
            return null;
        }
        CopyOnWriteArrayList<FavoritePOIInfo> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        for (GirfFavoritePoint a2 : list) {
            FavoritePOIInfo a3 = a(a2, i);
            if (a3 != null) {
                copyOnWriteArrayList.add(a3);
            }
        }
        return copyOnWriteArrayList;
    }

    private static FavoritePOIInfo a(@Nullable GirfFavoritePoint girfFavoritePoint, int i) {
        String str;
        if (girfFavoritePoint == null) {
            return null;
        }
        try {
            int parseInt = Integer.parseInt(girfFavoritePoint.px);
            int parseInt2 = Integer.parseInt(girfFavoritePoint.py);
            GeoPoint geoPoint = new GeoPoint(parseInt, parseInt2);
            String str2 = girfFavoritePoint.item_id;
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            String b2 = bim.aa().b((String) "101", str2);
            if (TextUtils.isEmpty(b2)) {
                return null;
            }
            IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
            if (iAccountService == null) {
                str = "";
            } else {
                ant e = iAccountService.e();
                if (e == null) {
                    str = "";
                } else {
                    str = e.a;
                }
            }
            if (bsr.a(b2, str2, str) == null) {
                return null;
            }
            FavoritePOIInfo favoritePOIInfo = new FavoritePOIInfo();
            StringBuilder sb = new StringBuilder();
            sb.append(girfFavoritePoint.id);
            favoritePOIInfo.id = sb.toString();
            favoritePOIInfo.name = girfFavoritePoint.name;
            favoritePOIInfo.address = girfFavoritePoint.address;
            favoritePOIInfo.custom_name = girfFavoritePoint.customName;
            favoritePOIInfo.common_name = girfFavoritePoint.commonName;
            favoritePOIInfo.city_code = girfFavoritePoint.cityCode;
            favoritePOIInfo.city_name = girfFavoritePoint.cityName;
            favoritePOIInfo.latitude = geoPoint.getLatitude();
            favoritePOIInfo.longitude = geoPoint.getLongitude();
            favoritePOIInfo.tag = girfFavoritePoint.tag;
            favoritePOIInfo.poi_type = String.valueOf(i);
            favoritePOIInfo.classification = girfFavoritePoint.classification;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(girfFavoritePoint.topTime);
            favoritePOIInfo.top_time = sb2.toString();
            favoritePOIInfo.item_id = girfFavoritePoint.item_id;
            favoritePOIInfo.type = girfFavoritePoint.type;
            favoritePOIInfo.uid = str;
            favoritePOIInfo.json = b2;
            favoritePOIInfo.p20X = parseInt;
            favoritePOIInfo.p20Y = parseInt2;
            return favoritePOIInfo;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final List<FavoritePOIInfo> acquireHomeData() {
        return this.b;
    }

    public final List<FavoritePOIInfo> acquireCompanyData() {
        return this.c;
    }

    public final List<FavoritePOIInfo> acquireFavoriteData() {
        if (bin.a == null) {
            return this.a;
        }
        if (bin.a.o("104")) {
            return this.a;
        }
        return null;
    }
}
