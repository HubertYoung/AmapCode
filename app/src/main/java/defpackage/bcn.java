package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.search.fragment.SearchCQDetailPage;
import java.util.List;

@BundleInterface(bci.class)
/* renamed from: bcn reason: default package */
/* compiled from: SearchCQDetailPageOpenerImpl */
public class bcn implements bci {
    private PageBundle a;

    public bcn() {
        if (this.a == null) {
            this.a = new PageBundle();
            this.a.putBoolean(AbstractBaseMapPage.PAGE_EXTENDS_LAYER, true);
        }
    }

    public final boolean a(@NonNull bid bid) {
        return bid instanceof SearchCQDetailPage;
    }

    public final boolean a(String str) {
        return SearchCQDetailPage.class.getName().equals(str);
    }

    public final void a(@NonNull bid bid, @NonNull POI poi, List<Long> list, boolean z) {
        this.a.putObject("key_cq_poi", poi);
        this.a.putObject("key_cq_subWay_Active_Ids", list);
        this.a.putInt("key_cq_type", 0);
        this.a.putBoolean("key_cq_is_favorite", z);
        bid.startPage(SearchCQDetailPage.class, this.a);
    }

    public final void a(@NonNull bid bid, @NonNull POI poi) {
        this.a.putObject("key_cq_poi", poi);
        this.a.putInt("key_cq_type", 1);
        bid.startPage(SearchCQDetailPage.class, this.a);
    }

    public final void a(@NonNull bid bid, @NonNull cdy cdy) {
        this.a.putInt("key_cq_type", 2);
        this.a.putObject("key_cq_gps_overlay_item", cdy);
        bid.startPage(SearchCQDetailPage.class, this.a);
    }
}
