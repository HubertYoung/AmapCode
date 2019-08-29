package defpackage;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.life.common.page.BaseListNodePage;

/* renamed from: doi reason: default package */
/* compiled from: BaseListNodePresenter */
public class doi<Page extends BaseListNodePage> extends AbstractBasePresenter<Page> {
    public doi(Page page) {
        super(page);
    }

    public void onPageCreated() {
        super.onPageCreated();
        ((BaseListNodePage) this.mPage).a();
    }

    public void onDestroy() {
        ((BaseListNodePage) this.mPage).i();
        super.onDestroy();
    }
}
