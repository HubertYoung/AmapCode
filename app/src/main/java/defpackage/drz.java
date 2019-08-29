package defpackage;

import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.myProfile.page.VerifyUserPage;

/* renamed from: drz reason: default package */
/* compiled from: VerifyUserPresenter */
public final class drz extends AbstractBasePresenter<VerifyUserPage> {
    public drz(VerifyUserPage verifyUserPage) {
        super(verifyUserPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((VerifyUserPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }
}
