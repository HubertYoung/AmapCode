package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.search.imagepreview.page.ImageDetailPage;

/* renamed from: byf reason: default package */
/* compiled from: ImageDetailPresenter */
public final class byf extends AbstractBasePresenter<ImageDetailPage> {
    public byf(ImageDetailPage imageDetailPage) {
        super(imageDetailPage);
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((ImageDetailPage) this.mPage).a();
    }
}
