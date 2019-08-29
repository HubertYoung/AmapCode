package defpackage;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.travelguide.page.TransparentTitleWebPage;

/* renamed from: drk reason: default package */
/* compiled from: TransparentTitleWebPresenter */
public final class drk extends AbstractBasePresenter<TransparentTitleWebPage> implements OnClickListener {
    public drk(TransparentTitleWebPage transparentTitleWebPage) {
        super(transparentTitleWebPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
        TransparentTitleWebPage transparentTitleWebPage = (TransparentTitleWebPage) this.mPage;
        View contentView = transparentTitleWebPage.getContentView();
        transparentTitleWebPage.a = contentView.findViewById(R.id.title_btn_left_transparent);
        transparentTitleWebPage.b = contentView.findViewById(R.id.title_btn_right_transparent);
        transparentTitleWebPage.d = (ExtendedWebView) contentView.findViewById(R.id.webView);
        transparentTitleWebPage.a.setOnClickListener((OnClickListener) transparentTitleWebPage.mPresenter);
        transparentTitleWebPage.b.setOnClickListener((OnClickListener) transparentTitleWebPage.mPresenter);
        transparentTitleWebPage.c = new JsAdapter(transparentTitleWebPage.getPageContext(), (a) transparentTitleWebPage.d);
        transparentTitleWebPage.c.setRightBtn(transparentTitleWebPage.b);
        transparentTitleWebPage.b.setVisibility(8);
        transparentTitleWebPage.d.initializeWebView((Object) transparentTitleWebPage.c, (Handler) null, true, false);
        transparentTitleWebPage.d.setVisibility(0);
        transparentTitleWebPage.d.setOnWebViewEventListener(transparentTitleWebPage);
        transparentTitleWebPage.d.clearView();
        transparentTitleWebPage.d.clearCache(false);
        TransparentTitleWebPage transparentTitleWebPage2 = (TransparentTitleWebPage) this.mPage;
        PageBundle arguments = transparentTitleWebPage2.getArguments();
        if (arguments != null) {
            transparentTitleWebPage2.e = arguments.getString("url");
        }
        if (!TextUtils.isEmpty(transparentTitleWebPage2.e)) {
            transparentTitleWebPage2.d.loadUrl(transparentTitleWebPage2.e);
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        TransparentTitleWebPage transparentTitleWebPage = (TransparentTitleWebPage) this.mPage;
        if (transparentTitleWebPage.d == null || !transparentTitleWebPage.d.canGoBack()) {
            transparentTitleWebPage.finish();
        } else {
            transparentTitleWebPage.d.goBack();
        }
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onClick(View view) {
        if (view.getId() == R.id.title_btn_left_transparent) {
            onBackPressed();
            return;
        }
        if (view.getId() == R.id.title_btn_right_transparent) {
            TransparentTitleWebPage transparentTitleWebPage = (TransparentTitleWebPage) this.mPage;
            if (transparentTitleWebPage.c != null) {
                transparentTitleWebPage.c.doRightBtnFunction();
            }
        }
    }
}
