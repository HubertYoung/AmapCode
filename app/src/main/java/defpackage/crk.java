package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.basemap.save.page.SaveSearchCitySuggestionPage;

/* renamed from: crk reason: default package */
/* compiled from: SaveSearchCitySuggestionPresenter */
public final class crk extends AbstractBasePresenter<SaveSearchCitySuggestionPage> {
    public crk(SaveSearchCitySuggestionPage saveSearchCitySuggestionPage) {
        super(saveSearchCitySuggestionPage);
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((SaveSearchCitySuggestionPage) this.mPage).c();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }
}
