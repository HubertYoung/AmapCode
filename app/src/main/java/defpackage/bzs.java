package defpackage;

import android.graphics.Point;
import android.view.View;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState;
import java.util.List;

/* renamed from: bzs reason: default package */
/* compiled from: ISearchViewManager */
public interface bzs extends bzr {
    void a(View view);

    void a(cbr cbr);

    void a(InfoliteResult infoliteResult);

    void a(PageBundle pageBundle, InfoliteResult infoliteResult, List<POI> list, boolean z);

    void a(POI poi);

    void a(POI poi, int i);

    void a(AbstractBasePage abstractBasePage, int i, ResultType resultType, PageBundle pageBundle);

    void b(int i);

    void c(boolean z);

    void d();

    void d(boolean z);

    int d_();

    void e();

    void e(boolean z);

    int g();

    int h();

    Point l();

    int m();

    void n();

    void o();

    void p();

    boolean q();

    void r();

    void s();

    boolean t();

    int u();

    boolean x();

    void y();

    DetailLayerState z();
}
