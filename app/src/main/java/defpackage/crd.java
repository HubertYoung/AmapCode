package defpackage;

import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.basemap.save.page.FavoriteTagFilterResultPage;

/* renamed from: crd reason: default package */
/* compiled from: FavoriteTagFilterResultPresenter */
public final class crd extends AbstractBasePresenter<FavoriteTagFilterResultPage> {
    public crd(FavoriteTagFilterResultPage favoriteTagFilterResultPage) {
        super(favoriteTagFilterResultPage);
    }

    public final void onStart() {
        super.onStart();
        ((FavoriteTagFilterResultPage) this.mPage).a(false);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        FavoriteTagFilterResultPage favoriteTagFilterResultPage = (FavoriteTagFilterResultPage) this.mPage;
        if (i == 243 && resultType == ResultType.OK) {
            if (pageBundle != null && pageBundle.containsKey("savepointkey")) {
                favoriteTagFilterResultPage.a((bth) pageBundle.get("savepointkey"));
            }
        } else if (i == 240 && resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey("result_poi")) {
            POI poi = (POI) pageBundle.get("result_poi");
            if (poi != null) {
                ((FavoritePOI) poi.as(FavoritePOI.class)).setTag(favoriteTagFilterResultPage.b);
                cpf.b(cpm.b().a()).b((POI) (FavoritePOI) poi.as(FavoritePOI.class));
                favoriteTagFilterResultPage.a(true);
            }
        }
        favoriteTagFilterResultPage.a();
    }
}
