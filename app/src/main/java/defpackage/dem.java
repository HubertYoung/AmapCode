package defpackage;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.auto.page.AliCarLinkManagerPage;
import com.autonavi.minimap.drive.auto.page.AliCarLinkManagerPage.CONNECTION_BUTTON_ACTION;
import com.autonavi.widget.ui.TitleBar;

/* renamed from: dem reason: default package */
/* compiled from: AlicarLinkManagerPresenter */
public final class dem extends sw<AliCarLinkManagerPage, deh> {
    public dem(AliCarLinkManagerPage aliCarLinkManagerPage) {
        super(aliCarLinkManagerPage);
    }

    public final void onPageCreated() {
        AliCarLinkManagerPage aliCarLinkManagerPage = (AliCarLinkManagerPage) this.mPage;
        ((TitleBar) aliCarLinkManagerPage.getContentView().findViewById(R.id.title)).setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                AliCarLinkManagerPage.this.finish();
            }
        });
        aliCarLinkManagerPage.a = (ImageView) aliCarLinkManagerPage.getContentView().findViewById(R.id.imageView2);
        aliCarLinkManagerPage.b = (Button) aliCarLinkManagerPage.getContentView().findViewById(R.id.connect_button);
        NoDBClickUtil.a((View) aliCarLinkManagerPage.b, aliCarLinkManagerPage.m);
        aliCarLinkManagerPage.c = (Button) aliCarLinkManagerPage.getContentView().findViewById(R.id.retry_connect_button);
        NoDBClickUtil.a((View) aliCarLinkManagerPage.c, aliCarLinkManagerPage.m);
        aliCarLinkManagerPage.d = (Button) aliCarLinkManagerPage.getContentView().findViewById(R.id.delete_connect_button);
        NoDBClickUtil.a((View) aliCarLinkManagerPage.d, aliCarLinkManagerPage.m);
        aliCarLinkManagerPage.e = (LinearLayout) aliCarLinkManagerPage.getContentView().findViewById(R.id.start_connection_sub1);
        aliCarLinkManagerPage.f = (LinearLayout) aliCarLinkManagerPage.getContentView().findViewById(R.id.fail_connection_sub1);
        aliCarLinkManagerPage.g = (LinearLayout) aliCarLinkManagerPage.getContentView().findViewById(R.id.fail_connection_sub2);
        aliCarLinkManagerPage.h = (LinearLayout) aliCarLinkManagerPage.getContentView().findViewById(R.id.delete_connection_sub1);
        aliCarLinkManagerPage.i = (LinearLayout) aliCarLinkManagerPage.getContentView().findViewById(R.id.delete_connection_sub2);
    }

    public final void onStart() {
        ((AliCarLinkManagerPage) this.mPage).a(CONNECTION_BUTTON_ACTION.TO_CONNECT);
    }

    public final void onDestroy() {
        AliCarLinkManagerPage aliCarLinkManagerPage = (AliCarLinkManagerPage) this.mPage;
        aliCarLinkManagerPage.j.b(aliCarLinkManagerPage.o);
        aliCarLinkManagerPage.j.b(aliCarLinkManagerPage.n);
        if (aliCarLinkManagerPage.l != null) {
            aliCarLinkManagerPage.dismissAllViewLayers();
            aliCarLinkManagerPage.l = null;
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        AliCarLinkManagerPage aliCarLinkManagerPage = (AliCarLinkManagerPage) this.mPage;
        if (aliCarLinkManagerPage.isViewLayerShowing(aliCarLinkManagerPage.l)) {
            ((AliCarLinkManagerPage) this.mPage).dismissAllViewLayers();
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        AliCarLinkManagerPage aliCarLinkManagerPage2 = (AliCarLinkManagerPage) this.mPage;
        if (!(aliCarLinkManagerPage2.k != null && aliCarLinkManagerPage2.k.isShowing())) {
            return super.onBackPressed();
        }
        ((AliCarLinkManagerPage) this.mPage).b();
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        if (resultType == ResultType.OK && i == 1000) {
            boolean z = false;
            if (pageBundle != null) {
                z = pageBundle.getBoolean("firstConnected", false);
            }
            if (z) {
                apr apr = (apr) a.a.a(apr.class);
                if (apr != null) {
                    apr.a(AMapPageUtil.getPageContext(), pageBundle);
                }
                return;
            }
            ((AliCarLinkManagerPage) this.mPage).finish();
        }
    }

    public final /* synthetic */ su a() {
        return new deh(this);
    }
}
