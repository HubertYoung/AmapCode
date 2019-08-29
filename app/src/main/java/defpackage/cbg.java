package defpackage;

import android.graphics.Rect;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.search.model.SearchConst.SearchFor;
import com.tencent.connect.common.Constants;

/* renamed from: cbg reason: default package */
/* compiled from: SearchBundle */
final class cbg {
    String a;
    int b = -1;
    dlg c;
    Rect d;
    SearchFor e = SearchFor.DEFAULT;
    boolean f;
    String g;
    String h;

    cbg() {
    }

    static cbg a(PageBundle pageBundle) {
        cbg cbg = new cbg();
        if (pageBundle == null) {
            return cbg;
        }
        cbg.b = pageBundle.getInt("poi_detail_page_type", -1);
        cbg.a = pageBundle.getString(TrafficUtil.KEYWORD);
        cbg.d = (Rect) pageBundle.get("searchRect");
        cbg.e = (SearchFor) pageBundle.getObject("searchFor");
        cbg.g = pageBundle.getString("transfer_mode");
        cbg.h = pageBundle.getString("sc_stype");
        cbg.f = !pageBundle.getBoolean("clear_search_edit_focus", false);
        if (pageBundle.containsKey(Constants.KEY_ACTION) && "actiono_back_scheme".equals(pageBundle.getString(Constants.KEY_ACTION))) {
            cbg.c = (dlg) pageBundle.getObject("key_back_scheme_param");
        }
        if (cbg.e == null) {
            cbg.e = SearchFor.DEFAULT;
        }
        return cbg;
    }
}
