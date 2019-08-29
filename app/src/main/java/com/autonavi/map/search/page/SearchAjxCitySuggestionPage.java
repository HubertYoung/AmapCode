package com.autonavi.map.search.page;

import android.content.Context;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchresult.ajx.ModuleSearchNoResult;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearchAjxCitySuggestionPage extends Ajx3Page implements com.autonavi.bundle.searchresult.ajx.ModuleSearchNoResult.a {
    static String a = "path://amap_bundle_search/src/search_result/pages/SearchCitySuggestionPage.page.js";
    private InfoliteResult b;
    private boolean c;
    private ArrayList<POI> d;
    private String e = "";
    private a f = null;
    private ModuleSearchNoResult g = null;
    private String h = "city_suggest";

    public interface a {
        void a(String str);
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.g = (ModuleSearchNoResult) this.mAjxView.getJsModule(ModuleSearchNoResult.MODULE_NAME);
        if (this.g != null) {
            this.g.setOnCitySuggUiListener(this);
        }
    }

    private String a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("keywords", this.e != null ? this.e : "");
            JSONArray jSONArray = new JSONArray();
            String str = this.h;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -460453422) {
                if (hashCode == -291564368) {
                    if (str.equals("city_suggest")) {
                        c2 = 1;
                    }
                }
            } else if (str.equals("area_suggest")) {
                c2 = 0;
            }
            if (c2 != 0) {
                if (!(this.b == null || this.b.searchInfo == null || this.b.searchInfo.g == null)) {
                    Iterator<CitySuggestion> it = this.b.searchInfo.g.iterator();
                    while (it.hasNext()) {
                        CitySuggestion next = it.next();
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("name", next.name);
                        jSONObject2.put("total", next.resultnum);
                        jSONObject2.put(AutoJsonUtils.JSON_ADCODE, next.adcode);
                        jSONArray.put(jSONObject2);
                    }
                }
            } else if (this.d != null) {
                Iterator<POI> it2 = this.d.iterator();
                while (it2.hasNext()) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put("name", it2.next().getName());
                    jSONArray.put(jSONObject3);
                }
            }
            jSONObject.put("regions", jSONArray);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0034 A[SYNTHETIC, Splitter:B:16:0x0034] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r6, java.lang.String r7) {
        /*
            r5 = this;
            java.lang.String r0 = r5.h
            int r1 = r0.hashCode()
            r2 = -460453422(0xffffffffe48e09d2, float:-2.0961162E22)
            r3 = 0
            r4 = 1
            if (r1 == r2) goto L_0x001d
            r2 = -291564368(0xffffffffee9f14b0, float:-2.4616563E28)
            if (r1 == r2) goto L_0x0013
            goto L_0x0027
        L_0x0013:
            java.lang.String r1 = "city_suggest"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0027
            r0 = 1
            goto L_0x0028
        L_0x001d:
            java.lang.String r1 = "area_suggest"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0027
            r0 = 0
            goto L_0x0028
        L_0x0027:
            r0 = -1
        L_0x0028:
            if (r0 == 0) goto L_0x0034
            com.autonavi.map.search.page.SearchAjxCitySuggestionPage$a r7 = r5.f
            if (r7 == 0) goto L_0x0033
            com.autonavi.map.search.page.SearchAjxCitySuggestionPage$a r7 = r5.f
            r7.a(r6)
        L_0x0033:
            return
        L_0x0034:
            int r6 = java.lang.Integer.parseInt(r7)     // Catch:{ Exception -> 0x010b }
            java.util.ArrayList<com.autonavi.common.model.POI> r7 = r5.d     // Catch:{ Exception -> 0x010b }
            java.lang.Object r6 = r7.get(r6)     // Catch:{ Exception -> 0x010b }
            com.autonavi.common.model.POI r6 = (com.autonavi.common.model.POI) r6     // Catch:{ Exception -> 0x010b }
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r7 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r7 = r6.as(r7)     // Catch:{ Exception -> 0x010b }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r7 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r7     // Catch:{ Exception -> 0x010b }
            if (r7 == 0) goto L_0x00fa
            java.lang.String r0 = "citycard"
            java.lang.String r1 = r7.getIndustry()     // Catch:{ Exception -> 0x010b }
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x010b }
            if (r0 == 0) goto L_0x00fa
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r5.b     // Catch:{ Exception -> 0x010b }
            aus r0 = r0.searchInfo     // Catch:{ Exception -> 0x010b }
            java.util.ArrayList<com.autonavi.common.model.POI> r0 = r0.l     // Catch:{ Exception -> 0x010b }
            r0.clear()     // Catch:{ Exception -> 0x010b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r5.b     // Catch:{ Exception -> 0x010b }
            aus r0 = r0.searchInfo     // Catch:{ Exception -> 0x010b }
            java.util.ArrayList<com.autonavi.common.model.POI> r0 = r0.l     // Catch:{ Exception -> 0x010b }
            r0.add(r6)     // Catch:{ Exception -> 0x010b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6 = r5.b     // Catch:{ Exception -> 0x010b }
            aus r6 = r6.searchInfo     // Catch:{ Exception -> 0x010b }
            r6.p = r4     // Catch:{ Exception -> 0x010b }
            java.lang.Double[] r6 = r7.getViewRegions()     // Catch:{ Exception -> 0x010b }
            if (r6 == 0) goto L_0x00c8
            java.lang.Double[] r6 = r7.getViewRegions()     // Catch:{ Exception -> 0x010b }
            int r6 = r6.length     // Catch:{ Exception -> 0x010b }
            r0 = 4
            if (r6 != r0) goto L_0x00c8
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6 = r5.b     // Catch:{ Exception -> 0x010b }
            aus r6 = r6.searchInfo     // Catch:{ Exception -> 0x010b }
            auk r6 = r6.a     // Catch:{ Exception -> 0x010b }
            java.lang.Double[] r0 = new java.lang.Double[r0]     // Catch:{ Exception -> 0x010b }
            r6.u = r0     // Catch:{ Exception -> 0x010b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6 = r5.b     // Catch:{ Exception -> 0x010b }
            aus r6 = r6.searchInfo     // Catch:{ Exception -> 0x010b }
            auk r6 = r6.a     // Catch:{ Exception -> 0x010b }
            java.lang.Double[] r6 = r6.u     // Catch:{ Exception -> 0x010b }
            java.lang.Double[] r0 = r7.getViewRegions()     // Catch:{ Exception -> 0x010b }
            r0 = r0[r3]     // Catch:{ Exception -> 0x010b }
            r6[r3] = r0     // Catch:{ Exception -> 0x010b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6 = r5.b     // Catch:{ Exception -> 0x010b }
            aus r6 = r6.searchInfo     // Catch:{ Exception -> 0x010b }
            auk r6 = r6.a     // Catch:{ Exception -> 0x010b }
            java.lang.Double[] r6 = r6.u     // Catch:{ Exception -> 0x010b }
            java.lang.Double[] r0 = r7.getViewRegions()     // Catch:{ Exception -> 0x010b }
            r0 = r0[r4]     // Catch:{ Exception -> 0x010b }
            r6[r4] = r0     // Catch:{ Exception -> 0x010b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6 = r5.b     // Catch:{ Exception -> 0x010b }
            aus r6 = r6.searchInfo     // Catch:{ Exception -> 0x010b }
            auk r6 = r6.a     // Catch:{ Exception -> 0x010b }
            java.lang.Double[] r6 = r6.u     // Catch:{ Exception -> 0x010b }
            java.lang.Double[] r0 = r7.getViewRegions()     // Catch:{ Exception -> 0x010b }
            r1 = 2
            r0 = r0[r1]     // Catch:{ Exception -> 0x010b }
            r6[r1] = r0     // Catch:{ Exception -> 0x010b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6 = r5.b     // Catch:{ Exception -> 0x010b }
            aus r6 = r6.searchInfo     // Catch:{ Exception -> 0x010b }
            auk r6 = r6.a     // Catch:{ Exception -> 0x010b }
            java.lang.Double[] r6 = r6.u     // Catch:{ Exception -> 0x010b }
            java.lang.Double[] r0 = r7.getViewRegions()     // Catch:{ Exception -> 0x010b }
            r1 = 3
            r0 = r0[r1]     // Catch:{ Exception -> 0x010b }
            r6[r1] = r0     // Catch:{ Exception -> 0x010b }
        L_0x00c8:
            com.autonavi.common.PageBundle r6 = new com.autonavi.common.PageBundle     // Catch:{ Exception -> 0x010b }
            r6.<init>()     // Catch:{ Exception -> 0x010b }
            java.lang.String r0 = "poi_search_result"
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r5.b     // Catch:{ Exception -> 0x010b }
            r6.putObject(r0, r1)     // Catch:{ Exception -> 0x010b }
            boolean r7 = r7.getRichInfoFlag()     // Catch:{ Exception -> 0x010b }
            if (r7 == 0) goto L_0x00e2
            java.lang.String r7 = "poi_detail_page_type"
            r0 = 8
            r6.putInt(r7, r0)     // Catch:{ Exception -> 0x010b }
            goto L_0x00e9
        L_0x00e2:
            java.lang.String r7 = "poi_detail_page_type"
            r0 = 10
            r6.putInt(r7, r0)     // Catch:{ Exception -> 0x010b }
        L_0x00e9:
            bid r7 = r5.getPageContext()     // Catch:{ Exception -> 0x010b }
            java.lang.Class<com.autonavi.map.search.fragment.PoiDetailPageNew> r0 = com.autonavi.map.search.fragment.PoiDetailPageNew.class
            r7.startPage(r0, r6)     // Catch:{ Exception -> 0x010b }
            bid r6 = r5.getPageContext()     // Catch:{ Exception -> 0x010b }
            r6.finish()     // Catch:{ Exception -> 0x010b }
            return
        L_0x00fa:
            bwv r7 = new bwv     // Catch:{ Exception -> 0x010b }
            boolean r0 = r5.c     // Catch:{ Exception -> 0x010b }
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x010b }
            r7.<init>(r5, r0)     // Catch:{ Exception -> 0x010b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r5.b     // Catch:{ Exception -> 0x010b }
            r7.a(r0, r6, r4)     // Catch:{ Exception -> 0x010b }
            return
        L_0x010b:
            r6 = move-exception
            r6.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.map.search.page.SearchAjxCitySuggestionPage.a(java.lang.String, java.lang.String):void");
    }

    public void onCreate(Context context) {
        if (getArguments() != null) {
            if (getArguments().containsKey("page_type")) {
                this.h = getArguments().getString("page_type");
            }
            if (getArguments().containsKey(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ)) {
                this.b = (InfoliteResult) getArguments().get(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ);
            }
            String str = this.h;
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -460453422) {
                if (hashCode == -291564368 && str.equals("city_suggest")) {
                    c2 = 1;
                }
            } else if (str.equals("area_suggest")) {
                c2 = 0;
            }
            if (c2 != 0) {
                this.e = getArguments().getString(TrafficUtil.KEYWORD);
                this.f = (a) getArguments().getObject(SearchErrorSuggestionPage.BUNDLE_KEY_ONITEMLISTENER);
            } else {
                this.d = (ArrayList) getArguments().get("Areas");
                this.c = getArguments().getBoolean("mFromVoice");
                this.e = this.b != null ? this.b.mWrapper.keywords : "";
            }
        }
        PageBundle arguments = getArguments();
        if (arguments == null) {
            arguments = new PageBundle();
            setArguments(arguments);
        }
        arguments.putString("url", a);
        arguments.putString("jsData", a());
        super.onCreate(context);
    }
}
