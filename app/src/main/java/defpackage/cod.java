package defpackage;

import android.net.Uri;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;

/* renamed from: cod reason: default package */
/* compiled from: OperationCommuteControllerImpl */
public class cod implements ava {
    /* access modifiers changed from: private */
    public AlertView a;

    public final void a(final Uri uri, final boolean z) {
        aho.a(new Runnable() {
            public final void run() {
                cod.a(cod.this);
                final bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    a aVar = new a(pageContext.getActivity());
                    int i = R.string.dialog_set_home_title;
                    int i2 = R.string.dialog_set_home_content;
                    if (z) {
                        i = R.string.dialog_set_company_title;
                        i2 = R.string.dialog_set_company_content;
                    }
                    aVar.a(i);
                    aVar.b(i2);
                    aVar.a(R.string.dialog_set_home_or_company_confirm, (a) new a() {
                        public final void onClick(AlertView alertView, int i) {
                            pageContext.dismissViewLayer(alertView);
                            cod.a();
                            cod.this.a = null;
                        }
                    });
                    aVar.b(R.string.dialog_set_home_or_company_cancel, (a) new a() {
                        public final void onClick(AlertView alertView, int i) {
                            pageContext.dismissViewLayer(alertView);
                            cod.this.a = null;
                        }
                    });
                    aVar.a(false);
                    cod.this.a = aVar.a();
                    pageContext.showViewLayer(cod.this.a);
                }
            }
        });
    }

    static /* synthetic */ void a(cod cod) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null && cod.a != null) {
            pageContext.dismissViewLayer(cod.a);
        }
    }

    static /* synthetic */ void a() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean("openMinePage", true);
        auz auz = (auz) a.a.a(auz.class);
        if (auz != null) {
            auz.a(pageBundle);
        }
    }
}
