package defpackage;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.life.order.base.page.BaseByPhonePage;

/* renamed from: dpt reason: default package */
/* compiled from: BaseByPhonePresenter */
public final class dpt extends AbstractBasePresenter<BaseByPhonePage> {
    public dpt(BaseByPhonePage baseByPhonePage) {
        super(baseByPhonePage);
    }

    public final void onDestroy() {
        ((BaseByPhonePage) this.mPage).g();
        super.onDestroy();
    }
}
