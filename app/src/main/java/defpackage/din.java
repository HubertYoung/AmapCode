package defpackage;

import android.view.KeyEvent;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.drive.search.fragment.SearchCitySuggestionPage;

/* renamed from: din reason: default package */
/* compiled from: SearchCitySuggestionPresenter */
public final class din extends AbstractBasePresenter<SearchCitySuggestionPage> {
    public din(SearchCitySuggestionPage searchCitySuggestionPage) {
        super(searchCitySuggestionPage);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            ((SearchCitySuggestionPage) this.mPage).setResult(ResultType.CANCEL, (PageBundle) null);
            ((SearchCitySuggestionPage) this.mPage).finish();
        }
        return super.onKeyDown(i, keyEvent);
    }
}
