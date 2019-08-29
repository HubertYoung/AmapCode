package com.autonavi.map.search.page;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.annotation.PageAction;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.searchresult.ajx.ModuleSearchNoResult;
import com.autonavi.bundle.searchresult.ajx.ModuleSearchNoResult.c;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;

@PageAction("amap.search.action.searcherror")
public class SearchAjxErrorPage extends Ajx3Page implements c {
    static String a = "path://amap_bundle_search/src/search_result/pages/SearchNoResultPage.page.js";
    public static final String b;
    public static final String c;
    public static final String d;
    public static final String e;
    public static final String f;
    public static final String g;
    public static final String h;
    public bbq i;
    private String j = "page_type";
    private int k = 0;
    private String l = "";
    private String[] m = new String[0];
    private ModuleSearchNoResult n;
    private InfoliteParam o = null;
    private Rect p;
    private POI q;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(SearchAjxErrorPage.class.getName());
        sb.append(".searchKeyWord");
        b = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(SearchAjxErrorPage.class.getName());
        sb2.append(".errorType");
        c = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(SearchAjxErrorPage.class.getName());
        sb3.append(".errorDesc");
        d = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(SearchAjxErrorPage.class.getName());
        sb4.append(".noresultsuggest");
        e = sb4.toString();
        StringBuilder sb5 = new StringBuilder();
        sb5.append(SearchAjxErrorPage.class.getName());
        sb5.append(".wrapper");
        f = sb5.toString();
        StringBuilder sb6 = new StringBuilder();
        sb6.append(SearchAjxErrorPage.class.getName());
        sb6.append(".map_center_rect");
        g = sb6.toString();
        StringBuilder sb7 = new StringBuilder();
        sb7.append(SearchAjxErrorPage.class.getName());
        sb7.append(".search_page_type");
        h = sb7.toString();
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.n = (ModuleSearchNoResult) this.mAjxView.getJsModule(ModuleSearchNoResult.MODULE_NAME);
        if (this.n != null) {
            this.n.setOnLineUiListener(this);
        }
    }

    private String d() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(this.j, 1);
            jSONObject.put("native_no_result_type", this.k);
            jSONObject.put(TrafficUtil.KEYWORD, this.l);
            if (this.m != null) {
                JSONArray jSONArray = new JSONArray();
                for (String put : this.m) {
                    jSONArray.put(put);
                }
                jSONObject.put("recomendWords", jSONArray);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public final void a(String str) {
        this.o.keywords = str;
        this.o.superid = SuperId.getInstance().getScenceId();
        int i2 = SuperId.getInstance().getBit1().equals(SuperId.BIT_1_RQBXY) ? 2 : SuperId.getInstance().getBit1().equals(SuperId.BIT_1_NEARBY_SEARCH) ? 1 : 0;
        bwx bwx = new bwx(str, i2, false);
        bwx.r = this.q;
        this.i = bwx.a(this.o, this.p, true);
        a(str, true);
    }

    public final void a() {
        if (DoNotUseTool.getMapView() != null) {
            POI createPOI = POIFactory.createPOI(this.l, GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapView().n()));
            IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
            if (iErrorReportStarter != null && createPOI != null) {
                iErrorReportStarter.startAddPoiFromSearch(createPOI);
            }
        }
    }

    public final void b() {
        try {
            PageBundle arguments = getArguments();
            if (arguments != null && arguments.containsKey("SearchErrorFragment.searchKeyWord")) {
                final String string = arguments.getString("SearchErrorFragment.searchKeyWord");
                StringBuilder sb = new StringBuilder("http://m.yz.sm.cn/s?q=");
                sb.append(URLEncoder.encode(string, "utf-8"));
                sb.append("&from=wh10001");
                aja aja = new aja(sb.toString());
                aja.b = new cbf() {
                    public final b c() {
                        return null;
                    }

                    public final boolean d() {
                        return true;
                    }

                    public final String b() {
                        return string;
                    }
                };
                aix aix = (aix) a.a.a(aix.class);
                if (aix != null) {
                    aix.a((bid) this, aja);
                }
            }
        } catch (UnsupportedEncodingException e2) {
            kf.a((Throwable) e2);
        }
    }

    public final void c() {
        PageBundle arguments = getArguments();
        a(arguments != null ? arguments.getString("SearchErrorFragment.searchKeyWord", "") : "", false);
    }

    private void a(String str, boolean z) {
        Rect rect;
        PageBundle arguments = getArguments();
        int i2 = 0;
        if (arguments != null) {
            i2 = arguments.getInt(h, 0);
            rect = (Rect) arguments.getObject(g);
        } else {
            rect = null;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putBoolean("clear_search_edit_focus", z);
        if (i2 == 2 && rect != null) {
            bbi bbi = (bbi) a.a.a(bbi.class);
            if (bbi != null) {
                bbh b2 = bbi.a().b((String) "220000").b(this.q).a(rect).a().b();
                if (!TextUtils.isEmpty(str)) {
                    b2.a(str);
                }
                finish();
                b2.a(this);
            }
        } else if (i2 == 1) {
            bbi bbi2 = (bbi) a.a.a(bbi.class);
            if (bbi2 != null) {
                bbh b3 = bbi2.a().b((String) "620000").b(this.q).a(rect).a().b();
                if (!TextUtils.isEmpty(str)) {
                    b3.a(str);
                }
                finish();
                b3.a(this);
            }
        } else {
            if (!TextUtils.isEmpty(str)) {
                pageBundle.putString(TrafficUtil.KEYWORD, str);
            }
            startPage((String) "amap.search.action.searchfragment", pageBundle);
        }
    }

    public void onCreate(Context context) {
        if (getArguments() != null) {
            if (getArguments().containsKey(c)) {
                this.k = getArguments().getInt(c);
            }
            this.l = getArguments().getString("SearchErrorFragment.searchKeyWord");
            if (getArguments().containsKey(e)) {
                String[] strArr = (String[]) getArguments().getObject(e);
                if (strArr != null && strArr.length > 0) {
                    this.m = strArr;
                }
            }
            if (getArguments().containsKey("map_center_rect")) {
                this.p = (Rect) getArguments().getObject("map_center_rect");
            }
            if (this.p == null) {
                this.p = new Rect();
            }
            if (getArguments().containsKey("POI")) {
                this.q = (POI) getArguments().getObject("POI");
            }
            if (this.q == null) {
                this.q = POIFactory.createPOI();
            }
            if (getArguments().containsKey(f)) {
                this.o = (InfoliteParam) getArguments().getObject(f);
            }
            if (this.o == null) {
                this.o = new InfoliteParam();
            }
        }
        PageBundle arguments = getArguments();
        if (arguments == null) {
            arguments = new PageBundle();
            setArguments(arguments);
        }
        arguments.putString("url", a);
        arguments.putString("jsData", d());
        super.onCreate(context);
    }
}
