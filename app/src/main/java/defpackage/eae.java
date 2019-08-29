package defpackage;

import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import com.autonavi.map.fragmentcontainer.page.IMapPage;

/* renamed from: eae reason: default package */
/* compiled from: BaseRouteMapPresenter */
public abstract class eae<Page extends IMapPage> extends AbstractBaseMapPagePresenter<Page> {
    private boolean a = true;

    public eae(Page page) {
        super(page);
    }

    public void onPageCreated() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" onPageCreated");
        eao.c("lifecircle", sb.toString());
        super.onPageCreated();
    }

    public void onResume() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" onResume");
        eao.c("lifecircle", sb.toString());
        super.onResume();
    }

    public void onPause() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" onPause");
        eao.c("lifecircle", sb.toString());
        super.onPause();
    }

    public void onStart() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" onStart");
        eao.c("lifecircle", sb.toString());
        super.onStart();
        if (this.a) {
            this.a = false;
        }
    }

    public void onStop() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" onStop");
        eao.c("lifecircle", sb.toString());
        super.onStop();
        if (drl.a().b()) {
            this.a = true;
        }
    }

    public void onDestroy() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" onDestroy");
        eao.c("lifecircle", sb.toString());
        super.onDestroy();
    }
}
