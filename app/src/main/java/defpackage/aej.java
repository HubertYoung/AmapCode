package defpackage;

import android.text.TextUtils;
import com.alibaba.analytics.core.config.UTTPKItem;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.searchservice.service.search.SearchSuggServiceImpl$1;
import com.amap.bundle.searchservice.service.search.param.AosInputSuggestionParam;
import com.amap.bundle.searchservice.service.search.param.SuggestionParam;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bl.search.InfoliteResult;
import com.autonavi.bundle.entity.infolite.external.PoiLocationInfo;
import com.autonavi.bundle.searchservice.utils.SearchUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.controller.AppManager;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@BundleInterface(ISearchService.class)
/* renamed from: aej reason: default package */
/* compiled from: SearchService */
public class aej implements ISearchService {
    public final adx b(final InfoliteParam infoliteParam, int i, final aeb<aud> aeb) {
        return c(infoliteParam, i, new aeb<InfoliteResult>() {
            public final /* synthetic */ void callback(Object obj) {
                aud aud;
                InfoliteResult infoliteResult = (InfoliteResult) obj;
                try {
                    if (infoliteResult.PoiType != 1) {
                        a aVar = new a();
                        aVar.a = infoliteParam;
                        aud = aud.a();
                        if (aud.a == null) {
                            aud.a = new PoiLocationInfo();
                        }
                        if (aud.a.POIList == null) {
                            aud.a.POIList = new ArrayList<>();
                        }
                        aud.c.isOnLine = false;
                        List<POI> a2 = aVar.a(infoliteResult.Result.poi_list);
                        a.b(a2);
                        if (a2.size() != 1 || !"190100".equals(a2.get(0).getType())) {
                            aud.b.e = a2.size();
                            aud.b.d.addAll(a2);
                        } else {
                            aud.a.POIList.add(a2.get(0));
                            aud.b.d.add(a2.get(0));
                            aud.b.a.d = 1;
                        }
                    } else {
                        JSONObject jSONObject = new JSONObject(infoliteResult.OriginalJson);
                        new b();
                        aud = b.a(jSONObject);
                    }
                } catch (Exception unused) {
                    aud = null;
                }
                if (aeb != null) {
                    if (aud == null) {
                        error(-1);
                        return;
                    }
                    aeb.callback(aud);
                }
            }

            public final void error(int i) {
                aeb.error(i);
            }
        });
    }

