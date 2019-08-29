package defpackage;

import com.amap.bundle.webview.page.BaseExtendWebViewPage;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.uc.webview.export.WebSettings;

/* renamed from: aje reason: default package */
/* compiled from: BaseExtendWebViewPresenter */
public final class aje<Page extends BaseExtendWebViewPage> extends AbstractBasePresenter<Page> {
    public aje(Page page) {
        super(page);
    }

    public final void onStart() {
        ((BaseExtendWebViewPage) this.mPage).a(false);
        ((BaseExtendWebViewPage) this.mPage).a();
        BaseExtendWebViewPage baseExtendWebViewPage = (BaseExtendWebViewPage) this.mPage;
        if (!(baseExtendWebViewPage.b == null || baseExtendWebViewPage.b.getWebView() == null)) {
            baseExtendWebViewPage.b.setOnLaunchTaobaoLogin(baseExtendWebViewPage);
            baseExtendWebViewPage.b.setMeizuAuthroizedListener(baseExtendWebViewPage);
            baseExtendWebViewPage.b.getWebView().onResume();
        }
        super.onStart();
    }

    public final void onStop() {
        ((BaseExtendWebViewPage) this.mPage).a(true);
        BaseExtendWebViewPage baseExtendWebViewPage = (BaseExtendWebViewPage) this.mPage;
        if (!(baseExtendWebViewPage.b == null || baseExtendWebViewPage.b.getWebView() == null)) {
            baseExtendWebViewPage.b.setOnLaunchTaobaoLogin(null);
            baseExtendWebViewPage.b.setMeizuAuthroizedListener(null);
            baseExtendWebViewPage.b.getWebView().onPause();
        }
        ((BaseExtendWebViewPage) this.mPage).b();
        super.onStop();
    }

    public final void onDestroy() {
        BaseExtendWebViewPage baseExtendWebViewPage = (BaseExtendWebViewPage) this.mPage;
        if (baseExtendWebViewPage.b != null) {
            baseExtendWebViewPage.b.setOnLaunchTaobaoLogin(null);
            baseExtendWebViewPage.b.setOnWebViewEventListener(null);
            baseExtendWebViewPage.b.dismissProgressDlg();
            if (baseExtendWebViewPage.b.getWebView() != null) {
                WebSettings settings = baseExtendWebViewPage.b.getWebView().getSettings();
                if (settings != null) {
                    settings.setBuiltInZoomControls(false);
                    settings.setSavePassword(false);
                }
                baseExtendWebViewPage.b.getWebView().destroyDrawingCache();
                baseExtendWebViewPage.b.getWebView().destroy();
            }
        }
        super.onDestroy();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        ((BaseExtendWebViewPage) this.mPage).a(i, pageBundle);
    }
}
