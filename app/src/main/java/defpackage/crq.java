package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.ajx3.util.KeyBoardUtil;
import com.autonavi.minimap.basemap.save.page.SetTagPage;

/* renamed from: crq reason: default package */
/* compiled from: SetTagPresenter */
public final class crq extends AbstractBasePresenter<SetTagPage> {
    public crq(SetTagPage setTagPage) {
        super(setTagPage);
    }

    public final void onDestroy() {
        KeyBoardUtil.hideSoftKeyboard(((SetTagPage) this.mPage).a);
        super.onDestroy();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((SetTagPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        ((SetTagPage) this.mPage).a();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }
}
