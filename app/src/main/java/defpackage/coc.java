package defpackage;

import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.page.FavoritesPage;
import com.autonavi.minimap.basemap.save.page.SaveSearchPage;

@BundleInterface(auz.class)
/* renamed from: coc reason: default package */
/* compiled from: FavoritesService */
public class coc extends esi implements auz {
    public final void a(PageBundle pageBundle) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(FavoritesPage.class, pageBundle);
        }
    }

    public final void a() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPageForResult(FavoritesPage.class, (PageBundle) null, -1);
        }
    }

    public final void a(PageBundle pageBundle, int i) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPageForResult(SaveSearchPage.class, pageBundle, i);
        }
    }

    public final void a(String str) {
        cra.a();
        cra.b(str);
    }

    public final void a(POI poi, String str) {
        cru.a(poi, str, str);
    }

    public final boolean b() {
        return cru.a();
    }

    public final void a(POI poi) {
        try {
            com com2 = (com) ank.a(com.class);
            if (com2 != null) {
                cop b = com2.b(com2.a());
                if (b != null) {
                    FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
                    favoritePOI.setCommonName(AMapAppGlobal.getApplication().getString(R.string.home));
                    b.f(favoritePOI);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void b(POI poi) {
        try {
            com com2 = (com) ank.a(com.class);
            if (com2 != null) {
                cop b = com2.b(com2.a());
                if (b != null) {
                    FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
                    favoritePOI.setCommonName(AMapAppGlobal.getApplication().getString(R.string.company));
                    b.e(favoritePOI);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
