package defpackage;

import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.basemap.save.page.SaveSearchResultListPage;

/* renamed from: cro reason: default package */
/* compiled from: SaveSearchResultListPresenter */
public final class cro extends AbstractBasePresenter<SaveSearchResultListPage> {
    public cro(SaveSearchResultListPage saveSearchResultListPage) {
        super(saveSearchResultListPage);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        SaveSearchResultListPage saveSearchResultListPage = (SaveSearchResultListPage) this.mPage;
        if (ResultType.OK.equals(resultType)) {
            if (i == 2 && pageBundle.containsKey("result_poi")) {
                saveSearchResultListPage.setResult(ResultType.OK, pageBundle);
                saveSearchResultListPage.finish();
            }
        } else if (ResultType.CANCEL.equals(resultType)) {
            saveSearchResultListPage.setResult(ResultType.CANCEL, (PageBundle) null);
            saveSearchResultListPage.finish();
        }
    }

    public final void onStart() {
        super.onStart();
        SaveSearchResultListPage saveSearchResultListPage = (SaveSearchResultListPage) this.mPage;
        if (saveSearchResultListPage.getArguments() != null && saveSearchResultListPage.getArguments().containsKey("SUPER_ID")) {
            saveSearchResultListPage.a = (SuperId) saveSearchResultListPage.getArguments().get("SUPER_ID");
        }
    }
}
