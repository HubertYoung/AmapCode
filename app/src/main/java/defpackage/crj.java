package defpackage;

import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.MapBasePresenter;
import com.autonavi.minimap.basemap.save.page.SavePointToMapPage;

/* renamed from: crj reason: default package */
/* compiled from: SavePointToMapPresenter */
public final class crj extends MapBasePresenter<SavePointToMapPage> {
    public final boolean onShowPoiTipView(PageBundle pageBundle, int i) {
        return false;
    }

    public crj(SavePointToMapPage savePointToMapPage) {
        super(savePointToMapPage);
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

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
    }

    public final boolean onMapLevelChange(boolean z) {
        super.onMapLevelChange(z);
        return true;
    }
}
