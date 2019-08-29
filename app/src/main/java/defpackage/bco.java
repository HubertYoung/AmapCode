package defpackage;

import android.content.Context;
import android.text.TextUtils;
import com.amap.bundle.searchservice.api.ISearchService;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.map.search.view.PoiFocusScenicView;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import de.greenrobot.event.EventBus;
import org.json.JSONObject;

@BundleInterface(bck.class)
/* renamed from: bco reason: default package */
/* compiled from: SearchResultExporter */
public class bco implements bck {
    public final bcf a() {
        return new bcm();
    }

    public final boolean a(POI poi) {
        if (poi == null) {
            return false;
        }
        PoiLayoutTemplate a = cbq.a((ISearchPoiData) (SearchPoi) poi.as(SearchPoi.class));
        if (a == null) {
            return false;
        }
        String a2 = cbq.a(a);
        if (TextUtils.isEmpty(a2) || !a2.equals("share")) {
            return false;
        }
        return true;
    }

    public final void b() {
        EventBus.getDefault().post(new cbm());
    }

    public final void a(JSONObject jSONObject, POI poi) {
        elu.a(jSONObject, null, (SearchPoi) poi.as(SearchPoi.class));
    }

    public final bbm a(ael ael, int i) {
        bvt bvt = new bvt();
        bvt.d = new bwx(ael.b, i, false);
        bvt.a(ael.c);
        bvt.e = ael.b;
        bvt.c = i;
        InfoliteParam a = aew.a((aem) ael);
        a.superid = SuperId.getInstance().getScenceId();
        return new ekv().a(a, 1, bvt);
    }

    public final bch a(Context context) {
        if (context == null) {
            return null;
        }
        return new PoiFocusScenicView(context);
    }

    public final void c() {
        bct.a();
    }

    public final bcg e() {
        return new ekz();
    }

    public final bbq a(InfoliteParam infoliteParam, bcl bcl) {
        bvt bvt = new bvt(bcl);
        infoliteParam.superid = SuperId.getInstance().getScenceId();
        ISearchService iSearchService = (ISearchService) a.a.a(ISearchService.class);
        if (iSearchService == null) {
            return new bbq((adx) null);
        }
        bbq bbq = new bbq(iSearchService.a(infoliteParam, 1, (aeb<InfoliteResult>) bvt));
        bct.a(bvt.getLoadingMessage(), bbq);
        return bbq;
    }

    public final void d() {
        bct.a = null;
    }
}
