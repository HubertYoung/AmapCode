package defpackage;

import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.miniapp.plugin.constant.ConstantCompat.SaveSearchResultMapPage;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import com.autonavi.minimap.basemap.save.page.SavePointEditMenuPage;

/* renamed from: cri reason: default package */
/* compiled from: SavePointEditMenuPresenter */
public final class cri extends AbstractBasePresenter<SavePointEditMenuPage> {
    public cri(SavePointEditMenuPage savePointEditMenuPage) {
        super(savePointEditMenuPage);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        SavePointEditMenuPage savePointEditMenuPage = (SavePointEditMenuPage) this.mPage;
        if (resultType == ResultType.OK) {
            switch (i) {
                case FavoritesPointFragment.REQUEST_HOME /*241*/:
                    if (pageBundle != null && pageBundle.containsKey("result_poi")) {
                        PageBundle pageBundle2 = new PageBundle();
                        pageBundle2.putObject("result_poi", pageBundle.get("result_poi"));
                        pageBundle2.putInt("request_type_key", FavoritesPointFragment.REQUEST_HOME);
                        if (pageBundle.containsKey(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY)) {
                            pageBundle2.putBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY, pageBundle.getBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY));
                        }
                        savePointEditMenuPage.setResult(ResultType.OK, pageBundle2);
                        break;
                    }
                case FavoritesPointFragment.REQUEST_COMPNAY /*242*/:
                    if (pageBundle != null && pageBundle.containsKey("result_poi")) {
                        PageBundle pageBundle3 = new PageBundle();
                        pageBundle3.putObject("result_poi", pageBundle.get("result_poi"));
                        pageBundle3.putInt("request_type_key", FavoritesPointFragment.REQUEST_COMPNAY);
                        if (pageBundle.containsKey(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY)) {
                            pageBundle3.putBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY, pageBundle.getBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY));
                        }
                        savePointEditMenuPage.setResult(ResultType.OK, pageBundle3);
                        break;
                    }
                case FavoritesPointFragment.REQUEST_EDIT_POINT /*243*/:
                    if (pageBundle != null && pageBundle.containsKey("savepointkey")) {
                        PageBundle pageBundle4 = new PageBundle();
                        pageBundle4.putObject("savepointkey", pageBundle.get("savepointkey"));
                        savePointEditMenuPage.setResult(ResultType.OK, pageBundle4);
                        break;
                    }
                case 244:
                    if (pageBundle != null && pageBundle.containsKey("savepointkey")) {
                        PageBundle pageBundle5 = new PageBundle();
                        pageBundle5.putObject("savepointkey", pageBundle.get("savepointkey"));
                        savePointEditMenuPage.setResult(ResultType.OK, pageBundle5);
                        break;
                    }
            }
            savePointEditMenuPage.finish();
        }
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((SavePointEditMenuPage) this.mPage).a(pageBundle);
    }
}
