package defpackage;

import android.view.View;
import android.view.ViewGroup;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.searchresult.ajx.ModulePoi.c;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.ajx3.views.AmapAjxView.BackCallback;
import com.autonavi.widget.slidinguppanel.SlidingUpPanelLayout.PanelState;

/* renamed from: cap reason: default package */
/* compiled from: IPoiDetailLayerManager */
public interface cap {

    /* renamed from: cap$a */
    /* compiled from: IPoiDetailLayerManager */
    public interface a {
        void a();
    }

    /* renamed from: cap$b */
    /* compiled from: IPoiDetailLayerManager */
    public interface b {
    }

    View a(AbstractBasePage abstractBasePage, ViewGroup viewGroup, boolean z, PageBundle pageBundle);

    void a();

    void a(View view, View view2);

    void a(a aVar);

    void a(com.autonavi.bundle.searchresult.ajx.AjxModuleTipDetailPage.a aVar);

    void a(com.autonavi.bundle.searchresult.ajx.ModulePoi.b bVar);

    void a(c cVar);

    void a(PageBundle pageBundle);

    void a(BackCallback backCallback);

    void a(String str);

    void a(boolean z);

    void a(boolean z, String str);

    boolean a(int i);

    void b();

    void b(PageBundle pageBundle);

    void b(String str);

    ON_BACK_TYPE c();

    caq d();

    void e();

    void f();

    void g();

    JsAdapter h();

    void i();

    PanelState j();

    void k();

    void l();

    boolean m();
}
