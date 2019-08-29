package defpackage;

import android.graphics.Rect;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import search.page.SearchFromAroundPage;

/* renamed from: bbj reason: default package */
/* compiled from: AroundInputPageOpenerImpl */
public final class bbj implements bbh {
    private PageBundle a = new PageBundle();

    public final bbh a(String str) {
        this.a.putString(TrafficUtil.KEYWORD, str);
        return this;
    }

    public final bbh b(String str) {
        this.a.putString("from_page", str);
        return this;
    }

    public final bbh a(Rect rect) {
        this.a.putObject("map_rect", rect);
        return this;
    }

    public final bbh a() {
        this.a.putBoolean("draw_center", true);
        return this;
    }

    public final bbh b() {
        this.a.putInt("search_type", 1);
        return this;
    }

    public final bbh a(POI poi) {
        this.a.putObject("center_poi", poi);
        return this;
    }

    public final bbh b(POI poi) {
        this.a.putObject("POI", poi);
        return this;
    }

    public final bbh c() {
        this.a.putBoolean("from_search_native_no_result_page", true);
        return this;
    }

    public final bbh a(boolean z) {
        this.a.putBoolean("key_can_search_with_hint", z);
        return this;
    }

    public final bbh b(boolean z) {
        this.a.putBoolean("is_cache", z);
        return this;
    }

    public final bbh c(boolean z) {
        this.a.putBoolean("is_from_new_nearby", z);
        return this;
    }

    public final bbh c(String str) {
        this.a.putString("hint_content", str);
        return this;
    }

    public final void a(bid bid) {
        this.a.containsKey("is_from_new_nearby");
        bid.startPage(SearchFromAroundPage.class, this.a);
    }
}
