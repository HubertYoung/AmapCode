package defpackage;

import android.content.Context;
import com.amap.bundle.drivecommon.mvp.view.DriveBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import defpackage.st;

/* renamed from: sv reason: default package */
/* compiled from: DriveBaseMapPresenter */
public abstract class sv<Page extends DriveBaseMapPage, Model extends st> extends AbstractBaseMapPagePresenter<Page> {
    protected Context a;
    protected Model b = a();

    /* access modifiers changed from: protected */
    public abstract Model a();

    public sv(Page page) {
        super(page);
    }

    public final void a(Context context) {
        this.a = context;
        this.b.a(context);
    }
}
