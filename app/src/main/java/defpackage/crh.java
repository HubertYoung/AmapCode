package defpackage;

import com.amap.bundle.utils.device.KeyboardUtil;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.basemap.save.page.SavePointEditExtraPage;

/* renamed from: crh reason: default package */
/* compiled from: SavePointEditExtraPresenter */
public final class crh extends AbstractBasePresenter<SavePointEditExtraPage> {
    public crh(SavePointEditExtraPage savePointEditExtraPage) {
        super(savePointEditExtraPage);
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((SavePointEditExtraPage) this.mPage).a(pageBundle);
    }

    public final void onDestroy() {
        super.onDestroy();
        KeyboardUtil.hideInputMethod(((SavePointEditExtraPage) this.mPage).getActivity());
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((SavePointEditExtraPage) this.mPage).finish();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }
}
