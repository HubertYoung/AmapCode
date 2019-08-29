package defpackage;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.amap.bundle.webview.page.WebViewPage;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;

@BundleInterface(aix.class)
/* renamed from: aiw reason: default package */
/* compiled from: WebviewService */
public class aiw extends esi implements aix {
    public final void a(bid bid, PageBundle pageBundle, aja aja) {
        if (bid != null) {
            pageBundle.putObject("h5_config", aja);
            bid.startPage(WebViewPage.class, pageBundle);
        }
    }

    public final void a(bid bid, aja aja) {
        if (bid != null && aja != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("h5_config", aja);
            bid.startPage(WebViewPage.class, pageBundle);
        }
    }

    public final void a(bid bid, aja aja, int i) {
        if (bid != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("h5_config", aja);
            bid.startPageForResult(WebViewPage.class, pageBundle, i);
        }
    }

    public final boolean a(bid bid) {
        return bid instanceof WebViewPage;
    }

    public final vy a(bid bid, String str) {
        if (TextUtils.isEmpty(str) || bid == null) {
            return null;
        }
        ajd ajd = new ajd(bid, str);
        if (!ajd.c) {
            if (ajd.b != null) {
                ajd.b.showViewLayer(ajd);
            }
            ajd.a.loadUrl(ajd.d);
            ajd.a.postDelayed(new Runnable() {
                public final void run() {
                    if (!ajd.this.f && !ajd.this.h) {
                        ajd.this.a.setViewOnTouchListener(new OnTouchListener() {
                            public final boolean onTouch(View view, MotionEvent motionEvent) {
                                if (ajd.this.c) {
                                    ajd.this.a();
                                }
                                return false;
                            }
                        });
                    }
                }
            }, 500);
            ajd.c = true;
        }
        return ajd;
    }
}
