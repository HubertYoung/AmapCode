package defpackage;

import com.autonavi.minimap.life.common.page.BaseListDataNodePage;

/* renamed from: doh reason: default package */
/* compiled from: BaseListDataNodePresenter */
public class doh<Page extends BaseListDataNodePage> extends doi<Page> {
    public doh(Page page) {
        super(page);
    }

    public void onPageCreated() {
        super.onPageCreated();
        ((BaseListDataNodePage) this.mPage).a();
    }

    public void onDestroy() {
        ((BaseListDataNodePage) this.mPage).i();
        super.onDestroy();
    }
}
