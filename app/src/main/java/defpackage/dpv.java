package defpackage;

import android.view.KeyEvent;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.life.order.base.page.BaseOrderWebViewPage;

/* renamed from: dpv reason: default package */
/* compiled from: BaseOrderWebViewPresenter */
public final class dpv extends AbstractBasePresenter<BaseOrderWebViewPage> {
    public dpv(BaseOrderWebViewPage baseOrderWebViewPage) {
        super(baseOrderWebViewPage);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        ((BaseOrderWebViewPage) this.mPage).a();
        return true;
    }
}
