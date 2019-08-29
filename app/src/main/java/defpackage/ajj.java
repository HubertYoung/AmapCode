package defpackage;

import android.os.Handler;
import android.os.Looper;
import com.amap.bundle.webview.page.TransparentWebViewPage;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;

/* renamed from: ajj reason: default package */
/* compiled from: TransparentWebViewPresenter */
public final class ajj extends AbstractBasePresenter<TransparentWebViewPage> {
    public ajj(TransparentWebViewPage transparentWebViewPage) {
        super(transparentWebViewPage);
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (((TransparentWebViewPage) this.mPage).a) {
            return super.onBackPressed();
        }
        ((TransparentWebViewPage) this.mPage).finish();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onDestroy() {
        super.onDestroy();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public final void run() {
                if (TransparentWebViewPage.this.c != null) {
                    TransparentWebViewPage.this.c.onDestory();
                    TransparentWebViewPage.this.c = null;
                }
                if (TransparentWebViewPage.this.b != null) {
                    TransparentWebViewPage.this.b.destroy();
                }
            }
        }, 100);
    }
}
