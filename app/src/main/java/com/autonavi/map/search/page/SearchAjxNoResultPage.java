package com.autonavi.map.search.page;

import android.content.Context;
import android.graphics.Rect;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.annotation.PageAction;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.searchresult.ajx.ModuleSearchNoResult;
import com.autonavi.bundle.searchresult.ajx.ModuleSearchNoResult.b;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.search.model.SearchConst.SearchFor;
import org.json.JSONObject;

@PageAction("amap.search.action.nativenoresult")
public class SearchAjxNoResultPage extends Ajx3Page implements b {
    public static String a = "key_center_poi";
    public static String b = "path://amap_bundle_search/src/search_result/pages/SearchNoResultPage.page.js";
    private String c = "native_no_result_type";
    private String d = TrafficUtil.KEYWORD;
    private String e = "page_type";
    private ModuleSearchNoResult f = null;
    private int g = 0;
    private InfoliteParam h = null;
    private Rect i = null;
    private POI j = null;
    private int k = 0;
    private String l = "";

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.f = (ModuleSearchNoResult) this.mAjxView.getJsModule(ModuleSearchNoResult.MODULE_NAME);
        if (this.f != null) {
            this.f.seOffLinetUiListener(this);
        }
    }

    private String b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(this.e, 0);
            jSONObject.put(this.c, String.valueOf(this.g));
            jSONObject.put(this.d, this.l);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public final void a(int i2) {
        if (i2 == 0) {
            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
            if (iOfflineManager != null) {
                iOfflineManager.enterOffFrmDownloadMap();
            }
        } else if (i2 != 2) {
            aey.a(true);
            ekv ekv = new ekv();
            bvt bvt = new bvt();
            bvt.c = this.k;
            bvt.e = this.h.keywords;
            bvt.b = SearchFor.DEFAULT;
            bwx bwx = new bwx(this.h.keywords, this.k, true);
            bwx.r = this.j;
            bwx.o = this.i;
            bwx.s = true;
            bvt.d = bwx;
            ekv.a(this.h, 2, bvt);
        } else {
            ekv ekv2 = new ekv();
            bvt bvt2 = new bvt();
            bvt2.c = 0;
            bvt2.e = this.h.keywords;
            bvt2.b = SearchFor.DEFAULT;
            bwx bwx2 = new bwx(this.h.keywords, this.k, true);
            bwx2.o = this.i;
            bwx2.r = this.j;
            bvt2.d = bwx2;
            ekv2.a(this.h, 1, bvt2);
        }
    }

    public final void a() {
        bbn bbn = null;
        if (this.k == 2 || this.k == 1) {
            bbi bbi = (bbi) a.a.a(bbi.class);
            if (bbi != null) {
                bbn = bbi.a().c().a().b().a(this.j).a(this.l);
                if (this.k == 2) {
                    ((bbh) bbn).b((String) "220000");
                } else if (this.k == 1) {
                    ((bbh) bbn).b((String) "620000");
                }
            }
        } else {
            bcb bcb = (bcb) a.a.a(bcb.class);
            if (bcb != null) {
                bbn = bcb.a().c(this.l);
            }
        }
        elc.c = false;
        if (bbn != null) {
            bbn.a(this);
        }
        if (!(bbn instanceof bca)) {
            finish();
        }
    }

    public void onCreate(Context context) {
        if (getArguments() != null) {
            this.g = getArguments().getInt("native_no_result_type");
            this.k = getArguments().getInt("search_page_type");
            this.h = (InfoliteParam) getArguments().getObject("search_url_wrapper");
            if (this.h == null) {
                this.h = new InfoliteParam();
            }
            this.i = (Rect) getArguments().getObject("map_center_rect");
            if (this.i == null) {
                this.i = new Rect();
            }
            this.l = getArguments().getString(TrafficUtil.KEYWORD);
            if (getArguments().containsKey(a)) {
                this.j = (POI) getArguments().getObject(a);
            }
            if (this.j == null) {
                this.j = POIFactory.createPOI();
            }
        }
        PageBundle arguments = getArguments();
        if (arguments == null) {
            arguments = new PageBundle();
            setArguments(arguments);
        }
        arguments.putString("url", b);
        arguments.putString("jsData", b());
        super.onCreate(context);
    }
}
