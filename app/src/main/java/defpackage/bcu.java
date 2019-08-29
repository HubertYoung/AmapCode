package defpackage;

import android.graphics.Point;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.bundle.datamodel.poi.CpData;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.ae.search.model.GADAREAEXTRAINFO;
import com.autonavi.bl.search.Bus;
import com.autonavi.bl.search.Busline;
import com.autonavi.bl.search.BuslineStation;
import com.autonavi.bl.search.BuslineStationSubway;
import com.autonavi.bl.search.Classify;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bl.search.InfoliteResponse;
import com.autonavi.bl.search.Locres;
import com.autonavi.bl.search.LocresPoiInfo;
import com.autonavi.bl.search.Lqii;
import com.autonavi.bl.search.PoilistCorrelation;
import com.autonavi.bl.search.PoilistDomain;
import com.autonavi.bl.search.PoilistParkinfoInoutInfo;
import com.autonavi.bl.search.PoilistPassageway;
import com.autonavi.bl.search.PoilistPoiInfo;
import com.autonavi.bl.search.PoilistSugInfo;
import com.autonavi.bl.search.Scenefilter;
import com.autonavi.bl.search.Suggestion;
import com.autonavi.bl.search.SuggestionRegionInfo;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.common.searchpoi.SearchRecommendPoi;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.bundle.entity.infolite.internal.Condition;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.infolite.internal.template.PoiSupperAddressTemplate;
import com.autonavi.bundle.feedback.ajx.ModuleFeedBack;
import com.autonavi.bundle.searchservice.utils.PoiArrayTemplate;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bcu reason: default package */
/* compiled from: CoInfoliteConvertor */
public final class bcu {
    public static void a(InfoliteResponse infoliteResponse, InfoliteResult infoliteResult, InfoliteParam infoliteParam) {
        infoliteResult.searchInfo.l = new ArrayList<>();
        infoliteResult.searchInfo.g = new ArrayList<>();
        infoliteResult.searchInfo.f = new ArrayList<>();
        infoliteResult.searchInfo.r = new ArrayList<>();
        infoliteResult.searchInfo.s = new ArrayList<>();
        a(infoliteResponse.suggestion, infoliteResult.searchInfo.f, infoliteResult.searchInfo.g);
        a(infoliteResponse.poi_list, infoliteResult.searchInfo.a, infoliteResult.searchInfo.l, infoliteParam, infoliteResult);
        b(infoliteResponse.poi_list, infoliteResult.searchInfo);
        a(infoliteResponse.poi_list, infoliteResult.searchInfo);
        b(infoliteResponse.busline_list, infoliteResult.searchInfo.r);
        a(infoliteResponse.bus_list, infoliteResult.searchInfo.s);
    }

