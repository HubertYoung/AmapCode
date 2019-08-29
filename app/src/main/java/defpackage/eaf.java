package defpackage;

import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.IPage;

/* renamed from: eaf reason: default package */
/* compiled from: BaseRoutePresenter */
public abstract class eaf<Page extends IPage> extends AbstractBasePresenter<Page> {
    public eaf(Page page) {
        super(page);
    }

    private String getTag() {
        return getClass().getSimpleName();
    }

    public void onPageCreated() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTag());
        sb.append(" onPageCreated");
        eao.c("lifecircle", sb.toString());
        super.onPageCreated();
    }

    public void onResume() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTag());
        sb.append(" onResume");
        eao.c("lifecircle", sb.toString());
        super.onResume();
    }

    public void onPause() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTag());
        sb.append(" onPause");
        eao.c("lifecircle", sb.toString());
        super.onPause();
    }

    public void onStart() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTag());
        sb.append(" onStart");
        eao.c("lifecircle", sb.toString());
        super.onStart();
    }

    public void onStop() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTag());
        sb.append(" onStop");
        eao.c("lifecircle", sb.toString());
        super.onStop();
    }

    public void onDestroy() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTag());
        sb.append(" onDestroy");
        eao.c("lifecircle", sb.toString());
        super.onDestroy();
    }
}
