package defpackage;

import android.content.Context;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import defpackage.su;

/* renamed from: sw reason: default package */
/* compiled from: DriveBasePresenter */
public abstract class sw<Page extends DriveBasePage, Model extends su> extends AbstractBasePresenter<Page> {
    protected Context a;
    public Model b = a();

    /* access modifiers changed from: protected */
    public abstract Model a();

    public sw(Page page) {
        super(page);
    }

    public final void a(Context context) {
        this.a = context;
        this.b.a(context);
    }

    public final Context b() {
        return this.a;
    }
}
