package defpackage;

import android.view.View;
import android.widget.ListView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.common.util.RouteShadowUtil$1;

/* renamed from: ebl reason: default package */
/* compiled from: RouteShadowUtil */
public final class ebl {
    public ListView a;
    public View b;
    private AbstractBasePage<?> c;

    public ebl(AbstractBasePage<?> abstractBasePage) {
        this.c = abstractBasePage;
    }

    public final void a() {
        if (this.a != null && this.b != null && this.c.isAlive()) {
            this.a.setOnScrollListener(new RouteShadowUtil$1(this));
        }
    }

    public final void b() {
        boolean z = true;
        int i = 0;
        if (this.a.getAdapter() != null && !this.a.getAdapter().isEmpty() && this.a.isShown()) {
            ListView listView = this.a;
            if (!(listView.getChildCount() > 0 && listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() >= listView.getPaddingTop())) {
                z = false;
            }
        }
        View view = this.b;
        if (z) {
            i = 8;
        }
        view.setVisibility(i);
    }
}