    public final adx a(final InfoliteParam infoliteParam, int i, final aeb<com.autonavi.bundle.entity.infolite.internal.InfoliteResult> aeb) {
        StringBuffer stringBuffer = new StringBuffer();
        if (!TextUtils.isEmpty(bod.a("amap_bundle_nearby"))) {
            stringBuffer.append(UTTPKItem.TYPE_NEARBY);
            stringBuffer.append(":");
            stringBuffer.append(bod.a("amap_bundle_nearby"));
            stringBuffer.append(";");
        }
        if (!TextUtils.isEmpty(bod.a("amap_bundle_scenic_area"))) {
            stringBuffer.append(WidgetType.SCENIC_AREA);
            stringBuffer.append(":");
            stringBuffer.append(bod.a("amap_bundle_scenic_area"));
            stringBuffer.append(";");
        }
        if (!TextUtils.isEmpty(bod.a("amap_bundle_poi"))) {
            stringBuffer.append("poi");
            stringBuffer.append(":");
            stringBuffer.append(bod.a("amap_bundle_poi"));
        }
        infoliteParam.ajxVersion = stringBuffer.toString();
        return c(infoliteParam, i, new aeb<InfoliteResult>() {
            /* JADX WARNING: Removed duplicated region for block: B:90:0x02eb  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final /* synthetic */ void callback(java.lang.Object r14) {
                /*
                    r13 = this;
                    com.autonavi.bl.search.InfoliteResult r14 = (com.autonavi.bl.search.InfoliteResult) r14
                    com.autonavi.bl.search.InfoliteParam r0 = r3
                    if (r14 == 0) goto L_0x047e
                    com.autonavi.bl.search.InfoliteResponse r1 = r14.Result
                    if (r1 != 0) goto L_0x000c
                    goto L_0x047e
                L_0x000c:
                    com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = new com.autonavi.bundle.entity.infolite.internal.InfoliteResult
                    r1.<init>()
                    java.lang.String r2 = r14.OriginalJson
                    r1.originalJson = r2
                    int r2 = r14.PoiType
                    r3 = 1
                    r4 = 0
                    if (r2 != r3) goto L_0x03ff
                    auq r2 = new auq
                    r2.<init>()
                    r1.responseHeader = r2
                    auq r2 = r1.responseHeader
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    java.lang.String r5 = r5.version
                    r2.a = r5
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    int r5 = r5.code
                    r2.c = r5
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    java.lang.String r5 = r5.message
                    r2.d = r5
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    java.lang.String r5 = r5.timestamp
                    r6 = 10
                    long r7 = defpackage.bcv.b(r5, r6)
                    r2.e = r7
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    java.lang.String r5 = r5.result
                    boolean r5 = defpackage.bcv.b(r5)
                    r2.b = r5
                    int r5 = r14.PoiType
                    if (r5 != r3) goto L_0x0052
                    r5 = 1
                    goto L_0x0053
                L_0x0052:
                    r5 = 0
                L_0x0053:
                    r2.f = r5
                    aus r2 = new aus
                    r2.<init>()
                    r1.searchInfo = r2
                    aus r2 = r1.searchInfo
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    java.lang.String r5 = r5.codepoint
                    int r5 = defpackage.bcv.a(r5, r4)
                    r2.u = r5
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    java.lang.String r5 = r5.is_general_search
                    int r5 = defpackage.bcv.a(r5, r4)
                    r2.v = r5
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    java.lang.String r5 = r5.general_flag
                    int r5 = defpackage.bcv.a(r5, r4)
                    r2.w = r5
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    java.lang.String r5 = r5.general_flag
                    boolean r5 = android.text.TextUtils.isEmpty(r5)
                    r5 = r5 ^ r3
                    r2.x = r5
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    int r5 = r5.total
                    r2.p = r5
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    java.lang.String r5 = r5.busline_count
                    int r5 = defpackage.bcv.a(r5, r4)
                    r2.q = r5
                    aus r2 = r1.searchInfo
                    auk r5 = new auk
                    r5.<init>()
                    r2.a = r5
                    com.autonavi.bl.search.InfoliteResponse r2 = r14.Result
                    com.autonavi.bl.search.Lqii r2 = r2.lqii
                    if (r2 == 0) goto L_0x047f
                    aus r2 = r1.searchInfo
                    auk r2 = r2.a
                    com.autonavi.bl.search.InfoliteResponse r5 = r14.Result
                    com.autonavi.bl.search.Lqii r5 = r5.lqii
                    java.lang.String r7 = r5.call_taxi
                    r8 = -1
                    int r7 = defpackage.bcv.a(r7, r8)
                    r2.k = r7
                    int r7 = r5.self_navigation
                    r2.z = r7
                    java.lang.String r7 = r5.preload_next_page
                    int r7 = defpackage.bcv.a(r7, r4)
                    r2.A = r7
                    com.autonavi.bl.search.LqiiRecommendInfo r7 = r5.recommend_info
                    if (r7 == 0) goto L_0x00fd
                    com.autonavi.bl.search.LqiiRecommendInfo r7 = r5.recommend_info
                    java.lang.String r7 = r7.display_msg
                    int r7 = defpackage.bcv.a(r7, r4)
                    r2.C = r7
                    com.autonavi.bl.search.LqiiRecommendInfo r7 = r5.recommend_info
                    com.autonavi.bl.search.LqiiRecommendCity r7 = r7.city
                    if (r7 == 0) goto L_0x00e7
                    com.autonavi.bl.search.LqiiRecommendInfo r7 = r5.recommend_info
                    com.autonavi.bl.search.LqiiRecommendCity r7 = r7.city
                    java.lang.String r7 = r7.current_adcode
                    r2.D = r7
                    com.autonavi.bl.search.LqiiRecommendInfo r7 = r5.recommend_info
                    com.autonavi.bl.search.LqiiRecommendCity r7 = r7.city
                    java.lang.String r7 = r7.current_city
                    r2.E = r7
                L_0x00e7:
                    com.autonavi.bl.search.LqiiRecommendInfo r7 = r5.recommend_info
                    com.autonavi.bl.search.LqiiRecommendMsg r7 = r7.msg
                    if (r7 == 0) goto L_0x00fd
                    com.autonavi.bl.search.LqiiRecommendInfo r7 = r5.recommend_info
                    com.autonavi.bl.search.LqiiRecommendMsg r7 = r7.msg
                    java.lang.String r7 = r7.msg_id
                    r2.F = r7
                    com.autonavi.bl.search.LqiiRecommendInfo r7 = r5.recommend_info
                    com.autonavi.bl.search.LqiiRecommendMsg r7 = r7.msg
                    java.lang.String r7 = r7.msg_content
                    r2.G = r7
                L_0x00fd:
                    java.lang.String r7 = r5.no_result_suggest
                    boolean r7 = android.text.TextUtils.isEmpty(r7)
                    if (r7 != 0) goto L_0x0128
                    java.lang.String r7 = r5.no_result_suggest
                    if (r7 == 0) goto L_0x0128
                    java.lang.String r9 = ""
                    boolean r9 = r7.equals(r9)
                    if (r9 != 0) goto L_0x0128
                    java.lang.String r9 = "+++"
                    int r9 = r7.indexOf(r9)
                    if (r9 <= 0) goto L_0x0122
                    java.lang.String r9 = "\\+{3}"
                    java.lang.String[] r7 = r7.split(r9)
                    r2.B = r7
                    goto L_0x0128
                L_0x0122:
                    java.lang.String[] r9 = new java.lang.String[r3]
                    r9[r4] = r7
                    r2.B = r9
                L_0x0128:
                    java.lang.String r7 = r5.need_history
                    boolean r7 = android.text.TextUtils.isEmpty(r7)
                    if (r7 != 0) goto L_0x0134
                    java.lang.String r7 = r5.need_history
                    r2.S = r7
                L_0x0134:
                    com.autonavi.bl.search.LqiiDirectjump r7 = r5.directjump
                    if (r7 == 0) goto L_0x0149
                    com.autonavi.bl.search.LqiiDirectjump r7 = r5.directjump
                    java.lang.String r7 = r7.url
                    r2.q = r7
                    java.lang.String r7 = r2.q
                    boolean r7 = android.text.TextUtils.isEmpty(r7)
                    if (r7 != 0) goto L_0x0149
                    r2 = 0
                    goto L_0x02e9
                L_0x0149:
                    int r7 = r0.pagenum
                    if (r7 != r3) goto L_0x0155
                    java.lang.String r7 = r5.view_region
                    java.lang.Double[] r7 = defpackage.bcx.a(r7)
                    r2.u = r7
                L_0x0155:
                    int r7 = r0.pagenum
                    if (r7 != r3) goto L_0x0169
                    java.lang.String r7 = r5.expand_range_tip
                    r2.d = r7
                    java.lang.String r7 = r5.change_query_tip
                    r2.f = r7
                    java.lang.String r7 = r5.change_query_type
                    int r7 = defpackage.bcv.a(r7, r4)
                    r2.e = r7
                L_0x0169:
                    java.lang.String r7 = r5.suggestionview
                    int r7 = defpackage.bcv.a(r7, r8)
                    r2.b = r7
                    java.lang.String r7 = r5.pdheatmap
                    int r7 = defpackage.bcv.a(r7, r4)
                    r2.c = r7
                    com.autonavi.bl.search.LqiiCacheDirective r7 = r5.cache_directive
                    if (r7 == 0) goto L_0x01a1
                    com.autonavi.bl.search.LqiiCacheDirective r7 = r5.cache_directive
                    com.autonavi.bl.search.LqiiCacheAll r7 = r7.cache_all
                    if (r7 == 0) goto L_0x01a1
                    com.autonavi.bl.search.LqiiCacheDirective r7 = r5.cache_directive
                    com.autonavi.bl.search.LqiiCacheAll r7 = r7.cache_all
                    java.lang.String r7 = r7.flag
                    int r7 = defpackage.bcv.a(r7, r4)
                    if (r7 != r3) goto L_0x0191
                    r7 = 1
                    goto L_0x0192
                L_0x0191:
                    r7 = 0
                L_0x0192:
                    r2.g = r7
                    com.autonavi.bl.search.LqiiCacheDirective r7 = r5.cache_directive
                    com.autonavi.bl.search.LqiiCacheAll r7 = r7.cache_all
                    java.lang.String r7 = r7.expires
                    int r7 = defpackage.bcv.a(r7, r4)
                    r2.h = r7
                    goto L_0x01a5
                L_0x01a1:
                    r2.g = r4
                    r2.h = r4
                L_0x01a5:
                    java.lang.String r7 = r5.has_recommend
                    int r7 = defpackage.bcv.a(r7, r8)
                    r2.n = r7
                    java.lang.String r7 = "brand"
                    java.lang.String r9 = r5.slayer_type
                    boolean r7 = r7.equalsIgnoreCase(r9)
                    r2.l = r7
                    com.autonavi.bl.search.LqiiResource r7 = r5.resource
                    if (r7 == 0) goto L_0x01e6
                    com.autonavi.bl.search.LqiiResource r7 = r5.resource
                    java.util.ArrayList<com.autonavi.bl.search.LqiiBrandUrl> r7 = r7.brand_url
                    if (r7 == 0) goto L_0x01e6
                    java.util.HashMap r7 = new java.util.HashMap
                    r7.<init>()
                    r2.m = r7
                    com.autonavi.bl.search.LqiiResource r7 = r5.resource
                    java.util.ArrayList<com.autonavi.bl.search.LqiiBrandUrl> r7 = r7.brand_url
                    java.util.Iterator r7 = r7.iterator()
                L_0x01d0:
                    boolean r9 = r7.hasNext()
                    if (r9 == 0) goto L_0x01e6
                    java.lang.Object r9 = r7.next()
                    com.autonavi.bl.search.LqiiBrandUrl r9 = (com.autonavi.bl.search.LqiiBrandUrl) r9
                    java.util.HashMap<java.lang.String, java.lang.String> r10 = r2.m
                    java.lang.String r11 = r9.brand
                    java.lang.String r9 = r9.url
                    r10.put(r11, r9)
                    goto L_0x01d0
                L_0x01e6:
                    java.lang.String r7 = r5.render_name_flag
                    int r7 = defpackage.bcv.a(r7, r8)
                    r2.o = r7
                    java.lang.String r7 = r5.querytype
                    int r7 = defpackage.bcv.a(r7, r8)
                    r2.a = r7
                    java.lang.String r7 = r5.is_current_city
                    int r7 = defpackage.bcv.a(r7, r8)
                    r2.p = r7
                    java.lang.String r7 = r5.is_view_city
                    int r7 = defpackage.bcv.a(r7, r8)
                    r2.i = r7
                    java.lang.String r7 = r5.target_view_city
                    r2.j = r7
                    java.lang.String r7 = r5.rich_info_flag
                    boolean r7 = defpackage.bcv.b(r7)
                    r2.V = r7
                    com.autonavi.bl.search.LqiiInterior r7 = r5.interior
                    if (r7 == 0) goto L_0x0230
                    com.autonavi.bl.search.LqiiInterior r7 = r5.interior
                    java.lang.String r7 = r7.interior_noresult
                    int r7 = defpackage.bcv.a(r7, r4)
                    r2.H = r7
                    com.autonavi.bl.search.LqiiInterior r7 = r5.interior
                    java.lang.String r7 = r7.need_expand
                    int r7 = defpackage.bcv.a(r7, r4)
                    r2.I = r7
                    com.autonavi.bl.search.LqiiInterior r7 = r5.interior
                    java.lang.String r7 = r7.result_type
                    r2.J = r7
                L_0x0230:
                    com.autonavi.bl.search.LqiiTesecai r7 = r5.tesecai
                    if (r7 == 0) goto L_0x024a
                    com.autonavi.bl.search.LqiiTesecai r7 = r5.tesecai
                    java.lang.String r7 = r7.type
                    r2.r = r7
                    com.autonavi.bl.search.LqiiTesecai r7 = r5.tesecai
                    java.lang.String r7 = r7.valid
                    int r7 = defpackage.bcv.a(r7, r4)
                    r2.s = r7
                    com.autonavi.bl.search.LqiiTesecai r7 = r5.tesecai
                    java.lang.String r7 = r7.query
                    r2.t = r7
                L_0x024a:
                    com.autonavi.bl.search.LqiiSuggesttips r7 = r5.suggesttips
                    if (r7 == 0) goto L_0x0264
                    com.autonavi.bl.search.LqiiSuggesttips r7 = r5.suggesttips
                    java.lang.String r7 = r7.city
                    r2.v = r7
                    com.autonavi.bl.search.LqiiSuggesttips r7 = r5.suggesttips
                    java.lang.String r7 = r7.adcode
                    long r6 = defpackage.bcv.b(r7, r6)
                    r2.w = r6
                    com.autonavi.bl.search.LqiiSuggesttips r6 = r5.suggesttips
                    java.lang.String r6 = r6.query
                    r2.x = r6
                L_0x0264:
                    java.lang.String r6 = r5.suggestcontent
                    r2.y = r6
                    com.autonavi.bl.search.LqiiQueryIntent r6 = r5.query_intent
                    if (r6 == 0) goto L_0x0282
                    com.autonavi.bl.search.LqiiQueryIntent r6 = r5.query_intent
                    java.lang.String r6 = r6.type
                    int r6 = defpackage.bcv.a(r6, r4)
                    r2.N = r6
                    com.autonavi.bl.search.LqiiQueryIntent r6 = r5.query_intent
                    java.lang.String r6 = r6.cate
                    r2.O = r6
                    com.autonavi.bl.search.LqiiQueryIntent r6 = r5.query_intent
                    java.lang.String r6 = r6.distribution
                    r2.P = r6
                L_0x0282:
                    java.lang.String r6 = r5.filterbox_fillback
                    r2.Q = r6
                    com.autonavi.bl.search.LqiiZoom r6 = r5.zoom
                    if (r6 == 0) goto L_0x02d4
                    com.autonavi.bl.search.LqiiZoom r6 = r5.zoom
                    java.lang.String r6 = r6.center
                    boolean r6 = android.text.TextUtils.isEmpty(r6)
                    if (r6 != 0) goto L_0x02d4
                    com.autonavi.bl.search.LqiiZoom r6 = r5.zoom
                    java.lang.String r6 = r6.center
                    java.lang.String r7 = ","
                    java.lang.String[] r6 = r6.split(r7)
                    int r7 = r6.length
                    r8 = 2
                    if (r7 != r8) goto L_0x02d4
                    r7 = r6[r4]
                    if (r7 == 0) goto L_0x02d4
                    r7 = r6[r3]
                    if (r7 == 0) goto L_0x02d4
                    com.autonavi.common.model.GeoPoint r7 = new com.autonavi.common.model.GeoPoint
                    r8 = r6[r4]
                    r9 = 0
                    double r11 = defpackage.bcv.a(r8, r9)
                    r6 = r6[r3]
                    double r8 = defpackage.bcv.a(r6, r9)
                    r7.<init>(r11, r8)
                    auv r6 = new auv
                    r6.<init>()
                    r2.M = r6
                    auv r6 = r2.M
                    r6.a = r7
                    auv r6 = r2.M
                    com.autonavi.bl.search.LqiiZoom r7 = r5.zoom
                    java.lang.String r7 = r7.level
                    int r7 = defpackage.bcv.a(r7, r4)
                    r6.b = r7
                L_0x02d4:
                    java.lang.String r6 = r5.gsid
                    boolean r6 = android.text.TextUtils.isEmpty(r6)
                    if (r6 != 0) goto L_0x02e0
                    java.lang.String r6 = r5.gsid
                    r2.R = r6
                L_0x02e0:
                    java.lang.String r6 = r5.show_hand_drawing
                    r2.T = r6
                    java.lang.String r5 = r5.smartspot
                    r2.U = r5
                    r2 = 1
                L_0x02e9:
                    if (r2 == 0) goto L_0x047f
                    aus r2 = r1.searchInfo
                    defpackage.bcu.a(r14, r2, r0)
                    aus r2 = r1.searchInfo
                    defpackage.bcu.b(r14, r2, r0)
                    aus r2 = r1.searchInfo
                    defpackage.bcu.c(r14, r2, r0)
                    aus r2 = r1.searchInfo
                    auk r2 = r2.a
                    int r2 = r2.a
                    if (r2 == r3) goto L_0x03c5
                    r3 = 4
                    if (r2 == r3) goto L_0x030c
                    com.autonavi.bl.search.InfoliteResponse r14 = r14.Result
                    defpackage.bcu.a(r14, r1, r0)
                    goto L_0x047f
                L_0x030c:
                    com.autonavi.bl.search.InfoliteResponse r14 = r14.Result
                    aur r2 = new aur
                    r2.<init>()
                    r1.routingInfo = r2
                    com.autonavi.bl.search.Routing r14 = r14.routing
                    aus r2 = r1.searchInfo
                    auk r2 = r2.a
                    aur r3 = r1.routingInfo
                    if (r14 == 0) goto L_0x047f
                    com.autonavi.bl.search.RoutingInfo r5 = r14.start
                    if (r5 == 0) goto L_0x0371
                    java.util.ArrayList r5 = new java.util.ArrayList
                    r5.<init>()
                    r3.c = r5
                    java.util.ArrayList r5 = new java.util.ArrayList
                    r5.<init>()
                    r3.e = r5
                    java.util.ArrayList r5 = new java.util.ArrayList
                    r5.<init>()
                    r3.d = r5
                    com.autonavi.bl.search.RoutingInfo r5 = r14.start
                    com.autonavi.bl.search.Locres r5 = r5.locres
                    if (r5 == 0) goto L_0x0353
                    com.autonavi.bl.search.RoutingInfo r5 = r14.start
                    com.autonavi.bl.search.Locres r5 = r5.locres
                    java.lang.String r5 = r5.loctype
                    int r5 = defpackage.bcv.a(r5, r4)
                    r3.b = r5
                    com.autonavi.bl.search.RoutingInfo r5 = r14.start
                    com.autonavi.bl.search.Locres r5 = r5.locres
                    java.util.ArrayList<com.autonavi.common.model.POI> r6 = r3.c
                    defpackage.bcu.a(r5, r6)
                L_0x0353:
                    com.autonavi.bl.search.RoutingInfo r5 = r14.start
                    com.autonavi.bl.search.RoutingKeywordres r5 = r5.keywordres
                    if (r5 == 0) goto L_0x0371
                    com.autonavi.bl.search.RoutingInfo r5 = r14.start
                    com.autonavi.bl.search.RoutingKeywordres r5 = r5.keywordres
                    com.autonavi.bl.search.Suggestion r5 = r5.suggestion
                    java.util.ArrayList<java.lang.String> r6 = r3.d
                    java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r7 = r3.e
                    defpackage.bcu.a(r5, r6, r7)
                    com.autonavi.bl.search.RoutingInfo r5 = r14.start
                    com.autonavi.bl.search.RoutingKeywordres r5 = r5.keywordres
                    java.util.ArrayList<com.autonavi.bl.search.PoilistPoiInfo> r5 = r5.poi_list
                    java.util.ArrayList<com.autonavi.common.model.POI> r6 = r3.c
                    defpackage.bcu.a(r5, r2, r6, r0, r1)
                L_0x0371:
                    com.autonavi.bl.search.RoutingInfo r5 = r14.end
                    if (r5 == 0) goto L_0x047f
                    java.util.ArrayList r5 = new java.util.ArrayList
                    r5.<init>()
                    r3.h = r5
                    java.util.ArrayList r5 = new java.util.ArrayList
                    r5.<init>()
                    r3.j = r5
                    java.util.ArrayList r5 = new java.util.ArrayList
                    r5.<init>()
                    r3.i = r5
                    com.autonavi.bl.search.RoutingInfo r5 = r14.end
                    com.autonavi.bl.search.Locres r5 = r5.locres
                    if (r5 == 0) goto L_0x03a5
                    com.autonavi.bl.search.RoutingInfo r5 = r14.end
                    com.autonavi.bl.search.Locres r5 = r5.locres
                    java.lang.String r5 = r5.loctype
                    int r4 = defpackage.bcv.a(r5, r4)
                    r3.f = r4
                    com.autonavi.bl.search.RoutingInfo r4 = r14.end
                    com.autonavi.bl.search.Locres r4 = r4.locres
                    java.util.ArrayList<com.autonavi.common.model.POI> r5 = r3.h
                    defpackage.bcu.a(r4, r5)
                L_0x03a5:
                    com.autonavi.bl.search.RoutingInfo r4 = r14.end
                    com.autonavi.bl.search.RoutingKeywordres r4 = r4.keywordres
                    if (r4 == 0) goto L_0x047f
                    com.autonavi.bl.search.RoutingInfo r4 = r14.end
                    com.autonavi.bl.search.RoutingKeywordres r4 = r4.keywordres
                    com.autonavi.bl.search.Suggestion r4 = r4.suggestion
                    java.util.ArrayList<java.lang.String> r5 = r3.i
                    java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r6 = r3.j
                    defpackage.bcu.a(r4, r5, r6)
                    com.autonavi.bl.search.RoutingInfo r14 = r14.end
                    com.autonavi.bl.search.RoutingKeywordres r14 = r14.keywordres
                    java.util.ArrayList<com.autonavi.bl.search.PoilistPoiInfo> r14 = r14.poi_list
                    java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.h
                    defpackage.bcu.a(r14, r2, r3, r0, r1)
                    goto L_0x047f
                L_0x03c5:
                    com.autonavi.bl.search.InfoliteResponse r14 = r14.Result
                    java.util.ArrayList<com.autonavi.bl.search.PoilistPoiInfo> r2 = r14.poi_list
                    boolean r2 = defpackage.bcu.a(r2)
                    if (r2 == 0) goto L_0x03d4
                    defpackage.bcu.a(r14, r1, r0)
                    goto L_0x047f
                L_0x03d4:
                    auj r0 = new auj
                    r0.<init>()
                    r1.locationInfo = r0
                    com.autonavi.bl.search.Locres r14 = r14.locres
                    auj r0 = r1.locationInfo
                    if (r14 == 0) goto L_0x047f
                    java.lang.String r2 = r14.loctype
                    int r2 = defpackage.bcv.a(r2, r4)
                    r0.a = r2
                    java.lang.String r2 = r14.total
                    int r2 = defpackage.bcv.a(r2, r4)
                    r0.b = r2
                    java.util.ArrayList r2 = new java.util.ArrayList
                    r2.<init>()
                    r0.c = r2
                    java.util.ArrayList<com.autonavi.common.model.POI> r0 = r0.c
                    defpackage.bcu.a(r14, r0)
                    goto L_0x047f
                L_0x03ff:
                    auq r2 = new auq
                    r2.<init>()
                    r1.responseHeader = r2
                    aus r2 = new aus
                    r2.<init>()
                    r1.searchInfo = r2
                    aus r2 = r1.searchInfo
                    auk r5 = new auk
                    r5.<init>()
                    r2.a = r5
                    aus r2 = r1.searchInfo
                    java.util.ArrayList r5 = new java.util.ArrayList
                    r5.<init>()
                    r2.l = r5
                    auj r2 = new auj
                    r2.<init>()
                    r1.locationInfo = r2
                    auj r2 = r1.locationInfo
                    java.util.ArrayList r5 = new java.util.ArrayList
                    r5.<init>()
                    r2.c = r5
                    auq r2 = r1.responseHeader
                    r2.f = r4
                    com.autonavi.bl.search.InfoliteResponse r2 = r14.Result
                    if (r2 == 0) goto L_0x0442
                    com.autonavi.bl.search.InfoliteResponse r14 = r14.Result
                    java.util.ArrayList<com.autonavi.bl.search.PoilistPoiInfo> r14 = r14.poi_list
                    aus r2 = r1.searchInfo
                    java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.l
                    defpackage.bcu.a(r14, r2, r0)
                L_0x0442:
                    aus r14 = r1.searchInfo
                    java.util.ArrayList<com.autonavi.common.model.POI> r14 = r14.l
                    if (r14 == 0) goto L_0x047f
                    aus r14 = r1.searchInfo
                    java.util.ArrayList<com.autonavi.common.model.POI> r14 = r14.l
                    int r14 = r14.size()
                    if (r14 != r3) goto L_0x047f
                    java.lang.String r14 = "190100"
                    aus r0 = r1.searchInfo
                    java.util.ArrayList<com.autonavi.common.model.POI> r0 = r0.l
                    java.lang.Object r0 = r0.get(r4)
                    com.autonavi.common.model.POI r0 = (com.autonavi.common.model.POI) r0
                    java.lang.String r0 = r0.getType()
                    boolean r14 = r14.equals(r0)
                    if (r14 == 0) goto L_0x047f
                    auj r14 = r1.locationInfo
                    java.util.ArrayList<com.autonavi.common.model.POI> r14 = r14.c
                    aus r0 = r1.searchInfo
                    java.util.ArrayList<com.autonavi.common.model.POI> r0 = r0.l
                    java.lang.Object r0 = r0.get(r4)
                    r14.add(r0)
                    aus r14 = r1.searchInfo
                    auk r14 = r14.a
                    r14.a = r3
                    goto L_0x047f
                L_0x047e:
                    r1 = 0
                L_0x047f:
                    com.autonavi.bl.search.InfoliteParam r14 = r3
                    r1.mWrapper = r14
                    aeb r14 = r5
                    r14.callback(r1)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: defpackage.aej.AnonymousClass1.callback(java.lang.Object):void");
            }

            public final void error(int i) {
                aeb.error(i);
            }
        });
    }

    private static adx c(InfoliteParam infoliteParam, int i, aeb<InfoliteResult> aeb) {
        return b.a.a(infoliteParam, i, aeb);
    }

    public final adx a(SuggestionParam suggestionParam, int i, aeb<aux> aeb) {
        SuggestionParam suggestionParam2 = suggestionParam;
        int i2 = i;
        aek aek = a.a;
        if (aek.b != null) {
            adx remove = aek.c.remove(aek.b);
            if (remove != null) {
                remove.a();
            }
        }
        AnonymousClass1 r7 = new adx() {
            boolean a = false;

            public final void a() {
                try {
                    this.a = true;
                    if (aek.this.b != null) {
                        yq.a();
                        yq.a((AosRequest) aek.this.b);
                    }
                } finally {
                    aek.this.c.remove(aek.this.b);
                }
            }

            public final boolean b() {
                return this.a;
            }
        };
        if (aey.c()) {
            aek.b = aek.a;
            aek.a(suggestionParam2, i2, aeb, r7);
        } else {
            aeb<aux> aeb2 = aeb;
            int latestPositionAdCode = SearchUtils.getLatestPositionAdCode();
            String valueOf = String.valueOf(latestPositionAdCode);
            if (suggestionParam.getAdcode() <= 0) {
                suggestionParam2.adcode = (long) latestPositionAdCode;
            }
            AosInputSuggestionParam aosInputSuggestionParam = new AosInputSuggestionParam(suggestionParam2.keyWord, String.valueOf(suggestionParam2.adcode), AppManager.getInstance().getUserLocInfo(), valueOf, suggestionParam2.category, suggestionParam2.suggestType, i2 == 1, DoNotUseTool.getPixel20Bound(), suggestionParam.getCenter() != null ? suggestionParam.getCenter().x : 0, suggestionParam.getCenter() != null ? suggestionParam.getCenter().y : 0);
            SuperId.getInstance().reset();
            SuperId.getInstance().setBit2("14");
            if (!aosInputSuggestionParam.datatype.equals("busline")) {
                aosInputSuggestionParam.superid = SuperId.getInstance().getScenceId();
            }
            aek.b = aax.b(aosInputSuggestionParam);
            aek.b.addReqParam("version", "2.13");
            yq.a();
            AosPostRequest aosPostRequest = aek.b;
            SearchSuggServiceImpl$1 searchSuggServiceImpl$1 = new SearchSuggServiceImpl$1(aek, aeb2, suggestionParam2, i2, r7);
            yq.a((AosRequest) aosPostRequest, (AosResponseCallback<T>) searchSuggServiceImpl$1);
        }
        aek.c.put(aek.b, r7);
        return r7;
    }
}
