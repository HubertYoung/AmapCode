package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;

/* renamed from: bbd reason: default package */
/* compiled from: SearchScenicEyireMapPresenter */
public final class bbd extends Ajx3PagePresenter {
    bbc a;

    public bbd(Ajx3Page ajx3Page) {
        super(ajx3Page);
        this.a = (bbc) ajx3Page;
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        if (this.a != null) {
            this.a.a(pageBundle);
        }
    }
}
