package defpackage;

import com.amap.bundle.environmentmap.page.SearchEnvironmentMapPage;
import java.lang.ref.WeakReference;

/* renamed from: uu reason: default package */
/* compiled from: GpsClickListenerImpl */
public final class uu implements a {
    private WeakReference<SearchEnvironmentMapPage> a;

    public uu(SearchEnvironmentMapPage searchEnvironmentMapPage) {
        this.a = new WeakReference<>(searchEnvironmentMapPage);
    }

    public final void a() {
        SearchEnvironmentMapPage searchEnvironmentMapPage = (SearchEnvironmentMapPage) this.a.get();
        if (searchEnvironmentMapPage != null) {
            searchEnvironmentMapPage.b();
            searchEnvironmentMapPage.a();
        }
    }
}
