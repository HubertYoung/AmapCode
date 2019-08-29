package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.life.order.viewpoint.fragment.ViewPointListFragmentNew;

/* renamed from: dqp reason: default package */
/* compiled from: ViewPointListNewPresenter */
public final class dqp extends AbstractBasePresenter<ViewPointListFragmentNew> {
    public dqp(ViewPointListFragmentNew viewPointListFragmentNew) {
        super(viewPointListFragmentNew);
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((ViewPointListFragmentNew) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }
}
