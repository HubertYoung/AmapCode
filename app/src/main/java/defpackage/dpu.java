package defpackage;

import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.life.order.base.page.BaseOrderTabPage;

/* renamed from: dpu reason: default package */
/* compiled from: BaseOrderTabPresenter */
public final class dpu extends AbstractBasePresenter<BaseOrderTabPage> {
    public boolean a = false;
    private boolean b = false;

    public dpu(BaseOrderTabPage baseOrderTabPage) {
        super(baseOrderTabPage);
    }

    public final void onResume() {
        super.onResume();
        this.a = this.b ^ a();
        if (this.a) {
            this.b = a();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        ((BaseOrderTabPage) this.mPage).b();
    }

    private static boolean a() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }
}
