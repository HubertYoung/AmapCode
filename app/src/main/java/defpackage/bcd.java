package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.map.search.page.SearchPage;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;

/* renamed from: bcd reason: default package */
/* compiled from: SearchHomePageOpenerImpl */
public final class bcd implements bca {
    private PageBundle a = new PageBundle();

    public final bca a(String str) {
        this.a.putString("city_name", str);
        return this;
    }

    public final bca b(String str) {
        this.a.putString("city_code", str);
        return this;
    }

    public final bca c(String str) {
        this.a.putString(TrafficUtil.KEYWORD, str);
        return this;
    }

    public final bca a() {
        this.a.putBoolean("clear_search_edit_focus", true);
        return this;
    }

    public final void a(bid bid) {
        bid.startPage(SearchPage.class, this.a);
    }
}
