package defpackage;

import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.search.imagepreview.page.AbsImageGridNodeBasePage;

/* renamed from: byg reason: default package */
/* compiled from: ImageGridPresenter */
public final class byg extends AbstractBasePresenter<AbsImageGridNodeBasePage> {
    public byg(AbsImageGridNodeBasePage absImageGridNodeBasePage) {
        super(absImageGridNodeBasePage);
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if ((i == 2 || i == 20484 || i == 20485) && pageBundle.getInt("PHOTO_UPLOAD_STATUS") > 0) {
            caf caf = (caf) a.a.a(caf.class);
            if (caf != null) {
                caf.a((bid) this.mPage, can.a().f);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        ((AbsImageGridNodeBasePage) this.mPage).start();
    }

    public final void onStop() {
        super.onStop();
        ((AbsImageGridNodeBasePage) this.mPage).stop();
    }

    public final void onDestroy() {
        super.onDestroy();
        ((AbsImageGridNodeBasePage) this.mPage).destroy();
    }
}
