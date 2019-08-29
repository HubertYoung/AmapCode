package defpackage;

import android.support.annotation.CallSuper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPageHost;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.MapBasePresenter;

/* renamed from: ann reason: default package */
/* compiled from: SearchMapBasePresenter */
public abstract class ann<Page extends MapBasePage> extends MapBasePresenter<Page> {
    private boolean a;
    private boolean b;

    /* access modifiers changed from: protected */
    public abstract void a();

    /* access modifiers changed from: protected */
    public abstract void b();

    /* access modifiers changed from: protected */
    public abstract void c();

    /* access modifiers changed from: protected */
    public abstract void d();

    public ann(Page page) {
        super(page);
    }

    @CallSuper
    public void onPageCreated() {
        super.onPageCreated();
        this.a = true;
    }

    @CallSuper
    public void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
    }

    @CallSuper
    public void onResume() {
        super.onResume();
        if (this.b || this.a) {
            a();
        } else {
            c();
        }
        this.b = false;
        this.a = false;
    }

    public void onPause() {
        super.onPause();
        if (!((IPageHost) ((MapBasePage) this.mPage).getActivity()).isHostPaused()) {
            this.b = true;
            b();
            return;
        }
        d();
    }
}
