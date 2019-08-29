package defpackage;

import android.content.Context;
import android.view.ViewGroup;
import com.autonavi.map.core.view.MapLayerDrawerView.a;
import com.autonavi.map.search.fragment.SearchCQDetailPage;
import com.autonavi.minimap.R;

/* renamed from: bzb reason: default package */
/* compiled from: SearchCQMapLayerDrawerController */
public final class bzb {
    SearchCQDetailPage a;
    volatile f b;
    private Object c = new Object();

    public bzb(SearchCQDetailPage searchCQDetailPage) {
        this.a = searchCQDetailPage;
    }

    public final boolean a() {
        if (this.b == null) {
            synchronized (this.c) {
                if (this.b == null) {
                    this.b = new bsd(this.a.getSuspendManager(), this.a.getMapManager(), new a() {
                        public final Context a() {
                            return bzb.this.a.getSuspendManager().a();
                        }

                        public final ViewGroup b() {
                            return (ViewGroup) bzb.this.a.getContentView().findViewById(R.id.home_page_drawer_content);
                        }
                    });
                }
            }
        }
        if (this.b.f()) {
            return false;
        }
        this.b.c();
        return true;
    }

    public final boolean b() {
        if (this.b == null || !this.b.f()) {
            return false;
        }
        this.b.d();
        return true;
    }
}
