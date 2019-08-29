package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.MapBasePresenter;
import com.autonavi.minimap.basemap.save.page.SaveSearchResultMapPage;

/* renamed from: crp reason: default package */
/* compiled from: SaveSearchResultMapPresenter */
public final class crp extends MapBasePresenter<SaveSearchResultMapPage> {
    public crp(SaveSearchResultMapPage saveSearchResultMapPage) {
        super(saveSearchResultMapPage);
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((SaveSearchResultMapPage) this.mPage).a();
    }

    public final boolean onFocusClear() {
        ((SaveSearchResultMapPage) this.mPage).dismissViewFooter();
        return super.onFocusClear();
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
    }
}
