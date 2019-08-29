package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.basemap.save.page.SaveDuplicateConfirmPage;

/* renamed from: crf reason: default package */
/* compiled from: SaveDuplicateConfirmPresenter */
public final class crf extends AbstractBasePresenter<SaveDuplicateConfirmPage> {
    public crf(SaveDuplicateConfirmPage saveDuplicateConfirmPage) {
        super(saveDuplicateConfirmPage);
    }

    public final ON_BACK_TYPE onBackPressed() {
        return ON_BACK_TYPE.TYPE_IGNORE;
    }
}
