package defpackage;

import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.KeyValueStorage.WebStorage;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.search.fragment.PoiDetailPageNew;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView.Event;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;

/* renamed from: byc reason: default package */
/* compiled from: SearchResultSubStationHelper */
public final class byc {
    public AbstractBasePage a;
    public InfoliteResult b;

    /* renamed from: byc$a */
    /* compiled from: SearchResultSubStationHelper */
    public class a implements com.autonavi.map.search.view.PoiDetailViewForCQ.a {
        private a() {
        }

        public /* synthetic */ a(byc byc, byte b) {
            this();
        }
    }

    /* renamed from: byc$b */
    /* compiled from: SearchResultSubStationHelper */
    public class b implements Event {
        boolean a;
        public InfoliteResult b;

        private b() {
            this.a = false;
        }

        public /* synthetic */ b(byc byc, byte b2) {
            this();
        }

        public final void onExecute(int i, POI poi) {
            if (poi != null) {
                WebStorage a2 = bic.a((String) "poi_info");
                if (a2 != null) {
                    String businfoAlias = ((ChildStationPoiData) poi.as(ChildStationPoiData.class)).getBusinfoAlias();
                    a2.beginTransaction();
                    if (businfoAlias == null) {
                        businfoAlias = "";
                    }
                    a2.set("CURRENT_BUS_ALIAS", businfoAlias);
                    a2.commit();
                }
                PageBundle pageBundle = new PageBundle();
                pageBundle.putSerializable("POI", poi);
                pageBundle.putBoolean("isGeoCode", false);
                pageBundle.putBoolean("isGPSPoint", false);
                pageBundle.putBoolean(Constant.KEY_IS_BACK, true);
                pageBundle.putBoolean("isMarkPoint", this.a);
                pageBundle.putString("fromSource", "poitip");
                if (this.b != null) {
                    pageBundle.putObject("poi_search_result", this.b);
                }
                pageBundle.putInt("key_search_process_key", 4);
                byc.this.a.startPage(PoiDetailPageNew.class, pageBundle);
            }
        }
    }

    public byc(AbstractBasePage abstractBasePage, InfoliteResult infoliteResult) {
        this.a = abstractBasePage;
        this.b = infoliteResult;
    }
}