    public static boolean a(ArrayList<PoilistPoiInfo> arrayList) {
        if (arrayList == null || arrayList.size() == 0) {
            return false;
        }
        Iterator<PoilistPoiInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            if ("citycard".equals(it.next().item_type)) {
                return true;
            }
        }
        return false;
    }

    private static void a(ArrayList<Bus> arrayList, ArrayList<POI> arrayList2) {
        if (arrayList != null && arrayList.size() != 0) {
            Iterator<Bus> it = arrayList.iterator();
            while (it.hasNext()) {
                Bus next = it.next();
                POI createPOI = POIFactory.createPOI();
                createPOI.setId(next.id);
                createPOI.setName(next.name);
                createPOI.setAddr(next.address);
                createPOI.getPoint().setLonLat(bcv.a(next.x, 0.0d), bcv.a(next.y, 0.0d));
                createPOI.setAdCode(next.areacode);
                createPOI.setEndPoiExtension(next.end_poi_extension);
                createPOI.setTransparent(next.transparent);
                createPOI.getPoiExtra().put("businfo_lineids", next.businfo_lineids);
                if (!TextUtils.isEmpty(next.businfo_line_keys)) {
                    String[] split = next.businfo_line_keys.split(";|\\|");
                    StringBuilder sb = new StringBuilder();
                    HashMap hashMap = new HashMap();
                    for (int i = 0; i < split.length; i++) {
                        if (!hashMap.containsKey(split[i])) {
                            hashMap.put(split[i], split[i]);
                            if (i != 0) {
                                sb.append("/");
                            }
                            sb.append(split[i]);
                        }
                    }
                    hashMap.clear();
                    createPOI.getPoiExtra().put("station_lines", sb.toString());
                }
                arrayList2.add(createPOI);
            }
        }
    }

    private static void b(ArrayList<Busline> arrayList, ArrayList<aue> arrayList2) {
        if (arrayList != null && arrayList.size() != 0) {
            Iterator<Busline> it = arrayList.iterator();
            while (it.hasNext()) {
                Busline next = it.next();
                aue aue = new aue();
                aue.a = next.id;
                aue.n = bcv.a(next.type, 0);
                aue.b = next.name;
                aue.i = next.front_name;
                aue.j = next.terminal_name;
                aue.k = bcv.a(next.start_time, -1);
                aue.l = bcv.a(next.end_time, -1);
                aue.h = next.key_name;
                aue.c = next.areacode;
                aue.o = bcv.a(next.status, 1);
                aue.A = next.description;
                aue.z = next.interval;
                aue.d = next.basic_price;
                aue.e = next.total_price;
                aue.f = next.basic_price_air;
                aue.g = next.total_price_air;
                aue.m = (int) bcv.a(next.length, 0.0d);
                aue.B = bcv.a(next.is_realtime, 0) == 1;
                aue.C = next.irregular_time;
                if (next.emergency != null) {
                    aue.E = new a();
                    aue.E.a = bcv.a(next.emergency.state, 0);
                    aue.E.b = next.description;
                }
                String str = next.xs;
                String str2 = next.ys;
                if (str != null && !str.trim().equals("") && str2 != null && !str2.trim().equals("")) {
                    String[] split = str.split(",");
                    String[] split2 = str2.split(",");
                    int length = split.length;
                    aue.p = new int[split.length];
                    aue.q = new int[split.length];
                    for (int i = 0; i < length; i++) {
                        if (i < split2.length) {
                            Point a = cfg.a(bcv.a(split2[i], 0.0d), bcv.a(split[i], 0.0d));
                            aue.p[i] = a.x;
                            aue.q[i] = a.y;
                        }
                    }
                }
                if (!(next.stations == null || next.stations.size() == 0)) {
                    int size = next.stations.size();
                    aue.r = new String[size];
                    aue.s = new int[size];
                    aue.t = new int[size];
                    aue.v = new String[size];
                    aue.u = new long[size];
                    aue.w = new int[size];
                    aue.x = new String[size];
                    aue.y = new String[size];
                    for (int i2 = 0; i2 < size; i2++) {
                        BuslineStation buslineStation = next.stations.get(i2);
                        aue.r[i2] = buslineStation.name;
                        aue.c = buslineStation.code;
                        aue.w[i2] = bcv.a(buslineStation.status, 1);
                        aue.x[i2] = buslineStation.poiid1;
                        aue.y[i2] = buslineStation.poiid2;
                        aue.v[i2] = buslineStation.station_id;
                        aue.u[i2] = bcv.b(buslineStation.code, 10);
                        String[] split3 = buslineStation.xy_coords.split(";");
                        if (split3.length == 2) {
                            Point a2 = cfg.a(bcv.a(split3[1], 0.0d), bcv.a(split3[0], 0.0d));
                            aue.s[i2] = a2.x;
                            aue.t[i2] = a2.y;
                        }
                        if (buslineStation.subways != null && buslineStation.subways.size() > 0) {
                            aue.D = new auf();
                            aue.D.a = new ArrayList<>();
                            Iterator<BuslineStationSubway> it2 = buslineStation.subways.iterator();
                            while (it2.hasNext()) {
                                BuslineStationSubway next2 = it2.next();
                                aug aug = new aug();
                                aug.d = buslineStation.station_id;
                                aug.c = buslineStation.name;
                                aug.b = next2.line_key;
                                StringBuilder sb = new StringBuilder(MetaRecord.LOG_SEPARATOR);
                                sb.append(next2.color);
                                aug.a = sb.toString();
                                aue.D.a.add(aug);
                            }
                        }
                    }
                }
                arrayList2.add(aue);
            }
        }
    }

    private static void a(ArrayList<PoilistPoiInfo> arrayList, aus aus) {
        if (arrayList != null && arrayList.size() != 0) {
            PoilistPoiInfo poilistPoiInfo = arrayList.get(0);
            try {
                if (!TextUtils.isEmpty(poilistPoiInfo.module_names_json)) {
                    aus.y = new JSONArray(poilistPoiInfo.module_names_json);
                }
                if (poilistPoiInfo.interface_results_json != null) {
                    String str = new String(poilistPoiInfo.interface_results_json);
                    if (!TextUtils.isEmpty(str)) {
                        aus.z = new JSONObject(str);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static void b(ArrayList<PoilistPoiInfo> arrayList, aus aus) {
        if (arrayList != null && arrayList.size() != 0) {
            aus.h = new ArrayList<>();
            aus.j = new ArrayList<>();
            aus.i = new ArrayList<>();
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < arrayList.size(); i++) {
                PoilistPoiInfo poilistPoiInfo = arrayList.get(i);
                String str = poilistPoiInfo.item_type;
                if ("search_result_slice".equals(str)) {
                    aus.t = i;
                } else {
                    int a = bcv.a(poilistPoiInfo.template_id, 0);
                    if (ModuleFeedBack.RECOMMEND.equalsIgnoreCase(str)) {
                        aup a2 = a(poilistPoiInfo);
                        a2.b = i;
                        aus.h.add(a2);
                        a(jSONArray, poilistPoiInfo, i);
                    } else if (bcy.a(str, a)) {
                        aun aun = new aun();
                        aun.b = poilistPoiInfo.title;
                        aun.c = poilistPoiInfo.subtitle;
                        aun.d = poilistPoiInfo.pic;
                        aun.e = poilistPoiInfo.schema;
                        aun.g = poilistPoiInfo.gsid;
                        aun.a = bcv.a(poilistPoiInfo.template_id, 0);
                        aun.f = i;
                        aus.j.add(aun);
                        a(jSONArray, poilistPoiInfo, i);
                    } else if (bcy.b(str, a)) {
                        aul aul = new aul();
                        aul.b = poilistPoiInfo.title;
                        aul.c = poilistPoiInfo.subtitle;
                        aul.d = poilistPoiInfo.pic;
                        aul.e = poilistPoiInfo.schema;
                        aul.g = poilistPoiInfo.gsid;
                        aul.a = bcv.a(poilistPoiInfo.template_id, 0);
                        aul.f = i;
                        aus.i.add(aul);
                        a(jSONArray, poilistPoiInfo, i);
                    }
                }
            }
            aus.k = jSONArray.toString();
        }
    }

    private static void a(JSONArray jSONArray, PoilistPoiInfo poilistPoiInfo, int i) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(RouteItem.ITEM_TAG, new JSONObject(com.alibaba.fastjson.JSONObject.toJSONString(poilistPoiInfo)));
            jSONObject.put("position", i);
            jSONArray.put(jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static aup a(PoilistPoiInfo poilistPoiInfo) {
        aup aup = new aup();
        aup.a = poilistPoiInfo.reason;
        aup.d = bcv.a(poilistPoiInfo.row, 0);
        aup.c = bcv.a(poilistPoiInfo.col, 0);
        aup.e = poilistPoiInfo.gsid;
        aup.f = new ArrayList();
        if (poilistPoiInfo.domain_list != null && poilistPoiInfo.domain_list.size() > 0) {
            Iterator<PoilistDomain> it = poilistPoiInfo.domain_list.iterator();
            while (it.hasNext()) {
                PoilistDomain next = it.next();
                auo auo = new auo();
                auo.b = next.value;
                auo.a = "query".equals(next.type) ? 1 : -1;
                auo.c = next.schema;
                aup.f.add(auo);
            }
        }
        return aup;
    }

    public static void a(Locres locres, ArrayList<POI> arrayList) {
        if (locres.poi_list != null && locres.poi_list.size() > 0) {
            Iterator<LocresPoiInfo> it = locres.poi_list.iterator();
            while (it.hasNext()) {
                LocresPoiInfo next = it.next();
                SearchPoi searchPoi = (SearchPoi) POIFactory.createPOI(SearchPoi.class);
                double a = bcv.a(next.longitude, -1.0d);
                double a2 = bcv.a(next.latitude, -1.0d);
                if (!(a == -1.0d || a2 == -1.0d)) {
                    searchPoi.getPoint().setLonLat(a, a2);
                    searchPoi.setAddr(next.address);
                    searchPoi.setName(next.name);
                    searchPoi.setAdCode(next.adcode);
                    if (!TextUtils.isEmpty(next.coords)) {
                        searchPoi.setRegions(bcx.b(next.coords));
                    }
                    arrayList.add(searchPoi);
                }
            }
        }
    }

    /* JADX WARNING: type inference failed for: r13v5, types: [java.lang.Object, com.autonavi.minimap.search.templete.type.PoiLayoutTemplate] */
    /* JADX WARNING: type inference failed for: r13v22 */
    /* JADX WARNING: type inference failed for: r12v10, types: [com.autonavi.bundle.searchservice.utils.PoiArrayTemplate] */
    /* JADX WARNING: type inference failed for: r13v23 */
    /* JADX WARNING: type inference failed for: r12v11 */
    /* JADX WARNING: type inference failed for: r13v33 */
    /* JADX WARNING: type inference failed for: r12v12, types: [com.autonavi.bundle.entity.infolite.internal.template.PoiSupperAddressTemplate] */
    /* JADX WARNING: type inference failed for: r12v19 */
    /* JADX WARNING: type inference failed for: r13v41 */
    /* JADX WARNING: type inference failed for: r13v42 */
    /* JADX WARNING: type inference failed for: r13v43 */
    /* JADX WARNING: type inference failed for: r13v44 */
    /* JADX WARNING: type inference failed for: r13v45 */
    /* JADX WARNING: type inference failed for: r13v46 */
    /* JADX WARNING: type inference failed for: r13v47 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x0562  */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x05c0  */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.util.ArrayList<com.autonavi.bl.search.PoilistPoiInfo> r16, defpackage.auk r17, java.util.ArrayList<com.autonavi.common.model.POI> r18, com.autonavi.bl.search.InfoliteParam r19, com.autonavi.bundle.entity.infolite.internal.InfoliteResult r20) {
        /*
            r1 = r17
            if (r16 == 0) goto L_0x0620
            int r3 = r16.size()
            if (r3 != 0) goto L_0x000c
            goto L_0x0620
        L_0x000c:
            java.util.Iterator r2 = r16.iterator()
        L_0x0010:
            boolean r3 = r2.hasNext()
            r4 = 1
            if (r3 == 0) goto L_0x05fb
            java.lang.Object r3 = r2.next()
            com.autonavi.bl.search.PoilistPoiInfo r3 = (com.autonavi.bl.search.PoilistPoiInfo) r3
            java.lang.String r5 = r3.item_type
            boolean r5 = defpackage.bcy.a(r5)
            if (r5 == 0) goto L_0x05f3
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r5 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r5 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r5)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r5 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r5
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r6 = new com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData
            r6.<init>()
            r5.setPoiChildrenInfo(r6)
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r6 = new com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData
            r6.<init>()
            r5.setIndoorPoiInfo(r6)
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r6 = new com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData
            r6.<init>()
            r5.setIDynamicRenderInfo(r6)
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.AutoNaviPoiData r6 = new com.autonavi.minimap.search.model.searchpoi.searchpoitype.AutoNaviPoiData
            r6.<init>()
            r5.setPoiAutoNaviInfo(r6)
            java.lang.String r6 = r3.id
            r5.setId(r6)
            java.lang.String r6 = r3.industry
            r5.setIndustry(r6)
            java.lang.String r6 = r3.heat_map_flag
            r7 = 0
            int r6 = defpackage.bcv.a(r6, r7)
            r5.setHeatMapFlag(r6)
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r6 = r5.getIDynamicRenderInfo()
            java.lang.String r8 = r3.render_rank
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x007f
            java.lang.String r8 = r3.minizoom
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x007f
            java.lang.String r8 = r3.anchor
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            if (r8 != 0) goto L_0x007f
            r8 = 1
            goto L_0x0080
        L_0x007f:
            r8 = 0
        L_0x0080:
            r6.bFlag = r8
            java.lang.String r8 = r3.render_style_main
            int r8 = defpackage.bcv.a(r8, r7)
            r6.mainStyle = r8
            java.lang.String r8 = r3.render_style_sub
            int r8 = defpackage.bcv.a(r8, r7)
            r6.subStyle = r8
            java.lang.String r8 = r3.render_rank
            float r8 = defpackage.bcv.a(r8)
            r6.fRank = r8
            java.lang.String r8 = r3.minizoom
            int r8 = defpackage.bcv.a(r8, r7)
            r6.minizoom = r8
            java.lang.String r8 = r3.anchor
            boolean r8 = android.text.TextUtils.isEmpty(r8)
            r9 = 2
            if (r8 != 0) goto L_0x00ce
            java.lang.String r8 = r3.anchor
            java.lang.String r10 = "0x"
            boolean r8 = r8.startsWith(r10)
            if (r8 != 0) goto L_0x00bf
            java.lang.String r8 = r3.anchor
            java.lang.String r10 = "0X"
            boolean r8 = r8.startsWith(r10)
            if (r8 == 0) goto L_0x00ce
        L_0x00bf:
            java.lang.String r8 = r3.anchor
            java.lang.String r8 = r8.substring(r9)
            r10 = 16
            long r10 = defpackage.bcv.b(r8, r10)
            int r8 = (int) r10
            r6.anchor = r8
        L_0x00ce:
            java.util.ArrayList<com.autonavi.bl.search.PoilistDomain> r6 = r3.domain_list
            r8 = 0
            if (r6 == 0) goto L_0x02c9
            java.util.ArrayList<com.autonavi.bl.search.PoilistDomain> r6 = r3.domain_list
            int r6 = r6.size()
            if (r6 != 0) goto L_0x00dd
            goto L_0x02c9
        L_0x00dd:
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            java.util.HashMap r10 = new java.util.HashMap
            r10.<init>()
            java.util.ArrayList<com.autonavi.bl.search.PoilistDomain> r11 = r3.domain_list
            java.util.Iterator r11 = r11.iterator()
        L_0x00ed:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x02c0
            java.lang.Object r12 = r11.next()
            com.autonavi.bl.search.PoilistDomain r12 = (com.autonavi.bl.search.PoilistDomain) r12
            java.lang.String r13 = "text"
            java.lang.String r14 = r12.type
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x012a
            com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate r13 = new com.autonavi.bundle.entity.infolite.internal.template.PoiTextTemplate
            r13.<init>()
            java.lang.String r14 = r12.id
            int r14 = defpackage.bcv.a(r14, r7)
            r13.setId(r14)
            java.lang.String r14 = r12.name
            r13.setName(r14)
            java.lang.String r14 = r12.type
            r13.setType(r14)
            java.lang.String r14 = r12.color
            r13.setColor(r14)
            java.lang.String r14 = r12.value
            r13.setValue(r14)
            a(r12, r5)
            goto L_0x02ae
        L_0x012a:
            java.lang.String r13 = "button"
            java.lang.String r14 = r12.type
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0158
            com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate r13 = new com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate
            r13.<init>()
            java.lang.String r14 = r12.id
            int r14 = defpackage.bcv.a(r14, r7)
            r13.setId(r14)
            java.lang.String r14 = r12.name
            r13.setName(r14)
            java.lang.String r14 = r12.type
            r13.setType(r14)
            java.lang.String r14 = r12.action
            r13.setAction(r14)
            java.lang.String r12 = r12.value
            r13.setValue(r12)
            goto L_0x02ae
        L_0x0158:
            java.lang.String r13 = "html"
            java.lang.String r14 = r12.type
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0181
            com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate r13 = new com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate
            r13.<init>()
            java.lang.String r14 = r12.id
            int r14 = defpackage.bcv.a(r14, r7)
            r13.setId(r14)
            java.lang.String r14 = r12.name
            r13.setName(r14)
            java.lang.String r14 = r12.type
            r13.setType(r14)
            java.lang.String r12 = r12.value
            r13.setValue(r12)
            goto L_0x02ae
        L_0x0181:
            java.lang.String r13 = "img"
            java.lang.String r14 = r12.type
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x01af
            com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate r13 = new com.autonavi.bundle.entity.infolite.internal.template.PoiImageTemplate
            r13.<init>()
            java.lang.String r14 = r12.id
            int r14 = defpackage.bcv.a(r14, r7)
            r13.setId(r14)
            java.lang.String r14 = r12.name
            r13.setName(r14)
            java.lang.String r14 = r12.type
            r13.setType(r14)
            java.lang.String r14 = r12.value
            r13.setValue(r14)
            java.lang.String r12 = r12.src
            r13.setSrc(r12)
            goto L_0x02ae
        L_0x01af:
            java.lang.String r13 = "dyn_button"
            java.lang.String r14 = r12.type
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x01dd
            com.autonavi.bundle.entity.infolite.internal.template.PoiDynButtonTemplate r13 = new com.autonavi.bundle.entity.infolite.internal.template.PoiDynButtonTemplate
            r13.<init>()
            java.lang.String r14 = r12.id
            int r14 = defpackage.bcv.a(r14, r7)
            r13.setId(r14)
            java.lang.String r14 = r12.name
            r13.setName(r14)
            java.lang.String r14 = r12.type
            r13.setType(r14)
            java.lang.String r14 = r12.value
            r13.setValue(r14)
            java.lang.String r12 = r12.action
            r13.setAction(r12)
            goto L_0x02ae
        L_0x01dd:
            java.lang.String r13 = "link"
            java.lang.String r14 = r12.type
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0206
            com.autonavi.bundle.entity.infolite.internal.template.PoiLinkTemplate r13 = new com.autonavi.bundle.entity.infolite.internal.template.PoiLinkTemplate
            r13.<init>()
            java.lang.String r14 = r12.id
            int r14 = defpackage.bcv.a(r14, r7)
            r13.setId(r14)
            java.lang.String r14 = r12.name
            r13.setName(r14)
            java.lang.String r14 = r12.type
            r13.setType(r14)
            java.lang.String r12 = r12.action
            r13.setAction(r12)
            goto L_0x02ae
        L_0x0206:
            java.lang.String r13 = "webimg"
            java.lang.String r14 = r12.type
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x022f
            com.autonavi.bundle.entity.infolite.internal.template.PoiWebImageTemplate r13 = new com.autonavi.bundle.entity.infolite.internal.template.PoiWebImageTemplate
            r13.<init>()
            java.lang.String r14 = r12.id
            int r14 = defpackage.bcv.a(r14, r7)
            r13.setId(r14)
            java.lang.String r14 = r12.name
            r13.setName(r14)
            java.lang.String r14 = r12.type
            r13.setType(r14)
            java.lang.String r12 = r12.value
            r13.setValue(r12)
            goto L_0x02ae
        L_0x022f:
            java.lang.String r13 = "backgroundcolor"
            java.lang.String r14 = r12.type
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x0240
            com.autonavi.bundle.entity.infolite.internal.template.PoiSupperAddressTemplate r12 = a(r12)
        L_0x023d:
            r13 = r12
            goto L_0x02ae
        L_0x0240:
            java.lang.String r13 = "array"
            java.lang.String r14 = r12.type
            boolean r13 = r13.equals(r14)
            if (r13 == 0) goto L_0x02ad
            com.autonavi.bundle.searchservice.utils.PoiArrayTemplate r12 = b(r12)
            r13 = r12
            com.autonavi.bundle.searchservice.utils.PoiArrayTemplate r13 = (com.autonavi.bundle.searchservice.utils.PoiArrayTemplate) r13
            int r14 = r13.getId()
            r15 = 2015(0x7df, float:2.824E-42)
            if (r14 != r15) goto L_0x026a
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r14 = r5.getPoiChildrenInfo()
            java.util.List r13 = r13.getChildPois(r5)
            r14.poiList = r13
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r13 = r5.getPoiChildrenInfo()
            r13.childType = r9
            goto L_0x023d
        L_0x026a:
            int r14 = r13.getId()
            r15 = 2013(0x7dd, float:2.821E-42)
            if (r14 != r15) goto L_0x0283
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r14 = r5.getPoiChildrenInfo()
            java.util.List r13 = r13.getChildStation()
            r14.stationList = r13
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r13 = r5.getPoiChildrenInfo()
            r13.childType = r4
            goto L_0x023d
        L_0x0283:
            int r14 = r13.getId()
            r15 = 3001(0xbb9, float:4.205E-42)
            if (r14 != r15) goto L_0x023d
            java.lang.String r14 = r13.getSrc()
            boolean r14 = r13.isOnlineBg(r14)
            if (r14 == 0) goto L_0x02a5
            java.util.HashMap<java.lang.String, java.lang.String> r14 = r1.m
            java.lang.String r13 = r13.getSrc()
            java.lang.Object r13 = r14.get(r13)
            java.lang.String r13 = (java.lang.String) r13
            r5.setMarkerBGOnline(r13)
            goto L_0x023d
        L_0x02a5:
            java.lang.String r13 = r13.getMarkBGId()
            r5.setIconSrcName(r13)
            goto L_0x023d
        L_0x02ad:
            r13 = r8
        L_0x02ae:
            if (r13 == 0) goto L_0x00ed
            r6.add(r13)
            int r12 = r13.getId()
            java.lang.Integer r12 = java.lang.Integer.valueOf(r12)
            r10.put(r12, r13)
            goto L_0x00ed
        L_0x02c0:
            r5.setTemplateData(r6)
            r5.setTemplateDataMap(r10)
            defpackage.bcx.a(r5)
        L_0x02c9:
            g(r3, r5)
            java.lang.String r6 = r3.aoi
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x02e6
            java.lang.String r6 = r3.aoi
            java.util.ArrayList r6 = defpackage.bcx.b(r6)
            java.util.HashMap r9 = r5.getPoiExtra()
            java.lang.String r10 = "poi_polygon_bounds"
            r9.put(r10, r6)
            r5.setRegions(r6)
        L_0x02e6:
            java.lang.String r6 = r3.view_region
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 != 0) goto L_0x0300
            java.lang.String r6 = r3.view_region
            java.lang.Double[] r6 = defpackage.bcx.a(r6)
            r5.setViewRegions(r6)
            r9 = r19
            int r10 = r9.pagenum
            if (r10 != r4) goto L_0x0302
            r1.u = r6
            goto L_0x0302
        L_0x0300:
            r9 = r19
        L_0x0302:
            java.lang.String r4 = r3.rich_info_flag
            boolean r4 = defpackage.bcv.b(r4)
            r5.setRichInfoFlag(r4)
            java.lang.String r4 = r3.distance_display
            r5.setVoiceDriveDistance(r4)
            java.lang.String r4 = r3.name
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x031d
            java.lang.String r4 = r3.name
            r5.setName(r4)
        L_0x031d:
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "rating"
            java.lang.String r10 = r3.rating
            r4.put(r6, r10)
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "averagecost"
            java.lang.String r10 = r3.averagecost
            r4.put(r6, r10)
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "averagecostname"
            java.lang.String r10 = r3.averagecostname
            r4.put(r6, r10)
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "businfo_station_status"
            java.lang.String r10 = r3.businfo_station_status
            r4.put(r6, r10)
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "update_flag"
            java.lang.String r10 = r3.update_flag
            r4.put(r6, r10)
            java.lang.String r4 = r3.typecode
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x0361
            java.lang.String r4 = r3.typecode
            r5.setType(r4)
        L_0x0361:
            java.lang.String r4 = r3.need_arrive_timecost
            int r4 = defpackage.bcv.a(r4, r7)
            r5.setNeedArriveTimeCost(r4)
            java.lang.String r4 = r3.recommend_flag
            int r4 = defpackage.bcv.a(r4, r7)
            r5.setRecommendFlag(r4)
            java.lang.String r4 = r3.reference_rlt_flag
            int r4 = defpackage.bcv.a(r4, r7)
            r5.setReferenceRltFlag(r4)
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r4 = r5.getIndoorPoiInfo()
            java.lang.String r6 = r3.sndt_fl_nona
            r4.sndtFloorName = r6
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r4 = r5.getIndoorPoiInfo()
            java.lang.String r6 = r3.sndt_parentid
            r4.buildingPoiId = r6
            java.lang.String r4 = r3.pic_status
            int r4 = defpackage.bcv.a(r4, r7)
            r5.setPicStatus(r4)
            java.lang.String r4 = r3.route_planning
            int r4 = defpackage.bcv.a(r4, r7)
            r5.setRoutePlanning(r4)
            java.lang.String r4 = r3.f_nona
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x03c0
            java.lang.String r4 = r3.f_nona
            r5.setFnona(r4)
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r4 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r4 = r5.as(r4)
            com.amap.bundle.datamodel.FavoritePOI r4 = (com.amap.bundle.datamodel.FavoritePOI) r4
            java.lang.String r6 = r3.f_nona
            r4.setFnona(r6)
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r4 = r5.getIndoorPoiInfo()
            java.lang.String r6 = r3.f_nona
            r4.floorName = r6
        L_0x03c0:
            java.lang.String r4 = r3.sndt_fl_no
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x03d4
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r4 = r5.getIndoorPoiInfo()
            java.lang.String r6 = r3.sndt_fl_no
            int r6 = defpackage.bcv.a(r6, r7)
            r4.floorNo = r6
        L_0x03d4:
            java.lang.String r4 = r3.sndt_parentid
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x03e4
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r4 = r5.getIndoorPoiInfo()
            java.lang.String r6 = r3.sndt_parentid
            r4.parentId = r6
        L_0x03e4:
            java.lang.String r4 = r3.display_icon_name_state
            int r4 = defpackage.bcv.a(r4, r7)
            r5.setDisplayIconNameState(r4)
            double r10 = r3.longitude
            double r12 = r3.latitude
            r14 = 0
            int r4 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            if (r4 <= 0) goto L_0x0402
            int r4 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r4 <= 0) goto L_0x0402
            com.autonavi.common.model.GeoPoint r4 = r5.getPoint()
            r4.setLonLat(r10, r12)
        L_0x0402:
            java.lang.String r4 = r3.display_x
            r10 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            double r12 = defpackage.bcv.a(r4, r10)
            java.lang.String r4 = r3.display_y
            double r14 = defpackage.bcv.a(r4, r10)
            int r4 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r4 == 0) goto L_0x0420
            int r4 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r4 == 0) goto L_0x0420
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint
            r4.<init>(r12, r14)
            r5.setDisplayPoint(r4)
        L_0x0420:
            java.lang.String r4 = r3.parent
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x043a
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r4 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r4 = r5.as(r4)
            com.amap.bundle.datamodel.FavoritePOI r4 = (com.amap.bundle.datamodel.FavoritePOI) r4
            java.lang.String r6 = r3.parent
            r4.setParent(r6)
            java.lang.String r4 = r3.parent
            r5.setParent(r4)
        L_0x043a:
            java.lang.String r4 = r3.childType
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x0454
            java.lang.String r4 = r3.childType
            r5.setChildType(r4)
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r4 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r4 = r5.as(r4)
            com.amap.bundle.datamodel.FavoritePOI r4 = (com.amap.bundle.datamodel.FavoritePOI) r4
            java.lang.String r6 = r3.childType
            r4.setChildType(r6)
        L_0x0454:
            java.lang.String r4 = r3.towards_angle
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x046e
            java.lang.String r4 = r3.towards_angle
            r5.setTowardsAngle(r4)
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r4 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r4 = r5.as(r4)
            com.amap.bundle.datamodel.FavoritePOI r4 = (com.amap.bundle.datamodel.FavoritePOI) r4
            java.lang.String r6 = r3.towards_angle
            r4.setTowardsAngle(r6)
        L_0x046e:
            java.lang.String r4 = r3.end_poi_extension
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x0488
            java.lang.String r4 = r3.end_poi_extension
            r5.setEndPoiExtension(r4)
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r4 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r4 = r5.as(r4)
            com.amap.bundle.datamodel.FavoritePOI r4 = (com.amap.bundle.datamodel.FavoritePOI) r4
            java.lang.String r6 = r3.end_poi_extension
            r4.setEndPoiExtension(r6)
        L_0x0488:
            java.lang.String r4 = r3.transparent
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x04a2
            java.lang.String r4 = r3.transparent
            r5.setTransparent(r4)
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r4 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r4 = r5.as(r4)
            com.amap.bundle.datamodel.FavoritePOI r4 = (com.amap.bundle.datamodel.FavoritePOI) r4
            java.lang.String r6 = r3.transparent
            r4.setTransparent(r6)
        L_0x04a2:
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "tra_tag"
            java.lang.String r10 = r3.tra_tag
            r4.put(r6, r10)
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "tra_title"
            java.lang.String r10 = r3.tra_title
            r4.put(r6, r10)
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "tra_action"
            java.lang.String r10 = r3.tra_action
            r4.put(r6, r10)
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "tra_action_param"
            java.lang.String r10 = r3.tra_action_param
            r4.put(r6, r10)
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "ugc_title"
            java.lang.String r10 = r3.ugc_title
            r4.put(r6, r10)
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "ugc_thread_url"
            java.lang.String r10 = r3.ugc_thread_url
            r4.put(r6, r10)
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r6 = "ugc_plot_url"
            java.lang.String r10 = r3.ugc_plot_url
            r4.put(r6, r10)
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0537 }
            r6 = r20
            java.lang.String r10 = r6.originalJson     // Catch:{ JSONException -> 0x0535 }
            r4.<init>(r10)     // Catch:{ JSONException -> 0x0535 }
            java.lang.String r10 = "poi_list"
            org.json.JSONArray r4 = r4.getJSONArray(r10)     // Catch:{ JSONException -> 0x0535 }
            if (r4 == 0) goto L_0x053e
            int r10 = r4.length()     // Catch:{ JSONException -> 0x0535 }
            if (r10 <= 0) goto L_0x053e
            int r10 = r4.length()     // Catch:{ JSONException -> 0x0535 }
            r11 = 0
        L_0x050b:
            if (r11 > r10) goto L_0x053e
            org.json.JSONObject r12 = r4.getJSONObject(r11)     // Catch:{ JSONException -> 0x052d }
            java.lang.String r13 = r3.id     // Catch:{ JSONException -> 0x052d }
            java.lang.String r14 = "id"
            java.lang.String r14 = r12.optString(r14)     // Catch:{ JSONException -> 0x052d }
            boolean r13 = r13.equals(r14)     // Catch:{ JSONException -> 0x052d }
            if (r13 == 0) goto L_0x0532
            java.util.HashMap r13 = r5.getPoiExtra()     // Catch:{ JSONException -> 0x052d }
            java.lang.String r14 = "original"
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x052d }
            r13.put(r14, r12)     // Catch:{ JSONException -> 0x052d }
            goto L_0x053e
        L_0x052d:
            r0 = move-exception
            r12 = r0
            r12.printStackTrace()     // Catch:{ JSONException -> 0x0535 }
        L_0x0532:
            int r11 = r11 + 1
            goto L_0x050b
        L_0x0535:
            r0 = move-exception
            goto L_0x053a
        L_0x0537:
            r0 = move-exception
            r6 = r20
        L_0x053a:
            r4 = r0
            r4.printStackTrace()
        L_0x053e:
            java.lang.String r4 = r3.citycode
            r5.setCityCode(r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            int r10 = r3.adcode
            r4.append(r10)
            java.lang.String r4 = r4.toString()
            r5.setAdCode(r4)
            f(r3, r5)
            e(r3, r5)
            java.lang.String r4 = r3.address
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x05b6
            java.lang.String r4 = r5.getType()
            boolean r10 = android.text.TextUtils.isEmpty(r4)
            if (r10 != 0) goto L_0x0578
            int r10 = r4.length()
            r11 = 5
            if (r10 <= r11) goto L_0x0578
            r10 = 4
            java.lang.String r4 = r4.substring(r7, r10)
        L_0x0578:
            java.lang.String r7 = r3.address
            r5.setAddr(r7)
            java.lang.String r10 = "1507"
            boolean r10 = r4.equals(r10)
            if (r10 != 0) goto L_0x05b6
            java.lang.String r10 = "1505"
            boolean r4 = r4.equals(r10)
            if (r4 != 0) goto L_0x05b6
            java.lang.String r4 = r3.districtname
            java.lang.String r10 = r3.provincename
            boolean r11 = android.text.TextUtils.isEmpty(r4)
            if (r11 != 0) goto L_0x05b3
            boolean r11 = android.text.TextUtils.isEmpty(r10)
            if (r11 != 0) goto L_0x05b3
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r4)
            r11.append(r10)
            r11.append(r7)
            java.lang.String r4 = r11.toString()
            r5.setAddr(r4)
            goto L_0x05b6
        L_0x05b3:
            r5.setAddr(r7)
        L_0x05b6:
            d(r3, r5)
            c(r3, r5)
            com.autonavi.bl.search.PoilistStations r4 = r3.stations
            if (r4 == 0) goto L_0x05cb
            java.util.HashMap r4 = r5.getPoiExtra()
            java.lang.String r7 = "stations"
            java.lang.String r10 = r3.stations_json
            r4.put(r7, r10)
        L_0x05cb:
            a(r3, r5, r8)
            b(r3, r5)
            a(r3, r5)
            if (r3 == 0) goto L_0x05ec
            if (r5 == 0) goto L_0x05ec
            java.lang.String r4 = r3.s_duration
            r5.setSketchDuration(r4)
            java.lang.String r4 = r3.show_sketching_map
            r5.setShowSketchingMap(r4)
            java.lang.String r4 = r3.sketch_url
            r5.setSketchUrl(r4)
            java.lang.String r3 = r3.show_environmental_map
            r5.setShowEnvironmentalMap(r3)
        L_0x05ec:
            r3 = r18
            r3.add(r5)
            goto L_0x0010
        L_0x05f3:
            r3 = r18
            r9 = r19
            r6 = r20
            goto L_0x0010
        L_0x05fb:
            r3 = r18
            boolean r1 = a(r18)
            java.util.Iterator r2 = r18.iterator()
        L_0x0605:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x061f
            java.lang.Object r3 = r2.next()
            com.autonavi.common.model.POI r3 = (com.autonavi.common.model.POI) r3
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r5 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r3 = r3.as(r5)
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r3 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r3
            r5 = r1 ^ 1
            r3.setIsShowName(r5)
            goto L_0x0605
        L_0x061f:
            return
        L_0x0620:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bcu.a(java.util.ArrayList, auk, java.util.ArrayList, com.autonavi.bl.search.InfoliteParam, com.autonavi.bundle.entity.infolite.internal.InfoliteResult):void");
    }

    public static void a(ArrayList<PoilistPoiInfo> arrayList, ArrayList<POI> arrayList2, InfoliteParam infoliteParam) {
        if (arrayList != null && arrayList.size() != 0) {
            Iterator<PoilistPoiInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                PoilistPoiInfo next = it.next();
                SearchPoi searchPoi = (SearchPoi) POIFactory.createPOI(SearchPoi.class);
                searchPoi.setId(next.id);
                searchPoi.setType(next.typecode);
                StringBuilder sb = new StringBuilder();
                sb.append(next.adcode);
                searchPoi.setAdCode(sb.toString());
                searchPoi.setName(next.name);
                searchPoi.setPhone(next.tel);
                searchPoi.setAddr(next.address);
                adz adz = (adz) a.a.a(adz.class);
                if (TextUtils.isEmpty(next.address) && next.adcode > 0 && adz != null) {
                    GADAREAEXTRAINFO a = adz.a(next.adcode);
                    if (a != null) {
                        String townName = a.getTownName();
                        String cityName = a.getCityName();
                        String provName = a.getProvName();
                        if (!TextUtils.isEmpty(townName)) {
                            searchPoi.setAddr(townName);
                        } else if (!TextUtils.isEmpty(cityName)) {
                            searchPoi.setAddr(cityName);
                        } else if (!TextUtils.isEmpty(provName)) {
                            searchPoi.setAddr(provName);
                        }
                    }
                }
                double d = next.longitude;
                double d2 = next.latitude;
                if (d > 0.0d && d2 > 0.0d) {
                    searchPoi.getPoint().setLonLat(d, d2);
                }
                searchPoi.getPoiExtra().put("SrcType", "nativepoi");
                searchPoi.getPoiExtra().put("query_type", infoliteParam.query_type);
                GeoPoint geoPoint = null;
                if ("RQBXY".equals(infoliteParam.query_type)) {
                    geoPoint = new GeoPoint(infoliteParam.longitude, infoliteParam.latitude);
                }
                a(next, searchPoi, geoPoint);
                if (next.entrances != null && next.entrances.size() > 0) {
                    ArrayList arrayList3 = new ArrayList();
                    for (int i = 0; i < next.entrances.size(); i++) {
                        PoilistPassageway poilistPassageway = next.entrances.get(i);
                        double d3 = poilistPassageway.longitude;
                        double d4 = poilistPassageway.latitude;
                        if (d3 > 0.0d && d4 > 0.0d) {
                            Point a2 = bcz.a(d4, d3);
                            arrayList3.add(new GeoPoint(a2.x, a2.y));
                        }
                    }
                    searchPoi.setEntranceList(arrayList3);
                }
                arrayList2.add(searchPoi);
            }
        }
    }

    private static void a(PoilistPoiInfo poilistPoiInfo, SearchPoi searchPoi) {
        if (poilistPoiInfo.correlation_list != null && poilistPoiInfo.correlation_list.size() != 0) {
            aum aum = new aum();
            aum.a = searchPoi.getId();
            Iterator<PoilistCorrelation> it = poilistPoiInfo.correlation_list.iterator();
            while (it.hasNext()) {
                PoilistCorrelation next = it.next();
                aut aut = new aut();
                aut.d = next.mPoiId;
                if (!TextUtils.isEmpty(next.mAnchor) && (next.mAnchor.startsWith("0x") || next.mAnchor.startsWith("0X"))) {
                    aut.e = (int) bcv.b(next.mAnchor.substring(2), 16);
                }
                aut.a = next.mLabelName;
                aut.f = bcv.a(next.mMainKey, 0);
                aut.g = bcv.a(next.mSubkey, 0);
                aut.i = bcv.a(next.mMaxzoom, 20);
                aut.h = bcv.a(next.mMinzoom, 0);
                aut.j = bcv.a(next.mRank);
                GeoPoint geoPoint = new GeoPoint(bcv.a(next.mLongitude, 0.0d), bcv.a(next.mLatitude, 0.0d));
                aut.b = geoPoint.x;
                aut.c = geoPoint.y;
                aum.b.add(aut);
            }
            searchPoi.setRecommendMode(aum);
        }
    }

    private static void b(PoilistPoiInfo poilistPoiInfo, SearchPoi searchPoi) {
        String str = poilistPoiInfo.cpdata;
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("\\|");
            ArrayList arrayList = new ArrayList();
            for (String split2 : split) {
                String[] split3 = split2.split(";");
                if (split3.length > 1) {
                    CpData cpData = new CpData();
                    cpData.setCpid(split3[0]);
                    cpData.setSource(split3[1]);
                    arrayList.add(cpData);
                }
            }
            searchPoi.getPoiExtra().put("Cpdata", com.alibaba.fastjson.JSONObject.toJSONString(arrayList));
            return;
        }
        searchPoi.getPoiExtra().remove("Cpdata");
    }

    private static void a(PoilistPoiInfo poilistPoiInfo, SearchPoi searchPoi, GeoPoint geoPoint) {
        searchPoi.setDistance(bcx.a(searchPoi, poilistPoiInfo.distance, geoPoint));
    }

    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1, types: [java.util.Collection] */
    /* JADX WARNING: type inference failed for: r5v2, types: [java.util.Collection<? extends com.autonavi.common.model.POI>] */
    /* JADX WARNING: type inference failed for: r5v3, types: [java.util.List] */
    /* JADX WARNING: type inference failed for: r5v13, types: [java.util.List] */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: type inference failed for: r5v15 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], java.util.List, java.util.Collection<? extends com.autonavi.common.model.POI>]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], java.util.Collection, java.util.List]
      mth insns count: 92
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void c(com.autonavi.bl.search.PoilistPoiInfo r9, com.autonavi.bundle.entity.common.searchpoi.SearchPoi r10) {
        /*
            java.util.ArrayList<com.autonavi.bl.search.PoilistChildStationInfo> r0 = r9.child_stations
            if (r0 == 0) goto L_0x012d
            java.util.ArrayList<com.autonavi.bl.search.PoilistChildStationInfo> r0 = r9.child_stations
            int r0 = r0.size()
            if (r0 != 0) goto L_0x000e
            goto L_0x012d
        L_0x000e:
            org.json.JSONArray r0 = new org.json.JSONArray
            r0.<init>()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r2 = 0
            java.util.ArrayList<com.autonavi.bl.search.PoilistChildStationInfo> r3 = r9.child_stations
            java.util.Iterator r3 = r3.iterator()
        L_0x001f:
            boolean r4 = r3.hasNext()
            r5 = 0
            r6 = 1
            if (r4 == 0) goto L_0x00c5
            java.lang.Object r4 = r3.next()
            com.autonavi.bl.search.PoilistChildStationInfo r4 = (com.autonavi.bl.search.PoilistChildStationInfo) r4
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r8 = com.alibaba.fastjson.JSONObject.toJSONString(r4)     // Catch:{ JSONException -> 0x00bf }
            r7.<init>(r8)     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r4 = r4.businfo_lineids     // Catch:{ JSONException -> 0x00bf }
            r1.append(r4)     // Catch:{ JSONException -> 0x00bf }
            java.util.ArrayList<com.autonavi.bl.search.PoilistChildStationInfo> r4 = r9.child_stations     // Catch:{ JSONException -> 0x00bf }
            int r4 = r4.size()     // Catch:{ JSONException -> 0x00bf }
            int r4 = r4 - r6
            if (r2 >= r4) goto L_0x0049
            r4 = 59
            r1.append(r4)     // Catch:{ JSONException -> 0x00bf }
        L_0x0049:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r4 = r10.getPoiChildrenInfo()     // Catch:{ JSONException -> 0x00bf }
            if (r4 == 0) goto L_0x0058
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r4 = r10.getPoiChildrenInfo()     // Catch:{ JSONException -> 0x00bf }
            java.util.Collection<? extends com.autonavi.common.model.POI> r4 = r4.stationList     // Catch:{ JSONException -> 0x00bf }
            r5 = r4
            java.util.List r5 = (java.util.List) r5     // Catch:{ JSONException -> 0x00bf }
        L_0x0058:
            if (r5 == 0) goto L_0x00b8
            int r4 = r5.size()     // Catch:{ JSONException -> 0x00bf }
            int r6 = r2 + -1
            if (r4 < r6) goto L_0x00b8
            java.lang.Object r4 = r5.get(r2)     // Catch:{ JSONException -> 0x00bf }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r4 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r4     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r5 = "businfo_line_key"
            java.lang.String r6 = r4.getAddr()     // Catch:{ JSONException -> 0x00bf }
            r7.put(r5, r6)     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r5 = "x"
            com.autonavi.common.model.GeoPoint r6 = r4.getPoint()     // Catch:{ JSONException -> 0x00bf }
            int r6 = r6.x     // Catch:{ JSONException -> 0x00bf }
            r7.put(r5, r6)     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r5 = "y"
            com.autonavi.common.model.GeoPoint r6 = r4.getPoint()     // Catch:{ JSONException -> 0x00bf }
            int r6 = r6.y     // Catch:{ JSONException -> 0x00bf }
            r7.put(r5, r6)     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r5 = "name"
            java.lang.String r6 = r10.getName()     // Catch:{ JSONException -> 0x00bf }
            r7.put(r5, r6)     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r5 = "poiid2"
            java.lang.String r6 = r10.getId()     // Catch:{ JSONException -> 0x00bf }
            r7.put(r5, r6)     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r5 = "poiid"
            java.lang.String r6 = r4.getPoiId()     // Catch:{ JSONException -> 0x00bf }
            r7.put(r5, r6)     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r5 = r10.getId()     // Catch:{ JSONException -> 0x00bf }
            r4.setPoiId2(r5)     // Catch:{ JSONException -> 0x00bf }
            java.util.HashMap r4 = r4.getPoiExtra()     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r5 = "businfo_lineids"
            java.lang.String r6 = "businfo_lineids"
            java.lang.String r6 = r7.optString(r6)     // Catch:{ JSONException -> 0x00bf }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x00bf }
        L_0x00b8:
            r0.put(r2, r7)     // Catch:{ JSONException -> 0x00bf }
            int r2 = r2 + 1
            goto L_0x001f
        L_0x00bf:
            r4 = move-exception
            r4.printStackTrace()
            goto L_0x001f
        L_0x00c5:
            java.lang.String r9 = r1.toString()
            java.util.HashMap r1 = r10.getPoiExtra()
            java.lang.String r2 = "businfo_lineids"
            boolean r1 = r1.containsKey(r2)
            if (r1 != 0) goto L_0x00e4
            boolean r1 = android.text.TextUtils.isEmpty(r9)
            if (r1 != 0) goto L_0x00e4
            java.util.HashMap r1 = r10.getPoiExtra()
            java.lang.String r2 = "businfo_lineids"
            r1.put(r2, r9)
        L_0x00e4:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r9 = r10.getPoiChildrenInfo()
            int r9 = r9.childType
            if (r9 != r6) goto L_0x012c
            java.util.HashMap r9 = r10.getPoiExtra()
            java.lang.String r1 = "child_stations"
            java.lang.String r2 = r0.toString()
            r9.put(r1, r2)
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r9 = r10.getPoiChildrenInfo()
            if (r9 == 0) goto L_0x0105
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r9 = r10.getPoiChildrenInfo()
            java.util.Collection<? extends com.autonavi.common.model.POI> r5 = r9.stationList
        L_0x0105:
            if (r5 == 0) goto L_0x012c
            java.util.Iterator r9 = r5.iterator()
        L_0x010b:
            boolean r1 = r9.hasNext()
            if (r1 == 0) goto L_0x012c
            java.lang.Object r1 = r9.next()
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r1 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r1
            java.util.HashMap r2 = r1.getPoiExtra()
            java.lang.String r3 = "child_stations"
            java.lang.String r4 = r0.toString()
            r2.put(r3, r4)
            java.lang.String r2 = r10.getName()
            r1.setName(r2)
            goto L_0x010b
        L_0x012c:
            return
        L_0x012d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bcu.c(com.autonavi.bl.search.PoilistPoiInfo, com.autonavi.bundle.entity.common.searchpoi.SearchPoi):void");
    }

    private static void d(PoilistPoiInfo poilistPoiInfo, SearchPoi searchPoi) {
        if (poilistPoiInfo.stations != null) {
            if (!TextUtils.isEmpty(poilistPoiInfo.stations.businfo_line_keys)) {
                String[] split = poilistPoiInfo.stations.businfo_line_keys.split(";|\\|");
                StringBuilder sb = new StringBuilder();
                HashMap hashMap = new HashMap();
                for (int i = 0; i < split.length; i++) {
                    if (!hashMap.containsKey(split[i])) {
                        hashMap.put(split[i], split[i]);
                        if (i != 0) {
                            sb.append("/");
                        }
                        sb.append(split[i]);
                    }
                }
                hashMap.clear();
                searchPoi.getPoiExtra().put("station_lines", sb.toString());
            }
            if (!TextUtils.isEmpty(poilistPoiInfo.stations.businfo_lineids)) {
                searchPoi.getPoiExtra().put("businfo_lineids", poilistPoiInfo.stations.businfo_lineids);
            }
        }
    }

    private static void e(PoilistPoiInfo poilistPoiInfo, SearchPoi searchPoi) {
        if (poilistPoiInfo.parkinfo != null) {
            HashMap<String, Serializable> poiExtra = searchPoi.getPoiExtra();
            StringBuilder sb = new StringBuilder();
            sb.append("parkinfo");
            sb.append("_geometry");
            poiExtra.put(sb.toString(), poilistPoiInfo.parkinfo.geometry);
            HashMap<String, Serializable> poiExtra2 = searchPoi.getPoiExtra();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("parkinfo");
            sb2.append("_prc_c_d_e");
            poiExtra2.put(sb2.toString(), poilistPoiInfo.parkinfo.prc_c_d_e);
            HashMap<String, Serializable> poiExtra3 = searchPoi.getPoiExtra();
            StringBuilder sb3 = new StringBuilder();
            sb3.append("parkinfo");
            sb3.append("_charge");
            poiExtra3.put(sb3.toString(), poilistPoiInfo.parkinfo.charge);
            HashMap<String, Serializable> poiExtra4 = searchPoi.getPoiExtra();
            StringBuilder sb4 = new StringBuilder();
            sb4.append("parkinfo");
            sb4.append("_points");
            poiExtra4.put(sb4.toString(), poilistPoiInfo.parkinfo.points);
            HashMap<String, Serializable> poiExtra5 = searchPoi.getPoiExtra();
            StringBuilder sb5 = new StringBuilder();
            sb5.append("parkinfo");
            sb5.append("_tag_category");
            poiExtra5.put(sb5.toString(), poilistPoiInfo.parkinfo.tag_category);
            HashMap<String, Serializable> poiExtra6 = searchPoi.getPoiExtra();
            StringBuilder sb6 = new StringBuilder();
            sb6.append("parkinfo");
            sb6.append("_inout_info");
            poiExtra6.put(sb6.toString(), poilistPoiInfo.parkinfo.inout_info_json);
            if (poilistPoiInfo.parkinfo.inout_info != null && poilistPoiInfo.parkinfo.inout_info.size() > 0) {
                Iterator<PoilistParkinfoInoutInfo> it = poilistPoiInfo.parkinfo.inout_info.iterator();
                while (it.hasNext()) {
                    PoilistParkinfoInoutInfo next = it.next();
                    if (next != null && ("2".equals(next.keytype) || "0".equals(next.keytype))) {
                        Point a = bcz.a(bcv.a(next.y, 0.0d), bcv.a(next.x, 0.0d));
                        if (searchPoi.getEntranceList() == null) {
                            searchPoi.setEntranceList(new ArrayList());
                        }
                        searchPoi.getEntranceList().add(new GeoPoint(a.x, a.y));
                    }
                }
            }
        }
    }

    private static void f(PoilistPoiInfo poilistPoiInfo, SearchPoi searchPoi) {
        if (poilistPoiInfo.entrances != null && poilistPoiInfo.entrances.size() > 0) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < poilistPoiInfo.entrances.size(); i++) {
                PoilistPassageway poilistPassageway = poilistPoiInfo.entrances.get(i);
                double d = poilistPassageway.longitude;
                double d2 = poilistPassageway.latitude;
                if (d > 0.0d && d2 > 0.0d) {
                    Point a = bcz.a(d2, d);
                    arrayList.add(new GeoPoint(a.x, a.y));
                }
            }
            searchPoi.setEntranceList(arrayList);
        }
        if (poilistPoiInfo.exits != null && poilistPoiInfo.exits.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            for (int i2 = 0; i2 < poilistPoiInfo.exits.size(); i2++) {
                PoilistPassageway poilistPassageway2 = poilistPoiInfo.exits.get(i2);
                double d3 = poilistPassageway2.longitude;
                double d4 = poilistPassageway2.latitude;
                if (d3 > 0.0d && d4 > 0.0d) {
                    Point a2 = bcz.a(d4, d3);
                    arrayList2.add(new GeoPoint(a2.x, a2.y));
                }
            }
            searchPoi.setExitList(arrayList2);
        }
    }

    private static void g(PoilistPoiInfo poilistPoiInfo, SearchPoi searchPoi) {
        if (poilistPoiInfo.poi_sug_info != null && poilistPoiInfo.poi_sug_info.size() != 0) {
            ArrayList arrayList = new ArrayList();
            Iterator<PoilistSugInfo> it = poilistPoiInfo.poi_sug_info.iterator();
            while (it.hasNext()) {
                PoilistSugInfo next = it.next();
                SearchRecommendPoi searchRecommendPoi = (SearchRecommendPoi) POIFactory.createPOI(SearchRecommendPoi.class);
                searchRecommendPoi.setShowType(0);
                String str = next.tupu_icon_forcsr;
                if (!"".equals(str) && str.indexOf(44) > 0 && str.indexOf(44) < str.length() - 1) {
                    String[] split = str.split(",");
                    searchRecommendPoi.setTpIcon(split[0]);
                    searchRecommendPoi.setShowType(bcv.a(split[1], 0));
                }
                searchRecommendPoi.setType(next.typecode);
                String str2 = next.name;
                searchRecommendPoi.setName(str2);
                searchRecommendPoi.setId(next.pguid);
                String str3 = next.shortname;
                if ("".equals(str3)) {
                    searchRecommendPoi.setShortName(bcx.c(str2));
                } else {
                    searchRecommendPoi.setShortName(str3);
                }
                if (searchRecommendPoi.getShortName().length() > 21) {
                    if (searchRecommendPoi.getShowType() == 1) {
                        searchRecommendPoi.setShowType(2);
                    } else if (searchRecommendPoi.getShowType() == 4) {
                        searchRecommendPoi.setShowType(3);
                    }
                }
                Point a = bcz.a(bcv.a(next.y, 0.0d), Double.valueOf(bcv.a(next.x, 0.0d)).doubleValue());
                searchRecommendPoi.setPoint(new GeoPoint(a.x, a.y));
                searchRecommendPoi.setEndPoiExtension(next.end_poi_extension);
                searchRecommendPoi.setTransparent(next.transparent);
                arrayList.add(searchRecommendPoi);
            }
            if (arrayList.size() > 0) {
                searchPoi.setRecommonPoiInfos(arrayList);
            }
        }
    }

    private static PoiSupperAddressTemplate a(PoilistDomain poilistDomain) {
        String[] split;
        PoiSupperAddressTemplate poiSupperAddressTemplate = new PoiSupperAddressTemplate();
        poiSupperAddressTemplate.setId(bcv.a(poilistDomain.id, 0));
        poiSupperAddressTemplate.setName(poilistDomain.name);
        poiSupperAddressTemplate.setType(poilistDomain.type);
        poiSupperAddressTemplate.setValue(poilistDomain.value);
        ArrayList arrayList = new ArrayList();
        for (String str : poilistDomain.value.split(";")) {
            if (!TextUtils.isEmpty(str)) {
                arrayList.add(str);
            }
        }
        poiSupperAddressTemplate.setAddrs(arrayList);
        return poiSupperAddressTemplate;
    }

    private static PoiArrayTemplate b(PoilistDomain poilistDomain) {
        PoiArrayTemplate poiArrayTemplate = new PoiArrayTemplate();
        int i = 0;
        poiArrayTemplate.setId(bcv.a(poilistDomain.id, 0));
        poiArrayTemplate.setName(poilistDomain.name);
        poiArrayTemplate.setType(poilistDomain.type);
        if (poiArrayTemplate.getId() == 2015 || poiArrayTemplate.getId() == 2013 || poiArrayTemplate.getId() == 6001 || poiArrayTemplate.getId() == 1103) {
            poiArrayTemplate.setPoiids(poilistDomain.poiids);
            poiArrayTemplate.setPxs(poilistDomain.pxs);
            poiArrayTemplate.setPys(poilistDomain.pys);
            poiArrayTemplate.setValue(poilistDomain.values);
            poiArrayTemplate.setShortName(poilistDomain.shortname);
            poiArrayTemplate.setChildType(poilistDomain.childtype);
            poiArrayTemplate.setAddress(poilistDomain.address);
            poiArrayTemplate.setPoiName(poilistDomain.poiname);
            poiArrayTemplate.setDeepinfo(poilistDomain.deepinfo);
            poiArrayTemplate.setDistence(poilistDomain.distance);
            poiArrayTemplate.setAnchor(poilistDomain.anchor);
            poiArrayTemplate.setRenderStyleMain(poilistDomain.render_style_main);
            poiArrayTemplate.setRenderStyleSub(poilistDomain.render_style_sub);
            poiArrayTemplate.setMiniZoom(poilistDomain.minizoom);
            poiArrayTemplate.setRenderRank(poilistDomain.render_rank);
            poiArrayTemplate.setXEntr(poilistDomain.pentrxs);
            poiArrayTemplate.setYEntr(poilistDomain.pentrys);
            poiArrayTemplate.setShowChild(bcv.a(poilistDomain.show_child, 1));
            poiArrayTemplate.setTags(poilistDomain.tags);
            poiArrayTemplate.setTagColors(poilistDomain.tag_colors);
            if (poiArrayTemplate.getId() == 2015) {
                poiArrayTemplate.setLabel(poilistDomain.label);
                poiArrayTemplate.setLabelNew(poilistDomain.label_new);
            } else if (poiArrayTemplate.getId() == 1103) {
                poiArrayTemplate.setLabel(poilistDomain.label);
                auw auw = new auw();
                auw.b = bcv.a(poilistDomain.max_rows, 0);
                auw.a = bcv.a(poilistDomain.columns, 0);
                auw.c = bcv.a(poilistDomain.default_rows, 0);
                poiArrayTemplate.setChildConfig(auw);
            } else if (poiArrayTemplate.getId() == 2013) {
                poiArrayTemplate.setBusAlias(poilistDomain.bus_alias);
            }
        } else if (poiArrayTemplate.getId() == 3001) {
            poiArrayTemplate.setSrc(poilistDomain.src);
        } else if (poiArrayTemplate.getId() == 2027) {
            poiArrayTemplate.setShoppingMallDatas(poilistDomain.value);
            poiArrayTemplate.setShoppingMallUrl(poilistDomain.url);
        } else if (poiArrayTemplate.getId() == 2029) {
            String str = poilistDomain.gas_type;
            String str2 = poilistDomain.gas_price;
            String str3 = poilistDomain.gas_unit;
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
                return poiArrayTemplate;
            }
            String[] split = str.split(PoiLayoutTemplate.SPLITER_REG);
            int length = split.length;
            String[] split2 = str2.split(PoiLayoutTemplate.SPLITER_REG);
            if (length > split2.length) {
                length = split2.length;
            }
            String[] split3 = str3.split(PoiLayoutTemplate.SPLITER_REG);
            if (length > split3.length) {
                length = split3.length;
            }
            if (length == 0) {
                return poiArrayTemplate;
            }
            if (length > 2) {
                length = 2;
            }
            String[] strArr = new String[length];
            String[] strArr2 = new String[length];
            String[] strArr3 = new String[length];
            while (i < length) {
                strArr[i] = split[i].trim();
                strArr2[i] = split2[i].trim();
                strArr3[i] = split3[i].trim();
                i++;
            }
            poiArrayTemplate.setGas_types(strArr);
            poiArrayTemplate.setGas_prices(strArr2);
            poiArrayTemplate.setGas_utils(strArr3);
        } else if (poiArrayTemplate.getId() == 2038) {
            poiArrayTemplate.setValue(poilistDomain.value);
        } else if (poiArrayTemplate.getId() == 2115 && !TextUtils.isEmpty(poilistDomain.charge_color) && !TextUtils.isEmpty(poilistDomain.charge_detail) && !TextUtils.isEmpty(poilistDomain.charge_type)) {
            String[] split4 = poilistDomain.charge_type.split("\\+\\+\\+");
            String[] split5 = poilistDomain.charge_color.split("\\+\\+\\+");
            String[] split6 = poilistDomain.charge_detail.split("\\+\\+\\+");
            if (split4 == null || split6 == null || split4.length != split6.length) {
                return poiArrayTemplate;
            }
            ArrayList arrayList = new ArrayList();
            while (i < split4.length) {
                auh auh = new auh();
                auh.a = split4[i];
                auh.b = split6[i];
                if (split5 != null && split5.length > i) {
                    auh.c = split5[i];
                }
                arrayList.add(auh);
                i++;
            }
            poiArrayTemplate.setCharge(arrayList);
        }
        return poiArrayTemplate;
    }

    private static void a(PoilistDomain poilistDomain, SearchPoi searchPoi) {
        int i = 0;
        int a = bcv.a(poilistDomain.id, 0);
        if (a == 3002) {
            String str = poilistDomain.value;
            ArrayList arrayList = new ArrayList();
            String[] split = str.split("\\|");
            int length = split.length;
            while (i < length) {
                ArrayList<GeoPoint> d = bcx.d(split[i]);
                if (d != null) {
                    arrayList.add(d);
                }
                i++;
            }
            searchPoi.getPoiExtra().put("poi_polygon_bounds", arrayList);
        } else if (a == 3004) {
            String str2 = poilistDomain.value;
            if (!TextUtils.isEmpty(str2)) {
                String[] split2 = str2.split("\\|");
                int length2 = split2.length;
                ArrayList arrayList2 = new ArrayList(length2);
                while (i < length2) {
                    ArrayList<GeoPoint> d2 = bcx.d(split2[i]);
                    if (d2 != null) {
                        arrayList2.add(d2);
                    }
                    i++;
                }
                searchPoi.getPoiExtra().put("poi_roadaoi_bounds", arrayList2);
            }
        } else if (a == 2001) {
            searchPoi.setName(poilistDomain.value);
        } else if (a == 2009) {
            searchPoi.setAddr(poilistDomain.value);
        } else {
            if (a == 3005) {
                String str3 = poilistDomain.value;
                if (!TextUtils.isEmpty(str3)) {
                    ArrayList arrayList3 = new ArrayList();
                    arrayList3.add(str3);
                    searchPoi.getPoiExtra().put("poi_deep_info", arrayList3);
                }
            }
        }
    }

    private static boolean a(List<POI> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (POI as : list) {
            SearchPoi searchPoi = (SearchPoi) as.as(SearchPoi.class);
            if (2 == searchPoi.getPoiChildrenInfo().childType) {
                return true;
            }
            if (1 == searchPoi.getPoiChildrenInfo().childType) {
                return true;
            }
        }
        return false;
    }

    public static void a(com.autonavi.bl.search.InfoliteResult infoliteResult, aus aus, InfoliteParam infoliteParam) {
        if (infoliteResult.Result.poi_list != null && infoliteResult.Result.poi_list.size() != 0 && infoliteParam.pagenum == 1 && infoliteResult.Result.classify != null && infoliteResult.Result.classify.size() != 0) {
            Iterator<Classify> it = infoliteResult.Result.classify.iterator();
            while (it.hasNext()) {
                Classify next = it.next();
                if ("label".equals(next.ctype) && next.category != null && next.category.size() > 0) {
                    a[] aVarArr = new a[next.category.size()];
                    for (int i = 0; i < next.category.size(); i++) {
                        aVarArr[i] = new a(next.category.get(i).name, next.category.get(i).value);
                    }
                    aus.d = new aui(next.checkedvalue, next.name, next.ctype, aVarArr);
                    return;
                }
            }
        }
    }

    @SuppressFBWarnings({"UC_USELESS_OBJECT"})
    public static void b(com.autonavi.bl.search.InfoliteResult infoliteResult, aus aus, InfoliteParam infoliteParam) {
        if (infoliteResult.Result.poi_list != null && infoliteResult.Result.poi_list.size() != 0 && infoliteParam.pagenum == 1 && infoliteResult.Result.classify != null && infoliteResult.Result.classify.size() != 0) {
            Lqii lqii = infoliteResult.Result.lqii;
            aus.b = new Condition();
            if (lqii.classify_range != null) {
                aus.b.setDistrictAdcode(lqii.classify_range.district_adcode);
                aus.b.setDistrictEnable(bcv.a(lqii.classify_range.district_enable, 0));
                aus.b.setNearbyEnable(bcv.a(lqii.classify_range.nearby_enable, 0));
                aus.b.setSubwayAdcode(lqii.classify_range.subway_adcode);
                aus.b.setSubwayEnable(bcv.a(lqii.classify_range.subway_enable, 0));
            }
            aus.c = infoliteResult.Result.classify_json;
            if (!TextUtils.isEmpty(aus.c)) {
                try {
                    JSONArray jSONArray = new JSONArray();
                    JSONArray jSONArray2 = new JSONArray(aus.c);
                    for (int i = 0; i < jSONArray2.length(); i++) {
                        JSONObject optJSONObject = jSONArray2.optJSONObject(i);
                        if (!"label".equals(optJSONObject.optString("ctype"))) {
                            jSONArray.put(optJSONObject);
                        }
                    }
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("classify", jSONArray);
                    aus.b.parseConditions(jSONObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void c(com.autonavi.bl.search.InfoliteResult infoliteResult, aus aus, InfoliteParam infoliteParam) {
        if (infoliteResult.Result.poi_list != null && infoliteResult.Result.poi_list.size() != 0 && infoliteParam.pagenum == 1 && infoliteResult.Result.scenefilter != null && infoliteResult.Result.scenefilter.size() != 0) {
            aus.e = new ArrayList<>();
            Iterator<Scenefilter> it = infoliteResult.Result.scenefilter.iterator();
            while (it.hasNext()) {
                Scenefilter next = it.next();
                if (!TextUtils.isEmpty(next.name) && !TextUtils.isEmpty(next.value)) {
                    Condition condition = new Condition();
                    condition.name = next.name;
                    condition.value = next.value;
                    aus.e.add(condition);
                }
            }
        }
    }

    public static void a(Suggestion suggestion, ArrayList<String> arrayList, ArrayList<CitySuggestion> arrayList2) {
        if (arrayList != null && arrayList2 != null) {
            arrayList.clear();
            arrayList2.clear();
            if (suggestion != null) {
                if (suggestion.keywords != null && suggestion.keywords.length > 0) {
                    arrayList.addAll(Arrays.asList(suggestion.keywords));
                }
                if (suggestion.regions != null && suggestion.regions.size() > 0) {
                    Iterator<SuggestionRegionInfo> it = suggestion.regions.iterator();
                    while (it.hasNext()) {
                        SuggestionRegionInfo next = it.next();
                        CitySuggestion citySuggestion = new CitySuggestion();
                        citySuggestion.name = next.name;
                        citySuggestion.ename = next.ename;
                        citySuggestion.adcode = next.adcode;
                        citySuggestion.citycode = next.areacode;
                        citySuggestion.resultnum = bcv.a(next.total, 0);
                        arrayList2.add(citySuggestion);
                    }
                }
            }
        }
    }
}
