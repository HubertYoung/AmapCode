package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.mine.page.setting.sysabout.page.SysAboutPage;

/* renamed from: cgs reason: default package */
/* compiled from: SysAboutPresenter */
public final class cgs extends AbstractBasePresenter<SysAboutPage> {
    public cgs(SysAboutPage sysAboutPage) {
        super(sysAboutPage);
    }

    public final void onStop() {
        if (((SysAboutPage) this.mPage).getClickVersionTimes() != 5) {
            ((SysAboutPage) this.mPage).setClickVersionTimes(0);
        }
        super.onStop();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((SysAboutPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }
}
