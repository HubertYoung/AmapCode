package defpackage;

import android.content.Context;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.basemap.route.page.TitleBarPage;

/* renamed from: cqv reason: default package */
/* compiled from: TitleBarPresenter */
public class cqv<Page extends TitleBarPage> extends AbstractBasePresenter<Page> {
    public cqv(Page page) {
        super(page);
    }

    public void onPageCreated() {
        super.onPageCreated();
        ((TitleBarPage) this.mPage).a(((TitleBarPage) this.mPage).getArguments());
    }

    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        bnm.b(true);
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
        bnm.b(false);
    }

    public ON_BACK_TYPE onBackPressed() {
        return super.onBackPressed();
    }

    public final Context b() {
        return ((TitleBarPage) this.mPage).getContext();
    }
}
