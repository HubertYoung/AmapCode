package defpackage;

import android.view.KeyEvent;
import com.autonavi.map.common.page.LicenseConfirmPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;

/* renamed from: bri reason: default package */
/* compiled from: LicenseConfirmPresenter */
public final class bri extends AbstractBasePresenter<LicenseConfirmPage> {
    public bri(LicenseConfirmPage licenseConfirmPage) {
        super(licenseConfirmPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        LicenseConfirmPage licenseConfirmPage = (LicenseConfirmPage) this.mPage;
        if (licenseConfirmPage.a == null || !licenseConfirmPage.a.canGoBack()) {
            return false;
        }
        licenseConfirmPage.a.goBack();
        return true;
    }
}
